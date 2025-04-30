package daoImp;

import DAO.CardDAO;
import model.Card;

import java.util.HashMap;
import java.util.Map;

public class CardDaoImp implements CardDAO {
    private static final Map<String, Card> cardStore = new HashMap<>();

    @Override
    public Card getCardByNumber(String number){
        return cardStore.get(number);
    }

    public void insertCard(Card card){
        cardStore.put(card.getCardNumber(),card);
    }
}
