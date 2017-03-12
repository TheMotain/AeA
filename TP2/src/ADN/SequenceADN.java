package ADN;

import java.util.ArrayList;
import java.util.List;

public class SequenceADN {
	private String adnSequence;

	public SequenceADN() {
		adnSequence = "";
	}

	public SequenceADN(final String _adnSequence) {
		this.adnSequence = _adnSequence;
	}

	public void append(final String line) {
		adnSequence = adnSequence.concat(line);
	}

	/**
	 * Renvoi la taille de la séquence d'ADN
	 *
	 *
	 * @return taille
	 */
	public int length() {
		return adnSequence.length();
	}

	/**
	 * Renvoi le caractère à la position choisie
	 *
	 * @param index
	 * 		index du caractère
	 * @return le caractère
	 */
	public char charAt(final int index) {
		return adnSequence.charAt(index);
	}

	/**
	 * Permet de réaliser le complémentaire d'une séquence ADN
	 *
	 * @param word
	 * 		séquence d'origine
	 * @return complémentaire
	 */
	public List<String> getComplementaires(final String word) {
		List<String> outputs = new ArrayList<>();
		List<String> l1;
		List<String> l2;
		outputs.add("");
		for (final char c : word.toCharArray()) {
			switch (c) {
				case 'A':
					outputs = ajouter(outputs, 'U');
					break;
				case 'T':
					outputs = ajouter(outputs, 'A');
					break;
				case 'C':
					outputs = ajouter(outputs, 'G');
					break;
				case 'G':
					l1 = ajouter(outputs, 'U');
					l2 = ajouter(outputs, 'C');
					outputs.clear();
					outputs.addAll(l1);
					outputs.addAll(l2);
					break;
				case 'U':
					l1 = ajouter(outputs, 'A');
					l2 = ajouter(outputs, 'G');
					outputs.clear();
					outputs.addAll(l1);
					outputs.addAll(l2);
					break;
				default:
					outputs = ajouter(outputs, c);
			}
		}
		return outputs;
	}

	/**
	 * ajoute à tous les complémentaires trouvé le caractère en paramètre
	 *
	 * @param inputs
	 * 		liste des complémentaires généré
	 * @param a
	 * 		caractère à concaténer
	 * @return nouvelle liste de complémentaires
	 */
	private List<String> ajouter(final List<String> inputs, final char a) {
		final List<String> outputs = new ArrayList<>();
		for (final String input : inputs) {
			outputs.add(input.concat(String.valueOf(a)));
		}
		return outputs;
	}


	/**
	 * Getter de l'attribut {@link SequenceADN#adnSequence}
	 *
	 * @return l'attribut
	 */
	public String getAdnSequence() {
		return adnSequence;
	}

	/**
	 * Setter de l'attribut {@link SequenceADN#adnSequence}
	 *
	 * @param adnSequence
	 * 		l'attribut à setter
	 */
	public void setAdnSequence(final String adnSequence) {
		this.adnSequence = adnSequence;
	}

	/**
	 * Retourne la taille de la séquence d'ADN
	 *
	 * @return la taille
	 */
	public int getSize() {
		return this.adnSequence.length();
	}

	/**
	 * Retourne une séquence aléatoire de taille n présente dans la SequenceADN
	 *
	 * @return Sequence de la SequenceADN
	 */
	public SequenceADN getRandomSequence(final int n) {
		if (n > this.adnSequence.length()) {
			return new SequenceADN();
		}
		final int i = (int) (Math.random() * this.adnSequence.length() - n + 1);
		return new SequenceADN(this.adnSequence.substring(i, i + n));
	}

	/**
	 * Permet de realiser une sous séquence de la séquence d'ADN
	 *
	 * @param i
	 * 		debut de la sous séquence
	 * @param j
	 * 		fin de la sous séquence
	 * @return la sous séquence créée
	 */
	public String substring(int i, final int j) {
		if (i < 0) {
			i = 0;
		}
		if (i > this.length()) {
			return "";
		}
		if (j >= this.length()) {
			return this.adnSequence.substring(i);
		} else {
			return this.adnSequence.substring(i, j);
		}
	}

	/**
	 * Permet de realiser une sous séquence de la séquence d'ADN
	 *
	 * @param i
	 * 		debut de la sous séquence
	 * @return la sous séquence céée
	 */
	public String substring(final int i) {
		return adnSequence.substring(i);
	}
}
