package Model;

import javax.swing.ImageIcon;

public class Card {
    private int id;
    private int deckID;
    private String frontText;
    private String backText;
    /*
    private ImageIcon frontImage;
    private ImageIcon backImage;
    //audiofiles for back and front
     */
    private double dueTime;
    private boolean wasForgotten;

    /**
     * Constructor for creating card from users input
     */
    public Card(int deckID, String frontText, String backText, double dueTime, boolean wasForgotten) {
        this.deckID = deckID;
        this.frontText = frontText;
        this.backText = backText;
        this.dueTime = dueTime;
        this.wasForgotten = wasForgotten;
    }

    /**
     * Constructor for fetching card data from database and creating model class from it
     */
    public Card(int id, int deckID, String frontText, String backText, double dueTime, boolean wasForgotten) {
        this.id = id;
        this.deckID = deckID;
        this.frontText = frontText;
        this.backText = backText;
        this.dueTime = dueTime;
        this.wasForgotten = wasForgotten;
    }

    public int getId() { return id; }
    public int getDeckID() { return deckID; }
    public String getFrontText() { return frontText; }
    public String getBackText() { return backText; }
    //public ImageIcon getFrontImage() { return frontImage; }
    //public ImageIcon getBackImage() { return backImage; }
    public double getDueTime() { return dueTime; }
    public boolean getWasForgotten() { return wasForgotten; }

    public void setId(int id) { this.id = id; }
    public void setDeckID(int deckID) { this.deckID = deckID; }
    public void setFrontText(String frontText) { this.frontText = frontText; }
    public void setBackText(String backText) { this.backText = backText; }
    //public void setFrontImage(ImageIcon frontImage) { this.frontImage = frontImage; }
    //public void setBackImage(ImageIcon backImage) { this.backImage = backImage; }
    public void setDueTime(double dueTime) { this.dueTime = dueTime; }
    public void setWasForgotten(boolean wasForgotten) { this.wasForgotten = wasForgotten; }
}
