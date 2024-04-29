import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.FileWriter;
import java.io.IOException;

public class Transaction {
    private int senderAccountNumber;
    private int receiverAccountNumber;
    private double amount;
    private LocalDateTime transactionTime;

    public Transaction(int senderAccountNumber, int receiverAccountNumber, double amount) {
        this.senderAccountNumber = senderAccountNumber;
        this.receiverAccountNumber = receiverAccountNumber;
        this.amount = amount;
        this.transactionTime = LocalDateTime.now();
    }

    public void saveToFile() {
        try (FileWriter writer = new FileWriter("transaction_history.txt", true)) { // Append to file
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formatDateTime = transactionTime.format(formatter);

            writer.write("Date and Time: " + formatDateTime + ", Sender Account Number: " + senderAccountNumber + ", Receiver Account Number: " + receiverAccountNumber + ", Amount: " + amount + "\n");
            System.out.println("Transaction saved to transaction_history.txt");
        } catch (IOException e) {
            System.err.println("Error saving transaction to file: " + e.getMessage());
        }
    }
}
