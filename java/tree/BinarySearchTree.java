package tree;

import llsq.ListNode;
import llsq.LinkedList;
import llsq.Stack;

// Binary Tree
// DONE. Write pseudocode to add a new node to a Binary Search Tree (BST).
// DONE. check if a given binary tree is a binary search tree or not.
// DONE. implement the preorder(), inorder() and postorder() traversals. What's their time complexities?
// DONE. iterative preorder, inorder and postorder tree traversals.
// DONE. WJC iterative & recursive Preorder, Inorder and Postorder tree traversals.
// DONE. Implement Breadth First Search (BFS - Level Order) and Depth First Search (DFS - PreOrder).
// DONE. implement level order traversal of a tree.
// DONE. set Grand-Parent of every node in Binary tree.  (Interview Ques)
// DONE. find the Maximum Value of a tree (both recursive and iterative).
// DONE. search for a value in a binary tree (both recursive and iterative).
// DONE. Add new nodes to Binary Tree (used Level Order)
// DONE. determine the number of elements (or size) in a tree.
// DONE. Print Level Order in reverse order : L3 L2 L1 (Within Li in proper order)
// DONE. find the depth or height of a tree (both iter/rec).
// EASY. delete a node from a Binary Tree.
// EASY. delete a tree (i.e, free up its nodes). Post Order
// EASY. count the number of leaves in a tree - both recursive (return 1 when L & R are null) / iterative (level order)
// EASY. Java code to count the no. of full nodes in a tree - both recursive (return 1 when L & R are NOT null) / iterative (level order)
// EASY. Java code to count the no. of half nodes in a tree - both recursive (return 1 when only one of L | R are null) / iterative (level order)
// EASY. Java code to check if two trees are structurally identical
// EASY. find sum of all elements in a binary tree. (recursion/ iteration)
// DONE. find diameter of a binary tree
// EASY. find the mininum value in a binary search tree.
// DONE. compute the maximum depth in a tree (both recursive and iterative).
// EASY. find the level which is having maximum sum - similar to max depth iter, instead of counter, sum and retain max level
// EASY. Level Order Zig Zag Traversal.
// EASY. print all root-to-leaf paths, can use array with filled, or fill an ArrayList or (Array of arrays) & print them - Recursive
// EASY. check the existence of a path of given sum - Recursive - progressively decrease sum with node value...
// EASY. convert a Binary tree to its mirror (left nodes become right and right nodes become left)!.
// EASY. check if two trees are mirrors of each other.
// DONE. print ancestors of a given node.
// DONE. Vertical Sum of Binary Tree (Interview Ques)
// EASY. Every node to the right something... (Interview Ques)
// QUES. Can you construct a tree using postorder and preorder traversal? YES Figure this out, but normally Inorder and another order should be given...
// DONE. Construct a tree given its inorder and preorder traversal strings.
// DONE. Similarly construct a tree given its inorder and post order traversal strings.
// DONE. Similarly construct a tree given its inorder and level order traversal strings.
// DONE. Get nth node of an inorder traversal of a BT.
// EASY. create a copy of a tree.
// TBD. delete a node from a Binary Search Tree.
// EASY. search for a value in a binary search tree (BST).
// Find the closest ancestor of two nodes in a tree.
// Given an expression tree, evaluate the expression and obtain a paranthesized form of the expression.
// How do you convert a tree into an array?
// What is an AVL tree?
// How many different trees can be constructed using n nodes?
// A full N-ary tree has M non-leaf nodes, how many leaf nodes does it have?
// What is a threaded binary tree?

public class BinarySearchTree extends BinaryTree
{
	public BinarySearchTree()
	{
		super(null);
	}
	public BinarySearchTree(BinaryNode tree)
	{
		super(tree);
	}

	// CTCI Cracking the Coding Interview BEGIN ....
	static class NodeInfo
	{
		private ListNode list;
		private BinaryNode tree;
		NodeInfo(ListNode listNode, BinaryNode treeNode)
		{
			this.list = listNode;
			this.tree = treeNode;
		}
		public ListNode list() { return list; }
		public BinaryNode tree() { return tree; }
	}


