package Model;

import javax.swing.ImageIcon;

public class Card {
    private int id;
    private String frontText;
    private String backText;
    private ImageIcon frontImage;
    private ImageIcon backImage;
    //audiofiles for back and front
    private double dueTime;
    private boolean wasForgotten;

    public int getId() {
        return id;
    }
    public String getFrontText() {
        return frontText;
    }
    public String getBackText() {
        return backText;
    }
    public ImageIcon getFrontImage() {
        return frontImage;
    }
    public ImageIcon getBackImage() {
        return backImage;
    }
    public double getDueTime() {
        return dueTime;
    }
    public boolean isWasForgotten() {
        return wasForgotten;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setFrontText(String frontText) {
        this.frontText = frontText;
    }
    public void setBackText(String backText) {
        this.backText = backText;
    }
    public void setFrontImage(ImageIcon frontImage) {
        this.frontImage = frontImage;
    }
    public void setBackImage(ImageIcon backImage) {
        this.backImage = backImage;
    }
    public void setDueTime(double dueTime) {
        this.dueTime = dueTime;
    }
    public void setWasForgotten(boolean wasForgotten) {
        this.wasForgotten = wasForgotten;
    }
}
