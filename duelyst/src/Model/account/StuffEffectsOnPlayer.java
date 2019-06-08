package Model.account;

import Controller.menu.Battle;
import Model.card.hermione.Hermione;
import Model.card.hermione.Minion;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.BuffActionHolly;
import exeption.InvalidCellException;

public class StuffEffectsOnPlayer {
    private int manaTheriac = 0;
    private int maxManaTheriac = 0;
    private boolean hasAssasinationDagger = false;

    private boolean baptism = false ;



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



    public void handleMinionDeploy(Minion card) {
        if (hasAssasinationDagger) {
            Battle.getMenu().getEnemyPlayer().getDeck().getHero().changeHealthPoint(-1);
        }
        if (baptism){
            Buff buff = new Buff(2 , true , BuffActionHolly.getBuffAction()) ;
            try {
                buff.deploy(Battle.getMenu().getPlayer(), card);
            }catch (InvalidCellException ignored){
                System.err.println("Unexpected InvalidCellException while deploying holyBuff on Baptism (StuffEffectsOnPlayer)");
            }
        }
    }

    public void setBaptism(boolean baptism) {
        this.baptism = baptism;
    }

}
