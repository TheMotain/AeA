package interfaces;

import exceptions.VertexAlreadyExistException;
import exceptions.VertexNotFoundException;

/**
 * Interface définissant différentes méthodes de génération de graphe aléatoire
 * Created by alex on 03/05/17.
 */
public interface RandomGraphGenerator {

    /**
     * Méthode de génération d'un graphe selon la méthode Erdos Renyi
     * @param n nombre de noeuds
     * @param p probabilité de création d'un lien
     * @return graphique généré
     * @throws IllegalArgumentException
     * @throws VertexAlreadyExistException
     */
    Graph generateErdosRenyiGraph(int n, double p)
            throws IllegalArgumentException, VertexAlreadyExistException, VertexNotFoundException;

}
