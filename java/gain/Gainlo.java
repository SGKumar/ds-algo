package gain;

import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.lang.Math;
//import java.util.Comparable;

import llsq.Queue;
import tree.BinaryNode;
import tree.BinaryTree;
import llsq.LinkedList;
import llsq.ListNode;

public class Gainlo
{
	// http://blog.gainlo.co/index.php/2016/04/12/find-the-longest-substring-with-k-unique-characters/
	// FB - Find the longest Substring with k unique characters

	// http://blog.gainlo.co/index.php/2016/04/15/print-all-paths-of-a-binary-tree/
	// FB - Print all Paths of a Binary Tree
	// Done in paper Verified in interviewbit.

	// http://blog.gainlo.co/index.php/2016/04/08/if-a-string-contains-an-anagram-of-another-string/
	// Uber - If a String contains an anagram of another String
	
	// http://blog.gainlo.co/index.php/2016/04/26/deepest-node-in-a-tree/
	// Uber - Deepest Node in a tree/
	// Last node in a level order search

	// http://blog.gainlo.co/index.php/2016/04/29/minimum-number-of-deletions-of-a-string/
	// FB - Minimum Number of Deletions of a String
	// Since it is just deletions we know the String has to be "in order"
	// Make all possible combinations of string (2^N for string of length N), check starting from the longest
	// if any of them is present in the dictionary.
		
	// http://blog.gainlo.co/index.php/2016/05/06/group-anagrams/
	// FB, Uber - Group Anagrams
	public static Comparator<String> StringComparator = new Comparator<String>()
	{
		public int compare(String s1, String s2)
		{
			return sortString(s1).compareTo(sortString(s2));
		}
	};
	private static String sortString(String s)
	{
		char[] cs = s.toCharArray();
		Arrays.sort(cs);
		return new String(cs);
	}
	public static String groupAnagrams(String[] strs)
	{
		HashMap<String, ArrayList<String>> strMap = new HashMap<>();
		for(String str : strs) {
			String s = sortString(str);
			if(!strMap.containsKey(s)) {
				strMap.put(s, new ArrayList<String>());
			}
			strMap.get(s).add(str);
		}
		
		StringBuilder sb = new StringBuilder();
		for(Map.Entry<String, ArrayList<String>> entry : strMap.entrySet()) {
			sb.append(entry.getValue().toString());
		}
		return sb.toString();
	}
	
	// http://blog.gainlo.co/index.php/2016/05/10/duplicate-elements-of-an-array/
	// FB - Duplicate Elements of an Array
	// Simple descriptive problem about external merge sort and pivot... (like qsort)
	
	// http://blog.gainlo.co/index.php/2016/06/01/subarray-with-given-sum/
	// FB - Subarray with Given Sum
	// Given an array of non-negative numbers, find continuous subarray with sum to S.
	public static int[] subArraySum(int arr[], int val)
	{
		if(arr == null || val < 0 || arr.length == 0) return new int[] {};

		int left = 0, right = 0, len = arr.length, sum = arr[0];
		while(left <= right && right < len) {
			if(sum == val) {
				return new int[] {left, right};
			}
			if(sum < val) {
				right++;
				if(right < len) sum += arr[right];
			}
			else {
				if(left < len) sum -= arr[left];
				left++;
			}
		}
		return new int[] {};
	}
	// Follow-up, Given an array of numbers, find subarray with sum to S. DP problem there already
	
	// http://blog.gainlo.co/index.php/2016/06/03/second-largest-element-of-a-binary-search-tree/
	// FB - Second Largest Element of a Binary Search Tree
	// Coded a reverse inorder tranversal - get items in descending order choose 2nd.
	
