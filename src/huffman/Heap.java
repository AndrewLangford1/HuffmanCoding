package huffman;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.PriorityQueue;


/**
 * Class to build a min heap
 * @author Andrew Langford
 *
 */
public class Heap {		
							
	
	/**
	 * 
	 * builds a min heap utilizing javas priority queue and returns it
	 * @param frequencyMap the map of characters to frequences
	 */
	public static PriorityQueue<HuffmanTreeNode> buildMinHeap(HashMap<Integer, Integer> frequencyMap){
	
		try{
				
			PriorityQueue<HuffmanTreeNode> priorityQueue = new PriorityQueue<HuffmanTreeNode>();
			
			for(Entry<Integer, Integer> entry : frequencyMap.entrySet()){
				priorityQueue.offer(new HuffmanTreeNode(entry.getKey(), entry.getValue(), true));
			}
			
			
			frequencyMap.clear();
			
			return priorityQueue;
		}
		catch(Exception e){
			e.printStackTrace();	
			
		}
		
		return null;

	}
	
}
