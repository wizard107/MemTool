package Controller;

import Model.Deck;
import Model.User;

import java.io.File;
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


    public static void main(String[] args) {

        //storeUser(new User("elon", "msuk", "gmail"));
        User test = getUser(1);
        for(Deck d : test.getDecks()) {
            System.out.println(d.getDeckName());
        }
        //System.out.println(test.getUsername());
        //editUser(new User(2, "marl", "on", "gmail"));
        //ArrayList<Deck> decks = getDecksFromUser(2);
        //for(Deck d : decks ) {
        //    System.out.println(d.getDeckName());
        //}


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
