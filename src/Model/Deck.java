package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class Deck {
    private int id;
    private String deckName;
    private ArrayList<Card> cards;
    private Map<Integer, String> tags;
    private int numberOfCards;
    private int due;
    private int newCards;
    private int again;
    private double rating;
    //private boolean isPublic;
    private int deckPosition;

    /**
     * Default Constructor
     */
    public Deck() {}

    /**
     * Constructor for fetching deck data from database and creating model class from it
     */
    public Deck(int id, String deckName, ArrayList<Card> cards, Map<Integer, String> tags,
                int numberOfCards, int due, int newCards, int again, double rating) {
        this.id = id;
        this.deckName = deckName;
        this.cards = cards;
        this.tags = tags;
        this.numberOfCards = numberOfCards;
        this.due = due;
        this.newCards = newCards;
        this.again = again;
        this.rating = rating;
        //this.isPublic = isPublic;
    }

    /**
     * Constructor for creating an deck object to store as table entry in table Deck
     */
    public Deck(/*int id,*/ String deckName, int numberOfCards, int due, int newCards, int again, double rating) {
        //this.id = id;
        this.deckName = deckName;
        this.numberOfCards = numberOfCards;
        this.due = due;
        this.newCards = newCards;
        this.again = again;
        this.rating = rating;
        //this.isPublic = false;
    }

    public int getId() {
        return id;
    }
    public String getDeckName() {
        return deckName;
    }
    public ArrayList<Card> getCards() {
        return cards;
    }
    public Map<Integer, String> getTags() {
        return tags;
    }
    public int getNumberOfCards() { return numberOfCards;}
    public int getDue() { return due;}
    public int calcDue(){
        int countDue = 0;
        for(Card card: cards){
            if((card.getDueDate().equals(LocalDate.now())||(card.getDueDate().isBefore(LocalDate.now())))) countDue++;
            //&!=isnew !=isforgotten
        }
        return countDue;
    }
    public int calcNew(){
        int countNew = 0;
        for(Card card: cards){
            if(card.getIsNew()) countNew++;
        }
        return countNew;
    }
    public int calcAgain(){
        int countAgain = 0;
        for(Card card: cards){
            if(card.getWasForgotten()) countAgain++;
        }
        return countAgain;
    }
    public int getDeckPosition(){
        return deckPosition;
    }
    public void setDeckPosition(int i){
        deckPosition = i;
    }

    public int getNewCards() { return newCards;}
    public int getAgain() { return again;}
    public double getRating() {
        return rating;
    }
    //public boolean getIsPublic() { return isPublic;}

    public void setId(int id) {
        this.id = id;
    }
    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }
    public void setCardDeck(ArrayList<Card> cards) {
        this.cards = cards;
    }
    public void setTags(Map<Integer, String> tags) {
        this.tags = tags;
    }
    public void setNumberOfCards(int numberOfCards) { this.numberOfCards = numberOfCards;}
    public void setDue(int due) { this.due = due;}
    public void setNewCards(int newCards) { this.newCards = newCards;}
    public void setAgain(int again) { this.again = again;}
    public void setRating(double rating) {
        this.rating = rating;
    }
    //public void setIsPublic(boolean isPublic) {this.isPublic = isPublic;}
    public int getSize(){
        return cards.size();
    }
}
