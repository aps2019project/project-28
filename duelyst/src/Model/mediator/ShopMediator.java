package Model.mediator;

import Model.account.Collection;
import Model.account.Shop;
import Model.card.Card;
import Model.card.hermione.Hermione;
import Model.item.Usable;
import exeption.InvalidCardException;
import exeption.InvalidItemException;

import java.util.Map;

public interface ShopMediator {

//    void init(Collection collection, Map<Integer, Integer> cards, Map<Integer, Integer> items);

    void init();
    boolean hasCard(String name) throws Exception;

    boolean hasItem(String name) throws Exception;

    Card getCard(String name) throws Exception;

    Usable getItem(String name) throws Exception;


    boolean buy(String name) throws Exception;


    void sell(String name) throws Exception;

    void search(String name) throws InvalidCardException, InvalidItemException;

    Collection getCollection() throws Exception;

    int getRemain(String name);

    void addCard(Card card);
}
