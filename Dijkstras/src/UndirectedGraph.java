import java.util.LinkedList;

public class UndirectedGraph extends Graph {
    /**
     * Constructs a Graph with the specified number of nodes, initializing the adjacency list.
     * Each node is represented as a separate linked list for storing adjacent edges.
     *
     * @param nodes the number of nodes in the graph
     */
    public UndirectedGraph(int nodes) {
        super(nodes);
    }

    /**
     * Adds an undirected edge between two nodes.
     * Since this is an undirected graph, the edge is stored in both nodes' adjacency lists.
     *
     * @param edge the edge to be added, containing source and destination nodes
     */
    @Override
    void addEdge(Edge edge) {
        if (!edgeExists(edge)) {
            adjacencyList.get(edge.getSource()).add(edge);
                if (!containsEdge(edge.getDestination(), edge.getSource())) {
                    Edge reverseEdge;
                    if (edge instanceof WeightedEdge) {
                        reverseEdge = new WeightedEdge(edge.getDestination(), edge.getSource(), ((WeightedEdge) edge).getWeight());
                    } else {
                        reverseEdge = new UnweightedEdge(edge.getDestination(), edge.getSource());
                    }
                    adjacencyList.get(edge.getDestination()).add(reverseEdge);
                }
        }
    }


    /**
     * Removes an edge between two nodes in an undirected graph. If the graph contains
     * an edge from the source node to the destination node, it is removed. Additionally,
     * if a reverse edge (from destination to source) exists, it is also removed.
     *
     * @param source the source node of the edge to be removed
     * @param destination the destination node of the edge to be removed
     * @return true if at least one of the edges (source to destination or destination to source)
     *         was successfully removed, false otherwise
     */

    @Override
    public boolean removeEdge(int source, int destination) {
        // Initialize variables to track if the edges were removed
        boolean removed = false;
        boolean reverseRemoved = false;

        // Remove the edge from source to destination
        LinkedList<Edge> sourceEdges = adjacencyList.get(source);
        for (int i = 0; i < sourceEdges.size(); i++) { // Iterate through the edges originating from 'source' to find and remove the edge leading to 'destination'
            if (sourceEdges.get(i).getDestination() == destination) {
                sourceEdges.remove(i);
                removed = true;
                i--; // Adjust index after removal
            }
        }

        // Remove the reverse edge from destination to source
        LinkedList<Edge> destinationEdges = adjacencyList.get(destination);
        for (int i = 0; i < destinationEdges.size(); i++) {
            if (destinationEdges.get(i).getDestination() == source) {
                destinationEdges.remove(i);
                reverseRemoved = true;
                i--; // Adjust index after removal
            }
        }

        // Return true if either edge was removed
        return removed || reverseRemoved;
    }
}
