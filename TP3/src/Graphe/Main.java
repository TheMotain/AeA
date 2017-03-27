package Graphe;

import Utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

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
        String[] Dico = Dicos.dico4;
        Graphe g = new Graphe(Dico);
        lettreQuiSaute(g);
        //afficher(g);
        //visit(g);
        afficherCheminPlusCourt(g, g.getIndex("lion"), g.getIndex("peur"));
    }

    /**
     * Ajoute une arrète au graphe
     *
     * @param g Graphe
     * @param s Sommet 1
     * @param d Sommet 2
     */
    public static void ajouterArete(Graphe g, int s, int d) {
        // if (!g.isSuccesseur(s, d))
        //     g.setSuccesseur(s, new Successeur(d, g.getSuccesseur(s)));//if (!g.isSuccesseur(d, s))
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

    public static void supLettre(Graphe g, int sup) {
        for (String str1 : g.getMot()) {
            for (String str2 : g.getMot()) {
                if (str1.equals(str2)) {
                    continue;
                }
                if (StringUtils.supLettre(str1, str2, sup)) {
                    ajouterArete(g, g.getIndex(str1), g.getIndex(str2));
                }
            }
        }
    }

    public static void difLettre(Graphe g, int dif) {
        for (String str1 : g.getMot()) {
            for (String str2 : g.getMot()) {
                if (str1.equals(str2)) {
                    continue;
                }
                if (StringUtils.diffLettre(str1, str2, dif)) {
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
     * !!! Voir pour afficher "i : " seulement si il existe une composante connexe !!!
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
     *
     * @param g Le graph
     */
    public static void afficher(final Graphe g) {
        System.out.println(g);
    }

    public static void chemin(Graphe g, int from, int to) {
        ArrayList<Integer> path = new ArrayList<Integer>();
        g.resetParcour();
        if (getChemin(g, path, from, to) != null) {
            for (Integer i : path) {
                System.out.print(g.getMot()[i] + " ");
            }
            System.out.println("");
        } else System.out.println("Aucun chemin trouver entre " + from + " et " + to + ".");
    }

    public static ArrayList<Integer> getChemin(Graphe g, ArrayList<Integer> path, int actuel, int to) {
        if (actuel == to) {
            path.add(actuel);
            return path;
        }
        g.parcour(actuel);
        Successeur next = g.getSuccesseur(actuel);
        while (next != null) {
            if (g.dejaVu(next.getNoeud()) == false && getChemin(g, path, next.getNoeud(), to) != null) {
                path.add(actuel);
                return path;
            }
            next = next.getNextNoeuds();
        }
        return null;
    }

    public static List<Integer> BFSIteratif(Graphe g, int from, int to) {
        g.resetParcour();
        List<Integer> aList = new ArrayList<>();
        aList.add(from);
        g.parcour(from);
        while (aList.size() > 0) {
            int s = aList.remove(0);
            System.out.println(s);
            Successeur next = g.getSuccesseur(s);
            while (next != null) {
                if (g.dejaVu(next.getNoeud()) == false) {
                    aList.add(next.getNoeud());
                    g.setPere(next.getNoeud(), s);
                    g.parcour(next.getNoeud());
                    if (next.getNoeud() == to) return aList;
                }
                next = next.getNextNoeuds();
            }
        }
        return null;
    }

    public static void afficherCheminPlusCourt(Graphe g, int from, int to) {
        if (BFSIteratif(g, from, to) != null) {
            System.out.print("Chemin : ");
            int elt = to;
            while (elt != -1) {
                System.out.print(g.getMot()[elt] + " ");
                elt = g.pere(elt);
            }
            System.out.println();
        } else {
            System.out.println("Aucun chemin, RIP !");
        }

    }

}
