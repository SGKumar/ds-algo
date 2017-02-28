package tree;

import java.lang.StringBuilder;
import java.util.Stack;
import java.lang.String;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.Map;
import java.util.Collection;

public class BinaryTree extends BinaryTreeBase
{
	// Constructors
	public BinaryTree()
	{
		super();
	}
	public BinaryTree(BinaryNode tree)
	{
		super(tree);
	}

	static class LevelInfo
	{
		private int depth;
		private int diameter;
		LevelInfo(int depth, int diameter)
		{
			this.depth = depth;
			this.diameter = diameter;
		}
		public int depth() { return depth; }
		public int dia() { return diameter; }
	}

	public static String ancestors(BinaryNode node, char ch)
	{
		StringBuilder str = new StringBuilder();
		Stack<BinaryNode> stack = new Stack<>();
		BinaryNode prev = null;

		while(!stack.isEmpty() || node != null) {
			if(node != null) {
				if(node.value() == ch) {
					str.append(node.value()).append(" ");
					break;
				}
				stack.push(node);
				node = node.left();
			}
			else {
				BinaryNode peeknode = stack.peek();
				if(prev != peeknode.right() && peeknode.right() != null)
					node = peeknode.right();
				else {
					node = stack.pop();
					prev = node;
					node = null;
				}
			}
		}

		while(!stack.isEmpty()) {
			str.append(stack.pop().value()).append(" ");
		}

		return str.toString();
	}

	private static String ancestors2(BinaryNode node, char ch)
	{
		if(node == null) {
			return "";
		}
		if(node.value() == ch) {
			return String.valueOf(ch);
		}

		String s = ancestors2(node.left(), ch);
		if("".equals(s)) {
			s = ancestors2(node.right(), ch);
		}
		if(!"".equals(s)) {
			return (new StringBuilder()).append(node.value()).append(" ").append(s).toString();
		}

		return "";
	}

	public static BinaryNode buildTreeFromInAndPre(char[] in, int inBegin, int inEnd, char[] pre, int preBegin, int preEnd)
	{
		//System.out.printf("inBegin %d inEnd %d preBegin %d preEnd %d\n", inBegin, inEnd, preBegin, preEnd);
		//if(inBegin > inEnd || preBegin > preEnd)
		//	return null;

		BinaryNode node = new BinaryNode(pre[preBegin]);

		if(inBegin == inEnd || preBegin == preEnd)
			return node;

		// pre[preBegin] preOrder gives root, inOrder gives left/right of root
		int inRoot = findInArray(in, inBegin, inEnd, pre[preBegin]);

		// segregate left/right in preOrder
		//int preRight = findInArray(pre, preBegin+1, preEnd, in[inLeft+1]);
		int preRight = preBegin+inRoot-inBegin;

		// if inRoot is first inOrder node, left tree is null
		if(inRoot != inBegin)
		{
			BinaryNode left = buildTreeFromInAndPre(in, inBegin, inRoot-1, pre, preBegin+1, preRight);
			node.left(left);
		}

		// if preRight is last preOrder node, right tree is null
		if(preRight != preEnd)
		{
			BinaryNode right = buildTreeFromInAndPre(in, inRoot+1, inEnd, pre, preRight+1, preEnd);
			node.right(right);
		}
		return node;
	}
	public BinaryNode getTreeFromPreAllFullNodes(char[] pre, int preBegin, int preEnd)
	{
		// error condition when this is called with trees unfilled.
		if(preBegin > preEnd)
		{
			return null;
		}
		BinaryNode node = new BinaryNode(pre[preBegin]);
		if(pre[preBegin] == 'L')
		{
			node.preIL = preBegin;
			return node;
		}

		// set preIL variable to hold last node processed for left tree
		BinaryNode temp = getTreeFromPreAllFullNodes(pre, preBegin+1, preEnd);
		if(temp == null)
		{
			return null;
		}
		else
		{
			node.left(temp);
		}

		temp = getTreeFromPreAllFullNodes(pre, temp.preIL+1, preEnd);
		if(temp == null)
		{
			return null;
		}
		else
		{
			node.right(temp);
		}
		return node;
	}
	protected static int findInArray(char[] chs, int begin, int end, char val)
	{
		for(int i = begin; i <= end; i++) {
			if(chs[i] == val) {
				return i;
			}
		}
		return -1;
	}

