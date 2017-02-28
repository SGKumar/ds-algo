import java.util.*;
import java.util.*;

// https://en.wikipedia.org/wiki/TreapNode
public class TreapImplicitKey {

	// Modify the following 5 methods to implement your custom operations on the tree.
	// This example implements Add/Max operations. Operations like Add/Sum, Set/Max can also be implemented.
	static int modifyOperation(int x, int y) {
		return x + y;
	}

	// query (or combine) operation
	static int queryOperation(int leftValue, int rightValue) {
		return Math.max(leftValue, rightValue);
	}

	static int deltaEffectOnSegment(int delta, int segmentLength) {
		if (delta == getNeutralDelta()) return getNeutralDelta();
		// Here you must write a fast equivalent of following slow code:
		// int result = delta;
		// for (int i = 1; i < segmentLength; i++) result = queryOperation(result, delta);
		// return result;
		return delta;
	}

	static int getNeutralDelta() {
		return 0;
	}

	static int getNeutralValue() {
		return Integer.MIN_VALUE;
	}

	// generic code
	static Random random = new Random();

	static int joinValueWithDelta(int value, int delta) {
		if (delta == getNeutralDelta()) return value;
		return modifyOperation(value, delta);
	}

	static int joinDeltas(int delta1, int delta2) {
		if (delta1 == getNeutralDelta()) return delta2;
		if (delta2 == getNeutralDelta()) return delta1;
		return modifyOperation(delta1, delta2);
	}

	static void pushDelta(TreapNode root) {
		if (root == null)
			return;
		root.nodeValue = joinValueWithDelta(root.nodeValue, root.delta);
		root.subTreeValue = joinValueWithDelta(root.subTreeValue, deltaEffectOnSegment(root.delta, root.size));
		if (root.left != null)
			root.left.delta = joinDeltas(root.left.delta, root.delta);
		if (root.right != null)
			root.right.delta = joinDeltas(root.right.delta, root.delta);
		root.delta = getNeutralDelta();
	}

	public static class TreapNode {
		int nodeValue;
		int subTreeValue;
		int delta; // delta affects nodeValue, subTreeValue, left.delta and right.delta
		int size;
		long prio;
		TreapNode left;
		TreapNode right;

		TreapNode(int value) {
			nodeValue = value;
			subTreeValue = value;
			delta = getNeutralDelta();
			size = 1;
			prio = random.nextLong();
		}

		void update() {
			subTreeValue = queryOperation(queryOperation(getSubTreeValue(left), joinValueWithDelta(nodeValue, delta)), getSubTreeValue(right));
			size = 1 + getSize(left) + getSize(right);
		}
	}

	static int getSize(TreapNode root) {
		return root == null ? 0 : root.size;
	}

	static int getSubTreeValue(TreapNode root) {
		return root == null ? getNeutralValue() : joinValueWithDelta(root.subTreeValue, deltaEffectOnSegment(root.delta, root.size));
	}

	public static class TreapPair {
		TreapNode left;
		TreapNode right;

		TreapPair(TreapNode left, TreapNode right) {
			this.left = left;
			this.right = right;
		}
	}

	public static TreapPair split(TreapNode root, int minRight) {
		if (root == null)
			return new TreapPair(null, null);
		pushDelta(root);
		if (getSize(root.left) >= minRight) {
			TreapPair sub = split(root.left, minRight);
			root.left = sub.right;
			root.update();
			sub.right = root;
			return sub;
		} else {
			TreapPair sub = split(root.right, minRight - getSize(root.left) - 1);
			root.right = sub.left;
			root.update();
			sub.left = root;
			return sub;
		}
	}

	public static TreapNode merge(TreapNode left, TreapNode right) {
		pushDelta(left);
		pushDelta(right);
		if (left == null)
			return right;
		if (right == null)
			return left;
		// if (random.nextInt(left.size + right.size) < left.size) {
		if (left.prio > right.prio) {
			left.right = merge(left.right, right);
			left.update();
			return left;
		} else {
			right.left = merge(left, right.left);
			right.update();
			return right;
		}
	}

