import org.junit.jupiter.api.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;
class BankServiceTest {
    private BankService bank;
    @BeforeEach void setup() throws Exception {bank=new BankService(Files.createTempDirectory("minibank-test").resolve("accounts.dat"));}
    @Test void depositIncreasesBalance() throws Exception {Account a=bank.openAccount("A",100);bank.deposit(a.getAccountNumber(),50);assertEquals(150,a.getBalance());}
    @Test void withdrawalOverBalanceThrows() throws Exception {Account a=bank.openAccount("A",100);assertThrows(InsufficientFundsException.class,()->bank.withdraw(a.getAccountNumber(),101));assertEquals(100,a.getBalance());}
    @Test void concurrentDepositsRemainAccurate() throws Exception {
        Account a=bank.openAccount("A",0);int threads=10,each=1000;ExecutorService pool=Executors.newFixedThreadPool(threads);
        try {List<Future<?>> fs=new ArrayList<>();for(int i=0;i<threads;i++)fs.add(pool.submit(()->{for(int j=0;j<each;j++)try{a.deposit(1);}catch(BankException e){throw new RuntimeException(e);}}));for(Future<?> f:fs)f.get();}
        finally{pool.shutdownNow();}assertEquals((long)threads*each,a.getBalance());
    }
    @Test void transferMovesMoney() throws Exception {Account a=bank.openAccount("A",500),b=bank.openAccount("B",100);bank.transfer(a.getAccountNumber(),b.getAccountNumber(),200);assertEquals(300,a.getBalance());assertEquals(300,b.getBalance());}
    @Test void dataPersistsAcrossRestart() throws Exception {Path f=Files.createTempDirectory("mb").resolve("accounts.dat");BankService b1=new BankService(f);Account a=b1.openAccount("A",900);b1.deposit(a.getAccountNumber(),100);b1.save();BankService b2=new BankService(f);assertEquals(1000,b2.getRepository().findById(a.getAccountNumber()).getBalance());}
    @Test void parserWorks(){Command c=CommandParser.parse("DEPOSIT AC0001 500");assertEquals(TransactionType.DEPOSIT,c.type());assertEquals(500,c.amount());}
}
