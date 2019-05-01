package Model.card.spell;


import Controller.Game;
import Model.Map.*;
import Model.account.Player;
import Model.card.hermione.Hermione;

public interface Action {
    void deploy(Spell spell, Cell... cells);
}

class ActionDisarm implements Action {
    private static ActionDisarm obj;

    public static ActionDisarm getAction() {
        if (obj == null) obj = new ActionDisarm();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) {
        for (Cell cell : cells) {
            Buff buff = new Buff(spell.duration, false, BuffActionDisarm.getBuffAction());
            buff.deploy(Game.battle.getPlayer(), cell.getCardOnCell());
        }
    }
}

class ActionDispel implements Action {
    private static ActionDispel obj;

    public static ActionDispel getAction() {
        if (obj == null) obj = new ActionDispel();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) {
        for (Cell cell : cells) {
            for (Buff buff : cell.getCardOnCell().getAppliedBuffs()) {
                if (buff.isItPositive() ^ buff.getPlayer() == Game.battle.getPlayer()) {
                    buff.destroy();
                }
            }
        }
    }
}

class ActionChangeAP implements Action {
    private static ActionChangeAP obj;

    public static ActionChangeAP getAction() {
        if (obj == null) obj = new ActionChangeAP();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) {
        for (Cell cell : cells) {
            Hermione card = cell.getCardOnCell();
            Buff buff = new Buff(spell.duration, spell.getPerk() > 0, BuffActionAP.getBuffAction());
            buff.deploy(Game.battle.getPlayer(), cell.getCardOnCell());
        }
    }
}

class ActionChangeHP implements Action {
    private static ActionChangeHP obj;

    public static ActionChangeHP getAction() {
        if (obj == null) obj = new ActionChangeHP();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) {
        for (Cell cell : cells) {
            Hermione card = cell.getCardOnCell();
            Buff buff = new Buff(spell.duration, spell.getPerk() > 0, BuffActionHP.getBuffAction());
            buff.deploy(Game.battle.getPlayer(), cell.getCardOnCell());
        }
    }
}

class ActionApplyFirecell implements Action {
    private static ActionApplyFirecell obj;

    public static ActionApplyFirecell getAction() {
        if (obj == null) obj = new ActionApplyFirecell();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) {
        for (Cell cell : cells) {
            cell.setCellAffect(CellAffects.fire);
        }
    }
}

class ActionPoisonCell implements Action {
    private static ActionPoisonCell obj;

    public static ActionPoisonCell getAction() {
        if (obj == null) obj = new ActionPoisonCell();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) {
        for (Cell cell : cells) {
            cell.setCellAffect(CellAffects.poison);
        }
    }
}

class ActionDisarmAndAddAP implements Action {
    private static ActionDisarmAndAddAP obj;

    public static ActionDisarmAndAddAP getAction() {
        if (obj == null) obj = new ActionDisarmAndAddAP();
        return obj;
    }

    //has duration
    @Override
    public void deploy(Spell spell, Cell... cells) {
        spell.decreaseDuration();
        ActionDisarm.getAction().deploy(spell, cells);
        ActionChangeAP.getAction().deploy(spell, cells);
    }
}

class ActionAllPoison implements Action {
    private static ActionAllPoison obj;

    public static ActionAllPoison getAction() {
        if (obj == null) obj = new ActionAllPoison();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) {
        for (Cell cell : cells) {
            //cell.getCardOnCell().changeHealthPoint(-1);
            Buff poisonBuff = new Buff(4, false, BuffActionPoison.getBuffAction());
            poisonBuff.deploy(Game.battle.getPlayer(), cell.getCardOnCell());

        }
    }
}

class ActionHealthWithProfit implements Action {
    private static ActionHealthWithProfit obj;

    public static ActionHealthWithProfit getAction() {
        if (obj == null) obj = new ActionHealthWithProfit();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) {
        //TODO
    }
}

class ActionGhazaBokhor implements Action {
    private static ActionGhazaBokhor obj;

    public static ActionGhazaBokhor getAction() {
        if (obj == null) obj = new ActionGhazaBokhor();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) {
        for (Cell cell : cells) {
            Buff buff = new Buff(-1, true, BuffActionAP.getBuffAction() , cell.getCardOnCell().getHealthPoint());
            buff.deploy(Game.battle.getPlayer(), cell.getCardOnCell());
        }
    }
}

class ActionSacrifice implements Action {
    private static ActionSacrifice obj;

    public static ActionSacrifice getAction() {
        if (obj == null) obj = new ActionSacrifice();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) {
        for (Cell cell : cells) {
            int mhp = cell.getCardOnCell().getHealthPoint();
            cell.getCardOnCell().die();
            Player player = Game.battle.getPlayer();
            player.getDeck().getHero().setHealthPoint(player.getDeck().getHero().getHealthPoint() + mhp);
        }
    }
}

class ActionKillMinion implements Action {
    private static ActionKillMinion obj;

    public static ActionKillMinion getAction() {
        if (obj == null) obj = new ActionKillMinion();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) {
        cells[0].getCardOnCell().die();
    }
}

class ActionStun implements Action {
    private static ActionStun obj;

    public static ActionStun getAction() {
        if (obj == null) obj = new ActionStun();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) {
        for (Cell cell : cells) {
            Buff buff = new Buff(spell.duration, false, BuffActionStun.getBuffAction());
            buff.deploy(Game.battle.getPlayer(), cell.getCardOnCell());
        }
    }
}

