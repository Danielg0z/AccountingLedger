package com.pluralsight;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
                    // Get the Deposit information - date|time|description|vendor(transactionID|type|amount|account  -
                    addDeposit();

                    System.out.println("Deposit");
                    break;
                case "P":
                    makePayment();
                    System.out.println("Payment");
                    break;
                case "L":
                    System.out.println("Ledger");
                    //A - Loops through all the transactions
                    //D - Loop through all the tranasctions to find if its a deposit
                    //P - loops through all transactions to find if its a payment
                    //R - An
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
                    "Debit", // Type (all deposits are credits)
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
                    "Credit", // Type (all deposits are credits)
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


    public static void writeTransactionToFile(Transaction transaction) {
        try (BufferedWriter bufWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv", true))) {
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




}
