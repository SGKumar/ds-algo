package dp;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.BitSet;

import tree.TreeNode;

// InterviewBit questions
public class DynProg2
{
	// maximum value contiguous subsequence 
	// give algo for finding contiguous subsequence A(i).....A(j) in an array of n numbers 
	// for which sum is maximum.
	// OR maximum sum subarray
	public static int maxsum(int[] a)
	{
		int currSum = 0, maxSum = 0, negSum = 0;
		for(int i = 0; i < a.length; i++) {
			if(a[i] >= 0) {
				currSum += a[i];
			}
			else {
				negSum += a[i];
				if((i < a.length - 1) && (a[i+1] > 0)) {
					if(currSum + negSum > 0) {
						currSum += negSum;
					}
					else {
						currSum = 0;
					}
					negSum = 0;
				}
			}
			if(currSum > maxSum) {
				maxSum = currSum;
			}
		}
		return maxSum;
	}
	public static int maxsumDP(int[] a)
	{
		if(a == null || a.length == 0 || (a.length == 1 && a[0] <= 0)) {
			return 0;
		}
		int[] maxarr = new int[a.length];
		maxarr[0] = (a[0] > 0)?a[0]:0;
		int maxSum = maxarr[0];
		for(int i = 1; i < a.length; i++) {
			maxarr[i] = ((maxarr[i-1] + a[i]) > 0)?(maxarr[i-1] + a[i]):0;
			maxSum = (maxSum > maxarr[i])?maxSum : maxarr[i];
		}
		return maxSum;
	}
	public static int[] maxsum2(int[] a)
	{
		if(a == null || a.length == 0 || (a.length == 1 && a[0] <= 0)) {
			return (new int[]{0});
		}
		int currSum = 0, maxSum = 0;
		int begin = 0, end = 0, currBegin = 0;
		for(int i = 0; i < a.length; i++) {
			currSum += a[i];
			if(currSum < 0) {
				currSum = 0;
				currBegin = i+1;
			}
			if(currSum > maxSum) {
				maxSum = currSum;
				end = i;
				begin = currBegin;
			}
		}
		return (new int[] {maxSum, begin, end});
	}
	public static int maxsumDPFromBack(int[] arr)
	{
		int len = arr.length, maxSum = 0;
		int[] sum = new int[len];
		sum[len-1] = (arr[len-1] > 0)?arr[len-1]:0;
		for(int i = len - 2; i >= 0; i--) {
			sum[i] = (arr[i] + sum[i+1] > 0)? (arr[i] + sum[i+1]):0;
			if(maxSum < sum[i]) {
				maxSum = sum[i];
			}
		}
		return maxSum;
	}

	public static int[] maxsum2FromBack(int[] a)
	{
		if(a == null || a.length == 0 || (a.length == 1 && a[0] <= 0)) {
			return (new int[]{0});
		}
		int currSum = 0, maxSum = 0;
		int currEnd = a.length - 1, begin = 0, end = 0;
		for(int i = a.length-1; i >= 0; i--) {
			currSum += a[i];
			if(currSum < 0) {
				currSum = 0;
				currEnd = i-1;
			}
			else if(currSum > maxSum) {
				maxSum = currSum;
				begin = i;
				end = currEnd;
			}
		}
		return (new int[] {maxSum, begin, end});
	}
	
	public static int maxsumNo2Adj(int[] a)
	{
		if(a == null || a.length == 0) return 0;
		if(a.length == 1) return a[0];
		if(a.length == 2) return (a[0] > a[1])?a[0]:a[1];
		
		int[] sums = new int[a.length];
		sums[0] = a[0];
		sums[1] = (a[0] > a[1])?a[0]:a[1];
		for(int i = 2; i < a.length; i++) {
			sums[i] = ((sums[i-2] + a[i]) > sums[i-1])?(sums[i-2]+a[i]):sums[i-1];
		}
		return sums[a.length-1];
	}
	
	public static int zigzagMaxlen(int[] a)
	{
		if(a == null || a.length == 0) return 0;
		if(a.length == 1) return 1;
		
		int[] up = new int[a.length], dn = new int[a.length];
		for(int i = 0; i < a.length; i++) {
			up[i] = 1;
			dn[i] = 1;
		}
		for(int i = 0; i < a.length; i++) {
			for(int j = 0; j < i; j++) {
				if(a[i] > a[j]) {
					up[i] = Math.max(up[i], 1+dn[j]);
				}
				else if(a[i] < a[j]) {
					dn[i] = Math.max(dn[i], 1+up[j]);
				}
			}
		}
		int maxlen = 0;
		for(int i = 0; i < a.length; i++) {
			maxlen = max(up[i], dn[i], maxlen);
		}
		System.out.println("UP Array :" + Arrays.toString(up));
		System.out.println("DN Array :" + Arrays.toString(dn));
		return maxlen;
	}
	private static int max(int val1, int val2, int val3)
	{
		return Math.max(val1, Math.max(val2, val3));
	}
	
