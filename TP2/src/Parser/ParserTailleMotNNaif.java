package Parser;

import ADN.SequenceADN;
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

	public ParserTailleMotNNaif(final SequenceADN sequenceADN) {
		this.sequenceADN = sequenceADN;
	}


	public static void main(String[] arg) {
		SequenceADN sequenceADN = new SequenceADN();
		//		sequenceADN.setAdnSequence("ACGUAGAAACCCCCGUAAUAUGUGCACAUAUUACGGGGGUUUCUACGU");
		//		sequenceADN.setAdnSequence("UAAUAUGUGACGCCCACAUAUUA");
		sequenceADN.setAdnSequence("ACGUAGAAACCCCCGUAAUAUGUGACGCCCACAUAUUACGGGGGUUUCUACGU");
		ParserTailleMotNNaif parser = new ParserTailleMotNNaif(sequenceADN);
		Map<String, List<Integer>> indexs = parser.run(3);
		boolean[] match = new boolean[sequenceADN.getSize()];
		for (int i = 0; i < match.length; i++) {
			match[i] = false;
		}
		char[] apparaiment = new char[sequenceADN.getSize()];
		List<Integer> blackList = new ArrayList<Integer>();

		for (Map.Entry<String, List<Integer>> listIndex : indexs.entrySet()) {
			List<Integer> positionDirect = listIndex.getValue();
			List<Integer> positionComplementaire = new ArrayList<>();
			for (String rc : sequenceADN.getComplementaires(StringUtils.reverse(listIndex.getKey()))) {
				if (indexs.containsKey(rc)) {
					for (Integer position : indexs.get(rc)) {
						if (!positionDirect.contains(position)) {
							positionComplementaire.add(position);
						}
					}
				}
			}

			Collections.sort(positionDirect, new Comparator<Integer>() {
				@Override
				public int compare(Integer o1, Integer o2) {
					return o1 - o2;
				}
			});
			Collections.sort(positionComplementaire, new Comparator<Integer>() {
				@Override
				public int compare(Integer o1, Integer o2) {
					return o2 - o1;
				}
			});
			for (Integer position : positionDirect) {
				if (!positionComplementaire.isEmpty() && !blackList.contains(position)) {
					match[position] |= true;
					match[position + 1] |= true;
					match[position + 2] |= true;
					match[positionComplementaire.get(0)] |= true;
					match[positionComplementaire.get(0) + 1] |= true;
					match[positionComplementaire.get(0) + 2] |= true;
					blackList.add(positionComplementaire.remove(0));
					blackList.add(position);
				}
			}
		}
		System.out.println(sequenceADN.getAdnSequence());
		for (int i = 0; i < match.length; i++) {
			if (match[i]) {
				System.out.print("|");
			} else {
				System.out.print("-");
			}
		}
		System.out.println();
	}

	public Map<String, List<Integer>> run(final int n) {
		final Map<String, List<Integer>> result = new LinkedHashMap<>();
		for (int i = 0; i < sequenceADN.length(); i++) {
			//Je vérifie si le mot peut passer avant de faire mes tests
			if (n + i > sequenceADN.length()) {
				break;
			}
			final String word = sequenceADN.getAdnSequence().substring(i, i + n);
			if (!result.containsKey(word)) {
				result.put(word, new ArrayList<>());
			}
			result.get(word).add(i);
		}

		return result;
	}
}
