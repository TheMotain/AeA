package Utils;

/**
 * Created by alex on 23/01/17.
 */
public class StringUtils {

    public static String reverse (final String word){
        return new StringBuilder(word).reverse().toString();
    }

    public static int countChar(final String chaine,final char aChar){
        int count = 0;
        for (int i = 0; i < chaine.length(); i++) {
            if (chaine.charAt(i) == aChar) count++;
        }
        return  count;
    }
}