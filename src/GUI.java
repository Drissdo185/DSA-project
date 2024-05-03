import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class GUI extends JFrame {
    private static final String FILE_NAME = "bank_accounts.txt";
    private LinkedList accountsList;
    private JButton createAccountButton;
    private JButton makeTransactionButton;
    private JButton deleteAccount;
    private JButton checkTransaction;
    private JButton viewCustomer;


    public GUI() {
        accountsList = new LinkedList();
        loadAccountsFromFile(accountsList); // Load accounts from file if it exists

        setTitle("Banking Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null); // Set layout to null for absolute positioning
        panel.setPreferredSize(new Dimension(600, 600)); // Set preferred size

        createAccountButton = new JButton("Create Account");
        createAccountButton.setBounds(350, 100, 200, 80);
        makeTransactionButton = new JButton("Make Transaction");
        makeTransactionButton.setBounds(350, 200, 200, 80);
        checkTransaction = new JButton("Check Transaction");
        checkTransaction.setBounds(350, 300, 200, 80);
        deleteAccount = new JButton("Delete Account");
        deleteAccount.setBounds(350,400,200,80);
        viewCustomer = new JButton("View Customer");
        viewCustomer.setBounds(350,500,200,80);


        panel.add(deleteAccount);
        panel.add(makeTransactionButton);
        panel.add(createAccountButton);
        panel.add(checkTransaction);
        panel.add(viewCustomer);
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
                            Transaction transaction = new Transaction(senderAccountNumber, receiverAccountNumber, amount, LocalDateTime.now());
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

        checkTransaction.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            // Create a new JFrame
            JFrame newFrame = new JFrame("Check Transaction");
            newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this frame on exit

            // Create a new panel
            JPanel newPanel = new JPanel();
            newPanel.setLayout(null);
            newPanel.setPreferredSize(new Dimension(600, 500));

            // Create a JTextField for the account number
            JTextField accNumField = new JTextField(20);
            accNumField.setBounds(150, 80, 300, 30);

            // Create a JLabel for the account number
            JLabel accNumJLabel = new JLabel("Account Number:");
            accNumJLabel.setBounds(30, 80, 200, 30);


            // Create a JButton for checking the transaction history
            JButton checkButton = new JButton("Check Transaction History");
            checkButton.setBounds(200, 400, 200, 30);
            newPanel.add(accNumJLabel);
            newPanel.add(accNumField);
            newPanel.add(checkButton);

            checkButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Get the account number from the JTextField
                    int accountNumber = Integer.parseInt(accNumField.getText());

                    // Load transactions from file
                    List<Transaction> transactions = loadTransactionsForAccount(accountNumber);

                    // Sort transactions by amount using insertion sort
                    for (int i = 1; i < transactions.size(); i++) {
                        Transaction key = transactions.get(i);
                        int j = i - 1;
                        while (j >= 0 && transactions.get(j).getAmount() > key.getAmount()) {
                            transactions.set(j + 1, transactions.get(j));
                            j = j - 1;
                        }
                        transactions.set(j + 1, key);
                    }

                    // Create table and populate it with transactions
                    String[] columnNames = {"Date and Time", "Sender Account Number", "Receiver Account Number", "Amount"};
                    DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                    for (Transaction transaction : transactions) {
                        Object[] row = new Object[4];
                        row[0] = transaction.getDate();
                        row[1] = transaction.getSenderAccountNumber();
                        row[2] = transaction.getReceiverAccountNumber();
                        row[3] = transaction.getAmount();
                        model.addRow(row);
                    }
                    JTable table = new JTable(model);
                    JScrollPane scrollPane = new JScrollPane(table);
                    scrollPane.setBounds(10, 150, 580, 100); // Set bounds for JScrollPane

                    JButton sortButton = new JButton("Sort by Amount");
                    sortButton.setBounds(200, 300, 200, 30);
                    sortButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Sort transactions by amount using insertion sort
                            for (int i = 1; i < transactions.size(); i++) {
                                Transaction key = transactions.get(i);
                                int j = i - 1;
                                while (j >= 0 && transactions.get(j).getAmount() < key.getAmount()) {
                                    transactions.set(j + 1, transactions.get(j));
                                    j = j - 1;
                                }
                                transactions.set(j + 1, key);
                            }

                            // Clear the table
                            model.setRowCount(0);

                            // Populate the table with sorted transactions
                            for (Transaction transaction : transactions) {
                                Object[] row = new Object[4];
                                row[0] = transaction.getDate();
                                row[1] = transaction.getSenderAccountNumber();
                                row[2] = transaction.getReceiverAccountNumber();
                                row[3] = transaction.getAmount();
                                model.addRow(row);
                            }

                            // Repaint the panel to update the table
                            newPanel.revalidate();
                            newPanel.repaint();

                        }
                    });

                    newPanel.add(sortButton);
                    newPanel.add(scrollPane);

                    // Repaint the panel to update the table
                    newPanel.revalidate();
                    newPanel.repaint();
                }
            });

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

        viewCustomer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                // Create a new JFrame
                JFrame newFrame = new JFrame("View Customer");
                newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                // Create a new panel
                JPanel newPanel = new JPanel();
                newPanel.setLayout(null);
                newPanel.setPreferredSize(new Dimension(600, 500));

                // Create a JTable and a DefaultTableModel
                String[] columnNames = {"Account Number", "Name", "Balance"};
                DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                JTable table = new JTable(model);
                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setBounds(30, 120, 540, 360);

                // Read the bank_accounts.txt file
                try (Scanner scanner = new Scanner(new File("bank_accounts.txt"))) {
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        String[] parts = line.split(",");
                        if (parts.length == 3) {
                            // Add each line as a row in the DefaultTableModel
                            model.addRow(new Object[]{parts[0], parts[1], parts[2]});
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                JButton sortLastNameButton = new JButton("Sort by Last Name");
                sortLastNameButton.setBounds(30, 50, 200, 30);
                sortLastNameButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        sortAccountsByLastName(accountsList);
                        // Refresh the table
                        refreshTable(model, accountsList);
                    }
                });
                JButton searchButton = new JButton("Search");
                searchButton.setBounds(350, 80, 200, 30);
                JTextField userName = new JTextField(20);
                userName.setBounds(350, 50, 200, 30);
                JLabel userNameLabel = new JLabel("Enter the name of the customer:");
                userNameLabel.setBounds(350, 20, 200, 30);

                searchButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name = userName.getText();

                        // Convert LinkedList to ArrayList
                        ArrayList<Account> accountsArrayList = new ArrayList<>();
                        Node current = accountsList.head;
                        while (current != null) {
                            accountsArrayList.add(current.data);
                            current = current.next;
                        }

                        // Sort ArrayList by name
                        Collections.sort(accountsArrayList, new Comparator<Account>() {
                            @Override
                            public int compare(Account a1, Account a2) {
                                return a1.getName().compareTo(a2.getName());
                            }
                        });

                        // Recursive binary search
                        int index = binarySearch(accountsArrayList, 0, accountsArrayList.size() - 1, name);

                        if (index != -1) {
                            model.setRowCount(0);
                            model.addRow(new Object[]{accountsArrayList.get(index).getAccountNumber(), accountsArrayList.get(index).getName(), accountsArrayList.get(index).getBalance()});
                            model.fireTableDataChanged();
                        } else {
                            JOptionPane.showMessageDialog(null, "Customer not found");
                        }
                    }

    private int binarySearch(ArrayList<Account> accounts, int left, int right, String name) {
        if (right >= left) {
            int mid = left + (right - left) / 2;
            int res = name.compareTo(accounts.get(mid).getName());

            // Check if name is present at mid
            if (res == 0) {
                return mid;
            }

            // If name is greater, ignore left half
            if (res > 0) {
                return binarySearch(accounts, mid + 1, right, name);
            }

            // If name is smaller, ignore right half
            return binarySearch(accounts, left, mid - 1, name);
        }

        // Name is not present in array
        return -1;
    }
});

                newPanel.add(userNameLabel);
                newPanel.add(userName);
                newPanel.add(searchButton);
                newPanel.add(sortLastNameButton);
                newPanel.add(scrollPane);
                newFrame.add(newPanel);
                newFrame.pack();
                newFrame.setLocationRelativeTo(null);
                newFrame.setVisible(true);
            }
        });

        


        add(panel); // Add the panel to the frame

        pack(); // Adjust frame size to fit components
        setLocationRelativeTo(null); //hehehe

        setVisible(true);
    }
    public void refreshTable(DefaultTableModel model, LinkedList accountsList) {
    // Clear the table
    model.setRowCount(0);

    // Repopulate the table with sorted data
    Node current = accountsList.head;
    while (current != null) {
        model.addRow(new Object[]{current.data.getAccountNumber(), current.data.getName(), current.data.getBalance()});
        current = current.next;
    }

    // Notify the JTable that the data has changed
    model.fireTableDataChanged();
}
    private List<Transaction> loadTransactionsForAccount(int accountNumber) {
    List<Transaction> transactions = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader("transaction_history.txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(", ");
            LocalDateTime dateTime = LocalDateTime.parse(parts[0].split(": ")[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            int senderAccountNumber = Integer.parseInt(parts[1].split(": ")[1]);
            int receiverAccountNumber = Integer.parseInt(parts[2].split(": ")[1]);
            double amount = Double.parseDouble(parts[3].split(": ")[1]);
            if (senderAccountNumber == accountNumber) {
                transactions.add(new Transaction(senderAccountNumber, receiverAccountNumber, amount, dateTime));
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return transactions;
}
    public void sortAccountsByLastName(LinkedList accountsList) {
    Node current = accountsList.head;
    while (current != null) {
        Node min = current;
        Node r = current.next;

        while (r != null) {
            String[] currentNameParts = current.data.getName().split(" ");
            String currentLastName = currentNameParts[currentNameParts.length - 1];

            String[] minNameParts = min.data.getName().split(" ");
            String minLastName = minNameParts[minNameParts.length - 1];

            if (r.data.getName().split(" ")[r.data.getName().split(" ").length - 1].compareTo(minLastName) < 0) {
                min = r;
            }
            r = r.next;
        }

        // Swapping Data
        Account x = current.data;
        current.data = min.data;
        min.data = x;

        current = current.next;
    }
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