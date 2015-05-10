package huffman;
import java.io.*;
import java.util.HashMap;

/**
 * Class to read in file and  build huffman table
 * @author Andrew Langford
 *
 */
public class DecodeReader {
	
	
	/**
	 * reads an encoded source file and generates the huffman tablr
	 * @param fileName the name of the source file that is encoded
	 * @return a map of characters to their code lengths
	 */
	public static HashMap<Integer, Integer> readFile(String fileName){
		HashMap<Integer, Integer> codes = new HashMap<Integer, Integer>();
		
		try {
			
			//readers
			FileInputStream fileInput = new FileInputStream(fileName);
			BufferedInputStream buffIn = new BufferedInputStream(fileInput);
			
			//build table
			int numChars = buffIn.read();
			if(numChars != -1){
				for(int i =0; i < numChars; i++){
					int theCharacter = buffIn.read();
					
					int theCodeLength = buffIn.read();
					
					if(theCharacter != -1 && theCodeLength != -1){
						codes.put(theCharacter, theCodeLength);
					}
					
				}
			}
			

			//close reader
			buffIn.close();
		
			return codes;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
