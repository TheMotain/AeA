package algorithmes;

import graphe.Graphe;
import graphe.Node;

import java.util.List;

/**
 * Traitement naif pour la coloration des noeud. Pour chaque noeud on choisi une couleur différente des tous ses voisin.
 * La couleur de base étant à 0 on prendra la plus petite couleur disponible pour chaque noeud.
 */
public class Naif extends AbstractAlgorithm {

    @Override
    public Graphe run(final Graphe input) {
        final int baseColor = 0;
        // Parcours de tous les noeud
        for (final Node node : input.getNodeList()) {
            int color = baseColor;
            for (Node tmp : node.getNeighborsOrderByColor()) {
                if (tmp.getColor() != null && tmp.getColor() != color) {
                    break;
                } else if (tmp.getColor() != null) {
                    color++;
                }
            }
            node.setColor(color);
        }
        return input;
    }
}
