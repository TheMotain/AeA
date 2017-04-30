package graphe;

import com.sun.xml.internal.ws.developer.Serialization;
import exception.NodeAlreadyExistException;
import exception.NodeNotFoundException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

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
     * Constructeur
     * @param input graphe à copier
     */
    public Graphe(final Graphe input){
        this.nodeList = new ArrayList<>(input.getNodeList());
        this.linkList = new ArrayList<>();
        for(final Link link : input.getLinkList()){
            this.linkList.add(new Link(link.getSource(),link.getTarget(),false));
        }
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
        this.addLink(new Link(n1, n2,true));
    }

    /**
     * Permet d'ajouter le lien passé en paramètre
     * @param l lien à ajouter
     */
    public void addLink(final Link l) {
        linkList.add(l);
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
        this.addLink(new Link(ni, nj, true));
    }

    /**
     * Récupère le noeud pour l'id passé en paramètre
     * @param id id du noeud à récupérer
     * @return le noeud récupéré
     * @throws NodeNotFoundException est remonté si le noeud demandé n'existe pas
     */
    public Node getNode(final int id) throws NodeNotFoundException {
        for (Node tmp : nodeList) {
            if (tmp.getId() == id) {
                return tmp;
            }
        }
        throw new NodeNotFoundException();
    }

    /**
     * Ajoute un noeud au graphe
     * @param n node à ajouter
     */
    public void addNode(final Node n) {
        this.nodeList.add(n);
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
     * Trie les noeuds par ordre de degrée descendant
     */
    public void sortNodeByDegree() {
        nodeList.sort((node1, node2) -> node2.getNeighbors().size() - node1.getNeighbors().size());
    }

    /**
     * Remplace un couple de deux noeuds par un noeud méta.
     * @param tuple couple à remplacer
     * @param meta noeud de remplacement
     */
    public void replaceTuple(final Node[] tuple, final MetaNode meta) {
        this.nodeList.remove(tuple[0]);
        this.nodeList.remove(tuple[1]);
        this.nodeList.add(meta);
        for(Link link : this.linkList){
            replaceNodeLink(link,tuple[0],meta);
            replaceNodeLink(link,tuple[1],meta);
        }
    }

    /**
     * Remplace un couple de deux noeuds par un noeud méta
     * et le positionne dans la liste à l'index choisi.
     * @param tuple couple à remplacer
     * @param meta noeud de remplacement
     * @param idx index où positionner le noeud méta
     */
    public void replaceTuple(final Node[] tuple, final MetaNode meta, final int idx) {
        this.nodeList.remove(tuple[0]);
        this.nodeList.remove(tuple[1]);
        this.nodeList.add(idx,meta);
        for(Link link : this.linkList){
            replaceNodeLink(link,tuple[0],meta);
            replaceNodeLink(link,tuple[1],meta);
        }
    }

    /**
     * Remplace le noeud par le noeud méta si il est dans le lien en paramètre
     * @param link lien à mettre à jour
     * @param node noeud à rempalcer
     * @param meta métanoeud à remplacer
     */
    private void replaceNodeLink(final Link link, final Node node, final MetaNode meta){
        if(link.getSource() == node){
            link.setSource(meta);
        }else if(link.getTarget() == node){
            link.setTarget(meta);
        }
    }

    /**
     * Permet de savoir si un lien existe entre deux nodes.
     * @param node1 node 1
     * @param node2 node 2
     * @return vrai ou faux
     */
    public boolean existLink(final Node node1, final Node node2) {
        return this.linkList.contains(new Link(node1,node2,false));
    }

    /**
     * Retourne le nombre de voisins en commun entre deux noeuds
     * @param node1 node 1
     * @param node2 node 2
     * @return Nombre de targets communes
     */
    public int findCommonTarget(final Node node1, final Node node2) {
        int count = 0;
        List<Node> neigthbors = node2.getNeighbors();
        for(Node node : node1.getNeighbors()){
            if(neigthbors.contains(node)){
                count++;
            }
        }
        return count;
    }

    /**
     * Permet de supprimer un noeud et tout ses liens
     * @param node noeud à supprimer.
     */
    public void removeNode(final Node node) {
        this.nodeList.remove(node);
        ListIterator<Link> it = this.linkList.listIterator();
        while (it.hasNext()){
            Link link = it.next();
            if(link.getSource() == node || link.getTarget() == node){
                it.remove();
            }
        }
    }
}
