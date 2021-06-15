package Controller;

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

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



}
