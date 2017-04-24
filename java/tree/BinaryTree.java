package tree;

import java.lang.StringBuilder;
import java.util.Stack;
import java.lang.String;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
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

	protected static char min(BinaryNode root) {
		if(root.left() == null && root.right() == null) return root.value();
		char l = 128, r = 128;
		if(root.left() != null) 
			l = min(root.left());
		if(root.right() != null) 
			r = min(root.right());
		return (l < r)?l:r;
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
				if(peeknode.right() == null || prev == peeknode.right()) {
					node = stack.pop();
					prev = node;
					node = null;
				}
				else {
					node = peeknode.right();
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
		return anc2(node, ch).toString();
	}
	private static StringBuilder anc2(BinaryNode node, char ch) {
		if(node == null) {
			return new StringBuilder();
		}
		if(node.value() == ch) {
			return new StringBuilder().append(ch).append(" ");
		}

		StringBuilder s = anc2(node.left(), ch);
		if(s.length() == 0) {
			s = anc2(node.right(), ch);
		}
		if(s.length() > 0) {
			s.append(node.value()).append(" ");
		}

		return s;
	}

	public static BinaryNode buildFromInPre(char[] in, char[] pre) {
		if(in == null || pre == null || in.length == 0 || (in.length != pre.length)) return null;
		return buildFromInPre(in, 0, in.length-1, pre, 0);
	}
	private static BinaryNode buildFromInPre(char[] in, int inBegin, int inEnd, char[] pre, int prePos)
	{
		if(inEnd < inBegin)
			return null;
		
		BinaryNode node = new BinaryNode(pre[prePos]);

		int inRoot = inBegin;
		for(inRoot = inBegin; inRoot <= inEnd; inRoot++) {
			if(in[inRoot] == pre[prePos]) 
				break;
		}
		int leftSz = inRoot-inBegin;

		// if inRoot is first inOrder node, left tree is null
		BinaryNode left = buildFromInPre(in, inBegin, inRoot-1, pre, prePos+1);
		node.left(left);

		// if preRight is last preOrder node, right tree is null
		BinaryNode right = buildFromInPre(in, inRoot+1, inEnd, pre, prePos+leftSz+1);
		node.right(right);

		return node;
	}
	public static BinaryNode buildFromInPost(char[] in, char[] post) {
		if(in == null || post == null || in.length == 0 || (in.length != post.length)) return null;
		return buildFromInPost(in, 0, in.length-1, post, post.length-1);
	}
	public static BinaryNode buildFromInPost(char[] in, int inBegin, int inEnd, char[] post, int pEnd)
	{
		BinaryNode node = new BinaryNode(post[pEnd]);

		int i = inBegin;
		for(; i <= inEnd; i++) {
			if(in[i] == post[pEnd]) 
				break;
		}
		int leftSz = i-inBegin;

		// if i is first inOrder node, left tree is null
		if(i > inBegin) {
			//BinaryNode left = buildFromInPost(in, inBegin, i-1, post, pBegin, pBegin+leftSz-1);
			BinaryNode left = buildFromInPost(in, inBegin, i-1, post, pEnd - (inEnd - i) -1);
			node.left(left);
		}

		// if postRight is last postOrder node, right tree is null
		if(i < inEnd) {
			//BinaryNode right = buildFromInPost(in, i+1, inEnd, post, pBegin+leftSz, pEnd-1);
			BinaryNode right = buildFromInPost(in, i+1, inEnd, post, pEnd-1);
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
		int dia = Math.max(1 + left.depth() + right.depth(), Math.max(left.dia(), right.dia()));
		int depth = 1 + Math.max(left.depth(), right.depth());
		return new LevelInfo(depth, dia);
	}

	public static boolean isBalanced(BinaryNode root) {
		return (-1 != checkHeight(root));
	}
	private static int checkHeight(BinaryNode node)
	{
		if(node == null) return 0;

		int l = checkHeight(node.left());
		if(l == -1) return -1;

		int r = checkHeight(node.right());
		if(r == -1) return -1;

		if(Math.abs(l - r) > 1) {
			return -1;
		}

		return 1 + Math.max(l, r);
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
	
	// makeinjava.com BEGIN ....
	// identical trees have same structure, AND data
	public static boolean identical(BinaryNode t1, BinaryNode t2) {
		if(t1 == null && t2 == null) return true;
		if(t1 == null || t2 == null) return false;
		if(t1.val != t2.val) return false;

		return identical(t1.left, t2.left) && identical(t1.right, t2.right);
	}
	// mirror image trees have mirror structure, AND data
	public boolean mirrorImage(TreeNode t1, TreeNode t2)
	{
		if(t1 == null && t2 == null) return true;
		if(t1 == null || t2 == null) return false;
		if(t1.val != t2.val) return false;

		return mirrorImage(t1.left, t2.right) && mirrorImage(t1.right, t2.left);
	}
	// isomorphic trees have same structure, ignore data
	public boolean isomorphic(TreeNode t1, TreeNode t2)
	{
		if(t1 == null && t2 == null) return true;
		if(t1 == null || t2 == null) return false;
		return isomorphic(t1.left, t2.left) && isomorphic(t1.right, t2.right);
	}
	// mirror trees have mirrored structure, ignore data
	public boolean mirror(TreeNode t1, TreeNode t2)
	{
		if(t1 == null && t2 == null) return true;
		if(t1 == null || t2 == null) return false;

		return mirror(t1.left, t2.right) && mirror(t1.right, t2.left);
	}
	// quasi isomorphic trees can be eithered mirrored/isomorphic structure, INDEPENDENTLY at any level
	public boolean quasiIso(TreeNode t1, TreeNode t2)
	{
		if(t1 == null && t2 == null) return true;
		if(t1 == null || t2 == null) return false;

		if(quasiIso(t1.left, t2.left) && quasiIso(t1.right, t2.right)) {
			return true;
		}
		return quasiIso(t1.left, t2.right) && quasiIso(t1.right, t2.left);
	}
	
	public static List<Character> nodesFromLeaf(BinaryNode t, int k) {
		ArrayList<Character> res = new ArrayList<>();
		if(k >= 0) {
			nodesFromLeaf(t, k, "", res);
		}
		return res;
	}
	private static void nodesFromLeaf(BinaryNode t, int k, String path, List<Character> res) {
		if(t.left == null && t.right == null) {
			String p = path + t.val;
			if(k < p.length()) {
				char ch = p.charAt(p.length()-k-1);
				if(!res.contains(ch))
					res.add(ch);
			}
		}
		if(t.left != null) {
			nodesFromLeaf(t.left, k, path + t.val, res);
		}
		if(t.right != null) {
			nodesFromLeaf(t.right, k, path + t.val, res);
		}
	}

	public static ArrayList<Integer> maxSumPath(TreeNode t) {
		if(t == null) return new ArrayList<Integer>();
		HashMap<Integer, ArrayList<Integer>> res = new HashMap<>();

		int[] path = new int[1024];
		maxSumPath(t, path, 0, 0, res);
		int key = res.keySet().iterator().next();
		return res.get(key);
	}
	private static ArrayList<Integer> clone(int[] arr, int size) {
		ArrayList<Integer> res = new ArrayList<>();
		for(int i = 0; i < size; i++) {
			res.add(arr[i]);
		}
		return res;
	}
	private static void maxSumPath(TreeNode t, int[] path, int size, int sum, HashMap<Integer, ArrayList<Integer>> res) {
		sum += t.val;
		path[size++] = t.val;
		if(t.left == null && t.right == null) {
			if(!res.isEmpty()) {
				int key = res.keySet().iterator().next();
				if(key >= sum)
					return;
				res.remove(key);
			}
			res.put(sum, clone(path, size));
		}
		if(t.left != null) {
			maxSumPath(t.left, path, size, sum, res);
		}
		if(t.right != null) {
			maxSumPath(t.right, path, size, sum, res);
		}
	}
	// makeinjava.com END ....

	public static void main(String[] args)
	{
		BinaryNode root = smallTree();
		System.out.println("Tree is " + (isBalanced(root)?"":"NOT ") + "Balanced");
		btCreates(root);
		btSimple(root);
		btHard(root);
		testnodesFromLeaf(root);

		root = complexTree();
		System.out.println("Tree is " + (isBalanced(root)?"":"NOT ") + "Balanced");
		btCreates(root);
		btSimple(root);
		btHard(root);
		testnodesFromLeaf(root);
		
		btComplex();
	}
	private static void btSimple(BinaryNode t)
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
        System.out.println("Height: " + t.height());
	}
	private static void btHard(BinaryNode t)
	{
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
		BinaryNode node1 = buildFromInPre(in.toCharArray(), 0, in.length()-1, pre.toCharArray(), 0);
		String post = BinaryTreeTraversals.postOrderIterNew(t);
		BinaryNode node2 = buildFromInPost(in.toCharArray(), 0, in.length()-1, post.toCharArray(), post.length()-1);

		String in2 = BinaryTreeTraversals.inOrderIterWiki(node1);
		String pre2 = BinaryTreeTraversals.preOrderIterWiki(node1);
		String post2 = BinaryTreeTraversals.postOrderIterNew(node1);
		System.out.println("Build-pre/post::" + "pre & post trees identical ? " + (identical(node1, node2)? "YES": "NO"));
		System.out.println("  Orig in-ord : " + in + " In Order: " + (in.equals(in2)? "YES": "NO"));
		System.out.println("  Orig pre-ord : " + pre + " Pre Order: " + (pre.equals(pre2)? "YES": "NO"));
		System.out.println("  Orig post-ord : " + post + " Post Order: " + (post.equals(post2)? "YES": "NO"));

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
	private static void testnodesFromLeaf(BinaryNode root) {
		System.out.println("nodesFromLeaf 0: " + nodesFromLeaf(root, 0));
		System.out.println("nodesFromLeaf 1: " + nodesFromLeaf(root, 1));
		System.out.println("nodesFromLeaf 2: " + nodesFromLeaf(root, 2));
		System.out.println("nodesFromLeaf 3: " + nodesFromLeaf(root, 3));
		System.out.println("nodesFromLeaf 4: " + nodesFromLeaf(root, 4));
		System.out.println("nodesFromLeaf 5: " + nodesFromLeaf(root, 5));
		System.out.println("nodesFromLeaf 6: " + nodesFromLeaf(root, 6));
		System.out.println("nodesFromLeaf 7: " + nodesFromLeaf(root, 7));
		System.out.println("nodesFromLeaf 8: " + nodesFromLeaf(root, 8));
	}
	private static void btComplex() {
		TreeNode root = BinaryTreeBase.smallTree4();
		System.out.println("maxSumPath: " + maxSumPath(root));
	}
}
