package com.pluralsight;

import java.util.Date;

public class Transaction {
    //date|time|description|vendor(transactionID|type|amount|account
    private Date transactionDate; // date of transaction
    private String description; // what the payment was for ex: "Rent payment"
    private String vendor; // Vendor
    private String type; // Debit or Credit transaction
    private double amount; // transaction amount
    private String account; // savings or checking

    public Transaction(Date transactionDate, String description, String vendor, double amount, String type, String account) {
        this.transactionDate = transactionDate;
        this.vendor = vendor;
        this.description = description;
        this.type = type;
        this.account = account;
    }

    public Date getTransactionDate() {
        return this.transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getDescription() {
        return this.description;
    }

    public void setVe(String description) {
        this.description = description;
    }

    public String getVendor() {
        return this.vendor;
    }

    public void setVendor(String transactionId) {
        this.vendor = transactionId;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    //Method for Transaction validation
    public boolean isTransactionValid() {
        // Checks that the amount of money enter is greater than 0
        // Because you can't have a transaction with zero or negative money
        if (amount < 0) {
            System.out.print("Error: Amount must be greater than zero ");
            return false;
        }
        // Makes sure that the transaction is either with Debit or Credit
        if (!type.equalsIgnoreCase("Debit") && !type.equalsIgnoreCase("Credit")) {
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