	// CTCI Cracking the Coding Interview BEGIN ....
	public static ArrayList<LinkedList<BinaryNode>> createLevelLinkedList(BinaryNode tree)
	{
		if(tree == null) return null;
		ArrayList<LinkedList<BinaryNode>> lists = new ArrayList<>();
		createLevelLinkedList(tree, lists, 0);
		return lists;
	}
	private static void createLevelLinkedList(BinaryNode tree, ArrayList<LinkedList<BinaryNode>> lists, int level)
	{
		if(tree == null) return;
		LinkedList<BinaryNode> list = null;
		if(lists.size() < level) {
			lists.add(new LinkedList<BinaryNode>());
		}
		list = lists.get(level);
		list.add(tree);
		createLevelLinkedList(tree.left(), lists, level+1);
		createLevelLinkedList(tree.right(), lists, level+1);
	}
	public static ArrayList<LinkedList<BinaryNode>> createLevelLinkedList2(BinaryNode tree)
	{
		if(tree == null) return null;
		ArrayList<LinkedList<BinaryNode>> lists = new ArrayList<>();
		LinkedList<BinaryNode> list = new LinkedList<>();

		list.add(tree);
		lists.add(list);
		for(int level = 0; lists.size() > level; level++) {
			list = lists.get(level);
			LinkedList<BinaryNode> newList = new LinkedList<>();
			BinaryNode node = null;
			for(int i = 0; i < list.size(); i++) {
				node = list.get(i);
				if(node.left() != null) {
					newList.add(node.left());
				}
				if(node.right() != null) {
					newList.add(node.right());
				}
			}
			if(!newList.isEmpty()) {
				lists.add(newList);
			}
		}
		return lists;
	}

	public static int diameter(BinaryNode root)
	{
		return diaHelper(root).dia();
	}
	private static LevelInfo diaHelper(BinaryNode node)
	{
		if(node == null) {
			return new LevelInfo(0, 0);
		}
		LevelInfo left = diaHelper(node.left());
		LevelInfo right = diaHelper(node.right());
		int dia = Math.max(left.depth() + right.depth(), Math.max(left.dia(), right.dia()));
		int depth = 1 + Math.max(left.depth(), right.depth());
		return new LevelInfo(depth, dia);
	}

	public static boolean isBalanced(BinaryNode root) {
		return (Integer.MIN_VALUE != checkHeight(root));
	}
	private static int checkHeight(BinaryNode node)
	{
		if(node == null) return 0;

		int leftHt = checkHeight(node.left());
		if(leftHt == Integer.MIN_VALUE) {
			return Integer.MIN_VALUE;
		}
		int rightHt = checkHeight(node.right());
		if(rightHt == Integer.MIN_VALUE) {
			return Integer.MIN_VALUE;
		}
		if(Math.abs(leftHt - rightHt) > 1) {
			return Integer.MIN_VALUE;
		}
		else {
			return 1 + Math.max(leftHt, rightHt);
		}
	}

	protected BinaryNode getNode(char c)
	{
		return BinaryTree.getNode(root, c);
	}
	private static BinaryNode getNode(BinaryNode node, char c)
	{
		if(node == null) return null;
		if(node.value() == c) return node;
		BinaryNode temp = getNode(node.left(), c);
		if(temp == null) {
			temp = getNode(node.right(), c);
		}
		return temp;
	}

