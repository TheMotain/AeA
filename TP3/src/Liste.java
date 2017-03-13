import java.util.ArrayList;
import java.util.List;

/**
 * Représente une liste d'arrètes de destination pour un graphe orienté <br/>
 *
 * Created by dalencourt on 13/03/17.
 */
public class Liste {
    private int noeud;

    private List<Integer> nextNoeuds;

    public Liste(final int noeud) {
        this.noeud = noeud;
        this.nextNoeuds = new ArrayList<>();
    }

    public int getNoeud() {
        return noeud;
    }

    public void setNoeud(final int noeud) {
        this.noeud = noeud;
    }

    public List<Integer> getNextNoeuds() {
        return nextNoeuds;
    }
}
