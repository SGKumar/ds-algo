package tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

// Max Heap
public class MaxHeap
{
	// capacity >= size
	int[] _heap;
	int capacity;
	int size;
	//Comparator<int> compare;

	public MaxHeap()//Comparator<int> compare
	{
		size = 0;
		capacity = 0;
		//this.compare = compare;
	}

	public MaxHeap(int capacity)//Comparator<int> compare
	{
		size = 0;
		this.capacity = capacity;
		_heap = new int[capacity];
		//this.compare = compare;
	}

	// parent of node at location "i"
	public int parent(int i)
	{
		if(i <= 1 || i > size)
			return -1;
		return _heap[i/2];
	}

	public int left(int i)
	{
		if(i<<1 > size)
			return -1;
		return i<<1;
	}
	public int right(int i)
	{
		if((i<<1)+1 > size)
			return -1;
		return (i<<1)+1;
	}

	// Now it will return min
	public int max()
	{
		if(0 == size)
			return -1;
		return _heap[1];
	}
	public int delMax()
	{
		if(0 == size)
			return -1;
		int retVal = _heap[1];

		_heap[1] = _heap[size--];
		sink(1);

		return retVal;
	}
	public int minInMaxHeap()
	{
		int min = Integer.MAX_VALUE;
		for(int i = size/2; i <= size; i++)
		{
			if(-1 == compare(_heap[i], min))
			{
				min = _heap[i];
			}
		}
		return min;
	}

	// This is max heap
	private void sink(int index)
	{
		while(2*index <= size)
		{
			int child = left(index);

			if(child < size && -1 == compare(_heap[child], _heap[child+1]))
			{
				child++;
			}
			if(1 == compare(_heap[index], _heap[child]))
				break;

			swap(index, child);
			index = child;
		}
	}

	private void swap(int src, int dst)
	{
		int temp = _heap[dst];
		_heap[dst] = _heap[src];
		_heap[src] = temp;
	}

	private int compare(int val1, int val2)
	{
		if(val1 < val2)
			return -1;
		else if(val1 == val2)
			return 0;
		else
			return 1;
	}
	/*public static Comparator<int> MaxHeap = new Comparator<int>()
	{
		public int compare(int val1, int val2)
		{
			if(val1 < val2)
				return -1;
			else if(val1 == val2)
				return 0;
			else
				return 1;
		}
	};
	public static Comparator<int> MinHeap = new Comparator<int>()
	{
		public int compare(int val1, int val2)
		{
			if(val1 > val2)
				return -1;
			else if(val1 == val2)
				return 0;
			else
				return 1;
		}
	};*/

	public void insert(int val)
	{
		// ensure capacity
		// assume no duplicates
		_heap[++size] = val;
		swim(size);
	}

	private void swim(int index)
	{
		while(index > 1 && 1 == compare(_heap[index], _heap[index>>1]))
		{
			swap(index, index >>1);

			index >>= 1;
		}
	}

	public void buildHeap(int[] arr)
	{
		// assume no duplicates
		// Just copy array
		if(size == 0)
		{
			_heap = new int[arr.length+1];
			size = capacity = arr.length;
		}
		else
		{
			size = arr.length;
		}

		for(int i = 0; i < arr.length; i++)
			_heap[i+1] = arr[i];

		// HEAPIFY !!!
		// Leaf nodes already have "heap" property, so just sink non-leaf nodes
		for(int j = size/2; j > 0; j--) {
			sink(j);
			System.out.println("After sink " + j + ": " + Arrays.toString(_heap));
		}
	}

	// current one is Max Heap
	public void heapSort(int[] arr)
	{
		buildHeap(arr);
		int _size = size; // Backup size to restore later

		for(int i = 1; i <= _size; i++)
		{
			swap(1, size); // 1 always has Max
			size--;

			sink(1);
		}
		// Restore size
		size = _size;
	}

	public String toString()
	{
		StringBuilder str = new StringBuilder(256);
		for(int i = 1; i <= size; i++)
		{
			if(i > 1)
				str.append(",");
			str.append(_heap[i]);
		}
		return str.toString();
	}

    public static void main(String args[])
    {
		MaxHeap heap = new MaxHeap(65);//MaxHeap.MaxHeap);
		heap.insert(65);
		heap.insert(74);
		heap.insert(67);
		heap.insert(69);
		heap.insert(73);
		heap.insert(72);
		heap.insert(68);
		heap.insert(66);
		heap.insert(70);

		System.out.println(heap.toString());
		System.out.println("Min in MAX heap: " + heap.minInMaxHeap());
		System.out.println("Max is : " + heap.max());
		System.out.println("Deleting max : " + heap.delMax());
		
		System.out.println("Heap after deleting max " + heap.toString());
		System.out.println("Min in MAX heap: " + heap.minInMaxHeap());

		//char arr[] = {'A', 'C', 'D', 'B', 'E', 'F', 'G'};
		int arr[] = {'I', 'H', 'G', 'F', 'E', 'D', 'C', 'B', 'A'};
		MaxHeap heap2 = new MaxHeap();
		heap2.buildHeap(arr);
		System.out.print("Array is ");
		System.out.println(Arrays.toString(arr));
		System.out.println("Arr.length " + arr.length + " heap " + heap2.toString());

		MaxHeap heap21 = new MaxHeap();
		heap21.heapSort(arr);
		System.out.println("Heap after sort " + heap21.toString());
		
		Random rnd = new Random();
		int arr1[] = new int[10];
		/*for(int i = 0; i < 10; i++) {
			//System.out.printf("i %d random %d\n", i, rnd.nextInt(1 << 10));
			arr1[i] = rnd.nextInt(1 << 10);
		}*/
		arr1[0] = 247;
		arr1[1] = 767;
		arr1[2] = 502;
		arr1[3] = 902;
		arr1[4] = 197;
		arr1[5] = 736;
		arr1[6] = 462;
		arr1[7] = 501;
		arr1[8] = 326;
		arr1[9] = 660;
		
		System.out.println(Arrays.toString(arr1));
		
		MaxHeap heap3 = new MaxHeap();
		heap3.buildHeap(arr1);
		System.out.print("Array is ");
		System.out.println("Arr.length " + arr1.length + " heap " + heap3.toString());


    }
}



