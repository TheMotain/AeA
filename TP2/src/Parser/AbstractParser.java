package Parser;

import ADN.SequenceADN;

/**
 * Contient les méthodes communes à tous les parsers
 * Created by alex on 30/01/17.
 */
public abstract class AbstractParser {

	protected final SequenceADN sequenceADN;

	public AbstractParser(final SequenceADN sequenceADN) {
		this.sequenceADN = sequenceADN;
	}

	/**
	 * Etablit un tableau de matching des preARM
	 *
	 * @return tableau produit
	 */
	public abstract char[] runParser();
}
