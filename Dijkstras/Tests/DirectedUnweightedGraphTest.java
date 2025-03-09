import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
public class DirectedUnweightedGraphTest {
    private DirectedGraph graph;
//make a graph for each test

    @BeforeEach
    void setUp() {
        graph = new DirectedGraph(6); // Initialize a graph with 6 nodes
    }

    @Test
    void testDijkstraNoPath() {
        graph.addEdge(new UnweightedEdge(0, 1));
        graph.addEdge(new UnweightedEdge(1, 2));
        // No edge connecting from node 2 to node 5, meaning no valid path
        LinkedList<Integer> path = graph.dijkstra(0, 5);

        assertTrue(path.isEmpty()); // Path should be empty as no path exists
    }

    @Test
    void testEdgeRemoval() {
        UnweightedEdge edge = new UnweightedEdge(0, 1);
        graph.addEdge(edge);

        assertTrue(graph.edgeExists(edge), "Edge should exist before removal");
        assertTrue(graph.removeEdge(0, 1), "Edge should be removed successfully");
        assertFalse(graph.edgeExists(edge), "Edge should no longer exist after removal");
    }

    @Test
    void testDijkstraWithCycles() {
        // Create a cyclic graph: 0 -> 1 -> 2 -> 0, and an extra path 0 -> 3 -> 5
        graph.addEdge(new UnweightedEdge(0, 1));
        graph.addEdge(new UnweightedEdge(1, 2));
        graph.addEdge(new UnweightedEdge(2, 0)); // Cycle here
        graph.addEdge(new UnweightedEdge(0, 3));
        graph.addEdge(new UnweightedEdge(3, 5));

        LinkedList<Integer> path = graph.dijkstra(0, 5);

        assertNotNull(path);
        assertEquals(3, path.size());
        assertEquals(0, path.get(0));
        assertEquals(3, path.get(1));
        assertEquals(5, path.get(2));
    }

    @Test
    void testEdgeExistsAfterAddingMultipleEdges() {
        UnweightedEdge edge1 = new UnweightedEdge(0, 1);
        UnweightedEdge edge2 = new UnweightedEdge(1, 2);
        UnweightedEdge edge3 = new UnweightedEdge(2, 3);

        graph.addEdge(edge1);
        graph.addEdge(edge2);
        graph.addEdge(edge3);

        assertTrue(graph.edgeExists(edge1));
        assertTrue(graph.edgeExists(edge2));
        assertTrue(graph.edgeExists(edge3));
    }

    @Test
    void testDijkstraAlternativePath() {
        // Graph structure:
        // 0 -> 1 (1 step), 0 -> 2 -> 1 (2 steps)
        graph.addEdge(new UnweightedEdge(0, 1)); // Direct path (short path)
        graph.addEdge(new UnweightedEdge(0, 2));
        graph.addEdge(new UnweightedEdge(2, 1)); // Longer path

        LinkedList<Integer> path = graph.dijkstra(0, 1);

        // Path should choose the shortest route (direct path 0 -> 1)
        assertNotNull(path);
        assertEquals(2, path.size());
        assertEquals(0, path.get(0));
        assertEquals(1, path.get(1));
    }

    @Test
    void testRemoveNonExistentEdge() {
        boolean result = graph.removeEdge(0, 2);

        assertFalse(result, "Removing non-existent edge should return false");
    }

    @Test
    void testDijkstraDisconnectedGraph() {
        // Create a graph with two disconnected components: 0->1->2 and 3->4
        graph.addEdge(new UnweightedEdge(0, 1));
        graph.addEdge(new UnweightedEdge(1, 2));
        graph.addEdge(new UnweightedEdge(3, 4));

        LinkedList<Integer> path = graph.dijkstra(0, 4);

        assertTrue(path.isEmpty(), "Path should be empty because the nodes are disconnected");
    }

    @Test
    void testEdgeExistsAfterRemoval() {
        UnweightedEdge edge = new UnweightedEdge(0, 1);
        graph.addEdge(edge);

        assertTrue(graph.edgeExists(edge), "Edge should exist in the graph before removal");
        graph.removeEdge(0, 1);
        assertFalse(graph.edgeExists(edge), "Edge should not exist in the graph after being removed");
    }

    @Test
    void testGraphSize() {
        assertEquals(6, graph.getSize(), "Graph size should be equal to the number of nodes initialized");
    }




@Test
    void testDijkstraDirectedUnweighted() {
        Graph directedGraph = new DirectedGraph(7);

        directedGraph.addEdge(new UnweightedEdge(0, 1));
        directedGraph.addEdge(new UnweightedEdge(1, 5));
        directedGraph.addEdge(new UnweightedEdge(0, 2));
        directedGraph.addEdge(new UnweightedEdge(2, 3));
        directedGraph.addEdge(new UnweightedEdge(3, 5));
        directedGraph.addEdge(new UnweightedEdge(0, 4));
        directedGraph.addEdge(new UnweightedEdge(4, 5));

        LinkedList<Integer> path = directedGraph.dijkstra(0, 5);

        assertNotNull(path);
        assertEquals(3, path.size());
    }

    @Test
    void testEdgeExistsDirectedUnweightedEdge() {
        Graph graph = new DirectedGraph(5);

        UnweightedEdge edge1 = new UnweightedEdge(0, 1);
        UnweightedEdge edge2 = new UnweightedEdge(2, 3);

        graph.addEdge(edge1);
        graph.addEdge(edge2);

        assertTrue(graph.edgeExists(edge1));
        assertTrue(graph.edgeExists(edge2));
    }
}

