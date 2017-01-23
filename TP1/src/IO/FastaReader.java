package IO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import ADN.SequenceADN;

public class FastaReader {
	private SequenceADN sequence;

	public FastaReader (final String file) throws IOException{
		final BufferedReader reader = new BufferedReader(new FileReader(new File(file)));
		reader.readLine();
		initSequence(reader);
		reader.close();
	}

	private void initSequence(final BufferedReader reader) throws IOException{
		String line = "";
		while((line = reader.readLine()) != null){
			sequence.append(line);
		}
	}

	public SequenceADN getSequence() {
		return sequence;
	}

	public void setSequence(final SequenceADN sequence) {
		this.sequence = sequence;
	}

}
