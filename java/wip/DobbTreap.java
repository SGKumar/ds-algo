import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Stack;

/**
 * An implentation of an ordered dictionary data structure, based
 * on the randomized search trees (treaps) by Seidel and Aragon.
 * <blockquote>
 * R. Seidel and C. R. Aragon. Randomized Binary Search Trees.
 * <em>Algorithmica</em>, 16(4/5):464-497, 1996.
 * </blockquote>
 *
 * This class implements an ordered dictionary that maps keys to values.
 * Any non-null object can be used as a value, but the keys must be
 * non-null objects that implement the <code>DobbOrdered</code> interface.
 * The methods are similar to the ones in <code>java.util.Hashtable</code>.
 * In addition, efficient methods for finding the minimum and maximum
 * keys and their values are included. The enumeration returns the keys
 * in sorted order. <p>
 * 
 * Most methods run in <math>O(log n)</math> randomized time, where
 * <math>n</math> is the number of keys in the treap; the exceptions
 * are <code>clone()</code> and <code>toString()</code> that run in time
 * proportional to the size of their output.
 *
 * @author Stefan.Nilsson@hut.fi
 * @see DobbOrdered
 * @version 1.0, 22 April 1997
 */
public class DobbTreap implements Cloneable
{
   private DobbNode root;
   private int size;
   private Random prio;

   /* If during an <code>insert()</code> or <code>delete()</code> it is
    * found that the key is present in the root, <code>keyFound</code>
    * will be <code>true</code> and <code>prevValue</code> will contain
    * the previous value assosiacted with the key before the update.
    */
   private boolean keyFound;
   private Object prevValue;

   /**
    * Constructs a new empty treap.
    */
   public DobbTreap() {
      root = null;
      size = 0;
      prio = new Random();
   }

   /**
    * Maps the key to the specified value in this treap.
    * Neither the key, nor the value can be <code>null</code>.
    * @return the previous value to which the key was mapped,
    * or <code>null</code> if the key did not have a previous
    * mapping in the treap.
    */
   public synchronized Object put(DobbOrdered key, Object value) {
      if (key == null || value == null) 
         throw new NullPointerException();

      DobbNode node = new DobbNode();
      node.key = key;
      node.value = value;
      node.prio = prio.nextInt(500);
      keyFound = false;
      root = insert(node, root);
      if (keyFound)
         return prevValue;
      else {
         size++;
         return null;
      }
   }

   /**
    * Gets the object associated with the specified key in the treap.
    * @return the value to which the key is mapped in the treap, or
    * <code>null</code> if the key is not mapped to any value in
    * this treap.
    */
   public synchronized Object get(DobbOrdered key) {
      DobbNode t = root;
      int comp;

      while (t != null && (comp = key.compareTo(t.key)) != 0)
         if (comp < 0)
            t = t.left;
         else
            t = t.right;
      return t != null ? t.value : null;
   }

   /**
    * Returns the minimum key of the treap.
    * @exception NoSuchElementException if the treap is empty.
    */
   public synchronized DobbOrdered getMinKey() {
      DobbNode t = root;

      if (t == null)
         throw new NoSuchElementException("DobbTreap");
      while (t.left != null)
         t = t.left;
      return t.key;
   }

   /**
    * Returns the maximum key of the treap.
    * @exception NoSuchElementException if the treap is empty.
    */
   public synchronized DobbOrdered getMaxKey() {
      DobbNode t = root;

      if (t == null)
         throw new NoSuchElementException("DobbTreap");
      while (t.right != null)
         t = t.right;
      return t.key;
   }

   /**
    * Returns the value to which the minimum key of the treap is mapped.
    * @exception NoSuchElementException if the treap is empty.
    */
   public synchronized Object getMinValue() {
      DobbNode t = root;

      if (t == null)
         throw new NoSuchElementException("DobbTreap");
      while (t.left != null)
         t = t.left;
      return t.value;
   }

   /**
    * Returns the value to which the maximum key of the treap is mapped.
    * @exception NoSuchElementException if the treap is empty.
    */
   public synchronized Object getMaxValue() {
      DobbNode t = root;

      if (t == null)
         throw new NoSuchElementException("DobbTreap");
      while (t.right != null)
         t = t.right;
      return t.value;
   }

   /**
    * Returns an ordered enumeration of the keys in this treap.
    * The enumeration will list the keys in ascending order.
    * @see Enumeration
    */
   public synchronized Enumeration keys() {
      return new TreapEnumeration(root, true, true);
   }

