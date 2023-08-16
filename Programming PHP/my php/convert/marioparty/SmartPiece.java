import java.util.ArrayList;
import java.util.Arrays;

class SmartPiece extends Piece
{
	
	SmartPiece(String name, Board b) throws InterruptedException
	{
		
			super(name);
			
			this.Coins=0;
			this.Stars=0;
				//Land of the starting GameNode
			
			this.Location=randomizeLocation(b.Nodes);
	}
	
	public Node randomizeLocation(Node Nodes[][]) throws InterruptedException
	{
		for(int i=(int)(Math.random()*Nodes.length); i<Nodes.length;i++)
			for(int j=(int)(Math.random()*Nodes[i].length); j<Nodes[i].length;j++)
			{
			if(!Nodes[i][j].isBlank)
			{
				this.Location = Nodes[i][j];
				
				Nodes[i][j].land(this);
				
				return Nodes[i][j];
			}
			continue;	//pick another node if node is blank
		}
		return null;
	}
	public boolean Move() throws InterruptedException
	{
		ArrayList<ArrayList<Node>> paths = getAllPossiblePaths(5);//im thinking int steps ahead of you
		
		for(ArrayList<Node> p : paths)
			System.out.println(p);
		
		
		
		
		while(!paths.isEmpty())
		{
			
			int i=0;
			if(paths.get(i)==Location.NextNodes)
			{
				System.out.print("Yes");
				
				
				return false;
			}
			else
			
			setLocation(this,Location.NextNodes.get(i));
			
			
			System.out.println(++i);
			
			System.out.println(Location.NextNodes);

				paths.remove(0);
				
				return false;
		}

		return false;
	}
	
	 //TODO fix this
	private void setLocation(SmartPiece smartPiece, Node node) throws InterruptedException 
	{
		Location.unland(smartPiece);
		node.land(smartPiece);
		
		Location = node;
		
	}

	public ArrayList<ArrayList<Node>> getAllPossiblePaths(int step) 
	{
		ArrayList<ArrayList<Node>> paths = new ArrayList<ArrayList<Node>>();
		ArrayList<Node> open = new ArrayList<>();
		this.Location.pathToGetTo = new ArrayList<>(Arrays.asList(Location));
		
		open.add(Location);
		
		System.out.println("the path to the next node:"+Location.pathToGetTo);
		
		while(!open.isEmpty())
		{
			Node c = open.get(0);//current  node
			open.remove(0);
			
			if(c.pathToGetTo.size() > step)
			{
				paths.add(c.pathToGetTo);
			}else
			{
				for(Node x : c.NextNodes)
				{
					ArrayList<Node> connectedPath = new ArrayList<Node>(c.pathToGetTo);
					connectedPath.add(x);
					x.pathToGetTo = connectedPath;
					open.add(x);
				}
			}
		}
		return paths;
	}
	
	
}
