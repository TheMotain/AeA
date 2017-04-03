package Exercice1.algorithme;

import Exercice1.interfaces.Graph;
import Exercice1.models.Edge;
import Exercice1.models.GraphImpl;
import Exercice1.models.Vertex;

import java.util.*;

/**
 * Implémentation de l'algorithme de Kruskal
 *
 * Created by dalencourt on 27/03/17.
 */
public class Kruskal implements Algorithm {

    @Override
    public Graph startAlgorithm(final Graph graph) {
        // Map des sous graphes qui ont été créé pour chaque noeud visité
        final Map<Vertex, GraphID> visitedVertex = new HashMap<>();

        final Graph a = new GraphImpl();

        for (Iterator<Edge> it = graph.getSortedEdgeIterator(); it.hasNext(); ) {
            // Contrôle en premier si tous les noeuds ont été rencontré une fois
            // Si la première condition est validé on regarde grâce à un set si nous avons un unique graph
            if (a.getCountVertex() == graph.getCountVertex() && new HashSet<>(visitedVertex.values()).size() == 1)
                // Si la condition est validée arrêt de l'algorithme
                break;

            final Edge e = it.next();
            // Contrôle si le noeud source ou destination n'a pas encore été rencontré
            // Si les deux premières conditions n'ont pas été validées on regarde si les graphes associés pour chaque noeud sont différents
            if (visitedVertex.get(e.getSource()) == null || visitedVertex.get(e.getSource()) == null || !visitedVertex.get(e.getSource()).equals(visitedVertex.get(e.getTarget()))) {
                // Les condition sont valides on ajoute le lien au graphe
                a.addEdge(e);
                // On récupère les sous graphes associés
                GraphID g1 = visitedVertex.get(e.getSource());
                GraphID g2 = visitedVertex.get(e.getTarget());

                if (g1 == null && g2 == null) {
                    // Les deux noeuds n'ont encore jamais été rencontré, on créer un sous graphe pour ces noeud
                    g1 = new GraphID();
                    visitedVertex.put(e.getSource(), g1);
                    visitedVertex.put(e.getTarget(), g1);
                    a.addVertex(e.getSource());
                    a.addVertex(e.getTarget());
                } else if (g1 == null) {
                    // La source n'a pas encore été rencontrée on l'associe au sous graphe de la destination
                    visitedVertex.put(e.getSource(), g2);
                    a.addVertex(e.getSource());
                } else if (g2 == null) {
                    // La destination n'a pas encore été rencontrée on l'associe au sous graphe de la source
                    visitedVertex.put(e.getTarget(), g1);
                    a.addVertex(e.getTarget());
                } else {
                    // Les deux noeud on des sous graphes différents
                    // On fusionne les sous graphe en changeant l'ID du sous graphe du noeud de destination
                    // Les autres noeud du sous graphe par référence
                    g2.setId(g1.getId());
                    g1.addFusion(g2);
                }
            }
        }
        return a;
    }

    /**
     * Définit un numéro de sous graphe avec un id généré automatiquement à la création du sous graphe
     */
    private static class GraphID {
        /**
         * Id du prochain sous graphe
         */
        private static int id_inc = 1;
        /**
         * Id du graphe
         */
        private int id = id_inc++;
        /**
         * Liste de graph fusionné
         */
        private final List<GraphID> fusions = new ArrayList<>();

        /**
         * Permet de modifier l'id du graphe et de tous les graphes fusionnés
         *
         * @param id nouvel ID
         */
        void setId(int id) {
            this.id = id;
            for (GraphID fusion : fusions) {
                fusion.setId(id);
            }
        }

        /**
         * Récupère l'ID du graphe
         *
         * @return l'ID
         */
        int getId() {
            return this.id;
        }

        /**
         * Permet de fusionner un graph au graph courrant
         *
         * @param graph Graph à fusionner
         */
        void addFusion(GraphID graph) {
            this.fusions.add(graph);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            final GraphID graphID = (GraphID) o;

            return id == graphID.id;
        }

        @Override
        public int hashCode() {
            return id;
        }
    }
}
