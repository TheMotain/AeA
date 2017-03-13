package Utils;

/**
 * Méthodes utilitaires pour la gestion des Strings<br/>
 * <p>
 * Created by dalencourt on 13/03/17.
 */
public class StringUtils {

    static boolean diffUneLettre(String a, String b) {
        // a et b supposees de meme longueur
        int i = 0;
        while (i < a.length() && a.charAt(i) == b.charAt(i))
            ++i;
        if (i == a.length()) return false;
        ++i;
        while (i < a.length() && a.charAt(i) == b.charAt(i))
            ++i;
        return i == a.length();
    }
}
