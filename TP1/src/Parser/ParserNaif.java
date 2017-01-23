package Parser;

import java.util.ArrayList;

import ADN.SequenceADN;

public class ParserNaif {
	private ParametrageParser parametrageParser;
	private SequenceADN sequenceADN;
	
	public ParserNaif(SequenceADN seq, ParametrageParser params)
	{
		sequenceADN = seq;
		parametrageParser = params;	
	}
	
	public ArrayList<Integer> GetIndexOccurence() 
	{
		ArrayList<Integer> maList = new ArrayList<Integer>();
		
		return maList;
	}
}
