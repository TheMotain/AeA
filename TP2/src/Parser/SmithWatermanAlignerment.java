package Parser;

import ADN.SequenceADN;

import javax.management.BadStringOperationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Parser selon la methode Smith & Waterman
 * <p>
 * Created by dalencourt on 13/02/17.
 */
public class SmithWatermanAlignerment {

	/**
	 * Cout d'une insertion
	 */
	private static final int INS = -2;

	/**
	 * Cout d'une délétion
	 */
	private static final int DEL = -2;

	/**
	 * Cout d'une substitution
	 */
	private static final int SUB = -1;

	/**
	 * Gain d'un match
	 */
	private static final int MATCH = 2;

	/**
	 * Séquence d'origine
	 */
	private final SequenceADN arnMessage;

	/**
	 * Séquence d'origine reverse
	 */
	private final SequenceADN microARN;

	/**
	 * constructeur
	 *
	 * @param arnMessage
	 * 		ARN messagé
	 * @param microARN
	 * 		Pré-micro ARN
	 */
	public SmithWatermanAlignerment(final SequenceADN arnMessage, final SequenceADN microARN) {
		this.arnMessage = arnMessage;
		this.microARN = microARN;
	}

	/**
	 * Permet de lancer la séquence d'alignement qui trouvera le meilleur
	 *
	 * @return Retourne le meilleurs alignement possible entre l'arnMessage et le microARN
	 * @throws BadStringOperationException
	 * 		Si un caractère non attendu est rencontré
	 */
	public String[] runAlignement() throws BadStringOperationException {
		final List<Integer> startPoints = new ArrayList<>();
		final ParserShiftOr parserShiftOr = new ParserShiftOr(arnMessage);
		for (final String str : microARN.getComplementaires(microARN.substring(0, 7))) {
			startPoints.addAll(parserShiftOr.run(str));
		}
		final String subMicroARN = microARN.substring(7);
		String subArnMessa;
		InDelSubMatch[] alignementFinal = null;
		int finalIndex = 0;
		int scoreMax = 0;
		// évaluation des différents apparaiments possibles
		for (final Integer idx : startPoints) {
			subArnMessa = arnMessage.substring(idx + 7, idx + 7 + (subMicroARN.length() + 5));
			final int[][] tableCouts = new int[subArnMessa.length()][subMicroARN.length()];
			// exécution de la récurence de Smith Watermen
			recurrenceSmithWaterman(tableCouts, subArnMessa, subMicroARN, subArnMessa.length() - 1,
					subMicroARN.length() - 1);
			// Calcul du score maximum
			final MaximumCout maximumCout = new MaximumCout(tableCouts).invoke();
			final InDelSubMatch[] alignement = remontee(tableCouts, maximumCout);
			// On garde l'alignement avec le score maximum puis avec le moins de gap possible
			if (alignementFinal == null ||
					scoreMax < tableCouts[maximumCout.getI_pos()][maximumCout.getJ_pos()] ||
					(scoreMax < tableCouts[maximumCout.getI_pos()][maximumCout.getJ_pos()] &&
							alignement.length < alignementFinal.length)) {
				alignementFinal = alignement;
				finalIndex = idx;
				scoreMax = tableCouts[maximumCout.getI_pos()][maximumCout.getJ_pos()];
			}
		}

		if (alignementFinal != null) {
			System.out.println("Match:" + (7 + Collections.frequency(Arrays.asList(alignementFinal), InDelSubMatch
					.MATCH)));
			System.out.println("Ins:" + Collections.frequency(Arrays.asList(alignementFinal), InDelSubMatch.INS));
			System.out.println("Del:" + Collections.frequency(Arrays.asList(alignementFinal), InDelSubMatch.DEL));
			System.out.println("Sub:" + Collections.frequency(Arrays.asList(alignementFinal), InDelSubMatch.SUB));
		}
		return generateResultat(alignementFinal, finalIndex);
	}

	/**
	 * Génère l'alignement de résultat entre le microARN et l'ARN messagé
	 *
	 * @param alignementFinal
	 * 		alignement final qui a été trouvé
	 * @param finalIndex
	 * 		index final de démarage de l'ARN messagé pour l'alignement
	 * @return Les deux séquences alignées
	 */
	private String[] generateResultat(final InDelSubMatch[] alignementFinal,
	                                  final int finalIndex) {
		String subMicroARN = microARN.substring(7);
		String subArnMessa;
		String resArnMessage = arnMessage.substring(finalIndex, finalIndex + 7);
		String resMicroArn = microARN.substring(0, 7);
		if (alignementFinal != null) {
			subArnMessa = arnMessage.substring(finalIndex + 7);
			for (final InDelSubMatch current : alignementFinal) {
				switch (current) {
					case DEL:
						resMicroArn = resMicroArn.concat("-");
						resArnMessage = resArnMessage.concat(subArnMessa.charAt(0) + "");
						subArnMessa = subArnMessa.substring(1);
						break;
					case INS:
						resArnMessage = resArnMessage.concat("-");
						resMicroArn = resMicroArn.concat(subMicroARN.charAt(0) + "");
						subMicroARN = subMicroARN.substring(1);
						break;
					case SUB:
					case MATCH:
						resArnMessage = resArnMessage.concat(subArnMessa.charAt(0) + "");
						resMicroArn = resMicroArn.concat(subMicroARN.charAt(0) + "");
						subArnMessa = subArnMessa.substring(1);
						subMicroARN = subMicroARN.substring(1);
						break;
				}
			}
		}

		return new String[]{resArnMessage, resMicroArn};
	}

