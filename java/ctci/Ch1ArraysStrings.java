package ctci;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.BitSet;

import llsq.Queue;

public class Ch1ArraysStrings
{
	public StringBuilder sb;
	int[] pattern;
	char[][] charKey;

    public Ch1ArraysStrings()
	{
		charKey = new char[][] {{'0'}, {'1'}, {'A','B','C'}, {'D','E','F'}, {'G','H','I'}, {'J','K','L'},
		{'M','N','O'}, {'P','Q','R','S'}, {'T','U','V'}, {'W','X','Y','Z'}};
    }

	private static class Counter
	{
		private int count;
		public Counter()
		{
			count = 1;
		}
		public int count()
		{
			return count;
		}
		public void plus()
		{
			count++;
		}
		public void minus()
		{
			count--;
		}
	}
	private static class Point
	{
		public int x, y;
		public Point(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
	}
	class Rect
	{
		Point ul, lr;
		public Rect(Point ul, Point lr)
		{
			this.ul = ul;
			this.lr = lr;
		}
	}
	
	public static boolean intersect(Rect r1, Rect r2)
	{
		return !(r1.lr.x < r2.ul.x || r1.ul.x > r2.lr.x || r1.lr.y > r2.ul.y || r1.ul.y < r2.lr.y);
	}
	public static boolean hasUniqueChars(String str) {
		BitSet flags = new BitSet(128);
		for(int i = 0; i < str.length(); i++) {
			if(flags.get(str.charAt(i))) return false; // already present
			flags.set(str.charAt(i));
		}
		return true;
	}
	public static boolean hasUniqueChars2(String string) {
		char[] str = string.toCharArray();
		Arrays.sort(str);
		
		for(int i = 1; i < str.length; i++) {
			if(str[i] == str[i-1]) return false; // already present
		}
		return true;
	}
	private static void testhasUniqChars() { // 1.1
		System.out.println("strToInt of 2345 is " + (hasUniqueChars("strToInt of 2345 is ")?"":"NOT ") + "UNIQUE");
		System.out.println("nejam is " + (hasUniqueChars("nejam")?"":"NOT ") + "UNIQUE");
		System.out.println("2 strToInt of 2345 is " + (hasUniqueChars2("strToInt of 2345 is ")?"":"NOT ") + "UNIQUE");
		System.out.println("2 nejam is " + (hasUniqueChars2("nejam")?"":"NOT ") + "UNIQUE");
	}
	public static boolean isPermutation(String str, String s) {
		if(str.length() != s.length()) return false;

		HashMap<Character, Counter> charCounts = new HashMap<>();
		for(int i = 0; i < str.length(); i++) {
			if(charCounts.containsKey(str.charAt(i))) {
				Counter c = charCounts.get(str.charAt(i));
				c.plus();
			}
			else {
				Counter c = new Counter();
				charCounts.put(str.charAt(i), c);
			}
		}
		for(int i = 0; i < s.length(); i++) {
			if(!charCounts.containsKey(s.charAt(i))) return false;

			Counter c = charCounts.get(s.charAt(i));
			if(c.count() == 1) {
				charCounts.remove(s.charAt(i));
			}
			else {
				c.minus();
			}
		}
		return charCounts.isEmpty();
	}
	public static boolean isPermutation2(String str, String s)
	{
		if(str.length() != s.length()) {
			return false;
		}
		char[] ascii = new char[128];
		for(int i = 0; i < str.length(); i++) {
			ascii[str.charAt(i)]++;
		}
		for(int i = 0; i < s.length(); i++) {
			if(ascii[s.charAt(i)] == 0) {
				return false;
			}
			ascii[s.charAt(i)]--;
		}
		return true;
	}
	public static boolean isPermutation3(String str, String s)
	{
		BitSet flags = new BitSet(128);
		for(char ch : str.toCharArray()) {
			flags.flip(ch);
		}
		for(char ch : s.toCharArray()) {
			flags.flip(ch);
		}
		return (flags.cardinality() == 0);
	}
	private static void testcheckPermutation() { // 1.2
		System.out.println("nejama is " + (isPermutation("nejama", "jamane")?"":"NOT ") + "a perm of jamane");
		System.out.println("nejama is " + (isPermutation("nejama", "chumma")?"":"NOT ") + "a perm of chumma");
		System.out.println("chumma is " + (isPermutation("chumma", "poda")?"":"NOT ") + "a perm of poda");
		System.out.println("2 nejama is " + (isPermutation2("nejama", "jamane")?"":"NOT ") + "a perm of jamane");
		System.out.println("2 nejama is " + (isPermutation2("nejama", "chumma")?"":"NOT ") + "a perm of chumma");
		System.out.println("2 chumma is " + (isPermutation2("chumma", "poda")?"":"NOT ") + "a perm of poda");
		System.out.println("3 nejama is " + (isPermutation3("nejama", "jamane")?"":"NOT ") + "a perm of jamane");
		System.out.println("3 nejama is " + (isPermutation3("nejama", "chumma")?"":"NOT ") + "a perm of chumma");
		System.out.println("3 chumma is " + (isPermutation3("chumma", "poda")?"":"NOT ") + "a perm of poda");
	}

