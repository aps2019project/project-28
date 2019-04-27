package Model.card.spell;


import Controller.Game;
import Model.Map.* ;
import Model.account.Player;
import Model.card.hermione.Hermione;

public interface Action {
    public void deploy(Spell spell, Cell... cells);
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
            cell.getCardOnCell().setCanCounterattack(false);
        }
    }
}

class ActionBuffDistroyer implements Action {
    private static ActionBuffDistroyer obj;

    public static ActionBuffDistroyer getAction() {
        if (obj == null) obj = new ActionBuffDistroyer();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) {
        for (Cell cell : cells) {
            for (Buff buff : cell.getAppliedBuffs()) {
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
            card.setAttackPoint(card.getAttackPoint() + spell.perk);
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
            card.setHealthPoint(card.getHealthPoint() + spell.perk);
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
        ActionDisarm disarm = new ActionDisarm();
        ActionDisarm.getAction().deploy(spell, cells);
        ActionChangeAP addap = new ActionChangeAP();
        addap.deploy(spell, cells);
    }
}

class ActionPoison implements Action {
    private static ActionPoison obj;

    public static ActionPoison getAction() {
        if (obj == null) obj = new ActionPoison();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) {
        for (Cell cell : cells) {
            cell.setCardOnCell();
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
            cell.getCardOnCell().setHealthPoint(0);
            Player player = Game.battle.getPlayer();
            player.getHero().setHealthPoint(player.getHero().getHealthPoint() + mhp);
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
        cells[0].getCardOnCell().setHealthPoint(0);
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

    }
}

