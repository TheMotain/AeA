package Tests.ADN

import ADN.SequenceADN
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Created by alex on 23/01/17.
 */
class SequenceADNTest {
    private SequenceADN sequenceADN;

    @Before
    void setUp() {
        sequenceADN = new SequenceADN();
    }

    @Test
    void testAppend() {
        sequenceADN.setAdnSequence("azerty");
        sequenceADN.append("ytreza");
        Assert.assertEquals("azertyytreza",sequenceADN.getAdnSequence());
    }

    @Test
    void testGetComplementaireEmptyString(){
        Assert.assertEquals("", sequenceADN.getComplementaire(""))
    }

    @Test
    void testGetComplementaire(){
        Assert.assertEquals("CTATGT", sequenceADN.getComplementaire("GATACA"));
    }
}
