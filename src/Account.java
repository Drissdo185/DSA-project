import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Account {
    private String name;
    private int accountNumber;
    private double balance;
    

    public Account(int accountNumber, String name, double balance) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            System.out.println("Insufficient balance");
        }
    }

    public double getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    public void display() {
        System.out.println("Name: " + name + ", Balance: " + balance);
    }
    public String getTransactionHistory() {
        StringBuilder history = new StringBuilder();
        try (Scanner scanner = new Scanner(new File("transaction_history.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int senderAccountNumber = Integer.parseInt(parts[1].split(": ")[1].trim());
                    int receiverAccountNumber = Integer.parseInt(parts[2].split(": ")[1].trim());
                    if (this.accountNumber == senderAccountNumber || this.accountNumber == receiverAccountNumber) {
                        history.append(line).append("\n");
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading transaction history: " + e.getMessage());
        }
        return history.toString();
    }
}
