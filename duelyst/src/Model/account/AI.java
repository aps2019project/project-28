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
import Model.card.hermione.SPATime;
import Model.card.spell.Spell;
import Model.card.spell.Target;
import exeption.*;


import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class StringWrapper{
    public String string;

    public void set(String string) {
        this.string = string;
    }
}
public class AI extends Account {
    int level;
    int move = -1; //0: insert card , 1&3: select hero, 2: move Hero ,
                // 4: attack with hero ,and then : minions : select-move-select-attack
    private Map map;
    private Player enemy;
    public StringWrapper output=new StringWrapper();

    public AI(int level) throws FullDeckException, DeckAlreadyHasThisCardException, InvalidDeckException,
            DeckAlreadyHasAHeroException, DeckAlreadyHasThisItemException {
        super("AI", "itsAI", "imAnAIgirlInAnAIWorld");
//        super(,2,2)
        this.level = level;

        try {
            this.collection = new Collection();
            collection.setOwner(this);
            Deck deck = getDeck(level);
            deck.setCollection(collection);
            collection.forcePushDeck(deck);
            collection.setMainDeck(deck.getName());


        } catch (FullDeckException | DeckAlreadyHasThisCardException | DeckAlreadyHasAHeroException |
                DeckAlreadyHasThisItemException | InvalidDeckException e) {
            throw e;
        }

    }
    public AI(Deck deck){
        super("AI", "itsAI", "imAnAIgirlInAnAIWorld");
//        super(,2,2)
        this.level = 1;

        try {
            this.collection = new Collection();
            collection.setOwner(this);
            deck.setCollection(collection);
            collection.forcePushDeck(deck);
            collection.setMainDeck(deck.getName());


        } catch (InvalidDeckException ignored) {}
    }


    private void play() {
        move++ ;
        String command;
        map = Game.battle.getMap();
        enemy = Game.battle.getEnemyPlayer();
        switch(move){
            case 0:
                Random randTypeCard = new Random();
                int randCard = randTypeCard.nextInt(2);
                if (randCard == 0) {
                    command = insertMinion();
                    if (command != null && !command.isEmpty()) {
                        output.set(command);
                        return;
                    }
                } else {
                    command = insertSpell();
                    if (command!= null && !command.isEmpty()) {
                        output.set(command);
                        return;
                    }
                }
                move++ ;
            case 1:
            case 3:
                command = "Select "+ collection.getMainDeck().getHero().getCardID();
                output.set(command);
                return ;
            case 2:
                Hero hero = this.player.getDeck().getHero();//collection.getMainDeck().getHero();
                for (int i = 2 ; i >0 ; i--){
                    Cell[] cells = map.getCellsInDistance(hero.getLocation() , i) ;
                    for (Cell cell : cells){
                        if (cell.getCardOnCell() == null) { //TODO after arshia makes the canReach function for hermione check that too !
                            command = "Move to " + cell.getX() + " " + cell.getY() ;
                            output.set(command);
                            return ;
                        }
                    }
                }
                move+=2 ;

            case 4:
                hero = collection.getMainDeck().getHero() ;
                 command = attack(hero);
                output.set(command);
                return ;
            default:
                if (move > player.getMinionsInGame().size() + 5) {
                    move = -1 ;
                    output.set("End turn");
                    return ;
                }
                if (move % 2 == 1 && player.getMinionsInGame().size() > 0){
                    command = "Select " + player.getMinionsInGame().get(move-5) ;
                    output.set(command);
                }
                else if (player.getMinionsInGame().size() > 0){
                    Minion card = player.getMinionsInGame().get(move-6) ;
                    command = attack(card) ;
                    output.set(command);
                }
        }
    }

