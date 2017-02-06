package Parser.ParserTailleMot;

import ADN.SequenceADN;

import javax.management.BadStringOperationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Parser Naif qui retourne la liste de tous les mots de taille N dans la chaine
 * Created by alex on 30/01/17.
 */
public class ParserTailleMotNNaif {
    private final SequenceADN sequenceADN;

    public ParserTailleMotNNaif( final SequenceADN sequenceADN) {
        this.sequenceADN = sequenceADN;
    }

    public Map<String, List<Integer>>run(final int n) {
        Map<String, List<Integer>> result = new HashMap<>();
        for (int i = 0; i < sequenceADN.length(); i++) {
            //Je vérifie si le mot peut passer avant de faire mes tests
            if (n + i >= sequenceADN.length()) break;
            String word = sequenceADN.getAdnSequence().substring(i, i + n);
            if(!result.containsKey(word)) result.put(word,new ArrayList<>());
            result.get(word).add(i);
        }
        return result;
    }

}
