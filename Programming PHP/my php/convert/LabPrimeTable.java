
public class LabPrimeTable {

	public static void main(String args[])
	{
		
		 int SIZE_1 = 4;
		//Part 1
		 for (int i = 1;i <= SIZE_1; i++)
		{
			int lcv = i;
			
			print(lcv, "X");
			
			System.out.println(" "); 
		}
		
		 for (int  j = 1; j <= SIZE_1-1; j++)
		{
			   int lcv = j;
			       
			   printSpace(lcv);
			       
			  int lcv2 = SIZE_1-j;
			       
			  print(lcv2, "X");
			       	
			  System.out.println(" ");
		}
		     
		      System.out.println(" ");
		      System.out.println(" ");
		 
		//Part 2
		
		//two ways of solving
		      //1. load a string var with the correct amount of elements and spaces, then output the string to console
		      //Source ~ https://stackoverflow.com/questions/41164491/print-the-letter-x-on-a-background-console
		      
		      //2. Directly System.out.print every individual element (dictated by selection) , seems slow and lame juxtaposed to the above source but i'll go with
		
		int SIZE_2 = 10;
		int THICK_2 = 2;
		
		
		int start = 0;
		
		int end = SIZE_2 - 1;
		
		
		
		for(int X  = 0; X < SIZE_2; X++)
		{
			
			for(int Y = 0; Y < SIZE_2; Y++)
			{
				if( Y == start || Y == end)
				{
					System.out.print("\\ ");
				}
				else if (X == start || X == end)
				{
					System.out.print(" ");
				}
				else
					System.out.print(" ");
			}
				
			start++;
			end--;
			System.out.println(" ");
		}
		   
		      System.out.println(" ");
		      System.out.println(" ");
		// Part 3
		/*
		 * 
		 
			int SIZE = 20;
			
			
			
			float start1 = SIZE *.15f;
			float stop1 = SIZE* .25f;
			
			float start2 = SIZE *.15f;
			float stop2 = SIZE* .25f;
			
			
			for(int y=0; y<SIZE;y++)
			{
				for(int x=0;x<SIZE;x++)
				{
					float dist = distance(x,y,SIZE/2,SIZE/2);
					if (dist > start1 && dist < stop1)
						System.out.print("O");
					else if (dist > start2 && dist < stop2)
						System.out.print("O");
					//else
						//System.out.print("\");
				}
			}
			System.out.println();*/
		}//End of main
	public static float distance(int x1, int y1, int x2, int y2)
	{
		return (float)Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
	}
    
	//print any amount of String function
	public static void print (float num, String car)
	{
		for (int i = 1; i <= num; i++)
		{
			System.out.print(car);
		}
	}

	//Print any amount of empty spaces function
	public static void printSpace (int num)
	{
		for (int i = 1; i <= num; i++)
		{
			System.out.print(" ");
		}
	}
	
	
}

