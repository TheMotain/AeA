package Parser;

import ADN.SequenceADN;
import Utils.StringUtils;

/**
 * Parser selon la methode needleman wunsch
 * <p>
 * Created by dalencourt on 13/02/17.
 */
public class NeedlemanWunschAlternativParser2 {
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
     * Coût d'une substitution
     */
    private static final int SUB = -1;

    /**
     * Séquence de nucléotides à apparailler
     */
    private final SequenceADN sequenceADN;
    /**
     * Stock les calculs de score optimal pour la méthode Needleman Wunsch
     */
    private final Integer[][] tableChoix;

    /**
     * Constructor
     *
     * @param sequenceADN
     */
    public NeedlemanWunschAlternativParser2(final SequenceADN sequenceADN) {
        this.sequenceADN = sequenceADN;
        this.tableChoix = new Integer[sequenceADN.getSize() + 1][sequenceADN.getSize() + 1];
        //initialisation du tableau
        for (int i = 0; i < tableChoix.length; i++) {
            for (int j = 0; j < tableChoix[i].length; j++) {
                tableChoix[i][j] = null;
            }
        }
        tableChoix[0][0] = 0;
        for (int i = 1; i < tableChoix.length; i++) {
            tableChoix[i][0] = tableChoix[i - 1][0] + INS;
            tableChoix[0][i] = tableChoix[0][i - 1] + DEL;
        }
    }

    public static void main(final String[] args) {
        final SequenceADN seq = new SequenceADN();
        //		seq.setAdnSequence("ACGUA");
        //		seq.setAdnSequence("ACGUAGAAACCCCCGUAAUAUGUGCACAUAUUACGGGGGUUUCUACGU");
        //		seq.setAdnSequence("ACGUAGAAACCCCCGUAAUAUGUGACGCCCACAUAUUACGGGGGUUUCUACGU");
        //		seq.setAdnSequence("ACGUAGCCAAACCCCCGUAAUAUGUGACGCCCACAUAUUACGGGGGUUUCUACGU");
        seq.setAdnSequence(
                "UGCUUCCGGCCUGUUCCCUGAGACCUCAAGUGUGAGUGUACUAUUGAUGCUUCACACCUGGGCUCUCCGGGUACCAGGACGGUUUGAGCA");
        new NeedlemanWunschAlternativParser2(seq).runParser();
    }

    public char[] runParser() {
        int i = 0;
        int j = sequenceADN.length() - 1;
        recurrenceNeedlemanWunsch(i, j);

        for (i = 0; i < tableChoix.length; i++) {
            System.out.print("\t" + i + "\t|");
        }
        System.out.println();
        for (i = 0; i < tableChoix.length; i++) {
            for (j = 0; j < tableChoix[i].length; j++) {
                if (tableChoix[i][j] != null) {
                    if (tableChoix[i][j] > -100 && tableChoix[i][j] < 100) {
                        System.out.print("\t" + tableChoix[i][j] + "\t|");
                    } else {
                        System.out.print("\t" + tableChoix[i][j] + "|");
                    }
                } else {
                    System.out.print("\t\t|");
                }
            }
            System.out.println();
        }
        i = 0;
        j = sequenceADN.length() - 1;
        final int j_pos = tableChoix.length - 1;
        final int i_pos = tableChoix.length - 1;
        char[] apparaiment = new char[sequenceADN.length()];
        for (int n = 0; n < apparaiment.length; n++) {
            apparaiment[n] = 'X';
        }
        apparaiment = apparaiment(i, j, j_pos, i_pos, apparaiment, 0, 0);
        //		apparaiment = apparaimentAlternatif(i, j, i_pos, j_pos, apparaiment);
        System.out.println(sequenceADN.getAdnSequence());
        for (i = 0; i < sequenceADN.length(); i++) {
            System.out.print(apparaiment[i]);
        }
        System.out.println();
        System.out.println(valide(apparaiment));
        return apparaiment;
    }

    private char[] apparaiment(final int i_param, final int j_param, final int j_pos_param, final int i_pos_param,
                               final char[] apparaiment_param,
                               final int countmatchopen_param, final int countmatchclose_param) {
        final int i = i_param;
        final int j = j_param;
        final int j_pos = j_pos_param;
        final int i_pos = i_pos_param;
        final char[] apparaiment = apparaiment_param;
        final int countmatchopen = countmatchopen_param;
        final int countmatchclose = countmatchclose_param;
        while (i <= j) {
            if (i == j) {
                apparaiment[i] = '.';
                break;
            }

        }
        return apparaiment;
    }

