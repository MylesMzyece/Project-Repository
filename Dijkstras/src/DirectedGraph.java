import java.util.LinkedList;

public class DirectedGraph extends Graph{
    /**
     * Constructs a Graph with the specified number of nodes, initializing the adjacency list.
     * Each node is represented as a separate linked list for storing adjacent edges.
     *
     * @param nodes the number of nodes in the graph
     */
    public DirectedGraph(int nodes) {
        super(nodes);
    }

    /**
     * Adds a directed edge from the source to the destination.
     * Since this is a directed graph, the edge is only stored in the source node's adjacency list.
     *
     * @param edge the edge to be added, containing source and destination nodes
     */
    @Override
    void addEdge(Edge edge) {
        adjacencyList.get(edge.getSource()).add(edge); // Only add edge in one direction
    }

    //returns true if the edge was removed, false if the edge was not found.
    public boolean removeEdge(int source, int destination){
        LinkedList<Edge> edges = adjacencyList.get(source);

        for(Edge edge : edges){
            if(edge.getDestination() == destination){
                edges.remove(edge);
                return true;
            }
        }
        return false;
    }
}
