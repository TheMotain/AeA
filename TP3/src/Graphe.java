/**
 * Représente la liste des mots avec les liaisons associées<br/>
 * Created by dalencourt on 13/03/17.
 */
public class Graphe {
    Liste listeSucc[];
    String mot[];
    int nbMot = 0;
    public Graphe(String[] lesMots){
        mot = lesMots.clone();
        nbMot = lesMots.length;
        listeSucc = new Liste[nbMot];
        for (int i = 0; i < lesMots.length; i++) {
            listeSucc[i] = new Liste();
        }
    }

    public void getSuccesseur(int s) {
    }
}
