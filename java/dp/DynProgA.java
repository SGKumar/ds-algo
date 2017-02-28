package dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import tree.TreeNode;

public class DynProgA
{
	int[] _stack;
	int first;
	int min;

	public DynProgA()
	{
		_stack = new int[50];
		int first = 0;
	}

	public static int maxSumContSubseq1(int[] vals)
	{
		int maxSum = 0, leftSum = 0;
		for(int i = 0; i < vals.length; i++) {
			if(vals[i] > maxSum) {
				maxSum = vals[i];
			}
			leftSum += vals[i];
			if(leftSum > maxSum) {
				maxSum = leftSum;
			}
			int rightSum = 0;
			for(int j = i+1; j < vals.length; j++) {
				rightSum += vals[j];
				if(rightSum > maxSum) {
					maxSum = rightSum;
				}
			}
			if(rightSum + leftSum > maxSum) {
				maxSum = rightSum + leftSum;
			}
		}
		return maxSum;
	}

	public static int maxSumContSubseq2(int[] vals)
	{
		int maxSum = 0;
		for(int i = 0; i < vals.length; i++) {
			int currSum = 0;
			for(int j = i; j < vals.length; j++) {
				currSum += vals[j];
				if(currSum > maxSum) {
					maxSum = currSum;
				}
			}
		}
		return maxSum;
	}
	
	public static int maxSumContSubseq3(int[] vals)
	{
		int[] maxSum = new int[vals.length];
		maxSum[0] = (vals[0] >= 0)? vals[0]:0;
		for(int i = 1; i < vals.length; i++) {
			if(maxSum[i-1] + vals[i] <= 0) {
				maxSum[i] = 0;
			}
			else {
				maxSum[i] = maxSum[i-1] + vals[i];
			}
		}

		int maxIndex = 0;
		for(int i = 1; i < maxSum.length; i++) {
			maxIndex = (maxSum[maxIndex] > maxSum[i])?maxIndex:i;
		}
		return maxSum[maxIndex];
	}
		
	public static int maxSumContigSubseqFromBegin(int[] vals)
	{
		int currSum = 0, maxSum = 0;
		int begin = 0, end = 0, currBegin = 0;
		for(int i = 0; i < vals.length; i++) {
			currSum += vals[i];
			if(currSum < 0) {
				currSum = 0;
				currBegin = i+1;
			}
			else if(currSum >= maxSum) {
				maxSum = currSum;
				begin = currBegin;
				end = i;
			}
		}
		//System.out.printf("Begin %d end %d\n", begin, end);
		return maxSum;
	}

	public static int maxSumContigSubseqFromEnd(int[] vals)
	{
		int n = vals.length;
		int currSum = 0, maxSum = 0;
		int begin = n-1, end = n-1, currEnd = n-1;
		for(int i = n-1; i >= 0; i--) {
			currSum += vals[i];
			if(currSum < 0) {
				currSum = 0;
				currEnd = i-1;
			}
			else if(currSum >= maxSum) {
				maxSum = currSum;
				begin = i;
				end = currEnd;
			}
		}
		//System.out.printf("Begin %d end %d\n", begin, end);
		return maxSum;
	}

	public static int changeMinCoins(int[] coins, int val)
	{
		int[] table = new int[val+1];
		// where no change combo possible table[i] will be Integer.INT_MAX;
		table[0] = 0;
		for(int i = 1; i <= val; i++) {
			table[i] = Integer.MAX_VALUE;
			for(int j = 0; j < coins.length; j++) {
				if(i >= coins[j]) {
					int subres = table[i-coins[j]];
					if(subres != Integer.MAX_VALUE && subres + 1 < table[i]) {
						table[i] = 1 + subres;
					}
				}
			}
		}
		return table[val];
	}
	
	// weights and values are at 0-based index
	public static int thiefMostValueKnapSack01(int[] values, int[] weights, int cap)
	{
		int[][] table = new int[values.length+1][cap+1];

		for(int i = 0; i <= values.length; i++) {
			table[i][0] = 0;
		}
		for(int w = 0; w <= cap; w++) {
			table[0][w] = 0;
		}
		
		for(int i = 1; i <= values.length; i++) {
			for(int w = 1; w <= cap; w++) {
				table[i][w] = table[i-1][w];
				if(w >= weights[i-1]) {
					int subres = table[i-1][w-weights[i-1]];
					if(subres + values[i-1] > table[i][w]) {
						table[i][w] = values[i-1] + subres;
					}
				}
			}
		}
		//System.out.println(Arrays.deepToString(table));
		return table[values.length][cap];
	}
	
