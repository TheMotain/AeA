package Parser;

import ADN.SequenceADN;

import javax.management.BadStringOperationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Parser selon la méthode Shift - Or
 * Created by alex on 23/01/17.
 */
public class ParserShiftOr {
	/**
	 * Alphabet des caractères possibles
	 */
	private static final char[] alphabet = {'A', 'C', 'G', 'T', 'U'};

	/**
	 * Séquence ADN à parser
	 */
	private final SequenceADN sequenceADN;

	/**
	 * Constructeur
	 *
	 * @param sequenceADN
	 * 		Séquence ADN à utiliser
	 */
	public ParserShiftOr(final SequenceADN sequenceADN) {
		this.sequenceADN = sequenceADN;
	}

	/**
	 * Methode pour exécuter le parser
	 *
	 * @param word
	 * 		Mot à trouver
	 * @return Liste des indexs où se trouve le mot cherché
	 * @throws BadStringOperationException
	 * 		Est remontée si une la sequence d'ADN contient des caractères non attendus
	 */
	protected List<Integer> run(final String word) throws BadStringOperationException {
		final int[][] matriceB = generateMatriceB(word);
		final int[][] matriceOccur = new int[sequenceADN.length()][word.length()];

		final int[] currentCharacter = new int[word.length()];
		// Initialisation du traitement
		for (int i = 0; i < word.length(); i++) {
			currentCharacter[i] = 0;
		}
		if (sequenceADN.charAt(0) == word.charAt(0)) {
			currentCharacter[0] += 0b1;
		}
		System.arraycopy(currentCharacter, 0, matriceOccur[0], 0, matriceOccur[0].length);

		generateMatriceOccurence(matriceB, matriceOccur, currentCharacter);

		return recuperationIndices(word, matriceOccur);
	}

	/**
	 * Génère la matrice d'occurences
	 *
	 * @param matriceB
	 * 		Matrice de référence
	 * @param matriceOccur
	 * 		Matrice finale des occurences
	 * @param currentCharacter
	 * 		Matrice du caractère en cours de lecture
	 * @throws BadStringOperationException
	 * 		Est remontée si une la sequence d'ADN contient des caractères non attendus
	 */
	private void generateMatriceOccurence(final int[][] matriceB, final int[][] matriceOccur,
	                                      final int[] currentCharacter) throws
			BadStringOperationException {
		for (int i = 1; i < sequenceADN.length(); i++) {
			System.arraycopy(currentCharacter, 0, currentCharacter, 1, currentCharacter.length - 1);
			currentCharacter[0] = 0b1;

			// récupération de la matrice B
			int z;
			for (z = 0; z < alphabet.length; z++) {
				if (alphabet[z] == sequenceADN.charAt(i)) {
					break;
				}
			}
			if (z >= alphabet.length) {
				throw new BadStringOperationException("Unknow caractere");
			}

			for (int j = 0; j < currentCharacter.length; j++) {
				currentCharacter[j] &= matriceB[z][j];
			}
			System.arraycopy(currentCharacter, 0, matriceOccur[i], 0, matriceOccur[i].length);
		}
	}

	/**
	 * Construit la liste des indices d'occurence à partir de la matrice qui a été générée
	 *
	 * @param word
	 * 		Mot à reconstruire
	 * @param matriceOccur
	 * 		Matrice des occurences
	 * @return liste des indices trouvé
	 */
	private List<Integer> recuperationIndices(final String word, final int[][] matriceOccur) {
		final List<Integer> result = new ArrayList<>();
		initial:
		for (int i = 0; i < sequenceADN.length() - word.length(); i++) {
			if (matriceOccur[i][0] == 0b1) {
				int y = i + 1;
				int z = 1;
				while (z < word.length()) {
					if (matriceOccur[y][z] != 0b1) {
						continue initial;
					}
					z++;
					y++;
				}
				result.add(i);
			}
		}
		return result;
	}

	/**
	 * Permet de générer la matrice mot à chercher et index dans l'alphabet
	 *
	 * @param word
	 * 		Mot à chercher
	 * @return matrice
	 */
	private int[][] generateMatriceB(final String word) {
		final int matriceB[][] = new int[alphabet.length][word.length()];
		for (int i = 0; i < alphabet.length; i++) {
			for (int j = 0; j < word.length(); j++) {
				matriceB[i][j] = word.charAt(j) == alphabet[i] ? 0b1 : 0;
			}
		}
		return matriceB;
	}
}
