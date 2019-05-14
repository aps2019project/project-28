package Model.account;

import Controller.Game;
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

public class AI extends Account {
    int level;
    int move; //0: insert card , 1&3: select hero, 2: move Hero ,
                // 4: attack with hero ,and then : minions : select-move-select-attack
    Map map;
    Player enemy;


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
            collection.setMainDeck(deck.getName());


        } catch (FullDeckException | DeckAlreadyHasThisCardException | DeckAlreadyHasAHeroException |
                DeckAlreadyHasThisItemException | InvalidDeckException e) {
            throw e;
        }

    }
    @Override
    public String play() {
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
                } else {
                    command = insertSpell();
                }
                if (command != null && !command.isEmpty()) return command;
                else move++ ;
            case 1:
            case 3:
                command = "Select "+ collection.getMainDeck().getHero().getCardID();
                return command ;
            case 2:
                Hero hero = collection.getMainDeck().getHero();
                for (int i = 2 ; i >0 ; i--){
                    Cell[] cells = map.getCellsInDistance(hero.getLocation() , i) ;
                    for (Cell cell : cells){
                        if (cell.getCardOnCell() == null) {
                            command = "Move to (" + cell.getX() + ", " + cell.getY()+")" ;
                            return command ;
                        }
                    }
                }
                move++ ;

            case 4:
                hero = collection.getMainDeck().getHero() ;
                 command = attack(hero);
                if (command != null) return command;
                else move++;
            default:
                if (move > player.getMinionsInGame().size() + 5) {
                    move = -1 ;
                    return "End turn";
                }
                if (move % 2 == 1){
                    command = "Select " + player.getMinionsInGame().get(move-5) ;
                    return command ;
                }
                else{
                    Minion card = player.getMinionsInGame().get(move-6) ;
                    command = attack(card) ;
                    return command ;
                }
        }
    }

    private String attack(Hermione card) {
        String command;
        if (card.canAttackThisCard(enemy.getDeck().getHero())){
            command = "Attack " + enemy.getDeck().getHero();
            return command ;
        }
        Random rand = new Random();
        ArrayList<Minion> target = new ArrayList<>(enemy.getMinionsInGame()) ;
        int counter = 0 ;
        for (int i = rand.nextInt(target.size()) ; true ; i = rand.nextInt(target.size())){
            counter++ ;
            if (card.canAttackThisCard(target.get(i))){
                command = "Attack " + target.get(i);
                return command ;
            }
            if (counter > 30) break ;
        }
        return null ;
    }

    private String insertMinion() {
        String command = "";
        for (Card card : this.player.getHand().getCards()) {
            if (card.getClass().equals(Minion.class)) {
                if (player.getMana() >= card.getPrice()) {
                    command = "Insert " + card.getName() + " in (";
                    break;
                }
            }
        }
        if (command.isEmpty()) return command;
        for (int i = 1; i < 8; i++) {
            Cell[] cells = this.map.getCellsInDistance(this.collection.getMainDeck().getHero().getLocation(), i);
            for (Cell cell : cells) {
                if (cell.getCardOnCell() == null) {
                    command = command + cell.getX() + ", " + cell.getY() + ")";
                    return command;
                }
            }
        }
        return null;
    }

    private String insertSpell() {
        String command = "";
        Spell spell = null;
        for (Card card : this.player.getHand().getCards()) {
            if (card.getClass().equals(Spell.class)) {
                if (player.getMana() >= card.getPrice()) {
                    command = "Insert " + card.getName() + " in (";
                    spell = (Spell) card;
                    break;
                }
            }
        }
        if (command.isEmpty() || spell == null) return command;
        Target target = spell.getTarget();
        for (Cell cell : map.getCells()) {
            try {
                target.getTarget(cell);
                command = command + cell.getX() + ", " + cell.getY() + ")";
                return command;
            } catch (InvalidCellException e) {
                continue;
            }
        }
        return null;
    }

    private Deck getDeck(int level) throws FullDeckException, DeckAlreadyHasThisCardException,
            DeckAlreadyHasAHeroException, DeckAlreadyHasThisItemException {
        Deck deck = new Deck("AIDeck",this.collection);
        switch (level) {
            case 1:
                try {
                    deck.addCardToDeck(Primary.heroes.get(0));

                    deck.addCardToDeck(Primary.minions.get(0));
                    deck.addCardToDeck(Primary.minions.get(8));
                    deck.addCardToDeck(Primary.minions.get(10));
                    deck.addCardToDeck(Primary.minions.get(10));
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
                    deck.addCardToDeck(Primary.minions.get(14));
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
    public Scanner getOutputStream() {
        if(this.outputStream.scanner!=null){
            this.outputStream.scanner.close();
        }
        this.outputStream.scanner=new Scanner(this.play());
        return this.outputStream.scanner;
    }
}
