package Graphe;

/**
 * Représente une liste d'arrètes de destination pour un graphe orienté <br/>
 *
 * Created by dalencourt on 13/03/17.
 */
public class Successeur {
    /**
     * Noeud d'orrigine
     */
    private int noeud;

    /**
     * Successeur du noeud ou null
     */
    private Successeur nextNoeuds;

    /**
     * Constructeur
     * @param noeud Noeud d'origine
     */
    public Successeur(final int noeud) {
        this.noeud = noeud;
        this.nextNoeuds = null;
    }

    /**
     * Constructeur
     * @param noeud noeud d'origine
     * @param succ successeur du noeud
     */
    public Successeur(final int noeud, final Successeur succ) {
        this(noeud);
        this.nextNoeuds = succ;
    }

    /**
     * @return the noeud
     * @see Successeur#noeud
     */
    public int getNoeud() {
        return noeud;
    }

    /**
     * @param noeud the noeud
     * @see Successeur#noeud
     */
    public void setNoeud(final int noeud) {
        this.noeud = noeud;
    }

    /**
     * @return the nextNoeuds
     * @see Successeur#nextNoeuds
     */
    public Successeur getNextNoeuds() {
        return nextNoeuds;
    }

    /**
     * @param nextNoeuds the nextNoeuds
     * @see Successeur#nextNoeuds
     */
    public void setNextNoeuds(final Successeur nextNoeuds) {
        this.nextNoeuds = nextNoeuds;
    }
}
