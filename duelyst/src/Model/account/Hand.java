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

    private ArrayList<Card> deck;
    private static ArrayList<OnHandPresentedListener> handPresenters = new ArrayList<>();
    private static final int SIZE = 5;
    private Card[] cards = new Card[SIZE];
    private Card nextCard;

    public Hand(Deck deck) {
        int j = 0;
        this.deck = deck.getCards();
        for (int i = 0; i < SIZE; i++) {
            if(!(cards[i] instanceof Hero)) {
                this.cards[j++] = this.deck.get(i);
            }
        }
        if(!(cards[SIZE] instanceof Hero)) {
            this.nextCard = this.deck.get(SIZE);
        }
    }

    public void updateHand() throws DeckIsEmptyException, HandFullException {
        for (int i = 0; i < SIZE; i++) {
            if (cards[i] == null) {
                this.addCard();
            }
        }
    }

    private void addCard() throws DeckIsEmptyException, HandFullException {
        if (nextCard != null) {
            for (int i = 0; i < SIZE; i++) {
                if (cards[i] == null) {
                    cards[i] = nextCard;
                    setNextCard();
                    return;
                }
            }
            throw new HandFullException();
        }
        throw new DeckIsEmptyException();
    }

    private void setNextCard() {
        if (deck.indexOf(nextCard) + 1 < Deck.CARD_SIZE) {
            Card card = deck.get(deck.indexOf(nextCard) + 1);
            if(!(card instanceof Hero)){
                nextCard = card;
            }
        } else {
            nextCard = null;
        }
    }

    private void removeCard(Card card) {
        for (int i = 0; i < SIZE; i++) {
            if (cards[i] == card) {
                cards[i] = null;
                return;
            }
        }
    }

    public void handleHand(Card card) throws DeckIsEmptyException, HandFullException {
        removeCard(card);
        addCard();
    }

    public static ArrayList<OnHandPresentedListener> getHandPresenters() {
        return (handPresenters);
    }

    public static void addOnHandPresentedListener(OnHandPresentedListener handPresenter) {
        Hand.handPresenters.add(handPresenter);
    }

    public Card getCard(int cardID) throws InvalidCardException {
        for (Card card : this.cards) {
            if (card.getCardID() == cardID) return card;
        }
        throw new InvalidCardException();
    }

    public Card getNextCard() {
        return nextCard;
    }

    public Card[] getCards() {
        return cards;
    }
}
