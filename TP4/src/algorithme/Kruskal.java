package algorithme;

import interfaces.Graph;
import models.Edge;
import models.GraphImpl;
import models.Vertex;

import java.util.*;

/**
 * Implémentation de l'algorithme de Kruskal
 *
 * Created by dalencourt on 27/03/17.
 */
public class Kruskal implements Algorithm {

    @Override
    public Graph startAlgorithm(final Graph graph) {
        // Liste des Groupes de vertex en cours de fusions
        final List<VertexGroup> fusions = new ArrayList<>();

        for (Iterator<Edge> it = graph.getSortedEdgeIterator(); it.hasNext(); ) {
            // On arrète lorque tous les noeuds ont été rencontré et fusionné dans un même groupe
            if(fusions.size() == 1 && fusions.get(0).getVertexs().size() == graph.getCountVertex()){
                break;
            }

            final Edge edge = it.next();
            VertexGroup src = null;
            VertexGroup tgt = null;
            // On recheche les regrouppements associés à chaques extrémité du lien
            for(VertexGroup group : fusions){
                if(group.getVertexs().contains(edge.getSource())){
                    src = group;
                }
                if(group.getVertexs().contains(edge.getTarget())){
                    tgt = group;
                }
            }

            if(src == null && tgt == null){
                // Si les deux extrémités n'ont pas été encore rencontré création d'un nouveau groupe
                fusions.add(new VertexGroup(edge));
            } else if (src == tgt){
                // Si les deux extrémités appertienent au même regroupement on passe au lien suivant
                continue;
            } else if (src == null){
                // Si l'extrémité source n'a pas encore été regroupé on le regroupe avec l'extrémité target
                tgt.fusion(edge.getSource(),edge);
            } else if (tgt == null){
                // Si l'extrémité target n'a pas encore été regroupé on le regroupe avec l'extrémité source
                src.fusion(edge.getTarget(),edge);
            } else {
                // Les deux extrémités ont rencontrés
                // fusion des deux regrouppements
                src.fusion(tgt,edge);
                fusions.remove(tgt);
            }
        }
        // Reconstruction du graphe de sortie
        final Graph output = new GraphImpl();
        output.getVertexList().addAll(fusions.get(0).getVertexs());
        output.getLinks().addAll(fusions.get(0).getEdges());
        return output;
    }

    /**
     * Définit un regrouppement de vertexs
     */
    private class VertexGroup{
        /**
         * Liste des vertexs regroupés
         */
        private List<Vertex> vertexs;
        /**
         * Liste des liens concerné par le regrouppement
         */
        private List<Edge> edges;

        /**
         * Constructeur à partir d'un lien de deux noeud
         * @param edge lien sur lequel créer un regrouppement
         */
        public VertexGroup(final Edge edge){
            vertexs = new ArrayList<>();
            edges = new ArrayList<>();
            vertexs.add(edge.getSource());
            vertexs.add(edge.getTarget());
            edges.add(edge);
        }

        /**
         * Fusionne le regrouppement en paramètre avec le regrouppement courrant grâce au lien en paramètre
         * @param group regrouppement à fusionner
         * @param edge lien permettant la fusion
         */
        public void fusion(final VertexGroup group, final Edge edge){
            vertexs.addAll(group.getVertexs());
            edges.addAll(group.getEdges());
            edges.add(edge);
        }

        /**
         * Groupe un vertex au regrouppement courrant
         * @param vertex vertex à regrouper
         * @param edge lien permettant la fusion
         */
        public void fusion(final Vertex vertex, final Edge edge){
            vertexs.add(vertex);
            edges.add(edge);
        }

        /**
         * @return the vertexs
         * @see VertexGroup#vertexs
         */
        public List<Vertex> getVertexs() {
            return vertexs;
        }

        /**
         * @return the edges
         * @see VertexGroup#edges
         */
        public List<Edge> getEdges() {
            return edges;
        }
    }
}
