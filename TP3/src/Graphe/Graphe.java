package Graphe;

/**
 * Représente la liste des mots avec les liaisons associées<br/>
 * Created by dalencourt on 13/03/17.
 */
public class Graphe {
    /** Liste chainé des succésseur */
    private Successeur listeSucc[];
    /** Tableau des mots présent dans le graph */
    private String mot[];
    /** Le nombre de mot présent dans le graph */
    private int nbMot = 0;
    /** Permet de savoir si un sommet à déjà etait visité */
    private boolean dejaVu[];

    /**
     * Getter de l'attribut {@link Graphe#mot}
     *
     * @return Table des mots
     */
    public String[] getMot() {
        return mot;
    }

    /**
     * Constructeur
     * @param lesMots Liste des mots à mettre dans le graphe
     */
    public Graphe(String[] lesMots){
        mot = lesMots.clone();
        nbMot = lesMots.length;
        listeSucc = new Successeur[nbMot];
        dejaVu = new boolean[nbMot];
        for (int i = 0; i < dejaVu.length; i++) dejaVu[i] = false;
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
     * Permet de savoir si s est un succésseur de idx
     * @param idx le noeud à evaluer
     * @param s le succésseur possible à chercher
     * @return Oui/Non
     */
    public boolean isSuccesseur(int idx,int s){
        Successeur next = getSuccesseur(idx);
        while (next != null){
            if (next.getNoeud() == s) return true;
            next = next.getNextNoeuds();
        }
        return false;
    }

    /**
     * Remplace le premier succésseur du mot à l'index idx
     * @param idx Indice du mot
     * @param s Premier succésseur
     */
    public void setSuccesseur(int idx, Successeur s) {
        listeSucc[idx] = s;
    }

    /**
     * Renvoi l'index du mot présent dans le tableau de mot
     * @param str le mot à chercher
     * @return l'index du mot
     */
    public int getIndex(String str) {
        for (int i = 0; i < mot.length; i++) {
            if (mot[i] == str) return i;
        }
        return 0;
    }

    /**
     * Affiche tous les mots du graphe avec leur succésseurs
     * @return la chaine à afficher
     */
    public boolean dejaVu(int idx) {
        return this.dejaVu[idx];
    }

    public String parcour(int idx) {
        this.dejaVu[idx] = true;
        return this.mot[idx];
    }

    public void resetParcour() {
        this.dejaVu = new boolean[this.nbMot];
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
