package inClassNotes;

public class ExampleArea {
	public static void main (String k[]) {
		
		int arr[] = {1,3,6,7,8,9,14,18,19,23,29,35};
		System.out.println(search(arr,29));
		System.out.println(binarySearch(arr,29));
		//quicksort (worst case is when the data is reversed)
	}
	//BigO to compare algorithms
	//3 cases: best case, average case, worst case
	//limit as N (size) approaches infinity
	//O(1), O(logN), O(N), O(NlogN), O(N^2),O(2^N)
	//eliminate constants O(N+7) (remove that 7 beause it is insignificant)
	//eliminate coefficients O(N)
	public static int search(int arr[], int value)
	{
		for(int i = 0;i<arr.length;i++)
		{
			if(arr[i] == value)
				return i;
			
		}
	return -1;
	}
	public static int binarySearch(int arr[], int value)
	{
		int lo=0;
		int hi = 0;
		while(lo<=hi)
		{
			int mid = (lo+hi)/2;
			if(arr[mid]==value)
				return mid;
			else if (arr[mid] < value)
				lo = mid+1;
			else
				hi = mid+1;
			
		
		}
		return -1;
	}
	public static int getSmallest(int arr[],int start)
	{
		//smallest value
				int small = arr[0];
				int pos = 0;
				for(int i = start+1;i<arr.length;i++)
					if(arr[i] < small)
					{
						small = arr[i];
						pos = i;
					}
			return pos;
	}
	//O(
	public static void selectionSort(int arr[])
	{
		for(int i=0; i<arr.length;i++)
		{
			
		
		int pos = getSmallest(arr,0);
		
		//swapping value
		int temp = arr[0];
		arr[0] = arr[pos];
		arr[pos] = temp;
		}
	}
	//O(n^2) best O(n)
	static void bubbleSort(int arr[])
	{
		
	int n = arr.length;
		
	for(int i=0; i < n-1; i++)		 //n
	{
		boolean done = false;
		for(int j=0; j < n-i-1; j++) {
			done = true;
			if(arr[j] > arr[j+1])
				{
				done = false;
				// swap temp and arr[i]
				int temp = arr[j];
				arr[j] = arr[j+1];
				arr[j+1] = temp;
			
				}
		
			}
	
		}
	}
	static void insertionSort(int arr[])
	{
		int n = arr.length;
		for(int i=1; i<n; i++)
		{
			int key = arr[i];
			int j = i-1;
			while(j>=0 && arr[j] > key) {
				arr[j+1] = arr[j];
				j = j-1;
			}
			arr[j+1] = key;
		}
	}
}

//recursion
	//O(nlog(n))							 worst
	//Merge Sort (>90%) (divide and conquer) nlogn
	//Quick Sort (1%) (pivoting)			 n^2
	//shell Sort (2%) (shelling)			 nlogn
	
//memory Sort  (nlogn)
	//Heap sort  (reigning king of sorts)
		//graphs and trees
