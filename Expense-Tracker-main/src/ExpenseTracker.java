public class ExpenseTracker {
    public static void main(String[] args) {
        Database.createTable();
        CLIManager cliManager = new CLIManager();
        cliManager.showMenu();
    }
}
