package main;

public class MainSystem {
	public static void main(String[] args) {
		BankAccount bankAccount1 = new CurrentAccount("IND7671028",2000);
		bankAccount1.withdraw(3000);
		bankAccount1.showBalance();
	}
}
