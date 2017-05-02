package parser;

import exceptions.VertexNotFoundException;
import models.Edge;
import models.GraphImpl;

import java.io.*;

/**
 * Created by ludovicgoldak on 03/04/2017.
 */
public class parser {

    public static GraphImpl FileToGraph(String fileName) throws VertexNotFoundException, IOException {
        GraphImpl Graph = new GraphImpl();
        String line;
        BufferedReader lecteurAvecBuffer = new BufferedReader(new FileReader(fileName));
        while (( line = lecteurAvecBuffer.readLine()) != null){
            String values[]  = line.split(" ");
            int x = Integer.parseInt(values[0]);
            for (int i = 1; i <  values.length; i += 2) {
                int y = Integer.parseInt(values[i]);
                int value = Integer.parseInt(values[i + 1]);
                Graph.addEdge(x,y,value);
            }
        }
        lecteurAvecBuffer.close();
        return  Graph;
    }

    public static String GraphToFile(GraphImpl graph,String Out){
        final File fichier =new File(Out);
        try {
            fichier .createNewFile();
            final FileWriter writer = new FileWriter(fichier);
            try {
                for (int i = 0; i < graph.getCountVertex(); i++) {
                    String Noeux = "";
                    for(int j = 0 ; j < graph.getLinks().size();j++){
                        Edge link = graph.getLinks().get(j);
                        if (link.getSource().getId() == i){
                            Noeux += " " + link.getTarget().getId() + " " + link.getWeight();
                        }
                    }
                    if(Noeux != ""){
                        writer.write(i + Noeux  + "\n");
                    }
                }
            } finally {
                writer.close();
            }
        } catch (Exception e) {
            System.out.println("Impossible de creer le fichier");
        }



        return Out;
    }
}
