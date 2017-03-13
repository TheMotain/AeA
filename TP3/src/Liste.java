/**
 * Représente une liste d'arrètes de destination pour un graphe orienté <br/>
 *
 * Created by dalencourt on 13/03/17.
 */
public class Liste {
    private int noeud;

    private Liste nextNoeud;

    public Liste(final int noeud) {
        this.noeud = noeud;
        this.nextNoeud = null;
    }

    public int getNoeud() {
        return noeud;
    }

    public void setNoeud(final int noeud) {
        this.noeud = noeud;
    }

    public Liste getNextNoeud() {
        return nextNoeud;
    }

    public void setNextNoeud(final Liste nextNoeud) {
        this.nextNoeud = nextNoeud;
    }
}
