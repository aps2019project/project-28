package Model.mediator;

import Controller.Game;
import Controller.menu.Battle;
import Controller.menu.Menu;
import Model.Map.Map;
import exeption.*;
public class OfflineBattleMediator implements BattleMediator {


    @Override
    public boolean init() {
        Battle.getMenu().setMap(Battle.getMenu().getGameMode().generateMap());
        try {
            Battle.getMenu().insert(Battle.getMenu().player[0].getDeck().getHero(), Battle.getMenu().getMap().getCell(Map.FIRST_HERO_X, Map.FIRST_HERO_Y));
            Battle.getMenu().insert(Battle.getMenu().player[1].getDeck().getHero(), Battle.getMenu().getMap().getCell(Map.SECOND_HERO_X, Map.SECOND_HERO_Y));
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("\n");
            if (Battle.getMenu().player[1] == null) System.err.println("player 1 is null");
            else if (Battle.getMenu().player[1].getDeck() == null) System.err.println("deck is null !");
            else if (Battle.getMenu().player[1].getDeck().getHero() == null) System.err.println("hero is null");
            else if (Battle.getMenu().getMap() == null) System.err.println("map is null !");
            else {
                try {
                    if (Battle.getMenu().getMap().getCell(Map.SECOND_HERO_X, Map.SECOND_HERO_Y) == null)
                        System.err.println("getCell is null");
                } catch (InvalidCellException ex) {
                    ex.printStackTrace();
                }
                System.out.println("\n\n\n");
            }
        } catch (InvalidCellException e) {
            e.printStackTrace();
        }
        return true;
    }
    @Override
    public void insert(int cardID, int x, int y){ }
    @Override
    public void select(int ID){}
    @Override
    public void move(int x, int y){}
    @Override
    public void attack(int cardID){}
    @Override
    public void useSpecialPower(int x, int y){}
    @Override
    public void useItem(int x, int y){}
    @Override
    public void endTurn(){}

    @Override
    public void handleBattleFinish() {
        Battle.getMenu().getGameMode().handleWin();
    }
}
