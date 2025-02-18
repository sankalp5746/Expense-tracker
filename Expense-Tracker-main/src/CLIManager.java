import java.util.List;
import java.util.Scanner;

public class CLIManager {
    private ExpenseDAO expenseDAO;
    private Scanner scanner;

    public CLIManager() {
        expenseDAO = new ExpenseDAO();
        scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n================ Expense Tracker =================");
            System.out.println("1 Add Expense");
            System.out.println("2 View All Expenses");
            System.out.println("3 Delete Expense");
            System.out.println("4 Edit an Expense");
            System.out.println("5 View Expenses by Category");
            System.out.println("6 View Total Monthly Expense");
            System.out.println("7 View Monthly Expense Records");
            System.out.println("8 View Total Expenses (All Time)");
            System.out.println("9 Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addExpense();
                    break;
                case 2:
                    viewExpenses();
                    break;
                case 3:
                    deleteExpense();
                    break;
                case 4:
                    editExpense();
                    break;
                case 5:
                    viewExpensesByCategory();
                    break;
                case 6:
                    viewMonthlyExpenseSummary();
                    break;
                case 7:
                    viewMonthlyExpenseRecords();
                    break;
                case 8:
                    viewTotalExpenses();
                    break;
                case 9:
                    System.out.println("Exiting... Thank you!");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void addExpense() {
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter category: ");
        String category = scanner.nextLine();

        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();

        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        Expense expense = new Expense(0, amount, category, date, description);
        expenseDAO.addExpense(expense);
        System.out.println(" Expense added successfully!");
    }

    private void viewExpenses() {
        List<Expense> expenses = expenseDAO.getAllExpenses();
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
        } else {
            System.out.println("\n  All Expenses:");
            for (Expense exp : expenses) {
                System.out.println(exp);
            }
        }
    }

    private void deleteExpense() {
        System.out.print("Enter Expense ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        expenseDAO.deleteExpense(id);
        System.out.println(" Expense deleted.");
    }

    private void editExpense() {
        System.out.print("Enter Expense ID to edit: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Fetch existing record to modify only selected fields
        Expense existingExpense = expenseDAO.getExpenseById(id);
        if (existingExpense == null) {
            System.out.println(" Expense ID not found!");
            return;
        }

        while (true) {
            System.out.println("\n===== Select Field to Edit =====");
            System.out.println("1 Amount");
            System.out.println("2 Category");
            System.out.println("3 Date");
            System.out.println("4 Description");
            System.out.println("5 Done (Exit Editing)");
            System.out.print("Choose an option: ");

            int opt = scanner.nextInt();
            scanner.nextLine();

            switch (opt) {
                case 1:
                    System.out.print("Enter new amount: ");
                    double newAmount = scanner.nextDouble();
                    scanner.nextLine();
                    expenseDAO.updateSingleField(id, "amount", newAmount);
                    break;
                case 2:
                    System.out.print("Enter new category: ");
                    String newCategory = scanner.nextLine();
                    expenseDAO.updateSingleField(id, "category", newCategory);
                    break;
                case 3:
                    System.out.print("Enter new date (YYYY-MM-DD): ");
                    String newDate = scanner.nextLine();
                    expenseDAO.updateSingleField(id, "date", newDate);
                    break;
                case 4:
                    System.out.print("Enter new description: ");
                    String newDescription = scanner.nextLine();
                    expenseDAO.updateSingleField(id, "description", newDescription);
                    break;
                case 5:
                    System.out.println(" Editing complete. Returning to menu.");
                    return; // Exit loop
                default:
                    System.out.println(" Invalid option. Try again.");
            }

            System.out.println(" Field updated successfully!");
        }
    }

    private void viewExpensesByCategory() {
        System.out.print("Enter category: ");
        String category = scanner.nextLine();

        List<Expense> expenses = expenseDAO.getExpensesByCategory(category);
        if (expenses.isEmpty()) {
            System.out.println(" No expenses found in this category.");
        } else {
            System.out.println("\n  Expenses in category '" + category + "':");
            for (Expense exp : expenses) {
                System.out.println(exp);
            }
        }
    }

    private void viewMonthlyExpenseSummary() {
        System.out.print("Enter month (MM): ");
        String month = scanner.nextLine();
        System.out.print("Enter year (YYYY): ");
        String year = scanner.nextLine();

        double totalExpense = expenseDAO.getMonthlyExpenseSummary(month, year);
        System.out.println(" Total expense for " + month + "/" + year + ": ₹" + totalExpense);
    }

    private void viewMonthlyExpenseRecords() {
        System.out.print("Enter month (MM): ");
        String month = scanner.nextLine();
        System.out.print("Enter year (YYYY): ");
        String year = scanner.nextLine();

        List<Expense> expenses = expenseDAO.getMonthlyExpenseRecords(month, year);
        if (expenses.isEmpty()) {
            System.out.println(" No expenses recorded for this month.");
        } else {
            System.out.println("\n  Expense Records for " + month + "/" + year + ":");
            for (Expense exp : expenses) {
                System.out.println(exp);
            }
        }
    }

    private void viewTotalExpenses() {
        double total = expenseDAO.getTotalExpenses();
        System.out.println("\n Total expenses (all time): ₹" + total);
    }
}
