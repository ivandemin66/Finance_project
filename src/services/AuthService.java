package services;

import models.User;

import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private final Map<String, User> users;

    public AuthService() {
        this.users = new HashMap<>();
    }

    public User register(String username, String password) throws IllegalArgumentException {
        if (users.containsKey(username)) {
            throw new IllegalArgumentException("Пользователь с таким именем уже существует.");
        }

        User newUser = new User(username, password);
        users.put(username, newUser);
        return newUser;
    }

    public User login(String username, String password) throws IllegalArgumentException {
        User user = users.get(username);

        if (user == null || !user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Неверное имя пользователя или пароль.");
        }

        return user;
    }

    public User getUser(String username) {
        return users.get(username);
    }
}
