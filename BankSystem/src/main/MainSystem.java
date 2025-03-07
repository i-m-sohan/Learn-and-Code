package main;

public class MainSystem {
	public static void main(String[] args) {
		BankAccount bankAccount = new CurrentAccount("IND7671028",2000);
		bankAccount.withdraw(3000);
		bankAccount.showBalance();
	}
}
