package Model.account;

import Controller.Game;
import Model.Map.Cell;
import Model.Map.Map;
import Model.PreProcess;
import Model.card.Card;

import Model.card.hermione.Minion;
import Model.card.spell.Spell;
import Model.card.spell.Target;
import exeption.*;


import java.util.Random;

public class AI extends Account {
    int level ;
    Map map ;
    Player enemy ;
    Deck mode1 = new Deck("mode1");
    Deck mode2 = new Deck("mode2");
    Deck mode3 = new Deck("mode3");
    {
        try {
            mode1.addCardToDeck(PreProcess.getHeroes().get(0));

            mode1.addCardToDeck(PreProcess.getMinions().get(0));
            mode1.addCardToDeck(PreProcess.getMinions().get(8));
            mode1.addCardToDeck(PreProcess.getMinions().get(10));
            mode1.addCardToDeck(PreProcess.getMinions().get(10));
            mode1.addCardToDeck(PreProcess.getMinions().get(12));
            mode1.addCardToDeck(PreProcess.getMinions().get(16));
            mode1.addCardToDeck(PreProcess.getMinions().get(17));
            mode1.addCardToDeck(PreProcess.getMinions().get(20));
            mode1.addCardToDeck(PreProcess.getMinions().get(21));
            mode1.addCardToDeck(PreProcess.getMinions().get(25));
            mode1.addCardToDeck(PreProcess.getMinions().get(33));
            mode1.addCardToDeck(PreProcess.getMinions().get(35));

            mode1.addCardToDeck(PreProcess.getSpells().get(0));
            mode1.addCardToDeck(PreProcess.getSpells().get(6));
            mode1.addCardToDeck(PreProcess.getSpells().get(9));
            mode1.addCardToDeck(PreProcess.getSpells().get(10));
            mode1.addCardToDeck(PreProcess.getSpells().get(11));
            mode1.addCardToDeck(PreProcess.getSpells().get(17));
            mode1.addCardToDeck(PreProcess.getSpells().get(19));

            mode1.addItemToDeck(PreProcess.getUsables().get(0));

        }catch (FullDeckException e){}
        catch (DeckAlreadyHasThisCardException e) {}
        catch (DeckAlreadyHasAHeroException e) {}
        catch (DeckAlreadyHasThisItemException e) {}


        try {
            mode2.addCardToDeck(PreProcess.getHeroes().get(4));

            mode2.addCardToDeck(PreProcess.getMinions().get(1));
            mode2.addCardToDeck(PreProcess.getMinions().get(2));
            mode2.addCardToDeck(PreProcess.getMinions().get(4));
            mode2.addCardToDeck(PreProcess.getMinions().get(7));
            mode2.addCardToDeck(PreProcess.getMinions().get(11));
            mode2.addCardToDeck(PreProcess.getMinions().get(14));
            mode2.addCardToDeck(PreProcess.getMinions().get(14));
            mode2.addCardToDeck(PreProcess.getMinions().get(18));
            mode2.addCardToDeck(PreProcess.getMinions().get(22));
            mode2.addCardToDeck(PreProcess.getMinions().get(26));
            mode2.addCardToDeck(PreProcess.getMinions().get(29));
            mode2.addCardToDeck(PreProcess.getMinions().get(32));
            mode2.addCardToDeck(PreProcess.getMinions().get(34));

            mode2.addCardToDeck(PreProcess.getSpells().get(1));
            mode2.addCardToDeck(PreProcess.getSpells().get(2));
            mode2.addCardToDeck(PreProcess.getSpells().get(4));
            mode2.addCardToDeck(PreProcess.getSpells().get(7));
            mode2.addCardToDeck(PreProcess.getSpells().get(8));
            mode2.addCardToDeck(PreProcess.getSpells().get(12));
            mode2.addCardToDeck(PreProcess.getSpells().get(18));

            mode2.addItemToDeck(PreProcess.getUsables().get(9));

        }catch (FullDeckException e){}
        catch (DeckAlreadyHasThisCardException e) {}
        catch (DeckAlreadyHasAHeroException e) {}
        catch (DeckAlreadyHasThisItemException e) {}

        try {
            mode3.addCardToDeck(PreProcess.getHeroes().get(6));

            mode3.addCardToDeck(PreProcess.getMinions().get(5));
            mode3.addCardToDeck(PreProcess.getMinions().get(6));
            mode3.addCardToDeck(PreProcess.getMinions().get(9));
            mode3.addCardToDeck(PreProcess.getMinions().get(13));
            mode3.addCardToDeck(PreProcess.getMinions().get(15));
            mode3.addCardToDeck(PreProcess.getMinions().get(15));
            mode3.addCardToDeck(PreProcess.getMinions().get(19));
            mode3.addCardToDeck(PreProcess.getMinions().get(23));
            mode3.addCardToDeck(PreProcess.getMinions().get(24));
            mode3.addCardToDeck(PreProcess.getMinions().get(27));
            mode3.addCardToDeck(PreProcess.getMinions().get(28));
            mode3.addCardToDeck(PreProcess.getMinions().get(30));
            mode3.addCardToDeck(PreProcess.getMinions().get(33));

            mode3.addCardToDeck(PreProcess.getSpells().get(4));
            mode3.addCardToDeck(PreProcess.getSpells().get(9));
            mode3.addCardToDeck(PreProcess.getSpells().get(11));
            mode3.addCardToDeck(PreProcess.getSpells().get(13));
            mode3.addCardToDeck(PreProcess.getSpells().get(14));
            mode3.addCardToDeck(PreProcess.getSpells().get(15));
            mode3.addCardToDeck(PreProcess.getSpells().get(16));

            mode3.addItemToDeck(PreProcess.getUsables().get(4));

        }catch (FullDeckException e){}
        catch (DeckAlreadyHasThisCardException e) {}
        catch (DeckAlreadyHasAHeroException e) {}
        catch (DeckAlreadyHasThisItemException e) {}


    }
    public AI(int level) throws Exception {
        super("AI", "itsAI", "imAnAIgirlInAnAIWorld");
        this.level = level;

        try {
            this.collection = new Collection();
            collection.setOwner(this);
            Deck deck;
            if (level == 1) deck = mode1 ;
            else if (level == 2) deck = mode2 ;
            else  deck = mode3 ;
            deck.setCollection(collection);
            collection.setMainDeck(deck.getName());


        } catch (InvalidDeckException e) {
            throw new Exception("this is not supposed to happen ! : AI constructor");
        }
    }

