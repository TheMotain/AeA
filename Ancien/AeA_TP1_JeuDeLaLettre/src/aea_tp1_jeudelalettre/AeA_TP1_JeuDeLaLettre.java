package aea_tp1_jeudelalettre;

import java.util.ArrayList;

public class AeA_TP1_JeuDeLaLettre {

    
    public static void main(String[] args) {
        Graphe g = new Graphe (Dicos.dico3) ;
        lettreQuiSaute(g);
        visit(g);
        chemin(g,"epi","lit");
    }
    
    // Rajoute s à  la liste des successeurs de d et d à  celle de s
    public static void ajouterArete(Graphe g,int s, int d){
        //System.out.println(g.mots[s] + " et " + g.mots[d]);
        g.listeSucc[s].l.add(d);
        g.listeSucc[d].l.add(s);
    }
    
    public static void lettreQuiSaute(Graphe g){
        //Je compare tous les mots entre eux
        for (int i = 0; i < g.nb; i++) 
            for (int j = 0; j < g.nb; j++)
                if(diffUneLettre(g.mots[i],g.mots[j]))
                    ajouterArete(g,i,j);
    }
    
    public static boolean diffUneLettre(String a, String b){
        // a et b supposees de méme longueur
        int i=0 ;
        //J'avance jusqu'au temps que je trouve une lettre différente
        while (i < a.length() && a.charAt (i) == b.charAt (i)) 
            i++;
        //Si il s'agit du méme mot je retourne faux
        if (i == a.length()) return false;
            i++;//Sinon je passe la lettre différente
        //Je regarde si le reste est égal
        while (i < a.length() && a.charAt (i) == b.charAt (i))
            i++;
        //Si il y a que la lettre que j'avait trouver qui différe je retourne true
        if (i == a.length()) return true ; 
            return false; //Sinon je retourne faux
    } 
    
    public static void DFS(Graphe g, int x){
        if(g.dejaVu[x] == false){
            System.out.print(g.mots[x] + " ");
            g.dejaVu[x] = true;
            for(Integer i : g.listeSucc[x].l) {
                g.pere[i] = x;
                DFS(g,i);
            }
        }
    }
    
    public static void visit(Graphe g){
        for(int i=0;i< g.nb;i++)
            g.dejaVu[i] = false;
        int nbCompo = 0;
        for(int i=0;i<g.nb;i++)
            if(g.dejaVu[i] == false){
                nbCompo ++;
                System.out.print(nbCompo + ": ");
                DFS(g,i);
                System.out.println("");
            }
    }
    
    public static int indice (String m, String[] tabMots) {
        for (int i=0 ; i<tabMots.length ; ++i)
            if (m.equals (tabMots[i])) return i ;
                throw new Error (m + " n'est pas dans le tableau.") ;
    }
    
    
    
    public static void chemin (Graphe g, String from, String to){
        ArrayList<Integer> path = new ArrayList<Integer>();
        int iFrom = indice(from,g.mots);
        int iTo = indice(to,g.mots);
        for(int i=0;i< g.nb;i++) 
            g.dejaVu[i] = false;
        if(getChemin(g,path,iFrom,iTo) != null){
        	for(Integer i : path){
        		System.out.print(g.mots[i] + " ");
        	}
        	System.out.println("");
        }
        else System.out.println("Aucun chemin trouver entre " + from + " et " + to + ".");
    }
    
    public static ArrayList<Integer> getChemin(Graphe g, ArrayList<Integer> path,int actuel, int to){
    	if (actuel == to){ path.add(actuel);	return path;}
    	g.dejaVu[actuel] = true;
    	for(Integer i : g.listeSucc[actuel].l) {
    		if(g.dejaVu[i] == false && getChemin(g,path,i,to) != null){
    			path.add(actuel);return path;
    		}                	
        }
    	return null; 		
    }
   
    
    /* public static void searchComposanteConnexe(Graphe g, String mot){
        int x = 0;
        while (x < g.nb)
            if(g.mots[x].equals(mot)) break;
            else x++;
        if (x == g.nb) System.out.println("Mot : " + mot + " non prÃ©sent dans le graphe !");               
        for(int i=0;i< g.nb;i++)
            g.dejaVu[i] = false;
        int nbCompo = 0;
        for(int i=0;i<g.nb;i++)
            if(g.dejaVu[i] == false){
                nbCompo ++;
                System.out.print(nbCompo + ": ");
                DFS(g,i);
                System.out.println("");
            }
    } */
            
}

