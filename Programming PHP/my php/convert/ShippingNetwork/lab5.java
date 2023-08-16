
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class labN5 {
	public static final String fileName = "C:\\Users\\miyaa\\eclipse-workspace\\5lab\\src\\labN5\\Network.txt";
	public static void main(String[] args) throws FileNotFoundException
	{
		ArrayList<Site> sites = listOfSites(fileName);
		ArrayList<Vehicle> vehicleList = vehicleReading(fileName, sites);
		ArrayList<Cargo> cargoList = cargosReadings(fileName, sites);

		FileReader(fileName, sites);
		
		int clock = 0;
		while(Cargo.activeCargo(cargoList) != null)
		{
			System.out.println("TIME " + clock);
			for(int i = 0; i < vehicleList.size(); i++)
			{
				vehicleList.get(i).Act(cargoList, clock);
			}
			clock++;
		}
		
		
	}
	private static void FileReader(String fileName, ArrayList<Site> sites) throws FileNotFoundException
	{
		Scanner scan = new Scanner(new File(fileName));
		String line;

		ArrayList<Route> routes = new ArrayList<>();

		int id = 0; 
		int cost = 0;
		int time = 0;
		String start = "";
		String end = "";
		RouteType type = RouteType.road;
		while(scan.hasNextLine())
		{
			line = scan.nextLine();
			if(line.length() < 1)
			{
				break;
			}
			else if(line.startsWith("#"))
			{
				continue;
			}
			String[] temp = line.split("\\s");
			for(int x = 0; x < temp.length; x++)
			{
				switch(x)
				{
				case 0:
					id = Integer.parseInt(temp[x]);
					break;
				case 1:
					start = temp[x];
					break;
				case 2:
					end = temp[x];
					break;
				case 3:
					cost = Integer.parseInt(temp[x]);
					break;
				case 4:
					time = Integer.parseInt(temp[x]);
					break;
				case 5:
					type = RouteType.valueOf(temp[x]);
					break;
				}
			}
			Site temppy = sites.get(sites.indexOf(Site.searchNames(sites, start)));
			temppy.addRoute(new Route(temppy, sites.get(sites.indexOf(Site.searchNames(sites, end))), cost, time, type));
		}
	}
	public static ArrayList<Site> listOfSites(String fileName) throws FileNotFoundException
	{
		ArrayList<Site> sites = new ArrayList<>();
		Scanner scan = new Scanner(new File(fileName));
		String line;

		while(scan.hasNextLine())
		{
			line = scan.nextLine();
			if(line.length() < 1)
			{
				break;
			}
			else if(line.startsWith("#"))
			{
				continue;
			}

			String[] temp = line.split("\\s");
			for(int i = 1; i <= 2; i++)
			{
				if(sites.indexOf(Site.searchNames(sites, temp[i])) < 0)
				{
					sites.add(new Site(temp[i]));
				}
				else
				{
					continue;
				}
			}
		}
		return sites;
	}
	public static ArrayList<Vehicle> vehicleReading(String fileName, ArrayList<Site> sites) throws FileNotFoundException
	{
		ArrayList<Vehicle> vehicles = new ArrayList<>();
		Scanner scan = new Scanner(new File(fileName));
		boolean turnyThingy = false;
		RouteType type = RouteType.all;
		String name = "";
		String location = "";
		int weight = 0;

		String line;

		while(scan.hasNextLine())
		{
			line = scan.nextLine();
			if(!line.equals("#vehicles") && !turnyThingy)
			{
				continue;
			}
			else if(line.length() < 1)
			{
				break;
			}
			else if(line.startsWith("#vehicles"))
			{
				turnyThingy = true;
				continue;
			}
			else if(line.startsWith("#"))
			{
				continue;
			}

			String[] temp = line.split("\\s");
			for(int i = 0; i < temp.length;i++)
			{
				switch(i)
				{
				case 0:
					type = RouteType.valueOf(temp[i]);
					switch(type)
					{
					case water:
						weight = 15;
						break;
					case air:
						weight = 25;
						break;
					case road:
						weight = 5;
						break;
					case train:
						weight = 100;
						break;
					}
					break;
				case 1:
					name = temp[i];
					break;
				case 2:
					location = temp[i];
					break;
				}
			}
			vehicles.add(new Vehicle(name, sites.get(sites.indexOf(Site.searchNames(sites, location))), weight, type, VehicleStatus.Idle));
		}
		return vehicles;
	}
	public static ArrayList<Cargo> cargosReadings(String fileName, ArrayList<Site> sites) throws FileNotFoundException
	{
		ArrayList<Cargo> cargos = new ArrayList<>();
		Scanner scan = new Scanner(new File(fileName));
		String line;
		boolean turnyThingy = false;

		int time = 0;
		String name = "";
		String start = "";
		String destination = "";
		int weight = 0;

		while(scan.hasNextLine())
		{
			line = scan.nextLine();
			if(!line.equals("#cargo list") && !turnyThingy)
			{
				continue;
			}
			else if(line.length() < 1)
			{
				break;
			}
			else if(line.startsWith("#cargo list"))
			{
				turnyThingy = true;
				continue;
			}
			else if(line.startsWith("#"))
			{
				continue;
			}

			
			String[] temp = pureString(line);
			for(int i = 0; i < temp.length;i++)
			{
				switch(i)
				{
				case 0:
					time = Integer.parseInt(temp[i]);
					break;
				case 1:
					name = temp[i];
					break;
				case 2:
					start = temp[i];
					break;
				case 3:
					destination = temp[i];
					break;
				case 4:
					weight = Integer.parseInt(temp[i]);
					break;
				}
			}
			Site starty = sites.get(sites.indexOf(Site.searchNames(sites, start)));
			Site desty = sites.get(sites.indexOf(Site.searchNames(sites, destination)));
			cargos.add(new Cargo(time, name, starty, desty, weight, CargoStatus.Idle));
		}
		return cargos;
	}

	public static String[] pureString(String name)
	{
		String[] tinyString = new String[5];
		String temp; 
		String namey = name + ",";
		for(int i = 0, counter = 0; 0 <= namey.indexOf(',');counter++)
		{
			temp = namey.substring(i, namey.indexOf(','));
			temp = temp.trim();
			tinyString[counter] = temp;

			i = namey.indexOf(',');
			namey = namey.replaceFirst(",", " ");
		}
		return tinyString;

	}
}



