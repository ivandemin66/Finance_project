package models;

public class Transaction {
    private String category;
    private double amount;
    private TransactionType type;

    public Transaction(String category, double amount, TransactionType type) {
        this.category = category;
        this.amount = amount;
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "category='" + category + '\'' +
                ", amount=" + amount +
                ", type=" + type +
                '}';
    }
}
