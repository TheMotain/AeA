package mains;

import algorithmes.AbstractAlgorithm;
import algorithmes.BrighamDutton;
import algorithmes.RecursiveLargestFirstLeighton;
import algorithmes.WelshPowel;
import exception.NodeAlreadyExistException;
import exception.NodeNotFoundException;
import graphe.Graphe;
import graphe.GrapheGenerator;

import java.util.Date;

/**
 * Contient le main permettant de comparer les algorithmes de coloration de graphes
 */
public class PerformanceComparator {

    /** Nombre d'éxécution pour l'échantillonnage */
    public static final int NOMBRE_EXECUTION = 50;

    /** Nombre de noeuds */
    public static int N = 100;

    /** Probabilité de démarage pour la création des liens */
    public static double P = 0.1;

    public static void main(String[] args) throws NodeAlreadyExistException, NodeNotFoundException {
        long time_total_algo_BD = 0;
        long time_total_algo_WP = 0;
        long time_total_algo_RLF = 0;
        Integer nb_couleur_total_BD = 0;
        Integer nb_couleur_total_WP = 0;
        Integer nb_couleur_total_RLF = 0;
        AbstractAlgorithm bd = new BrighamDutton();
        AbstractAlgorithm wp = new WelshPowel();
        AbstractAlgorithm rlf = new RecursiveLargestFirstLeighton();
        for(int i = 0; i < NOMBRE_EXECUTION; i++){
            Graphe reference = new GrapheGenerator().generateErdosReyniGraph(N, P + (0.2 * (i % 5)));

            // Exécution de la méthode WelshPowel
            Graphe g = new Graphe(reference,true);
            long start = System.currentTimeMillis();
            g = wp.run(g);
            time_total_algo_WP += System.currentTimeMillis() - start;
            nb_couleur_total_WP += g.getColorCount();

            // Exécution de la méthode BrighamDutton
            g = new Graphe(reference,true);
            start = System.currentTimeMillis();
            g = bd.run(g);
            time_total_algo_BD += System.currentTimeMillis() - start;
            nb_couleur_total_BD += g.getColorCount();

            // Exécution de la méthode RLF
            g = new Graphe(reference,true);
            start = System.currentTimeMillis();
            g = rlf.run(g);
            time_total_algo_RLF += System.currentTimeMillis() - start;
            nb_couleur_total_RLF += g.getColorCount();
        }
        time_total_algo_BD /= NOMBRE_EXECUTION;
        time_total_algo_RLF /= NOMBRE_EXECUTION;
        time_total_algo_WP /= NOMBRE_EXECUTION;
        nb_couleur_total_BD /= NOMBRE_EXECUTION;
        nb_couleur_total_RLF /= NOMBRE_EXECUTION;
        nb_couleur_total_WP /= NOMBRE_EXECUTION;
        System.out.println(String.format("Méthode WelshPowel -> Temps moyen : %s ms, Nombre de couleurs moyen : %s", time_total_algo_WP, nb_couleur_total_WP));
        System.out.println(String.format("Méthode BrighamDutton -> Temps moyen : %s ms, Nombre de couleurs moyen : %s", time_total_algo_BD, nb_couleur_total_BD));
        System.out.println(String.format("Méthode RLF -> Temps moyen : %s ms, Nombre de couleurs moyen : %s", time_total_algo_RLF, nb_couleur_total_RLF));
    }
}
