
import java.lang.StringBuilder;
import java.util.Stack;
import java.lang.String;
import java.util.HashMap;

public class BinaryTree extends BinaryTreeBase //implements Comparable<MyBinaryTree>
{
	// Constructors
	public BinaryTree()
	{
		super();
		m_vertSum = new HashMap<Integer, Integer>();
	}
	public BinaryTree(BinaryNode tree)
	{
		super(tree);
		m_vertSum = new HashMap<Integer, Integer>();
	}

	public HashMap<Integer, Integer> getVerticalSumMap()
	{
		return m_vertSum;
	}
	
	// TRAVERSALS BEGIN .....
	public String preOrderIter()
	{
		return preOrderIter(root);
	}
	private String preOrderIter(BinaryNode node)
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
	public String preOrderIterWiki()
	{
		return preOrderIterWiki(root);
	}
	private String preOrderIterWiki(BinaryNode node)
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
	public String preOrderIter2016Aug()
	{
		return preOrderIter2016Aug(root);
	}
	private String preOrderIter2016Aug(BinaryNode node)
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

	public String inOrderIterWiki()
	{
		return inOrderIterWiki(root);
	}
	private String inOrderIterWiki(BinaryNode node)
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

	public String postOrderIterGaneshOld()
	{
		return postOrderIterGaneshOld(root);
	}
	private String postOrderIterGaneshOld(BinaryNode node)
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

	public String postOrderIterNew()
	{
		return postOrderIterNew(root);
	}
	private String postOrderIterNew(BinaryNode node)
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

	public String levelOrderIterGanesh()
	{
		return levelOrderIterGanesh(root);
	}
	private String levelOrderIterGanesh(BinaryNode node)
	{
		StringBuilder str = new StringBuilder();
		MyQueue queue = new MyQueue();
		queue.enque(node);
		
		while(!queue.isEmpty())
		{
			node = queue.deque();
			str.append(node.value());
			if(node.left() != null)
				queue.enque(node.left());
			if(node.right() != null)
				queue.enque(node.right());
		}
		return str.toString();
	}

	public String levelOrderIterReverse()
	{
		return levelOrderIterReverse(root);
	}
	private String levelOrderIterReverse(BinaryNode node)
	{
		StringBuilder str = new StringBuilder();
		MyQueue queue = new MyQueue();
		queue.enque(node);

		Stack<BinaryNode> stack = new Stack<>();
		while(!queue.isEmpty())
		{
			node = queue.deque();
			stack.push(node);

			if(node.right() != null)
				queue.enque(node.right());
			if(node.left() != null)
				queue.enque(node.left());
		}

		while(!stack.isEmpty())
		{
			str.append(stack.pop().value());
		}	
		return str.toString();
	}

