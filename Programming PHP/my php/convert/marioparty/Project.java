import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


//swing library for java
public class Project {
	public static void main(String[] args) throws FileNotFoundException
	{
		String fileName = "F:\\eclipse-jee-2021-12-R-win32-x86_64\\eclipse\\Workspace\\Project 1 MarioParty BoardGame\\src\\GameBoard.txt";
		// init gameBoard//
			GameBoard b = new GameBoard(fileName);
			
			Scanner s = new Scanner(System.in);
			
			b.findNewStarPlace();
			
			GameHumanPiece p = new GameHumanPiece("H",b.getNode(5, 5));
			GameSmartPiece ai = new GameSmartPiece("Ai",b.getNode(4, 2));
			
			
			
			while(true)
			{
				b.DrawBoard();
				
				boolean starFound = false;
				//tell the players to move, if they find the star then it needs to be reset to a new location)
				
				p.Move(GameBoard b, GameHumanPiece p, );
				
				ai.Move();
				
				//TODO if ai found star else player found star
				if (starFound)
				{
					b.findNewStarPlace();
					p.Move();
				}
				
				//output the coins and stars
				System.out.println("Player 1 coins: "+p.Coins+" stars: "+p.Stars);
				//System.out.println("Ai Player coins: "+ai.Coins+" stars: "+ai.Stars);
				
				
			}
			
		
		
		
	}

	

}

class GameBoard
{



	public GameBoard(String fileName) throws FileNotFoundException
	{
			int[] populate = new int[4];
			
			populate = setGameBoardPropertys(fileName);
			//TODO change this to an interface
			final int NODE_SIZE = populate[0];
			final int NODE_NUMBER=populate[1];
			final int ROW_NUMBER=populate[2];
			final int COL_NUMBER=populate[3];
			
		//loop through the generated 2d array of GameNode and set to blank
		//then set the GameNode status with the method readGameNodePaths 
		for(int i=0; i<GameNodes.length;i++)
			for(int j=0; j<GameNodes[i].length;j++)
			{
				GameNodes[i][j] = new GameNode(0,i,j);
			}
		
		//PointNodes
				GameNodes[1][1] = new RangeGameNode(1,4);
				GameNodes[0][1] = new RangeGameNode(-2,0);
				GameNodes[2][0] = new GameNode(-2,2,0);
				GameNodes[1][0] = new GameNode(5,1,0);
				GameNodes[1][2] = new GameNode(-2,1,2);
				GameNodes[1][1] = new GameNode(3,1,1);
				GameNodes[2][2] = new GameNode(6,2,2);
				
				//Blanks
				GameNodes[3][1].isBlank = true;
				GameNodes[1][3].isBlank = true;
				GameNodes[3][3].isBlank = true;
				GameNodes[0][5].isBlank = true;
				
				//TODO
				//Setup paths (normally this would be read into a file)
				//column 1
				GameNodes[0][0].NextNodes.add(GameNodes[1][0]);
				GameNodes[1][0].NextNodes.add(GameNodes[2][0]);
				GameNodes[1][0].NextNodes.add(GameNodes[1][1]);
				GameNodes[2][0].NextNodes.add(GameNodes[2][1]);
				GameNodes[2][0].NextNodes.add(GameNodes[3][0]);
				GameNodes[3][0].NextNodes.add(GameNodes[4][0]);
				GameNodes[4][0].NextNodes.add(GameNodes[4][1]);
				GameNodes[4][0].NextNodes.add(GameNodes[5][0]);
				GameNodes[5][0].NextNodes.add(GameNodes[5][1]);
				
				//column 2
				GameNodes[0][1].NextNodes.add(GameNodes[0][0]);
				GameNodes[1][1].NextNodes.add(GameNodes[0][1]);
				GameNodes[1][1].NextNodes.add(GameNodes[2][1]);
				GameNodes[2][1].NextNodes.add(GameNodes[2][2]);
				GameNodes[4][1].NextNodes.add(GameNodes[4][2]);
				GameNodes[5][1].NextNodes.add(GameNodes[5][2]);
				
				//column 3
				GameNodes[0][2].NextNodes.add(GameNodes[0][1]);
				GameNodes[0][2].NextNodes.add(GameNodes[0][3]);
				GameNodes[1][2].NextNodes.add(GameNodes[0][2]);
				GameNodes[1][2].NextNodes.add(GameNodes[1][1]);
				GameNodes[2][2].NextNodes.add(GameNodes[1][2]);
				GameNodes[2][2].NextNodes.add(GameNodes[3][2]);
				GameNodes[3][2].NextNodes.add(GameNodes[4][2]);
				GameNodes[4][2].NextNodes.add(GameNodes[4][3]);
				GameNodes[5][2].NextNodes.add(GameNodes[5][3]);
				
				//column 4
				GameNodes[0][3].NextNodes.add(GameNodes[0][4]);
				GameNodes[2][3].NextNodes.add(GameNodes[2][2]);
				GameNodes[4][3].NextNodes.add(GameNodes[4][4]);
				GameNodes[5][3].NextNodes.add(GameNodes[4][3]);
				
				//column 5
				GameNodes[0][4].NextNodes.add(GameNodes[1][4]);
				GameNodes[1][4].NextNodes.add(GameNodes[2][4]);
				GameNodes[2][4].NextNodes.add(GameNodes[2][3]);
				GameNodes[3][4].NextNodes.add(GameNodes[2][4]);
				GameNodes[4][4].NextNodes.add(GameNodes[3][4]);
				GameNodes[4][4].NextNodes.add(GameNodes[5][4]);
				GameNodes[5][4].NextNodes.add(GameNodes[5][5]);
				
				//Column 6
				GameNodes[5][5].NextNodes.add(GameNodes[4][5]);
				GameNodes[4][5].NextNodes.add(GameNodes[3][5]);
				GameNodes[3][5].NextNodes.add(GameNodes[2][5]);
				GameNodes[2][5].NextNodes.add(GameNodes[1][5]);
				GameNodes[1][5].NextNodes.add(GameNodes[1][4]);
		
	}
	
	

