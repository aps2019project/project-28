package Controller.menu;

import Controller.Game;
import Model.account.AI;
import exeption.*;

public class SinglePlayerModeMenu extends Menu {
    private static SinglePlayerModeMenu menu;
    private SinglePlayerModeMenu(String name) {
        super(name);
    }
    public static SinglePlayerModeMenu getMenu(){
        if(SinglePlayerModeMenu.menu==null){
            SinglePlayerModeMenu.menu=new SinglePlayerModeMenu("SinglePlayerModeMenu");
        }
        return menu;
    }

    @Override
    public void init(Menu parentMenu) {
        super.init(parentMenu);
        try {
            Game.accounts[1]=new AI(Game.accounts[0].getStoryModeSPX());
        } catch (FullDeckException | DeckAlreadyHasThisCardException | InvalidDeckException | DeckAlreadyHasAHeroException | DeckAlreadyHasThisItemException ignored) {}
    }
}

