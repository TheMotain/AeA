package Parser;
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
	private boolean complementaire_reverse;

	/**
	 * Mots à rechercher.
	 */
	private String word;

	public ParametrageParser(){
		resetParam();
		resetWord();
	}

	private void resetWord(){
		word = "";
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
				complementaire_reverse = true;
				break;
			}
		}
	}

	/**
	 * Permet de choisir le mot à chercher
	 * @param word
	 * 	Le mot à chercher.
	 */
	public void setWordToParse(final String word){
		this.word  = word;
	}

	/**
	 * Permet de supprimer le mot à rechercher
	 */
	public void removeWordToParse(){
		resetWord();
	}

	/**
	 * @return word to parse
	 */
	public String getWordToParse() {
		return this.word;
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
	public boolean isComplementaire_reverse() {
		return complementaire_reverse;
	}

	/**
	 * @param complementaire_reverse the complementaire_reverse to set
	 */
	public void setRomplementaire_reverse(
			final boolean complementaire_reverse) {
		this.complementaire_reverse = complementaire_reverse;
	}

	public enum TypeRecherche {
		DIRECT,
		REVERSE,
		COMPLEMENTAIRE,
		COMPLEMENTAIRE_REVERSE;
	}
}
