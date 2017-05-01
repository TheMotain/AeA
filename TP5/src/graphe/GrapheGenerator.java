package graphe;

import exception.NodeAlreadyExistException;
import exception.NodeNotFoundException;

import java.util.Random;

/**
 * Générateur de graphe selon différentes méthodes.
 */
public class GrapheGenerator {

    /**
     * Génère un graphe aléatoire selon la méthode Erdos Reyni
     * @param n nombre de noeuds
     * @param p probabilité de lien
     * @return Le graphe généré
     * @throws IllegalArgumentException remonté si les paramètres ne sont pas valides
     * @throws NodeAlreadyExistException est remonté si une node existe déja à la création
     * @throws NodeNotFoundException est remonté si une node n'existe pas à la cration de lien
     */
    public Graphe generateErdosReyniGraph(int n, double p) throws IllegalArgumentException, NodeAlreadyExistException, NodeNotFoundException {
        final Graphe output = new Graphe();
        if(n < 1 || p < 0 || p > 1){
            throw new IllegalArgumentException();
        }
        for(int i = 1; i <= n; i++){
            try {
                output.createNode(i);
            }catch(NodeAlreadyExistException ignored){}
            for (int j = i + 1; j <= n; j++) {
                double r = Math.random();
                if (r >= 0  && r <= p)
                    output.createLink(i, j);
            }
        }
        return output;
    }

    /**
     * Génère un graphe static
     * @return graphe généré
     * @throws NodeNotFoundException est remonté si une node n'est pas existante
     */
    public Graphe generateStaticGraphe() throws NodeNotFoundException {
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
