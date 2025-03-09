import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;



public class GUI extends JPanel {
    //arrays to hold crutial data
    private int[][] coordinates;
    private int[][] edges;

    private JFrame frame;

    //for any nodes that change color
    private Set<Integer> highlightedNodes;

    //edges of final path (produces the line)
    private LinkedList<Integer> pathEdges;

    private JLabel mapsImage;

    //labels
    private JLabel instructionLabel;
    private JTextField textFieldA;
    private JLabel textFieldALabel;
    private JTextField textFieldB;
    private JLabel textFieldLabelB;
    private JLabel pathDistanceLabel;

    //used for text fields
    private static int pointA=0;
    private static int pointB=0;

    //used for cursor click
    private Integer point1 = null;
    private Integer point2 = null;

    //graph that holds map
    protected static UndirectedGraph graph;

    //used for cursor
    private Integer hoveredNode = null;

//gui constructor with graph
    public GUI(int[][] coordinates, int[][] edges, LinkedList<Integer> path, int distance, UndirectedGraph graph) {
        //initialize variables
        this.coordinates = coordinates;
        this.edges = edges;
        this.highlightedNodes = new HashSet<>();
        this.pathEdges = new LinkedList<>();
        this.graph = graph;


        //set up frame, image, and instructions/label
        setUpFrame();
        setMapImage();
        setInstructions();
        setShortestDistance(distance);
        setUpTextField();


        // show the users path
        highlightPath(path);
        makePathEdges(path);

        //mouse listeners for cursor clicking
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectNode(e.getX(), e.getY());
            }
        });

      // Add a mouse motion listener for hover functionality
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                highlightOnHover(e.getX(), e.getY());
            }
        });

        frame.setVisible(true);
    }


    // set up the frame size, name, etc
    public void setUpFrame(){
        // Get screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        // Scale nodes to fit window
        scaleCoordinates(width, height);

        //initialize jframe
        frame = new JFrame("Map Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);


        frame.setLocationRelativeTo(null);

        // Set a layout manager
        this.setLayout(null);

        // Set the background color for the panel
        this.setBackground(Color.WHITE);

        frame.add(this);

        // Make the frame resizable
        frame.setResizable(true);

        // Create Exit Button
        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(200, height - 300, 100, 40);
        exitButton.addActionListener(e -> System.exit(0));
        this.add(exitButton);

        // Create Start Button
        JButton startButton = new JButton("Start");
        startButton.setBounds(50, height - 300, 100, 40);

        //what happens when start is clicked
        startButton.addActionListener(e -> {
            // Display a message when starting
            JOptionPane.showMessageDialog(frame, "Starting the application...");
            // Hide the map image
            mapsImage.setVisible(false);
            // Remove the Start and Exit buttons
            this.remove(startButton);
            this.remove(exitButton);
            // Repaint the panel to update the UI
            this.revalidate();
            this.repaint();
            createMenuBar();
        });
        this.add(startButton);
    }

    //Set up the image of map
    private void setMapImage() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        ImageIcon icon = new ImageIcon("Dijkstras/Maps.png");
        Image img = icon.getImage().getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH);
        mapsImage = new JLabel(new ImageIcon(img));
        mapsImage.setBounds(0, 0, screenSize.width, screenSize.height);
        mapsImage.setVisible(true);
        this.add(mapsImage);

    }

    // create a for instructions label to make UI simpler
    public void setInstructions(){
        instructionLabel = new JLabel("", SwingConstants.CENTER);
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 17));
        instructionLabel.setForeground(Color.BLACK);

        this.add(instructionLabel);
        instructionLabel.setVisible(true);
        instructionLabel.setBounds(-400, 700, frame.getWidth(), 50);
        instructionLabel.setText("Please Enter Two Integers for Point A and Point B to see displayed path");

    }
    // create a label to display the distance when a path is chosen
    public void setShortestDistance(int distance){
        //reset label when new distance is found
        if(pathDistanceLabel!=null&&pathDistanceLabel.isVisible()){
            pathDistanceLabel.setVisible(false);
        }
        pathDistanceLabel = new JLabel("", SwingConstants.CENTER);
        pathDistanceLabel.setFont(new Font("Arial", Font.BOLD, 20));
        pathDistanceLabel.setForeground(Color.BLACK);


        this.add(pathDistanceLabel);
        pathDistanceLabel.setVisible(true);
        pathDistanceLabel.setBounds(-500, 650, frame.getWidth(), 50);
        pathDistanceLabel.setText("The total distance is "+ distance + " miles");

    }


    // Highlight a node when the user hovers over it
    private void highlightOnHover(int mouseX, int mouseY) {
        Integer closestNode = null;
        double minDistance = Double.MAX_VALUE;

        // Check all coordinates and find the closest node to the cursor
        for (int[] node : coordinates) {
            int nodeX = node[1];
            int nodeY = getHeight() - node[2];
            double distance = Math.sqrt(Math.pow(mouseX - nodeX, 2) + Math.pow(mouseY - nodeY, 2));

            // Update the closest node if a closer one is found
            if (distance < 10 && distance < minDistance) {
                minDistance = distance;
                closestNode = node[0];
            }
        }
        // Only repaint if the hovered node has changed
        if (closestNode != hoveredNode) {
            hoveredNode = closestNode;
            repaint();
        }
    }

    // Method to highlight a path by node IDs
    public void highlightPath(LinkedList<Integer> path) {
        if (path.isEmpty()) return;
        // Clear previously highlighted nodes
        highlightedNodes.clear();
        // Add the path nodes to the highlighted set, function of the set
        highlightedNodes.addAll(path);
        repaint();
    }

    // Method to create path edges
    private void makePathEdges(LinkedList<Integer> path) {
        if (path.isEmpty()) return;
        pathEdges.clear();
        for (int i = 0; i < path.size() - 1; i++) {
            pathEdges.add(path.get(i));
            pathEdges.add(path.get(i + 1));
        }
    }

    //method to select nodes as start and end points of the path when clicked
    private void selectNode(int mouseX, int mouseY) {
        Integer selectedNode = null;
        double minDistance = Double.MAX_VALUE;

        // Find the closest node to the mouse click using logic from hover method
        for (int[] node : coordinates) {
            int nodeX = node[1];
            // Adjust for screen inversion
            int nodeY = getHeight() - node[2];
            double distance = Math.sqrt(Math.pow(mouseX - nodeX, 2) + Math.pow(mouseY - nodeY, 2));

            // Check if the click is within a threshold distance of the node
            if (distance < 10 && distance < minDistance) {
                minDistance = distance;
                selectedNode = node[0];
            }
        }


        // If we have a selected node, set it as pointA or pointB
        if (selectedNode != null) {
            if (point1 == null) {
                point1 = selectedNode;
                //reset point 2 only after point one has been clicked
                point2=null;

            } else if (point2 == null) {
                point2 = selectedNode;
            }
        }
        //if point is selected, call dijstras and highlight the path
        if (point1 != null && point2 != null) {
            LinkedList<Integer>path=graph.dijkstra(point1, point2);
            highlightPath(path);
            makePathEdges(path);
            setShortestDistance(shortestDistance(path));
            //reset point 1
            point1=null;
        }

        // repaint to update the visual representation
        repaint();
    }


    // Scale coordinates to fit the screen (done by Ishaan pre gui merge)
    private void scaleCoordinates(int width, int height) {
        int minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE;

        for (int[] node : coordinates) {
            minX = Math.min(minX, node[1]);
            maxX = Math.max(maxX, node[1]);
            minY = Math.min(minY, node[2]);
            maxY = Math.max(maxY, node[2]);
        }


        //scaling coordinates, multiplied by 0.85 to fit on screen (changed by charlotte)
        for (int i = 0; i < coordinates.length; i++) {
            coordinates[i][1] = (int) ((coordinates[i][1] - minX) * ((double) width / (maxX - minX))*(0.85));
            coordinates[i][2] = (int) ((coordinates[i][2] - minY) * ((double) height / (maxY - minY))*(0.85));
        }


        // Move the map over by a fixed amount
        int moveAmount = -20;
        for (int i = 0; i < coordinates.length; i++) {
            coordinates[i][1] -= 6*moveAmount;
        }
    }

    // Create a MenuBar
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // File menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        // View menu
        JMenu viewMenu = new JMenu("View");
        JMenuItem highlightPathItem = new JMenuItem("Highlight Path");
        highlightPathItem.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Path is highlighted!"));
        viewMenu.add(highlightPathItem);
        menuBar.add(viewMenu);

        // Help menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(frame, "This program depicts a map of cities in the United States. It \n returns the shortest path between two given points as well \n as the length of the path in miles."));
        helpMenu.add(aboutItem);
        menuBar.add(helpMenu);

        // Set the menu bar to the JFrame
        frame.setJMenuBar(menuBar);
    }

