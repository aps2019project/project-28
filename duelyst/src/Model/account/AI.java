package Model.account;

import Controller.Game;
import Model.Map.Map;
import Model.PreProcess;
import Model.card.hermione.Hermione;
import Model.card.hermione.Hero;
import Model.card.hermione.Minion;
import Model.card.spell.Spell;
import Model.item.Item;
import Model.item.Usable;
import exeption.*;

import java.util.ArrayList;
import java.util.Collections;

public class AI extends Account {
    int level;
    String heroName = new String();
    ArrayList<String> minionNames = new ArrayList<>();
    ArrayList<String> usableNames = new ArrayList<>();
    ArrayList<String> spellNames = new ArrayList<>();

    public AI(int level) throws Exception {
        super("AI", "itsAI", "imAnAIgirlInAnAIWorld");
        this.level = level;
        try {
            this.collection = new Collection();
            collection.setOwner(this);

            Deck deck = getDeck();
            deck.setCollection(collection);


            //TODO
            collection.setMainDeck("AIMainDeck");


        } catch (InvalidDeckException e) {
            throw new Exception("this is not supposed to happen ! : AI constructor");
        }
    }

    public String play() {
        Map map = Game.battle.getMap();

        return null;
    }

    public int getLevel() {
        return level;
    }

    private Deck getDeck(String heroName, ArrayList<String> usableNames, ArrayList<String> minionNames) throws FullDeckException, DeckAlreadyHasAHeroException, DeckAlreadyHasThisCardException
            , DeckAlreadyHasThisItemException {
        Deck deck = new Deck("AIDeck");
        ArrayList<Minion> minions = PreProcess.getMinions();
        ArrayList<Usable> items = PreProcess.getUsables();
        ArrayList<Hero> heroes = PreProcess.getHeroes();
        ArrayList<Spell> spells = PreProcess.getSpells();


        for (Hero hero : heroes) {
            if (hero.getName().equals(heroName)) {
                try {
                    deck.addCardToDeck(hero);
                } catch (FullDeckException | DeckAlreadyHasAHeroException | DeckAlreadyHasThisCardException e) {
                    throw e;
                }
                break;
            }
        }
        for (String usableName : usableNames) {
            for (Item item : items) {
                if (item.getName().equals(usableName)) {
                    try {
                        deck.addItemToDeck(item);
                    } catch (DeckAlreadyHasThisItemException e) {
                        throw e;
                    }
                    break;
                }
            }
        }
        for (String minionName : minionNames) {
            for (Minion minion : minions) {
                if (minion.getName().equals(minionName)) {
                    try {
                        deck.addCardToDeck(minion);
                    } catch (FullDeckException | DeckAlreadyHasAHeroException | DeckAlreadyHasThisCardException e) {
                        throw e;
                    }
                    break;
                }
            }
        }

        for (String spellName : spellNames){
            for (Spell spell : spells){
                if (spell.getName().equals(spellName)){
                    try{
                        deck.addCardToDeck(spell) ;
                    }catch (FullDeckException | DeckAlreadyHasAHeroException | DeckAlreadyHasThisCardException e) {
                        throw e;
                    }
                }
            }
        }

        return deck;
    }

    private void setDeckNames(int level){
        switch(level){
            case 1:
                heroName = "White Demon" ;

                minionNames.add("Persian Archer") ;
                minionNames.add("Turanian Lancer") ;
                minionNames.add("Turanian MaceBearer") ;
                minionNames.add("Turanian MaceBearer") ;
                minionNames.add("Stone Thrower Giant") ;
                minionNames.add("Black Demon") ;
                minionNames.add("One Eye Giant") ;
                minionNames.add("Venomous Snake") ;
                minionNames.add("Giant Snake") ;
                minionNames.add("White Wolf") ;
                minionNames.add("The Wizard") ;
                minionNames.add("Siavash") ;
                minionNames.add("Arzhang Div") ;

                usableNames.add("Wisdom Crown") ;

                spellNames.add("Total Disarm") ;
                spellNames.add("Lightning Bolt") ;
                spellNames.add("All Disarm") ;
                spellNames.add("All Poison") ;
                spellNames.add("Dispel") ;
                spellNames.add("Sacrifice") ;
                spellNames.add("Shock") ;

            case 2:
                heroName = "Zahak" ;

                minionNames.add("Persian Swordsman") ;
                minionNames.add("Persian Lancer") ;
                minionNames.add("Persian Warrior") ;
                minionNames.add("Turanian Slinger") ;
                minionNames.add("Turanian Prince") ;
                minionNames.add("Eagle") ;
                minionNames.add("Eagle") ;
                minionNames.add("Fire Dragon") ;
                minionNames.add("Leopard") ;
                minionNames.add("Genie") ;
                minionNames.add("Giv") ;
                minionNames.add("Ashkbus") ;
                minionNames.add("Eurymedon") ;


                usableNames.add("Soul Eater") ;

                spellNames.add("Area Dispel") ;
                spellNames.add("Empower") ;
                spellNames.add("God Strength") ;
                spellNames.add("Madness") ;
                spellNames.add("Poison Lake") ;
                spellNames.add("Health With Profit") ;
                spellNames.add("Kings Guard") ;

            case 3:
                heroName = "Arash" ;

                minionNames.add("Persian General") ;
                minionNames.add("Turanian Archer") ;
                minionNames.add("Stone Thrower Giant") ;
                minionNames.add("Hog Rider Demon") ;
                minionNames.add("Hog Rider Demon") ;
                minionNames.add("Fierce Lion") ;
                minionNames.add("Wolf") ;
                minionNames.add("The Wizard") ;
                minionNames.add("Wild Goraz") ;
                minionNames.add("Piran") ;
                minionNames.add("Bahman") ;
                minionNames.add("Great Giant") ;


                usableNames.add("Terror Hood") ;

                spellNames.add("Hell Fire") ;
                spellNames.add("All Disarm") ;
                spellNames.add("Dispel") ;
                spellNames.add("Power Up") ;
                spellNames.add("All Power") ;
                spellNames.add("All Attack") ;
                spellNames.add("Weakening") ;

        }
    }
}
