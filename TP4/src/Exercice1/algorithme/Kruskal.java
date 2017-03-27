package Exercice1.algorithme;

import Exercice1.interfaces.Graph;
import Exercice1.models.GraphImpl;
import Exercice1.models.Vertex;

import java.util.Iterator;

/**
 * Created by dalencourt on 27/03/17.
 */
public class Kruskal implements Algorithm {

    @Override
    public Graph startAlgorithm(final Graph graph) {
    /*  5   pour chaque arête (u, v) de G prise par poids croissant :
        6      si find(u) ≠ find(v) :
        7         ajouter l'arête (u, v) à l'ensemble A
        8         union(u, v)
        9   retourner A */

        Graph a = new GraphImpl();
        for (Iterator<Vertex> it = graph.getVertexIterator(); it.hasNext(); ) {
            final Vertex v = it.next();
            a.addVertex(v);
        }
        return null;
    }
}
