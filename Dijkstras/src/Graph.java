import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

abstract class Graph {
    protected ArrayList<LinkedList<Edge>> adjacencyList;

    /**
     * Constructs a Graph with the specified number of nodes, initializing the adjacency list.
     * Each node is represented as a separate linked list for storing adjacent edges.
     *
     * @param nodes the number of nodes in the graph
     */

    public Graph (int nodes){
        adjacencyList = new ArrayList<>();
        for(int i = 0; i < nodes; i++){
            adjacencyList.add(new LinkedList<>());
        }
    }

    //Universally applicable methods

    //Returns a linkedList of edges surrounding the source node

    public LinkedList<Edge> getIncidentEdges(int source){
        return adjacencyList.get(source);
    }

    //Takes in an int for source and destination, returns true if there is an edge connecting
    //the source and destination, false if there isn't
    public boolean containsEdge(int source, int destination){
        for(Edge edge : adjacencyList.get(source)){
            if(edge.getDestination() == destination){
                return true;
            }
        }
        return false;
    }

    //returns amount of vertexes.
    public int getSize(){
        return adjacencyList.size();
    }

    LinkedList<Integer> dijkstra(int source, int destination) {
        int[] distances = new int[adjacencyList.size()];
        boolean[] visited = new boolean[adjacencyList.size()];
        int[] prev = new int[adjacencyList.size()];
        Heap<Edge> priorityQueue = new Heap<>(adjacencyList.size());

        // Initialize distances and predecessors
        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);
        distances[source] = 0;
        // Insert source into the priority queue with distance 0
        priorityQueue.insert(new UnweightedEdge(source, source), 0);

        while (priorityQueue.getMinData() != null ) { // While there is something in the heap
            int currentNode = ((Edge) priorityQueue.getMinData()).getDestination(); // Get the node with the smallest distance
            int currentDistance = priorityQueue.getMinWeight();
            priorityQueue.extractMin();

            if (!visited[currentNode]) {
                visited[currentNode] = true;

                for (Edge edge : getIncidentEdges(currentNode)) {
                    int neighbor = edge.getDestination();
                    int newDist = currentDistance + edge.getWeight();

                    if (!visited[neighbor] && newDist < distances[neighbor]) {
                        distances[neighbor] = newDist; // Update the distance to this neighbor
                        prev[neighbor] = currentNode; // Insert or update the neighbor in the heap
                        priorityQueue.insert(edge, newDist); // Insert into priority queue
                    }
                }
            }
        }
        return reconstructPath(source, destination, prev);
    }

    private LinkedList<Integer> reconstructPath(int source, int destination, int[] prev) {
        LinkedList<Integer> path = new LinkedList<>();
        if (prev[destination] == -1) {
            // No path exists to the destination
            return path;
        }

        for (int at = destination; at != -1; at = prev[at]) {
            path.addFirst(at); // Add each node to the path in reverse
        }

        return path;
    }

    //Abstract methods
    abstract boolean removeEdge(int source, int destination);

    abstract void addEdge(Edge edge);

    public boolean edgeExists(Edge edge) {
        for (Edge x : adjacencyList.get(edge.getSource())) {
            if (x.equals(edge)) {
                return true;
            }
        }
        return false;

    }

    public int returnWeight(int source, int destination){
        for(Edge edge : adjacencyList.get(source)){
            if(edge.getDestination() == destination){
                return edge.getWeight();
            }
        }
        return -1;
    }
}
