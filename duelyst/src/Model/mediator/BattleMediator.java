package Model.mediator;

import Controller.Game;
import Controller.menu.Battle;
import Controller.menu.MainMenu;
import Controller.menu.Menu;
import Controller.menu.SignInMenu;
import Model.Map.Cell;
import Model.Map.Map;
import Model.account.Account;
import Model.account.Deck;
import Model.card.Card;
import Model.card.hermione.Hermione;
import Model.card.hermione.Hero;
import Model.card.hermione.Minion;
import Model.card.hermione.SPATime;
import View.MenuHandler;
import exeption.*;

import java.util.ArrayList;

public interface BattleMediator {

    boolean init() ;

    void insert(int cardID, int x, int y) throws InvalidCardException, NotEnoughManaException, DestinationIsFullException, InvalidCellException;
    void select(int ID) throws InvalidCardException, InvalidItemException;
    void move(int x, int y) throws NoCardHasBeenSelectedException, CardCantBeMovedException, MoveTrunIsOverException, DestinationOutOfreachException, InvalidCellException, DestinationIsFullException;
    void attack(int cardID) throws NoCardHasBeenSelectedException, InvalidCardException, DestinationOutOfreachException, CantAttackException, InvalidCellException ;
    void useSpecialPower(int x, int y) throws InvalidCellException, CantSpecialPowerCooldownException, InvalidCardException ;
    void useItem(int x, int y) throws InvalidCellException, NoItemHasBeenSelectedException;
    void endTurn();

    void handleBattleFinish();

}
