package Parser.ParserTailleMot;

import ADN.SequenceADN;

import javax.management.BadStringOperationException;
import java.util.*;

/**
 * Parser Naif qui retourne la liste de tous les mots de taille N dans la chaine
 * Created by alex on 30/01/17.
 */
public class ParserTailleMotNNaif {
    private final SequenceADN sequenceADN;

    public ParserTailleMotNNaif( final SequenceADN sequenceADN) {
        this.sequenceADN = sequenceADN;
    }

    public Map<String, List<Integer>>run(final int n,final boolean combine) {
        Map<String, List<Integer>> result = new HashMap<>();
        for (int i = 0; i < sequenceADN.length(); i++) {
            //Je vérifie si le mot peut passer avant de faire mes tests
            if (n + i >= sequenceADN.length()) break;
            String word = sequenceADN.getAdnSequence().substring(i, i + n);
            if(!result.containsKey(word)) result.put(word,new ArrayList<>());
            result.get(word).add(i);
        }
        if (combine){
            int i = 0;
            Set listKeys=result.keySet();  // Obtenir la liste des clés
            Iterator iterateur=listKeys.iterator();
            // Parcourir les clés et afficher les entrées de chaque clé;
            while(iterateur.hasNext())            {
                Object key= iterateur.next();
                System.out.println (key+"=>"+result.get(key));
            }
        }
        return result;
    }

}
