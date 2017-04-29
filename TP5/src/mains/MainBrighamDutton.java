package mains;

import algorithmes.BrighamDutton;
import exception.NodeNotFoundException;
import graphe.Graphe;

/**
 * Main pour l'algo Naif
 */
public class MainBrighamDutton {

    public static void main(String[] args) throws NodeNotFoundException {
        Graphe graphe = MainNaif.generateGraphe();
        new BrighamDutton().run(graphe);
        System.out.println(graphe.getLinkList());
        System.out.println(graphe.getNodeList());
    }
}
