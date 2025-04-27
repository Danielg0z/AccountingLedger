package com.pluralsight;

import java.util.Date;

public class Transaction {
    private String transactionId;
    private Date transactionDate; // date of transaction
    private String description; // what the payment was for ex: "Rent payment"
    private Double amount; // transaction amount
    private String type; // Debit or Credit transaction
    private String category; // helps organize transactions ex: "Rent", "Entertainment", "Insurance", etc
    private String account; // savings or checking

    public Transaction(String transactionId, Date transactionDate, String description, Double amount, String type, String category, String account) {
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;
        this.description = description;
        amount = amount;
        this.type = type;
        this.category = category;
        this.account = account;
    }

    //Method for Transaction validation
    public boolean isTransactionValid() {
        // Checks that the amount of money enter is greater than 0
        // Because you can't have a transaction with zero or negative money
        if (amount <= 0) {
            System.out.print("Error: Amount must be greater than zero");
            return false;
        }
        // Makes sure that the transaction is either with Debit or Credit
        if (!type.equalsIgnoreCase("Debit") || !type.equalsIgnoreCase("Credit")) {
            System.out.print("Error: Transaction must be either 'Debit' or 'Credit'. ");
            return false;
        }
        return true;

    }

    public double applyTransaction(double currentBalance) {
        if (type.equalsIgnoreCase("Debit")) {
            return currentBalance - amount; // this means money is being spent(-) + current balance
        } else if (type.equalsIgnoreCase("Credit")) {
            return currentBalance + amount; // this returns money is being earned(+) + current balance
        } else {
            return currentBalance; // this returns unchanged balance if invalid type
        }
    }
}

