import java.lang.Math;
import java.util.Arrays;
import java.util.HashMap;

import java.util.Stack;

public class BinaryTreeOld extends BinaryTreeBase
{
	public BinaryTreeOld(MyBinaryNode tree)
	{
		super(tree);
	}

	public String PreOSIter()
	{
		StringBuilder str = new StringBuilder("");

		Stack<MyBinaryNode> stack = new Stack<MyBinaryNode>();
		stack.push(root);
		MyBinaryNode curr = null;
		while(!stack.empty())
		{
			curr = stack.pop();
			str.append(curr.Element());

			if(curr.Right() != null)
			{
				stack.push(curr.Right());
			}
			if(curr.Left() != null)
			{
				stack.push(curr.Left());
			}
		}
		return str.toString();
	}

	public String InOSIter()
	{
		StringBuilder str = new StringBuilder("");

		Stack<MyBinaryNode> stack = new Stack<MyBinaryNode>();

		MyBinaryNode curr = root;
		while(!stack.empty() || curr != null)
		{
			while(curr != null)
			{
				stack.push(curr);
				curr = curr.Left();
			}

			curr = stack.pop();
			str.append(curr.Element());
			curr = curr.Right();
		}
		return str.toString();
	}

	public String PostOSIter()
	{
		StringBuilder str = new StringBuilder("");

		Stack<MyBinaryNode> stack = new Stack<MyBinaryNode>();

		MyBinaryNode prev = null, curr = root;
		while(!stack.empty() || curr != null)
		{
			while(curr != null)
			{
				stack.push(curr);
				curr = curr.Left();
			}

			curr = stack.peek();
			if(curr.Right() != null && prev != curr.Right())
			{
				curr = curr.Right();
			}
			else
			{
				curr = stack.pop();
				str.append(curr.Element());
				prev = curr;
				curr = null;
			}
		}
		return str.toString();
	}

	public String LevelOSZigZagIter()
	{
		StringBuilder str = new StringBuilder("");

		Stack<MyBinaryNode> l2rStack = new Stack<MyBinaryNode>();
		Stack<MyBinaryNode> r2lStack = new Stack<MyBinaryNode>();

		boolean fRev = false;

		MyBinaryNode curr = root;
		l2rStack.push(curr);
		while(!l2rStack.empty() || !r2lStack.empty())
		{
			if(fRev)
			{
				curr = r2lStack.pop();
				if(curr.Right() != null)
					l2rStack.push(curr.Right());
				if(curr.Left() != null)
					l2rStack.push(curr.Left());

				if(r2lStack.empty())
					fRev = false;
			}
			else
			{
				curr = l2rStack.pop();
				if(curr.Left() != null)
					r2lStack.push(curr.Left());
				if(curr.Right() != null)
					r2lStack.push(curr.Right());

				if(l2rStack.empty())
					fRev = true;
			}

			str.append(curr.Element());
		}
		return str.toString();
	}

	// Diameter is no. of nodes on the longest path between two leaves in the tree.
	public int DiameterEx(MyBinaryNode tree)
	{
		return GetDia(tree).Diameter();
	}
	BTDia GetDia(MyBinaryNode tree)
	{
		if(tree == null)
			return new BTDia();

		BTDia lDia = GetDia(tree.Left());
		BTDia rDia = GetDia(tree.Right());

		int nHt = 1 + (lDia.Height() > rDia.Height()?lDia.Height():rDia.Height());

		int nDia = (lDia.Diameter() > rDia.Diameter()?lDia.Diameter():rDia.Diameter());
		if(tree.Left() != null && tree.Right() != null)
		{
			if(nDia < lDia.Height() + rDia.Height() + 1)
				nDia = lDia.Height() + rDia.Height() + 1;
		}
		return new BTDia(nHt, nDia);
	}

