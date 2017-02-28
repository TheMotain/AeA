package Parser;

import ADN.SequenceADN;
import Utils.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Parser selon la methode Smith & Waterman
 * <p>
 * Created by dalencourt on 13/02/17.
 */
public class SmithWatermanParser extends AbstractParser {

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
	private int tableChoix[][];

	/**
	 * Séquence d'origine
	 */
	private String sequence1;

	/**
	 * Séquence d'origine reverse
	 */
	private String sequence2;

	private boolean[] tabMatch;

	/**
	 * Constructor
	 *
	 * @param sequenceADN
	 */
	public SmithWatermanParser(final SequenceADN sequenceADN) {
		super(sequenceADN);
		tableChoix = new int[sequenceADN.getSize()][sequenceADN.getSize()];
		sequence1 = sequenceADN.getAdnSequence();
		sequence2 = StringUtils.reverse(sequenceADN.getAdnSequence());
		//initialisation du tableau
		for (int i = 0; i < tableChoix.length; i++) {
			for (int j = 0; j < tableChoix[i].length; j++) {
				tableChoix[i][j] = -1;
			}
		}
		tabMatch = new boolean[sequence1.length()];
		for (int i = 0; i < tabMatch.length; i++) {
			tabMatch[i] = false;
		}
	}

	public static void main(String[] args) {
		SequenceADN seq = new SequenceADN();
		//		seq.setAdnSequence("AACGU");
		//		seq.setAdnSequence("ACGUAGAAACCCCCGUAAUAUGUGCACAUAUUACGGGGGUUUCUACGU");
		//		seq.setAdnSequence("ACGUAGAAACCCCCGUAAUAUGUGACGCCCACAUAUUACGGGGGUUUCUACGU");
		seq.setAdnSequence("ACGUAGGAAAACCCCCGUAAUAUGUGACGCCCACAUAUUACGGGGGUUUCUACGU");
		new SmithWatermanParser(seq).runParser();
	}

	@Override
	public char[] runParser() {
		int i = sequence1.length() - 1;
		int j = sequence2.length() - 1;
		recurrenceWmithWaterman(i, j);

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
		try {
			PrintWriter buffer = new PrintWriter(new FileWriter(new File("output.csv")));
			buffer.write(";");
			for (int i = 0; i < sequence1.length(); i++) {
				buffer.write(sequence1.charAt(i) + ";");
			}
			buffer.write("\n");
			for (int j = 0; j < sequence2.length(); j++) {
				buffer.write(sequence2.charAt(j) + ";");
				for (int i = 0; i < sequence1.length(); i++) {
					buffer.write(tableChoix[i][j] + ";");
				}
				buffer.write("\n");
			}
			buffer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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

			} else {
				apparaiment[i] = '.';
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
		int i_index = 0;
		int j_index = 0;
		int max_value = 0;
		for (int i = 0; i < tableChoix.length; i++) {
			for (int j = 0; j < tableChoix[i].length; j++) {
				if (tableChoix[i][j] > max_value) {
					max_value = tableChoix[i][j];
					i_index = i;
					j_index = j;
				}
			}
		}

		InDelSubMatch[] tabRemonte = new InDelSubMatch[sequenceADN.getSize()];
		int i = i_index;
		int j = j_index;
		while (i >= 0 && j >= 0) {
			Integer left = null;
			Integer leftup = null;
			if (j > 0) {
				left = tableChoix[i - 1][j];
				if (i > 0) {
					leftup = tableChoix[i - 1][j - 1];
				}
			}
			if (leftup != null && leftup >= left) {
				tabRemonte[i] = InDelSubMatch.MATCH;
				if (leftup == 0) {
					tabRemonte[i - 1] = InDelSubMatch.MATCH;
					break;
				}
				j--;
				i--;
			} else if (left != null) {
				tabRemonte[i] = InDelSubMatch.INS;
				if (left == 0) {
					tabRemonte[i - 1] = InDelSubMatch.INS;
					break;
				}
				i--;
			} else {
				tabRemonte[i] = InDelSubMatch.DEL;
				j--;
			}
		}
		return tabRemonte;
	}

	/**
	 * permet d'afficher la matrice dans la console
	 */
	private void logMatrice() {
		int i;
		int j;
		System.out.print("\t\t|");
		for (i = 0; i < sequence1.length(); i++) {
			System.out.print("\t" + sequence1.charAt(i) + "\t|");
		}
		System.out.println();

		for (j = 0; j < sequence2.length(); j++) {
			System.out.print("\t" + sequence2.charAt(j) + "\t|");
			for (i = 0; i < sequence1.length(); i++) {
				if (tableChoix[i][j] <= -100 || tableChoix[i][j] >= 100) {
					System.out.print("\t" + tableChoix[i][j] + "|");
				} else {
					System.out.print("\t" + tableChoix[i][j] + "\t|");
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
	private int recurrenceWmithWaterman(int i, int j) {
		if (tableChoix[i][j] >= 0) {
			return tableChoix[i][j];
		}
		if (i == 0 && j == 0) {
			tableChoix[i][j] = 0;
		} else if (i == 0) {
			tableChoix[i][j] = 0;
		} else if (j == 0) {
			tableChoix[i][j] = 0;
		} else if (sequenceADN.getComplementaires(sequence1.charAt(i) + "").contains(sequence2.charAt(j) + "")) {
			tableChoix[i][j] = Math.max(0, Math.max(recurrenceWmithWaterman(i - 1, j - 1) + MATCH,
					Math.max(recurrenceWmithWaterman(i - 1, j) + DEL, recurrenceWmithWaterman(i, j - 1) + INS)));
		} else {
			tableChoix[i][j] = Math.max(0, Math
					.max(recurrenceWmithWaterman(i - 1, j) + DEL, recurrenceWmithWaterman(i, j - 1) + INS));
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
