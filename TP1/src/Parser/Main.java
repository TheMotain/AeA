package Parser;

import ADN.SequenceADN;
import IO.DotPlotWriter;
import IO.FastaReader;
import Parser.ParametrageParser.TypeRecherche;
import Parser.ParserTailleMot.ParserTailleMotNNaif;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {

	public static void main(final String[] args) throws IOException {
		final SequenceADN sequenceADN = new FastaReader(args[0]).getSequence();
		final ParametrageParser parametrage = new ParametrageParser();
		if (args.length > 2) {
			parametrage.setParam(TypeRecherche.findValues(Arrays.copyOfRange(args, 2, args.length)));
		} else {
			parametrage.setParam(TypeRecherche.DIRECT, TypeRecherche.COMPLEMENTAIRE, TypeRecherche.REVERSE,
					TypeRecherche.COMPLEMENTAIRE_REVERSE);
		}
		final ParserTailleMotNNaif parser = new ParserTailleMotNNaif(sequenceADN, parametrage);
		final Map<String, List<Integer>> result = parser.run(Integer.parseInt(args[1]));
		new DotPlotWriter(result).generateDotPlotMultiWord(
				args[0].substring(args[0].lastIndexOf("/") + 1, args[0].lastIndexOf(".")) + "-lengthOccur-" + args[1] +
						".plot");
	}


}
