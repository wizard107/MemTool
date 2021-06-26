package Model;

import javax.swing.*;
import java.util.ArrayList;

public class User {
    /** id of user */
    private int id;

    /** Username of user */
    private String username;

    // brauchen wir firstname und lastname?

    /** Firstname of user */
    private String firstname;

    /** Lastname of user */
    private String lastname;

    /** Password of user */
    private String password;

    /** Email of user */
    private String email;

    /** List of events which the user is partaking*/
    private ArrayList<Deck> decks = new ArrayList<Deck>();

    /** Boolean to determine if user is Admin */
    private Boolean isAdmin;

    /** icon or rather the avatar of user */
    //private ImageIcon avatar = MasterUI.avatarImage2;

    /**
     * Constructor to create a user after registration but before storing
     * database
     */
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.decks = new ArrayList<Deck>();
        this.isAdmin = false;
    }

    /**
     * Constructor after account creation and before storing to database
     */
    public User(int id, String username, String password, String email, ArrayList<Deck> decks) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.decks = decks;
        this.isAdmin = false;
    }

    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getFirstname() { return firstname; }
    public String getLastname() {
        return lastname;
    }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }
    public ArrayList<Deck> getDecks() {
        return decks;
    }
    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setDecks(ArrayList<Deck> decks) {
        this.decks = decks;
    }
    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
    public Deck getMainDeck(int i){
        return decks.get(i);
    }
    public void setMainDeck(int i, Deck deck){
        decks.set(i, deck);
    }
    public void initializePositions(){
        int count = 0;
        for(Deck deck: decks){
            deck.setDeckPosition(count++);
            System.out.println(deck.getDeckName());
            int countCards=0;
            for(Card card : deck.getCards()){
                card.setCardPosition(countCards++);
            }
        }
    }
    public void initializeDeckPositions(){
        int count = 0;
        for(Deck deck: decks){
            deck.setDeckPosition(count++);
        }
    }
    public void initializeCardPositions(int i){
        int count = 0;
        for(Card card: decks.get(i).getCards()){
            card.setCardPosition(count++);
        }
    }
}
