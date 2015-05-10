package huffman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.PriorityQueue;

/**
 * Class to represent and manipulate a HuffmanTree
 * @author Andrew Langford
 *
 */
public class HuffmanTree {
	
//--Fields--//
	
	private HuffmanTreeNode root;
	private HuffmanTreeNode current;
	
	
	/**
	 * Main constructor
	 * 
	 */
	public HuffmanTree(){
		this.root = null;
		
	}
	
	
	/**
	 * Builds the huffman tree from the algorithm given in class
	 * @param minHeap the minHeap of nodes used to build the huffman tree
	 */
	public void buildHuffmanTree(PriorityQueue<HuffmanTreeNode> minHeap){
		int n = minHeap.size();
		
		//while we still have at least two nodes
		for(int i= 1; i<= n-1; i++){
			
			//create a new node that will be the parent of the two least frequently occuring characters in the min heap.
			HuffmanTreeNode node = new HuffmanTreeNode();
			
			//Set left child
			HuffmanTreeNode leftChild = minHeap.poll();
			node.setLeft(leftChild);
			
			//set the right child
			HuffmanTreeNode rightChild = minHeap.poll();
			node.setRight(rightChild);
			
			node.setFrequency(leftChild.getFrequency() + rightChild.getFrequency());
			
			//insert new node int
			minHeap.offer(node);
		}
		
		 setRoot(minHeap.poll());
		 
		 minHeap.clear();
	}
			
	
	
	/**
	 * Builds a code lookup table 
	 * @return a map of characters (as their unicode integer representation) to their integer code values.
	 */
	public HashMap<Integer, String> buildCanonizedEncodingMap(){
		ArrayList<HuffmanTreeNode> canonicalCodes = new ArrayList<HuffmanTreeNode>();
		buildOptimalHuffmanCode(this.root, 0, canonicalCodes);
		sortLeafNodesCanonically(canonicalCodes);
		return codeMapBuilder(canonicalCodes);
		
		
	}
	
	
	/**
	 * Builds canonical codes for each leaf node in a tree
	 * @param canonicalCodes
	 * @return a map of a character to its code binary representaion
	 */
	public HashMap<Integer, String> codeMapBuilder(ArrayList<HuffmanTreeNode> canonicalCodes){
		
		HashMap<Integer, String> codeMap = new HashMap<Integer, String>();
		int codeLength = 0;
		long code = 0;
		for(HuffmanTreeNode node : canonicalCodes){
			if(node.getCodeLength() < codeLength){
				int difference = codeLength - node.getCodeLength();
				code = (long) (code / Math.pow(2.0 , (double) difference));
			}
			
			codeLength = node.getCodeLength();
			
			String asBinary = String.format("%" + codeLength+   "s", Long.toBinaryString(code)).replace(' ', '0');
			
			node.setCode(asBinary);
			
			System.out.println(node.getCharacter() + "  => " + asBinary);
			codeMap.put(node.getCharacter(), asBinary);
			
			code ++;	
		}
		
		
		return codeMap;
	}
	
	
	/**
	 * builds a codemap from codelengths
	 * @param codes the character to codelength map received from decode reader
	 * @return a hashmap of characters to codes
	 */
	public void buildTreeFromHuffmanTable(HashMap<Integer,Integer> codes){
		ArrayList<HuffmanTreeNode> nodes = reverselyBuildLeafNodes(codes);
		sortLeafNodesCanonically(nodes);
		codeMapBuilder(nodes);
		
		this.root = new HuffmanTreeNode();
		this.current = root;
		
		for(HuffmanTreeNode node : nodes)
			buildPathToNode(node);
		
	}
	
	
	/**
	 * builds a path on the binary tree to the specified leaf node, creating branch nodes if need be
	 * @param leafNode the leaf node we want to build a path to
	 */
	private void buildPathToNode(HuffmanTreeNode leafNode){
		String code = leafNode.getCode();
		HuffmanTreeNode currentNode = this.root;
		
		for(int i =0; i < code.length(); i++){
			if(i == code.length()-1){
				switch(code.charAt(i)){
					case('0'):{
						currentNode.setLeft(leafNode);
						
					}
					
					break;
				
					case('1'):{
						currentNode.setRight(leafNode);
						
					}
					break;
					
					default:{
						
						
					}
					
					return;
				
				}
			}
			else{
				currentNode = traverseAndBuildSubtree(currentNode, code.charAt(i));
			}
		}
	}
	
