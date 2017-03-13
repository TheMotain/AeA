package Graphe;

import Utils.StringUtils;

/**
 * Classe principale du programme<br/>
 * Created by dalencourt on 13/03/17.
 */
public class Main {

    /**
     * Ajoute une arrète au graphe
     *
     * @param g Graphe
     * @param s Sommet 1
     * @param d Sommet 2
     */
    public static void ajouterArete(Graphe g, int s, int d) {
        g.setSuccesseur(s, new Successeur(d, g.getSuccesseur(s)));
        g.setSuccesseur(d, new Successeur(s, g.getSuccesseur(d)));
    }

    /**
     * Ajoute toutes les arrête en fonction des mots présent dans le graphe selon la règle de la lettre qui saute
     *
     * @param g Graphe
     */
    public static void lettreQuiSaute(Graphe g) {
        for (String str1 : g.getMot()) {
            for (String str2 : g.getMot()) {
                if (str1.equals(str2)) {
                    continue;
                }
                if (StringUtils.diffUneLettre(str1, str2)) {
                    ajouterArete(g, g.getIndex(str1), g.getIndex(str2));
                }
            }
        }
    }

    /**
     * Méthode main
     *
     * @param args Arguments éventuels
     */
    public static void main(String[] args) {
        String[] dico3court = {
                "gag", "gai", "gaz", "gel", "gks", "gin",
                "gnu", "glu", "gui", "guy", "gre", "gue",
                "ace", "acm", "agi", "ait", "aie", "ail",
                "air", "and", "alu", "ami", "arc", "are",
                "art", "apr", "avr", "sur", "mat", "mur"};
        Graphe g = new Graphe(dico3court);
        lettreQuiSaute(g);
        afficher(g);
    }

    private static void afficher(final Graphe g) {
        System.out.println(g);
    }

}
