package Parser;

import ADN.SequenceADN;
import IO.FastaReader;

import java.io.IOException;

public class Main {

    public static void main(final String[] args) throws IOException {
        final SequenceADN sequenceADN = new FastaReader(args[0]).getSequence();
        final ParametrageParser parametrage = new ParametrageParser();
        final Parser parser = null;
    }

	/*public static void main(final String[] args) throws IOException, BadStringOperationException {
		//final SequenceADN sequenceADN = new FastaReader(args[0]).getSequence()
		final SequenceADN sequenceADN = new FastaReader("fasta/ARNmessager-1.fasta").getSequence();
		//final SequenceADN sequenceADN = new FastaReader("fasta/hsa-miR-20a_MI_ARN.fasta").getSequence();
		final ParametrageParser parametrage = new ParametrageParser();
		//parametrage.setParam(TypeRecherche.DIRECT,TypeRecherche.REVERSE,TypeRecherche.COMPLEMENTAIRE,TypeRecherche
		// .COMPLEMENTAIRE_REVERSE);
		parametrage.setWordToParse("GU");
		parametrage.setParam(TypeRecherche.DIRECT);

		//final ParserNaif parser = new ParserNaif(parametrage,sequenceADN); //Occ trouvé : 218 : OK
		//final AbstractParser parser = new ParserShiftOr(parametrage, sequenceADN); //Occ trouvé : 218 : Ok
		//final Parser parser = new ParserKarpRabin(parametrage,sequenceADN); //Occ trouvé : 218 : OK
        final  ParserTailleMotNNaif parser = new ParserTailleMotNNaif(sequenceADN);
        // N = Taille des mots à chercher
        final Map<String, List<Integer>> result = parser.run(2);
		System.out.println("Nombre d'occurence trouver : " + result.size());
        Set cles = result.keySet();
        for (Object cle : cles) {
            System.out.println("Clef : " + cle + " nbOccu : " + result.get(cle).size());
        }
        //new DotPlotWriter(result, result).generateDotPlot("output.plot");
	}*/

}
