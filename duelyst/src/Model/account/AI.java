package Model.account;

import Controller.Game;
import Controller.menu.Battle;
import Model.Map.Cell;
import Model.Map.Map;
import Model.Primary;
import Model.card.Card;

import Model.card.hermione.Hermione;
import Model.card.hermione.Hero;
import Model.card.hermione.Minion;
import Model.card.spell.Spell;
import Model.card.spell.Target;
import exeption.*;


import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class AI extends Player {
    int move = -1; //0: insert card , 1&3: select hero, 2: move Hero ,
    // 4: attack with hero ,and then : minions : select-move-select-attack
    private Map map;
    private Player enemy;
    public String output;

    public AI(Account user, int maxMana, int mana,Player enemy) {
        super(user, maxMana, mana);
        this.map=Battle.getMenu().getMap();
        this.enemy=enemy;
    }







    private void play() {
        move++;
        String command;
        map = Battle.getMenu().getMap();
        enemy = Battle.getMenu().getEnemyPlayer();
        switch (move) {
            case 0:
                Random randTypeCard = new Random();
                int randCard = randTypeCard.nextInt(2);
                if (randCard == 0) {
                    command = insertMinion();
                    if (command != null && !command.isEmpty()) {
                        output=(command);
                        return;
                    }
                } else {
                    command = insertSpell();
                    if (command != null && !command.isEmpty()) {
                        output=(command);
                        return;
                    }
                }
                move++;
            case 1:
            case 3: //selecting the hero
                command = "Select " + this.getDeck().getHero().getCardID();
                output=(command);
                return;
            case 2: //move tha hero
                Hero hero = this.getDeck().getHero();//collection.getMainDeck().getHero();
                for (int i = 2; i > 0; i--) {
                    Cell[] cells = map.getCellsInDistance(hero.getLocation(), i);
                    for (Cell cell : cells) {
                        try {
                            if (hero.canMove(cell.getX(), cell.getY())) {
                                command = "Move to " + cell.getX() + " " + cell.getY();
                                output=(command);
                                return;
                            }
                        }catch(Exception ignored){}
                    }
                }
                move += 2;

            case 4: //he attacc
                hero = this.getDeck().getHero();
                if(attack(hero)) return;

                move++;
            default:
                do{
                    if (move > this.getMinionsInGame().size() + 5) {
                        move = -1;
                        output=("End turn");
                        return;
                    }
                    if (move % 2 == 1 && this.getMinionsInGame().size() > 0) {
                        command = "Select " + this.getMinionsInGame().get(move - 5);
                        output=(command);
                    } else if (this.getMinionsInGame().size() > 0) {
                        Minion card = this.getMinionsInGame().get(move - 6);
                        if (attack(card)) return;
                    }
                    move++;
                }while(this.getMinionsInGame().size() > 0) ;
                move = -1;
                output=("End turn");
        }
    }
//
    private boolean attack(Hermione card){
        String command;
        try {
            if (card.canAttack(enemy.getDeck().getHero())) {
                command = "Attack " + enemy.getDeck().getHero();
                output=(command);
                return true;
            }
            Random rand = new Random();
            ArrayList<Minion> target = new ArrayList<>(enemy.getMinionsInGame());
            if (target.isEmpty()) return false;
            int counter = 0;
            for (int i = rand.nextInt(target.size()); true; i = rand.nextInt(target.size())) {
                counter++;
                if (card.canAttack(target.get(i))) {
                    command = "Attack " + target.get(i).getCardID();
                    output=(command);
                    return true;
                }
                if (counter > 30) break;
            }
            output=("Unfortunately AI has stopped working");
            return false;
        }catch(Exception ignored){
            return false;
        }
    }
//
    private String insertMinion() {
        String command = "";
        for (Card card : this.getHand().getCards()) {
            if (card.getClass().equals(Minion.class)) {
                if (this.getMana() >= card.getPrice()) {
                    command = "Insert " + card.getName() + " in ";
                    break;
                }
            }
        }
        if (command.isEmpty()) {
            output=(command);
            return command;
        }
        for (int i = 1; i < 8; i++) {
            Cell[] cells = this.map.getCellsInDistance(this.getDeck().getHero().getLocation(), i);
            for (Cell cell : cells) {
                if (cell.getCardOnCell() == null) {
                    command = command + cell.getX() + " " + cell.getY();
                    output=(command);
                }
            }
        }
        output=("AI has decided not to work somehow!");
        return "AI has decided not to work somehow!";
    }
//
    private String insertSpell() {
        String command = "";
        Spell spell = null;
        for (Card card : this.getHand().getCards()) {
            if (card.getClass().equals(Spell.class)) {
                if (this.getMana() >= card.getPrice()) {
                    command = "Insert " + card.getName() + " in ";
                    spell = (Spell) card;
                    break;
                }
            }
        }
        if (command.isEmpty() || spell == null) {
            output=(command);
        }
        Target target;
        try {
            target = spell.getTargetClass();
        } catch (NullPointerException e) {
            System.err.println("AI wanted to deploy a spell but it didn't have a target class !");
            return "Spell has no target";
        }
        for (Cell cell : map.getCells()) {
            try {
                target.getTarget(cell);
                command = command + cell.getX() + " " + cell.getY();
                output=(command);
            } catch (InvalidCellException ignored) {
            }

        }
        output=("AI has decided not to work somehow!");
        return "AI has decided not to work somehow!";
    }

    @Override
    public void doYourMove() {
        System.err.println("ArshiA mn player e pesar am");
        this.play();
        System.out.println("_____________________________________");
        System.out.println("AI output is : " + this.output);
        System.out.println("=====================================");
        if (output == null || output.isEmpty()) output=("dude output is empty !");
    }
    @Override
    public Scanner getOutputStream() {
        if (this.outputStream != null && this.outputStream.scanner != null) this.outputStream.scanner.close();

        this.outputStream = new ScannerWrapper();
        this.outputStream.scanner = new Scanner(output);

        return this.outputStream.scanner;
    }
}
