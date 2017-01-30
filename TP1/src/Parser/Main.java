package Parser;

import ADN.SequenceADN;
import IO.DotPlotWriter;
import IO.FastaReader;
import Parser.ParametrageParser.TypeRecherche;

import javax.management.BadStringOperationException;
import java.io.IOException;
import java.util.List;

public class Main {
	public static void main(final String[] args) throws IOException, BadStringOperationException {
		//final SequenceADN sequenceADN = new FastaReader(args[0]).getSequence()
		final SequenceADN sequenceADN = new FastaReader("fasta/hsa-miR-20a_MI_ARN.fasta").getSequence();
		final ParametrageParser parametrage = new ParametrageParser();
		//parametrage.setParam(TypeRecherche.DIRECT,TypeRecherche.REVERSE,TypeRecherche.COMPLEMENTAIRE,TypeRecherche
		// .COMPLEMENTAIRE_REVERSE);
		parametrage.setWordToParse("GU");
		parametrage.setParam(TypeRecherche.DIRECT);

		//final ParserNaif parser = new ParserNaif(parametrage,sequenceADN); //Occ trouvé : 218 : OK
		final AbstractParser parser = new ParserShiftOr(parametrage, sequenceADN); //Occ trouvé : 218 : Ok
		//final Parser parser = new ParserKarpRabin(parametrage,sequenceADN); //Occ trouvé : 218 : OK

		final List<Integer> result = parser.runParser();
		System.out.println("Nombre d'occurence trouver : " + result.size());
		new DotPlotWriter(result, result).generateDotPlot("output.plot");
	}

}
