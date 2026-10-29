import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
public class Account implements Comparable<Account>, Serializable {
    private static final long serialVersionUID = 1L;
    private static final AtomicLong COUNTER = new AtomicLong(0);
    @Id private final String accountNumber;
    private final String ownerName;
    private long balance;
    private boolean active = true;
    private final List<String> history = new ArrayList<>();
    public Account(String ownerName, long openingBalance) {
        this("AC" + String.format("%04d", COUNTER.incrementAndGet()), ownerName, openingBalance);
    }
    public Account(String ownerName) { this(ownerName, 0); }
    public Account(String accountNumber, String ownerName, long openingBalance) {
        if (ownerName == null || ownerName.isBlank()) throw new IllegalArgumentException("Owner name is required.");
        if (openingBalance < 0) throw new IllegalArgumentException("Opening balance cannot be negative.");
        this.accountNumber=accountNumber; this.ownerName=ownerName.trim(); this.balance=openingBalance;
        if (accountNumber != null && accountNumber.matches("AC[0-9]+")) {
            try { COUNTER.accumulateAndGet(Long.parseLong(accountNumber.substring(2)), Math::max); }
            catch (NumberFormatException ignored) { /* Keep the current counter for unusual IDs. */ }
        }
        history.add("Account opened with balance ₹" + openingBalance);
    }
    public synchronized void deposit(long amount) throws InvalidAmountException {
        if(amount<=0) throw new InvalidAmountException("Deposit amount must be positive.");
        balance=Math.addExact(balance, amount);
        history.add("Deposit: +₹"+amount+" | Balance: ₹"+balance);
    }
    public synchronized void withdraw(long amount) throws InsufficientFundsException, InvalidAmountException {
        if(amount<=0) throw new InvalidAmountException("Withdrawal amount must be positive.");
        if(balance<amount) throw new InsufficientFundsException("Insufficient funds.", amount-balance);
        balance-=amount; history.add("Withdrawal: -₹"+amount+" | Balance: ₹"+balance);
    }
    public synchronized void creditTransfer(long amount, String from) throws InvalidAmountException {
        if(amount<=0) throw new InvalidAmountException("Transfer amount must be positive.");
        balance=Math.addExact(balance, amount);
        history.add("Transfer received from "+from+": +₹"+amount+" | Balance: ₹"+balance);
    }
    public synchronized void debitTransfer(long amount, String to) throws InsufficientFundsException, InvalidAmountException {
        if(amount<=0) throw new InvalidAmountException("Transfer amount must be positive.");
        if(balance<amount) throw new InsufficientFundsException("Insufficient funds.", amount-balance);
        balance-=amount;
        history.add("Transfer sent to "+to+": -₹"+amount+" | Balance: ₹"+balance);
    }
    public synchronized long getBalance(){return balance;}
    public String getAccountNumber(){return accountNumber;}
    public String getOwnerName(){return ownerName;}
    public synchronized boolean isActive(){return active;}
    public synchronized void close(){active=false; history.add("Account closed.");}
    public synchronized List<String> getHistory(){return new ArrayList<>(history);}
    public synchronized void record(String entry){history.add(entry);}
    @Override public int compareTo(Account other){return accountNumber.compareTo(other.accountNumber);}
    @Override public boolean equals(Object o){if(this==o)return true;if(!(o instanceof Account))return false;return accountNumber.equals(((Account)o).accountNumber);}
    @Override public int hashCode(){return accountNumber.hashCode();}
    @Override public synchronized String toString(){return accountNumber+" | "+ownerName+" | Balance: ₹"+balance+(active?" | ACTIVE":" | CLOSED");}
}
