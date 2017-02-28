import java.util.Arrays;
/*import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
*/
//import java.util.IllegalArgumentException;
/*import java.util.ArrayList;

import java.util.HashMap;
import java.util.BitSet;
*/
// Harry He Book Ch.03 Data Structures questions
public class DS
{
	// 5. An array contains n numbers ranging from 0 to n-2. There is exactly one number duplicated in
	// the array. How do you find the duplicated number? For example, if an array with length 5 contains
	// numbers {0, 2, 1, 3, 2}, the duplicated number is 2
	public static int duplicate(int[] a)
	{
		int sumNums = 0;
		for(int num : a) {
			if(num < 0 || num > a.length - 2) {
				throw new IllegalArgumentException("Invalid numbers");
			}
			sumNums += num;
		}
		int sumArray= ((a.length - 2) * (a.length - 1)) >> 1;
		return (sumNums - sumArray);
	}
	
	// 6. An array contains n numbers ranging from 0 to n-1. There are some numbers duplicated in the
	// array. It is not clear how many numbers are duplicated or how many times a number gets duplicated.
	// How do you find a duplicated number in the array? For example, if an array of length 7 contains
	// the numbers {2, 3, 1, 0, 2, 5, 3}, the implemented function (or method) should return either 2 or 3.
	public static int anyduplicate(int[] nums)
	{
		for(int i = 0; i < nums.length; ) {
			if(nums[i] == i) {
				i++;
			}
			else {
				if(nums[i] == nums[nums[i]]) {
					return nums[i];
				}
				// swap nums[i] and nums[nums[i]]
				int temp = nums[i];
				nums[i] = nums[temp];
				nums[temp] = temp;
			}
		}
		return -1;
	}

	// 7. find val in Sorted 1-d array expressed as 2-d array
	public static boolean find(int[][] table, int val)
	{
		if(table == null) return false;
		int rows = table.length, cols = table[0].length;
		if(val < table[0][0] || val > table[rows-1][cols-1]) {
			return false;
		}

		int left = 0, right = rows * cols - 1;
		int row = 0, col = 0, mid = -1;
		while(left <= right) {
			mid = left + ((right - left) >> 1);
			row = mid/cols;
			col = mid%cols;
			if(val == table[row][col]) {
				return true;
			}
			if(val > table[row][col]) {
				left = mid + 1;
			}
			else {
				right = mid - 1;
			}
		}
		return false;
	}

	// 8b. find in row and col sorted 2-d array
	public static boolean findIn2DArray(int[][] table, int val)
	{
		if(table == null) return false;
		int rows = table.length, cols = table[0].length;
		if(val < table[0][0] || val > table[rows-1][cols-1]) {
			return false;
		}
		
		boolean found = false;
		int row = 0, col = cols - 1;
		while(row < rows && col >= 0) {
			if(val == table[row][col]) {
				found = true;
				break;
			}
			if(val > table[row][col]) {
				row++;
			}
			else {
				col--;
			}
		}
		return found;
	}
	
	private static void testduplicate()
	{
		testduplicateAll(new int[] {0, 3, 1, 3, 2});
		testduplicateAll(new int[] {0, 0, 1, 2, 3});
	}
	private static void testduplicateAll(int[] arr)
	{	
		System.out.println(duplicate(arr) + " duplicate: " + Arrays.toString(arr));
	}
	private static void testanyduplicate()
	{
		testanyduplicateAll(new int[] {2, 3, 1, 0, 2, 5, 3});
		testanyduplicateAll(new int[] {0, 1, 2, 2, 3, 3, 5});
		testanyduplicateAll(new int[] {5, 3, 3, 2, 2, 1, 0});
		testanyduplicateAll(new int[] {2, 3, 1, 2, 5, 3});
	}
	private static void testanyduplicateAll(int[] arr)
	{	
		System.out.println(anyduplicate(arr) + " anydup: " + Arrays.toString(arr));
	}
	private static void testfind()
	{
		int[][] arr = new int[][] {{1, 3, 5}, {7, 9, 11}, {13, 15, 17}};
		System.out.println("find: " + Arrays.deepToString(arr));
		System.out.println("4 " + find(arr, 4));
		System.out.println("8 " + find(arr, 8));
		System.out.println("9 " + find(arr, 9));
		System.out.println("15 " + find(arr, 15));
		System.out.println("20 " + find(arr, 20));
		System.out.println("14 " + find(arr, 14));
	}
	private static void testfindIn2DArray()
	{
		int[][] arr = new int[][] {{1, 2, 3, 5, 8, 10}, {2, 4, 9, 12, 14, 16}, {4, 7, 10, 13, 17, 20}, 
					{6, 8, 11, 15, 18, 23}};
		System.out.println("find2D: " + Arrays.deepToString(arr));
		System.out.println("6 " + findIn2DArray(arr, 6));
		System.out.println("0 " + findIn2DArray(arr, 0));
		System.out.println("17 " + findIn2DArray(arr, 17));
		System.out.println("25 " + findIn2DArray(arr, 25));
		System.out.println("10 " + findIn2DArray(arr, 10));
		System.out.println("11 " + findIn2DArray(arr, 11));
		System.out.println("14 " + findIn2DArray(arr, 14));
		System.out.println("19 " + findIn2DArray(arr, 19));
	}

	public static void main(String[] args)
	{
		testduplicate();
		testanyduplicate();
		testfind();
		testfindIn2DArray();
	}
}



