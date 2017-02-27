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

	private String sequence1;

	private String sequence2;

	public NeedlemanWunschParser(final String sequence1, final String sequence2) {
		super(null);
		this.sequence1 = sequence1;
		this.sequence2 = sequence2;
		tableChoix = new Integer[sequence1.length() + 1][sequence2.length() + 1];
		//initialisation du tableau
		for (int i = 0; i < tableChoix.length; i++) {
			for (int j = 0; j < tableChoix[i].length; j++) {
				tableChoix[i][j] = null;
			}
		}
	}

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
		new NeedlemanWunschParser("ACGGCTAT", "ACTGTAG").runParser();
	}

	@Override
	public boolean[] runParser() {
		int i = sequence1.length();
		int j = sequence2.length();
		recurrenceNeedlemanWunsch(i, j);

		logMatrice();

		
		return new boolean[0];
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
					System.out.print("\t" + tableChoix[i][j] + "\t|");
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
		} else if (sequence1.charAt(i - 1) == sequence2.charAt(j - 1)) {
			tableChoix[i][j] = Math.max(recurrenceNeedlemanWunsch(i - 1, j - 1) + MATCH,
					Math.max(recurrenceNeedlemanWunsch(i - 1, j) + DEL, recurrenceNeedlemanWunsch(i, j - 1) + INS));
		} else {
			tableChoix[i][j] = Math.max(recurrenceNeedlemanWunsch(i - 1, j - 1) + SUB,
					Math.max(recurrenceNeedlemanWunsch(i - 1, j) + DEL, recurrenceNeedlemanWunsch(i, j - 1) + INS));
		}
		return tableChoix[i][j];
	}
}