	public static String findLongestCommonSubseq(char[] S, char[] T)
	{
		int[][] table = new int[S.length+1][T.length+1];
		int rows = S.length, cols = T.length;

		for(int row = 0; row <= rows; row++) {
			table[row][cols] = 0;
		}
		for(int col = 0; col <= cols; col++) {
			table[rows][col] = 0;
		}
		
		for(int row = rows-1; row >= 0; row--) {
			for(int col = cols-1; col >= 0; col--) {
				if(S[row] == T[col]) {
					table[row][col] = 1 + table[row+1][col+1];
				}
				else {
					table[row][col] = Math.max(table[row+1][col], table[row][col+1]);
				}
			}
		}
		
		// Find the string
		int len = table[0][0];
		char lcs[] = new char[len];
		int row = 0, col = 0, i = 0;
		while(row < rows && col < cols) {
			if(S[row] == T[col]) {
				lcs[i] = S[row];
				i++; row++; col++;
			}
			else {
				if(table[row+1][col] > table[row][col+1]) {
					row++;
				}
				else {
					col++;
				}
			}
		}
		return new String(lcs);
		//return table[0][0];
	}
	
	public static int findLongestIncSubseq(int[] S)
	{
		int[] table = new int[S.length];
		int m = S.length;

		for(int i = 0; i < table.length; i++) {
			table[i] = 1;
			for(int j = i-1; j >= 0; j--) {
				if(S[i] > S[j]) {
					int subres = table[j];
					if(1+ subres > table[i]) {
						table[i] = 1 + subres;
					}
				}
			}
		}
		
		int max = table[0];
		for(int i = 1; i < table.length; i++) {
			if(max < table[i]) {
				max = table[i];
			}
		}
		//System.out.println(Arrays.deepToString(table));
		return max;
	}

	// Uses just 1D array logic is to flip true L->R progressively
	public static boolean findSubsetSum2(int[] vals, int T)
	{
		boolean[] table = new boolean[T+1];
		// if M[i, k] is true, M[i, k+A[j]] is true for all A[j] in array
		// table is basically a bitmap of all sums <= T possible with contents of vals[]

		table[0] = true;
		for(int i = 0; i < vals.length; i++) {
			for(int j = T-vals[i]; j >= 0; j--) {
				if(table[j] && (vals[i] + j <= T)) {
					table[vals[i]+j] = true;
				}
			}
			//System.out.println(i + " " + vals[i] + " " + Arrays.toString(table));
		}
		
		//System.out.println(Arrays.toString(table));
		return table[T];
	}

	public static boolean findSubsetSum(int[] vals, int T)
	{
		boolean[][] table = new boolean[vals.length+1][T+1];
		// M[i, T] = M[i-1, T] OR M[i-1, T-A[i]]

		// set first row to false
		for(int col = 0; col <= T; col++) {
			table[0][col] = false;
		}
		
		for(int i = 1; i <= vals.length; i++) {
			for(int j = 1; j <= T; j++) {
				if(j == vals[i-1]) {
					table[i][j] = true;
				}
				else {
					boolean subres = table[i-1][j];
					if(j > vals[i-1]) {
						subres = subres || table[i-1][j-vals[i-1]];
					}
					table[i][j] = subres;
				}
			}
		}
		
		//System.out.println(Arrays.deepToString(table));
		return table[vals.length][T];
	}

	private static void printMoves(int P[][], int A[], int N)
	{
		int sum1 = 0, sum2 = 0;
		int m = 0, n = N-1;
		boolean myTurn = true;
		while (m <= n) {
			int P1 = P[m+1][n]; // If take A[m], opponent can get...
			int P2 = P[m][n-1]; // If take A[n]
			System.out.print((myTurn ? "I" : "You") + " take coin no. ");
			if (P1 <= P2) {
				System.out.print(m+1 + " (" + A[m] + ")");
				m++;
			} else {
				System.out.print(n+1 + " (" + A[n] + ")");
				n--;
			}
			System.out.print((myTurn ? ", " : ".\n"));
			myTurn = !myTurn;
		}
		System.out.println("The total amount of money (maximum) I get is " + P[0][N-1]);
	}
 
