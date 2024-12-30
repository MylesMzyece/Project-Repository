import acm.graphics.GImage;

import javax.print.Doc;

public class King extends DocilePiece {
    public King (Location l, World w, Boolean iW, int satiety){
        super(l,w,iW, satiety);
        pieceValue = 1;
        myLifeSpan = Integer.MAX_VALUE;
        if (iW == true) {
            myPiece = new GImage("/Users/tmzyece25/Downloads/king.png");
        } else {
            myPiece = new GImage("/Users/tmzyece25/Downloads/king1.png");
        }
    }
    public void reproduce(){

    }
}
