package graphe;

import java.util.*;

/**
 * Définit un noeud
 */
public class Node {
    /**
     * Id du noeud
     */
    private int id;

    /**
     * Liste des voisins du noeud
     */
    protected List<Node> neighbors;

    /**
     * Couleur du noeud
     */
    private Integer color;

    /**
     * Constructeur
     * @param id du noeud
     */
    public Node(int id) {
        this.id = id;
        this.neighbors = new ArrayList<>();
    }

    /**
     * @see #id
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Ajoute un voisin au noeud
     */
    public void addNeightbors(final Node node){
        this.neighbors.add(node);
    }

    /**
     * récupère la liste des voisins d'un noeud
     * @return liste des voisins
     */
    public List<Node> getNeighbors() {
        return neighbors;
    }

    /**
     * Récupère la couleur du graphe
     * @return couleur
     */
    public Integer getColor() {
        return color;
    }

    /**
     * Modifie la couleur du noeud
     * @param color couleur à setter
     */
    public void setColor(final Integer color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", color=" + color +
                '}';
    }

    public List<Node> getNeighborsOrderByColor() {
        Comparator<Node> comparator = new Comparator<Node>() {
            @Override
            public int compare(final Node t1, final Node t2) {
                if(t1.getColor() == null && t2.getColor() == null){
                    return 0;
                } else if(t1.getColor() == null){
                    return 1;
                } else if(t2.getColor() == null){
                    return -1;
                }
                return Integer.compare(t1.getColor(), t2.getColor());
            }
        };
        Collections.sort(neighbors,comparator);
        return neighbors;
    }
}
