package sortsearch;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Arrays;
import java.util.HashMap;
import java.util.BitSet;
import java.util.Scanner;

import llsq.Stack;

public class Searching
{
	/*public enum OpenParen
	{
		Bracket('('), Square('['), Braces('{');
		private char ch;
		private OpenParen(char c)
		{
			ch = ch;
		}
	}	
	public enum CloseParen
	{
		Bracket(')'), Square(']'), Braces('}');
		private char ch;
		private CloseParen(char c)
		{
			ch = ch;
		}
	}*/
	LinkedHashSet<Integer> once;
	HashSet<Integer> dupes;
	public Searching()
	{
		once = new LinkedHashSet<Integer>();
		dupes = new HashSet<Integer>();
	}
	// Find first duplicate
	public static int s03findDuplicate(int[] intVals)
	{
		HashSet<Integer> repeats = new HashSet<>();
		for(int i = 0; i < intVals.length; i++) {
			if(repeats.contains(intVals[i])) {
				return i;
			}
			else {
				repeats.add(intVals[i]);
			}
		}
		return -1;
	}
	public static int s11findEarliestRepeat2(int[] vals)
	{
		HashMap<Integer, Integer> repeats = new HashMap<>();
		int minRepeat = Integer.MAX_VALUE;
		for(int i = 0; i < vals.length; i++) {
			if(!repeats.containsKey(vals[i])) {
				repeats.put(vals[i], i);
			}
			else {
				minRepeat = (repeats.get(vals[i]) < minRepeat)? i: minRepeat;
			}
		}
		return minRepeat;
	}	
	public static int indexInRotArray(int[] vals, int num)
	{
		int mid, left = 0, right = vals.length-1;
		while(left <= right) {
			mid = left + (right - left)/2;
			//System.out.println("left " + left + " mid " + mid + " right " + right);
			if(num == vals[mid]) {
				return mid;
			}
			if(vals[mid] < vals[left]) {
				if(num >= vals[left] || num < vals[mid]) {
					right = mid - 1;
				}
				else {
					left = mid + 1;
				}
			}
			else {
				if(num >= vals[left] && num < vals[mid]) {
					right = mid - 1;
				}
				else {
					left = mid + 1;
				}
			}
		}
		return -1;
	}
	
	public static void s17findMissingNum()
	{
		int[] intVals = {3, 2, 5, 4, 1, 7, 8, 12, 9, 10, 11}; // 6 missing
		int xorVals = 0, xorNums = 0, i = 0;
		
		for(i = 1; i <= intVals.length; i++)
		{
			xorVals ^= intVals[i-1];
			xorNums ^= i;
		}
		// The one missing no's count from the array 
		xorNums ^= i;
		System.out.println("Missing num " + (xorVals ^ xorNums));
		
	}

	public static void s21find2RepeatedNums()
	{
		int[] intVals = {3, 2, 5, 4, 1, 6, 7, 8, 6, 12, 9, 3, 10, 11}; // 3 & 6 repeat
		int[] countNums = new int[12+1];
		
		for(int i = 0; i < 12; i++)
		{
			countNums[intVals[i]]++;
			if(countNums[intVals[i]] == 2)
			{
				System.out.println("Repeated num " + intVals[i]);
			}
		}

	}

	public static void s22find2RepeatedNums()
	{
		int[] intVals = {12, 2, 5, 4, 1, 6, 7, 8, 6, 12, 9, 3, 10, 11}; // 3 & 6 repeat
		int n = 12, iXorVals = 0, iRightMostBit = 0, iRepVal1 = 0, iRepVal2 = 0;
		
		for(int i = 0; i < intVals.length; i++)
		{
			iXorVals ^= intVals[i];
		}
		for(int i = 1; i <= n; i++)
		{
			iXorVals ^= n;
		}
		iRightMostBit = iXorVals & ~(iXorVals - 1);
		
		for(int i = 0; i < intVals.length; i++)
		{
			if((intVals[i] & iRightMostBit) > 0)
			{
				iRepVal1 ^= intVals[i];
			}
			else
			{
				iRepVal2 ^= intVals[i];
			}
		}
		for(int i = 1; i <= n; i++)
		{
			if((i & iRightMostBit) > 0)
			{
				iRepVal1 ^= i;
			}
			else
			{
				iRepVal2 ^= i;
			}
		}
		System.out.println("Repeated nums " + iRepVal1 + " " + iRepVal2);
	}


