package huffman;

import java.util.HashMap;

/**
 * Main class to handle file decodings
 * @author Andrew Langford
 *
 */
public class Decode {	
	
	/**
	 * Main decode function
	 * @param args command line args
	 */
	public static void main(String[] args){
		
		//read in file and build huffman table
		HashMap<Integer, Integer> codes = DecodeReader.readFile(args[0]);
		
		if(codes != null){
			//reconstruct the huffman tree from the huffman table
			HuffmanTree tree = new HuffmanTree();
			tree.buildTreeFromHuffmanTable(codes);
			
			//decode encoded file and write to the source file.
			DecodeWriter.writeFile(args[0], args[1], tree);
		}	
	}
	

}
