package Parser;

import ADN.SequenceADN;

import javax.management.BadStringOperationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

/**
 * Parser selon la methode needleman wunsch
 * <p>
 * Created by dalencourt on 13/02/17.
 */
public class NeedlemanWunschAlignement {
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
     *         ARN messagé
     * @param microARN
     *         Pré-micro ARN
     */
    public NeedlemanWunschAlignement(final SequenceADN arnMessage, final SequenceADN microARN) {
        this.arnMessage = arnMessage;
        this.microARN = microARN;
    }

    /**
     * Permet de lancer la séquence d'alignement qui trouvera le meilleur
     *
     * @return Retourne le meilleurs alignement possible entre l'arnMessage et le microARN ainsi que le score final
     *
     * @throws BadStringOperationException
     *         Si un caractère non attendu est rencontré
     */
    public Object[] runAlignement() throws BadStringOperationException {
        final List<Integer> startPoints = new ArrayList<>();
        final ParserShiftOr parserShiftOr = new ParserShiftOr(arnMessage);
        // Recherche tous les points d'entrées où un alignement est possible
        for (final String str : microARN.getComplementaires(microARN.substring(0, 7))) {
            startPoints.addAll(parserShiftOr.run(str));
        }
        final String subMicroARN = microARN.substring(7);
        String subArnMessaFinal = null;
        int finalIndex = -1;
        int scoreMax = -100;
        Integer[][] tableCoutsFinal = null;
        List<InDelSubMatch> alignementFinal = null;
        // évaluation des différents apparaiments possibles
        for (final Integer idx : startPoints) {
            // découpage de la chaine, On autorisera un maximum de 5 gap pour l'arn message
            int endIdx = idx + 7 + (subMicroARN.length() + 5);
            if (endIdx > arnMessage.length()) {
                endIdx = arnMessage.length();
            }
            final String subArnMessa =
                    arnMessage.substring(idx + 7, endIdx);
            final Integer[][] tableCouts = new Integer[subArnMessa.length() + 1][subMicroARN.length() + 1];
            // exécution de la récurence de Smith Watermen
            recurrenceNeedlemanWunsch(tableCouts, subArnMessa, subMicroARN, subArnMessa.length(),
                    subMicroARN.length());
            final List<InDelSubMatch> alignement = remontee(tableCouts);
            // Calcul du score selon la formule:
            // nb match * 2 + nb substitution + nb InDel * -1
            final Integer score = Collections.frequency(alignement, InDelSubMatch.MATCH) * 2 +
                    Collections.frequency(alignement, InDelSubMatch.SUB) +
                    Collections.frequency(alignement, InDelSubMatch.INS) * -1 +
                    Collections.frequency(alignement, InDelSubMatch.DEL) * -1 + 14;
            if (score > scoreMax) {
                tableCoutsFinal = tableCouts;
                subArnMessaFinal = subArnMessa;
                scoreMax = score;
                finalIndex = idx;
                alignementFinal = alignement;
            }
        }
        if (subArnMessaFinal != null) {
            final String[] align = reconstruction(alignementFinal, subArnMessaFinal, subMicroARN, scoreMax);
            align[0] = arnMessage.substring(finalIndex, finalIndex + 7) + align[0];
            align[1] = "|||||||" + align[1];
            align[2] = microARN.substring(0, 7) + align[2];
            final Object[] output = new Object[]{align[0], align[1], align[2], scoreMax};
            return output;
        } else {
            return new Object[]{"", "", "", 0};
        }
    }

    /**
     * Effectue la reconstruction des deux chaines alignées
     *
     * @param alignement
     *         Alignement à réaliser
     * @param subArnMessaFinal
     *         Arn messagé d'origine
     * @param subMicroARN
     *         Micro ARN d'origine
     *
     * @return L'alignement réalisé
     */
    private String[] reconstruction(final List<InDelSubMatch> alignement, final String subArnMessaFinal,
                                    final String subMicroARN, final int score) {
        String alignSubArnMessa = "";
        String match = "";
        String alignSubMicroARN = "";
        int i = 0;
        int j = 0;

        final ListIterator<InDelSubMatch> it = alignement.listIterator();
        while (it.hasNext()) {
            switch (it.next()) {
                case INS:
                    alignSubArnMessa += '-';
                    alignSubMicroARN += subMicroARN.charAt(j);
                    match += '-';
                    j++;
                    break;
                case DEL:
                    alignSubArnMessa += subArnMessaFinal.charAt(i);
                    alignSubMicroARN += '-';
                    i++;
                    match += '-';
                    break;
                case MATCH:
                    alignSubArnMessa += subArnMessaFinal.charAt(i);
                    alignSubMicroARN += subMicroARN.charAt(j);
                    i++;
                    j++;
                    match += '|';
                    break;
                case SUB:
                default:
                    alignSubArnMessa += subArnMessaFinal.charAt(i);
                    alignSubMicroARN += subMicroARN.charAt(j);
                    i++;
                    j++;
                    match += 'X';
                    break;
            }
        }
        if (i < subMicroARN.length()) {
            alignSubMicroARN += subMicroARN.substring(i);
            for (; i < subMicroARN.length(); i++) {
                alignSubArnMessa += '-';
            }
        }
        return new String[]{alignSubArnMessa, match, alignSubMicroARN};
    }

