package tree;

import java.lang.StringBuilder;
//import java.util.Stack;
import java.lang.String;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collection;
import java.util.Arrays;

import llsq.Queue;
import llsq.Stack;

public class BinaryTreeTraversals
{
	public static class Tuple<A, B>
	{
		public final A a;
		public final B b;

		public Tuple(A a, B b) {
			this.a = a;
			this.b = b;
		}
    }

	public static String preOrderIter(BinaryNode node)
	{
		StringBuilder str = new StringBuilder();

		Stack<BinaryNode> stack = new Stack<>();
		while(null != node)
		{
			str.append(node.value());
			if(node.right() != null)
				stack.push(node.right());
			if(node.left() != null)
				stack.push(node.left());

			if(stack.isEmpty())
				node = null;
			else
				node = stack.pop();
		}

		return str.toString();
	}

	public static String preOrderIterWiki(BinaryNode node)
	{
		StringBuilder str = new StringBuilder();

		Stack<BinaryNode> stack = new Stack<>();
		while(!stack.isEmpty() || null != node)
		{
			if(node != null)
			{
				str.append(node.value());
				if(node.right() != null)
					stack.push(node.right());
				node = node.left();
			}
			else
			{
				node = stack.pop();
			}
		}

		return str.toString();
	}
	public static String preOrderIter2016Aug(BinaryNode node)
	{
		StringBuilder str = new StringBuilder();

		Stack<BinaryNode> stack = new Stack<>();
		stack.push(node);
		while(!stack.isEmpty())
		{
			node = stack.pop();
			str.append(node.value());

			if(node.right() != null)
				stack.push(node.right());
			if(node.left() != null)
				stack.push(node.left());
		}
		return str.toString();
	}

	public static String inOrderIterWiki(BinaryNode node)
	{
		StringBuilder str = new StringBuilder();

		Stack<BinaryNode> stack = new Stack<>();
		while(!stack.isEmpty() || null != node)
		{
			if(node != null)
			{
				stack.push(node);
				node = node.left();
			}
			else
			{
				node = stack.pop();
				str.append(node.value());
				node = node.right();
			}
		}
		return str.toString();
	}

	public static String postOrderIterGaneshOld(BinaryNode node)
	{
		StringBuilder str = new StringBuilder();
		Stack<BinaryNode> stack = new Stack<>();
		BinaryNode prev = null;

		while(!stack.isEmpty() || node != null)
		{
			if(node != null)
			{
				stack.push(node);
				node = node.left();
			}
			else
			{
				if(prev != stack.peek().right() && stack.peek().right() != null)
					node = stack.peek().right();
				else
				{
					node = stack.pop();
					str.append(node.value());
					prev = node;
					node = null;
				}
			}
		}

		return str.toString();
	}

	public static String postOrderIterNew(BinaryNode node)
	{
		StringBuilder str = new StringBuilder();
		Stack<BinaryNode> stack = new Stack<>();
		BinaryNode prev = null;

		while(!stack.isEmpty() || node != null) {
			if(node != null) {
				stack.push(node);
				node = node.left();
			}
			else {
				if(stack.peek().right() != null && prev != stack.peek().right()) {
					node = stack.peek().right();
				}
				else {
					node = stack.pop();
					str.append(node.value());
					prev = node;
					node = null;
				}
			}
		}
		return str.toString();
	}

	public static String levelOrderIterGanesh(BinaryNode node)
	{
		StringBuilder str = new StringBuilder();
		Queue<BinaryNode> queue = new Queue<>();
		queue.add(node);

		while(!queue.isEmpty())
		{
			node = queue.remove();
			str.append(node.value());
			if(node.left() != null)
				queue.add(node.left());
			if(node.right() != null)
				queue.add(node.right());
		}
		return str.toString();
	}

	public static String levelOrderIterReverse(BinaryNode node)
	{
		StringBuilder str = new StringBuilder();
		Queue<BinaryNode> queue = new Queue<>();
		queue.add(node);

		Stack<BinaryNode> stack = new Stack<>();
		while(!queue.isEmpty())
		{
			node = queue.remove();
			stack.push(node);

			if(node.right() != null)
				queue.add(node.right());
			if(node.left() != null)
				queue.add(node.left());
		}

		while(!stack.isEmpty())
		{
			str.append(stack.pop().value());
		}
		return str.toString();
	}

