package models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private List<Wallet> wallets;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.wallets = new ArrayList<>();
        // Add a default wallet for the user
        this.wallets.add(new Wallet("Default Wallet"));
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Wallet> getWallets() {
        return wallets;
    }

    public Wallet getWallet(String walletName) {
        return wallets.stream()
                .filter(wallet -> wallet.getName().equals(walletName))
                .findFirst()
                .orElse(null);
    }

    public void addWallet(Wallet wallet) {
        wallets.add(wallet);
    }

    public void removeWallet(String walletName) {
        wallets.removeIf(wallet -> wallet.getName().equals(walletName));
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", wallets=" + wallets +
                '}';
    }
}