	// http://blog.gainlo.co/index.php/2016/06/12/flatten-a-linked-list/
	// FB - Flatten a Linked List
	// Coded on Paper (PIE has solution and unflattening too)
	public static ListNode flatten(ListNode node)  {
		ListNode slow = node, fast = node;
		while(slow.child != null || fast.next != null) {
			while(slow.next != null && slow.child == null) {
				slow = slow.next;
			}
			while(fast.next != null) {
				fast = fast.next;
			}
			if(slow.child != null) {
				fast.next = slow.child;
				slow = slow.next;
			}
		}
		return node;
	}
	public static ListNode flatten2(ListNode node)  {
		ListNode n = node, tail = node;
		while(n != null) {
			while(tail.next != null) {
				tail = tail.next;
			}
			if(n.child != null) {
				tail.next = n.child;
				n = n.next;
			}
		}
		return node;
	}
	private static void testflatten() {
		ListNode fst = LinkedList.small2DList1();
		System.out.println("List: " + LinkedList.toStringSimple(fst) + " " + LinkedList.toStringChild(fst));
		System.out.println("  After flatten: " + LinkedList.toStringSimple(flatten(fst)));
		System.out.println("  After flaten2: " + LinkedList.toStringSimple(flatten(LinkedList.small2DList1())));
		fst = LinkedList.small2DList2();
		System.out.println("List: " + LinkedList.toStringSimple(fst) + " " + LinkedList.toStringChild(fst));
		System.out.println("  After flatten: " + LinkedList.toStringSimple(flatten(fst)));
		System.out.println("  After flaten2: " + LinkedList.toStringSimple(flatten(LinkedList.small2DList2())));
	}
	
	// http://blog.gainlo.co/index.php/2016/07/06/lowest-common-ancestor/
	// FB - Lowest Common Ancestor
	// Solved earlier and now in paper DONE
	
	// http://blog.gainlo.co/index.php/2016/07/12/meeting-room-scheduling-problem/
	// FB - Meeting Room Scheduling Problem
	// Given a list of meeting times, checks if any of them overlaps. The follow-up question is to 
	// return the minimum number of rooms required to accommodate all the meetings.
	// ### TODO To-do
	
	// http://blog.gainlo.co/index.php/2016/07/19/3sum/
	// FB - 3Sum
	// determine if any 3 integers in an array sum to 0
	// ### TODO To-do
	public static boolean find3Sum0(int[] arr) {
		Arrays.sort(arr);
		for(int i = 0; i < arr.length-2; i++) {
			int sum = 0-arr[i];
			int left = i+1, right = arr.length-1;
			while(left < right) {
				if(arr[left] + arr[right] == sum) {
					return true;
				}
				if(arr[left] + arr[right] > sum) {
					right--;
				}
				else {
					left++;
				}
			}
		}
		return false;
	}
	// find 3 integers in an array whose sum is *closest* to 0
	public static int[] find3SumClosest0(int[] arr) {
		Arrays.sort(arr);
		int prevSum = Integer.MAX_VALUE, retval[] = {};
		for(int i = 0; i < arr.length-2; i++) {
			int left = i+1, right = arr.length-1;
			while(left < right) {
				int sum = arr[left] + arr[right] + arr[i];
				if(Math.abs(sum) < prevSum) {
					prevSum = sum;
					retval = new int[] {arr[i], arr[left], arr[right]};
				}
				if(arr[left] + arr[right] == sum) {
					break;
				}
				if(arr[left] + arr[right] > sum) {
					right--;
				}
				else {
					left++;
				}
			}
		}
		return retval;
	}
	// determine if any 3 integers in an array sum to 0. Each no. can be used multiple times
	public static boolean find3Sum0Repeats(int[] arr) {
		Arrays.sort(arr);
		for(int i = 0; i < arr.length-2; i++) {
			int sum = 0-arr[i];
			int left = i, right = arr.length-1;
			while(left < right) {
				if(arr[left] + arr[right] == sum) {
					return true;
				}
				if(arr[left] + arr[right] > sum) {
					right--;
				}
				else {
					left++;
				}
			}
		}
		return false;
	}
	// find 3 integers in an array whose sum is *closest* to 0. Each no. can be used multiple times
	public static int[] find3SumClosest0Repeats(int[] arr) {
		Arrays.sort(arr);
		int prevSum = Integer.MAX_VALUE, retval[] = {};
		for(int i = 0; i < arr.length; i++) {
			int left = i, right = arr.length-1;
			while(left <= right) {
				int sum = arr[left] + arr[right] + arr[i];
				if(Math.abs(sum) < prevSum) {
					prevSum = sum;
					retval = new int[] {arr[i], arr[left], arr[right]};
				}
				if(arr[left] + arr[right] == sum) {
					break;
				}
				if(arr[left] + arr[right] > sum) {
					right--;
				}
				else {
					left++;
				}
			}
		}
		return retval;
	}
	public static void testfind3Sum0()
	{
		int[] arr = new int[] {4, 3, -1, 2, -2, 10};
		test3Sum0(arr);

		arr = new int[] {-4, 4};
		test3Sum0(arr);

		arr = new int[] {-4, 4, 5, 6, 1, -3, 9};
		test3Sum0(arr);

		arr = new int[] {4, 3, -1, 2, 5, 10};
		test3Sum0(arr);

		arr = new int[] {0, 4, 3, 1, 2, 5, 10};
		test3Sum0(arr);
		
		arr = new int[] {-4, 3, -3, 4, 2, 5, 10};
		test3Sum0(arr);
	}
	public static void test3Sum0(int[] arr) {
		System.out.println("3Sum unique == 0 " + Arrays.toString(arr) + " " + find3Sum0(arr));
		System.out.println(" uniq closest to 0 " + Arrays.toString(arr) + " " + Arrays.toString(find3SumClosest0(arr)));
		System.out.println(" repeats    == 0 " + Arrays.toString(arr) + " " + find3Sum0Repeats(arr));
		System.out.println("repts closest to 0 " + Arrays.toString(arr) + " " + Arrays.toString(find3SumClosest0Repeats(arr)));
	}

