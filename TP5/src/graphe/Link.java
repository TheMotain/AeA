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
     *
     * @param source   noeud source
     * @param target   noeud cible
     * @param dispatch permet de propager la création de lien dans les noeuds
     */

    public Link(Node source, Node target, boolean dispatch) {
        this.source = source;
        this.target = target;
        if (dispatch) {
            source.addNeightbors(target);
            target.addNeightbors(source);
        }
    }

    /**
     * @return the source
     * @see #source
     */
    public Node getSource() {
        return source;
    }

    /**
     * @return the target
     * @see #target
     */
    public Node getTarget() {
        return target;
    }

    /**
     * @param source the source
     * @see Link#source
     */
    public void setSource(final Node source) {
        this.source = source;
    }

    /**
     * @param target the target
     * @see Link#target
     */
    public void setTarget(final Node target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "Link{" +
                "source=" + source.getId() +
                ", target=" + target.getId() +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null){
            return false;
        }
        final Link link = (Link) o;
        return (this.source == link.source && this.target == link.target) ||
                (this.source == link.target && this.target == link.source);
    }
}
