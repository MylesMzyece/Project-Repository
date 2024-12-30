import acm.graphics.GImage;

public class Bishop extends AttackingPiece {

    /**
     * @param loc The location of the piece.
     * @param world The world where the piece exists.
     * @param isWhite Boolean indicating whether the piece is white.
     */

    public Bishop(Location loc, World world, Boolean isWhite, int satiety){
        super(loc, world, isWhite, satiety);
        pieceValue = 3;
        myLifeSpan = pieceValue*8;
        if (isWhite) {
            myPiece = new GImage("/Users/tmzyece25/Downloads/bishop.png");
        } else {
            myPiece = new GImage("/Users/tmzyece25/Downloads/bishop1.png");
        }
        satiety = 5;
    }
    public void move() {
        Location l = myLocation;
        int xSpaceToLeft = l.getX();
        int xSpaceToRight = myWorld.getWidth() - l.getX() - 1;
        int ySpaceToTop = l.getY();
        int ySpaceToBottom = myWorld.getHeight() - l.getY() - 1;

        int randDir = (int) (Math.random() * 4);

        boolean hasMoved = false;
        int count = 0;
        while (hasMoved == false && count < 200) {
            count++;
            if (randDir == 0) {
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
            if (randDir == 1) {
                int movementLength = 1;
                if (xSpaceToRight > ySpaceToBottom) {
                    movementLength = (int) (Math.random() * ySpaceToBottom);
                } else {
                    movementLength = (int) (Math.random() * xSpaceToRight);
                }

                myLocation.setY(myLocation.getY() + movementLength);
                myLocation.setX(myLocation.getX() + movementLength);
                hasMoved = true;
            }

            if (randDir == 2) {
                int movementLength = 0;
                if (xSpaceToLeft > ySpaceToBottom) {
                    movementLength = (int) (Math.random() * ySpaceToBottom);
                } else {
                    movementLength = (int) (Math.random() * xSpaceToLeft);
                }

                myLocation.setY(myLocation.getY() + movementLength);
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
