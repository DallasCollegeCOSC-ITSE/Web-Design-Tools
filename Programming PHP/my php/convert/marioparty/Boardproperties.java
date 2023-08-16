import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BoardProperties {
	int[] populate = new int[4];
	
	BoardProperties(String fileName) throws FileNotFoundException
	{
		populate = setGameBoardPropertys(fileName);
	}
	
	public static int[] setGameBoardPropertys(String fileName) throws FileNotFoundException {
		
		int[] a = new int[4];
		String line = "";
		
		Scanner s = new Scanner(new File(fileName));
		
		System.out.println("");
		
		for(int i=0; i<4; i++)
		{
			line = s.nextLine();
		line = line.substring((line.lastIndexOf(":")+1));
		
		a[i]=Integer.parseInt(line);
		
		
		}
			for(int j =0; j<a.length;j++)
		System.out.println(a[j]);
			
			return a;
		}
	
	public int[] getGameBoardPropertys()
	{
		return populate;
	}
	
	public static void fillNode(Node[][] n )
	{
		
		//PointNodes
		n[1][1] = new RangeNode(1,4);
		n[0][1] = new RangeNode(-2,0);
		n[2][0] = new MiniGameNode(20,2,0);
		n[1][0] = new Node(5,1,0);
		n[1][2] = new Node(-2,1,2);
		n[1][1] = new Node(3,1,1);
		n[2][2] = new Node(6,2,2);
		
		//Blanks
		n[3][1].isBlank = true;
		n[1][3].isBlank = true;
		n[3][3].isBlank = true;
		n[0][5].isBlank = true;
	}
	
	// Node one connects to node2
	public static void setNodePath(Node[][] n)
	{
		
		//column 1
		n[0][0].NextNodes.add(n[1][0]);
		n[1][0].NextNodes.add(n[2][0]);
		n[1][0].NextNodes.add(n[1][1]);
		n[2][0].NextNodes.add(n[2][1]);
		n[2][0].NextNodes.add(n[3][0]);
		n[3][0].NextNodes.add(n[4][0]);
		n[4][0].NextNodes.add(n[4][1]);
		n[4][0].NextNodes.add(n[5][0]);
		n[5][0].NextNodes.add(n[5][1]);
		
		//column 2
		n[0][1].NextNodes.add(n[0][0]);
		n[1][1].NextNodes.add(n[0][1]);
		n[1][1].NextNodes.add(n[2][1]);
		n[2][1].NextNodes.add(n[2][2]);
		n[4][1].NextNodes.add(n[4][2]);
		n[5][1].NextNodes.add(n[5][2]);
		
		//column 3
		n[0][2].NextNodes.add(n[0][1]);
		n[0][2].NextNodes.add(n[0][3]);
		n[1][2].NextNodes.add(n[0][2]);
		n[1][2].NextNodes.add(n[1][1]);
		n[2][2].NextNodes.add(n[1][2]);
		n[2][2].NextNodes.add(n[3][2]);
		n[3][2].NextNodes.add(n[4][2]);
		n[4][2].NextNodes.add(n[4][3]);
		n[5][2].NextNodes.add(n[5][3]);
		
		//column 4
		n[0][3].NextNodes.add(n[0][4]);
		n[2][3].NextNodes.add(n[2][2]);
		n[4][3].NextNodes.add(n[4][4]);
		n[5][3].NextNodes.add(n[4][3]);
		
		//column 5
		n[0][4].NextNodes.add(n[1][4]);
		n[1][4].NextNodes.add(n[2][4]);
		n[2][4].NextNodes.add(n[2][3]);
		n[3][4].NextNodes.add(n[2][4]);
		n[4][4].NextNodes.add(n[3][4]);
		n[4][4].NextNodes.add(n[5][4]);
		n[5][4].NextNodes.add(n[5][5]);
		
		//Column 6
		n[5][5].NextNodes.add(n[4][5]);
		n[4][5].NextNodes.add(n[3][5]);
		n[3][5].NextNodes.add(n[2][5]);
		n[2][5].NextNodes.add(n[1][5]);
		n[1][5].NextNodes.add(n[1][4]);
	}
	
	
	
}
