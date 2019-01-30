
import java.util.*;

public class HuffmanTree {
	// DO NOT include the frequency or priority in the tree
	private class Node {
		private Node left;
		private char data;
		private Node right;
		private Node parent;

		private Node(Node L, char d, Node R, Node P) {
			left = L;
			data = d;
			right = R;
			parent = P;
		}
	}

	private Node root;
	private Node current; // this value is changed by the move methods

	public HuffmanTree() {
		root = null;
		current = null;
	}

	public HuffmanTree(char d) {
		// makes a single node tree
		root = new Node(null, d, null, null);
	}

	public HuffmanTree(String t, char nonLeaf) {
		// Assumes t represents a post order representation of the tree as discussed
		// in class
		// nonLeaf is the char value of the data in the non-leaf nodes
		// in class we used (char) 128 for the non-leaf value
		Stack<HuffmanTree> stack = new Stack<>();
		HuffmanTree lSub, rSub, tree;
		//push into stack until noLeaf is reached
		//pop two from stack and combine with nonLeaf as root
		
		for (int i = 0; i < t.length(); i++) {
			if (t.charAt(i) == nonLeaf) {
				rSub = stack.pop();
				lSub = stack.pop();
				tree = new HuffmanTree(lSub, rSub, nonLeaf);
				stack.push(tree);
			} else {
				tree = new HuffmanTree(t.charAt(i));
				stack.push(tree);
			}
		}
			tree = stack.pop();
			root = tree.root;
	}

	public HuffmanTree(HuffmanTree b1, HuffmanTree b2, char d) {
		// makes a new tree where b1 is the left subtree and b2 is the right subtree
		// d is the data in the root
		root = new Node(b1.root, d, b2.root, null);
		b1.root.parent = root;
		b2.root.parent = root;
	}

	// use the move methods to traverse the tree
	// the move methods change the value of current
	// use these in the decoding process
	public void moveToRoot() {
		// change current to reference the root of the tree
		current = root;
	}

	public void moveToLeft() {
		// PRE: the current node is not a leaf
		// change current to reference the left child of the current node
		current = current.left;
	}

	public void moveToRight() {
		// PRE: the current node is not a leaf
		// change current to reference the right child of the current node
		current = current.right;
	}

	public void moveToParent() {
		// PRE: the current node is not the root
		// change current to reference the parent of the current node
		current = current.parent;
	}

	public boolean atRoot() {
		// returns true if the current node is the root otherwise returns false
		if (current.equals(root)) {
			return true;
		}
		return false;
	}

	public boolean atLeaf() {
		// returns true if current references a leaf other wise returns false
		if (current.left == null && current.right == null) {
			return true;
		}
		return false;
	}

	public char current() {
		// returns the data value in the node referenced by current
		return current.data;
	}

	public class PathIterator implements Iterator<String> {
		// the iterator returns the path (a series of 0s and 1s) to each leaf
		// DO NOT compute all paths in the constructor
		// only compute them as needed (similar to what you did in homework 2)
		// add private methods and variables as needed

		Stack<HuffmanTree.Node> stack;
		ArrayList<Node> postOrder;
		Node temp;
		int nextCount = 0;

		// FIXME test method
		public void asdf() {
			System.out.println("asdf");
		}

		public PathIterator() {
			stack = new Stack<HuffmanTree.Node>();
			postOrder = new ArrayList<>();
			//current = root;
			fillPostOrder();
		}

		public boolean hasNext() {
			if (nextCount < postOrder.size())
				return true;
			return false;
		}

		// post order iterator, check path to root after leaf is decided
		public String next() {
			// the format of the string should be leaf value, a space, a sequence of
			// 0s and 1s
			// the 0s and 1s indicate the path from the root the node containing
			// the leaf value
			if (hasNext()) {
				String out = postOrder.get(nextCount).data + " " + getPath(postOrder.get(nextCount));
				nextCount++;
				return out;
			}
			return null;
		}
		
		//1 = left, 0 = right
		private String getPath(Node node) {
			String out = "";
			while (node.parent != null) {
				Node temp = node.parent;
				if (temp.left.equals(node)) {
					out = 1 + out;
				}
				else {
					out = 0 + out;
				}
				node = node.parent;
			}
			return out;
		}

		// fills postOrder array list
		private void fillPostOrder() {
			Node temp = root;

			while (temp != null) {
				if (temp.right != null) {
					stack.push(temp.right);
				}
				stack.push(temp);
				temp = temp.left;
			}
			
			while (!stack.isEmpty()){
				while (temp != null) {
					if (temp.right != null) {
						stack.push(temp.right);
					}
					stack.push(temp);
					temp = temp.left;
				}
				// given: temp == null
				temp = stack.pop();
				if (!stack.isEmpty() && temp.right != null && temp.right == stack.peek()) {
					Node rChild = stack.pop();
					stack.push(temp);
					temp = rChild; // same as temp = temp.right
				} else {
					postOrder.add(temp);
					temp = null;
				}
			}
		}
	}

	public void remove() {
		// optional method not implemented
	}

	public Iterator<String> iterator() {
		// return a new path iterator object
		return new PathIterator();
	}
	@Override
	public String toString() {
		// returns a string representation of the tree using the postorder format
		// discussed in class
		Iterator<String> x = iterator();
		String out = "";
		while (x.hasNext()) {
			String a = x.next().substring(0, 1);
			out = out + a;
		}
		return out;
	}
}
