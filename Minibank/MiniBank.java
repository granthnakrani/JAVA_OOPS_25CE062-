import java.util.*;
public class MiniBank {
    private static final Scanner IN=new Scanner(System.in);
    private static final BankInfo INFO=new BankInfo("MiniBank","Main Branch");
    private static final BankService BANK=new BankService();
    record BankInfo(String name,String branch){}
    enum MenuOption {OPEN_ACCOUNT,DEPOSIT,WITHDRAW,TRANSFER,STATEMENT,LIST,REPORT,BATCH,EXIT}
    public static void main(String[] args){
        System.out.println("====================================\n "+INFO.name()+" | "+INFO.branch()+"\n====================================");
        boolean running=true;
        while(running){
            printMenu();String choice=IN.nextLine().trim();
            try{
                switch(choice){
                    case "1" -> open();
                    case "2" -> deposit();
                    case "3" -> withdraw();
                    case "4" -> transfer();
                    case "5" -> statement();
                    case "6" -> list();
                    case "7" -> report();
                    case "8" -> batch();
                    case "9" -> {BANK.save();System.out.println("Data saved. Thank you for using MiniBank.");running=false;}
                    default -> System.out.println("Invalid option. Choose 1–9.");
                }
            }catch(BankException e){
                if(e instanceof InsufficientFundsException f) System.out.println("Transaction failed: "+e.getMessage()+" Shortfall: ₹"+f.getShortfall());
                else System.out.println("Transaction failed: "+e.getMessage());
            }catch(IllegalArgumentException|IllegalStateException e){System.out.println("Input/operation error: "+e.getMessage());}
            catch(Exception e){System.out.println("Operation failed: "+e.getMessage());}
            finally { /* each menu operation is isolated so the application keeps running */ }
        }
    }
    private static void printMenu(){
        System.out.println("\n1. Open account\n2. Deposit\n3. Withdraw\n4. Transfer\n5. Statement\n6. List accounts\n7. Transaction report\n8. Batch transactions\n9. Save and exit");
        System.out.print("Choose: ");
    }
    private static String read(String prompt){System.out.print(prompt);return IN.nextLine().trim();}
    private static long amount() {try{long n=Long.parseLong(read("Amount (whole rupees): "));if(n<=0)throw new NumberFormatException();return n;}catch(NumberFormatException e){throw new IllegalArgumentException("Enter a positive whole-rupee amount.");}}
    private static void open(){
        String name=read("Owner name: ");long opening;
        try{opening=Long.parseLong(read("Opening balance (0 or more): "));}catch(NumberFormatException e){throw new IllegalArgumentException("Opening balance must be a whole number.");}
        Account a=BANK.openAccount(name,opening);System.out.println("Account created: "+a);
    }
    private static void deposit()throws BankException{BANK.deposit(read("Account number: "),amount());System.out.println("Deposit successful.");}
    private static void withdraw()throws BankException{BANK.withdraw(read("Account number: "),amount());System.out.println("Withdrawal successful.");}
    private static void transfer()throws BankException{String from=read("From account: "),to=read("To account: ");BANK.transfer(from,to,amount());System.out.println("Transfer successful.");}
    private static void statement()throws BankException{System.out.println(BANK.statement(read("Account number: ")));}
    private static void list(){List<Account> a=BANK.listAccounts();if(a.isEmpty())System.out.println("No accounts yet.");else a.forEach(System.out::println);}
    private static void report(){List<String> r=BANK.report();if(r.isEmpty())System.out.println("No transactions yet.");else r.forEach(System.out::println);}
    private static void batch()throws BankException{
        System.out.println("Enter commands like DEPOSIT AC0001 500 or WITHDRAW AC0001 200. Type DONE to process.");
        List<Command> commands=new ArrayList<>();
        while(true){String line=read("> ");if(line.equalsIgnoreCase("DONE"))break;try{commands.add(CommandParser.parse(line));}catch(IllegalArgumentException e){System.out.println(e.getMessage());}}
        BANK.processBatch(commands);System.out.println("Processed "+commands.size()+" transaction(s).");
    }
}
