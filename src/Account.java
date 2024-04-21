public class Account {
    private String name;
    private int accountNumber;
    private double balance;
    

    public Account(String name, int accountNumber,double balance) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = balance;
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

    





    
}
