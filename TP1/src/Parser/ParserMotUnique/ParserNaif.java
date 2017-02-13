package Parser.ParserMotUnique;

import ADN.SequenceADN;
import Parser.ParametrageParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser simple avec l'algorithme Naif sans amélioraton
 * Permet de tester les futurs algorithmes implémenté
 * Created by Ludovic on 28/01/2017.
 */

public class ParserNaif extends AbstractParser {

	public ParserNaif(final ParametrageParser parametrage, final SequenceADN sequenceADN) {
		super(parametrage, sequenceADN);
	}

	@Override
	protected List<Integer> run(final String word) {
		final List<Integer> result = new ArrayList<>();
		int j;
		for (int i = 0; i < sequenceADN.length(); i++) {
			//Je vérifie si le mot peut passer avant de faire mes tests
			if (word.length() + i >= sequenceADN.length()) {
				break;
			}
			for (j = 0; j < word.length(); j++) {
				if (sequenceADN.charAt(i + j) != word.charAt(j)) {
					break;
				}
			}
			//Si tout le "for" s'est éxécuter c'est que le mot a était vérifier totalement
			if (j == word.length()) {
				result.add(i);
			}
		}
		return result;
	}
}
