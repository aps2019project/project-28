package Controller.menu;

import Controller.Game;
import Controller.menu.Graphics.FXMLController.LeaderBoardHavingFXMLC;
import Model.account.Account;
import Model.account.Deck;
import Model.mediator.MultiPlayerMenuMediator;
import View.Listeners.OnDeckSelectorClickedListener;
import View.Listeners.OnLeaderBoardClickedListener;
import View.MenuHandler;
import exeption.InvalidAccountException;
import exeption.WrongPassException;
import network.ChatMSG;

import java.io.IOException;
import java.util.ArrayList;

public class MultiPlayerModeMenu extends Menu implements DeckSelectorHavingMenu {
    private static MultiPlayerModeMenu menu;
    private OnDeckSelectorClickedListener deckSelectorListener ;

    private MultiPlayerMenuMediator mediator;

    private MultiPlayerModeMenu(String name) {
        super(name);
    }


    public static MultiPlayerModeMenu getMenu(){
        if(MultiPlayerModeMenu.menu==null){
            MultiPlayerModeMenu.menu=new MultiPlayerModeMenu("MultiPlayer");
        }
        return menu;
    }

    public void selectUser(String username,String password) throws InvalidAccountException, WrongPassException {
        if(this.account.getCollection().getMainDeck()==null)
            System.out.println("throw new MainDeckNotSelectedException()------------------------------------------");
        System.out.println("this.account.getCollection().getMainDeck() = " + this.account.getCollection().getMainDeck());
        try {
            System.err.println();
            this.mediator.selectUser(username,password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancel(){
        this.mediator.cancel();
    }

    public void sendMessage(String text){
        this.mediator.sendMessage(text);
    }

    public ArrayList<ChatMSG> getChats(){
        return this.mediator.getChats();
    }

    @Override
    public void help() {
        super.help();
        System.out.println("4)select user [username] [password]");
    }

    @Override
    public void setDeckSelectorListener(OnDeckSelectorClickedListener ds) {
        deckSelectorListener = ds ;
    }

    @Override
    public void showDeckSelector(Account account) {
        deckSelectorListener.show(account , this , "" , true);
    }

    @Override
    public void selectDeck(Account account, Deck deck) {
        account.getCollection().setMainDeck(deck);
    }

    public void setMediator(MultiPlayerMenuMediator mediator) {
        this.mediator = mediator;
    }
}
