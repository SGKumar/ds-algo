package bits;

import java.util.ArrayList;
import java.util.Arrays;

public class BitMath
{
	// BEGIN Cracking the Coding Interview Ch. 05
	private static int insertBinaryNum(int n, int m, int i, int j)
	{
		int mask = (-1 << j) + (1 <<i )-1;
		int n_gapped = n & mask;
		return n_gapped | (m << i);
	}

	private static String doubleToBinaryString(double d)
	{
		StringBuilder sb = new StringBuilder(64);
		sb.append(".");
		while(sb.length() <= 32 && d != 0) {
			d = d*2;
			if(d >= 1) {
				sb.append("1");
				d = d - 1.0;
			}
			else {
				sb.append("0");
			}
			System.out.println(sb + " double value is " + d);
		}
		if(d > 0) {
			sb.append(" ERROR");
		}
		return sb.toString();
	}

	private static int longestSeqFlip0To1(int m)
	{
		int bit = 1, maxLen = 0, currLen = 0, prevLen = 0;
		while(bit <= m) {
			if((bit & m) > 0) {
				currLen++;
			}
			else {
				prevLen = currLen;
				currLen = 0;
			}
			bit <<= 1;
			maxLen = java.lang.Math.max(maxLen, prevLen + currLen + 1);
		}
		//maxLen = java.lang.Math.max(maxLen, prevLen + currLen + 1);
		return maxLen;
	}

	private static int nextLargerWithSameNumof1Bits(int m)
	{
		// change first "01" pattern from right to "10"
		int bit = 1, loCheck, hiCheck;
		do {
			loCheck = bit & m;
			hiCheck = ((bit << 1) & m);
			bit <<= 1;
		} while(loCheck == 0 || hiCheck > 0);
		return m + (bit >> 1);
	}
	private static int nextSmallerWithSameNumof1Bits(int m)
	{
		if((m & (m+1)) == 0) return 0;
		// change first "10" pattern from right to "01"
		int bit = 1, loCheck, hiCheck;
		do {
			loCheck = bit & m;
			hiCheck = ((bit << 1) & m);
			bit <<= 1;
		} while(loCheck > 0 || hiCheck == 0);
		return m -(bit >> 1);
	}

	private static boolean isPowerOf2(int n)
	{
		return ((n & (n-1)) == 0);
	}

	private static int flipBitsToConvert(int m, int n)
	{
		int val = m ^ n;
		int bitsToFlip = 0;
		for(int i = val; i > 0; i = (i & (i-1))) {
			bitsToFlip++;
		}
		return bitsToFlip;
	}

	private static int pairwiseBitSwap(int n)
	{	// remember vals at even and odd locations.
		int evenMask = 0x55555555 & n;
		int oddMask = 0xaaaaaaaa & n;
		// merge both after shifting left/right by 1
		return (evenMask << 1) | (oddMask >>> 1);
	}

	private void drawLine(byte[] screen, int width, int x1, int x2, int y)
	{
		int startByte = y*width/8 + (x1/8);
		int endByte = y*width/8 + (x2/8);

		if(startByte == endByte) {
			int startBit = x1%8;
			int endBit = x2%8;
			int mask = (1 << (8 - startBit)) - 1;
			mask &= 1 << (8 - endBit- 1);
			screen[startByte+1] |= mask;
			return;
		}

		if(x1%8 > 0) {
			int startBit = x1%8;
			int mask = (1 << (8 - startBit)) - 1;
			screen[startByte] |= mask;
			startByte++;
		}
		if(x2%8 < 7) {
			int endBit = x2%8;
			int mask = (1 << (1 + endBit)) - 1;
			mask <<= (8 - endBit - 1);
			screen[endByte] |= mask;
			endByte--;
		}
		for(int i = startByte; i <= endByte; i++) {
			screen[i] |= 0xFF;
		}
	}
	// END Cracking the Coding Interview Ch. 05
	
	private static int leftmostBit(int n) {
		int b = 0;
		while(n > 0) {
			n >>>= 1;
			b++;
		}
		return b;
	}
	public static int onBitsRec(int n) {
		if(n < 3) return n;
		int k = leftmostBit(n);
		if(isPowerOf2(n)) {
			return 1 + (k-1)*(1 <<(k-2));
		}
		if(isPowerOf2(n+1)) {
			return k*(1 <<(k-1));
		}
		int tillPowerOf2 = 1 + (k-1)*(1 <<(k-2));
		int leftMostBits = n - (1 <<(k-1));
		int sum = tillPowerOf2 + leftMostBits;
		return sum + onBitsRec(leftMostBits);
	}
	public static int onBitsIter(int n) {
		int sum = 0;
		while(n >= 3) {
			int k = leftmostBit(n);
			if(isPowerOf2(n)) {
				sum += 1 + (k-1)*(1 <<(k-2));
				return sum;
			}
			else if(isPowerOf2(n+1)) {
				sum += k*(1 <<(k-1));
				return sum;
			}
			else {
				sum += 1 + (k-1)*(1 <<(k-2)) + n -(1 <<(k-1));
			}
			n -= (1 <<(k-1));
		}
		sum += n;
		return sum;
	}
	public static int onBits(int n) {
		int sum = 0;
		while(n >= 3) {
			int k = leftmostBit(n);
			sum += 1 + (k-1)*(1 <<(k-2));
			int remBits = n -(1 <<(k-1));
			sum += remBits;
			n -= remBits;
		}
		sum += n;
		return sum;
	}
	
