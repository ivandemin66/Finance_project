package services;

import models.Transaction;
import models.TransactionType;
import models.User;
import models.Wallet;

public class FinanceService {

    public void addTransaction(User user, String type, String category, double amount) {
        TransactionType transactionType = parseTransactionType(type);
        Transaction transaction = new Transaction(category, amount, transactionType);
        user.getWallets().get(0).addTransaction(transaction); // Используем первый кошелек как активный
    }

    public String generateReport(User user) {
        Wallet wallet = user.getWallets().get(0); // Предполагаем использование первого кошелька
        double totalIncome = wallet.calculateTotalByType(TransactionType.INCOME);
        double totalExpense = wallet.calculateTotalByType(TransactionType.EXPENSE);

        StringBuilder report = new StringBuilder();
        report.append("=== Финансовый отчет ===\n");
        report.append("Доходы: ").append(totalIncome).append("\n");
        report.append("Расходы: ").append(totalExpense).append("\n");
        report.append("Баланс: ").append(wallet.getBalance()).append("\n");

        return report.toString();
    }

    public void setBudget(User user, String category, double limit) {
        Wallet wallet = user.getWallets().get(0);
        Transaction budgetTransaction = new Transaction(category, limit, TransactionType.EXPENSE);
        wallet.addTransaction(budgetTransaction); // Условно считаем бюджет расходом для управления лимитами
    }

    private TransactionType parseTransactionType(String type) {
        if (type.equalsIgnoreCase("доход")) {
            return TransactionType.INCOME;
        } else if (type.equalsIgnoreCase("расход")) {
            return TransactionType.EXPENSE;
        } else {
            throw new IllegalArgumentException("Неизвестный тип транзакции: " + type);
        }
    }
}
