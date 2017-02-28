package ctci;

import java.util.List;
import java.util.ArrayList;

public class CTCICh8DynProg
{
	// 8.1 child triple step
	public static int childSteps(int n)
	{
		if(n == 0) return 1;
		int pMinus3 = 0, pMinus2 = 0, pMinus1 = 1, steps = 0;
		for(int i = 1; i <= n; i++) {
			steps = pMinus1 + pMinus2 + pMinus3;
			pMinus3 = pMinus2;
			pMinus2 = pMinus1;
			pMinus1 = steps;
		}
		return steps;
	}
	// 8.2 robot in a grid
	public static boolean getRobotPath(boolean[][] matrix)
	{
		if(!matrix[0][0]) return false;
		
		int rows = matrix.length;
		int cols = matrix[0].length;
		boolean table[][] = new boolean[rows][cols];
		
		for(int row = 1; row < rows; row++) {
			table[row][0] = matrix[row][0] && matrix[row-1][0];
		}
		for(int col = 1; col < cols; col++) {
			table[0][col] = matrix[0][col] && matrix[0][col-1];
		}
		for(int row = 1; row < rows; row++) {
			for(int col = 1; col < cols; col++) {
				table[row][col] = matrix[row][col] && (matrix[row-1][col] && matrix[row][col-1]);
			}
		}
		return table[rows-1][cols-1];
	}
	// 8.3 magic index no repeats
	public static int magicIndex(int[] a)
	{
		if(a == null || a.length == 0) return 0;
		int left = 0, right = a.length - 1;
		while(left <= right) {
			int mid = left + (right - left)/2;
			if(a[mid] == mid) {
				return mid;
			}
			if(a[mid] > mid) {
				right = mid-1;
			}
			else {
				left = mid+1;
			}
		}
		return -1;
	}
	// 8.3 magic index with repeats
	public static int magicIndexRepeats(int[] a)
	{
		if(a == null || a.length == 0) return 0;
		return magicIndexRepeatsFast(a, 0, a.length-1);
	}
	private static int magicIndexRepeatsFast(int[] a, int left, int right)
	{
		if(right < left) return -1;
		int mid = left + (right - left)/2;
		if(a[mid] == mid) {
			return mid;
		}
		int leftrBound = ((mid-1) < a[mid])?(mid-1):a[mid];
		int index = magicIndexRepeatsFast(a, left, leftrBound);
		if(index != -1) {
			return index;
		}
		
		int rightlBound = ((mid+1) > a[mid])?(mid+1):a[mid];
		return magicIndexRepeatsFast(a, rightlBound, right);
	}

	// 8.4 recursive fn to mult 2 +ve integers without using *
	// 8.4 can use add, subtract or bit shift
	public static int multRec(int a, int b)
	{
		if(a == 0 || b == 0) return 0;
		if(a == 1) return b;
		if(b == 1) return a;
		if(a < b) {
			return multRec(b, a, 0);
		}
		else {
			return multRec(a, b, 0);
		}
	}
	private static int multRec(int a, int b, int shift)
	{
		if(b == 0) return 0;
		int curr = ((b & 1) == 1)?a:0;
		curr <<= shift;
		return curr + multRec(a, b >>> 1, shift+1);
	}
	public static int[] prodInMinSteps(int a, int b)
	{
		int small = (a < b)?a:b;
		int big = (a >= b)?a:b;
		int prod = 0, step = 0;
		while(small > 0){
			if((small & 1) == 1){
				prod += (big << step);
				small >>= 1;
			}
			step++;
		}
		return (new int[] {step, prod});
	}
	
	private static void testchildSteps(int n)
	{
		System.out.println(childSteps(n) + " possible ways to run up " + n + " stairs");
	}
	
	private static void testprodInMinSteps(int a, int b)
	{
		int[] x = prodInMinSteps(a, b);
		System.out.println(a + " * " + b + " = " + x[1] + " in " + x[0] + " steps " + multRec(a, b));
	}
	public static void main(String[] args)
	{
		testchildSteps(0);
		testchildSteps(1);
		testchildSteps(2);
		testchildSteps(3);
		testchildSteps(4);
		testchildSteps(5);
		testchildSteps(35);

		testprodInMinSteps(31, 35);
	}
}

