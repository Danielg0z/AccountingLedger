# Accounting Ledger App

## Overview
The **Accounting Ledger App** is a Java-based console application that enables users to manage financial transactions effectively. It allows users to perform tasks such as adding deposits, making payments, and viewing a ledger of all transactions. The application uses a CSV file (`transactions.csv`) to store transaction data, making it easy to save and retrieve financial records.

---

## Features
- **Add Deposit**:
  - Record deposits with details such as description, vendor, amount, and account type.
  - Automatically validates deposits to ensure they are valid credit transactions.
  - Example usage:
    ```plaintext
    Enter a description for the deposit (e.g., 'Salary'): 
    job
    Receive from (e.g., 'Amazon' or 'Jimmy'): 
    work
    Enter the amount to deposit: 
    1200
    All deposits are received as Credit:
    Enter the account type (e.g., 'Checking' or 'Savings'): 
    Savings
    Deposit successfully recorded!
    ```

    Below are screenshots of the **Add Deposit** functionality in action:

    ![Add Deposit Input](https://user-images.githubusercontent.com/path-to-image-1.png "Add Deposit Input")
    ![Add Deposit CSV Output](https://user-images.githubusercontent.com/path-to-image-2.png "Add Deposit CSV Output")
    ![Add Deposit Example](https://user-images.githubusercontent.com/path-to-image-3.png "Add Deposit Example")

- **Make Payment**:
  - Record payments with details such as description, vendor, amount, and account type.
  - Ensures that only valid debit transactions are saved.
  - Example usage:
    ```plaintext
    Enter a description for the payment (e.g., 'Rent'): 
    wholesale deed
    Pay to (e.g., 'Walmart' or 'Landlord'): 
    zillow
    Enter the amount to pay: 
    150
    All deposits are received as Debit:
    Enter the account type (e.g., 'Checking' or 'Savings'): 
    Savings
    Payment successfully recorded!
    ```

    Below is a screenshot of the **Make Payment** functionality in action:

    ![Make Payment Example](https://user-images.githubusercontent.com/path-to-image-3.png "Make Payment Example")
    
- **View Ledger**:
  - Displays all transactions stored in the `transactions.csv` file.
  - Filter transactions by type (e.g., deposits or payments).
  - Example usage:
    ```plaintext
    Welcome to the Ledger!
    These are your following options:
    (A) View all Entries
    (D) View all Deposits
    (P) View all Payments
    (R) Run Reports or Run a Custom Search
    (H) Return to Home Screen

    Reading Transactions from transactions.csv...
    ------------------------------------------------
    Date|Time|Description|Vendor|Type|Amount|Account
    2025-05-02|01:33:41|job|work|Credit|1200.0|Savings
    2025-05-02|02:18:24|wholesale deed|zillow|Debit|150.0|Savings
    ------------------------------------------------
    Finished reading transactions.
    ```

    Below are screenshots of the **View Ledger** functionality in action:

    ![View Ledger Example 1](https://user-images.githubusercontent.com/path-to-image-4.png "View Ledger Example 1")
    ![View Ledger Example 2](https://user-images.githubusercontent.com/path-to-image-5.png "View Ledger Example 2")
    ![View Ledger Example 3](https://user-images.githubusercontent.com/path-to-image-6.png "View Ledger Example 3")

**Exit procedures per Loop**:
  - The application allows users to return to the main menu or exit the program cleanly after completing their tasks.
    Below is a screenshot showing these exit procedures:

    ![Exit Procedures Example](https://user-images.githubusercontent.com/path-to-image-7.png "Exit Procedures Example")

---

## File Structure

```
src/
├── main/
│   ├── java/
│   │   ├── com/
│   │   │   ├── pluralsight/
│   │   │   │   ├── AccountingLedgerApp.java
│   │   │   │   ├── Transaction.java
│   ├── resources/
│       ├── transactions.csv
```

### Key Files:
- **`AccountingLedgerApp.java`**: The main application code, which contains the logic for adding deposits, making payments, and viewing the ledger.
- **`Transaction.java`**: Represents a financial transaction with attributes like date, description, vendor, amount, type, and account.
- **`transactions.csv`**: A CSV file that stores all transaction records.

---

## How to Use

1. **Run the Application**:
   - Compile and run the `AccountingLedgerApp.java` file using a Java IDE or terminal.
   - Example:
     ```bash
     javac AccountingLedgerApp.java
     java com.pluralsight.AccountingLedgerApp
     ```

2. **Main Menu**:
   - When the application starts, you'll see the following menu:
     ```
     Welcome to the Account Ledger App!
     Here are your following options:
     (D) Add Deposit
     (P) Make a Payment
     (L) Ledger
     (X) Exit
     ```

3. **Add Deposit**:
   - Select `D` to add a deposit.
   - Enter the required details:
     - Description (e.g., "Salary")
     - Vendor (e.g., "Employer")
     - Amount (e.g., 1500.0)
     - Account Type (e.g., "Checking" or "Savings")
   - The deposit will be validated and saved to `transactions.csv`.

4. **Make Payment**:
   - Select `P` to record a payment.
   - Enter the required details:
     - Description (e.g., "Rent")
     - Vendor (e.g., "Landlord")
     - Amount (e.g., 1000.0)
     - Account Type (e.g., "Checking" or "Savings")
   - The payment will be validated and saved to `transactions.csv`.

5. **View Ledger**:
   - Select `L` to view all transactions or filter by type (Deposit/Payment).

6. **Exit**:
   - Select `X` to exit the application.

---

## Example Usage

### Adding a Deposit:
```plaintext
Enter a description for the deposit (e.g., 'Salary'): Salary
Receive from (e.g., 'Amazon' or 'Jimmy'): Employer
Enter the amount to deposit: 1500.0
All deposits are received as Credit
Enter the account type (e.g., 'Checking' or 'Savings'): Checking
Deposit successfully recorded!
```

---

## Validation Rules
The app ensures all transactions are valid using the following rules:
1. **Amount**:
   - Must be greater than `0.0`.
2. **Type**:
   - Must be either `Debit` or `Credit`.
3. **Account Type**:
   - Must be a valid account type (e.g., "Checking" or "Savings").

---

## CSV File Format
The `transactions.csv` file stores transactions in the following format:

```
date|time|description|vendor|type|amount|account
```

### Example:
```csv
2025-05-02|01:12:07|lunch money|Mom|Credit|10.5|Checking
```

---

## Future Enhancements
- Add a graphical user interface (GUI) for easier interaction.
- Implement advanced filtering options (e.g., by vendor or account type).
- Add support for recurring transactions.
- Provide summary reports (e.g., total income, total expenses, etc.).

---

## Contributing
Contributions are welcome! Please follow these steps:
1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Submit a pull request with a detailed explanation of your changes.

---

## License
This project is licensed under the MIT License. See the `LICENSE` file for details.

---

## Contact
For questions or feedback, please contact **[Danielg0z](https://github.com/Danielg0z)**.