//paint component method to add things to the screen, creates dots and paths
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw regular nodes and highlighted path nodes
        for (int[] node : coordinates) {
            int x = node[1];
            // Invert the y-coordinate so that the map is right-side-up
            int y = getHeight() - node[2];

            //adjust the hover node and add to screen
            if (hoveredNode != null && node[0] == hoveredNode) {
                g2d.setColor(Color.ORANGE);
                // Larger node for hover effect
                g2d.fillOval(x - 4, y - 4, 12, 12);
            } else {
                g2d.setColor(Color.LIGHT_GRAY); // Regular nodes
                g2d.fillOval(x - 2, y - 2, 4, 4);
            }
        }


        // Set stroke and color for edges to make them thicker
        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke(3));

        // Draw the path edges or lines between highlighted nodes
        for (int i = 0; i < pathEdges.size() - 1; i += 2) {
            int nodeAId = pathEdges.get(i);
            int nodeBId = pathEdges.get(i + 1);


            int[] nodeA = getNodeById(nodeAId);
            int[] nodeB = getNodeById(nodeBId);


            if (nodeA != null && nodeB != null) {
                int yA = getHeight() - nodeA[2];
                int yB = getHeight() - nodeB[2];
                g2d.drawLine(nodeA[1], yA, nodeB[1], yB);
            }
        }


        //find first node and last node in path to make start and stop buttons
        if(!pathEdges.isEmpty()) {
            Integer firstNode = pathEdges.getFirst();
            Integer lastNode = pathEdges.getLast();
            // Draw the start and end nodes in green last so they appear on top of everything
            for (int[] node : coordinates) {
                int x = node[1];
                // Invert the y-coordinate again
                int y = getHeight() - node[2];

                // If this node is in the highlighted set, draw a larger green circle
                if (node[0] == firstNode) {
                    g2d.setColor(Color.GREEN);
                    g2d.fillOval(x - 3, y - 3, 10, 10);
                } else if (node[0] == lastNode) {
                    g2d.setColor(Color.RED);
                    g2d.fillOval(x - 3, y - 3, 10, 10);
                }
            }
        }
    }