	/* * * * * * * * * * * * * * * *
	 *
	 * These are all OLD CODE .....
	 *
	 * * * * * * * * * * * * * * * */
	public void AddLevelOrder(char val)
	{
		AddLevelOrder(new MyBinaryNode(val));
	}
	public void AddLevelOrder(MyBinaryNode node)
	{
		if(root == null)
		{
			root = node;
			return;
		}
		MyBinaryNode temp = null;
		MyQueue queue = new MyQueue();

		queue.Enque(root);
		while(!queue.IsEmpty())
		{
			temp = queue.Deque();
			if(temp.Left() == null)
			{
				temp.Left(node);
				break;
			}
			else
				queue.Enque(temp);

			if(temp.Right() != null)
			{
				temp.Right(node);
				break;
			}
			else
				queue.Enque(temp);
		}
	}

	// Node has op with leaves having Values - Op Left Right aka DEPTH FIRST SEARCH
	public String PreOSRec()
	{
		return PreOSRec(root).toString();
	}
	public StringBuilder PreOSRec(MyBinaryNode tree)
	{
		StringBuilder str = new StringBuilder("");
		if(null == tree)
			return str;

		return str.append(tree.Element()).append(PreOSRec(tree.Left())).append(PreOSRec(tree.Right()));
	}
	/*public String PreOSIter()
	{
		if(null == root) return "";

		MyStack stack = new MyStack();
		StringBuilder str = new StringBuilder("");

		MyBinaryNode node = root;
		stack.Push(node);
		while(!stack.IsEmpty())
		{
			node = stack.Pop();
			str.append(node.Element());

			if(null != node.Right())
				stack.Push(node.Right());

			if(null != node.Left())
				stack.Push(node.Left());
		}

		/*while(true)
		{
			while(node != null)
			{
				str.append(node.Element());
				stack.Push(node);
				node = node.Left();
			}

			if(stack.IsEmpty())
				break;

			node = stack.Pop();
			node = node.Right();
		}

		return str.toString();
	}*/

	// Node has op with leaves having Values - Left OP Right
	public String InOSRec()
	{
		return InOSRec(root).toString();
	}
	public StringBuilder InOSRec(MyBinaryNode tree)
	{
		StringBuilder str = new StringBuilder("");
		if(null == tree)
			return str;

		return str.append(InOSRec(tree.Left())).append(tree.Element()).append(InOSRec(tree.Right()));
	}
/*	public String InOSIter()
	{
		if(null == root) return "";

		MyStack stack = new MyStack();
		StringBuilder str = new StringBuilder("");

		MyBinaryNode node = root;
		while(true)
		{
			while(node != null)
			{
				stack.Push(node);
				node = node.Left();
			}

			if(stack.IsEmpty())
				break;

			node = stack.Pop();
			str.append(node.Element());
			node = node.Right();
		}

		return str.toString();
	}
*/
	// Node has op with leaves having Values - Left Right OP
	public String PostOSRec()
	{
		return PostOSRec(root).toString();
	}
	public StringBuilder PostOSRec(MyBinaryNode tree)
	{
		StringBuilder str = new StringBuilder("");
		if(null == tree)
			return str;

		return str.append(PostOSRec(tree.Left())).append(PostOSRec(tree.Right())).append(tree.Element());
	}
	/*public String PostOSIter()
	{
		if(null == root) return "";

		MyStack stack = new MyStack();
		StringBuilder str = new StringBuilder("");

		MyBinaryNode node = root, lastPop = null;
		while(true)
		{
			while(node != null)
			{
				stack.Push(node);
				node = node.Left();
			}

			if(stack.IsEmpty())
				break;

			if(stack.Top().Right() == null || stack.Top().Right() == lastPop)
			{
				lastPop = stack.Pop();
				str.append(lastPop.Element());
			}
			else
			{
				node = stack.Top().Right();
			}
		}

		return str.toString();
	}*/

