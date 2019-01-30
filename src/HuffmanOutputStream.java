
import java.io.*;

public class HuffmanOutputStream {
	// add additional private variables as needed
	// do not modify the public methods signatures or add public methods
	
//	ArrayList<String> characters = new ArrayList<>();
//	ArrayList<String> paths = new ArrayList<>();
	
	String[] characters = new String[128];
	String[] paths = new String[128];
	
	char[] bits = new char[8];
	int bitCount = 0;
	String out = "";
	
	DataOutputStream d;

	public HuffmanOutputStream(String fileName, String tree, int totalChars) {
		try {
			d = new DataOutputStream(new FileOutputStream(fileName));
			d.writeUTF(tree);
			d.writeInt(totalChars);
		} catch (IOException e) {
			System.out.println("File " +  fileName + " not found");
		}
		// add other initialization statements as needed
	}

	public void writeBit(char bit) {
		// PRE:bit == '0' || bit == '1'
		bits[bitCount] = bit;
		bitCount++;
		if (bitCount == 8) {
			out = "";
			for(int i = 0; i < 8; i++) {
				out = out + bits[i];
			}
			int outByte = Integer.parseInt(out, 2);
			
			try {
				if (d != null) {
					d.writeByte(outByte);
				}
			} catch(IOException e) {
				System.out.println("File" + " not found");
			}
			bitCount = 0;
		}
	}

	public void close() {
		// write final byte (if needed)
		// close the DataOutputStream//Pads last byte with enough bits to reach 8
		for(int i = bitCount; i < 8 && i > 0; i++) {
			writeBit('0');
		}
		try {
			if (d != null) {
				d.close();
			}
		} catch(IOException e) {
			System.out.println("File" + " not found");
		}
	}
}