package graphe;

import com.sun.xml.internal.ws.developer.Serialization;
import exception.NodeAlreadyExistException;
import exception.NodeNotFoundException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Définit un graphe
 */
public class Graphe {
    /**
     * Liste des noeuds
     */
    private List<Node> nodeList;

    /**
     * Liste des liens
     */
    private List<Link> linkList;

    /**
     * Constructeur
     */
    public Graphe() {
        this.nodeList = new ArrayList<>();
        this.linkList = new ArrayList<>();
    }

    /**
     * Ajoute un noeud au graphe
     * @param n node à ajouter
     */
    public void addNode(final Node n) {
        this.nodeList.add(n);
    }

    /**
     * Créer et ajoute une node au graphe
     * @param id id du noeud à créer
     * @throws NodeAlreadyExistException si l'id existe déjà
     */
    public void createNode(final int id) throws NodeAlreadyExistException {
        Node e = new Node(id);
        if (this.nodeList.contains(e)) {
            throw new NodeAlreadyExistException();
        }
        this.nodeList.add(e);
    }

    /**
     * Ajoute un lien entre deux noeuds
     * @param n1 noeud source
     * @param n2 noeud cible
     */
    public void addLink(final Node n1, final Node n2) {
        this.addLink(new Link(n1, n2));
    }

    /**
     * Créer un lien entre deux noeud identifié par un id. Créer les noeuds si ils n'existent pas.
     * @param i noeud source
     * @param j noeud cible
     * @throws NodeNotFoundException est remonté si les noeuds créé n'existent pas
     */
    public void createLink(final int i, final int j) throws NodeNotFoundException {
        Node ni;
        Node nj;
        try{
            ni = getNode(i);
        }catch(NodeNotFoundException e ){
            ni = new Node(i);
            addNode(ni);
        }
        try{
            nj = getNode(j);
        }catch(NodeNotFoundException e ){
            nj = new Node(j);
            addNode(nj);
        }
        this.addLink(new Link(getNode(i), getNode(j)));
    }

    /**
     * Permet d'ajouter le lien passé en paramètre
     * @param l lien à ajouter
     */
    public void addLink(final Link l) {
        linkList.add(l);
    }

    /**
     * Récupère le noeud pour l'id passé en paramètre
     * @param i id du noeud à récupérer
     * @return le noeud récupéré
     * @throws NodeNotFoundException est remonté si le noeud demandé n'existe pas
     */
    public Node getNode(final int i) throws NodeNotFoundException {
        for (Node tmp : nodeList) {
            if (tmp.getId() == i) {
                return tmp;
            }
        }
        throw new NodeNotFoundException();
    }

    /**
     * Récupère le nombre de noeuds du graphe
     * @return nombre de noeuds
     */
    public int getCountNode() {
        return nodeList.size();
    }

    @Override
    public String toString() {
        return "Graph{" +
                "nodes=" + nodeList +
                ", links=" + linkList +
                '}';
    }

    /**
     * @see #nodeList
     * @return the node list
     */
    public List<Node> getNodeList() {
        return nodeList;
    }

    /**
     * @see #linkList
     * @return the node list
     */
    public List<Link> getLinkList() {
        return linkList;
    }
}
