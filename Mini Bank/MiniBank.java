import java.util.Scanner;

record Bankinfo(String name,String branch){}

enum MenuOption{
    OPEN_ACCOUNT,DEPOSIT,WITHDRAW,TRANSFER,EXIT;
}


public class MiniBank {
    public static void main(String arg[]){
    Scanner sc=new Scanner(System.in);

    Bankinfo info= new Bankinfo("MiniBank", "CHARUSAT Main Branch");
    System.out.println("= = = = = = = = = = = = = = = = =");
    System.out.println(" "+info.name()+ " - " +info.branch());
    System.out.println("= = = = = = = = = = = = = = = = =");

    MenuOption[] options = MenuOption.values();
    MenuOption selected = null;
    
    while(selected!=MenuOption.EXIT){
        System.out.println("Menu");
        for (int i=0;i<options.length;i++){
            System.out.println((i+1)+ " - "+options[i]);
        }
        System.out.println("Enter your choice");

        if(!sc.hasNextInt()){
            System.out.println("Enter a number");
            sc.next();
            continue;
        }
        int choice=sc.nextInt();
        if(choice<1 || choice>options.length){
            System.out.println("Invalid choice. Select from 1-" +options.length);
            continue ;
        }
        selected=options[choice-1];
        String message = switch (selected) {
                case OPEN_ACCOUNT -> "Open Account — to be implemented in a later lab";
                case DEPOSIT      -> "Deposit — to be implemented in a later lab";
                case WITHDRAW     -> "Withdraw — to be implemented in a later lab";
                case TRANSFER     -> "Transfer — to be implemented in a later lab";
                case EXIT         -> "Thank you for using MiniBank. Goodbye!";
            };
            System.out.println(message);
    }
    sc.close();
    }
}
