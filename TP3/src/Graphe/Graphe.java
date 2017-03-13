package Graphe;

/**
 * Représente la liste des mots avec les liaisons associées<br/>
 * Created by dalencourt on 13/03/17.
 */
public class Graphe {

    private Successeur listeSucc[];

    private String mot[];

    private int nbMot = 0;

    private boolean dejaVu[];

    /**
     * Constructeur
     * @param lesMots Liste des mots à mettre dans le graphe
     */
    public Graphe(String[] lesMots){
        mot = lesMots.clone();
        nbMot = lesMots.length;
        listeSucc = new Successeur[nbMot];
        dejaVu = new boolean[nbMot];
    }

    /**
     * Retourne le premier succésseur du mot à l'index idx.
     * @param idx Indice du mot
     * @return Premier succésseur
     */
    public Successeur getSuccesseur(int idx) {
        return listeSucc[idx];
    }

    /**
     * Remplace le premier succésseur du mot à l'index idx
     * @param idx Indice du mot
     * @param s Premier succésseur
     */
    public void setSuccesseur(int idx, Successeur s) {
        listeSucc[idx] = s;
    }

    public int getIndex(String str) {
        return 0;
    }

    @Override
    public String toString() {
        String output = "";
        for (int i = 0; i < nbMot; i++) {
            output += i + " : " + mot[i] + " / successeurs : ";
            Successeur s = listeSucc[i];
            while (s != null) {
                output += s.getNoeud() + " - ";
                s = s.getNextNoeuds();
            }
            output += "\n";
        }
        return output;
    }
}
