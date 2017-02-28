package ctci;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

// 16.1 DONE swap without temp
// 16.2 IMPR Can trie or prefix/suffix tree be used ?
// 16.3 TODO
// 16.4 TODO
// 16.5 DONE
// 16.6 DONE
// 16.7 DONE
// 16.8 TODO
// 16.9 TODO
// 16.10 TODO
// 16.11 TODO
// 16.12 TODO
// 16.13 TODO
// 16.14 TODO
// 16.15 TODO
// 16.16 DONE
// 16.17 TODO
// 16.18 TODO
// 16.19 TODO
// 16.20 TODO
// 16.21 TODO
// 16.22 TODO
// 16.23 TODO
// 16.24 TODO
// 16.25 TODO
// 16.26 TODO
 
public class CTCICh16Moderate
{
	private static class Counter
	{
		private int count;
		public Counter()
		{
			count = 0;
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

	// Swap no.s in place (no temp vars) use +/- or xor
	public static int getFreq(String[] book, String word)
	{
		int counter = 0;
		for(String str : book) {
			if(word.trim().equalsIgnoreCase(str)) {
				counter++;
			}
		}
		return counter;
	}
	public static int getFreq(HashMap<String, Counter> wordFreq, String word)
	{
		word = word.trim().toLowerCase();
		if(wordFreq.containsKey(word)) {
			return wordFreq.get(word).getCount();
		}
		return 0;
	}
	public static HashMap<String, Counter> preProcessBook(String[] book)
	{
		HashMap<String, Counter> wordFreq = new HashMap<>();
		for(String str : book) {
			String word = str.trim().toLowerCase();
			if(!"".equals(word)) {
				if(!wordFreq.containsKey(word)) {
					wordFreq.put(word, new Counter());
				}
				wordFreq.get(word).plus();
			}
		}
		return wordFreq;
	}
	
	public static int countZerosFact(int num)
	{
		if(num < 0) return -1;
		int count = 0;
		for(int i = 5; i <= num; i += 5) {
			count += num5Factors(i);
		}
		return count;
	}
	private static int num5Factors(int num)
	{
		int fives = 0;
		while(num%5 == 0) {
			fives++;
			num /= 5;
		}
		return fives;
	}
	public static int countZerosFact2(int num)
	{
		if(num < 0) return -1;
		int count = 0;
		for(int i = 5; num/i > 0; i *= 5) {
			count += num/i;
		}
		return count;
	}

	public static String leastDiff(int[] arr1, int[] arr2)
	{
		if(arr1 == null || arr2 == null || arr1.length < 1 || arr2.length < 1) return "";
		Arrays.sort(arr1);
		Arrays.sort(arr2);
		int val1 = 0, val2 = 0, diff = Integer.MAX_VALUE;
		for(int i = 0, j = 0; i < arr1.length && j < arr2.length;) {
			if(diff > Math.abs(arr1[i] - arr2[j])) {
				val1 = arr1[i];
				val2 = arr2[j];
				diff = Math.abs(val1 - val2);
			}
			if(arr1[i] < arr2[j]) {
				i++;
			}
			else {
				j++;
			}
		}
		return String.format("{%d, %d} %d ", val1, val2, diff);
	}

	public static boolean isPower(int x)
	{
		if (x <= 1) return true;
		for (int base = 2; base < x && base < Integer.MAX_VALUE / base; base++) {
			int temp = base; 
			while (temp <= x && temp < Integer.MAX_VALUE/ base) {
				temp *= base;
				if (temp == x) return true;
			}
		}
		return false;
	}
	public static int[] isPower2(int x)
	{
		if (x <= 1) return (new int[] {1});
		for (int base = 2; base < x && base < Integer.MAX_VALUE / base; base++) {
			int temp = base, exp = 1; 
			while (temp <= x && temp < Integer.MAX_VALUE/ base) {
				temp *= base;
				exp++;
				if (temp == x) return (new int[] {base, exp});
			}
		}
		return (new int[]{});
	}
	public static int findMax(int a, int b)
	{
		// 0 if a > b, else 1
		int sign_aMinusb = ((a - b) >>> 31) & 1; 
		return a - sign_aMinusb *(a - b);
	}
	public static int findMax2(int a, int b)
	{
		// 0 if a > b, else 1
		int sign_aMinusb = ((a - b) >>> 31) & 1; 
		// return a - sign_aMinusb *(a - b);
		
		// overflow only occurs when 
		// a is -ve/ b is +ve, then a < b, so sign for (a-b) is 1
		// a is +ve/ b is -ve, then a > b, so sign for (a-b) is 0
		// if a and b have opposite signs, sign(a-b) = sign(a)
		// if a and b have same signs, sign(a-b) = sign(a-b)
		int sign_a = ((a >>> 31) & 1);
		int sign_b = ((b >>> 31) & 1);
		int signOverflow = (sign_a ^ sign_b) & sign_a;
		int signNoOverflow = (1 - (sign_a ^ sign_b)) & sign_aMinusb;
		return a - (signOverflow | signNoOverflow) *(a - b);
	}
	private static int sign(int x)
	{
		return (x >>> 31) & 1; 
	}
	public static int findMax3(int a, int b)
	{
		// k*a + ~k*b 0  k = 1 if a > b, else 0
		int k = 1 - sign(a - b); 
		// return a - sign_aMinusb *(a - b);
		int val1 = (1 - sign(a)^sign(b)) * (k*a + (1-k)*b);

		// if a and b have opposite signs, choose the one with + sign
		k = 1 - sign(a); 
		int val2 = (sign(a)^sign(b)) * (k*a + (1-k)*b);

		return (val1 + val2);
	}

	public static int[] subsort(int[] arr)
	{
		int begin = 0, end = arr.length-1;
		boolean goLeft = true, goRight = true;
		while((arr[begin] <= arr[end]) && (begin < end) && (goLeft || goRight)) {
			if(arr[begin] <= arr[begin+1]) {
				begin++;
			}
			else {
				goRight = false;
			}
			if(arr[end] >= arr[end-1]) {
				end--;
			}
			else {
				goLeft = false;
			}
		}
		if(begin >= end) {
			return (new int[] {});
		}
		// now find (min, max) between [begin, end]
		int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
		for(int i = begin; i <= end; i++) {
			if(arr[i] < min) {
				min = arr[i];
			}
			if(arr[i] > max) {
				max = arr[i];
			}
		}
		// go left till min can be placed
		for(int i = begin; i >= 0 && arr[i] > min; i--) {
			begin = i;
		}

		// go right till max can be placed
		for(int i = end; i < arr.length && arr[i] < max; i++) {
			end = i;
		}
		return (new int[] {begin, end});
	}
	private static void testfindMax()
	{
		System.out.print("max(-2, 0) = " + findMax(-2, 0));
		System.out.print("  max(2, 0) = " + findMax(2, 0));
		System.out.print("  max(0, 0) = " + findMax(0, 0));
		System.out.print("  max(2, 2) = " + findMax(2, 2) + "\n");
		System.out.print("max(-2, 1) = " + findMax(-2, 1));
		System.out.print("  max(1, -2) = " + findMax(1, -2));
		System.out.print("  max(-5, -2) = " + findMax(-5, -2) + "\n");
		System.out.print("max(-2, -2) = " + findMax(-2, -2));
		System.out.print("  max(-2, -5) = " + findMax(-2, -5));
		System.out.print("  max(2, 5) = " + findMax(2, 5));
		System.out.print("  max(10, 1) = " + findMax(10, 1) + "\n");

		System.out.println("max(Integer.MAX_VALUE, -15) = " + findMax(Integer.MAX_VALUE, -15));
		System.out.println("max(-15, Integer.MAX_VALUE) = " + findMax(-15, Integer.MAX_VALUE));

		System.out.print("max2(-2, 0) = " + findMax2(-2, 0));
		System.out.print("  max2(2, 0) = " + findMax2(2, 0));
		System.out.print("  max2(0, 0) = " + findMax2(0, 0));
		System.out.print("  max2(2, 2) = " + findMax2(2, 2) + "\n");
		System.out.print("max2(-2, 1) = " + findMax2(-2, 1));
		System.out.print("  max2(1, -2) = " + findMax2(1, -2));
		System.out.print("  max2(-5, -2) = " + findMax2(-5, -2) + "\n");
		System.out.print("max2(-2, -2) = " + findMax2(-2, -2));
		System.out.print("  max2(-2, -5) = " + findMax2(-2, -5));
		System.out.print("  max2(2, 5) = " + findMax2(2, 5));
		System.out.print("  max2(10, 1) = " + findMax2(10, 1) + "\n");

		System.out.println("Integer.MAX_VALUE = " + Integer.MAX_VALUE);
		System.out.println("max2(Integer.MAX_VALUE, -15) = " + findMax2(Integer.MAX_VALUE, -15));
		System.out.println("max2(-15, Integer.MAX_VALUE) = " + findMax2(-15, Integer.MAX_VALUE));

		System.out.print("max3(-2, 0) = " + findMax3(-2, 0));
		System.out.print("  max3(2, 0) = " + findMax3(2, 0));
		System.out.print("  max3(0, 0) = " + findMax3(0, 0));
		System.out.print("  max3(2, 2) = " + findMax3(2, 2) + "\n");
		System.out.print("max3(-2, 1) = " + findMax3(-2, 1));
		System.out.print("  max3(1, -2) = " + findMax3(1, -2));
		System.out.print("  max3(-5, -2) = " + findMax3(-5, -2) + "\n");
		System.out.print("max3(-2, -2) = " + findMax3(-2, -2));
		System.out.print("  max3(-2, -5) = " + findMax3(-2, -5));
		System.out.print("  max3(2, 5) = " + findMax3(2, 5));
		System.out.print("  max3(10, 1) = " + findMax3(10, 1) + "\n");

		System.out.println("Integer.MAX_VALUE = " + Integer.MAX_VALUE);
		System.out.println("max3(Integer.MAX_VALUE, -15) = " + findMax3(Integer.MAX_VALUE, -15));
		System.out.println("max3(-15, Integer.MAX_VALUE) = " + findMax3(-15, Integer.MAX_VALUE));

		//System.out.println("Integer.MAX_VALUE - 2 + 15 = " + (Integer.MAX_VALUE - 2 + 15));
		//System.out.println("Integer.MIN_VALUE - 15 = " + (Integer.MIN_VALUE - 15));
	}
	private static void testcountZerosFact()
	{
		System.out.print("F0s(25) = " + countZerosFact(25));
		System.out.print("  F0s(15) = " + countZerosFact(15));
		System.out.print("  F0s(125) = " + countZerosFact(125));
		System.out.print("  F0s(499) = " + countZerosFact(499));
		System.out.print("  F0s(500) = " + countZerosFact(500));
		System.out.print("  F0s(501) = " + countZerosFact(501));
		System.out.print("  F0s(626) = " + countZerosFact(626) + "\n");

		System.out.print("F0s(25) = " + countZerosFact2(25));
		System.out.print("  F0s(15) = " + countZerosFact2(15));
		System.out.print("  F0s(125) = " + countZerosFact2(125));
		System.out.print("  F0s(499) = " + countZerosFact2(499));
		System.out.print("  F0s(500) = " + countZerosFact2(500));
		System.out.print("  F0s(501) = " + countZerosFact2(501));
		System.out.print("  F0s(626) = " + countZerosFact2(626) + "\n");
	}
	private static void testleastDiff()
	{
		System.out.print(leastDiff(new int[] {1, 3, 15, 11, 2}, new int[] {23, 127, 235, 19, 8}));
		System.out.print(leastDiff(new int[] {1, 3, 15, 11, 2, 21}, new int[] {23, 127, 235, 19, 8}));
		System.out.println(leastDiff(new int[] {1, 2, 11, 15}, new int[] {4, 12, 19, 23, 127, 235}));
	}
	private static void testisPower()
	{
		System.out.println("1024000000 " + isPower(1024000000));
		System.out.println("393 " + isPower(393));
		System.out.println("1024000000 " + Arrays.toString(isPower2(1024000000)));
		System.out.println("" + 11*11*11*11*11 + Arrays.toString(isPower2(11*11*11*11*11)));
		System.out.println("393 " + Arrays.toString(isPower2(393)));
	}
	private static void testsubsort()
	{
		int[] a = new int[] {1, 2, 4, 7, 10, 11, 7, 12, 6, 7, 16, 18, 19};
		System.out.println(Arrays.toString(a) + " " + Arrays.toString(subsort(a)));
		a = new int[] {1, 2, 4, 7, 10, 11, 8, 12, 5, 6, 16, 18, 19};
		System.out.println(Arrays.toString(a) + " " + Arrays.toString(subsort(a)));
		a = new int[] {1, 2, 4, 7, 10, 11, 7, 17, 12, 3, 6, 7, 16, 18, 19};
		System.out.println(Arrays.toString(a) + " " + Arrays.toString(subsort(a)));
		a = new int[] {1, 2, 4, 7, 10, 11, 16, 17, 18, 19};
		System.out.println(Arrays.toString(a) + " " + Arrays.toString(subsort(a)));
		a = new int[] {1, 4, 2, 19, 18, 20};
		System.out.println(Arrays.toString(a) + " " + Arrays.toString(subsort(a)));
		a = new int[] {4, 2, 19, 18};
		System.out.println(Arrays.toString(a) + " " + Arrays.toString(subsort(a)));
		a = new int[] {4, 3, 2, 1};
		System.out.println(Arrays.toString(a) + " " + Arrays.toString(subsort(a)));
		a = new int[] {1, 2, 4, 7, 10, 11, 7, 12, 6, 7, 16, 18, 19, 1};
		System.out.println(Arrays.toString(a) + " " + Arrays.toString(subsort(a)));
		a = new int[] {1, 2, 3, 4};
		System.out.println(Arrays.toString(a) + " " + Arrays.toString(subsort(a)));
		a = new int[] {4, 4, 4, 4};
		System.out.println(Arrays.toString(a) + " " + Arrays.toString(subsort(a)));
	}
	public static void main(String[] args)
	{
		testfindMax();
		testcountZerosFact();
		testleastDiff();
		testisPower();
		testsubsort();
	}
}
