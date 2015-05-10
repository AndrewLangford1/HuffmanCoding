package huffman;
import java.io.*;


import java.util.HashMap;
import java.util.Map.Entry;


/**
 * Class to Write encoded bytes to a target file
 * @author Andrew Langford
 *
 */
public class EncodeWriter {
	
	
	
	/**
	 * Encodes source file and writes to target file
	 *  
	 * @param source the source file we want to encode
	 * @param target the target file we want to write to 
	 * @param codeMap the hashmap of code lookups
	 */
	public static void writeEncodedFile(String source, String target, HashMap<Integer, String> codeMap){
		try{
		
			//writers	
			FileOutputStream fileOut = new FileOutputStream(target);
			BufferedOutputStream buffOut = new BufferedOutputStream(fileOut);
			
			//readers
			FileInputStream fileInputStream = new FileInputStream(source);
			InputStreamReader inputReader = new InputStreamReader(fileInputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputReader);
			
			
			//write header
			buffOut.write(codeMap.size());
			for(Entry<Integer, String> entry : codeMap.entrySet()){
				buffOut.write(entry.getKey());
				buffOut.write(entry.getValue().length());
			}
			
			
			///get current char (as byte)
			int currentChar = bufferedReader.read();
	
			
			String byteToWrite = "";

			
			//performs actual encoding
			while(currentChar != -1){
				String binaryRep = codeMap.get(currentChar);
				for(int i = 0; i < binaryRep.length(); i++){
					if(byteToWrite.length() == 8){
						buffOut.write(Integer.valueOf(byteToWrite, 2));
						byteToWrite = "";
					}
					
					byteToWrite += binaryRep.charAt(i);
				}
			
				currentChar = bufferedReader.read();
			}
			
			
			
			
			///Handle EOF
			byteToWrite += codeMap.get(0x000);
			while((byteToWrite.length() % 8) != 0){
				byteToWrite += '0';
			}
			
			int begin = 0;
			for(int i=0; i < byteToWrite.length(); i++){
				if((i % 8) == 0 && (i != 0)){
					buffOut.write(Integer.valueOf(byteToWrite.substring(begin, i), 2));
					begin = i;
				}
			}
			
			
			
			

		
			//flush out bytes buffout may not have written
			buffOut.flush();
			
			//close everything
			bufferedReader.close();
			inputReader.close();
			fileInputStream.close();	
			buffOut.close();
		}
		catch(Exception e){
			e.printStackTrace();
			
		}
	}
	
}
