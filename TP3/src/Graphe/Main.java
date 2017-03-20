package Graphe;

import Utils.StringUtils;

/**
 * Classe principale du programme<br/>
 * Created by dalencourt on 13/03/17.
 */
public class Main {


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
        visit(g);
    }

    /**
     * Ajoute une arrète au graphe
     *
     * @param g Graphe
     * @param s Sommet 1
     * @param d Sommet 2
     */
    public static void ajouterArete(Graphe g, int s, int d) {
        if (!g.isSuccesseur(s,d))
            g.setSuccesseur(s, new Successeur(d, g.getSuccesseur(s)));
        if(!g.isSuccesseur(d,s))
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
     * Affiche la composante connexe contenant le mot x.
     *
     * @param g Le graph
     * @param x Le mot à visiter
     */
    public static void DFS(Graphe g, int x) {
        if (!g.dejaVu(x)) {
            System.out.print(g.parcour(x) + " ");
            Successeur s = g.getSuccesseur(x);
            while (s != null) {
                DFS(g, s.getNoeud());
                s = s.getNextNoeuds();
            }
        }
    }

    /**
     * Visite l'ensemble du graph et affiche toute les composantes
     * connexes.
     *
     * @param g Le graph
     */
    public static void visit(Graphe g) {
        g.resetParcour();
        int i = 1;
        for (String str : g.getMot()) {
            System.out.print(i + " : ");
            DFS(g, g.getIndex(str));
            i++;
            System.out.println();
        }
    }

    /**
     * Affiche le graph g
     * @param g Le graph
     */
    public static void afficher(final Graphe g) {
        System.out.println(g);
    }


}
