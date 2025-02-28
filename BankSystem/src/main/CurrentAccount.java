package main;

public class CurrentAccount extends BankAccount {
	
	private static final double OVERDRAFT_LIMIT = 10000;
	
    public CurrentAccount(String accountNumber, double balance) {
        super(accountNumber, balance);
    }
    
    @Override
    public void withdraw(double amount) {
        if (isAmountValid(amount)) {
            balance -= amount;
            System.out.println(amount + " withdrawn from your Current Account");
        }
        else {
			System.out.println("Error : Please Enter a Valid Amount"); 
        }
    }
    
    private boolean isAmountValid(double amount) {
		if(amount > 0 && amount <= balance + OVERDRAFT_LIMIT) {
			return true;
		}
		else {
			return false;
		}
	}
    
     @Override
     public void showBalance() {
    	if(balance>=0) {
    		System.out.println("Account Balance : " + balance);
    	}
    	else {
    		System.out.println("Account is in Overdraft by : " + (-balance));
    	}
 	}
}