package llsq;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class LinkedList
{
    // Friendly data; accessible by other package routines
	private ListNode head;
	private ListNode tail;
	private int len;

	static class ListAddResult
	{
		private ListNode node;
		private int carry;
		ListAddResult(int carry, ListNode node)
		{
			this.node = node;
			this.carry = carry;
		}
		public ListNode node() { return node; }
		public int carry() { return carry; }
	}
	static class ListInfo
	{
		private ListNode tail;
		private int length;
		ListInfo(ListNode end, int len)
		{
			this.tail = end;
			this.length = len;
		}
		public ListNode tail() { return tail; }
		public int length() { return length; }
	}
	static class ListPali
	{
		private boolean isMatch;
		private ListNode node;
		ListPali(boolean isMatch, ListNode node)
		{
			this.node = node;
			this.isMatch = isMatch;
		}
		public ListNode node() { return node; }
		public boolean isMatch() { return isMatch; }
	}

	// Constructors
	LinkedList()
	{
        head = null;
    }

	public ListNode head() { return head; }
	public ListNode tail() { return tail; }
	public int length() { return len; }

	public static int length(ListNode node)
	{
		int len = 0;
		while(node != null) {
			node = node.next();
			len++;
		}
		return len;
	}
	public static ListInfo getTailAndLength(ListNode node)
	{
		if(node == null) return null;
		int len = 1;
		while(node.next() != null) {
			node = node.next();
			len++;
		}
		return new ListInfo(node, len);
	}

	public ListNode add(int value)
	{
		return add(new ListNode(value));
	}

	public ListNode add(ListNode node)
	{
		if(head == null) {
			head = tail = node;
		}
		else {
			tail.next(node);
			tail = node;
		}
		len++;
		return node;
	}

	public static LinkedList createCycleList()
	{
		LinkedList list = createSortedList();

		ListNode node = list.head();
		while(node.value() < 7) {
			node = node.next();
		}
		list.tail().next(node);
		return list;
	}

	public static LinkedList createListFromArray(int[] vals)
	{
		LinkedList list = new LinkedList();
		for(int val : vals) {
			list.add(val);
		}
		return list;
	}

	public void appendArrayToList(int[] vals)
	{
		for(int val : vals) {
			add(val);
		}
	}

	public static LinkedList createSortedList()
	{
		return LinkedList.createSortedList(10);
	}
	public static LinkedList createSortedList(int count)
	{
		LinkedList list = new LinkedList();
		for(int i = 1; i <= count; i++)
		{
			list.add(i);
		}
		/*list.add(new ListNode('A'));
		list.add('B');
		ListNode c = list.add('C');
		//list.add('C');
		list.add('D');
		list.add('E');
		list.add('F');
		ListNode g = list.add('G');
		list.add('H');
		list.add('I');
		//list.add('J');
		ListNode j = list.add('J');
		//j.next(c);
		list.add('K');
		list.add('L');*/

		return list;
	}
	public static LinkedList createSortedRandomList(int nodes)
	{
		LinkedList list = LinkedList.createSortedList(nodes);

		for(ListNode curr = list.head(); curr.next().next() != null; curr = curr.next())
		{
			curr.random(curr.next().next());
		}

		return list;
	}

	// BEGIN Cracking the Coding Interview  -->
	// Only have access to node object - no access to head, and it is Middle node
	public int removeDupes()
	{
		ListNode prev = null, curr = head;
		HashSet<Integer> valSet = new HashSet<>();
		int delCount = 0;
		while(curr != null) {
			if(valSet.contains(curr.value())) {
				prev.next(curr.next());
				delCount++;
			}
			else {
				valSet.add(curr.value());
			}
			prev = curr;
			curr = curr.next();
		}
		return delCount;
	}
	public int removeDupes2()
	{
		ListNode curr = head;
		int delCount = 0;
		while(curr != null) {
			ListNode prev = curr, p = curr.next();
			while(p != null) {
				if(p.value() == curr.value()) {
					prev.next(p.next());
					delCount++;
				}
				else {
					prev = p;
				}
				p = p.next();
			}
			curr = curr.next();
		}
		return delCount;
	}

	public ListNode kthFromLast(int k)
	{
		ListNode p1 = head;
		for(int i = 0; i < k; i++) {
			if(p1 == null) {
				return null;
			}
			p1 = p1.next();
		}
		ListNode p2 = head;
		while(p1 != null) {
			p1 = p1.next();
			p2 = p2.next();
		}
		return p2;
	}

	public boolean deleteMiddleNode(ListNode node)
	{
		if(node == null || node.next() != null) {
			return false;
		}
		ListNode next = node.next();
		node.value(next.value());
		node.next(next.next());
		return true;
	}

	public void partition(int val)
	{
		ListNode headSmall = null, tailSmall = null;
		ListNode headBig = null, tailBig = null;

		ListNode curr = head;
		while(curr != null) {
			ListNode next = curr.next();
			curr.next(null);
			if(curr.value() < val) {
				if(headSmall == null) {
					headSmall = curr;
				}
				else {
					tailSmall.next(curr);
				}
				tailSmall = curr;
			}
			else {
				if(headBig == null) {
					headBig = curr;
				}
				else {
					tailBig.next(curr);
				}
				tailBig = curr;
			}
			curr = next;
		}

		if(tailSmall != null) {
			head = headSmall;
			tailSmall.next(headBig);
		}
		if(tailBig != null) {
			tail = tailBig;
		}
	}
	public void partition2(int val)
	{
		ListNode curr = head, end = head;
		while(curr != null) {
			ListNode next = curr.next();
			if(curr.value() < val) {
				curr.next(head);
				head = curr;
			}
			else {
				end.next(curr);
				end = curr;
			}
			curr = next;
		}
		end.next(null);
		
		tail = end;
	}

	public static ListNode sumList(LinkedList l1, LinkedList l2)
	{
		ListNode n1 = l1.head();
		ListNode n2 = l2.head();
		ListNode sumHead = null, sumNode = null, lastSumNode = null;
		int carry = 0;
		
		while(n1 != null || n2 != null) {
			int sum = ((n1 == null)? 0:n1.value()) +
					((n2 == null)?0:n2.value()) + carry;
			sumNode = new ListNode(sum%10);
			carry = sum/10;
			if(sumHead == null) {
				sumHead = sumNode;
			}
			if(lastSumNode != null) {
				lastSumNode.next(sumNode);
			}
			lastSumNode = sumNode;
			n1 = (n1 != null)? n1.next():null;
			n2 = (n2 != null)? n2.next():null;
		}
		if(carry > 0) {
			lastSumNode.next(new ListNode(carry));
		}
		return sumHead;
	}
	public static ListNode sumListRec(LinkedList l1, LinkedList l2)
	{
		return sumListRec(l1.head(), l2.head(), 0);
	}
	private static ListNode sumListRec(ListNode n1, ListNode n2, int carry)
	{
		if(n1 == null && n2 == null) {
			return ((carry > 0)? new ListNode(carry):null);
		}

		int sum = ((n1 == null)? 0:n1.value()) +
					((n2 == null)?0:n2.value()) + carry;
		ListNode sumNode = new ListNode(sum%10);

		carry = sum/10;
		n1 = (n1 != null)? n1.next():null;
		n2 = (n2 != null)? n2.next():null;
		ListNode sumNext = sumListRec(n1, n2, carry);
		sumNode.next(sumNext);
		return sumNode;
	}

	public static ListNode sumRevListRec(LinkedList list1, LinkedList list2)
	{
		return sumRevListRec(list1.head(), list2.head());
	}
	private static ListNode sumRevListRec(ListNode n1, ListNode n2)
	{
		int len1 = LinkedList.length(n1);
		int len2 = LinkedList.length(n2);
		ListAddResult result = null;
		if(len1 >= len2) {
			result = sumRevListRec(n1, n2, len1-len2);
		}
		else {
			result = sumRevListRec(n2, n1, len2-len1);
		}
		return result.node();
	}
	private static ListAddResult sumRevListRec(ListNode n1, ListNode n2, int lenDiff)
	{
		int sum = 0;
		ListAddResult result;
		if(n1.next() == null && n2.next() == null) {
			sum = n1.value() + n2.value();
			return new ListAddResult(sum/10, new ListNode(sum%10));
		}
		else {
			result = sumRevListRec(n1.next(), (lenDiff > 0)? n2:n2.next(), (lenDiff > 0)?lenDiff - 1:0);
		}

		sum = n1.value() + ((lenDiff > 0)?0:n2.value()) + result.carry();
		ListNode sumNode = new ListNode(sum%10);
		sumNode.next(result.node());
		return new ListAddResult(sum/10, sumNode);
	}

	public static boolean isPalindrome(ListNode node)
	{
		if(node == null) return true;
		ListNode reverse = cloneReverse2ndHalf(node);
		return isEqual(node, reverse);
	}
	private static ListNode cloneReverse2ndHalf(ListNode node)
	{
		if(node == null) return null;
		ListNode slowPtr = node, fastPtr = node;
		while(fastPtr.next() != null && fastPtr.next().next() != null) {
			fastPtr = fastPtr.next().next();
			slowPtr = slowPtr.next();
		}
		ListNode headPtr = null;
		while(slowPtr != null) {
			ListNode curr = new ListNode(slowPtr.value());
			if(headPtr != null) {
				curr.next(headPtr);
			}
			headPtr = curr;
			slowPtr = slowPtr.next();
		}
		return headPtr;
	}
	private static boolean isEqual(ListNode longList, ListNode shortList)
	{
		while(longList != null && shortList != null) {
			if(longList.value() != shortList.value()) {
				return false;
			}
			longList = longList.next();
			shortList = shortList.next();
		}
		return (shortList == null);
	}
	public static boolean isPalindrome2(ListNode node)
	{
		int length = LinkedList.length(node);
		Stack<Integer> valStack = new Stack<>();
		for(int i = 0; i < length/2; i++) {
			valStack.push(node.value());
			node = node.next();
		}
		if(length%2 == 1) {
			node = node.next();
		}
		
		while(!valStack.isEmpty()) {
			if(valStack.pop() != node.value()) {
				return false;
			}
			node = node.next();
		}
		return true;
	}
	public static boolean isPalindrome3(ListNode node)
	{
		int length = LinkedList.length(node);
		if(length < 2) return true;
		else if(length == 2) return node.value() == node.next().value();
		return isPalindromeRec(node, length).isMatch();
	}
	private static ListPali isPalindromeRec(ListNode node, int lengthLeft)
	{
		if(null == node || lengthLeft <= 0) {
			return new ListPali(true, node);
		}
		else if(1 == lengthLeft) { // midpoint of odd length list
			return new ListPali(true, node.next());
		}
		//else if(2 == lengthLeft) { // midpoint of even length list
		//	return new ListPali(node.value() == node.next().value(), node.next().next());
		//}
		
		// unraveling the stack
		ListPali result = isPalindromeRec(node.next(), lengthLeft-2);

		if(result.isMatch() && result.node() != null) { // yet to reach (begin, end)
			result = new ListPali(result.node().value() == node.value(), result.node().next());
		}
		else {
			result = new ListPali(result.isMatch(), null);
		}
		return result;
	}
	
	public ListNode hasLoop(boolean findNode)
	{
		ListNode slow = head, fast = head;
		System.out.println("SLOW\tFAST");
		while(fast != null && fast.next() != null)
		{
			System.out.println(slow.value() + "\t" + fast.value());
			slow = slow.next();
			fast = fast.next().next();
			if(fast == slow)
				break;
		}

		if((fast == slow) && findNode)
		{
			System.out.println("----------");
			slow = head;
			while(fast != slow)
			{
				System.out.println(slow.value() + "\t" + fast.value());
				fast = fast.next();
				slow = slow.next();
			}
		}
		return fast;
	}

	public ListNode findIntersection(ListNode first1, ListNode first2)
	{
		ListInfo info1 = LinkedList.getTailAndLength(first1);
		ListInfo info2 = LinkedList.getTailAndLength(first2);
		
		if(info1.tail() != info2.tail()) {
			return null;
		}
		
		ListNode shortList = info1.length() > info2.length()?first1:first2;
		ListNode longList = info1.length() <= info2.length()?first2:first1;
		
		for(int i = 0; i < Math.abs(info1.length() - info2.length()); i++) {
			longList = longList.next();
		}
		while(shortList != longList) {
			shortList = shortList.next();
			longList = longList.next();
		}
		return longList;
	}
	// END Cracking the Coding Interview  -->

	// BEGIN Amazon Bible, GeekforGeeks, leetcode  -->
	public void reverse(int k)
	{
		if(head == null) return;
		if(k < 2 || length() < k) {
			return;
		}
		ListNode blockInit = null, blockEnd = null, init = null;
		ListNode fst = head, snd = null, temp = null;
		for(int block = 0; block < length()/k; block++) {
			blockInit = fst;
			snd = fst.next();
			for(int i = 1; i < k; i++) {
				temp = snd.next();
				snd.next(fst);
				
				fst = snd;
				snd = temp;
			}
			if(init == null) {
				init = fst;
			}
			else {
				blockEnd.next(fst);
			}
			blockInit.next(snd);
			blockEnd = blockInit;
			fst = snd;
		}
		head = init;
	}
	// END Amazon Bible, GeekforGeeks, leetcode  -->
	
	// Will only work if there is a merge
	// Will fail for independent lists
	public ListNode findMergeNode(ListNode first1, ListNode first2)
	{
		ListNode slow = first1, fast = first1;

		System.out.println("SLOW          FAST");
		System.out.println("------------------");

		while(fast != slow)
		{
			if(slow.next() != null)
				slow = slow.next();
			else
				slow = first2;

			if(fast.next() != null)
				fast = fast.next();
			else
				fast = first2;
			if(fast.next() != null)
				fast = fast.next();
			else
				fast = first2;

			System.out.println("  " + slow.value() + "         " + fast.value());
		}

		System.out.println("------------------");
		slow = first1;
		while(fast != slow)
		{
			System.out.println("  " + slow.value() + "     " + fast.value());
			if(slow.next() != null)
				slow = slow.next();
			else
				slow = first2;

			if(fast.next() != null)
				fast = fast.next();
			else
				fast = first2;
		}
		return slow;
	}

	// appendCycleNodes ???
	public ListNode append(ListNode node1, ListNode node2)
	{
		if(node1 == null)
			return node2;
		if(node2 == null)
			return node1;

		node2.next(node1.next());
		node1.next(node2);

		return node2;
	}
	// Merge 2 sorted single linked lists
	public static ListNode merge(ListNode list1, ListNode list2)
	{
		if(list1 == null) return list2;
		if(list2 == null) return list1;

		ListNode curr1 = list1, curr2 = list2;
		ListNode head = null, curr = null;
		if(list1.value() < list2.value())
		{
			head = curr = list1;
			curr1 = curr1.next();
		}
		else
		{
			head = curr = list2;
			curr2 = curr2.next();
		}

		while(true)
		{
			// End of list1
			if(curr1 == null)
			{
				curr.next(curr2);
				break;
			}
			// End of list2
			if(curr2 == null)
			{
				curr.next(curr1);
				break;
			}
			if(curr1.value() < curr2.value())
			{
				curr.next(curr1);
				curr = curr1;
				curr1 = curr1.next();
			}
			else
			{
				curr.next(curr2);
				curr = curr2;
				curr2 = curr2.next();
			}

		}
		return head;
	}

	public void RevIter()
	{
		head = RevIter(head);
	}

	private ListNode RevIter(ListNode list)
	{
		ListNode prev = null, curr = list, next = null;
		while(curr != null)
		{
			next = curr.next();
			curr.next(prev);

			prev = curr;
			curr = next;
		}
		return prev;
	}

	public void RevRec()
	{
		head = RevRec(head);
	}
	// Destroys the original list
	private ListNode RevRec(ListNode head)
	{
		if(head == null || head.next() == null)
			return head;

		ListNode tail = head.next();
		head.next(null);

		ListNode rev = RevRec(tail);
		tail.next(head);
		return rev;
	}

	public void Alt_Rev_Rec()
	{
		head = Alt_Rev_Rec(head);
	}
	// Reverse alternate nodes
	// {1}->{1}, {1,2}->{2,1}, {1,2,3}->{2,1,3}, {1,2,3,4}->{2,1,4,3}
	private ListNode Alt_Rev_Rec(ListNode list)
	{
		if(list == null || list.next() == null)
			return list;

		ListNode node1 = list;
		ListNode node2 = list.next();
		ListNode tail = null;
		if(node1 != null)
		{
			tail = (node2 != null)?node2.next():null;

			node2.next(node1);
			node1.next(Alt_Rev_Rec(tail));
		}
		return node2;
	}

	public void Rev2Rec()
	{
		head = Rev2Rec(head);
	}
	private ListNode Rev2Rec(ListNode list)
	{
		if(list == null || list.next() == null)
			return list;

		ListNode temp = list.next();
		list.next(Rev2Rec(temp.next()));
		temp.next(list);

		return temp;
	}

	public void Alt_Rev_Iter()
	{
		head = Alt_Rev_Iter(head);
	}
	private ListNode Alt_Rev_Iter(ListNode list)
	{
		if(list == null || list.next() == null)
			return list;

		ListNode head = list, snd = null, newfirst = list.next();
		ListNode tail = null;
		ListNode temp = null;

		while(head != null && (snd = head.next()) != null)
		{
			if(tail != null)
			{
				tail.next(snd);
			}
			tail = head;

			head.next(snd.next());
			snd.next(head);
			head = head.next();
		}
		return newfirst;
	}

	public void ReverseK(int k)
	{
		head = ReverseK(head, k);
	}
	private ListNode ReverseK(ListNode list, int k)
	{
		if(list == null || list.next() == null || k == 0)
			return list;

		ListNode rfirst = null;
		ListNode curr = list, prevbegin = null;
		ListNode begink = list, endk = null, revk = null;
		int nodeIndex = 0;
		while(curr != null)
		{
			begink = curr;
			for(nodeIndex = 1; (nodeIndex < k) && (curr != null); nodeIndex++)
			{
				curr = curr.next();
			}
			if(curr != null) // Nodes still remain
			{
				endk = curr;
				curr = curr.next();

				endk.next(null);
				revk = RevIter(begink);

				if(rfirst == null)
					rfirst = revk;
				else
					prevbegin.next(revk);

				prevbegin = begink;
			}
			else // EOL
			{
				if(rfirst == null)
					rfirst = begink;
				else
					prevbegin.next(begink);
			}
		}
		return rfirst;
	}

	private void PrintReverse(ListNode list)
	{
		if(list == null) return;
		//System.out.print(list.value() + " ");
		PrintReverse(list.next());
		System.out.print(list.value() + " ");
	}

    public String toString()
    {
		return LinkedList.toString(head);
	}
    public static String toStringSimple(ListNode list)
    {
    	StringBuilder str = new StringBuilder("");
    	for(ListNode iter = list; iter != null; iter = iter.next())
    	{
    		str.append(iter.value() + "->");
    	}
    	return str.toString();
    }
    public static String toString(ListNode list)
    {
    	StringBuilder str = new StringBuilder("");
    	for(ListNode iter = list; iter != null; iter = iter.next())
    	{
    		str = str.append(String.format("[%s,%s,%s]", iter.value(), (iter.next() !=null)?iter.next().value():"", (iter.random() != null)?iter.random().value():""));
    	}
    	return str.toString();
    }

	public void RevkIter(int k)
	{
		head = RevkIter(head, k);
	}
	public ListNode RevkIter(ListNode list, int k)
	{
		if(list == null || list.next() == null || k <= 1) return list;

		ListNode batchBegin = list, prevEnd = null;
		ListNode returnList = null;

		while(batchBegin != null)
		{
			ListNode batchEnd = batchBegin;
			int i;
			for(i = 1; batchEnd != null && i < k; i++)
			{
				batchEnd = batchEnd.next();
			}

			if(i < k)
			{
				break;
			}

			if(returnList == null)
				returnList = batchEnd;
			
			ListNode nextBatch = batchEnd.next();

			// Reverse till batchEnd is encountered
			ListNode prev = null, next = null;
			ListNode curr = batchBegin;
			while(curr != nextBatch)
			{
				next = curr.next();
				curr.next(prev);
				
				prev = curr;
				curr = next;
			}
			// Set reversed batch to point to tail of list
			batchBegin.next(nextBatch);
			
			if(prevEnd != null)
			{
				prevEnd.next(batchEnd);
			}
			prevEnd = batchBegin;

			batchBegin = nextBatch;
		}
		return returnList;
	}

	public static LinkedList CloneRandomListNoMods(LinkedList list)
	{
		if(list == null) return null;
		
		ListNode temp1 = list.head();
		ListNode temp2 = new ListNode(temp1.value());
		LinkedList dst = new LinkedList();
		dst.add(temp2);
		
		HashMap<ListNode, ListNode> mapOldNew = new HashMap<>();
		mapOldNew.put(temp1, temp2);

		while(temp1.next() != null)
		{
			temp1 = temp1.next();
			
			temp2.next(new ListNode(temp1.value()));
			temp2 = temp2.next();

			mapOldNew.put(temp1, temp2);
		}

		if(!mapOldNew.isEmpty())
		{
			for(temp1 = list.head(), temp2 = dst.head(); 
					temp1 != null; 
					temp1 = temp1.next(), temp2 = temp2.next())
			{
				temp2.random(mapOldNew.get(temp1.random()));
			}
		}
		return dst;
	}
	public static LinkedList cloneRandomListNoModsNew(LinkedList list)
	{
		if(list == null || list.head() == null) return null;
		
		ListNode dst = null, src = list.head();
		HashMap<ListNode, ListNode> nodeMap = new HashMap<>();

		while(src != null) {
			nodeMap.put(src, new ListNode(src.value()));
			src = src.next();
		}

		src = list.head();
		while(src != null) {
			dst = nodeMap.get(src);
			dst.next(nodeMap.get(src.next()));
			dst.random(nodeMap.get(src.random()));
			src = src.next();
		}
		LinkedList temp = new LinkedList();
		temp.add(nodeMap.get(list.head()));
		return temp;
	}
	public static LinkedList CloneRandomList(LinkedList list)
	{
		if(list == null) return null;
		
		ListNode temp1 = list.head();
		ListNode temp2;
		LinkedList dst = new LinkedList();
		
		while(temp1 != null)
		{
			temp2 = new ListNode(temp1.value());
			temp2.next(temp1.next());
			temp1.next(temp2);
			
			temp1 = temp2.next();
		}

		temp1 = list.head();
		while(temp1 != null)
		{
			temp2 = temp1.next();
			
			if(temp1.random() != null)
			{
				temp2.random(temp1.random().next());
			}
			if(dst.head() == null)
				dst.add(temp2);
			
			temp1.next(temp2.next());
			temp2.next(temp1.next());
			
			temp1 = temp1.next();
		}
		return dst;
	}

	private void q14sortLinkedList()
	{
		head = q14sortLinkedList(head);
	}
	private ListNode q14sortLinkedList(ListNode head)
	{
		if(head == null || head.next() == null)
			return head;
		
		ListNode endPrefix = head;
		ListNode tail = head.next();
		
		while(tail != null)
		{
			if(tail.value() < head.value())
			{
				endPrefix.next(tail.next());
				tail.next(head);
				head = tail;
			}
			else if(tail.value() >= endPrefix.value())
			{
				endPrefix = tail;
			}
			else
			{
				ListNode temp = head;
				while(temp.next().value() < tail.value())
				{
					temp = temp.next();
				}
				endPrefix.next(tail.next());
				tail.next(temp.next());
				temp.next(tail);
			}
			tail = endPrefix.next();
		}
		
		return head;
	}

	public boolean isListEven()
	{
		ListNode node = head;
		while(node != null && node.next() != null) {
			node = node.next().next();
		}
		return ((node == null)?true:false);
	}
	
    public static void main(String args[])
    {
		oldFunctions();
		CTCIFunctions();
		palindromeFunctions();
		moreProbs();
	}
	private static void oldFunctions()
	{
		LinkedList ll = LinkedList.createSortedList();
    	System.out.println("LL Original:\t" + ll.toString() + " is " + (ll.isListEven()?"EVEN":"ODD"));
    	ll.ReverseK(2);
    	System.out.println("LL ReverseK:\t" + ll.toString());

		LinkedList ll2 = LinkedList.createSortedList(2); // 3 gives an error check it later.
    	System.out.println("LL Original:\t" + ll2.toString() + " is " + (ll2.isListEven()?"EVEN":"ODD"));
    	ll2.RevkIter(2);
    	System.out.println("LL RevkIter 2:\t" + ll2.toString());

		LinkedList ll3 = LinkedList.createSortedList();
    	System.out.println("LL Original:\t" + ll3.toString() + " is " + (ll3.isListEven()?"EVEN":"ODD"));
    	ll3.RevkIter(3);
    	System.out.println("LL After RevkIter 3:\t" + ll3.toString());

		LinkedList ll4 = LinkedList.createSortedList();
    	System.out.println("LL Original:\t" + ll4.toString());
    	ll4.RevkIter(4);
    	System.out.println("LL After ReverseK 4:\t" + ll4.toString());

		LinkedList ll5 = LinkedList.createSortedList();
    	System.out.println("LL Original:\t" + ll5.toString());
    	ll5.RevkIter(5);
    	System.out.println("LL RevkIter 5:\t" + ll5.toString());

		//LinkedList ll6 = LinkedList.createCycleList();

		LinkedList ll7 = LinkedList.createSortedRandomList(10);
    	System.out.println("LL Random7:\t\t" + ll7.toString() + " is " + (ll7.isListEven()?"EVEN":"ODD"));
		LinkedList ll8 = LinkedList.CloneRandomList(ll7);
    	System.out.println("LL7 Cloned:\t\t" + ll8.toString());
		LinkedList ll9 = LinkedList.CloneRandomListNoMods(ll7);
    	System.out.println("LL7 Cloned No Mods:\t\t" + ll9.toString());
		LinkedList ll91 = LinkedList.cloneRandomListNoModsNew(ll7);
    	System.out.println("LL7 Cloned No Mods new:\t\t" + ll91.toString());
		
		System.out.println("Q14SortLinkedList");
		LinkedList ll10 = LinkedList.createListFromArray(new int[]{3, 5, 8, 5, 9, 10, 2, 20, 1, 7, 6, 4, 15, 18, 19, 14, 13, 11});
		System.out.println("Before sort: " + ll10.toString());
		ll10.q14sortLinkedList();
		System.out.println("After sort: " + ll10.toString());
	}
	private static void moreProbs()
	{
		LinkedList list1 = createListFromArray(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
		list1.reverse(10);
		System.out.println("reverse 10: " + LinkedList.toStringSimple(list1.head()));

		list1.reverse(3);
		System.out.println("reverse 3: " + LinkedList.toStringSimple(list1.head()));
		
		list1.reverse(2);
		System.out.println("reverse 2: " + LinkedList.toStringSimple(list1.head()));

		list1.reverse(6);
		System.out.println("reverse 6: " + LinkedList.toStringSimple(list1.head()));

	}
	private static void CTCIFunctions()
	{
		System.out.println("CTCI 2.4");
		LinkedList l1 = LinkedList.createListFromArray(new int[]{3, 5, 8, 5, 10, 2, 1});
		System.out.println("Before partition: " + l1.toString());
		l1.partition(5);
		System.out.println("After partition 5 : " + l1.toString());
		l1.appendArrayToList(new int[]{6, 20, 4, 28, 25, 30, 7, 22, 21});
		System.out.println("Before partition: " + l1.toString());
		l1.partition(0);
		System.out.println("After partition 0 : " + l1.toString());
		l1.partition(100);
		System.out.println("After partition 100 : " + l1.toString());

		LinkedList l2 = LinkedList.createListFromArray(new int[]{3, 5, 8, 5, 10, 2, 1});
		System.out.println("Before partition2: " + l2.toString());
		l2.partition(5);
		System.out.println("After partition2 5 : " + l2.toString());
		l2.appendArrayToList(new int[]{6, 20, 4, 28, 25, 30, 7, 22, 21});
		System.out.println("Before partition2: " + l2.toString());
		l2.partition(0);
		System.out.println("After partition2 0 : " + l2.toString());
		l2.partition(100);
		System.out.println("After partition2 100 : " + l2.toString());
		l2.partition(20);
		System.out.println("After partition2 20 : " + l2.toString());
	
		ListNode n = l2.kthFromLast(5);
		System.out.println("kthFromLast 5: " + ((n == null)?"k too long":n.toString()));
		n = l2.kthFromLast(15);
		System.out.println("kthFromLast 15: " + ((n == null)?"k too long":n.toString()));
		n = l2.kthFromLast(100);
		System.out.println("kthFromLast 500: " + ((n == null)?"k too long":n.toString()));

		LinkedList l3 = LinkedList.createListFromArray(new int[]{3, 5, 8, 5, 10, 3, 3, 2, 2, 1, 8});
		System.out.println("removed " + l3.removeDupes() + " Dupes");
		l3 = LinkedList.createListFromArray(new int[]{3, 5, 8, 5, 10, 3, 3, 2, 2, 1, 8});
		System.out.println("removed2 " + l3.removeDupes2() + " Dupes");
		l3 = LinkedList.createListFromArray(new int[]{3, 5, 8, 10, 3, 2, 1});
		System.out.println("removed2 " + l3.removeDupes() + " Dupes");
		l3 = LinkedList.createListFromArray(new int[]{3, 5, 8, 10, 3, 2, 1});
		System.out.println("removed2 " + l3.removeDupes2() + " Dupes");

		ListNode n1 = sumList(LinkedList.createListFromArray(new int[]{3, 5, 8}), 
								LinkedList.createListFromArray(new int[]{9, 5, 8}));
		System.out.println("sum list is " + toString(n1));
		ListNode n2 = sumList(LinkedList.createListFromArray(new int[]{3, 5, 8, 2, 6, 7, 3, 9}), 
								LinkedList.createListFromArray(new int[]{9, 5, 8}));
		System.out.println("sum list is " + toString(n2));
		System.out.println("sum list2 " + toString(sumListRec(n1, n2, 0)));

		n1 = sumRevListRec(LinkedList.createListFromArray(new int[]{6, 1, 7}), 
								LinkedList.createListFromArray(new int[]{2, 9, 5, 8}));
		System.out.println("sumrev list is " + toString(n1));
		
		LinkedList l4 = LinkedList.createCycleList();
		n1 = l4.hasLoop(true);
		System.out.println("list has a loop at " + n1.value());
	}
	private static void palindromeFunctions()
	{
		LinkedList l5 = LinkedList.createListFromArray(new int[]{3, 5, 8, 10, 3, 10, 8, 5, 3});
		System.out.println("list " + l5.toString() + "is " + (isPalindrome(l5.head())?"":"NOT ") + "a palin");
		System.out.println("list2 " + l5.toString() + "is " + (isPalindrome2(l5.head())?"":"NOT ") + "a palin");
		System.out.println("list3 " + l5.toString() + "is " + (isPalindrome3(l5.head())?"":"NOT ") + "a palin");
		l5 = LinkedList.createListFromArray(new int[]{13, 12, 18, 10, 10, 18, 12, 3});
		System.out.println("list " + l5.toString() + "is " + (isPalindrome(l5.head())?"":"NOT ") + "a palin");
		System.out.println("list2 " + l5.toString() + "is " + (isPalindrome2(l5.head())?"":"NOT ") + "a palin");
		System.out.println("list3 " + l5.toString() + "is " + (isPalindrome3(l5.head())?"":"NOT ") + "a palin");
		l5 = LinkedList.createListFromArray(new int[]{13, 12, 18, 10, 10, 18, 12, 13});
		System.out.println("list " + l5.toString() + "is " + (isPalindrome(l5.head())?"":"NOT ") + "a palin");
		System.out.println("list2 " + l5.toString() + "is " + (isPalindrome2(l5.head())?"":"NOT ") + "a palin");
		System.out.println("list3 " + l5.toString() + "is " + (isPalindrome3(l5.head())?"":"NOT ") + "a palin");
		l5 = LinkedList.createListFromArray(new int[]{});
		System.out.println("list " + l5.toString() + "is " + (isPalindrome(l5.head())?"":"NOT ") + "a palin");
		System.out.println("list2 " + l5.toString() + "is " + (isPalindrome2(l5.head())?"":"NOT ") + "a palin");
		System.out.println("list3 " + l5.toString() + "is " + (isPalindrome3(l5.head())?"":"NOT ") + "a palin");
		l5 = LinkedList.createListFromArray(new int[]{3});
		System.out.println("list " + l5.toString() + "is " + (isPalindrome(l5.head())?"":"NOT ") + "a palin");
		System.out.println("list2 " + l5.toString() + "is " + (isPalindrome2(l5.head())?"":"NOT ") + "a palin");
		System.out.println("list3 " + l5.toString() + "is " + (isPalindrome3(l5.head())?"":"NOT ") + "a palin");
		l5 = LinkedList.createListFromArray(new int[]{3, 5});
		System.out.println("list " + l5.toString() + "is " + (isPalindrome(l5.head())?"":"NOT ") + "a palin");
		System.out.println("list2 " + l5.toString() + "is " + (isPalindrome2(l5.head())?"":"NOT ") + "a palin");
		System.out.println("list3 " + l5.toString() + "is " + (isPalindrome3(l5.head())?"":"NOT ") + "a palin");
		l5 = LinkedList.createListFromArray(new int[]{3, 5, 3});
		System.out.println("list " + l5.toString() + "is " + (isPalindrome(l5.head())?"":"NOT ") + "a palin");
		System.out.println("list2 " + l5.toString() + "is " + (isPalindrome2(l5.head())?"":"NOT ") + "a palin");
		System.out.println("list3 " + l5.toString() + "is " + (isPalindrome3(l5.head())?"":"NOT ") + "a palin");
	}
}
