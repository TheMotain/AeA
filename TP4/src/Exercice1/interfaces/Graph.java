package Exercice1.interfaces;

import Exercice1.exceptions.VertexAlreadyExistException;
import Exercice1.exceptions.VertexNotFoundException;
import Exercice1.models.Edge;
import Exercice1.models.Vertex;

import java.util.Iterator;
import java.util.List;

/**
 * Created by dalencourt on 27/03/17.
 */
public interface Graph {

    void addVertex(Vertex v);

    void addVertexNumber(int i)
            throws VertexAlreadyExistException;

    void addEdge(Vertex v1, Vertex v2, int weight);

    void addEdge(int i, int j, int weight)
            throws VertexNotFoundException;

    void addEdge(Edge e);

    Vertex getVertex(int i) throws VertexNotFoundException;

    Iterator<Vertex> getVertexIterator();

    Iterator<Edge> getSortedEdgeIterator();

    int getCountVertex();

     List<Edge> getLinks();

     Edge getEdge(int i,int j);


     List<Integer> getNeighbors(int elt);


    }
