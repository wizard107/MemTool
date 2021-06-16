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

    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getFirstname() {
        return firstname;
    }
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
}
