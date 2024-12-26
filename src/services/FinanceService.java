package services;

import models.Transaction;
import models.TransactionType;
import models.User;
import models.Wallet;
import utils.Notifier;

import java.util.HashMap;
import java.util.Map;

public class FinanceService {

    private final Map<String, Double> categoryBudgets = new HashMap<>(); // Хранение бюджетов по категориям

    public void addTransaction(User user, String type, String category, double amount) {
        TransactionType transactionType = parseTransactionType(type);
        Wallet wallet = user.getWallets().get(0); // Используем первый кошелек как активный

        if (transactionType == TransactionType.EXPENSE) {
            Double categoryBudget = categoryBudgets.getOrDefault(category, Double.MAX_VALUE);

            double currentExpense = wallet.getTransactionsByCategory(category).stream()
                    .filter(t -> t.getType() == TransactionType.EXPENSE)
                    .mapToDouble(Transaction::getAmount)
                    .sum();

            if (currentExpense + amount > categoryBudget) {
                Notifier.notifyGeneral("Ошибка: превышение бюджета для категории '" + category + "'. Установленный лимит: " + categoryBudget + ", текущие расходы: " + currentExpense);
                return;
            }

            if (wallet.getBalance() < amount) {
                Notifier.notifyGeneral("Ошибка: недостаточно средств для выполнения этой транзакции. Текущий баланс: " + wallet.getBalance());
                return;
            }
        }

        wallet.addTransaction(new Transaction(category, amount, transactionType));

        if (transactionType == TransactionType.EXPENSE && wallet.getBalance() < 0) {
            Notifier.notifyLowBalance(wallet.getBalance());
        }
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

        report.append("\n=== Категории и бюджеты ===\n");
        for (Map.Entry<String, Double> entry : categoryBudgets.entrySet()) {
            String category = entry.getKey();
            double budget = entry.getValue();
            double currentExpense = wallet.getTransactionsByCategory(category).stream()
                    .filter(t -> t.getType() == TransactionType.EXPENSE)
                    .mapToDouble(Transaction::getAmount)
                    .sum();
            report.append("Категория: ").append(category).append(", Бюджет: ").append(budget)
                    .append(", Текущие расходы: ").append(currentExpense).append("\n");
        }

        return report.toString();
    }

    public void setBudget(User user, String category, double limit) {
        Wallet wallet = user.getWallets().get(0);
        double currentExpense = wallet.getTransactionsByCategory(category).stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();

        if (currentExpense > limit) {
            Notifier.notifyBudgetExceeded(category, limit, currentExpense);
            return;
        }

        categoryBudgets.put(category, limit);
        Notifier.notifyGeneral("Бюджет для категории '" + category + "' установлен: " + limit);
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
