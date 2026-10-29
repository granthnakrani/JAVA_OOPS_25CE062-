import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
public class BankService {
    private final Repository<Account> repository = new Repository<>();
    private final ConcurrentMap<String,Account> concurrentAccounts = new ConcurrentHashMap<>();
    private final Path dataFile;
    private final List<String> transactions = new CopyOnWriteArrayList<>();
    public BankService(){this(Path.of("data_p14","accounts.dat"));}
    public BankService(Path dataFile){this.dataFile=dataFile; load();}
    public synchronized Account openAccount(String owner,long openingBalance) {
        if(owner==null||owner.isBlank()) throw new IllegalArgumentException("Owner name is required.");
        if(openingBalance<0) throw new IllegalArgumentException("Opening balance cannot be negative.");
        Account a=new Account(owner,openingBalance);repository.save(a);concurrentAccounts.put(a.getAccountNumber(),a);
        transactions.add("OPEN "+a.getAccountNumber()+" ₹"+openingBalance);return a;
    }
    public Account require(String id) throws AccountNotFoundException {
        Account a=repository.findById(id);
        if(a==null) throw new AccountNotFoundException("Account not found: "+id);
        if(!a.isActive()) throw new AccountNotFoundException("Account is closed: "+id);
        return a;
    }
    public void deposit(String id,long amount) throws BankException {
        Account a=require(id);a.deposit(amount);transactions.add("DEPOSIT "+id+" ₹"+amount);
    }
    public void withdraw(String id,long amount) throws BankException {
        Account a=require(id);a.withdraw(amount);transactions.add("WITHDRAW "+id+" ₹"+amount);
    }
    public synchronized void transfer(String from,String to,long amount) throws BankException {
        if(amount<=0) throw new InvalidAmountException("Transfer amount must be positive.");
        Account source=require(from), target=require(to);
        if(source==target) throw new InvalidAmountException("Source and destination must differ.");
        source.debitTransfer(amount, to);
        try {target.creditTransfer(amount, from);}
        catch(BankException | ArithmeticException e) {
            try {source.creditTransfer(amount, from);} catch(BankException rollback){e.addSuppressed(rollback);}
            if(e instanceof BankException be) throw be;
            throw new InvalidAmountException("Transfer failed due to balance overflow.");
        }
        transactions.add("TRANSFER "+from+" -> "+to+" ₹"+amount);
    }
    public String statement(String id) throws AccountNotFoundException{return StatementFormatter.buildStatement(require(id));}
    public ArrayList<Account> listAccounts(){ArrayList<Account> list=repository.findAll();list.sort(Comparator.comparingLong(Account::getBalance).reversed());return list;}
    public List<String> report(){return new ArrayList<>(transactions);}
    public Repository<Account> getRepository(){return repository;}
    public ConcurrentMap<String,Account> getConcurrentAccounts(){return concurrentAccounts;}
    public void processBatch(List<Command> commands) throws BankException {
        TransactionProcessor p=new TransactionProcessor();concurrentAccounts.values().forEach(p::register);
        p.processBatch(commands);for(Command c:commands)transactions.add("BATCH "+c.type()+" "+c.accountNumber()+" ₹"+c.amount());
    }
    public synchronized void save() {
        try {
            Path parent=dataFile.toAbsolutePath().getParent();if(parent!=null)Files.createDirectories(parent);
            try(ObjectOutputStream out=new ObjectOutputStream(Files.newOutputStream(dataFile))){
                out.writeObject(repository.findAll());out.writeObject(new ArrayList<>(transactions));
            }
        } catch(IOException e){throw new IllegalStateException("Could not save MiniBank data: "+e.getMessage(),e);}
    }
    @SuppressWarnings("unchecked") private synchronized void load() {
        if(!Files.exists(dataFile))return;
        try(ObjectInputStream in=new ObjectInputStream(Files.newInputStream(dataFile))){
            Object a=in.readObject();Object t=in.readObject();
            if(a instanceof List<?> list) for(Object item:list) if(item instanceof Account account){repository.save(account);concurrentAccounts.put(account.getAccountNumber(),account);}
            if(t instanceof List<?> list) for(Object item:list) if(item instanceof String s)transactions.add(s);
        } catch(IOException|ClassNotFoundException|RuntimeException e){
            System.err.println("Saved data could not be loaded; starting with empty storage. "+e.getMessage());
        }
    }
}
