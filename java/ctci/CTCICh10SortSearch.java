package ctci;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Collection;

public class CTCICh10SortSearch
{
	static class Listy
	{
		public int elementAt(int i) { return -1;}
	}
	// BEGIN CTCI Cracking the Coding interview -->
	public static void mergeSortedArraysIntoFirst(int[] arr1, int sz1, int[] arr2)
	{
		int i = sz1-1, j = arr2.length-1, k = sz1+arr2.length-1;
		while(i >= 0 && j >= 0 && k >= 0) {
			if(arr1[i] >= arr2[j]) {
				arr1[k--] = arr1[i--];
			}
			else {
				arr1[k--] = arr2[j--];
			}
		}
	}
	
	static class HashMapList<T, I>
	{
		private HashMap<T, ArrayList<I>> map = new HashMap<>();
		
		public void put(T key, I item)
		{
			if(!map.containsKey(key)) {
				map.put(key, new ArrayList<I>());
			}
			map.get(key).add(item);
		}
		public Collection<ArrayList<I>> values()
		{
			return map.values();
		}
	}
	public static void groupAnagrams(String[] strings)
	{
		HashMapList<String, String> map = new HashMapList<>();
		for(String s : strings) {
			String s1 = sortChars(s);
			map.put(s1, s);
		}
		int index = 0;
		for(ArrayList<String> list : map.values()) {
			for(String s : list) {
				strings[index++] = s;
			}
		}
	}
	private static String sortChars(String s)
	{
		char[] c1 = s.toCharArray();
		Arrays.sort(c1);
		return new String(c1);
	}
	
