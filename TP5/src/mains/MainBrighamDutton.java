package mains;

import algorithmes.BrighamDutton;
import exception.NodeNotFoundException;
import graphe.Graphe;
import graphe.GrapheGenerator;

/**
 * Main pour l'algo WelshPowel
 */
public class MainBrighamDutton {

    public static void main(String[] args) throws NodeNotFoundException {
        Graphe graphe = new GrapheGenerator().generateStaticGraphe();
        new BrighamDutton().run(graphe);
        System.out.println(graphe.getLinkList());
        System.out.println(graphe.getNodeList());
    }
}
