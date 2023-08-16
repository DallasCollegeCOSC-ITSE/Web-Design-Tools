//I have Questions 

public class Cars
	{
	public static Cars bob;
	public static Cars Pat;
	public static Cars Alex;
	public static Cars BobE;
	public static Cars Steve;
	public static Cars Dick;
	String Name;
	 float  HandlingS;
	 float  HandlingC;
	 float  HandlingU;
	 float  cRaceProgress;
	 float  CurrentSpeed;
	 float  TopSpeed;
	 float  Acceleration;
	 int    Wins;
	 char   Symbol;
	
	
	public  Cars (String Name, float HandlingS, float HandlingC, float HandlingU, float cRaceProgress, float CurrentSpeed, float TopSpeed, float Acceleration, int Wins, 
			char Symbol ) 
			{
		 		
			super();
			Name = Name;
			HandlingS = HandlingS;
			HandlingC = HandlingC;
			HandlingU = HandlingU;
			cRaceProgress = cRaceProgress;
			CurrentSpeed = CurrentSpeed;
			TopSpeed = TopSpeed;
			Acceleration = Acceleration;
			Wins = Wins;
			Symbol = Symbol;
			
			String Progress = " ";
			for (int i=0; i <= Progress.length(); i++)
			{
				Progress += " ";
				System.out.println(Progress + Symbol);
				//ToDO fix this
				cRaceProgress = (Progress.length() + cRaceProgress);
			}
				
				
			}
	}//end of car class
				
		

public class lab2_RaceTrack
{
		
	public static void main (String[] args )
			
	{		
		
		
		Cars.bob = new Cars("Bob", 0.30f, 0.62f, 0.1f,0.0f, 0.0f, 0.93f, 0.11f, 0, '1');
				
		Cars.Pat = new Cars ("Pat", 0.45f, 0.65f, 0.3f, 0.0f, 0.0f, 0.75f, 0.08f, 0,'P');
				
		Cars.Alex = new Cars("Alex", 0.29f, 0.55f, 0.2f, 0.0f, 0.0f, 0.83f, 0.19f, 0,'A');
				
		Cars.BobE = new Cars ("BobE", 0.15f, 0.20f, 0.03f, 0.0f, 0.0f, 0.9f, 0.21f, 0,'D');
				
		Cars.Steve = new Cars ("Steve", 0.20f, 0.22f, 0.05f, 0.0f, 0.0f, 0.92f, 0.19f, 0,'H');
				
		Cars.Dick = new Cars ("Dick", 0.40f, 0.5f, 0.35f, 0.0f, 0.0f, 0.675f, 0.19f, 0,'R');
		
	
		
		
		
		for (int raceNumber = 0; raceNumber <= 25; raceNumber++)
			{
				
			String Racetrack = genTrack();
			int raceLength = Racetrack.length();
			
				
			boolean runningRace = true; //once someone wins we can stop this
			while (runningRace)
			{
				System.out.println(Racetrack);
							
					//accelerate based on where you are on the track
					
					switch (Racetrack[((int)cRace])
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
							CurrentSpeed = TopSpeed*car1HandlingC;
						break;
					case 'U': //Hairpin
						if (CurrentSpeed < TopSpeed*HandlingU)
							CurrentSpeed += Acceleration;
						else if (CurrentSpeed > TopSpeed*HandlingU)
							CurrentSpeed = TopSpeed*HandlingU;
						break;
					}
					//increase progress
					RaceProgress+=CurrentSpeed;
					
					if (RaceProgress >= raceLength)
					{
						System.out.println(Name+" wins");
						Wins++;
						runningRace=false;
					}	
					//else if(//high score algo for all possable racers)
			}	//end of while loop
		}//end of race loop
	
	}//end of main
			


	
	



	

	public static String genTrack() 
	{
	String raceTrack = new String();
	for(int i=0; i <= 40; i++)
	{
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
			}
		}
	return raceTrack;
	}

}