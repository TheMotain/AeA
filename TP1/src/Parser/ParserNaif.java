package Parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ADN.SequenceADN;
import Utils.StringUtils;

import javax.management.BadStringOperationException;

/**
 * Parser simple avec l'algorithme Naif sans amélioraton
 * Permet de tester les futurs algorithmes implémenté
 * Created by Ludovic on 28/01/2017.
 */

public class ParserNaif {
	private ParametrageParser parametrage;
	private SequenceADN sequenceADN;

	public ParserNaif(ParametrageParser parametrage, SequenceADN sequenceADN) {
		this.parametrage = parametrage;
		this.sequenceADN = sequenceADN;
	}

	/**
	 * Recherche toutes les occurence du mot dans la séquence d'adn en fonction du paramétrage
	 *
	 * @return la liste de tous les indexes sans doublons.
	 * @throws BadStringOperationException Est retourné si la séquence d'ADN contient des caractère ne se trouvant pas dans l'alphabet
	 */
	public List<Integer> runParser() throws BadStringOperationException {
		Set<Integer> result = new HashSet<>();
		if(parametrage.isDirect())
			result.addAll(run(parametrage.getWordToParse()));
		if(parametrage.isReverse())
			result.addAll(run(StringUtils.reverse(parametrage.getWordToParse())));
		if(parametrage.isComplementaire())
			result.addAll(run(sequenceADN.getComplementaire(parametrage.getWordToParse())));
		if(parametrage.isComplementaire_reverse()){
			result.addAll(run(sequenceADN.getComplementaire(StringUtils.reverse(parametrage.getWordToParse()))));
		}
		return new ArrayList<>(result);
	}

	/**
	 * Exécute la recherche du mot passé en paramètre dans la séquence d'ADN chargé.
	 *
	 * @param word Le mot à chercher
	 * @return La liste des positions où se trouve le mot
	 */
	private List<Integer> run(String word) {
		final List<Integer> result = new ArrayList<>();
		int j;
		for(int i = 0; i < sequenceADN.length(); i++) {
			//Je vérifie si le mot peut passer avant de faire mes tests
			if(word.length() + i >= sequenceADN.length()) break;
			for(j= 0; j < word.length() ; j++) {
				if (sequenceADN.charAt(i + j) != word.charAt(j)) break;
			}
			//Si tout le "for" s'est éxécuter c'est que le mot a était vérifier totalement
			if (j == word.length()) result.add(i);
		}
		return result;
	}
}
