package Model.card.spell;

import Controller.Battle;
import Controller.Game;
import Controller.Match;
import Model.account.Player;
import Model.card.Card;
import Model.Map.*;
import Model.card.hermione.Hermione;
import Model.card.hermione.Minion;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.Arrays;


public class Spell extends Card {
    protected Target target;
    protected Action action;
    protected int duration ;
    protected int perk ;

    public Spell(int cardID, String name, int price, int manaPoint) {
        super(cardID, name, price, manaPoint);
    }

    public int getDuration() {
        return duration;
    }

    public void decreaseDuration() {duration--;}

    public void deploy(Player player, Player enemy, Cell cell) {
        this.target.deploy(player, enemy, cell, this);
    }

    public void reverseChanges(Player player , Player enemy){return;}

    public void deployAction(Cell... cells){
        this.action.deploy(this , cells);
    }
}





interface Target {
    public void deploy(Player player, Player enemy, Cell cell, Spell spell);
}

class TargetSingleCell implements Target {
    @Override
    public void deploy(Player player, Player enemy, Cell cell, Spell spell) {
        spell.action.deploy(spell, cell);
    }
}

class TargetAllCards {
    public void deploy(Player player, Cell cell, Spell spell) {
        Minion[] minions = player.getMinionsInGame().toArray(new Minion[0]);
        Cell[] cells = new Cell[minions.length + 1];
        cells[0] = player.getHero().getLocation();
        int index = 1;
        for (Minion mini : minions) {
            cells[index] = mini.getLocation();
        }
        spell.action.deploy(spell, cells);
    }
}

class TargetAllEnemyCards extends TargetAllCards implements Target {
    public void deploy(Player player, Player enemy, Cell cell, Spell spell) {
        if (cell != player.getHero().getLocation()) {
            View.;
            return;
        }
        super.deploy(enemy, cell, spell);

    }
}

class TargetAllOwnCards extends TargetAllCards implements Target {
    @Override
    public void deploy(Player player, Player enemy, Cell cell, Spell spell) {
        if (cell != player.getHero().getLocation()) {
            View.;
            return;
        }
        super.deploy(player, cell, spell);
    }
}

class TargetEnemyHero implements Target {
    @Override
    public void deploy(Player player, Player enemy, Cell cell, Spell spell) {
        if (cell.getCardOnCell() == enemy.getHero()) {
            View.;
            return;
        }
        spell.action.deploy(spell, cell);

    }
}

class TargetEnemyMinion implements Target {
    @Override
    public void deploy(Player player, Player enemy, Cell cell, Spell spell) {
        for (Minion mini : enemy.getMinionsInGame()) {
            if (mini.getLocation() == cell) {
                spell.action.deploy(spell, cell);
                return;
            }
        }
        View.;
    }
}

class TargetEnemyCard implements Target {
    @Override
    public void deploy(Player player, Player enemy, Cell cell, Spell spell) {
        if (cell.getCardOnCell() != enemy.getHero() && !enemy.getMinionsInGame().contains(cell.getCardOnCell())) {
            View.;
            return;
        }
        spell.action.deploy(spell, cell);
    }
}

class TargetOwnHero implements Target {
    @Override
    public void deploy(Player player, Player enemy, Cell cell, Spell spell) {
        if (cell != player.getHero().getLocation()) {
            View.;
            return;
        }
        spell.action.deploy(spell, cell);
    }
}

class TargetOwnMinion implements Target {
    @Override
    public void deploy(Player player, Player enemy, Cell cell, Spell spell) {
        if (!player.getMinionsInGame().contains(cell.getCardOnCell())) {
            View.;
            return;
        }
        spell.action.deploy(spell, cell);
    }
}

class TargetOwnCard implements Target {
    @Override
    public void deploy(Player player, Player enemy, Cell cell, Spell spell) {
        if (player.getHero().getLocation() != cell && !player.getMinionsInGame().contains(cell.getCardOnCell())) {
            View.;
            return;
        }
        spell.action.deploy(spell, cell);
    }
}

class TargetTwoByTwo implements Target {
    @Override
    public void deploy(Player player, Player enemy, Cell cell, Spell spell) {
        int x = cell.getX();
        int y = cell.getY();
        Map map = Game.battle.getMap();
        if (x == Map.WIDTH || y == Map.HEIGHT) {
            View.;
            return;
        }
        Cell[] cells = {cell, map.getCell(x, y + 1), map.getCell(x + 1, y), map.getCell(x + 1, y + 1)};
        spell.action.deploy(spell, cells);
    }
}

class TargetThreeByThree implements Target {
    @Override
    public void deploy(Player player, Player enemy, Cell cell, Spell spell) {
        int x = cell.getX();
        int y = cell.getY();
        Map map = Game.battle.getMap();
        if (x > Map.WIDTH - 2 || y > Map.HEIGHT - 2) {
            View.;
            return;
        }
        Cell[] cells = {cell, map.getCell(x, y + 1), map.getCell(x, y + 2), map.getCell(x + 1, y),
                map.getCell(x + 2, y), map.getCell(x + 1, y + 1), map.getCell(x + 1, y + 2),
                map.getCell(x + 2, y + 1), map.getCell(x + 2, y + 2)};

        spell.action.deploy(spell, cells);
    }
}

