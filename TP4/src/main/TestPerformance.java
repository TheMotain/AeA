package main;

import exceptions.VertexAlreadyExistException;
import exceptions.VertexNotFoundException;
import execution.MSTToolsImpl;
import execution.RandomGraphGeneratorImpl;
import interfaces.Graph;
import interfaces.MSTTools;
import interfaces.RandomGraphGenerator;

import java.util.Date;

/**
 * Main qui exécute un test de performance pour un N (taille du graphe) P (probabilité d'une arrête)
 * ALGO (algorithme sélectionné) I (Nombre d'exécutions)
 * Created by alex on 03/05/17.
 */
public class TestPerformance {

    public static void main(String[] args) throws VertexAlreadyExistException, VertexNotFoundException {
        if(args.length != 4){
            throw new IllegalArgumentException("Argumens: ALGO (algorithme P ou K) I (Nombre d'exécutions) N (taille du graphe) P (probabilité d'une arrête) ");
        }
        long temps_total = 0;
        int nbExe = Integer.valueOf(args[1]);
        int n = Integer.valueOf(args[2]);
        double p = Double.valueOf(args[3]);
        for(int i = 0; i < nbExe; i++) {
            long temps = 0;
            System.out.println("Exécution n°: " + (i + 1));
            Graph input = new RandomGraphGeneratorImpl().generateErdosRenyiGraph(n, p);
            System.out.println(input);
            Graph output;
            temps = System.currentTimeMillis();
            switch (args[0]) {
                case "P":
                    output = new MSTToolsImpl().runPrim(input);
                    break;
                case "K":
                    output = new MSTToolsImpl().runKruskal(input);
                    break;
                default:
                    throw new IllegalArgumentException("Algorithme non reconnu");
            }
            temps = System.currentTimeMillis() - temps;
            System.out.println(output);
            System.out.println("Temps d'exécution (ms) : " + temps);
            temps_total += temps;
        }
        temps_total /= nbExe;
        System.out.println("----------------------------------");
        System.out.println("Temps moyen (ms) : " + temps_total);
    }
}
