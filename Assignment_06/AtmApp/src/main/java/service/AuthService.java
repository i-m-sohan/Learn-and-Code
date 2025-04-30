package service;

import DAO.CardDAO;
import exception.CardBlockedException;
import exception.InvalidPinException;
import model.Card;

public class AuthService {
    private CardDAO cardDAO;

    public AuthService(){
    }
    public AuthService(CardDAO cardDAO){
        this.cardDAO = cardDAO;
    }

    public void authenticate(Card card, String enteredPin){
        if (!card.getPin().equals(enteredPin)) {
            card.incrementPinAttempts();
            if (card.isBlocked()) {
                throw new CardBlockedException("Card is now blocked.");
            }
            throw new InvalidPinException("Incorrect PIN.");
        }

        card.resetPinAttempts();
    }
}