class Site
{
	//Fields
	String name;
	ArrayList<Route> routes = new ArrayList<>(); //routes
	ArrayList<Cargo> cargos = new ArrayList<>();

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
				if (r.type == type || type == RouteType.all) //are we allowed to use this route
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

	public static ArrayList<Integer> schedule(ArrayList<Site> path, int currentTime)
	{
		ArrayList<Integer> timeSchedule = new ArrayList<Integer>(path.size());
		timeSchedule.add(currentTime);
		for(int i = 1; i < path.size(); i++)
		{
			timeSchedule.add(Route.getRoute(path, path.get(i)).time + timeSchedule.get(i-1));
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
				heehee = temp.routes.get(i);
				break;
			}
		}
		return heehee;
	}

	public static Boolean whereRoute(ArrayList<Site> route)
	{
		if(route == null)
		{
			return true;
		}
		return false;
	}
}

class Vehicle
{
	String name;
	int maxWeight;
	int currentLoad;
	private Site location;
	public RouteType type;
	VehicleStatus status;
	Cargo payload;
	ArrayList<Site> route;
	ArrayList<Integer> timeStamps;

	public Vehicle(String name, Site startingLocation, int maxW, RouteType type, VehicleStatus status)
	{
		this.name = name;
		this.location = startingLocation;
		this.maxWeight = maxW;
		this.type = type;
		this.status = status;
	}

	//This rotates through the cycle of Vehicle Statuses 
	public void Act(ArrayList<Cargo> cargo, int currentTime) 
	{
		//This checks the status of the vehicle
		if(this.checkStatus(VehicleStatus.Idle))
		{
			//This loops through the Cargo looking for idle Cargo
			for(Cargo c : cargo)
			{
				//Once it finds an idle cargo it then assigns a route to the car
				if(c.checkStatus(CargoStatus.Idle))
				{
					this.route = Route.findPath(this.currentLocation(), c.currentLocation, this.type);
					if(Route.whereRoute(route))
					{
						System.out.println(this.name + " has no path");
						break;
					}

					this.timeStamps = Route.schedule(route, currentTime);
					System.out.println(this.name + " PATH IS " + this.route);
					this.status = VehicleStatus.OnRouteToCargo;
					payload = c.split(this.maxWeight);
					payload.status = CargoStatus.VehicleOnRoute;
					System.out.println(this.name + " is on Route to " + payload.name);
					break;
				}
			}
		}
		//This keeps the Vehicle moving towards the Cargo until it reaches its destination
		else if(this.checkStatus(VehicleStatus.OnRouteToCargo))
		{
			if(this.currentLocation().equals(payload.currentLocation)) //Once it arrives it picks up the cargo
			{
				payload.status = CargoStatus.Transit;
				this.status = VehicleStatus.DeliveringCargo;
				this.route = Route.findPath(this.currentLocation(), payload.destination, this.type);
				System.out.println(this.name + " PATH IS " + this.route);
				
				this.timeStamps = Route.schedule(route, currentTime);
				System.out.println(this.name + " has acquired " + payload.name);
			}
			else
			{
				//This method only updates the location of the Vehicle as it is traveling to the cargo
				this.traveling(this.route, this.timeStamps, currentTime, this.type);
			}
		}
		else if(this.checkStatus(VehicleStatus.DeliveringCargo))
		{
			//This checks if the vehicle location is equal to the destination
			if(this.currentLocation().equals(payload.destination))
			{
				this.status = VehicleStatus.Idle;
				payload.reachedDestination();//This checks the Cargo's internal location
				System.out.println(this.name + " has delivered the " + payload.name);
			}
			else
			{
				//Overloading the traveling method so that it also updates the cargo's location
				this.traveling(this.route, this.timeStamps, currentTime, payload , this.type);
			}
		}
	}

