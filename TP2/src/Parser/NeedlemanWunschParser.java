package Parser;

import ADN.SequenceADN;
import Utils.StringUtils;

/**
 * Parser selon la methode needleman wunsch
 * <p>
 * Created by dalencourt on 13/02/17.
 */
public class NeedlemanWunschParser extends AbstractParser {

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
	 * Stock les calculs de score optimal pour la méthode Needleman Wunsch
	 */
	private Integer tableChoix[][];

	/**
	 * Séquence d'origine
	 */
	private String sequence1;

	/**
	 * Séquence d'origine reverse
	 */
	private String sequence2;

	/**
	 * Constructor
	 *
	 * @param sequenceADN
	 */
	public NeedlemanWunschParser(final SequenceADN sequenceADN) {
		super(sequenceADN);
		tableChoix = new Integer[sequenceADN.getSize() + 1][sequenceADN.getSize() + 1];
		sequence1 = sequenceADN.getAdnSequence();
		sequence2 = StringUtils.reverse(sequenceADN.getAdnSequence());
		//initialisation du tableau
		for (int i = 0; i < tableChoix.length; i++) {
			for (int j = 0; j < tableChoix[i].length; j++) {
				tableChoix[i][j] = null;
			}
		}
	}

	public static void main(String[] args) {
		SequenceADN seq = new SequenceADN();
		seq.setAdnSequence("ACGUAGAAACCCCCGUAAUAUGUGACGCCCACAUAUUACGGGGGUUUCUACGU");
		new NeedlemanWunschParser(seq).runParser();
	}

	@Override
	public char[] runParser() {
		int i = sequence1.length();
		int j = sequence2.length();
		recurrenceNeedlemanWunsch(i, j);

		logMatrice();
		exportMatrice();

		InDelSubMatch[] tabRemontee = remontee();

		logMatch(tabRemontee);

		char[] apparaiment = apparaimentNucleotide(tabRemontee);

		logApparaiment(apparaiment);

		return apparaiment;
	}

