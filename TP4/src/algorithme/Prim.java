package algorithme;

import exceptions.VertexNotFoundException;
import interfaces.Graph;
import models.Edge;
import models.GraphImpl;
import models.Vertex;
import sun.awt.image.ImageWatched;

import java.util.*;

/**
 * Implémentation de l'algorithme de Prim
 * <p>
 * Created by dalencourt on 27/03/17.
 */
public class Prim implements Algorithm {

    private final Comparator<Edge> edgeComparator = (edge, t1) -> edge.getWeight() - t1.getWeight();

    @Override
    public Graph startAlgorithm(final Graph graph) throws VertexNotFoundException {
        List<Vertex> vertexInsere = new ArrayList<>();
        List<Edge> edgeInsere = new ArrayList<>();

        // On sort le premier noeud pour démarrer le calcul d'arbre recouvrant de poid le plus faible
        Vertex current = graph.getVertexList().remove(0);
        // Le noeud est ajouté au noeuds rencontré
        vertexInsere.add(current);
        // récupération des liens disponibles à partir des noeuds rencontrés
        List<Edge> liensDisponibles = graph.getEdgesFromVertex(current);
        liensDisponibles.sort(edgeComparator);

        // Tant qu'il y a des noeuds non rencontré
        while(graph.getCountVertex() > 0 ) {
            // sélection du lien de poid le plus faible parmis les liens disponnible
            Edge tmp = Collections.min(liensDisponibles, edgeComparator);
            // Récupération du nouveau noeud
            Vertex target = vertexInsere.contains(tmp.getSource()) ? tmp.getTarget() : tmp.getSource();
            // Suppression du noeud du graphe d'origine
            graph.getVertexList().remove(target);
            // Sauvegarde du noeud dans le graphe cible
            vertexInsere.add(target);
            // Sauvegarde du lien
            edgeInsere.add(tmp);
            // Récupération des liens disponibles avec l'ajout du nouveau noeud
            liensDisponibles.addAll(graph.getEdgesFromVertex(target));
            // Suppression des cycles possible dans les liens possibles par rapport aux noeuds courrants rencontrés
            supprimerCycles(vertexInsere, liensDisponibles);
            // Si il n'y a plus liens mais encore des noeuds on rajoute le premier noeud de la liste
            // Cas de composante non connexe
            while (liensDisponibles.size() == 0 && graph.getCountVertex() > 0){
                Vertex v = graph.getVertexList().remove(0);
                liensDisponibles.addAll(graph.getEdgesFromVertex(v));
                vertexInsere.add(v);
            }
        }

        final Graph output = new GraphImpl();
        output.getVertexList().addAll(vertexInsere);
        output.getLinks().addAll(edgeInsere);
        return output;
    }

    /**
     * Supprime les cycles pouvant être crée avec les liens disponibles
     * @param vertexInsere Vertex présent
     * @param liensDisponibles Liens disponible
     */
    private void supprimerCycles(final List<Vertex> vertexInsere, final List<Edge> liensDisponibles) {
        ListIterator<Edge> it = liensDisponibles.listIterator();
        while (it.hasNext()){
            Edge tmp = it.next();
            // Si les noeud du lien ont déjà été rencontré un cycle peut être cré donc suppression
            if(vertexInsere.contains(tmp.getSource()) && vertexInsere.contains(tmp.getTarget())){
                it.remove();
            }
        }
    }
}
