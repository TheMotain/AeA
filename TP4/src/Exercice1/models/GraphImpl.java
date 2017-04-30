package Exercice1.models;

import Exercice1.exceptions.VertexAlreadyExistException;
import Exercice1.exceptions.VertexNotFoundException;
import Exercice1.interfaces.Graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
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
    public void addEdge(final Vertex v1, final Vertex v2, final int weight) {
        this.addEdge(new Edge(v1, v2, weight));
    }

    @Override
    public void addEdge(final int i, final int j, final int weight) throws VertexNotFoundException {
        Vertex Vi;
        Vertex Vj;
        try{
            Vi = getVertex(i);
        }catch(VertexNotFoundException e ){
            Vi = new Vertex(i);
            addVertex(Vi);
        }
        try{
            Vj = getVertex(j);
        }catch(VertexNotFoundException e ){
            Vj = new Vertex(j);
            addVertex(Vj);
        }
        this.addEdge(new Edge(getVertex(i), getVertex(j), weight));
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
    public Iterator<Vertex> getVertexIterator() {
        return this.nodes.iterator();
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

    public List<Edge> getLinks(){
        return  links;
    }

    public Edge getEdge(final int i,final int j)
    {
        for (int k = 0; k < links.size() ; k++) {
            Edge link = links.get(k);
            if (link.getSource().getId() == i && link.getTarget().getId() == j)
                return link;
            else if(link.getSource().getId() == j && link.getTarget().getId() == i)
                return link;
        }

        return null;
    }

    public List<Integer> getNeighbors(int elt){
        List<Integer> alist = new ArrayList<Integer>();
        for (int i = 0; i < links.size() ; i++) {
            Edge link = links.get(i);
            if (link.getSource().getId() == elt)
                alist.add(link.getTarget().getId());
            else if(link.getTarget().getId() == elt)
                alist.add(link.getSource().getId());
        }
        return  alist;
    }
}
