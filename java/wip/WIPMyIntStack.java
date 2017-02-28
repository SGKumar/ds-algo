import java.util.ArrayList;
import java.util.Arrays;

//import MyBinaryNode;
// Basic node stored in unbalanced binary search trees
// Note that this class is not accessible outside
// of this package.
/*interface BinaryTreeNode<T> : Comparable<T>
{
	char Element();
	T getLeft();
	T getRight();
}*/

public class MyIntStack
{
	int[] _stack;
	int first;
	int min;

	public MyIntStack()
	{
		_stack = new int[50];
		int first = 0;
	}

	public void push(int val)
	{
		if(first == 0)
		{
			_stack[first++] = val;
			min = val;
		}
		else if(val >= min)
		{
			_stack[first++] = val;
		}
		else //val < min
		{
			_stack[first++] = 2*val - min;
			min = val;
		}
	}

	public int pop()
	{
		if(first == 0)
		{
			//min = java.lang.Integer.MIN_VALUE;
			return java.lang.Integer.MIN_VALUE;
		}

		int ret = _stack[first-1];
		if(_stack[first-1] < min)
		{
			ret = min;
			min = 2*min-_stack[--first];
		}
		else
			ret = _stack[--first];

		return ret;
	}

	public int top()
	{
		if(first == 0)
			return java.lang.Integer.MIN_VALUE;

		if(_stack[first-1] < min)
			return min;
		else
			return _stack[first-1];
	}

	public boolean isEmpty()
	{
		return (0 == first);
	}

	public int min()
	{
		return min;
	}

	public String toString()
	{
		return Arrays.toString(Arrays.copyOfRange(_stack, 0, first));
	}

	public static int[] calcSpanGanesh(int[] price)
	{
		int spans[] = new int[price.length];
		MyIntStack stack = new MyIntStack();
		
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
		MyIntStack stack = new MyIntStack();
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

	/*static int getMaxAreaNsqrd(int hist[])
	{
		for(int i = 0; i < hist.length; i++) {
			for(int j = i+1; j < )
		}
			
	}*/
	
	static int getMaxAreaGeek(int hist[])
	{
		// Create an empty stack. The stack holds indexes of hist[] array
		// The bars stored in stack are always in increasing order of their
		// heights.
		MyIntStack s = new MyIntStack();
	 
		int max_area = 0; // Initalize max area
		int tp;  // To store top of stack
		int area_with_top; // To store area with top bar as the smallest bar
	 
		// Run through all bars of given histogram
		int i = 0;
		while ((i < hist.length) || !s.isEmpty())
		{
			// If this bar is higher than the bar on top stack, push it to stack
			System.out.printf("B4 push i: %d, hist[i]: %d, top: %d\n", i, (i < hist.length)?hist[i]:-1, (s.isEmpty()?-1:s.top()));
			if (s.isEmpty() || (i < hist.length && hist[s.top()] <= hist[i]))
			{
				s.push(i++);
				System.out.printf("A4 push i: %d, hist[i]: %d, top: %d\n", i, (i < hist.length)?hist[i]:-1, (s.isEmpty()?-1:s.top()));
			}
	 
			// If this bar is lower than top of stack, then calculate area of rectangle 
			// with stack top as the smallest (or minimum height) bar. 'i' is 
			// 'right index' for the top and element before top in stack is 'left index'
			else
			{
				tp = s.pop();  // store the top index
	 
				// Calculate the area with hist[tp] stack as smallest bar
				System.out.printf("tp: %d, hist[tp]: %d, top: %d, i: %d\n", tp, hist[tp], (s.isEmpty()?-1:s.top()), i);
				area_with_top = hist[tp] * (s.isEmpty() ? i : i - s.top()-1);
	 
				// update max area, if needed
				System.out.printf("Current Area: %d, Max_area: %d\n", area_with_top, max_area);
				if (area_with_top > max_area)
					max_area = area_with_top;
			}
		}
	 
		// Now pop the remaining bars from stack and calculate area with every
		// popped bar as the smallest bar
		/*while (!s.isEmpty())
		{
			tp = s.pop();
			//System.out.printf("#tp: %d, hist[tp]: %d, top: %d, i: %d\n", tp, hist[tp], (s.isEmpty()?-1:s.top()), i);
			area_with_top = hist[tp] * (s.isEmpty() ? i : i - s.top()-1);
	 
			//System.out.printf("#Current Area: %d, Max_area: %d\n", area_with_top, max_area);
			if (max_area < area_with_top)
				max_area = area_with_top;
		}*/
	 
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

	private static void testCalcSpan(int[] a) {
		System.out.println("Stack Span of " + Arrays.toString(a) + " is: " + Arrays.toString(MyIntStack.calcSpanGanesh(a)));
		System.out.println("GEEK Span of " + Arrays.toString(a) + " is: " + Arrays.toString(MyIntStack.calcSpanGeek(a)));
	}
	private static void testMaxAreaHistogram(int[] hist) {
		System.out.println("Max area of " + Arrays.toString(hist) + " is: " + getMaxAreaGeek(hist));
	}
	private static void testcalcHoldArea(int[] hts) {
		System.out.println("Holding area of " + Arrays.toString(hts) + " is: " + calcWaterholdArea(hts));
	}

	private static void basicStackOps() {
		MyIntStack stack = new MyIntStack();
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
		testMaxAreaHistogram(new int[] {7, 10, 10, 10, 10});
		testMaxAreaHistogram(new int[] {9, 6, 2, 1, 3, 5, 4, 8, 2, 7});

		testcalcHoldArea(new int[] {3, 2, 3, 4, 3, 5, 1, 2, 3, 1, 4, 6, 1, 3, 5, 2, 4, 3});
		testcalcHoldArea(new int[] {3, 0, 0, 2, 0, 4});
		testcalcHoldArea(new int[] {3, 2, 3, 4, 3, 5, 1, 2, 3, 1, 4, 6, 5, 4, 3, 2, 1, 0});
		testcalcHoldArea(new int[] {3, 4, 8, 4, 2});
		testcalcHoldArea(new int[] {5, 3, 4, 8, 4, 2});
		testcalcHoldArea(new int[] {9, 6, 2, 1, 3, 5, 4, 8, 2, 7});
		testcalcHoldArea(new int[] {0, 0, 4, 0, 0, 6, 0, 0, 3, 0, 5, 0, 1, 0, 0, 0});
		testcalcHoldArea(new int[] {0, 0, 4, 0, 0, 6, 0, 0, 3, 0, 8, 0, 2, 0, 5, 2, 0, 3, 0, 0});
	}
}

