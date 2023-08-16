package GraphsAndApplications.Graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class UnweightedGraph<V> implements Graph<V> {

    protected List<V> vertices = new ArrayList<>(/*Store vertices*/);

    protected List<List<Edge>> neighbors = new ArrayList<>(/*Adjacency Edge lists*/);

    protected UnweightedGraph(V[] vertices, int[][]edges/*Construct a graph from vertices and edges stored in arrays*/)
    {
        this.vertices = new ArrayList<>();
        this.neighbors = new ArrayList<>();

        for (int i = 0; i < vertices.length; i++)
            addVertex(vertices[i]);

        createAdjacencyList(edges, vertices.length);
    }

    protected UnweightedGraph(List<V> vertices, List<Edge> edges/*Construct a graph from vertices and edges stored in List*/)
    {
        this.vertices = new ArrayList<>();
        this.neighbors = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++)
            addVertex(vertices.get(i));

        createAdjacencyList(edges, vertices.size());
    }

    protected UnweightedGraph(List<Edge> edges, int numberOfVertices/*Construct a graph for integer vertices 0,1,2 and edge list*/)
    {
        this.vertices = new ArrayList<>();
        this.neighbors = new ArrayList<>();

        for (int i = 0; i < numberOfVertices; i++)
            addVertex((V) (Integer.valueOf(i)))/*vertices is (0,1,...)*/;

        createAdjacencyList(edges, numberOfVertices);
    }

    protected UnweightedGraph(int[][] edges, int numberOfVertices)
    {
        this.vertices = new ArrayList<>();
        this.neighbors = new ArrayList<>();

        for (int i = 0; i < numberOfVertices; i++)
            addVertex((V)(Integer.valueOf(i)))/*vertices is {0,1...}*/;

            createAdjacencyList(edges,numberOfVertices);
    }

    private void createAdjacencyList(int[][]edges, int numberOFVertices /*Create adjacency list for each vertex*/)
    {
        for (int i = 0; i < edges.length; i++)
            addEdge(edges[i][0],edges[i][1]);
    }

    private void createAdjacencyList(List<Edge> edges, int numberOfVertices /*Create adjacency list for each vertex*/)
    {
        for (Edge edge :edges) {
            addEdge(edge.u,edge.v);
        }
    }
    @Override
    public int getSize() {
        return vertices.size()/*Return the number of vertices in the graph*/;
    }

    @Override
    public List<V> getVertices() {
        return vertices;
    }

    @Override
    public V getVertex(int index) {
        return vertices.get(index);
    }

    @Override
    public int getIndex(V v/*Return the index for the specified vertex object*/) {
        return vertices.indexOf(v);
    }

    @Override
    public List<Integer> getNeighbors(int index /*Return the neighbors of the specified vertex*/) {
        List<Integer> result = new ArrayList<>();
        for(Edge e : neighbors.get(index))
            result.add(e.v);

        return result;
    }


    public int getDegree(V v /*Return the degree for a specified vertex*/) {
        int index = getIndex(v);

        return neighbors.get(index).size();
    }

    @Override
    public void printEdges() {
        for (int i = 0; i < neighbors.size(); i++) {
            System.out.print(getVertex(i)+" (" + i + "): ");
            for(Edge e : neighbors.get(i))
                System.out.println("("+getVertex(e.u)+", " + getVertex(e.v) + ") ");

            System.out.println();
        }
    }

    @Override
    public void clear(/*Clear the graph*/) {
        vertices.clear();
        neighbors.clear();
    }

    @Override
    public boolean addVertex(V vertex) {
        if(!vertices.contains(vertex))
        {
            vertices.add(vertex);
            neighbors.add(new ArrayList<>());
            return true;
        }
        else {return false;}
    }

    @Override
    public boolean addEdge(int u, int v/*Add an edge to the graph*/) {

        return addEdge(new Edge(u,v));
    }

    @Override
    public boolean addEdge(Edge e) {
        if(e.u < 0 || e.u >= getSize()) throw new IllegalArgumentException("No such index: " + e.u);

        if(e.v < 0 || e.v >= getSize()) throw new IllegalArgumentException("No such index: " + e.v);

        List<Edge> uNeighbors = neighbors.get(e.u);
        if(!neighbors.get(e.u).contains(e))
        {
            uNeighbors.add(e);
            return true;
        }else {return false;}

    }

    @Override
    public boolean remove(V v/*Remove vertex v and return true if successful */) {
         int index = getIndex(v);

         if(index != -1)
         {
             vertices.remove(index);
             neighbors.remove(index);

             for(List<Edge> neighborList : neighbors)
             {
                 for (Edge e: neighborList)
                 {
                     if(e.u == index || e.v == index)
                     {
                         neighborList.remove(e);
                         break;
                     }
                 }
             }
             return true;
         }
        return false;
    }

    @Override
    public boolean remove(int u, int v)/*Remove edge (u, v) and return true if successful */ {

        List<Edge> uNeighbors = neighbors.get(u);

        for(Edge e : uNeighbors){
            if(e.v == v){
                uNeighbors.remove(e);
                return true;
            }
        }
        return false;
    }

    public SearchTree dfs(int v)
    {
        List<Integer> searchOrder = new ArrayList<>();

        int[] parent = new int[getSize()];

        boolean[] isVisited = new boolean[getSize()];

        dfs(v,parent,searchOrder,isVisited);

        return new SearchTree(v,parent,searchOrder);
    }
    private void dfs(int v, int[] parent, List<Integer> searchOrder,boolean[] isVisited)
    {
        isVisited[v] = true;
        searchOrder.add(v);

        for (int i = 0; i < neighbors.size(); i++) {
            if (!isVisited[i] && neighbors.get(v).contains(i)) {
                parent[i] = v;
                dfs(i, parent, searchOrder, isVisited);
            }
        }
    }



    @Override
    public UnweightedGraph<V>.SearchTree def(int v) {
        return dfs(v);
    }

    @Override
    public UnweightedGraph<V>.SearchTree bfs(int v) {
        List<Integer> searchOrder = new ArrayList<>();
        int[] parent = new int[getSize()];

        boolean[] isVisited = new boolean[vertices.size()];

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(v);
        isVisited[v] = true;

        while(!queue.isEmpty())
        {
            int u = queue.poll();
            searchOrder.add(u);

            for (Edge edge : neighbors.get(u)) {
                int neighbor = edge.v;
                if (!isVisited[neighbor]) {
                    queue.offer(neighbor);
                    parent[neighbor] = u;
                    isVisited[neighbor] = true;
                }
            }
        }

        return new SearchTree(v,parent,searchOrder);
    }

    public class SearchTree /*Tree inner class inside the UnweightedGraph class*/{

        private int root /*The root of the tree*/;
        private int[] parent /*Store the parent of each vertex*/;
        private List<Integer> searchOrder /*Store the search order*/;

        public SearchTree(int root, int[] parent, List<Integer> searchOrder)
        {
            this.root = root;
            this.parent = parent;
            this.searchOrder = searchOrder;
        }

        public int getRoot(){return root;/*Return the root of the tree*/}

        public int getparent(int v){/*Return the parent of the vertex*/ return parent[v];}

        public List<Integer>getSearchOrder() {/*Return an array representing search order*/ return searchOrder;}

        public int getNumberOfVerticesFound(){return searchOrder.size();/*Return number of vertices found*/}

        public List<V> getPath(int index /*Return the path of vertices from a vertex to the root*/)
        {
            ArrayList<V> path = new ArrayList<>();

            do{
                path.add(vertices.get(index));
                index = parent[index];
            }
            while(index != -1);
            return path;
        }

        public void printPath(int index /*A path from the root to vertex v*/)
        {
            List<V> path = getPath(index);
            System.out.println("A path from "+ vertices.get(root) + " to " + vertices.get(index) + ": ");

            for(int i = path.size()-1; i>=0; i--)
                System.out.println(path.get(i) + " ");
        }

        public void printTree(/*Print the while tree*/)
        {
            System.out.println("Root is: " + vertices.get(root));
            System.out.print("Edges: ");

            for (int i = 0; i < parent.length; i++) {
                if(parent[i] != -1)
                System.out.println("(" + vertices.get(parent[i]) + ", " + vertices.get(i) + ") ");
            }
            System.out.println();
        }
    }//End of SearchTree
}