	public String printLevelOrderLinebyline()
	{
		return printLevelOrderLinebyline(root);
	}
	private String printLevelOrderLinebyline(BinaryNode node)
	{
		StringBuilder str = new StringBuilder();
		MyQueue queue = new MyQueue();
		queue.enque(node);
		int currLevel = 1, nextLevel = 0;
		while(!queue.isEmpty())
		{
			node = queue.deque();
			currLevel--;
			str.append(node.value());
			if(node.left() != null)
			{
				queue.enque(node.left());
				nextLevel++;
			}
			if(node.right() != null)
			{
				queue.enque(node.right());
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
	// TRAVERSALS END .....

	// Print inorder successor's value, given parent pointer
	// Usually node is passed, here assume ch is present in the binary tree
	public char getInorderSuccessor(char ch)
	{
		BinaryNode node = findNodeInBST(ch);
		BinaryNode next = null;

		// if node has right child, find leftmost node in right subtree
		if(node.right() != null) {
			next = node.right();
			while(next.left() != null) {
				next = next.left();
			}
		}
		else {
			if(node.getParent() != null) {
				// if node is left child of its parent, find parent
				if(node.getParent().left() == node) {
					next = node.getParent();
				}
				else // if node is right child of its parent, find ancestor who is left child of his parent*
				// this parent* is successor
				{
					next = node;
					BinaryNode parent = node.getParent();
					while(parent != null && parent.right() == next) 
					{
						next = parent;
						parent = parent.getParent();
					}
					next = parent;
				}
			}
		}
		return ((next == null)?' ':next.value());
	}
	
	public String ancestors(char ch)
	{
		return ancestors(root, ch);
	}
	private String ancestors(BinaryNode node, char ch)
	{
		StringBuilder str = new StringBuilder();
		Stack<BinaryNode> stack = new Stack<>();
		BinaryNode prev = null;
		
		while(!stack.isEmpty() || node != null)
		{
			if(node != null)
			{
				if(node.value() != ch)
				{
					stack.push(node);
					node = node.left();
				}
				else
				{
					str.append(ch).append(" ");
					break;
				}
			}
			else
			{
				BinaryNode peeknode = stack.peek();
				if(prev != peeknode.right() && peeknode.right() != null)
					node = peeknode.right();
				else
				{
					node = stack.pop();
					//str.append(node.value());
					prev = node;
					node = null;
				}
			}
		}
			
		while(!stack.isEmpty())
		{
			str.append(stack.pop().value()).append(" ");
		}

		return str.toString();
	}

	public String ancestors2(char ch)
	{
		return ancestors2(root, ch);
	}
	private String ancestors2(BinaryNode node, char ch)
	{
		StringBuilder str = new StringBuilder();
		if(node == null)
		{
			return "";
		}
		if(node.value() == ch)
		{
			return str.append(ch).toString();
		}
		
		String ch2;
		if((ch2 = ancestors2(node.left(), ch)) != "" || (ch2 = ancestors2(node.right(), ch)) != "")
		{
			return str.append(node.value()).append(" ").append(ch2).toString();
		}
		
		return "";
	}

	public BinaryNode getTreeFromInAndPre(char[] in, int inBegin, int inEnd, char[] pre, int preBegin, int preEnd)
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
			BinaryNode left = getTreeFromInAndPre(in, inBegin, inRoot-1, pre, preBegin+1, preRight);
			node.left(left);
		}
		
		// if preRight is last preOrder node, right tree is null
		if(preRight != preEnd)
		{
			BinaryNode right = getTreeFromInAndPre(in, inRoot+1, inEnd, pre, preRight+1, preEnd);
			node.right(right);
		}
		return node;
	}
	int preIL = 0;
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
			preIL = preBegin;
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
		
		temp = getTreeFromPreAllFullNodes(pre, preIL+1, preEnd);
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
	private int findInArray(char[] chs, int begin, int end, char val)
	{
		for(int i = begin; i <= end; i++) {
			if(chs[i] == val) {
				return i;
			}
		}
		return -1;
	}

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
	
	HashMap<Integer, Integer> m_vertSum;
	private void removeVertSumEntries()
	{
		if(m_vertSum != null)
			m_vertSum.clear();
	}
	private void verticalSum(BinaryNode node, int column)
	{
		if(node == null) return;

		Integer val = new Integer(node.value());
		if(m_vertSum.containsKey(column))
			val += m_vertSum.get(column);
		m_vertSum.put(column, val);
		
		verticalSum(node.left(), column-1);
		verticalSum(node.right(), column+1);
	}
	
	// Fill NextSibling pointer when available, all NULL initially.
	private String setNextSibling(BinaryNode node)
	{
		if(node == null) return "";
		StringBuilder str = new StringBuilder(512);
		MyQueue queue = new MyQueue();
		queue.enque(node);
		
		while(!queue.isEmpty())
		{
			node = queue.deque();

			if(node.left() != null)
			{
				node.left().next(node.right());
				queue.enque(node.left());
			}
			if(node.right() != null)
			{
				if(node.next() != null)
					node.right().next(node.next().left());
				queue.enque(node.right());
			}
			str.append(node);
		}
		return str.toString();
	}
	
	// Assume ch is valid character in tree.
	public BinaryNode findNodeInBST(char ch)
	{
		BinaryNode node = root;
		while(node != null && node.value() != ch) {
			node = (node.value() > ch) ? node.left(): node.right();
		}
		return node;
	}

	// Find Least Common Parent of BST given 2 node values
	// In ex below LCA(K,M) = K, LCA(E,F) = E, LCA(C,F) = D, LCA(A,D) = B, LCA(A,L) = H
	// If EITHER v1 or v2 do NOT exist in the tree return null
	public BinaryNode LCA(char v1, char v2)
	{
		return LCA(root, v1, v2);
	}
	private BinaryNode LCA(BinaryNode tree, char v1, char v2)
	{
		if(tree == null) return null;
		if(v1 == tree.value() || v2 == tree.value())
			return tree;

		BinaryNode l = LCA(tree.left(), v1, v2);
		BinaryNode r = LCA(tree.right(), v1, v2);
		if(l != null && r != null)
			return tree;
		if(l == null && r != null)
			return r;
		else
			return l;
	}

	// LCA for Binary Search Tree very easy
	// Assume node1 < node2, and both nodes are PRESENT in the tree.
	private BinaryNode lowestCommonAncestorBST(BinaryNode tree, char node1, char node2)
	{
		BinaryNode node = null;
		
		// Options are both are LEFT, both are RIGHT, they are on EITHER side.
		while(tree != null)
		{
			if(node1 < tree.value() && node2 < tree.value())
				tree = tree.left();
			else if(node1 > tree.value() && node2 > tree.value())
				tree = tree.right();
			else
			{
				node = tree;
				break;
			}
		}
		return node;
	}

	// Convert Binary Search Tree to Double-Linked list, left for prev, right for next
	// Version 1 - Inorder Traversal iterative
	private BinaryNode convertBSTreeToDoubleLListIter(BinaryNode node)
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
	private BinaryNode convertBSTreeToDoubleLListRec(BinaryNode node)
	{
		BinaryNode list = convertBST2DLLRec(node);
		if(list == null)
			return null;
		
		while(list.left() != null)
		{
			list = list.left();
		}
		return list;
	}
	private BinaryNode convertBST2DLLRec(BinaryNode node)
	{
		if(node == null)
			return null;
		
		BinaryNode left = convertBST2DLLRec(node.left());
		if(left != null) //left always points to the list's LAST node
		{
			left.right(node);
			node.left(left);
		}

		BinaryNode retval = node;
		BinaryNode right = convertBST2DLLRec(node.right());
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
	private BinaryNode convertBSTreeToDoubleLListRotate(BinaryNode node)
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
	private String printBSTreeDoubleLList(BinaryNode first)
	{
		StringBuilder str = new StringBuilder(512);
		while(first != null)
		{
			str.append(first).append("->");
			first = first.right();
		}
		return str.toString();
	}
	
	private static BinaryNode singleLLToBalancedBST(ListNode list)
	{
		int length = 0;
		for(ListNode temp = list; temp != null; temp = temp.next())
		{
			length++;
		}
		int levels = nextPowerOf2(length);

		BinaryNode tree = new BinaryNode(' ');
		inOrder(tree, list, levels);
		return tree;
	}
	private static ListNode inOrder(BinaryNode root, ListNode node, int levels)
	{
		ListNode temp = node;
		System.out.printf("root %c node %c level %d\n", (root==null)?' ':root.value(), (node==null)?' ':node.value(), levels);
		if(levels > 1 && node != null)
		{
			root.left(new BinaryNode(' '));
			temp = inOrder(root.left(), node, levels-1);
		}

		System.out.printf("root %c temp %c level %d\n", (root==null)?' ':root.value(), (temp==null)?' ':temp.value(), levels);
		root.value((char)temp.value());
		temp = temp.next();
		System.out.printf("root %c temp %c level %d\n", (root==null)?' ':root.value(), (temp==null)?' ':temp.value(), levels);

		if(levels > 1 && temp != null)
		{
			root.right(new BinaryNode(' '));
			temp = inOrder(root.right(), temp, levels-1);
		}
		return temp;
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
	private static int inOrder(BinaryNode root, char[] arr, int start, int levels)
	{
		int arrAt = start;
		if(levels > 1)
		{
			root.left(new BinaryNode(' '));
			arrAt = inOrder(root.left(), arr, start, levels-1);
		}
		root.value(arr[arrAt]);
		arrAt++;

		if(levels > 1)
		{
			root.right(new BinaryNode(' '));
			arrAt = inOrder(root.right(), arr, arrAt, levels-1);
		}
		return arrAt;
	}
/*	
	private BinaryNode deleteBSTNode(BinaryNode tree, char val)
	{
		if(tree.value() == val)
			return tree;
		BinaryNode parent = null;
		BinaryNode temp = null;
		//Find node
		while(tree != null && val != tree.value())
		{
			//parent = tree;
			if(val1 < tree.value())
				tree = tree.left();
			else
				tree = tree.right();
		}
		// Empty tree or val isn't in tree
		if(tree == null)
			return tree;
		
		if(tree.left() == null)
		{
			temp = new BinaryNode(val);
			temp.left(tree.left());
			temp.right(tree.right());
			
			// Copy right child
			tree.value(tree.right().value());
			tree.left(tree.right().left());
			tree.right(tree.right().right());
			if(parent.value() > tree.value())
				parent.left(tree.right());
			else
				parent.right(tree.right());
			return tree;
		}
		else if(tree.right() == null) // right node is empty
		{
			if(parent.value() > tree.value())
				parent.left(tree.left());
			else
				parent.right(tree.left());
			return tree;
		}
		else // Both children there
		{
			// Find maximum from left sub-tree.
			Node maxLeft = tree;
			
			
		}
		// Options are both are LEFT, both are RIGHT, they are on EITHER side.
		while(tree != null)
		{
			if(node1 < tree.value() && node2 < tree.value())
				tree = tree.left();
			else if(node1 > tree.value() && node2 > tree.value())
				tree = tree.right();
			else
			{
				node = tree;
				break;
			}
		}
		return node;
	}
*/
	
	public static void main(String[] args)
	{
		/*
		int maxInt = java.lang.Integer.MAX_VALUE;
		int low = maxInt - 0xFF;
		int high = maxInt;
		int midAvg = (low + high)/2;
		int midMinus = low + ((high-low)/2);
		int midShift = (low + high) >>> 1;
		System.out.println("maxInt " + maxInt + " low " + low + " high " + high + " midAvg " + midAvg + " midMinus " + midMinus + " midShift " + midShift);

		*/
		BinaryTree t = new BinaryTree();
		CreateSmallTree(t);
		//binaryTreeTraversals(t);
		//btHardQues(t);
		btDataStructures(t);

		t = new BinaryTree();
		CreateComplexTree(t);
		//binaryTreeTraversals(t);
		//btHardQues(t);
		btDataStructures(t);

		btCreates();

	}
	private static void binaryTreeTraversals(BinaryTree t)
	{
		System.out.println("Pre-Order Iter Ganesh  : " + t.preOrderIter());
		System.out.println("Pre-Order Iter Wiki    : " + t.preOrderIterWiki());
		System.out.println("Pre-Order Iter Aug2016 : " + t.preOrderIter2016Aug());
		
		System.out.println("In-Order Iter Wiki     : " + t.inOrderIterWiki());
		System.out.println("Post-Order Iter New    : " + t.postOrderIterNew());
		System.out.println("Post-Order Iter Ganesh : " + t.postOrderIterGaneshOld());
		System.out.println("Level-Order Ganesh     : " + t.levelOrderIterGanesh());
		System.out.println("Level-Order Reverse    : " + t.levelOrderIterReverse());
		
		System.out.println("Level-Order Linebyline : " + t.printLevelOrderLinebyline());

		/*
		System.out.println("Reverse In-Order Traversal Recursive: " + t.RevInOSRec());
		System.out.println("Reverse In-Order Traversal Iterative: " + t.RevInOSIter());

		System.out.println("BFSRev:: Level-Order Search Iterative R2L: " + t.LevelOSR2LIter());
		System.out.println("BFSRev:: Level-Order Search Iterative Reverse: " + t.LevelOSReverseIter());
		System.out.println("BFSZigZag:: Level-Order Search ZigZag Old : " + t.LevelOSZigZagIterOld());
		System.out.println("BFSZigZag:: Level-Order Search ZigZag New : " + t.LevelOSZigZagIter());

		*/
		
		nextPowerOf2(13);
		nextPowerOf2(14);
		nextPowerOf2(15);
		nextPowerOf2(16);
		System.out.println("Next power_of_2 of 7 is " + nextPowerOf2(7));
		System.out.println("Next power_of_2 of 93 is " + nextPowerOf2(93));
		System.out.println("Next power_of_2 of 7 is " + nextPowerOf2(7));
		System.out.println("Next power_of_2 of 93 is " + nextPowerOf2(93));

		// ancestors
		System.out.println("Ancestors of I are	 : " + t.ancestors('I'));
		System.out.println("Ancestors of Q are	 : " + t.ancestors('Q'));
		System.out.println("Ancestors2 of I are	 : " + t.ancestors2('I'));
		System.out.println("Ancestors2 of Q are	 : " + t.ancestors2('Q'));

		// Vertical Sum
		t.removeVertSumEntries();
		t.verticalSum(t.root(), 0xF0); 
		System.out.println("Vertical Sum : " + t.getVerticalSumMap());

		// setNextSibling
		System.out.println("SetNextSibling : " + t.setNextSibling(t.root()));

	}
	private static void btCreates()
	{
		// from single linked list to Balanced BST
		//ListNode list = LinkedList.createSortedList().head();
		//LinkedList list = LinkedList.createListFromArray(new int[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79});
		//BinaryNode tree = t.singleLLToBalancedBST(list.head());

		// from array to Balanced BST
		BinaryNode root = arrayToBalancedBST(new char[] {'A','B','C','D','E','F','G'});
		System.out.println("Balanced BST from array ");
		root.prettyPrint();
	}
	private static void btDataStructures(BinaryTree t)
	{
		//System.out.println("BST -> DLL Iter: " + t.printBSTreeDoubleLList(t.convertBSTreeToDoubleLListIter(t.root())));
		//System.out.println("BST -> DLL Rec : " + t.printBSTreeDoubleLList(t.convertBSTreeToDoubleLListRec(t.root())));
		System.out.println("Pre-Order Iter Ganesh  : " + t.preOrderIter());
		System.out.println("BST -> DLL Rotate : " + t.printBSTreeDoubleLList(t.convertBSTreeToDoubleLListRotate(t.root())));

	}
	private static void btHardQues(BinaryTree t)
	{
		// inorder successor
		System.out.println("Inorder successor of I is : " + t.getInorderSuccessor('I'));
		System.out.println("Inorder successor of Q is : " + t.getInorderSuccessor('Q'));

		char[] in = t.inOrderIterWiki().toCharArray();
		char[] pre = t.preOrderIterWiki().toCharArray();
		BinaryNode node = t.getTreeFromInAndPre(in, 0, in.length-1, pre, 0, pre.length-1);

		// LCA TODO
		System.out.println("LCA (K, M) : " + t.LCA('K', 'M'));
		System.out.println("LCA (C, F) : " + t.LCA('C', 'F'));
		System.out.println("LCA (A, L) : " + t.LCA('A', 'L'));
		System.out.println("LCA (A, Z) : " + t.LCA('A', 'Z'));
		System.out.println("LCA (Q, Z) : " + t.LCA('Q', 'Z'));

		// LCA BST
		System.out.println("lowestCommonAncestorBST (K, M) : " + t.lowestCommonAncestorBST(t.root(), 'K', 'M'));
		System.out.println("lowestCommonAncestorBST (C, F) : " + t.lowestCommonAncestorBST(t.root(), 'C', 'F'));
		System.out.println("lowestCommonAncestorBST (A, L) : " + t.lowestCommonAncestorBST(t.root(), 'A', 'L'));
		System.out.println("lowestCommonAncestorBST (A, Z) : " + t.lowestCommonAncestorBST(t.root(), 'A', 'Z'));
		System.out.println("lowestCommonAncestorBST (Q, Z) : " + t.lowestCommonAncestorBST(t.root(), 'Q', 'Z'));
	}
}