	public static void s26find2NumsGivenSum()
	{
		int[] intVals = {3, 2, 5, 4, 1, 6, 7, 8, 6, 12, 9, 3, 10, 11};
		
		// Sum is 6
		int k = 6, sum = 0;
		Arrays.sort(intVals);
		for(int i = 0, j = intVals.length -1; i < j;)
		{
			sum = intVals[i] + intVals[j];
			if(sum == k)
			{
				System.out.println(intVals[i] + " + " + intVals[j] + " = " + k);
				break;
			}
			if(sum > k)
			{
				j--;
			}
			else
			{
				i++;
			}
		}

	}

	public static void s28find2NumsGivenSum()
	{
		int[] intVals = {3, 2, 5, 4, 1, 6, 7, 8, 6, 12, 9, 3, 10, 11};
		
		// Sum is 6
		int k = 6;
		HashMap<Integer, Integer> mapNums = new HashMap<>();
		for(int i : intVals) {
			if ((k != 2 *i) && mapNums.containsKey(k - i)) {
				System.out.println("Sum found " + (k - i) + " + " + i + " = " + k);
				break;
			}
			else {
				if (!mapNums.containsKey(i))
					mapNums.put(i, 1);
			}
		}

	}
	//s37find2NumsGivenSum
	

	// BEGIN Amazon Bible, GeekforGeeks, leetcode  -->
	private static int power(int val, int raise)
	{
		if(raise == 0) return 1;
		int powerVal = 1;
		for(int i = 1; i <= raise; i++) {
			powerVal *= val;
		}
		return powerVal;
	}
	public static int nextHigherDecimalSameDigits(int val)
	{
		int num = val/10;
		int loop = 1, sink = 0;
		int currDigit = val%10, lastDigit = 0;
		
		while(num > 0) {
			lastDigit = currDigit;
			currDigit = num % 10;
			num /= 10; loop++;
			if(currDigit < lastDigit) {
				int retval = num*power(10, loop);
				retval += lastDigit*power(10, loop-1) + currDigit * power(10, loop-2);
				retval += sink;
				return retval;
			}
			else {
				sink += lastDigit * power(10, loop-2);
			}
		}
		return val;
	}
	
	// String can have any combination of parantheses type but they should be nested.
	public static boolean validateParentheses(String s)
	{
		String openParens = "([{";
		String closeParens = ")]}";
		Stack<Integer> stack = new Stack<>();
		for(int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			int ndx = openParens.indexOf(ch);
			if(ndx != -1) {
				stack.push(ndx);
				continue;
			}
			ndx = closeParens.indexOf(ch);
			if(ndx != -1) {
				if(ndx != stack.pop()) {
					return false;
				}
			}
		}
		return stack.isEmpty();
	}
	// Given a infinite stream of integers, find the first nonrepeated number till now
	public void processInt(int num)
	{
		if(dupes.contains(num)) return;
		if(once.contains(num)) {
			once.remove(num);
			dupes.add(num);
		}
		else {
			once.add(num);
		}
	}
	// Same as s11findEarliestRepeat2 but the earliest non-dupe
	public int firstNonRepeat()
	{
		System.out.println("dupes " + dupes.size() + " once " + once.size());
		Iterator<Integer> iter = once.iterator();
		int val = -1;
		if(iter.hasNext()) {
			val = (int)iter.next();
		}
		return val;
	}
	// END Amazon Bible, GeekforGeeks, leetcode  -->
	
