import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class BankingApplication {
    private static final String FILE_NAME = "accounts.json";

    public static void main(String[] args){
        LinkedList accountsList = new LinkedList();
        loadAccountsFromFile(accountsList);
    }

    private static void saveAccountsToFile(LinkedList accoLinkedList){
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Convert LinkedList to JSON and write to file
            mapper.writeValue(new File(FILE_NAME), accountsList);
            System.out.println("Accounts saved to " + FILE_NAME);
        } catch (IOException e) {
            System.err.println("Error saving accounts to file: " + e.getMessage());
        }
    }

    private static void loadAccountsFromFile(LinkedList accountsList) {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try {
                // Read JSON file and convert to LinkedList
                LinkedList loadedList = mapper.readValue(file, LinkedList.class);
                // Copy loaded accounts to the provided accountsList
                Node current = loadedList.head;
                while (current != null) {
                    accountsList.addAccount(current.data);
                    current = current.next;
                }
                System.out.println("Accounts loaded from " + FILE_NAME);
            } catch (IOException e) {
                System.err.println("Error loading accounts from file: " + e.getMessage());
            }
        }
    }
    
}
