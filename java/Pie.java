import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.BitSet;

// Programming Interviews Exposed questions
public class Pie
{
	public StringBuilder sb;
	int[] pattern;
	char[][] charKey;

    public Pie()
	{
		charKey = new char[][] {{'0'}, {'1'}, {'A','B','C'}, {'D','E','F'}, {'G','H','I'}, {'J','K','L'},
		{'M','N','O'}, {'P','Q','R','S'}, {'T','U','V'}, {'W','X','Y','Z'}};
    }

	class Point
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
	// find first nonrepeated character in a string.
	// first nonrepeated character in "total" is 'o', in "teeter" is 'r'.
	public static String firstNonRepeated(String str)
	{
		HashMap<Integer, Object> charMap = new HashMap<>();
		Object seen, once = new Object(), many = new Object();
		for(int i = 0; i < str.length();) {
			int ch = str.codePointAt(i);
			i += Character.charCount(ch);
			
			seen = charMap.get(ch);
			if(seen == null) {
				charMap.put(ch, once);
			}
			else if(seen == once) {
				charMap.put(ch, many);
			}
		}
		for(int i = 0; i < str.length();) {
			int ch = str.codePointAt(i);
			i += Character.charCount(ch);
			if(once == charMap.get(ch)) {
				return new String(Character.toChars(ch));
			}
		}
		return null;
	}
	// delete specified chars from ASCII string
	public static String removeChars(String str, String remove)
	{
		char[] s = str.toCharArray();
		BitSet flags = new BitSet(128);
		
		for(int i = 0; i < remove.length(); i++) {
			flags.set(remove.charAt(i));
		}
		int dst = 0;
		for(int src = 0; src < str.length(); src++) {
			if(!flags.get(s[src])) {
				s[dst++] = s[src];
			}
		}
		return new String(s, 0, dst);
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
		System.out.println("Battle of the Vowels: Hawaii vs. Grozny - aeiou = " + removeChars("Battle of the Vowels: Hawaii vs. Grozny", "aeiou "));
		System.out.println("Reverse of [Do or do not, there is no try.] is " + reverseWords("Do or do not, there is no try."));
	
		System.out.println("strToInt of -1567 is " + strToInt("-1567"));
		System.out.println("strToInt of 1567 is " + strToInt("1567"));
		System.out.println("strToInt of 23 45 is " + strToInt("23 45"));
		System.out.println("intToStr of 2345 is " + intToStr(2345));
		System.out.println("intToStr of -2345 is " + intToStr(-2345));
		System.out.println("intToStr of 0 is " + intToStr(0));
		
		Pie s = new Pie();
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
		
	}
}