	private static void tests03findDuplicate()
	{
		int[] vals = new int[] {3, 2, 1, 2, 4, 2, 4, 3};
		int val = s03findDuplicate(vals);
		System.out.println("First duplicate in " + Arrays.toString(vals) + " @ " + val + " is " + ((val != -1)?""+vals[val]:"invalid"));

		int ndx = s11findEarliestRepeat2(vals);
		System.out.println("Earliest item duped in " + Arrays.toString(vals) + " @ " + ndx + " is " + ((ndx != -1)?""+vals[ndx]:"invalid"));

	}
	private static void testfirstNonRepeat()
	{
		Searching s = new Searching();
		int[] vals = new int[] {3, 2, 5, 1, 2, 4, 6, 2, 7, 4, 3, 6};
		for(int val : vals) {
			s.processInt(val);
		}
		System.out.println("Earliest item NOT duped in (5) " + Arrays.toString(vals) + " is " + s.firstNonRepeat());
	}
	private static void testindexInRotArray()
	{
		int[] ints = new int[]{20, 24, 27, 30, 33, 36, 38, 41, 45, 46, 47, 49, 51, 53, 55, 56, 67, 72, 1, 2, 4, 5, 7, 8, 10, 11, 13, 15, 16, 17, 19};
		System.out.println("Rotated array " + Arrays.toString(ints));
		System.out.println(" find 72 " + indexInRotArray(ints, 72));
		System.out.println(" find 10 " + indexInRotArray(ints, 10));
		System.out.println(" find 0 " + indexInRotArray(ints, 0));
		System.out.println(" find 75 " + indexInRotArray(ints, 75));
		System.out.println(" find 70 " + indexInRotArray(ints, 70));
		System.out.println(" find 12 " + indexInRotArray(ints, 12));

		ints = new int[]{14, 15, 16, 17, 18, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
		System.out.println("Rotated array " + Arrays.toString(ints));
		System.out.println(" find 1 " + indexInRotArray(ints, 1));

		ints = new int[]{14, 15, 16, 17, 18, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
		System.out.println("Rotated array " + Arrays.toString(ints));
		System.out.println(" find 0 " + indexInRotArray(ints, 0));

		ints = new int[]{15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14};
		System.out.println("Rotated array " + Arrays.toString(ints));
		System.out.println(" find 5 " + indexInRotArray(ints, 5));
	}
	public static void testnextHigherDecimalSameDigits()
	{
		System.out.println("Next hi of " + 1987654 + " is " + nextHigherDecimalSameDigits(1987654));
		System.out.println("Next hi of " + 9873546 + " is " + nextHigherDecimalSameDigits(9873546));
		System.out.println("Next hi of " + 9873456 + " is " + nextHigherDecimalSameDigits(9873456));
		System.out.println("Next hi of " + 9873465 + " is " + nextHigherDecimalSameDigits(9873465));
		System.out.println("Next hi of " + 9873654 + " is " + nextHigherDecimalSameDigits(9873654));
		System.out.println("Next hi of " + 25 + " is " + nextHigherDecimalSameDigits(25));
		System.out.println("Next hi of " + 98765 + " is " + nextHigherDecimalSameDigits(98765));
		System.out.println("Next hi of " + 62832 + " is " + nextHigherDecimalSameDigits(62832));
	}
	private static void testvalidateParentheses()
	{
		System.out.println("(() is " + (validateParentheses("(()")?"":"NOT") + " valid");
		System.out.println("() is " + (validateParentheses("()")?"":"NOT") + " valid");
		System.out.println("([{[(Ganesh())]}]) is " + (validateParentheses("([{[(Ganesh())]}])")?"":"NOT") + " valid");
		System.out.println("([chumma]) is " + (validateParentheses("([chumma])")?"":"NOT") + " valid");
		System.out.println("[] is " + (validateParentheses("[]")?"":"NOT") + " valid");
		System.out.println("{  } is " + (validateParentheses("{}")?"":"NOT") + " valid");
		System.out.println("([[[}}}() is " + (validateParentheses("([[[}}}()")?"":"NOT") + " valid");
	}
	
	public static void main(String[] args)
	{
		tests03findDuplicate();
		s11findEarliestRepeat();
		testfirstNonRepeat();
		testindexInRotArray();
		testnextHigherDecimalSameDigits();
		testvalidateParentheses();
		s17findMissingNum();
		s21find2RepeatedNums();
		s22find2RepeatedNums();

		s26find2NumsGivenSum();
		s28find2NumsGivenSum();
	}

	
	public static void s11findEarliestRepeat()
	{
		int[] intVals = {3, 2, 1, 2, 4, 2, 4, 3}; // Answer here is 3 NOT 2
		int val;
		System.out.println("Int Vals " + Arrays.toString(intVals));
		
		HashMap<Integer, Integer> mapRepeats = new HashMap<>();
		for(int i = 0; i < intVals.length; i++)
		{
			if(mapRepeats.containsKey(intVals[i]))
			{
				val = (int)mapRepeats.get(intVals[i]);
				if(val > 0)
				{
					mapRepeats.put(intVals[i], -1 * val);
				}
			}
			else
			{
				mapRepeats.put(intVals[i], i+1);
			}
		}
		
		for(int i = 0; i < intVals.length; i++)
		{
			if(mapRepeats.containsValue(-1*(i+1)))
			{
				System.out.println("First in array among repeated elements : " + intVals[i]);
				break;
			}
		}
	}
}
