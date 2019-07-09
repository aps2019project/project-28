package Model.card.spell;

import Model.card.spell.SpecialPowerActions.*;
import Model.card.spell.SpellAction.Action;

import java.util.HashSet;
import java.util.Set;

public class SpecialPower extends Spell {
    private static Set<Action> spActions = new HashSet<>();
    static{
        spActions.add(SPActionAP.getSpecialPower());
        spActions.add(SPActionCombo.getSpecialPower());
        spActions.add(SPActionDisBuffer.getSpecialPower());
        spActions.add(SPActionHolyBuffDiverter.getSpecialPower());
        spActions.add(SPActionHPBuff.getSpecialPower());
        spActions.add(SPActionNextTurnDamager.getSpecialPower());
        spActions.add(SPActionPersianChamp.getSpecialPower());
        spActions.add(SPActionPoisoner.getSpecialPower());
        spActions.add(SPActionStunWhenAttacking.getSpecialPower());
        spActions.add(SPActionTooranianSpy.getSpecialPower());
        spActions.add(SPActionUnholyer.getSpecialPower());
        spActions.add(SPActionWhiteWolf.getSpecialPower());
    }

    public SpecialPower(String name, int price, int manaPoint, int duration , int perk, String info, Target target, Action action) {
        super(name, price, manaPoint, duration, perk, info, target, action);
    }

    public static Set<Action> getSpActions() {
        return spActions;
    }
}
