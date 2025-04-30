package model;

public class Card {
    private String cardNumber;
    private String pin;
    private int pinAttempts = 0;
    private boolean blocked = false;

    public Card(){
    }

    public Card(String cardNumber, String pin) {
        this.cardNumber = cardNumber;
        this.pin = pin;
    }

    public void setCardNumber(String cardNumber){
        this.cardNumber = cardNumber;
    }
    public String getCardNumber() { return cardNumber; }

    public void setPin(String pin){
        this.pin = pin;
    }
    public String getPin() { return pin; }

    public boolean isBlocked() { return blocked; }
    public int getPinAttempts() { return pinAttempts; }

    public void incrementPinAttempts() {
        pinAttempts++;
        if (pinAttempts >= 3) {
            blocked = true;
        }
    }

    public void resetPinAttempts() {
        pinAttempts = 0;
    }
}
