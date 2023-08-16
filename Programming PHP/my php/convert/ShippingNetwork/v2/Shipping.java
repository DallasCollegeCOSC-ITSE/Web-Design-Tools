import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.ImageIcon;

public class Shipping {
	String fileName = "Site.txt";
	
	Graphics2D g;
	
	Shipping() throws FileNotFoundException
	{
		
		//TODO read in file
		//init sites
		
		Site.parseFile(fileName);
		
		
	}
	
	
}

class Site extends Screenobj
{
	//fields
	String name;
	
	ArrayList<Route> routes = new ArrayList<>();
	ArrayList<Cargo> cargos = new ArrayList<>();
	
	
	//Transient
	transient ArrayList<Site> pathToReach = new ArrayList<>();//should be cleared at the start of each new pathFind operation
	
	public Site(String name, int x, int y) //x and y = location
	{
		this.name = name;
		
		this.image = new ImageIcon("Site.png").getImage();
		
		this.location = new Vector2D(x,y);//temp for testing
		
		this.size = new Vector2D(50,50);
	}
	
	public void addRoute(Route route)
	{
		routes.add(route);
	}
	public void addCargo(Cargo cargo)
	{
		cargos.add(cargo);
	}
	public void removeCargo(Cargo cargo)
	{
		cargos.remove(cargos.indexOf(cargo));
	}

	public static Site searchNames(ArrayList<Site> sites, String name)
	{
		for(int i = 0; i < sites.size(); i++)
		{	
			if(sites.get(i).name.equals(name))
			{
				return sites.get(i);
			}
		}
		return null;
	}

	public String toString()
	{
		return name;
	}
	
	public static void parseFile(String fileName) throws FileNotFoundException
	{
		Scanner file = new Scanner (new File(fileName));
		
		while(file.hasNextLine())
		{
			String line = file.nextLine();
			
			//line = file.nextLine();
			
			
			String siteName = " ";
			
			int X=0;
			int Y=0;
			
			String[] s = line.split("\\s+");
			
			siteName = s[0];
			
			//System.out.println(siteName);
			
			X = Integer.parseInt(s[1]);
			
			Y = Integer.parseInt(s[2]);
			
			//init and draw site locations
			Site temp = new Site(siteName,X, Y);
			
			//TODO find a way to draw the sitename string
			
			
		}
		
	}
}


enum RouteType
{
	water,
	road,
	train,
	air,
	all,
}
enum CargoStatus
{
	Idle,
	VehicleOnRoute,
	Transit,
	Arrived
}
enum VehicleStatus
{
	Idle,
	OnRouteToCargo,
	DeliveringCargo
}


class Route extends Screenobj
{
	Site start, end;
	int cost, time;
	RouteType type;
	
	public Route(Site start, Site end, int cost, int time, RouteType type) {
		this.start = start;
		this.end = end;
		this.cost = cost;
		this.time = time;
		this.type = type;
		
		this.location = new Vector2D(start.location.getX(),start.location.getY());//temp for testing
		
		this.size = new Vector2D(50,50);
		
	}
	
	public static ArrayList<Site> findPath(Site origin, Site destination, RouteType type)
	{
		ArrayList<Site> path = null; 			   //currently working with (what we return at the end)
		ArrayList<Site> open = new ArrayList<>();  //these are all the n that we can reach but have yet to look at
		ArrayList<Site> closed = new ArrayList<>();//we have already visited this n and know how to reach it
		
		open.add(origin);						   
		
		origin.pathToReach = new ArrayList<>(Arrays.asList(origin));//Array.asList is a helper method for creating ArrayLusts with values
		
		while(!open.isEmpty())
		{
			Site current = open.get(0); //This is the Immportabt part of the Algorithm (Heuristic)
			open.remove(0);// they are no longer on the open list
			
			//are we there yet
			if(current == destination)//== can be done other ways
			{
				System.out.println(":Found path: " + current.pathToReach);
				//if we haven't found a path yet, this is our best
				if(path==null)
					path = current.pathToReach;
				
				//if this is another way to reach the destination then ...
				if(getPathCost(path) > getPathCost(current.pathToReach))
			}
			
		}
		return path;
	}//end of find path
	
	//Find the cost of each path
	public static int getPathCost(ArrayList<Site> path)
	{
		return costl
	}
}

class Vehicle extends Screenobj
{
	String name;
	int maxWeight,currentLoad;
	private Site location;
	public RouteType type;
	VehicleStatus status;
	Cargo load;
	
	ArrayList<Site> route;
	ArrayList<Integer> timeStamps;
	
	public Vehicle(String name, Site startingLocation, int maxW, RouteType type, VehicleStatus status)
	{
		this.name = name;
		this.location = startingLocation;
		this.maxWeight=maxW;
		this.type=type;
		this.status=status;
	}
	
	
	
}

class Cargo
{
	String name;
	ArrayList<Site> route;
	Site currentLocation;
	Site destination;
	Vehicle convoy;
	
	CargoStatus status;
	ArrayList<Cargo> split = new ArrayList<>();
	public int weight;//hide this
	public int time; //hide this
	private static int internalID = 0;
	private int ID;
	
	//private ArrayList<Cargo> scattered = new ArrayList<Cargo>();
	
	public Cargo (int time, String name, Site currentLocation, Site destination, int weight, CargoStatus status)
	{
		ID = internalID++;
		
		this.time = time;
		this.name = name;
		this.currentLocation = currentLocation;
		this.destination = destination;
		this.weight = weight;
		this.status = status;
		
		route = Route.findPath()
		
	}
	
}
//information about the cargo, vehicle's routes, vehicles, cost, and time tobe displayed when a Vehicle is clicked or moused over 
class Company extends Screenobj
{
	
}