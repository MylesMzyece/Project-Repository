import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.LinkedList;
public class GraphTests {

    protected Graph graph;


    // Helper method for setting up the graph
    @BeforeEach
    void setUp() {
        graph = new UndirectedGraph(5);
    }

    @Test
    void testAddEdge() {
        UnweightedEdge edge = new UnweightedEdge(0, 1);
        graph.addEdge(edge);

        LinkedList<Edge> edgesFrom0 = graph.getIncidentEdges(0);
        LinkedList<Edge> edgesFrom1 = graph.getIncidentEdges(1);

        assertEquals(1, edgesFrom0.size(), "Node 0 should have 1 edge.");
        assertEquals(1, edgesFrom1.size(), "Node 1 should have 1 edge.");
        assertTrue(edgesFrom0.contains(edge), "Edge should be in the list of node 0.");
    }

    @Test
    void testIncidentEdges() {
        UnweightedEdge edge1 = new UnweightedEdge(0, 1);
        UnweightedEdge edge2 = new UnweightedEdge(1, 2);

        graph.addEdge(edge1);
        graph.addEdge(edge2);

        LinkedList<Edge> edgesFrom1 = graph.getIncidentEdges(1);
        assertEquals(2, edgesFrom1.size(), "Node 1 should have 1 incident edge.");
        assertTrue(edgesFrom1.contains(edge2), "Edge from 1 to 2 should exist.");
    }

    @Test
    void testAddMultipleEdges() {
        UnweightedEdge edge1 = new UnweightedEdge(0, 1);
        UnweightedEdge edge2 = new UnweightedEdge(1, 2);
        UnweightedEdge edge3 = new UnweightedEdge(0, 2);


        graph.addEdge(edge1);
        graph.addEdge(edge2);
        graph.addEdge(edge3);

        LinkedList<Edge> edgesFrom0 = graph.getIncidentEdges(0);
        LinkedList<Edge> edgesFrom1 = graph.getIncidentEdges(1);

        assertEquals(2, edgesFrom0.size(), "Node 0 should have 2 edges");
        assertEquals(2, edgesFrom1.size(), "Node 1 should have 1 edge");
    }

    @Test
    void testAddEdgeDuplicate() {
        UnweightedEdge edge1 = new UnweightedEdge(0, 1);
        UnweightedEdge edge2 = new UnweightedEdge(0, 1);

        graph.addEdge(edge1);
        graph.addEdge(edge2);

        LinkedList<Edge> edgesFrom0 = graph.getIncidentEdges(0);
        LinkedList<Edge> edgesFrom1 = graph.getIncidentEdges(1);

        assertEquals(1, edgesFrom0.size(), "Node 0 should have only 1 edge.");
        assertEquals(1, edgesFrom1.size(), "Node 1 should have only 1 edge.");
    }

    @Test
    void testNoEdges() {
        for (int i = 0; i < 5; i++) {
            LinkedList<Edge> edges = graph.getIncidentEdges(i);
            assertEquals(0, edges.size(), "Node " + i + " should have no edges.");
        }
    }

    @Test
    void testCycleEdge() {
        UnweightedEdge edge1 = new UnweightedEdge(0, 1);
        UnweightedEdge edge2 = new UnweightedEdge(1, 2);
        UnweightedEdge edge3 = new UnweightedEdge(2, 0);

        graph.addEdge(edge1);
        graph.addEdge(edge2);
        graph.addEdge(edge3);

        LinkedList<Edge> edgesFrom2 = graph.getIncidentEdges(2);
        assertEquals(2, edgesFrom2.size(), "Node 2 should have 1 incident edge.");
        assertTrue(edgesFrom2.contains(edge3), "Cycle edge 2 to 0 should exist.");
    }

