package interfaces;

import exceptions.VertexAlreadyExistException;
import exceptions.VertexNotFoundException;
import models.Edge;
import models.Vertex;

import java.util.Iterator;
import java.util.List;

/**
 * Interface définissant un graphe
 * Created by dalencourt on 27/03/17.
 */
public interface Graph {

    /**
     * Ajoute un vertex
     *
     * @param v vertex à ajouter
     */
    void addVertex(Vertex v);

    /**
     * Ajoute un vertex avec un id précisé
     *
     * @param i id du vertex
     * @throws VertexAlreadyExistException est remonté si l'id existe déjà
     */
    void addVertexNumber(int i)
            throws VertexAlreadyExistException;

    /**
     * Ajoute un lien entre deux vertex. Le vertex est créé si inexistant
     *
     * @param i      id du vertex source
     * @param j      id du vertex cible
     * @param weight poid du lien
     * @throws VertexNotFoundException
     */
    void addEdge(int i, int j, int weight)
            throws VertexNotFoundException;

    /**
     * Ajoute un lien en les triants par poid
     *
     * @param e lien à ajouter
     */
    void addEdge(Edge e);

    /**
     * Recherche un vertex
     *
     * @param i id du vertex
     * @return retourne le vertex
     * @throws VertexNotFoundException si le vertex demandé n'existe pas
     */
    Vertex getVertex(int i) throws VertexNotFoundException;

    /**
     * Retourne un iterator sur les liens trié par poid croissant
     *
     * @return
     */
    Iterator<Edge> getSortedEdgeIterator();

    /**
     * Retourne le nombre de vertexs
     *
     * @return nombre de vertexs
     */
    int getCountVertex();

    /**
     * récupère la liste des liens
     *
     * @return liste
     */
    List<Edge> getLinks();

    /**
     * récupère les liens en partance d'un vertex
     * @param vertex id du vertex
     * @return lien du vertex
     */
    List<Edge> getEdgesFromVertex(Vertex vertex);

    /**
     * Retourne la liste des vertexs
     * @return liste des vertex
     */
    List<Vertex> getVertexList();
}
