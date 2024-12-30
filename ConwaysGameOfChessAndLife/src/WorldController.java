import java.awt.Color;
import acm.graphics.*;
import acm.io.IODialog;
import acm.program.*;
import acm.util.*;


public class WorldController extends GraphicsProgram {

	// Declaration of class variables

		private World theWorld;
		private GCanvas theWorldCanvas;
		public int WORLD_SIZE;
		public static int UNIT_SIZE = 30;
		public int APPLICATION_WIDTH = 400;
		public int APPLICATION_HEIGHT = 430;
		public boolean CLASSIC_CHESS_SETUP = false;


	// Declaration of RandomGenerator object
		RandomGenerator rGen = new RandomGenerator();

	// Declaration of Color objects for board squares
		Color DARK_BROWN = new Color(181,136,99);
		Color LIGHT_BROWN = new Color(255, 233, 197);

	// Main method to execute the program
		public void run(){
			getUserInput();
			setUpWorld();
			runWorld();
		}

	// Method to initialize the program -
		public void initialize(){
			resize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		}

	// Method to set up the world based on user preferences

		public void setUpWorld(){
			//init(); // Invoking method to initialize the program - This doesn't really work,
			if (CLASSIC_CHESS_SETUP == true){
				// Here, the application width for classic chess is set
				APPLICATION_WIDTH = 8*UNIT_SIZE;
				APPLICATION_HEIGHT = 9*UNIT_SIZE;
				makeClassicBoard();
				initialize();
			} else {
				//This option allows the user to create a custom world where pieces spawn somewhere randomly
				theWorld = new World(WORLD_SIZE, WORLD_SIZE);
				// The application height and width are just the size of the world multiplied by the size in pixels of a singular unit in that world
				APPLICATION_WIDTH = WORLD_SIZE*UNIT_SIZE;
				APPLICATION_HEIGHT = (WORLD_SIZE+1)*UNIT_SIZE;
				initialize();
				makeKings();

				makePiece("Queen", 3, true);
				makePiece("Queen", 3, false);
				makePiece("Rook", 2, false);
				makePiece("Rook", 2, true);
				makePiece("Knight", 5, false);
				makePiece("Bishop", 5, true);
				makePiece("Pawn", 10, true);
				makePiece("Pawn", 10, false);
				 // Getting the canvas for the world
			}
			theWorldCanvas = this.getGCanvas();
		}

	// Method to run the world simulation
		public void runWorld(){
			drawWorld();
			for(int i=0; i<400;i++){ // Running the simulation for x number of iterations
				theWorld.letTimePass();
				pause(100);
				theWorld.purgeTheDead();
				drawWorld();
			}
		}

	// Method to get user input for world setup
		public void getUserInput() {
			IODialog dialog = getDialog(); // Creating a dialog box for input - I took this from Yahtzee
			String setUpAnswer = dialog.readLine("Would you like a classical chess board set-up?");
			if (setUpAnswer.equalsIgnoreCase("yes") || setUpAnswer.equalsIgnoreCase("yes.") || setUpAnswer.equalsIgnoreCase("yes ")) {
				CLASSIC_CHESS_SETUP = true;
			} else {
				WORLD_SIZE = dialog.readInt("How big would you like the world to be?");
			}
		}

	// Method to draw the world
		public void drawWorld(){
			drawBlankWorld();
			drawCreatures();
		}

	/** Method to create kings
	 *  Spawns a black and white king at a random unoccupied location in a world of any size
	**/
		public void makeKings(){
			theWorld.getCreatureList().add(new King(new Location(getRandNumWorldSize(), getRandNumWorldSize()), theWorld, true, 0)); // Adding white king
			int newX = getRandNumWorldSize();
			int newY = getRandNumWorldSize();
			while (newX == theWorld.getCreatureList().get(0).getMyLocation().getX() && newY == theWorld.getCreatureList().get(0).getMyLocation().getY()) {
				newX = getRandNumWorldSize();
				newY = getRandNumWorldSize();
			}
			theWorld.getCreatureList().add(new King(new Location(newX, newY), theWorld, false, 0)); // Adding black king
		}

