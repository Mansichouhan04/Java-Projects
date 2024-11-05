package _concept.Project_Assignment.Project2;
//2.Banking System>>>>>
import java.util.Scanner;
class BankingSystem1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        System.out.print("Enter your account number: ");
        String accountNumber = sc.nextLine();

        System.out.println("Choose account type: ");
        System.out.println("1. Current");
        System.out.println("2. Savings");

        int choice = sc.nextInt();
        sc.nextLine();

        BankAccount1 account;
        if (choice == 1) {
            account = new CheckingAccount1(0.0);
        } else if (choice == 2) {
            System.out.print("Enter interest rate ( 5 for 5%): ");
            double interestRate = sc.nextDouble();
            account = new SavingsAccount1(0.0, interestRate);
        } else {
            System.out.println("Invalid choice");
            return;
        }

        while (true) {
            System.out.println("Choose operation: ");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check balance");
            System.out.println("4. Exit");

            choice = sc.nextInt();
            sc.nextLine(); 

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter amount to deposit: ");
                        double amount = sc.nextDouble();
                        account.deposit(amount);
                        break;
                    case 2:
                        System.out.print("Enter amount to withdraw: ");
                        amount = sc.nextDouble();
                        account.withdraw(amount);
                        break;
                    case 3:
                        System.out.println("Your balance: " + account.getBalance());
                        break;
                    case 4:
                        System.out.println("Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice");
                }
            } catch (Exception e) {
                System.out.println("Invalid input");
                sc.nextLine(); 
            } 
            System.out.print("Do you want to see your balance? (yes/no): ");
            String response = sc.nextLine();
            if (response.equalsIgnoreCase("yes")) 
            {
                System.out.println("Your balance: " + account.getBalance());
            }
        }
    }
}
interface Account1 {
    void deposit(double amount);
    void withdraw(double amount);
    double getBalance();
}
abstract class BankAccount1 implements Account1 {
    protected double balance;

    public BankAccount1(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited: " + amount);
    }

    public void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient balance");
        } else {
            balance -= amount;
            System.out.println("Withdrawn successfully: " + amount);
        }
    }
   public double getBalance() {
        return balance;
    }
}
class SavingsAccount1 extends BankAccount1 {
    private final double interestRate;

    public SavingsAccount1(double balance, double interestRate) {
        super(balance);
        this.interestRate = interestRate;
    }

    public void addInterest() {
        double interest = getBalance() * interestRate / 100;
        deposit(interest);
        System.out.println("Interest added: " + interest);
    }
}
class CheckingAccount1 extends BankAccount1 {
    public CheckingAccount1(double balance) {
        super(balance);
    }
    public void withdraw(double amount) {
        super.withdraw(amount + 1.00);
    }
}


