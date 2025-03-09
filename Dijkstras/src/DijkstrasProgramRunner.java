import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class DijkstrasProgramRunner {
    public static int[][] coordinates;
    public static int[][] edges;
    protected static UndirectedGraph graph;

    public static void main(String[] args) throws IOException {
        File file = new File("Dijkstras/usa.txt"); // Change this to the correct file path
        readFile(file);

        LinkedList<Integer> emptyPath = new LinkedList<>();
        GUI window = new GUI (coordinates, edges,emptyPath, shortestDistance(emptyPath),graph);


        window.findNewPath(window);
    }

    public static void readFile(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        // Read the first line (number of nodes and edges)
        int numNodes = scanner.nextInt();
        int numEdges = scanner.nextInt(); // This is stored but not used here

        // Initialize 2D array for coordinates
        coordinates = new int[numNodes][3];
        edges = new int[numEdges][2];
        graph = new UndirectedGraph(numNodes);

        // Read the next 'numNodes' lines and populate the 2D array
        for (int i = 0; i < numNodes; i++) {
            int nodeID = scanner.nextInt();
            int x = scanner.nextInt();
            int y = scanner.nextInt();

            coordinates[i][0] = nodeID;
            coordinates[i][1] = x;
            coordinates[i][2] = y;
        }
        for(int i = 0; i < numEdges; i++) {
            int sourceID1 = scanner.nextInt();
            int sourceID2 = scanner.nextInt();
            edges[i][0] = sourceID1;
            edges[i][1] = sourceID2;
            int x1 = coordinates[sourceID1][1];
            int y1 = coordinates[sourceID1][2];
            int x2 = coordinates[sourceID2][1];
            int y2 = coordinates[sourceID2][2];

            // Compute distance (weight)
            int weight = (int) Math.round(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)));

            // Add edge to graph
            graph.addEdge(new WeightedEdge(sourceID1, sourceID2, weight));
        }
        scanner.close();
    }

    public static int shortestDistance(LinkedList<Integer> path){
        int shortestDistance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            shortestDistance += graph.returnWeight(path.get(i), path.get(i + 1));
        }
        shortestDistance*=0.34;
        return shortestDistance;
    }
}