package Model;

import javax.swing.ImageIcon;
import java.time.LocalDate;

public class Card {
    private int id;
    private int deckID;
    private String frontText;
    private String backText;
    private int correct;
    /*
    private ImageIcon frontImage;
    private ImageIcon backImage;
    //audiofiles for back and front
     */
    private int dueTime;
    private LocalDate dueDate;
    private boolean wasForgotten;
    private boolean isNew;

    /**
     * Constructor for creating card from users input
     */
    public Card(int deckID, String frontText, String backText, int correct, int dueTime, LocalDate dueDate, boolean wasForgotten, boolean isNew) {
        this.deckID = deckID;
        this.frontText = frontText;
        this.backText = backText;
        this.correct = correct;
        this.dueTime = dueTime;
        this.dueDate = dueDate;
        this.wasForgotten = wasForgotten;
        this.isNew = isNew;
    }

    /**
     * Constructor for fetching card data from database and creating model class from it
     */
    public Card(int id, int deckID, String frontText, String backText, int correct, int dueTime, LocalDate dueDate, boolean wasForgotten, boolean isNew) {
        this.id = id;
        this.deckID = deckID;
        this.frontText = frontText;
        this.backText = backText;
        this.correct = correct;
        this.dueTime = dueTime;
        this.dueDate = dueDate;
        this.wasForgotten = wasForgotten;
        this.isNew = isNew;
    }

    public int getId() { return id; }
    public int getDeckID() { return deckID; }
    public String getFrontText() { return frontText; }
    public String getBackText() { return backText; }
    //public ImageIcon getFrontImage() { return frontImage; }
    //public ImageIcon getBackImage() { return backImage; }
    public int getCorrect() { return correct; }
    public int getDueTime() { return dueTime; }
    public LocalDate getDueDate() { return dueDate;}
    public boolean getWasForgotten() { return wasForgotten; }
    public boolean getIsNew() { return isNew; }

    public void setId(int id) { this.id = id; }
    public void setDeckID(int deckID) { this.deckID = deckID; }
    public void setFrontText(String frontText) { this.frontText = frontText; }
    public void setBackText(String backText) { this.backText = backText; }
    //public void setFrontImage(ImageIcon frontImage) { this.frontImage = frontImage; }
    //public void setBackImage(ImageIcon backImage) { this.backImage = backImage; }
    public void setCorrect(int correct) { this.correct = correct; }
    public void setDueTime(int dueTime) { this.dueTime = dueTime; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate;};
    public void setWasForgotten(boolean wasForgotten) { this.wasForgotten = wasForgotten; }
    public void setIsNew(boolean isNew) { this.isNew = isNew; }

}
