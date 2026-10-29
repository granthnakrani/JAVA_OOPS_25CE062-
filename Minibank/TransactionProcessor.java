import java.util.*;
import java.util.concurrent.*;
public class TransactionProcessor {
    private final ConcurrentMap<String,Account> accounts = new ConcurrentHashMap<>();
    public void register(Account a){accounts.put(a.getAccountNumber(),a);}
    public void processBatch(List<Command> commands) throws BankException {
        for(Command c:commands){
            Account a=accounts.get(c.accountNumber());
            if(a==null) throw new AccountNotFoundException("Account not found: "+c.accountNumber());
            if(c.type()==TransactionType.DEPOSIT) a.deposit(c.amount()); else a.withdraw(c.amount());
        }
    }
}