	public String LevelOSIter()
	{
		if(null == root) return "";

		MyQueue queue = new MyQueue();
		StringBuilder str = new StringBuilder("");

		MyBinaryNode node = root;
		queue.Enque(node);
		while(!queue.IsEmpty())
		{
			node = queue.Deque();
			str.append(node.Element());

			if(node.Left() != null)
				queue.Enque(node.Left());
			if(node.Right() != null)
				queue.Enque(node.Right());
		}
		return str.toString();
	}
	public String LevelOSR2LIter()
	{
		if(null == root) return "";

		MyQueue queue = new MyQueue();
		StringBuilder str = new StringBuilder("");

		MyBinaryNode node = root;
		queue.Enque(node);
		while(!queue.IsEmpty())
		{
			node = queue.Deque();
			str.append(node.Element());

			if(node.Right() != null)
				queue.Enque(node.Right());
			if(node.Left() != null)
				queue.Enque(node.Left());
		}
		return str.toString();
	}
	public String LevelOSReverseIter()
	{
		if(null == root) return "";

		MyQueue queue = new MyQueue();
		MyStack stack = new MyStack();

		MyBinaryNode node = root;
		queue.Enque(node);
		while(!queue.IsEmpty())
		{
			node = queue.Deque();
			stack.Push(node);

			if(node.Right() != null)
				queue.Enque(node.Right());
			if(node.Left() != null)
				queue.Enque(node.Left());
		}

		StringBuilder str = new StringBuilder("");
		while(!stack.IsEmpty())
		{
			node = stack.Pop();
			str.append(node.Element());
		}
		return str.toString();
	}

	public String LevelOSZigZagIterOld()
	{
		if(null == root) return "";
		MyQueue queue = new MyQueue();
		StringBuilder str = new StringBuilder();
		boolean r2l = true;

		MyBinaryNode node = root;
		queue.Enque(node);
		queue.Enque(null);
		while(!queue.IsEmpty())
		{
			node = queue.Deque();
			if(null == node)
			{
				r2l = !r2l;
				if(!queue.IsEmpty())
					queue.Enque(null);
				continue;
			}
			str.append(node.Element());

			if(r2l)
			{
				if(node.Right() != null)
					queue.Enque(node.Right());
				if(node.Left() != null)
					queue.Enque(node.Left());
			}
			else
			{
				if(node.Left() != null)
					queue.Enque(node.Left());
				if(node.Right() != null)
					queue.Enque(node.Right());
			}
		}
		return str.toString();
	}


	public void SetGPRec(MyBinaryNode node, MyBinaryNode parent)
	{
		if(null == node) return;
		if(node.Left() != null)
		{
			node.Left().setGP(parent);
			SetGPRec(node.Left(), node);
		}
		if(node.Right() != null)
		{
			node.Right().setGP(parent);
			SetGPRec(node.Right(), node);
		}
	}
	public void SetGPIter()
	{
		if(null == root) return;

		MyBinaryNode node = root, gp = null, temp = null;
		MyStack stack = new MyStack();

		stack.Push(node);
		while(!stack.IsEmpty())
		{
			node = stack.Pop();
			if(node.Left() != null)
			{
				temp = node.Left();
				stack.Push(temp);
				if(temp.Left() != null)
					temp.Left().setGP(node);
				if(temp.Right() != null)
					temp.Right().setGP(node);
			}
			if(node.Right() != null)
			{
				temp = node.Right();
				stack.Push(temp);
				if(temp.Left() != null)
					temp.Left().setGP(node);
				if(temp.Right() != null)
					temp.Right().setGP(node);
			}
		}
	}
	public String GetGPString()
	{
		return GetGPString(root).toString();
	}
	private StringBuilder GetGPString(MyBinaryNode tree)
	{
		StringBuilder str = new StringBuilder("");
		if(null == tree)
			return str;

		str = str.append(GetGPString(tree.Left()));
		str = str.append("[").append(tree.Element()).append(",").append((null == tree.getGP())?" ":tree.getGP().Element()).append("]");
		str = str.append(GetGPString(tree.Right()));
		return str;
	}

	public int FindMaxBTRec(MyBinaryNode node)
	{
		if(node == null) return 0;
		int max, left, right;
		max = node.Element();
		left = FindMaxBTRec(node.Left());
		right = FindMaxBTRec(node.Right());

		if(max < left)
			max = left;
		if(max < right)
			max = right;
		return max;
	}
	public int FindMaxBTIter()
	{
		if(root == null) return 0;

		MyBinaryNode node = root;
		MyQueue queue = new MyQueue();
		int max = 0;

		queue.Enque(node);
		while(!queue.IsEmpty())
		{
			node = queue.Deque();
			if(max < node.Element())
				max = node.Element();

			if(node.Left() != null)
				queue.Enque(node.Left());
			if(node.Right() != null)
				queue.Enque(node.Right());
		}
		return max;
	}

