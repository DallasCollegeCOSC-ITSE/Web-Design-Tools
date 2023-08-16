package GraphsAndApplications.Graph;

public interface Graph<V> {

    public int getSize()/*Return the number of vertices in the graph*/;

    public java.util.List<V> getVertices()/*Return the vertices in the graph*/;

    public V getVertex(int index)/*Return the object for the specified vertex index*/;
    public int getIndex(V v)/*Return the index for the specified vertex object*/;

    public java.util.List<Integer> getNeighbors(int index)/* Return the neighbors of vertex with the specified index*/;

    public int getDegree( V v)/*Return the index for the specified vertex*/;

    public void printEdges()/*Print the edges*/;

    public void clear()/* Clear the graph*/;

    public boolean addVertex(V vertex) /*Add a vertex to the graph*/;
    public boolean addEdge(int u, int v)/*Add an edge to the graph*/;
    public boolean addEdge(Edge e);

    public boolean remove(V v)/*Remove a vertex v from the graph, return true if successful*/;

    public boolean remove(int u, int v)/*Remove a vertex v from the graph, return true if successful*/;

    public UnweightedGraph<V>.SearchTree def(int v)/*Obtain a depth-first search tree*/;

    public UnweightedGraph<V>.SearchTree bfs(int v)/*Obtain a breadth-first search tree*/;

}
