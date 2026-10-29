public final class CommandParser {
    private CommandParser(){}
    public static Command parse(String line) {
        if(line==null) throw new IllegalArgumentException("Command cannot be empty.");
        String[] p=line.trim().split("\\s+");
        if(p.length!=3) throw new IllegalArgumentException("Use: DEPOSIT|WITHDRAW accountNumber amount");
        TransactionType type;
        try { type=TransactionType.valueOf(p[0].toUpperCase()); }
        catch(IllegalArgumentException e){throw new IllegalArgumentException("Unknown transaction type: "+p[0]);}
        long amount;
        try {amount=Long.parseLong(p[2]);}catch(NumberFormatException e){throw new IllegalArgumentException("Amount must be a whole number.");}
        if(type==TransactionType.TRANSFER) throw new IllegalArgumentException("Use the menu to transfer between two accounts.");
        return new Command(type,p[1],amount);
    }
}
