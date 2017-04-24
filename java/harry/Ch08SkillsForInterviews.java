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
public class Ch08SkillsForInterviews
{
	// 90. Given a positive value s, print all sequences with continuous numbers (with two numbers atleast) whose sum is s.
	// Take the input s=15 as an example. Because 1+2+3+4+5=4+5+6=7+8=15, three continuous sequences should
	// be printed: 1~5, 4~6, and 7~8.
	public static ArrayList<ArrayList<Integer>> contSeq(int s)
	{
		ArrayList<ArrayList<Integer>> res = new ArrayList<>();

		int left = 1, right = 2;
		int sum = left + right, top = 1 + s/2;
		while(right <= top) {
			if(sum == s) {
				ArrayList<Integer> a = new ArrayList<>();
				for(int i = left; i <= right; i++)
					a.add(i);
				res.add(a);
			}
			if(sum >= s) {
				sum -= left;
				left++;
			}
			else {
				right++;
				sum += right;
			}
		}
		return res;
	}
	private static void testcontSeq()
	{
		System.out.println("15: " + contSeq(15).toString());
		System.out.println("16: " + contSeq(16).toString());
		System.out.println("18: " + contSeq(18).toString());
		System.out.println("100: " + contSeq(100).toString());
	}

	public static char[] leftRotate(char[] st, int pos) {
		char[] str = Arrays.copyOf(st, st.length);
		pos %= str.length;
		if(pos <= 0) return str;
		reverse(str, 0, pos-1);
		reverse(str, pos, str.length-1);
		reverse(str, 0, str.length-1);
		
		return str;
	}
	private static void reverse(char[] str, int start, int end) {
		while(start < end) {
			char ch = str[start];
			str[start] = str[end];
			str[end] = ch;
			
			start++; end--;
		}
	}
	private static void testleftRotate()
	{
		char[] st = {'a', 'b', 'c', 'd', 'e', 'f', 'g'};
		System.out.println(Arrays.toString(st) + " 2 " + Arrays.toString(leftRotate(st, 2)));
		System.out.println(Arrays.toString(st) + " 3 " + Arrays.toString(leftRotate(st, 3)));
		System.out.println(Arrays.toString(st) + " 4 " + Arrays.toString(leftRotate(st, 4)));
		System.out.println(Arrays.toString(st) + " 7 " + Arrays.toString(leftRotate(st, 7)));
		System.out.println(Arrays.toString(st) + " -12 " + Arrays.toString(leftRotate(st, -12)));
		System.out.println(Arrays.toString(st) + " 22 " + Arrays.toString(leftRotate(st, 22)));
	}

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
		// remove item out of window even if it is MAX
		if(!dq.isEmpty() && (i - dq.getFirst()) >= k) {
			dq.removeFirst();
		}
		while(!dq.isEmpty() && arr[dq.getLast()] <= arr[i]) {
			dq.removeLast();
		}
		dq.addLast(i);
	}

	private static void testmaxInSlidingWindow()
	{
		int[] arr = {2, 3, 4, 2, 6, 2, 5, 1};
		System.out.println("max 3: " + Arrays.toString(arr) + " " + Arrays.toString(maxInSlidingWindow(arr, 3)));
		arr = new int[] {1, 3, -1, -3, 5, 3, 6, 7};
		System.out.println("max 3: " + Arrays.toString(arr) + " " + Arrays.toString(maxInSlidingWindow(arr, 3)));
		arr = new int[] {1, 2, 3, 5, 3, 1, 6};
		System.out.println("max 3: " + Arrays.toString(arr) + " " + Arrays.toString(maxInSlidingWindow(arr, 3)));
	}
	
	public static void main(String[] args)
	{
		testcontSeq();
		testleftRotate();
		testmaxInSlidingWindow();
	}
	
}



