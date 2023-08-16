
import java.util.ArrayList;
import java.util.Arrays;

public class Pathing 
{
	public static void main(String[] args) 
	{
		Site A = new Site("A");
		Site sc = new Site("Short Cut");
		Site B = new Site("B");
		Site C = new Site("C");
		Site D = new Site("D");
		
		A.addRoute(new Route(A,B,1,1,RouteType.Truck));
		A.addRoute(new Route(A,sc,2,1,RouteType.Truck));
		sc.addRoute(new Route(sc,D,1,1,RouteType.Ship));
		B.addRoute(new Route(B,C,5,2,RouteType.Truck));
		C.addRoute(new Route(C,D,2,3,RouteType.Truck));
		D.addRoute(new Route(D,B,2,2,RouteType.Truck));
		B.addRoute(new Route(B,A,2,1,RouteType.Truck));
	
		
		ArrayList<Site> bruh = Route.findPath(A, D, RouteType.Truck);
		
		ArrayList<Integer>timeStamps = Route.schedule(bruh);
		
		for(int i = 0; i < bruh.size(); i++)
		{
			for(int x = 0; x < bruh.get(i).routes.size(); x++)
			{
				//System.out.println(bruh.get(i).routes.get(x).start + " " + bruh.get(i).routes.get(x).end);
			}
		}
		
		System.out.println(timeStamps);
		Vehicle Johnny = new Vehicle(A , 100, RouteType.Truck);
		
		for(int clock = 0, spot = 0; clock < 15; clock++)
		{
			if(spot < bruh.size())
			{
				if(Johnny.updateClock(timeStamps.get(spot), clock, bruh.get(spot), RouteType.Truck))
				{
					spot++;
					
				}
			}
		}
	}
}

class Site
{
	//Fields
	String name;
	ArrayList<Route> routes = new ArrayList<>(); //routes
	
	//transient
	transient ArrayList<Site> pathToReach = new ArrayList<>(); //should be cleared at the start of each new pathFind operation
	public Site(String name)
	{
		this.name = name;
	}
	public void addRoute(Route route)
	{
		routes.add(route);
	}
	public String toString()
	{
		return name;
	}
	
}
enum RouteType
{
	Ship,
	Truck,
	Train,
	Plain,
	All,
}
class Route //association class
{
	Site start, end;
	int cost, time; //how and where we use this
	RouteType type;
	
	public Route(Site start, Site end, int cost, int time, RouteType type) {
		this.start = start;
		this.end = end;
		this.cost = cost;
		this.time = time;
		this.type = type;
	}
	public static ArrayList<Site> findPath(Site origin, Site destination, RouteType type)
	{
		ArrayList<Site> path = null; //currently working with (what we return at the end)
		ArrayList<Site> open = new ArrayList<>(); //these are all the n that we can reach but have yet to look at
		ArrayList<Site> closed = new ArrayList<>(); //we have already visited this n and know how to reach it
		open.add(origin);             //Arrays.asList is a helper method for creating ArrayLists with values
		origin.pathToReach = new ArrayList<>(Arrays.asList(origin));
		
		while (!open.isEmpty())
		{
			Site current = open.get(0); //This is the important part of the algorithm (Heuristic)
			open.remove(0); //they are no longer on the open list
			
			//are we there yet
			if (current == destination) //== can be done other ways
			{
				System.out.println("Found path: " + current.pathToReach);
				//if we haven't found a path yet, this is our best
				if (path == null)
					path = current.pathToReach;
				//if this is another way to reach the destination then....
				if (getPathCost(path) > getPathCost(current.pathToReach))
					path = current.pathToReach;	
			}
			else
				closed.add(current);
			
			//where can we go from here
			ArrayList<Site> neighbors = new ArrayList<>();
			for (Route r : current.routes)
				if (r.type == type || type == RouteType.All) //are we allowed to use this route
					neighbors.add(r.end);
			
			for (Site s : neighbors)
			{
				if (!closed.contains(s) && !open.contains(s))
				{
					ArrayList<Site> copy = new ArrayList<Site>(current.pathToReach); //deep copy the current n way to get to it
					copy.add(s);
					s.pathToReach = copy;
					open.add(s);
				}
				else //what if I have a better way to reach it
				{
					ArrayList<Site> copy = new ArrayList<Site>(current.pathToReach); //deep copy the current n way to get to it
					copy.add(s);
					if (getPathCost(copy) < getPathCost(s.pathToReach))
					{
						s.pathToReach = copy;
						//maybe now that we found a better way to reach this n, maybe it's paths might be better
						closed.remove(s);
						if (!open.contains(s)) //don't add it to the open list if it is already there
							open.add(s);
					}
				}
			}
		}
		return path;
	}//end of find path
	public static int getPathCost(ArrayList<Site> path)
	{
		int cost = 0;
		for (int i=0;i<path.size()-1;i++)
		{
			Site start = path.get(i);
			Site end = path.get(i+1);
			for (Route r : start.routes)
			{
				if (r.end == end)
				{
					cost += r.cost;
					break;
				}
			}					
		}
		return cost;
	}
	
