package huffman;

import java.util.HashMap;


/**
 * Class to handle all IO for endodings
 * @author Andrew Langford
 *
 */
public class EncodingHandler {
	
	//hashmap to hold the frequency of characters
	private HashMap<Integer,Integer> frequencyMap;
	
	/**
	 * main constructor
	 */
	public EncodingHandler(){
		this.frequencyMap = new HashMap<Integer, Integer>();
		this.frequencyMap.put(0x000, 1);
	}
	
	
	/**
	 * Reads text file and handles it
	 * @param fileName source file to read in
	 */
	public void readInFile(String fileName){
		EncodeReader.readFile(fileName, this);	
	}
	
	
	/**
	 * returns the frequency map
	 * @return the frequencyMap
	 */
	public HashMap<Integer, Integer> getFrequencyMap() {
		return frequencyMap;
	}
	
	
	/**
	 * Handles building a hashmap with for the frequencies of characters.
	 * @param currentChar current char we are reading in
	 */
	public void handleInput(int currentChar){
		
		int frequency = 0;
		
		if(frequencyMap.containsKey( currentChar))
		   frequency = frequencyMap.get(currentChar);
		
		frequencyMap.put(currentChar, ++frequency);
	}
}
