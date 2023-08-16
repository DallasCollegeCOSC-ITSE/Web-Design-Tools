import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static void main (String[] args) throws FileNotFoundException, InterruptedException
	{
		//Read from file the BoardProperties //
		String fileName = "F:\\eclipse-jee-2021-12-R-win32-x86_64\\eclipse\\Workspace\\Project 1 MarioParty BoardGame\\src\\GameBoard.txt";
		boolean valid = true;
		
		BoardProperties p = new BoardProperties(fileName);
		Board b = new Board();
		HumanPiece h = new HumanPiece("H",b);
		
		SmartPiece ai = new SmartPiece("AI",b);
		b.findNewStarPlace();
		b.DrawBoard();
		
		
		Scanner s = new Scanner(System.in);
		
		
		for(int i = 30; i>0; i--)
		{
			
			System.out.println("enter move");
			
			h.printMoves();
			String line = s.next();
			
			int number = 0;
			
			//Validate input
			//TODO BUG: if non valid input is passed game breaks
			for(char c : line.toCharArray())
				{
					if(!Character.isDigit(c))
					{
						valid = false;
						System.err.println("Enter a Valid Move");
						System.out.println(" 8 is up, 2 is down, 4 is left, and 6 is right ");
						i+=1;
						continue;
					}	
				}
			
			if(valid)
			{
				number = Integer.parseInt(line);
			}
			
			//Move players
			if(h.move(number,b.Nodes))
			{
				 ai.Move();
				b.DrawBoard();
				
			}
			
			b.UpdateBoard();
		}
		
	}
	
	public static boolean startMiniGame()
	{
		//TODO fix pong, very broken
		Pong pong = new Pong();
		
		Window window = new Window();
		Thread t1 = new Thread(window);	//window need to be on a separate thread for sleeping and etc
		
		t1.start();
		return true;
	}
}
