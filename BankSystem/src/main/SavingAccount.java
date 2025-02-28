package main;

public class SavingAccount extends BankAccount {
	private static final double WITHDRAWAL_LIMIT = 10000;
	
	public SavingAccount(String accountNumber, double balance) {
		super(accountNumber,balance);
	}
	
	@Override
	public void withdraw(double amount) {
		if(isAmountValid(amount)) {
			balance -= amount;
			System.out.println(amount+" withdrawn from your Saving account");
		}
		else {
			System.out.println("Error : Please Enter a Valid Amount"); 
		}
	}
	
	private boolean isAmountValid(double amount) {
		if(amount > 0 && amount <= balance && amount < WITHDRAWAL_LIMIT) {
			return true;
		}
		else {
			return false;
		}
	}
}
