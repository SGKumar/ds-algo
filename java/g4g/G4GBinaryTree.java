package g4g;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import llsq.Queue;
import tree.BinaryTreeBase;
import tree.TreeNode;

public class G4GBinaryTree {
	static class WInfo {
		int left = 0;
		int right = 0;
		WInfo() {
			left = 0; right = 0;
		}
		WInfo(int l, int r) {
			left = l; right = r;
		}
	}
	public static int width(TreeNode root)
	{
		WInfo w = width(root, 0);
		return w.right - w.left;
	}
	private static WInfo width(TreeNode root, int col)
	{
		if(root.left == null && root.right == null) {
			return new WInfo(col, col);
		}
		WInfo l = new WInfo(), r = new WInfo();
		if(root.left != null) {
			l = width(root.left, col-1);
		}
		if(root.right != null) {
			r = width(root.right, col+1);
		}
		return new WInfo(Math.min(l.left, r.left), Math.max(l.right, r.right));
	}

	public static int maxWidth(TreeNode root)
	{
		if(root == null) return 0;
		Queue<TreeNode> q = new Queue<>();
		q.add(root);
		int currLevel = 1, nextLevel = 0, width = 1;
		
		while(!q.isEmpty()) {
			TreeNode node = q.remove();
			currLevel--;
			
			if(node.left != null) {
				q.add(node.left);
				nextLevel++;
			}
			if(node.right != null) {
				q.add(node.right);
				nextLevel++;
			}
			if(currLevel == 0) {
				currLevel = nextLevel;
				width = Math.max(width, nextLevel);
				nextLevel = 0;
			}
		}
		return width;
	}
	static class TInfo
	{
		public final int ndx;
		public final int d;
		TInfo(int index, int depth)
		{
			this.ndx = index;
			this.d = depth;
		}
		/*@Override
		public boolean equals(Object o) {
			if(o == this) return true;
			if(!(o instanceof TInfo)) return false;
			TInfo info = (TInfo)o;
			return ndx == info.ndx && d == info.d;
		}*/
		@Override
		public int hashCode() {
			return Objects.hash(ndx, d);
		}
	}
	
	// Test for hashcode and equals
	private static void testTInfo() {
		TInfo info1 = new TInfo(2, 5);
		TInfo info2 = new TInfo(2, 5);
		System.out.println("Implemented: hashcode - NO, equals - NO; Returns: == false, equals false");
		System.out.println("Implemented: hashcode - YES, equals - NO; Returns: == false, equals false");
		System.out.println("Implemented: hashcode - NO, equals - YES; Returns: == false, equals true");
		System.out.println("Implemented: hashcode - YES, equals - YES; Returns: == false, equals true");
		System.out.println("overrode hashCode == " + (info1 == info1));
		System.out.println("overrode hashCode equals " + info1.equals(info2));
	}

	// L is leaf, N is node, tree has all full nodes, pre-ord traversal is given
	public static int depth(String pre) {
		if(pre == null || pre.length() == 0) return 0;
		TInfo info = depth(pre, 0);
		//info.ndx should be equal to pre.length()
		return info.d;
	}
	private static TInfo depth(String pre, int start) {
		if(pre.charAt(start) == 'L') {
			return new TInfo(start, 0);
		}

		TInfo l = depth(pre, start+1);
		TInfo r = depth(pre, l.ndx+1);
		return new TInfo(r.ndx, 1 + Math.max(l.d, r.d));
	}

	// construct cncestor matrix from a Given Binary Tree
	// values are from [0 to n-1] (or 1 to n), mat[i][j] = 1 if i is ancestor of j, 0 otherwise
	public static int[][] markAnc(TreeNode t, int nodes) {
		int[][] ret = new int[nodes+1][nodes+1];
		markAnc(t, ret, new ArrayList<Integer>());
		return ret;
	}
	private static void markAnc(TreeNode t, int mat[][], ArrayList<Integer> path) {
		if(t == null) return;
		
		for(Integer parent : path) {
			mat[parent][t.val] = 1;
		}
		path.add(t.val);
		markAnc(t.left, mat, path);
		markAnc(t.right, mat, path);
		path.remove(path.size()-1);
	}

	static class NodeInfo implements Comparable<NodeInfo>
	{
		public final int val;
		public final int kids;
		NodeInfo(int val, int kids)
		{
			this.val = val;
			this.kids = kids;
		}
		int numKids() { return kids;}
		public int compareTo(NodeInfo p) {
			if(this.kids != p.kids) 
				return Integer.compare(this.kids, p.kids);
			else 
				return Integer.compare(this.val, p.val);
		}
		public String toString() {
			return "(" + val + "," + kids + ")";
		}
	}

