package com.pluralsight;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

//For the readme file use a language called markdown to generate your read me - AI may assist

public class AccountingLedgerApp {

    static Scanner theScanner = new Scanner(System.in);
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss");

    public static void main(String[] args) {
        String userChoice = homeScreen();
        System.out.println(userChoice);
    }

    public static String homeScreen() {
        boolean appRunning = true;
        String homeSelect = ""; // stores user choice

        while (appRunning) {
            //display the welcome message and the home screen
            System.out.println("Welcome to the Account Ledger App!");
            System.out.println("Here are your following options:");
            System.out.println("(D) Add Deposit");
            System.out.println("(P) Make a Payment");
            System.out.println("(L) Ledger");
            System.out.println("(X) Exit");

            //get the user's input
            homeSelect = theScanner.nextLine().trim().toUpperCase();

            // Handle user input with a switch statement
            switch (homeSelect) {
                case "D":
                    // Get the Deposit information - date|time|description|vendor|type|amount|account  -
                    addDeposit();

                    System.out.println("Deposit");
                    break;
                case "P":
                    makePayment();
                    System.out.println("Payment");
                    break;
                case "L":
                    System.out.println("Ledger");
                    theLedger();
                    break;
                case "X":
                    System.out.println("Exit");
                    appRunning = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;

            }
        }
        return homeSelect; //Returns the last valid choice
    }

    public static void addDeposit() {
        try {
            // Prompt the user for deposit information
            System.out.println("Enter a description for the deposit (e.g., 'Salary'): ");
            String description = theScanner.nextLine();

            System.out.println("Recieve from(ex. 'Amazon' or 'Jimmy'):");
            String vendor = theScanner.nextLine();

            System.out.println("Enter the amount to deposit: ");
            double amount = Double.parseDouble(theScanner.nextLine());

            System.out.println("All deposits are received as Credit'): ");

            System.out.println("Enter the account type (e.g., 'Checking' or 'Savings'): ");
            String account = theScanner.nextLine();

            // Create the transaction object
            LocalDateTime today = LocalDateTime.now();
            Transaction depositTransaction = new Transaction( //calling the constructor - follow constroctor order
                    java.sql.Date.valueOf(today.toLocalDate()) // Converst to SQL date
                    , description,
                    vendor, // Generate a unique transaction ID
                    amount, // Amount
                    "Credit", // Type (all deposits are credits)
                    account // Account type
            );

            // Validate the transaction
            if (!depositTransaction.isTransactionValid()) {
                System.out.println("Invalid transaction. Deposit not saved.");
                return;
            }

            // Write the transaction to the CSV file
            writeTransactionToFile(depositTransaction);
            System.out.println("Deposit successfully recorded!");

        } catch (Exception e) {
            System.out.println("An error occurred while processing the deposit. Please try again.");
            e.printStackTrace();
        }
    }
    public static void makePayment(){
        try{
            System.out.println("Enter a description for the payment (e.g., 'Rent'): ");
            String description = theScanner.nextLine();

            System.out.println("Pay to(ex. 'Walmart' or 'landlord'):");
            String vendor = theScanner.nextLine();

            System.out.println("Enter the amount to pay: ");
            double amount = Double.parseDouble(theScanner.nextLine());

            System.out.println("All deposits are received as Debit'): ");

            System.out.println("Enter the account type (e.g., 'Checking' or 'Savings'): ");
            String account = theScanner.nextLine();

            // Create the transaction object
            LocalDateTime today = LocalDateTime.now();
            Transaction paymentTransaction = new Transaction( //calling the constructor - follow constroctor order
                    java.sql.Date.valueOf(today.toLocalDate()) // Converst to SQL date
                    , description,
                    vendor, // Generate a unique transaction ID
                    amount, // Amount
                    "Debit", // Type (all payments are debit)
                    account // Account type
            );

            if (!paymentTransaction.isTransactionValid()) {
                System.out.println("Invalid transaction. Deposit not saved.");
                return;
            }

            // Write the transaction to the CSV file
            writeTransactionToFile(paymentTransaction);
            System.out.println("Payment" +
                    " successfully recorded!");

        } catch (Exception e) {
            System.out.println("An error occurred while processing the deposit. Please try again.");
            e.printStackTrace();
        }
    }