	public boolean FindBTRec(MyBinaryNode node, char val)
	{
		if(node == null) return false;
		if(node.Element() == val)
			return true;

		if(FindBTRec(node.Left(), val))
			return true;
		return FindBTRec(node.Right(), val);
	}
	public boolean FindBTIter(char val)
	{
		if(root == null) return false;
		MyBinaryNode node = root;
		MyQueue queue = new MyQueue();
		boolean found = false;

		queue.Enque(node);
		while(!queue.IsEmpty())
		{
			node = queue.Deque();
			if(node.Element() == val)
			{
				found = true;
				break;
			}
			if(node.Left() != null)
				queue.Enque(node.Left());
			if(node.Right() != null)
				queue.Enque(node.Right());
		}
		return found;
	}

	public int countNodesRec()
	{
		return countNodesRec(root);
	}
	private int countNodesRec(MyBinaryNode tree)
	{
		if(null == tree)
			return 0;

		return 1 + countNodesRec(tree.Left()) + countNodesRec(tree.Right());
	}
	public int countNodesIter()
	{
		if(null == root)
			return 0;

		MyBinaryNode node = root;
		MyQueue q = new MyQueue();
		q.Enque(node);

		int nodeCount = 0;
		while(!q.IsEmpty())
		{
			node = q.Deque();
			nodeCount++;
			if(node.Left() != null)
				q.Enque(node.Left());
			if(node.Right() != null)
				q.Enque(node.Right());
		}
		return nodeCount;
	}

	// Depth/Height is length of the path (no. of nodes) from root to deepest node
	public int DepthRec(MyBinaryNode tree)
	{
		if(null == tree) return 0;
		int lDepth = DepthRec(tree.Left());
		int rDepth = DepthRec(tree.Right());
		return 1 + ((lDepth > rDepth)?lDepth:rDepth);
	}

	public int DepthIter()
	{
		if(null == root) return 0;
		int depth = 0;
		MyBinaryNode node = root;

		MyQueue queue = new MyQueue();
		queue.Enque(node);
		queue.Enque(null);
		while(!queue.IsEmpty())
		{
			node = queue.Deque();
			if(null != node)
			{
				if(null != node.Left())
					queue.Enque(node.Left());

				if(null != node.Right())
					queue.Enque(node.Right());
			}
			else
			{
				depth++;
				if(!queue.IsEmpty())
					queue.Enque(null);
			}
		}
		return depth;
	}

	public MyBinaryNode DeepestNode()
	{
		if(null == root) return null;
		MyBinaryNode node = root;

		MyQueue queue = new MyQueue();
		queue.Enque(node);
		while(!queue.IsEmpty())
		{
			node = queue.Deque();

			if(null != node.Left())
				queue.Enque(node.Left());
			if(null != node.Right())
				queue.Enque(node.Right());
		}
		return node;
	}

	// Not used anywhere
	public boolean DeleteNode(char value)
	{
		if(null == root) return false;
		MyBinaryNode node = null, temp = root, lParent = null, rParent = null;
		boolean found = false;

		MyQueue queue = new MyQueue();
		queue.Enque(temp);
		while(!queue.IsEmpty())
		{
			temp = queue.Deque();
			if(temp.Element() == value)
				node = temp;

			if(null != temp.Left())
			{
				queue.Enque(temp.Left());
				lParent = temp;
				rParent = null;
			}
			if(null != temp.Right())
			{
				queue.Enque(temp.Right());
				lParent = null;
				rParent = temp;
			}
		}

		if(node != null)
		{
			node.Element(temp.Element());
			if(lParent != null)
				lParent.Left(null);
			if(rParent != null)
				rParent.Right(null);
		}

		return true;
	}

	public int CountLeavesRec(MyBinaryNode tree)
	{
		if(null == tree) return 0;

		if(null == tree.Left() && null == tree.Right())
			return 1;

		return CountLeavesRec(tree.Left()) + CountLeavesRec(tree.Right());
	}