	// http://blog.gainlo.co/index.php/2016/10/07/facebook-interview-longest-substring-without-repeating-characters/
	// FB - Longest Substring without repeating characters
	// ### TODO To-do
	public static String longest(String str)
	{
		int start = 0, end = 0, maxStart = 0, maxEnd = 0;

		HashMap<Character, Integer> chars = new HashMap<>();
		for(int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if(chars.containsKey(ch)) {
				int prevIndex = chars.get(ch);
				// Was prevIndex part of this string ?
				if(prevIndex >= start) {
					if(maxEnd - maxStart < end - start) {
						maxStart = start;
						maxEnd = end; 
					}
					start = prevIndex+1;
				}
			}
			chars.put(ch, i);
			end = i;
		}
		if(maxEnd - maxStart < end - start) {
			return str.substring(start, end + 1); // String quirk for substr end....
		}
		else {
			return str.substring(maxStart, maxEnd + 1); // String quirk for substr end....
		}
		/*		
		int maxLen = 0, max = 0, n = A.length();
		HashMap<Character, Integer> hashMap = new HashMap<>();
	    
	    for (int i = 0; i < n; i++) {
	        
	        char c = A.charAt(i);
	        if (hashMap.containsKey(c)) {
	            int prevIndex = hashMap.get(c);
	            count = Math.min(count + 1, i - prevIndex);
	        } else {
	            count++;
	        }
            hashMap.put(c, i);
	        
	        max = Math.max(max, count);
	    }
	    return max;
		*/
	}
	
	// http://blog.gainlo.co/index.php/2016/08/14/uber-interview-question-map-implementation/
	// Uber - Map Implementation
	
	// http://blog.gainlo.co/index.php/2016/09/30/uber-interview-question-delimiter-matching/
	// Uber - Delimiter Matching
	
	// http://blog.gainlo.co/index.php/2016/11/11/uber-interview-question-weighted-random-numbers/
	// Uber - Weighted Random Numbers
	
