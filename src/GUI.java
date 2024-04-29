import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class GUI extends JFrame {
    private static final String FILE_NAME = "bank_accounts.txt";
    private LinkedList accountsList;
    private JButton createAccountButton;
    private JButton makeTransactionButton;

    private JButton deleteAccount;

    public GUI() {
        accountsList = new LinkedList();
        loadAccountsFromFile(accountsList); // Load accounts from file if it exists

        setTitle("Banking Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null); // Set layout to null for absolute positioning
        panel.setPreferredSize(new Dimension(600, 600)); // Set preferred size

        createAccountButton = new JButton("Create Account");
        createAccountButton.setBounds(350, 150, 200, 80);
        makeTransactionButton = new JButton("Make Transaction");
        makeTransactionButton.setBounds(350, 250, 200, 80);
        deleteAccount = new JButton("Delete Account");
        deleteAccount.setBounds(350,350,200,80);

        panel.add(deleteAccount);
        panel.add(makeTransactionButton); // Add the button to the panel
        panel.add(createAccountButton); // Add the button to the panel

        createAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a new JFrame
                JFrame newFrame = new JFrame("Create Account");
                newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this frame on exit


                // Create a new panel
                JPanel newPanel = new JPanel();
                newPanel.setLayout(null);
                newPanel.setPreferredSize(new Dimension(400, 400));

                JLabel firstnameJLabel = new JLabel("First name:");
                firstnameJLabel.setBounds(50, 50, 100, 30);
                panel.add(firstnameJLabel);

                JTextField firstnameField = new JTextField(20);
                firstnameField.setBounds(150, 50, 200, 30);
                panel.add(firstnameField);

                JLabel lastnameJLabel = new JLabel("Last name:");
                lastnameJLabel.setBounds(50, 100, 100, 30);
                panel.add(lastnameJLabel);

                JTextField lastnameField = new JTextField(20);
                lastnameField.setBounds(150, 100, 200, 30);
                panel.add(lastnameField);

                JLabel balanceLabel = new JLabel("Balance:");
                balanceLabel.setBounds(50, 150, 100, 30);
                panel.add(balanceLabel);

                JTextField balanceField = new JTextField(20);
                balanceField.setBounds(150, 150, 200, 30);
                panel.add(balanceField);

                JButton createButton = new JButton("Create");
                createButton.setBounds(150, 200, 100, 30);

                createButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String firstname = firstnameField.getText();
                        String lastname = lastnameField.getText();
                        double balance = Double.parseDouble(balanceField.getText());

                        // Generate a random 6-digit account number
                        Random rand = new Random();
                        int accountNumber = 100000 + rand.nextInt(900000);

                        Account account = new Account(accountNumber, firstname + " " + lastname, balance);
                        accountsList.addAccount(account);
                        saveAccountsToFile(accountsList);
                        newFrame.dispose(); // Close the frame

                        // Show a message dialog with the account number
                        JOptionPane.showMessageDialog(null, "Account created successfully! Your account number is: " + accountNumber);
                    }
                });;        

                newPanel.add(firstnameJLabel);
                newPanel.add(lastnameJLabel);
                newPanel.add(firstnameField);
                newPanel.add(lastnameField);
                newPanel.add(balanceLabel);
                newPanel.add(balanceField);
                newPanel.add(createButton);

                

                // Add the new panel to the frame
                newFrame.add(newPanel);

                // Pack the frame (adjust its size to fit its subcomponents)
                newFrame.pack();

                // Center the frame on the screen
                newFrame.setLocationRelativeTo(null);

                // Show the frame
                newFrame.setVisible(true);
            }
        });

        makeTransactionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a new JFrame
                JFrame newFrame = new JFrame("Make Transaction");
                newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this frame on exit

                // Create a new panel
                JPanel newPanel = new JPanel();
                newPanel.setLayout(null);
                newPanel.setPreferredSize(new Dimension(500, 500));

                JLabel accNumSenderJLabel = new JLabel("Sender Account Number:");
                accNumSenderJLabel.setBounds(50, 50, 200, 30);
                JTextField accNumSender = new JTextField(20);
                accNumSender.setBounds(250, 50, 200, 30);

                JLabel amountSenderJLabel = new JLabel("Amount:");
                amountSenderJLabel.setBounds(50, 100, 200, 30);
                JTextField amountSender = new JTextField(20);
                amountSender.setBounds(250, 100, 200, 30); 
                JLabel accNumReceiverJLabel = new JLabel("Receiver Account Number:");
                accNumReceiverJLabel.setBounds(50, 250, 200, 30); 
                JTextField accNumReceiver = new JTextField(20);
                accNumReceiver.setBounds(250, 250, 200, 30); 

                JButton transferButton = new JButton("Transfer");
                transferButton.setBounds(200, 400, 100, 30); 

                transferButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        int senderAccountNumber = Integer.parseInt(accNumSender.getText());
                        int receiverAccountNumber = Integer.parseInt(accNumReceiver.getText());
                        double amount = Double.parseDouble(amountSender.getText());

                        Node current = accountsList.head;
                        Account senderAccount = null;
                        Account receiverAccount = null;
                        while (current != null) {
                            if (current.data.getAccountNumber() == senderAccountNumber) {
                                senderAccount = current.data;
                            }
                            if (current.data.getAccountNumber() == receiverAccountNumber) {
                                receiverAccount = current.data;
                            }
                            current = current.next;
                        }

                        if (senderAccount != null && receiverAccount != null) {
                            senderAccount.withdraw(amount);
                            receiverAccount.deposit(amount);
                            saveAccountsToFile(accountsList);
                            Transaction transaction = new Transaction(senderAccountNumber, receiverAccountNumber, amount);
                            transaction.saveToFile(); // Save transaction to file
                            newFrame.dispose(); // Close the frame
                            JOptionPane.showMessageDialog(null, "Transaction successful!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid account number(s)");
                        }
                    }
                });


                newPanel.add(accNumSenderJLabel);
                newPanel.add(amountSender);
                newPanel.add(accNumSender);
                newPanel.add(amountSenderJLabel);
                newPanel.add(accNumReceiverJLabel);
                newPanel.add(accNumReceiver);
                newPanel.add(transferButton);



                // Add the new panel to the frame
                newFrame.add(newPanel);

                // Pack the frame (adjust its size to fit its subcomponents)
                newFrame.pack();

                // Center the frame on the screen
                newFrame.setLocationRelativeTo(null);

                // Show the frame
                newFrame.setVisible(true);


            }});
        deleteAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a new JFrame
                JFrame newFrame = new JFrame("Delete Account");
                newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this frame on exit

                // Create a new panel
                JPanel newPanel = new JPanel();
                newPanel.setLayout(null);
                newPanel.setPreferredSize(new Dimension(400, 400));

                JLabel accNumJLabel = new JLabel("Account Number:");
                accNumJLabel.setBounds(30, 100, 200, 30);
                JTextField accNumField = new JTextField(20);
                accNumField.setBounds(150, 100, 200, 30);

                JButton deletebutton = new JButton("Delete");
                deletebutton.setBounds(200, 200, 100, 30);

                deletebutton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        int accountNumber = Integer.parseInt(accNumField.getText());

                        Node current = accountsList.head;
                        Node prev = null;
                        while (current != null) {
                            if (current.data.getAccountNumber() == accountNumber) {
                                if (prev == null) {
                                    accountsList.head = current.next;
                                } else {
                                    prev.next = current.next;
                                }
                                saveAccountsToFile(accountsList);
                                newFrame.dispose(); // Close the frame
                                JOptionPane.showMessageDialog(null, "Account deleted successfully!");
                                return;
                            }
                            prev = current;
                            current = current.next;
                        }
                        JOptionPane.showMessageDialog(null, "Account not found");
                    }
            });

        newPanel.add(accNumJLabel);
        newPanel.add(accNumField);
        newPanel.add(deletebutton);

        // Add the new panel to the frame
        newFrame.add(newPanel);

        // Pack the frame (adjust its size to fit its subcomponents)
        newFrame.pack();

        // Center the frame on the screen
        newFrame.setLocationRelativeTo(null);

        // Show the frame
        newFrame.setVisible(true);
    }
});
        

        add(panel); // Add the panel to the frame

        pack(); // Adjust frame size to fit components
        setLocationRelativeTo(null);
        setVisible(true);
    }



    private void saveAccountsToFile(LinkedList accountsList) {
        try (FileWriter writer = new FileWriter(FILE_NAME, false)) { // Append to file
            Node current = accountsList.head;
            while (current != null) {
                writer.write(current.data.getAccountNumber() + "," + current.data.getName() + "," + current.data.getBalance() + "\n");
                
                current = current.next;
            }
            System.out.println("Accounts saved to " + FILE_NAME);
        } catch (IOException e) {
            System.err.println("Error saving accounts to file: " + e.getMessage());
        }
    }

    private void loadAccountsFromFile(LinkedList accountsList) {
        try (Scanner scanner = new Scanner(new File(FILE_NAME))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int accountNumber = Integer.parseInt(parts[0]);
                    String namString = parts[1];
                    double balance = Double.parseDouble(parts[2]);
                    Account account = new Account(accountNumber, namString, balance);
                    accountsList.addAccount(account);
                }
            }
            System.out.println("Accounts loaded from " + FILE_NAME);
        } catch (IOException e) {
            System.err.println("Error loading accounts from file: " + e.getMessage());
        }
    }


}