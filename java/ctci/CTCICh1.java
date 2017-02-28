package ctci;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.BitSet;

import llsq.Queue;

// Programming Interviews Exposed questions
public class CTCICh1
{
	public StringBuilder sb;
	int[] pattern;
	char[][] charKey;

    public CTCICh1()
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
		public int getCount()
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
	public static boolean hasUniqueChars(String str)
	{
		BitSet flags = new BitSet(128);
		for(int i = 0; i < str.length(); i++) {
			if(flags.get(str.charAt(i))) {
				return false; // already present
			}
			flags.set(str.charAt(i));
		}
		return true;
	}
	public static boolean hasUniqueChars2(String string)
	{
		char[] str = string.toCharArray();
		Arrays.sort(str);
		
		for(int i = 1; i < str.length; i++) {
			if(str[i] == str[i-1]) {
				return false; // already present
			}
		}
		return true;
	}
	
	public static boolean isPermutation(String str, String s)
	{
		if(str.length() != s.length()) {
			return false;
		}
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
			Counter c = charCounts.get(s.charAt(i));
			if(c == null) {
				return false;
			}
			else if(c.getCount() == 1) {
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

	public static boolean isPermutationOfPalindrome(String str)
	{
		BitSet flags = new BitSet(128);
		for(char ch : str.toCharArray()) {
			flags.flip(ch);
		}
		return (flags.cardinality() < 2);
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
	private static boolean oneEditRemove(String sShort, String sLong)
	{
		int iShort = 0, iLong = 0;
		boolean foundDiff = false;
		while(iShort < sShort.length() && iLong < sLong.length()) {
			if(sShort.charAt(iShort) != sLong.charAt(iLong)) {
				if(foundDiff) {
					return false;
				}
				foundDiff = true;
				iLong++;
			}
			else {
				iShort++;
				iLong++;
			}
		}
		return true;
	}
	public static boolean oneEditAway2(String str1, String str2)
	{
		if(Math.abs(str1.length() - str2.length()) > 1) {
			return false;
		}
		String sShort = str1.length() <= str2.length() ? str1: str2;
		String sLong = str1.length() > str2.length() ? str1: str2;
		
		int iShort = 0, iLong = 0;
		boolean foundDiff = false;
		while(iShort < sShort.length() && iLong < sLong.length()) {
			if(sShort.charAt(iShort) != sLong.charAt(iLong)) {
				if(foundDiff) {
					return false;
				}
				foundDiff = true;
				iLong++;
				if(sShort.length() == sLong.length()) {
					iShort++;
				}
			}
			else {
				iShort++;
				iLong++;
			}
		}
		return true;
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
	public static void setZeros2(int[][] matrix)
	{
		boolean firstRowHasZero, firstColHasZero;
		// Does first row and first col have zero ?
		for(int col = 0; col < matrix[0].length; col++) {
			if(matrix[0][col] == 0) {
				firstRowHasZero = true;
				break;
			}
		}
		for(int row = 0; row < matrix.length; row++) {
			if(matrix[row][0] == 0) {
				firstColHasZero = true;
				break;
			}
		}
		for(int row = 1; row < matrix.length; row++) {
			for(int col = 1; col < matrix[0].length; col++) {
				if(matrix[row][col] == 0) {
					matrix[row][0] = 0;
					matrix[0][col] = 0;
				}
			}
		}
		//set rows to zero
		for(int row = 0; row < matrix.length; row++) {
			if(matrix[row][0] == 0) {
				for(int col = 1; col < matrix[0].length; col++) {
					matrix[row][col] = 0;
				}
			}
		}
		//set cols to zero
		for(int col = 0; col < matrix[0].length; col++) {
			if(matrix[0][col] == 0) {
				for(int row = 1; row < matrix.length; row++) {
					matrix[row][col] = 0;
				}
			}
		}
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
	public static void reverseWord(char[] str, int begin, int end)
	{
		while(begin < end) {
			swap(str, begin, end);
			begin++;
			end--;
		}
	}
	public static String reverseWords(String str)
	{
		char[] s = str.toCharArray();
		int begin = 0, end = 0;
		while(begin < s.length) {
			while(end < s.length && s[end] != ' ') {
				end++;
			}
			reverseWord(s, begin, end-1);
			end++;
			begin = end;
		}
		return new String(s);
	}
	public static int strToInt(String s)
	{
		int begin = 0, ret = 0;
		boolean minus = false;
		if(s.charAt(begin) == '-') {
			begin++;
			minus = true;
		}
		while(begin < s.length()) {// && s.charAt(begin) > '0' && s.charAt(begin) <= '9') {
			ret = ret * 10 + (s.charAt(begin) - '0');
			begin++;
		}
		return ret * (minus?-1:1);
	}
	public static String intToStr(int val)
	{
		char[] str = new char[32];
		str[0] = '0';
		if(val < 0) {
			str[0] = '-';
			val = -1 * val;
		}
		int dst = 1;
		while(val > 0) {
			str[dst++] = (char)(val%10 + '0');
			val /= 10;
		}
		for(int i = 1, j = dst-1; i < j; i++, j--) {
			swap(str, i, j);
		}
		return new String(str, 0, dst);
	}

	public void permuteRec(String s)
	{
		sb = new StringBuilder(512);
		char[] str = s.toCharArray();
		permuteRec(str, 0);
	}
	private void permuteRec(char[] s, int begin)
	{
		if(begin == s.length) {
			sb.append(s).append(" ");
			//System.out.println(s);
		}
		else {
			for(int i = begin; i < s.length; i++) {
				swap(s, i, begin);
				permuteRec(s, begin+1);
				swap(s, i, begin);
			}
		}
	}

	private String combos(String s)
	{
		sb = new StringBuilder(512);
		int combos = 2 << s.length()-1;
		int bitsOn = 0;

		for(int i = 1; i <= combos; i++) {
			for(int bit = 0; bit < s.length(); bit++) {
				bitsOn = (1 << bit) & i;
				if(bitsOn > 0) {
					sb.append(s.charAt(bit));
				}
			}
			sb.append(" ");
		}
		return sb.toString();
	}
	
	private int numOnesInBinary(int n)
	{
		int numOnes = 0;
		while(n != 0) {
			if((1 & n) == 1) {
				numOnes++;
			}
			n = (n >>> 1);
		}
		return numOnes;
	}

	// Assume 7 digit phone number is given with each number 0-9
	public void getPhoneNumWords(int[] phNum)
	{
		sb = new StringBuilder(2048);
		pattern = setPatternArray(phNum);
		
		getPhoneNumWords(phNum, "", 0);
	}
	private void getPhoneNumWords(int[] phNum, String prefix, int start)
	{
		if(start == phNum.length) {
			sb.append(prefix).append(" ");
			return;
		}
		for(int j = 0; j < pattern[start]; j++) {
			String s = prefix + getCharKey(phNum[start], j);
			getPhoneNumWords(phNum, s, start+1);
		}
	}
	private int[] setPatternArray(int[] phNum)
	{
		int[] pattern = new int[phNum.length];
		for(int i = 0; i < phNum.length; i++) {
			if(phNum[i] == 0 || phNum[i] == 1) {
					pattern[i] = 1;
			}
			else if(phNum[i] == 7 || phNum[i] == 9) {
					pattern[i] = 4;
			}
			else { // 2, 3, 4, 5, 6, 8
					pattern[i] = 3;
			}
		}
		return pattern;
	}
	private char getCharKey(int key, int place)
	{
		char ch = 0;
		switch(key) {
			case 0: case 1:
				ch = charKey[key][0];
				break;
			case 2: case 3: case 4:
			case 5: case 6: case 7:
			case 8: case 9:
				ch = charKey[key][place];
				break;
		}
		return ch;
		//switch(key) {
		//	case 0: case 1:
		//		return (char)('0' + key);
		//	case 2: case 3: case 4:
		//	case 5: case 6: case 7:
		//		return (char)('A' + 3*(key - 2) + place -1);
		//	case 8: case 9: default:
		//		return (char)('A' + 3*(key - 2) + place);
		//}
	}
	// Programming Interviews Exposed - END

	public static void main(String[] args)
	{
		System.out.println("strToInt of 2345 is " + (hasUniqueChars("strToInt of 2345 is ")?"":"NOT ") + "UNIQUE");
		System.out.println("nejam is " + (hasUniqueChars("nejam")?"":"NOT ") + "UNIQUE");
		System.out.println("2 strToInt of 2345 is " + (hasUniqueChars2("strToInt of 2345 is ")?"":"NOT ") + "UNIQUE");
		System.out.println("2 nejam is " + (hasUniqueChars2("nejam")?"":"NOT ") + "UNIQUE");

		System.out.println("nejama is " + (isPermutation("nejama", "jamane")?"":"NOT ") + "a perm of jamane");
		System.out.println("nejama is " + (isPermutation("nejama", "chumma")?"":"NOT ") + "a perm of chumma");
		System.out.println("chumma is " + (isPermutation("chumma", "poda")?"":"NOT ") + "a perm of poda");
		System.out.println("2 nejama is " + (isPermutation2("nejama", "jamane")?"":"NOT ") + "a perm of jamane");
		System.out.println("2 nejama is " + (isPermutation2("nejama", "chumma")?"":"NOT ") + "a perm of chumma");
		System.out.println("2 chumma is " + (isPermutation2("chumma", "poda")?"":"NOT ") + "a perm of poda");

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

		System.out.println("chumma is " + (isPermutationOfPalindrome("chumma")?"":"NOT ") + "a perm of a palindrome");
		System.out.println("chuachumma is " + (isPermutationOfPalindrome("chuachumma")?"":"NOT ") + "a perm of a palindrome");
		System.out.println("chuchumma is " + (isPermutationOfPalindrome("chuchumma")?"":"NOT ") + "a perm of a palindrome");

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

		System.out.println("compress for : " + "aabccccccaaax is " + compress("aabccccccaaax"));
		System.out.println("compress for : " + "aabbccddee is " + compress("aabbccddee"));
		
		CTCICh1 s = new CTCICh1();
		s.permuteRec("abcd");
		System.out.println("Permute String: " + s.sb.toString());
		System.out.println("Combos of h: " + s.combos("h"));
		System.out.println("Combos of hat: " + s.combos("hat"));
		System.out.println("Combos of blake: " + s.combos("blake"));
		System.out.println("Combos of abcd: " + s.combos("abcd"));
		System.out.println("numOnesInBinary of 7: " + s.numOnesInBinary(7));
		System.out.println("numOnesInBinary of -5: " + s.numOnesInBinary(-5));
		System.out.println("numOnesInBinary of 0: " + s.numOnesInBinary(0));
		System.out.println("numOnesInBinary of 31: " + s.numOnesInBinary(31));
		System.out.println("numOnesInBinary of 47: " + s.numOnesInBinary(47));

		s.getPhoneNumWords(new int[]{7, 3, 1});
		System.out.println("Ph num words: " + s.sb.toString());
		
		int[][] m1 = new int[][] {{1,2,3,4,5}, {6,7,8,9,10}, {11,12,13,14,15}, {16,17,18,19,20}, {21,22,23,24,25}};
		System.out.println("B4 rot90 " + m1[0].length + " " + Arrays.deepToString(m1));
		rotateMatrix90DegInline(m1);
		System.out.println("A4 rot90 " + m1[0].length + " " + Arrays.deepToString(m1));

		int[][] m2 = new int[][] {{2,8,5,6,0}, {1,5,4,3,2}, {6,7,8,9,10}, {11,12,13,14,15}, {21,22,23,24,0}};
		System.out.println("B4 set0s " + m2[0].length + " " + Arrays.deepToString(m2));
		setZeros(m2);
		System.out.println("A4 set0s " + Arrays.deepToString(m2));
		int[][] m21 = new int[][] {{2,8,5,6,0}, {1,5,4,3,2}, {6,7,8,9,10}, {11,12,13,14,15}, {21,22,23,24,0}};
		setZeros2(m21);
		System.out.println("A4 set0s " + Arrays.deepToString(m21));
		
		System.out.println("erbottlewat is" + (isRotation("waterbottle", "erbottlewat")?" ":"NOT ") + "a rotation of waterbottle");
		System.out.println("erbottlawat is" + (isRotation("waterbottle", "erbottlawat")?" ":"NOT ") + "a rotation of waterbottle");
	}
}