	public static BinaryNode lca(BinaryNode root, char c1, char c2)
	{
		if(isPresent(root, c1) && isPresent(root, c2)) {
			return lcaHelper(root, c1, c2);
		}
		return null;
	}
	private static BinaryNode lcaHelper(BinaryNode node, char c1, char c2)
	{
		if(node == null || node.value() == c1 || node.value() == c2) {
			return node;
		}
		BinaryNode leftFind = lcaHelper(node.left(), c1, c2);
		BinaryNode rightFind = lcaHelper(node.right(), c1, c2);
		if(leftFind != null && rightFind != null) {
			return node;
		}
		return (leftFind != null)?leftFind:rightFind;
	}
	protected static boolean isPresent(BinaryNode node, char c)
	{
		if(node == null) return false;
		if(node.value() == c) return true;
		return isPresent(node.left(), c) || isPresent(node.right(), c);
	}
	// CTCI Cracking the Coding Interview END ....
	
	// 2 trees are isomorphic if their structure is the same.
	public boolean isomorphic(BinaryNode t1, BinaryNode t2)
	{
		if(t1 == null && t2 == null) {
			return true;
		}
		if((t1 == null && t2 != null) || (t1 != null && t2 == null)) {
			return false;
		}
		return isomorphic(t1.left(), t2.left()) && isomorphic(t1.right(), t2.right());
	}
	// bible, geek4geeks.com leetcode END
	
	protected static int nextPowerOf2(int in)
	{
		// in > 0
		int levels = 1, nodes = in;
		while((nodes = nodes >>> 1) > 0)
		{
			levels++;
		}
		//System.out.printf("nextPowerOf2 of %d is :%d %d\n", in, levels, 1<<levels);
		return levels;
	}

