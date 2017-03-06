package Utils;

import ADN.SequenceADN;
import IO.FastaReader;
import Parser.SmithWatermanParser;

import java.io.IOException;

/**
 * Created by alex on 06/03/17.
 */
public class MainHybridation {

	public static void main(String[] arg) throws IOException {
		SequenceADN arnMessage = new FastaReader(arg[0]).getSequence();
		SequenceADN microARN = new FastaReader(arg[1]).getSequence();
		SmithWatermanParser parser = new SmithWatermanParser(arnMessage, microARN);
		parser.runParser();
	}
}