	public static boolean isPermutationOfPalindrome(String str)
	{
		BitSet flags = new BitSet(128);
		for(char ch : str.toCharArray()) {
			flags.flip(ch);
		}
		return (flags.cardinality() < 2);
	}
	private static void testpalindromepermutation() {
		System.out.println("chumma is " + (isPermutationOfPalindrome("chumma")?"":"NOT ") + "a perm of a palindrome");
		System.out.println("chuachumma is " + (isPermutationOfPalindrome("chuachumma")?"":"NOT ") + "a perm of a palindrome");
		System.out.println("chuchumma is " + (isPermutationOfPalindrome("chuchumma")?"":"NOT ") + "a perm of a palindrome");
	}
	// urlify - remove space with %20 from ASCII string
	public static void urlify(char[] str, int length)
	{
		int spaces = 0;
		for(int i = 0; i < length; i++) {
			if(str[i] == ' ') {
				spaces++;
			}
		}
		for(int src = length-1, dst = spaces*2 + length-1; src >= 0; src--) {
			if(str[src] == ' ') {
				str[dst--] = '0';
				str[dst--] = '2';
				str[dst--] = '%';
			}
			else {
				str[dst--] = str[src];
			}
		}
	}
	private static void testurlify() { // 1.3
		char[] s1 = new char[20];
		for(int i = 0; i < 13; i++) {
			s1[i] = (char)('a' + i);
		}
		s1[3] = ' ';
		s1[5] = ' ';
		s1[7] = ' ';
		System.out.print("urlify of " + Arrays.toString(s1) + " is ");
		urlify(s1, 13);
		System.out.println(Arrays.toString(s1));
	}

	public static boolean oneEditAway(String str1, String str2)
	{
		if(str1.length() == str2.length()) {
			return oneEditReplace(str1, str2);
		}
		else if(str1.length() + 1 == str2.length()) {
			return oneEditRemove(str1, str2);
		}
		else if(str1.length() == 1 + str2.length()) {
			return oneEditRemove(str2, str1);
		}
		return false;
	}
	private static boolean oneEditReplace(String s1, String s2)
	{
		boolean foundDiff = false;
		for(int i = 0; i < s1.length(); i++) {
			if(s1.charAt(i) != s2.charAt(i)) {
				if(foundDiff) {
					return false;
				}
				foundDiff = true;
			}
		}
		return true;
	}
	private static boolean oneEditRemove(String small, String large)
	{
		int i = 0, j = 0;
		boolean removed = false;
		while(i < small.length() && j < large.length()) {
			if(small.charAt(i) != large.charAt(j)) {
				if(removed) return false;
				removed = true;
			}
			else {
				i++;
			}
			j++;
		}
		return true;
	}
	public static boolean oneEditAway2(String str1, String str2)
	{
		if(Math.abs(str1.length() - str2.length()) > 1) {
			return false;
		}
		String small = str1.length() <= str2.length() ? str1: str2;
		String large = str1.length() > str2.length() ? str1: str2;
		
		int i = 0, j = 0, diffs = 0;
		while(i < small.length() && j < large.length() && diffs < 2) {
			if(small.charAt(i) != large.charAt(j)) {
				diffs++;
				if(small.length() == large.length()) 
					i++;
			}
			else {
				i++;
			}
			j++;
		}
		return (diffs < 2);
	}
	private static void testeditsAway() {
		System.out.println("spale and pale EA " + (oneEditAway("spale", "pale")?"=":">") + " 1");
		System.out.println("spale and pale EA " + (oneEditAway2("spale", "pale")?"=":">") + " 1");
		System.out.println("pale and ple EA " + (oneEditAway("pale", "ple")?"=":">") + " 1");
		System.out.println("pale and ple EA " + (oneEditAway2("pale", "ple")?"=":">") + " 1");
		System.out.println("pale and pla EA " + (oneEditAway("pale", "pla")?"=":">") + " 1");
		System.out.println("pale and pla EA " + (oneEditAway2("pale", "pla")?"=":">") + " 1");
		System.out.println("pales and pale EA " + (oneEditAway("pales", "pale")?"=":">") + " 1");
		System.out.println("pales and pale EA " + (oneEditAway2("pales", "pale")?"=":">") + " 1");
		System.out.println("pale and bale EA " + (oneEditAway("pale", "bale")?"=":">") + " 1");
		System.out.println("pale and bale EA " + (oneEditAway2("pale", "bale")?"=":">") + " 1");
		System.out.println("pale and bake EA " + (oneEditAway("pale", "bake")?"=":">") + " 1");
		System.out.println("pale and bake EA " + (oneEditAway2("pale", "bake")?"=":">") + " 1");
	}

