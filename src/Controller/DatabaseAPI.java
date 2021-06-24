package Controller;

import Model.Card;
import Model.Deck;
import Model.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseAPI {

    /** URI to database*/
    private static final String SQL_URI = "jdbc:mysql://remotemysql.com:3306/0BUZljxuwC";

    /** Username used for the Database. */
    private static final String SQL_USERNAME= "0BUZljxuwC";

    /** Password used for the Database. */
    private static final String SQL_PASSWORD = "YvvjZB8oLk";

    /** Used for connecting to the Database. */
    private static Connection con = null;

    /**
     * Build a connection to the application database.
     * If a connection already resides, that connection is used instead.
     *
     * @return <code>null</code> on failed connection, else return connection object
     */
    private static Connection connectDatabase() {
        try {
            if(con != null)
                return con;
            con = DriverManager.getConnection(SQL_URI, SQL_USERNAME, SQL_PASSWORD);
            return con;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Close an existing connection to the database. This function should be used
     * after every other database API function, as multiple unused connection may
     * reach cloud traffic limit.
     */
    public static void closeDatabase() {
        try {
            if (con != null) {
                con.close();
                con = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Verify if given username and pass correspond to a user in the database.
     *
     * @param username - String of username
     * @param password - String of <i>hashed<i> password
     * @return <code>true</code> if user exists
     * @throws SQLException in case of errors during queries.
     */
    public static boolean verifyUser(String username, String password) {
        Connection connection = connectDatabase();
        ResultSet userData = fetchUserData(connection, username);
        if(userData == null) {
            System.out.println("EMPTY DATASET");
            return false;
        }
        try {
            String saltHash = userData.getString("password");
            String passwordEncrpyted = PasswordEncryption.verify(password, saltHash);

            String sql = "SELECT * FROM User WHERE username = ? AND password = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, passwordEncrpyted);

            ResultSet result = statement.executeQuery();

            Boolean isUser = result.next();
            statement.close();
            // closeDatabase();
            System.out.println("Verified user.");
            return isUser;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Fetch user data from database. This is only called by other user related DB
     * functions.
     *
     * @param connection - SQL jdbc connection object, connection to DB
     * @param key        - used to find a certain user
     * @return SQL result of data entry or <code>null</code> if user doesn't exist
     */
    private static <T> ResultSet fetchUserData(Connection connection, T key) {
        String sqlColumn = "";

        if (key instanceof String)
            sqlColumn = "username";
        else
            sqlColumn = "id";

        String query = "SELECT * FROM User WHERE " + sqlColumn + " = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            if (key instanceof String) {
                String username = key.toString();
                statement.setString(1, username);
            } else if (key instanceof Integer) {
                Integer userId = ((Integer) key).intValue();
                statement.setInt(1, userId);
            }

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                System.out.println("Fetching user data.");
                return result;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
            return null;
        }
    }

    /**
     * Create table entry of a new user in database. Used for account creation.
     *
     * @param user - User object of new user
     * @return <code>true</code> on successful user creation
     */
    public static boolean storeUser(User user) {
        String sql = "INSERT INTO User(username, pw, email) VALUES(?,?,?)";
        Connection connection = connectDatabase();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.executeUpdate();

            statement.close();
            closeDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
            closeDatabase();
            return false;
        }
        System.out.println("Stored new user");
        return true;
    }

    public static User getUser(int key) {
        Connection connection = connectDatabase();
        ResultSet result = fetchUserData(connection, key);
        if(result == null) {
            closeDatabase();
            return null;
        }
        try {
            int id = result.getInt("id");
            String username = result.getString("username");
            String pw = result.getString("pw");
            String email = result.getString("email");
            ArrayList<Deck> decks = getDecksFromUser(id);

            User user = new User(id, username, pw, email, decks);
            closeDatabase();
            System.out.println("User data fetched");
            return user;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            closeDatabase();
            return null;
        }
    }

    public static boolean editUser(User user) {
        String update =  "UPDATE User SET username = ?, pw = ?, email = ? WHERE id = ?";
        Connection connection = connectDatabase();

        try {
            PreparedStatement statement = connection.prepareStatement(update);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setInt(4, user.getId());

            statement.executeUpdate();
            statement.close();
            closeDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
            closeDatabase();
            return false;
        }
        System.out.println("User edited");
        return true;
    }

    public static ArrayList<Deck> getDecksFromUser(int key) {
        String query = "SELECT Deck.* FROM Deck LEFT JOIN UserDeck ON UserDeck.id = Deck.id WHERE UserDeck.userID = ?";
        Connection connection = connectDatabase();
        ArrayList<Deck> decks = new ArrayList<Deck>();

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, key);
            ResultSet result = statement.executeQuery();

            while(result.next()) {
                int id = result.getInt("id");
                String deckname = result.getString("deckname");
                int numberOfCards = result.getInt("numberofCards");
                int due = result.getInt("due");
                int newCards = result.getInt("new");
                int again = result.getInt("again");
                double rating = result.getDouble("rating");
                //ArrayList<Card> cards = getCardsFromDeck(id);
                //String[] tags = getTagsFromDeck(id);

                //test
                Deck deck = new Deck(id, deckname, null, null, numberOfCards, due,
                        newCards, again, rating);
                decks.add(deck);
            }
            statement.close();
            closeDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
            closeDatabase();
            return null;
        }
        System.out.println("Fetched all Decks from User with id: " + key);
        return decks;
    }

    /**
     * Create a new entry in table Deck
     *
     * @param deck Deck object of new entry
     * @return deckID on successful insertion, -1 on failed insertion
     */
    public static int storeDeck(Deck deck) {
        String insert = "INSERT INTO Deck(deckname, numberOfCards, due , new, again, rating) VALUES(?,?,?,?,?,?)";
        Connection connection = connectDatabase();
        ResultSet result = null;
        int deckID;

        try {
            PreparedStatement statement = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,deck.getDeckName());
            statement.setInt(2, deck.getNumberOfCards());
            statement.setInt(3, deck.getDue());
            statement.setInt(4, deck.getNewCards());
            statement.setInt(5, deck.getAgain());
            statement.setDouble(6, deck.getRating());
            statement.executeUpdate();

            result = statement.getGeneratedKeys();
            if(result.next()) {
                deckID = result.getInt(1);
            } else {
                throw new SQLException("Creating deck failed, no ID obtained.");
            }
            statement.close();
            closeDatabase();
            System.out.println("Stored new Deck");
            return deckID;
        } catch (SQLException e) {
            e.printStackTrace();
            closeDatabase();
            return -1;
        }
    }


    /**
     * Creates entry in table UserDeck since the relation between table User and table Deck
     * is a many-to-many relation
     * @param userID user id the corresponding user
     * @param deckID deck id of corresponding deck
     * @return true if insertion successful, false if insertion failed
     */
    public static boolean createUserDeckBridge(int userID, int deckID) {
        String insert = "INSERT INTO UserDeck(userID, deckID) VALUES(?,?)";
        Connection connection = connectDatabase();

        try {
            PreparedStatement statement = connection.prepareStatement(insert);
            statement.setInt(1, userID);
            statement.setInt(2, deckID);
            statement.executeUpdate();
            statement.close();
            closeDatabase();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            closeDatabase();
            return false;
        }
    }


    /**
     * Gets all cards of a deck through deckID
     * @param deckID id of deck to which the cards belong to
     * @return list of all cards of a deck
     */
    public static ArrayList<Card> getCardsFromDeck(int deckID) {
        String query = "SELECT * FROM Card WHERE deckID = ?";
        Connection connection = connectDatabase();
        ResultSet result;
        ArrayList<Card> cards = new ArrayList<Card>();

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, deckID);
            result = statement.executeQuery();
            while(result.next()) {
                int id = result.getInt("id");
                String frontText = result.getString("front");
                String backText = result.getString("back");
                int dueTime = result.getInt("dueTime");
                boolean wasForgotten = result.getBoolean("wasForgotten");

                Card card = new Card(id, deckID, frontText, backText, dueTime, wasForgotten);
                cards.add(card);
            }
            statement.close();
            closeDatabase();
            System.out.println("Fetched all Cards from Deck with id " + deckID);
            return cards;
        } catch (SQLException e) {
            e.printStackTrace();
            closeDatabase();
            return null;
        }
    }

    /**
     * Creates a new entry in table Card
     *
     * @param card Card object which data is to be stored in the database
     * @return cardID on successful insertion, -1 on failed insertion
     */
    public static int storeCard(Card card) {
        String insert = "INSERT INTO Card(deckID, front, back, dueTime, wasForgotten) VALUES(?,?,?,?,?)";
        Connection connection = connectDatabase();
        ResultSet result;
        int cardID;

        try {
            PreparedStatement statement = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, card.getDeckID());
            statement.setString(2, card.getFrontText());
            statement.setString(3, card.getBackText());
            statement.setDouble(4, card.getDueTime());
            statement.setBoolean(5, card.getWasForgotten());
            statement.executeUpdate();

            result = statement.getGeneratedKeys();
            if(result.next()) {
                cardID = result.getInt(1);
            } else {
                throw new SQLException("Creating Card failed, no ID obtained.");
            }
            statement.close();
            closeDatabase();
            System.out.println("Stored new Card");
            return cardID;
        } catch (SQLException e) {
            e.printStackTrace();
            closeDatabase();
            return -1;
        }
    }

    /**
     * Updates table entry of a card if edited
     * @param card to be edited
     * @return true on successful update, false on failed update
     */
    public static boolean editCard(Card card) {
        String update =  "UPDATE Card SET deckID = ?, front = ?, back = ?, dueTime = ?, wasForgotten = ? WHERE id = ?";
        Connection connection = connectDatabase();

        try {
            PreparedStatement statement = connection.prepareStatement(update);
            statement.setInt(1, card.getDeckID());
            statement.setString(2, card.getFrontText());
            statement.setString(3, card.getBackText());
            statement.setDouble(4, card.getDueTime());
            statement.setBoolean(5, card.getWasForgotten());
            statement.setInt(6, card.getId());

            statement.executeUpdate();
            statement.close();
            closeDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
            closeDatabase();
            return false;
        }
        System.out.println("Card edited");
        return true;
    }

    /**
     * TODO
     * @param cardID
     * @return
     */
    public static ArrayList<File> getMediaFromCard(int cardID) {
        String query = "SELECT * FROM Media WHERE cardID = ?";
        Connection connection = connectDatabase();
        ArrayList<File> files = new ArrayList<>();
        InputStream input = null;
        FileOutputStream output = null;

        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1 ,  cardID);

            ResultSet result = statement.executeQuery();

            while(result.next()){
                File tempFile = new File(result.getString("filename"));
                output = new FileOutputStream(tempFile);
                input = result.getBinaryStream("file");

                byte[] buffer = new byte[1024];
                while (input.read(buffer) > 0){
                    output.write(buffer);
                }

                files.add(tempFile);

                input.close();
                output.close();
            }


            return files;
        } catch (SQLException | IOException e) {
            e.printStackTrace();

            return null;
        }

    }



    public static void main(String[] args) {

        //storeUser(new User("elon", "msuk", "gmail"));
//        User test = getUser(1);
//        for(Deck d : test.getDecks()) {
//            System.out.println(d.getDeckName());
//        }
        //System.out.println(test.getUsername());
        //editUser(new User(2, "marl", "on", "gmail"));
        //ArrayList<Deck> decks = getDecksFromUser(2);
        //for(Deck d : decks ) {
        //    System.out.println(d.getDeckName());
        //}
        //storeDeck(new Deck(5,"RTS", 35, 30,5,10,8));
        //createUserDeckBridge(1,5);
        //createUserDeckBridge(1,6);
        //storeCard(new Card(2,"Kraftwerk der Zelle", "Mitochondrien", 5, false));
        //storeCard(new Card(2, "Von DNA zum Protein", "Tanskription, mRNA-Prozessieurng, Translation", 3, true));
        //storeCard(new Card(2, "Welche Struktur hat die DNA?", "Doppelhelixstruktur", 3, false));
        //storeCard(new Card(3, "Spannung Einheit?", "Volt", 3, false));
        //for(Card c : getCardsFromDeck(3)) {
            //System.out.println(c.getFrontText());
            //System.out.println(c.getBackText());
        //}
        editCard(new Card(5, 3, "Widerstand Einheit?", "Ohm", 10, true));


        /*
        String path = System.getProperty("user.home") + File.separator + "Documents";
        path += File.separator + "Your Custom Folder";
        File customDir = new File(path);

        if (customDir.exists()) {
            System.out.println(customDir + " already exists");
        } else if (customDir.mkdirs()) {
            System.out.println(customDir + " was created");
        } else {
            System.out.println(customDir + " was not created");
        }

        Path file = new Path(path);

         */
    }



}
