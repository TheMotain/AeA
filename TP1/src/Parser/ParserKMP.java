package Parser;

import ADN.SequenceADN;
import Utils.StringUtils;

import javax.management.BadStringOperationException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ludov on 29/01/2017.
 * Algorithme de Knuth, Morris et Pratt(1975)
 */
public class ParserKMP {
	private final ParametrageParser parametrage;
	private final SequenceADN sequenceADN;

	public ParserKMP(final ParametrageParser parametrage, final SequenceADN sequenceADN) {
		this.parametrage = parametrage;
		this.sequenceADN = sequenceADN;
	}

	/**
	 * Recherche toutes les occurence du mot dans la séquence d'adn en fonction du paramétrage
	 *
	 * @return la liste de tous les indexes sans doublons.
	 *
	 * @throws BadStringOperationException
	 * 		Est retourné si la séquence d'ADN contient des caractère ne se trouvant pas dans l'alphabet
	 */
	public List<Integer> runParser() throws BadStringOperationException {
		final Set<Integer> result = new HashSet<>();
		if (parametrage.isDirect())
			result.addAll(run(parametrage.getWordToParse()));
		if (parametrage.isReverse())
			result.addAll(run(StringUtils.reverse(parametrage.getWordToParse())));
		if (parametrage.isComplementaire())
			result.addAll(run(sequenceADN.getComplementaire(parametrage.getWordToParse())));
		if (parametrage.isComplementaire_reverse()) {
			result.addAll(run(sequenceADN.getComplementaire(StringUtils.reverse(parametrage.getWordToParse())
			)));
		}
		return new ArrayList<>(result);
	}

	/**
	 * Exécute la recherche du mot passé en paramètre dans la séquence d'ADN chargé.
	 *
	 * @param word
	 * 		Le mot à chercher
	 * @return La liste des positions où se trouve le mot
	 */
	private List<Integer> run(final String word) {
		final List<Integer> result = new ArrayList<>();

		return result;
	}
}
