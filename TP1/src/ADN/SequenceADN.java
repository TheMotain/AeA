package ADN;

public class SequenceADN {
	private String adnSequence;

	public SequenceADN(){
		adnSequence = "";
	}

	public SequenceADN(final String adn){
		adnSequence = adn;
	}

	/**
	 * Concatène la ligne à la séquence déjà existante
	 * @param line
	 * ligne à concaténer.
	 */
	public void append(final String line) {
		adnSequence = adnSequence.concat(line);
	}

	public String getAdnSequence() {
		return adnSequence;
	}

	public void setAdnSequence(final String adnSequence) {
		this.adnSequence = adnSequence;
	}

	/**
	 * Permet de réaliser le complémentaire d'une séquence ADN
	 * @param word séquence d'origine
	 * @return complémentaire
	 */
	public String getComplementaire(String word) {
		String output = "";
		for(int i = 0; i < word.length(); i++){
		    switch(word.charAt(i)){
		        case 'A' :
                    output += 'T';
                    break;
                case 'T' :
                    output += 'A';
                    break;
                case 'C' :
                    output += 'G';
                    break;
                case 'G' :
                    output += 'C';
                    break;
                default:
                    output += word.charAt(i);
		    }
        }
		return output;
	}
}
