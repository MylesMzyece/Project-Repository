import acm.graphics.GImage;
public class Rook extends AttackingPiece {
    public Rook(Location l, World w, Boolean iW, int satiety) {
        super(l, w, iW, satiety);
        pieceValue = 5;
        myLifeSpan = pieceValue*8;
        if (iW ==true) {
            myPiece = new GImage("/Users/tmzyece25/Downloads/rook.png");
        } else{
            myPiece = new GImage("/Users/tmzyece25/Downloads/rook1.png");
        }
        satiety = 5;
    }

    public void move(){
        boolean hasMoved = false;
        int count = 0;
        while (hasMoved == false && count < 200) {
            count++;
            Location l = myLocation;
            int randDir = (int) ((Math.random() * 4));
            int xSpaceToLeft = l.getX() - 1;
            int xSpaceToRight = myWorld.getWidth() - l.getX() - 2;
            int ySpaceToTop = l.getY() - 1;
            int ySpaceToBottom = myWorld.getHeight() - l.getY() - 2;
            if (randDir == 0) {
                int movementLength = (int) (Math.random() * ySpaceToTop + 1);
                myLocation.setY(myLocation.getY() - movementLength);
                hasMoved = true;
            } else if (randDir == 1) {
                int movementLength = (int) (Math.random() * xSpaceToRight + 1);
                myLocation.setX(myLocation.getX() + movementLength);
                hasMoved = true;
            } else if (randDir == 2) {
                int movementLength = (int) (Math.random() * ySpaceToBottom + 1);
                myLocation.setY(myLocation.getY() + movementLength);
                hasMoved = true;
            } else {
                int movementLength = (int) (Math.random() * xSpaceToLeft + 1);
                myLocation.setX(myLocation.getX() - movementLength);
                hasMoved = true;
            }
        }
    }
}
