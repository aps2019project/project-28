package Controller.menu;

import Controller.Game;
import Model.Map.Map;
import Model.mediator.BattleMediator;
import Model.mediator.NetworkMediator;
import exeption.*;
import network.Message;

public class OnlineBattleMediator implements BattleMediator {

    @Override
    public void insert(int cardID, int x, int y){
        sendPlayerMove("insert card " + cardID + " in " + x + " " + y);
    }
    @Override
    public void select(int ID) {
        sendPlayerMove("select "+ID);
    }
    @Override
    public void move(int x, int y){
        sendPlayerMove("move to "+x+" "+y);
    }
    @Override
    public void attack(int cardID) {
        sendPlayerMove("attack "+cardID);
    }
    @Override
    public void useSpecialPower(int x, int y){
        sendPlayerMove("Use special power "+x+" "+y);
    }
    @Override
    public void useItem(int x, int y){
        sendPlayerMove("use "+x+" "+y);
    }
    @Override
    public void endTurn() {
        sendPlayerMove("end turn");
    }


    @Override
    public boolean init() {
        Message message=new Message("init");
        Game.getBattleClient().write(message);

        message = Game.getBattleClient().read();

        try {
            if (!NetworkMediator.isValid(message)) return false;
        } catch (Exception e) {
             return false;
        }

        /*
        * this message contains the map
        * */
        Battle.getMenu().setMap((Map) message.getCarry().get(0));
        return true;
    }

    @Override
    public void handleBattleFinish() {
        Message message=new Message("finish");
        Game.getBattleClient().write(message);

    }

    private void sendPlayerMove(String playerMove) {
        Message message = new Message("playerMove");
        message.addCarry(playerMove);
        System.out.println("message.getText() = " + message.getCarry().get(0));
        Game.getBattleClient().write(message);
    }
}
