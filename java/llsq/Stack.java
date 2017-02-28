package llsq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.NoSuchElementException;

public class Stack<T>
{
	private static class StackNode<T>
	{
		private T data;
		private StackNode<T> next;

		public StackNode(T data) {
			this.data = data;
		}
	}

	private static class MyQueue<T>
	{
		private Stack<T> newStack;
		private Stack<T> oldStack;
		
		public MyQueue()
		{
			newStack = new Stack<>();
			oldStack = new Stack<>();
		}
		public int size()
		{
			return oldStack.size() + newStack.size();
		}
		public void add(T t)
		{
			newStack.push(t);
		}
		private void new2Old()
		{
			if(oldStack.isEmpty()) {
				while(!newStack.isEmpty()) {
					oldStack.push(newStack.pop());
				}
			}
		}
		public T peek()
		{
			new2Old();
			return oldStack.peek();
		}
		public T poll()
		{
			new2Old();
			return oldStack.pop();
		}
	}
	
	private StackNode<T> top;
	private int size;
	public int size()
	{
		return size;
	}
	public void push(T item)
	{
		StackNode<T> t = new StackNode<T>(item);
		t.next= top;
		top= t;
		size++;
	}
	public T pop()
	{
		if (top == null) throw new EmptyStackException();
		T item = top.data;
		top = top.next;
		size--;
		return item;
	}
	public T peek()
	{
		if (top == null) throw new EmptyStackException();
		return top.data;
	}
	public T top()
	{
		if (top == null) throw new EmptyStackException();
		return top.data;
	}
	public T min()
	{
		//return min;
		if (top == null) throw new EmptyStackException();
		return top.data;
	}

	public boolean isEmpty()
	{
		return top== null;
	}
	
	public boolean isFull()
	{
		return (size == 3);
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder(128);
		for(StackNode<T> node = top; node != null; node = node.next) {
			sb.append(node.data).append(" ");
		}
		return sb.toString();
	}

	// BEGIN Cracking the Coding Interview  -->
	public static void sort(Stack<Integer> s)
	{
		Stack<Integer> r = new Stack<>();
		while(!s.isEmpty()) {
			int val = s.pop();
			while(!r.isEmpty() && r.peek() >= val) {
				s.push(r.pop());
			}
			r.push(val);
		}
		// move all of r to s now.
		while(!r.isEmpty()) {
			s.push(r.pop());
		}
	}
	// END Cracking the Coding Interview  -->

	private static int[] calcSpanGanesh(int[] price)
	{
		int spans[] = new int[price.length];
		Stack<Integer> stack = new Stack<>();
		
		for(int i = 0; i < price.length; i++)
		{
			while(!stack.isEmpty() && price[i] > price[stack.top()])
			{
				stack.pop();
			}

			int p = stack.isEmpty()? -1: stack.top();
			spans[i] = i - p;
			stack.push(i);
		}
		return spans;
	}

	public static int[] calcSpanGeek(int[] price)
	{
		int spans[] = new int[price.length];
		Stack<Integer> stack = new Stack<>();
		//stack.push(0);
		//spans[0] = 1;
		
		for(int i = 0; i < price.length; i++)
		{
			while(!stack.isEmpty() && price[stack.top()] < price[i])
			{
				stack.pop();
			}
			spans[i] = (stack.isEmpty())? (i + 1): (i - stack.top());
			stack.push(i);
		}
		return spans;
	}

