package com.pluralsight;

import java.util.Scanner;

public class AccountingLedgerApp {

    static Scanner theScanner = new Scanner(System.in);

    public static void main(String[] args) {
    String userChoice = homeScreen();
    System.out.println("You chose: " + userChoice);
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

                    System.out.println("Deposit");
                    break;
                case "P":
                    System.out.println("Payment");
                    break;
                case "L":
                    System.out.println("Ledger");
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

}
