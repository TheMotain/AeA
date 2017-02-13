package IO;

import ADN.SequenceADN;
import Parser.ParametrageParser;
import Utils.StringUtils;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class DotPlotWriter {

	/**
	 * Liste des positions pour ADN axe des abscices
	 */
	private final List<Integer> indexAbs;

	/**
	 * Liste des positions pour ADN axe des ordonnées
	 */
	private final List<Integer> indexOrd;

	/**
	 * Liste de tous les indexe pour chaque mot
	 */
	private final Map<String, List<Integer>> mapIndex;

	/**
	 * Constructeur standard
	 */
	public DotPlotWriter() {
		indexAbs = null;
		indexOrd = null;
		mapIndex = null;
	}

	/**
	 * Constructeur avec deux listes de positions une en abscice une en ordonnée afin de comparer les indexes d'un seul
	 * et unique mot
	 *
	 * @param indicesAbs
	 * 		Indexes en abscices
	 * @param indicesOrd
	 * 		Indexes en ordonnés
	 */
	public DotPlotWriter(final List<Integer> indicesAbs, final List<Integer> indicesOrd) {
		this.indexAbs = indicesAbs;
		this.indexOrd = indicesOrd;
		this.mapIndex = null;
	}

	/**
	 * Constructeur qui prend une liste de mots leurs positions.
	 *
	 * @param mapIndex
	 * 		La map de des mots positions
	 */
	public DotPlotWriter(final Map<String, List<Integer>> mapIndex) {
		this.mapIndex = mapIndex;
		this.indexAbs = null;
		this.indexOrd = null;
	}

	/**
	 * Génère le fichier plot pour toutes les occurences pour tous les mots de la map
	 *
	 * @param file
	 * @throws FileNotFoundException
	 */
	public void generateDotPlotMultiWord(final String file, ParametrageParser param, SequenceADN sequence) throws FileNotFoundException {
		final PrintWriter writer = new PrintWriter(file);
		for (final Map.Entry<String, List<Integer>> word : mapIndex.entrySet()) {
			if(param.isDirect())
				writePositions(writer, word.getValue(), word.getValue());
			if(param.isReverse())
				writePositions(writer, word.getValue(), mapIndex.get(StringUtils.reverse(word.getKey())));
			if(param.isComplementaire())
				writePositions(writer, word.getValue(), mapIndex.get(sequence.getComplementaire(word.getKey())));
			if(param.isComplementaire_reverse())
				writePositions(writer, word.getValue(), mapIndex.get(StringUtils.reverse(sequence.getComplementaire(word.getKey()))));
		}
		writer.flush();
		writer.close();
	}

	/**
	 * Génère le fichier plot pour les occurences d'un seul mot
	 *
	 * @param file
	 * 		Fichier où écrire la sortie
	 * @throws FileNotFoundException
	 */
	public void generateDotPlotUniqWord(final String file) throws FileNotFoundException {
		final PrintWriter writer = new PrintWriter(file);
		writePositions(writer, indexAbs, indexOrd);
		writer.flush();
		writer.close();
	}

	/**
	 * Ecrit toutes les positions d'occurences en respectant le format dot plot
	 *
	 * @param writer
	 * 		Le writer d'écriture dans le fichier
	 * @param abs
	 * 		liste des occurences en abscice
	 * @param ord
	 * 		liste des occurences en ordonnées
	 */
	private void writePositions(final PrintWriter writer, final List<Integer> abs, final List<Integer> ord) {
		if(abs == null || ord == null)
			return;
		for (final Integer x : abs) {
			for (final Integer y : ord) {
				writer.write(String.format("%s %s\n", x, y));
			}
		}
	}

	/**
	 * @return the indexAbs
	 */
	public List<Integer> getIndexAbs() {
		return indexAbs;
	}

	/**
	 * @return the indexOrd
	 */
	public List<Integer> getIndexOrd() {
		return indexOrd;
	}
}
