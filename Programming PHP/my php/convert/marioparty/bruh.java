import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class bruh
{
	public static void main(String[] args) 
	{
		Board b = new Board();
		HumanPiece p1 = new HumanPiece("P1", b);
		SmartPiece AI = new SmartPiece("AI",b);
		Scanner userInput = new Scanner(System.in);
		b.findNewStarPlace();
		b.DrawBoard();
		
		//b.NumbeBoard();
		ArrayList<Piece> listOfPieces = new ArrayList<>();
		listOfPieces.add(p1);
		listOfPieces.add(AI);
		
		
		for(int i = 0; i < 25; i++)
		{
			Piece.checkTheirLocation(listOfPieces, b, i);
			
			System.out.println("Please type in an move");
			int nextLine = userInput.nextInt();
			
			if(p1.Move(b.Nodes, nextLine, b))
			{
				AI.updatePath();
				b.DrawBoard();
			}
			
			b.UpdateBoard();
			
		}
		/*
		while(true)
		{
			b.DrawBoard();
			boolean starFound = false;
			//tell the players to move, if they find the star then it needs to be reset to a new location)
			///starFound = player1.Move();
			
			if (starFound)
			{
				b.findNewStarPlace();
			}
			
			//output the coins and stars
			//System.out.println("Player 1 coins: "+player1.coins+" stars: "player1.stars);
			
			b.UpdateBoard();		
		}*/
	}
}
//Simple board class
class Board
{
	//these can be modified for different board sizes (they should be specified in a file for the board information)
	public static final int NODE_SIZE = 11;
	public static final int NODE_NUMBER = 9;
	public static final int ROW_NUMBER = 6;
	public static final int COL_NUMBER = 6;
	
	Node currentStarNode;
	Node Nodes[][] = new Node[ROW_NUMBER][COL_NUMBER];
	
	public Board()
	{
		
		for (int i=0;i<Nodes.length;i++)
			for (int j=0;j<Nodes[i].length;j++)
						Nodes[i][j] = new Node(0,i,j);
		
		
		//DecidingWhichIsBlank();
		NodeShop.ConvertRandomNode(Nodes);
		//PointNodes
		//Nodes[1][1] = new RangeNode(1,4);
		//Nodes[0][1] = new RangeNode(-2,0);
		/*Nodes[2][0] = new Node(1);
		Nodes[1][0] = new Node(5);
		Nodes[1][2] = new Node(-2);
		Nodes[1][1] = new Node(3);
		Nodes[2][2] = new Node(6);*/
		
		//Blanks
		Nodes[3][1].isBlank = true;
		Nodes[1][3].isBlank = true;
		Nodes[3][3].isBlank = true;
		Nodes[0][5].isBlank = true;
		
		//Setup paths (normally this would be read into a file)
		//column 1
		Nodes[0][0].NextNodes.add(Nodes[1][0]);
		Nodes[1][0].NextNodes.add(Nodes[2][0]);
		Nodes[1][0].NextNodes.add(Nodes[1][1]);
		Nodes[2][0].NextNodes.add(Nodes[2][1]);
		Nodes[2][0].NextNodes.add(Nodes[3][0]);
		Nodes[3][0].NextNodes.add(Nodes[4][0]);
		Nodes[4][0].NextNodes.add(Nodes[4][1]);
		Nodes[4][0].NextNodes.add(Nodes[5][0]);
		Nodes[5][0].NextNodes.add(Nodes[5][1]);
		
		//column 2
		Nodes[0][1].NextNodes.add(Nodes[0][0]);
		Nodes[1][1].NextNodes.add(Nodes[0][1]);
		Nodes[1][1].NextNodes.add(Nodes[2][1]);
		Nodes[2][1].NextNodes.add(Nodes[2][2]);
		Nodes[4][1].NextNodes.add(Nodes[4][2]);
		Nodes[5][1].NextNodes.add(Nodes[5][2]);
		
		//column 3
		Nodes[0][2].NextNodes.add(Nodes[0][1]);
		Nodes[0][2].NextNodes.add(Nodes[0][3]);
		Nodes[1][2].NextNodes.add(Nodes[0][2]);
		Nodes[1][2].NextNodes.add(Nodes[1][1]);
		Nodes[2][2].NextNodes.add(Nodes[1][2]);
		Nodes[2][2].NextNodes.add(Nodes[3][2]);
		Nodes[3][2].NextNodes.add(Nodes[4][2]);
		Nodes[4][2].NextNodes.add(Nodes[4][3]);
		Nodes[5][2].NextNodes.add(Nodes[5][3]);
		
		//column 4
		Nodes[0][3].NextNodes.add(Nodes[0][4]);
		Nodes[2][3].NextNodes.add(Nodes[2][2]);
		Nodes[4][3].NextNodes.add(Nodes[4][4]);
		Nodes[5][3].NextNodes.add(Nodes[4][3]);
		
		//column 5
		Nodes[0][4].NextNodes.add(Nodes[1][4]);
		Nodes[1][4].NextNodes.add(Nodes[2][4]);
		Nodes[2][4].NextNodes.add(Nodes[2][3]);
		Nodes[3][4].NextNodes.add(Nodes[2][4]);
		Nodes[4][4].NextNodes.add(Nodes[3][4]);
		Nodes[4][4].NextNodes.add(Nodes[5][4]);
		Nodes[5][4].NextNodes.add(Nodes[5][5]);
		
		//Column 6
		Nodes[5][5].NextNodes.add(Nodes[4][5]);
		Nodes[4][5].NextNodes.add(Nodes[3][5]);
		Nodes[3][5].NextNodes.add(Nodes[2][5]);
		Nodes[2][5].NextNodes.add(Nodes[1][5]);
		Nodes[1][5].NextNodes.add(Nodes[1][4]);
	}
	
