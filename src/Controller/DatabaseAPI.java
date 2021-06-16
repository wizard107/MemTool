package Controller;

import java.io.File;
import java.nio.file.Path;
import java.sql.*;

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
            sqlColumn = "user_id";

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

    public static void main(String[] args) {
        try {
            connectDatabase();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM testtable");

            while(resultSet.next()) {
                System.out.println(resultSet.getString("firstname"));
            }
            statement.close();
            closeDatabase();

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
