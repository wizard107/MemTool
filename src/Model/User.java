package Model;

import Controller.DatabaseAPI;

import javax.swing.*;
import java.util.ArrayList;

public class User {
    /** id of user */
    private int id;

    /** Username of user */
    private String username;

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
    public int[] calcCardsLearned(){
        int[] calc = new int[2];
        int countNew = 0;
        int countTotal = 0;
        for(Deck deck: decks){
            for(Card card: deck.getCards()){
                countTotal++;
                if(card.getIsNew()||card.getWasForgotten()) countNew++;
            }
        }
        calc[0] = countTotal - countNew;
        calc[1] = countNew;
        return calc;
    }
    /**
     * Updates the local list of events from the database
     */
    public void updateDeckList(){
        decks.clear();
        decks.addAll(DatabaseAPI.getDecksFromUser(this.getId()));
        initializeDeckPositions();
    }
    /**
     * Removes Deck from user
     */
    public void deleteDeck(Deck deck) {
        DatabaseAPI.deleteDeck(deck.getId());
        updateDeckList();
    }

    public void editDeck(Deck deck) {
        DatabaseAPI.editDeck(deck);
        updateDeckList();
    }
}
