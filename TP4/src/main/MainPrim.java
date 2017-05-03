package main;

import algorithme.Prim;
import exceptions.VertexAlreadyExistException;
import exceptions.VertexNotFoundException;
import interfaces.Graph;
import models.GraphImpl;

/**
 * Main qui exécute lalgorithme de prim
 * Created by ludovicgoldak on 24/04/2017.
 */
public class MainPrim {

    public static void main(String[] args) throws VertexAlreadyExistException, VertexNotFoundException {
        Graph input = new GraphImpl();
        input.addVertexId(1);
        input.addVertexId(2);
        input.addVertexId(3);
        input.addVertexId(4);
        input.addVertexId(5);
        input.addVertexId(6);
        input.addVertexId(7);
        input.addVertexId(8);
        input.addVertexId(9);
        input.addVertexId(10);
        input.addVertexId(11);
        input.addVertexId(12);
        input.addEdge(1, 2, 7);
        input.addEdge(1, 3, 10);
        input.addEdge(2, 3, 2);
        input.addEdge(3, 5, 9);
        input.addEdge(3, 4, 21);
        input.addEdge(4, 5, 19);
        input.addEdge(4, 7, 6);
        input.addEdge(5, 6, 8);
        input.addEdge(6, 10, 20);
        input.addEdge(10, 9, 12);
        input.addEdge(9, 5, 3);
        input.addEdge(4, 8, 5);
        input.addEdge(5, 8, 13);
        input.addEdge(8, 9, 1);
        input.addEdge(8, 11, 4);
        input.addEdge(8, 12, 11);
        System.out.println(new Prim().startAlgorithm(input));
    }

}
