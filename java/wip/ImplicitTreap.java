/*
 * An implicit treap is a treap that uses array indexes as its key and the value at that index as its value.
 * As a result, it can perform insertion and deletion in O(log N)
 *
 * https://github.com/jeffrey-xiao/Competitive-Programming/blob/master/src/codebook/datastructures/ImplicitTreap.java
 * Time complexity:
 *  - Remove: O(log N)
 *  - Insertion: O(log N)
 *  - Search: O(log N)
 *  - Access: O(log N)
 */

class ImplicitTreap {
	// root of the tree
	Node root = null;

	// object representing the nodes of the tree
	class Node {
		Integer size;
		Integer value;
		Double priority;
		Node left, right;

		Node (int value) {
			this.value = value;
			this.size = 1;
			priority = Math.random();
		}
	}

	private int getSize (Node n) {
		return n == null ? 0 : n.size;
	}

	private void updateSize (Node n) {
		if (n != null)
			n.size = getSize(n.left) + getSize(n.right) + 1;
	}

	// object representing a pair of nodes of the tree
	class NodePair {
		Node left, right;

		NodePair (Node left, Node right) {
			this.left = left;
			this.right = right;
		}
	}

	// auxiliary function to split
	private NodePair split (Node n, Integer key, Integer lowerCnt) {
		NodePair res = new NodePair(null, null);
		if (n == null)
			return res;
		Integer nKey = lowerCnt + getSize(n.left) + 1;
		if (nKey > key) {
			res = split(n.left, key, lowerCnt);
			n.left = res.right;
			res.right = n;
		} else {
			res = split(n.right, key, lowerCnt + getSize(n.left) + 1);
			n.right = res.left;
			res.left = n;
		}
		updateSize(res.left);
		updateSize(res.right);
		return res;
	}

	// auxiliary function to merge
	private Node merge (Node t1, Node t2) {
		if (t1 == null)
			return t2;
		else if (t2 == null)
			return t1;

		Node newRoot = null;
		if (t1.priority > t2.priority) {
			t1.right = merge(t1.right, t2);
			newRoot = t1;
		} else {
			t2.left = merge(t1, t2.left);
			newRoot = t2;
		}
		updateSize(newRoot);
		return newRoot;
	}

	public void pushBack (Integer val) {
		root = merge(root, new Node(val));
	}

	public void add (Integer key, Integer val) {
		NodePair n = split(root, key, 0);
		Node newRoot = merge(n.left, new Node(val));
		newRoot = merge(newRoot, n.right);
		root = newRoot;
	}

	public void modify (Integer key, Integer val) {
		root = modify(root, key, val, 0);
	}

	// auxiliary method for modify
	private Node modify (Node n, Integer key, Integer val, Integer lowerCnt) {
		Integer nKey = lowerCnt + getSize(n.left) + 1;
		if (nKey == key)
			n.value = val;
		else if (nKey > key)
			n.left = modify(n.left, key, val, lowerCnt);
		else
			n.right = modify(n.right, key, val, lowerCnt + getSize(n.left) + 1);
		return n;
	}

	public void remove (Integer k) {
		if (k > getSize(root))
			throw new IllegalArgumentException();
		root = remove(root, k, 0);
	}

	// auxiliary function to delete
	private Node remove (Node n, Integer k, Integer lowerCnt) {
		if (n == null)
			return n;
		Integer key = lowerCnt + getSize(n.left) + 1;
		int cmp = k.compareTo(key);
		if (cmp < 0)
			n.left = remove(n.left, k, lowerCnt);
		else if (cmp > 0)
			n.right = remove(n.right, k, lowerCnt + getSize(n.left) + 1);
		else {
			n = merge(n.left, n.right);
		}
		updateSize(n);
		return n;
	}

	public Integer get (Integer k) {
		if (k > getSize(root) || k <= 0)
			throw new IllegalArgumentException();
		return get(root, k, 0);
	}

	// auxiliary function for get
	private Integer get (Node n, Integer k, Integer lowerCnt) {
		if (n == null)
			return null;
		Integer key = lowerCnt + getSize(n.left) + 1;
		int cmp = k.compareTo(key);
		if (cmp < 0)
			return get(n.left, k, lowerCnt);
		else if (cmp > 0)
			return get(n.right, k, lowerCnt + getSize(n.left) + 1);
		return n.value;
	}

	public void traverse () {
		traverse(root);
	}

	// auxiliary method for traverse
	private void traverse (Node n) {
		if (n == null)
			return;
		traverse(n.left);
		System.out.println(n.value + " " + n.priority);
		traverse(n.right);
	}

	public String toString()
	{
		StringBuilder str = new StringBuilder();
		for (int i = 1; i <= 8; i++)
			str.append(get(i)).append(" ");
		
		return str.toString();
	}
	