	//private BinaryNode getRoot() { return root; }
	public static boolean isBST(BinaryNode root) {  return isBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE); }
	private static boolean isBST(BinaryNode tree, int min, int max)
	{
		if(null == tree) return true;
		if(tree.value() <= min || tree.value() >= max) return false;

		return isBST(tree.left(), min, tree.value()) && isBST(tree.right(), tree.value(), max);
	}

	public static boolean isBstInorder(BinaryNode root) {  return isBstInorder(root, Integer.MIN_VALUE); }
	private static boolean isBstInorder(BinaryNode tree, int prev)
	{
		if(null == tree) return true;
		if(isBstInorder(tree.left(), prev)) {
			if(tree.value() <= prev) return false;
			return isBstInorder(tree.right(), tree.value());
		}
		return false;
	}

	public static boolean isBstRevIn(BinaryNode root) {  return isBstRevIn(root, Integer.MIN_VALUE); }
	private static boolean isBstRevIn(BinaryNode tree, int min)
	{
		if(null == tree) return true;
		if(tree.value() <= min) return false;

		return isBstRevIn(tree.left(), min) && isBstRevIn(tree.right(), tree.value());
	}

	public static int ceiling(TreeNode root, int val) {
		TreeNode node = root;
		int ceil = Integer.MAX_VALUE;
		while(node != null && node.val <= ceil) {
			if(node.val == val) {
				ceil = val;
				break;
			}
			if(node.val < val) {
				node = node.right;
			}
			else {
				ceil = node.val;
				node = node.left;
			}
		}
		if(ceil == Integer.MAX_VALUE) {
			ceil = -1;
		}
		return ceil;
	}
	public static int floor(TreeNode root, int val) {
		TreeNode node = root;
		int flr = Integer.MIN_VALUE;
		while(node != null && node.val >= flr) {
			if(node.val == val) {
				flr = val;
				break;
			}
			if(node.val > val) {
				node = node.left;
			}
			else {
				flr = node.val;
				node = node.right;
			}
		}
		if(flr == Integer.MIN_VALUE) {
			flr = -1;
		}
		return flr;
	}
	
	public static BinaryNode arrayToBalancedBST(char[] array)
	{
		return arrayToBalancedBST(array, 0, array.length-1);
	}
	private static BinaryNode arrayToBalancedBST(char[] array, int start, int end)
	{
		if(end < start) {
			return null;
		}
		int mid = start + (end - start)/2;
		BinaryNode node = new BinaryNode(array[mid]);
		node.left(arrayToBalancedBST(array, start, mid-1));
		node.right(arrayToBalancedBST(array, mid+1, end));
		return node;
	}

	private static BinaryNode singleLLToBalancedBST(ListNode list)
	{
		int length = 0;
		for(ListNode temp = list; temp != null; temp = temp.next()) {
			length++;
		}
		return singleLLToBalancedBST(list, length).tree();
	}
	private static NodeInfo singleLLToBalancedBST(ListNode list, int currLength)
	{
		NodeInfo left = null;
		ListNode currHead = list;
		if(currLength > 1) {
			left = singleLLToBalancedBST(list, currLength/2);
			currHead = left.list();
		}

		BinaryNode root = new BinaryNode((char)currHead.value());
		root.left((left == null)?null:left.tree());

		int remLength = currLength - currLength/2 -1;
		NodeInfo right = null;
		if(remLength > 0) {
			right = singleLLToBalancedBST(currHead.next(), remLength);
			root.right(right.tree());
		}
		return new NodeInfo((right == null)?currHead.next():right.list(), root);
	}
	private static BinaryNode singleLLToBalancedBST2(ListNode list)
	{
		int length = 0;
		for(ListNode temp = list; temp != null; temp = temp.next()) {
			length++;
		}
		return singleLLToBalancedBST2(list, 0, length-1).tree();
	}
	private static NodeInfo singleLLToBalancedBST2(ListNode list, int start, int end)
	{
		if(start > end) {
			return new NodeInfo(null, null);
		}
		int mid = start + (end - start)/2;
		NodeInfo left = singleLLToBalancedBST2(list, start, mid-1);

		ListNode curr = (left.list() != null)? left.list():list;
		BinaryNode node = new BinaryNode((char)curr.value());
		node.left(left.tree());

		NodeInfo right = singleLLToBalancedBST2(curr.next(), mid+1, end);
		node.right(right.tree());
		return new NodeInfo((right.list() != null)?right.list():curr.next(), node);
	}

	private static BinaryNode findNodeInBST(BinaryNode root, char ch)
	{
		BinaryNode node = root;
		while(node != null && node.value() != ch) {
			node = (node.value() > ch) ? node.left(): node.right();
		}
		return node;
	}
	protected static boolean isPresent(BinaryNode root, char c)
	{
		return (null != findNodeInBST(root, c));
	}
	public static BinaryNode lcaBst(BinaryNode root, char c1, char c2)
	{
		if(isPresent(root, c1) && isPresent(root, c2)) {
			return lcaBstHelper(root, c1, c2);
		}
		return null;
	}
	private static BinaryNode lcaBstHelper(BinaryNode tree, char c1, char c2)
	{
		if(tree == null || c1 == tree.value() || c2 == tree.value()) {
			return tree;
		}

		if(tree.value() < c1 && tree.value() < c2) {
			return lcaBstHelper(tree.right(), c1, c2);
		}
		if(tree.value() > c1 && tree.value() > c2) {
			return lcaBstHelper(tree.left(), c1, c2);
		}
		return tree;
	}

	// CTCI Cracking the Coding Interview END ....

	// Convert Binary Search Tree to Double-Linked list, left for prev, right for next
	// Version 1 - Inorder Traversal iterative
	private static BinaryNode Bst2DllIter(BinaryNode node)
	{
		BinaryNode first = null, curr = null;
		Stack<BinaryNode> stack = new Stack<>();

		while(node != null || !stack.isEmpty())
		{
			if(node != null)
			{
				stack.push(node);
				node = node.left();
			}
			else
			{
				node = stack.pop();
				if(curr != null)
				{
					curr.right(node);
					node.left(curr);
					curr = curr.right();
				}
				else
				{
					first = curr = node;
				}
				node = node.right();
			}
		}

		return first;
	}
	// Version 2 - Inorder Traversal recursive, this is the enveloping function
	private static BinaryNode Bst2DllRec(BinaryNode node)
	{
		BinaryNode list = Bst2DllRecHelper(node);
		if(list == null)
			return null;

		while(list.left() != null)
		{
			list = list.left();
		}
		return list;
	}
	private static BinaryNode Bst2DllRecHelper(BinaryNode node)
	{
		if(node == null)
			return null;

		BinaryNode left = Bst2DllRecHelper(node.left());
		if(left != null) //left always points to the list's LAST node
		{
			left.right(node);
			node.left(left);
		}

		BinaryNode retval = node;
		BinaryNode right = Bst2DllRecHelper(node.right());
		if(right != null) //right always points to the list's LAST node
		{
			retval = right;
			while(right.left() != null)
			{
				right = right.left();
			}
			right.left(node);
			node.right(right);
		}
		return retval;
	}
	// Version 3 - Inorder Traversal iterative, use node rotating
	private static BinaryNode Bst2DllRotate(BinaryNode node)
	{
		BinaryNode prevNode = null, leftNode = null, first = null;
		while(node != null)
		{
			while(node.left() != null)
			{
				//System.out.println("Rotate: B4 node - " + node);
				// Rotate now
				leftNode = node.left();
				node.left(leftNode.right());
				leftNode.right(node);

				//System.out.println("Rotate: A4 node - " + node);
				node = leftNode;
			}
			if(first == null)
				first = node;

			if(prevNode != null)
				prevNode.right(node);

			prevNode = node;
			node = node.right();
			//System.out.println("Rotate: Curnode - " + node);
		}

		// Set prev pointers for Double Linked List
		for(node = first; node.right() != null; node = node.right())
		{
			node.right().left(node);
		}
		return first;
	}
	private static String getBstDll(BinaryNode first)
	{
		StringBuilder str = new StringBuilder(512);
		while(first != null)
		{
			str.append(first).append("->");
			first = first.right();
		}
		return str.toString();
	}

	private static void oldBinaryTreeFunctions(BinaryNode t)
	{
		System.out.println("### check oldBinaryTreeFunctions to move ");
		/*
        String pre = t.PreOSRec();
        System.out.println("DFS::Pre-Order Search Recursive: " + pre);
        System.out.println("DFS::Pre-Order Search Iterative: " + t.PreOSIter());

        System.out.println("In-Order Search Recursive: " + t.InOSRec());
        String in = t.InOSIter();
        System.out.println("In-Order Search Iterative: " + in);
        System.out.println("Reverse In-Order Search Recursive: " + t.RevInOSRec());
        System.out.println("Reverse In-Order Search Iterative: " + t.RevInOSIter());

        System.out.println("Post-Order Search Recursive: " + t.PostOSRec());
        String post = t.PostOSIter();
        System.out.println("Post-Order Search Iterative: " + post);

        String level = t.LevelOSIter();
        System.out.println("BFS:: Level-Order Search Iterative: " + level);
        System.out.println("BFSRev:: Level-Order Search Iterative R2L: " + t.LevelOSR2LIter());
        System.out.println("BFSRev:: Level-Order Search Iterative Reverse: " + t.LevelOSReverseIter());
        System.out.println("BFSZigZag:: Level-Order Search ZigZag Old : " + t.LevelOSZigZagIterOld());
        System.out.println("BFSZigZag:: Level-Order Search ZigZag New : " + t.LevelOSZigZagIter());

        t.SetGPIter();
        System.out.println("GP: " + t.GetGPString());
        t.SetGPRec(t.Root(), null);
        System.out.println("GP: " + t.GetGPString());


		System.out.println("Maximum value Recursive: " + (char)t.FindMaxBTRec(t.Root()));
        System.out.println("Maximum value Iterative: " + (char)t.FindMaxBTIter());

        System.out.println("Search value Recursive I: " + t.FindBTRec(t.Root(), 'I'));
        System.out.println("Search value Recursive T: " + t.FindBTRec(t.Root(), 'T'));
        System.out.println("Search value Iterative I: " + t.FindBTIter('I'));
        System.out.println("Search value Iterative T: " + t.FindBTIter('T'));

        System.out.print("Size Rec : " + t.countNodesRec() + " ");
        System.out.println("Size Iter: " + t.countNodesIter());

        System.out.println("BFSRev:: Level-Order Search Iterative Reverse: " + t.LevelOSReverseIter());
        System.out.print("Depth Recursive: " + t.DepthRec(t.Root()) + " ");
        System.out.println("Depth Iterative: " + t.DepthIter());

        System.out.println("Deepest Node: " + t.DeepestNode().value());

        //t.DeleteNode('F');

        System.out.print("Count Leaves Rec: " + t.CountLeavesRec(t.Root()) + " ");
        System.out.println("Count Leaves Iter: " + t.CountLeavesIter());
        System.out.print("Count Full nodes Rec: " + t.CountFullNodesRec(t.Root()) + " ");
        System.out.println("Count Full nodes Iter: " + t.CountFullNodesIter());
        System.out.print("Count Half nodes Rec: " + t.CountHalfNodesRec(t.Root()) + " ");
        System.out.println("Count Half nodes Iter: " + t.CountHalfNodesIter());

		BinaryNode f =  new BinaryNode((char)70);
		t.insertBST(f); // F
        System.out.println("Ancestors: existing F " + t.Ancestors(f));
        System.out.println("Ancestors: new node with F " + t.Ancestors(new BinaryNode('F')));
        System.out.println("Ancestors: new node with X " + t.Ancestors(new BinaryNode('X')));

        System.out.println("Vertical Sum: " + t.FindVerticalSum());

		System.out.println("IOS kth node");
		BinaryNode node1 = t.IOSkIter(1);
        System.out.println("	In: " + in + " IOSkIter (1): " + (node1!=null?node1.value():"null"));
		node1 = t.IOSkIter(17);
        System.out.println("	In: " + in + " IOSkIter (17): " + (node1!=null?node1.value():"null"));
		node1 = t.IOSkIter(10);
        System.out.println("	In: " + in + " IOSkIter (10): " + (node1!=null?node1.value():"null"));
		node1 = t.IOSkIter(25);
        System.out.println("	In: " + in + " IOSkIter (25): " + (node1!=null?node1.value():"null"));
		BinaryNode node2 = t.IOSkRec(1);
        System.out.println("	In: " + in + " IOSkRec (1): " + (node2!=null?node2.value():"null"));
		BinaryNode node3 = t.IOSkRec(4);
        System.out.println("	In: " + in + " IOSkRec (4): " + (node3!=null?node3.value():"null"));
		BinaryNode node4 = t.IOSkRec(14);
        System.out.println("	In: " + in + " IOSkRec (14): " + (node4!=null?node4.value():"null"));
		BinaryNode node5 = t.IOSkRec(17);
        System.out.println("	In: " + in + " IOSkRec (17): " + (node5!=null?node5.value():"null"));
		BinaryNode node6 = t.IOSkRec(25);
        System.out.println("	In: " + in + " IOSkRec (25): " + (node6!=null?node6.value():"null"));
		*/

	}
	private static void testbst(BinaryNode t, String tag) {
        System.out.println("IsBST: " + tag + " " + isBST(t) + " ");
		//System.out.println("IsBSTRevIn: " + isBstRevIn(t) + " ");
		//System.out.println("IsBSTInOrder: " + isBstInorder(t) + " ");
	}
	private static void bstCreates()
	{
		BinaryNode t1 = BinarySearchTree.buildFromInPre("ABEG".toCharArray(), "EBAG".toCharArray());
		testbst(t1, "in: ABEG, pre: EBAG");

		BinaryNode t2 = BinarySearchTree.buildFromInPre("ABJEG".toCharArray(), "EBAJG".toCharArray());
		testbst(t2, "in: ABJEG, pre: EBAJG");

		BinaryNode t3 = BinarySearchTree.buildFromInPre("ABECG".toCharArray(), "EBAGC".toCharArray());
		testbst(t3, "in: ABECG, pre: EBAGC");

		BinaryNode t4 = BinarySearchTree.buildFromInPre("ABJECG".toCharArray(), "EBAJGC".toCharArray());
		testbst(t4, "in: ABJECG, pre: EBAJGC");


		// from single linked list to Balanced BST
		LinkedList list = LinkedList.createListFromArray(new int[] {65, 66, 67, 68, 69, 70, 71, 72, 73, 74});
		BinaryNode tree = BinarySearchTree.singleLLToBalancedBST(list.head());
		tree.prettyPrint();
		BinaryNode tree2 = BinarySearchTree.singleLLToBalancedBST2(list.head());
		tree2.prettyPrint();

		list = LinkedList.createListFromArray(new int[] {65});
		tree = BinarySearchTree.singleLLToBalancedBST(list.head());
		tree.prettyPrint();
		tree2 = BinarySearchTree.singleLLToBalancedBST2(list.head());
		tree2.prettyPrint();

		list = LinkedList.createListFromArray(new int[] {65, 66});
		tree = BinarySearchTree.singleLLToBalancedBST(list.head());
		tree.prettyPrint();
		tree2 = BinarySearchTree.singleLLToBalancedBST2(list.head());
		tree2.prettyPrint();

		list = LinkedList.createListFromArray(new int[] {65, 66, 67});
		tree = BinarySearchTree.singleLLToBalancedBST(list.head());
		tree.prettyPrint();
		tree2 = BinarySearchTree.singleLLToBalancedBST2(list.head());
		tree2.prettyPrint();

		list = LinkedList.createListFromArray(new int[] {65, 66, 67, 68});
		tree = BinarySearchTree.singleLLToBalancedBST(list.head());
		tree.prettyPrint();
		tree2 = BinarySearchTree.singleLLToBalancedBST2(list.head());
		tree2.prettyPrint();

		list = LinkedList.createListFromArray(new int[] {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79});
		tree = BinarySearchTree.singleLLToBalancedBST(list.head());
		tree.prettyPrint();
		tree2 = BinarySearchTree.singleLLToBalancedBST2(list.head());
		tree2.prettyPrint();

		list = LinkedList.createListFromArray(new int[] {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75});
		tree = BinarySearchTree.singleLLToBalancedBST(list.head());
		tree.prettyPrint();
		tree2 = BinarySearchTree.singleLLToBalancedBST2(list.head());
		tree2.prettyPrint();

		// from array to Balanced BST
		BinaryNode root = arrayToBalancedBST(new char[] {'A','B','C','D','E','F','G'});
		System.out.println("Balanced BST from array ");
		root.prettyPrint();
		arrayToBalancedBST(new char[] {'A','B','C','D','E','F','G','H','I','J'}).prettyPrint();
		arrayToBalancedBST(new char[] {'A'}).prettyPrint();
		arrayToBalancedBST(new char[] {'A','B'}).prettyPrint();
		arrayToBalancedBST(new char[] {'A','B','C'}).prettyPrint();
		arrayToBalancedBST(new char[] {'A','B','C','D'}).prettyPrint();
		arrayToBalancedBST(new char[] {'A','B','C','D','E','F','G','H','I','J','K'}).prettyPrint();
		arrayToBalancedBST(new char[] {'A','B','C','D','E','F','G','H','I','J','K','L'}).prettyPrint();
	}
	private static void bstDataStructures(BinaryNode root)
	{
		//System.out.println("BST -> DLL Iter: " + getBstDll(Bst2DllIter(root)));
		//System.out.println("BST -> DLL Rec : " + getBstDll(Bst2DllRec(root)));
		System.out.println("Pre-Order Iter Ganesh  : " + BinaryTreeTraversals.preOrderIter(root));
		System.out.println("BST -> DLL Rotate : " + getBstDll(Bst2DllRotate(root)));
	}
	private static void bstFunctions(BinaryNode t)
	{
		testbst(t, "");

		// LCA BST LCA(K,M) = K, LCA(E,F) = E, LCA(C,F) = D, LCA(A,D) = B, LCA(A,L) = H
		System.out.println("lcaBst (K, M) : " + lcaBst(t, 'K', 'M'));
		System.out.println("lcaBst (E, F) : " + lcaBst(t, 'E', 'F'));
		System.out.println("lcaBst (C, F) : " + lcaBst(t, 'C', 'F'));
		System.out.println("lcaBst (A, D) : " + lcaBst(t, 'A', 'D'));
		System.out.println("lcaBst (A, L) : " + lcaBst(t, 'A', 'L'));
		System.out.println("lcaBst (A, Z) : " + lcaBst(t, 'A', 'Z'));
		System.out.println("lcaBst (Q, Z) : " + lcaBst(t, 'Q', 'Z'));
	}
	private static void testbstdata() {
		TreeNode t = smallBst1();
		System.out.println("ceiling 22: " + ceiling(t, 22));
		System.out.println("ceiling 24: " + ceiling(t, 24));
		System.out.println("ceiling 26: " + ceiling(t, 26));
		System.out.println("ceiling 28: " + ceiling(t, 28));
		System.out.println("ceiling 30: " + ceiling(t, 30));
		System.out.println("ceiling 35: " + ceiling(t, 35));
		System.out.println("ceiling 45: " + ceiling(t, 45));
		System.out.println("ceiling 0: " + ceiling(t, 0));
		System.out.println("ceiling 2: " + ceiling(t, 2));
		System.out.println("ceiling 4: " + ceiling(t, 4));
		System.out.println("ceiling 6: " + ceiling(t, 6));
		System.out.println("ceiling 7: " + ceiling(t, 7));
		System.out.println("ceiling 8: " + ceiling(t, 8));

		System.out.println("floor 22: " + floor(t, 22));
		System.out.println("floor 24: " + floor(t, 24));
		System.out.println("floor 26: " + floor(t, 26));
		System.out.println("floor 28: " + floor(t, 28));
		System.out.println("floor 30: " + floor(t, 30));
		System.out.println("floor 35: " + floor(t, 35));
		System.out.println("floor 45: " + floor(t, 45));
		System.out.println("floor 0: " + floor(t, 0));
		System.out.println("floor 2: " + floor(t, 2));
		System.out.println("floor 4: " + floor(t, 4));
		System.out.println("floor 6: " + floor(t, 6));
		System.out.println("floor 7: " + floor(t, 7));
		System.out.println("floor 8: " + floor(t, 8));
	}
	public static void main(String[] args)
	{
		//CreateSmallTree(t);
		BinaryNode t = complexTree();
		testbstdata();
		oldBinaryTreeFunctions(t);
		bstFunctions(t);
		bstCreates();
		bstDataStructures(t);
	}
}
