package models;

import services.AuthService;
import services.FinanceService;
import utils.InputValidator;


import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AuthService authService = new AuthService();
        FinanceService financeService = new FinanceService();

        User currentUser = null;
        System.out.println("Добро пожаловать в приложение управления личными финансами!");

        while (true) {
            if (currentUser == null) {
                System.out.println("\nДоступные команды: 1. Регистрация, 2. Вход, 3. Выход");
                System.out.print("Введите номер команды: ");
                String command = scanner.nextLine().trim();

                switch (command) {
                    case "1":
                        System.out.print("Введите имя пользователя: ");
                        String username = scanner.nextLine().trim();
                        System.out.print("Введите пароль: ");
                        String password = scanner.nextLine().trim();

                        if (!InputValidator.validateUsername(username) || !InputValidator.validatePassword(password)) {
                            System.out.println("Ошибка: Имя пользователя должно быть от 3 до 20 символов, а пароль — не менее 6 символов.");
                            continue;
                        }

                        try {
                            currentUser = authService.register(username, password);
                            System.out.println("Пользователь успешно зарегистрирован и вошел в систему.");
                        } catch (IllegalArgumentException e) {
                            System.out.println("Ошибка: " + e.getMessage());
                        }
                        break;

                    case "2":
                        System.out.print("Введите имя пользователя: ");
                        username = scanner.nextLine().trim();
                        System.out.print("Введите пароль: ");
                        password = scanner.nextLine().trim();

                        try {
                            currentUser = authService.login(username, password);
                            System.out.println("Вход выполнен успешно. Добро пожаловать, " + currentUser.getUsername() + "!");
                        } catch (IllegalArgumentException e) {
                            System.out.println("Ошибка: " + e.getMessage());
                        }
                        break;

                    case "3":
                        System.out.println("До свидания!");
                        return;

                    default:
                        System.out.println("Неизвестная команда. Попробуйте снова.");
                }
            } else {
                System.out.println("\nДоступные команды: 1. Добавить доход/расход, 2. Показать отчет, 3. Установить бюджет, 4. Выйти из аккаунта, 5. Выход");
                System.out.print("Введите номер команды: ");
                String command = scanner.nextLine().trim();

                switch (command) {
                    case "1":
                        System.out.print("Введите тип (доход/расход): ");
                        String type = scanner.nextLine().trim();
                        System.out.print("Введите категорию: ");
                        String category = scanner.nextLine().trim();
                        System.out.print("Введите сумму: ");
                        double amount;
                        try {
                            amount = Double.parseDouble(scanner.nextLine().trim());
                            if (!InputValidator.validateAmount(amount)) {
                                throw new IllegalArgumentException("Сумма должна быть положительным числом.");
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println("Ошибка: " + e.getMessage());
                            continue;
                        }

                        try {
                            financeService.addTransaction(currentUser, type, category, amount);
                            System.out.println("Транзакция успешно добавлена.");
                        } catch (IllegalArgumentException e) {
                            System.out.println("Ошибка: " + e.getMessage());
                        }
                        break;

                    case "2":
                        System.out.println(financeService.generateReport(currentUser));
                        break;

                    case "3":
                        System.out.print("Введите категорию: ");
                        category = scanner.nextLine().trim();
                        System.out.print("Введите лимит бюджета: ");
                        double limit;
                        try {
                            limit = Double.parseDouble(scanner.nextLine().trim());
                            if (!InputValidator.validateAmount(limit)) {
                                throw new IllegalArgumentException("Лимит бюджета должен быть положительным числом.");
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println("Ошибка: " + e.getMessage());
                            continue;
                        }

                        try {
                            financeService.setBudget(currentUser, category, limit);
                            System.out.println("Бюджет успешно установлен.");
                        } catch (IllegalArgumentException e) {
                            System.out.println("Ошибка: " + e.getMessage());
                        }
                        break;

                    case "4":
                        currentUser = null;
                        System.out.println("Вы вышли из аккаунта.");
                        break;

                    case "5":
                        System.out.println("До свидания!");
                        return;

                    default:
                        System.out.println("Неизвестная команда. Попробуйте снова.");
                }
            }
        }
    }
}
