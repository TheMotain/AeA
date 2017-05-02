package Main;

import Graphe.Dicos;
import Graphe.Graphe;
import Utils.DicoParser;

import java.io.IOException;

/**
 * Main pour l'exercice 5
 * Created by alex on 02/05/17.
 */
public class Exercice5 {
    /**
     * Méthode main
     *
     * @param args Arguments éventuels
     */
    public static void main(String[] args) throws IOException {
        if(args.length != 5){
            throw new IllegalArgumentException("5 paramètres attendu");
        }
        String[] dico = DicoParser.parseFile(args[0]);
        Graphe g = new Graphe(dico);
//        Exercices.supDiffLettre(g,1,1);
        Exercices.supDiffLettre(g,Integer.valueOf(args[1]),Integer.valueOf(args[2]));
        Exercices.afficherCheminPlusCourt(g,args[3],args[4]);
    }
}
