package Exercice1.algorithme;

import Exercice1.interfaces.Graph;
import Exercice1.models.Edge;
import Exercice1.models.GraphImpl;
import Exercice1.models.Vertex;

import java.util.*;

/**
 * Implémentation de l'algorithme de Kruskal
 * <p>
 * Created by dalencourt on 27/03/17.
 */
public class Prim  {

    public int [] startAlgorithm(final Graph graph, final int s) {
        final int[] dist = new int[graph.getCountVertex()];  // shortest known distance to MST
        final int[] pred = new int[graph.getCountVertex()];  // preceeding node in tree
        final boolean[] visited = new boolean[graph.getCountVertex()]; // all false initially

        for (int i = 0; i < dist.length; i++) {
            dist[i] = Integer.MAX_VALUE;
        }
        dist[s] = 0;

        for (int i = 0; i < dist.length; i++) {
            final int next = minVertex(dist, visited);
            visited[next] = true;
            // The edge from pred[next] to next is in the MST (if next!=s)
            final List<Integer> n = graph.getNeighbors(next);
            for (int j = 0; j < n.size(); j++) {
                final int v = n.get(j);
                 Edge a = graph.getEdge(next,v);
                final int d = a.getWeight();
                if (dist[v] > d) {
                    dist[v] = d;
                    pred[v] = next;
                }
            }
        }
        return pred;  // (ignore pred[s]==0!)
    }

    private static int minVertex (int [] dist, boolean [] v) {
              int x = Integer.MAX_VALUE;
               int y = -1;   // graph not connected, or no unvisited vertices
               for (int i=0; i<dist.length; i++) {
                     if (!v[i] && dist[i]<x) {y=i; x=dist[i];}
                  }
              return y;
    }


/*
    public int [] startAlgorithm(final Graph graph, final int s) {
        final int[] cout = new int[graph.getCountVertex()];
        final int[] pred = new int[graph.getCountVertex()];
        final boolean[] visited = new boolean[graph.getCountVertex()];

        for (int i = 0; i < graph.getCountVertex(); i++) {
            cout[i] = Integer.MAX_VALUE;
        }
        cout[s] = 0;
      return pred;
    }
    */
}
