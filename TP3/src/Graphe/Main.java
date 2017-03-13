package Graphe;

import Utils.StringUtils;

import java.util.ArrayList;

/**
 * Classe principale du programme<br/>
 * Created by dalencourt on 13/03/17.
 */
public class Main {

    public static void ajouterArete(Graphe g, int s, int d) {
        g.setSuccesseur(s, new Successeur(d, g.getSuccesseur(s)));
        g.setSuccesseur(d, new Successeur(s, g.getSuccesseur(d)));
    }

    public static void lettreQuiSaute(Graphe g) {
        for (String str1 : new ArrayList<String>()) {
            for (String str2 : new ArrayList<String>()) {
                if (str1.equals(str2)) {
                    continue;
                }
                if (StringUtils.diffUneLettre(str1, str2)) {
                    ajouterArete(g, g.getIndex(str1), g.getIndex(str2));
                }
            }
        }
    }

    public static void main(String[] args) {
        String[] dico3court = {
                "gag", "gai", "gaz", "gel", "gks", "gin",
                "gnu", "glu", "gui", "guy", "gre", "gue",
                "ace", "acm", "agi", "ait", "aie", "ail",
                "air", "and", "alu", "ami", "arc", "are",
                "art", "apr", "avr", "sur", "mat", "mur"};
        Graphe g = new Graphe(dico3court);
        lettreQuiSaute(g);



        //afficher (g) ;
    }




}
