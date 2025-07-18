package com.pluralsight;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Scanner;
import java.text.NumberFormat;

public class Ledger {
    private Scanner scanner;
    private boolean appRunning;

    public Ledger(Scanner scanner, boolean appRunning) {
        this.scanner = scanner;
        this.appRunning = appRunning;
    }

    public void startLedger() {
        boolean ledgerAppRunning = true;
        String ledgerSelect;

        while (ledgerAppRunning && appRunning) {
            System.out.println("Welcome to the Ledger!");
            System.out.println("(A) View all Entries");
            System.out.println("(D) View all Deposits");
            System.out.println("(P) View all Payments");
            System.out.println("(R) Run Reports");
            System.out.println("(H) Return to Home Screen");

            ledgerSelect = scanner.nextLine().trim().toUpperCase();

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
                    ledgerAppRunning = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public void printAllTransactionsFile() {
        try (BufferedReader bufReader = new BufferedReader(new FileReader("src/main/resources/transactions.csv"))) {
            String line;
            System.out.println("All Transactions:");
            while ((line = bufReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            System.out.println("Error reading file.");
            e.printStackTrace();
        }
    }

    public void printDepositTransactions() {
        try (BufferedReader bufReader = new BufferedReader(new FileReader("src/main/resources/transactions.csv"))) {
            String header = bufReader.readLine();
            String line;
            System.out.println("Deposits:");
            while ((line = bufReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 6 && parts[parts.length - 3].equalsIgnoreCase("Credit")) {
                    System.out.println(line);
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading deposits.");
            e.printStackTrace();
        }
    }

    public void printPaymentTransactions() {
        try (BufferedReader bufReader = new BufferedReader(new FileReader("src/main/resources/transactions.csv"))) {
            String header = bufReader.readLine();
            String line;
            System.out.println("Payments:");
            while ((line = bufReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 6 && parts[parts.length - 3].equalsIgnoreCase("Debit")) {
                    System.out.println(line);
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading payments.");
            e.printStackTrace();
        }
    }

    public void ledgerReports() {
        boolean reportsRunning = true;
        while (reportsRunning) {
            System.out.println("Reports:");
            System.out.println("(1) Month to Date");
            System.out.println("(2) Previous Month");
            System.out.println("(3) Year To Date");
            System.out.println("(4) Previous Year");
            System.out.println("(5) Search by Vendor");
            System.out.println("(0) Back");

            String choice = scanner.nextLine().trim().toUpperCase();
            switch (choice) {
                case "5":
                    searchByVendor();
                    break;
                case "0":
                    reportsRunning = false;
                    break;
                default:
                    System.out.println("Feature not implemented.");
            }
        }
    }

    public void searchByVendor() {
        try (BufferedReader bufReader = new BufferedReader(new FileReader("src/main/resources/transactions.csv"))) {
            System.out.println("Enter vendor name:");
            String vendor = scanner.nextLine().trim();
            String header = bufReader.readLine();
            String line;
            boolean found = false;

            while ((line = bufReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 4 && parts[3].equalsIgnoreCase(vendor)) {
                    System.out.println(line);
                    found = true;
                }
            }
            if (!found) System.out.println("No transactions found for vendor: " + vendor);
        } catch (Exception e) {
            System.out.println("Error searching vendor.");
            e.printStackTrace();
        }
    }
}
