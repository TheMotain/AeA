package Main;

import ADN.SequenceADN;
import IO.FastaReader;
import Parser.SmithWatermanAlignerment;

import javax.management.BadStringOperationException;
import java.io.IOException;

/**
 * Main class permettant la réaliation de l'hybridation entre une séquence de microARN et un ARN messagé
 * Created by alex on 06/03/17.
 */
public class MainHybridation {

	public static void main(final String[] arg) throws IOException, BadStringOperationException {
		final SequenceADN arnMessage = new FastaReader(arg[0]).getSequence();
		final SequenceADN microARN = new FastaReader(arg[1]).getSequence();
		final SmithWatermanAlignerment parser = new SmithWatermanAlignerment(arnMessage, microARN);
		final String[] hybritadion = parser.runAlignement();
		System.out.println(hybritadion[0]);
		for (int i = 0; i < hybritadion[0].length() && i < hybritadion[1].length(); i++) {
			if (arnMessage.getComplementaires(hybritadion[0].charAt(i) + "").contains(hybritadion[1].charAt(i) + "")) {
				System.out.print("|");
			} else {
				System.out.print(" ");
			}
		}
		System.out.println();
		System.out.println(hybritadion[1]);

		// Calcul du pourcentage d'hybridation selon la formule suivante
		// (nombre match) * 100 / taille
	}
}
