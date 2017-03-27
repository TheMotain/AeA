package Exercice1;

import sun.security.provider.certpath.Vertex;

/**
 * Created by dalencourt on 27/03/17.
 */
public interface Graph {

    void addVertex();

    void addVertexNumber(int i)
            throws VertexAlreadyExistException;

    void addEdge(Vertex v1, Vertex v2);
                    throws VertexNotFoundException;

    void addEdge(int i, int j)
            throws VertexNotFoundException;

    Vertex getVertex(int i);

    Iterator<Edge> getSortedEdgeIterator();
}
