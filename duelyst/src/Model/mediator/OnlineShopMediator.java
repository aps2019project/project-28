package Model.mediator;

import Controller.Game;
import Model.account.Collection;
import Model.card.Card;
import Model.item.Usable;
import exeption.InvalidCardException;
import exeption.InvalidItemException;
import network.Message;

public class OnlineShopMediator implements ShopMediator {
    @Override
    public void init() {

    }

    @Override
    public boolean hasCard(String name) throws Exception {
        Message message=new Message("has card");
        message.addCarry(name);

        Game.getClient().write(message);

        message = Game.getClient().read();
        if (!NetworkMediator.isValid(message)) throw new Exception();

        return (boolean) message.getCarry().get(0);
    }

    @Override
    public boolean hasItem(String name) throws Exception {
        Message message=new Message("has item");
        message.addCarry(name);

        Game.getClient().write(message);

        message = Game.getClient().read();
        if (!NetworkMediator.isValid(message)) throw new Exception();

        return (boolean) message.getCarry().get(0);

    }

    @Override
    public Card getCard(String name) throws Exception {
        Message message=new Message("get card");
        message.addCarry(name);

        Game.getClient().write(message);

        message = Game.getClient().read();
        if (!NetworkMediator.isValid(message)) throw new Exception();

        return (Card) message.getCarry().get(0);
    }

    @Override
    public Usable getItem(String name) throws Exception {
        Message message=new Message("get item");
        message.addCarry(name);

        Game.getClient().write(message);

        message = Game.getClient().read();
        if (!NetworkMediator.isValid(message)) throw new Exception();

        return (Usable) message.getCarry().get(0);

    }

    @Override
    public boolean buy(String name) throws Exception {

        Message message=new Message("buy");
        message.addCarry(name);

        Game.getClient().write(message);

        message = Game.getClient().read();
        if (!NetworkMediator.isValid(message)) throw new Exception();

        return (boolean) message.getCarry().get(0);

    }

    @Override
    public void sell(String name) throws Exception {
        Message message=new Message("sell");
        message.addCarry(name);

        Game.getClient().write(message);

        message = Game.getClient().read();
        if (!NetworkMediator.isValid(message)) throw new Exception();

    }

    @Override
    public void search(String name) throws InvalidCardException, InvalidItemException {
        // TODO: 7/6/19 bebin che kar bayad kard
    }

    @Override
    public Collection getCollection() throws Exception {
        Message message=new Message("get collection");

        Game.getClient().write(message);

        message = Game.getClient().read();
        if (!NetworkMediator.isValid(message)) throw new Exception();

        return (Collection) message.getCarry().get(0);
    }

    @Override
    public int getRemain(String name) {
        Message message=new Message("get Remain");
        message.addCarry(name);

        Game.getClient().write(message);

        message = Game.getClient().read();
        try {
            if (!NetworkMediator.isValid(message))return 0;
        } catch (Exception e) {
            return 0;
        }
        return (int) message.getCarry().get(0);
    }
}
