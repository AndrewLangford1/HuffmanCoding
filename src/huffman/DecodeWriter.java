package huffman;
import java.io.*;
import java.util.ArrayList;


/**
 * Class to read in encoded file and write unenecoded characters to the target file
 * @author Andrew Langford
 *
 */
public class DecodeWriter {
	

		/**
		 * 
		 * Reads source file and decodes it while simultaneously writing to the target file
		 * @param inputFile source file that is encoded 
		 * @param outputFile target file that is decoded
		 * @param tree the huffman tree used to unencrypt characters
		 */
		public static void writeFile(String inputFile, String outputFile, HuffmanTree tree){
			
			try {
				//readers
				FileInputStream fileInput = new FileInputStream(inputFile);
				BufferedInputStream buffIn = new BufferedInputStream(fileInput);
					
				FileWriter fw = new FileWriter(outputFile);
				BufferedWriter dataOut = new BufferedWriter(fw);
				
				
				
				//skip over the header part
				int numChars = buffIn.read();
	
				if(numChars != -1){
					buffIn.skip(numChars *2);
				}
				
				
				//decode file
				int currentByte = buffIn.read();
				
				while(currentByte != -1){
					String asBinary = String.format("%" + 8 +   "s", Long.toBinaryString(currentByte)).replace(' ', '0');
					ArrayList<Integer> toWrite = tree.decode(asBinary);
					
					for(Integer x : toWrite){
			
						dataOut.write(x);
					}
					
					currentByte = buffIn.read();
					
				}
		
				
				//close readers/writers
				buffIn.close();
				dataOut.close();

				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
