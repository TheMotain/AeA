package Parser;

import ADN.SequenceADN;
import Utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Permet à partir de l'index de tous les mots de 3 caractère de réaliser un apparaiment d'un microARN<br/>
 * Created by alex on 11/03/17.
 */
public class UltimateParser {

    private final SequenceADN sequenceADN;

    public UltimateParser(final SequenceADN sequenceADN) {
        this.sequenceADN = sequenceADN;
    }

    public static void main(final String[] args) {
        final SequenceADN seq = new SequenceADN();
//        seq.setAdnSequence("ACGCGUA");
//        seq.setAdnSequence("ACGUAGAAACCCCCGUAAUAUGUGCACAUAUUACGGGGGUUUCUACGU");
//        seq.setAdnSequence("ACGUAGAAACCCCCGUAAUAUGUGACGCCCACAUAUUACGGGGGUUUCUACGU");
//        seq.setAdnSequence("ACGUAGCCAAACCCCCGUAAUAUGUGACGCCCACAUAUUACGGGGGUUUCUACGU");
        seq.setAdnSequence(
                "UGCUUCCGGCCUGUUCCCUGAGACCUCAAGUGUGAGUGUACUAUUGAUGCUUCACACCUGGGCUCUCCGGGUACCAGGACGGUUUGAGCA");
        new UltimateParser(seq).runParser();
    }

    public char[] runParser() {
        final Map<String, List<Integer>> mots = new ParserTailleMotNNaif(sequenceADN).run(3);
        final Map<Integer, String> reverseMap = calculateReverseMap(mots);

        int pos_i = 0;
        int pos_j = sequenceADN.length() - 1;

        // Initialisation du tableau de résultat brut. Les paire d'apparaiment seront réalisé plus tard
        final boolean[] apparaimentInitial = new boolean[sequenceADN.length()];
        for (int i = 0; i < apparaimentInitial.length; i++) {
            apparaimentInitial[i] = false;
        }

        while (pos_i < pos_j) {
            final String tmp = reverseMap.get(pos_i);
            final List<Integer> reverses = new ArrayList<>();
            // Recherce de tous les indexes de complémentaire
            for (final String cmpl : sequenceADN.getComplementaires(tmp)) {
                if (mots.containsKey(StringUtils.reverse(cmpl))) {
                    reverses.addAll(mots.get(StringUtils.reverse(cmpl)));
                }
            }
            boolean notcontains = true;
            // si le mot n'est pas contenu dans 4 déplacement en j c'est que le gap est en i
            // si au contraire il est contenu dans moins de 4 déplacement en j c'est que le gap est en j
            for (int k = 0; k <= 4; k++) {
                if (reverses.contains(pos_j - 2 - k)) {
                    apparaimentInitial[pos_i] |= true;
                    apparaimentInitial[pos_i + 1] |= true;
                    apparaimentInitial[pos_i + 2] |= true;
                    apparaimentInitial[pos_j - k] |= true;
                    apparaimentInitial[pos_j - 1 - k] |= true;
                    apparaimentInitial[pos_j - 2 - k] |= true;
                    pos_i++;
                    pos_j = pos_j - k - 1;
                    notcontains = false;
                    break;
                }
            }
            if (notcontains) {
                pos_i++;
            }
        }

        final char[] apparaimentFinal = new char[sequenceADN.length()];

        int i = 0;
        int j = sequenceADN.length() - 1;
        while (i <= j) {
            if (i == j) {
                apparaimentFinal[i] = '.';
                break;
            }
            if (apparaimentInitial[i] && apparaimentInitial[j]) {
                apparaimentFinal[i] = '(';
                apparaimentFinal[j] = ')';
                i++;
                j--;
            } else if (!apparaimentInitial[i]) {
                apparaimentFinal[i] = '.';
                i++;
            } else {
                apparaimentFinal[j] = '.';
                j--;
            }
        }

        System.out.println(sequenceADN.getAdnSequence());
        System.out.println(new String(apparaimentFinal));

        return null;
    }

    private Map<Integer, String> calculateReverseMap(final Map<String, List<Integer>> mots) {
        final Map<Integer, String> output = new HashMap<>();
        for (final Map.Entry<String, List<Integer>> entry : mots.entrySet()) {
            for (final Integer i : entry.getValue()) {
                output.put(i, entry.getKey());
            }
        }
        return output;
    }
}
