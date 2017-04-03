package Exercice1.algorithme;

import Exercice1.exceptions.VertexAlreadyExistException;
import Exercice1.exceptions.VertexNotFoundException;
import Exercice1.interfaces.Graph;
import Exercice1.models.GraphImpl;

/**
 * Created by dalencourt on 27/03/17.
 */
public class MainKruskal {

    public static void main(String[] args) throws VertexAlreadyExistException, VertexNotFoundException {
        Graph input = new GraphImpl();
        input.addVertexNumber(1);
        input.addVertexNumber(2);
        input.addVertexNumber(3);
        input.addVertexNumber(4);
        input.addVertexNumber(5);
        input.addVertexNumber(6);
        input.addVertexNumber(7);
        input.addVertexNumber(8);
        input.addVertexNumber(9);
        input.addVertexNumber(10);
        input.addVertexNumber(11);
        input.addVertexNumber(12);
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
        System.out.println(new Kruskal().startAlgorithm(input));
    }

}
