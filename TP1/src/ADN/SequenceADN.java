package ADN;

public class SequenceADN {
	private String adnSequence;

	public SequenceADN(){
		adnSequence = "";
	}

	public void append(final String line) {
		adnSequence = adnSequence.concat(line);
	}

    /**
     * Renvoi la taille de la séquence d'ADN
     * @return taille
     */
	public int length(){
		return adnSequence.length();
	}

    /**
     * Renvoi le caractère à la position choisie
     * @param index index du caractère
     * @return le caractère
     */
	public char charAt(int index){
	    return adnSequence.charAt(index);
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
                    output += 'U';
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
                case 'U' :
                    output += 'A';
                    break;
                default:
                    output += word.charAt(i);
		    }
        }
		return output;
	}


    public String getAdnSequence() {
        return adnSequence;
    }

    public void setAdnSequence(final String adnSequence) {
        this.adnSequence = adnSequence;
    }

}