	public static int maxbitonic(int[] a)
	{
		int[] lis = lisFromFront(a);
		int[] lisback = lisFromBack(a);
		int maxlen = 0;
		for(int i = 0; i < a.length; i++) {
			maxlen = Math.max(maxlen, lis[i] + lisback[i] -1);
		}
		return maxlen;
	}
	private static int[] lisFromBack(int[] a)
	{
		int[] lisback = new int[a.length];
		for(int i = 0; i < a.length; i++) {
			lisback[i] = 1;
		}
		for(int i = a.length - 1; i >= 0; i--) {
			for(int j = a.length - 1; j > i; j--) {
				if(a[i] > a[j]) {
					lisback[i] = Math.max(lisback[i], 1+lisback[j]);
				}
			}
		}
		return lisback;
	}

	public static int[] lis(int[] a)
	{
		int[] lis = lisFromFront(a);
		int maxlen = 0, elem = 0;
		for(int i = 0; i < lis.length; i++) {
			if(lis[i] > maxlen) {
				maxlen = lis[i];
				elem = i;
			}
		}

		// find actual lis, elem has last max index
		int[] lisCore = new int[maxlen];
		int iMax = elem, max = maxlen;

		lisCore[max-1] = a[elem];
		for(int i = elem-1; i >= 0 && max >= 0; i--) {
			if(lis[i] == max-1 && a[i] < lisCore[max-1]) {
				max--;
				lisCore[max-1] = a[i];
			}
		}
		
		return lisCore;
	}
	private static int[] lisFromFront(int[] a)
	{
		int[] lis = new int[a.length];
		for(int i = 0; i < a.length; i++) {
			lis[i] = 1;
		}
		for(int i = 0; i < a.length; i++) {
			for(int j = 0; j < i; j++) {
				if(a[i] > a[j]) {
					lis[i] = Math.max(lis[i], 1+lis[j]);
				}
			}
		}
		return lis;
	}

	// Other problems from outside
	// 1. Shortest prefix to make curr string a palindrome
	public static String shortestPrefix(String s)
	{
		int left = 0, right = s.length()-1, prefixLen = 0;
		String pal = new StringBuilder(s).reverse().toString();
		while(left < right) {
			if(s.charAt(left) == s.charAt(right)) {
				left++;
			}
			else {
				prefixLen = s.length() - right;
				left = 0;
			}
			right--;
		}
		return pal.substring(0, prefixLen);
	}
	
	public static int arrangeHorses(String horses, int K)
	{
		if(horses == null || K < 1) return -1;
		int N = horses.length();
		if(N < K) return -1;
		if(N == K) return 0;
		
		int dp[][] = new int[N + 1][K + 1];
		// Assuming all are initialized to 0
		// N - num of horses, K - num of stables
		for(int n = 2; n <= N; n++) {
			dp[n][1] = prod(horses, 0, n-1);
		}
		
		int zz = 0;
		for(int k = 2; k <= K; k++) {
			for(int n = k+1; n <= N; n++) {
				dp[n][k] = Integer.MAX_VALUE;
				// Use up k-1 till you leave just 1
				for(int i = k-1; i <= n-1; i++) {
					dp[n][k] = Math.min(dp[n][k], dp[i][k-1] + prod(horses, i, n-1));

					//System.out.println(zz++ + ": " + Arrays.deepToString(dp));
				}
			}
		}
		return dp[N][K];
	}
	private static int prod(String horses, int start, int end)
	{
		//int len = horses.length();
		int W = 0, B = 0;
		for(int i = start; i <= end; i++) {
			if('W' == horses.charAt(i)) {
				W++;
			}
			else if('B' == horses.charAt(i)){
				B++;
			}
		}
		return W*B;
	}

	public static int minInsForPali(String s)
	{
		int len = s.length();
		int left = 0, right = len - 1, ins = 0;
		while(left < right) {
			if(s.charAt(left) == s.charAt(right)) {
				right--;
			}
			else {
				ins++;
			}
			left++;
		}
		return ins;
	}
	private static void testshortestPrefix()
	{
		System.out.println("Shortest prefix for pali: abab " + shortestPrefix("abab"));
		System.out.println("Shortest prefix for pali: axcbab " + shortestPrefix("axcbab"));
	}
	
