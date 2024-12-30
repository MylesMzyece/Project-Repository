import acm.graphics.GImage;
import acm.util.RandomGenerator;

public class Queen extends AttackingPiece{

    /**
     * @param l The location of the piece.
     * @param w The world where the piece exists.
     * @param iW Boolean indicating whether the piece is white.
     */

    RandomGenerator rGen = new RandomGenerator();

    public Queen(Location l, World w, Boolean iW, int satiety){
        super(l, w, iW, satiety);
        pieceValue=9;
        myLifeSpan=pieceValue*8;

        if(iW){
            myPiece= new GImage("/Users/tmzyece25/Downloads/queen.png");
        }
        else{
            myPiece= new GImage("/Users/tmzyece25/Downloads/queen1.png");
        }

    }

    /**
     * The queen moves chooses 1 of 8 random directions to move in
     * Another random integer controls the distance that it travels
     * Its travel distance is limited to the edges of the world
     */

    public void move(){
        /** hasMoved exists to ensure that the only reason why the queen hasn't moved is because movement length is equal to zero
         *  There's also a counter to prevent a potential stack overflow
         */
        boolean hasMoved = false;
        int count = 0;
        while (hasMoved  == false && count<200) {
            Location l = myLocation;
            int randDir = (int) (Math.random() * 8);
            int xSpaceToLeft = l.getX();
            int xSpaceToRight = myWorld.getWidth() - l.getX() - 1;
            int ySpaceToTop = l.getY();
            int ySpaceToBottom = myWorld.getHeight() - l.getY() - 1;
            count ++;
            if (randDir == 0) {
                int movementLength = 0;
                    movementLength = (int) (Math.random() * ySpaceToTop);

                myLocation.setY(myLocation.getY() - movementLength);
                hasMoved = true;
            }
            if (randDir == 1) {
                int movementLength = 0;
                if (xSpaceToRight > ySpaceToTop) {

                        movementLength = (int) (Math.random() * ySpaceToTop);

                } else {

                        movementLength = (int) (Math.random() * xSpaceToRight);

                }
                myLocation.setY(myLocation.getY() - movementLength);
                myLocation.setX(myLocation.getX() + movementLength);
                hasMoved = true;
            }

            if (randDir == 2) {
                int movementLength = 0;

                    movementLength = (int) (Math.random() * xSpaceToRight);

                myLocation.setX(myLocation.getX() + movementLength);
                hasMoved = true;
            }

            if (randDir == 3) {
                int movementLength = 0;

                    movementLength = (int) (Math.random() * xSpaceToRight);

                if (xSpaceToRight > ySpaceToBottom) {

                        movementLength = (int) (Math.random() * ySpaceToBottom);

                } else {

                        movementLength = (int) (Math.random() * xSpaceToRight);

                }
                myLocation.setY(myLocation.getY() + movementLength);
                myLocation.setX(myLocation.getX() + movementLength);
                hasMoved = true;
            }

            if (randDir == 4) {
                int movementLength = 0;

                    movementLength = (int) (Math.random() * ySpaceToBottom);

                myLocation.setY(myLocation.getY() + movementLength);
                hasMoved = true;
            }
            if (randDir == 5) {
                int movementLength = 0;
                if (xSpaceToLeft > ySpaceToBottom) {

                        movementLength = (int) (Math.random() * ySpaceToBottom);

                } else {

                        movementLength = (int) (Math.random() * xSpaceToLeft);

                }
                myLocation.setY(myLocation.getY() + movementLength);
                myLocation.setX(myLocation.getX() - movementLength);
                hasMoved = true;
            }
            if (randDir == 6) {
                int movementLength = 0;

                    movementLength = (int) (Math.random() * xSpaceToLeft);

                myLocation.setX(myLocation.getX() - movementLength);
                hasMoved = true;
            } else {
                int movementLength = 0;
                if (xSpaceToLeft > ySpaceToTop) {

                        movementLength = (int) (Math.random() * ySpaceToTop);

                } else {

                        movementLength = (int) (Math.random() * xSpaceToLeft);

                }
                myLocation.setY(myLocation.getY() - movementLength);
                myLocation.setX(myLocation.getX() - movementLength);
                hasMoved = true;
            }
        }
    }

}

