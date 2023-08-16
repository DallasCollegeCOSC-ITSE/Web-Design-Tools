
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


import org.jsfml.audio.Music;
import org.jsfml.graphics.*;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

import java.io.IOException;
import java.nio.file.Paths;

//Simplified example to show how to chain method calls to "discover" useful info
public class ProjectHelp
{
	

	
	public static void main(String[] args) throws FileNotFoundException
	{
		//code Source for font check https://github.com/pdinklag/JSFML/wiki/Fonts-and-Texts#the-code
		//Try to open a font file
		Font Helvetica = new Font();
	try {
	   Helvetica.loadFromFile(Paths.get("F:\\eclipse-jee-2021-12-R-win32-x86_64\\eclipse\\Workspace\\COSC-1437-Luka Bostick\\src\\assets\\Helvetica.ttf"));
	} catch(IOException ex) {
	    //Failed to load font
	    ex.printStackTrace();
	}
		//Create the window
		RenderWindow window = new RenderWindow();
	window.create(new VideoMode(640, 480), "Hello JSFML!");
	//Limit the framerate
	window.setFramerateLimit(30);
		
	Sqr squares[][] = readFile("F:\\eclipse-jee-2021-12-R-win32-x86_64\\eclipse\\Workspace\\COSC-1437-Luka Bostick\\src\\squares.txt", new Sqr[6][7]); //the size could be set by the file just making this simpler for the example
		
		while(window.isOpen())
		{
			
		squares[0][0].isStart = true; //again, hard coded for demo purposes
		squares[3][6].isEnd = true;
		
		setNeighbors(squares);
		
		drawSquares(squares);
		
		System.out.println();
		
		window.draw(findPath(squares[0][0], squares[3][6]));
		}
		
		
	}
	public static Drawable findPath(Sqr start, Sqr end)
	{
		ArrayList<Sqr> path = null;
		ArrayList<Sqr> open = new ArrayList<Sqr>();
		ArrayList<Sqr> closed = new ArrayList<Sqr>();
		open.add(start);
		start.pathToGetTo = new ArrayList<Sqr>(Arrays.asList(start));
		while (!open.isEmpty())
		{
			Sqr current = open.get(0);
			open.remove(0);
			System.out.println("Current: "+current.y + ", "+current.x);
			if (current.isEnd)
			{
				System.out.println("Found path: "+Sqr.getPathToCost(current.pathToGetTo));
				if (path == null)
					path = current.pathToGetTo;
				else if (Sqr.getPathToCost(path) > Sqr.getPathToCost(current.pathToGetTo))
					path = current.pathToGetTo;
			}
			if (!current.isEnd)
				closed.add(current);
			
			ArrayList<Sqr> adj = current.neighbors;
			for (Sqr s : adj)
			{				
				if (!closed.contains(s) && !open.contains(s)) //this should check if the current path is better or worse
				{
					ArrayList<Sqr> copy = new ArrayList<Sqr>(current.pathToGetTo); //deep copy
					copy.add(s);
					s.pathToGetTo = copy;
					open.add(s);
				}
				else //if they are on the closed list but the new path to get there is better then set that as the pathtoget there and add them back to the open list
				{
					ArrayList<Sqr> copy = new ArrayList<Sqr>(current.pathToGetTo); //deep copy
					copy.add(s);
					if (Sqr.getPathToCost(s.pathToGetTo) > Sqr.getPathToCost(copy))
					{
						s.pathToGetTo = copy;
						closed.remove(s);
						open.add(s);
					}
				}
			}			
		}
		
		return (Drawable) path;
		
	}
	public static void setNeighbors(Sqr squares[][])
	{
		for (int r=0;r<squares.length;r++)
			for (int c=0;c<squares[r].length;c++)
				squares[r][c].setNeighbors(squares);
	}
	public static void drawSquares(Sqr squares[][])
	{
		for (int r=0;r<squares.length;r++)
		{
			//top row for squares
			for (int c=0;c<squares[r].length;c++)
					squares[r][c].drawTop();
			System.out.println();
			//bottom row
			for (int c=0;c<squares[r].length;c++)
					squares[r][c].drawBottom();
			System.out.println();
			//note that you can have as many rows per square/n/space or whatever you want
		}
	}
	public static Sqr[][] readFile(String fileName, Sqr squares[][]) throws FileNotFoundException
	{
		Scanner s = new Scanner(new File(fileName));
		int lineNumber = 0;
		while (s.hasNextLine())
		{
			String line = s.nextLine();
			for (int i=0;i<line.length();i++)
			{
				if (line.charAt(i) != '0')
					squares[lineNumber][i] = new Sqr(lineNumber, i, line.charAt(i)-'0');
				else
					squares[lineNumber][i] = new Sqr(lineNumber, i, true);
			}
			lineNumber++;
		}
		return squares;
	}




class Sqr
{
	int x,y;
	int cost;
	boolean isStart;
	boolean isEnd;
	boolean isBlank;
	ArrayList<Sqr> neighbors = new ArrayList<>();
	
	ArrayList<Sqr> pathToGetTo;
	
	public Sqr(int y, int x, int cost)
	{
		this.y = y;
		this.x = x;
		this.cost = cost;
	}
	public Sqr(int y, int x, boolean isBlank)
	{
		this.y = y;
		this.x = x;
		this.isBlank = isBlank;
		cost = Integer.MAX_VALUE;
	}
	public static int getPathToCost(ArrayList<Sqr> path)
	{
		int costToGetTo = 0;
		for (Sqr s : path)
			costToGetTo += s.cost;
		return costToGetTo;
	}
	public String toString()
	{
		return "Sqr("+y+","+x+")";
	}
	//checking are four cardinal directions then adds it to the neighbors list
	public void setNeighbors(Sqr[][] sqrs)
	{
		if (y>0 && !sqrs[y-1][x].isBlank)
			neighbors.add(sqrs[y-1][x]);
		if (y+1<sqrs.length && !sqrs[y+1][x].isBlank)
			neighbors.add(sqrs[y+1][x]);
		if (x>0 && !sqrs[y][x-1].isBlank)
			neighbors.add(sqrs[y][x-1]);
		if (x+1<sqrs[y].length && !sqrs[y][x+1].isBlank)
			neighbors.add(sqrs[y][x+1]);
	}
	public void drawTop()
	{
		if (isBlank)
			System.out.print("     ");
		else
			System.out.print("┌───┐");
	}
	public void drawBottom()
	{
		if (isBlank)
			System.out.print("     ");
		else if (isStart)
			System.out.print("|_S_|");
		else if (isEnd)
			System.out.print("|_E_|");
		else 
			System.out.print("|___|");
	}
}	
}