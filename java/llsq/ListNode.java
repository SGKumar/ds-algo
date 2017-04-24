package llsq;

/*interface ListNode<T> : Comparable<T>
{
	char value();
	T getPrev();
	T getNext();
}*/

//implements Comparable<ListNode>
public class ListNode 
{
    // Friendly data; accessible by other package routines
	//private final char nodeValue;
	public int val;
	public int value() { return val; }
	public void value(int val) { this.val = val; }

	public ListNode next, prev;
	public ListNode random;
	public ListNode child;

	public void next(ListNode next) { this.next = next; }
	public ListNode next() { return next; }

	public void prev(ListNode prev) { this.prev = prev; }
	public ListNode prev() { return prev; }

	public void random(ListNode random) { this.random = random; }
	public ListNode random() { return random; }

    // Constructors
    public ListNode(int value)
    {
        this.val = value;
        next = null;
        prev = null;
		child = null;
    }

    public ListNode(ListNode node)
    {
        this.val = node.value();
        next = null;
        prev = null;
    }

	// applicable only for double linked lists.
    public ListNode append(ListNode in)
    {
		prev.next(in);
		in.prev().next(this);

		ListNode temp = prev;
		prev = in.prev();
		in.prev(temp);

		return this;
	}

    /*public ListNode(MyBinaryNode treeNode)
    {
        nodeValue = treeNode.value();
        next = this;
        prev = this;
    }*/

	public String toString()
	{
		StringBuilder str = new StringBuilder(16);
		str.append('[').append(val).append(',');
		str.append((child == null)?' ':child.value()).append(',');
		str.append((prev == null)?' ':prev.value()).append(',');
		str.append((next == null)?' ':next.value()).append(']');

		return str.toString();
	}
}
