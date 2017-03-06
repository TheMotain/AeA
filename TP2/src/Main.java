import ADN.SequenceADN;
import IO.FastaReader;
import Utils.StringUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by nes on 27/02/17.
 */
public class Main {

	/**
	 * Creation, initialisation and démarrage du server
	 **/
	public static void main(String[] args) throws IOException {
		test100nucle(args);
	}

	public static void test100nucle(String[] args) throws IOException {
		File f = new File(args[0]);
		if (f.exists()) {
			SequenceADN SequenceADN = new FastaReader(f.getPath()).getSequence();
			SequenceADN RandomSequence = SequenceADN.getRandomSequence(100);
			System.out.println(SequenceADN.getAdnSequence());
			System.out.println(RandomSequence.getAdnSequence());
		} else {
			System.out.println("Le fichier n'existe pas !");
		}
	}

	public static boolean verifARN(String ARN, int nbNucleotidesMini) {
		if (StringUtils.countChar(ARN, '(') + StringUtils.countChar(ARN, ')') < nbNucleotidesMini) {
			System.out.println("Il n'y a pas assez de nucléotides dans l'ARN ! mini = " + nbNucleotidesMini);
		}

		return true;
	}

    /*public static int sizeMaxNucleotique(String ARN,char debut,char fin){
        int max = 0;
        for (int i = 0; i < ARN.length(); i++) {
            int compteur = 0;
            while(i + 1)
        }
        return max;
    }*/

}