	public static TreeNode ancTree(int mat[][]) {
		int nodes = mat.length;
		TreeMap<NodeInfo, ArrayList<Integer>> kidsMap = new TreeMap<>();
		for(int parent = 1; parent <= nodes-1; parent++) {

			ArrayList<Integer> kids = new ArrayList<>();
			for(int child = 1; child <= nodes-1; child++) {
				if(mat[parent][child] == 1) {
					kids.add(child);
				}
			}
			kidsMap.put(new NodeInfo(parent, kids.size()), (kids.size()==0)?null:kids);
		}
		//System.out.println("nodes " + nodes + " kidsMap " + kidsMap.toString()); 
		
		HashMap<Integer, TreeNode> nodeMap = new HashMap<>();
		for(Map.Entry<NodeInfo, ArrayList<Integer>> entry: kidsMap.entrySet()) {
			NodeInfo nInfo = entry.getKey();

			TreeNode currNode = new TreeNode(nInfo.val);
			if(nInfo.kids > 0) {
				ArrayList<Integer> kids = entry.getValue();
				for(Integer kid : kids) {
					if(nodeMap.containsKey(kid)) {
						TreeNode n = nodeMap.get(kid);
						if(currNode.left == null) {
							currNode.left = n;
						}
						else {
							currNode.right = n;
						}
						nodeMap.remove(kid);
					}
				}
			}
			nodeMap.put(nInfo.val, currNode);
		}
		return nodeMap.values().iterator().next();
	}
	public static TreeNode ancTree2(int mat[][]) {
		int nodes = mat.length;
		TreeSet<NodeInfo> kidSet = new TreeSet<>();
		for(int parent = 1; parent <= nodes-1; parent++) {
			int count = 0;
			for(int child = 1; child <= nodes-1; child++) {
				if(mat[parent][child] == 1) {
					count++;
				}
			}
			kidSet.add(new NodeInfo(parent, count));
		}

		TreeNode res[] = new TreeNode[nodes];
		for(NodeInfo nInfo : kidSet) {
			res[nInfo.val] = new TreeNode(nInfo.val);
			if(nInfo.kids == 0) {
				continue;
			}

			int row = nInfo.val;
			TreeNode currNode = res[row];
			for(int col = 1; col <= nodes-1; col++) {
				if(mat[row][col] == 1 && res[col] != null) {
					//TreeNode n = res[col];
					if(currNode.left == null) {
						currNode.left = res[col];
					}
					else {
						currNode.right = res[col];
					}
					res[col] = null;
				}
			}
		}
		return res[kidSet.last().val];
	}

	public static String diagOrder(TreeNode t) {
		HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
		diagOrder(t, 0, 0, map);
		
		StringBuilder str = new StringBuilder();
		for(ArrayList<Integer> ints : map.values()) {
			str.append(ints.toString()).append("\n");
		}
		return str.toString();
	}
	private static void diagOrder(TreeNode t, int x, int y, HashMap<Integer, ArrayList<Integer>> map) {
		if(t == null) return;
		if(!map.containsKey(x+y)) {
			map.put(x+y, new ArrayList<Integer>());
		}
		map.get(x+y).add(t.val);
		diagOrder(t.left, x-1, y-1, map);
		diagOrder(t.right, x+1, y-1, map);
	}
	private static void testdiagOrder() {
		int[] ints = new int[] {19, 8, 3, 10, 1, 6, -1, 14, -1, -1, 4, 7, 13, -1, -1, -1, -1, -1, -1, -1};
		TreeNode t1 = BinaryTreeBase.createTree(ints);
		t1.prettyPrint();
		System.out.println("diagOrder " + diagOrder(t1));
	}
	
	public static void main(String[] args) {
		//btInfo1();
		btInfos();
		testAncMat();
		testTInfo();
		testdiagOrder();
	}
	private static void btInfo1()
	{
		TreeNode t = BinaryTreeBase.smallTree1();
		System.out.println("Width: " + width(t) + " maxWidth: " + maxWidth(t));
		t = BinaryTreeBase.smallTree2();
		System.out.println("Width: " + width(t) + " maxWidth: " + maxWidth(t));
		t = BinaryTreeBase.BigTree3();
		System.out.println("Width: " + width(t) + " maxWidth: " + maxWidth(t));
	}
	private static void btInfos()
	{
		System.out.println("pre: " + "NLNLL" + " depth: " + depth("NLNLL"));
		System.out.println("pre: " + "NLNLNLL" + " depth: " + depth("NLNLNLL"));
		System.out.println("pre: " + "NNLLNLL" + " depth: " + depth("NNLLNLL"));
		System.out.println("pre: " + "NNLLNLNLL" + " depth: " + depth("NNLLNLNLL"));
		String pre = "NNNLLNNLLLNLNLL";
		System.out.println("pre: " + pre + " depth: " + depth(pre));
		pre = "NNNNLNLNLNLLNLNLNLLLNLL";
		System.out.println("pre: " + pre + " depth: " + depth(pre));
	}
	private static void testAncMat() {
		TreeNode t1 = BinaryTreeBase.smallTree1();
		t1.prettyPrint();
		int[][] anc = markAnc(t1, 9);
		System.out.println("anc matrix " + Arrays.deepToString(anc));
		TreeNode t2 = ancTree(anc);
		t2.prettyPrint();
		t2 = ancTree2(anc);
		t2.prettyPrint();
	}
}