	public int CountLeavesIter()
	{
		if(null == root) return 0;
		MyBinaryNode node = root;
		int leaves = 0;

		MyQueue queue = new MyQueue();
		queue.Enque(node);
		while(!queue.IsEmpty())
		{
			node = queue.Deque();
			if(null == node.Left() && null == node.Right())
				leaves++;

			if(null != node.Left())
				queue.Enque(node.Left());

			if(null != node.Right())
				queue.Enque(node.Right());
		}
		return leaves;
	}

	public int CountFullNodesRec(MyBinaryNode tree)
	{
		if(null == tree) return 0;

		int nfull = 0;
		if(null != tree.Left() && null != tree.Right())
			nfull = 1;

		return nfull + CountFullNodesRec(tree.Left()) + CountFullNodesRec(tree.Right());
	}

	public int CountFullNodesIter()
	{
		if(null == root) return 0;
		MyBinaryNode node = root;
		int nfull = 0;

		MyQueue queue = new MyQueue();
		queue.Enque(node);
		while(!queue.IsEmpty())
		{
			node = queue.Deque();
			if(null != node.Left() && null != node.Right())
				nfull++;

			if(null != node.Left())
				queue.Enque(node.Left());

			if(null != node.Right())
				queue.Enque(node.Right());
		}
		return nfull;
	}

	public int CountHalfNodesRec(MyBinaryNode tree)
	{
		if(null == tree) return 0;

		int nhalf = 0;
		if((null != tree.Left() && null == tree.Right()) || (null == tree.Left() && null != tree.Right()))
			nhalf = 1;

		return nhalf + CountHalfNodesRec(tree.Left()) + CountHalfNodesRec(tree.Right());
	}

	public int CountHalfNodesIter()
	{
		if(null == root) return 0;
		MyBinaryNode node = root;
		int nhalf = 0;

		MyQueue queue = new MyQueue();
		queue.Enque(node);
		while(!queue.IsEmpty())
		{
			node = queue.Deque();
			if((null != node.Left() && null == node.Right()) || (null == node.Left() && null != node.Right()))
				nhalf++;

			if(null != node.Left())
				queue.Enque(node.Left());

			if(null != node.Right())
				queue.Enque(node.Right());
		}
		return nhalf;
	}

	int treeDia;
	// Diameter is no. of nodes on the longest path between two leaves in the tree.
	public int Diameter(MyBinaryNode tree)
	{
		treeDia = 0;
		HtSetDia(tree);
		return treeDia;
	}
	public int HtSetDia(MyBinaryNode tree)
	{
		if(null == tree) return 0;

		// Gives no. of nodes....
		int lHt = (null == tree.Left())?0:HtSetDia(tree.Left());
		int rHt = (null == tree.Right())?0:HtSetDia(tree.Right());

		int levelHt = 1 + ((lHt < rHt)?rHt:lHt);

		if(treeDia < (lHt+rHt) && (lHt > 0 && rHt >0))
			treeDia = lHt + rHt;

		return levelHt;
	}

	// Node has op with leaves having Values - Right Op Left
	public String RevInOSRec()
	{
		return RevInOSRec(root).toString();
	}
	private StringBuilder RevInOSRec(MyBinaryNode tree)
	{
		if(null == tree)
			return new StringBuilder("");

		return RevInOSRec(tree.Right()).append(tree.Element()).append(RevInOSRec(tree.Left()));
	}
	public String RevInOSIter()
	{
		if(null == root) return "";
		StringBuilder str = new StringBuilder("");
		MyBinaryNode node = root;
		MyStack stack = new MyStack();

		while(true)
		{
			while(node != null)
			{
				stack.Push(node);
				node = node.Right();
			}
			if(stack.IsEmpty())
				break;
			node = stack.Pop();
			str.append(node.Element());
			node = node.Left();
		}
		return str.toString();
	}