	//This method updates the location of the Vehicle 
	public void traveling(ArrayList<Site> path, ArrayList<Integer> timeStamps, int currentTime, RouteType type)
	{
		if(!this.currentLocation().equals(path.get(path.size()-1)))
		{
			int spot = path.indexOf(this.currentLocation());
			if(timeStamps.get(spot+1) == currentTime && this.type == type)
			{
				this.location = path.get(spot+1);
				System.out.println(this.name + " has arrived in " + path.get(spot+1));
			}
		}
	}

	//This method overloads the traveling method so that it updates both cargo and the vehicle's location
	public void traveling(ArrayList<Site> path, ArrayList<Integer> timeStamps, int currentTime, Cargo payload, RouteType type)
	{
		if(!this.currentLocation().equals(path.get(path.size()-1)))
		{
			int spot = path.indexOf(this.currentLocation());
			if(timeStamps.get(spot+1) == currentTime && this.type == type)
			{
				this.location = path.get(spot+1);
				payload.updateLocation(location);
				System.out.println(this.name + " has arrived in " + path.get(spot+1));
			}
		}
	}

	public Site currentLocation()
	{
		return location;
	}

	public void yoink(Cargo yeetThis)
	{
		this.payload = yeetThis;
	}

	public boolean checkStatus(VehicleStatus check)
	{
		return check == this.status;
	}

	public void updateStatus(int status)
	{
		switch(status)
		{
		case 0:
			this.status = VehicleStatus.Idle;
			break;
		case 1:
			this.status = VehicleStatus.OnRouteToCargo;
			break;
		case 2:
			this.status = VehicleStatus.DeliveringCargo;
			break;
		}
	}

	public String toString()
	{
		return location.name;
	}
}
class Cargo
{
	String name;
	ArrayList <Site> route;
	Site currentLocation;
	Site destination;
	Vehicle convoy;
	CargoStatus status;
	ArrayList<Cargo> split = new ArrayList<>();
	public int weight; //hide this
	public int time; //hide this
	private static int internalID = 0;
	private int ID;

	//private ArrayList<Cargo> scattered = new ArrayList<Cargo>();
	public Cargo(int time, String name, Site currentLocation, Site destination, int weight, CargoStatus status)
	{
		this.time = time;
		this.name = name;
		ID = internalID++;
		this.currentLocation = currentLocation;
		this.destination = destination;
		this.weight = weight;
		this.status = status;
		route = Route.findPath(currentLocation, destination, RouteType.all);
		currentLocation.addCargo(this);
		split.add(this);
		//scattered.add(new Cargo(this.currentLocation, this.destination, this.weight, this.reached));
	}
	public Site ping()
	{
		if(!this.checkStatus(CargoStatus.Arrived))
		{
			return currentLocation;
		}
		return null;
	}

	//This updates the location of the cargo internally and in sites class
	public void updateLocation(Site newLocation)
	{
		this.currentLocation.removeCargo(this);
		this.currentLocation = newLocation;
		this.currentLocation.addCargo(this);

		System.out.println(this.name + " is located at " + newLocation);
	}

	public Cargo split(int avaliableWeight) 
	{
		if(avaliableWeight < this.weight)
		{
			this.weight -= avaliableWeight;
			Cargo temp = new Cargo(this.time, this.name, this.currentLocation, this.destination, avaliableWeight, this.status);
			this.split.add(temp);
			System.out.println(this.name + " has " + this.weight + " weight left at " + this.currentLocation);
			return temp;
		}
		else
		{
			return split.get(0);
		}
	}

	public void checking()
	{
		for(int i = 0; i < this.split.size(); i++)
		{

			System.out.println(this.split.get(i).status);
		}
	}

	public void reachedDestination()
	{
		if(currentLocation == destination)
		{
			this.status = CargoStatus.Arrived;
			System.out.println(this.name + " HAS BEEN DELIVERED");
		}
	}

	public boolean checkStatus(CargoStatus check)
	{
		return this.status == check;
	}

	public static Cargo activeCargo(ArrayList<Cargo> cargoList)
	{
		for(int i = 0; i < cargoList.size(); i++)
		{
			if(cargoList.get(i).status == CargoStatus.Idle)
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