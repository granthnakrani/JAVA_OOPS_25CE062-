import java.util.Scanner;

public class Tollbooth {
    record Vehicle(String number,String type){}

    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);

        int total=0;
        int bikeCount=0;int truckCount=0;int carCount=0;

        while(true){
            System.out.println("Enter Vehicle Number(Or enter done to exit) ");
            String number=sc.nextLine();

            if(number.equalsIgnoreCase("done")){
                break;
            }

            System.out.println("Enter Vehicle type bike/car/truck");
            String type=sc.nextLine().trim().toLowerCase();

            Vehicle vehicle =new Vehicle(number,type);

            int toll= switch(vehicle.type()){

                case "bike"->20;
                case "car"-> 50;
                case "truck"->150;
                default->0;

            };
            total+=toll;
            switch(vehicle.type()){
                 case "bike"-> bikeCount++;
                case "car"-> carCount++;
                case "truck"-> truckCount++;
            }
        }
            String mostFrequent;
            if (carCount > bikeCount && carCount > truckCount)
                mostFrequent = "car";
            else if (bikeCount > carCount && bikeCount > truckCount)
                mostFrequent = "bike";
            else
                mostFrequent = "truck";
        
            System.out.println("Total toll :" + total);
            System.out.println("Most Frequent :" + mostFrequent);

    }
}