    private String  attack(Hermione card) {
        String command;
        if (card.canAttackThisCard(enemy.getDeck().getHero())){
            command = "Attack " + enemy.getDeck().getHero();
            output.set(command);
        }
        Random rand = new Random();
        ArrayList<Minion> target = new ArrayList<>(enemy.getMinionsInGame()) ;
        if (target.isEmpty()) return "nope not my cup of tea" ;
        int counter = 0 ;
        for (int i = rand.nextInt(target.size()) ; true ; i = rand.nextInt(target.size())){
            counter++ ;
            if (card.canAttackThisCard(target.get(i))){
                command = "Attack " + target.get(i).getCardID();
                output.set(command);
                return command ;
            }
            if (counter > 30) break ;
        }
        output.set("Unfortunately AI has stopped working");
        return "Unfortunately AI has stopped working" ;
    }

    private String insertMinion() {
        String command = "";
        for (Card card : this.player.getHand().getCards()) {
            if (card.getClass().equals(Minion.class)) {
                if (player.getMana() >= card.getPrice()) {
                    command = "Insert " + card.getName() + " in ";
                    break;
                }
            }
        }
        if (command.isEmpty()) {
            output.set(command);
            return command;
        }
        for (int i = 1; i < 8; i++) {
            Cell[] cells = this.map.getCellsInDistance(this.collection.getMainDeck().getHero().getLocation(), i);
            for (Cell cell : cells) {
                if (cell.getCardOnCell() == null) {
                    command = command + cell.getX() + " " + cell.getY() ;
                    output.set(command);
                }
            }
        }
        output.set("AI has decided not to work somehow!");
        return "AI has decided not to work somehow!";
    }

    private String insertSpell() {
        String command = "";
        Spell spell = null;
        for (Card card : this.player.getHand().getCards()) {
            if (card.getClass().equals(Spell.class)) {
                if (player.getMana() >= card.getPrice()) {
                    command = "Insert " + card.getName() + " in ";
                    spell = (Spell) card;
                    break;
                }
            }
        }
        if (command.isEmpty() || spell == null) {
            output.set(command);
        }
        Target target ;
        try {
            target = spell.getTargetClass();
        }catch(NullPointerException e){
            System.err.println("AI wanted to deploy a spell but it didn't have a target class !");
            return "Spell has no target" ;
        }
        for (Cell cell : map.getCells()) {
            try {
                target.getTarget(cell);
                command = command + cell.getX() + " " + cell.getY() ;
                output.set(command);
            } catch (InvalidCellException ignored) {}

        }
        output.set("AI has decided not to work somehow!");
        return "AI has decided not to work somehow!";
    }

