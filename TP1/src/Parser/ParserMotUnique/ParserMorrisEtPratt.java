package Parser.ParserMotUnique;

import ADN.SequenceADN;
import Parser.ParametrageParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ludov on 28/01/2017.
 */
public class ParserMorrisEtPratt extends AbstractParser {

	public ParserMorrisEtPratt(final ParametrageParser parametrage, final SequenceADN sequenceADN) {
		super(parametrage, sequenceADN);
	}

	/**
	 * Exécute la recherche du mot passé en paramètre dans la séquence d'ADN chargé.
	 *
	 * @param word
	 * 		Le mot à chercher
	 * @return La liste des positions où se trouve le mot
	 */
	@Override
	protected List<Integer> run(final String word) {
		final List<Integer> result = new ArrayList<>();

		return result;
	}
}
