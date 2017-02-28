package dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import tree.TreeNode;

public class DynProgB
{
	// longest common subsequence
	public static int lcsmem(char[] str1, char[] str2)
	{
		char S[] = null, T[] = null;
		if(str1.length < str2.length) {
			T = str1; S = str2;
		}
		else {
			S = str1; T = str2;
		}
		int rows = S.length, cols = T.length;
		int[][] dp = new int[2][cols+1];

		for(int row = 0; row < 2; row++) {
			dp[row][0] = 0;
		}
		for(int col = 0; col <= cols; col++) {
			dp[0][col] = 0;
		}

		for(int row = 1; row <= rows; row++) {
			for(int col = 1; col <= cols; col++) {
				if(S[row-1] == T[col-1]) {
					dp[1][col] = 1 + dp[0][col-1];
				}
				else {
					dp[1][col] = Math.max(dp[0][col], dp[1][col-1]);
				}
			}
			System.arraycopy(dp[1], 1, dp[0], 1, cols);
		}
		return dp[1][cols];
	}
	public static String lcs(char[] S, char[] T)
	{
		int[][] dp = new int[S.length+1][T.length+1];
		int rows = S.length, cols = T.length;

		for(int row = 0; row <= rows; row++) {
			dp[row][0] = 0;
		}
		for(int col = 0; col <= cols; col++) {
			dp[0][col] = 0;
		}

		for(int row = 1; row <= rows; row++) {
			for(int col = 1; col <= cols; col++) {
				if(S[row-1] == T[col-1]) {
					dp[row][col] = 1 + dp[row-1][col-1];
				}
				else {
					dp[row][col] = Math.max(dp[row-1][col], dp[row][col-1]);
				}
			}
		}

		// Find the string
		int len = dp[rows][cols];
		char lcs[] = new char[len];
		int row = rows, col = cols, i = len-1;
		while(row > 0 && col > 0) {
			if(S[row-1] == T[col-1]) {
				lcs[i] = S[row-1];
				i--; row--; col--;
			}
			else {
				if(dp[row-1][col] > dp[row][col-1]) {
					row--;
				}
				else {
					col--;
				}
			}
		}
		return new String(lcs);
	}
	public static String lcs_r(char[] S, char[] T)
	{
		int[][] dp = new int[S.length+1][T.length+1];
		int rows = S.length, cols = T.length;

		for(int row = 0; row <= rows; row++) {
			dp[row][cols] = 0;
		}
		for(int col = 0; col <= cols; col++) {
			dp[rows][col] = 0;
		}
		
		for(int row = rows-1; row >= 0; row--) {
			for(int col = cols-1; col >= 0; col--) {
				if(S[row] == T[col]) {
					dp[row][col] = 1 + dp[row+1][col+1];
				}
				else {
					dp[row][col] = Math.max(dp[row+1][col], dp[row][col+1]);
				}
			}
		}
		
		// Find the string
		int len = dp[0][0];
		char lcs[] = new char[len];
		int row = 0, col = 0, i = 0;
		while(row < rows && col < cols) {
			if(S[row] == T[col]) {
				lcs[i] = S[row];
				i++; row++; col++;
			}
			else {
				if(dp[row+1][col] > dp[row][col+1]) {
					row++;
				}
				else {
					col++;
				}
			}
		}
		return new String(lcs);
	}
	public static int lcsmem_r(char[] str1, char[] str2)
	{
		char S[] = null, T[] = null;
		if(str1.length < str2.length) {
			T = str1; S = str2;
		}
		else {
			S = str1; T = str2;
		}
		int rows = S.length, cols = T.length;
		int[][] dp = new int[2][T.length+1];

		for(int row = 0; row < 2; row++) {
			dp[row][cols] = 0;
		}
		for(int col = 0; col <= cols; col++) {
			dp[1][col] = 0;
		}
		
		for(int row = rows-1; row >= 0; row--) {
			for(int col = cols-1; col >= 0; col--) {
				if(S[row] == T[col]) {
					dp[0][col] = 1 + dp[1][col+1];
				}
				else {
					dp[0][col] = Math.max(dp[1][col], dp[0][col+1]);
				}
			}
			System.arraycopy(dp[0], 0, dp[1], 0, cols);
		}
		return dp[0][0];
	}
	