	/**
	 * moves one depth down the tree, and builds a new branch node if need be
	 * @param currentNode current node we are sitting on
	 * @param currentChar the current bit we are using to traverse the tree
	 * @return
	 */
	private HuffmanTreeNode traverseAndBuildSubtree(HuffmanTreeNode currentNode, char currentChar){
		switch(currentChar){
			case('0'):{
				if(currentNode.getLeft() == null){
					currentNode.setLeft(new HuffmanTreeNode());
				}
				currentNode = currentNode.getLeft();
			}
			
			break;
			
			case('1'):{
				if(currentNode.getRight() == null){
					currentNode.setRight(new HuffmanTreeNode());
				}
				currentNode = currentNode.getRight();
			}
			break;
			
			default:{
				//
			}
			
			
		}
		
		return currentNode;
	}
	
	
	
	/**
	 * builds the leaf nodes for the tree used in Decode
	 * @param codes the map of characters to their code lengths
	 * @return a list of leaf nodes
	 */
	private ArrayList<HuffmanTreeNode> reverselyBuildLeafNodes(HashMap<Integer, Integer> codes){
		ArrayList<HuffmanTreeNode> nodes = new ArrayList<HuffmanTreeNode>();
		for(Entry<Integer, Integer> entry : codes.entrySet()){
			HuffmanTreeNode node = new HuffmanTreeNode();
			node.setCharacter(entry.getKey());
			node.setCodeLength(entry.getValue());
			node.setLeafNode(true);
			nodes.add(node);
		}
		
		return nodes;
		
	}
	
	
	/**
	 * traverses the huffman tree as specified by the input, returns a list of characters it matches.
	 * @param byteAsString the binary string used to traverse the tree
	 * @return a list of Integers that represent characters to be written to the source file
	 */
	public ArrayList<Integer> decode(String byteAsString){
		ArrayList<Integer> charsToWrite = new ArrayList<Integer>();
		for(int i =0; i < byteAsString.length(); i++){
			current = traverseAndFindChar(byteAsString.charAt(i), current);
			if(current.isLeafNode()){
				charsToWrite.add(current.getCharacter());
				this.current = root;
			}
		}
		
		return charsToWrite;
	}
	
	
	/**
	 * moves one step down the tree and returns the node it sits on after computation
	 * @param currentChar either 0 or 1, tells tree to go left or right
	 * @param currentNode
	 * @return the current node the traversal is visiting
	 */
	private HuffmanTreeNode traverseAndFindChar(char currentChar, HuffmanTreeNode currentNode){
		if(currentNode == null){
			return null;
		}
		else{
			if(currentChar == '1'){
				return currentNode.getRight();
			}
			
			if(currentChar == '0'){
				return currentNode.getLeft();	
			}
			
			return null;
		}
	}
	

	
	
	/**
	 * Builds a codemap (not canonical) from the tree built in Encode
	 * @param currentNode the current node we're visiting
	 * @param codeLength the length of the codeword
	 * @param canonicalCodes the lost of leaf nodes
	 */
	private void buildOptimalHuffmanCode(HuffmanTreeNode currentNode, int codeLength, ArrayList<HuffmanTreeNode> canonicalCodes){
		if(currentNode == null){
			return;	
		}
		else{
		
			buildOptimalHuffmanCode(currentNode.getLeft(), codeLength + 1, canonicalCodes);
			buildOptimalHuffmanCode(currentNode.getRight(), codeLength + 1, canonicalCodes);	
			
			//TODO write each character to the output file as part of the header.
			if(currentNode.isLeafNode()){
				currentNode.setCodeLength(codeLength);
				canonicalCodes.add(currentNode);
			}	
		}	
	}
	
	
	/**
	 * Sorts the leaf nodes from longest to shortest codes. ties are settled by lexographical order.
	 * @param leafNodes the leaf nodes of the huffman tree
	 */
	private void sortLeafNodesCanonically(ArrayList<HuffmanTreeNode> leafNodes){
		Collections.sort(leafNodes, new Comparator<HuffmanTreeNode>(){

			@Override
			public int compare(HuffmanTreeNode o1, HuffmanTreeNode o2) {
				if(o1.getCodeLength() < o2.getCodeLength()){
					return 1;
				}
				else{
					if(o1.getCodeLength() == o2.getCodeLength()){
						if(o1.getCharacter() < o2.getCharacter()){
							return -1;
						}
						else{
							return 1;
						}
					}
					
					return -1;
				}
			}
		});	
	}
	

	
	
	/**	
	 * Returns the root of tree
	 * @return the root of the tree
	 */
	public HuffmanTreeNode getRoot() {
		return root;
	}
	

	/**
	 * Sets the root of the tree
	 * @param root the root to set
	 */
	public void setRoot(HuffmanTreeNode root) {
		this.root = root;
	}
	
	

}
