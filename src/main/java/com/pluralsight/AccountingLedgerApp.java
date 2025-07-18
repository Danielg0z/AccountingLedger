package com.pluralsight;

import org.w3c.dom.ls.LSOutput;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;
import java.io.*;

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

    public static void homeScreen() {
        String homeSelect = "";

        while (appRunning) {
            System.out.println("------------------------------------------------");
            System.out.println("Say Hi to your PAL!");
            System.out.println("PAL is your Personal Accounting Ledger!");
            System.out.println("------------------------------------------------");
            System.out.println("Here are your following options PAL offers:");
            System.out.println("(D) Add Deposit");
            System.out.println("(P) Make a Payment");
            System.out.println("(L) Ledger");
            System.out.println("(X) Exit");
            System.out.println("------------------------------------------------");

            homeSelect = theScanner.nextLine().trim().toUpperCase();

            switch (homeSelect) {
                case "D":
                    addDeposit();
                    break;
                case "P":
                    makePayment();
                    break;
                case "L":
                    Ledger ledger = new Ledger(theScanner, appRunning);
                    ledger.startLedger();
                    break;
                case "X":
                    appRunning = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public static void addDeposit() {
        try {
            System.out.println("Enter a description for the deposit (e.g., 'Salary'): ");
            String description = theScanner.nextLine();

            System.out.println("Receive from (e.g., 'Amazon' or 'Jimmy'):");
            String vendor = theScanner.nextLine();

            System.out.println("Enter the amount to deposit: ");
            double amount = Double.parseDouble(theScanner.nextLine());

            System.out.println("Enter the account type (e.g., 'Checking' or 'Savings'): ");
            String account = theScanner.nextLine();

            LocalDateTime today = LocalDateTime.now();
            Transaction deposit = new Transaction(
                    java.sql.Date.valueOf(today.toLocalDate()),
                    description,
                    vendor,
                    amount,
                    "Credit",
                    account
            );

            if (!deposit.isTransactionValid()) {
                System.out.println("Invalid transaction. Deposit not saved.");
                return;
            }

            writeTransactionToFile(deposit);
            System.out.println("Deposit successfully recorded!");

        } catch (Exception e) {
            System.out.println("An error occurred while processing the deposit.");
            e.printStackTrace();
        }
    }

    public static void makePayment() {
        try {
            System.out.println("Enter a description for the payment (e.g., 'Rent'): ");
            String description = theScanner.nextLine();

            System.out.println("Pay to (e.g., 'Walmart' or 'Landlord'):");
            String vendor = theScanner.nextLine();

            System.out.println("Enter the amount to pay: ");
            double amount = Double.parseDouble(theScanner.nextLine());

            System.out.println("Enter the account type (e.g., 'Checking' or 'Savings'): ");
            String account = theScanner.nextLine();

            LocalDateTime today = LocalDateTime.now();
            Transaction payment = new Transaction(
                    java.sql.Date.valueOf(today.toLocalDate()),
                    description,
                    vendor,
                    amount,
                    "Debit",
                    account
            );

            if (!payment.isTransactionValid()) {
                System.out.println("Invalid transaction. Payment not saved.");
                return;
            }

            writeTransactionToFile(payment);
            System.out.println("Payment successfully recorded!");

        } catch (Exception e) {
            System.out.println("An error occurred while processing the payment.");
            e.printStackTrace();
        }
    }

    public static void writeTransactionToFile(Transaction transaction) {
        String filePath = "src/main/resources/transactions.csv";
        File file = new File(filePath);

        try (BufferedWriter bufWriter = new BufferedWriter(new FileWriter(filePath, true));
             BufferedReader bufReader = new BufferedReader(new FileReader(filePath))) {

            String firstLine = bufReader.readLine();
            if (firstLine == null || !firstLine.equalsIgnoreCase("Date|Time|Description|Vendor|Type|Amount|Account")) {
                String header = "Date|Time|Description|Vendor|Type|Amount|Account";
                bufWriter.write(header);
                bufWriter.newLine();
            }

            double signedAmount = transaction.getType().equalsIgnoreCase("Debit")
                    ? -Math.abs(transaction.getAmount()) : Math.abs(transaction.getAmount());

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
            String formattedAmount = currencyFormat.format(signedAmount);

            String output = LocalDateTime.now().format(formatter) + "|" +
                    transaction.getDescription() + "|" +
                    transaction.getVendor() + "|" +
                    transaction.getType() + "|" +
                    formattedAmount + "|" +
                    transaction.getAccount();

            bufWriter.write(output);
            bufWriter.newLine();

        } catch (Exception e) {
            System.out.println("An error occurred while writing the transaction to the file.");
            e.printStackTrace();
        }
    }
}


