package Controller.menu;

import Controller.Game;
import Controller.menu.Graphics.FXMLController.LeaderBoardHavingFXMLC;
import Model.account.Account;
import Model.account.Deck;
import View.Listeners.OnDeckSelectorClickedListener;
import View.Listeners.OnLeaderBoardClickedListener;
import View.MenuHandler;
import exeption.InvalidAccountException;
import exeption.WrongPassException;

import java.util.ArrayList;

public class MultiPlayerModeMenu extends Menu implements DeckSelectorHavingMenu {
    private static MultiPlayerModeMenu menu;
    private OnDeckSelectorClickedListener deckSelectorListener ;
    private OnLeaderBoardClickedListener leaderBoardPresenters;

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
        Account account = Account.getAccount(username);
        if (account.getPassword().equals(password)) {
            Game.setFirstAccount(this.account);
            Game.setSecondAccount(account);
            try {
                showDeckSelector(this.account);
            }catch(Exception e){
                e.printStackTrace();
            }
        } else {
            throw new WrongPassException();
        }
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
        deckSelectorListener.show(account , this , "");
    }

    @Override
    public void selectDeck(Account account, Deck deck) {
        account.getCollection().setMainDeck(deck);
        if (account != this.account) MenuHandler.enterMenu(ChooseBattleModeMenu.getMenu().enter());
        else showDeckSelector(Game.getAccount(1));
    }

    public void addLeaderBoardClickedListener(OnLeaderBoardClickedListener presenter) {
        this.leaderBoardPresenters = presenter ;
    }

    public void showLeaderBoard() {
        Account.sort();
        ArrayList<Account> accounts = new ArrayList<>() ;
        for (Account account : Account.getAccounts()){
            if (account != menu.getAccount()){
                accounts.add(account);
            }
        }

        leaderBoardPresenters.show(accounts , (LeaderBoardHavingFXMLC)getGraphic().getController());

    }
}
