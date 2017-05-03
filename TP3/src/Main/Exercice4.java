package Main;

import Graphe.Dicos;
import Graphe.Graphe;
import Utils.Exercices;

/**
 * Main pour l'exercice 4
 * Created by alex on 02/05/17.
 */
public class Exercice4 {
    /**
     * Méthode main
     *
     * @param args Arguments éventuels
     */
    public static void main(String[] args) {
        if(args.length != 2){
            throw new IllegalArgumentException("2 paramètres attendus");
        }
        String[] dico = Dicos.dico4;
        Graphe g = new Graphe(dico);
        Exercices.lettreQuiSaute(g);
//        Exercices.afficherCheminPlusCourt(g,"lion","peur");
        Exercices.afficherCheminPlusCourt(g, args[0],args[1]);
    }
}
