package execution;

import algorithme.Kruskal;
import algorithme.Prim;
import exceptions.VertexNotFoundException;
import interfaces.Graph;
import interfaces.MSTTools;

/**
 * Définit l'appel aux différents algorithmes
 * Created by alex on 03/05/17.
 */
public class MSTToolsImpl implements MSTTools {
    @Override
    public Graph runPrim(final Graph g) throws VertexNotFoundException {
        return new Prim().startAlgorithm(g);
    }

    @Override
    public Graph runKruskal(final Graph g) {
        return new Kruskal().startAlgorithm(g);
    }
}
