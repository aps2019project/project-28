package Model.Graphics;

import Controller.menu.Battle;
import Controller.menu.Menu;
import Model.Graphics.Listeners.*;
import Model.Map.Cell;
import Model.card.hermione.Hermione;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class HermioneGraphics extends SpellGraphics {

    private ArrayList<OnMoveListener> moveListeners = new ArrayList<>();
    private ArrayList<OnAttackListener> attackListeners = new ArrayList<>();
    private ArrayList<OnSpawnListener> spawnListeners = new ArrayList<>();
    private ArrayList<OnDeathListener> deathListeners = new ArrayList<>();
    private ArrayList<OnDamageListener> damageListeners = new ArrayList<>();
    private ArrayList<OnSpeacialPowerAppliedListeners> SPApliedListenrs = new ArrayList<>();
    private ArrayList<OnCardSelectedListener> selectedListeners = new ArrayList<>();
    private String units;
    private double unitWidth;
    private double unitHeight;
    private int row;
    private int column;
    private String unitGifs;

    // TODO: 6/11/19 onCardSelected where to use
    private Hermione hermione;

    public Menu getBattleMenu() {
        return Battle.getMenu();
    }

    public HermioneGraphics(Hermione hermione) {
        this.hermione = hermione;
    }

    public void onMove(Cell cell) {
        this.moveListeners.forEach(l -> l.show(cell));
    }

    public void onAttack(Hermione enemyCard) {
        this.attackListeners.forEach(l -> l.show(enemyCard));
    }

    public void onSpawn(Cell cell) {
        this.spawnListeners.forEach(l -> l.show(cell));
    }

    public void onDeath() {
        this.deathListeners.forEach(l -> l.show());
    }

    public void onDamage() {
        this.damageListeners.forEach(l -> l.show());
    }

    public void onSpecialPowerApplied(Cell cell) {
        this.SPApliedListenrs.forEach(l -> l.show(cell));
    }

    public void onCardSelected(String state) {
        this.selectedListeners.forEach(l -> l.show(state));
    }

    public void addMoveListener(OnMoveListener moveListener) {
        this.moveListeners.add(moveListener);
    }

    public void addAttackListenr(OnAttackListener attackListener) {
        this.attackListeners.add(attackListener);
    }

    public void addSpawnListener(OnSpawnListener spawnListener) {
        this.spawnListeners.add(spawnListener);
    }

    public void addDeathListener(OnDeathListener deathListener) {
        this.deathListeners.add(deathListener);
    }

    public void addDamageListeners(OnDamageListener damageListener) {
        this.damageListeners.add(damageListener);
    }

    public void addSpecialPowerAppliedListener(OnSpeacialPowerAppliedListeners specialPowerAppliedListener) {
        this.SPApliedListenrs.add(specialPowerAppliedListener);
    }

    public void addCardSelectedListener(OnCardSelectedListener cardSelectedListener) {
        this.selectedListeners.add(cardSelectedListener);
    }

    public void setUnits(String units, double unitWidth, double unitHeight, int column, int row) {
        this.units = units;
        this.unitWidth = unitWidth;
        this.unitHeight = unitHeight;
        this.column = column;
        this.row = row;
    }

    public String getUnits() {
        return units;
    }

    public String getUnitGifs() {
        return unitGifs;
    }

    public void setUnitGifs(String unitGifs) {
        this.unitGifs = unitGifs;
    }

    public double getUnitHeight() {
        return unitHeight;
    }

    public double getUnitWidth() {
        return unitWidth;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
