package Utils;

/**
 * Méthodes utilitaires pour la gestion des Strings<br/>
 * <p>
 * Created by dalencourt on 13/03/17.
 */
public class StringUtils {

    /**
     * Regarde si deux chaines ont une seule lettre de différence. On suppose les chaines de taille égales
     * @param a mot 1
     * @param b mot 2
     * @return vrai ou faux
     */
    public static boolean diffUneLettre(String a, String b) {
        // a et b supposees de meme longueur
        int i = 0;
        while (i < a.length() && a.charAt(i) == b.charAt(i)) {
            ++i;
        }
        if (i == a.length()) {
            return false;
        }
        ++i;
        while (i < a.length() && a.charAt(i) == b.charAt(i)) {
            ++i;
        }
        return i == a.length();
    }

    /**
     * Permet de savoir si deux mots on au plus sup suppressions de differences et diff différences de caractères
     * @param origine mot a
     * @param objectif mot b
     * @param sup nombre de suppressions possibles
     * @param diff nombre de différences possibles
     * @return vrai ou faux si les différences sont acceptables
     */
    public static boolean supDiffLettre(String origine,String objectif, int sup, int diff){
        if(Math.abs(origine.length() - objectif.length()) > sup){
            return false;
        }
        // Alignement par récurrence
        return recSupDiff(origine,objectif,sup,diff,0,0,0,0);
    }

    /**
     * Permet de réaliser l'algorithme par récusivité
     * @param origine mot d'origine
     * @param objectif mot à comparrer
     * @param sup nombre de suppressions possible
     * @param diff nombre de différences possible
     * @param origine_idx position du parcours en a actuel
     * @param objectif_idx position du parcours en b actuel
     * @param sup_actuel nombre de suppressions actuel
     * @param diff_actuel nombre de différences actuel
     * @return vrai ou faux
     */
    private static boolean recSupDiff(String origine, String objectif, int sup, int diff,
                                      int origine_idx, int objectif_idx, int sup_actuel, int diff_actuel){
        boolean match = false;
        boolean swap;
        boolean sup_a;

        // Test de finission des parcours
        if(sup_actuel > sup || diff_actuel > diff){
            // si trop de suppressions ou trops de différence chemin refusé
            return false;
        } else if(objectif.length() <= objectif_idx){
            // si l'objectif a été atteint le chemin est accepté
            return true;
        } else if(origine.length() <= origine_idx){
            // sinon si a est arrivé à la fin on regarde si il est possible de faire encore suffisament de suppressions
            return sup_actuel + (objectif.length() - objectif_idx) <= sup;
        }

        // si les parcours ne sont pas fini
        if(origine.charAt(origine_idx) == objectif.charAt(objectif_idx)){
            // si un match et réalisé on déplace les deux curseurs en a et en b
            // il possible que le match soit un faut match il faut donc rechercher aussi les autres cas possibles
            match = recSupDiff(origine,objectif,sup,diff,origine_idx + 1,objectif_idx + 1,sup_actuel,diff_actuel);
        }
        // sinon on test les différents chemins possibles :
        // - différence entre deux caractères
        // - suppression sur le mot a
        swap = recSupDiff(origine,objectif,sup,diff,origine_idx + 1, objectif_idx + 1, sup_actuel,diff_actuel + 1);
        sup_a = recSupDiff(origine,objectif,sup,diff,origine_idx + 1,objectif_idx, sup_actuel + 1, diff_actuel);

        return  swap || sup_a || match;
    }
}
