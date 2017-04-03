package Exercice1.Parser;

import Exercice1.exceptions.VertexAlreadyExistException;
import Exercice1.exceptions.VertexNotFoundException;
import Exercice1.models.GraphImpl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by ludovicgoldak on 03/04/2017.
 */
public class MainParser {

    public static void main(String[] args) throws VertexAlreadyExistException, VertexNotFoundException, IOException {
        GraphImpl graph = parser.FileToGraph("testGraph.txt");
        System.out.println(graph.toString());

        parser.GraphToFile(graph,"teste2.txt");
    }
}