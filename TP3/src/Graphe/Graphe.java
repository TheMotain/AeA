package Graphe;

/**
 * Représente la liste des mots avec les liaisons associées<br/>
 * Created by dalencourt on 13/03/17.
 */
public class Graphe {
    Successeur listeSucc[];
    String mot[];
    int nbMot = 0;
    public Graphe(String[] lesMots){
        mot = lesMots.clone();
        nbMot = lesMots.length;
        listeSucc = new Successeur[nbMot];
    }

    public Successeur getSuccesseur(int s) {
        return null;
    }
}