class TargetHeroSurroundings implements Target {
    @Override
    public void deploy(Player player, Player enemy, Cell cell, Spell spell) {
        if (cell != player.getHero().getLocation()) {
            View.;
            return;
        }
        Map map = Game.battle.getMap();
        int x = cell.getX();
        int y = cell.getY();
        Cell[] cells = {map.getCell(x - 1, y - 1), map.getCell(x, y - 1), map.getCell(x + 1, y - 1),
                map.getCell(x - 1, y), map.getCell(x + 1, y), map.getCell(x - 1, y + 1),
                map.getCell(x, y + 1), map.getCell(x + 1, y + 1)};

        spell.action.deploy(spell, cells);
    }
}

class TargetOwnHeroRow implements Target {
    @Override
    public void deploy(Player player, Player enemy, Cell cell, Spell spell) {
        if (cell != player.getHero().getLocation()) {
            View.;
            return;
        }
        Map map = Game.battle.getMap();
        Cell[] cells = new Cell[Map.WIDTH];
        int y = cell.getY();
        for (int x = 1; x <= Map.WIDTH; x++) {
            cells[x-1] = map.getCell(x, y);
        }
        spell.action.deploy(spell, cells);
    }
}

class TargetEnemyHeroColumn implements Target {
    @Override
    public void deploy(Player player, Player enemy, Cell cell, Spell spell) {
        if (cell != enemy.getHero().getLocation()){
            View. ;
            return ;
        }
        Map map = Game.battle.getMap() ;
        Cell[] cells = new Cell[Map.HEIGHT] ;
        int x = cell.getX() ;
        for (int y = 1 ; y <= map.HEIGHT ; y++){
            cells[y-1] = map.getCell(x , y) ;
        }
        spell.action.deploy(spell , cells);
    }
}




interface Action {
    public void deploy(Spell spell, Cell... cells);
}

class ActionDisarm implements Action{
    @Override
    public void deploy(Spell spell, Cell... cells) {
        for (Cell cell : cells){
            cell.getCardOnCell().setCanCounterattack(false);
        }
    }
}

class ActionBuffDistroyer implements Action{
    @Override
    public void deploy(Spell spell, Cell... cells) {

    }
}

class ActionChangeAP implements Action{
    @Override
    public void deploy(Spell spell, Cell... cells) {
        for (Cell cell : cells){
            Hermione card = cell.getCardOnCell() ;
            card.setAttackPoint(card.getAttackPoint() + spell.perk);
        }
    }
}

class ActionChangeHP implements Action{
    @Override
    public void deploy(Spell spell, Cell... cells) {
        for (Cell cell : cells){
            Hermione card = cell.getCardOnCell() ;
            card.setHealthPoint(card.getHealthPoint() + spell.perk);
        }
    }
}

class ActionFireCell implements Action{
    @Override
    public void deploy(Spell spell, Cell... cells) {
        for (Cell cell : cells){
            cell.
        }
    }
}

class ActionMakeFireCell implements Action{
    @Override
    public void deploy(Spell spell, Cell... cells) {
        Spell
        for (Cell cell : cells){
            cell.applySpellOnCard();
        }
    }
}

class ActionPoisonCell implements Action {
    @Override
    public void deploy(Spell spell, Cell... cells) {

    }
}

class ActionDisarmAndAddAP implements Action{
    //has duration
    @Override
    public void deploy(Spell spell, Cell... cells) {
        spell.decreaseDuration();
        ActionDisarm disarm = new ActionDisarm();
        disarm.deploy(spell , cells);
        ActionChangeAP addap = new ActionChangeAP() ;
        addap.deploy(spell , cells);
    }
}

class ActionPoison implements Action{
    @Override
    public void deploy(Spell spell, Cell... cells) {

    }
}

class ActionDispel implements Action{
    @Override
    public void deploy(Spell spell, Cell... cells) {

    }
}

class ActionHealthWithProfit implements Action{
    @Override
    public void deploy(Spell spell, Cell... cells) {

    }
}

class ActionGhazaBokhor implements Action{
    @Override
    public void deploy(Spell spell, Cell... cells) {

    }
}

class ActionSacrifice implements Action{
    @Override
    public void deploy(Spell spell, Cell... cells) {
        for (Cell cell : cells){
            int mhp = cell.getCardOnCell().getHealthPoint() ;
            cell.getCardOnCell().setHealthPoint(0);
            Player player = Game.battle.getPlayer();
            player.getHero().setHealthPoint(player.getHero().getHealthPoint() + mhp);
        }
    }
}

class ActionKillMinion implements Action{
    @Override
    public void deploy(Spell spell, Cell... cells) {
        cells[0].getCardOnCell().setHealthPoint(0);
    }
}

class ActionStun implements Action{
    @Override
    public void deploy(Spell spell, Cell... cells) {

    }
}
