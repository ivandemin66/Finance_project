package utils;

public class Notifier {

    public static void notifyBudgetExceeded(String category, double limit, double currentAmount) {
        System.out.println("Внимание: бюджет для категории '" + category + "' превышен. Лимит: " + limit + ", текущие расходы: " + currentAmount);
    }

    public static void notifyLowBalance(double balance) {
        System.out.println("Внимание: низкий баланс. Текущий баланс: " + balance);
    }

    public static void notifyGeneral(String message) {
        System.out.println("Уведомление: " + message);
    }
}