	public static ArrayList<Integer> schedule(ArrayList<Site> path)
	{
		ArrayList<Integer> timeSchedule = new ArrayList<Integer>(path.size());
		timeSchedule.add(0);
		for(int i = 1; i < path.size(); i++)
		{
			timeSchedule.add(i+timeSchedule.get(i-1));
		}
		return timeSchedule;
	}
	
	public static Route getRoute(ArrayList<Site> path, Site nextLocation)
	{
		Route heehee = null;
		Site temp = path.get(path.indexOf(nextLocation)-1);
		for(int i = 0; i < temp.routes.size(); i++)
		{
			if(temp.routes.get(i).end.equals(path.get(path.indexOf(nextLocation)))) 
			{
				//System.out.println("Route ENDING " + temp.routes.get(i).end);
				heehee = temp.routes.get(i);
				break;
			}
		}
		return heehee;
	}
}

class Vehicle
{
	int maxWeight;
	int currentLoad;
	private Site location;
	private RouteType type;
	boolean inTransit;
	private Cargo takenUwU;
	
	public Vehicle(Site startingLocation, int maxW, RouteType type)
	{
		this.location = startingLocation;
		this.maxWeight = maxW;
		this.type = type;
		this.inTransit = false;
	}
	
	public boolean updateClock(int timeStamps, int currentTime, Site nextLocation, RouteType type)
	{
		if(timeStamps == currentTime && this.type == type)
		{
			this.location = nextLocation;
			//this.takenUwU.updateLocation(nextLocation);
			//this.takenUwU.reachedDestination();
			System.out.println("BLYAT, I have arrived in " + nextLocation);
			return true;
		}
		else if(this.type != type)
		{
			//Drop off cargo
		}
		return false;
	}
	
	public Site currentLocation()
	{
		return location;
	}
	
	public void yoink(Cargo yeetThis)
	{
		this.takenUwU = yeetThis;
	}
	
	public String toString()
	{
		return location.name;
	}
}
class Cargo
{
	Site currentLocation;
	Site destination;
	private int weight;
	private static int internalID = 0;
	private int ID;
	//private ArrayList<Cargo> scattered = new ArrayList<Cargo>();
	boolean reached;
	public Cargo(Site currentLocation, Site destination, int weight, boolean reached)
	{
		ID = internalID++;
		this.currentLocation = currentLocation;
		this.destination = destination;
		this.weight = weight;
		this.reached = reached;
		//scattered.add(new Cargo(this.currentLocation, this.destination, this.weight, this.reached));
	}
	public Site ping()
	{
		if(!reached)
		{
			return currentLocation;
		}
		return null;
	}
	
	public void updateLocation(Site newLocation)
	{
		this.currentLocation = newLocation;
	}
	
	public void split(int avaliableWeight) 
	{
		//splitting uwu
	}
	
	public void reachedDestination()
	{
		if(currentLocation == destination)
		{
			this.reached = true;
			System.out.println("GOODS HAVE BEEN DELIVRED BLYAT");
		}
	}
	
	public Cargo whoHasReached(ArrayList<Cargo> cargoList)
	{
		for(int i = 0; i < cargoList.size(); i++)
		{
			if(!cargoList.get(i).reached)
			{
				return cargoList.get(i);
			}
		}
		return null;
	}
	
}
class Company
{
	
}