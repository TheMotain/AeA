package algorithmes;

import exception.NodeNotFoundException;
import graphe.Graphe;
import graphe.MetaNode;
import graphe.Node;

import java.util.List;

/**
 * Implémentation de l'algorithme de Brigham Dutton
 */
public class BrighamDutton extends AbstractAlgorithm {

    @Override
    public Graphe run(final Graphe input) throws NodeNotFoundException {
        Graphe copy = new Graphe(input);
        Node[] tuple;
        // Contraction des noeuds tant que c'est possible
        while((tuple = contractable(copy)) != null){
            // Création de la contraction
            MetaNode meta = new MetaNode(tuple);
            // Remplacement des noeuds contractés
            copy.replaceTuple(tuple, meta);
        }
        // Reconstruction des couleurs
        int color = 0;
        for(Node node : copy.getNodeList()){
            node.setColor(color++);
        }
        return input;
    }

    /**
     * Récupère deux nodes qui peuvent être contracté avec le nombre d'arrètes communes maximum.
     * @param graphe Graphe où chercher l'information
     * @return un tuple de deux nodes à contracter
     */
    private Node[] contractable(final Graphe graphe) {
        int max_common_link = -1;
        Node src = null;
        Node tgt = null;
        List<Node> nodeList = graphe.getNodeList();
        // Parcours avec curseur pour ne pas traiter un lien deux fois
        for(int i = 0; i < nodeList.size() - 1; i++){
            for(int j = i + 1; j < nodeList.size(); j++){
                // Contrôle de l'existance d'un lien entre deux noeuds
                if(!graphe.existLink(nodeList.get(i),nodeList.get(j))){
                    int max = graphe.findCommonTarget(nodeList.get(i), nodeList.get(j));
                    // Si le nombre de voisins commun est supérieur au max rencontré sauvegarde du tuple
                    if(max > max_common_link){
                        max_common_link = max;
                        src = nodeList.get(i);
                        tgt = nodeList.get(j);
                    }
                }
            }
        }
        return max_common_link > 0 ? new Node[]{src,tgt} : null;
    }
}
