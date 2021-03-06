package ctci;

import java.util.ArrayList;
import java.util.Arrays;

public class Ch5BitManipulation
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
	{ // 5.5
		return ((n & (n-1)) == 0);
	}
	private static void testisPowerOf2(int val)
	{
		System.out.println(val + " is " + (isPowerOf2(val)?"":"NOT ") + "a power of 2");
	}

	public static int flipBitsToConvert(int m, int n)
	{ // 5.6
		int val = m ^ n;
		int bitsToFlip = 0;
		for(int i = val; i > 0; i &= (i-1)) {
			bitsToFlip++;
		}
		return bitsToFlip;
	}
	private static void testflipBitsToConvert(int m, int n)
	{
		System.out.println("" + flipBitsToConvert(m, n) + " bits to flip to convert from " + m + " to " + n);
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
	private static void testpairwiseBitSwap(int n)
	{
		System.out.println("B4 Swap: " + Integer.toBinaryString(n) + "\n" +
			"A4 Swap: " + Integer.toBinaryString(pairwiseBitSwap(n)));
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

		System.out.println("No of bytes in int & long " + Integer.SIZE/8 + " " + Long.SIZE/8);
		System.out.println("fraction % 2.55%10 = " + (int)2.55%10);
	}
}
