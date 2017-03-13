package Graphe;

/**
 * Classe principale du programme<br/>
 * Created by dalencourt on 13/03/17.
 */
public class Main {

    public static void ajouterArete(Graphe g, int s, int d) {
        g.setSuccesseur(s, new Successeur(d, g.getSuccesseur(s)));
        g.setSuccesseur(s, new Successeur(d, g.getSuccesseur(s)));
    }

    public static void lettreQuiSaute(Graphe g) {

    }
}
