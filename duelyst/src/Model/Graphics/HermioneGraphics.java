package Model.Graphics;

import Controller.menu.Battle;
import Controller.menu.Menu;
import Model.Graphics.Listeners.*;
import Model.Map.Cell;
import Model.card.hermione.Hermione;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class HermioneGraphics {

    private ArrayList<OnMoveListener> moveListeners = new ArrayList<>();
    private ArrayList<OnAttackListener> attackListeners = new ArrayList<>();
    private ArrayList<OnSpawnListener> spawnListeners = new ArrayList<>();
    private ArrayList<OnDeathListener> deathListeners = new ArrayList<>();
    private ArrayList<OnDamageListener> damageListeners = new ArrayList<>();
    private ArrayList<OnSpeacialPowerAppliedListeners> SPApliedListenrs = new ArrayList<>();
    private ArrayList<OnCardSelectedListener> selectedListeners = new ArrayList<>();
    private String avatar;

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
        System.err.println("debug");
        this.spawnListeners.add(spawnListener);

        System.err.println("debug");
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

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

}