	// This is latest code I wrote myself
	private static int[] leftGTs(int[] hist)
	{
		Stack<Integer> stack = new Stack<>();
		int[] left = new int[hist.length];
		for(int i = 0; i < hist.length; i++) {
			while(!stack.isEmpty() && hist[stack.peek()] >= hist[i]) {
				stack.pop();
			}
			left[i] = stack.isEmpty()?i:(i-stack.peek()-1);
			stack.push(i);
		}
		//System.out.print("Left " + Arrays.toString(left) + " ");
		return left;
	}
	private static int[] rightGTs(int[] hist)
	{
		Stack<Integer> stack = new Stack<>();
		int[] right = new int[hist.length];
		for(int i = hist.length - 1; i >= 0; i--) {
			while(!stack.isEmpty() && hist[stack.peek()] >= hist[i]) {
				stack.pop();
			}
			right[i] = stack.isEmpty()?(hist.length-i-1):(stack.peek()-i)-1;
			stack.push(i);
		}
		//System.out.println("Right " + Arrays.toString(right));
		return right;
	}
	private static int maxArea(int hist[])
	{
		int[] left = leftGTs(hist);
		int[] right = rightGTs(hist);
		int max = 0;;
		for(int i = 0; i < hist.length; i++) {
			max = Math.max(max, hist[i]*(left[i] + right[i] + 1));
		}
		return max;
	}
	private static int getMaxAreaGeek(int hist[])
	{
		// Stack holds hist[] array indexes in increasing order of bar heights.
		Stack<Integer> s = new Stack<>();
	 
		int max_area = 0; // Initalize max area
		int tp;  // To store top of stack
		int area_with_top; // To store area with top bar as the smallest bar
	 
		int i = 0;
		while ((i < hist.length) || !s.isEmpty())
		{
			// If this bar is higher than the bar on top stack, push it to stack
			if (s.isEmpty() || (i < hist.length && hist[s.top()] <= hist[i]))
			{
				s.push(i++);
			}
	 
			// If this bar is lower than top of stack, then calculate area of rectangle 
			// with stack top as the smallest (or minimum height) bar. 'i' is 
			// 'right index' for the top and element before top in stack is 'left index'
			else
			{
				tp = s.pop();  // store the top index
	 
				// Calculate the area with hist[tp] stack as smallest bar
				area_with_top = hist[tp] * (s.isEmpty() ? i : i-s.top()-1);
	 
				// update max area, if needed
				if (area_with_top > max_area)
					max_area = area_with_top;
			}
		}
		return max_area;
	}
 
	private static int calcWaterholdArea(int[] heights)
	{
		int maxIndex = 0, area = 0;
		for(int i = 1; i < heights.length; i++)
		{
			if(heights[i] >= heights[maxIndex]) {
				for(int j = maxIndex+1; j < i; j++) {
					area += (heights[maxIndex] - heights[j]);
				}
				maxIndex = i;
			}
		}

		//if(maxIndex < heights.length-2) {// && heights[maxIndex] > heights[heights.length-1])
			int endMax = heights.length-1;
			for(int i = heights.length-2; i >= maxIndex; i--)
			{
				if(heights[i] >= heights[endMax]) {
					for(int j = i+1; j < endMax; j++) {
						area += (heights[endMax] - heights[j]);
					}
					endMax = i;
				}
			}
		//}
		return area;
	}
	private static int calcWaterholdArea2(int[] hts)
	{
		int maxIndex = 0;
		for(int i = 1; i < hts.length; i++) {
			if(hts[i] > hts[maxIndex]) {
				maxIndex = i;
			}
		}
		int max = 0, area = 0;
		for(int i = 1; i < maxIndex; i++) {
			if(hts[i] > hts[max]) {
				max = i;
			}
			if(hts[i] < hts[max]) {
				area += (hts[max] - hts[i]);
			}
		}
		max = hts.length-1;
		for(int i = hts.length-2; i > maxIndex; i--) {
			if(hts[i] > hts[max]) {
				max = i;
			}
			if(hts[i] < hts[max]) {
				area += (hts[max] - hts[i]);
			}
		}
		return area;
	}

	
	private static void testCalcSpan(int[] a) {
		System.out.println("Stack Span of " + Arrays.toString(a) + " is: " + Arrays.toString(calcSpanGanesh(a)));
		System.out.println("GEEK Span of " + Arrays.toString(a) + " is: " + Arrays.toString(calcSpanGeek(a)));
	}
	private static void testMaxAreaHistogram(int[] hist) {
		System.out.println("Max area of " + Arrays.toString(hist) + " is: " + getMaxAreaGeek(hist));
		System.out.println("Maxarea2 of " + Arrays.toString(hist) + " is: " + maxArea(hist));
	}
	private static void testcalcHoldArea(int[] hts) {
		System.out.println("Holding area of " + Arrays.toString(hts) + " is: " + calcWaterholdArea(hts));
		System.out.println("Holding are2 of " + Arrays.toString(hts) + " is: " + calcWaterholdArea2(hts));
	}
	private static void testSortStack(int[] vals){
		Stack<Integer> stack = new Stack<>();
		for(int val : vals) {
			stack.push(val);
		}
		System.out.println("B4 Sort: " + stack.toString());
		Stack.sort(stack);
		System.out.println("A4 Sort: " + stack.toString());
	}