	public String Ancestors(MyBinaryNode node)
	{
		return Ancestors(root, node).toString();
	}
	private StringBuilder Ancestors(MyBinaryNode tree, MyBinaryNode node)
	{
		StringBuilder str = new StringBuilder();
		if(null == tree)
			return str;

		if(tree.Left() == node || tree.Right() == node)
		{
			str.append(tree.Element());
		}
		StringBuilder str1 = Ancestors(tree.Left(), node);
		if(str1.length() > 0)
		{
			str.append(tree.Element()).append(str1);
		}
		else
		{
			str1 = Ancestors(tree.Right(), node);
			if(str1.length() > 0)
			{
				str.append(tree.Element()).append(str1);
			}
		}

		return str;
	}

	public String FindVerticalSum()
	{
		HashMap<Integer, Long> map = new HashMap<Integer, Long>();
		FindVerticalSum(root, 0xFF, map);
		return map.toString();
	}
	private void FindVerticalSum(MyBinaryNode tree, int yAxis, HashMap<Integer, Long> map)
	{
		if(null == tree) return;
		Long val = 0L;
		Integer intKey = new Integer(yAxis);
		if(map.containsKey(intKey))
		{
			val = map.get(intKey);
		}
		map.put(yAxis, (long)tree.Element() + val);
		FindVerticalSum(tree.Left(), yAxis-1, map);
		FindVerticalSum(tree.Right(), yAxis+1, map);
	}

	// Construct binary tree given order traversal strings
	public MyBinaryNode BuildBTWithInPre(String inOrder, int iStart, int iEnd, String preOrder, int pStart)
	{
		if(iStart > iEnd) return null;

		MyBinaryNode node = new MyBinaryNode(preOrder.charAt(pStart));

		if(iStart == iEnd) return node;

		int i = Utils.indexInString(inOrder, node.Element(), iStart, iEnd);
		if(i == -1)
		{
			// throw some exception if integer not found within this band
		}

		node.Left(BuildBTWithInPre(inOrder, iStart, i-1, preOrder, pStart+1));
		node.Right(BuildBTWithInPre(inOrder, i+1, iEnd, preOrder, pStart+i-iStart+1));

		return node;
	}
	public MyBinaryNode BuildBTWithInPost(String inOrder, int iStart, int iEnd, String postOrder, int pEnd)
	{
		//System.out.print("iStart: " + iStart + " iEnd: " + pEnd + " pEnd: " + pEnd);
		//System.out.print(" InOrder: " + inOrder.subSequence(iStart, iEnd+1));
		//System.out.println(" PostOrder: " + postOrder);

		if(iStart > iEnd) return null;

		MyBinaryNode node = new MyBinaryNode(postOrder.charAt(pEnd));

		if(iStart == iEnd) return node;

		int i = Utils.indexInString(inOrder, node.Element(), iStart, iEnd);
		if(i == -1)
		{
			// throw some exception if integer not found within this band
		}

		node.Left(BuildBTWithInPost(inOrder, iStart, i-1, postOrder, pEnd-(iEnd-i)-1));
		node.Right(BuildBTWithInPost(inOrder, i+1, iEnd, postOrder, pEnd-1));

		return node;
	}
	public MyBinaryNode BuildBTWithInLevel(String inOrder, int iStart, int iEnd, String levelOrder)
	{
		//System.out.print("iStart: " + iStart + " iEnd: " + pEnd + " pEnd: " + pEnd);
		//System.out.print(" InOrder: " + inOrder.subSequence(iStart, iEnd+1));
		//System.out.println(" LevelOrder: " + levelOrder);

		if(iStart > iEnd) return null;

		MyBinaryNode node = new MyBinaryNode(levelOrder.charAt(0));

		if(iStart == iEnd) return node;

		int i = Utils.indexInString(inOrder, node.Element(), iStart, iEnd);
		if(i == -1)
		{
			// throw some exception if integer not found within this band
		}
		String levOrderl = levelOrder.replaceAll("[" + inOrder.substring(i, iEnd+1) + "]", "");
		String levOrderr = levelOrder.replaceAll("[" + inOrder.substring(iStart, i+1) + "]", "");
		//String levOrderr = Utils.replace(levelOrder, inOrder, iStart, i);

		node.Left(BuildBTWithInLevel(inOrder, iStart, i-1, levOrderl));
		node.Right(BuildBTWithInLevel(inOrder, i+1, iEnd, levOrderr));

		return node;
	}

