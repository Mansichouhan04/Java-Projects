package _concept.Project_Assignment;
//3.DMart Shopping application>>>>>
import java.util.Scanner;
class DMartShoppingApplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter total purchase amount in Rs.");
        double amount = sc.nextDouble();
         if (amount >= 3000 && amount<5000 ) {
            System.out.println("You get a flat discount of Rs.500");
        }
        else if (amount >= 5000 && amount<10000) {
            double discount = amount * 0.30;
            System.out.println("You get a 30% discount of the total bill"  + " "+ discount);
        }  
        else if(amount<3000) {
            System.out.println("No discounts or free gifts applicable");
        }
        if (amount >= 10000) {
            if (amount < 15000) {
                System.out.println("Congratulations! You get a free mixer.");
            } else {
                System.out.println("Congratulations! You get a free suitcase.");
            }
        } 
    }
}
