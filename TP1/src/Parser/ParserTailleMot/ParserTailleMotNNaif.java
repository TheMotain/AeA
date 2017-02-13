package Parser.ParserTailleMot;

import ADN.SequenceADN;
import Parser.ParametrageParser;
import Utils.StringUtils;
import java.util.*;

/**
 * Parser Naif qui retourne la liste de tous les mots de taille N dans la chaine
 * Created by alex on 30/01/17.
 */
public class ParserTailleMotNNaif {
	/**
	 * Séquenc d'ADN à lire
	 */
	private final SequenceADN sequenceADN;

	/**
	 * Paramétrage du parser
	 */
	private final ParametrageParser parametrage;

	public ParserTailleMotNNaif(final SequenceADN sequenceADN, final ParametrageParser parametrage) {
		this.sequenceADN = sequenceADN;
		this.parametrage = parametrage;
	}

	public Map<String, List<Integer>> run(final int n) {
		final Map<String, List<Integer>> result = new HashMap<>();
		for (int i = 0; i < sequenceADN.length(); i++) {
			//Je vérifie si le mot peut passer avant de faire mes tests
			if (n + i >= sequenceADN.length()) {
				break;
			}
			final String word = sequenceADN.getAdnSequence().substring(i, i + n);
			if (!result.containsKey(word)) {
				result.put(word, new ArrayList<>());
			}
			result.get(word).add(i);
		}

		return fusionMotsIdentique(result);
	}

	/**
	 * Fusionne les mots identiques selon le parametrage
	 *
	 * @param result
	 * 		résultat des occurences de mot trouvé
	 * @return résultat à renvoyer
	 */
	private Map<String, List<Integer>> fusionMotsIdentique(final Map<String, List<Integer>> result) {
		final Iterator<Map.Entry<String, List<Integer>>> it = result.entrySet().iterator();
		final List<String> motTraite = new ArrayList<String>();
		final Map<String, List<Integer>> output = new HashMap<String, List<Integer>>();
		while (it.hasNext()) {
			final Map.Entry<String, List<Integer>> entry = it.next();
			it.remove();
			final List<Integer> positions = new ArrayList<Integer>();
			String keyDirect = null, keyReverse = null, keyComp = null, keyCompRevse = null;

			if (!motTraite.contains(entry.getKey())) {
				motTraite.add(entry.getKey());
				String keyUsed = null;
				if (parametrage.isDirect()) {
					keyDirect = entry.getKey();
					keyUsed = output.containsKey(keyDirect) ? keyDirect : null;
					positions.addAll(entry.getValue());
				}
				if (parametrage.isReverse()) {
					if (result.containsKey(StringUtils.reverse(entry.getKey()))) {
						keyReverse = StringUtils.reverse(entry.getKey());
						positions.addAll(result.get(keyReverse));
						motTraite.add(keyReverse);
					}
					if (keyUsed == null) {
						keyUsed = output.containsKey(keyReverse) ? keyReverse : null;
					}
				}
				if (parametrage.isComplementaire()) {
					if (result.containsKey(sequenceADN.getComplementaire(entry.getKey()))) {
						keyComp = sequenceADN.getComplementaire(entry.getKey());
						positions.addAll(result.get(keyComp));
						motTraite.add(keyComp);
					}
					if (keyUsed == null) {
						keyUsed = output.containsKey(keyComp) ? keyComp : null;
					}
				}
				if (parametrage.isComplementaire_reverse()) {
					if (result.containsKey(sequenceADN.getComplementaire(StringUtils.reverse(entry.getKey())))) {
						keyCompRevse = sequenceADN.getComplementaire(StringUtils.reverse(entry.getKey()));
						positions
								.addAll(result.get(sequenceADN.getComplementaire(StringUtils.reverse(entry.getKey())
								)));

						motTraite.add(sequenceADN.getComplementaire(StringUtils.reverse(entry.getKey())));
					}
					if (keyUsed == null) {
						keyUsed = output.containsKey(keyCompRevse) ? keyCompRevse : null;
					}
				}
				if (keyUsed == null) {
					keyUsed = keyDirect != null ? keyDirect :
							keyReverse != null ? keyReverse : keyComp != null ? keyComp : keyCompRevse;
				}
				if (keyUsed != null) {
					output.put(keyUsed, new ArrayList<Integer>(positions));
				}
			}
		}
		return output;
	}

}
