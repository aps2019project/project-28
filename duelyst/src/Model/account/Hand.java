package Model.account;

import Model.card.Card;
import View.Listeners.OnHandPresentedListener;
import exeption.DeckIsEmptyException;
import exeption.HandFullException;
import exeption.InvalidCardException;

import java.util.ArrayList;
import java.util.Collections;

public class Hand {

    private ArrayList<Card> deck;
    private ArrayList<OnHandPresentedListener> handPresenters = new ArrayList<>();
    private static final int SIZE = 5;
    private Card[] cards = new Card[SIZE];
    private Card nextCard;

    public Hand(Deck deck) {
        this.deck = deck.getCards();
        for (int i = 0; i < SIZE; i++) {
            this.cards[i] = this.deck.get(i);
        }
        this.nextCard = this.deck.get(SIZE);
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
            nextCard = deck.get(deck.indexOf(nextCard) + 1);
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

    public ArrayList<OnHandPresentedListener> getHandPresenters() {
        return (ArrayList<OnHandPresentedListener>) Collections.unmodifiableList(handPresenters);
    }

    public void addOnHandPresentedListener(OnHandPresentedListener handPresenter) {
        this.handPresenters.add(handPresenter);
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
