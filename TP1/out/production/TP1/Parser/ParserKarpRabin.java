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
public class ParserKarpRabin {
	private final ParametrageParser parametrage;
	private final SequenceADN sequenceADN;

	public ParserKarpRabin(final ParametrageParser parametrage, final SequenceADN sequenceADN) {
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
		final int hachageValueWord = getHachageValue(word);
		final String wordTest = "";
		int j = 0;
		for (int i = 0; i < sequenceADN.length(); i++) {
			//Je vérifie si le mot peut passer avant de faire mes tests
			if (word.length() + i >= sequenceADN.length()) break;
			if (getHachageValue(sequenceADN.getAdnSequence().substring(i, i + word.length())) == hachageValueWord) {
				for (j = 0; j < word.length(); j++) {
					if (sequenceADN.charAt(i + j) != word.charAt(j)) break;
				}
				//Si tout le "for" s'est éxécuter c'est que le mot a était vérifier totalement
				if (j == word.length()) result.add(i);
			}
		}
		return result;
	}

	/**
	 * Permet d'executer la fonction "h" permettant d'obtenir la valeur de hachage de la chaine
	 *
	 * @param word
	 * 		la chaine à calculer
	 * @return la valeur de hachage de la chaine word
	 */
	private int getHachageValue(final String word) {
		int result = 0;
		for (int i = 0; i < word.length(); i++) {
			result += IdAlphabet(word.charAt(i)) * Math.pow(5, word.length() - 1 - i);
		}
		return result;
	}

	/**
	 * Permet de travailler avec des nombres plutôt qu'avec des char , indispensable pour la fonction de hachage
	 *
	 * @param aChar
	 * 		le char à chercher
	 * @return la valeur du char dans notre alphabet
	 */
	private int IdAlphabet(final char aChar) {
		switch (aChar) {
			case 'A':
				return 1;
			case 'C':
				return 2;
			case 'G':
				return 3;
			case 'T':
				return 4;
			case 'U':
				return 5;
			default:
				return 0;
		}
	}
}
