package mains;

import algorithmes.WelshPowel;
import exception.NodeNotFoundException;
import graphe.Graphe;
import graphe.GrapheGenerator;

/**
 * Main pour l'algo WelshPowel
 */
public class MainWelshPowel {

    public static void main(String[] args) throws NodeNotFoundException {
        Graphe graphe = new GrapheGenerator().generateStaticGraphe();
        new WelshPowel().run(graphe);
        System.out.println(graphe.getLinkList());
        System.out.println(graphe.getNodeList());
    }
}
