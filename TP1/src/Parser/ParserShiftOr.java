package Parser;

import ADN.SequenceADN;
import Utils.StringUtils;

import javax.management.BadStringOperationException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Parser selon la méthode Shift - Or
 *
 * Created by alex on 23/01/17.
 */
public class ParserShiftOr {
    private static final char[] alphabet = {'A','C','G','T','U'};

    private ParametrageParser parametrage;

    private SequenceADN sequenceADN;

    public ParserShiftOr(ParametrageParser parametrage, SequenceADN sequenceADN) {
        this.parametrage = parametrage;
        this.sequenceADN = sequenceADN;
    }

    /**
     * Recherche toutes les occurence du mot dans la séquence d'adn en fonction du paramétrage
     *
     * @return la liste de tous les indexes sans doublons.
     * @throws BadStringOperationException Est retourné si la séquence d'ADN contient des caractère ne se trouvant pas dans l'alphabet
     */
    public List<Integer> runParser() throws BadStringOperationException {
        Set<Integer> result = new HashSet<>();
        if(parametrage.isDirect())
            result.addAll(run(parametrage.getWordToParse()));
        if(parametrage.isReverse())
            result.addAll(run(StringUtils.reverse(parametrage.getWordToParse())));
        if(parametrage.isComplementaire())
            result.addAll(run(sequenceADN.getComplementaire(parametrage.getWordToParse())));
        if(parametrage.isComplementaire_reverse()){
            result.addAll(run(sequenceADN.getComplementaire(StringUtils.reverse(parametrage.getWordToParse()))));
        }
        return new ArrayList<>(result);
    }

    /**
     * Exécute la recherche du mot passé en paramètre dans la séquence d'ADN chargé.
     *
     * @param word Le mot à chercher
     * @return La liste des positions où se trouve le mot
     * @throws BadStringOperationException Est retourné si la séquence d'ADN contient des caractère ne se trouvant pas dans l'alphabet
     */
    private List<Integer> run(String word) throws BadStringOperationException {
        final List<Integer> result = new ArrayList<>();
        final int[][] matriceB = generateMatriceB(word);
        final int[][] matriceOccur = new int[sequenceADN.length()][word.length()];

        final int[] currentCharacter = new int[word.length()];
        for(int i = 0; i < word.length(); i++) currentCharacter[i] = 0;

        if(sequenceADN.charAt(0) == word.charAt(0)) currentCharacter[0] += 0b1;
        for(int i = 0; i < matriceOccur[0].length; i++){
            matriceOccur[0][i] = currentCharacter[i];
        }

        for(int i = 1; i < sequenceADN.length(); i++){
            for(int j = currentCharacter.length - 1; j > 0; j--){ //shift
                currentCharacter[j] = currentCharacter[j-1];
            }
            currentCharacter[0] = 0b1;

            // récupération de la matrice B
            int z;
            for(z = 0; z < alphabet.length; z++ ){
                if(alphabet[z] == sequenceADN.charAt(i))
                    break;
            }
            if(z >= alphabet.length) throw new BadStringOperationException("Unknow caractere");

            for(int j = 0; j < currentCharacter.length; j++){
                currentCharacter[j] &= matriceB[z][j];
            }
            for(int j = 0; j < matriceOccur[i].length; j++){
                matriceOccur[i][j] = currentCharacter[j];
            }
        }

        initial : for(int i = 0; i < sequenceADN.length() - word.length(); i++){
            if(matriceOccur[i][0] == 0b1){
                int y = i + 1;
                int z = 1;
                while(z < word.length()){
                    if(matriceOccur[y][z] != 0b1){
                        continue initial;
                    }
                    z++;
                    y++;
                }
                result.add(i);
            }
        }

        return result;
    }

    private int[][] generateMatriceB(final String word) {
        final int matriceB [][] = new int[alphabet.length][word.length()];
        for(int i = 0; i < alphabet.length; i++){
            for(int j = 0; j < word.length(); j++){
                matriceB[i][j] = word.charAt(j) == alphabet[i] ? 0b1 : 0;
            }
        }
        return matriceB;
    }
}
