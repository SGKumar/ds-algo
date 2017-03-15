package tree;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.TreeMap;
// Basic node stored in unbalanced binary search trees
// Note that this class is not accessible outside
// of this package.
/*interface BinaryTreeNode : Comparable
{
	char value();
	T getLeft();
	T getRight();
}*/

public class BinaryNode //implements Comparable<BinaryNode>
{
    // Friendly data; accessible by other package routines
	//private final char nodeValue;
	public char val;
    int preIL = 0;
    //HashMap<Integer, Integer> m_vertSum;
	public char value() { return val; }
	public char val() { return val; }
	public void value(char v) { val = v; }

	public BinaryNode left, right;         // left child
	public BinaryNode next;
	private BinaryNode gp;
	private BinaryNode parent;

	boolean isLeaf()
	{
		return (null == left) && (null == right);
	}
	boolean isChild(BinaryNode node)
	{
		return (node == left) || (node == right);
	}

	public void left(BinaryNode left) { this.left = left; }
	public BinaryNode left() { return left; }

	public void right(BinaryNode right) { this.right = right; }
	public BinaryNode right() { return right; }

	void parent(BinaryNode p) { this.parent = p; }
	BinaryNode parent() { return parent; }

	void gp(BinaryNode gp) { this.gp = gp; }
	BinaryNode gp() { return gp; }

	public void next(BinaryNode next) { this.next = next; }
	public BinaryNode next() { return next; }

    // Constructors
    BinaryNode(char value)
    {
        val = value;
        left = right = null;
        //prev = next = null;
    }
	
	public String toStringFlat()
	{
		StringBuilder str = new StringBuilder().append("[").append(value()).append(",");
		str.append((left()==null)?" ":left().value()).append(",");
		str.append((right()==null)?" ":right().value()).append(",");
		str.append((next()==null)?" ":next().value()).append("]");
		return str.toString();
	}
	
	public void prettyPrint()
	//public void prettyPrint(int height)
	{
		System.out.println(prettyPrint(this, 1, height()));
	}

	private StringBuilder prettyPrint(BinaryNode root, int currentHeight, int totalHeight)
	{
		StringBuilder sb = new StringBuilder();
		int spaces = getSpaceCount(totalHeight-currentHeight + 1);
		if(root == null) {
			//create a 'spatial' block and return it
			String row = String.format("%"+(2*spaces+1)+"s%n", "");
			//now repeat this row space+1 times
			String block = new String(new char[spaces+1]).replace("\0", row);
			return new StringBuilder(block);
		}
		if(currentHeight==totalHeight) return new StringBuilder(root.val+"");
		int slashes = getSlashCount(totalHeight-currentHeight +1);
		sb.append(String.format("%"+(spaces+1)+"s%"+spaces+"s", root.val+"", ""));
		sb.append("\n");
		//now print / and \
		// but make sure that left and right exists
		char leftSlash = root.left == null? ' ':'/';
		char rightSlash = root.right==null? ' ':'\\';
		int spaceInBetween = 1;
		for(int i=0, space = spaces-1; i<slashes; i++, space --, spaceInBetween+=2) {
			for(int j=0; j<space; j++) sb.append(" ");
			sb.append(leftSlash);
			for(int j=0; j<spaceInBetween; j++) sb.append(" ");
			sb.append(rightSlash+"");
			for(int j=0; j<space; j++) sb.append(" ");
			sb.append("\n");
		}
		//sb.append("\n");

		//now get string representations of left and right subtrees
		StringBuilder leftTree = prettyPrint(root.left, currentHeight+1, totalHeight);
		StringBuilder rightTree = prettyPrint(root.right, currentHeight+1, totalHeight);
		// now line by line print the trees side by side
		Scanner leftScanner = new Scanner(leftTree.toString());
		Scanner rightScanner = new Scanner(rightTree.toString());
//		spaceInBetween+=1;
		while(leftScanner.hasNextLine()) {
			if(currentHeight==totalHeight-1) {
				sb.append(String.format("%-2s %2s", leftScanner.nextLine(), rightScanner.nextLine()));
				sb.append("\n");
				spaceInBetween-=2;				
			}
			else {
				sb.append(leftScanner.nextLine());
				sb.append(" ");
				sb.append(rightScanner.nextLine()+"\n");
			}
		}

		return sb;
	}

	private int getSlashCount(int height) 
	{
		if(height <= 3) return height -1;
		return (int) (3*Math.pow(2, height-3)-1);
	}
	private int getSpaceCount(int height) 
	{
		return (int) (3*Math.pow(2, height-2)-1);
	}
	/*public void print() 
	{
		inorder(this);
		System.out.println();
	}
	private void inorder(BinaryNode root) 
	{
		if (root==null) return;
		inorder(root.left);
		System.out.print(root.data+" ");
		inorder(root.right);
	}*/
	public int height() 
	{
		return height(this);
	}
	private int height(BinaryNode root)
	{
		if (root == null) return 0;
		return Math.max(height(root.left), height(root.right))+1;
	}
	@Override
	public String toString() {
		return this.val+"";
	}
}