	// http://blog.gainlo.co/index.php/2016/11/18/uber-interview-question-move-zeroes/
	// Uber - Move zeroes in array to end
	public static void moveZeros(int[] arr)
	{
		int left = 0, right = arr.length - 1;
		while(left < right) {
			while(left < right && arr[left] != 0) left++;
			while(left < right && arr[right] == 0) right--;
			if(left < right) {
				arr[left] = arr[right];
				arr[right] = 0;
			}
		}
	}
	public static void moveZeros2(int[] arr)
	{
		int nPos = 0;
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] != 0) {
				if(nPos != i) {
					arr[nPos] = arr[i];
					arr[i] = 0;
				}
				nPos++;
			}
		}
	}
	
	// http://blog.gainlo.co/index.php/2016/12/02/uber-interview-question-maximum-sum-non-adjacent-elements/
	// Uber - Maximum sum of non-adjacent elements
	// Already solved in DynProg2.java/maxsumNo2Adj
	
	// http://blog.gainlo.co/index.php/2016/12/23/uber-interview-questions-permutations-parentheses/
	// Uber - Permutations of Parantheses
	public static ArrayList<String> parens(int n)
	{
		ArrayList<String> res = new ArrayList<>();
		if(n > 0) {
			parens(2*n, n, n, res, new char[2*n]);
		}
		return res;
	}
	private static void parens(int num, int left, int right, ArrayList<String> res, char[] cs)
	{
		if(left == 0 && right == 0) {
			res.add(new String(cs));
			return;
		}
		int pos = num - left - right;
		if(left > 0) {
			cs[pos] = '(';
			parens(num, left-1, right, res, cs);
		}
		if(right > left) {
			cs[pos] = ')';
			parens(num, left, right-1, res, cs);
		}
	}

	public static ArrayList<String> parens2(int n)
	{
		ArrayList<String> res = new ArrayList<>();
		if(n > 0) {
			parens2(n, 0, res, "");
		}
		return res;
	}
	private static void parens2(int left, int right, ArrayList<String> res, String prefix)
	{
		if(left == 0 && right == 0) {
			res.add(prefix);
		}
		if(left > 0) {
			parens2(left-1, right+1, res, prefix + "(");
		}
		if(right > 0) {
			parens2(left, right-1, res, prefix + ")");
		}
	}
	
	// http://blog.gainlo.co/index.php/2017/01/05/uber-interview-questions-permutations-array-arrays/
	// Uber - Permutations of an array of arrays
	
	// http://blog.gainlo.co/index.php/2017/01/12/rotated-array-binary-search/
	// Uber – Search in sorted and rotated array
	// Already solved in CTCICh10SortSearch.java

	// http://blog.gainlo.co/index.php/2017/01/20/arrange-given-numbers-to-form-the-biggest-number-possible/
	// Uber - Arrange Given Numbers To Form The Biggest Number Possible
	public static int biggest(int num)
	{
		if(num == 0) return -1;
		boolean neg = false;
		if(num < 0) {
			neg = true;
			num = -num;
		}
		ArrayList<Integer> digits = new ArrayList<>();
		while(num > 0) {
			digits.add(num%10);
			num /= 10;
		}

		if(neg) {
			Collections.sort(digits);
		}
		else {
			Collections.sort(digits, Collections.reverseOrder());
		}

		int ret = 0;
		for(int digit : digits) {
			ret = ret*10 + digit;
		}
		return neg?-ret:ret;
	}
	
	private static boolean compare(int v1, int v2, boolean reverseOrder)
	{
		return reverseOrder?(v1 > v2):(v1 < v2);
	}
	// check if no is from R to L constants increasing/decreasing
	private static boolean sortedDigits(int num, boolean reverseOrder)
	{
		int val = num, prevDigit = 0;
		int currDigit = val%10;
		boolean sorted = true;
		while(val > 0 && sorted) {
			prevDigit = currDigit;
			currDigit = val%10;
			val /= 10;

			sorted = !compare(currDigit, prevDigit, reverseOrder);;
		}
		return sorted;
	}
	private static int nextInOrder(ArrayList<Integer> digits, int val, boolean reverseOrder)
	{
		for(int i = 0; i < digits.size(); i++) {
			if(compare(digits.get(i), val, !reverseOrder)) {
				int ret = digits.get(i);
				digits.remove(i);
				digits.add(i, val);
				return ret;
			}
		}
		// This should NEVER happen
		return -1;
	}
	// Uber - Arrange Given Numbers To Form The Next Bigger Number Possible
	public static int next(int num)
	{
		if(num >= 0 && num <= 11) return num;
		boolean neg = false;
		if(num < 0) {
			neg = true;
			num = -num;
		}
		if(sortedDigits(num, neg)) {
			return neg?-num:num; // Already MAX
		}

		int val = num, currDigit = val%10, prevDigit = 0;
		ArrayList<Integer> digits = new ArrayList<>();
		while(val > 0) {
			prevDigit = currDigit;
			currDigit = val%10;
			val /= 10;

			if(compare(currDigit, prevDigit, neg)) break;
			digits.add(currDigit);
		}

		if(neg) {
			Collections.sort(digits, Collections.reverseOrder());
		}
		else {
			Collections.sort(digits);
		}
		int ndx = nextInOrder(digits, currDigit, neg);
		val = val * 10 + ndx;

		for(int digit : digits) {
			val = val*10 + digit;
		}
		return neg?-val:val;
	}

	// http://blog.gainlo.co/index.php/2017/02/02/uber-interview-questions-longest-increasing-subarray/
	// Uber - Given an array, return the length of the longest increasing subarray.
	// also - return longest increasing subsequence...
	public static int[] lisubArray(int[] arr)
	{
		int start = 0, end = 0, maxStart = 0, maxEnd = 0;
		for(int i = 1; i < arr.length; i++) {
			if(arr[i] <= arr[i-1]) {
				if(maxEnd - maxStart < end - start) {
					maxStart = start;
					maxEnd = end;
				}
				start = i;
			}
			end = i;
		}
		int[] res = new int[1 + Math.max(maxEnd - maxStart, end - start)];
		if(maxEnd - maxStart > end - start) {
			for(int i = maxStart; i <= maxEnd; i++)
				res[i-maxStart] = arr[i];
		}
		else {
			for(int i = start; i <= end; i++)
				res[i-start] = arr[i];
		}
		return res;
	}

	private static void testlisubArray()
	{
		int[] arr = new int[] {1, 3, 2, 3, 4, 8, 7, 9};
		System.out.println("LISub-Arr: " + Arrays.toString(lisubArray(arr)) + " for " + Arrays.toString(arr));
		arr = new int[] {1, 2, 3, 4, 8, 7, 9, 1};
		System.out.println("LISub-Arr: " + Arrays.toString(lisubArray(arr)) + " for " + Arrays.toString(arr));
	}
	public static void testBinaryTreePaths()
	{
		BinaryNode root = BinaryTree.smallTree();
		if(root == null) return;
		printBTPaths(root, "");

		root = BinaryTree.complexTree();
		if(root == null) return;
		printBTPaths(root, "");
	}
	private static void printBTPaths(BinaryNode node, String path)
	{
		if(node.left() == null && node.right() == null) {
			System.out.print(path + node.value() + " ");
			return;
		}
		if(node.left() != null) {
			printBTPaths(node.left(), path + node.value());
		}
		if(node.right() != null) {
			printBTPaths(node.right(), path + node.value());
		}
	}
	
	private static void testbiggest()
	{
		System.out.println("biggest of 423865 is " + biggest(423865));
		System.out.println("biggest of 62832 is " + biggest(62832));
		System.out.println("biggest of -423865 is " + biggest(-423865));
		System.out.println("biggest of 865 is " + biggest(865));
		System.out.println("biggest of -865 is " + biggest(-865));
		System.out.println("biggest of -568 is " + biggest(-568));
		System.out.println("biggest of 0 is " + biggest(0));
	}
	private static void testnextbigger()
	{
		System.out.println("nextbigger of " + 987 + " is " + next(987));
		System.out.println("nextbigger of " + 238642 + " is " + next(238642));

		System.out.println("nextbigger of " + -789 + " is " + next(-789));
		System.out.println("nextbigger of " + -987 + " is " + next(-987));
		System.out.println("nextbigger of " + -238642 + " is " + next(-238642));

	}
	private static void testmoveZeros()
	{
		int[] a = new int[] {1, 2, 0, 3, 0, 1, 2};
		System.out.print("1. B4: " + Arrays.toString(a));
		moveZeros(a);
		System.out.println(" A4: " + Arrays.toString(a));
		a = new int[] {1, 2, 0, 3, 0, 1, 2};
		System.out.print("2. B4: " + Arrays.toString(a));
		moveZeros2(a);
		System.out.println(" A4: " + Arrays.toString(a));

		a = new int[] {0, 0, 0, 1, 0, 2, 0};
		System.out.print("B4: " + Arrays.toString(a));
		moveZeros(a);
		System.out.println(" A4: " + Arrays.toString(a));
		a = new int[] {0, 0, 0, 1, 0, 2, 0};
		System.out.print("B4: " + Arrays.toString(a));
		moveZeros2(a);
		System.out.println(" A4: " + Arrays.toString(a));

		a = new int[] {1, 2};
		System.out.print("B4: " + Arrays.toString(a));
		moveZeros(a);
		System.out.println(" A4: " + Arrays.toString(a));
		a = new int[] {1, 2};
		System.out.print("B4: " + Arrays.toString(a));
		moveZeros2(a);
		System.out.println(" A4: " + Arrays.toString(a));

		a = new int[] {0, 0};
		System.out.print("B4: " + Arrays.toString(a));
		moveZeros(a);
		System.out.println(" A4: " + Arrays.toString(a));
		a = new int[] {0, 0};
		System.out.print("B4: " + Arrays.toString(a));
		moveZeros2(a);
		System.out.println(" A4: " + Arrays.toString(a));

		a = new int[] {1, 2, 3, 4, 0, 0};
		System.out.print("B4: " + Arrays.toString(a));
		moveZeros(a);
		System.out.println(" A4: " + Arrays.toString(a));
		a = new int[] {1, 2, 3, 4, 0, 0};
		System.out.print("B4: " + Arrays.toString(a));
		moveZeros2(a);
		System.out.println(" A4: " + Arrays.toString(a));

	}
	private static void testlusubstr()
	{
		System.out.println("abccdefgh" + " " + longest("abccdefgh"));
		System.out.println("abcc" + " " + longest("abcc"));
		System.out.println("abcxyzcdefgh" + " " + longest("abcxyzcdefgh"));
		System.out.println("cabccyzdefgh" + " " + longest("cabccyzdefgh"));
		System.out.println("cabcxcxyzdefgh" + " " + longest("cabcxcxyzdefgh"));
		System.out.println("abcadbef" + " " + longest("abcadbef"));
		System.out.println("dadbc" + " " + longest("dadbc"));
		System.out.println("abac" + " " + longest("abac"));
		System.out.println("aaaaa" + " " + longest("aaaaa"));
		System.out.println("abcdbxyzapqrsta" + " " + longest("abcdbxyzapqrsta"));
		System.out.println("abcabbacdefgh" + " " + longest("abcabbacdefgh"));
		String str = "Wnb9z9dMc7E8v1RTUaZPoDNIAXRlzkqLaa97KMWLzbitaCkRpiE4J4hJWhRcGnC8H6mwasgDfZ76VKdXhvEYmYrZY4Cfmf4HoSlchYWFEb1xllGKyEEmZOLPh1V6RuM7Mxd7xK72aNrWS4MEaUmgEn7L4rW3o14Nq9l2EN4HH6uJWljI8a5irvuODHY7A7ku4PJY2anSWnfJJE1w8p12Ks3oZRxAF3atqGBlzVQ0gltOwYmeynttUmQ4QBDLDrS4zn4VRZLosOITo4JlIqPD6t4NjhHThOjJxpMp9fICkrgJeGiDAwsb8a3I7Txz5BBKV9bEfMsKNhCuY3W0ZHqY0MhBfz1CbYCzwZZdM4p65ppP9s5QJcfjadmMMi26JKz0TVVwvNA8LP5Vi1QsxId4SI19jfcUH97wmZu0pbw1zFtyJ8GAp5yjjQTzFIboC1iRzklnOJzJld9TMaxqvBNBJKIyDjWrdfLOY8FGMOcPhfJ97Dph35zfxYyUf4DIqFi94lm9J0skYqGz9JT0kiAABQZDazZcNi80dSSdveSl6h3dJjHmlK8qHIlDsqFd5FMhlEirax8WA0v3NDPT8vPhwKpxcnVeu14Gcxr3h1wAXXV0y7Xy9qqB2NQ5HQLJ7cyXAckEYHsLCPSy28xcdNJatx1KLWohOQado4WywJbGvsFR17rKmvOPABweXnFD3odrbSMD4Na4nuBBswvMmFRTUOcf7jZi4z5JnJqXz6hitaPnaEtjoSEBq82a52nvqYy7hhldBoxen2et2OMadVEHeTYLL7GLsIhTP6UizHIuzcJMljo4lFgW5AyrfUlIBPAlhwaSiJtTvcbVZynDSM6RO1PqFKWKg2MHIgNhjuzENg2oFCfW7z5KJvEL9qWqKzZNc0o3BMRjS04NCHFvhtsteQoQRgz84XZBHBJRdekCdcVVXu9c01gYRAz7oIAxN3zKZb64EFKssfQ4HW971jv3H7x5E9dAszA0HrKTONyZDGYtHWt4QLhNsIs8mo4AIN7ecFKewyvGECAnaJpDn1MTTS4yTgZnm6N6qnmfjVt6ZU51F9BxH0jVG0kovTGSjTUkmb1mRTLQE5mTlVHcEz3yBOh4WiFFJjKJdi1HBIBaDL4r45HzaBvmYJPlWIomkqKEmQ4rLAbYG7C5rFfpMu8rHvjU7hP0JVvteGtaGn7mqeKsn7CgrJX1tb8t0ldaS3iUy8SEKAo5IZHNKOfEaij3nI4oRVzeVOZsH91pMsA4jRYgEohubPW8ciXwVrFi1qEWjvB8gfalyP60n1fHyjsiLW0T5uY1JzQWHKCbLVh7QFoJFAEV0L516XmzIo556yRH1vhPnceOCjebqgsmO78AQ8Ir2d4pHFFHAGB9lESn3OtJye1Lcyq9D6X93UakA3JKVKEt6JZDLVBMp4msOefkPKSw59Uix9d9kOQm8WCepJTangdNSOKaxblZDNJ5eHvEroYacBhd9UdafEitdF3nfStF7AhkSfQVC61YWWkKTNdx96OoJGTnxuqt4oFZNFtO7aMuN3IJAkw3m3kgZFRGyd3D3wweagNL9XlYtvZwejbjpkDOZz33C0jbEWaMEaUPw6BG49XqyQoUwtriguO0yvWyaJqD4ye3o0E46huKYAsdKAq6MLWMxF6tfyPVaoqOGd0eOBHbAF89XXmDd4AIkoFPXkAOW8hln5nXnIWP6RBbfEkPPbxoToMbV";
		System.out.println(longest(str));

	}
	public static void testsubArraySum()
	{
		int[] arr = new int[] {1, 4, 20, 3, 10, 5};
		System.out.println("Sum 33: " + Arrays.toString(subArraySum(arr, 33)) + " in " + Arrays.toString(arr));
		arr = new int[] {1, 4, 0, 0, 3, 10, 5};
		System.out.println("Sum 7: " + Arrays.toString(subArraySum(arr, 7)) + " in " + Arrays.toString(arr));
		arr = new int[] {15, 2, 4, 8, 9, 5, 10, 35};
		System.out.println("Sum 25: " + Arrays.toString(subArraySum(arr, 25)) + " in " + Arrays.toString(arr));
		arr = new int[] {15, 2, 4, 8, 9, 5, 10, 35};
		System.out.println("Sum 35: " + Arrays.toString(subArraySum(arr, 35)) + " in " + Arrays.toString(arr));
	}
	public static void testgroupAnagrams()
	{
		String[] ss = new String[] {"cat", "dog", "act", "door", "odor"};
		System.out.println("Group Anags of " + Arrays.toString(ss) + " is " + groupAnagrams(ss));
	}

	// Heights of people - codevillage.wordpress.com - 02/08
	public static class People implements Comparable<People>
	{
		int ht;
		int prev;
		public People(int height, int befores)
		{
			this.ht = height;
			this.prev = befores;
		}
		public int compareTo(People p)
		{
			/*if(this.ht == p.ht) {
				Integer.compare(this.prev, p.prev);
			}*/
			return Integer.compare(this.ht, p.ht);
		}
		public String toString()
		{
			return "(" + this.ht + "," + this.prev + ")";
		}
	}
	public static ArrayList<Integer> queue(int hts[], int prevs[])
	{
		ArrayList<People> ppls = new ArrayList<>();
		for(int i = 0; i < hts.length; i++) {
			ppls.add(new People(hts[i], prevs[i]));
		}
		Collections.sort(ppls, Collections.reverseOrder());
		
		ArrayList<Integer> res = new ArrayList<>();
		for(People ppl : ppls) {
			res.add(ppl.prev, ppl.ht);
		}

		return res;
	}
	private static void testqueue()
	{
		int[] hts = new int[] {5, 3, 2, 6, 1, 4};
		int[] prv = new int[] {0, 1, 2, 0, 3, 2};
		System.out.println(Arrays.toString(hts) + " " + Arrays.toString(prv) + " " + queue(hts, prv));

		hts = new int[] {6, 1, 11, 5, 10, 4};
		prv = new int[] {2, 4, 0, 1, 0, 0};
		System.out.println(Arrays.toString(hts) + " " + Arrays.toString(prv) + " " + queue(hts, prv));

		hts = new int[] {7, 4, 7, 5, 6, 5};
		prv = new int[] {0, 4, 1, 0, 1, 2};
		System.out.println(Arrays.toString(hts) + " " + Arrays.toString(prv) + " " + queue(hts, prv));

		hts = new int[] {33, 11, 22, 44, 55};
		prv = new int[] {0, 2, 1, 1, 0};
		System.out.println(Arrays.toString(hts) + " " + Arrays.toString(prv) + " " + queue(hts, prv));
	}
	
	public static ArrayList<Integer> twoSum(final List<Integer> a, int sum) {
	    int index1 = Integer.MAX_VALUE, index2 = Integer.MAX_VALUE;
	    int left = 0, right = left+1;

	    for(left = 0; left < Math.min(index1, a.size()-1); left++) {
	        for(right = left+1; right < Math.min(index2, a.size()); right++) {

			if(a.get(left) + a.get(right) == sum) {
					//System.out.println("2Sum: (" + (left+1) + "," + (right+1) + ") (" + a.get(left) + "," + a.get(right) + ")");
    	            if(right < index2 || (right == index2 && left < index1)) {
    	                index2 = right;
    	                index1 = left;
    	            }
					break;
    	        }
	        }
	    }
        if(index1 == Integer.MAX_VALUE /*|| index2 == Integer.MAX_VALUE*/) {
            return new ArrayList<Integer>();
        }
        else {
            Integer[] ints = new Integer[] {index1+1, index2+1};
            return new ArrayList<Integer>(Arrays.asList(ints));
        }
	}
	private static void testtwoSum()
	{
		Integer[] arr = new Integer[] { -5, 1, 4, -7, 10, -7, 0, 7, 3, 0,
								-2, -5, -3, -6, 4, -7, -8, 0, 4, 9,
								4, 1, -8, -6, -6, 0, -9, 5, 3, -9,
								-5, -9, 6, 3, 8, -10, 1, -2, 2, 1,
								-9, 2, -3, 9, 9, -10, 0, -9, -2, 7,
								0, -4, -3, 1, 6, -3 };
		System.out.println("2Sum: " + twoSum(new ArrayList<Integer>(Arrays.asList(arr)), -1).toString());
	}
	private static void testparens()
	{
		ArrayList<String> res;
		for(int i = 0; i <= 7; i++) {
			res = parens(i);
			System.out.println(i + " " + res.size() + " " + res.toString());
			res = parens2(i);
			System.out.println(i + " " + res.size() + " " + res.toString());
		}
	}

	public  static String shortestPal(String s)
    {
        String rev_s = new StringBuilder(s).reverse().toString();
        //use special character to avoid overlap
        String l = s + "#" + rev_s; 
         
        int[] p = new int[l.length()];
         
        //build KMP table.
        //i -> suffix boundary
        //j -> prefix boundary
         
         
        for(int i=1; i<l.length(); i++)
        {
            //update prefix boundary to previous match position
            int j = p[i-1];
             
            //move to the last prefix boundary match
            while(j>0 && l.charAt(i)!=l.charAt(j))
                j = p[j-1];
             
            //if prefix boundary matches suffix boundary,
            //increase prefix length 
            if(l.charAt(i) == l.charAt(j)) p[i] = j + 1;
        }
         
        return rev_s.substring(0, s.length() - p[l.length() - 1]) + s;
    }
	
	public static void main(String[] args)
	{
		testqueue();
		testflatten();
		System.out.println("abab" + " " + shortestPal("abab"));
		System.out.println("axbab" + " " + shortestPal("axbab"));
		testlisubArray();
		testbiggest();
		testnextbigger();
		testmoveZeros();
		testlusubstr();
		testfind3Sum0();
		testsubArraySum();
		//testgroupAnagrams();
		testBinaryTreePaths();
		testtwoSum();
		//testmaxPoints();
		//testparens();

	}
}
