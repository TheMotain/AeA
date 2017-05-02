package algorithme;

import exceptions.VertexNotFoundException;
import interfaces.Graph;

/**
 * Created by dalencourt on 27/03/17.
 */
public interface Algorithm {

    Graph startAlgorithm(Graph graph) throws VertexNotFoundException;
}
