package tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.ArrayDeque;

public class BinaryTreeBase
{
	protected BinaryNode root;
	public BinaryTreeBase()
	{
		root = null;
		//this(new BinaryNode((char)0));
		//System.out.println("This class {BinaryTree} is NOT working code.. I started from {BinarySearchTree}");
	}

	public BinaryTreeBase(BinaryNode node)
	{
		root = node;
	}
	public BinaryNode root()
	{
		return root;
	}

	public void insert(BinaryNode node)
	{
		if(null == root)
			root = node;
		else
			insert(node, root);
	}
	private void insert(BinaryNode node, BinaryNode parent)
	{
		if(node.value() < parent.value()) {
			if(null == parent.left()) {
				parent.left(node);
				node.parent(parent);
			}
			else {
				insert(node, parent.left());
			}
		}
		else if(node.value() > parent.value()) {
			if(null == parent.right()) {
				parent.right(node);
				node.parent(parent);
			}
			else {
				insert(node, parent.right());
			}
		}
		/*
		else {
			throw new DuplicateItemException(node.toStringFlat());
		}
		*/	
	}

	public static TreeNode createTree(int[] vals) {
		if(vals == null || vals.length < 2 || vals[0] != vals.length-1) return null;
		
		Queue<TreeNode> q = new ArrayDeque<TreeNode>();
		TreeNode root = new TreeNode(vals[1]);
		q.add(root);
		int i = 2;
		while(i <= vals[0] && !q.isEmpty()) {
			TreeNode node = q.remove();
			if(vals[i] != -1) {
				node.left = new TreeNode(vals[i]);
				q.add(node.left);
			}
			i++;
			if(i == vals.length)
				break;
			if(vals[i] != -1) {
				node.right = new TreeNode(vals[i]);
				q.add(node.right);
			}
			i++;
		}
		if(null != root) {
			//System.out.println("Generated Tree from array " + Arrays.toString(vals));
			//root.prettyPrint();
		}
		return root;
	}
	public static TreeNode smallTree1() {
		int[] vals = new int[] {19, 1, 2, 3, 4, 5, -1, 9, -1, -1, 6, 7, -1, -1, -1, -1, -1, 8, -1, -1};
		return createTree(vals);
		
	}
	public static TreeNode smallTree2() {
		int[] vals = new int[] {37, 78, 72, 80, 66, 74, 79, 81, 65, 71, 73, 75, -1, -1, -1, 83, -1, -1, 
								68, -1, -1, -1, -1, 76, 82, -1, 67, 69, -1, 77, -1, -1, -1, -1, -1, 70, -1, -1};
		return createTree(vals);
	}
	public static TreeNode smallTree3() {
		int[] vals = new int[] {35, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -1, -1, -1, 10, 11, 12, -1, -1, -1, -1, 13, 
								-1, 14, -1, 15, -1, -1, 16, 17, -1, -1, -1, -1, -1, -1};
		return createTree(vals);
	}
	public static TreeNode smallTree4() {
		int[] vals = new int[] {35, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -1, 10, -1, -1, 11, 12, -1, -1, -1, -1, 13, 
								-1, 14, -1, 15, -1, 16, 17, 18, -1, -1, -1, -1, -1, -1};
		return createTree(vals);
	}
	public static TreeNode BigTree3() {
		int[] vals = new int[] {763, 3, 7, 9, 9, 0, -1, -1, 2, 1, 4, 3, 2, 5, 2, 2, 4, 8, 1, 1, 4, 9, 0, -1, 8, 3, 5, 2, 5,
							-1, 1, 6, 2, 8, 1, 0, 7, 3, -1, 7, -1, 6, 6, 1, 7, 1, 5, 9, 4, 7, -1, 7, -1, -1, -1, 6, 2,
							8, 7, 8, 1, 5, 9, 0, 4, 6, -1, -1, 5, 6, -1, 2, 1, 8, 2, 5, 5, -1, 4, -1, 1, 9, 1, 4, 3, 5,
							7, 4, -1, -1, 0, 6, 7, 5, -1, 2, 1, 7, 1, 9, 0, 2, 5, 4, -1, -1, -1, -1, -1, 8, 2, 2, -1,
							-1, -1, -1, -1, 2, -1, 3, 9, 4, 8, 8, 6, 4, 7, 2, 5, 7, 1, -1, 9, 5, 3, 8, 0, 4, -1, -1, 5,
							5, 7, 2, -1, -1, -1, 8, 0, 4, 4, 5, 5, 7, -1, -1, 5, 6, 3, -1, 9, 1, 9, -1, 8, -1, -1, 9,
							-1, -1, 8, -1, -1, -1, -1, -1, -1, -1, -1, -1, 6, 7, 3, -1, 1, 8, -1, -1, 1, 8, -1, -1, -1,
							8, 0, 0, 5, 6, -1, -1, 0, -1, 9, -1, 5, -1, 6, 6, -1, 6, 2, 6, 5, -1, -1, 7, 3, 1, 6, -1, 7,
							6, -1, -1, 6, -1, 3, 9, -1, -1, -1, 0, -1, 2, -1, 0, -1, 7, 3, 5, -1, 8, 2, 0, 6, 8, 7, 3,
							9, 0, 1, 0, -1, -1, -1, 0, 8, 7, 2, 9, -1, 6, 6, 6, -1, 2, 3, 2, -1, -1, 1, 1, 4, 8, -1, 2,
							0, -1, -1, -1, -1, -1, -1, 1, 3, 6, -1, -1, -1, -1, 5, 4, 1, 7, 7, -1, -1, -1, -1, -1, -1,
							0, 8, 0, -1, 5, 5, -1, 7, 3, -1, -1, 1, -1, -1, -1, 7, 9, 4, -1, 4, -1, -1, -1, -1, -1, -1,
							-1, -1, -1, 0, 0, 5, 5, -1, -1, -1, 2, 6, 8, 1, -1, 0, -1, 6, -1, 0, -1, -1, -1, -1, -1, -1,
							6, 8, 2, -1, 4, 2, -1, 1, -1, -1, -1, 2, 1, 0, 2, 7, 8, -1, 1, -1, -1, 3, 4, -1, -1, -1, -1,
							-1, 5, -1, -1, 8, 2, -1, -1, -1, -1, 2, 8, -1, 3, -1, 8, 6, 3, -1, -1, -1, 8, 6, 4, -1, -1,
							-1, -1, 5, -1, -1, -1, -1, -1, -1, 9, 4, -1, -1, -1, -1, -1, -1, -1, 2, 2, 7, 3, 9, -1, -1,
							9, -1, -1, -1, -1, 6, -1, 3, 8, -1, -1, -1, -1, -1, -1, -1, 3, -1, -1, -1, -1, -1, -1, -1,
							-1, 4, -1, 2, -1, -1, -1, -1, 2, -1, -1, 1, 9, 1, -1, -1, 2, 5, 1, -1, -1, 4, 2, -1, -1, -1,
							7, 6, 3, 8, 2, 8, -1, -1, 0, -1, -1, 3, 1, -1, -1, 5, -1, -1, 9, -1, 2, -1, 0, -1, -1, -1, 8,
							-1, -1, 8, -1, -1, 0, -1, 0, -1, 7, -1, -1, 1, 4, -1, 9, 5, 3, -1, -1, -1, 2, 3, -1, -1, -1,
							6, 7, -1, 0, 6, -1, -1, -1, -1, -1, 5, -1, -1, -1, 5, -1, -1, -1, -1, -1, 4, 8, 3, -1, -1, 9,
							5, -1, -1, -1, 9, 0, -1, -1, -1, -1, -1, -1, -1, -1, 4, -1, 7, -1, -1, -1, -1, -1, -1, -1, 4,
							0, -1, 8, 1, -1, 5, -1, 0, -1, -1, -1, -1, -1, 1, 1, 0, -1, 8, 6, -1, -1, -1, -1, -1, -1, 3,
							5, 9, 4, 1, 9, -1, -1, -1, -1, -1, 6, -1, -1, -1, -1, -1, -1, 5, -1, -1, 9, -1, -1, 0, -1, -1,
							-1, -1, 0, 1, -1, 3, -1, 8, -1, 1, -1, -1, -1, -1, 2, 5, 6, 2, 6, -1, 6, 6, 4, -1, 9, -1, -1,
							-1, -1, 5, 8, -1, -1, -1, -1, -1, 1, -1, -1, -1, -1, 5, -1, 7, -1, -1, -1, -1, 9, 4, 2, 1, 8,
							-1, -1, -1, 3, 4, -1, -1, -1, -1, -1, -1, -1, 5, -1, -1, -1, -1, -1, 4, -1, 9, -1, -1, -1, 3,
							-1, -1, 3, -1, -1, 7, 4, 1, -1, -1, -1, -1, -1, -1, -1, -1, 7, 8, -1, 9, 0, -1, -1, -1, 2, 6,
							-1, 8, -1, -1, -1, -1, -1, 2, -1, 4, 2, -1, -1, 6, 8, -1, -1, -1, -1, -1, 4, -1, -1
			};
		return createTree(vals);
	}
	public static TreeNode smallBst1() {
		int[] vals = new int[] {35, 20, 10, 30, 6, 16, 26, 36, 4, 8, 12, 18, 24, 28, 32, 38, -1, -1, -1, -1, -1, 14, -1, -1,
							-1, -1, -1, -1, -1, 34, -1, -1, -1, -1, -1, -1};
		return createTree(vals);
		
	}
	
