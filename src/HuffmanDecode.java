
import java.io.*;

public class HuffmanDecode {
	
	public HuffmanDecode(String in, String out) {
		// implements the Huffman Decode Algorithm
		// Add private methods and instance variables as needed
		HuffmanInputStream inStream = new HuffmanInputStream(in);
		
		HuffmanTree tree = new HuffmanTree(inStream.getTree(), (char) 128);
		
		try {
			PrintWriter outputWriter = new PrintWriter(out);
			
			tree.moveToRoot();
			int x = inStream.readBit();
			
			for (int i = 0; i < inStream.getTotalChars(); i++) {
				while (!tree.atLeaf() && x != -1) {
					if(x == 0) tree.moveToRight();
					else tree.moveToLeft();
					x = inStream.readBit();
				}
				outputWriter.write(tree.current()); 
				tree.moveToRoot();
			}
			outputWriter.close();
		}catch (IOException e) {
			System.out.println("File not found");
		}
	}

	public static void main(String args[]) {
		// args[0] is the name of a input file (a file created by Huffman Encode)
		// args[1] is the name of the output file for the uncompressed file
		 new HuffmanDecode(args[0], args[1]);
		// do not add anything here
		//new HuffmanDecode("encoded.txt", "decoded.txt");
	}
}