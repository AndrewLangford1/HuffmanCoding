package huffman;

import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Class to handle all encodings of files
 * @author Andrew Langford
 *
 */
public class Encode {
	
	private static EncodingHandler encodingHandler;
	
	/**
	 * Main function
	 * @param args command line args
	 */
	public static void main(String[] args){		
		encodingHandler = new EncodingHandler();
		
		
		//read in source file and build frequency map
		readFile(args[0]);
				
		//build min heap from the frequency map.
		PriorityQueue<HuffmanTreeNode> minHeap = Heap.buildMinHeap(encodingHandler.getFrequencyMap());
	
		
		//build an optimal huffman tree from the minHeap
		HuffmanTree huffmanTree = new HuffmanTree();
		huffmanTree.buildHuffmanTree(minHeap);
		
		
		//build the canonized encodings and write the file
		writeFile(args[0], args[1], huffmanTree.buildCanonizedEncodingMap());
				
	}
	
	
	
	
	/**
	 * Reads the source file and creates a frequency map.
	 * @param sourceFile the path to the sourcefile we wish to encode
	 */
	private static void readFile(String sourceFile){
		encodingHandler.readInFile(sourceFile);
	}
	
	
	/**
	 * Reads the source file and writes each character as its associated code to the target file.
	 * @param sourceFile the source file to read
	 * @param targetFile the target file to write to 
	 */
	private static void writeFile(String sourceFile, String targetFile, HashMap<Integer, String> codeMap){
		EncodeWriter.writeEncodedFile(sourceFile, targetFile, codeMap);
	}
		
}
