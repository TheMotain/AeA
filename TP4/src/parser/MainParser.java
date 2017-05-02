package parser;

import exceptions.VertexAlreadyExistException;
import exceptions.VertexNotFoundException;
import models.GraphImpl;

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