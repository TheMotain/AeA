package Graphe;

/**
 * Représente une liste d'arrètes de destination pour un graphe orienté <br/>
 *
 * Created by dalencourt on 13/03/17.
 */
public class Successeur {
    private int noeud;

    private Successeur nextNoeuds;

    public Successeur(final int noeud) {
        this.noeud = noeud;
        this.nextNoeuds = null;
    }

    public Successeur(final int noeud, final Successeur succ) {
        this(noeud);
        this.nextNoeuds = succ;
    }

    public int getNoeud() {
        return noeud;
    }

    public void setNoeud(final int noeud) {
        this.noeud = noeud;
    }

    public Successeur getNextNoeuds() {
        return nextNoeuds;
    }

    public void setNextNoeuds(final Successeur nextNoeuds) {
        this.nextNoeuds = nextNoeuds;
    }
}