   /**
    * Returns an ordered enumeration of the keys in this treap.
    * The enumeration will list the keys in ascending order
    * if <code>ascending</code> is <code>true</code>, otherwise the
    * keys will be listed in descending order.
    * @see Enumeration
    */
   public synchronized Enumeration keys(boolean ascending) {
      return new TreapEnumeration(root, true, ascending);
   }

   /**
    * Returns an enumeration of the elemets in this treap.
    * The enumeration will list the elements in ascending order
    * (according to the corresponding keys).
    * @see Enumeration
    */
   public synchronized Enumeration elements() {
      return new TreapEnumeration(root, false, true);
   }

   /**
    * Returns an enumeration of the elemets in this treap.
    * The enumeration will list the elements in ascending order
    * (according to the corresponding keys) if <code>ascending</code>
    * is <code>true</code>, otherwise the elements will be listed
    * in descending order.
    * @see Enumeration
    */
   public synchronized Enumeration elements(boolean ascending) {
      return new TreapEnumeration(root, false, ascending);
   }

   /**
    * Returns <code>true</code> if this treap contains no mappings.
    */
   public boolean isEmpty() {
      return root == null;
   }

   /**
    * Removes tke key (and its corresponding value) from this treap.
    * This method does nothing if the key is not in the treap.
    * @return the value to which the key had been mapped in this treap,
    * or <code>null</code> if the key did not have a mapping.
    */
   public synchronized Object remove(DobbOrdered key) {
      keyFound = false;
      root = delete(key, root);
      if (keyFound) {
         size--;
         return prevValue;
      } else
         return null;
   }

   /**
    * Removes tke minimum key (and its corresponding value) from
    * this treap. This method does nothing if the treap is empty.
    * @return the value to which the minimum key had been mapped in
    * this treap, or <code>null</code> if the treap was empty.
    */
   public synchronized Object removeMin() {
      DobbNode t = root, tprev;

      if (root == null)
         return null;
      if (t.left == null)
         root = t.right;
      else {
         do {
            tprev = t;
            t = t.left;
         } while (t.left != null);
         tprev.left = t.right;
      }
      size--;
      return t.value;
   }

   /**
    * Removes tke maximum key (and its corresponding value) from
    * this treap. This method does nothing if the treap is empty.
    * @return the value to which the maximum key had been mapped in
    * this treap, or <code>null</code> if the treap was empty.
    */
   public synchronized Object removeMax() {
      DobbNode t = root, tprev;

      if (t == null)
         return null;
      if (t.right == null)
         root = t.left;
      else {
         do {
            tprev = t;
            t = t.right;
         } while (t.right != null);
         tprev.right = t.left;
      }
      size--;
      return t.value;
   }

   /**
    * Clear the treap so that it contains no mappings.
    */
   public synchronized void clear() {
      root = null;
      size = 0;
   }

   /**
    * Returns the number of keys in this treap.
    */
   public int size() {
      return size;
   }

   /**
    * Returns a string representation of this treap.
    * This routine is inefficient and primarily intended for
    * debugging. To access the elements in the treap in sorted order
    * use the <code>keys()</code> and <code>elements()</code methods.
    * @see DobbTreap#keys
    * @see DobbTreap#elements
    */
   public synchronized String toString() {
      StringBuffer strbuf = new StringBuffer();

      strbuf.append("{");
      if (root != null)
         strbuf.append(root.toString());
      if (strbuf.length() > 1)
         strbuf.setLength(strbuf.length() - 2);  // remove last ", "
      strbuf.append("}");
      return strbuf.toString();
   }

   /**
    * Creates a shallow copy of this treap.
    * The keys and the values themselves are not cloned.
    * @return a clone of this treap.
    */
   public synchronized Object clone() {
      try {
         DobbTreap treap = (DobbTreap) super.clone();

         treap.prio = new Random();
         treap.root = (root != null) ? (DobbNode) root.clone() : null;
         return treap;
      } catch (CloneNotSupportedException e) {
         // Cannot happen
         throw new InternalError(e.toString());
      }
   }

   /* Inserts a node into tree and returns the updated treap */
   private DobbNode insert(DobbNode node, DobbNode t) {
      if (t == null) return node;
      int comp = node.key.compareTo(t.key);
      if (comp < 0) {
         t.left = insert(node, t.left);
         if (t.prio > t.left.prio)
            t = t.rotateRight();
      } else if (comp > 0) {
         t.right = insert(node, t.right);
         if (t.prio > t.right.prio)
            t = t.rotateLeft();
      } else {
         keyFound = true;
         prevValue = t.value;
         t.value = node.value;
      }
      return t;
   }

