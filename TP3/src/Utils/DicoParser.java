package Utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Parse un fichier dictionnaire
 */
public class DicoParser {

    /**
     * parse un fichier et retourne un tableau de mots associé
     * @param path fichier à parser
     */
    public static String[] parseFile(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        List<String> words = new ArrayList<String>();
        String word;
        while ((word = reader.readLine()) != null){
            words.add(word);
        }
        reader.close();
        return words.toArray(new String[words.size()]);
    }
}
