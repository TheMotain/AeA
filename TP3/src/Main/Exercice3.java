package Main;

import Graphe.Dicos;
import Graphe.Graphe;

/**
 * Main pour l'exercice 3
 * Created by alex on 02/05/17.
 */
public class Exercice3 {
    /**
     * Méthode main
     *
     * @param args Arguments éventuels
     */
    public static void main(String[] args) {
        if(args.length != 2){
            throw new IllegalArgumentException("2 paramètres attendu");
        }
        String[] dico = Dicos.dico4;
        Graphe g = new Graphe(dico);
        Exercices.lettreQuiSaute(g);
        Exercices.chemin(g, args[0], args[1]);
//        Exercices.chemin(g,"lion","peur");
    }
}