	private static String compress(String s)
	{
		int cLen = calcCompressLength(s, null);
		if(cLen >= s.length()) {
			return s;
		}
		StringBuilder sb = new StringBuilder(cLen);
		calcCompressLength(s, sb);
		return sb.toString();
	}
	private static int calcCompressLength(String s, StringBuilder sb)
	{
		int count = 0, retLength = 0;
		for(int i = 0; i < s.length(); i++) {
			count++;
			if((i == s.length()-1) || (s.charAt(i) != s.charAt(i+1))) {
				if(sb != null) {
					sb.append(s.charAt(i)).append(count);
				}
				else {
					retLength += 1 + numLength(count);
				}
				count = 0;
			}
		}
		return retLength;
	}
	private static int numLength(int n)
	{
		int length = 0;
		while(n > 0) {
			length++;
			n /= 10;
		}
		return length;
	}
	private static String compress2(String s)
	{
		StringBuilder sb = new StringBuilder(128);
		int w = 0;
		for(int i = 0; i < s.length(); i += w) {
			char c = s.charAt(i);
			w = 1;
			for(int j = i+1; j < s.length() && s.charAt(j) == c; j++) {
				w++;
			}
			sb.append(c).append(w);
		}

		return (sb.length() < s.length())?sb.toString():s;
	}
	private static void testcompress() {
		System.out.println("compress  for : " + "aabccccccaaax is " + compress("aabccccccaaax"));
		System.out.println("compress2 for : " + "aabccccccaaax is " + compress2("aabccccccaaax"));
		System.out.println("compress  for : " + "aabbccddee is " + compress("aabbccddee"));
		System.out.println("compress2 for : " + "aabbccddee is " + compress2("aabbccddee"));
	}

	public static void rotateMatrix90DegInline(int[][] matrix)
	{
		// Equal no. of rows and columns check if(matrix)
		if(matrix.length != matrix[0].length) return;

		int nRows = matrix.length;
		for(int i = 0; i < nRows; i++) {
			for(int j = i; j < nRows-i-1; j++) {
				Point dst = new Point(i, j);
				//StringBuilder sb = new StringBuilder("(" + i + "," + j + ") ");
				do {
					dst = rotatePoint90(dst, nRows-1);
					//sb.append("-> (" + dst.x + "," + dst.y + ") ");
					int temp = matrix[dst.x][dst.y];
					matrix[dst.x][dst.y] = matrix[i][j];
					matrix[i][j] = temp;
				} while(!(dst.x == i && dst.y == j));
			//System.out.println(sb.toString());
			}
		}
	}
	private static Point rotatePoint90(Point src, int rows)
	{
		return new Point(src.y, rows - src.x);
	}

