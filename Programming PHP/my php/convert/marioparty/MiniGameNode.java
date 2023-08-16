
public class MiniGameNode extends Node

{
	MiniGameNode(int points, int r, int c)
	{
		this.points = points;
		row = r;
		col = c;
	}
	
	public boolean land(Piece p) throws InterruptedException
	{
		Occupants.add(p);
		
		Pong pong = new Pong();
		
		Window window = new Window();
		Thread t1 = new Thread(window);	//window need tobe on a seprate thread for sleeping and etc
		
		t1.start();
		
		if(window.isActive())
		{
			Thread.sleep(30000);
		}
		
		return true;
	}
	
	public void UnLand(Piece p)
	{
		Occupants.remove(p);
	}
}
