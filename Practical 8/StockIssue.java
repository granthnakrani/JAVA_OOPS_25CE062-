import java.util.Scanner;

class OutOfStockException extends Exception{
        private int shortfall;

        public OutOfStockException(int shortfall){
            super("Out Of stock.Shortfall:" +shortfall);
            this.shortfall=shortfall;
    }
}
class InvalidQuantityException extends Exception{
    public InvalidQuantityException(String  message){
        super(message);
    }
}


class Warehouse {
    
    int stock;
    public Warehouse(int stock){
        this.stock=stock;
    }

    void issue(String item,int qty)
        throws OutOfStockException,InvalidQuantityException{

            if(qty<=0){
                throw new InvalidQuantityException("Quantity must be greater than 0.");
            }
            if(qty>stock){
                throw new OutOfStockException(qty-stock);
            }

            stock=stock-qty;
            System.out.println(item+ " issued : "+ qty +
                " .Remaining Stock : "+stock );
    }
}

public class StockIssue {
    public static void main(String arg[]){
        Warehouse warehouse= new Warehouse(50);

        String[]item={"book","pen","Pencil","Bag","Watch"};
        int[]qty={10,15,20,25,2,5};

        for(int i=0;i<item.length;i++){
            try{
            warehouse.issue(item[i],qty[i]);
            }
            catch(OutOfStockException e){
                System.out.println("Failed :"+item[i]+ " - " +e.getMessage());
            }
            catch(InvalidQuantityException e){
                System.out.println(qty+" "+e.getMessage());
            }
        }


    }
    
}