    /**
     * Effectue la remontée du tableau d'alignement
     *
     * @param tableCoutsFinal
     *         Tableau des couts
     *
     * @return Liste des actions à effectuer pour réaliser l'alignement
     */
    private List<InDelSubMatch> remontee(final Integer[][] tableCoutsFinal) {
        final List<InDelSubMatch> output = new ArrayList<>();
        int i = tableCoutsFinal.length - 1;
        int j = tableCoutsFinal[0].length - 1;
        while (i != 0 || j != 0) {
            if (i == 0) {
                output.add(0, InDelSubMatch.DEL);
                j--;
            } else if (j == 0) {
                output.add(0, InDelSubMatch.INS);
                i--;
            } else if (tableCoutsFinal[i - 1][j - 1] >= tableCoutsFinal[i - 1][j] &&
                    tableCoutsFinal[i - 1][j - 1] >= tableCoutsFinal[i][j - 1]) {
                if (tableCoutsFinal[i][j] == tableCoutsFinal[i - 1][j - 1] + MATCH) {
                    output.add(0, InDelSubMatch.MATCH);
                    i--;
                    j--;
                } else {
                    output.add(0, InDelSubMatch.SUB);
                    i--;
                    j--;
                }
            } else if (tableCoutsFinal[i - 1][j] >= tableCoutsFinal[i][j - 1]) {
                output.add(0, InDelSubMatch.DEL);
                i--;
            } else {
                output.add(0, InDelSubMatch.INS);
                j--;
            }
        }
        return output;
    }

    /**
     * Fonction qui effectue la récurrence selon la méthode Needleman et Wunch avec comme référenciel d'égalité la
     * complémentarité de deux nucléothides
     *
     * @param tableCouts
     *         table des coûts
     * @param arnMessage
     *         arn messagé à aligner
     * @param microARN
     *         micro arn à aligner
     * @param i
     *         Position de parcours i
     * @param j
     *         Position de parcours j
     */
    private Integer recurrenceNeedlemanWunsch(final Integer[][] tableCouts, final String arnMessage,
                                              final String microARN,
                                              final int i, final int j) {
        if (null == tableCouts[i][j]) {
            if (i == 0 && j == 0) {
                tableCouts[i][j] = 0;
            } else if (i == 0) {
                tableCouts[i][j] = recurrenceNeedlemanWunsch(tableCouts, arnMessage, microARN, i, j - 1) + INS;
            } else if (j == 0) {
                tableCouts[i][j] = recurrenceNeedlemanWunsch(tableCouts, arnMessage, microARN, i - 1, j) + DEL;
            } else if (this.arnMessage.getComplementaires("" + arnMessage.charAt(i - 1))
                                      .contains("" + microARN.charAt(j - 1))) {
                tableCouts[i][j] =
                        Math.max(recurrenceNeedlemanWunsch(tableCouts, arnMessage, microARN, i - 1, j - 1) + MATCH,
                                Math.max(recurrenceNeedlemanWunsch(tableCouts, arnMessage, microARN, i - 1, j) + DEL,
                                        recurrenceNeedlemanWunsch(tableCouts, arnMessage, microARN, i, j - 1) + INS));
            } else {
                tableCouts[i][j] =
                        Math.max(recurrenceNeedlemanWunsch(tableCouts, arnMessage, microARN, i - 1, j - 1) + SUB,
                                Math.max(recurrenceNeedlemanWunsch(tableCouts, arnMessage, microARN, i - 1, j) + DEL,
                                        recurrenceNeedlemanWunsch(tableCouts, arnMessage, microARN, i, j - 1) + INS));
            }
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
}
