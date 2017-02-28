import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collection;

public class Sorting
{
	// For all sorts print the pre-/post- sorted arrays
	// and the no. of swaps
	public static int[] getIntArrayCopy(int is[])
	{
		int isNew[] = new int[is.length];
		System.arraycopy(is, 0, isNew, 0, is.length);
		return isNew;
	}
	public static char[] getCharArrayCopy(char chs[])
	{
		char chNew[] = new char[chs.length];
		System.arraycopy(chs, 0, chNew, 0, chs.length);
		return chNew;
	}

	public static void BubbleSort(char chs[])
	{
		System.out.println("BubbleSort Old: " + Arrays.toString(chs));
		char temp;
		int comps = 0, swaps = 0;
		for(int i = 0; i < chs.length; i++)
		{
			for(int j = i+1; j < chs.length; j++)
			{
				comps++;
				if(chs[i] > chs[j])
				{
					temp = chs[i];
					chs[i] = chs[j];
					chs[j] = temp;
					swaps++;
				}
			}
		}
		System.out.print("Length " + chs.length + " Comps: " + comps + " Swaps: " + swaps);
		System.out.println(" New: " + Arrays.toString(chs));
	}
	public static void BubbleSortEx(char chs[])
	{
		System.out.println("BubbleSortEx Skip Swaps: " + Arrays.toString(chs));
		char temp;
		int comps = 0, swaps = 0;
		boolean swapped = true;
		for(int i = 0; i < chs.length && swapped; i++)
		{
			swapped = false;
			for(int j = i+1; j < chs.length; j++)
			{
				comps++;
				if(chs[i] > chs[j])
				{
					temp = chs[i];
					chs[i] = chs[j];
					chs[j] = temp;
					swaps++;
					swapped = true;
				}
			}
		}
		System.out.print("Length " + chs.length + " Comps: " + comps + " Swaps: " + swaps);
		System.out.println(" New: " + Arrays.toString(chs));
	}

	public static void SelectionSort(char chs[])
	{
		System.out.println("SelectionSort: " + Arrays.toString(chs));
		char temp;
		int min = 0, comps = 0, swaps = 0;
		for(int i = 0; i < chs.length; i++)
		{
			min = i;
			for(int j = i; j < chs.length; j++)
			{
				comps++;
				if(chs[i] > chs[j])
				{
					min = j;
				}
			}
			temp = chs[i];
			chs[i] = chs[min];
			chs[min] = temp;
			swaps++;
		}
		System.out.print("Length " + chs.length + " Comps: " + comps + " Swaps: " + swaps);
		System.out.println(" New: " + Arrays.toString(chs));
	}

	// Merge 2 sorted arrays - Iteration
	public static int[] mergeSortedArraysIter()
	{
    	int[] arr1 = {1, 3, 7, 11, 17, 29};
    	int[] arr2 = {2, 4, 6, 10, 12, 14, 15};

    	if(arr1 == null) return arr2;
		if(arr2 == null) return arr1;

    	int sz1 = arr1.length, sz2 = arr2.length;

		int[] arr = new int[sz1+sz2];
		int i1 = 0, i2 = 0, i = 0;

    	//System.out.println("Array1: " + Arrays.toString(arr1) + " Array2: " + Arrays.toString(arr2));
		while(i1 < sz1 && i2 < sz1)
		{
			if(arr1[i1] < arr2[i2])
				arr[i++] = arr1[i1++];
			else
				arr[i++] = arr2[i2++];
		}
		// End of list2
		while(i1 < sz1)
		{
			arr[i++] = arr1[i1++];
		}
		// End of list1
		while(i2 < sz2)
		{
			arr[i++] = arr2[i2++];
		}

		return arr;
	}

	// 0-based array
	public static void mergeSort(int[] arr)
	{
		mergeSort(arr, 0, arr.length-1);
	}
	private static void mergeSort(int[] arr, int left, int right)
	{
		if(left == right || left > right)
			return;
		if(right - left == 1)
		{
			if(arr[left] > arr[right])
			{
				int temp = arr[right];
				arr[right] = arr[left];
				arr[left] = temp;
			}
		}
		else
		{
			int mid = left + (right - left)/2;
			mergeSort(arr, left, mid);
			mergeSort(arr, mid+1, right);
			
			mergeSortedSubarrays(arr, left, mid, right);
		}
	}
	
