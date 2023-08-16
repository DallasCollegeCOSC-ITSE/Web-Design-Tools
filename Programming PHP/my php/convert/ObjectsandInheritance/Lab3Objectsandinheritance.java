
public class Lab3Objectsandinheritance {
	/**
	 * This is the program driver (where the program starts)
	 * It is in charge of creating the race and it's participants and telling them to "go" in the race.
	 * @author vjl8401
	 */
		public final static int raceDuration = 1000; //store the length of the race (can be accessed anywhere in code)

		public static void main(String arg[])
		{
			
			//Instantiating my object instances	
				//Engine engine = new Engine(67);
				//Vehicle c1 = new Car(1,new Engine(50),2); //this one I'm storing as the base class (vehicle)			
				Car c2 = new Car(2,new Engine(94),4);    //this one I store as the sub class (car). There are differences but they do not come into play here
				Truck t1 = new Truck(3,new Engine(87),2,250);
				Motorcycle m1 = new Motorcycle(4,new Engine(30),1);
			//This is an array
			Vehicle[] allVehicles = new Vehicle[3];
			//placing the vehicles into an array
				allVehicles[0] = c2;   //polymorphism ("isa")
				allVehicles[1] = t1;   //remember just because I store these as vehicles doesn't mean that the 
				allVehicles[2] = m1;   //methods for them has changed. Each still stores its own go method.
						
			//infinite loop (well without the base case it is)
			
				for(int i = 0; i<20; i++)
				{
					
				
			while (true) //this will run until a race participant crosses the finish line (passes raceDuration)
			{
				int max=0;
				//tell the cars to "go" one by one
					for (int j=0; j<allVehicles.length;j++) //3 times
					{
						Vehicle v = allVehicles[j];
										
						v.Go();//polymorphism
						
						System.out.println(v);
						max = Math.max(max,v.getRaceProgress());					
					}
					System.out.println();
				//check to see if someone has won the race
					if (max > raceDuration)
					{
						break;
					}
			}		
			System.out.println("We have a winner!!! \n*** Vehicle "+GetFurthestVehicle(allVehicles)+" *** \n");
			//check for winner
			for(int k=0; k<allVehicles.length; k++)
			{
				//check if Vin == the winning vehicle
				if(allVehicles[k].getVIN() == GetFurthestVehicle(allVehicles))
				{
					allVehicles[k].addWin();
				}
				
				System.out.println("*** Vehicle "+allVehicles[k].getVIN()+" Total Wins"+ " ***");
				System.out.println(allVehicles[k].getNumWins());
			
				
			}
			//reset
			for(int l=0; l<allVehicles.length; l++)
				allVehicles[l].reset();
				
			
				}
				
		//TODO High score algo is broken 
			int highestScore = 0;
			
			for(int m=0; m<allVehicles.length; m++)
			{
				int score = (int)(allVehicles[m].getNumWins()); 
				
				if (highestScore < score)
				{
					highestScore = score;
				}
				else if (highestScore == score)
					break;
				else
					continue;
				System.out.println("*** Vehicle "+allVehicles[m].getVIN()+" Total Wins"+ " ***"+"Is the Overall Winner!!!");
			}
		}
			//just a helper method to find out which vehicle won the race
		
			
			public static int GetFurthestVehicle(Vehicle[] allVehicles)
		{
			int max=0;
			int VIN=0;
			for (int i=0; i<allVehicles.length;i++)
			{
				if (max < allVehicles[i].getRaceProgress())
				{
					max = allVehicles[i].getRaceProgress();
					VIN = allVehicles[i].getVIN();
				}
			}
			return VIN;
		}
			//TODO output each vehicle's number of wins, output the overall race winner's win total
			//method to calculate and store wins
}
	
	class Engine
	//if a sub class needs to tell a parent class something use super
	{
		//General base class 
		
		 int speed;
		public Engine(int speed)
		{
			this.speed = speed;
		}
		/**
		 * This is the original speed modifier (it may need to be redefined)
		 * @return int random between half the speed and the whole speed
		 */
		 int SpeedModifier()
		{
			//returns speed/2 to maxSpeed
			return (int)(Math.random()*speed/2)+speed/2;
		}
		 
	}
	
	//Base class (abstract means that we can't make Vehicles)
	//class' responsibility to protect its data/attributes
	//learn inheritance write a pattern class that makes the pieces fit
	abstract class Vehicle extends Engine
	{		
		//attributes (fields
		private int passengers;
		private int RaceProgress;
		private int VIN;
		//TODO set/get max passengers
		//TODO output error if more then max amount of passengers are added
		private int maxPassengers;
		private int numWins;
		Engine engine;   //storage (association, aggregation, composition)
		
		//Constructors (used to create the objects):
		Vehicle(int vin, Engine e) 
		{
			super(vin);
			passengers = 1;
			setRaceProgress(0);
			VIN = vin;
			engine = e;
			numWins = 0;
		}	
		
		//Get and set private vars
		public int getPassengers()
		{
			return passengers;
		}
		public void setPassengers(int passengers)
		{
			this.passengers = passengers;
		}
		
		public int getVIN()
		{
			return VIN;
		}
		
		public int getRaceProgress()
		{
			return RaceProgress;
		}
		
		public void setRaceProggress(int RaceProgress) {
			this.setRaceProgress(RaceProgress);
		}
		public void setRaceProggress(float RaceProgress) {
			this.setRaceProgress(RaceProgress);
		}
				
		public int getNumWins() {
			return numWins;
		}
		public void setNumWins(int numWins) {
			this.numWins = numWins;
		}
		public void addWin()
		{
			this.setNumWins(this.getNumWins() + 1);
		}

		/**
		 * This is the main function that progresses the vehicles through the race
		 * this should be called each loop of the program (this must be redefined in each 
		 * subclass of vehicle)
		 */
		abstract public void Go(); //this is correctly coded, the abstract method only has its header, and no body - the body must be overwritten in subclasses
		
		/**
		 * Part of object. This is invoked when an instance of this class is attempted to be used as a string (like during System.out.println)
		 */
		public String toString()
		{
			return "Vehicle: "+VIN+
					" Progress: "+getRaceProgress();		
		}	
		public boolean equals(Object other)
		{
			return this.VIN == ((Vehicle)other).VIN;
		}	
		
		public void reset()
		{
			setRaceProggress(0);
		}
		public void setRaceProgress(int raceProgress) {
			RaceProgress = raceProgress;
		}
		
		public void setRaceProgress(float raceProgress) {
			RaceProgress = (int) raceProgress;
		}
	}
	/**
	 * 
	 * SubClass of Vehicle
	 *
	 */
	class Car extends Vehicle //car "is a" vehicle
	{	
		/**
		 * Car Constructor (no return specified)
		 * @param i = (0,100) 
		 * @param passengers
		 * @param speed
		 */
		Car(int i, Engine e, int passengers) //Working constructor
		{			
			//super or this
			//super(); implied
			super(i,e);//calling the constructor in Vehicle
			if(passengers <= 5)
				this.setPassengers(passengers);
			
		}
		public String toString()
		{
			return "Car::"+super.toString();		
		}
		//This is overwriting the super/base classes method
		//car satisfies the vehicles Go requirement
		public void Go()
		{
			setRaceProgress(getRaceProgress() + (engine.SpeedModifier() - 10 * (getPassengers()-1)));		
		}
		
		
	}
	//Another subclass of Vehicle (this is considered a concrete class because it is not abstract)
	class Truck extends Vehicle
	{
		//This is data that exists only in trucks
		int passengers;
		int towWeight;//special note that vehicle cannot access this. In order for it to do so a 
		              //cast operation must be applied to a valid truck.
		Truck(int i, Engine e, int passengers, int towWeight)
		{
			super(i,e);
			this.passengers = passengers;
			this.towWeight = towWeight;
		}
		public String toString()
		{
			return "Truck::"+super.toString();		
		}
		//truck satisfies the vehicles Go requirement
		public void Go()
		{
			setRaceProgress(getRaceProgress() +  (engine.SpeedModifier() - (0.1f * towWeight)));
		}	
		
		public void addWin()
		{
			
			this.setNumWins(this.getNumWins() + 1);
		}
		
	}
	//class motorcycle extends vehicle 
	class Motorcycle extends Vehicle
	{
		 boolean crash;
		 Motorcycle(int i, Engine e, int passengers)
		{
			super(i,e);
			if(passengers == 1)
				this.setPassengers(passengers);
			
			//did Motorcycle crash
			this.crash = false;
		}
		public void GO()
		{
			if(Math.random() < .05)
			{
				this.crash = true;
			}
			if(crash)
			{
				this.setRaceProggress(0);
				System.out.println("Motocycle Has Crashed");
			}
		}
		
		public void setPassengers(int passengers) {
			// TODO Auto-generated method stub
			
		}
		public String toString()
		{
			return "Motocycle::"+super.toString();
		}
		public void Go()
		{
			setRaceProgress(getRaceProgress() + (engine.SpeedModifier() - 10 * (getPassengers()-1)));
			
		}	
		
	}