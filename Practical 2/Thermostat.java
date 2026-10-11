public class Thermostat {
    private String location;
    private int temperature;
    private static final int MIN=16,MAX=30;
    private static int activeCount=0;

    Thermostat(String location,int StartTemp){
        this.location=location;
        this.temperature=(StartTemp>=MIN && StartTemp<=MAX)? StartTemp :22;
        activeCount++;
    }

    public Thermostat(String location){
        this(location,22);
    }

    public void raise(){
        if(temperature<MAX)
            temperature++;
        else 
            System.out.println(location+ " Already At maximum - "+MAX);
    }

    public void lower(){
        if(temperature>MIN)
            temperature--;
        else 
            System.out.println(location+ " Already At minimum - "+MIN);
    }

    public int getTemperature(){
        return temperature;
    }

    public int getActiveCounter(){
        return activeCount;
    }

    public static void main(String arg[]){

        Thermostat Anand =new Thermostat("Anand");
        Thermostat Changa =new Thermostat("Changa",25);

        System.out.println("====== Anand temperature 10 Times ======");
        for(int i=0;i<10;i++){
            Anand.raise();
            System.out.println("Anand Temperature now - " +Anand.getTemperature());
        }

        System.out.println("====== Changa temperature 20 Times ======");
        for(int i=0;i<20;i++){
            Changa.lower();
            System.out.println("Changa Temperature now - " +Changa.getTemperature());
        }

        System.out.print(" Active Thermostat Count - "+activeCount);

    }


    
}