    public String play() {
        map = Game.battle.getMap();
        enemy = Game.battle.getEnemyPlayer() ;
        Random randTypeCard = new Random();
        int randCard = randTypeCard.nextInt(2) ;
        if (randCard == 0){
            insertMinion() ;
        }else{
            insertSpell() ;
        }

        return null;
    }

    private String insertMinion(){
        String command = "";
        for (Card card : this.player.getHand().getCards()) {
            if (card.getClass().equals(Minion.class)) {
                if (player.getMana() >= card.getPrice()) {
                    command = "Insert " + card.getName() + " in (";
                    break;
                }
            }
        }
        if (command.isEmpty()) return command ;
        for (int i = 1 ; i < 8 ; i++){
            Cell[] cells = this.map.getCellsInDistance(this.collection.getMainDeck().getHero().getLocation() , i) ;
            for (Cell cell : cells){
                if (cell.getCardOnCell() == null){
                    command = command + cell.getX() + ", " + cell.getY()+")" ;
                    return command ;
                }
            }
        }
        return null ;
    }

    private String insertSpell(){
        String command = "";
        Spell spell = null ;
        for (Card card : this.player.getHand().getCards()) {
            if (card.getClass().equals(Spell.class)) {
                if (player.getMana() >= card.getPrice()) {
                    command = "Insert " + card.getName() + " in (";
                    spell = (Spell) card ;
                    break;
                }
            }
        }
        if (command.isEmpty() || spell == null) return command ;
        Target target = spell.getTarget() ;
        for (Cell cell : map.getCells()){
            try{
                target.getTarget(cell);
                command = command + cell.getX() + ", " + cell.getY()+")" ;
                return command ;
            }catch (InvalidCellException e){
                continue;
            }
        }
        return null ;
    }
}
