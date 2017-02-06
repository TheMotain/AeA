package Parser;

import ADN.SequenceADN;
import IO.DotPlotWriter;
import IO.FastaReader;
import Parser.ParserTailleMot.ParserTailleMotNNaif;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(final String[] args) throws IOException {
        final SequenceADN sequenceADN = new FastaReader(args[0]).getSequence();
        final ParserTailleMotNNaif parser = new ParserTailleMotNNaif(sequenceADN);
        Map<String,List<Integer>> result = parser.run(Integer.parseInt(args[1]));
        new DotPlotWriter(result).generateDotPlotMultiWord(args[0].substring(args[0].lastIndexOf("/")+1,args[0].lastIndexOf(".")) + "-lengthOccur-" + args[1]+".plot");
    }


}
