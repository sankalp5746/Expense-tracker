
public class Expense {
    private int id;
    private double amount;
    private String category;
    private String date;
    private String description;

    public Expense(int id, double amount, String category, String date, String description) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.description = description;
    }

    public int getId() { return id; }
    public double getAmount() { return amount; }
    public String getCategory() { return category; }
    public String getDate() { return date; }
    public String getDescription() { return description; }

    public void setId(int id) { this.id = id; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setCategory(String category) { this.category = category; }
    public void setDate(String date) { this.date = date; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "ID: " + id + ", Amount: " + amount + ", Category: " + category + ", Date: " + date + ", Description: " + description;
    }
}

