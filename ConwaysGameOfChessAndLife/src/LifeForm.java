// This class represents a basic life form in a simulated world.
import acm.graphics.GImage;
import acm.util.RandomGenerator;

import java.awt.Color;

public abstract class LifeForm {

	// Attributes defining a life form

		protected World myWorld;
		protected int myLifeSpan;
		protected Location myLocation;
		protected Color myColor;
		protected GImage myPiece;
		protected int myAge;
		protected boolean alive;
		protected int pieceValue;
		protected boolean isPieceWhite;
		protected int satiety;

	public int getSatiety(){
		return satiety;
	}
	public void setSatiety(int satiety){
		this.satiety= satiety;
	}
	public void addToSatiety(int x){
		satiety+=x;
	}


	// Random generator for spawning creatures randomly
		RandomGenerator rGen = new RandomGenerator();

	// Constructors for life forms

	public LifeForm(int myLifeSpan, Location myLocation, World myWorld, int pieceValue, boolean isPieceWhite, GImage myPiece) {
			super();
			this.myLifeSpan = myLifeSpan;
			this.myLocation = myLocation;
			this.isPieceWhite = isPieceWhite;
			this.myColor = (isPieceWhite) ? Color.white : Color.black;
			this.myWorld = myWorld;
			this.pieceValue = pieceValue;
			this.myPiece = myPiece;
			alive = true;
		}

	public LifeForm(Location myLocation, World myWorld, Boolean isPieceWhite, int satiety) {
		super();
		this.satiety=satiety;
		this.myWorld = myWorld;
		this.myLocation = myLocation;
		this.isPieceWhite = isPieceWhite;
		this.myColor = (isPieceWhite) ? Color.white : Color.black;
		alive = true;
	}

	/* Basic life form functions:
	 * 		- age
	 * 		- reproduce
	 * 		- color
	 *		- aliveness
	 * 		- numerical value
	 */

	// Aging a life form by a specified amount of time
		public void age(int time){
			myAge += time;
			if (myAge > myLifeSpan)
				alive = false;
		}

	// Abstract method for reproduction, to be implemented by subclasses
		public abstract void reproduce();

	// Checks if the life form is dead
		public boolean isDead(){
			return !alive;
		}

	// Getters and setters

		public int getMyLifeSpan() {
			return myLifeSpan;
		}

		public void setMyLifeSpan(int myLifeSpan) {
			this.myLifeSpan = myLifeSpan;
		}

		public void killLifeForm(){
			this.alive = false;
		}

		public int getValue(){
			return this.pieceValue;
		}

		public Location getMyLocation() {
			return myLocation;
		}

		public void setMyLocation(Location myLocation) {
			this.myLocation = myLocation;
		}

		public Color getMyColor() {
			return myColor;
		}

		public void setMyColor(Color myColor) {
			this.myColor = myColor;
		}

		public int getAge() {
			return myAge;
		}

		public void setAge(int age) {
			this.myAge = age;
		}

		public GImage getMyPiece(){
			return myPiece;
		}

	// Generates a random number within the world size for reproduction purposes
		public int getRandNumWorldSize(){
			return rGen.nextInt(rGen.nextInt(myWorld.getHeight()) + 1);
		}


	// Override of toString method for textual representation of a life form
	@Override
	public String toString() {
		return "LifeForm [myLifeSpan=" + myLifeSpan + ", myLocation="
				+ myLocation + ", myColor=" + myColor + "]";
	}
}

