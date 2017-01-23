package Parser;

import java.io.IOException;

import ADN.SequenceADN;
import IO.FastaReader;
import Parser.ParametrageParser.TypeRecherche;

public class Main {
	public static void main(final String[] args) throws IOException {
		final SequenceADN sequenceADN = new FastaReader(args[0]).getSequence();
		final ParametrageParser parametrage = new ParametrageParser();
		parametrage.addWordToParse("GATACA");
		parametrage.setParam(TypeRecherche.DIRECT,TypeRecherche.REVERSE,TypeRecherche.COMPLEMENTAIRE,TypeRecherche.COMPLEMENTAIRE_REVERSE);
	}
}