	private static int maxMoney(int A[])
	{
		int N = A.length;
		int[][] P = new int[N][N];
		int a, b, c;
		for (int i = 0; i < N; i++) {
			for (int m = 0, n = i; n < N; m++, n++) {
				assert(m < N); assert(n < N);
				a = ((m+2 <= N-1)             ? P[m+2][n] : 0);
				b = ((m+1 <= N-1 && n-1 >= 0) ? P[m+1][n-1] : 0);
				c = ((n-2 >= 0)               ? P[m][n-2] : 0);
				P[m][n] = Math.max(A[m] + Math.min(a,b), A[n] + Math.min(b,c));
				//System.out.printf("m %d n %d a %d b %d c %d P[m][n] %d\n", m, n, a, b, c, P[m][n]);
			}
		}
		printMoves(P, A, N);
		return P[0][N-1];
	}

	public static int countBoolParantheses(boolean[] syms, char[] ops)
	{
		// length of ops = length of syms - 1
		int[][] T = new int[syms.length][syms.length];
		int[][] F = new int[syms.length][syms.length];

		// set diagonals to values
		for(int i = 0; i < syms.length; i++) {
			if(syms[i]) {
				T[i][i] = 1;
			}
			else {
				F[i][i] = 1;
			}
		}
		
		for(int gap = 1; gap < syms.length; gap++) {
			for(int i = 0, j = gap; j < syms.length; i++, j++) {
				//T[i][j] = F[i][j] = 0;
				for(int g = 0; g < gap; g++) {
					int k = i + g;
					switch(ops[k]) {
						case '&':
							T[i][j] += T[i][k]*T[k+1][j];
							F[i][j] += F[i][k]*T[k+1][j] + T[i][k]*F[k+1][j] + F[i][k]*F[k+1][j];
							break;
						
						case '|':
							T[i][j] += T[i][k]*T[k+1][j] + T[i][k]*F[k+1][j] + F[i][k]*T[k+1][j];
							F[i][j] += F[i][k]*F[k+1][j];
							break;

						case '^':
							T[i][j] += T[i][k]*F[k+1][j] + F[i][k]*T[k+1][j];
							F[i][j] += T[i][k]*T[k+1][j] + F[i][k]*F[k+1][j];
							break;
					
					}
				}
			}
		}
		
		//System.out.println(Arrays.deepToString(table));
		return T[0][syms.length-1];
	}

	private static int min(int val1, int val2, int val3)
	{
		return Math.min(val1, Math.min(val2, val3));
	}
	public static int getEditDistance(char[] A, char[] B)
	{
		// transform A -> B
		int[][] dist = new int[A.length+1][B.length+1];

		// set first row and col
		for(int row = 0; row <= A.length; row++) {
			dist[row][0] = row;
		}
		for(int col = 0; col <= B.length; col++) {
			dist[0][col] = col;
		}
		
		for(int i = 1; i <= A.length; i++) {
			for(int j = 1; j <= B.length; j++) {
				if(A[i-1] == B[j-1]) {
					dist[i][j] = dist[i-1][j-1];
				}
				else {
					dist[i][j] = 1+ min(dist[i-1][j], dist[i-1][j-1], dist[i][j-1]);
				}
			}
		}
		
		return dist[A.length][B.length];
	}

	public static int longestPalindromeSubseq(char[] A)
	{
		int[][] table = new int[A.length][A.length];
		for(int i = 0; i < A.length; i++) {
			table[i][i] = 1;
		}

		for(int gap = 1; gap < A.length; gap++) {
			for(int i = 0, j = gap; j < A.length; i++, j++) {
				if(A[i] != A[j]) {
					table[i][j] = Math.max(table[i+1][j], table[i][j-1]);
				}
				else {
					if(gap >= 2) {
						table[i][j] = table[i+1][j-1] + 2;
					}
					else {
						table[i][j] = 2;
					}
				}
			}
		}
		return table[0][A.length-1];
	}

