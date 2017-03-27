package Exercice1.algorithme;

import Exercice1.interfaces.Graph;
import Exercice1.models.Edge;
import Exercice1.models.GraphImpl;
import Exercice1.models.Vertex;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by dalencourt on 27/03/17.
 */
public class Kruskal implements Algorithm {

    @Override
    public Graph startAlgorithm(final Graph graph) {
        final List<Vertex> visitedVertex = new ArrayList<>();
        final Graph a = new GraphImpl();
        for (Iterator<Vertex> it = graph.getVertexIterator(); it.hasNext(); ) {
            final Vertex v = it.next();
            a.addVertex(v);
        }
        for (Iterator<Edge> it = graph.getSortedEdgeIterator(); it.hasNext(); ) {
            if (a.getCountVertex() == visitedVertex.size()) {
                break;
            }
            final Edge e = it.next();
            if (!visitedVertex.contains(e.getSource()) || !visitedVertex.contains(e.getTarget())) {
                a.addEdge(e);
            }
        }
        return a;
    }
}
