package IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
	private final Map<String,List<Integer>> mapIndex;

	public DotPlotWriter() {
		indexAbs = null;
		indexOrd = null;
		mapIndex = null;
	}

	public DotPlotWriter(final List<Integer> indicesAbs, final List<Integer> indicesOrd) {
		this.indexAbs = indicesAbs;
		this.indexOrd = indicesOrd;
		this.mapIndex = null;
	}

    public DotPlotWriter(final Map<String, List<Integer>> mapIndex) {
        this.mapIndex = mapIndex;
        this.indexAbs = null;
        this.indexOrd = null;
	}

    /**
     * Génère le fichier plot pour toutes les occurences pour tous les mots de la map
     * @param file
     * @throws FileNotFoundException
     */
	public void generateDotPlotMultiWord(final String file) throws FileNotFoundException {
	    final PrintWriter writer = new PrintWriter(file);
        for(Map.Entry<String,List<Integer>> word : mapIndex.entrySet()){
            writePositions(writer,word.getValue(),word.getValue());
        }
        writer.flush();
	    writer.close();
	}

    /**
     * Génère le fichier plot pour les occurences d'un seul mot
     * @param file
     *  Fichier où écrire la sortie
     * @throws FileNotFoundException
     */
    public void generateDotPlotUniqWord(final String file) throws FileNotFoundException {
		final PrintWriter writer = new PrintWriter(file);
        writePositions(writer,indexAbs,indexOrd);
        writer.flush();
        writer.close();
	}

    /**
     * Ecrit toutes les positions d'occurences en respectant le format dot plot
     * @param writer
     *  Le writer d'écriture dans le fichier
     * @param abs liste des occurences en abscice
     * @param ord liste des occurences en ordonnées
     */
    private void writePositions(final PrintWriter writer, final List<Integer> abs, final List<Integer> ord) {
        for (final Integer x : abs)
            for (final Integer y : ord)
                writer.write(String.format("%s %s\n", x, y));
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