	public static int longestPalindromeSubstring(char[] A)
	{
		int[][] table = new int[A.length][A.length];
		for(int i = 0; i < A.length; i++) {
			table[i][i] = 1;
		}
		for(int i = 1; i < A.length; i++) {
			if(A[i-1] == A[i]) {
				table[i-1][i] = 2;
			}
		}

		int iMax = 0, jMax = 0;
		for(int gap = 2; gap < A.length; gap++) {
			for(int i = 0, j = gap; j < A.length; i++, j++) {

				/*
				if(A[i] != A[j] || table[i+1][j-1] < j-i-1) {
					table[i][j] = Math.max(table[i+1][j], table[i][j-1]);
				}
				else if(table[i+1][j-1] == j-i-1) {
					table[i][j] = 2 + table[i+1][j-1];
				}
				*/
				if(A[i] != A[j]) {
					table[i][j] = 0;
				}
				else {
					table[i][j] = 2 + table[i+1][j-1];
					if(table[i][j] > table[iMax][jMax]) {
						iMax = i; jMax = j;
					}
				}
				
			}
		}
		return table[iMax][jMax];
		//System.out.println(Arrays.deepToString(table));
		//return table[0][A.length-1];
	}

	//http://stackoverflow.com/questions/6877249/find-the-number-of-occurrences-of-a-subsequence-in-a-string
	// No. of occurrences of a pattern as a subsequence in a string
	public static int getPatternSubseqCount(int[] pattern, int[] A)
	{
		int[][] table = new int[pattern.length][A.length];
		
		// Fill last column with match
		for(int row = 0; row < pattern.length; row++) {
			table[row][A.length-1] = ((pattern[pattern.length-1] == A[A.length-1])?1:0);
		}

		for(int j = A.length-2; j >= 0; j--) {
			for(int i = 0; i < pattern.length; i++) {

				table[i][j] = table[i][j+1];
				if(pattern[i] == A[j]) {
					if(i == pattern.length-1) {
						table[i][j] += 1;
					}
					else {
						table[i][j] += table[i+1][j+1];
					}
				}
			}
		}
		return table[0][0];
	}

	public static int getMatrixSubSquareAllOnes(int[][] matrix)
	{
		int rows = matrix.length, cols = matrix[0].length;
		int[][] table = new int[rows][cols];
		
		// Fill first row and first column with same
		for(int col = 0; col < cols; col++) {
			table[0][col] = matrix[0][col];
		}
		for(int row = 1; row < rows; row++) {
			table[row][0] = matrix[row][0];
		}

		int row = 0, col = 0, max = 0;
		for(int i = 1; i < rows; i++) {
			for(int j = 1; j < cols; j++) {
				if(matrix[i][j] == 1) {
					table[i][j] = min(table[i][j-1], table[i-1][j-1], table[i-1][j]) + 1;
				}
				if(max < table[i][j]) {
					max = table[i][j];
					row = i;
					col = j;
				}
			}
		}
		System.out.printf("All 1 matrix of square %d @bottom %d right %d\n", max, row, col);
		return max;
	}

	// Solution from InterviewBit - BEGIN
	// Find minimum no. of jumps to reach final step
	int minJumps_cpp(int A[], int n) {
		if(n <= 1){
			return 0;
		}
		int maxReachPos = A[0];
		int curMaxReachPos = A[0];
		int steps = 1;
		for(int i = 1; i <= maxReachPos; i++){
			if(i == n - 1){
				return steps;
			}
			curMaxReachPos = Math.max(curMaxReachPos, i + A[i]);
			if(i == maxReachPos){
				if (curMaxReachPos <= i) return -1;
				maxReachPos = curMaxReachPos;
				steps++;
			}
		}
		return -1;
	}
	public int minJumps_java(ArrayList<Integer> A) {
	    if (A == null || (A.size() > 1 && A.get(0) == 0))
	        return -1;
	    if (A.size() == 1)
	        return 0;
	        
	    int count = A.get(0);
	    int n = A.size(), max = 0, steps = 0;
	    for (int i = 1; i < n; i++) {
	        count--;
	        max--;
	        max = Math.max(max, A.get(i));
	        
	        if (i == n - 1) {
	            return steps + 1;
	        }
	        if (count == 0) {
	            if (max < 0)
	                return -1;
	            
	            count = max;
	            max = 0;
	            steps++;
	        }
	    }
	    return steps;
	}
	
	// Can I jump to final step given the jumps possible....
	boolean canJump_cpp(int A[], int n)
	{
		int minIndexPossible = n - 1;
		boolean isPossibleFromThisIndex = true;
		for (int i = n - 2; i >= 0; i--) {
			isPossibleFromThisIndex = (i + A[i] >= minIndexPossible);
			if(isPossibleFromThisIndex) {
				minIndexPossible = i;
			}
			//if (i == 0) return isPossibleFromThisIndex;
		}
		return isPossibleFromThisIndex; // or (minIndexPossible == 0)
	}

