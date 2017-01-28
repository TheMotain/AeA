package Parser;

import java.io.*;
import java.util.List;

import ADN.SequenceADN;
import IO.DotPlotWriter;
import IO.FastaReader;
import Parser.ParametrageParser.TypeRecherche;

import javax.management.BadStringOperationException;

public class Main {
	public static void main(final String[] args) throws IOException, BadStringOperationException {
		//final SequenceADN sequenceADN = new FastaReader(args[0]).getSequence()
        final SequenceADN sequenceADN = new FastaReader("TP1/fasta/ARNmessager-1.fasta").getSequence();
        final ParametrageParser parametrage = new ParametrageParser();
		//parametrage.setParam(TypeRecherche.DIRECT,TypeRecherche.REVERSE,TypeRecherche.COMPLEMENTAIRE,TypeRecherche.COMPLEMENTAIRE_REVERSE);
		parametrage.setWordToParse("GU");
		parametrage.setParam(TypeRecherche.DIRECT);

		//final ParserNaif parser = new ParserNaif(parametrage,sequenceADN); //Occ trouvé : 218 : OK
		//final ParserShiftOr parser = new ParserShiftOr(parametrage,sequenceADN); //Occ trouvé : 218 : Ok
		final ParserKarpRabin parser = new ParserKarpRabin(parametrage,sequenceADN); //Occ trouvé : 218 : OK

		final List<Integer> result = parser.runParser();
		System.out.println("Nombre d'occurence trouver : " + result.size());
		new DotPlotWriter(result,result).generateDotPlot("output.plot");
	}

}
