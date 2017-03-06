import ADN.SequenceADN;
import IO.FastaReader;

import java.io.File;
import java.io.IOException;

/**
 * Created by nes on 27/02/17.
 */
public class MainApparaimentPreMicro {

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

}