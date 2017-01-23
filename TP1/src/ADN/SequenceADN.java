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
		adnSequence.concat(line);
	}

	public String getAdnSequence() {
		return adnSequence;
	}

	public void setAdnSequence(final String adnSequence) {
		this.adnSequence = adnSequence;
	}

}
