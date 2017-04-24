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
	// TODO 3.11 regular expression to match pattern with string
	/*
	boolean match(String s, String pattern) {
		if(s == null || pattern == null) return false;
		return matchCore(string, pattern);
	}
	boolean matchCore(String string, String pattern) {
		if(string == "" && pattern == "") return true;
		if(string != "" && pattern == "") return false;

		if(pattern.charAt(1) == '*') {
			if(pattern.charAt(0) == string.charAt(0) || (pattern.charAt(0) == '.' && *string != '\0'))
// move on the next state
return matchCore(string + 1, pattern + 2)
// stay on the current state
|| matchCore(string + 1, pattern)
// ignore a '*'
|| matchCore(string, pattern + 2);
else
// ignore a '*'
return matchCore(string, pattern + 2);
}
if(*string == *pattern || (*pattern == '.' && *string != '\0'))
return matchCore(string + 1, pattern + 1);
return false;
}
*/	
	
	public static String paren(int n) {
		StringBuilder sb = new StringBuilder(128);
		char [] s = new char[2*n];
		paren(s, 0, n, n, sb);
		return sb.toString();
	}
	private static void paren(char[] s, int at, int op, int cl, StringBuilder sb) {
		if(op == 0 && cl == 0) {
			sb.append(s).append(" ");
			return;
		}
		if(op > 0) {
			s[at] = '(';
			paren(s, at+1, op-1, cl, sb);
		}
		if(cl > op) {
			s[at] = ')';
			paren(s, at+1, op, cl-1, sb);
		}
	}
	private static void testparen()
	{
		System.out.println("1 "  + paren(1));
		System.out.println("2 "  + paren(2));
		System.out.println("3 "  + paren(3));
		System.out.println("4 "  + paren(4));
		System.out.println("5 "  + paren(5));
	}

	public static void main(String[] args)
	{
		testparen();
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