    private boolean matchable(final int i_pos, final int j_pos, final int countmatch) {
        if (countmatch >= 3) {
            return true;
        }
        if (tableChoix[i_pos - 1][j_pos - 1] == null ||
                tableChoix[i_pos - 1][j_pos - 1] + MATCH != tableChoix[i_pos][j_pos] ||
                tableChoix[i_pos - 1][j_pos] > tableChoix[i_pos - 1][j_pos - 1] ||
                tableChoix[i_pos][j_pos - 1] > tableChoix[i_pos - 1][j_pos - 1]) {
            return false;
        }
        return matchable(i_pos - 1, j_pos - 1, countmatch + 1);
    }

    private boolean valide(final char[] apparaiment) {
        int open = 0;
        int close = 0;
        for (int i = 0; i < apparaiment.length; i++) {
            if (apparaiment[i] == '(') {
                open++;
            }
            if (apparaiment[i] == ')') {
                close++;
            }
        }
        int count = 0;
        boolean match = false;
        boolean matchopen = true;
        for (int i = 0; i < apparaiment.length; i++) {
            if (apparaiment[i] == '(') {
                if (!match) {
                    if (count > 3) {
                        return false;
                    }
                    count = 0;
                }
                matchopen = true;
                match = true;
                count++;
            } else if (apparaiment[i] == '.') {
                if (match) {
                    if (count < 3) {
                        return false;
                    }
                    count = 0;
                }
                match = false;
                count++;
            } else if (apparaiment[i] == ')') {
                if (!match) {
                    if (count > 3 && !matchopen) {
                        return false;
                    }
                    count = 0;
                }
                matchopen = false;
                match = true;
                count++;
            } else {
                return false;
            }
        }
        return open == close;
    }

    /**
     * Fonction qui effectue la récurrence
     *
     * @param i
     *         Position de parcours i
     * @param j
     *         Position de parcours j
     */
    private int recurrenceNeedlemanWunsch(final int i, final int j) {
        final int i_index = sequenceADN.length() - i;
        final int j_index = j + 1;
        if (tableChoix[i_index][j_index] != null) {
            return tableChoix[i_index][j_index];
        }
        if (i >= j) {
            tableChoix[i_index][j_index] = 0;
        } else if (i_index == 0) {
            tableChoix[i_index][j_index] = recurrenceNeedlemanWunsch(i, j - 1) + INS;
        } else if (j_index == 0) {
            tableChoix[i_index][j_index] = recurrenceNeedlemanWunsch(i + 1, j) + DEL;
        } else if (match(i, j)) {
            tableChoix[i_index][j_index] = Math.max(recurrenceNeedlemanWunsch(i + 1, j - 1) + MATCH,
                    Math.max(recurrenceNeedlemanWunsch(i + 1, j) + DEL, recurrenceNeedlemanWunsch(i, j - 1) + INS));
        } else {
            tableChoix[i_index][j_index] = Math
                    .max(recurrenceNeedlemanWunsch(i + 1, j + 1) + SUB,
                            Math.max(recurrenceNeedlemanWunsch(i + 1, j) + DEL,
                                    recurrenceNeedlemanWunsch(i, j - 1) + INS));
        }
        return tableChoix[i_index][j_index];
    }

    private boolean match(final int i, final int j) {
        String subsequence1 = StringUtils.reverse(sequenceADN.substring(i - 2, i + 1));
        String subsequence2 = sequenceADN.substring(j, j + 3);
        final boolean less2 = (subsequence1.length() == 3 && subsequence2.length() == 3) &&
                sequenceADN.getComplementaires(subsequence1).contains(subsequence2);
        subsequence1 = StringUtils.reverse(sequenceADN.substring(i - 1, i + 2));
        subsequence2 = sequenceADN.substring(j - 1, j + 2);
        final boolean less1 = (subsequence1.length() == 3 && subsequence2.length() == 3) &&
                sequenceADN.getComplementaires(subsequence1).contains(subsequence2);
        subsequence1 = StringUtils.reverse(sequenceADN.substring(i, i + 3));
        subsequence2 = sequenceADN.substring(j - 2, j + 1);
        final boolean less0 = (subsequence1.length() == 3 && subsequence2.length() == 3) &&
                sequenceADN.getComplementaires(subsequence1).contains(subsequence2);
        return less2 || less1 || less0;
    }
}