	public void DecidingWhichIsBlank()
	{
		for(int i = 0; i < 4; i++)
		{
			int row = (int)(Math.random()*ROW_NUMBER);
			int col = (int)(Math.random()*COL_NUMBER);
			Nodes[row][col].isBlank = true;	
		}
	}
	
	public Node getNode(int row, int col)
	{
		return Nodes[row][col];
	}
	
	public void UpdateBoard()
	{
		for (int i=0;i<Nodes.length;i++)
		{
			for (int j=0;j<Nodes[i].length;j++)
			{
				Nodes[i][j].Points++;
			}
		}
	}
	
	public Node starPosition()
	{
		return currentStarNode;
	}
	
	public void findNewStarPlace()
	{
		while (true)
		{
			int row = (int)(Math.random()*Nodes.length);
			int col = (int)(Math.random()*Nodes[row].length);
			if (Nodes[row][col].isBlank)
				continue; //choose again
			Nodes[row][col].hasStar = true;
			currentStarNode = Nodes[row][col];
			break;
		}
	}
	public void NumbeBoard()
	{
		for (int i=0;i<Nodes.length-1;i++)
		{
			String line1 = "";
			String line2 = "";
			for (int j=0;j<Nodes[i].length-1;j++)
			{
				Node one = Nodes[i][j];
				Node two = Nodes[i][j+1];
				Node three = Nodes[i+1][j];
				
				//draw node 1
				line1 += FormToBoard("(" + i + "," + j + ")");
				
				//draw relationship between 1 and 2
				if (one.NextNodes.contains(two))
					line1 += FormToBoard(">>");
				else if (two.NextNodes.contains(one))
					line1 += FormToBoard("<<");
				else
					line1 += FormToBoard("  ");
				
				//draw relationship between 1 and 3
				if (one.NextNodes.contains(three))
					line2 += FormToBoard(" \\/");
				else if (three.NextNodes.contains(one))
					line2 += FormToBoard(" /\\");
				else
					line2 += FormToBoard("  ");
				
				//There needs to be an extra space on line 2
				line2 += FormToBoard("  ");
			}
			line1 += FormToBoard("(" + i + "," + (Nodes.length-1) + ")");
			Node one = Nodes[i][Nodes.length-1];
			Node three = Nodes[i+1][Nodes.length-1];
			if (one.NextNodes.contains(three))
				line2 += FormToBoard(" \\/");
			else if (three.NextNodes.contains(one))
				line2 += FormToBoard(" /\\");
			else
				line2 += FormToBoard("  ");
			System.out.println(line1);
			System.out.println();
			System.out.println(line2);
			System.out.println();				
		}
		
		//draw the final line of nodes and relationships
		String bottomLine = "";
		for (int j=0;j<Nodes[Nodes.length-1].length-1;j++)
		{			
			Node one = Nodes[Nodes.length-1][j];
			Node two = Nodes[Nodes.length-1][j+1];
			
			//draw node 1
			bottomLine += FormToBoard("(" + (Nodes.length-1) + "," + j + ")");
			
			//draw relationship between 1 and 2
			if (one.NextNodes.contains(two))
				bottomLine += FormToBoard(">>");
			else if (two.NextNodes.contains(one))
				bottomLine += FormToBoard("<<");
			else
				bottomLine += FormToBoard("  ");
		}
		//draw the very last node
		bottomLine += FormToBoard("(" + (Nodes.length-1) + "," + (Nodes.length-1) + ")");
		System.out.println(bottomLine);
		
		String BreakBetweenDraws = "";
		for (int i=0;i<Nodes.length*2-1;i++)
		{
			BreakBetweenDraws += FormToBoard("--------------");
		}
		System.out.println(BreakBetweenDraws);
	}
	