	public static String zigzag(BinaryNode root) {
		if(null == root) return "";

		Queue<BinaryNode> q = new Queue<>();
		Stack<BinaryNode> s = new Stack<>();
		StringBuilder str = new StringBuilder();

		BinaryNode node = root;
		q.add(node);
		int currLevel = 1, nextLevel = 0;
		boolean r2l = true;

		while(!q.isEmpty()) {
			node = q.remove();
			currLevel--;
			str.append(node.value());
			if(r2l) {
				if(node.right() != null) {
					s.push(node.right());
					nextLevel++;
				}
				if(node.left() != null) {
					s.push(node.left());
					nextLevel++;
				}
			}
			else {
				if(node.left() != null) {
					s.push(node.left());
					nextLevel++;
				}
				if(node.right() != null) {
					s.push(node.right());
					nextLevel++;
				}
			}
			if(currLevel == 0 && nextLevel > 0) {
				r2l = !r2l;
				currLevel = nextLevel;
				nextLevel = 0;
				while(!s.isEmpty()) {
					q.add(s.pop());
				}
			}
		}
		return str.toString();
	}
	public static String zigzag2(BinaryNode root) {
		if(null == root) return "";

		Stack<BinaryNode> l2r = new Stack<>();
		Stack<BinaryNode> r2l = new Stack<>();
		StringBuilder str = new StringBuilder();

		l2r.push(root);

		while(!l2r.isEmpty() || !r2l.isEmpty()) {
			while(!l2r.isEmpty()) {
				BinaryNode node = l2r.pop();
				str.append(node.value());
				if(node.left() != null) {
					r2l.push(node.left());
				}
				if(node.right() != null) {
					r2l.push(node.right());
				}
			}

			while(!r2l.isEmpty()) {
				BinaryNode node = r2l.pop();
				str.append(node.value());
				if(node.right() != null) {
					l2r.push(node.right());
				}
				if(node.left() != null) {
					l2r.push(node.left());
				}
			}
		}
		return str.toString();
	}

	public static String printLevelOrderLinebyline(BinaryNode node)
	{
		StringBuilder str = new StringBuilder();
		Queue<BinaryNode> queue = new Queue<>();
		queue.add(node);
		int currLevel = 1, nextLevel = 0;
		while(!queue.isEmpty())
		{
			node = queue.remove();
			currLevel--;
			str.append(node.value());
			if(node.left() != null)
			{
				queue.add(node.left());
				nextLevel++;
			}
			if(node.right() != null)
			{
				queue.add(node.right());
				nextLevel++;
			}

			if(currLevel == 0)
			{
				str.append("\n");
				currLevel = nextLevel;
				nextLevel = 0;
			}
		}
		return str.toString();
	}

	// Print inorder successor's value, given parent pointer
	// Usually node is passed, here assume ch is present in the binary tree
	public static BinaryNode inOrderSuccessor(BinaryNode root, char ch)
	{
		BinaryNode node = getNode(root, ch);
		if(node == null) return null;

		BinaryNode next = null;
		if(node.right() != null) {
			// left-most child in right sub-tree
			next = node.right();
			while(next.left() != null) {
				next = next.left();
			}
		}
		else {
			next = node;
			while(next.parent() != null && next.parent().left() != next) {
				next = next.parent();
			}
			next = next.parent();
		}
		
		return next;
	}
	protected static BinaryNode getNode(BinaryNode root, char c)
	{
		if(root == null) return null;
		if(root.value() == c) return root;
		BinaryNode temp = getNode(root.left(), c);
		if(temp == null) {
			temp = getNode(root.right(), c);
		}
		return temp;
	}
	// CTCI Cracking the Coding Interview END ....

	// bible, geek4geeks.com leetcode BEGIN
	private static void vertSum(BinaryNode node, Map<Integer, Integer> colMap, int col)
	{
		if(node == null) return;

		Integer val = new Integer(node.value());
		if(colMap.containsKey(col)) {
			val += colMap.get(col);
		}
		colMap.put(col, val);

		vertSum(node.left(), colMap, col-1);
		vertSum(node.right(), colMap, col+1);
	}
	public static String vertSum(BinaryNode root)
	{
		LinkedHashMap<Integer, Integer> colMap = new LinkedHashMap<>();
		vertSum(root, colMap, 0);

		StringBuilder sb = new StringBuilder(128);
		for(Map.Entry<Integer, Integer> entry: colMap.entrySet()) {
			sb.append("Key : " + entry.getKey() + " Value : " + entry.getValue() + "\n");
		}
		sb.append("\n");
		//Collection<String> colNodes = colMap.values();
		for(Integer val : colMap.values() /*colNodes*/) {
			//System.out.println("Nodes at each col are : " + colNode);
			sb.append(val).append("\n");
		}
		return sb.toString();
	}

