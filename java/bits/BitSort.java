/* Copyright (C) 1999 Lucent Technologies */
/* From 'Programming Pearls' by Jon Bentley */

/* bitsort.c -- bitmap sort from Column 1
 *   Sort distinct integers in the range [0..N-1]
 */

package bits;
 
import java.util.Arrays;
import java.util.BitSet;

public class BitSort
{
	/*final int BITSPERWORD = 32;
	final int SHIFT = 5;
	final int MASK  = 0x1F;
	final int N = 10000000;
	int a[];
	//int a[1 + N/BITSPERWORD];

	void set(int i) {        a[i>>SHIFT] |=  (1<<(i & MASK)); }
	void clr(int i) {        a[i>>SHIFT] &= ~(1<<(i & MASK)); }
	int  test(int i){ return a[i>>SHIFT] &   (1<<(i & MASK)); }
	*/
	private static final int BYTE_SIZE = 8;
	private static final int SHORT_SIZE = 16;
	private static final int INT_SIZE = 32;
	private static final int LONG_SIZE = 64;

	public static void sortDistinctIntegers(int[] a, int min, int max)
	{
		// Create Bit-array
		int reqdBytes = (max-min) / LONG_SIZE + 1;
		long[] bitmap = new long[reqdBytes];

		// Fill Bit-array from input no.s
		for(int i = 0; i < a.length; i++)
			bitmap[(a[i]-min)/LONG_SIZE] |= 1L << ((a[i]-min) % LONG_SIZE);

		// Read Bit-array and fill a[] in sorted order
		int k = 0;
		for(int i = 0; i < reqdBytes; i++)
		{
			for(int j = 0; j < LONG_SIZE; j++)
			{
				if((bitmap[i] & (1L << j)) > 0)
				{
					a[k++] = i * LONG_SIZE + j + min;
				}
			}
		}
	}

	public static void sortDistinctIntegersBS(int[] a, int min, int max)
	{
		// Create Bit-array
		BitSet bits = new BitSet(max-min);

		// Fill Bit-array from input no.s
		for(int i = 0; i < a.length; i++)
			bits.set(a[i]-min, true);

		// Read Bit-array and fill a[] in sorted order
		for(int i= 0, k = 0; i < bits.length(); i++)
		{
			if(bits.get(i))
			{
				a[k++] = i + min;
			}
		}
	}


	public static void main(String[] args)
	{
		int i;
	/*	Replace above 2 lines with below 3 for word-parallel init
		int top = 1 + N/BITSPERWORD;
		for (i = 0; i < top; i++)
			a[i] = 0;
	 */
	 	int a[] = {102, 105, 104, 199, 123, 134, 110, 103, 107, 120, 111, 114, 112};
		System.out.println("B4 Sort: " + Arrays.toString(a));
		sortDistinctIntegersBS(a, 101, 200);
		System.out.println("A4 Sort: " + Arrays.toString(a));
	}
}