	public static void setZeros(int[][] matrix)
	{
		boolean rows[] = new boolean[matrix.length];
		boolean cols[] = new boolean[matrix[0].length];

		for(int row = 0; row < matrix.length; row++) {
			for(int col = 0; col < matrix[0].length; col++) {
				if(matrix[row][col] == 0) {
					rows[row] = true;
					cols[col] = true;
				}
			}
		}
		//set rows to zero
		for(int row = 0; row < rows.length; row++) {
			if(rows[row]) {
				for(int col = 0; col < cols.length; col++) {
					matrix[row][col] = 0;
				}
			}
		}
		//set cols to zero
		for(int col = 0; col < cols.length; col++) {
			if(cols[col]) {
				for(int row = 0; row < rows.length; row++) {
					matrix[row][col] = 0;
				}
			}
		}
	}
	public static void setZerosInline(int[][] matrix)
	{
		// Does cell[0][0] have zero ?
		boolean firstRowHasZero = (matrix[0][0] == 0);
		boolean firstColHasZero = (matrix[0][0] == 0);

		for(int row = 0; row < matrix.length; row++) {
			for(int col = 0; col < matrix[0].length; col++) {
				if(matrix[row][col] == 0) {
					if(row == 0) firstRowHasZero = true;
					if(col == 0) firstColHasZero = true;
					matrix[row][0] = 0;
					matrix[0][col] = 0;
				}
			}
		}
		
		//set rows to zero
		for(int row = matrix.length-1; row > 0; row--) {
			if(matrix[row][0] == 0) {
				for(int col = 1; col < matrix[0].length; col++) {
					matrix[row][col] = 0;
				}
			}
		}
		//set cols to zero
		for(int col = matrix[0].length-1; col > 0; col--) {
			if(matrix[0][col] == 0) {
				for(int row = 1; row < matrix.length; row++) {
					matrix[row][col] = 0;
				}
			}
		}
		
		//check first row
		if(firstRowHasZero) {
			for(int col = 0; col < matrix[0].length; col++) {
				matrix[0][col] = 0;
			}
		}
		if(firstColHasZero) {
			for(int row = 0; row < matrix.length; row++) {
				matrix[row][0] = 0;
			}
		}
	}
	private static void testsetZeros() {
		int[][] m2 = new int[][] {{2,8,5,6,0}, {1,5,4,3,2}, {6,7,8,9,10}, {11,12,13,14,15}, {21,22,23,24,0}};
		System.out.println("B4 set0s " + m2[0].length + " " + Arrays.deepToString(m2));
		setZeros(m2);
		System.out.println("A4 set0s " + Arrays.deepToString(m2));
		int[][] m21 = new int[][] {{2,8,5,6,0}, {1,5,4,3,2}, {6,7,8,9,10}, {11,12,13,14,15}, {21,22,23,24,0}};
		setZerosInline(m21);
		System.out.println("A2 set0s " + Arrays.deepToString(m21));
	}

	// is s2 a rotation of s1 ? isSubstring already available.
	public static boolean isRotation(String s1, String s2)
	{
		if(s1.length() == 0 || s1.length() != s2.length()) {
			return false;
		}
		StringBuilder sb = new StringBuilder(2*s1.length());
		sb.append(s1).append(s1);
		return (sb.toString().indexOf(s2) != -1);
	}
	
	public static void swap(char[] str, int pos1, int pos2)
	{
		if(pos1 != pos2) {
			str[pos1] ^= str[pos2];
			str[pos2] ^= str[pos1];
			str[pos1] ^= str[pos2];
		}
	}


	public static void main(String[] args)
	{
		/*
		testhasUniqChars();*/
		testcheckPermutation();
		/*testurlify();
		testpalindromepermutation();
		*/testeditsAway();
		testcompress();
		/*testsetZeros();
		*/
		
		int[][] m1 = new int[][] {{1,2,3,4,5}, {6,7,8,9,10}, {11,12,13,14,15}, {16,17,18,19,20}, {21,22,23,24,25}};
		System.out.println("B4 rot90 " + m1[0].length + " " + Arrays.deepToString(m1));
		rotateMatrix90DegInline(m1);
		System.out.println("A4 rot90 " + m1[0].length + " " + Arrays.deepToString(m1));

		//System.out.println("erbottlewat is" + (isRotation("waterbottle", "erbottlewat")?" ":" NOT ") + "a rotation of waterbottle");
		//System.out.println("erbottlawat is" + (isRotation("waterbottle", "erbottlawat")?" ":" NOT ") + "a rotation of waterbottle");
	}
}