	public void DrawBoard()
	{
		//draw all the lines except the bottom one
		//String one: nodes and relationships horizontal
		//String two: relationships vertical
		//one  --  two
		// |
		//three 
		for (int i=0;i<Nodes.length-1;i++)
		{
			String line1 = "";
			String line2 = "";
			for (int j=0;j<Nodes[i].length-1;j++)
			{
				Node one = Nodes[i][j];
				Node two = Nodes[i][j+1];
				Node three = Nodes[i+1][j];
				
				//draw node 1
				line1 += FormToBoard(one.toString());
				
				//draw relationship between 1 and 2
				if (one.NextNodes.contains(two))
					line1 += FormToBoard(">>");
				else if (two.NextNodes.contains(one))
					line1 += FormToBoard("<<");
				else
					line1 += FormToBoard("  ");
				
				//draw relationship between 1 and 3
				if (one.NextNodes.contains(three))
					line2 += FormToBoard(" \\/");
				else if (three.NextNodes.contains(one))
					line2 += FormToBoard(" /\\");
				else
					line2 += FormToBoard("  ");
				
				//There needs to be an extra space on line 2
				line2 += FormToBoard("  ");
			}
			line1 += FormToBoard(Nodes[i][Nodes[i].length-1].toString());
			Node one = Nodes[i][Nodes.length-1];
			Node three = Nodes[i+1][Nodes.length-1];
			if (one.NextNodes.contains(three))
				line2 += FormToBoard(" \\/");
			else if (three.NextNodes.contains(one))
				line2 += FormToBoard(" /\\");
			else
				line2 += FormToBoard("  ");
			System.out.println(line1);
			System.out.println();
			System.out.println(line2);
			System.out.println();				
		}
		
		//draw the final line of nodes and relationships
		String bottomLine = "";
		for (int j=0;j<Nodes[Nodes.length-1].length-1;j++)
		{			
			Node one = Nodes[Nodes.length-1][j];
			Node two = Nodes[Nodes.length-1][j+1];
			
			//draw node 1
			bottomLine += FormToBoard(one.toString());
			
			//draw relationship between 1 and 2
			if (one.NextNodes.contains(two))
				bottomLine += FormToBoard(">>");
			else if (two.NextNodes.contains(one))
				bottomLine += FormToBoard("<<");
			else
				bottomLine += FormToBoard("  ");
		}
		//draw the very last node
		bottomLine += FormToBoard(Nodes[Nodes.length-1][Nodes.length-1].toString());
		System.out.println(bottomLine);
		
		String BreakBetweenDraws = "";
		for (int i=0;i<Nodes.length*2-1;i++)
		{
			BreakBetweenDraws += FormToBoard("--------------");
		}
		System.out.println(BreakBetweenDraws);
	}
	