	private static void basicStackOps() {
		Stack<Integer> stack = new Stack<Integer>();
		System.out.println("min: " + stack.min() + " " + stack.toString());
		stack.push(14);
		System.out.println("min: " + stack.min() + " " + stack.toString());
		stack.push(10);
		System.out.println("min: " + stack.min() + " " + stack.toString());
		stack.push(2);
		System.out.println("min: " + stack.min() + " " + stack.toString());
		stack.push(5);
		System.out.println("min: " + stack.min() + " " + stack.toString());
		stack.push(8);
		System.out.println("min: " + stack.min() + " " + stack.toString());
		stack.push(4);
		System.out.println("min: " + stack.min() + " " + stack.toString());
		stack.push(1);
		System.out.println("min: " + stack.min() + " " + stack.toString());
		System.out.println("pop: " + stack.pop());
		System.out.println("min: " + stack.min() + " " + stack.toString());
		System.out.println("pop: " + stack.pop());
		System.out.println("min: " + stack.min() + " " + stack.toString());
		System.out.println("pop: " + stack.pop());
		System.out.println("min: " + stack.min() + " " + stack.toString());
		System.out.println("pop: " + stack.pop());
		System.out.println("min: " + stack.min() + " " + stack.toString());
		System.out.println("pop: " + stack.pop());
		System.out.println("min: " + stack.min() + " " + stack.toString());
		System.out.println("pop: " + stack.pop());
		System.out.println("min: " + stack.min() + " " + stack.toString());
		System.out.println("pop: " + stack.pop());
		System.out.println("min: " + stack.min() + " " + stack.toString());

		System.out.println("## New Input");
		stack.push(1);
		System.out.println("min: " + stack.min() + " " + stack.toString());
		stack.push(4);
		System.out.println("min: " + stack.min() + " " + stack.toString());
		stack.push(2);
		System.out.println("min: " + stack.min() + " " + stack.toString());
		stack.push(0);
		System.out.println("min: " + stack.min() + " " + stack.toString());
		stack.push(-1);
		System.out.println("min: " + stack.min() + " " + stack.toString());
		stack.push(-2);
		System.out.println("min: " + stack.min() + " " + stack.toString());
		stack.push(-3);
		System.out.println("min: " + stack.min() + " " + stack.toString());
		stack.push(-4);
		System.out.println("min: " + stack.min() + " " + stack.toString());
		stack.push(-5);
		System.out.println("min: " + stack.min() + " " + stack.toString());
		stack.push(-6);
		System.out.println("min: " + stack.min() + " " + stack.toString());
		System.out.println("pop: " + stack.pop());
		System.out.println("min: " + stack.min() + " " + stack.toString());
		System.out.println("pop: " + stack.pop());
		System.out.println("min: " + stack.min() + " " + stack.toString());
		System.out.println("pop: " + stack.pop());
		System.out.println("min: " + stack.min() + " " + stack.toString());
		System.out.println("pop: " + stack.pop());
		System.out.println("min: " + stack.min() + " " + stack.toString());
		System.out.println("pop: " + stack.pop());
		System.out.println("min: " + stack.min() + " " + stack.toString());
		System.out.println("pop: " + stack.pop());
		System.out.println("min: " + stack.min() + " " + stack.toString());
		System.out.println("pop: " + stack.pop());
		System.out.println("min: " + stack.min() + " " + stack.toString());

		System.out.println("Stack class with Array holds max 50");
	}
	
