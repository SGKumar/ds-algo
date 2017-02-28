package harry;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Arrays;
import java.util.HashMap;

// All HarryHe book problems/examples
public class HarryHe
{
    private int[] intValues;
    private int n;                       // number of items on priority queue

	// Find duplicate in n-array 0..n-2
	public static boolean q7findNumInIncRowColMatrix(int val)
	{
		int[][] val2DMatrix = {{1, 3, 4, 5}, {7, 9, 10, 11}, {12, 14, 16, 17}, {22, 24, 33, 35}};
		int rows = val2DMatrix.length;
		int cols = val2DMatrix[0].length;
		int start = 0, end = rows*cols -1;
		
		while(start <= end)
		{
			int mid = start + (end - start)/2;
			int row = mid/cols;
			int col = mid%cols;
			if(val2DMatrix[row][col] == val)
			{
				System.out.println("q7findNumInIncRowColMatrix: found " + val + " in (Row,Col) increasing 2d array");
				return true;
			}

			if(val2DMatrix[row][col] > val)
			{
				end = mid-1;
			}
			else
			{
				start = mid+1;
			}
		}
		System.out.println("q7findNumInIncRowColMatrix: NOT found " + val + " in (Row,Col) increasing 2d array");
		return false;
	}

	// 
	public static void s11findEarliestRepeat()
	{
		int[] intVals = {3, 2, 1, 2, 4, 2, 4, 3}; // Answer here is 3 NOT 2 or 4
		int val;
		System.out.println("Int Vals " + Arrays.toString(intVals));
		//Arrays.sort(intVals);
		
		HashMap<Integer, Integer> mapRepeats = new HashMap<>();
		for(int i = 0; i < intVals.length; i++)
		{
			if(mapRepeats.containsKey(intVals[i]))
			{
				val = (int)mapRepeats.get(intVals[i]);
				if(val > 0)
				{
					mapRepeats.put(intVals[i], -1 * val);
				}
			}
			else
			{
				mapRepeats.put(intVals[i], i+1);
			}
		}
		
		for(int i = 0; i < intVals.length; i++)
		{
			if(mapRepeats.containsValue(-1*(i+1)))
			{
				System.out.println("First in array among repeated elements : " + intVals[i]);
				break;
			}
		}
	}
	
	public static void s17findMissingNum()
	{
		int[] intVals = {3, 2, 5, 4, 1, 7, 8, 12, 9, 10, 11}; // 6 missing
		int xorVals = 0, xorNums = 0, i = 0;
		
		for(i = 1; i <= intVals.length; i++)
		{
			xorVals ^= intVals[i-1];
			xorNums ^= i;
		}
		// The one missing no's count from the array 
		xorNums ^= i;
		System.out.println("Missing num " + (xorVals ^ xorNums));
		
	}

	public static void s21find2RepeatedNums()
	{
		int[] intVals = {3, 2, 5, 4, 1, 6, 7, 8, 6, 12, 9, 3, 10, 11}; // 3 & 6 repeat
		int[] countNums = new int[12+1];
		
		for(int i = 0; i < 12; i++)
		{
			countNums[intVals[i]]++;
			if(countNums[intVals[i]] == 2)
			{
				System.out.println("Repeated num " + intVals[i]);
			}
		}

	}

	public static void s22find2RepeatedNums()
	{
		int[] intVals = {12, 2, 5, 4, 1, 6, 7, 8, 6, 12, 9, 3, 10, 11}; // 3 & 6 repeat
		int n = 12, iXorVals = 0, iRightMostBit = 0, iRepVal1 = 0, iRepVal2 = 0;
		
		for(int i = 0; i < intVals.length; i++)
		{
			iXorVals ^= intVals[i];
		}
		for(int i = 1; i <= n; i++)
		{
			iXorVals ^= n;
		}
		iRightMostBit = iXorVals & ~(iXorVals - 1);
		
		for(int i = 0; i < intVals.length; i++)
		{
			if((intVals[i] & iRightMostBit) > 0)
			{
				iRepVal1 ^= intVals[i];
			}
			else
			{
				iRepVal2 ^= intVals[i];
			}
		}
		for(int i = 1; i <= n; i++)
		{
			if((i & iRightMostBit) > 0)
			{
				iRepVal1 ^= i;
			}
			else
			{
				iRepVal2 ^= i;
			}
		}
		System.out.println("Repeated nums " + iRepVal1 + " " + iRepVal2);
	}


	public static void s26find2NumsGivenSum()
	{
		int[] intVals = {3, 2, 5, 4, 1, 6, 7, 8, 6, 12, 9, 3, 10, 11};
		
		// Sum is 6
		int k = 6, sum = 0;
		Arrays.sort(intVals);
		for(int i = 0, j = intVals.length -1; i < j;)
		{
			sum = intVals[i] + intVals[j];
			if(sum == k)
			{
				System.out.println(intVals[i] + " + " + intVals[j] + " = " + k);
				break;
			}
			if(sum > k)
			{
				j--;
			}
			else
			{
				i++;
			}
		}

	}

	public static void s28find2NumsGivenSum()
	{
		int[] intVals = {3, 2, 5, 4, 1, 6, 7, 8, 6, 12, 9, 3, 10, 11};
		
		// Sum is 6
		int k = 6;
		HashMap<Integer, Integer> mapNums = new HashMap<>();
		for(int i : intVals)
		{
			if ((k != 2 *i) && mapNums.containsKey(k - i))
			{
				System.out.println("Sum found " + (k - i) + " + " + i + " = " + k);
				break;
			}
			else
			{
				if (!mapNums.containsKey(i))
					mapNums.put(i, 1);
			}
		}

	}

	public static void main(String[] args)
	{
		q7findNumInIncRowColMatrix(35);
		q7findNumInIncRowColMatrix(15);
	}

}
