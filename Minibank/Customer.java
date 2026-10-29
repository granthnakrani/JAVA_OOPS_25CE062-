import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;
public final class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final AtomicLong COUNTER = new AtomicLong(100);
    @Id private final String customerId;
    private final String name, email, mobile;
    public Customer(String name, String email, String mobile) {
        this("CUST" + COUNTER.incrementAndGet(), name, email, mobile);
    }
    public Customer(String customerId, String name, String email, String mobile) {
        this.customerId=customerId; this.name=name; this.email=email; this.mobile=mobile;
    }
    public String getCustomerId(){return customerId;}
    public String getName(){return name;}
    public String getEmail(){return email;}
    public String getMobile(){return mobile;}
    @Override public boolean equals(Object o){if(this==o)return true;if(!(o instanceof Customer))return false;return customerId.equals(((Customer)o).customerId);}
    @Override public int hashCode(){return customerId.hashCode();}
    @Override public String toString(){return customerId+" | "+name+" | "+email+" | "+mobile;}
}
