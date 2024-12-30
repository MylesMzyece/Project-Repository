public class Card {

    private int v;
    private String suit;

    private String colour;

    public Card(int v, String suit, String colour){
        this.v = v;
        this.suit = suit;
        this.colour = colour;

    }

    //getters

    public int getValue(){
        return v;
    }

    public String getSuit(){
        return suit;
    }

    public String getColour(){return colour;}

    public String toString(){
        return (v + " of " + suit + ". ");
    }

}