	GameNode  GameNodes[][] = new GameNode[6][6];
	
	

	public static int[] setGameBoardPropertys(String fileName) throws FileNotFoundException {
			
			int[] a = new int[4];
			String line = "";
			
			Scanner s = new Scanner(new File(fileName));
			
			
			
			for(int i=0; i<4; i++)
			{
				line = s.nextLine();
			line = line.substring((line.lastIndexOf(":")+1));
			
			a[i]=Integer.parseInt(line);
			
			
			}
				//for(int j =0; j<a.length;j++)
			//System.out.println(a[j]);
				
				return a;
			}
	
	public GameNode getNode(int row, int col)
	{
		return GameNodes[row][col];
	}
	
	public void UpdateBoard()
	{
		//TODO change to forEach
		
		for(int i=0; i<GameNodes.length;i++)
			for(int j=0; j<GameNodes[i].length; j++)
				GameNodes[i][j].points++;
	}
	
	public void findNewStarPlace()
	{
		while(true)
		{
			int row = (int)(Math.random()*GameNodes.length);
			int col = (int)(Math.random()*GameNodes[row].length);
			if(GameNodes[row][col].isBlank)
				continue; //choose again
			GameNodes[row][col].hasStar = true;
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
		for(int i=0; i<GameNodes.length-1;i++)
		{
			//TODO Can we use string buffer
			String line1 = "";
			String line2 = "";
			for(int j=0; j<GameNodes[i].length-1;j++)
			{
				GameNode one = GameNodes[i][j];
				GameNode two = GameNodes[i][j+1];
				GameNode three = GameNodes[i+1][j];
				
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
			
			line1 += FormToBoard(GameNodes[i][GameNodes[i].length-1].toString());
			GameNode one = GameNodes[i][GameNodes.length-1];
			GameNode three = GameNodes[i+1][GameNodes.length-1];
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
		for(int j=0; j<GameNodes[GameNodes.length-1].length-1;j++)
		{
			GameNode one = GameNodes[GameNodes.length-1][j];
			GameNode two = GameNodes[GameNodes.length-1][j+1];
			
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
		bottomText += FormToBoard(GameNodes[GameNodes.length-1][GameNodes.length-1].toString());
		
		System.out.println(bottomText);
		
		String breakBetweenDraws = "";
		
		for(int i=0; i<GameNodes.length*2-1;i++)
			breakBetweenDraws += FormToBoard("--------------");
		
		System.out.println(breakBetweenDraws);
	}
	
	public static String FormToBoard(String s)
	{
		s+="            ";
		return s.substring(0, getBoardParams("NODE_SIZE"));
	}
		
}

class GameNode
{
	public int row, col;
	public int points;
	
	//hasStar and isBlank are the ?qualities? of the basic GameNode, anything more complicated should be
		//done by extending the GameNode class
	
	public boolean hasStar;
	public boolean isBlank;
	public ArrayList<GameNode> NextNodes = new ArrayList<GameNode>();
	public ArrayList<GamePiece> Occupants = new ArrayList<GamePiece>();
	
	public ArrayList pathToGetTo;
	
	
	public GameNode()
	{
		this(false);
		
		points=0;
	}
	
	public GameNode(boolean isBlank)
	{
		this.isBlank = isBlank;
	}
	
	public GameNode(int points, int r, int c)
	{
		this.points = points;
		row = r;
		col = c;
	}
	
	//Star logic
	public boolean land(GamePiece p)
	{
		if(hasStar && p.Coins > 50)
		{
			System.out.println(p.Name + "Has Bought a Star");
			hasStar = false;
			p.Stars++;
			points = 0;
			p.Coins -= 50;
			return true;
		}else if(hasStar)	//and coins are not high enough
		{
			p.Coins -= 0;//STAR Tax
		}
		p.Coins += points;	//takes in account how many points the ai player already has
		points = -5;
		Occupants.add(p);
		
		if(p.Coins<0)
			p.Coins = 0;
		else if (p.Coins > 100)
			p.Coins = 100;
		
		return false;
	}
	
	public void unland(GamePiece p)
	{
		Occupants.remove(p);
		
	}
	
	public String toString()
	{
		if(hasStar)
			return GameBoard.FormToBoard("* "+Occupants.toString());
		if(isBlank)
			return Board.FormToBoard("  ");
			
			return GameBoard.FormToBoard(points+" "+Occupants.toString());
	}
	
	//ai finds the path with the greatest ammount of coins 
		//thanks to this handy method
	
	public int getPoints(int playerCoinAmount)
	{
		//TODO fix this nessted if 
		if(hasStar)
		{
			if(playerCoinAmount > 50)
				return 1000;
			else
				return -25;
		}
		return points;
	}
}
class RangeGameNode extends GameNode
{
	int pointMax;
	int pointMin;
	
	public RangeGameNode(int max, int min)
	{
		pointMax = max;
		pointMin = min;
		points = (max+min) / 2;	//used for Ai Calculations
	}
	
	public boolean land(GamePiece p)
	{
		p.Coins += (int)(Math.random() * (pointMax-pointMin)+ pointMin);
		//Occupants.add(p);
		return false;
	}
	public void UnLand(GamePiece p)
	{
		Occupants.remove(p);
	}
}
class miniGameNode extends GameNode
{
	
}
class GamePiece
{
	public int Coins;
	public int Stars;
	public GameNode Location;
	public String Name;
	
	public GamePiece(String n)
	{
		Name = n;
	}
	
	public GamePiece()
	{
		this("No name");
	}
	
	public String toString()
	{
		return Name;
	}
	
	public boolean Move()
	{
		return false;
	}
}

class GameHumanPiece extends GamePiece
{
	GameHumanPiece(String Name,GameNode Node)
	{
		super(Name);
		
		this.Coins=0;
		this.Stars=0;
		this.Location=Node;
		
		Node.land(this);	//Land of the starting GameNode
		
	}
	
	//TODO create methos to do only a single thing
		//ex Land Unland setLocation
	
	public static ArrayList<ArrayList<GameNode>> getPossiblePaths(GameNode location, int step) 
	{
		ArrayList<ArrayList<GameNode>> allPossiblePaths = new ArrayList<ArrayList<GameNode>>();
		ArrayList<GameNode> open = new ArrayList<>();
		open.add(location);
		location.pathToGetTo = new ArrayList<>(Arrays.asList(location));
		while (!open.isEmpty())
		{
			GameNode current = open.remove(0);
			for (GameNode n : current.NextNodes)
			{
				ArrayList<GameNode> newPath = new ArrayList<>(current.pathToGetTo);
				newPath.add(n);
				n.pathToGetTo = newPath;
				System.out.println(n);
				
				if (newPath.size() >= step)
					allPossiblePaths.add(newPath);
				else
					open.add(n);				
			}
		}
		
		return allPossiblePaths;
	}
	
	
	public static void move()
	{
		
	}
}


class GameSmartPiece extends GamePiece
{
	
	GameSmartPiece(String name, GameNode Node)
	{
		super(name);
		
		this.Coins=0;
		this.Stars=0;
		this.Location=Node;
		
		Node.land(this);	//Land of the starting GameNode
	}
	
	
	public boolean Move()
	{
		
		ArrayList<ArrayList<GameNode>> paths = getAllPossiblePaths(1);//im thinking int steps ahead of you
		
		for(ArrayList<GameNode> p : paths)
			System.out.println(p);
		
		
		
		
		while(!paths.isEmpty())
		{
			
			int i=0;
			if(paths.get(i)==Location.NextNodes)
			{
				System.out.print("Yes");
				setLocation(this,Location.NextNodes.get(i));
				
				return false;
			}
			else
			
			setLocation(this,Location.NextNodes.get(i));
			
			
			System.out.println(++i);
			
			System.out.println(Location.NextNodes);

				paths.remove(0);
				
				return false;
		}

		return false;
	}
	
	 
	public ArrayList<ArrayList<GameNode>> getAllPossiblePaths(int step) 
	{
		ArrayList<ArrayList<GameNode>> paths = new ArrayList<ArrayList<GameNode>>();
		ArrayList<GameNode> open = new ArrayList<>();
		this.Location.pathToGetTo = new ArrayList<>(Arrays.asList(Location));
		
		open.add(Location);
		
		System.out.println("the path to the next node:"+Location.pathToGetTo);
		
		while(!open.isEmpty())
		{
			GameNode c = open.get(0);//current  node
			open.remove(0);
			
			if(c.pathToGetTo.size() > step)
			{
				paths.add(c.pathToGetTo);
			}else
			{
				for(GameNode x : c.NextNodes)
				{
					ArrayList<GameNode> connectedPath = new ArrayList<GameNode>(c.pathToGetTo);
					connectedPath.add(x);
					x.pathToGetTo = connectedPath;
					open.add(x);
				}
			}
		}
		return paths;
	}
}