	// longest common substring
	public static int lcsubstrmem(char[] str1, char[] str2)
	{
		char S[], T[];
		if(str1.length < str2.length) {
			T = str1; S = str2;
		}
		else {
			S = str1; T = str2;
		}
		int[][] dp = new int[2][T.length+1];
		int rows = S.length, cols = T.length, len = 0;

		for(int row = 0; row < 2; row++) {
			dp[row][0] = 0;
		}
		for(int col = 0; col <= cols; col++) {
			dp[0][col] = 0;
		}
		
		for(int row = 1; row <= rows; row++) {
			for(int col = 1; col <= cols; col++) {
				if(S[row-1] == T[col-1]) {
					dp[1][col] = 1 + dp[0][col-1];
				}
				else {
					dp[1][col] = 0;
				}
				len = Math.max(len, dp[1][col]);
			}
			System.arraycopy(dp[1], 1, dp[0], 1, cols);
		}
		return len;
	}
	public static String lcsubstr(char[] S, char[] T)
	{
		int[][] dp = new int[S.length+1][T.length+1];
		int rows = S.length, cols = T.length;
		int len = 0, maxRow = 0, maxCol = 0;

		for(int row = 0; row <= rows; row++) {
			dp[row][0] = 0;
		}
		for(int col = 0; col <= cols; col++) {
			dp[0][col] = 0;
		}
		
		for(int row = 1; row <= rows; row++) {
			for(int col = 1; col <= cols; col++) {
				if(S[row-1] == T[col-1]) {
					dp[row][col] = 1 + dp[row-1][col-1];
				}
				else {
					dp[row][col] = 0;
				}
				if(len < dp[row][col]) {
					len = dp[row][col];
					maxRow = row; maxCol = col;
				}
			}
		}
		
		// Find the string
		char lcs[] = new char[len];
		int row = maxRow;
		while(len > 0) {
			lcs[len-1] = S[row-1];
			len--; row--;
		}
		return new String(lcs);
	}
	public static String lcsubstr_r(char[] S, char[] T)
	{
		int[][] dp = new int[S.length+1][T.length+1];
		int rows = S.length, cols = T.length;
		int len = 0, maxRow = 0, maxCol = 0;

		for(int row = 0; row <= rows; row++) {
			dp[row][cols] = 0;
		}
		for(int col = 0; col <= cols; col++) {
			dp[rows][col] = 0;
		}
		
		for(int row = rows-1; row >= 0; row--) {
			for(int col = cols-1; col >= 0; col--) {
				if(S[row] == T[col]) {
					dp[row][col] = 1 + dp[row+1][col+1];
				}
				else {
					dp[row][col] = 0;
				}
				if(len < dp[row][col]) {
					len = dp[row][col];
					maxRow = row; maxCol = col;
				}
			}
		}
		
		// Find the string
		int i = 0;
		char lcs[] = new char[len];
		int row = maxRow;
		while(i < len) {
			lcs[i] = S[row];
			i++; row++;
		}
		return new String(lcs);
	}
	public static int lcsubstrmem_r(char[] str1, char[] str2)
	{
		char S[] = null, T[] = null;
		if(str1.length < str2.length) {
			T = str1; S = str2;
		}
		else {
			S = str1; T = str2;
		}
		int rows = S.length, cols = T.length;
		int[][] dp = new int[2][T.length+1];
		int len = 0;

		for(int row = 0; row < 2; row++) {
			dp[row][cols] = 0;
		}
		for(int col = 0; col <= cols; col++) {
			dp[1][col] = 0;
		}
		
		for(int row = rows-1; row >= 0; row--) {
			for(int col = cols-1; col >= 0; col--) {
				if(S[row] == T[col]) {
					dp[0][col] = 1 + dp[1][col+1];
				}
				else {
					dp[0][col] = 0;
				}
				len = Math.max(len, dp[0][col]);
			}
			System.arraycopy(dp[0], 0, dp[1], 0, cols);
		}
		
		return len;
	}

	private static void testlcs(char[] S, char[] T) {
		System.out.println("lcs mem " + Arrays.toString(S)+ ", " + Arrays.toString(T) + " is " + lcsmem(S, T));
		System.out.println("lcsmem_r " + Arrays.toString(S)+ ", " + Arrays.toString(T) + " is " + lcsmem_r(S, T));
		System.out.println("lcs of " + Arrays.toString(S)+ ", " + Arrays.toString(T) + " is " + lcs(S, T));
		System.out.println("lcs_r  " + Arrays.toString(S)+ ", " + Arrays.toString(T) + " is " + lcs_r(S, T));
	}
	private static void testlcsubstr(char[] S, char[] T) {
		System.out.println("mem of " + Arrays.toString(S)+ ", " + Arrays.toString(T) + " is " + lcsubstrmem(S, T));
		System.out.println("mem_r of " + Arrays.toString(S)+ ", " + Arrays.toString(T) + " is " + lcsubstrmem_r(S, T));
		System.out.println("lcsubstr of " + Arrays.toString(S)+ ", " + Arrays.toString(T) + " is " + lcsubstr(S, T));
		System.out.println("lcsubstr_r  " + Arrays.toString(S)+ ", " + Arrays.toString(T) + " is " + lcsubstr_r(S, T));
	}
	