	private static void vertOrder1(BinaryNode node, Map<Integer, String> colMap, int col)
	{
		if(node == null) return;

		if(!colMap.containsKey(col)) {
			colMap.put(col, ""); 
		}
		colMap.put(col, colMap.get(col) + node.value() + " ");
		vertOrder1(node.left(), colMap, col-1);
		vertOrder1(node.right(), colMap, col+1);
	}
	public static String vertOrder1(BinaryNode root)
	{
		TreeMap<Integer, String> colMap = new TreeMap<>();
		vertOrder1(root, colMap, 0);

		StringBuilder sb = new StringBuilder(128);
		for(Map.Entry<Integer, String> entry: colMap.entrySet()) {
			sb.append("Key : " + entry.getKey() + " Value : " + entry.getValue() + "\n");
		}
		sb.append("\n");
		Collection<String> colNodes = colMap.values();
		for(String colNode : colNodes) {
			//System.out.println("Nodes at each col are : " + colNode);
			sb.append(colNode).append("\n");
		}
		return sb.toString();
	}
	public static String vertOrder(BinaryNode root)
	{
		if(root == null) return "";
		TreeMap<Integer, String> vMap = new TreeMap<>();
		Queue<Tuple<Integer, BinaryNode>> queue = new Queue<>();
		queue.add(new Tuple<>(0, root));

		int currLevel = 1, nextLevel = 0;
		while(!queue.isEmpty()) {
			Tuple<Integer, BinaryNode> t = queue.remove();
			if(!vMap.containsKey(t.a)) {
				vMap.put(t.a, "");
			}
			vMap.put(t.a, vMap.get(t.a) + t.b.value() + " ");
			if(t.b.left() != null) {
				queue.add(new Tuple<>(t.a-1, t.b.left()));
				nextLevel++;
			}
			if(t.b.right() != null) {
				queue.add(new Tuple<>(t.a+1, t.b.right()));
				nextLevel++;
			}
			if(currLevel == 0) {
				currLevel = nextLevel;
				nextLevel = 0;
			}
		}

		StringBuilder sb = new StringBuilder(128);
		for(Map.Entry<Integer, String> entry: vMap.entrySet()) {
			sb.append("Key : " + entry.getKey() + " Value : " + entry.getValue() + "\n");
		}
		sb.append("\n");
		Collection<String> colNodes = vMap.values();
		for(String colNode : colNodes) {
			//System.out.println("Nodes at each col are : " + colNode);
			sb.append(colNode).append("\n");
		}
		return sb.toString();
	}
	// bible, geek4geeks.com leetcode END

	/*
	private static void MorrisThreadedTraversals()
	{
        BinarySearchTree t1 = new BinarySearchTree();
		t1.insertBST( new BinaryNode((char)71)); // G
		t1.insertBST( new BinaryNode((char)70)); // F
		t1.insertBST( new BinaryNode((char)73)); // I
		t1.insertBST( new BinaryNode((char)72)); // H
		t1.insertBST( new BinaryNode((char)75)); // K
		t1.insertBST( new BinaryNode((char)74)); // J
		t1.insertBST( new BinaryNode((char)76)); // L
		t1.insertBST( new BinaryNode((char)66)); // B
		t1.insertBST( new BinaryNode((char)65)); // A
		t1.insertBST( new BinaryNode((char)69)); // E
		t1.insertBST( new BinaryNode((char)68)); // D
		t1.insertBST( new BinaryNode((char)67)); // C
		// Ignore this
		//System.out.println("MorrisIOT: " + t1.MorrisIOT());
		//System.out.println("MorrisIOT: " + t.MorrisIOT());
	}
	*/
	
	// fill nextSibling pointer when available, all NULL initially.
	public static String setNextRec(BinaryNode node) {
		if(node == null) return "";
		
		if(node.right() != null) {
			node.right().next(nextKid(node));
		}
		if(node.left() != null) {
			node.left().next((node.right() != null)?node.right():nextKid(node));
		}
		return node.toStringFlat() + setNextRec(node.right()) + setNextRec(node.left());
	}
	public static String setNextIter(BinaryNode root) {
		System.out.println("### setNextIter BUG fixed leveraging INTERVIEWBIT same prob");
		if(root == null) return "";
		StringBuilder str = new StringBuilder(512);
		Queue<BinaryNode> queue = new Queue<>();
		queue.add(root);

		while(!queue.isEmpty()) {
			BinaryNode node = queue.remove();

			if(node.left() != null) {
				node.left().next((node.right() != null)?node.right():nextKid(node));
				queue.add(node.left());
			}
			if(node.right() != null) {
				node.right().next(nextKid(node));
				queue.add(node.right());
			}
			str.append(node.toStringFlat());
		}
		return str.toString();
	}
	// node will never be null
	private static BinaryNode nextKid(BinaryNode node) {
		BinaryNode temp = null;
		while(node.next() != null) {
			if(node.next().left() != null) {
				temp = node.next().left();
				break;
			}
			if(node.next().right() != null) {
				temp = node.next().right();
				break;
			}
			node = node.next();
		}
		return temp;
	}
	public static String nextIter(TreeNode root) {
		System.out.println("### nextIter MOST ELEGANT do curr level NOT look-ahead like before");
		if(root == null) return "";

		StringBuilder str = new StringBuilder(512);
		Queue<TreeNode> queue = new Queue<>();
		queue.add(root);

		int currLevel = 1, nextLevel = 0;
		while(!queue.isEmpty()) {
			TreeNode node = queue.remove();
			currLevel--;
			if(node.left != null) {
				queue.add(node.left);
				nextLevel++;
			}
			if(node.right != null) {
				queue.add(node.right);
				nextLevel++;
			}
			
			str.append(node.val);
			// Not end of list @curr level
			if(currLevel > 0) {
				node.next = queue.peek();
				str.append("->").append(node.next).append("\t");
			}
			else {
				currLevel = nextLevel;
				nextLevel = 0;
				str.append("\t");
			}
		}
		return str.toString();
	}

