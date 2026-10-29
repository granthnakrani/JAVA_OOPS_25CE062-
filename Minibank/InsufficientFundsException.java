public class InsufficientFundsException extends BankException {
    private final long shortfall;
    public InsufficientFundsException(String message, long shortfall) {
        super(message); this.shortfall = shortfall;
    }
    public long getShortfall() { return shortfall; }
}
