package Model.card.spell;

import Model.Graphics.SpellGraphics;
import Model.Primary;
import Model.account.player.Player;
import Model.card.Card;
import Model.Map.*;
import Model.card.spell.SpellAction.*;
import exeption.InvalidCardException;
import exeption.InvalidCellException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class Spell extends Card {
    private static Set<Action> spellActions = new HashSet<>() ;
    protected static Set<Target> targets = new HashSet<>() ;
    static {
        //actions
        {
            spellActions.add(ActionDisarm.getAction());
            spellActions.add(ActionVoid.getAction());
            spellActions.add(ActionChangeAPBuff.getAction());
            spellActions.add(ActionChangeHPBuff.getAction());
            spellActions.add(ActionDeployPoison.getAction());
            spellActions.add(ActionDeployHollyBuff.getAction());
            spellActions.add(ActionStun.getAction());
            spellActions.add(ActionDispel.getAction());
            spellActions.add(ActionDispelPositives.getAction());
            spellActions.add(ActionDispelNegatives.getAction());
            spellActions.add(ActionApplyFirecell.getAction());
            spellActions.add(ActionHollyCell.getAction());
            spellActions.add(ActionCombo.getAction());
            spellActions.add(ActionPoisonCell.getAction());
            spellActions.add(ActionKillMinion.getAction());
            spellActions.add(ActionHealthWithProfit.getAction());
            spellActions.add(ActionSacrifice.getAction());
            spellActions.add(ActionGhazaBokhor.getAction());
        }
    }

    private ArrayList<Spell> activeSpells = new ArrayList<>() ;
    protected Target target;
    private Cell[] targetCells = new Cell[Map.WHIDTH *Map.HEIGHT];
    protected ArrayList<Action> actions = new ArrayList<>();
    private ArrayList<Integer> perks = new ArrayList<>();
    private ArrayList<Integer> durations = new ArrayList<>();
    private SpellGraphics spellGraphics = new SpellGraphics();

    public Spell(String name, int price , int manaPoint, int duration , int perk , String info, Target target, Action action ) {
        super( name, price, manaPoint, info);
        this.durations.add(duration) ;
        this.perks.add(perk) ;
        this.actions.add(action);
        this.target = target;
        targets.add(target);
    }

    //custom spell :
    public Spell(String name, int price , int manaPoint, ArrayList<Integer> durations , ArrayList<Integer> perks ,
                 String info, ArrayList<Action> actions , Cell ... targetCells) {
        super( name, price, manaPoint, info);
        this.durations = durations ;
        this.perks = perks  ;
        this.actions = actions ;
        this.target = null;
        this.targetCells = targetCells ;
    }

    public Spell(String name, int price , int manaPoint, ArrayList<Integer> durations , ArrayList<Integer> perks ,
                 String info, ArrayList<Action> actions , ArrayList<Cell> targetCells) {
        super( name, price, manaPoint, info);
        this.durations = durations ;
        this.perks = perks  ;
        this.actions = actions ;
        this.target = null;
        this.targetCells = targetCells.toArray(this.targetCells) ;
    }



    public void addAction(Action action , int perk , int duration){
        this.actions.add(action) ;
        this.perks.add(perk);
        this.durations.add(duration);
    }


    public void setTarget(Target target) {
        this.target = target;
    }

    public Target getTarget() {
        return target;
    }

    public Target getTargetClass() {
        return this.target.getTargetClass();
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public int getDuration(int i) {
        if (i < durations.size() )
            return durations.get(i);
        else return 0 ;
    }

    public int getPerk(int i) {
        if (i < perks.size())
            return perks.get(i);
        else return 0 ;
    }

    public Action getAction(int i){
        if (i < actions.size())
            return actions.get(i);
        else return ActionVoid.getAction() ;
    }

    public int getIndexOfAction(Action action){
        if  (actions.contains(action))
            return actions.indexOf(action) ;
        else {
            System.err.println("action wasn't found in the actions list ! here's your action : " + action.getClass());
            System.err.println("and here are the actions list :");
            for (Action actio : actions) System.out.println("\t\t\t" + actio.getClass());
            return 0 ;
        }
    }



    public void deploy(Player player, Player enemy, Cell cell) throws InvalidCellException, InvalidCardException {
        try{
            activeSpells.add(this);
            if(targetCells == null || targetCells.length == 0||this.target==null) {
                try {
                    targetCells = this.target.getTarget(cell);
                }catch (NullPointerException e){
                    throw new InvalidCellException();
                }
            }
            for (int i = 0 ; i < actions.size() ; i++){
                Action action = actions.get(i);
                try {
                    action.deploy(this, targetCells);
                    int index = getIndexOfAction(action) ;
                    durations.set(index , durations.get(index) - 1) ;
                    if (durations.get(index) == 0){
                        actions.remove(index);
                        durations.remove(index);
                        perks.remove(index);
                    }
                }catch (NullPointerException e){
                    System.err.println("it was deployed ! but it didn't do anything ! i hope that's cool ! " + this.getName() + " " + this.getID());
                }
            }
            if (actions.size() == 0) activeSpells.remove(this);
        } catch(Exception e ){
            activeSpells.remove(this) ;
            throw e ;
        }
    }

    public void deployAction(Cell... cells) throws InvalidCellException, InvalidCardException {
        if (cells == null ) return ;
        for (Action action : this.actions)
            action.deploy(this, cells);
    }

    public static Set<Action> getSpellActions() {
        return spellActions;
    }

    public static Set<Target> getTargets() {
        return targets;
    }

    public SpellGraphics getSpellGraphics() {
        return spellGraphics;
    }

    public void setSpellGraphics(SpellGraphics spellGraphics) {
        this.spellGraphics = spellGraphics;
    }
}
