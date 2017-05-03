package Utils;

import Graphe.Graphe;
import Graphe.Successeur;

import java.util.ArrayList;
import java.util.List;

/**
 * Contient les différents méthodes demandé dans les exercices <br/>
 * Created by dalencourt on 13/03/17.
 */
public class Exercices {

    /**
     * Ajoute une arrète au graphe
     *
     * @param g Graphe
     * @param s Sommet 1
     * @param d Sommet 2
     */
    public static void ajouterArete(Graphe g, int s, int d) {
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
     * Ajoute toutes les arrête en fonction des mots présent dans le graphe selon la règle sup/diff
     * @param g graphe
     * @param sup nombre de suppressions possibles
     * @param diff nombre de différences possibles
     */
    public static void supDiffLettre(Graphe g, int sup, int diff) {
        for (String str1 : g.getMot()) {
            for (String str2 : g.getMot()) {
                if (str1.equals(str2)) {
                    continue;
                }
                if (StringUtils.supDiffLettre(str1, str2, sup, diff)) {
                    ajouterArete(g, g.getIndex(str2), g.getIndex(str1));
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
     *
     * @param g Le graph
     */
    public static void afficher(final Graphe g) {
        System.out.println(g);
    }

    /**
     * Recherche un chemin entre deux mots par la méthode du parcours en profondeur
     * @param g Graphe
     * @param from Mot de départ
     * @param to Mot d'arrivé
     */
    public static void chemin(Graphe g, String from, String to) {
        ArrayList<Integer> path = new ArrayList<Integer>();
        g.resetParcour();
        if (getChemin(g, path, g.getIndex(from), g.getIndex(to)) != null) {
            for (Integer i : path) {
                System.out.print(g.getMot()[i] + " ");
            }
            System.out.println("");
        } else System.out.println("Aucun chemin trouver entre " + from + " et " + to + ".");
    }

    /**
     * Récupère la liste des mots entre deux mots avec le parcours en profondeur par récurence
     * @param g graphe
     * @param path chemin actuellement réalisé
     * @param actuel mot courrant
     * @param to mot final
     * @return chemin réalisé
     */
    public static ArrayList<Integer> getChemin(Graphe g, ArrayList<Integer> path, int actuel, int to) {
        if (actuel == to) {
            path.add(actuel);
            return path;
        }
        g.parcour(actuel);
        Successeur next = g.getSuccesseur(actuel);
        while (next != null) {
            if (!g.dejaVu(next.getNoeud()) && getChemin(g, path, next.getNoeud(), to) != null) {
                path.add(actuel);
                return path;
            }
            next = next.getNextNoeuds();
        }
        return null;
    }

    /**
     * Implémentation de la recherche de chemin via le parcours en largeur
     * @param g graphe
     * @param from mot de départ
     * @param to mot d'arrivé
     * @return la liste des étapes
     */
    public static List<Integer> BFSIteratif(Graphe g, int from, int to) {
        g.resetParcour();
        List<Integer> aList = new ArrayList<>();
        aList.add(from);
        g.parcour(from);
        while (aList.size() > 0) {
            int s = aList.remove(0);
            Successeur next = g.getSuccesseur(s);
            while (next != null) {
                if (!g.dejaVu(next.getNoeud())) {
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

    /**
     * Affiche le plus court chemin selon la méthode de parcours en largeur
     * @param g le graphe
     * @param from mot de départ
     * @param to mot d'arrivé
     */
    public static void afficherCheminPlusCourt(Graphe g, String from, String to) {
        final int idxFrom = g.getIndex(from);
        final int idxTo = g.getIndex(to);
        if (BFSIteratif(g, idxFrom, idxTo) != null) {
            System.out.print("Chemin : ");
            int elt = idxTo;
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
