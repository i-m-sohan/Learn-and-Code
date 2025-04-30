import DAO.AccountDao;
import DAO.CardDAO;
import daoImp.AccountDaoImp;
import daoImp.CardDaoImp;
import exception.*;
import helper.InputHelper;
import model.Account;
import model.Card;
import service.ATMService;
import service.AuthService;
import util.ServerUtil;

public class AtmApp {

    private static void initiateApp(){
        AccountDao accountDao = new AccountDaoImp();

        Account account = new Account();
        account.setAccountNumber("ACC6739202");
        account.setBalance(9000);
        account.setCardNumber("47280372990");
        account.setDailyLimit(5000);
        account.setDailyWithdrawn(0);
        account.setCardNumber("617483498474");
        accountDao.insertAccount(account);

        CardDAO cardDAO = new CardDaoImp();
        Card card = new Card();
        card.setCardNumber("617483498474");
        card.setPin("1234");
        card.resetPinAttempts();
        cardDAO.insertCard(card);
    }

    private static void startSession(String enteredPin){
        InputHelper inputHelper = new InputHelper();

        String cardNumber = "617483498474";
        AccountDao accountDao = new AccountDaoImp();
        CardDAO cardDAO = new CardDaoImp();
        Card currentCard = cardDAO.getCardByNumber(cardNumber);
        Account currentAccount = accountDao.getAccountByCardNumber(cardNumber);
        AuthService authService = new AuthService();
        ATMService atmService = new ATMService();


        try{
            authService.authenticate(currentCard,enteredPin);
            double amountToWithdraw = inputHelper.inputDouble("Amount to Withdraw : ");
            atmService.withdraw(currentAccount,amountToWithdraw);
            ServerUtil.checkServerAvailability();
        }
        catch (ATMOutOfCashException atmOutOfCashException){
            System.out.println(atmOutOfCashException.getMessage());
        }
        catch(CardBlockedException cardBlockedException){
            System.out.println(cardBlockedException.getMessage());
        }
        catch (DailyLimitExceededException dailyLimitExceededException){
            System.out.println(dailyLimitExceededException.getMessage());
        }
        catch (InsufficientBalanceException insufficientBalanceException){
            System.out.println(insufficientBalanceException.getMessage());
        }
        catch (InvalidPinException invalidPinException){
            System.out.println(invalidPinException.getMessage());
        }
    }

    public static void main(String[] args){
        initiateApp();
        InputHelper inputHelper = new InputHelper();
        System.out.println("Card Detected.....\n");

        int choice;
        do {
            System.out.println("Enter PIN: ");
            String enteredPin = inputHelper.inputString();
            try {
                startSession(enteredPin);
            }
            catch(ServerUnavailableException serverUnavailableException){
                System.out.println(serverUnavailableException.getMessage());
                break;
            }
            choice = inputHelper.inputInt("Enter 0 to exit || Any other key to continue");
        }
        while(choice!=0);
    }
}
