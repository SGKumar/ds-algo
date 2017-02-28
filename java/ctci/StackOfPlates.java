package ctci;

import java.util.ArrayList;
import llsq.Stack;

public class StackOfPlates<T> extends Stack<T>
{
	private ArrayList<Stack<T>> stacks;
	public StackOfPlates(/*int sizeOfEach*/)
	{
		stacks = new ArrayList<>();
	}

	public Stack<T> getLastStack()
	{
		if(stacks.size() == 0) return null;
		return stacks.get(stacks.size()-1);
	}
	private void ensureCapacity()
	{
		if(stacks.size() == 0 || getLastStack().isFull()) {
			stacks.add(new Stack<>());
		}
		//System.out.println("added new stack size: " + stacks.size());
	}
	public void push(T t)
	{
		ensureCapacity();
		Stack<T> last = getLastStack();
		last.push(t);
	}
	private void ensureNotEmpty()
	{
		ensureNotEmpty(stacks.size()-1);
	}
	private void ensureNotEmpty(int index)
	{
		if(index < 0 || index >= stacks.size()) {
			throw new IndexOutOfBoundsException();
		}
		Stack<T> stack = stacks.get(index);
		if(stack.isEmpty()) {
			stacks.remove(stacks.size()-1);
		}
		//System.out.println("removed empty stack " + stacks.size());
	}
	public T pop()
	{
		ensureNotEmpty();
		return getLastStack().pop();
	}
	public T popAt(int index)
	{
		ensureNotEmpty(index);
		return stacks.get(index).pop();
	}

	public static void main(String[] args) {
		testStackOfPlates(new int[] {2, 3, 4, 5, 6, 1, 7, 8, 9, 10});
	}

	private static void testStackOfPlates(int[] vals){
		StackOfPlates<Integer> stack = new StackOfPlates<>();
		for(int val : vals) {
			stack.push(val);
		}
	}
}