	public static void main(String args[])
    {
		testlcs(new char[] {'A', 'B', 'C', 'D', 'E', 'F'}, new char[] {'A', 'C', 'B', 'C', 'F'});
		testlcs(new char[] {'A', 'C', 'B', 'C', 'F'}, new char[] {'A', 'B', 'C', 'D', 'E', 'F'});
		testlcs(new char[] {'A', 'B', 'D', 'F', 'E'}, new char[] {'A', 'B', 'C', 'D', 'E'});
		testlcs(new char[] {'A', 'B', 'C', 'D', 'E'}, new char[] {'A', 'B', 'D', 'F', 'E'});
		testlcs(new char[] {'A', 'B', 'C', 'D', 'E'}, new char[] {'A', 'E', 'B', 'D', 'F'});
		testlcs(new char[] {'A', 'E', 'B', 'D', 'F'}, new char[] {'A', 'B', 'C', 'D', 'E'});
		testlcs(new char[] {'A', 'C', 'B', 'A', 'E', 'D'}, new char[] {'A', 'B', 'C', 'A', 'D', 'F'});
		testlcs(new char[] {'A', 'B', 'C', 'A', 'D', 'F'}, new char[] {'A', 'C', 'B', 'A', 'E', 'D'});
		testlcs(new char[] {'A', 'B', 'C', 'D', 'S', 'E', 'F', 'G', 'D'}, new char[] {'A', 'C', 'F', 'E', 'F', 'X', 'V', 'G', 'A', 'B'});

		testlcsubstr(new char[] {'Y', 'A', 'B', 'Z', 'X'}, new char[] {'Z', 'X', 'A', 'B', 'Z', 'Y'});
		testlcsubstr(new char[] {'Z', 'X', 'A', 'B', 'Z', 'Y'}, new char[] {'Y', 'A', 'B', 'Z', 'X'});
		testlcsubstr(new char[] {'A', 'B', 'C', 'D', 'S', 'E', 'F', 'G', 'D'}, new char[] {'A', 'C', 'F', 'E', 'F', 'X', 'V', 'G', 'A', 'B'});

		/*
		testMaxSumContigSubSequence(new int[] {-18, -5, -1, -2, -1, -3, -20});
		testMaxSumContigSubSequence(new int[] {-2, -11, -4, 0, -13, -5, -2, -1});
		testMaxSumContigSubSequence(new int[] {-1, 3, -5, 4, 6, -1, 2, -7, 13, -3});
		testMaxSumContigSubSequence(new int[] {-2, 11, -4, 13, -5, 2});
		testMaxSumContigSubSequence(new int[] {-2, -3, 4, -1, -2, 1, 5, -3});
		testMaxSumContigSubSequence(new int[] {-2, 1, -3, 4, -1, 2, 1, -5, 4});
		testMaxSumContigSubSequence(new int[] {18, -5, 1, -1, 1, -2, 2});

		testchangeMinCoins(new int[] {1, 3, 9, 10}, 15);
		testchangeMinCoins(new int[] {2, 4, 5, 10}, 9);
		testchangeMinCoins(new int[] {2, 4, 5, 10}, 11);
		testchangeMinCoins(new int[] {2, 4, 5, 10}, 13);
		testchangeMinCoins(new int[] {2, 5, 3, 6}, 10);
		testchangeMinCoins(new int[] {10, 5, 4, 2}, 13);
		testchangeMinCoins(new int[] {9, 5, 1, 6}, 11);
		testchangeMinCoins(new int[] {1, 5, 10, 25}, 35);
		testchangeMinCoins(new int[] {1, 5, 10, 25}, 37);
		testchangeMinCoins(new int[] {1, 3, 9, 19, 26}, 65);
		testchangeMinCoins(new int[] {1, 3, 9, 19, 26}, 66);
		testchangeMinCoins(new int[] {1, 3, 9, 10}, 15);
		testchangeMinCoins(new int[] {1, 3, 9, 10}, 15);
		testchangeMinCoins(new int[] {1, 3, 9, 10}, 15);

		testthiefMostValueKnapSack01(new int[] {3, 4, 5, 6}, new int[] {2, 3, 4, 5}, 5);
		testthiefMostValueKnapSack01(new int[] {15, 10, 9, 5}, new int[] {1, 5, 3, 4}, 8);
		testthiefMostValueKnapSack01(new int[] {15, 9, 5, 10}, new int[] {1, 3, 4, 5}, 8);
		testthiefMostValueKnapSack01(new int[] {3, 4, 5, 8, 10}, new int[] {2, 3, 4, 5, 9}, 20);
		testthiefMostValueKnapSack01(new int[] {10, 4, 9, 11}, new int[] {3, 8, 9, 8}, 20);
		testthiefMostValueKnapSack01(new int[] {6, 4, 5, 3, 9, 7}, new int[] {4, 2, 3, 1, 6, 4}, 10);
		testthiefMostValueKnapSack01(new int[] {1, 2, 5, 9, 4}, new int[] {2, 3, 3, 4, 6}, 10);
		
		testfindLongestIncSubseq(new int[] {1, 3, 9, 10, 15});
		testfindLongestIncSubseq(new int[] {5, 6, 2, 3, 4, 1, 9, 9, 8, 9, 5});
		testfindLongestIncSubseq(new int[] {0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15});
		testfindLongestIncSubseq(new int[] {1, 12, 7, 0, 23, 11, 52, 31, 61, 69, 70, 2});
		
		testfindSubsetSum(new int[] {3, 2, 4, 19, 3, 7, 13, 10, 6, 11}, 17);
		testfindSubsetSum(new int[] {3, 2, 14, 19, 3, 7, 13, 10, 16, 11}, 6);
		testfindSubsetSum(new int[] {3, 2, 4, 7, 10, 16, 11}, 6);
		testfindSubsetSum(new int[] {3, 2, 5, 7, 10, 16, 3, 11}, 6);
		testfindSubsetSum(new int[] {3, 2, 5, 8, 10, 16, 3, 11}, 9);
		//testfindSubsetSum(new int[] {3, 2, 14, 19, 3, 7, 13, 10, 16, 11}, 89);
		//testfindSubsetSum(new int[] {3, 2, 14, 19, 3, 7, 13, 10, 16, 11}, 101);
		
		maxMoney(new int[] {5, 3, 7, 10});
		maxMoney(new int[] {8, 15, 3, 7});
		maxMoney(new int[] {3, 2, 2, 3, 1, 2});

		testcountBoolParantheses(new boolean[] {true, true, false, true}, new char[] {'|', '&', '^'});
		testcountBoolParantheses(new boolean[] {true, false, true}, new char[] {'^', '&'});

		testgetEditDistance(new char[] {'S', 'A', 'T', 'U', 'R', 'D', 'A', 'Y'}, new char[] {'S', 'U', 'N', 'D', 'A', 'Y'});
		testgetEditDistance(new char[] {'S', 'U', 'N', 'D', 'A', 'Y'}, new char[] {'S', 'U', 'N', 'D', 'A', 'Y'});

		testlongestPalindromeSubseq(new char[] {'A', 'G', 'C', 'T', 'C', 'B', 'M', 'A', 'A', 'C', 'T', 'G', 'G', 'A', 'M'});
		testlongestPalindromeSubseq(new char[] {'A', 'G', 'C', 'T', 'C', 'B', 'M', 'A'});
		testlongestPalindromeSubseq(new char[] {'A', 'X', 'B', 'M', 'B', 'A'});
		testlongestPalindromeSubseq(new char[] {'A', 'A', 'B', 'B', 'B', 'A'});

		testlongestPalindromeSubstring(new char[] {'A', 'G', 'C', 'T', 'C', 'B', 'M', 'A', 'A', 'C', 'T', 'G', 'G', 'A', 'M'});
		testlongestPalindromeSubstring(new char[] {'A', 'G', 'C', 'T', 'C', 'B', 'M', 'A'});
		testlongestPalindromeSubstring(new char[] {'A', 'X', 'B', 'M', 'B', 'A'});
		testlongestPalindromeSubstring(new char[] {'A', 'A', 'B', 'B', 'B', 'A'});
		testlongestPalindromeSubstring(new char[] {'A', 'G', 'B', 'D', 'B', 'A'});
		testlongestPalindromeSubstring(new char[] {'A', 'B', 'A', 'A', 'B', 'C'});
		testlongestPalindromeSubstring(new char[] {'B', 'A', 'N', 'A', 'N', 'A', 'S'});
		
		testgetPatternSubseqCount(new int[] {1, 2, 3}, new int[] {3, 1, 4, 1, 5, 9, 2, 6, 1, 5, 3});
		testgetPatternSubseqCount(new int[] {65, 66}, new int[] {65, 66, 65, 68, 67, 66});
		testgetPatternSubseqCount(new int[] {1, 2, 3}, new int[] {3, 1, 4, 1, 5, 9, 2, 6, 5, 3});
		testgetPatternSubseqCount(new int[] {1, 2, 3}, new int[] {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5});
		testgetPatternSubseqCount(new int[] {1, 5}, new int[] {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5});
		testgetPatternSubseqCount(new int[] {3, 5}, new int[] {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5});
		testgetPatternSubseqCount(new int[] {8, 5}, new int[] {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5});
		testgetPatternSubseqCount(new int[] {5, 8}, new int[] {5});
		testgetPatternSubseqCount(new int[] {5, 8}, new int[] {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5});
		testgetPatternSubseqCount(new int[] {5, 5}, new int[] {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5});
		
		getMatrixSubSquareAllOnes(new int[][] {{0,1,1,0,1}, {1,1,0,1,0}, {0,1,1,1,0}, {1,1,1,1,0}, {1,1,1,1,1}, {0,0,0,0,0}});
		getMatrixSubSquareAllOnes(new int[][] {{0,1,0,1,0,1}, {1,0,1,0,1,0}, {0,1,1,1,1,0}, {0,0,1,1,1,0}, {1,1,1,1,1,1}});
		
		testminJumpsForLastElement(new int[] {2, 3, 1, 1, 4});
		testminJumpsForLastElement(new int[] {1, 1, 2, 10, 1, 1, 1, 1, 1, 1, 4});
		testminJumpsForLastElement(new int[] {2, 32, 1, 1, 4});
		testminJumpsForLastElement(new int[] {1, 3, 5, 8, 9, 2, 6, 7, 6, 8, 9});
		testminJumpsForLastElement(new int[] {1, 3, 6, 3, 2, 3, 6, 8, 9, 5});
		testminJumpsForLastElement(new int[] {3, 2, 1, 0, 4});
		testminJumpsForLastElement(new int[] {0, 3, 2, 1, 0, 4});
		testminJumpsForLastElement(new int[] {0});
		*/

		/*int j[] = new int[] { // 860 items 
				0, 10, 0, 0, 25, 0, 18, 0, 26, 7, 0, 0, 0, 19, 16, 0, 0, 0, 18, 7, 15, 2, 0, 23, 0, 1, 0, 19, 0, 0,
				0, 0, 0, 6, 0, 0, 0, 0, 0, 20, 25, 0, 26, 0, 0, 0, 0, 25, 0, 0, 15, 16, 29, 0, 0, 24, 0, 0, 17, 0,
				0, 22, 30, 6, 0, 0, 0, 21, 24, 0, 12, 0, 0, 0, 0, 0, 1, 0, 0, 0, 18, 16, 0, 0, 20, 0, 0, 0, 0, 14,
				7, 0, 0, 8, 0, 19, 0, 0, 22, 20, 18, 0, 26, 0, 0, 0, 0, 0, 28, 0, 0, 8, 0, 9, 28, 30, 0, 10, 0, 0,
				0, 0, 23, 0, 0, 0, 0, 0, 4, 0, 0, 13, 29, 0, 9, 0, 3, 6, 22, 0, 0, 0, 29, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				26, 0, 0, 17, 0, 0, 0, 0, 29, 0, 0, 27, 0, 0, 0, 0, 29, 22, 0, 8, 0, 0, 2, 0, 13, 8, 0, 2, 0, 0, 0, 0,
				1, 13, 3, 0, 0, 18, 0, 5, 23, 0, 0, 3, 24, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 2, 2, 0, 0, 0, 14,
				24, 0, 17, 0, 0, 0, 0, 0, 0, 17, 2, 0, 10, 6, 5, 0, 0, 20, 21, 26, 16, 0, 0, 0, 21, 9, 0, 0, 0, 0, 0,
				11, 0, 25, 0, 0, 0, 9, 0, 6, 20, 0, 0, 0, 0, 0, 0, 0, 26, 25, 0, 14, 0, 0, 0, 7, 0, 0, 1, 0, 0, 5, 0,
				0, 0, 1, 0, 29, 0, 0, 0, 9, 9, 0, 0, 0, 0, 0, 24, 0, 0, 0, 0, 25, 16, 0, 0, 0, 0, 16, 23, 0, 0, 11, 0,
				26, 0, 0, 0, 0, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 27, 0, 0, 0, 17, 0, 0, 0, 0, 0, 22, 0, 0, 23, 0, 0, 0,
				0, 0, 0, 13, 0, 8, 0, 0, 5, 0, 17, 0, 14, 0, 0, 14, 0, 0, 0, 0, 0, 0, 0, 15, 25, 26, 17, 0, 11, 6, 0,
				0, 9, 3, 0, 0, 0, 0, 7, 0, 26, 0, 0, 0, 28, 0, 0, 0, 0, 0, 28, 0, 0, 14, 0, 0, 0, 0, 18, 25, 0, 0, 0,
				0, 0, 0, 6, 14, 12, 27, 12, 21, 11, 15, 0, 6, 9, 25, 0, 0, 0, 0, 18, 0, 3, 0, 5, 0, 0, 8, 9, 17, 6, 28,
				26, 0, 12, 0, 0, 0, 0, 22, 0, 0, 0, 0, 9, 0, 0, 0, 0, 14, 16, 8, 19, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 30, 0, 9, 0, 0, 23, 0, 0, 0, 0, 9, 0, 0, 13, 7, 0, 22, 0, 0, 0, 14, 26, 0, 27, 0, 0, 4, 0, 0,
				0, 0, 0, 17, 0, 0, 9, 0, 2, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 27, 10, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0,
				8, 0, 0, 0, 4, 0, 0, 0, 0, 21, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 19, 0, 0, 0, 21, 0, 14, 0, 23, 0, 18, 0, 0,
				0, 0, 0, 0, 0, 10, 0, 7, 0, 0, 0, 0, 0, 0, 0, 10, 16, 0, 16, 0, 0, 30, 0, 0, 4, 0, 0, 0, 0, 0, 0, 3, 0, 25,
				0, 0, 0, 0, 20, 0, 0, 0, 5, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 18, 27, 18, 7, 0, 4, 0, 30, 18, 26, 17,
				0, 0, 0, 8, 16, 0, 0, 0, 0, 0, 0, 27, 0, 0, 0, 0, 0, 9, 0, 0, 28, 0, 17, 0, 2, 0, 27, 0, 21, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 17, 0, 6, 0, 19, 23, 0, 0, 0, 20, 0, 0, 16, 0, 0, 0, 0, 0, 16, 0, 0, 0, 4, 0, 0, 0, 0, 24, 8,
				0, 0, 25, 12, 0, 3, 0, 5, 21, 0, 0, 3, 0, 18, 25, 10, 30, 24, 0, 14, 0, 0, 18, 0, 0, 0, 0, 0, 0, 18, 0, 15,
				0, 27, 0, 0, 22, 0, 0, 0, 0, 14, 0, 17, 0, 18, 29, 0, 0, 23, 0, 2, 0, 0, 0, 0, 23, 0, 25, 0, 0, 0, 2, 0, 2,
				0, 0, 5, 28, 0, 0, 18, 0, 0, 0, 0, 0, 0, 17, 19, 0, 0, 0, 26, 0, 0, 0, 0, 3, 0, 0, 29, 11, 0, 0, 4, 0, 27,
				11, 0, 3, 0, 0, 18, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 20, 0, 18, 0, 0, 0, 0, 16, 0, 0, 0, 0, 0, 0, 0, 0,
				13, 0, 0, 0, 0, 0, 30, 0, 0, 1, 0, 0, 0, 0, 27, 0, 0, 0, 1, 0, 10, 0, 0
			};
		testminJumpsForLastElement(j);

		int i = 0, x = Integer.MAX_VALUE;
		for(i = 0; x > 0; i++) {
			x >>= 1;
		}
		System.out.println("int max " + Integer.MAX_VALUE + " no of bits " + i);
		*/
		
		/*
		int[] a = new int[] {101, 202, 303, 404, 505, 108, 606, 77, 125, 301, 151, 258};
		System.out.println("containsNearby k=5 v=7 " + Arrays.toString(a));
		System.out.println(containsNearbyAlmostDuplicate(a, 5, 7));
		a = new int[] {9, 13, -2, 3, 14, -1, 5, 0, 7, 6};
		System.out.println("containsNearby k=2 v=3 " + Arrays.toString(a));
		System.out.println(containsNearbyAlmostDuplicate(a, 2, 3));
		
		boolean canReach = false; 
		int iCan = 5;
		System.out.println("canReach: " + canReach + " iCan " + iCan);
		while (canReach = (iCan >= 0)) {
			System.out.println("canReach: " + canReach + " iCan " + iCan);
			iCan--;
		}
		System.out.println("canReach: " + canReach + " iCan " + iCan);

		Integer[] ints = new Integer[] {1000, 1000, 1000, 1000, 1001, 1002, 1003, 1003, 1004, 1010};
		ArrayList<Integer> ax = new ArrayList<Integer>(Arrays.asList(ints));
		System.out.println(ax.toString() + " " + removeDupes(ax) + " " + ax.toString());
		ints = new Integer[] {1000, 1000, 1000, 1000, 1000, 1000};
		ax = new ArrayList<Integer>(Arrays.asList(ints));
		System.out.println(ax.toString() + " " + removeDupes(ax) + " " + ax.toString());
		ints = new Integer[] {10, 10, 10, 10, 11, 12, 13, 13, 14, 20};
		ax = new ArrayList<Integer>(Arrays.asList(ints));
		System.out.println(ax.toString() + " " + removeDupes(ax) + " " + ax.toString());
		ints = new Integer[] {1, 1, 1, 1, 2, 3, 3, 4, 10};
		ax = new ArrayList<Integer>(Arrays.asList(ints));
		System.out.println(ax.toString() + " " + removeDupes(ax) + " " + ax.toString());
		
		int[] vals = new int[] {19, 1, 2, 3, 4, 5, -1, 9, -1, -1, 6, 7, -1, -1, -1, -1, -1, 8, -1, -1};
		TreeNode root = BinaryTreeBase.createTree(vals);
		System.out.println("sumNumbers " + sumNumbers(root));

		vals = new int[] {763, 3, 7, 9, 9, 0, -1, -1, 2, 1, 4, 3, 2, 5, 2, 2, 4, 8, 1, 1, 4, 9, 0, -1, 8, 3, 5, 2, 5,
							-1, 1, 6, 2, 8, 1, 0, 7, 3, -1, 7, -1, 6, 6, 1, 7, 1, 5, 9, 4, 7, -1, 7, -1, -1, -1, 6, 2,
							8, 7, 8, 1, 5, 9, 0, 4, 6, -1, -1, 5, 6, -1, 2, 1, 8, 2, 5, 5, -1, 4, -1, 1, 9, 1, 4, 3, 5,
							7, 4, -1, -1, 0, 6, 7, 5, -1, 2, 1, 7, 1, 9, 0, 2, 5, 4, -1, -1, -1, -1, -1, 8, 2, 2, -1,
							-1, -1, -1, -1, 2, -1, 3, 9, 4, 8, 8, 6, 4, 7, 2, 5, 7, 1, -1, 9, 5, 3, 8, 0, 4, -1, -1, 5,
							5, 7, 2, -1, -1, -1, 8, 0, 4, 4, 5, 5, 7, -1, -1, 5, 6, 3, -1, 9, 1, 9, -1, 8, -1, -1, 9,
							-1, -1, 8, -1, -1, -1, -1, -1, -1, -1, -1, -1, 6, 7, 3, -1, 1, 8, -1, -1, 1, 8, -1, -1, -1,
							8, 0, 0, 5, 6, -1, -1, 0, -1, 9, -1, 5, -1, 6, 6, -1, 6, 2, 6, 5, -1, -1, 7, 3, 1, 6, -1, 7,
							6, -1, -1, 6, -1, 3, 9, -1, -1, -1, 0, -1, 2, -1, 0, -1, 7, 3, 5, -1, 8, 2, 0, 6, 8, 7, 3,
							9, 0, 1, 0, -1, -1, -1, 0, 8, 7, 2, 9, -1, 6, 6, 6, -1, 2, 3, 2, -1, -1, 1, 1, 4, 8, -1, 2,
							0, -1, -1, -1, -1, -1, -1, 1, 3, 6, -1, -1, -1, -1, 5, 4, 1, 7, 7, -1, -1, -1, -1, -1, -1,
							0, 8, 0, -1, 5, 5, -1, 7, 3, -1, -1, 1, -1, -1, -1, 7, 9, 4, -1, 4, -1, -1, -1, -1, -1, -1,
							-1, -1, -1, 0, 0, 5, 5, -1, -1, -1, 2, 6, 8, 1, -1, 0, -1, 6, -1, 0, -1, -1, -1, -1, -1, -1,
							6, 8, 2, -1, 4, 2, -1, 1, -1, -1, -1, 2, 1, 0, 2, 7, 8, -1, 1, -1, -1, 3, 4, -1, -1, -1, -1,
							-1, 5, -1, -1, 8, 2, -1, -1, -1, -1, 2, 8, -1, 3, -1, 8, 6, 3, -1, -1, -1, 8, 6, 4, -1, -1,
							-1, -1, 5, -1, -1, -1, -1, -1, -1, 9, 4, -1, -1, -1, -1, -1, -1, -1, 2, 2, 7, 3, 9, -1, -1,
							9, -1, -1, -1, -1, 6, -1, 3, 8, -1, -1, -1, -1, -1, -1, -1, 3, -1, -1, -1, -1, -1, -1, -1,
							-1, 4, -1, 2, -1, -1, -1, -1, 2, -1, -1, 1, 9, 1, -1, -1, 2, 5, 1, -1, -1, 4, 2, -1, -1, -1,
							7, 6, 3, 8, 2, 8, -1, -1, 0, -1, -1, 3, 1, -1, -1, 5, -1, -1, 9, -1, 2, -1, 0, -1, -1, -1, 8,
							-1, -1, 8, -1, -1, 0, -1, 0, -1, 7, -1, -1, 1, 4, -1, 9, 5, 3, -1, -1, -1, 2, 3, -1, -1, -1,
							6, 7, -1, 0, 6, -1, -1, -1, -1, -1, 5, -1, -1, -1, 5, -1, -1, -1, -1, -1, 4, 8, 3, -1, -1, 9,
							5, -1, -1, -1, 9, 0, -1, -1, -1, -1, -1, -1, -1, -1, 4, -1, 7, -1, -1, -1, -1, -1, -1, -1, 4,
							0, -1, 8, 1, -1, 5, -1, 0, -1, -1, -1, -1, -1, 1, 1, 0, -1, 8, 6, -1, -1, -1, -1, -1, -1, 3,
							5, 9, 4, 1, 9, -1, -1, -1, -1, -1, 6, -1, -1, -1, -1, -1, -1, 5, -1, -1, 9, -1, -1, 0, -1, -1,
							-1, -1, 0, 1, -1, 3, -1, 8, -1, 1, -1, -1, -1, -1, 2, 5, 6, 2, 6, -1, 6, 6, 4, -1, 9, -1, -1,
							-1, -1, 5, 8, -1, -1, -1, -1, -1, 1, -1, -1, -1, -1, 5, -1, 7, -1, -1, -1, -1, 9, 4, 2, 1, 8,
							-1, -1, -1, 3, 4, -1, -1, -1, -1, -1, -1, -1, 5, -1, -1, -1, -1, -1, 4, -1, 9, -1, -1, -1, 3,
							-1, -1, 3, -1, -1, 7, 4, 1, -1, -1, -1, -1, -1, -1, -1, -1, 7, 8, -1, 9, 0, -1, -1, -1, 2, 6,
							-1, 8, -1, -1, -1, -1, -1, 2, -1, 4, 2, -1, -1, 6, 8, -1, -1, -1, -1, -1, 4, -1, -1
			};
		root = BinaryTreeBase.createTree(vals);
		System.out.println("sumNumbers " + sumNumbers(root));
		*/

		/*
		vals = new int[] {37, 78, 72, 80, 66, 74, 79, 81, 65, 71, 73, 75, -1, -1, -1, 83, -1, -1, 
								68, -1, -1, -1, -1, 76, 82, -1, 67, 69, -1, 77, -1, -1, -1, -1, -1, 70, -1, -1};
		BinaryTreeBase.createTree(vals);

		String A = "eZCHXr0CgsB4O3TCDlitYI7kH38rEElI";
		String B = "UhSQsB6CWAHE6zzphz5BIAHqSWIY24D";
		String C = "eUZCHhXr0SQsCgsB4O3B6TCWCDlAitYIHE7k6H3z8zrphz5EEBlIIAHqSWIY24D";
		System.out.println(isInterleave(A, B, C) + " ");
		*/
		
	}

