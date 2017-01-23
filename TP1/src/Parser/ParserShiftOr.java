package Parser;

import ADN.SequenceADN;
import Utils.StringUtils;

import javax.management.BadStringOperationException;

/**
 * Created by alex on 23/01/17.
 */
public class ParserShiftOr {
    private static final char[] alphabet = {'A','C','G','T'};

    private ParametrageParser parametrage;

    private SequenceADN sequenceADN;

    public ParserShiftOr(ParametrageParser parametrage, SequenceADN sequenceADN) {
        this.parametrage = parametrage;
        this.sequenceADN = sequenceADN;
    }

    public void runParser() throws BadStringOperationException {
        if(parametrage.isDirect())
            run(parametrage.getWordToParse());
        if(parametrage.isReverse())
            run(StringUtils.reverse(parametrage.getWordToParse()));
        if(parametrage.isComplementaire())
            run(sequenceADN.getComplementaire(parametrage.getWordToParse()));
        if(parametrage.isComplementaire_reverse()){
            run(sequenceADN.getComplementaire(StringUtils.reverse(parametrage.getWordToParse())));
        }
    }

    private void run(String word) throws BadStringOperationException {
        final int[][] matriceB = generateMatriceB(word);
        final int[][] matriceOccur = new int[sequenceADN.getAdnSequence().length()][word.length()];

        final int[] currentCharacter = new int[word.length()];
        for(int i = 0; i < word.length(); i++) currentCharacter[i] = 0;

        if(sequenceADN.getAdnSequence().charAt(0) == word.charAt(0)) currentCharacter[0] += 0b1;
        matriceOccur[0] = currentCharacter;

        for(int i = 1; i < sequenceADN.getAdnSequence().length(); i++){
            for(int j = currentCharacter.length - 1; j > 0; j++){ //shift
                currentCharacter[j] = currentCharacter[j-1];
            }
            currentCharacter[0] = 0b1;
            for(int j = 0; j < currentCharacter.length; j++){
                int z;
                for(z = 0; z < alphabet.length; z++ ){
                    if(alphabet[z] == sequenceADN.getAdnSequence().charAt(i))
                        break;
                }
                if(z > alphabet.length) throw new BadStringOperationException("Unknow caractere");
                currentCharacter[j] &= matriceB[j][j];

                matriceOccur[i] = currentCharacter;
            }
        }
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
