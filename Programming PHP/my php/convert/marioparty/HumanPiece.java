import java.util.ArrayList;
import java.util.Arrays;

class HumanPiece extends Piece {

	HumanPiece(String Name, Board b) throws InterruptedException {
		super(Name);

		this.Coins = 0;
		this.Stars = 0;
		// Land of the starting GameNode

		this.Location = randomizeLocation(b.Nodes);
	}

	public Node randomizeLocation(Node Nodes[][]) throws InterruptedException {
		// TODO this is bad, change it to findnewStar

		for (int i = (int) (Math.random() * Nodes.length); i < Nodes.length; i++)
			for (int j = (int) (Math.random() * Nodes[i].length); j < Nodes[i].length; j++) {
				if (!Nodes[i][j].isBlank) {
					this.Location = Nodes[i][j];

					Nodes[i][j].land(this);

					return Nodes[i][j];
				}
				continue; // pick another node if node is blank
			}
		return null;
	}

	// TODO create methos to do only a single thing
	// ex Land Unland setLocation

	public static ArrayList<ArrayList<Node>> getPossiblePaths(Node location, int step) {
		ArrayList<ArrayList<Node>> allPossiblePaths = new ArrayList<ArrayList<Node>>();
		ArrayList<Node> open = new ArrayList<>();
		open.add(location);
		location.pathToGetTo = new ArrayList<>(Arrays.asList(location));
		while (!open.isEmpty()) {
			Node current = open.remove(0);
			for (Node n : current.NextNodes) {
				ArrayList<Node> newPath = new ArrayList<>(current.pathToGetTo);
				newPath.add(n);
				n.pathToGetTo = newPath;
				System.out.println(n);

				if (newPath.size() >= step)
					allPossiblePaths.add(newPath);
				else
					open.add(n);
			}
		}

		return allPossiblePaths;
	}

	public boolean move(int s, Node[][] nodes) throws InterruptedException {
		int row = this.Location.row;
		int col = this.Location.col;

		// TODO add movment valiadation
		if (s == 8) {
			nodes[row][col].unland(this);
			this.Location = nodes[row - 1][col];
			nodes[Location.row][Location.col].land(this);

			return true;
		}

		if (s == 2) {
			nodes[row][col].unland(this);
			this.Location = nodes[row + 1][col];
			nodes[Location.row][Location.col].land(this);

			return true;
		}
		if (s == 4) {
			nodes[row][col].unland(this);
			this.Location = nodes[row][col - 1];
			nodes[Location.row][Location.col].land(this);

			return true;
		}
		if (s == 6) {
			nodes[row][col].unland(this);
			this.Location = nodes[row][col + 1];
			nodes[Location.row][Location.col].land(this);

			return true;
		}
		return false;

	}

	public void printMoves() {
		int row = this.Location.row;
		int col = this.Location.col;

		// TODO add && is connected
		// jankey
		if (!((row - 1) < 0)) {
			System.out.println("Enter 8: To Move Up");
		}
		if (!((row + 1) > Constrants.ROW_NUMBER - 1)) {
			System.out.println("Enter 2: To Move Down");
		}

		if (!((col - 1) < 0)) {
			System.out.println("Enter 4: To Move Left");
		}
		if (!((col + 1) > Constrants.COL_NUMBER - 1)) {
			System.out.println("Enter 6: To Move Right");
		}

	}
}