	public static String FormToBoard(String s)
	{
		s += "            ";
		return s.substring(0, Board.NODE_SIZE);
	}
}
class Node
{
	public int row, col;
	public int Points;
	//hasStar and isBlank are the ?qualities? of the basic node, anything more complicated should be
	//done by extending the Node class
	public boolean hasStar;
	public boolean isBlank;
	public ArrayList<Node> NextNodes = new ArrayList<Node>();
	public ArrayList<Piece> Occupants = new ArrayList<Piece>();
	public ArrayList<Node> pathToGetTo = new ArrayList<Node>();
	public Node()
	{
		this(false);
		Points = 0;
	}
	public Node(boolean isBlank)
	{
		this.isBlank = isBlank;
	}
	public Node(int Points, int r, int c)
	{
		this.Points = Points;
		row = r;
		col = c;
	}
	public boolean Land(Piece p)
	{
		if (hasStar && p.Coins > 50)
		{
			System.out.println(p.Name + " has bought a star");
			hasStar = false;
			p.Stars++;
			Points = 0;
			p.Coins -= 50;
			return true;
		}
		else if (hasStar) //and coins are not high enough
		{
			p.Coins -= 25;//STAR Tax = blyat ;-;
		}
		p.Coins += Points;
		Points = -5;
		Occupants.add(p);
		
		if (p.Coins<0)
			p.Coins = 0;
		else if (p.Coins > 100)
			p.Coins = 100;
		
		return false;
	}
	public void UnLand(Piece p)
	{
		Occupants.remove(p);
	}
	public String toString()
	{
		if (hasStar)
			return Board.FormToBoard("* "+ Occupants.toString());
		if (isBlank)
			return Board.FormToBoard("  ");
		return Board.FormToBoard(Points + " " + Occupants.toString());
	}
	
	public String toString(int pos)
	{
		return ("(" + this.row + "," + this.col + ")");
	}
	
	public int GetPoints(int playerCoinAmount)
	{
		if (hasStar)
		{
			if (playerCoinAmount > 50)
				return 1000; //high priority
			else
				return -25;
		}
		return Points;
	}
	public static boolean validMove(Node nodes[][], Node currentNode, Node potentialMove)
	{
		for(int i = 0; i < currentNode.NextNodes.size(); i++)
		{
			if(nodes[currentNode.row][currentNode.col].NextNodes.get(i).equals(nodes[potentialMove.row][potentialMove.col]))
			{
				return true;
			}
		}
		return false;
	}
}
class NodeShop extends Node
{
	static ArrayList<Items> itemsList = new ArrayList<>();
	public static int posRow;
	public static int posCol;
	public NodeShop(int Points, int r, int c)
	{
		this.Points = Points;
		this.row = r;
		this.col = c;
		itemsList.add(new Items("Bonds", 50));
		itemsList.add(new Items("Claims", 500));
	}
	public String toString()
	{
		if (hasStar)
			return Board.FormToBoard("*S "+ Occupants.toString());
		if (isBlank)
			return Board.FormToBoard("  ");
		return Board.FormToBoard(Points + " S" + Occupants.toString());
	}
	
	
	public static void ConvertRandomNode(Node Nodes[][])
	{
		while(true)
		{
			int row = (int)(Math.random()*Nodes.length);
			int col = (int)(Math.random()*Nodes[row].length);
			if(Nodes[row][col].isBlank)
				continue;
			Nodes[row][col] = new NodeShop(0, row, col);
			posRow = row;
			posCol = col;
			break;
		}
	}
	
	public static void InStock()
	{
		for(int i = 0; i < itemsList.size();i++)
		{
			System.out.println(i + ") " + itemsList.get(i));
		}
	}
	public static boolean pieceOnShop(Node currentLocation, Node Nodes[][], int row, int col)
	{
		if(currentLocation.equals(Nodes[row][col]))
			return true;
		return false;
	}
	public static void PlayerHasLanded(Piece player, int currentTurn)
	{
		System.out.println("Welcome to the shop lad");
		InStock();
		Scanner playerInput = new Scanner(System.in);
		int userInput = playerInput.nextInt();
		int cost;
		switch(userInput)
		{
		case 0:
			System.out.println("How many would you like to buy?");
			userInput = playerInput.nextInt();
			cost = 5;
			if(userInput*cost <= player.Coins)
			{
				System.out.println("Sure thing I'll be right there");
				
				player.inventory.addAmount("Bonds", new Bonds(currentTurn, userInput));
				player.inventory.displayInventory();
			}
			
			break;
		case 1:
			System.out.println("How many would you like to buy?");
			userInput = playerInput.nextInt();
			cost = 5;
			if(userInput*cost <= player.Coins)
			{
				System.out.println("Sure thing I'll be right there");
				
				player.inventory.addAmount("Claims", new Claims(userInput));
				player.inventory.displayInventory();
			}
			System.out.println("Sure thing I'll be right there");
			break;
		default:
			System.out.println("Sorry Sonny but we only sell things in our shop");
			break;
		}
	}
	
