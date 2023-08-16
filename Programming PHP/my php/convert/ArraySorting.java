
public class ArraySorting {
	public static int[] getNumbers()
	{
		final int SIZE = 10000;
		int[] array = new int[SIZE];
		for(int i=0;i<array.length;i++)
			array[i] = (int)(Math.random() * 10);
		return array;
	}
	public static void arrayMetrics()
	{
		final int RUNS = 100;
		System.out.println();
		System.out.println("Array Metrics:");
		
		//bubble
		long total2 = 0;
		for(int i=0;i<RUNS;i++)
		{
			long start = System.currentTimeMillis();
			bubbleSort(getNumbers());
			long end = System.currentTimeMillis();
			total2+= end - start;
		}
		
		//selection
		long total = 0;
		for(int i=0;i<RUNS;i++)
		{
			long start = System.currentTimeMillis();
			SortDescending2(getNumbers());
			long end = System.currentTimeMillis();
			total += end - start;
		}
		
		//insertion
		long total3 = 0;
		for(int i=0; i<RUNS; i++)
		{
			long start = System.currentTimeMillis();
			insertionSort(getNumbers());
			long end = System.currentTimeMillis();
			total3 += end - start;
		}
		System.out.println("Selection: " + total+" ms");
		System.out.println("Bubble: " + total2+" ms");
		//TODO fix the the out of bounds
		System.out.println("Insertion: " + total3+" ms");
	
	
	
	}
	public static void main(String[] args)
	{
		//Make an array of 10 random numbers between 0 and 9 and print them out
			int[] array = new int[10];
			for(int i=0; i<array.length;i++)
				array[i] = (int)(Math.random() * 10);
			for(int i : array)
				System.out.print(i+" ");
		//sort them descending and print them
			array = SortDescending2(array);
			System.out.println();
			for(int i : array)
				System.out.print(i+" ");
		//sort them Ascending and print them
			array = SortAscending(array);
			System.out.println();
			for (int i : array)
				System.out.print(i+ " ");
			
		//System.out.println("\nMax value: "+max);
			arrayMetrics();
				
	}
	
	//
	
	//3 versions of the selection sort
	//O(n^2)
	public static int[] SortDescending(int[] array)
	{
		int arraySorted[] = new int[array.length];
		for(int i=0; i<array.length;i++)
		{
			int max = Integer.MIN_VALUE;
			int index = -1;
			for(int j = 1;j<array.length;j++)
			{
				if(array[j] > max)
				{
					max = array[j];
					index = j;
				}
			}
			arraySorted[i] = array[index];
			array[index] = Integer.MIN_VALUE;//why would we do this
		}
		return arraySorted;
	}
	
	
	public static int[] SortAscending(int[] array)
	{
		int arraySorted[] = new int[array.length];
		for(int i=0; i<array.length;i++)
		{
			int max = Integer.MIN_VALUE;
			int index = 1;
			for(int j = 1;j<array.length;j++)
			{
				if(array[j] > max)
				{
					max = array[j];
					index = j;
				}
			}
			arraySorted[i] = array[index];
			array[index] = Integer.MIN_VALUE;//why would we do this
		}
		return arraySorted;
	}
	//class Person
		//public static Person[] SortAscendingPersons(Person[] array) 
			//public static int[] SortAscending(int[] array)
			// 0 1 2 3 4 5 6 7  8
			// 7 1 2 8 5 4 3 10 6
			// 10 1 2 8 5 4 3 7 6
			// selection sort
		public static int[] SortDescending2(int[] array)
		{
			//internal algorithm
			for(int i=0;i<array.length;i++)
			{
				int max=array[i];
				int index=i;
				for(int j=i;j<array.length;j++)
				{
					if(max < array[j])
					{
						max = array[j];
						index = j;
					}
				}
				//swap
				int temp = array[i];
				array[i] = max;
				array[index] = temp;
			}
		
			return array;
		
		}
	//other sorts:
	//O(n^2)
	// 0 1 2 3 4 5 6 7 8
	// 1 2 7 5 4 3 8 6 10
	//instead of finding the largest number it goes through and constantly compares
	//one versus the next 1, if it finds one larger then the other then it swaps
		
	static void bubbleSort(int arr[])
		{
			
		int n = arr.length;
			
		for(int i=0; i < n-1; i++)		 //n
				
			for(int j=0; j < n-i-1; j++) //n
					
				if(arr[j] > arr[j+1])
				
				{
				
					// swap temp and arr[i]
					
					int temp = arr[j];
					
					arr[j] = arr[j+1];
					
					arr[j+1] = temp;
					
				}
		}
		
	//insertion sort O(n^2)
		//Sorts from the beginning and each new element that it finds
		//it figures out where it should put it in its already sorted portion 
		//then inserts it there
		//Fastest of the three sort algorithms
	static void insertionSort(int arr[])
		
	{
			
		int n = arr.length;
		for(int i=1; i<n; ++i)          //n
		{
			int key = arr[i];
			int j = i-1;
			while(j>=0 && arr[j] > key) //n
			{
				arr[j+1] = arr[j];
				j = j-1;
			}
			arr[j+1] = key;
		}
	}
	//dynamic arrays
	
	//stacks and queues
	
	//recursion
	//O(nlog(n))							 worst
	//Merge Sort (>90%) (divide and conquer) nlogn
	//Quick Sort (1%) (pivoting)			 n^2
	//shell Sort (2%) (shelling)			 nlogn
	
	//memory Sort  (nlogn)
	//Heap sort  (reigning king of sorts)
		//graphs and trees
	//Champion of sorts(not "in-place" algorithm) (eats memory)

	
	//adding 
	//unsorted add: BigO(1)
	//sorted add: BigO(n)
	//
	//Linear search 
	//BigO(n)
	//Binary sort

}	