//done by ishaan pre combination of gui temp and gui, given ID of node from list returns "node"
    private int[] getNodeById(int id) {
        for (int[] node : coordinates) {
            if (node[0] == id) {
                return node;
            }
        }
        return null;
    }

    //set up text boxes to enter point a and point b by typing
    private void setUpTextField(){

        //text field point a
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        textFieldA = createJTextField();
        textFieldA.setBounds(10,screenSize.height/2+35,100,50);

        textFieldA.setToolTipText("Enter Beginning Point");
        textFieldA.setVisible(true);

        textFieldALabel = new JLabel("Please Enter Point A:");
        textFieldALabel.setFont(new Font("Arial", Font.BOLD,15));
        textFieldALabel.setBounds(10,screenSize.height/2,400,50);

        this.add(textFieldALabel);
        this.add(textFieldA);


        //text field point b
        textFieldB= createJTextField();
        textFieldB.setBounds(10,screenSize.height/2+120,100,50);
        textFieldB.setToolTipText("Enter Destination Point");
        textFieldB.setVisible(true);

        textFieldLabelB = new JLabel("Please Enter Point B: ");
        textFieldLabelB.setFont(new Font("Arial", Font.BOLD,15));
        textFieldLabelB.setBounds(10,screenSize.height/2+85,400,50);
        textFieldLabelB.setVisible(true);

        this.add(textFieldLabelB);
        this.add(textFieldB);


        //action listeners for field A
        textFieldA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pointA= Integer.parseInt(textFieldA.getText());

                textFieldA.setVisible(false);

                textFieldALabel.setVisible(false);
            }
        });

        //action listeners for field B
        textFieldB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pointB= Integer.parseInt(textFieldB.getText());
                System.out.println("point B: "+pointB);
                textFieldB.setVisible(false);
                textFieldLabelB.setVisible(false);
            }
        });
    }

    //text field creator
    public JTextField createJTextField(){
        JTextField textField = new JTextField(10);
        return textField;
    }

//finds a new path given entered numbers into text fields and highlights it
    public void findNewPath(GUI gui){
        int pointATemp=0;
        int pointBTemp=0;

        while(pointB==0||pointA==0) {
            if(!textFieldB.getText().equals("") &&!textFieldA.getText().equals("")){
                pointATemp = pointA;
                pointBTemp = pointB;
            }
        };
        LinkedList<Integer> path2 = graph.dijkstra(pointATemp, pointBTemp);
        printPath(path2);

        pointA=0;
        pointB=0;

        highlightPath(path2);
        makePathEdges(path2);
        setShortestDistance(shortestDistance(path2));

        SwingUtilities.updateComponentTreeUI(frame);


        textFieldA.setText("");
        textFieldB.setText("");


        textFieldA.setVisible(true);
        textFieldB.setVisible(true);


        textFieldALabel.setVisible(true);
        textFieldLabelB.setVisible(true);


        findNewPath(gui);
    }
    //prints the path out if needed
    public static void printPath(LinkedList<Integer> path) {
        int shortestDistance = 0;
        if (path.isEmpty()) {
            System.out.println("No path found.");
            return;
        }


        System.out.println("Shortest path:");
        for(int i = 0; i<path.size();i++){
            System.out.println(path.get(i));
        }
    }

    //finds the shortest distance based on path, converts approximately to miles
    public static int shortestDistance(LinkedList<Integer> path){
        int shortestDistance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            shortestDistance += graph.returnWeight(path.get(i), path.get(i + 1));
        }
        shortestDistance*=0.34;
        return shortestDistance;
    }

// setters and getters for point a and b if needed
    public static int getPointA(){
        return pointA;
    }


    public static int getPointB(){
        //System.out.println(pointB);


        return pointB;
    }


    public static void setPointA(int zero){
        pointA=zero;
    }
    public static void setPointB(int zero){
        pointB=zero;
    }



    public static void main(String[] args) throws IOException {

    }
}

