package Model.account;

import Controller.Game;
import Model.Map.Cell;
import Model.Map.Map;
import Model.PreProcess;
import Model.card.Card;

import Model.card.hermione.Hero;
import Model.card.hermione.Minion;
import Model.card.spell.Spell;
import Model.card.spell.Target;
import exeption.*;


import java.util.Random;

public class AI extends Account {
    int level;
    int move; //0: insert card , 1: move Hero , 2: attack with hero , 3: move minions , 4: attack with minions
    Map map;
    Player enemy;


    public AI(int level) throws FullDeckException, DeckAlreadyHasThisCardException, InvalidDeckException,
            DeckAlreadyHasAHeroException, DeckAlreadyHasThisItemException {
        super("AI", "itsAI", "imAnAIgirlInAnAIWorld");
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

    public String play() {
        String command;
        map = Game.battle.getMap();
        enemy = Game.battle.getEnemyPlayer();
        Random randTypeCard = new Random();
        int randCard = randTypeCard.nextInt(2);
        if (randCard == 0) {
            command = insertMinion();
        } else {
            command = insertSpell();
        }
        if (command != null && !command.isEmpty()) return command;

        Hero hero = collection.getMainDeck().getHero();


        return "End turn";
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
        Deck deck = new Deck("AIDeck");
        switch (level) {
            case 1:
                try {
                    deck.addCardToDeck(PreProcess.getHeroes().get(0));

                    deck.addCardToDeck(PreProcess.getMinions().get(0));
                    deck.addCardToDeck(PreProcess.getMinions().get(8));
                    deck.addCardToDeck(PreProcess.getMinions().get(10));
                    deck.addCardToDeck(PreProcess.getMinions().get(10));
                    deck.addCardToDeck(PreProcess.getMinions().get(12));
                    deck.addCardToDeck(PreProcess.getMinions().get(16));
                    deck.addCardToDeck(PreProcess.getMinions().get(17));
                    deck.addCardToDeck(PreProcess.getMinions().get(20));
                    deck.addCardToDeck(PreProcess.getMinions().get(21));
                    deck.addCardToDeck(PreProcess.getMinions().get(25));
                    deck.addCardToDeck(PreProcess.getMinions().get(33));
                    deck.addCardToDeck(PreProcess.getMinions().get(35));

                    deck.addCardToDeck(PreProcess.getSpells().get(0));
                    deck.addCardToDeck(PreProcess.getSpells().get(6));
                    deck.addCardToDeck(PreProcess.getSpells().get(9));
                    deck.addCardToDeck(PreProcess.getSpells().get(10));
                    deck.addCardToDeck(PreProcess.getSpells().get(11));
                    deck.addCardToDeck(PreProcess.getSpells().get(17));
                    deck.addCardToDeck(PreProcess.getSpells().get(19));

                    deck.addItemToDeck(PreProcess.getUsables().get(0));

                } catch (FullDeckException | DeckAlreadyHasThisCardException | DeckAlreadyHasAHeroException |
                        DeckAlreadyHasThisItemException e) {
                    throw e;
                }

            case 2:
                try {
                    deck.addCardToDeck(PreProcess.getHeroes().get(4));

                    deck.addCardToDeck(PreProcess.getMinions().get(1));
                    deck.addCardToDeck(PreProcess.getMinions().get(2));
                    deck.addCardToDeck(PreProcess.getMinions().get(4));
                    deck.addCardToDeck(PreProcess.getMinions().get(7));
                    deck.addCardToDeck(PreProcess.getMinions().get(11));
                    deck.addCardToDeck(PreProcess.getMinions().get(14));
                    deck.addCardToDeck(PreProcess.getMinions().get(14));
                    deck.addCardToDeck(PreProcess.getMinions().get(18));
                    deck.addCardToDeck(PreProcess.getMinions().get(22));
                    deck.addCardToDeck(PreProcess.getMinions().get(26));
                    deck.addCardToDeck(PreProcess.getMinions().get(29));
                    deck.addCardToDeck(PreProcess.getMinions().get(32));
                    deck.addCardToDeck(PreProcess.getMinions().get(34));

                    deck.addCardToDeck(PreProcess.getSpells().get(1));
                    deck.addCardToDeck(PreProcess.getSpells().get(2));
                    deck.addCardToDeck(PreProcess.getSpells().get(4));
                    deck.addCardToDeck(PreProcess.getSpells().get(7));
                    deck.addCardToDeck(PreProcess.getSpells().get(8));
                    deck.addCardToDeck(PreProcess.getSpells().get(12));
                    deck.addCardToDeck(PreProcess.getSpells().get(18));

                    deck.addItemToDeck(PreProcess.getUsables().get(9));
                } catch (FullDeckException | DeckAlreadyHasThisCardException | DeckAlreadyHasAHeroException |
                        DeckAlreadyHasThisItemException e) {
                    throw e;
                }

            case 3:
                try {
                    deck.addCardToDeck(PreProcess.getHeroes().get(6));

                    deck.addCardToDeck(PreProcess.getMinions().get(5));
                    deck.addCardToDeck(PreProcess.getMinions().get(6));
                    deck.addCardToDeck(PreProcess.getMinions().get(9));
                    deck.addCardToDeck(PreProcess.getMinions().get(13));
                    deck.addCardToDeck(PreProcess.getMinions().get(15));
                    deck.addCardToDeck(PreProcess.getMinions().get(15));
                    deck.addCardToDeck(PreProcess.getMinions().get(19));
                    deck.addCardToDeck(PreProcess.getMinions().get(23));
                    deck.addCardToDeck(PreProcess.getMinions().get(24));
                    deck.addCardToDeck(PreProcess.getMinions().get(27));
                    deck.addCardToDeck(PreProcess.getMinions().get(28));
                    deck.addCardToDeck(PreProcess.getMinions().get(30));
                    deck.addCardToDeck(PreProcess.getMinions().get(33));

                    deck.addCardToDeck(PreProcess.getSpells().get(4));
                    deck.addCardToDeck(PreProcess.getSpells().get(9));
                    deck.addCardToDeck(PreProcess.getSpells().get(11));
                    deck.addCardToDeck(PreProcess.getSpells().get(13));
                    deck.addCardToDeck(PreProcess.getSpells().get(14));
                    deck.addCardToDeck(PreProcess.getSpells().get(15));
                    deck.addCardToDeck(PreProcess.getSpells().get(16));

                    deck.addItemToDeck(PreProcess.getUsables().get(4));
                } catch (FullDeckException | DeckAlreadyHasThisCardException | DeckAlreadyHasAHeroException |
                        DeckAlreadyHasThisItemException e) {
                    throw e;
                }

        }
        return deck;
    }
}