	private static void testmaxsum()
	{
		testmaxsumAll(new int[] {-2, 11, -4, 13, -18, 2});
		testmaxsumAll(new int[] {-2, 11, -4, -10, 13, 1, -18, 2, 4, 6, 3});
		testmaxsumAll(new int[] {-18, -5, -1, -2, -1, -3, -20});
		testmaxsumAll(new int[] {-2, -11, -4, 0, -13, -5, -2, -1});
		testmaxsumAll(new int[] {-1, 3, -5, 4, 6, -1, 2, -7, 13, -3});
		testmaxsumAll(new int[] {-2, 11, -4, 13, -5, 2});
		testmaxsumAll(new int[] {-2, -3, 4, -1, -2, 1, 5, -3});
		testmaxsumAll(new int[] {-2, 1, -3, 4, -1, 2, 1, -5, 4});
		testmaxsumAll(new int[] {18, -5, 1, -1, 1, -2, 2});
	}
	private static void testmaxsumAll(int[] arr)
	{	
		System.out.println(maxsum(arr) + " maxsum: " + Arrays.toString(arr));
		System.out.println(maxsumDP(arr) + " maxsumDP: " + Arrays.toString(arr));
		System.out.println(Arrays.toString(maxsum2(arr)) + " maxsum2: " + Arrays.toString(arr));
		System.out.println(maxsumDPFromBack(arr) + " maxsumDPBack: " + Arrays.toString(arr));
		System.out.println(Arrays.toString(maxsum2FromBack(arr)) + " maxsum2Back: " + Arrays.toString(arr));
	}
	private static void testmaxsumNo2Adj()
	{
		int[] arr = new int[] {3, 2, 7, 10};
		System.out.println(maxsumNo2Adj(arr) + " maxsumNo2Adj: " + Arrays.toString(arr));
		arr = new int[] {3, 2, 5, 10, 7};
		System.out.println(maxsumNo2Adj(arr) + " maxsumNo2Adj: " + Arrays.toString(arr));
		arr = new int[] {5, 5, 10, 40, 50, 35};
		System.out.println(maxsumNo2Adj(arr) + " maxsumNo2Adj: " + Arrays.toString(arr));
		arr = new int[] {5, 5, 10, 100, 10, 5};
		System.out.println(maxsumNo2Adj(arr) + " maxsumNo2Adj: " + Arrays.toString(arr));
		arr = new int[] {4, 1, 1, 4, 2, 1};
		System.out.println(maxsumNo2Adj(arr) + " maxsumNo2Adj: " + Arrays.toString(arr));
		arr = new int[] {2, 5, 3, 1, 7};
		System.out.println(maxsumNo2Adj(arr) + " maxsumNo2Adj: " + Arrays.toString(arr));
		arr = new int[] {8, 3, 1, 7};
		System.out.println(maxsumNo2Adj(arr) + " maxsumNo2Adj: " + Arrays.toString(arr));
		arr = new int[] {1, 2, 3, 4, 5};
		System.out.println(maxsumNo2Adj(arr) + " maxsumNo2Adj: " + Arrays.toString(arr));
		arr = new int[] {-1, 4, -2, 6, 8, 9};
		System.out.println(maxsumNo2Adj(arr) + " maxsumNo2Adj: " + Arrays.toString(arr));
		arr = new int[] {1, 3, 1, 4, 1, 3, 20};
		System.out.println(maxsumNo2Adj(arr) + " maxsumNo2Adj: " + Arrays.toString(arr));
	}
	private static void testzigzagMaxlen()
	{
		int[] arr = new int[] {2, -1, 4, 3, 5, -1, 3, 2};
		System.out.println(zigzagMaxlen(arr) + " zigzag: " + Arrays.toString(arr));
		arr = new int[] {1, 7, 4, 9, 2, 5 };
		System.out.println(zigzagMaxlen(arr) + " zigzag: " + Arrays.toString(arr));
		arr = new int[] { 1, 17, 5, 10, 13, 15, 10, 5, 16, 8 };
		System.out.println(zigzagMaxlen(arr) + " zigzag: " + Arrays.toString(arr));
		arr = new int[] {44};
		System.out.println(zigzagMaxlen(arr) + " zigzag: " + Arrays.toString(arr));
		arr = new int[] {};
		System.out.println(zigzagMaxlen(arr) + " zigzag: " + Arrays.toString(arr));
		arr = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		System.out.println(zigzagMaxlen(arr) + " zigzag: " + Arrays.toString(arr));
		arr = new int[] { 70, 55, 13, 2, 99, 2, 80, 80, 80, 80, 100, 19, 7, 5, 5, 5, 1000, 32, 32 };
		System.out.println(zigzagMaxlen(arr) + " zigzag: " + Arrays.toString(arr));
		arr = new int[] { 374, 40, 854, 203, 203, 156, 362, 279, 812, 955, 600, 947, 978, 46, 100,
							953, 670, 862, 568, 188, 67, 669, 810, 704, 52, 861, 49, 640, 370, 908,
							477, 245, 413, 109, 659, 401, 483, 308, 609, 120, 249, 22, 176, 279, 23,
							22, 617, 462, 459, 244 };
		System.out.println(zigzagMaxlen(arr) + " zigzag: " + Arrays.toString(arr));
		arr = new int[] { 3, 1, 4, 1, 5, 9, 2, 6, 5, 3 };
		System.out.println(zigzagMaxlen(arr) + " zigzag: " + Arrays.toString(arr));
		arr = new int[] { 10, 22, 9, 33, 21, 50, 41, 60, 80 };
		System.out.println(zigzagMaxlen(arr) + " zigzag: " + Arrays.toString(arr));
	}
	private static void testmaxbitonic()
	{
		
		int[] arr = new int[] {2, -1, 4, 3, 5, -1, 3, 2};
		System.out.println(maxbitonic(arr) + " bitonic: " + Arrays.toString(arr));
		arr = new int[] {1, 7, 4, 9, 2, 5 };
		System.out.println(maxbitonic(arr) + " bitonic: " + Arrays.toString(arr));
		arr = new int[] { 1, 17, 5, 10, 13, 15, 10, 5, 16, 8 };
		System.out.println(maxbitonic(arr) + " bitonic: " + Arrays.toString(arr));
		arr = new int[] {44};
		System.out.println(maxbitonic(arr) + " bitonic: " + Arrays.toString(arr));
		arr = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		System.out.println(maxbitonic(arr) + " bitonic: " + Arrays.toString(arr));
		arr = new int[] { 3, 1, 4, 1, 5, 9, 2, 6, 5, 3};
		System.out.println(maxbitonic(arr) + " bitonic: " + Arrays.toString(arr));
		arr = new int[] { 10, 22, 9, 33, 21, 50, 41, 60, 80 };
		System.out.println(maxbitonic(arr) + " bitonic: " + Arrays.toString(arr));
		arr = new int[] { 1, 11, 2, 10, 4, 5, 2, 1 };
		System.out.println(maxbitonic(arr) + " bitonic: " + Arrays.toString(arr));
		arr = new int[] { 12, 11, 40, 5, 3, 1 };
		System.out.println(maxbitonic(arr) + " bitonic: " + Arrays.toString(arr));
		arr = new int[] { 80, 60, 30, 40, 20, 10 };
		System.out.println(maxbitonic(arr) + " bitonic: " + Arrays.toString(arr));
		arr = new int[] { 0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15 };
		System.out.println(maxbitonic(arr) + " bitonic: " + Arrays.toString(arr));
	}
	private static void testlis()
	{
		int[] arr = new int[] { 0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15 };
		System.out.println(Arrays.toString(lis(arr)) + " lis: " + Arrays.toString(arr));
		arr = new int[] { 5, 6, 2, 3, 4, 1, 9, 9, 8, 9, 5};
		System.out.println(Arrays.toString(lis(arr)) + " lis: " + Arrays.toString(arr));
	}
	private static void testarrangeHorses()
	{
		System.out.println("Min aggro for BBWBWB, K-3 " + arrangeHorses("BBWBWB", 3));
		System.out.println("Min aggro for BWBWBBWWBWBWBWBBBWWW, K-4 " + arrangeHorses("BWBWBBWWBWBWBWBBBWWW", 4));
		System.out.println("Min aggro for BWBWBBWWBWBWBWBBBWWW, K-5 " + arrangeHorses("BWBWBBWWBWBWBWBBBWWW", 5));
		System.out.println("Min aggro for BWBWBBWWBWBWBWBBBWWW, K-6 " + arrangeHorses("BWBWBBWWBWBWBWBBBWWW", 6));
		System.out.println("Min aggro for WWBB, K-2: " + arrangeHorses("WWBB", 2));
	}
	private static void testminInsForPali()
	{
		System.out.println("Min Ins for pali: ab " + minInsForPali("ab"));
		System.out.println("Min Ins for pali: aa " + minInsForPali("aa"));
		System.out.println("Min Ins for pali: abcd " + minInsForPali("abcd"));
		System.out.println("Min Ins for pali: abcda " + minInsForPali("abcda"));
		System.out.println("Min Ins for pali: abcde " + minInsForPali("abcde"));
		System.out.println("Min Ins for pali: abcdcb " + minInsForPali("abcdcb"));
	}
	public static void main(String[] args)
	{
		/*
		testmaxsum();
		testmaxsumNo2Adj();
		*/
		testzigzagMaxlen();
		/*
		testmaxbitonic();
		*/
		testlis();
		testarrangeHorses();
		testminInsForPali();
		testshortestPrefix();
		
		int a = 1000*1000*1000+7;
		System.out.println("a " + a);
	}
}