    @Test
    void testStressAddEdges() {
        int nodeCount = 1000;
        // Use an appropriate graph implementation
        Graph largeGraph = new DirectedGraph(nodeCount);

        // Add a large number of edges
        for (int i = 0; i < nodeCount - 1; i++) {
            largeGraph.addEdge(new WeightedEdge(i, i + 1, i * 10));
        }

        for (int i = 0; i < nodeCount - 1; i++) {
            LinkedList<Edge> edgesFromI = largeGraph.getIncidentEdges(i);
            assertEquals(1, edgesFromI.size(), "Node " + i + " should have 1 edge.");
        }

        // Check last node
        LinkedList<Edge> edgesFromLastNode = largeGraph.getIncidentEdges(nodeCount - 1);
        assertEquals(0, edgesFromLastNode.size(), "Last node should have no edges.");
    }

    @Test
    void testFullyConnectedGraph() {
        int nodeCount = 1000;
        // Use an appropriate graph implementation
        Graph largeGraph = new DirectedGraph(nodeCount);

        // Add edges to create a fully connected graph (without self-loops)
        for (int i = 0; i < nodeCount; i++) {
            for (int j = 0; j < nodeCount; j++) {
                if (i != j) {
                    // Add appropriate weighted edge
                    largeGraph.addEdge(new WeightedEdge(i, j, 10));
                }
            }
        }

        // Check if the number of edges from each node is correct
        for (int i = 0; i < nodeCount; i++) {
            LinkedList<Edge> edgesFromI = largeGraph.getIncidentEdges(i);
            assertEquals(nodeCount - 1, edgesFromI.size(), "Node " + i + " should have " + (nodeCount - 1) + " edges.");
        }
    }

    @Test
    void testAddAndRemoveEdge() {
        Graph graph = new UndirectedGraph(5);

        graph.addEdge(new UnweightedEdge(0, 1));
        graph.addEdge(new UnweightedEdge(1, 2));

        assertTrue(graph.containsEdge(0, 1));
        assertTrue(graph.containsEdge(1, 2));

        graph.removeEdge(0, 1);

        assertFalse(graph.containsEdge(0, 1));
    }

    @Test
    void testDijkstraSmallGraph() {
        Graph graph = new DirectedGraph(4);

        graph.addEdge(new WeightedEdge(0, 1, 2));
        graph.addEdge(new WeightedEdge(1, 2, 4));
        graph.addEdge(new WeightedEdge(0, 2, 5));
        graph.addEdge(new WeightedEdge(2, 3, 3));

        LinkedList<Integer> path = graph.dijkstra(0, 3);

        assertNotNull(path);
        assertEquals(3, path.size());
        assertEquals(0, path.get(0));
        assertEquals(2, path.get(1));
        assertEquals(3, path.get(2));
    }

    @Test
    void testDijkstraMultiplePaths() {
        Graph graph = new DirectedGraph(6);

        graph.addEdge(new WeightedEdge(0, 1, 3));
        graph.addEdge(new WeightedEdge(1, 5, 7));
        graph.addEdge(new WeightedEdge(0, 2, 2));
        graph.addEdge(new WeightedEdge(2, 3, 2));
        graph.addEdge(new WeightedEdge(3, 5, 4));
        graph.addEdge(new WeightedEdge(0, 4, 5));
        graph.addEdge(new WeightedEdge(4, 5, 2));

        LinkedList<Integer> path = graph.dijkstra(0, 5);

        assertNotNull(path);
        assertEquals(3, path.size());
        assertEquals(0, path.get(0));
        assertEquals(4, path.get(1));
        assertEquals(5, path.get(2));
    }

    @Test
    void testDijkstraSelfLoop() {
        Graph graph = new DirectedGraph(3);

        graph.addEdge(new WeightedEdge(0, 0, 5));
        graph.addEdge(new WeightedEdge(0, 1, 2));
        graph.addEdge(new WeightedEdge(1, 2, 3));

        LinkedList<Integer> path = graph.dijkstra(0, 2);

        assertNotNull(path);
        assertEquals(3, path.size());
        assertEquals(0, path.get(0));
        assertEquals(1, path.get(1));
        assertEquals(2, path.get(2));
    }

