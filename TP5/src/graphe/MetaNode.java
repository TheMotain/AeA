package graphe;

import java.util.ArrayList;
import java.util.List;

/**
 * Définit une métanode
 */
public class MetaNode extends Node {

    /**
     * Contient la liste des nodes contractées
     */
    private List<Node> nodeList;

    /**
     * Constructeur. Réalise la fusion de deux noeuds.
     * @param tuple les noeuds à fusionner
     */
    public MetaNode(final Node[] tuple) {
        super(-1);
        nodeList = new ArrayList<>();
        addNode(tuple[0]);
        addNode(tuple[1]);
    }

    /**
     * Ajoute un noeud à la liste des noeuds contractés.
     * Si le noeud est une métanode le contenu de la méta est copié dans la méta courante.
     *
     * @param node node à intégrer.
     */
    public void addNode(final Node node) {
        if (node instanceof MetaNode) {
            nodeList.addAll(((MetaNode) node).getNodeList());
        } else {
            nodeList.add(node);
        }
        // On fusionne les voisins
        super.neighbors.addAll(node.getNeighbors());
    }

    @Override
    public void setColor(final Integer color) {
        for(Node node : nodeList){
            node.setColor(color);
        }
    }

    /**
     * @return the nodeList
     * @see MetaNode#nodeList
     */
    public List<Node> getNodeList() {
        return nodeList;
    }
}
