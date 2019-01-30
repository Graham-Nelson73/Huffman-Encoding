/*TODO
 * X Read in characters from file and store characters/frequencies
 * 
 * X Fill Binary Heap with single node huffman trees containing characters and frequencies, stored in parallel arrays
 * 
 * X Build full huffman tree of characters based on frequency, most used characters higher in tree, non leaf nodes have no data
 * 
 * X store huffman tree in string form and codes for each character
 * 
 * X store file with encoded characters, huffman tree and character code key
 * 
 * rebuild huffman tree from string 
 * 
 * decode characters from file using stored character code key
 * 
 * rebuild file in readable format, identical to original
 * 
 */


import java.io.*;
import java.util.*;

public class HuffmanEncode {
	
	HuffmanTree finalTree;
	int numChars = 0;
	
	public HuffmanEncode(String in, String out) {
		// Implements the Huffman encoding algorithm
		// Add private methods and instance variables as needed
		try {
			FileInputStream inStream = new FileInputStream(in);
			int symbol = inStream.read();
			
			BinaryHeap heap = new BinaryHeap(127);
			char[] characters = new char[heap.trees.length + 1];
			int[] frequency = new int[heap.trees.length + 1];
			int freqSize = 0;
			
			//loops for every individual character in the file
			while (symbol != -1) {
				char sym = (char)symbol;
				//if the current character has already been encountered and added to the array
				if (indexOf(characters, sym) >= 0) {
					frequency[indexOf(characters, sym)]++;
				}
				//if character is encountered for first time, add char to characters array, iterate new index of priority, iterate size
				else {
					characters[freqSize + 1] = sym;
					frequency[freqSize + 1]++;
					freqSize++;
				}
				numChars++;
				symbol = inStream.read();
			}
			//puts single node trees in minheap, lowest frequency at root. takes char and frequency from local arrays
			for (int i = 0; i <= freqSize; i++) {
				HuffmanTree tree = new HuffmanTree(characters[i]);
				heap.insert(frequency[i], tree);
			}
			//Band-aid on bullet hole
			heap.priority[1] = heap.priority[0];
			heap.priority[0] = 0;
			heap.trees[1] = heap.trees[0];
			heap.trees[0] = null;
			
			finalTree = buildTree(heap);
			Iterator<String> it = finalTree.iterator();
			encode(it, in, out);

			inStream.close();
		}
		catch (IOException e) {
			System.out.println("File " +  in + " not found");
		}
	}
	
	//Outputs encoded huffman paths to output file
	private void encode(Iterator<String> it, String inFile, String outFile) {
		String[] paths = new String[128];
		//fills array with paths of characters, paths stored at index of character
		while (it.hasNext()) {
			String temp = it.next();
			if (temp.charAt(0) != 128) {
				paths[(int)temp.charAt(0)] = temp.substring(2);
			}
		}
		
		try {
			FileInputStream reader = new FileInputStream(inFile);
			int symbol = reader.read();
			HuffmanOutputStream outStream = new HuffmanOutputStream(outFile, finalTree.toString(), numChars);
			//sets temp to string representation of path of current char, writes each individual bit or path to output stream
			while (symbol != -1) {
				String temp = paths[symbol];
				if (temp != null) {
					for (int i = 0; i < temp.length(); i++) {
						outStream.writeBit(temp.charAt(i));
					}
				}
				symbol = reader.read();
				
			}
			reader.close();
			outStream.close();
		}catch (IOException e) {
			System.out.println("File " +  outFile + " not found");
		}
	}
	
	//returns index of given string in array, returns -1 if not found
	private int indexOf(char[] characters, char symbol) {
		for (int i = 0; i < characters.length; i++) {
			if (characters[i] == symbol) {
				return i;
			}
		}
		return -1;
	}
	
	//builds huffman tree by removing two lowest frequency trees from the queue, combining them into one tree and 
	//re inserting them into the queue
	private HuffmanTree buildTree(BinaryHeap heap) {
		while (heap.getSize() > 1) {
			int minPriority1 = heap.getMinPriority();
			HuffmanTree tree1 = heap.getMinTree();
			heap.removeMin();
			
			int minPriority2 = heap.getMinPriority();
			HuffmanTree tree2 = heap.getMinTree();
			heap.removeMin();
			
			HuffmanTree tree = new HuffmanTree(tree1, tree2, (char)128);
			heap.insert(minPriority1 + minPriority2, tree);
		}
		return heap.trees[1];
	}

	public static void main(String args[]) {
		// args[0] is the name of the file whose contents should be compressed
		// args[1] is the name of the output file that will hold the compressed
		// content of the input file
		new HuffmanEncode(args[0], args[1]);
		// do not add anything here
		//new HuffmanEncode("book1.txt", "encoded.txt");
	}
}
