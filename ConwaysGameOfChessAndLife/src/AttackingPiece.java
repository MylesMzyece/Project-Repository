import acm.util.RandomGenerator;

/**
 * Abstract class representing an attacking piece on the game board.
 */
public abstract class AttackingPiece extends LifeForm {

    /**
     * @param loc The location of the piece.
     * @param world The world where the piece exists.
     * @param isWhite Boolean indicating whether the piece is white.
     */
        public AttackingPiece(Location loc, World world, Boolean isWhite, int satiety) {
            super(loc, world, isWhite, satiety);
        }

        public abstract void move();

    /**
     * Checks if the piece overlaps with another piece of the same color.
     * @return True if overlaps, false otherwise.
     */
        public boolean pieceOverlapsSameColor() {
            for (int i = 0; i < myWorld.getCreatureList().size(); i++) {
                if (myWorld.getCreatureList().get(i).getMyLocation().getX() == getMyLocation().getX() &&
                        myWorld.getCreatureList().get(i).getMyLocation().getY() == getMyLocation().getY() &&
                        isPieceWhite == myWorld.getCreatureList().get(i).isPieceWhite &&
                        this != myWorld.getCreatureList().get(i)) {
                    return true;
                }
            }
            return false;
        }


    //Resets the position of the piece in the event that there is overlap

        public void resetPos(int creatureIndex) {
            setMyLocation(myWorld.getPreviousLocation().get(creatureIndex));
        }

        public void reproduce(){
            if (satiety > 1){
                int count = 0; // Counter to limit stack overflow in the event that every square is occupied
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
                satiety = 0;
            }
        }


    public boolean overlapsReproduction(int newX, int newY) {
        for (int i = 0; i < myWorld.getCreatureList().size(); i++) {
            if (newX == myWorld.getCreatureList().get(i).getMyLocation().getX() && newY == myWorld.getCreatureList().get(i).getMyLocation().getY() && this != myWorld.getCreatureList().get(i)) {
                return true;
            }
        }
        return false;
    }
}

