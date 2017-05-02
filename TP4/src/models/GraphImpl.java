package models;

import exceptions.VertexAlreadyExistException;
import exceptions.VertexNotFoundException;
import interfaces.Graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implémentation du graphe
 * Created by dalencourt on 27/03/17.
 */
public class GraphImpl implements Graph {

    private List<Vertex> nodes;

    private List<Edge> links;

    public GraphImpl() {
        this.nodes = new ArrayList<>();
        this.links = new ArrayList<>();
    }

    @Override
    public void addVertex(final Vertex v) {
        this.nodes.add(v);
    }

    @Override
    public void addVertexNumber(final int i) throws VertexAlreadyExistException {
        Vertex e = new Vertex(i);
        if (this.nodes.contains(e)) {
            throw new VertexAlreadyExistException();
        }
        this.nodes.add(e);
    }
    
    @Override
    public void addEdge(final int i, final int j, final int weight) throws VertexNotFoundException {
        Vertex vi;
        Vertex vj;
        try {
            vi = getVertex(i);
        } catch (VertexNotFoundException e) {
            vi = new Vertex(i);
            addVertex(vi);
        }
        try {
            vj = getVertex(j);
        } catch (VertexNotFoundException e) {
            vj = new Vertex(j);
            addVertex(vj);
        }
        this.addEdge(new Edge(vi, vj, weight));
    }

    @Override
    public void addEdge(final Edge e) {
        int i = 0;
        while (i < this.links.size() && e.getWeight() > links.get(i).getWeight()) {
            i++;
        }
        this.links.add(i, e);
    }

    @Override
    public Vertex getVertex(final int i) throws VertexNotFoundException {
        for (Vertex tmp : nodes) {
            if (tmp.getId() == i) {
                return tmp;
            }
        }
        throw new VertexNotFoundException();
    }

    @Override
    public Iterator<Edge> getSortedEdgeIterator() {
        return this.links.iterator();
    }

    @Override
    public int getCountVertex() {
        return nodes.size();
    }

    @Override
    public String toString() {
        return "GraphImpl{" +
                "nodes=" + nodes +
                ", links=" + links +
                '}';
    }

    public List<Edge> getLinks() {
        return links;
    }

    @Override
    public List<Edge> getEdgesFromVertex(Vertex vertex) {
        List<Edge> alist = new ArrayList<>();
        for (Edge link : links) {
            if (link.getSource().equals(vertex))
                alist.add(link);
            else if (link.getTarget().equals(vertex))
                alist.add(link);
        }
        return alist;
    }

    @Override
    public List<Vertex> getVertexList() {
        return nodes;
    }
}