	private static void testPalindrome(int val)
	{
		int i = 0, valp = 0;
		System.out.println(val + " is " + Integer.toBinaryString(val));
		while(val != 0) {
			valp = (valp << 1) + (val | ((val >>> 1) << 1));
			val >>>= 1;
		}
		System.out.println("val's binary is " + ((val & valp)!=val?"NOT ":"") + "a palindrome");
	}
	private static void testinsertBinaryNum(int N, int M, int i, int j)
	{
		System.out.println("insert " + Integer.toBinaryString(M) + " into " + Integer.toBinaryString(N) +
			" @pos " + i + " to " + j + " is " +  Integer.toBinaryString(insertBinaryNum(N, M, i, j)));
	}
	private static void testdoubleToBinaryString(double d)
	{
		System.out.println("doubleToBinary of %f " + d + " " + doubleToBinaryString(d));
	}
	private static void testlongestSeqFlip0To1(int val)
	{
		System.out.println("Longest sequence length for " + Integer.toBinaryString(val) + " is " + longestSeqFlip0To1(val));
	}
	private static void testnextLargerWithSameNumof1Bits(int val)
	{
		System.out.println("Next larger of " + Integer.toBinaryString(val) + " w same no. of 1s " +
			Integer.toBinaryString(nextLargerWithSameNumof1Bits(val)));
	}
	private static void testnextSmallerWithSameNumof1Bits(int val)
	{
		System.out.println("Next smaller of " + Integer.toBinaryString(val) + " w same no. of 1s " +
			Integer.toBinaryString(nextSmallerWithSameNumof1Bits(val)));
	}
	private static void testisPowerOf2(int val)
	{
		System.out.println(val + " is " + (isPowerOf2(val)?"":"NOT ") + "a power of 2");
	}
	private static void testflipBitsToConvert(int m, int n)
	{
		System.out.println("" + flipBitsToConvert(m, n) + " bits to flip to convert from " + m + " to " + n);
	}
	private static void testpairwiseBitSwap(int n)
	{
		System.out.println("B4 Swap: " + Integer.toBinaryString(n) + "\n" +
			"A4 Swap: " + Integer.toBinaryString(pairwiseBitSwap(n)));
	}
	private static void testonBits()
	{
		for(int i = 1; i < 65; i++) {
		//for(int i = 1; i < 264; i+=5) {
			System.out.println("onBits:\t" + i + "\t" + onBitsRec(i) + "\t" + onBitsIter(i) /*+ "\t" + onBits(i)*/);
		}
	}

	public static void main(String args[])
    {
		testPalindrome(-1);
		testinsertBinaryNum(0b10000000000, 0b10011, 2, 6);

		testlongestSeqFlip0To1(1775);
		testlongestSeqFlip0To1(1767);
		testlongestSeqFlip0To1(7);

		testnextLargerWithSameNumof1Bits(7);
		testnextLargerWithSameNumof1Bits(13);
		testnextLargerWithSameNumof1Bits(8);
		testnextLargerWithSameNumof1Bits(57);
		testnextLargerWithSameNumof1Bits(56);

		testnextSmallerWithSameNumof1Bits(7);
		testnextSmallerWithSameNumof1Bits(13);
		testnextSmallerWithSameNumof1Bits(8);
		testnextSmallerWithSameNumof1Bits(57);

		testisPowerOf2(-1);
		testisPowerOf2(1);
		testisPowerOf2(64);
		testisPowerOf2(65);
		testisPowerOf2(28);
		testisPowerOf2(512);

		testflipBitsToConvert(29, 15);
		testflipBitsToConvert(57, 2);

		testpairwiseBitSwap(-29);
		testpairwiseBitSwap(29);

		testdoubleToBinaryString(0.125);
		testdoubleToBinaryString(0.72);

		testonBits();

		System.out.println("No of bytes in int & long " + Integer.SIZE/8 + " " + Long.SIZE/8);
		System.out.println("fraction % 2.55%10 = " + (int)2.55%10);
	}
}
