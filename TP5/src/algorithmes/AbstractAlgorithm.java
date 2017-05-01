package algorithmes;

import exception.NodeNotFoundException;
import graphe.Graphe;

/**
 * Définit la partie comune à tous les algorithmes
 */
public abstract class AbstractAlgorithm {

    public abstract Graphe run(final Graphe input) throws NodeNotFoundException;
}