    @Test
    void testDijkstraLargeGraph() {
        Graph graph = new DirectedGraph(50);

        for (int i = 0; i < 49; i++) {
            graph.addEdge(new WeightedEdge(i, i + 1, i % 5 + 1));
        }

        LinkedList<Integer> path = graph.dijkstra(0, 49);

        assertNotNull(path);
        assertEquals(50, path.size());

        for (int i = 0; i < 50; i++) {
            assertEquals(i, path.get(i));
        }
    }




    @Test
    void testDijkstraWithDirectPath() {
        // Setup: add edges to form a direct path
        graph.addEdge(new WeightedEdge(0, 1, 10));
        graph.addEdge(new WeightedEdge(1, 4, 5));

        // Test: Run dijkstra's algorithm from node 0 to node 4
        LinkedList<Integer> path = graph.dijkstra(0, 4);

        // Verify that the shortest path is [0, 1, 4]
        assertNotNull(path);
        assertEquals(3, path.size());
        assertEquals(0, path.get(0));
        assertEquals(1, path.get(1));
        assertEquals(4, path.get(2));
    }

    @Test
    void testDijkstraWithNoPath() {
        // Setup: add edges that don't connect the source to the destination
        graph.addEdge(new WeightedEdge(0, 1, 10));
        graph.addEdge(new WeightedEdge(2, 3, 5));
        // Test: Run dijkstra's algorithm from node 0 to node 3
        LinkedList<Integer> path = graph.dijkstra(0, 3);

        // Verify that no path exists (should return an empty list)
        assertTrue(path.isEmpty());
    }

    @Test
    void testDijkstraWithMultiplePaths() {
        // Setup: add edges to form multiple paths from node 0 to node 4
        // Path 1: 0 -> 1 -> 4
        graph.addEdge(new WeightedEdge(0, 1, 10));
        graph.addEdge(new WeightedEdge(1, 4, 5));
        // Path 2: 0 -> 2 -> 4
        graph.addEdge(new WeightedEdge(0, 2, 15));
        graph.addEdge(new WeightedEdge(2, 4, 5));

        // Test: Run dijkstra's algorithm from node 0 to node 4
        LinkedList<Integer> path = graph.dijkstra(0, 4);

        // Verify that the shortest path is [0, 1, 4]
        assertNotNull(path);
        assertEquals(3, path.size());
        assertEquals(0, path.get(0));
        assertEquals(1, path.get(1));
        assertEquals(4, path.get(2));
    }

