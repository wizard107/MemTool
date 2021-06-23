package Model;

import java.util.ArrayList;

public class Deck {
    private int id;
    private String deckName;
    private ArrayList<Card> cards;
    private String[] tags;
    private int numberOfCards;
    private int due;
    private int newCards;
    private int again;
    private double rating;

    /**
     * Default Constructor
     */
    public Deck() {}

    /**
     * Constructor for fetching deck from database and creating model class from it
     */
    public Deck(int id, String deckName, ArrayList<Card> cards, String[] tags,
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
    public String[] getTags() {
        return tags;
    }
    public int getNumberOfCards() { return numberOfCards;}
    public int getDue() { return due;}
    public int getNewCards() { return newCards;}
    public int getAgain() { return again;}
    public double getRating() {
        return rating;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }
    public void setCardDeck(ArrayList<Card> cards) {
        this.cards = cards;
    }
    public void setTags(String[] tags) {
        this.tags = tags;
    }
    public void setNumberOfCards(int numberOfCards) { this.numberOfCards = numberOfCards;}
    public void setDue(int due) { this.due = due;}
    public void setNewCards(int newCards) { this.newCards = newCards;}
    public void setAgain(int again) { this.again = again;}
    public void setRating(double rating) {
        this.rating = rating;
    }
}
