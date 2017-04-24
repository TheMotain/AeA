package graphe;

/**
 * Définit un lien entre deux noeuds
 */
public class Link {
    /**
     * Node source
     */
    private Node source;

    /**
     * Node cible
     */
    private Node target;

    /**
     * Constructeur
     * @param source noeud source
     * @param target noeud cible
     */
    public Link(Node source, Node target) {
        this.source = source;
        this.target = target;
        source.addNeightbors(target);
        target.addNeightbors(source);
    }

    /**
     * @see #source
     * @return the source
     */
    public Node getSource() {
        return source;
    }

    /**
     * @see #target
     * @return the target
     */
    public Node getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return "Link{" +
                "source=" + source.getId() +
                ", target=" + target.getId() +
                '}';
    }
}