    private Deck getDeck(int level) throws FullDeckException, DeckAlreadyHasThisCardException,
            DeckAlreadyHasAHeroException, DeckAlreadyHasThisItemException {
        Deck deck = new Deck("AIDeck", this.collection);
        switch (level) {
            case 1:
                try {
                    deck.addCardToDeck(Primary.heroes.get(0));

                    deck.addCardToDeck(Primary.minions.get(0));
                    deck.addCardToDeck(Primary.minions.get(8));
                    deck.addCardToDeck(Primary.minions.get(10));
                    deck.addCardToDeck(Primary.minions.get(15));
                    deck.addCardToDeck(Primary.minions.get(12));
                    deck.addCardToDeck(Primary.minions.get(16));
                    deck.addCardToDeck(Primary.minions.get(17));
                    deck.addCardToDeck(Primary.minions.get(20));
                    deck.addCardToDeck(Primary.minions.get(21));
                    deck.addCardToDeck(Primary.minions.get(25));
                    deck.addCardToDeck(Primary.minions.get(33));
                    deck.addCardToDeck(Primary.minions.get(35));

                    deck.addCardToDeck(Primary.spells.get(0));
                    deck.addCardToDeck(Primary.spells.get(6));
                    deck.addCardToDeck(Primary.spells.get(9));
                    deck.addCardToDeck(Primary.spells.get(10));
                    deck.addCardToDeck(Primary.spells.get(11));
                    deck.addCardToDeck(Primary.spells.get(17));
                    deck.addCardToDeck(Primary.spells.get(19));

                    deck.addItemToDeck(Primary.usables.get(0));

                } catch (FullDeckException | DeckAlreadyHasThisCardException | DeckAlreadyHasAHeroException |
                        DeckAlreadyHasThisItemException e) {
                    throw e;
                }
                break ;
            case 2:
                try {
                    deck.addCardToDeck(Primary.heroes.get(4));

                    deck.addCardToDeck(Primary.minions.get(1));
                    deck.addCardToDeck(Primary.minions.get(2));
                    deck.addCardToDeck(Primary.minions.get(4));
                    deck.addCardToDeck(Primary.minions.get(7));
                    deck.addCardToDeck(Primary.minions.get(11));
                    deck.addCardToDeck(Primary.minions.get(14));
//                    deck.addCardToDeck(Primary.minions.get(14));
                    deck.addCardToDeck(Primary.minions.get(18));
                    deck.addCardToDeck(Primary.minions.get(22));
                    deck.addCardToDeck(Primary.minions.get(26));
                    deck.addCardToDeck(Primary.minions.get(29));
                    deck.addCardToDeck(Primary.minions.get(32));
                    deck.addCardToDeck(Primary.minions.get(34));

                    deck.addCardToDeck(Primary.spells.get(1));
                    deck.addCardToDeck(Primary.spells.get(2));
                    deck.addCardToDeck(Primary.spells.get(4));
                    deck.addCardToDeck(Primary.spells.get(7));
                    deck.addCardToDeck(Primary.spells.get(8));
                    deck.addCardToDeck(Primary.spells.get(12));
                    deck.addCardToDeck(Primary.spells.get(18));

                    deck.addItemToDeck(Primary.usables.get(9));
                } catch (FullDeckException | DeckAlreadyHasThisCardException | DeckAlreadyHasAHeroException |
                        DeckAlreadyHasThisItemException e) {
                    throw e;
                }
                break ;
            case 3:
                try {
                    deck.addCardToDeck(Primary.heroes.get(6));

                    deck.addCardToDeck(Primary.minions.get(5));
                    deck.addCardToDeck(Primary.minions.get(6));
                    deck.addCardToDeck(Primary.minions.get(9));
                    deck.addCardToDeck(Primary.minions.get(13));
                    deck.addCardToDeck(Primary.minions.get(15));
                    deck.addCardToDeck(Primary.minions.get(15));
                    deck.addCardToDeck(Primary.minions.get(19));
                    deck.addCardToDeck(Primary.minions.get(23));
                    deck.addCardToDeck(Primary.minions.get(24));
                    deck.addCardToDeck(Primary.minions.get(27));
                    deck.addCardToDeck(Primary.minions.get(28));
                    deck.addCardToDeck(Primary.minions.get(30));
                    deck.addCardToDeck(Primary.minions.get(33));

                    deck.addCardToDeck(Primary.spells.get(4));
                    deck.addCardToDeck(Primary.spells.get(9));
                    deck.addCardToDeck(Primary.spells.get(11));
                    deck.addCardToDeck(Primary.spells.get(13));
                    deck.addCardToDeck(Primary.spells.get(14));
                    deck.addCardToDeck(Primary.spells.get(15));
                    deck.addCardToDeck(Primary.spells.get(16));

                    deck.addItemToDeck(Primary.usables.get(4));
                } catch (FullDeckException | DeckAlreadyHasThisCardException | DeckAlreadyHasAHeroException |
                        DeckAlreadyHasThisItemException e) {
                    throw e;
                }
        }
        return deck;
    }

    @Override
    public void doYourMove() {
        this.play();
        System.out.println("\t\t\t\t\t\t\t AI output is : " + this.output.string);
        if(output.string==null || output.string.isEmpty()) output.set("dude output is empty !");
    }

    @Override
    public Scanner getOutputStream() {
        if(this.outputStream!=null && this.outputStream.scanner!=null)this.outputStream.scanner.close();

        this.outputStream = new ScannerWrapper();
        this.outputStream.scanner = new Scanner(output.string);

        return this.outputStream.scanner;
    }
}