	public static void main(String args[])
    {
		//basicStackOps();
		
		testCalcSpan(new int[] {6, 3, 4, 5, 2});
		testCalcSpan(new int[] {6, 3, 4, 5, 2, 7});
		testCalcSpan(new int[] {100, 80, 60, 70, 60, 75, 85});
		testCalcSpan(new int[] {3, 2, 4, 1, 5, 6, 8, 7, 9, 10, 12, 11});
		testCalcSpan(new int[] {1, 2, 3, 4, 5, 6, 8, 7, 9, 10, 12, 11});
		testCalcSpan(new int[] {7, 2, 3, 2, 5, 4, 6, 5, 7, 9, 10, 12, 11});
		testCalcSpan(new int[] {9, 6, 2, 1, 3, 5, 4, 8, 2, 7});

		testMaxAreaHistogram(new int[] {6, 2, 5, 4, 5, 1, 6});
		testMaxAreaHistogram(new int[] {3, 1, 2, 5, 6, 1, 4, 4});
		testMaxAreaHistogram(new int[] {2, 4, 3, 4, 6, 2, 4, 4});
		testMaxAreaHistogram(new int[] {4, 3, 5, 6, 1});
		testMaxAreaHistogram(new int[] {7, 2, 1, 4, 5, 1, 3, 3});
		testMaxAreaHistogram(new int[] {10, 10, 10, 10, 10});
		testMaxAreaHistogram(new int[] {7, 10, 10, 10, 10});
		testMaxAreaHistogram(new int[] {7, 10, 10, 10, 10, 7});
		testMaxAreaHistogram(new int[] {3, 2, 5, 6, 1, 4, 4});
		testMaxAreaHistogram(new int[] {3, 2, 5, 6, 2, 4, 4});
		testMaxAreaHistogram(new int[] {2, 1, 5, 6, 2, 3});
		testMaxAreaHistogram(new int[] {9, 6, 2, 1, 3, 5, 4, 8, 2, 7});

		testcalcHoldArea(new int[] {3, 2, 3, 4, 3, 5, 1, 2, 3, 1, 4, 6, 1, 3, 5, 2, 4, 3});
		testcalcHoldArea(new int[] {3, 0, 0, 2, 0, 4});
		testcalcHoldArea(new int[] {3, 2, 3, 4, 3, 5, 1, 2, 3, 1, 4, 6, 5, 4, 3, 2, 1, 0});
		testcalcHoldArea(new int[] {3, 4, 8, 4, 2});
		testcalcHoldArea(new int[] {5, 3, 4, 8, 4, 2});
		testcalcHoldArea(new int[] {9, 6, 2, 1, 3, 5, 4, 8, 2, 7});
		testcalcHoldArea(new int[] {0, 0, 4, 0, 0, 6, 0, 0, 3, 0, 5, 0, 1, 0, 0, 0});
		testcalcHoldArea(new int[] {0, 0, 4, 0, 0, 6, 0, 0, 3, 0, 8, 0, 2, 0, 5, 2, 0, 3, 0, 0});

		testSortStack(new int[] {10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0});
		testSortStack(new int[] {10, 7, 9, 3, 4, 1, 2, 6, 8, 5});
		testSortStack(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
	}
}

/*
import java.util.TreeMap;

public class Solution {
	public ArrayList<ArrayList<Integer>> fourSum(ArrayList<Integer> a, int sum) {
	    TreeMap<Integer, ArrayList<ArrayList<Integer>>> map = new TreeMap<>();
	    Collections.sort(a);
	    
	    for(int i = 0; i < a.size()-1; i++) {
	        for(int j = i+1; j < a.size(); j++) {
	            int key = a.get(i) + a.get(j);
	            ArrayList<Integer> val = new ArrayList<>();
	            val.add(a.get(i));
	            val.add(a.get(j));
	            if(!map.containsKey(key)) {
	                map.put(key, new ArrayList<ArrayList<Integer>>());
	            }
	            ArrayList<ArrayList<Integer>> putVal = map.get(key);
	            putVal.add(val);
	        }
	    }
	    
	    ArrayList<ArrayList<Integer>> res = new ArrayList<>();
	    for(int key : map.keySet()) {
	        if(key <= (sum-key) && map.containsKey(key) && map.containsKey(sum-key)) {
	            ArrayList<ArrayList<Integer>> list1 = map.get(key);
	            ArrayList<ArrayList<Integer>> list2 = map.get(sum-key);
	            
	            int end_i = ((list1.hashCode() == list2.hashCode())?(list1.getSize()-1):list1.getSize();
	            int init_j = ((list1.hashCode() == list2.hashCode())?(list1.getSize()-1):list1.getSize();
	            for(int i = 0;  i++) {
	                for(int j = i+1; j < ArrayList<Integer> ints2 : list2) {
	                    if()
	                    ArrayList<Integer> temp = new ArrayList<>();
	                    temp.addAll(ints1);
	                    temp.addAll(ints2);
	                    res.add(temp);
	                }
	            }
	        }
	    }
	    return res;
	}
}
*/
