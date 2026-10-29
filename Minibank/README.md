# MiniBank

Final assembled MiniBank demonstrating the OOP Java practical concepts: classes and records, encapsulation, exceptions, collections, generics, annotations, concurrency, file I/O, persistence, transaction history, and tests.

## Prerequisites

- JDK 17+
- Optional: Maven/JUnit if you want to configure and run the JUnit tests. The application itself uses only the JDK.

## Project Structure

```text
MiniBank_RepoStyle/
└── practical_14/
    ├── MiniBank.java
    ├── Account.java
    ├── Customer.java
    ├── BankService.java
    ├── Repository.java
    ├── Id.java
    ├── BankException.java
    ├── InsufficientFundsException.java
    ├── AccountNotFoundException.java
    ├── InvalidAmountException.java
    ├── Command.java
    ├── CommandParser.java
    ├── TransactionProcessor.java
    ├── StatementFormatter.java
    ├── Validator.java
    ├── TransactionType.java
    ├── tests/
    └── README.md
```


## Menu Options

| # | Action |
|---|---|
| 1 | Open Account |
| 2 | Deposit |
| 3 | Withdraw |
| 4 | Transfer |
| 5 | Statement |
| 6 | List All Accounts |
| 7 | Transaction Report |
| 8 | Run Batch Transactions |
| 9 | Save & Exit |

## Data files

Account balances and transaction history are saved in `data_p14/accounts.dat` when you choose Save & Exit and loaded at the next startup. Keep this folder if you want to preserve the demo data.

## Key Design

- **Model:** `Account` and `Customer` keep account/customer data encapsulated.
- **Storage:** generic `Repository<T>` uses a concurrent map and `@Id` reflection.
- **Service:** `BankService` performs account operations and checks business rules.
- **Exceptions:** checked `BankException` hierarchy handles invalid amounts, missing accounts, and insufficient funds.
- **Concurrency:** synchronized money operations and concurrent collection storage.
- **Persistence:** Java object streams save/reload application state.
- **Batch commands:** `Command` record and `CommandParser` validate batch input.
- **Statements:** account statement formatting and transaction report output.

## Note

The Java application is JDK-only. JUnit test source files are kept in `tests/`; running them requires adding JUnit 5 to the classpath or configuring them in an IDE.