	// Method to create pieces for the classic chess board
		public void makeClassicBoard(){
			/** Loop to create rooks
			 * 	This is probably needlessly complicated
			 */
			theWorld = new World(8, 8);
			for (int i = 0; i < 14; i+=7) {
				for (int j = 0; j < 14; j += 7) {
					if (j >0) {
						theWorld.getCreatureList().add(new Rook(new Location(i, j), theWorld, true, 0)); // Adding white rook
					} else{
						theWorld.getCreatureList().add(new Rook(new Location(i, j), theWorld, false, 0)); // Adding black rook
					}
				}
			}

			// Loop to create knights
			for (int i = 1; i < 10; i+=5) {
				for (int j = 0; j < 14; j += 7) {
					if (j >0) {
						theWorld.getCreatureList().add(new Knight(new Location(i, j), theWorld, true, 0)); // Adding white knight
					} else{
						theWorld.getCreatureList().add(new Knight(new Location(i, j), theWorld, false, 0)); // Adding black knight
					}
				}
			}

			// Loop to create bishops
			for (int i = 2; i < 6; i+=3) {
				for (int j = 0; j < 14; j += 7) {
					if (j >0) {
						theWorld.getCreatureList().add(new Bishop(new Location(i, j), theWorld, true, 0)); // Adding white bishop
					} else{
						theWorld.getCreatureList().add(new Bishop(new Location(i, j), theWorld, false, 0)); // Adding black bishop
					}
				}
			}

			// Adding queens
			for(int i = 0; i < 14; i+=7){
				if(i>0){
					theWorld.getCreatureList().add(new Queen(new Location(3, i), theWorld, true, 0)); // Adding white queen
				} else {
					theWorld.getCreatureList().add(new Queen(new Location(3, i), theWorld, false, 0)); // Adding black queen
				}
			}

			// Adding kings
			for(int i = 0; i < 14; i+=7){
				if(i>0){
					theWorld.getCreatureList().add(new King(new Location(4, i), theWorld, true, 0)); // Adding white king
				} else {
					theWorld.getCreatureList().add(new King(new Location(4, i), theWorld, false, 0)); // Adding black king
				}
			}

			// Loop to create pawns
			for (int i = 0; i < 8; i++) {
				for (int j = 1; j < 10; j += 5) {
					if (j >1) {
						theWorld.getCreatureList().add(new Pawn(new Location(i, j), theWorld, true, 0));
					} else{
						theWorld.getCreatureList().add(new Pawn(new Location(i, j), theWorld, false, 0));
					}
				}
			}
			theWorldCanvas = this.getGCanvas(); // Getting the canvas for the world
		}

	/**	This method is similar to makeKings, but it more generalized and allows the user to decide on the number of desired pieces
	 * 	The pieces spawn at a random unoccupied location in the world
	 */

		public void makePiece(String A, int numPieces, boolean isWhite){
			for (int i = 0; i < numPieces; i++){
				int count = 0;
				int newX = getRandNumWorldSize();
				int newY = getRandNumWorldSize();
				while (newPieceOverlaps(newX, newY) && count < 50) {
					newX = getRandNumWorldSize();
					newY = getRandNumWorldSize();
					count++;
				}
				if (!newPieceOverlaps(newX, newY)) {
					// Adding specified piece based on input type
					if (A.equals("Pawn")) {
						theWorld.getCreatureList().add(new Pawn(new Location(newX, newY), theWorld, isWhite, 0));
					}
					if (A.equals("Knight")){
						theWorld.getCreatureList().add(new Knight(new Location(newX, newY), theWorld, isWhite, 0));
					}
					if (A.equals("Rook")){
						theWorld.getCreatureList().add(new Rook(new Location(newX, newY), theWorld, isWhite, 0));
					}
					if (A.equals("Bishop")){
						theWorld.getCreatureList().add(new Bishop(new Location(newX, newY), theWorld, isWhite, 0));
					}
					if (A.equals("Queen")){
						theWorld.getCreatureList().add(new Queen(new Location(newX, newY), theWorld, isWhite, 0));
					}
				}
			}
		}

	// Method to draw blank world
		public void drawBlankWorld(){
			// Loop through each row in the grid
			for(int row = 0 ; row < theWorld.getWidth(); row++) {
				// Loop through each column in the grid
				for (int col = 0; col < theWorld.getHeight(); col++) {
					// Create a new rectangle representing a square on the grid
					GRect r = new GRect(row * UNIT_SIZE, col * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
					// Check if the sum of the row and column indices is even or odd
					if ((row + col) % 2 == 0) {
						// Set the fill color to light brown for even indices
						r.setFillColor(LIGHT_BROWN);
					} else {
						// Set the fill color to dark brown for odd indices
						r.setFillColor(DARK_BROWN);
					}
					// Fill the square with the chosen color
					r.setFilled(true);
					// Add the square to the canvas
					theWorldCanvas.add(r);
				}
			}
		}

	// Method to check if a new piece overlaps with existing pieces
		public boolean newPieceOverlaps(int newX, int newY) {
			for (int i = 0; i < theWorld.getCreatureList().size(); i++) {
				if (newX == theWorld.getCreatureList().get(i).getMyLocation().getX() && newY == theWorld.getCreatureList().get(i).getMyLocation().getY()) {
					return true;
				}
			}
			return false;
		}

	// Method to draw creatures in the world
		public void drawCreatures(){
			for(LifeForm x: theWorld.getCreatureList()){
				GImage i = x.getMyPiece(); // Getting image representation of creature
				i.setSize(UNIT_SIZE, UNIT_SIZE); // Setting size of the image
				i.setLocation(x.getMyLocation().getX()*UNIT_SIZE, x.getMyLocation().getY()*UNIT_SIZE);
				theWorldCanvas.add(i);
			}
		}

	// Method to get a random number within world size
		public int getRandNumWorldSize(){
			return rGen.nextInt(WORLD_SIZE);
		}
}