    public static String theLedger(){
        boolean appRunning = true;
        String ledgerSelect = ""; // stores user choice

        while (appRunning) {
            //display the welcome message and the home screen
            System.out.println("Welcome to the Ledger!");
            System.out.println("These are your following options:");
            System.out.println("(A) View all Entries");
            System.out.println("(D) View all Deposits");
            System.out.println("(P) View all Payments");
            System.out.println("(R) Run Reports or Run a Custom Search");
            System.out.println("(H) Return to Home Screen");

            //get the user's input
            ledgerSelect = theScanner.nextLine().trim().toUpperCase();

            // Handle user input with a switch statement
            switch (ledgerSelect) {
                case "A":
                    printAllTransactionsFile();
                    break;
                case "D":
                    printDepositTransactions();
                    break;
                case "P":
                    printPaymentTransactions();
                    break;
                case "R":
                    System.out.println("Reports");
                    break;
                case "H":
                    System.out.println("Returning to the Home Screen...");
                    homeScreen(); // Call the homeScreen method
                    return ""; // breaks out of the ledger loop
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;

            }
        }
        return ledgerSelect; //Returns the last valid choice
    }


    public static void writeTransactionToFile(Transaction transaction) {
        String filePath = "src/main/resources/transactions.csv";
        File file = new File(filePath);

        try (BufferedWriter bufWriter = new BufferedWriter(new FileWriter(filePath, true))) {
            // Check if the file is empty, and if so, write the header
            if (file.length() == 0) {
                String header = "Date|Time|Description|Vendor|Type|Amount|Account";
                bufWriter.write(header);
                bufWriter.newLine();
            }

            // Format the transaction details
            String output = LocalDateTime.now().format(formatter) + "|" +
                    transaction.getDescription() + "|" +
                    transaction.getVendor() + "|" +
                    transaction.getType() + "|" +
                    transaction.getAmount() + "|" +
                    transaction.getAccount();

            // Write the transaction to the file
            bufWriter.write(output);
            bufWriter.newLine();
        } catch (Exception e) {
            System.out.println("An error occurred while writing the transaction to the file.");
            e.printStackTrace();
        }
    }

    public static void printAllTransactionsFile() {
        try (BufferedReader bufReader = new BufferedReader(new FileReader("src/main/resources/transactions.csv"))) {

            String line; // To store each line read from the file

            System.out.println("Reading Transactions from transactions.csv...");
            System.out.println("------------------------------------------------");

            // Read each line from the file and print it
            while ((line = bufReader.readLine()) != null) {
                System.out.println(line);
            }

            System.out.println("------------------------------------------------");
            System.out.println("Finished reading transactions.");

        } catch (Exception e) {
            System.out.println("An error occurred while reading the transactions from the file.");
            e.printStackTrace();
        }
    }

    public static void printDepositTransactions() {
        try (BufferedReader bufReader = new BufferedReader(new FileReader("src/main/resources/transactions.csv"))) {

            String line; // To store each line read from the file

            System.out.println("Reading Deposit from transactions.csv...");
            System.out.println("------------------------------------------------");

            // Skip the header line
            String header = bufReader.readLine();
            System.out.println(header); // Optionally print the header if needed

            while ((line = bufReader.readLine()) != null) {
                // Splits the lines into fields using pipes "|"
                String[] parts = line.split("\\|");

                //checks if lines have the debit string, because all payments are debit
                if(parts.length >= 3 && parts[parts.length - 3].equalsIgnoreCase("Credit")){
                    System.out.println(line);

                }
            }

            System.out.println("------------------------------------------------");
            System.out.println("Finished reading Payment transactions.");

        } catch (Exception e) {
            System.out.println("An error occurred while reading the transactions from the file.");
            e.printStackTrace();
        }
    }

    public static void printPaymentTransactions() {
        try (BufferedReader bufReader = new BufferedReader(new FileReader("src/main/resources/transactions.csv"))) {

            String line; // To store each line read from the file

            System.out.println("Reading Deposit from transactions.csv...");
            System.out.println("------------------------------------------------");

            // Skip the header line
            String header = bufReader.readLine();
            System.out.println(header); // Optionally print the header if needed

            while ((line = bufReader.readLine()) != null) {
                // Splits the lines into fields using pipes "|"
                String[] parts = line.split("\\|");

                //checks if lines have the debit string, because all payments are debit
                if(parts.length >= 3 && parts[parts.length - 3].equalsIgnoreCase("Debit")){
                    System.out.println(line);

                }
            }

            System.out.println("------------------------------------------------");
            System.out.println("Finished reading Payment transactions.");

        } catch (Exception e) {
            System.out.println("An error occurred while reading the transactions from the file.");
            e.printStackTrace();
        }
    }


}
