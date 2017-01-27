package ADN;

public class SequenceADN {
	private String adnSequence;

	public SequenceADN(){
		adnSequence = "";
	}

	public void append(final String line) {
		adnSequence += line; 
	}

	public String getAdnSequence() {
		return adnSequence;
	}

	public void setAdnSequence(final String adnSequence) {
		this.adnSequence = adnSequence;
	}

}
