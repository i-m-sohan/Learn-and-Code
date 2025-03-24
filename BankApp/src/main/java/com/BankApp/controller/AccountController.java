package com.BankApp.controller;


import com.BankApp.dto.MoneyTransferDTO;
import com.BankApp.helper.InputHelper;
import com.BankApp.model.Account;
import com.BankApp.service.AccountService;
import com.BankApp.service.TransactionService;

public class AccountController {

    private InputHelper inputHelper;
    private AccountService accountService;
    private TransactionService transactionService;

    public AccountController() {
        this.inputHelper = new InputHelper();
        this.accountService = new AccountService();
        this.transactionService = new TransactionService();
    }
    public AccountController(InputHelper inputHelper, AccountService accountService, TransactionService transactionService) {
        this.inputHelper = inputHelper;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    public void accountMainMenu(String username){
        while (true) {
            System.out.println("\n===== ACCOUNT MENU =====");
            System.out.println("1. Deposit Money");
            System.out.println("2. Withdraw Money");
            System.out.println("3. Check Balance");
            System.out.println("4. Transfer Money");
            System.out.println("5. View Transaction History");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");

            int choice = inputHelper.inputInt();
            int enteredPin;

            switch (choice) {
                case 1:
                    int depositAmount = inputHelper.inputInt("Deposit Amount : ");
                    enteredPin = inputHelper.inputInt("PIN :  ");
                    accountService.deposit(username,depositAmount,enteredPin);
                    break;
                case 2:
                    int withdrawAmount = inputHelper.inputInt("Withdraw Amount : ");
                    enteredPin = inputHelper.inputInt("PIN :  ");
                    accountService.withdraw(username,withdrawAmount,enteredPin);
                    break;
                case 3:
                    enteredPin = inputHelper.inputInt("PIN :  ");
                    accountService.checkBalance(username,enteredPin);
                    break;
                case 4:
                    String recipientAccountNumber = inputHelper.inputLine("Recipient Account Number :");
                    int amountToTransfer = inputHelper.inputInt("Amount  : ");
                    enteredPin = inputHelper.inputInt("PIN :  ");
                    MoneyTransferDTO moneyTransferDTO = new MoneyTransferDTO();
                    moneyTransferDTO.setReceiverAccountNumber(recipientAccountNumber);
                    moneyTransferDTO.setSenderUsername(username);
                    moneyTransferDTO.setAmount(amountToTransfer);
                    moneyTransferDTO.setPin(enteredPin);

                    accountService.moneyTransfer(moneyTransferDTO);
                    break;
                case 5:
                    transactionService.viewTransactionHistory(username);
                    break;
                case 6:
                    System.out.println("Logging out... Returning to Main Menu.");
                    return;
                default:
                    System.out.println("Invalid choice! Please enter a number between 1 and 6.");
            }
        }
    }
}
