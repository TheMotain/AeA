
package aea_tp1_jeudelalettre;

public class Graphe {
    public String[] mots;
    public Liste[] listeSucc;
    public int nb;
    public boolean[] dejaVu;
    public int[] pere;
    
    public Graphe(String[] lesMots){
        nb = lesMots.length;
        mots = new String[nb];
        listeSucc = new Liste[nb];
        dejaVu = new boolean[nb];
        pere = new int[nb];
        for(int i=0;i<nb; i++)
        {
            mots[i] = lesMots[i];
            listeSucc[i] = new Liste();
        }
    }
}
