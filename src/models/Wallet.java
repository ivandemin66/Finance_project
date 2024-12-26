package models;

import java.util.ArrayList;
import java.util.List;

public class Wallet {
    private String name;
    private double balance;
    private List<Transaction> transactions;

    public Wallet(String name) {
        this.name = name;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        if (transaction.getType() == TransactionType.INCOME) {
            balance += transaction.getAmount();
        } else {
            balance -= transaction.getAmount();
        }
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public List<Transaction> getTransactionsByCategory(String category) {
        List<Transaction> filtered = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getCategory().equalsIgnoreCase(category)) {
                filtered.add(t);
            }
        }
        return filtered;
    }

    public double calculateTotalByType(TransactionType type) {
        return transactions.stream()
                .filter(t -> t.getType() == type)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "name='" + name + '\'' +
                ", balance=" + balance +
                ", transactions=" + transactions +
                '}';
    }
}