	// indexes left and right are inclusive.
	private static void mergeSortedSubarrays(int[] arr, int left, int mid, int right)
	{
		// arr1 and arr2 aren't NULL
		int temp1[] = new int[mid-left+1];
		System.arraycopy(arr, left, temp1, 0, mid-left+1);

		int temp2[] = new int[right-mid];
		System.arraycopy(arr, mid+1, temp2, 0, right-mid);

		int i1 = 0, i2 = 0, i = left;
		
		while(i1 < temp1.length && i2 < temp2.length)
		{
			if(temp1[i1] < temp2[i2])
			{
				arr[i++] = temp1[i1++];
			}
			else
			{
				arr[i++] = temp2[i2++];
			}
		}
		// End of list2 or list1 only one of those will be here.
		while(i1 < temp1.length)
		{
			arr[i++] = temp1[i1++];
		}
		// End of list1
		while(i2 < temp2.length)
		{
			arr[i++] = temp2[i2++];
		}
	}

	// 0-based array
	public static void quickSort(int[] arr)
	{
		quickSort(arr, 0, arr.length-1);
	}
	private static void quickSort(int[] arr, int left, int right)
	{
		//System.out.println("qsort#: left " + left + " right " + right);
		if(left == right || left > right)
			return;
		if(right - left == 1)
		{
			//System.out.println("qsort@: left " + left + " right " + right);
			if(arr[left] > arr[right])
			{
				int temp = arr[right];
				arr[right] = arr[left];
				arr[left] = temp;
			}
		}
		else
		{
			System.out.println("qsort$: left " + left + " right " + right);
			int pivot = partition(arr, left, right);
			quickSort(arr, left, pivot-1);
			quickSort(arr, pivot+1, right);
		}
	}
	
	// indexes left and right are inclusive.
	private static int partition(int[] arr, int left, int right)
	{
		// arr1 and arr2 aren't NULL
		int pivot = right;
		// store pivot at the end
		int temp;

		int lows = left, highs = right-1;
		
		while(lows < highs)
		{
			while(lows < right && arr[lows] <= arr[right])
			{
				lows++;
			}
			while(highs > left && arr[highs] > arr[right])
			{
				highs--;
			}
			if(lows < highs)
			{
				temp = arr[lows];
				arr[lows] = arr[highs];
				arr[highs] = temp;
			}
		}

		temp = arr[lows];
		arr[lows] = arr[right];
		arr[right] = temp;
		
		return lows;
	}
	
	public static int[] countSort(int[] arr, int k)
	{
		// 0..k-l are the range of values in array.
		int[] count = new int[k];
		int[] retval = new int[arr.length];
		
		for(int i=0; i < arr.length; i++)
		{
			count[arr[i]]++;
		}
		int index = 0;
		for(int i=0; i < k; i++)
		{
			for(int j=0; j < count[i]; j++)
			{
				retval[index++] = i;
			}
		}
		return retval;
	}
	
	// BEGIN Amazon Bible, GeekforGeeks, leetcode  -->
	// END Amazon Bible, GeekforGeeks, leetcode  -->
	
    public static void main(String args[])
    {
		char arr[] = {'Z', 'Y', 'X', 'W', 'V', 'U', 'T', 'S' , 'R', 'Q'};
		BubbleSort(getCharArrayCopy(arr));
		BubbleSortEx(getCharArrayCopy(arr));
		SelectionSort(getCharArrayCopy(arr));

    	int[] arr1 = {38, 32, 4, 5, 30, 31, 18, 15, 28, 26, 27, 25, 20, 2};
		System.out.println("Before mergeSort: " + Arrays.toString(arr1));
		mergeSort(arr1);
		System.out.println("After mergeSort:  " + Arrays.toString(arr1));
		
    	int[] arr2 = {38, 32, 4, 5, 30, 31, 18, 15, 28, 26, 27, 25, 20, 2};
		System.out.println("Before quickSort: " + Arrays.toString(arr2));
		quickSort(arr2);
		System.out.println("After quickSort:  " + Arrays.toString(arr2));

    	int[] arr3 = {7, 2, 9, 0, 1, 2, 0, 9, 7, 4, 4, 6, 9, 1, 0, 9, 3, 2, 5, 9};
		System.out.println("Before countSort: " + Arrays.toString(arr3));
		int[] arr4 = countSort(arr3, 10);
		System.out.println("After countSort:  " + Arrays.toString(arr4));
		
	}
}



