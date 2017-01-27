package Tests.Utils

import Utils.StringUtils
import org.junit.Assert
import org.junit.Test

/**
 * Created by alex on 23/01/17.
 */
class StringUtilsTest {
    @Test
    void testReverse() {
        Assert.assertEquals("ytreza", StringUtils.reverse("azerty"));
    }
}
