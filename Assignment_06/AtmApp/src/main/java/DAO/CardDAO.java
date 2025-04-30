package DAO;

import model.Card;

public interface CardDAO {
    public abstract Card getCardByNumber(String number);
    public abstract void insertCard(Card card);
}
