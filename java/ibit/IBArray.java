package ibit;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.BitSet;

// InterviewBit questions
public class IBArray
{
	public static int[] maxset(int[] a)
	{
		long currSum = 0, maxSum = 0;
		int currBegin = 0, currEnd = 0, begin = 0, end = 0;

		for(int i = 0; i <= a.length; i++) {
			if((i < a.length) && (a[i] >= 0)) {
				if(currBegin == -1) {
					currBegin = i; 
				}
				currSum += a[i];
				currEnd = i;
			}
			else {
				if((currSum > maxSum) ||
				   ((currSum == maxSum) && ((currEnd - currBegin) > (end - begin)))) {
					   maxSum = currSum;
					   begin = currBegin;
					   end = currEnd;
				}
				currSum = 0;
				currBegin = -1;
			}
		}
		int[] ret = {};
		if(begin >= 0) {
			ret = new int[end - begin + 1];
			for(int i = begin, j = 0; i <= end; i++, j++) {
				ret[j] = a[i];
			}
		}
		return ret;
	}
	public static int[] maxset2(int[] a)
	{
		long currSum = 0, maxSum = 0;
		int currBegin = 0, currSize = 0, begin = 0, size = 0;

		for(int i = 0; i < a.length; i++) {
			if(a[i] >= 0) {
				if(currSum == 0) {
					currBegin = i; 
				}
				currSum += a[i];
				currSize++;
			}
			else {
				currSum = 0;
				currSize = 0;
			}
			if((currSum > maxSum) || ((currSum == maxSum) && (currSize > size))) {
				   maxSum = currSum;
				   begin = currBegin;
				   size = currSize;
			}
		}
		int[] ret = {};
		if(size > 0) {
			ret = new int[size];
			for(int i = 0; i < size; i++) {
				ret[i] = a[begin +i];
			}
		}
		return ret;
	}


	private static void testmaxset()
	{
		int[] m1 = new int[] {24115, -75629, -46517, 30105, 19451, -82188, 99505, 6752, -36716, 54438, -51501, 83871, 11137, -53177, 22294, -21609, -59745, 53635, -98142, 27968, -260, 41594, 16395, 19113, 71006, -97942, 42082, -30767, 85695, -73671};
		int[] maxs = maxset(m1);
		System.out.println(Arrays.toString(maxs));
		maxs = maxset2(m1);
		System.out.println(Arrays.toString(maxs));

		m1 = new int[] {1, 2, 3, 4, -1, 6, -7, 2, 4};
		maxs = maxset(m1);
		System.out.println(Arrays.toString(maxs));
		maxs = maxset2(m1);
		System.out.println(Arrays.toString(maxs));
		
		m1 = new int[] {-1, -2, -3, -4, -5, -6};
		maxs = maxset(m1);
		System.out.println(Arrays.toString(maxs));
		maxs = maxset2(m1);
		System.out.println(Arrays.toString(maxs));
		
		int[] m2 = new int[] {24831, 53057, 19790, -10679, 77006, -36098, 75319, -45223, -56760, -86126, -29506, 76770, 29094, 87844, 40534, -15394, 18738, 59590, -45159, -64947, 7217, -55539, 42385, -94885, 82420, -31968, -99915, 63534, -96011, 24152, 77295};
		maxs = maxset(m2);
		System.out.println(Arrays.toString(maxs));
		maxs = maxset2(m2);
		System.out.println(Arrays.toString(maxs));
	}

	//public int removeDuplicates(ArrayList<Integer> a)
	public static int removeDuplicates(int[] a)
	{
	    if(a == null) return -1;
	    if(a.length <= 1) return a.length;

	    int i = 0, j= i+1;
	    while(i < a.length && j < a.length) {
	        int x = a[i];
	        int y = a[j];
	        if(x != y) {
	            i++;
	            a[i] = y;
	        }
            j++;
	    }
	    return i;
	}

	public static String reverse(String s)
	{
		int i = 0, end = s.length(), begin = 0;
		StringBuilder str = new StringBuilder(end);
		for(i = s.length()-1; i >= -1; i--) {
			if(i == -1 || s.charAt(i) == ' ') {
				begin = i+1;
				str.append(s.substring(begin, end)).append(' ');
				end = i;
			}
		}
		return str.toString();
	}
	
	public static void main(String[] args)
	{
		testmaxset();
		int[] a = {0, 0, 1, 1, 2, 2, 3, 3};
		int ret = removeDuplicates(a);
		System.out.println("removedup : " + ret + " : " + Arrays.toString(a));
		System.out.println("reverse : I am a student. X " + reverse("I am a student."));
	}
}