	// Find Least Common Parent of BST given 2 node values
	// In ex below LCA(K,M) = K, LCA(E,F) = E, LCA(C,F) = D, LCA(A,D) = B, LCA(A,L) = H
	// If EITHER v1 and v2 do NOT exist in the tree return null
	public MyBinaryNode LCA(MyBinaryNode v1, MyBinaryNode v2)
	{
		return LCA(root, v1, v2);
	}
	private MyBinaryNode LCA(MyBinaryNode tree, MyBinaryNode v1, MyBinaryNode v2)
	{
		if(tree == null) return null;
		if(v1 == tree || v2 == tree)
			return tree;

		MyBinaryNode l = LCA(tree.Left(), v1, v2);
		MyBinaryNode r = LCA(tree.Right(), v1, v2);
		if(l != null && r != null)
			return tree;
		if(l == null && r != null)
			return r;
		else
			return l;
	}

	// Trees I & L, each node has 0 or 2 children, generate tree from pre-order string
	public MyBinaryNode BuildBTWithPreIL(String preOrder)
	{
		return BuildBTWithPreIL(preOrder, 0);
	}
	private MyBinaryNode BuildBTWithPreIL(String preOrder, int i)
	{
		if(preOrder == null) return null;

		MyBinaryNode node = new MyBinaryNode(preOrder.charAt(i));
		if(node.Element() == 'L')
			return node;

		i = i+1;
		node.Left(BuildBTWithPreIL(preOrder, i));
		i = i+1;
		node.Right(BuildBTWithPreIL(preOrder, i));

		return node;
	}
	public MyBinaryNode BuildBTWithPreIL1(String preOrder)
	{
		if(preOrder == null) return null;
		MyStack stack = new MyStack();

		MyBinaryNode node, nodel, noder;
		for(int i = preOrder.length()-1; i >=0; i--)
		{
			if(preOrder.charAt(i) == 'L')
				stack.Push(new MyBinaryNode('L'));
			else
			{
				node = new MyBinaryNode('N');
				node.Left(stack.Pop());
				node.Right(stack.Pop());
				stack.Push(node);
			}
		}
		return stack.Pop();
	}

	// Fill Next Sibling
	// DOES NOT WORK
	/*
	public void SetNextSiblingRec(MyBinaryNode node, MyBinaryNode parent)
	{
		if(null == node) return;
		if(node.Left() != null)
		{
			node.Left().Next(node.Right());
			SetNextSiblingRec(node.Left(), node);
		}
		if(node.Right() != null)
		{
			if(node.Next() != null)
			node.Right().setGP(parent);
			SetGPRec(node.Right(), node);
		}
	}*/
	public String GetNextSibling()
	{
		return GetNextSibling(root).toString();
	}
	private StringBuilder GetNextSibling(MyBinaryNode tree)
	{
		StringBuilder str = new StringBuilder("");
		if(null == tree)
			return str;

		str = str.append(GetNextSibling(tree.Left()));
		str = str.append("[").append(tree.Element()).append(",").append((null == tree.Next())?" ":tree.Next().Element()).append("]");
		str = str.append(GetNextSibling(tree.Right()));
		return str;
	}
	// Recursion is very difficult to implement....
	/*
	public void SetNextSiblingRec1(MyBinaryNode tree)
	{
		MyBinaryNode node = tree, sib = null;
		while(node != null)
		{
			if(node.Next() != null)
				sib = node.Next().Left();
			else
				sib = null;
			node.Left().Next(node.Right());
			node.Right().Next(sib);

			node = node.Next();
		}
		SetNextSiblingRec1(tree.Left());
	}*/
	public void SetNextSiblingIter()
	{
		if(null == root) return;
		MyQueue queue = new MyQueue();
		MyBinaryNode node = null, prev = null;

		queue.Enque(root);
		queue.Enque(null);
		while(!queue.IsEmpty())
		{
			node = queue.Deque();
			prev = queue.Last();
			if(node != null)
			{
				if(node.Left() != null)
				{
					queue.Enque(node.Left());
					// 1 node.Left().Next(node.Right());
					if(prev != null) // 2 Begin
					{
						prev.Next(node.Left());
					}
					prev = node.Left();
					// 2 End
				}
				if(node.Right() != null)
				{
					queue.Enque(node.Right());
					// 1 node.Left().Next(node.Right());
					// 1 node.Right().Next((node.Next() != null)?node.Next().Left():null);
					if(prev != null) // 2 Begin
					{
						prev.Next(node.Right());
					}
					prev = node.Right();
					// 2 End
				}
			}
			else
			{
				if(!queue.IsEmpty())
				{
					queue.Enque(null);
					prev = null; //2
				}
			}
		}
	}

