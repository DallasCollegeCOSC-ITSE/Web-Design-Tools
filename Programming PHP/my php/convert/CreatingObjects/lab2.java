import lab2.cars;

public class lab2 
{	
	public static void main (String[] args )
	{
		
	
		cars.bob = new cars("Bob", 0.30f, 0.62f, 0.1f,0.0f, 0.0f, 0.93f, 0.11f, 0, '1');
		cars.pat = new cars ("Pat", 0.45f, 0.65f, 0.3f, 0.0f, 0.0f, 0.75f, 0.08f, 0,'P');
		cars.alex = new cars ("Alex", 0.29f, 0.55f, 0.2f, 0.0f, 0.0f, 0.83f, 0.19f, 0,'A');
		cars.bobE = new cars ("Bob", 0.15f, 0.20f, 0.03f, 0.0f, 0.0f, 0.9f, 0.21f, 0,'D');
		cars.steve = new cars ("Steve", 0.20f, 0.22f, 0.05f, 0.0f, 0.0f, 0.92f, 0.19f, 0,'H');
		cars.dick = new cars ("Dick", 0.40f, 0.5f, 0.35f, 0.0f, 0.0f, 0.675f, 0.19f, 0,'R');
	
	for (int raceNumber = 0; raceNumber <= 25; raceNumber++)
	{
		
		String Racetrack = "";
		//to-do fix vars' for readablity 
		int raceLength = Racetrack.length();
		switch ((int)(Math.random()*8))
		{
		case 0:
			Racetrack.concat("-");
			break;
		case 1:
			Racetrack += "C";
			break;
		case 2:
			Racetrack += "U";
			break;
		default:
			Racetrack += "-";
		}
		
		boolean runningRace = true; //once someone wins we can stop this
		while (runningRace)
		{
			System.out.println(Racetrack);
			
			//Car 1
				//display the carS
				String Progress = " ";
				for (int i=0; i <= Progress.length(); i++)
					Progress += genTrack(cars.bob);
				System.out.println(Progress);//fix this parma
						
				
		}	//end of while loop
	}//end of main
	
	}
	class cars
	{
		public enum Racers {
			bob,pat,alex,bobE,steve,dick
		}
		
		public static lab2.cars dick;
		
		public static lab2.cars steve;
		public static lab2.cars bobE;
		public static lab2.cars alex;
		public static lab2.cars pat;
		public static lab2.cars bob;
		
		
		//to-do find out if I need this
		
		private String Name;
		private float  HandlingS;
		private float  HandlingC;
		private float  HandlingU;
		private float  cRaceProgress;
		private float  CurrentSpeed;
		private float  TopSpeed;
		private float  Acceleration;
		private int    Wins;
		private char   Symbol;
		
		

		public cars (String Name, float HandlingS, float HandlingC, float HandlingU, float cRaceProgress, float CurrentSpeed, float TopSpeed, float Acceleration, int Wins, char Symbol ) 
			{
				this.Name = Name;
				
				this.HandlingS = HandlingS;
				this.HandlingC = HandlingC;
				this.HandlingU = HandlingU;
				this.cRaceProgress = cRaceProgress;
				this.CurrentSpeed = CurrentSpeed;
				this.TopSpeed = TopSpeed;
				this.Acceleration = Acceleration;
				this.Wins = Wins;
				this.Symbol = Symbol;
			}
	}//end of car class
	
	
	//so complete the entire race generation for each racer (as a string) and print out all racers 
	// then loop that 25 times
	//i'm not confident i'm using objs' properly
	public static String genTrack(lab2.cars racerName)
	{
		//to-do assign all of the proper racing values
		
		float currentSpeedTemp = lab2.cars.racerName.CurrentSpeed;
		for(int i=0; i <= 40; i++)
		{
		
		String raceTrack = new String();
		switch((int)(Math.random()*8))
		{
		case 0:
			raceTrack += "S";
			
			break;
		case 1:
			raceTrack += "C";
			break;
		case 2:
			raceTrack += "U";
			break;
		default:
				raceTrack += "-";
		
		
				//accelerate based on where you are on the track
				//Racetrack[(int)car1RaceProgress)]
				
				
				//increase progress
				
				}	
		switch (raceTrack.charAt(i))
	{
	case '-': //strait-away
		if (CurrentSpeed < TopSpeed)
			CurrentSpeed += Acceleration;
		if (CurrentSpeed > TopSpeed)
			CurrentSpeed = TopSpeed;
		break;
	case 'S': //Chicane
		if (CurrentSpeed < TopSpeed*HandlingS)
			CurrentSpeed += Acceleration;
		else if (CurrentSpeed > TopSpeed*HandlingS)
			CurrentSpeed = TopSpeed*HandlingS;
		break;
	case 'C': //curve
		if (CurrentSpeed < TopSpeed*HandlingC)
			CurrentSpeed += Acceleration;
		else if (CurrentSpeed > TopSpeed*HandlingC)
			CurrentSpeed = TopSpeed*HandlingC;
		break;
	case 'U': //Hairpin
		if (CurrentSpeed < TopSpeed*HandlingU)
			CurrentSpeed += Acceleration;
		else if (CurrentSpeed > TopSpeed*HandlingU)
			CurrentSpeed = TopSpeed*HandlingU;
		break;
	}	
			
		}
		return null;
			}
}