	/**
	 * Effectue la remontée du tableau des choix<br/>
	 * Si décalage en i -> DELETE
	 * Si décalage en j -> INSERT
	 * Si décalage en i - j -> MATCH / SUBSTITUTION
	 *
	 * @param tableCouts
	 * 		table des coûts
	 * @param maximumCout
	 * 		position du coût maximum
	 * @return Tableau des insertions deletions substitutions et match
	 */
	private InDelSubMatch[] remontee(final int[][] tableCouts, final MaximumCout maximumCout) {
		final List<InDelSubMatch> reconstruction = new ArrayList<>();
		int i = maximumCout.getI_pos();
		int j = maximumCout.getJ_pos();
		while (!(i == 0 && j == 0)) {
			if (i == 0) {
				reconstruction.add(0, InDelSubMatch.INS);
				j--;
			} else if (j == 0) {
				reconstruction.add(0, InDelSubMatch.DEL);
				i--;
			} else {
				if (tableCouts[i - 1][j - 1] != 0 && tableCouts[i - 1][j - 1] >= tableCouts[i][j - 1] &&
						tableCouts[i - 1][j - 1] >= tableCouts[i - 1][j]) {
					if (tableCouts[i][j] == tableCouts[i - 1][j - 1] + MATCH) {
						reconstruction.add(0, InDelSubMatch.MATCH);
					} else {
						reconstruction.add(0, InDelSubMatch.SUB);
					}
					i--;
					j--;
				} else if (tableCouts[i - 1][j] != 0 && tableCouts[i - 1][j] >= tableCouts[i][j - 1]) {
					reconstruction.add(0, InDelSubMatch.DEL);
					i--;
				} else if (tableCouts[i][j - 1] != 0) {
					reconstruction.add(0, InDelSubMatch.INS);
					j--;
				} else {
					while (i != 0 && j != 0) {
						if (i != 0) {
							reconstruction.add(0, InDelSubMatch.DEL);
							i--;
						}
						if (j != 0) {
							reconstruction.add(0, InDelSubMatch.INS);
							j--;
						}
						break;
					}
				}
			}
		}

		return reconstruction.toArray(new InDelSubMatch[reconstruction.size()]);
	}

	/**
	 * Fonction qui effectue la récurrence selon la méthode Smith et Waterman avec comme référenciel d'égalité la
	 * complémentarité de deux nucléothides
	 *
	 * @param tableCouts
	 * 		table des coûts
	 * @param arnMessage
	 * 		arn messagé à aligner
	 * @param microARN
	 * 		micro arn à aligner
	 * @param i
	 * 		Position de parcours i
	 * @param j
	 * 		Position de parcours j
	 */
	private int recurrenceSmithWaterman(final int[][] tableCouts, final String arnMessage, final String microARN,
	                                    final int i, final int j) {
		if (tableCouts[i][j] > 0) {
			return tableCouts[i][j];
		}
		if (i == 0 && j == 0) {
			tableCouts[i][j] = 0;
		} else if (i == 0) {
			tableCouts[i][j] = 0;
		} else if (j == 0) {
			tableCouts[i][j] = 0;
		} else if (this.microARN.getComplementaires(microARN.charAt(j) + "").contains(arnMessage.charAt(i) + "")) {
			tableCouts[i][j] = Math
					.max(0, Math.max(recurrenceSmithWaterman(tableCouts, arnMessage, microARN, i - 1, j - 1) + MATCH,
							Math.max(recurrenceSmithWaterman(tableCouts, arnMessage, microARN, i - 1, j - 1) + SUB,
									Math.max(recurrenceSmithWaterman(tableCouts, arnMessage, microARN, i - 1, j) + DEL,
											recurrenceSmithWaterman(tableCouts, arnMessage, microARN, i, j - 1) +
													INS))));

		} else {
			tableCouts[i][j] = Math
					.max(0, Math.max(recurrenceSmithWaterman(tableCouts, arnMessage, microARN, i - 1, j - 1) + SUB,
							Math.max(recurrenceSmithWaterman(tableCouts, arnMessage, microARN, i - 1, j) + DEL,
									recurrenceSmithWaterman(tableCouts, arnMessage, microARN, i, j - 1) + INS)));
		}
		return tableCouts[i][j];
	}

	/**
	 * Enumération définissant les opérations possibles
	 */
	enum InDelSubMatch {
		INS,
		DEL,
		SUB,
		MATCH
	}

	/**
	 * Coût maximum d'une table de couts
	 */
	private class MaximumCout {
		/**
		 * Table des coûts
		 */
		private final int[][] tableCouts;
		/**
		 * position i du maximum
		 */
		private int i_pos;
		/**
		 * position j du maximum
		 */
		private int j_pos;

		/**
		 * Constructeur
		 *
		 * @param tableCouts
		 * 		Table des coûts
		 */
		public MaximumCout(final int[][] tableCouts) {
			this.tableCouts = tableCouts;
			this.i_pos = 0;
			this.j_pos = 0;
		}

		/**
		 * Récupère la position du maximum en i
		 *
		 * @return Position en i
		 */
		public int getI_pos() {
			return i_pos;
		}

		/**
		 * Récupère la position du maximum en j
		 *
		 * @return Position en j
		 */
		public int getJ_pos() {
			return j_pos;
		}

		/**
		 * Méthode à invoquer pour trouver le coût maximum
		 *
		 * @return Retourne l'instance du coût maximum contenant les informations trouvées
		 */
		public MaximumCout invoke() {
			int max = 0;
			for (int i = 0; i < tableCouts.length; i++) {
				for (int j = 0; j < tableCouts[i].length; j++) {
					if (tableCouts[i][j] > max) {
						j_pos = j;
						i_pos = i;
						max = tableCouts[i][j];
					}
				}
			}
			return this;
		}
	}
}
