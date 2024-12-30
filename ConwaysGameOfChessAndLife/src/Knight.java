import acm.graphics.GImage;

public class Knight extends AttackingPiece{
    public Knight (Location l, World w, Boolean iW, int satiety){
        super(l, w, iW, satiety);
        pieceValue = 3;
        myLifeSpan = pieceValue*8;
        if (iW) {
            myPiece = new GImage("/Users/tmzyece25/Downloads/knight.png");
        } else {
            myPiece = new GImage("/Users/tmzyece25/Downloads/knight1.png");
        }
        satiety = 0;

    }
    /*public void reproduce() {
        if (satiety >= pieceValue * 2) {
            int newX = (myLocation.getX());
            int newY = (myLocation.getY());
            myWorld.getCreatureList().add(new Knight(new Location(newX,newY), myWorld, isPieceWhite, 0));
        }
    }*/

    public void move(){
        boolean hasMoved = false;
        int count = 0;
        while (hasMoved  == false && count<200) {
            count++;
            Location l = myLocation;
            int randDir = (int) (Math.random() * 8);

            if (randDir == 1 && myLocation.getX() >= 2 && myLocation.getY() >= 1 && !pieceOverlapsSameColor()) {
                myLocation.setX(myLocation.getX() - 2);
                myLocation.setY(myLocation.getY() - 1);
                hasMoved = true;
            } else if (randDir == 2 && myLocation.getX() >= 1 && myLocation.getY() >= 2 && !pieceOverlapsSameColor()) {
                myLocation.setX(myLocation.getX() - 1);
                myLocation.setY(myLocation.getY() - 2);
                hasMoved = true;
            } else if (randDir == 3 && myLocation.getX() <= myWorld.getWidth() - 2 && myLocation.getY() >= 2 && !pieceOverlapsSameColor()) {
                myLocation.setX(myLocation.getX() + 1);
                myLocation.setY(myLocation.getY() - 2);
                hasMoved = true;
            } else if (randDir == 4 && myLocation.getX() <= myWorld.getWidth() - 3 && myLocation.getY() >= 1 && !pieceOverlapsSameColor()) {
                myLocation.setX(myLocation.getX() + 2);
                myLocation.setY(myLocation.getY() - 1);
                hasMoved = true;
            } else if (randDir == 5 && myLocation.getX() <= myWorld.getWidth() - 3 && myLocation.getY() < myWorld.getHeight() - 1 && !pieceOverlapsSameColor()) {
                myLocation.setX(myLocation.getX() + 2);
                myLocation.setY(myLocation.getY() + 1);
                hasMoved = true;
            } else if (randDir == 6 && myLocation.getX() <= myWorld.getWidth() - 2 && myLocation.getY() < myWorld.getHeight() - 2 && !pieceOverlapsSameColor()) {
                myLocation.setX(myLocation.getX() + 1);
                myLocation.setY(myLocation.getY() + 2);
                hasMoved = true;
            } else if (randDir == 7 && myLocation.getX() >= 1 && myLocation.getY() < myWorld.getHeight() - 2 && !pieceOverlapsSameColor()) {
                myLocation.setX(myLocation.getX() - 1);
                myLocation.setY(myLocation.getY() + 2);
                hasMoved = true;
            } else if (randDir == 8 && myLocation.getX() >= 2 && myLocation.getY() < myWorld.getHeight() - 1 && !pieceOverlapsSameColor()) {
                myLocation.setX(myLocation.getX() - 2);
                myLocation.setY(myLocation.getY() + 1);
                hasMoved = true;
            }
        }

    }

}