	public static int sumNumbers(TreeNode a) {
	    int sum = sumNumbers(a, 0);
		return sum%1003;
	}
	private static int sumNumbers(TreeNode a, int pre) {
	    if(a.left == null && a.right == null) {
	        int finVal = (pre * 10 + a.val)%1003;
			//System.out.println("final long " + finVal);
	        return finVal;
	    }
	    
	    int lval = 0, rval = 0;
	    pre *= 10;
	    pre += a.val;
		pre %= 1003;
	    if(a.left != null) {
	        lval = sumNumbers(a.left, pre);
	    }
	    if(a.right != null) {
	        rval = sumNumbers(a.right, pre);
	    }
	    return (lval + rval)%1003;
	}
	
	public static int removeDupes(ArrayList<Integer> a) {
	    if(a == null) return 0;
	    
	    int i = 1, j = 1, count = 1;
	    while(j < a.size()) {
			Integer val = a.get(j);
	        if(!a.get(j).equals(a.get(j-1))) {
	            count = 1;
				j++;
	        }
	        else {
                count++;
	            if(count > 2) {
					a.remove(j);
	            }
				else {
					j++;
				}
	        }
	    }
	    return a.size();
	}
/*
	private static boolean containsNearbyAlmostDuplicate(int[] nums, int k, int v)
	{
		if(k < 1 || v < 0) return false;
		
		HashMap<Integer, Integer> buckets = new HashMap<>();
		int len = nums.length, bucketNum = 0, offset = 0;
		for(int i = 0; i < len; i++) {
			int num = nums[i];
			if(v > 0) {
				bucketNum =  (int)Math.floor(num/ v);
				offset = 1;
			}
			else {
				bucketNum =  num;
				offset = 0;
			}

			// Check buckets to see if there is a possible solution
			for (int j = bucketNum - offset; j <= bucketNum + offset; j++) {
			  if (buckets.containsKey(j) && Math.abs(buckets.get(j) - num) <= v)
				  return true;
			}
			// Push num into bucket 
			buckets.put(bucketNum, num);
			
			// Remove buckets that are too far away
			if (v != 0 && i >= k) {
				buckets.remove((int)Math.floor(nums[i - k]/ v));
			}
			else if(i >= k){
				buckets.remove(nums[i - k]);
			} 
			System.out.println("Buckets " + buckets.toString());
		}
		return false;
	}
	*/
	
	
}
