package Main;

import ADN.SequenceADN;
import IO.FastaReader;
import Parser.NeedlemanWunschAlignement;
import Utils.StringUtils;

import javax.management.BadStringOperationException;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Main class permettant la réaliation de l'hybridation entre une séquence de microARN et un ARN messagé
 * Created by alex on 06/03/17.
 */
public class MainHybridation {

    public static void main(final String[] arg) throws IOException, BadStringOperationException {
        final SequenceADN arnMessage = new FastaReader(arg[0]).getSequence();
        final SequenceADN microARN = new FastaReader(arg[1]).getSequence();
        final NeedlemanWunschAlignement parser = new NeedlemanWunschAlignement(arnMessage, microARN);
        final Object[] hybritadion = parser.runAlignement();
        for (int i = 0; i < hybritadion.length - 1; i++) {
            System.out.println(hybritadion[i]);
        }
        final BigDecimal pourcentageComplexe =
                new BigDecimal((Integer) hybritadion[3]).divide(BigDecimal.valueOf(microARN.length() * 2), 3, 3);
        final BigDecimal pourcentageNaif =
                new BigDecimal(StringUtils.countChar((String) hybritadion[1], '|')).divide(
                        BigDecimal.valueOf(microARN.length()), 3, 3);
        System.out.println("Pourcentage d'hybridation méthode naif : " + (pourcentageNaif.doubleValue() * 100) + "%");
        System.out
                .println("Pourcentage d'hybridation méthode calculée : " + (pourcentageComplexe.doubleValue() * 100) +
                        "%");
    }
}
