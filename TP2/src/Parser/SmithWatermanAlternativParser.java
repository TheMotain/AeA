package Parser;

import ADN.SequenceADN;

/**
 * Parser selon la methode Smith & Waterman
 * <p>
 * Created by dalencourt on 13/02/17.
 */
public class SmithWatermanAlternativParser extends AbstractParser {

	/**
	 * Cout d'une insertion
	 */
	private static final int INS = -2;

	/**
	 * Cout d'une délétion
	 */
	private static final int DEL = -2;

	/**
	 * Gain d'un match
	 */
	private static final int MATCH = 2;

	/**
	 * Stock les calculs de score optimal pour la méthode Needleman Wunsch
	 */
	private int tableChoix[][];

	/**
	 * Constructor
	 *
	 * @param sequenceADN
	 */
	public SmithWatermanAlternativParser(final SequenceADN sequenceADN) {
		super(sequenceADN);
		tableChoix = new int[sequenceADN.getSize()][sequenceADN.getSize()];
		//initialisation du tableau
		for (int i = 0; i < tableChoix.length; i++) {
			for (int j = 0; j < tableChoix[i].length; j++) {
				tableChoix[i][j] = -1;
			}
		}
	}

	public static void main(String[] args) {
		SequenceADN seq = new SequenceADN();
		seq.setAdnSequence("ACGUA");
		//		seq.setAdnSequence("ACGUAGAAACCCCCGUAAUAUGUGCACAUAUUACGGGGGUUUCUACGU");
		//		seq.setAdnSequence("ACGUAGAAACCCCCGUAAUAUGUGACGCCCACAUAUUACGGGGGUUUCUACGU");
		//		seq.setAdnSequence("ACGUAGGAAAACCCCCGUAAUAUGUGACGCCCACAUAUUACGGGGGUUUCUACGU");
		new SmithWatermanAlternativParser(seq).runParser();
	}

	@Override
	public char[] runParser() {
		int i = 0;
		int j = sequenceADN.length() - 1;
		recurrenceSmithWatermanAlternativ(i, j);
		for (i = 0; i < tableChoix.length; i++) {
			for (j = 0; j < tableChoix[i].length; j++) {
				System.out.print("\t" + tableChoix[i][j] + "\t|");
			}
			System.out.println();
		}
		i = 0;
		j = sequenceADN.length() - 1;
		int j_pos = tableChoix.length - 1;
		int i_pos = tableChoix.length - 1;
		char[] apparaiment = new char[sequenceADN.length()];
		while (i < j) {
			if (i == j) {
				apparaiment[i] = '.';
			}
			int top = tableChoix[i_pos - 1][j_pos];
			int left = tableChoix[i_pos][j_pos - 1];
			int topleft = tableChoix[i_pos - 1][j_pos - 1];
			if (topleft >= top && topleft >= left) {
				j_pos--;
				i_pos--;
				apparaiment[i] = '(';
				apparaiment[j] = ')';
				i++;
				j--;
			} else if (left >= top) {
				j_pos--;
				apparaiment[j] = '.';
				j--;
			} else {
				i_pos--;
				apparaiment[i] = '.';
				i++;
			}
		}
		System.out.println(sequenceADN.getAdnSequence());
		for (i = 0; i < sequenceADN.length(); i++) {
			System.out.print(apparaiment[i]);
		}
		System.out.println();
		return new char[0];
	}


	/**
	 * Fonction qui effectue la récurrence
	 *
	 * @param i
	 * 		Position de parcours i
	 * @param j
	 * 		Position de parcours j
	 */
	private int recurrenceSmithWatermanAlternativ(int i, int j) {
		int i_index = sequenceADN.length() - 1 - i;
		if (tableChoix[i_index][j] >= 0) {
			return tableChoix[i_index][j];
		}
		if (i >= j) {
			tableChoix[i][j] = 0;
		} else if (i_index == 0) {
			tableChoix[i_index][j] = 0;
		} else if (j == 0) {
			tableChoix[i_index][j] = 0;
		} else if (sequenceADN.getComplementaires(sequenceADN.charAt(i) + "").contains(sequenceADN.charAt(j) + "")) {
			tableChoix[i_index][j] = Math.max(recurrenceSmithWatermanAlternativ(i + 1, j - 1) + MATCH,
					Math.max(recurrenceSmithWatermanAlternativ(i + 1, j) + DEL,
							recurrenceSmithWatermanAlternativ(i, j - 1) + INS));
		} else {
			tableChoix[i_index][j] = Math.max(0, Math
					.max(recurrenceSmithWatermanAlternativ(i + 1, j) + DEL,
							recurrenceSmithWatermanAlternativ(i, j - 1) + INS));
		}
		return tableChoix[i_index][j];
	}
}
