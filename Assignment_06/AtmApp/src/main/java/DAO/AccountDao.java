package DAO;

import model.Account;

public interface AccountDao {
    public abstract Account getAccountByCardNumber(String cardNumber);
    public abstract void insertAccount(Account account);
}