	public void NPCHasLanded(Piece NPC)
	{
		
	}
}

class Inventory
{
	ArrayList<Items> inventory = new ArrayList<>();
	public Inventory()
	{
		
	}
	public void addAmount(String name, Items newItem)
	{
		if(searchInventory(name) != null || !(searchInventory(name) instanceof Bonds))
		{
			searchInventory(name).amount += newItem.amount; 
		}
		else
		{
			inventory.add(newItem);
		}
	}
	public Items searchInventory(String name)
	{
		for(int i = 0; i < inventory.size();i++)
		{
			if(this.inventory.get(i).name.equals(name))
			{
				return inventory.get(i);
			}
		}
		return null;
	}
	
	public void removeItem(Items name)
	{
		for(int i = 0; i < inventory.size(); i++)
		{
			if(inventory.get(i).equals(name))
			{
				inventory.remove(i);
			}
		}
	}
	public void displayInventory()
	{
		for(int i = 0; i < inventory.size();i++)
		{
			System.out.println(inventory.get(i));
		}
	}
}

class Items
{
	String name;
	int cost;
	int amount = 0;
	public Items(String name, int cost)
	{
		this.name = name;
		this.cost = cost;
	}
	public Items()
	{
		
	}
	public String toString()
	{
		return name + " " + amount;
	}
	public String toString(int fact)
	{
		return name;
	}
}
class Bonds extends Items
{
	int expirationDate;
	int cost = 300;
	public Bonds(int currentTurn, int amount)
	{
		this.name = "Bonds";
		this.amount = amount;
		expirationDate = currentTurn + 10;
	}
	
	public Bonds()
	{
		this.name = "Bonds";
	}
	
	public void BoughtBonds(Piece player, int currentTurn)
	{
		if(currentTurn >= expirationDate)
		{
			player.Coins += 1500 * this.amount;
		}
	}
}
class Claims extends Items
{
	int cost = 500;
	public Claims(int amount)
	{
		this.name = "Claims";
		this.amount = amount;
	}
	public Claims()
	{
		this.name = "Claims";
	}
	public void playerWishesToLayClaim(Node currentLocation, Piece player)
	{
		if(player.inventory.searchInventory("Claims").amount > 0)
		{
			if(player.inventory.searchInventory("Claims").amount - 1 == 0)
			{
				player.territory.add(currentLocation);
				player.inventory.removeItem(player.inventory.searchInventory("Claims"));
			}
			else
			{
				player.territory.add(currentLocation);
				player.inventory.searchInventory("Claims").amount -= 1;
			}
		}
	}
}
/**
 * Random AI should be implemented here
 *
 */
class Piece
{
	public int Coins;
	public int Stars;
	public Node Location;
	public String Name;
	public Inventory inventory = new Inventory();
	public ArrayList<Node> territory = new ArrayList<>();
	
	public Piece(String n)
	{
		Name = n;
	}
	public Piece()
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
	public static void checkTheirLocation(ArrayList<Piece> listOfPieces, Board board, int currentTurn)
	{
		
		for(int i = 0; i < listOfPieces.size(); i++)
		{
			if(pieceOnStar(board.currentStarNode, listOfPieces.get(i).Location))
			{
				listOfPieces.get(i).Coins += board.currentStarNode.GetPoints(listOfPieces.get(i).Coins);
				board.currentStarNode.hasStar = false;
				board.findNewStarPlace();
			}
			else if(NodeShop.pieceOnShop(listOfPieces.get(i).Location, board.Nodes, NodeShop.posRow, NodeShop.posCol))
			{
				if(i == 0)
				{
					NodeShop.PlayerHasLanded(listOfPieces.get(i), currentTurn);
				}
				else
				{
					
				}
			}
		}
	}
	public static boolean pieceOnStar(Node LocationOfStar, Node currentLocation)
	{
		if(currentLocation.equals(LocationOfStar))
			return true;
		return false;
	}
}

