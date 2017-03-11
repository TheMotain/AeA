package Main;

import ADN.SequenceADN;
import IO.FastaReader;
import Parser.NeedlemanWunschAlternativParser;

import java.io.IOException;

/**
 * Classe main permettant de réaliser un apparaiment de type prémicro ARM
 * Created by nes on 27/02/17.
 */
public class MainApparaimentMicroARN {

    /**
     * Creation, initialisation and démarrage du server
     **/
    public static void main(final String[] args) throws IOException {
        final SequenceADN microARN = new FastaReader(args[0]).getSequence();
        final NeedlemanWunschAlternativParser apparailler = new NeedlemanWunschAlternativParser(microARN);
        System.out.println(microARN.getAdnSequence());
        System.out.println(new String(apparailler.runParser()));
    }
}