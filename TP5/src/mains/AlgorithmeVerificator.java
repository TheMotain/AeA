package mains;

import algorithmes.BrighamDutton;
import algorithmes.RecursiveLargestFirstLeighton;
import algorithmes.WelshPowel;
import exception.NodeAlreadyExistException;
import exception.NodeNotFoundException;
import graphe.Graphe;
import graphe.GrapheGenerator;

/**
 * Main qui génère un graphe aléatoire en fonction des paramètres en argument et réalise l'algorithme choisi.<br>
 * Permet de contrôler la validité desn algorithmes écrits
 */
public class AlgorithmeVerificator {

    public static void main(String[] args) throws NodeAlreadyExistException, NodeNotFoundException {
        if (args.length != 3) {
            throw new IllegalArgumentException();
        }
        Graphe reference = new GrapheGenerator().generateErdosReyniGraph(Integer.valueOf(args[1]), Double.valueOf(args[2]));
        if (args[0].equals("wp")) {
            // Exécution de la méthode WelshPowel
            reference = new WelshPowel().run(reference);
        } else if (args[0].equals("bd")) {
            // Exécution de la méthode BrighamDutton
            reference = new BrighamDutton().run(reference);
        } else if (args[0].equals("rlf")) {
            // Exécution de la méthode RLF
            reference = new RecursiveLargestFirstLeighton().run(reference);
        } else {
            throw new IllegalArgumentException("Algorithme non reconnu : wp / bd / rlf");
        }
        System.out.println(reference);
    }
}