    @Test
    void testDijkstraWithEmptyGraph() {
        // Test: Run dijkstra's algorithm on an empty graph
        graph = new DirectedGraph(0);

        // Run dijkstra's algorithm from node 0 to node 0 (empty graph, no nodes)
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            graph.dijkstra(0, 0);
        });

    }

    @Test
    void testDijkstraWithSingleNodeGraph() {
        // Test: Run dijkstra's algorithm on a graph with a single node
        graph = new DirectedGraph(2);
        graph.addEdge(new UnweightedEdge(0, 1));

        // Run dijkstra's algorithm from node 0 to node 0
        LinkedList<Integer> path = graph.dijkstra(0, 1);

        // Verify that the path is [0]
        assertNotNull(1);
        assertEquals(2, path.size());
        assertEquals(0, path.get(0));
    }

    @Test
    void testDijkstraWithDisconnectedNodes() {
        // Setup: Graph with multiple disconnected nodes
        Graph graph = new DirectedGraph(6);
        graph.addEdge(new WeightedEdge(0, 1, 10));
        graph.addEdge(new WeightedEdge(2, 3, 5));
        graph.addEdge(new WeightedEdge(4, 5, 2));

        //Run dijkstra's from 0 to 3 (no connection)
        LinkedList<Integer> path = graph.dijkstra(0, 3);

        // Verify that no path exists
        assertTrue(path.isEmpty());
    }

    @Test
    void testDijkstraWithMultipleEqualPaths() {
        // Setup: Two paths of equal weight
        graph.addEdge(new WeightedEdge(0, 1, 10));
        graph.addEdge(new WeightedEdge(1, 4, 5));
        graph.addEdge(new WeightedEdge(0, 2, 10));
        graph.addEdge(new WeightedEdge(2, 4, 5));

        // Test: Run dijkstra’s algorithm from node 0 to node 4
        LinkedList<Integer> path = graph.dijkstra(0, 4);

        // Verify that one of the valid shortest paths is returned
        assertNotNull(path);
        assertEquals(3, path.size());
        assertTrue((path.get(1) == 1 && path.get(2) == 4) || (path.get(1) == 2 && path.get(2) == 4));
    }

    @Test
    void testEdgeExistsAfterRemoval() {
        Graph graph = new UndirectedGraph(5);

        UnweightedEdge edge = new UnweightedEdge(0, 1);
        graph.addEdge(edge);
        assertTrue(graph.edgeExists(edge));

        graph.removeEdge(0, 1);
        assertFalse(graph.edgeExists(edge));
    }

    @Test
    void testEdgeExistsForNonexistentEdge() {
        Graph graph = new UndirectedGraph(5);

        UnweightedEdge edge = new UnweightedEdge(0, 1);
        assertFalse(graph.edgeExists(edge));
    }

    @Test
    void testContainsEdgeDirected() {
        Graph graph = new DirectedGraph(5);

        graph.addEdge(new WeightedEdge(0, 1, 5));
        graph.addEdge(new WeightedEdge(1, 2, 10));

        assertTrue(graph.containsEdge(0, 1));
        assertTrue(graph.containsEdge(1, 2));
    }

    @Test
    void testContainsEdgeUndirected() {
        Graph graph = new UndirectedGraph(5);

        graph.addEdge(new UnweightedEdge(0, 1));
        graph.addEdge(new UnweightedEdge(2, 3));

        assertTrue(graph.containsEdge(0, 1));
        assertTrue(graph.containsEdge(1, 0));
        assertTrue(graph.containsEdge(2, 3));
        assertFalse(graph.containsEdge(0, 2));
    }

    @Test
    void testContainsEdgeNonExistent() {
        Graph graph = new DirectedGraph(5);

        assertFalse(graph.containsEdge(0, 1));
        assertFalse(graph.containsEdge(3, 4));
    }

    @Test
    void testDijkstra1() {
        Graph graph = new UndirectedGraph(10);
        graph.addEdge(new WeightedEdge(0, 1, 3));
        graph.addEdge(new WeightedEdge(0, 2, 2));
        graph.addEdge(new WeightedEdge(1, 2, 5));
        graph.addEdge(new WeightedEdge(1, 3, 4));
        graph.addEdge(new WeightedEdge(1, 5, 7));
        graph.addEdge(new WeightedEdge(2, 3, 8));
        graph.addEdge(new WeightedEdge(2, 4, 2));
        graph.addEdge(new WeightedEdge(3, 5, 2));
        graph.addEdge(new WeightedEdge(3, 6, 7));
        graph.addEdge(new WeightedEdge(4, 6, 1));
        graph.addEdge(new WeightedEdge(6, 7, 3));
        graph.addEdge(new WeightedEdge(7, 8, 1));
        graph.addEdge(new WeightedEdge(5, 8, 1));
        graph.addEdge(new WeightedEdge(8, 9, 2));

        LinkedList<Integer> path = graph.dijkstra(0,9);
        assertNotNull(path);
        assertEquals(7, path.size());
        assertEquals(0, path.get(0));
        assertEquals(2, path.get(1));
        assertEquals(4, path.get(2));
        assertEquals(6, path.get(3));
        assertEquals(7, path.get(4));
        assertEquals(8, path.get(5));
        assertEquals(9, path.get(6));

        path = graph.dijkstra(0, 8);
        assertEquals(6, path.size());
        assertEquals(0, path.get(0));
        assertEquals(2, path.get(1));
        assertEquals(4, path.get(2));
        assertEquals(6, path.get(3));
        assertEquals(7, path.get(4));
        assertEquals(8, path.get(5));
    }

    @Test
    void testDijkstra2() {
        Graph graph = new UndirectedGraph(15);
        graph.addEdge(new WeightedEdge(0, 1, 1));
        graph.addEdge(new WeightedEdge(1, 2, 4));
        graph.addEdge(new WeightedEdge(2, 3, 2));
        graph.addEdge(new WeightedEdge(3, 4, 5));
        graph.addEdge(new WeightedEdge(4, 5, 1));
        graph.addEdge(new WeightedEdge(5, 6, 7));
        graph.addEdge(new WeightedEdge(6, 7, 3));
        graph.addEdge(new WeightedEdge(7, 8, 4));
        graph.addEdge(new WeightedEdge(8, 9, 2));
        graph.addEdge(new WeightedEdge(9, 10, 6));
        graph.addEdge(new WeightedEdge(10, 11, 5));
        graph.addEdge(new WeightedEdge(11, 12, 3));
        graph.addEdge(new WeightedEdge(12, 13, 2));
        graph.addEdge(new WeightedEdge(13, 14, 1));

        LinkedList<Integer> path = graph.dijkstra(0,14);
        assertNotNull(path);
        assertEquals(15, path.size());
        for (int i = 0; i < 15; i++) {
            assertEquals(i, path.get(i));
        }
    }

    @Test
    void testDijkstra3() {
        Graph graph = new UndirectedGraph(12);
        graph.addEdge(new WeightedEdge(0, 1, 10));
        graph.addEdge(new WeightedEdge(0, 2, 5));
        graph.addEdge(new WeightedEdge(1, 3, 2));
        graph.addEdge(new WeightedEdge(2, 3, 3));
        graph.addEdge(new WeightedEdge(2, 4, 8));
        graph.addEdge(new WeightedEdge(3, 5, 1));
        graph.addEdge(new WeightedEdge(4, 6, 7));
        graph.addEdge(new WeightedEdge(5, 7, 9));
        graph.addEdge(new WeightedEdge(6, 8, 4));
        graph.addEdge(new WeightedEdge(7, 9, 2));
        graph.addEdge(new WeightedEdge(8, 10, 6));
        graph.addEdge(new WeightedEdge(9, 11, 3));
        graph.addEdge(new WeightedEdge(10, 11, 1));

        LinkedList<Integer> path = graph.dijkstra(0,11);
        assertNotNull(path);
        assertEquals(7, path.size());
        assertEquals(0, path.get(0));
        assertEquals(2, path.get(1));
        assertEquals(3, path.get(2));
        assertEquals(5, path.get(3));
        assertEquals(7, path.get(4));
        assertEquals(9, path.get(5));
        assertEquals(11, path.get(6));
    }

    @Test
    void testAddEdgeSelfLoop() {
        UnweightedEdge edge = new UnweightedEdge(0, 0);
        graph.addEdge(edge);

        LinkedList<Edge> edgesFrom0 = graph.getIncidentEdges(0);
        assertEquals(1, edgesFrom0.size(), "Node 0 should have 1 self-loop edge.");
        assertTrue(edgesFrom0.contains(edge), "Self-loop edge should exist.");
    }

    @Test
    void testAddEdgeOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            graph.addEdge(new UnweightedEdge(0, 6));
        });
    }

    @Test
    void testRemoveEdgeNonExistent() {
        graph.removeEdge(0, 1);
        assertFalse(graph.containsEdge(0, 1));
    }

    @Test
    void testRemoveSelfLoop() {
        graph.addEdge(new UnweightedEdge(0, 0));
        assertTrue(graph.containsEdge(0, 0));

        graph.removeEdge(0, 0);
        assertFalse(graph.containsEdge(0, 0));
    }

    @Test
    void testGraphDisconnection() {
        graph.addEdge(new UnweightedEdge(0, 1));
        graph.addEdge(new UnweightedEdge(1, 2));
        graph.removeEdge(1, 2);

        assertFalse(graph.containsEdge(1, 2));
        assertTrue(graph.containsEdge(0, 1));
    }

    @Test
    void testAddDuplicateWeightedEdge() {
        graph.addEdge(new WeightedEdge(0, 1, 10));
        graph.addEdge(new WeightedEdge(0, 1, 10));

        LinkedList<Edge> edges = graph.getIncidentEdges(0);
        assertEquals(1, edges.size(), "Duplicate edges should not be allowed.");
    }

    @Test
    void testDirectedEdgeBehavior() {
        Graph directedGraph = new DirectedGraph(5);
        directedGraph.addEdge(new WeightedEdge(0, 1, 5));

        assertTrue(directedGraph.containsEdge(0, 1));
        assertFalse(directedGraph.containsEdge(1, 0));
    }

    @Test
    void testUndirectedEdgeBehavior() {
        Graph undirectedGraph = new UndirectedGraph(5);
        undirectedGraph.addEdge(new UnweightedEdge(0, 1));

        assertTrue(undirectedGraph.containsEdge(0, 1));
        assertTrue(undirectedGraph.containsEdge(1, 0));
    }

    @Test
    void testDijkstraDisjointGraph() {
        Graph disjointGraph = new DirectedGraph(5);
        disjointGraph.addEdge(new WeightedEdge(0, 1, 3));
        disjointGraph.addEdge(new WeightedEdge(3, 4, 5));

        LinkedList<Integer> path = disjointGraph.dijkstra(0, 4);
        assertTrue(path.isEmpty());
    }

    @Test
    void testDijkstraNegativeWeights() {
        Graph graph = new DirectedGraph(4);
        graph.addEdge(new WeightedEdge(0, 1, 2));
        graph.addEdge(new WeightedEdge(1, 2, -3)); // Negative weight
        graph.addEdge(new WeightedEdge(2, 3, 4));

        LinkedList<Integer> path = graph.dijkstra(0, 3);
        assertNotNull(path);
    }

    @Test
    void testDijkstraWithZeroWeightEdges() {
        graph.addEdge(new WeightedEdge(0, 1, 0));
        graph.addEdge(new WeightedEdge(1, 2, 0));
        graph.addEdge(new WeightedEdge(2, 3, 1));

        LinkedList<Integer> path = graph.dijkstra(0, 3);
        assertNotNull(path);
        assertEquals(4, path.size());
    }

    @Test
    void testGraphMaxCapacity() {
        Graph largeGraph = new UndirectedGraph(100000);
        for (int i = 0; i < 99999; i++) {
            largeGraph.addEdge(new UnweightedEdge(i, i + 1));
        }

        assertEquals(1, largeGraph.getIncidentEdges(0).size());
    }

    @Test
    void testGraphFullyConnectedLarge() {
        int size = 500;
        Graph graph = new UndirectedGraph(size);

        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                graph.addEdge(new UnweightedEdge(i, j));
            }
        }

        for (int i = 0; i < size; i++) {
            assertEquals(size - 1, graph.getIncidentEdges(i).size());
        }
    }

    @Test
    void testGraphWithExtremeWeights() {
        Graph graph = new DirectedGraph(3);
        graph.addEdge(new WeightedEdge(0, 1, Integer.MAX_VALUE));
        graph.addEdge(new WeightedEdge(1, 2, Integer.MIN_VALUE));

        LinkedList<Integer> path = graph.dijkstra(0, 2);
        assertNotNull(path);
    }

    @Test
    void testGraphWithManySelfLoops() {
        Graph graph = new UndirectedGraph(100);
        for (int i = 0; i < 100; i++) {
            graph.addEdge(new UnweightedEdge(i, i));
        }

        for (int i = 0; i < 100; i++) {
            assertEquals(1, graph.getIncidentEdges(i).size());
        }
    }

    @Test
    void testGraphPerformance() {
        Graph graph = new DirectedGraph(1000000);
        for (int i = 0; i < 999999; i++) {
            graph.addEdge(new WeightedEdge(i, i + 1, 1));
        }

        LinkedList<Integer> path = graph.dijkstra(0, 999999);
        assertNotNull(path);
        assertEquals(1000000, path.size());
    }

}




