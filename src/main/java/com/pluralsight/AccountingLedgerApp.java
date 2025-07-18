package com.pluralsight;

import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

//For the readme file use a language called markdown to generate your read me - AI may assist

public class AccountingLedgerApp {

    static Scanner theScanner = new Scanner(System.in);
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss");
    static boolean appRunning = true;


    public static void main(String[] args) {
        while (appRunning) {
            homeScreen();
        }
        System.out.println("Goodbye for one PAL to another!");
    }


    public static String homeScreen() {
        String homeSelect = ""; // stores user choice

        while (appRunning) {
            //display the welcome message and the home screen options
            System.out.println("------------------------------------------------");
            System.out.println("Say Hi to your PAL!");
            System.out.println("PAL is your Personal Accouniting Ledger!");
            System.out.println("------------------------------------------------");
            System.out.println("Here are your following options PAL offers:");
            System.out.println("(D) Add Deposit");
            System.out.println("(P) Make a Payment");
            System.out.println("(L) Ledger");
            System.out.println("(X) Exit");
            System.out.println("------------------------------------------------");

            //get the user's input
            homeSelect = theScanner.nextLine().trim().toUpperCase();

            // Handle user input with a switch statement
            switch (homeSelect) {
                case "D":
                    //Deposit Functionality
                    addDeposit();
                    System.out.println("Deposit");
                    break;
                case "P":
                    //Payment Functionality
                    makePayment();
                    System.out.println("Payment");
                    break;
                case "L":
                    //Ledger Functionality
                    theLedger();
                    break;
                case "X":
                    //Exits application
                    System.out.println("Exit");
                    appRunning = false; //stops entire application
                    break;
                default:
                    System.out.println("Invalid option, :( Please try again.");
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

    public static void makePayment() {
        try {
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
            System.out.println("Payment  successfully recorded!");

        } catch (Exception e) {
            System.out.println("An error occurred while processing the deposit. Please try again.");
            e.printStackTrace();
        }
    }

    public static void theLedger() {
        boolean ledgerAppRunning = true;
        String ledgerSelect = ""; // stores user choice

        while (ledgerAppRunning && appRunning) {
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
                    ledgerReports();
                    break;
                case "H":
                    System.out.println("Returning to the Home Screen...");
                    homeScreen(); // Call the homeScreen method
                    ledgerAppRunning = false;
                    break;
                case "X":
                    System.out.println("Exit");
                    appRunning = false; // Stop the entire application
                    ledgerAppRunning = false; // Exit theLedger loop
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;

            }
        }
    }


    public static void writeTransactionToFile(Transaction transaction) {
        String filePath = "src/main/resources/transactions.csv";
        File file = new File(filePath);

        //Has to read and write to the file to ensure that the header exist no matter what
        try (BufferedWriter bufWriter = new BufferedWriter(new FileWriter(filePath, true));
             BufferedReader bufReader = new BufferedReader(new FileReader(filePath))) {    // Check if the file is empty, and if so, write the header

            // Check if the header exists in the file
            String firstLine = bufReader.readLine();
            if (firstLine == null || !firstLine.equalsIgnoreCase("Date|Time|Description|Vendor|Type|Amount|Account")) {
                // Write the header if it doesn't exist
                String header = "Date|Time|Description|Vendor|Type|Amount|Account";
                bufWriter.write(header);
                bufWriter.newLine();
            }

            // Make the oayment amount negative

            // Make the payment amount negative if debit is entered else make them positive(normal)
            double signedAmount = transaction.getType().equalsIgnoreCase("Debit")
                    ? -Math.abs(transaction.getAmount()) : Math.abs(transaction.getAmount());


            // These give your signedAmount you entered US dollar format
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
            String formattedAmount = currencyFormat.format(signedAmount);

            // Format the transaction details
            String output = LocalDateTime.now().format(formatter) + "|" +
                    transaction.getDescription() + "|" +
                    transaction.getVendor() + "|" +
                    transaction.getType() + "|" +
                    formattedAmount + "|" +
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
                if (parts.length >= 3 && parts[parts.length - 3].equalsIgnoreCase("Credit")) {
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
        // intializes the Buffer reader
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
                if (parts.length >= 3 && parts[parts.length - 3].equalsIgnoreCase("Debit")) {
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

    public static void ledgerReports() {
        boolean reportsAppRunning = true;
        String reportSelect = ""; // stores user choice

        while (reportsAppRunning && appRunning) {
            //display the welcome message and the home screen
            System.out.println("Welcome to the Reports!");
            System.out.println("(1) Month to Date");
            System.out.println("(2) Previous Month");
            System.out.println("(3) Year To Date");
            System.out.println("(4) Previous Year");
            System.out.println("(5) Search by Vendor");
            System.out.println("(0) Back");

            //get the user's input
            reportSelect = theScanner.nextLine().trim().toUpperCase();

            // Handle user input with a switch statement
            switch (reportSelect) {
                case "1":
                    System.out.println("Month to Date");
                    break;
                case "2":
                    //viewByPreviousMonth();
                    break;
                case "3":
                    System.out.println("Year to Date");
                    break;
                case "4":
                    System.out.println("Previous Year");
                    break;
                case "5":
                    searchByVendor();
                    break;
                case "0":
                    System.out.println("Returning to the Home Screen...");
                    theLedger(); // Call the homeScreen method
                    break;
                case "X":
                    System.out.println("Exit");
                    appRunning = false; // Stop the entire application
                    reportsAppRunning = false; // Exit ledgerReports loop
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;

            }
        }
    }

    //Search By vendor is going to be very similar to
    public static void searchByVendor() {
        String filePath = "src/main/resources/transactions.csv";

        try (BufferedReader bufReader = new BufferedReader(new FileReader(filePath))) {
            System.out.println("Enter the vendor name to search for (e.g., 'Costco', 'Landlord'):");
            String searchVendor = theScanner.nextLine().trim(); // Get user input for vendor

            String line; // To store each line read from the file
            boolean matchFound = false; // Flag to check if any match is found

            System.out.println("Transactions with vendor: " + searchVendor);
            System.out.println("------------------------------------------------");

            // Skip the header line
            String header = bufReader.readLine();
            System.out.println(header); // Optionally print the header once

            // Loop through each line in the file
            while ((line = bufReader.readLine()) != null) {
                // Split the line into fields using the delimiter "|"
                String[] parts = line.split("\\|");

                // Check if the file has the correct structure and if the vendor matches
                if (parts.length >= 4 && parts[3].equalsIgnoreCase(searchVendor)) {
                    System.out.println(line); // Print the matching line
                    matchFound = true;
                }
            }

            if (!matchFound) {
                System.out.println("No transactions found for the vendor: " + searchVendor);
            }

            System.out.println("------------------------------------------------");
        } catch (Exception e) {
            System.out.println("An error occurred while searching for the vendor.");
            e.printStackTrace();
        }
    }


    /*
        // view by Monthly
    public static void viewByPreviousMonth(int month, int year) {
        String filePath = "src/main/resources/transactions.csv";

        try (BufferedReader bufReader = new BufferedReader(new FileReader(filePath))) {
            System.out.println("Enter the vendor name to search for (e.g., 'Costco', 'Landlord'):");
            String line;
            boolean matchFound = false;

            //Skip header line
            // Skip the header line
            String header = bufReader.readLine();
            System.out.println(header); // Optionally print the header if needed

            while ((line = bufReader.readLine()) != null) {
                // Splits the lines into fields using pipes "|"
                String[] parts = line.split("\\|");

                if (parts.length >= 1) {
                    String[] dateParts = parts[0].split("-");
                    int lineYear = Integer.parseInt(dateParts[0]);
                    int lineMonth = Integer.parseInt(dateParts[1]);

                    // Check if the transaction matches the given month and year
                    if (lineYear == year && lineMonth == month) {
                        System.out.println(line); // Print the matching transaction
                        matchFound = true;
                    }
                }
            }

            if (!matchFound) {
                System.out.println("No transactions found for the specified month and year.");
            }

            System.out.println("------------------------------------------------");
        } catch (Exception e) {
            System.out.println("An error occurred while filtering transactions by month.");
            e.printStackTrace();
        }

    }
    */

}