class HumanPiece extends Piece
{
	public HumanPiece(String n, Board b)
	{
		this.Coins = 0;
		this.Stars = 0;
		this.Name = n;
		this.StartingLocation(b.Nodes);
	}
	public HumanPiece(Board b)
	{
		this.Coins = 0;
		this.Stars = 0;
		this.Name = "No name";
		this.StartingLocation(b.Nodes);
	}
	public String toString()
	{
		return this.Name;
	}
	public void StartingLocation(Node Nodes[][])
	{
		while (true)
		{
			int row = (int)(Math.random()*Nodes.length);
			int col = (int)(Math.random()*Nodes[row].length);
			if (Nodes[row][col].isBlank) //Might account for other pieces
				continue; //choose again
			this.Location = Nodes[row][col];
			Nodes[row][col].Land(this);
			break;
		}
	}
	
	public boolean Move(Node Nodes[][], int userInput, Board board)
	{
		int row = this.Location.row;
		int col = this.Location.col;
		switch(userInput)
		{
		case 8:
			if((row - 1) < 0)
			{
				System.out.println("Invalid Move");
				return false;
			}
			else if(Node.validMove(Nodes, this.Location, Nodes[row-1][col]))
			{
				Nodes[row][col].UnLand(this);
				this.Location = Nodes[row-1][col];
				Nodes[this.Location.row][this.Location.col].Land(this);
				return true;
			}
			System.out.println("Invalid Move");
			return false;
		case 6:
			
			if((col + 1) > board.COL_NUMBER-1)
			{
				System.out.println("Failed Move");
				return false;
			}
			else if(Node.validMove(Nodes, this.Location, Nodes[row][col+1]))
			{
				Nodes[row][col].UnLand(this);
				this.Location = Nodes[row][col+1];
				Nodes[this.Location.row][this.Location.col].Land(this);
				return true;
			}
			return false;
		case 2:
			if((row + 1) > board.ROW_NUMBER-1)
			{
				System.out.println("Invalid Move");
				return false;
			}
			else if(Node.validMove(Nodes, this.Location, Nodes[row+1][col]))
			{
				Nodes[row][col].UnLand(this);
				this.Location = Nodes[row+1][col];
				Nodes[this.Location.row][this.Location.col].Land(this);
				return true;
			}
			System.out.println("Invalid Move");
			return false;
		case 4:
			if((col - 1) < 0)
			{
				System.out.println("Invalid Move");
				return false;
			}
			else if(Node.validMove(Nodes, this.Location, Nodes[row][col-1]))
			{
				Nodes[row][col].UnLand(this);
				this.Location = Nodes[row][col-1];
				Nodes[this.Location.row][this.Location.col].Land(this);
				return true;
			}
			System.out.println("Invalid Move");
			return false;
		default:
		{
			System.out.println("Invalid Move");
			return false;
		}
		}
	}
	
}


class SmartPiece extends Piece
{
	ArrayList<Node> pathing = new ArrayList<>();
	Board board;
	public SmartPiece(String n, Board b)
	{
		this.Coins = 0;
		this.Stars = 0;
		this.Name = n;
		this.board = b;
		this.StartingLocation(board.Nodes);
		this.pathing = SmartPiece.getCostOfPaths(SmartPiece.paths(Location, 5, b));
	}
	public SmartPiece(Board b)
	{
		this.Coins = 0;
		this.Stars = 0;
		this.Name = "No name";
		this.board = b;
		this.StartingLocation(b.Nodes);
		this.pathing = SmartPiece.getCostOfPaths(SmartPiece.paths(Location, 5, b));
	}
	public String toString()
	{
		return this.Name;
	}
	public void StartingLocation(Node Nodes[][])
	{
		while (true)
		{
			int row = (int)(Math.random()*Nodes.length);
			int col = (int)(Math.random()*Nodes[row].length);
			if (Nodes[row][col].isBlank)
				continue; //choose again
			this.Location = Nodes[row][col];
			Nodes[row][col].Land(this);
			break;
		}
	}
	//Add the costs up of the path
	public static ArrayList<Node> getCostOfPaths(ArrayList<ArrayList<Node>> paths)
	{
		int currentValue = 0;
		int largestValue = -10000;
		ArrayList<Node> truePath = new ArrayList<>();
		for(int i = 0; i < paths.size();i++)
		{
			for(int x = 1; x < paths.get(i).size();x++)
			{
				if(paths.get(i).get(x).hasStar)
				{
					currentValue -= 200;
				}
				else
				{
					currentValue += paths.get(i).get(x).Points;
				}
			}
			if(currentValue > largestValue)
			{
				largestValue = currentValue;
				currentValue = largestValue;
				truePath.clear();
				truePath.addAll(paths.get(i));
			}
			currentValue = 0;
		}
		return truePath;
	}
	
