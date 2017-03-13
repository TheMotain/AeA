package Graphe;

/**
 * Représente la liste des mots avec les liaisons associées<br/>
 * Created by dalencourt on 13/03/17.
 */
public class Graphe {

    private Successeur listeSucc[];

    private String mot[];

    private int nbMot = 0;

    public Graphe(String[] lesMots){
        mot = lesMots.clone();
        nbMot = lesMots.length;
        listeSucc = new Successeur[nbMot];
    }

    public Successeur getSuccesseur(int idx) {
        return null;
    }

    public void setSuccesseur(int idx, Successeur s) {

    }

    public int getIndex(String str) {
        return 0;
    }
}
