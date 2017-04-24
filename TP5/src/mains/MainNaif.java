package mains;

import algorithmes.Naif;
import exception.NodeNotFoundException;
import graphe.Graphe;

/**
 * Main pour l'algo Naif
 */
public class MainNaif {

    public static void main(String[] args) throws NodeNotFoundException {
        Graphe graphe = generateGraphe();
        new Naif().run(graphe);
        System.out.println(graphe.getLinkList());
        System.out.println(graphe.getNodeList());
    }

    private static Graphe generateGraphe() throws NodeNotFoundException {
        Graphe output = new Graphe();
        output.createLink(1,2);
        output.createLink(1,3);
        output.createLink(1,4);
        output.createLink(1,7);
        output.createLink(1,8);
        output.createLink(2,3);
        output.createLink(2,5);
        output.createLink(3,4);
        output.createLink(3,6);
        output.createLink(3,7);
        output.createLink(3,8);
        output.createLink(4,5);
        output.createLink(5,6);
        output.createLink(5,7);
        output.createLink(5,8);
        return output;
    }
}
