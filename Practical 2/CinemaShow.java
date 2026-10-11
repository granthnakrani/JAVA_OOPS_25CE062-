public class CinemaShow {
    private String title;
    private int seatsAvailable;
    private final int capacity;
    private static int totalBooked=0;

    CinemaShow(String title,int capacity){
        this.title=title;
        this.capacity=capacity;
        this.seatsAvailable=capacity;
    }
    CinemaShow(String title){
        this(title,100);
    }

    public boolean book(int n){
        if(n<=seatsAvailable){
            seatsAvailable-=n;
            totalBooked+=n;
            return true;
        }
        else return false;
    }

    public void cancel(int n){
        seatsAvailable =Math.min(seatsAvailable+n,capacity);
    }

    public int getseatsAvailable(){
        return seatsAvailable;
    }
    public static int getTotalBooked(){
        return totalBooked;
    }

    public String getTitle(){
        return title;
    }

    public static void main(String arg[]){
        CinemaShow show1 = new CinemaShow("Inception", 50);
        CinemaShow show2 = new CinemaShow("Interstellar");   // capacity 100

        System.out.println("=== Booking for: " + show1.getTitle() + " ===");

        boolean result = show1.book(30);
        System.out.println("Book 30: " + result + " | Seats left: " + show1.getseatsAvailable());

        result = show1.book(25);
        System.out.println("Book 25: " + result + " | Seats left: " + show1.getseatsAvailable());

        show1.cancel(10);
        System.out.println("Cancel 10 | Seats left: " + show1.getseatsAvailable());

        result = show1.book(10);
        System.out.println("Book 10: " + result + " | Seats left: " + show1.getseatsAvailable());

        System.out.println("\n=== Booking for: " + show2.getTitle() + " ===");
        result = show2.book(60);
        System.out.println("Book 60: " + result + " | Seats left: " + show2.getseatsAvailable());

        System.out.println("\nTotal booked across all shows: " + CinemaShow.getTotalBooked());
    }
    }
    

    