	public static void main(String[] args)
	{
		BinaryNode root = smallTree();
		System.out.println("Tree is " + (isBalanced(root)?"":"NOT ") + "Balanced");
		btCreates(root);
		btHardQues(root);

		root = complexTree();
		System.out.println("Tree is " + (isBalanced(root)?"":"NOT ") + "Balanced");
		btCreates(root);
		btHardQues(root);
	}
	private static void btHardQues(BinaryNode t)
	{
        // ancestors
        System.out.println("anc iter of C are\t : " + ancestors(t, 'C'));
        System.out.println("anc iter of F are\t : " + ancestors(t, 'F'));
        System.out.println("anc iter of I are\t : " + ancestors(t, 'I'));
        System.out.println("anc iter of M are\t : " + ancestors(t, 'M'));
        System.out.println("anc iter of Q are\t : " + ancestors(t, 'Q'));
        System.out.println("anc iter of R are\t : " + ancestors(t, 'R'));
        System.out.println("anc -rec of C are\t : " + ancestors2(t, 'C'));
        System.out.println("anc -rec of F are\t : " + ancestors2(t, 'F'));
        System.out.println("anc -rec of I are\t : " + ancestors2(t, 'I'));
        System.out.println("anc -rec of M are\t : " + ancestors2(t, 'M'));
        System.out.println("anc -rec of Q are\t : " + ancestors2(t, 'Q'));
        System.out.println("anc -rec of R are\t : " + ancestors2(t, 'R'));

        System.out.println("Diameter: " + diameter(t));

		System.out.println("LCA (K, M) : " + lca(t, 'K', 'M'));
		System.out.println("LCA (C, F) : " + lca(t, 'C', 'F'));
		System.out.println("LCA (A, L) : " + lca(t, 'A', 'L'));
		System.out.println("LCA (A, Z) : " + lca(t, 'A', 'Z'));
		System.out.println("LCA (Q, Z) : " + lca(t, 'Q', 'Z'));

	}
	private static void btCreates(BinaryNode t)
	{
		System.out.println("TODO Need to test BuildBT new functions later with these inputs");

		String in = BinaryTreeTraversals.inOrderIterWiki(t);
		String pre = BinaryTreeTraversals.preOrderIterWiki(t);
		BinaryNode node = buildTreeFromInAndPre(in.toCharArray(), 0, in.length()-1, pre.toCharArray(), 0, pre.length()-1);

		String in2 = BinaryTreeTraversals.inOrderIterWiki(node);
		String pre2 = BinaryTreeTraversals.preOrderIterWiki(node);
		System.out.println("BuildBTWithInPre: ");
		System.out.println("  Orig in-ord : " + in + " In Order: " + (in.equals(in2)? "YES": "NO"));
		System.out.println("  Orig pre-ord : " + pre + " Pre Order: " + (pre.equals(pre2)? "YES": "NO"));

		/*
		BinaryNode root1 = t.BuildBTWithInPre(in, 0, in.length()-1, pre, 0);
		BinaryTree tree1 = new BinaryTree(root1);
		String in1 = tree1.InOSIter();
		String pre1 = tree1.PreOSIter();
		String post1 = tree1.PostOSIter();
		String level1 = tree1.LevelOSIter();
		System.out.println("BuildBTWithInPre: ");
		System.out.println("	In Ord : " + in1 + " In Order: " + (in1.equals(in)? "YES": "NO"));
		System.out.println("	Pre Ord : " + pre1 + " Pre Order: " + (pre1.equals(pre)? "YES": "NO"));
		System.out.println("	Post Ord : " + post1 + " Post Order: " + (post1.equals(post)? "YES": "NO"));
		System.out.println("	Level Ord : " + level1 + " Level Order: " + (level1.equals(level)? "YES": "NO"));

		BinaryNode root2 = t.BuildBTWithInPost(in, 0, in.length()-1, post, post.length()-1);
		BinaryTree tree2 = new BinaryTree(root2);
		String in2 = tree2.InOSIter();
		String pre2 = tree2.PreOSIter();
		String post2 = tree2.PostOSIter();
		String level2 = tree2.LevelOSIter();
		System.out.println("BuildBTWithInPost: ");
		System.out.println("	In Ord : " + in2 + " In Order: " + (in2.equals(in)? "YES": "NO"));
		System.out.println("	Pre Ord : " + pre2 + " Pre Order: " + (pre2.equals(pre)? "YES": "NO"));
		System.out.println("	Post Ord : " + post2 + " Post Order: " + (post2.equals(post)? "YES": "NO"));
		System.out.println("	Level Ord : " + level2 + " Level Order: " + (level2.equals(level)? "YES": "NO"));

		//System.out.println(" :- " + new String("abxcd").replaceAll("[abc]", ""));
		BinaryNode root3 = t.BuildBTWithInLevel(in, 0, in.length()-1, level);
		BinaryTree tree3 = new BinaryTree(root3);
		String in3 = tree3.InOSIter();
		String pre3 = tree3.PreOSIter();
		String post3 = tree3.PostOSIter();
		String level3 = tree3.LevelOSIter();
		System.out.println("BuildBTWithInLevel: ");
		System.out.println("	In Ord : " + in3 + " In Order: " + (in3.equals(in)? "YES": "NO"));
		System.out.println("	Pre Ord : " + pre3 + " Pre Order: " + (pre3.equals(pre)? "YES": "NO"));
		System.out.println("	Post Ord : " + post3 + " Post Order: " + (post3.equals(post)? "YES": "NO"));
		System.out.println("	Level Ord : " + level3 + " Level Order: " + (level3.equals(level)? "YES": "NO"));

		String preIn = "NNLLNLNLL"; //"NNLLNLL";
		BinaryNode root4 = t.BuildBTWithPreIL1(preIn);
		BinaryTree tree4 = new BinaryTree(root4);
		String pre4 = tree4.PreOSIter();
		System.out.println("Pre In: " + preIn + " Pre Out: " + pre4 + " " + (pre4.equals(preIn)? "YES": "NO"));
		*/
	}
	
}
