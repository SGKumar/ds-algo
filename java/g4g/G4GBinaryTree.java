package g4g;

import llsq.Queue;
import tree.BinaryTreeBase;
import tree.TreeNode;

public class G4GBinaryTree
{
	static class WInfo
	{
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
	
	public static void main(String[] args) {
		TreeNode t = BinaryTreeBase.smallTree1();
		System.out.println("Width: " + width(t) + " maxWidth: " + maxWidth(t));
		t = BinaryTreeBase.smallTree2();
		System.out.println("Width: " + width(t) + " maxWidth: " + maxWidth(t));
		t = BinaryTreeBase.BigTree3();
		System.out.println("Width: " + width(t) + " maxWidth: " + maxWidth(t));
	}
}