	public static void main(String[] args)
	{
		BinaryNode t = BinaryTree.smallTree();
		traversals(t);
		btHardQues(t);
		btBibleG4GLeet(t);

		t = BinaryTree.complexTree();
		traversals(t);
		btHardQues(t);
		btBibleG4GLeet(t);
		
		char[] cs = new char[10];
		for(int i = 0; i < 5; i++) {
			cs[i] = (char)(65+i);
		}
		String s = new String(cs, 0, 5);
		System.out.println("slen " + s.length() + " s " + s + " cs #" + Arrays.toString(cs) + "#");
	}
	
    private static void traversals(BinaryNode t /*root*/) {

		System.out.println("Pre-Order Iter Ganesh  : " + preOrderIter(t));
		System.out.println("Pre-Order Iter Wiki    : " + preOrderIterWiki(t));
		System.out.println("Pre-Order Iter Aug2016 : " + preOrderIter2016Aug(t));

        System.out.println("In-Order Iter Wiki     : " + inOrderIterWiki(t));
        System.out.println("Post-Order Iter New    : " + postOrderIterNew(t));
        System.out.println("Post-Order Iter Ganesh : " + postOrderIterGaneshOld(t));
        System.out.println("Level-Order Ganesh     : " + levelOrderIterGanesh(t));
        System.out.println("Level-Order Reverse    : " + levelOrderIterReverse(t));
        System.out.println("Level-Order Linebyline : " + printLevelOrderLinebyline(t));

		/*
		System.out.println("Reverse In-Order Traversal Recursive: " + t.RevInOSRec());
		System.out.println("Reverse In-Order Traversal Iterative: " + t.RevInOSIter());

		System.out.println("BFSRev:: Level-Order Search Iterative R2L: " + t.LevelOSR2LIter());
		System.out.println("BFSRev:: Level-Order Search Iterative Reverse: " + t.LevelOSReverseIter());
		*/
		System.out.println("zigzag : " + zigzag(t));
		System.out.println("zigzag2: " + zigzag2(t));
		//System.out.println("BFSZigZag:: Level-Order Search ZigZag New : " + t.LevelOSZigZagIter());

        // Vertical Sum
        System.out.println("Vertical Sum : " + vertSum(t));

		System.out.println("### setNextRec CODED leveraging INTERVIEWBIT same prob");
		System.out.println("### setNextRec CODED USING reverse pre-order search");
        System.out.println("sibling rec : " + setNextRec(t));
        System.out.println("sibling iter: " + setNextIter(t));
		
		TreeNode tn = BinaryTreeBase.smallTree3();
        System.out.println("nextIter best: " + nextIter(tn));
    }
	
	private static void btHardQues(BinaryNode t)
	{
		// inorder successor
		System.out.println("Inorder successor of I is : " + inOrderSuccessor(t, 'I'));
		System.out.println("Inorder successor of Q is : " + inOrderSuccessor(t, 'Q'));

	}
	private static void btBibleG4GLeet(BinaryNode t)
	{
		//int[] vals = new int[] {19, 1, 2, 3, 4, 5, 6, 7, -1, -1, -1, -1, -1, 8, -1, 9, -1, -1, -1, -1};
		//TreeNode root = BinaryTreeBase.createTree(vals);

		System.out.println("Vertical Order : \n" + vertOrder(t));
		System.out.println("Vertical Order1: \n" + vertOrder1(t));
		System.out.println("Vertical Sum : \n" + vertSum(t));
	}
}
