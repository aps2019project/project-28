package Model.account;

import Model.card.Card;

import java.util.ArrayList;

public class Hand {

    private ArrayList<Card> deck;
    private static final int SIZE = 5;
    private Card[] cards = new Card[SIZE];
    private Card nextCard;

    public Hand(Deck deck){
        this.deck = deck.getCards();
        for(int i = 0; i < SIZE; i++){
            this.cards[i] = this.deck.get(i);
        }
        this.nextCard = this.deck.get(SIZE);
    }

    private void addCard(){
        if(nextCard != null){
            for (int i = 0; i < SIZE; i++){
                if(cards[i] == null){
                    cards[i] = nextCard;
                    setNextCard();
                    return;
                }
            }
        }
    }

    private void setNextCard() {
        if(deck.indexOf(nextCard) + 1 < Deck.CARD_SIZE){
            nextCard = deck.get(deck.indexOf(nextCard) + 1 );
        }
        else {
            nextCard = null;
        }
    }

    private void removeCard(Card card){
        for (int i = 0; i < SIZE; i++){
            if(cards[i] == card){
                cards[i] = null;
                return;
            }
        }
    }

    public void handleHand(Card card){
        removeCard(card);
        addCard();
    }
}
