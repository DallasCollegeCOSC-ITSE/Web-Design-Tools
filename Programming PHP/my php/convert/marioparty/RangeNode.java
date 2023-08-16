
class RangeNode extends Node
{
	int pointMax;
	int pointMin;
	
	public RangeNode(int max, int min)
	{
		pointMax = max;
		pointMin = min;
		points = (max+min) / 2;	//used for Ai Calculations
	}
	
	public boolean land(Piece p)
	{
		p.Coins += (int)(Math.random() * (pointMax-pointMin)+ pointMin);
		Occupants.add(p);
		return false;
	}
	public void UnLand(Piece p)
	{
		Occupants.remove(p);
	}
}