	// Write Java code to return a pointer to the nth node of an inorder traversal of a Binary Tree.
	public MyBinaryNode IOSkRec(int k)
	{
		if(k == 0 || root == null) return null;
		return IOSkRec(root, new MyInt(k));
	}
	private MyBinaryNode IOSkRec(MyBinaryNode node, MyInt nodeNum)
	{
		MyBinaryNode node1 = null;
		if(node.Left() != null)
			node1 = IOSkRec(node.Left(), nodeNum);

		//System.out.println("IOSkRec: " + node.Element() + " " + nodeNum.Int());
		if(node1 != null)
			return node1;
		if(nodeNum.Int() == 1)
			return node;
		else
			nodeNum.Int(nodeNum.Int()-1);

		if(node.Right() != null)
			node1 = IOSkRec(node.Right(), nodeNum);
		return node1;
	}


	/*
	private MyBinaryNode IOSkRec(MyBinaryNode tree, MyInt nodeNum)
	{
		int currNode = 0;
		MyBinaryNode ret = null;

		new Object ()
		{
			public void visit(Node node)
			{
				visit(node.Left());
				if(currNode == nodeNum)
				{
				  // Do nothing.
				}
				else if(++currNode == nodeNum)
				{
					ret = node;
				}
				else
				{
					visit(node.Right());
				}

			}.visit(tree);
		}
		return ret;
	}
	*/
	public MyBinaryNode IOSkIter(int k)
	{
		if(root == null || k==0) return null;

		MyBinaryNode node = root;
		int i = 0;
		MyStack stack = new MyStack();

		while(true)
		{
			while(node != null)
			{
				stack.Push(node);
				node = node.Left();
			}
			if(stack.IsEmpty())
				break;

			node = stack.Pop();
			i++;
			if(i == k) return node;
			node = node.Right();
		}
		return node;
	}

	public String MorrisIOT()
	{
		if(root == null)
			return "";

		MyBinaryNode current, pre;
		StringBuilder str = new StringBuilder();
		//StringBuilder str2 = new StringBuilder(2048);
		//str2.append("Current\tLeft\tPre\tPre.Right\tCurrent\n");

		current = root;
		while(current != null)
		{
			//str2.append(current.Element() + "\t\t" + (current.Left()==null?"null\t\t\t":current.Left().Element())+"\t");
			if(current.Left() == null)
			{
				str.append(current.Element());
				current = current.Right();
			}
			else
			{
				/* Find the inorder predecessor of current */
				pre = current.Left();
				while(pre.Right() != null && pre.Right() != current)
					pre = pre.Right();

				//str2.append(pre.Element() + "\t" + (pre.Right()==null?"null":pre.Right().Element()) + "\t\t");
				/* Make current as right child of its inorder predecessor */
				if(pre.Right() == null)
				{
					pre.Right(current);
					current = current.Left();
				}
				/* Revert the changes made in if part to restore the original
				tree i.e., fix the right child of predecssor */
				else
				{
					pre.Right(null);
					str.append(current.Element());
					current = current.Right();
				} /* End of if condition pre->right == null */
			} /* End of if condition current.Left == null*/
			//str2.append((current==null?"null":current.Element()) + "\n");
		} /* End of while */
		//System.out.println(str2);

		return str.toString();
	}

	
}