   /* Searches for a node with this key. If found, deletes this
    * node and returns the updated treap.
    */
   private DobbNode delete(DobbOrdered key, DobbNode t) {
      if (t == null) return null;
      int comp = key.compareTo(t.key);
      if (comp < 0)
         t.left = delete(key, t.left);
      else if (comp > 0)
         t.right = delete(key, t.right);
      else {
         keyFound = true;
         prevValue = t.value;
         t = t.deleteRoot();
      }
      return t;
   }

   /**
    * Prints the treap on stderr, displaying the tree structure
    * and the priority numbers.
    */
   public synchronized void printDebug() {
      System.err.println("size: " + size);
      if (root != null) root.printDebug(0);
   }
}

/* An object of this class represents a node in a binary tree */
class DobbNode implements Cloneable
{
   DobbOrdered key;
   Object value;
   int prio;
   DobbNode left, right;

   /* Rotate this tree to the left
    */
   final DobbNode rotateLeft() {
      DobbNode temp = right;
      right = right.left;
      temp.left = this;
      return temp;
   }

   /* Rotate this tree to the right
    */
   final DobbNode rotateRight() {
      DobbNode temp = left;
      left = left.right;
      temp.right = this;
      return temp;
   }

   /* If one of the children is an empty tree, remove the root
    * and put the other child in its place. If both children
    * are nonempty, rotate the tree at the root so that the child
    * with the smallest prio-number comes to the top, then delete
    * the root from the other subtee.
    */
   final DobbNode deleteRoot() {
      DobbNode temp;
      
      if (left == null)
         return right;
      if (right == null)
         return left;
      if (left.prio < right.prio) {
         temp = rotateRight();
         temp.right = deleteRoot();
      } else {
         temp = rotateLeft();
         temp.left = deleteRoot();
      }
      return temp;
   }
   
   public String toString() {
      StringBuffer strbuf = new StringBuffer();
      
      if (left != null)
         strbuf.append(left.toString());
      strbuf.append(key + "=" + value + ", ");
      if (right != null)
         strbuf.append(right.toString());

      return strbuf.toString();
   }

   /* Creates a shallow copy of this tree.
    * The keys and the values themselves are not cloned.
    */
   protected Object clone() {
      try {
         DobbNode t = (DobbNode) super.clone();

         t.left = (left != null) ? (DobbNode) left.clone() : null;
         t.right = (right != null) ? (DobbNode) right.clone() : null;
         return t;
      } catch (CloneNotSupportedException e) {
	// Cannot happen
         throw new InternalError(e.toString());
      }
   }

   /* Print in sorted order, displaying the tree structure
    * and the priority numbers.
    */
   void printDebug(int level) {
      if (left != null)
         left.printDebug(level +1 );
      System.err.print("level " + level + "\t");
      for (int i = 0; i < level; i++)
         System.err.print("  ");
      System.err.println(key + "=" + value + " (" + prio + ")");
      if (right != null)
         right.printDebug(level + 1);
   }
}

/**
 * A treap enumeration class that returns the keys or elements
 * of a treap in sorted order.
 * This class should not be seen by the client.
 */
class TreapEnumeration implements Enumeration
{
   private Stack stack;
   private boolean keys;
   private boolean ascending;

   TreapEnumeration(DobbNode t, boolean keys, boolean ascending) {
      stack = new Stack();

      this.keys = keys;
      this.ascending = ascending;
      if (ascending)
         for ( ; t != null; t = t.left)
            stack.push(t);
      else // descending
         for ( ; t != null; t = t.right)
            stack.push(t);
   }

   public boolean hasMoreElements() {
      return !stack.empty();
   }

   public Object nextElement() {
      if (stack.empty())
         throw new NoSuchElementException("TreapEnumeration");

      DobbNode node = (DobbNode) stack.peek();
      if (ascending)
         if (node.right == null) {
            DobbNode t = (DobbNode) stack.pop();
            while (!stack.empty() &&
                   ((DobbNode) stack.peek()).right == t)
               t = (DobbNode) stack.pop();
         } else
            for (DobbNode t = node.right; t != null; t = t.left)
               stack.push(t);
      else // descending
         if (node.left == null) {
            DobbNode t = (DobbNode) stack.pop();
            while (!stack.empty() &&
                   ((DobbNode) stack.peek()).left == t)
               t = (DobbNode) stack.pop();
         } else
            for (DobbNode t = node.left; t != null; t = t.right)
               stack.push(t);

      return keys ? node.key : node.value;
   }
}