	public static void sortPeakValley(int[] arr)
	{
		boolean peak = true, valley = false;
		for(int i = 1; i < arr.length; i++) {
			if(peak) {
				if(arr[i] > arr[i-1]) {
					swap(arr, i, i-1);
				}
			}
			else if(valley) {
				if(arr[i] < arr[i-1]) {
					swap(arr, i, i-1);
				}
			}
			peak = !peak; valley = !valley;
		}
	}
	private static void swap(int[] arr, int i, int j)
	{
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	// 3. Search in Rotated array
	public static int searchRotArray(int[] a, int val)
	{
		int left = 0, right = a.length-1, mid = 0;

		while(left <= right) {
			mid = left + (right - left)/2;
			if(val == a[mid]) return mid;

			if(a[mid] < a[right]) {
				if(val > a[mid] && val <= a[right]) {
					left = mid + 1;
				}
				else {
					right = mid - 1;
				}
			}
			else {
				if(val >= a[left] && val < a[mid]) {
					right = mid - 1;
				}
				else {
					left = mid + 1;
				}
			}
		}
		return -1;
	}

	// BEGIN CTCI Cracking the Coding interview -->
	public static int findInListy(Listy l, int x)
	{
		int val = 0, i = 0, exp = 0;
		do {
			val = l.elementAt(i);
			if(val == x) {
				return i;
			}
			i = (1 << exp);
			exp++;
		} while(val != -1 || val < x);
		return binarySearch(l, x, i >>> 1, i);
	}
	private static int binarySearch(Listy l, int x, int low, int hi)
	{
		int mid = 0;
		while(low <= hi) {
			mid = low + (hi - low)/2;
			if(l.elementAt(mid) == x) {
				return mid;
			}
			if(l.elementAt(mid) == -1 || l.elementAt(mid) > x) {
				hi = mid - 1;
			}
			else {
				low = mid + 1;
			}
		}
		return -1;
	}
	// END CTCI Cracking the Coding interview -->

	private static void testsearchRotArray()
	{
		int[] a = new int[] {15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 11};
		System.out.println("RotSearch 5 in " + Arrays.toString(a) + " " + searchRotArray(a, 5));


		a = new int[] {17, 22, 26, 27, 36, 38, 39, 1, 2, 4, 7, 10, 11};
		System.out.println("RotSearch 2 in " + Arrays.toString(a) + " " + searchRotArray(a, 2));
		System.out.println("RotSearch 22 in " + Arrays.toString(a) + " " + searchRotArray(a, 22));
		System.out.println("RotSearch 1 in " + Arrays.toString(a) + " " + searchRotArray(a, 1));
		System.out.println("RotSearch 39 in " + Arrays.toString(a) + " " + searchRotArray(a, 39));

		a = new int[] {22, 26, 27, 36, 38, 39, 1, 2, 4, 7, 10, 11};
		System.out.println("RotSearch 2 in " + Arrays.toString(a) + " " + searchRotArray(a, 2));
		System.out.println("RotSearch 22 in " + Arrays.toString(a) + " " + searchRotArray(a, 22));
		System.out.println("RotSearch 1 in " + Arrays.toString(a) + " " + searchRotArray(a, 1));
		System.out.println("RotSearch 39 in " + Arrays.toString(a) + " " + searchRotArray(a, 39));

		a = new int[] {22, 26, 27, 36, 38, 1, 2, 4, 7, 10, 11};
		System.out.println("RotSearch 2 in " + Arrays.toString(a) + " " + searchRotArray(a, 2));
		System.out.println("RotSearch 11 in " + Arrays.toString(a) + " " + searchRotArray(a, 11));
		System.out.println("RotSearch 22 in " + Arrays.toString(a) + " " + searchRotArray(a, 22));
		System.out.println("RotSearch 1 in " + Arrays.toString(a) + " " + searchRotArray(a, 1));
		System.out.println("RotSearch 39 in " + Arrays.toString(a) + " " + searchRotArray(a, 39));
		System.out.println("RotSearch 5 in " + Arrays.toString(a) + " " + searchRotArray(a, 5));

		a = new int[] {24, 2, 9, 18};
		System.out.println("RotSearch 2 in " + Arrays.toString(a) + " " + searchRotArray(a, 2));
		System.out.println("RotSearch 11 in " + Arrays.toString(a) + " " + searchRotArray(a, 11));
		System.out.println("RotSearch 22 in " + Arrays.toString(a) + " " + searchRotArray(a, 22));
		System.out.println("RotSearch 1 in " + Arrays.toString(a) + " " + searchRotArray(a, 1));
		System.out.println("RotSearch 19 in " + Arrays.toString(a) + " " + searchRotArray(a, 19));
		System.out.println("RotSearch 18 in " + Arrays.toString(a) + " " + searchRotArray(a, 18));

		a = new int[] {4, 8, 12};
		System.out.println("RotSearch 12 in " + Arrays.toString(a) + " " + searchRotArray(a, 12));
		System.out.println("RotSearch 5 in " + Arrays.toString(a) + " " + searchRotArray(a, 5));
		System.out.println("RotSearch 8 in " + Arrays.toString(a) + " " + searchRotArray(a, 8));
		System.out.println("RotSearch 22 in " + Arrays.toString(a) + " " + searchRotArray(a, 22));
		System.out.println("RotSearch 4 in " + Arrays.toString(a) + " " + searchRotArray(a, 4));

		a = new int[] {12, 4, 8};
		System.out.println("RotSearch 12 in " + Arrays.toString(a) + " " + searchRotArray(a, 12));
		System.out.println("RotSearch 5 in " + Arrays.toString(a) + " " + searchRotArray(a, 5));
		System.out.println("RotSearch 8 in " + Arrays.toString(a) + " " + searchRotArray(a, 8));
		System.out.println("RotSearch 22 in " + Arrays.toString(a) + " " + searchRotArray(a, 22));
		System.out.println("RotSearch 4 in " + Arrays.toString(a) + " " + searchRotArray(a, 4));

		a = new int[] {4, 12};
		System.out.println("RotSearch 12 in " + Arrays.toString(a) + " " + searchRotArray(a, 12));
		System.out.println("RotSearch 3 in " + Arrays.toString(a) + " " + searchRotArray(a, 3));
		System.out.println("RotSearch 22 in " + Arrays.toString(a) + " " + searchRotArray(a, 22));
		System.out.println("RotSearch 4 in " + Arrays.toString(a) + " " + searchRotArray(a, 4));

		a = new int[] {4, 2};
		System.out.println("RotSearch 2 in " + Arrays.toString(a) + " " + searchRotArray(a, 2));
		System.out.println("RotSearch 3 in " + Arrays.toString(a) + " " + searchRotArray(a, 3));
		System.out.println("RotSearch 22 in " + Arrays.toString(a) + " " + searchRotArray(a, 22));
		System.out.println("RotSearch 1 in " + Arrays.toString(a) + " " + searchRotArray(a, 1));

		a = new int[] {4};
		System.out.println("RotSearch 4 in " + Arrays.toString(a) + " " + searchRotArray(a, 4));
		System.out.println("RotSearch 3 in " + Arrays.toString(a) + " " + searchRotArray(a, 3));
		System.out.println("RotSearch 22 in " + Arrays.toString(a) + " " + searchRotArray(a, 22));
		System.out.println("RotSearch 1 in " + Arrays.toString(a) + " " + searchRotArray(a, 1));
		
	}
	private static void testgroupAnagrams()
	{
		String[] ss = {"mmachu", "x", "y", "chumma", "maumch", "nejama", "jamane", "ummach", "achumm"};
		System.out.println("B4 group: " + Arrays.toString(ss));
		groupAnagrams(ss);
		System.out.println("A4 group: " + Arrays.toString(ss));
	}
	private static void testsortPeakValley()
	{
		int[] arr = {5, 8, 6, 2, 3, 4, 6};
		System.out.print(Arrays.toString(arr));
		sortPeakValley(arr);
		System.out.println(" -> " + Arrays.toString(arr));
		
		arr = new int[] {5, 8, 9, 11, 12, 13, 14, 16};
		System.out.print(Arrays.toString(arr));
		sortPeakValley(arr);
		System.out.println(" -> " + Arrays.toString(arr));
		
		arr = new int[] {5, 8, 15, 20, 18, 14, 12};
		System.out.print(Arrays.toString(arr));
		sortPeakValley(arr);
		System.out.println(" -> " + Arrays.toString(arr));

		arr = new int[] {5, 8, 10, 9, 12, 11, 14, 13, 15};
		System.out.print(Arrays.toString(arr));
		sortPeakValley(arr);
		System.out.println(" -> " + Arrays.toString(arr));
		
		arr = new int[] {5, 8, 9, 7, 6};
		System.out.print(Arrays.toString(arr));
		sortPeakValley(arr);
		System.out.println(" -> " + Arrays.toString(arr));

		arr = new int[] {5, 3, 1, 2, 3};
		System.out.print(Arrays.toString(arr));
		sortPeakValley(arr);
		System.out.println(" -> " + Arrays.toString(arr));
		
		arr = new int[] {0, 1, 4, 7, 8, 9};
		System.out.print(Arrays.toString(arr));
		sortPeakValley(arr);
		System.out.println(" -> " + Arrays.toString(arr));
		
		arr = new int[] {9, 1, 0, 4, 8, 7};
		System.out.print(Arrays.toString(arr));
		sortPeakValley(arr);
		System.out.println(" -> " + Arrays.toString(arr));
	}
	public static void main(String[] args)
	{
		int[] vals = new int[20];
		int[] arr = new int[] {0, 1, 4, 7, 8, 9};
		for(int i = 0; i < 6; i++) {
			vals[i] = arr[i];
		}
		int[] vals1 = {2, 3, 5, 6, 10, 11, 12};
		System.out.println("Merge sort " + Arrays.toString(vals) + " " + Arrays.toString(vals1));
		mergeSortedArraysIntoFirst(vals, 10, vals1);
		System.out.println("  into " + Arrays.toString(vals));
		
		testgroupAnagrams();
		testsortPeakValley();
		testsearchRotArray();
	}
}