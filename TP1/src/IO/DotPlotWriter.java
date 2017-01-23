package IO;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class DotPlotWriter {

	/**
	 * Liste des positions pour ADN axe des abscices
	 */
	private final List<Integer> indexAbs;

	/**
	 * Liste des positions pour ADN axe des ordonnées
	 */
	private final List<Integer> indexOrd;

	public DotPlotWriter(){
		indexAbs = new ArrayList<Integer>();
		indexOrd = new ArrayList<Integer>();
	}

	public DotPlotWriter(final List<Integer> indicesAbs, final List<Integer> indicesOrd){
		this.indexAbs = indicesAbs;
		this.indexOrd = indicesOrd;
	}

	public void generateDotPlot(final String file) throws FileNotFoundException{
		final PrintWriter writer = new PrintWriter(file);
		writer.write("# x y");
		for(Integer x : indexAbs)
		    for(Integer y : indexOrd)
		        writer.write(String.format("%s %s",x,y));
		writer.close();
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
