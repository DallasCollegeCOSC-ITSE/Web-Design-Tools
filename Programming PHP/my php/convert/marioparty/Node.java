import java.util.ArrayList;

class Node {
	public int row, col;
	public int points;

	// hasStar and isBlank are the ?qualities? of the basic n, anything more
	// complicated should be
	// done by extending the n class

	public boolean hasStar;
	public boolean isBlank;

	// public boolean hasMiniGame;

	public ArrayList<Node> NextNodes = new ArrayList<Node>();
	public ArrayList<Piece> Occupants = new ArrayList<Piece>();

	public ArrayList pathToGetTo;

	public Node() {
		this(false);

		points = 0;
	}

	public Node(boolean isBlank) {
		this.isBlank = isBlank;
	}

	public Node(int points, int r, int c) {
		this.points = points;
		row = r;
		col = c;
	}

	// Star logic
	public boolean land(Piece p) throws InterruptedException {
		if (hasStar && p.Coins > 50) {
			System.out.println(p.Name + "Has Bought a Star");
			hasStar = false;
			p.Stars++;
			points = 0;
			p.Coins -= 50;
			return true;
		} else if (hasStar) // and coins are not high enough
		{
			p.Coins -= 0;// STAR Tax
		}
		p.Coins += points; // takes in account how many points the ai player already has
		points = -5;
		Occupants.add(p);

		if (p.Coins < 0)
			p.Coins = 0;
		else if (p.Coins > 100)
			p.Coins = 100;

		return false;
	}

	public Node unland(Piece p) {
		Occupants.remove(p);

		return p.Location;
	}

	public String toString() {
		if (hasStar)
			return Board.FormToBoard("* " + Occupants.toString());
		if (isBlank)
			return Board.FormToBoard("  ");

		return Board.FormToBoard(points + " " + Occupants.toString());
	}

	// ai finds the path with the greatest ammount of coins
	// thanks to this handy method

	public int getPoints(int playerCoinAmount) {
		// TODO fix this nessted if
		if (hasStar) {
			if (playerCoinAmount > 50)
				return 1000;
			else
				return -25;
		}
		return points;
	}

}
