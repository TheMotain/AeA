package Parser;
import java.util.ArrayList;
import java.util.List;


/**
 * Permet de paramètrer le parser
 * 
 */
public class ParametrageParser {

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
	private boolean reverse_complementaire_reverse;

	/**
	 * Liste des mots à rechercher.
	 */
	private List<String> words;

	public ParametrageParser(){
		resetParam();
		resetWords();
	}

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
		reverse_complementaire_reverse = false;
	}

	/**
	 * Set les paramètrages souhaités
	 * @param recherches
	 * 		Suite de paramètres de type {@link TypeRecherche}
	 */
	public void setParam(final TypeRecherche... recherches){
		resetParam();
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
				reverse_complementaire_reverse = true;
				break;
			}
		}
	}

	/**
	 * Permet d'ajouter un mot à la liste des mots à chercher
	 * @param word
	 * 	Le mot à chercher.
	 */
	public void addWordToParse(final String word){
		words.add(word);
	}

	/**
	 * Permet de supprimer un mot de la liste
	 * @param word
	 * Le Mot à supprimer
	 */
	public void removeWordToParse(final String word){
		words.remove(word);
	}

	/**
	 * @return the direct
	 */
	public boolean isDirect() {
		return direct;
	}

	/**
	 * @param direct the direct to set
	 */
	public void setDirect(final boolean direct) {
		this.direct = direct;
	}

	/**
	 * @return the reverse
	 */
	public boolean isReverse() {
		return reverse;
	}

	/**
	 * @param reverse the reverse to set
	 */
	public void setReverse(final boolean reverse) {
		this.reverse = reverse;
	}

	/**
	 * @return the complementaire
	 */
	public boolean isComplementaire() {
		return complementaire;
	}

	/**
	 * @param complementaire the complementaire to set
	 */
	public void setComplementaire(final boolean complementaire) {
		this.complementaire = complementaire;
	}

	/**
	 * @return the reverse_complementaire_reverse
	 */
	public boolean isReverse_complementaire_reverse() {
		return reverse_complementaire_reverse;
	}

	/**
	 * @param reverse_complementaire_reverse the reverse_complementaire_reverse to set
	 */
	public void setReverse_complementaire_reverse(
			final boolean reverse_complementaire_reverse) {
		this.reverse_complementaire_reverse = reverse_complementaire_reverse;
	}

	/**
	 * @return the words
	 */
	public List<String> getWords() {
		return words;
	}

	/**
	 * @param words the words to set
	 */
	public void setWords(final List<String> words) {
		this.words = words;
	}



	public enum TypeRecherche {
		DIRECT,
		REVERSE,
		COMPLEMENTAIRE,
		COMPLEMENTAIRE_REVERSE;
	}
}
