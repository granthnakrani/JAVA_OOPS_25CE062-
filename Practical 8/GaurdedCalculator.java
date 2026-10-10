import java.util.Scanner;

class DivideByZeroException extends Exception {
    public DivideByZeroException(String message) {
        super(message);
    }
}

public class GaurdedCalculator{

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean success = false;

        while (!success) {
            
            try{
       
    System.out.print("enter First Number: ");
        double a =sc.nextDouble();

                System.out.print("enter operator (*,/,+,-) :");
                char operator = sc.next().charAt(0);

                System.out.print("enter second Number: ");
                double b = sc.nextDouble();

                double result;

                switch (operator) {
                    case '+':
                        result = a + b;
                        break;
                    case '-':
                        result = a - b;
                        break;
                    case '*':
                        result = a * b;
                        break;
                    case '/':
                        if (b == 0) {
                            throw new DivideByZeroException("Cannot divide by zero.");
                        }
                        result = a / b;
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid Operator");
                }

                System.out.println("Result: " + result);
                success = true;
            } catch (NumberFormatException e) {
                System.out.println("Enter valid number!");
                sc.nextLine();
            } catch (DivideByZeroException e) {
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
     finally{
        System.out.println("Account Logged");
     }       
        }
        sc.close();
        System.out.println("Calculation Completed Successfully");
    }
}


