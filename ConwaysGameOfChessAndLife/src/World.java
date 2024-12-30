import acm.util.RandomGenerator;
import java.util.ArrayList;

public class World {

	// Declaration of class variables
	private int width;
	private int height;
	private ArrayList<LifeForm> creatureList;

	RandomGenerator rGen = new RandomGenerator();

	private ArrayList<Location> previousLocation; // ArrayList to store previous locations of life forms in order to return them to that position in the event they are the same color and overlapping

	// Constructor to initialize the world with given width and height

	public World(int width, int height) {
			super();
			this.width = width;
			this.height = height;
			this.creatureList = new ArrayList<LifeForm>(); // Initializing creatureList
			this.previousLocation = new ArrayList<Location>(); // Initializing previousLocation
		}


		public void letTimePass() {
			makeNewCreatures();
			makeCreaturesMove();
			resetPosition();
			eatThings();
			creaturesGetOlder();
			purgeTheDead();
		}

	// Creates new creatures based on certain conditions
		public void makeNewCreatures() {
			int currentSizeOfCreatureList = creatureList.size();
			if (whiteKingExists() && blackKingExists()) {
				for (int i = 0; i < currentSizeOfCreatureList; i++) {
					creatureList.get(i).reproduce();
				}
			} else if (whiteKingExists()) {
				for (int i = 0; i < currentSizeOfCreatureList; i++) {
					if (creatureList.get(i).isPieceWhite)
						creatureList.get(i).reproduce();
				}
			} else if (blackKingExists()) {
				for (int i = 0; i < currentSizeOfCreatureList; i++) {
					if (creatureList.get(i).isPieceWhite == false) {
						creatureList.get(i).reproduce();
					}
				}
			}
		}

	// Method to remove dead creatures from the world
		public void purgeTheDead() {
			int i = 0;
			while (i < creatureList.size()) {
				if (creatureList.get(i).isDead())
					creatureList.remove(i);
				else
					i++;
			}
		}

	// Method to age all creatures in the world
		public void creaturesGetOlder() {
			for (LifeForm l : creatureList) {
				l.age(1);
			}
		}

	/** This method causes creatures to eat each other
	 * If the value of the two pieces are the same, both are eaten and disappear
	 * If the value of one is greater than the other, the one with the greater value remains
 	 */
		public void eatThings() {
			for (int i = 0; i < creatureList.size(); i++) {
				for (int j = 0; j < creatureList.size(); j++) {
					if (i != j && diffColourOverlapping(creatureList.get(i), creatureList.get(j)) && creatureList.get(i).getValue() == creatureList.get(j).getValue()) {
						creatureList.get(j).killLifeForm();
						creatureList.get(i).killLifeForm();
					} else if (i != j && diffColourOverlapping(creatureList.get(i), creatureList.get(j)) && creatureList.get(i).getValue() > creatureList.get(j).getValue()) {
						int value=creatureList.get(j).getValue();
						creatureList.get(i).addToSatiety(value);
						creatureList.get(j).killLifeForm();
						creatureList.remove(j);
					} else if (i != j && diffColourOverlapping(creatureList.get(i), creatureList.get(j)) && creatureList.get(i).getValue() < creatureList.get(j).getValue()) {
						creatureList.get(i).killLifeForm();
						int value=creatureList.get(i).getValue();
						creatureList.get(j).addToSatiety(value);
						creatureList.remove(i);
					}
				}
			}
		}

	// Method to make creatures move
		public void makeCreaturesMove() {
			for (int j = 0; j < creatureList.size(); j++) {
				Location l = new Location(creatureList.get(j).getMyLocation().getX(), creatureList.get(j).getMyLocation().getY());
				previousLocation.add(l);
			}

			for (int i = 0; i < creatureList.size(); i++) {
				LifeForm L = creatureList.get(i);
				if (L instanceof AttackingPiece) {
					// L has to be cast into an attacking piece in order for it to be able to move
					((AttackingPiece) L).move();
				}
			}
		}

	// This method to checks if there exists a white king in the world
		public boolean whiteKingExists() {
			for (int i = 0; i < creatureList.size(); i++) {
				if (creatureList.get(i) instanceof King && creatureList.get(i).isPieceWhite == true) {
					return true;
				}
			}
			return false;
		}

	// Method to check if there exists a black king in the world
		public boolean blackKingExists() {
			for (int i = 0; i < creatureList.size(); i++) {
				if (creatureList.get(i) instanceof King && creatureList.get(i).isPieceWhite == false) {
					return true;
				}
			}
			return false;
		}

	// Setters and getters
		public int getWidth() {
			return width;
		}


		public void setWidth(int width) {
			this.width = width;
		}

		public int getHeight() {
			return height;
		}


		public void setHeight(int height) {
			this.height = height;
		}


		public ArrayList<LifeForm> getCreatureList() {
			return creatureList;
		}


		public ArrayList<Location> getPreviousLocation() {
			return previousLocation;
		}


		public void setCreatureList(ArrayList<LifeForm> creatureList) {
			this.creatureList = creatureList;
		}

	// Method to check if two life forms of different colors are overlapping
		public boolean diffColourOverlapping(LifeForm A, LifeForm B) {
			if (A.getMyLocation().getY() == B.getMyLocation().getY() && A.getMyLocation().getX() == B.getMyLocation().getX() && A.isPieceWhite != B.isPieceWhite) {
				return true;
			}
			return false;
		}

	// Resets positions of creatures if they overlap with same color
	public void resetPosition() {
		for (int i = 0; i < creatureList.size(); i++) {
			LifeForm L = creatureList.get(i);
			if (L instanceof AttackingPiece && ((AttackingPiece) L).pieceOverlapsSameColor()) {
				AttackingPiece K = (AttackingPiece) L;
				K.resetPos(i);
			}
		}
		previousLocation.clear(); // Clearing the previous locations so that the index is always 1:1
	}

	// IDK what override does
	@Override
	public String toString() {
		return "World [width=" + width + ", height=" + height + ", creatureList=" + creatureList + "]";
	}
}
