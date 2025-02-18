import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO {
    private static final String URL = "jdbc:sqlite:expenses.db";

    private Connection connect() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(URL);
        } catch (Exception e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
        return conn;
    }


    public void addExpense(Expense expense) {
        String sql = "INSERT INTO expenses (amount, category, date, description) VALUES (?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, expense.getAmount());
            pstmt.setString(2, expense.getCategory());
            pstmt.setString(3, expense.getDate());
            pstmt.setString(4, expense.getDescription());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding expense: " + e.getMessage());
        }
    }

    public void updateExpense(Expense expense) {
        String sql = "UPDATE expenses SET amount = ?, category = ?, date = ?, description = ? WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, expense.getAmount());
            pstmt.setString(2, expense.getCategory());
            pstmt.setString(3, expense.getDate());
            pstmt.setString(4, expense.getDescription());
            pstmt.setInt(5, expense.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating expense: " + e.getMessage());
        }
    }

    public void updateSingleField(int id, String column, Object newValue) {
        String sql = "UPDATE expenses SET " + column + " = ? WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (newValue instanceof String) {
                pstmt.setString(1, (String) newValue);
            } else if (newValue instanceof Double) {
                pstmt.setDouble(1, (Double) newValue);
            }
            pstmt.setInt(2, id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating expense: " + e.getMessage());
        }
    }

    public List<Expense> getExpensesByCategory(String category) {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT * FROM expenses WHERE category = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, category);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                expenses.add(new Expense(
                        rs.getInt("id"),
                        rs.getDouble("amount"),
                        rs.getString("category"),
                        rs.getString("date"),
                        rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching expenses by category: " + e.getMessage());
        }
        return expenses;
    }

    public Expense getExpenseById(int id) {
        String sql = "SELECT * FROM expenses WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Expense(
                        rs.getInt("id"),
                        rs.getDouble("amount"),
                        rs.getString("category"),
                        rs.getString("date"),
                        rs.getString("description")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error fetching expense: " + e.getMessage());
        }
        return null;
    }





    public List<Expense> getAllExpenses() {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT * FROM expenses";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                expenses.add(new Expense(
                        rs.getInt("id"),
                        rs.getDouble("amount"),
                        rs.getString("category"),
                        rs.getString("date"),
                        rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching expenses: " + e.getMessage());
        }
        return expenses;
    }


    public double getMonthlyExpenseSummary(String month, String year) {
        String sql = "SELECT SUM(amount) FROM expenses WHERE date LIKE ?";
        double total = 0;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, year + "-" + month + "%");
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching monthly expense summary: " + e.getMessage());
        }
        return total;
    }


    public List<Expense> getMonthlyExpenseRecords(String month, String year) {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT * FROM expenses WHERE date LIKE ? ORDER BY date";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, year + "-" + month + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                expenses.add(new Expense(
                        rs.getInt("id"),
                        rs.getDouble("amount"),
                        rs.getString("category"),
                        rs.getString("date"),
                        rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching monthly expense records: " + e.getMessage());
        }
        return expenses;
    }


    public double getTotalExpenses() {
        String sql = "SELECT SUM(amount) FROM expenses";
        double total = 0;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching total expenses: " + e.getMessage());
        }
        return total;
    }


    public void deleteExpense(int id) {
        String sql = "DELETE FROM expenses WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting expense: " + e.getMessage());
        }
    }
}
