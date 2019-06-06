package Model.account;

import Controller.menu.Battle;

public class StuffEffectsOnPlayer {
    private int manaTheriac = 0;
    private int maxManaTheriac = 0;
    private boolean hasAssasinationDagger = false;

    public void setManaTheriac(int hasManaTheriac) {
        this.manaTheriac = hasManaTheriac;
    }
    public void setMaxManaTheriac(int maxManaTheriac) {
        this.maxManaTheriac += maxManaTheriac;
    }
    public void setHasAssasinationDagger(boolean hasAssasinationDagger) {
        this.hasAssasinationDagger = hasAssasinationDagger;
    }

    public int getManaTheriacs(){
        int a = maxManaTheriac + manaTheriac ;
        manaTheriac = 0 ;
        return a ;
    }

    public int getManaTheriac() {
        return manaTheriac;
    }



    public void handleMinionDeploy() {
        if (hasAssasinationDagger) {
            Battle.getMenu().getEnemyPlayer().getDeck().getHero().changeHealthPoint(-1);
        }
    }
}
