
public class BinarySearch {
	public static void main(String[] args)
	{
		int[] numbers = {11,5,2,7,8,3,12,4,10};
				//			   0 1 2 3  4  5  6  7		
		int[] numbersSorted = {2,3,5,7,10,12,22,85};
		//findNumbersThatSumToTheKey(12,numbers);
	}//end of main 
	
	//BigO(n) - complexity through a critical area
	//if searching through objects we would return null
	public static int sequentialSearch(int val, int[] array)
	{
		for(int i=0;i<array.length;i++)
			if(array[i]==val)
				return i;
		return -1;
	}
	//						 0 1 2 3 4  5  6  7
	//int[] numbersSorted = {2,3,5,7,10,12,22,85};
	//data MUST be sorted
	//BigO(log_2(n)) log_2(1000) == 10 loops (worst case)
	public static int binarySearchWorking(int key, int[] array)
	{
		//note that these are indexes 
		int lo = 0;
		int hi = array.length - 1;
		//while the indexes have not crossed
		while(lo <= hi)
		{
			int mid = lo +((hi-lo) / 2);
			if(key == array[mid])
				return mid;//if it is the mid
			if(key < array[mid])//if it is greater than the mid
				hi = mid - 1;
			else //if it is greater than the mid
				lo = mid + 1;
		}
		return -1; //not found
	}
	// 			unsorted array 				sorted array
	// add			1							log(N)
	// get          n							log(N)
	
	//which should you use in a job
	//how often are you adding vs getting
	// Bank: 10,000s of transactions a second, they occasionally 2 or 3 times a day have to access
	//Insurance: add 1 new customer every minute. 1000's of times a minute
	
	//requirements engineering 
	
	
	
	
	public static void findNumbersThatSumToTheKey(int key, int[] array)
	{
		
	}
	
	
}
