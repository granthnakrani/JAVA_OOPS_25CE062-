public final class StatementFormatter {
    private StatementFormatter(){}
    public static String buildStatement(Account a){
        StringBuilder b=new StringBuilder("========== MINIBANK STATEMENT ==========\n");
        b.append("Account: ").append(a.getAccountNumber()).append("\nOwner: ").append(a.getOwnerName())
         .append("\nCurrent balance: ₹").append(a.getBalance()).append("\nStatus: ").append(a.isActive()?"ACTIVE":"CLOSED")
         .append("\n--------------- HISTORY ----------------\n");
        for(String h:a.getHistory()) b.append(h).append('\n');
        return b.append("=========================================\n").toString();
    }
}
