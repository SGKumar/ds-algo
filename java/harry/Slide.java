package harry;

import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
/*import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
*/
//import java.util.IllegalArgumentException;
import java.util.ArrayList;
/*
import java.util.HashMap;
import java.util.BitSet;
*/
// Harry He Book filler till I find the right chapter Ch.03 Data Structures questions
public class Slide
{
	// Find max in sliding window of size k (< N)
	public static int[] maxInSlidingWindow(int[] arr, int k)
	{
		ArrayDeque<Integer> dq = new ArrayDeque<>();
		int[] maxs = new int[arr.length - k+1];
		for(int i = 0; i < k-1; i++) {
			addToWin(dq, arr, i, k);
		}
		for(int i = k-1; i < arr.length; i++) {
			addToWin(dq, arr, i, k);
			maxs[i-k+1] = arr[dq.getFirst()];
		}
		return maxs;
	}
	private static void addToWin(Deque<Integer> dq, int[] arr, int i, int k)
	{
		if(!dq.isEmpty() && i - dq.getFirst() > k) {
			dq.removeFirst();
		}
		while(!dq.isEmpty() && arr[dq.getLast()] <= arr[i]) {
			dq.removeLast();
		}
		dq.addLast(i);
	}

	private static void testmaxInSlidingWindow()
	{
		int[] arr = new int[] {1, 3, -1, -3, 5, 3, 6, 7};
		System.out.println("max 3: " + Arrays.toString(arr) + " " + Arrays.toString(maxInSlidingWindow(arr, 3)));
		arr = new int[] {1, 2, 3, 5, 3, 1, 6};
		System.out.println("max 3: " + Arrays.toString(arr) + " " + Arrays.toString(maxInSlidingWindow(arr, 3)));
	}

	public static void main(String[] args)
	{
		testmaxInSlidingWindow();
	}
	
	class TreeNode {
		int height;
		int count;
		int weight;
		
		TreeNode left;
		TreeNode right;
		
		TreeNode(int h, int c) {
		  height = h;
		  count = c;
		  weight = 1;
		}
	  }
  
  public List<int[]> reconstructLine(List<int[]> line) {
    // step 1. insert the person to the tree
    TreeNode root = null;
    for (int[] person : line) {
      root = insert(root, person, person[1]);
    }
    
    // step 2. do in-order traversal
    List<int[]> res = new ArrayList<>();
    inorder(root, res);
    
    return res;
  }
  
  TreeNode insert(TreeNode root, int[] person, int w) {
    if (root == null) {
      return new TreeNode(person[0], person[1]);
    }
    
    if (person[1] < root.weight) {
      // person's count less than current node's weight
      // increase current node's weight and insert to the left
      root.weight++;
      root.left = insert(root.left, person, w);
    } else {
      // otherwise insert to the right
      // and decrease the weight by root.weight
      root.right = insert(root.right, person, w - root.weight);
    }
    
    return root;
  }
  
  void inorder(TreeNode root, List<int[]> res) {
    if (root == null) {
      return;
    }
    
    inorder(root.left, res);
    res.add(new int[]{root.height, root.count});
    inorder(root.right, res);
  }

}



