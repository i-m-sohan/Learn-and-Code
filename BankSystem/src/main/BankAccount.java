package main;

public abstract class BankAccount {
	protected String accountNumber;
	protected double balance;
	
	BankAccount(String accountNumber, double balance){
		this.accountNumber = accountNumber;
		this.balance = balance;
	}
	
	public void deposit(double amount) {
		if(amount>0) {
			balance += amount;
		}
	}
	public abstract void withdraw(double amount);
	
	public void showAccountNumber() {
		System.out.println("Account Number  : " +accountNumber);
	}
	public void showBalance() {
		System.out.println("Account Balance : " + balance);
	}
}

