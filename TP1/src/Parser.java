import java.awt.List;
import java.util.ArrayList;


public class Parser {

	/**
	 * paramétrage recheche de mot direct
	 */
	private boolean direct;

	/**
	 * paramétrage recherche de mot reverse
	 */
	private boolean reverse;

	/**
	 * paramétrage recherche de mot complémentaire
	 */
	private boolean complementaire;

	/**
	 * paramétrage recherche de mot complementaire reverse
	 */
	private boolean complementaire_reverse;

	/**
	 * Liste des mots à rechercher.
	 */
	private List<String> words;

	private void resetWords(){
		words = new ArrayList<String>();
	}

	/**
	 * Remet à zero tous les paramétrages
	 */
	private void resetParam(){
		direct = false;
		reverse = false;
		complementaire = false;
		complementaire_reverse = false;
	}

	/**
	 * Set les paramètrages souhaités
	 * @param recherches
	 * 		Suite de paramètres de type {@link TypeRecherche}
	 */
	public void setParam(final TypeRecherche... recherches){
		for(final TypeRecherche type : recherches){
			switch(type){
			case DIRECT : 
				direct = true;
				break;
			case REVERSE : 
				reverse = true;
				break;
			case COMPLEMENTAIRE : 
				complementaire = true;
				break;
			case COMPLEMENTAIRE_REVERSE : 
				complementaire_reverse = true;
				break;
			}
		}
	}

	public void addWordToParse(final String word){
		words.add(word);
	}

	private enum TypeRecherche {
		DIRECT,
		REVERSE,
		COMPLEMENTAIRE,
		COMPLEMENTAIRE_REVERSE;
	}

	public static void main(final String[] args) {

	}
}
