
import java.io.*;

public class HuffmanInputStream {
	// add additional private variables and methods as needed
	// DO NOT modify the public method signatures or add public methods
	private String tree;
	private int totalChars;
	private DataInputStream d;
    private int current = 0;
	 String encoded = "";
	
	private int currentBit = 0;

	public HuffmanInputStream(String filename) {
		try {
			d = new DataInputStream(new FileInputStream(filename));
			tree = d.readUTF();
			totalChars = d.readInt();
			
			current = d.readUnsignedByte();
		} catch (IOException e) {
		}
		// add other initialization statements as needed
	}
	
	private int getBit(int currentByte, int position) {
		 return (currentByte >> 7 - position) & 1;
	}
	
	public int readBit() {
		// returns the next bit is the file
		// the value returned will be either a 0 or a 1
		if (currentBit > 7) {
			try {
				current = d.readUnsignedByte();
				currentBit = 0;
			}catch(IOException e) {
			}
		}
		int out = getBit(current, currentBit);
		currentBit++;
		return out;
	}

	public String getTree() {
		// return the tree representation read from the file
		return tree;
	}

	public int getTotalChars() {
		// return the character count read from the file
		return totalChars;
	}
}