import java.sql.*;

public class Database {
    private static final String URL = "jdbc:sqlite:expenses.db";

    public static Connection connect() {
        Connection conn = null;
        try {

            Class.forName("org.sqlite.JDBC");

            conn = DriverManager.getConnection(URL);
            System.out.println(" Database connected successfully.");
        } catch (ClassNotFoundException e) {
            System.out.println(" SQLite JDBC driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println(" Database connection error: " + e.getMessage());
        }
        return conn;
    }

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS expenses (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "amount REAL NOT NULL, " +
                "category TEXT NOT NULL, " +
                "date TEXT NOT NULL, " +
                "description TEXT)";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            if (conn != null) {
                stmt.execute(sql);
                System.out.println(" Table created successfully.");
            } else {
                System.out.println(" Database connection failed.");
            }
        } catch (SQLException e) {
            System.out.println(" Table creation error: " + e.getMessage());
        }
    }





    public static void main(String[] args) {
        createTable();
    }
}
