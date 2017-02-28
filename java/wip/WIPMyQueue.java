import java.util.ArrayList;
import java.util.Arrays;

//import BinaryNode;
// Basic node stored in unbalanced binary search trees
// Note that this class is not accessible outside
// of this package.
/*interface BinaryTreeNode<T> : Comparable<T>
{
	char Element();
	T getLeft();
	T getRight();
}*/

public class MyQueue
{
	BinaryNode[] _queue;
	int first;
	int last;

	public MyQueue()
	{
		this(50);
	}

	public MyQueue(int capacity)
	{
		_queue = new BinaryNode[capacity];
		int first = 0;
		int last = 0;
	}

	public void enque(BinaryNode val)
	{
		_queue[last++] = val;
	}

	public BinaryNode deque()
	{
		if(first == last)
			return null;
		else
			return _queue[first++];
	}

	public BinaryNode first()
	{
		if(first == last)
			return null;
		else
			return _queue[first];
	}

	public BinaryNode last()
	{
		if(first == last)
			return null;
		else
			return _queue[last-1];
	}

	public boolean isEmpty()
	{
		return (last == first);
	}

	public int Chumma(int a)
	{
		a = 999;
		return 1000;
	}

    public static void main(String args[])
    {
    	System.out.println("Queue class with Array holds max 50");
    }
}



