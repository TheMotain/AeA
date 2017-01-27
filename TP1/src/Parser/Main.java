package Parser;

import java.io.IOException;
import java.util.List;

import ADN.SequenceADN;
<<<<<<< HEAD
=======
import IO.DotPlotWriter;
import IO.FastaReader;
>>>>>>> bc6bf5615be102745e0ffe280fb6159655231e5b
import Parser.ParametrageParser.TypeRecherche;
import Reader.FastaReader;

import javax.management.BadStringOperationException;

public class Main {
	public static void main(final String[] args) throws IOException, BadStringOperationException {
		final SequenceADN sequenceADN = new FastaReader(args[0]).getSequence();
		final ParametrageParser parametrage = new ParametrageParser();
<<<<<<< HEAD
		parametrage.addWordToParse("GATACA");
		parametrage.setParam(TypeRecherche.DIRECT,TypeRecherche.REVERSE,TypeRecherche.COMPLEMENTAIRE,TypeRecherche.COMPLEMENTAIRE_REVERSE);
		Parser parser
	
=======
		parametrage.setWordToParse("GU");
		parametrage.setParam(TypeRecherche.DIRECT);
		final ParserShiftOr parser = new ParserShiftOr(parametrage,sequenceADN);
		final List<Integer> result = parser.runParser();
		new DotPlotWriter(result,result).generateDotPlot("output.plot");
>>>>>>> bc6bf5615be102745e0ffe280fb6159655231e5b
	}
}
