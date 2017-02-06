package Parser.ParserMotUnique;

import ADN.SequenceADN;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ludov on 29/01/2017.
 * Algorithme de Knuth, Morris et Pratt(1975)
 */
public class ParserKMP extends AbstractParser {

	public ParserKMP(final ParametrageParser parametrage, final SequenceADN sequenceADN) {
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
