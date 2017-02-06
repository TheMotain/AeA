package Parser;

import ADN.SequenceADN;
import Utils.StringUtils;

import javax.management.BadStringOperationException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ludov on 28/01/2017.
 */
public class ParserMorrisEtPratt extends AbstractParser{

	public ParserMorrisEtPratt(final ParametrageParser parametrage, final SequenceADN sequenceADN) {
		super(parametrage,sequenceADN);
	}

	/**
	 * Exécute la recherche du mot passé en paramètre dans la séquence d'ADN chargé.
	 *
	 * @param word
	 * 		Le mot à chercher
	 * @return La liste des positions où se trouve le mot
	 */
	protected List<Integer> run(final String word) {
		final List<Integer> result = new ArrayList<>();

		return result;
	}
}
