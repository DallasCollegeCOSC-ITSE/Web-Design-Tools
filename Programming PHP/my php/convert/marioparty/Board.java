import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

public class Board {
	//TODO fix the hard code
	Node Nodes[][] = new Node[Constrants.ROW_NUMBER][Constrants.COL_NUMBER];
		final int NODE_SIZE = Constrants.NODE_SIZE;
		final int NODE_NUMBER=Constrants.NODE_NUMBER;
		final int ROW_NUMBER= Constrants.ROW_NUMBER;
		final int COL_NUMBER=Constrants.COL_NUMBER;
		
		
	public Board() 
	{
		//loop through the generated 2d array of GameNode and set to blank
				//then set the GameNode status with the method readGameNodePaths 
		
		for(int i=0; i<Nodes.length;i++)
			for(int j=0; j<Nodes[i].length;j++)
			{
				Nodes[i][j] = new Node(0,i,j);
			}
			
			BoardProperties.fillNode(Nodes);
			
			BoardProperties.setNodePath(Nodes);
	}
	
	
	
	public Node getNode(int row, int col)
	{
		return Nodes[Constrants.ROW_NUMBER][Constrants.COL_NUMBER];
	}
	
	public void UpdateBoard()
	{
		//TODO change to forEach
		
		for(int i=0; i<Nodes.length;i++)
			for(int j=0; j<Nodes[i].length; j++)
				Nodes[i][j].points++;
	}
	
	public void findNewStarPlace()
	{
		while(true)
		{
			int row = (int)(Math.random()*Nodes.length);
			int col = (int)(Math.random()*Nodes[row].length);
			if(Nodes[row][col].isBlank)
				continue; //choose again
			Nodes[row][col].hasStar = true;
			break;
		}
	}
	static public int getBoardParams(String name)
	{
		if(name.equals("NODE_SIZE"))
			return 11;
		else if(name.equals("NODE_NUMBER"))
			return 9;
		else if(name.equals("ROW_NUMBER"))
			return 6;
		else if(name.equals("ROW_NUMBER"))
			return 6;
		return 0;
	}
	
	public void DrawBoard()
	{
		//draw all the lines except the bottom one
		//String one: GameNode and relationships horizontal
		//String two: relationships vertical
		//one - two
		//|
		//three
		
		//TODO can I split this up and make it better
		for(int i=0; i<Nodes.length-1;i++)
		{
			//TODO Can we use string buffer
			String line1 = "";
			String line2 = "";
			for(int j=0; j<Nodes[i].length-1;j++)
			{
				Node one = Nodes[i][j];
				Node two = Nodes[i][j+1];
				Node three = Nodes[i+1][j];
				
				//draw GameNode 1 
				line1 += FormToBoard(one.toString());
				
				//Draw relationship between 1 and 2
				
				if(one.NextNodes.contains(two))
					line1 += FormToBoard(">>");
				else if(two.NextNodes.contains(one))
					line1 += FormToBoard("<<");
				else
					line1 += FormToBoard("  ");
				
				//draw relationship between 1 and 3 
				
				if(one.NextNodes.contains(three))
					line2 += FormToBoard(" \\/");
				else if (three.NextNodes.contains(one))
					line2 += FormToBoard(" /\\");
				else
					line2 += FormToBoard("  ");
				
				//there need to be an extra space on line 2
				
				line2 += FormToBoard("  ");
				
			}
			
			line1 += FormToBoard(Nodes[i][Nodes[i].length-1].toString());
			Node one = Nodes[i][Nodes.length-1];
			Node three = Nodes[i+1][Nodes.length-1];
			if(one.NextNodes.contains(three))
				line2 += FormToBoard(" \\/");
			else if(three.NextNodes.contains(one))
				line2 += FormToBoard(" /\\");
			else
				line2 += FormToBoard("  ");
			
			System.out.println(line1);
			System.out.println();
			System.out.println(line2);
			System.out.println();
		}
		
		//draw the final line of GameNode and relationships
		
		String bottomText = "";
		for(int j=0; j<Nodes[Nodes.length-1].length-1;j++)
		{
			Node one = Nodes[Nodes.length-1][j];
			Node two = Nodes[Nodes.length-1][j+1];
			
			//draw GameNode 1
			
			bottomText += FormToBoard(one.toString());
			
			//draw relationship between 1 and 2
			if(one.NextNodes.contains(two))
					bottomText += FormToBoard(">>");
			else if (two.NextNodes.contains(one))
				bottomText += FormToBoard("<<");
			else
				bottomText += FormToBoard("  "); 
		}
		
		//draw the very last GameNode
		bottomText += FormToBoard(Nodes[Nodes.length-1][Nodes.length-1].toString());
		
		System.out.println(bottomText);
		
		String breakBetweenDraws = "";
		
		for(int i=0; i<Nodes.length*2-1;i++)
			breakBetweenDraws += FormToBoard("--------------");
		
		System.out.println(breakBetweenDraws);
	}
	
	public static String FormToBoard(String s)
	{
		s+="            ";
		return s.substring(0, getBoardParams("NODE_SIZE"));
	}
	
	
}