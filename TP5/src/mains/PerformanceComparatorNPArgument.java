package mains;

import algorithmes.AbstractAlgorithm;
import algorithmes.BrighamDutton;
import algorithmes.RecursiveLargestFirstLeighton;
import algorithmes.WelshPowel;
import exception.NodeAlreadyExistException;
import exception.NodeNotFoundException;
import graphe.Graphe;
import graphe.GrapheGenerator;

/**
 * Contient le main permettant de comparer les algorithmes de coloration de graphes
 */
public class PerformanceComparatorNPArgument {

    /** Nombre d'éxécution pour l'échantillonnage */
    public static final int NOMBRE_EXECUTION = 0;

    /** Nombre de noeuds */
    public static int N = 1;

    /** Probabilité de démarage pour la création des liens */
    public static int P = 2;

    public static void main(String[] args) throws NodeAlreadyExistException, NodeNotFoundException {
        if(args.length != 3){
            throw new IllegalArgumentException();
        }
        long time_total_algo_BD = 0;
        long time_total_algo_WP = 0;
        long time_total_algo_RLF = 0;
        Integer nb_couleur_total_BD = 0;
        Integer nb_couleur_total_WP = 0;
        Integer nb_couleur_total_RLF = 0;
        AbstractAlgorithm bd = new BrighamDutton();
        AbstractAlgorithm wp = new WelshPowel();
        AbstractAlgorithm rlf = new RecursiveLargestFirstLeighton();
        for(int i = 0; i < Integer.valueOf(args[NOMBRE_EXECUTION]); i++){
            Graphe reference = new GrapheGenerator().generateErdosReyniGraph(Integer.valueOf(args[N]), Double.valueOf(args[P]));

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
        time_total_algo_BD /= Integer.valueOf(args[NOMBRE_EXECUTION]);
        time_total_algo_RLF /= Integer.valueOf(args[NOMBRE_EXECUTION]);
        time_total_algo_WP /= Integer.valueOf(args[NOMBRE_EXECUTION]);
        nb_couleur_total_BD /= Integer.valueOf(args[NOMBRE_EXECUTION]);
        nb_couleur_total_RLF /= Integer.valueOf(args[NOMBRE_EXECUTION]);
        nb_couleur_total_WP /= Integer.valueOf(args[NOMBRE_EXECUTION]);
        System.out.println(String.format("Méthode WelshPowel -> Temps moyen : %s ms, Nombre de couleurs moyen : %s", time_total_algo_WP, nb_couleur_total_WP));
        System.out.println(String.format("Méthode BrighamDutton -> Temps moyen : %s ms, Nombre de couleurs moyen : %s", time_total_algo_BD, nb_couleur_total_BD));
        System.out.println(String.format("Méthode RLF -> Temps moyen : %s ms, Nombre de couleurs moyen : %s", time_total_algo_RLF, nb_couleur_total_RLF));
    }
}
