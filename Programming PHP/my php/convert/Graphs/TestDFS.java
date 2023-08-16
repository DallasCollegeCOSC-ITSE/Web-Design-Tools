package GraphsAndApplications.Graph;

public class TestDFS {
    public static void main(String[] args) throws Exception {
        String[] vertices = {"Seattle", "San Francisco", "Los Angeles", "Denver", "Kansas City", "Chicago", "Boston",
                "New York", "Atlanta", "Miami", "Dallas", "Houston"};

        int[][] edges = {
                {0, 1}, {0, 3}, {0, 5},
                {1, 0}, {1, 2}, {1, 3},
                {2, 1}, {2, 3}, {2, 4}, {2, 10},
                {3, 0}, {3, 1}, {3, 2}, {3, 4}, {3, 5},
                {4, 2}, {4, 3}, {4, 5}, {4, 7}, {4, 8}, {4, 10},
                {5, 0}, {5, 3}, {5, 4}, {5, 6}, {5, 7},
                {6, 5}, {6, 7},
                {7, 4}, {7, 5}, {7, 6}, {7, 8},
                {8, 4}, {8, 7}, {8, 9}, {8, 10}, {8, 11},
                {9, 8}, {9, 11},
                {10, 2}, {10, 4}, {10, 8}, {10, 11},
                {11, 8}, {11, 9}, {11, 10}
        };

        Graph<String> graph = new UnweightedGraph<>(vertices, edges);

        UnweightedGraph<String>.SearchTree dfs = ((UnweightedGraph<String>) graph).dfs(graph.getIndex("Chicago"));

        System.out.println("The number of Vertices in graph: " + graph.getSize());
        System.out.println("The vertex with index 1 is " + graph.getVertex(1));
        System.out.println("The index of Miami is " + graph.getIndex("Miami"));
        System.out.println("The edges for graph:");
        graph.printEdges();

        String[] names = {"Peter", "Jane", "Mark", "Cindy", "Wendy"};

        java.util.ArrayList<Edge> edgeList = new java.util.ArrayList<>();

        edgeList.add(new Edge(0, 2));
        edgeList.add(new Edge(1, 2));
        edgeList.add(new Edge(2, 4));
        edgeList.add(new Edge(3, 4));

        Graph<String> graph2 = new UnweightedGraph<>(java.util.Arrays.asList(names), edgeList);
    }
}