	public static BinaryNode smallTree()
	{
		// A - 65, B - 66, C - 67, D - 68, E - 69, F - 70, G - 71, H - 72, I - 73
		// J - 74, K - 75, L - 76, M - 77, N - 78, O - 79, P - 80, Q - 81, R - 82
		// S - 83, T - 84, U - 85, V - 86, W - 87, X - 88, Y - 89, Z - 90
		//
		/*						N
							  /   \
							H       P
						  /  \       \
						 B	  J       Q
						      /
					         I
		*/
		BinaryTreeBase t = new BinaryTreeBase();
		t.insert( new BinaryNode((char)78)); // N (Q)
		t.insert( new BinaryNode((char)72)); // H
		t.insert( new BinaryNode((char)66)); // B
		t.insert( new BinaryNode((char)74)); // J
		t.insert( new BinaryNode((char)73)); // I
		t.insert( new BinaryNode((char)80)); // P (R)
		t.insert( new BinaryNode((char)81)); // Q (T)

		if(null != t.root()) {
			System.out.println("Created Small Tree");
			t.root().prettyPrint();
		}
		return t.root();
	}

	public static BinaryNode complexTree()
	{
		//
		// A - 65, B - 66, C - 67, D - 68, E - 69, F - 70, G - 71, H - 72, I - 73
		// J - 74, K - 75, L - 76, M - 77, N - 78, O - 79, P - 80, Q - 81, R - 82
		// S - 83, T - 84, U - 85, V - 86, W - 87, X - 88, Y - 89, Z - 90
		//
		/*						Q
							  /   \
							H       U
						  /  \     /  \
						 B	  J   R    V
						/\    /\   \    \
					   A  G  I  K   S    X
						 /       \   \   /\
						D         L   T W  Z
					   / \         \       /
					  C   E         N     Y
						   \       / \
							F     M   O
							           \
									    P

		*/
		//char tree[] = new char[] {1,2,3,4,5,6,7,8,-1,9,-1,-1,12,14,17,-1,-1,-1,10,13,-1,15,-1,-1,-1,-1,-1,-1,-1,-1,-1,11,-1,-1,-1,-1,16};
		BinaryTreeBase t = new BinaryTreeBase();
		t.insert( new BinaryNode((char)81)); // Q
		t.insert( new BinaryNode((char)72)); // H
		t.insert( new BinaryNode((char)66)); // B
		t.insert( new BinaryNode((char)65)); // A
		t.insert( new BinaryNode((char)71)); // G
		t.insert( new BinaryNode((char)68)); // D
		t.insert( new BinaryNode((char)67)); // C
		t.insert( new BinaryNode((char)69)); // E
		t.insert( new BinaryNode((char)70)); // F
		t.insert( new BinaryNode((char)74)); // J
		t.insert( new BinaryNode((char)73)); // I
		t.insert( new BinaryNode((char)75)); // K
		t.insert( new BinaryNode((char)76)); // L
		t.insert( new BinaryNode((char)78)); // N
		t.insert( new BinaryNode((char)77)); // M
		t.insert( new BinaryNode((char)79)); // O
		t.insert( new BinaryNode((char)80)); // P
		t.insert( new BinaryNode((char)85)); // U
		t.insert( new BinaryNode((char)82)); // R
		t.insert( new BinaryNode((char)83)); // S
		t.insert( new BinaryNode((char)84)); // T
		t.insert( new BinaryNode((char)86)); // V
		t.insert( new BinaryNode((char)88)); // X
		t.insert( new BinaryNode((char)87)); // W
		t.insert( new BinaryNode((char)90)); // Z
		t.insert( new BinaryNode((char)89)); // Y
		//BinaryNode f =  new BinaryNode((char)70);
		//t.insert(f); // F

		if(null != t.root())
			System.out.println("Created Complex Tree");
		
		return t.root();
	}
}

