package mains;

import algorithmes.BrighamDutton;
import algorithmes.RecursiveLargestFirstLeighton;
import exception.NodeNotFoundException;
import graphe.Graphe;

/**
 * Main pour l'algo WelshPowel
 */
public class MainRLF {

    public static void main(String[] args) throws NodeNotFoundException {
        Graphe graphe = MainWelshPowel.generateGraphe();
        new RecursiveLargestFirstLeighton().run(graphe);
        System.out.println(graphe.getLinkList());
        System.out.println(graphe.getNodeList());
    }
}
