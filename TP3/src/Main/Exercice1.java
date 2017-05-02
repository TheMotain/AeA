package Main;

import Graphe.Dicos;
import Graphe.Graphe;

/**
 * Main pour l'exercice 1
 * Created by alex on 02/05/17.
 */
public class Exercice1 {
    /**
     * Méthode main
     *
     * @param args Arguments éventuels
     */
    public static void main(String[] args) {
        String[] dico = Dicos.dico3court;
        Graphe g = new Graphe(dico);
        Exercices.lettreQuiSaute(g);
        Exercices.afficher(g);
    }
}
