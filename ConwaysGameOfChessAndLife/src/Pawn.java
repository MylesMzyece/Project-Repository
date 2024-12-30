import acm.graphics.GImage;
import acm.util.RandomGenerator;


public class Pawn extends DocilePiece {

	// Constructor for Pawn class
	public Pawn(Location l, World w, Boolean iW, int satiety) {
		super(l, w, iW, satiety);
		pieceValue = 1;
		myLifeSpan = 3;
		if (iW == true) {
			myPiece = new GImage("/Users/tmzyece25/Downloads/pawn.png");
		} else {
			myPiece = new GImage("/Users/tmzyece25/Downloads/pawn1.png");
		}
	}

	/**	Method to handle reproduction of Pawn
	 * 	Spawns randomly in a world of any size
 	 */


		public void reproduce() {
			RandomGenerator rGen = new RandomGenerator();
			int fiftyFiftyChance = rGen.nextInt(0, 1);
			int count = 0; // Counter to limit stack overflow in the event that every square is occupied
			if (fiftyFiftyChance == 0) { //basically a coin-flip to determine if a pawn is going to reproduce
				int newX = getRandNumWorldSize();
				int newY = getRandNumWorldSize();
				while (overlapsReproduction(newX, newY) && count < 50) {
					newX = getRandNumWorldSize();
					newY = getRandNumWorldSize();
					count++; // Incrementing attempt counter
				}
				if (!overlapsReproduction(newX, newY)) { // Checking if the location is valid for reproduction
					myWorld.getCreatureList().add(new Pawn(new Location(newX, newY), myWorld, isPieceWhite, 0));
				}
			}
		}

	// Method to check if the given coordinates overlap with existing creatures for reproduction
		public boolean overlapsReproduction(int newX, int newY) {
			for (int i = 0; i < myWorld.getCreatureList().size(); i++) {
				if (newX == myWorld.getCreatureList().get(i).getMyLocation().getX() && newY == myWorld.getCreatureList().get(i).getMyLocation().getY() && this != myWorld.getCreatureList().get(i)) {
					return true;
				}
			}
			return false;
		}
}


