package algorithmes;

import graphe.Graphe;
import graphe.MetaNode;
import graphe.Node;

import java.util.List;

/**
 * Implémentation de l'algorithle RLF
 */
public class RecursiveLargestFirstLeighton extends AbstractAlgorithm {
    @Override
    public Graphe run(final Graphe input) {
        final Graphe copy = new Graphe(input);
        // On trie les noeuds par degré décroissant
        copy.sortNodeByDegree();
        int color = 0;
        // Tanqu'il existe de noeuds on réalise l'algorithme
        while(!copy.getNodeList().isEmpty()){
            // On sélectionne le noeud de plus grand degré
            Node node = copy.getNodeList().get(0);
            // On cherche un noeud contrctable selon l'algorithme d'élection
            Node contract = findContractNode(copy, node);
            if(contract != null) {
                // Si il existe un noeud contractable on réalise la contraction
                Node[] tuple = new Node[]{node, contract};
                MetaNode metaNode = new MetaNode(tuple);
                // Le meta-Noeud est positionné dans la liste des noeuds comme le noeud de plus grand degré
                // Le nouveau noeud reste le noeud de plus haut degré : (+ haut degré) + (autre degré) = (+ haut degré)
                copy.replaceTuple(tuple, metaNode,0);
            } else {
                // Si plus aucun noeud ne peut être contracté il existe un lien vers tous les noeuds du graphe
                // Suppression du noeud
                copy.removeNode(node);
                // Attribution de la première couleur disponible à tous les noeuds contracté dans node
                node.setColor(color++);
            }
        }
        return input;
    }

    /**
     * Permet de d'élire une node de concaténation avec un vc(x,y) maximum
     * @param graphe Graphe où effectuer les calculs
     * @param node Node source à concaténer avec la node élue
     * @return Node trouvée pour concaténation
     */
    private Node findContractNode(final Graphe graphe, final Node node){
        int max_common_link = -1;
        Node tgt = null;
        List<Node> nodeList = graphe.getNodeList();
        // Parcours des autres noeuds du graphe
        for(int j = 1; j < nodeList.size(); j++){
            // Contrôle de l'existance d'un lien les noeuds
            if(!graphe.existLink(node,nodeList.get(j))){
                int max = graphe.findCommonTarget(node, nodeList.get(j));
                // Si le nombre de voisins commun est supérieur au max rencontré sauvegarde du lien
                if(max > max_common_link){
                    max_common_link = max;
                    tgt = nodeList.get(j);
                }
            }
        }
        return tgt;
    }
}
