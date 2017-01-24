package Parser;

import java.io.IOException;
import java.util.List;

import ADN.SequenceADN;
import IO.DotPlotWriter;
import IO.FastaReader;
import Parser.ParametrageParser.TypeRecherche;

import javax.management.BadStringOperationException;

public class Main {
	public static void main(final String[] args) throws IOException, BadStringOperationException {
		final SequenceADN sequenceADN = new FastaReader(args[0]).getSequence();
		final ParametrageParser parametrage = new ParametrageParser();
		parametrage.setWordToParse("GU");
		parametrage.setParam(TypeRecherche.DIRECT);
		final ParserShiftOr parser = new ParserShiftOr(parametrage,sequenceADN);
		final List<Integer> result = parser.runParser();
		new DotPlotWriter(result,result).generateDotPlot("output.plot");
	}
}
