package Model.account;

import Model.card.Card;
import Model.card.hermione.Hero;
import View.Listeners.OnHandPresentedListener;
import exeption.DeckIsEmptyException;
import exeption.HandFullException;
import exeption.InvalidCardException;

import java.util.ArrayList;
import java.util.Collections;

public class Hand {

    private ArrayList<Card> deck = new ArrayList<>();
    private static ArrayList<OnHandPresentedListener> handPresenters = new ArrayList<>();
    public static final int SIZE = 5;
    private ArrayList<Card> cards = new ArrayList<>();
    private Card nextCard;

    public Hand(Deck deck) {
        for (Card card:
             deck.getCards()) {
            if(card instanceof Hero){
                continue;
            }
            this.deck.add(card);
        }
        for (int i = 0; i < SIZE; i++) {
            this.cards.add(this.deck.get(i));
        }
        this.nextCard = this.deck.get(SIZE);
    }

    public void updateHand() throws DeckIsEmptyException, HandFullException {
        if(cards.size() < SIZE){
            this.addCard();
        }
    }

    private void addCard() throws DeckIsEmptyException, HandFullException {
        if (nextCard != null) {
            if(cards.size() < SIZE){
                cards.add(nextCard);
                setNextCard();
                return;
            }
            throw new HandFullException();
        }
        throw new DeckIsEmptyException();
    }

    private void setNextCard() {
        if (deck.indexOf(nextCard) + 1 < Deck.CARD_SIZE) {
            this.nextCard = deck.get(deck.indexOf(nextCard) + 1);
        } else {
            this.nextCard = null;
        }
    }

    private void removeCard(Card card) {
        if(cards.size() > 0){
            cards.remove(card);
        }
    }

    public void handleHand(Card card){
        removeCard(card);
    }

    public static ArrayList<OnHandPresentedListener> getHandPresenters() {
        return (handPresenters);
    }

    public static void addOnHandPresentedListener(OnHandPresentedListener handPresenter) {
        Hand.handPresenters.add(handPresenter);
    }

    public Card getCard(int cardID) throws InvalidCardException {
//        System.err.println("you want this" + cardID);
        for (Card card : this.cards) {
//            System.err.println("we have this :"+card.getName()+card.getCardID());
            if (card.getCardID() == cardID) return card;
        }
        throw new InvalidCardException();
    }

    public Card getNextCard() {
        return nextCard;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
}
