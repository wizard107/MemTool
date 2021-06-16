package Model;

import java.util.ArrayList;

public class Deck {
    private int id;
    private String name;
    private ArrayList<Card> CardDeck[];
    private String[] hashtag;
    private double rating;

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public ArrayList<Card>[] getCardDeck() {
        return CardDeck;
    }
    public String[] getHashtag() {
        return hashtag;
    }
    public double getRating() {
        return rating;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCardDeck(ArrayList<Card>[] cardDeck) {
        CardDeck = cardDeck;
    }
    public void setHashtag(String[] hashtag) {
        this.hashtag = hashtag;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }
}
