package Model.mediator;

import exeption.*;


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