	public void print(Node root, int level)
	{
		if (root == null)
			return;
		print(root.left, level+1);
		for(int i = 0; i < level; i++)
			System.out.print(" ");
		
		System.out.print("[" + root.value + "," + getSize(root) + ", left:");
		System.out.print((root.left==null?"null":root.left.value) + ", right:");
		System.out.print((root.right==null?"null":root.right.value) + "]\n");
		print(root.right, level+1);
	}

	private static void testHackerRank()
	{
				ImplicitTreap t = new ImplicitTreap();
		for (int i = 1; i <= 8; i++) {
			t.pushBack(i);
		}
		//t.add(1, 100);
		System.out.println(t.toString());
		
		t.print(t.root, 0);
		
		// 1 2 4
		System.out.println("split @ orig index 1");
		NodePair p1 = t.split(t.root, 1, 0);
		t.print(p1.left, 0);
		t.print(p1.right, 0);

		System.out.println("split @ orig index 4");
		NodePair p2 = t.split(p1.right, 3, 0);
		t.print(p2.left, 0);
		t.print(p2.right, 0);

		Node n = t.merge(p2.left, p1.left);
		t.root = t.merge(n, p2.right);
		System.out.println("After 1 2 4 :: " + t.toString());

		// 2 3 5
		System.out.println("split @ orig index 2");
		p1 = t.split(t.root, 2, 0);
		t.print(p1.left, 0);
		t.print(p1.right, 0);

		System.out.println("split @ orig index 5");
		p2 = t.split(p1.right, 3, 0);
		t.print(p2.left, 0);
		t.print(p2.right, 0);

		n = t.merge(p1.left, p2.right);
		t.root = t.merge(n, p2.left);
		System.out.println("After 2 3 5 :: " + t.toString());

		// 1 4 7
		System.out.println("split @ orig index 4");
		p1 = t.split(t.root, 3, 0);
		t.print(p1.left, 0);
		t.print(p1.right, 0);

		System.out.println("split @ orig index 7");
		p2 = t.split(p1.right, 4, 0);
		t.print(p2.left, 0);
		t.print(p2.right, 0);

		n = t.merge(p2.left, p1.left);
		t.root = t.merge(n, p2.right);
		System.out.println("After 1 4 7 :: " + t.toString());

		// 2 1 4
		System.out.println("split @ orig index 4");
		p1 = t.split(t.root, 4, 0);
		t.print(p1.left, 0);
		t.print(p1.right, 0);

		n = t.merge(p1.right, p1.left);
		System.out.println("After 2 1 4 :: " + t.toString());
	}
	
	private static void testSplit_i1i7c1c7_to_i1c1i7c7()
	{
		ImplicitTreap t = new ImplicitTreap();
		for (int i = 1; i <= 14; i++) {
			t.pushBack(i);
		}
		//t.add(1, 100);
		System.out.println(t.toString());
		
		t.print(t.root, 0);
		
		// 1 2 4
		System.out.println("split @ orig index 7");
		NodePair p1 = t.split(t.root, 6, 0);
		t.print(p1.left, 0);
		t.print(p1.right, 0);

		/*System.out.println("split @ orig index 4");
		NodePair p2 = t.split(p1.right, 3, 0);
		t.print(p2.left, 0);
		t.print(p2.right, 0);

		Node n = t.merge(p2.left, p1.left);
		t.root = t.merge(n, p2.right);
		System.out.println("After 1 2 4 :: " + t.toString());

		// 2 3 5
		System.out.println("split @ orig index 2");
		p1 = t.split(t.root, 2, 0);
		t.print(p1.left, 0);
		t.print(p1.right, 0);

		System.out.println("split @ orig index 5");
		p2 = t.split(p1.right, 3, 0);
		t.print(p2.left, 0);
		t.print(p2.right, 0);

		n = t.merge(p1.left, p2.right);
		t.root = t.merge(n, p2.left);
		System.out.println("After 2 3 5 :: " + t.toString());

		// 1 4 7
		System.out.println("split @ orig index 4");
		p1 = t.split(t.root, 3, 0);
		t.print(p1.left, 0);
		t.print(p1.right, 0);

		System.out.println("split @ orig index 7");
		p2 = t.split(p1.right, 4, 0);
		t.print(p2.left, 0);
		t.print(p2.right, 0);

		n = t.merge(p2.left, p1.left);
		t.root = t.merge(n, p2.right);
		System.out.println("After 1 4 7 :: " + t.toString());

		// 2 1 4
		System.out.println("split @ orig index 4");
		p1 = t.split(t.root, 4, 0);
		t.print(p1.left, 0);
		t.print(p1.right, 0);

		n = t.merge(p1.right, p1.left);
		System.out.println("After 2 1 4 :: " + t.toString());*/
	}

	public static void main (String[] args) {
		//testHackerRank();
		testSplit_i1i7c1c7_to_i1c1i7c7();
	}
}