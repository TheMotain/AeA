package interfaces;

import exceptions.VertexNotFoundException;

/**
 * Interface définissant les algorithmes exécutables
 * Created by alex on 03/05/17.
 */
public interface MSTTools {

    /**
     * Exécute l'algorithme de Prim sur le graphe en paramètre
     * @param g graphe à réduire
     * @return graphe calculé
     */
    Graph runPrim(Graph g) throws VertexNotFoundException;

    /**
     * Exécute l'algorithme de Kruskal sur le graphe en paramètre
     * @param g graphe à réduire
     * @return graphe calculé
     */
    Graph runKruskal(Graph g);
}
