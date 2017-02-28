package llsq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class Queue<T>
{
	private static class QueueNode<T>
	{
		private T data;
		private QueueNode<T> next;

		public QueueNode(T data) {
			this.data = data;
		}
	}

	private QueueNode<T> head;
	private QueueNode<T> last;
	private int size;
	public int size()
	{
		return size;
	}
	public void add(T item)
	{
		QueueNode<T> t = new QueueNode<T>(item);
		if(head == null) {
			head = last = t;
		}
		else {
			last.next = t;
			last = last.next;
		}
		size++;
	}
	public T remove()
	{
		if (head == null) throw new NoSuchElementException();
		T item = head.data;
		head = head.next;
		if(head == null) {
			last = null;
		}
		size--;
		return item;
	}
	public T peek()
	{
		if (head == null) throw new NoSuchElementException();
		return head.data;
	}

	public boolean isEmpty()
	{
		return head == null;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder(128);
		for(QueueNode<T> node = head; node != null; node = node.next) {
			sb.append(node.data).append(" ");
		}
		return sb.toString();
	}

/*	private static void testCalcSpan(int[] a) {
		System.out.println("Queue Span of " + Arrays.toString(a) + " is: " + Arrays.toString(MyIntQueue.calcSpanGanesh(a)));
		System.out.println("GEEK Span of " + Arrays.toString(a) + " is: " + Arrays.toString(MyIntQueue.calcSpanGeek(a)));
	}
	private static void testMaxAreaHistogram(int[] hist) {
		System.out.println("Max area of " + Arrays.toString(hist) + " is: " + getMaxAreaGeek(hist));
	}
	private static void testcalcHoldArea(int[] hts) {
		System.out.println("Holding area of " + Arrays.toString(hts) + " is: " + calcWaterholdArea(hts));
	}
	private static void testQueueOfPlates(int[] vals){
		QueueOfPlates<Integer> q = new QueueOfPlates<>();
		for(int val : vals) {
			q.add(val);
		}
	}
*/
	private static void basicQueueOps() {
		Queue<Integer> q = new Queue<Integer>();
		q.add(14);
		q.add(10);
		q.add(2);
		q.add(5);
		q.add(8);
		q.add(4);
		q.add(1);
		System.out.println(q.toString());
		System.out.println("removed " + q.remove() + " q: " + q.toString());
		System.out.println("removed: " + q.remove() + " " + q.toString());
		System.out.println("removed: " + q.remove() + " " + q.toString());
		System.out.println("removed: " + q.remove() + " " + q.toString());
		System.out.println("removed: " + q.remove() + " " + q.toString());
		System.out.println("removed: " + q.remove() + " " + q.toString());
		System.out.println("removed: " + q.remove() + " " + q.toString());

		q.add(1);
	}
	
	public static void main(String args[])
    {
		basicQueueOps();
		//testQueueOfPlates(new int[] {2, 3, 4, 5, 6, 1, 7, 8, 9, 10});
	}
}

