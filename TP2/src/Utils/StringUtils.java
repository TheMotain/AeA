package Utils;

/**
 * Created by alex on 23/01/17.
 */
public class StringUtils {

    public static String reverse (final String word){
        return new StringBuilder(word).reverse().toString();
    }
}