	public static int canJump_java(ArrayList<Integer> A) {
	    int n = A.size();
	    int[] dp = new int[n];
	    Arrays.fill(dp, -1);
	    dp[0] = A.get(0);
	    
	    for (int i = 1; i < n; i++) {
	        if (dp[i - 1] >= i)
	            dp[i] = Math.max(dp[i - 1], i + A.get(i));
	    }
	    
	    for (int i = 0; i < n; i++) {
	        if (dp[i] >= n - 1)
	            return 1;
	    }
	    
	    return 0;
	    
	}
	// Solution from InterviewBit - END
	
	public static int[] minJump(int arr[]/*, int result[]*/) {  
		int[] jump = new int[arr.length];
		int[] result = new int[arr.length]; // mine
		jump[0] = 0;
		for(int i=1; i < arr.length ; i++){
			jump[i] = Integer.MAX_VALUE-10;
		}

		for(int i=1; i < arr.length; i++){
			for(int j=0; j < i; j++){
				if(arr[j] + j >= i){
					if(jump[i] > jump[j] + 1){
						result[i] = j;
						jump[i] = jump[j] + 1;
					}
				}
			}
		}

		//return jump[jump.length-1];
		result[0] = jump[jump.length-1];
		return result;
	}		
	public static int minJumpsForLastElement(int[] vals)
	{
		int max, count = -1, jump = 0;
		StringBuilder str = new StringBuilder(100).append("Pos: ");
		for(int i = 0; i < vals.length; i++) {
			// stop if I can already reach end from here...
			if(i + vals[i] >= vals.length-1) {
				jump = vals.length - 1 -i;
			}
			else {
				max = 0;
				for(int j = 1; j <= vals[i]; j++) {
					// Basically find the farthest cell that I can jump to
					if(max < j + vals[j]) {
						max = vals[j] + j;
						jump = j;
					}
				}
			}
			// vals[i] can't be 0 because there is nowhere to jump then
			count++;
			i += jump;
			str.append(i).append(" ");
		}
		System.out.println(str);
		return count;
	}
	private static void testchangeMinCoins(int[] coins, int val) {
		System.out.println("Min coins from " + Arrays.toString(coins) + " for " + val + " is: " + changeMinCoins(coins, val));
	}

	private static void testthiefMostValueKnapSack01(int[] values, int[] weights, int weightCap) {
		System.out.println("Values " + Arrays.toString(values)+ " weights " + Arrays.toString(weights) + " weightCap@ " + weightCap + " is " + thiefMostValueKnapSack01(values, weights, weightCap));
	}

	private static void testfindLongestCommonSubseq(char[] S, char[] T) {
		//System.out.println("LCS of " + Arrays.toString(S)+ " & " + Arrays.toString(T) + " is " + findLongestCommonSubseq(S, T));
	}
	
	private static void testfindLongestIncSubseq(int[] S) {
		System.out.println("LIS of " + Arrays.toString(S)+ " is " + findLongestIncSubseq(S));
	}
	
	private static void testfindSubsetSum(int[] S, int T) {
		System.out.println("Sum for " + T + " in " + Arrays.toString(S)+ (findSubsetSum(S, T)?" YES ": " NO "));
		System.out.println("Sum2 for " + T + " in " + Arrays.toString(S)+ (findSubsetSum2(S, T)?" YES ": " NO "));
	}

	private static void testcountBoolParantheses(boolean[] syms, char[] ops) {
		System.out.println(countBoolParantheses(syms, ops) + " bra combos for " + Arrays.toString(syms) + " " + Arrays.toString(ops));
	}
	
	private static void testgetEditDistance(char[] A, char[] B) {
		System.out.println("Edit dist for " + Arrays.toString(A) + ", " + Arrays.toString(B) + " : " + getEditDistance(A, B));
	}
	
	private static void testlongestPalindromeSubseq(char[] A) {
		System.out.println("Longest palindrome subseq length for " + Arrays.toString(A) + " is: " + longestPalindromeSubseq(A));
	}
	
	private static void testlongestPalindromeSubstring(char[] A) {
		System.out.println("Longest palindrome substr length for " + Arrays.toString(A) + " is: " + longestPalindromeSubstring(A));
	}