	public static ArrayList<Node> copyUntil(ArrayList<Node> path, int i)
	{
		ArrayList<Node> newPath = new ArrayList<>();
		for(int x = 0; x <= i; x++)
		{
			newPath.add(path.get(x));
		}
		return newPath;
	}
	
	public static ArrayList<Node> findPathToStar(ArrayList<ArrayList<Node>> paths)
	{
		ArrayList<Node> truePath = new ArrayList<>();
		for(int i = 0; i < paths.size();i++)
		{
			for(int x = 1; x < paths.get(i).size();x++)
			{
				if(paths.get(i).get(x).hasStar)
				{
					truePath = copyUntil(paths.get(i), x);
				}
			}
		}
		return truePath;
	}
	
	//This is used to print a 2D Array
	public static void TwoArray(ArrayList<ArrayList<Node>> pathing)
	{
		for(int i = 0; i < pathing.size(); i++)
		{
			for(int x = 0; x < pathing.get(i).size();x++)
			{
				Node currentNode = pathing.get(i).get(x);
				System.out.print(currentNode.toString(1));
			}
			System.out.println();
		}
	}
	//This is used to print a single array
	public static void SingleArray(ArrayList<Node> pathing)
	{
		for(int i = 0; i < pathing.size(); i++)
		{
			Node currentNode = pathing.get(i);
			System.out.print(currentNode.toString(1));
		}
		System.out.println();
	}
	
	public void updatePath()
	{
		this.DecidingOnPathing();
		if(pathing != null)
		{
			this.Location.UnLand(this);
			this.Location = this.pathing.get(1);
			this.Location.Land(this);
		}
	}
	
	public static ArrayList<Node> CopyList(ArrayList<ArrayList<Node>> pathing, Board board, int i, int x, int v)
	{
		Node next = pathing.get(i).get(x);
		ArrayList<Node> newList = new ArrayList<>();
		newList.addAll(pathing.get(i));
		newList.add(next.NextNodes.get(v));
		return newList;
	}
	
	public static void ScanningNextNodes(ArrayList<ArrayList<Node>> pathing, Board board, int i, int x)
	{
		Node next = pathing.get(i).get(x);
		for(int v = pathing.get(i).get(x).NextNodes.size()- 1; v >= 0;v--)
		{			
			if(v == 0)
			{
				pathing.get(i).add(next.NextNodes.get(v));
			}
			else if(Node.validMove(board.Nodes, next, next.NextNodes.get(v)))
			{
				pathing.add(SmartPiece.CopyList(pathing, board, i, x, v));
			}
		}
		//SmartPiece.TwoArray(pathing);
	}
	
	public static boolean equalLength(ArrayList<ArrayList<Node>> pathing, int steps)
	{
		for(int i = 0; i < pathing.size();i++)
		{
			if(pathing.get(i).size() < steps)
			{
				return true;
			}
		}
		return false;
	}
	
	//This gets the general pathings
	public static ArrayList<ArrayList<Node>> paths(Node location, int steps, Board board)
	{
		ArrayList<ArrayList<Node>> pathing = new ArrayList<ArrayList<Node>>();
		int size = location.NextNodes.size();
		System.out.println(size);
		
		//This creates the starting positions 
		pathing.add(new ArrayList<>());
		pathing.get(0).add(location);
		
		for(int i = 0; i < size;i++) //This gets the position of current array
		{
			for(int x = pathing.get(i).size()-1; x < steps; x++) //This gets the current 
			{
				Node next = pathing.get(i).get(x);
				if(!(next.NextNodes.size() > 1))
				{
					pathing.get(i).add(next.NextNodes.get(0));
				}
				else
				{
					SmartPiece.ScanningNextNodes(pathing, board, i, x);//This gets the current nextnode position
				}

			}
		}
		return pathing;
	}
	
	public void DecidingOnPathing()
	{
		if(Coins >= 50)
		{
			this.pathing = SmartPiece.findPathToStar(SmartPiece.paths(this.Location, 30, board));
		}
		else
		{
			this.pathing = SmartPiece.getCostOfPaths(SmartPiece.paths(Location, 5, board));
		}
	}
	
}
