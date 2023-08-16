
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MyShippingNetwork 
{
	public static void main(String[] args) throws InterruptedException, FileNotFoundException 
	{
		
		//test sites
		Site sc = new Site("Short Cut");
		Site B = new Site("B");
		Site C = new Site("C");
		Site D = new Site("D");
		
		
		
		sc.addRoute(new Route(sc,B,1,2,RouteType.Truck));
		sc.addRoute(new Route(B,sc,2,1,RouteType.Truck));
		sc.addRoute(new Route(sc,D,1,1,RouteType.Ship));
		B.addRoute(new Route(B,C,5,2,RouteType.Truck));
		C.addRoute(new Route(C,D,2,2,RouteType.Truck));
		
		Cargo c = new Cargo("cargo", sc, B, 10, 10);
		
		//final String fileName = "./Lab5/Network.txt";
			//readFile(fileName);
		
		ArrayList<Site> samplePath = new ArrayList<>();
				
		ArrayList<Site> path = findPath(sc,B,RouteType.All);
		
		System.out.println(findPath(sc, B, RouteType.All));
		
		Vehicle test = new Vehicle("Test",RouteType.Truck,sc, D, null, 0, path);
		
		//loop
		for(int i = 0; i >= 100; i++)
		{
			//
			test.act();
			c.act(i);
			
			
			//tick through all of the cargo, if it is awake then we tell a vehicle to get the cargo
			Thread.sleep(500);
			//cargo.act(time);

		}
	}//end of main
		public static final ArrayList<Site> findPath(Site origin, Site destination, RouteType type)
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
					System.out.println("Found path: "+current.pathToReach);
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
														//what happens if the file is not found
		public static void readFile (String fileName) throws FileNotFoundException
		{
			Scanner s = new Scanner(new File(fileName));
			String line = null;
			while(s.hasNextLine())
			{
				line = s.nextLine();
				
				if(line.equals("#vehicles"))
					break;
				
				if(line.startsWith("#")||line.length() < 5)
					continue;
					//						 //s is for whitespaces
				//with cargo split by comma and whitespace
				String tokens[] = line.split("\\s");
				int ID = Integer.parseInt(tokens[0]);
				
				Site start = (Site.findSiteByName(tokens[1]));
				if(start==null)
					start = new Site(tokens[1]);
				
				Site end = (Site.findSiteByName(tokens[1]));
				if(end==null)
					end = new Site(tokens[2]);
				
				int cost = Integer.parseInt(tokens[3]);
				int time = Integer.parseInt(tokens[4]);
				
				RouteType rt = RouteType.getRouteTypeByName(tokens[5]);
				
				start.addRoute(new Route(start,end,time,cost,rt));
					
			}
			//TODO Vehicles file reading
			//TODO Cargo file reading
			
			
			
		}

}//end of main class


