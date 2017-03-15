package sortsearch;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.BitSet;

public class Search2
{
	// move zeros in array to end
	public static void moveZeros(int[] arr) {
		int intpos = 0;
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] != 0) {
				if(i != intpos) {
					int temp = arr[i];
					arr[i] = arr[intpos];
					arr[intpos] = temp;
				}
				intpos++;
			}
		}
	}
	// #TODO Replace every element in integer array by greatest number on its right (java/ example)
	
	public static void Find2Dupes(int A[])
	{
		System.out.println("Array B4: " + Arrays.toString(A));
		for(int i = 0; i < A.length; i++)
		{
			A[(A[i]>0?A[i]:-1*A[i])-1] = -1 * A[((A[i]>0)?A[i]:-1*A[i])-1];
		}
		System.out.println("Array : " + Arrays.toString(A));
		System.out.print("Repeats are: ");
		for(int i = 0; i < A.length-2; i++)
		{
			if(A[i] < 0)
				A[i] = -1 * A[i];
			else
				System.out.print(i+1 + " ");
		}
		System.out.println("Array A4: " + Arrays.toString(A));
	}
	public static void Find2DupesXOR(int A[])
	{
		int xor = 0;
		for(int i = 1; i <= A.length-2; i++)
		{
			xor ^= i;
		}
		System.out.print("xor[1..n]: " + xor);
		for(int i = 0; i < A.length; i++)
		{
			xor ^= A[i];
		}
		System.out.print(" x^y: " + xor);
		int rightBitSet = xor & ~(xor-1);
		System.out.print(" Right Bit: " + rightBitSet);

		int x = 0, y = 0;
		for(int i = 0; i < A.length; i++)
		{
			if((A[i] & rightBitSet) > 0)
				x ^= A[i];
			else
				y ^= A[i];
		}
		for(int i = 1; i <= A.length-2; i++)
		{
			if((i & rightBitSet) > 0)
				x ^= i;
			else
				y ^= i;
		}
		System.out.print(" Repeats are x: " + x + " y: " + y);
		System.out.println(" Array: " + Arrays.toString(A));
	}

	public static void FindDupeMiss(int A[])
	{
		System.out.println("FindDupeMiss:: Array B4: " + Arrays.toString(A));
		for(int i = 0; i < A.length; i++)
		{
			A[((A[i]>0)?A[i]:-1*A[i])-1] = -1 * A[((A[i]>0)?A[i]:-1*A[i])-1];
		}
		System.out.println("Array : " + Arrays.toString(A));
		System.out.print("Repeat, Miss are: ");
		for(int i = 0; i < A.length; i++)
		{
			if(A[i] < 0)
				A[i] = -1 * A[i];
			else
				System.out.print(i+1 + " ");
		}
		System.out.println("Array A4: " + Arrays.toString(A));
	}
	public static void FindDupeMissExact(int A[])
	{
		System.out.println("FindDupeMissExact:: Array B4: " + Arrays.toString(A));
		int i = 0, x = 0;
		while(A[i] > 0)
		{
			x = A[i];
			A[i] *= -1;
			i = x - 1;
		}
		System.out.print("Repeat is: " + x);
		int xor = 0;
		for(i = 1; i <= A.length; i++)
		{
			if(A[i-1] < 0)
				A[i-1] = -1 * A[i-1];

			xor ^= i;
			xor ^= A[i-1];
		}
		System.out.println(" Miss is: " + (xor^x));
		System.out.println("Array : " + Arrays.toString(A));
	}
	public static void FindDupeMissXOR(int A[])
	{
		System.out.println("FindDupeMissXOR:: Array B4: " + Arrays.toString(A));
		int xor = 0;
		for(int i = 1; i <= A.length; i++)
		{
			xor ^= i;
			xor ^= A[i-1];
		}
		System.out.print(" x^y: " + xor);
		int rightBitSet = xor & ~(xor-1);
		System.out.print(" Right Bit: " + rightBitSet);

		int x = 0, y = 0;
		for(int i = 1; i <= A.length; i++)
		{
			if((A[i-1] & rightBitSet) > 0)
				x ^= A[i-1];
			else
				y ^= A[i-1];

			if((i & rightBitSet) > 0)
				x ^= i;
			else
				y ^= i;
		}
		System.out.print(" Repeat, Miss are x: " + x + " y: " + y);
		System.out.println(" Array: " + Arrays.toString(A));
	}

	// In an array where no.s increase then decrease OR decrease and then increase
	// find the point where direction changes.
	public static void DecIncBitonic()
	{
    	// Decreasing, then Increasing....
    	//int[] ints = {34, 32, 30, 24, 22, 20, 18, 16, 15, 13, 11, 41, 45, 46, 47, 49, 51, 53, 55, 56, 67, 72};
    	//int[] ints = {20, 41, 45, 46, 47, 49, 51, 53, 55, 56, 67, 72};
    	//int[] ints = {72, 67, 56, 55, 53, 51, 49, 47, 46, 45, 41, 20};
    	//int[] ints = {34, 32, 30, 24, 22, 20, 18, 17, 16, 15, 14, 13, 11, 10, 8, 7, 5, 4, 2, 1};
    	int[] ints = {41, 45, 46, 47, 49, 51, 53, 55, 56, 67, 72, 34, 32, 30, 24, 22, 20, 18, 16, 15, 13, 11, 10};
    	int size = ints.length;
    	int left = 0, right = size-1;
    	int index = (left+right)/2;

    	while(index > left && index < right)
   		{
    		//System.out.println(" Index: " + index + " left: " + left + " right: " + right);
    		if(ints[index] < ints[index-1] && ints[index] < ints[index+1])
    			break;

    		if(ints[index] < ints[index-1])
    			left = index;
    		else
    			right = index;
    		index = (left+right)/2;
   		}
   		if(index == left || index == right)
   			index = -1;
		System.out.print(" Array: " + Arrays.toString(ints));
    	System.out.println(" :: " + index);
	}

	private static int[] SeparateEvenOdd(int A[])
	{
		int even = 0, odd = A.length-1;
		while(true)
		{
			while(A[even]%2 == 0)
				even++;
			while(A[odd]%2 == 1)
				odd--;
			if(odd < even)
				break;
			else
			{
				A[even] ^= A[odd];
				A[odd] ^= A[even];
				A[even] ^= A[odd];
			}
		}
		return A;
	}

	public static int FindValueInRotArrayFast(int[] A, int val)
	{
		int mid, left = 0, right = A.length-1;

		while(right >= left)
		{
			mid = (left+right)/2;
    		System.out.println(" left: " + left + " right: " + right + " mid: " + mid + " A[i]: " + A[mid]);

			if(val == A[mid])
				return mid;

			if(A[mid] > A[left]) // left sorted
			{
				if(val >= A[left] && val < A[mid])
					right = mid-1;
				else
					left = mid+1;
			}
			else// if(A[mid] < A[right])
			{
				if(val <= A[right] && val > A[mid])
					left = mid+1;
				else
					right = mid-1;
			}
		}
		return -1;
	}
	public static int FindValueInRotArray(int[] ints, int val)
	{
    	int left, right;
    	int index = FindOffsetInRotArray(ints);

		//System.out.println(" Array: " + Arrays.toString(ints) + " Pivot: " + index);
		if(val > ints[index] || val < ints[index+1])
			return -1;
		else if(val < ints[ints.length-1])
		{
			left = index +1;
			right = ints.length-1;
		}
		else
		{
			left = 0;
			right = index;
		}

    	while(right >= left)
   		{
    		index = (left+right)/2;
    		System.out.println(" left: " + left + " right: " + right + " index: " + index + " A[i]: " + ints[index]);

    		if(val > ints[index])
   				left = index+1;
    		else if(val < ints[index])
   				right = index-1;
   			else
   				return index;
   		}
		return -1;
	}
	// Given a sorted array, rotated by k elements, find k
	private static int FindOffsetInRotArray(int A[])
	{
		int sz = A.length;
		int left = 0, right = A.length-1, index = 0;

		//System.out.println(" Array: " + Arrays.toString(A));
		while(A[right] < A[left])
		{
			index = (left + right)/2;
			if(A[index] > A[left])
				left = index;
			else
				right = index;
		}
		return index;
	}

	public static int Find1stDupeInSortedArrary(int A[], int val)
	{
    	int left = 0, right = A.length-1, mid;
		while(right >= left)
		{
			mid = (left+right)/2;
			System.out.println(" left: " + left + " right: " + right + " mid: " + mid + " A[i]: " + A[mid]);

			if(val > A[mid])
			{
				if(val == A[mid+1])
					return mid+1;
				else
					left = mid+1;
			}
			else if(val < A[mid])
				right = mid-1;
			else
				return mid;
		}
		return -1;
	}

	private static int Median(int A[], int start, int end)
	{
		// Includes both start and end, end > start
		if(((end-start)%2) == 0)
			return A[(end+start)/2];
		else
			return (A[(end+start)/2] + A[1+(end+start)/2])/2;
	}
	public static int FindMedianOf2nSortedArrays(int A1[], int A2[])
	{
		int l1 = 0, l2 = 0, r1 = A1.length-1, r2 = A2.length-1;

		int m1 = 0, m2 = 0;
		while(r1 - l1 > 1 && r2 - l2 > 1)
		{
			m1 = Median(A1, l1, r1);
			m2 = Median(A2, l2, r2);
			System.out.println("l1, r1, m1: " + l1 + " " + r1 + " " + m1 + " l2, r2, m2: " + l2 + " " + r2 + " " + m2);

			if(m1 == m2)
				return m1;
			if(m1 > m2)
			{
				r1 = (l1 + r1)/2;
				l2 = (r2+l2)/2;
			}
			else
			{
				l1 = (r1+l1)/2;
				r2 = (r2+l2)/2;
			}
		}
		System.out.println("l1, r1, m1: " + l1 + " " + r1 + " " + m1 + " l2, r2, m2: " + l2 + " " + r2 + " " + m2);
		return (((A1[l1] > A2[l2])?A1[l1]:A2[l2]) + ((A1[r1] > A2[r2])?A2[r2]:A1[r1]))/2;
	}
	public static int FindMedianof2nSortedArraysEx(int A1[], int A2[])
	{
		int l = 0, r = A2.length-1;
		int i = 0, j = 0;
		while(r >= l)
		{
			i = (l + r)/2;
			j = A2.length - i - 1;
			System.out.println("l, r, i, j, A1[i], A2[j], A2[j+1]: " + l + " " + r + " " + i + " " + j + " " + A1[i] + " " + A2[j] + " " + A2[j+1]);
			if(A1[i] >= A2[j] && A2[i] <= A2[j+1])
				break;
			if(A1[i] > A2[j+1])
				r = i - 1;
			else
				l = i + 1;
		}
		//System.out.println("i, j, A1[i], A2[j], A2[j+1]: " + i + " " + j + " " + A1[i] + " " + A2[j] + " " + A2[j+1]);
		return (A1[i] + A2[j])/2;
	}

	// From: http://www.geeksforgeeks.org/archives/2105
	/*public static int getXMedianIter(int ar1[], int ar2[])
	{
		int left = 0, right = ar1.length-1;
		int i = (left + right)/2, j = ar1.length - i - 1;

		while(ar1[i]
	}*/
	public static int getXMedian(int ar1[], int ar2[])
	{
		return getXMedianRec(ar1, ar2, 0, ar1.length-1, ar1.length);
	}
	public static int getXMedianRec(int ar1[], int ar2[], int left, int right, int n)
	{
		int i, j;
		System.out.println("ar1[0], ar2[0], left, right, n: " + ar1[0] + " " + ar2[0] + " " + left + " " + right + " " + n);

		/* We have reached at the end (left or right) of ar1[] */
		if(left > right)
			return getXMedianRec(ar2, ar1, 0, n-1, n);

		i = (left + right)/2;
		j = n - i - 1;  /* Index of ar2[] */

		/* Recursion terminates here.*/
		if (ar1[i] > ar2[j] && (j == n-1 || ar1[i] <= ar2[j+1]))
		{
			/*ar1[i] is decided as median 2, now select the median 1
			   (element just before ar1[i] in merged array) to get the
			   average of both*/
			if (i == 0 || ar2[j] > ar1[i-1])
				return (ar1[i] + ar2[j])/2;
			else
				return (ar1[i] + ar1[i-1])/2;
		}
		/*Search in left half of ar1[]*/
		else if (ar1[i] > ar2[j] && j != n-1 && ar1[i] > ar2[j+1])
			return getXMedianRec(ar1, ar2, left, i-1, n);

		/*Search in right half of ar1[]*/
		else /* ar1[i] is smaller than both ar2[j] and ar2[j+1]*/
			return getXMedianRec(ar1, ar2, i+1, right, n);
	}

	public static int MaxSumContiguousSubSeq(int A[])
	{
		//int[] ints = {-4, -2, 2, 3, -1, 8, -1, 10, -5, -8, -18, 22, -3, 4};
		int currSum = 0, maxSum = 0;
		for(int i = 0; i < A.length; i++)
		{
			currSum += A[i];
			if(currSum < 0)
				currSum = 0;
			if(currSum > maxSum)
				maxSum = currSum;
			//System.out.println("i, currSum, maxSum " + i + "\t" + currSum + "\t" + maxSum);
		}
		return maxSum;
	}

	// Find MIN no. NOT PRESENT in UNSORTED array of POSITIVE NO.S
	public static int FindMinInPositiveUnSortedArray(int A[])
	{
		int i = 0, val = 0, length = A.length-1;
		for(i = 1; i <= length; i++)
		{
			System.out.println("i: " + i + " " + Arrays.toString(A));
			if(A[i] <= length && A[i] != i)
			{
				val = A[A[i]];
				A[A[i]] = A[i];
				A[i] = val;

				if(val < i)
				{
					A[i] = A[val];
					A[val] = val;
				}
			}
		}
		for(i = 1; i <= length; i++)
			if(A[i] != i)
				break;
		return i;
	}

	// For all sorts print the pre-/post- sorted arrays
	// and the no. of swaps
	public static int[] arraycopy(int is[])
	{
		int isNew[] = new int[is.length];
		System.arraycopy(is, 0, isNew, 0, is.length);
		return isNew;
	}
	public static char[] arraycopy(char chs[])
	{
		char chNew[] = new char[chs.length];
		System.arraycopy(chs, 0, chNew, 0, chs.length);
		return chNew;
	}
	private static void testmovezeros() {
		int a[] = {0, 6, 9, 4, 15, 21, 17};
		System.out.print("move0: " + Arrays.toString(a));
		moveZeros(a);
		System.out.println(" after: " + Arrays.toString(a));

		a = new int[] {6, 9, 4, 15, 21, 17};
		System.out.print("move0: " + Arrays.toString(a));
		moveZeros(a);
		System.out.println(" after: " + Arrays.toString(a));

		a = new int[] {6, 9, 0, 4, 0, 15, 0, 21, 17};
		System.out.print("move0: " + Arrays.toString(a));
		moveZeros(a);
		System.out.println(" after: " + Arrays.toString(a));

		a = new int[] {6, 9, 4, 15, 21, 17, 0, 0, 0};
		System.out.print("move0: " + Arrays.toString(a));
		moveZeros(a);
		System.out.println(" after: " + Arrays.toString(a));

	}
    public static void main(String args[])
    {
		testmovezeros();
		char arr[] = {'Z', 'Y', 'X', 'W', 'V', 'U', 'T', 'S' , 'R', 'Q'};
		/*
		//int A[] = {1, 9, 2, 1, 8, 10, 5, 7, 6, 4, 3, 7};
		int A[] = {7, 9, 2, 4, 8, 7, 5, 1, 6, 4, 3, 10};
		//int A[] = {6, 2, 6, 5, 2, 4, 3, 1};
		Find2Dupes(arraycopy(A));
		Find2DupesXOR(arraycopy(A));
		*/
		int B[] = {7, 9, 2, 11, 8, 7, 5, 1, 6, 4, 12, 10};
		//FindDupeMiss(arraycopy(B));
		//FindDupeMissExact(arraycopy(B));
		FindDupeMissXOR(arraycopy(B));
		//DecIncBitonic();
		

		//int[] ints = {-4, -2, 2, 3, -1, 11, -1, 13, -5, -8, -18, 22, -3, 4};
		int[] ints = {-4, -2, 2, 3, -1, 8, -1, 10, -5, -8, -18, 22, -3, 4};
		System.out.println("MaxSumContiguousSubSeq: " + MaxSumContiguousSubSeq(ints));

		/*int[] ints = {20, 24, 27, 30, 33, 36, 38, 41, 45, 46, 47, 49, 51, 53, 55, 56, 67, 72, 1, 2, 4, 5, 7, 8, 10, 11, 13, 15, 16, 17, 19};

    	System.out.println("EO:: " + Arrays.toString(SeparateEvenOdd(arraycopy(ints))));

    	System.out.println("FindOffsetInRotArray (17): " +  FindOffsetInRotArray(ints));
    	int[] int1 = {20, 23, 24, 1, 2, 4, 5, 7, 8, 10, 11, 13, 15, 16};
		System.out.println("FindOffsetInRotArray (2): " +  FindOffsetInRotArray(int1));
    	int[] int2 = {20, 24, 27, 30, 33, 36, 38, 41, 45};
		System.out.println("FindOffsetInRotArray (0): " +  FindOffsetInRotArray(int2));
    	int[] int3 = {20, 24, 27, 30, 33, 36, 38, 40, 15};
		System.out.println("FindOffsetInRotArray (7): " +  FindOffsetInRotArray(int3));
		*/

		/*
		System.out.println(":: " + FindValueInRotArray(ints, 72));
		System.out.println(":: " + FindValueInRotArray(ints, 10));
		System.out.println(":: " + FindValueInRotArray(ints, 0));
		System.out.println(":: " + FindValueInRotArray(ints, 75));
		System.out.println(":: " + FindValueInRotArray(ints, 70));
		System.out.println(":: " + FindValueInRotArray(ints, 12));

		System.out.println(":: " + FindValueInRotArrayFast(ints, 72));
		System.out.println(":: " + FindValueInRotArrayFast(ints, 10));
		System.out.println(":: " + FindValueInRotArrayFast(ints, 0));
		System.out.println(":: " + FindValueInRotArrayFast(ints, 75));
		System.out.println(":: " + FindValueInRotArrayFast(ints, 70));
		System.out.println(":: " + FindValueInRotArrayFast(ints, 12));

		int[] dups = {1, 1, 2, 2, 3, 4, 4, 4, 6, 5, 5, 5, 5, 5, 6, 6, 6, 7, 7, 7, 7, 7, 8, 8, 8, 8, 9, 9, 10, 11, 12, 13, 13, 14, 15, 15};
		System.out.println("Find1stDupeInSortedArrary: " + Find1stDupeInSortedArrary(dups, 1));
		System.out.println("Find1stDupeInSortedArrary: " + Find1stDupeInSortedArrary(dups, 2));
		System.out.println("Find1stDupeInSortedArrary: " + Find1stDupeInSortedArrary(dups, 3));
		System.out.println("Find1stDupeInSortedArrary: " + Find1stDupeInSortedArrary(dups, 4));
		System.out.println("Find1stDupeInSortedArrary: " + Find1stDupeInSortedArrary(dups, 5));
		System.out.println("Find1stDupeInSortedArrary: " + Find1stDupeInSortedArrary(dups, 6));
		System.out.println("Find1stDupeInSortedArrary: " + Find1stDupeInSortedArrary(dups, 7));
		System.out.println("Find1stDupeInSortedArrary: " + Find1stDupeInSortedArrary(dups, 8));
		System.out.println("Find1stDupeInSortedArrary: " + Find1stDupeInSortedArrary(dups, 9));
		System.out.println("Find1stDupeInSortedArrary: " + Find1stDupeInSortedArrary(dups, 10));
		System.out.println("Find1stDupeInSortedArrary: " + Find1stDupeInSortedArrary(dups, 11));
		System.out.println("Find1stDupeInSortedArrary: " + Find1stDupeInSortedArrary(dups, 12));
		System.out.println("Find1stDupeInSortedArrary: " + Find1stDupeInSortedArrary(dups, 13));
		System.out.println("Find1stDupeInSortedArrary: " + Find1stDupeInSortedArrary(dups, 14));
		System.out.println("Find1stDupeInSortedArrary: " + Find1stDupeInSortedArrary(dups, 15));
		*/

		int A1[] = {1, 4, 7, 10, 13, 16, 20, 27, 33, 38, 45, 47, 51, 55, 67};
		int A2[] = {2, 15, 18, 21, 25, 29, 30, 32, 36, 41, 44, 49, 50, 52, 54};
		//System.out.println("FindMedianOf2nSortedArrays: " + FindMedianOf2nSortedArrays(A1, A2));
		System.out.println("FindMedianof2nSortedArraysEx: " + FindMedianof2nSortedArraysEx(A2, A1));
		System.out.println("getXMedian: " + getXMedian(A1, A2));

		int B1[] = {1, 12, 15, 26, 38};
		int B2[] = {2, 13, 17, 30, 45};
		//System.out.println("FindMedianOf2nSortedArrays: " + FindMedianOf2nSortedArrays(B1, B2));
		System.out.println("FindMedianof2nSortedArraysEx: " + FindMedianof2nSortedArraysEx(B1, B2));
		System.out.println("getXMedian: " + getXMedian(B1, B2));

		int C1[] = {1, 2, 12, 13, 15};
		int C2[] = {17, 26, 30, 38, 45};
		//System.out.println("FindMedianOf2nSortedArrays: " + FindMedianOf2nSortedArrays(B1, B2));
		//System.out.println("FindMedianof2nSortedArraysEx: " + FindMedianof2nSortedArraysEx(C1, C2));
		System.out.println("getXMedian: " + getXMedian(C1, C2));

		int Z1[] = {0, 25, 40, 5, 6, 10, 3, 1, 2};
		int Z2[] = {0, 6, 9, 4, 15, 21, 17};
		System.out.println("FindMinInPositiveUnSortedArray: " + FindMinInPositiveUnSortedArray(Z1));
		System.out.println("FindMinInPositiveUnSortedArray: " + FindMinInPositiveUnSortedArray(Z2));
		
    }
}

