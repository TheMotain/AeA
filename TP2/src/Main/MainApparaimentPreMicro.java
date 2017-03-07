package Main;

import ADN.SequenceADN;
import IO.FastaReader;

import java.io.File;
import java.io.IOException;

/**
 * Classe main permettant de réaliser un apparaiment de type prémicro ARM
 * Created by nes on 27/02/17.
 */
public class MainApparaimentPreMicro {

	/**
	 * Creation, initialisation and démarrage du server
	 **/
	public static void main(final String[] args) throws IOException {
		test100nucle(args);
	}

	public static void test100nucle(final String[] args) throws IOException {
		final File f = new File(args[0]);
		if (f.exists()) {
			final SequenceADN SequenceADN = new FastaReader(f.getPath()).getSequence();
			final SequenceADN RandomSequence = SequenceADN.getRandomSequence(100);
			System.out.println(SequenceADN.getAdnSequence());
			System.out.println(RandomSequence.getAdnSequence());
		} else {
			System.out.println("Le fichier n'existe pas !");
		}
	}

}