package daoImp;

import DAO.AccountDao;
import model.Account;

import java.util.HashMap;
import java.util.Map;

public class AccountDaoImp implements AccountDao{
    private static final Map<String, Account> accountStore = new HashMap<>();

    @Override
    public Account getAccountByCardNumber(String cardNumber){
        return accountStore.get(cardNumber);
    }

    public void insertAccount(Account account){
        accountStore.put(account.getCardNumber(),account);
    }
}