	/**
	 * Permet d'exporter la matrice au format CSV
	 */
	private void exportMatrice() {
	/*	try {
			PrintWriter buffer = new PrintWriter(new FileWriter(new File("output.csv")));
			buffer.write(";;");
			for (int i = 0; i < sequence1.length(); i++) {
				buffer
			}
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

	/**
	 * Affiche l'apparaiment des nucléothides
	 *
	 * @param apparaiment
	 * 		Tableau d'apparaiment
	 */
	private void logApparaiment(char[] apparaiment) {
		int i;
		for (i = 0; i < apparaiment.length; i++) {
			System.out.print(apparaiment[i]);
		}
		System.out.println();
	}

	/**
	 * Permet de réaliser l'apparaiment des nucléotides
	 *
	 * @param tabRemontee
	 * 		Le tableau des match missmatch
	 * @return Le tableau des apparaiments
	 */
	private char[] apparaimentNucleotide(final InDelSubMatch[] tabRemontee) {
		char[] apparaiment = new char[tabRemontee.length];
		int i = 0;
		int j = tabRemontee.length - 1;
		while (i < j) {
			if (tabRemontee[i] == InDelSubMatch.MATCH) {
				apparaiment[i] = '(';
				while (tabRemontee[j] != InDelSubMatch.MATCH) {
					apparaiment[j] = '.';
					j--;
				}
				apparaiment[j] = ')';
				j--;

			}
			i++;
		}
		return apparaiment;
	}

	/**
	 * Affiche les match et miss match de la chaine sur elle-même
	 *
	 * @param tabRemontee
	 * 		Tableau de la remontée
	 */
	private void logMatch(InDelSubMatch[] tabRemontee) {
		int i;
		System.out.println(sequenceADN.getAdnSequence());
		for (i = 0; i < sequenceADN.getSize(); i++) {
			if (tabRemontee[i] == InDelSubMatch.MATCH) {
				System.out.print("|");
			} else {
				System.out.print("-");
			}
		}
		System.out.println();
	}

	/**
	 * Effectue la remontée du tableau des choix
	 *
	 * @return Tableau des insertions deletions substitutions et match
	 */
	private InDelSubMatch[] remontee() {
		InDelSubMatch[] tabRemonte = new InDelSubMatch[sequenceADN.getSize()];
		int i = tableChoix.length - 1;
		int j = tableChoix[i].length - 1;
		while (i != 0 && j != 0) {
			Integer left = null;
			Integer leftup = null;
			InDelSubMatch tmp;
			int tmp_i = i;
			int tmp_j = j;
			if (j > 0) {
				left = tableChoix[i - 1][j];
				if (i > 0) {
					leftup = tableChoix[i - 1][j - 1];
				}
			}
			if (leftup != null && leftup >= left) {
				tmp = leftup + MATCH == tableChoix[i][j] ? InDelSubMatch.MATCH : InDelSubMatch.SUB;
				tmp_i--;
				tmp_j--;
			} else if (left != null) {
				tmp = InDelSubMatch.DEL;
				tmp_i--;
			} else {
				tmp = InDelSubMatch.INS;
				tmp_j--;
			}
			tabRemonte[i - 1] = tmp;
			i = tmp_i;
			j = tmp_j;
		}
		return tabRemonte;
	}

	/**
	 * permet d'afficher la matrice dans la console
	 */
	private void logMatrice() {
		int i;
		int j;
		System.out.print("\t\t|\t\t|");
		for (i = 0; i < sequence1.length(); i++) {
			System.out.print("\t" + sequence1.charAt(i) + "\t|");
		}
		System.out.println();

		for (j = 0; j < sequence2.length() + 1; j++) {
			if (j == 0) {
				System.out.print("\t\t|");
			} else {
				System.out.print("\t" + sequence2.charAt(j - 1) + "\t|");
			}
			for (i = 0; i < sequence1.length() + 1; i++) {
				if (tableChoix[i][j] != null) {
					if (tableChoix[i][j] <= -100 || tableChoix[i][j] >= 100) {
						System.out.print("\t" + tableChoix[i][j] + "|");
					} else {
						System.out.print("\t" + tableChoix[i][j] + "\t|");
					}
				} else {
					System.out.print("\t\t|");
				}
			}
			System.out.println();
		}
	}

	/**
	 * Fonction qui effectue la récurrence
	 *
	 * @param i
	 * 		Position de parcours i
	 * @param j
	 * 		Position de parcours j
	 */
	private int recurrenceNeedlemanWunsch(int i, int j) {
		if (tableChoix[i][j] != null) {
			return tableChoix[i][j];
		}
		if (i == 0 && j == 0) {
			tableChoix[i][j] = 0;
		} else if (i == 0) {
			tableChoix[i][j] = recurrenceNeedlemanWunsch(i, j - 1) + INS;
		} else if (j == 0) {
			tableChoix[i][j] = recurrenceNeedlemanWunsch(i - 1, j) + DEL;
		} else if (sequenceADN.getComplementaires(sequence1.charAt(i - 1) + "")
				.contains("" + sequence2.charAt(j - 1))) {
			tableChoix[i][j] = Math.max(recurrenceNeedlemanWunsch(i - 1, j - 1) + MATCH,
					Math.max(recurrenceNeedlemanWunsch(i - 1, j) + DEL, recurrenceNeedlemanWunsch(i, j - 1) + INS));
		} else {
			tableChoix[i][j] = Math.max(recurrenceNeedlemanWunsch(i - 1, j - 1) + SUB,
					Math.max(recurrenceNeedlemanWunsch(i - 1, j) + DEL, recurrenceNeedlemanWunsch(i, j - 1) + INS));
		}
		return tableChoix[i][j];
	}

	enum InDelSubMatch {
		INS,
		DEL,
		SUB,
		MATCH;
	}
}
