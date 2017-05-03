package execution;

import exceptions.VertexAlreadyExistException;
import exceptions.VertexNotFoundException;
import interfaces.Graph;
import interfaces.RandomGraphGenerator;
import models.GraphImpl;

/**
 * Génère des graphes aléatoires selon plusieurs methodes
 * Created by alex on 03/05/17.
 */
public class RandomGraphGeneratorImpl implements RandomGraphGenerator{

    @Override
    public Graph generateErdosRenyiGraph(final int n, final double p) throws IllegalArgumentException, VertexAlreadyExistException, VertexNotFoundException {
        int poidMax = n*n*n*n;
        Graph output = new GraphImpl();
        for(int i = 0; i < n; i++){
            output.addVertexId(i + 1);
        }
        for(int i = 1; i <= n; i++){
            for(int j = i + 1; j <= n; j++){
                if(Math.random() <= p){
                    output.addEdge(i,j, (int) (Math.random() * poidMax));
                }
            }
        }
        return output;
    }
}
