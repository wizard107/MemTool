package Controller;

import Model.Card;
import Model.Deck;
import Model.User;

import java.io.*;
import java.nio.file.Path;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     *
     * @return <code>null</code> on failed connection, else return connection object
     */
    private static Connection connectDatabase() {
        try {
            Connection connect;
            connect = DriverManager.getConnection(SQL_URI, SQL_USERNAME, SQL_PASSWORD);
            return connect;
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
    public static void closeDatabase(Connection connect) {
        try {
            if (connect != null) {
                connect.close();
                connect = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Verify if given username and pass correspond to a user in the database.
     *
     * @param username - String of username
     * @param pw - String of <i>hashed<i> password
     * @return <code>true</code> if user exists
     * @throws SQLException in case of errors during queries.
     */
    public static boolean verifyUser(String username, String pw) {
        Connection connection = connectDatabase();
        ResultSet userData = fetchUserData(connection, username);
        if(userData == null) {
            System.out.println("EMPTY DATASET");
            return false;
        }
        try {
            String saltHash = userData.getString("pw");
            String passwordEncrpyted = PasswordEncryption.verify(pw, saltHash);

            String sql = "SELECT * FROM User WHERE username = ? AND pw = ?";

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
            System.out.println("verifyUser");
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
            System.out.println("fetchUser");
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
            closeDatabase(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            closeDatabase(connection);
            return false;
        }
        System.out.println("Stored new user");
        return true;
    }

    public static <T> User getUser(T key) {
        Connection connection = connectDatabase();
        ResultSet result = fetchUserData(connection, key);
        if(result == null) {
            closeDatabase(connection);
            return null;
        }
        try {
            int id = result.getInt("id");
            String username = result.getString("username");
            String pw = result.getString("pw");
            String email = result.getString("email");
            ArrayList<Deck> decks = getDecksFromUser(id);

            User user = new User(id, username, pw, email, decks);
            closeDatabase(connection);
            System.out.println("User data fetched");
            return user;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            closeDatabase(connection);
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
            closeDatabase(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            closeDatabase(connection);
            return false;
        }
        System.out.println("User edited");
        return true;
    }

    /**
     * Deletes user in the table User and user's corresponding entries
     * table UserDeck since UserDeck contains userID as foreign key
     * which ist set to delete on cascade
     * @param userID Id of user to be deleted
     * @return true on successful deletion, false on failed deletion
     */
    public static boolean deleteUser(int userID) {
        String delete = "DELETE FROM User WHERE id = ?";
        Connection connection = connectDatabase();

        try {
            /*
            ArrayList<Deck> decks = getDecksFromUser(userID);
            for(Deck d : decks) {
                deleteDeck(d.getId());
            }*/
            PreparedStatement statement = connection.prepareStatement(delete);
            statement.setInt(1, userID);
            statement.executeUpdate();
            statement.close();
            closeDatabase(connection);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            closeDatabase(connection);
            return false;
        }
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
                int deckID = result.getInt("id");
                String deckname = result.getString("deckname");
                int numberOfCards = result.getInt("numberofCards");
                int due = result.getInt("due");
                int newCards = result.getInt("new");
                int again = result.getInt("again");
                double rating = result.getDouble("rating");
                ArrayList<Card> cards = getCardsFromDeck(deckID);
                Map<Integer, String> tags = getTagsFromDeck(deckID);

                //test
                Deck deck = new Deck(deckID, deckname, cards, tags, numberOfCards, due,
                        newCards, again, rating);
                decks.add(deck);
            }
            if(!connection.isClosed()) {
                statement.close();
                closeDatabase(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            closeDatabase(connection);
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
            closeDatabase(connection);
            System.out.println("Stored new Deck");
            return deckID;
        } catch (SQLException e) {
            e.printStackTrace();
            closeDatabase(connection);
            return -1;
        }
    }

    public static boolean editDeck(Deck deck) {
        String update =  "UPDATE Deck SET deckname = ?, numberOfCards = ?, due = ?, new = ?, again = ?, rating = ? WHERE id = ?";
        Connection connection = connectDatabase();

        try {
            PreparedStatement statement = connection.prepareStatement(update);
            statement.setString(1, deck.getDeckName());
            statement.setInt(2, deck.getNumberOfCards());
            statement.setInt(3, deck.getDue());
            statement.setInt(4, deck.getNewCards());
            statement.setInt(5, deck.getAgain());
            statement.setDouble(6, deck.getRating());
            statement.setInt(6, deck.getId());

            statement.executeUpdate();
            statement.close();
            closeDatabase(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            closeDatabase(connection);
            return false;
        }
        System.out.println("Deck edited");
        return true;
    }

    /**
     * Delete deck in table Deck and in deck's corresponding entries
     * in table Card, table UserDeck and table DeckTag
     * @param deckID Id of deck to be deleted
     * @return true on successful deletion, false on failed deletion
     */
    public static boolean deleteDeck(int deckID) {
        String delete = "DELETE FROM Deck WHERE id = ?";
        Connection connection = connectDatabase();

        try {
            PreparedStatement statement = connection.prepareStatement(delete);
            statement.setInt(1, deckID);
            statement.executeUpdate();
            statement.close();
            closeDatabase(connection);
            System.out.println("Deck with id " + deckID + " deleted");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            closeDatabase(connection);
            return false;
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
            closeDatabase(connection);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            closeDatabase(connection);
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
                int correct = result.getInt("correct");
                int dueTime = result.getInt("dueTime");
                LocalDate dueDate = result.getDate("dueDate").toLocalDate();
                boolean wasForgotten = result.getBoolean("wasForgotten");
                boolean isNew = result.getBoolean("isNew");

                Card card = new Card(id, deckID, frontText, backText, correct, dueTime, dueDate, wasForgotten, isNew);
                cards.add(card);
            }
            statement.close();
            closeDatabase(connection);
            System.out.println("Fetched all Cards from Deck with id " + deckID);
            return cards;
        } catch (SQLException e) {
            e.printStackTrace();
            closeDatabase(connection);
            return null;
        }
    }

    /**
     * Get all related tags/topics from a deck
     * @param deckID
     * @return list of tags as strings
     */
    public static Map<Integer,String> getTagsFromDeck(int deckID) {
        String query = "SELECT Tag.* FROM Tag LEFT JOIN DeckTag ON DeckTag.tagID = Tag.id WHERE DeckTag.deckID = ?";
        Connection connection = connectDatabase();
        Map<Integer, String>  tags = new HashMap<Integer, String>();
        //ArrayList<String> tags = new ArrayList<String>();

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, deckID);
            ResultSet result = statement.executeQuery();

            while(result.next()) {
                int tagID = result.getInt("id");
                String tag = result.getString("topic");
                tags.put(tagID, tag);
            }
            statement.close();
            closeDatabase(connection);
            System.out.println("Fetched all Tags from Deck with id: " + deckID);
            return tags;
        } catch (SQLException e) {
            e.printStackTrace();
            closeDatabase(connection);
            return null;
        }
    }

    /**
     * Creates a new entry in table Tag
     *
     * @param tag to the deck related tag as string to be stored
     * @return tagID on successful insertion, -1 on failed insertion
     */
    public static int storeTag(String tag) {
        String insert = "INSERT INTO Tag(topic) VALUES(?)";
        Connection connection = connectDatabase();
        ResultSet result;
        int tagID;

        try {
            PreparedStatement statement = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, tag);
            statement.executeUpdate();

            result = statement.getGeneratedKeys();
            if(result.next()) {
                tagID = result.getInt(1);
            } else {
                throw new SQLException("Creating Tag failed, no ID obtained.");
            }
            statement.close();
            closeDatabase(connection);
            System.out.println("Stored new tag");
            return tagID;
        } catch (SQLException e) {
            e.printStackTrace();
            closeDatabase(connection);
            return -1;
        }
    }

    /**
     * Edits a tag
     * @param topic new topic
     * @param tagID id of tag to be edited
     * @return true if edit was successful, false on failed update
     */
    public static boolean editTag(String topic, int tagID) {
        String update =  "UPDATE Tag SET topic = ? WHERE id = ?";
        Connection connection = connectDatabase();

        try {
            PreparedStatement statement = connection.prepareStatement(update);
            statement.setString(1, topic);
            statement.setInt(2, tagID);

            statement.executeUpdate();
            statement.close();
            closeDatabase(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            closeDatabase(connection);
            return false;
        }
        System.out.println("Tag edited");
        return true;
    }

    /**
     * Delete tag in table Tag and in deck's corresponding entries
     * in table DeckTag
     * @param tagID Id of deck to be deleted
     * @return true on successful deletion, false on failed deletion
     */
    public static boolean deleteTag(int tagID) {
        String delete = "DELETE FROM Tag WHERE id = ?";
        Connection connection = connectDatabase();

        try {
            PreparedStatement statement = connection.prepareStatement(delete);
            statement.setInt(1, tagID);
            statement.executeUpdate();
            statement.close();
            closeDatabase(connection);
            System.out.println("Tag with id " + tagID + " deleted");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            closeDatabase(connection);
            return false;
        }
    }

    /**
     * Creates entry in table DeckTag since the relation between table Deck and table tag
     * is a many-to-many relation
     * @param deckID deck id the corresponding user
     * @param tagID tag id of corresponding deck
     * @return true if insertion successful, false if insertion failed
     */
    public static boolean createDeckTagBridge(int deckID, int tagID) {
        String insert = "INSERT INTO DeckTag(deckID, tagID) VALUES(?,?)";
        Connection connection = connectDatabase();

        try {
            PreparedStatement statement = connection.prepareStatement(insert);
            statement.setInt(1, deckID);
            statement.setInt(2, tagID);
            statement.executeUpdate();
            statement.close();
            closeDatabase(connection);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            closeDatabase(connection);
            return false;
        }
    }


    /**
     * Creates a new entry in table Card
     *
     * @param card Card object which data is to be stored in the database
     * @return cardID on successful insertion, -1 on failed insertion
     */
    public static int storeCard(Card card) {
        String insert = "INSERT INTO Card(deckID, front, back, correct, dueTime, dueDate, wasForgotten, isNew) VALUES(?,?,?,?,?,?,?,?)";
        Connection connection = connectDatabase();
        ResultSet result;
        int cardID;

        try {
            PreparedStatement statement = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, card.getDeckID());
            statement.setString(2, card.getFrontText());
            statement.setString(3, card.getBackText());
            statement.setInt(4, card.getCorrect());
            statement.setInt(5, card.getDueTime());
            statement.setDate(6, Date.valueOf(card.getDueDate()));
            statement.setBoolean(7, card.getWasForgotten());
            statement.setBoolean(8, card.getIsNew());
            statement.executeUpdate();

            result = statement.getGeneratedKeys();
            if(result.next()) {
                cardID = result.getInt(1);
            } else {
                throw new SQLException("Creating Card failed, no ID obtained.");
            }
            statement.close();
            closeDatabase(connection);
            System.out.println("Stored new Card");
            return cardID;
        } catch (SQLException e) {
            e.printStackTrace();
            closeDatabase(connection);
            return -1;
        }
    }

    /**
     * Updates table entry of a card if edited
     * @param card to be edited
     * @return true on successful update, false on failed update
     */
    public static boolean editCard(Card card) {
        String update =  "UPDATE Card SET deckID = ?, front = ?, back = ?, correct = ?, dueTime = ?, dueDate = ?, wasForgotten = ?, isNew = ? WHERE id = ?";
        Connection connection = connectDatabase();

        try {
            PreparedStatement statement = connection.prepareStatement(update);
            statement.setInt(1, card.getDeckID());
            statement.setString(2, card.getFrontText());
            statement.setString(3, card.getBackText());
            statement.setInt(4, card.getCorrect());
            statement.setInt(5, card.getDueTime());
            statement.setDate(6, Date.valueOf(card.getDueDate()));
            statement.setBoolean(7, card.getWasForgotten());
            statement.setBoolean(8, card.getIsNew());
            statement.setInt(9, card.getId());

            statement.executeUpdate();
            statement.close();
            closeDatabase(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            closeDatabase(connection);
            return false;
        }
        System.out.println("Card edited");
        return true;
    }

    /**
     * Delete card in table Card and in card's corresponding entries
     * in table Media
     * @param cardID Id of deck to be deleted
     * @return true on successful deletion, false on failed deletion
     */
    public static boolean deleteCard(int cardID) {
        String delete = "DELETE FROM Card WHERE id = ?";
        Connection connection = connectDatabase();

        try {
            PreparedStatement statement = connection.prepareStatement(delete);
            statement.setInt(1, cardID);
            statement.executeUpdate();
            statement.close();
            closeDatabase(connection);
            System.out.println("Card with id " + cardID + " deleted");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            closeDatabase(connection);
            return false;
        }
    }

    /**
     * Gets the media files attached to a card out of the datatbase
     * @param cardID id of a card from which the attached files should be returned
     * @return list of files
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
            statement.close();
            closeDatabase(connection);
            return files;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            closeDatabase(connection);
            return null;
        }

    }


    /**
     * Adds attachment entry into the Database.
     *
     * @param file File to be uploaded into the database
     * @param cardID Id of card to which the file belongs to
     * @return -1 on failed creation, ID on successful creation
     */
    public static int storeMedia(File file, int cardID) {
        String sql = "INSERT INTO Media(cardID, file, filename, filepath) VALUES (?,?,?,?)";
        Connection connection = connectDatabase();
        int attachmentId = -1;
        try{
            PreparedStatement statement = connection.prepareStatement(sql , Statement.RETURN_GENERATED_KEYS);
            FileInputStream input = new FileInputStream(file);
            statement.setInt(1 , cardID);
            statement.setBinaryStream(2 , input);
            statement.setString(3, file.getName());
            statement.setString(4, file.getPath());

            statement.executeUpdate();
            ResultSet generatedKey = statement.getGeneratedKeys();

            if (generatedKey.next()) {
                attachmentId = generatedKey.getInt(1);
            } else {
                throw new SQLException("Creating media file failed, no ID obtained.");
            }

            input.close();
            statement.close();
            closeDatabase(connection);
            System.out.println("Media file stored.");
            return attachmentId;

        } catch (SQLException e){
            e.printStackTrace();
            closeDatabase(connection);
            return attachmentId;
        } catch (IOException e) {
            e.printStackTrace();
            closeDatabase(connection);
            return attachmentId;
        }
    }

    /**
     * Delete media/file in table Media
     * @param mediaID Id of deck to be deleted
     * @return true on successful deletion, false on failed deletion
     */
    public static boolean deleteMedia(int mediaID) {
        String delete = "DELETE FROM Media WHERE id = ?";
        Connection connection = connectDatabase();

        try {
            PreparedStatement statement = connection.prepareStatement(delete);
            statement.setInt(1, mediaID);
            statement.executeUpdate();
            statement.close();
            closeDatabase(connection);
            System.out.println("media with id " + mediaID + " deleted");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            closeDatabase(connection);
            return false;
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
        //editCard(new Card(5, 3, "Widerstand Einheit?", "Ohm", 10, true));
        //storeTag("Biologie");
        //storeTag("Abitur");
        //storeTag("Studium");
        //createDeckTagBridge(1,3);
        //createDeckTagBridge(2,1);
        //createDeckTagBridge(2,2);
        //createDeckTagBridge(3,2);
        //createDeckTagBridge(4,3);

        //DELETE DECK TEST
        //storeDeck(new Deck("Testdeck", 10, 10, 0, 0, 0));
        //storeCard(new Card(7,"test", "back", 4, false));
        //storeCard(new Card(7,"test", "back", 4, false));
        //storeCard(new Card(7,"test", "back", 4, false));
        //deleteDeck(7);

        //DELETE CARD TEST
        //storeCard(new Card(4,"test", "back", 4, false));
        //storeMedia(new File("C:/Users/User/Desktop/Schule/bio/mutationen.jpg"), 9);
        //deleteCard(9);


        //getTags of deck TEST
        //ArrayList<Deck> decks = getDecksFromUser(1);
        //for(Deck d : decks) {
            //System.out.println(d.getDeckName() + ": ");
            //Map<Integer, String> map = d.getTags();
            //for(String s : map.values()) {
                //System.out.println(s);
            //}
        //}

        //File example = new File("C:/Users/User/Desktop/Schule/bio/mutationen.jpg");
        //storeMedia(example, 3);
        //ArrayList<File> files = getMediaFromCard(3);
        //for(File f : files )
            //System.out.println(f.getAbsolutePath());

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
