package huffman;



/**
 * Class to represent nodes on the min heap and huffman tree.
 * @author Andrew Langford
 *
 */
public class HuffmanTreeNode implements Comparable<HuffmanTreeNode>{
	
	
//--Fields--//
	private Integer character;
	private Integer frequency;
	private String code;
	private int codeLength;

	private HuffmanTreeNode left;
	private HuffmanTreeNode right;
	private boolean isLeafNode;
	
	
	
//--Constructors --//
	/**
	 * Base constructor
	 */
	public HuffmanTreeNode(){
		this.character = null;
		this.frequency = 0;
		this.left = null;
		this.right = null;
		this.isLeafNode = false;
		this.codeLength = 0;
		this.code = "";
	}
	
	
	/**
	 * Main constructor
	 * @param character the character this node represents
	 * @param frequency the frequency this character appears in the file.
	 */
	public HuffmanTreeNode(Integer character, Integer frequency, boolean isLeafNode){
		this.character = character;
		this.frequency = frequency;	
		this.left = null;
		this.right = null;
		this.isLeafNode = isLeafNode;
		this.codeLength = 0;
		this.code = "";
	}

	/**
	 * get the character this node represents
	 * @return the character this node represents
	 */
	public Integer getCharacter() {
		return character;
	}


	/**
	 * get the integer frequency this character appears
	 * @return the frequency the character appears
	 */
	public Integer getFrequency() {
		return frequency;
	}
	
	/**
	 * get the left child node of this node
	 * @return the left child node
	 */
	public HuffmanTreeNode getLeft() {
		return left;
	}

	/**
	 * sets the left child node of this node
	 * @param left the left child node to set
	 */
	public void setLeft(HuffmanTreeNode left) {
		this.left = left;
	}

	/**Get the right child node of this node
	 * @return the right child node
	 */
	public HuffmanTreeNode getRight() {
		return right;
	}

	/**
	 * Set the right child node of this node
	 * @param right the right node to set
	 */
	public void setRight(HuffmanTreeNode right) {
		this.right = right;
	}

	/**
	 * set the character this node represents;
	 * @param character the character to set
	 */
	public void setCharacter(Integer character) {
		this.character = character;
	}

	/**
	 * Set the frequency this character appears
	 * @param frequency the frequency to set
	 */
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	/**
	 * @return the isLeafNode, true if this node is a leaf node, false otherwise
	 */
	public boolean isLeafNode() {
		return isLeafNode;
	}


	/**
	 * @param isLeafNode the isLeafNode to set
	 */
	public void setLeafNode(boolean isLeafNode) {
		this.isLeafNode = isLeafNode;
	}


	/**
	 * @return the codeLength
	 */
	public int getCodeLength() {
		return codeLength;
	}


	/**
	 * @param codeLength the codeLength to set
	 */
	public void setCodeLength(int codeLength) {
		this.codeLength = codeLength;
	}



	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}


	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}


	/**
	 * Comapres two nodes based on their frequency of occurrence in the sourcefile.
	 */
	@Override
	public int compareTo(HuffmanTreeNode o) {
		// TODO Auto-generated method stub
		if(this.frequency < o.frequency){
			return -1;
		}
		
		if(this.frequency > o.frequency){
			return 1;
		}
		return 0;
	}

	
	

}