	private static void testgetPatternSubseqCount(int[] pattern, int[] A) {
		System.out.println("Pattern " + Arrays.toString(pattern) + " occurrence in string " + Arrays.toString(A) + " " + getPatternSubseqCount(pattern, A));
	}

	private static void testminJumpsForLastElement(int[] vals) {
		//System.out.println("Vals " + Arrays.toString(vals) + " jump " + minJumpsForLastElement(vals) + " times");
		System.out.println("Vals " + Arrays.toString(vals) + " jump " + Arrays.toString(minJump(vals)));
	}
	
	private static void testMaxSumContigSubSequence(int[] vals) {
		System.out.println("Max sum contig subseq of " + Arrays.toString(vals) + " is: " + maxSumContigSubseqFromBegin(vals));
		System.out.println("Max sum contig subseq of " + Arrays.toString(vals) + " is: " + maxSumContigSubseqFromEnd(vals));
		System.out.println("Max sum contig subseq of " + Arrays.toString(vals) + " is: " + maxSumContSubseq1(vals));
		System.out.println("Max sum contig subseq of " + Arrays.toString(vals) + " is: " + maxSumContSubseq2(vals));
		System.out.println("Max sum contig subseq of " + Arrays.toString(vals) + " is: " + maxSumContSubseq3(vals));
	}
	
	public static void main(String args[])
    {
		/*
		testMaxSumContigSubSequence(new int[] {-18, -5, -1, -2, -1, -3, -20});
		testMaxSumContigSubSequence(new int[] {-2, -11, -4, 0, -13, -5, -2, -1});
		testMaxSumContigSubSequence(new int[] {-1, 3, -5, 4, 6, -1, 2, -7, 13, -3});
		testMaxSumContigSubSequence(new int[] {-2, 11, -4, 13, -5, 2});
		testMaxSumContigSubSequence(new int[] {-2, -3, 4, -1, -2, 1, 5, -3});
		testMaxSumContigSubSequence(new int[] {-2, 1, -3, 4, -1, 2, 1, -5, 4});
		testMaxSumContigSubSequence(new int[] {18, -5, 1, -1, 1, -2, 2});

		testfindLongestCommonSubseq(new char[] {'A', 'B', 'C', 'D', 'E', 'F'}, new char[] {'A', 'C', 'B', 'C', 'F'});
		testfindLongestCommonSubseq(new char[] {'A', 'C', 'B', 'C', 'F'}, new char[] {'A', 'B', 'C', 'D', 'E', 'F'});
		testfindLongestCommonSubseq(new char[] {'A', 'B', 'D', 'F', 'E'}, new char[] {'A', 'B', 'C', 'D', 'E'});
		testfindLongestCommonSubseq(new char[] {'A', 'B', 'C', 'D', 'E'}, new char[] {'A', 'B', 'D', 'F', 'E'});
		testfindLongestCommonSubseq(new char[] {'A', 'B', 'D', 'F', 'E'}, new char[] {'A', 'B', 'C', 'D', 'E'});
		testfindLongestCommonSubseq(new char[] {'A', 'B', 'C', 'D', 'E'}, new char[] {'A', 'E', 'B', 'D', 'F'});
		testfindLongestCommonSubseq(new char[] {'A', 'E', 'B', 'D', 'F'}, new char[] {'A', 'B', 'C', 'D', 'E'});
		testfindLongestCommonSubseq(new char[] {'A', 'C', 'B', 'A', 'E', 'D'}, new char[] {'A', 'B', 'C', 'A', 'D', 'F'});
		testfindLongestCommonSubseq(new char[] {'A', 'B', 'C', 'A', 'D', 'F'}, new char[] {'A', 'C', 'B', 'A', 'E', 'D'});
		testfindLongestCommonSubseq(new char[] {'A', 'B', 'C', 'D', 'S', 'E', 'F', 'G', 'D'}, new char[] {'A', 'C', 'F', 'E', 'F', 'X', 'V', 'G', 'A', 'B'});

		*/
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
		/*testchangeMinCoins(new int[] {1, 3, 9, 10}, 15);
		testchangeMinCoins(new int[] {1, 3, 9, 10}, 15);
		testchangeMinCoins(new int[] {1, 3, 9, 10}, 15);*/

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
		*/
		int i = 0, x = Integer.MAX_VALUE;
		for(i = 0; x > 0; i++) {
			x >>= 1;
		}
		System.out.println("int max " + Integer.MAX_VALUE + " no of bits " + i);
		
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

