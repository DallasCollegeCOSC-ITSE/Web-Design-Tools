package inClassNotes;

public class ExampleSorting {
public static void main(String args[])
{
	
}
public static void selectionSort(int arr[])
{
	//smallest value
	int small = arr[0];
	int pos = 0;
	for(int i = 1;i<arr.length;i++)
		if(arr[i] < small)
		{
			small = arr[i];
			pos = i;
		}
	System.out.println(small);
}
}
