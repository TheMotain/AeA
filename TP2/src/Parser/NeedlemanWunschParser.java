package Parser;

import ADN.SequenceADN;

/**
 * Parser selon la methode needleman wunsch
 *
 * Created by dalencourt on 13/02/17.
 */
public class NeedlemanWunschParser extends AbstractParser {
    public NeedlemanWunschParser(final SequenceADN sequenceADN) {
        super(sequenceADN);
    }

    @Override
    public boolean[] runParser() {
        return new boolean[0];
    }
}