	public static TreapNode insert(TreapNode root, int index, int value) {
		TreapPair t = split(root, index);
		return merge(merge(t.left, new TreapNode(value)), t.right);
	}

	public static TreapNode remove(TreapNode root, int index) {
		TreapPair t = split(root, index);
		return merge(t.left, split(t.right, index + 1 - getSize(t.left)).right);
	}

	public static TreapNode modify(TreapNode root, int a, int b, int delta) {
		TreapPair t1 = split(root, b + 1);
		TreapPair t2 = split(t1.left, a);
		t2.right.delta = joinDeltas(t2.right.delta, delta);
		return merge(merge(t2.left, t2.right), t1.right);
	}

	public static class TreapAndResult {
		TreapNode treap;
		int value;

		TreapAndResult(TreapNode t, int value) {
			this.treap = t;
			this.value = value;
		}
	}

	public static TreapAndResult query(TreapNode root, int a, int b) {
		TreapPair t1 = split(root, b + 1);
		TreapPair t2 = split(t1.left, a);
		int value = getSubTreeValue(t2.right);
		return new TreapAndResult(merge(merge(t2.left, t2.right), t1.right), value);
	}

	public static void print(TreapNode root) {
		if (root == null)
			return;
		pushDelta(root);
		print(root.left);
		System.out.print("[" + root.nodeValue + "," + getSize(root) + ", left:");
		System.out.print((root.left==null?"null":root.left.nodeValue) + ", right:");
		System.out.print((root.right==null?"null":root.right.nodeValue) + "]\n");
		print(root.right);
	}

	// Random test
	public static void main(String[] args) {
		TreapNode treap = null, t = null;
		List<Integer> list = new ArrayList<>();
		Random rnd = new Random();
		
		// First add 10 numbers
		/*
		while (list.size() < 10) {
			int pos = rnd.nextInt(list.size() + 1);
			int value = rnd.nextInt(100);
			list.add(pos, value);
			System.out.printf("pos %d value %d \n", pos, value);
			treap = insert(treap, pos, value);
		}
		System.out.printf("Before printing treap node %d ", treap.nodeValue);
		System.out.print("left " + (treap.left==null?"null":treap.left.nodeValue));
		System.out.println(" right " + (treap.right==null?"null":treap.right.nodeValue));
		print(treap);
		System.out.println("");
		*/
		
		for (int step = 0; step < 100000; step++) {
			int cmd = rnd.nextInt(6);
			if (cmd < 2 && list.size() < 30) {
				int pos = rnd.nextInt(list.size() + 1);
				System.out.println("int pos = rnd.nextInt(list.size() + 1 " + pos);
				int value = rnd.nextInt(100);
				System.out.println("int pos = rnd.nextInt(100) " + value);
				list.add(pos, value);
				treap = insert(treap, pos, value);
				System.out.println("After adding node " + treap.nodeValue);
			} /*else if (cmd < 3 && list.size() > 0) {
				int pos = rnd.nextInt(list.size());
				list.remove(pos);
				treap = remove(treap, pos);
			} */else if (cmd < 4 && list.size() > 0) {
				int b = rnd.nextInt(list.size());
				int a = rnd.nextInt(b + 1);
				int res = list.get(a);
				for (int i = a + 1; i <= b; i++)
					res = queryOperation(res, list.get(i));
				TreapAndResult tr = query(treap, a, b);
				treap = tr.treap;
				if (res != tr.value)
					throw new RuntimeException();
			} else if (cmd < 5 && list.size() > 0) {
				int b = rnd.nextInt(list.size());
				int a = rnd.nextInt(b + 1);
				int delta = rnd.nextInt(100) - 50;
				for (int i = a; i <= b; i++)
					list.set(i, joinValueWithDelta(list.get(i), delta));
				treap = modify(treap, a, b, delta);
			} else {
				for (int i = 0; i < list.size(); i++) {
					TreapAndResult tr = query(treap, i, i);
					treap = tr.treap;
					int v = tr.value;
					if (list.get(i) != v)
						throw new RuntimeException();
				}
			}
		}
		print(treap);
		System.out.println("Test passed");
	}
}