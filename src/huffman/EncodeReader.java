package huffman;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;



/**
 * Handles reading in of source file and building a hashmap of characters to frequencies
 * @author Andrew Langford
 *
 */
public class EncodeReader {	
		
		/**
		 * Reads a file and handles data accordingly
		 * @param fileName the name of the file to be read
		 */
		public static void readFile(String fileName, EncodingHandler dataHandler){
			try{
			
				FileInputStream fileInputStream = new FileInputStream(fileName);
				
				InputStreamReader inputReader = new InputStreamReader(fileInputStream, "UTF-8");
				
				BufferedReader bufferedReader = new BufferedReader(inputReader);
				
				///get current char (as byte)
				int currentChar = bufferedReader.read();

				while(currentChar != -1){
					//if we are building the huffman tree, build a frequency map
		
					dataHandler.handleInput(currentChar);
					currentChar = bufferedReader.read();
				}
				
				bufferedReader.close();
				inputReader.close();
				fileInputStream.close();		
			}
			
			catch(Exception e){
				System.out.println("Error reading file");
				e.printStackTrace();
			}
		}
}
