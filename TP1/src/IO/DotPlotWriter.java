package IO;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class DotPlotWriter {

	/**
	 * Liste des positions pour ADN axe des abscices
	 */
	private final List<Integer> indexeAbs;

	/**
	 * Liste des positions pour ADN axe des ordonnées
	 */
	private final List<Integer> indexeOrd;

	public DotPlotWriter(){
		indexeAbs = new ArrayList<Integer>();
		indexeOrd = new ArrayList<Integer>();
	}

	public DotPlotWriter(final List<Integer> indicesAbs, final List<Integer> indicesOrd){
		this.indexeAbs = indicesAbs;
		this.indexeOrd = indicesOrd;
	}

	public void generateDotPlot(final String file) throws FileNotFoundException{
		final PrintWriter writer = new PrintWriter(file);
		writer.write("x y");
		for(Integer x : indexeAbs)
		    for(Integer y : indexeOrd)
		        writer.write(String.format("%s %s",x,y));
		writer.close();
	}

	/**
	 * @return the indexeAbs
	 */
	public List<Integer> getIndexeAbs() {
		return indexeAbs;
	}

	/**
	 * @return the indexeOrd
	 */
	public List<Integer> getIndexeOrd() {
		return indexeOrd;
	}
}
