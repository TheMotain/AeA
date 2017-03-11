package Parser;

import ADN.SequenceADN;
import Utils.StringUtils;

/**
 * Parser selon la methode needleman wunsch
 * <p>
 * Created by dalencourt on 13/02/17.
 */
public class NeedlemanWunschAlternativParser {
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
     *         Séquence de micro ARN à contrôler
     */
    public NeedlemanWunschAlternativParser(final SequenceADN sequenceADN) {
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

    /**
     * Réalisation de l'apparaiment
     *
     * @return Apparaiment réalisé
     */
    public char[] runParser() {
        int i = 0;
        int j = sequenceADN.length() - 1;
        recurrenceNeedlemanWunsch(i, j);

        i = 0;
        j = sequenceADN.length() - 1;
        final int j_pos = tableChoix.length - 1;
        final int i_pos = tableChoix.length - 1;
        char[] apparaiment = new char[sequenceADN.length()];
        for (int n = 0; n < apparaiment.length; n++) {
            apparaiment[n] = 'X';
        }
        apparaiment = apparaiment(i, j, j_pos, i_pos, apparaiment, 0, 0);
        System.out.println("Validité : " + valide(apparaiment));
        return apparaiment;
    }

    /**
     * Permet de réaliser un apparaiment en fonction du tableau des choix réalisé en amont. Fonctionnent récursif en cas
     * d'égalité entre les gap. Utilisation de copies de paramètres afin d'être certain de ne pas altéré les méthodes
     * appellantes.
     *
     * @param i_param
     *         position i dans la chaine
     * @param j_param
     *         position j dans la chaine
     * @param j_pos_param
     *         position j dans le tableau des choix
     * @param i_pos_param
     *         Position i dans le tableau des choix
     * @param apparaiment_param
     *         Tableau des apparaiment réalisé
     * @param countmatchopen_param
     *         Nombre de '(' consécutif déjà calculé
     * @param countmatchclose_param
     *         Nombre de ')' consécutif déjà calculé
     *
     * @return L'apparaiment réalisé
     */
    private char[] apparaiment(final int i_param, final int j_param, final int j_pos_param, final int i_pos_param,
                               final char[] apparaiment_param,
                               final int countmatchopen_param, final int countmatchclose_param) {
        int i = i_param;
        int j = j_param;
        int j_pos = j_pos_param;
        int i_pos = i_pos_param;
        final char[] apparaiment = apparaiment_param;
        int countmatchopen = countmatchopen_param;
        int countmatchclose = countmatchclose_param;
        while (i <= j) {
            if (i == j) {
                apparaiment[i] = '.';
                break;
            }
            final Integer top = tableChoix[i_pos - 1][j_pos];
            final Integer left = tableChoix[i_pos][j_pos - 1];
            final Integer topleft = tableChoix[i_pos - 1][j_pos - 1];
            if (null != topleft && topleft == tableChoix[i_pos][j_pos] - MATCH &&
                    (topleft < 0 || ((top == null || topleft >= top) && (left == null || topleft >= left))
                    )) {
                // Le maximum est un match
                final boolean matchableOpen = matchable(i_pos - 1, j_pos - 1, countmatchopen + 1);
                final boolean matchableClose = matchable(i_pos - 1, j_pos - 1, countmatchclose + 1);
                if (!matchableOpen) {
                    // Vérification qu'il est possible de réaliser un match en ouverture
                    i_pos--;
                    apparaiment[i] = '.';
                    i++;
                    countmatchclose = 0;
                } else if (!matchableClose) {
                    // Vérification qu'il est possible de réaliser un match en fermeture
                    j_pos--;
                    apparaiment[j] = '.';
                    j--;
                    countmatchclose = 0;
                } else {
                    j_pos--;
                    i_pos--;
                    apparaiment[i] = '(';
                    apparaiment[j] = ')';
                    i++;
                    j--;
                    countmatchopen++;
                    countmatchclose++;
                }
            } else if (left != null && (top == null || left > top)) {
                // Si le gap est en j
                j_pos--;
                apparaiment[j] = '.';
                j--;
                countmatchclose = 0;
            } else {
                // si le gap est en i
                i_pos--;
                apparaiment[i] = '.';
                i++;
                countmatchopen = 0;
            }
        }
        return apparaiment;
    }

    /**
     * Permet de savoir si un nucléotide peut être sélectioné. Pour cela il faut qu'il y ai trois nucléotides succéssif
     * matchable
     *
     * @param i_pos
     *         Position i dans la remonté du tableau des choix
     * @param j_pos
     *         Position j dans la remonté du tableau des choix
     * @param countmatch
     *         Nombre de match consécutif déjà réalisé
     *
     * @return Vrai ou faux
     */
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

    /**
     * Méthode permettant de valider une séquence d'apparaiment.
     *
     * @param apparaiment
     *         L'apparaiment à valider
     *
     * @return Vrai ou faux
     */
    private boolean valide(final char[] apparaiment) {
        int open = 0;
        int close = 0;
        // Si le nombre d'overture != du nombre de fermeture -> non valide
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
        int size = 0;
        // On définit le début et la fin de la séuence comme étant le premier '(' et le dernier ')'
        for (int i = new String(apparaiment).indexOf("(");
             i >= 0 && (i < apparaiment.length || i <= new String(apparaiment).lastIndexOf(")")); i++) {
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
            size++;
        }
        return size <= 100 && open == close && open >= 24;
    }

    /**
     * Fonction qui effectue la récurrence de Needleman Wunsh un référentiel d'égalité différent de la m"thode
     * originelle. Voir Rapport.
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
                    .max(recurrenceNeedlemanWunsch(i + 1, j) + DEL, recurrenceNeedlemanWunsch(i, j - 1) + INS);
        }
        return tableChoix[i_index][j_index];
    }

    /**
     * Permet de savoir si deux positions dans la chaine est apparaillable par bloque de 3 nucléotides
     *
     * @param i
     *         Position i dans la chaine
     * @param j
     *         Position j dans la chaine
     *
     * @return Vrai eou faux
     */
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


    /**
     * Permet de logger la table des choix si nécessaire
     */
    private void logTableChoix() {
        for (int i = 0; i < tableChoix.length; i++)

        {
            System.out.print("\t" + i + "\t|");
        }
        System.out.println();
        for (int j = 0; j < tableChoix[j].length; j++)

        {
            for (int i = 0; i < tableChoix.length; i++) {
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
    }
}
