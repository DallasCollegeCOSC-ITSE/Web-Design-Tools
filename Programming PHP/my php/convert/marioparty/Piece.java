
class Piece
{
	public int Coins;
	public int Stars;
	public Node Location;
	public String Name;
	
	public Piece(String n)
	{
		Name = n;
	}
	
	public Piece()
	{
		this("No name");
	}
	
	public String toString()
	{
		return Name;
	}
	
	public boolean Move() throws InterruptedException
	{
		return false;
	}
	
}