//	enum RouteType
		{
			Ship("water"),
			Truck("road"),
			Train("train"),
			Plain("air"),
			All("all");
		
		String typeName;
		RouteType(String t)
		{
			typeName = t;
		}
		
		public static RouteType getRouteTypeByName(String type)
		{
			for(RouteType rt : RouteType.values())
				if(rt.toString().equals(type))
					return rt;
			return null;
		}
		}
	//class Route {
		
		Site start,end;
		int cost,time;
		RouteType type;
		
		public Route(Site start, Site end, int cost, int time, RouteType type) 
		{
			super();
			this.start = start;
			this.end = end;
			this.cost = cost;
			this.time = time;
			this.type = type;
		}	
	}

	// class Site {
		//nothing stores sites
		 private static ArrayList<Site> allSites = new ArrayList<>();
		 
		 private static ArrayList<Cargo> allCargo = new ArrayList<>();

		String name;
		ArrayList<Route> routes = new ArrayList<>();

		transient ArrayList<Site> pathToReach = new ArrayList<>();

		public Site(String name)
		{
			this.name = name;
		}
		public static Site findSiteByName(String name)
		{
			for(Site s : allSites)
				if(s.name.equals(name))
					return s;
				return null;
		}
		public void addRoute(Route route)
		{
			routes.add(route);
		}
		@Override
		public String toString()
		{
			return name;
		}
		public static ArrayList<Site> findPath(Site location,
				Site location2, RouteType type) {
			return(findPath(location,location2,type));
		}
		public static int getPathCost(ArrayList<Site> path) {
		
			return getPathCost(path);
		}
	}
	 
	// class Vehicle
	 {
		 private static ArrayList<Vehicle> allVehicles = new ArrayList<>();
		 String name;
		 RouteType type;
		 Site location,moveTarget;
		 Route route;
		 int travleTime;
		 ArrayList<Site> path;
		
		 public Vehicle(String name, RouteType type, Site location, Site moveTarget, Route route, int travleTime,
				ArrayList<Site> path) {
			super();
			this.name = name;
			this.type = type;
			this.location = location;
			this.moveTarget = moveTarget;
			this.route = route;
			this.travleTime = travleTime;
			this.path = path;
		}
		 
		 public void setMoveTarget(Site goHere)
		 {
			 moveTarget = goHere;
			 path = findPath(location,moveTarget,type);
			 path.remove(0); //i'm already at this n so lets take it off
			 //A->B->C
			 
			 //TODO turn this loop into it's own function because we are going to reuse it
			 for(Route r : location.routes)//loop through all locations until I find the right path 
				 						   //with the right vehicle type then set the route and the travel time
				 if(r.end == path.get(0) && type == r.type)
					 route = r;
			 travleTime = route.time;
		 }
		 
		 private ArrayList<Site> findPath(Site location2, Site moveTarget2, RouteType type2) {
			return findPath(location2,moveTarget2,type2);
		}

		//call this each loop (hour) to run the vehicle
		 public void act()
		 {
			 if(moveTarget == null)
			 {
				 //TODO look for things to do
			 }
			 else // we have a moveTarget and a path
			 {
				 travleTime--;
				 if(travleTime <= 0)
				 {
					 location = path.get(0);//moving are vehicle to the next index in the route
					 
					 ArrayList<Cargo> myCargo = new ArrayList<>();//init arrayList to store any cargo we may find
					 
					 for(Cargo c : myCargo)//look for any cargo in the current location
					 {
						 
					 }
					 if(!myCargo.isEmpty()) // if cargo is found

					 {
						 for(Cargo c : myCargo) //loop through all cargos in current location
						 {
							 if(path.isEmpty())//then I can pick things up
							 {
								 if(c.checkRoute(type))//check if the cargo's fastest route is compatible
								 {
									 	System.out.printf("Location of the cargo is :"+location+"\n","The Destination of the cargo is :"+c.destination+"\n");
									 	myCargo.add(c);
									 	//TODO Add null exception and check if we can move the cargo Part way
									 	setMoveTarget(c.destination);
									 	
								 }
								 else// if we cannot move the cargo in any significant way move on to the location in the route
								 {
									 path.remove(0);
									 
									 location = path.get(0);
								 }
							 }
						 }
					 }
					 else
					 {
						 //System.out.println(myCargo.+"Cargo");
						 System.out.print("Vehicle of type :"+type+" Has countinued along its route to :"+location);
					 }
					 for(Route r : location.routes)//loop through all locations until I find the right path 
						   //with the right vehicle type then set the route and the travel time
						 if(r.end == path.get(0) && type == r.type)
							 route = r;
					 travleTime = route.time;
					 
				 }
			 }
		 }
		 
		 public static <bestTime> Vehicle findVehicle(RouteType type, Site location)
		 {
			 ArrayList<Vehicle> availableVehicles = new ArrayList<>();
			 for(Vehicle v : allVehicles)
			 if(v.moveTarget == null && v.type==type)
				 allVehicles.add(v);
			 //get the travel time to the location
			 int bestTime = Integer.MAX_VALUE;
			 Vehicle bestVehicle = null;
			 for(Vehicle v : allVehicles)
			 {
				 ArrayList<Site> path = Site.findPath(v.location, location, v.type);
				 if(path != null)
				 {
					 
					if(getPathCost(path) < bestTime)
					 {
						 bestTime = Site.getPathCost(path);
						 bestVehicle = v;
						 
					 }
				 }
			 }
			return bestVehicle;
		 }

		private static int getPathCost(ArrayList<Site> path2) {
			return getPathCost(path2);
		}
	 
	 }
	 
	 enum CargoState
	 {
		 notAvailable,
		 Available,
		 WaitingForPickup,
		 TravelingWithVehicle,
		 Deliverd; 
	 }
	 
	 //class Cargo
	 {
		 String name;
		 Site location;
		 Site destination;
		 int size;
		 int awakeTime; // the time it would take to reach the cargo
		
		 public Cargo(String name, Site location, Site destination, int size,
				 int awakeTime) 
		 {
			 super();
			 this.name = name;
			 this.location = location;
			 this.destination = destination;
			 this.size = size;
			 this.awakeTime = awakeTime;
		}
		 
		 ArrayList<Site> idealPath;
		 CargoState state = CargoState.notAvailable; 
		 Vehicle transport = null;
		 
		 public Cargo(Cargo cargo, int size)
		 {
			 cargo.size -= size;
			 this.size = size;
			 this.name = cargo.name;
			 this.location = cargo.location;
			 this.destination = cargo.destination;
			 this.awakeTime = cargo.awakeTime;
			 this.idealPath = cargo.idealPath;
			 this.state = cargo.state;
			 this.transport = cargo.transport;
		 }
		 
		 public void act(int hour)//passing in time
		 {
			 if(state == CargoState.notAvailable && hour >= awakeTime)
			 {
				 state = CargoState.Available;
				 location.addRoute(null);
				 
			 }
			 if(state == CargoState.Available)
			 {
				 //Find a Vehicle to take cargo to a destination
				 idealPath = Site.findPath(location, location, RouteType.All);
				 idealPath.remove(0);
				 RouteType r = null;
				 for(Route s : location.routes)
					 //TODO fix this
					 //if(r.All == s.idealPath().get(0))
						 r=s.type;
				 Vehicle transport = Vehicle.findVehicle(r, location);
				 		 System.out.println(transport.name+" Has pick up-ed cargo ");
				 transport.setMoveTarget(destination);
				 state = CargoState.WaitingForPickup;
						 
			 }
			 //TODO add all other states
		 }
		 
		 public boolean checkRoute(RouteType type)
		 {
			 RouteType routeType = null;
			 for(Route r : location.routes)
				 if(r.end == idealPath.get(0))
					 routeType = r.type;
			 return routeType == type;
		 }
	 
	 }

	

/*
 * find path
 * move vehicle from location a to b
 * move vehicle from a to b pick up cargo and deliver it to c
 * move vehicle to pick up cargo but cargo is too big (split cargo into separate loads)
 * move vehicle with cargo and find cargo we can pick up along the way
 * move vehicle with cargo and find cargo we can't pick up
 * 2 vehicles now moving to pick up cargo, the other vehicle gets their first wa it is taking the cago
 * cargo needs to move to a place that requires 2 different vehicles to take it there
 * 
 * read the file add up the cost and the time
 * output the results
 * 
 * 
 */