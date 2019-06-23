package Model;

import Controller.menu.Graphics.FXMLController.BattleFXMLC;
import Controller.menu.Graphics.FXMLController.FXMLController;
import Model.Graphics.Listeners.*;
import Model.Graphics.SpriteAnimation;
import Model.Map.Cell;
import Model.account.Account;
import Model.account.Collection;
import Model.account.Deck;
import Model.card.Card;
import Model.card.hermione.*;
import Model.card.spell.*;
import Model.card.spell.SpecialPowerActions.SPActionPersianChamp;
import Model.card.spell.SpellAction.*;
import Model.card.spell.SpellAction.ActionChangeAPBuff;
import Model.card.spell.SpellAction.ActionChangeHPBuff;
import Model.card.spell.SpellAction.ActionDisarm;
import Model.card.spell.SpellAction.ActionStun;
import Model.card.spell.Targets.*;
import Model.item.Collectable;
import Model.item.Item;
import Model.item.ItemActions.*;
import Model.item.Usable;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.Gson;
import com.gilecode.yagson.com.google.gson.JsonElement;
import com.gilecode.yagson.com.google.gson.JsonStreamParser;
import exeption.*;
import javafx.animation.Animation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.awt.*;
import java.io.*;
import java.nio.charset.MalformedInputException;
import java.util.ArrayList;

public class Primary {

    public static ArrayList<String> defaultNames = new ArrayList<>();
    public static ArrayList<Deck> defaultDecks = new ArrayList<>();
    public static ArrayList<Item>  items= new ArrayList<>();
    public static ArrayList<Spell> spells = new ArrayList<>();
    public static ArrayList<Minion> minions = new ArrayList<>();
    public static ArrayList<Hero> heroes = new ArrayList<>();
    public static ArrayList<Usable> usables = new ArrayList<>();
    public static ArrayList<Collectable> collectables = new ArrayList<>();
    public  static  ArrayList<Card> cards = new ArrayList<>();
    public static ArrayList<Account> accounts = new ArrayList<>();


    public static void getItems(){
        items.addAll(usables);
        items.addAll(collectables);
    }

    public static void main(String[] args) throws IOException{
        Primary.Json();
    }

    public static void getHeroes() throws FileNotFoundException {
        YaGson gson = new YaGson();
        BufferedReader reader = new BufferedReader(new FileReader("Hero.json"));
        JsonStreamParser jsonStreamParser = new JsonStreamParser(reader);
        while (jsonStreamParser.hasNext()) {
            JsonElement jsonElement = jsonStreamParser.next();
            if (jsonElement.isJsonObject()) {
                Hero hero = gson.fromJson(jsonElement, Hero.class);
                heroes.add(hero);
            }
        }
    }

    public static void getMinions() throws FileNotFoundException {
        YaGson gson = new YaGson();
        BufferedReader reader = new BufferedReader(new FileReader("Minion.json"));
        JsonStreamParser jsonStreamParser = new JsonStreamParser(reader);
        while (jsonStreamParser.hasNext()) {
            JsonElement jsonElement = jsonStreamParser.next();
            if (jsonElement.isJsonObject()) {
                Minion minion = gson.fromJson(jsonElement, Minion.class);
                minions.add(minion);
            }
        }
    }

    public static void getUsables() throws FileNotFoundException {
        YaGson gson = new YaGson();
        BufferedReader reader = new BufferedReader(new FileReader("Usables.json"));
        JsonStreamParser jsonStreamParser = new JsonStreamParser(reader);
        while (jsonStreamParser.hasNext()) {
            JsonElement jsonElement = jsonStreamParser.next();
            if (jsonElement.isJsonObject()) {
                Usable usable = gson.fromJson(jsonElement, Usable.class);
                usables.add(usable);
            }
        }
    }

    public static void getCollectables() throws FileNotFoundException {

        YaGson gson = new YaGson();
        BufferedReader reader = new BufferedReader(new FileReader("Collectables.json"));
        JsonStreamParser jsonStreamParser = new JsonStreamParser(reader);
        while (jsonStreamParser.hasNext()) {
            JsonElement jsonElement = jsonStreamParser.next();
            if (jsonElement.isJsonObject()) {
                Collectable collectable = gson.fromJson(jsonElement, Collectable.class);
                collectables.add(collectable);
            }
        }
    }

    public static void getSpells() throws FileNotFoundException {
        YaGson gson = new YaGson();
        BufferedReader reader = new BufferedReader(new FileReader("Spell.json"));
        JsonStreamParser jsonStreamParser = new JsonStreamParser(reader);
        while (jsonStreamParser.hasNext()) {
            JsonElement jsonElement = jsonStreamParser.next();
            if (jsonElement.isJsonObject()) {
                Spell spell = gson.fromJson(jsonElement, Spell.class);
                spells.add(spell);
            }
        }
    }

    public static void getAccounts() throws IOException {
        YaGson gson = new YaGson();
        BufferedReader reader = new BufferedReader(new FileReader("Account.json"));
        JsonStreamParser jsonStreamParser = new JsonStreamParser(reader);
        if(jsonStreamParser.hasNext()) {
            while (jsonStreamParser.hasNext()) {
                JsonElement jsonElement = jsonStreamParser.next();
                if (jsonElement.isJsonObject()) {
                    accounts.add(gson.fromJson(jsonElement, Account.class));
                }
            }
        }
        reader.close();
    }

    public static  void getCards(){
        cards.addAll(heroes);
        cards.addAll(minions);
        cards.addAll(spells);
    }

    public static void pre() throws IOException, DeckAlreadyHasThisItemException, DeckAlreadyHasAHeroException, FullDeckException, DeckAlreadyHasThisCardException {
        getAccounts();
        Account.updateAccounts();
        generateAI();
    }


    public static void setDefaultDeck(Deck deck) throws IOException {
        File file  = new File("Decks"+ File.separator + deck.getName() +".json");
        YaGson gson = new YaGson();
        FileWriter fileWriter = new FileWriter(file, false);
        gson.toJson(deck, fileWriter);
        fileWriter.close();
    }

    public static void loadDefaultDecks() throws IOException {
        File folder = new File("Decks");
        File[] decks = folder.listFiles();
        for (File deck : decks) {
            YaGson gson = new YaGson();
            BufferedReader reader = new BufferedReader(new FileReader(deck));
            JsonStreamParser jsonStreamParser = new JsonStreamParser(reader);
            if(jsonStreamParser.hasNext()){
                while (jsonStreamParser.hasNext()){
                    JsonElement jsonElement = jsonStreamParser.next();
                    if(jsonElement.isJsonObject()){
                        Deck defaulfDeck = gson.fromJson(jsonElement, Deck.class);
                        defaultDecks.add(defaulfDeck);
                        defaultNames.add(defaulfDeck.getName());
                    }
                }
            }
            reader.close();
        }
    }


    public static void preprocess() throws IOException{
        getHeroes();
        getMinions();
        getSpells();
        getUsables();
        getCollectables();
        getCards();
        getItems();

    }

    private static void generateAI() throws DeckAlreadyHasAHeroException, DeckAlreadyHasThisCardException, FullDeckException, DeckAlreadyHasThisItemException {
        //level 1
        Account.AI[1].clearCollection();
        Deck deck = new Deck("AIDeck",Account.AI[1].getCollection());
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

                Account.AI[1].getCollection().forcePushDeck(deck);
         //level 2
        Account.AI[2].clearCollection();
        deck = new Deck("AIDeck",Account.AI[2].getCollection());

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

                Account.AI[2].getCollection().forcePushDeck(deck);

        Account.AI[3].clearCollection();
        deck=new Deck("AIDeck",Account.AI[3].getCollection());
                deck.addCardToDeck(Primary.heroes.get(6));

                deck.addCardToDeck(Primary.minions.get(5));
                deck.addCardToDeck(Primary.minions.get(6));
                deck.addCardToDeck(Primary.minions.get(9));
                deck.addCardToDeck(Primary.minions.get(13));
//                deck.addCardToDeck(Primary.minions.get(15));
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
        Account.AI[3].getCollection().forcePushDeck(deck);

    }

    public static void saveCustomSpell(Spell costumSpell) throws IOException {
        spells.add(costumSpell);
        YaGson gson = new YaGson();
        FileWriter fileWriter = new FileWriter("Spell.json", false);
        for (Spell spell :
                spells) {
            gson.toJson(spell, fileWriter);
            fileWriter.write("\n");
        }
        fileWriter.close();
    }

    public static void Json() throws IOException {
        //Spell
        spells.add(new Spell("Total Disarm", 1000, 0, -1, 1, "disarm till the end",
                TargetEnemyCard.getTargetInstance(), ActionDisarm.getAction()));
        spells.add(new Spell("Area Dispel", 1500, 2, 1, 1, "kills the negative buffs of your own cards and positive buffs of enemy cards in 2*2 area",
                TargetTwoByTwo.getTargetInstance(), ActionDispel.getAction()));
        spells.add(new Spell("Empower", 250, 1, 1, 2, "increases Attack Point 2 units",
                TargetOwnCard.getTargetInstance(), ActionChangeAP.getAction()));
        spells.add(new Spell("Fireball", 400, 1, 1, -4, "increases Attack Point 4 units",
                TargetEnemyCard.getTargetInstance(), ActionChangeHP.getAction()));
        spells.add(new Spell("God Strength", 450, 2, 1, 4, "increases Attack Point of Hero 4 units",
                TargetOwnHero.getTargetInstance(), ActionChangeAP.getAction()));
        spells.add(new Spell("Hell Fire", 600, 3, 2, 0, "fireCell, duration : 2",
                TargetTwoByTwo.getTargetInstance(), ActionApplyFirecell.getAction()));
        spells.add(new Spell("Lightning Bolt", 1250, 2, 1, -8, "attacks enemy's Hero 8 units",
                TargetEnemyHero.getTargetInstance(), ActionChangeHP.getAction()));
        spells.add(new Spell("Poison Lake", 900, 5, 1, 0, "poisonCell, duration : 1",
                TargetThreeByThree.getTargetInstance(), ActionPoisonCell.getAction()));
        Spell maddness = new Spell("Madness", 650, 0, 3, 4,"increases Attack Point 4 units, duration : 3, but the card will be disarmed",
                TargetOwnCard.getTargetInstance(), ActionChangeAPBuff.getAction()) ;
        maddness.addAction(ActionDisarm.getAction() , 0 , 1 );
        spells.add(maddness);
        spells.add(new Spell("All Disarm", 2000, 9, 1, 0, "all of enemy cards will be disarmed, duration : 1",
                TargetAllEnemyCards.getTargetInstance(), ActionDisarm.getAction()));
        spells.add(new Spell("All Poison", 1500, 8, 4, 0, "all of enemy cards will be poisoned, duration : 4",
                TargetAllEnemyCards.getTargetInstance(), ActionDeployPoison.getAction()));
        spells.add(new Spell("Dispel", 2100, 0, 1, 0, "kills the negative buffs of your own card or positive buffs of enemy card",
                TargetSingleCell.getTargetInstance(), ActionDispel.getAction()));
        spells.add(new Spell("Health With Profit", 2250, 0, 3, -6, "2 holy buffs, duration : 2, a weakness buff decreases health point 6 units",
                TargetOwnCard.getTargetInstance(), ActionHealthWithProfit.getAction()));
        spells.add(new Spell("Power Up", 2500, 2, 1, 6, "gives you a powerbuff, increases attack point 6 units",
                TargetOwnCard.getTargetInstance(), ActionChangeAPBuff.getAction()));
        spells.add(new Spell("All Power", 2000, 4, -1, 2, "gives your own cards a powerbuff, increases attack point 2 units permanently",
                TargetAllOwnCards.getTargetInstance(), ActionChangeAPBuff.getAction()));
        spells.add(new Spell("All Attack", 1500, 4, 1, -6, "attacks all enemy cards by 6 units",
                TargetAllEnemyCards.getTargetInstance(), ActionChangeHP.getAction()));
        spells.add(new Spell("Weakening", 1000, 1, 1, -4,"gives an enemy minion a weakness buff, it decreases attack point 4 units",
                TargetEnemyMinion.getTargetInstance(), ActionChangeAPBuff.getAction()));
        spells.add(new Spell("Sacrifice", 1600, 2, 1, -6, "gives an own minion a power buff, it increases attack point 8 units , it gives weakness buff too, it decreases health point 6 units",
                new TargetOwnMinion(), ActionSacrifice.getAction()));
        spells.add(new Spell("Kings Guard", 1750, 9, 1, 8, "it kills a random minion in hero surroundings",
                TargetHeroSurroundings.getTargetInstance(), ActionKillMinion.getAction()));
        spells.add(new Spell("Shock", 1200, 1, 2, 0, "an enemy card will be stuned, duration : 2",
                TargetEnemyCard.getTargetInstance(), ActionStun.getAction()));

        writeJson(spells, "Spell.json");

        //Minion
        SpecialPower nullSpecialPower =  new SpecialPower("null SpecialPower", 0, 0, 0, 0, "it DOESNT have special power",
                null, ActionVoid.getAction());

        minions.add(new Minion("Persian Archer", 300, 2, 6,
                4, new Range(), 7, nullSpecialPower
               , SPATime.NULL, "just an ordinary range minion"));
        minions.add(new Minion("Persian Swordsman", 400, 2, 6,
                4, new Melee(), 0,
                new SpecialPower("Persian SwordsMan SpecialPower", 0, 0, 0, 0, "",
                        TargetEnemyCard.getTargetInstance(), ActionStun.getAction()), SPATime.ATTACK, "while attacking, enemy's card will be stunned"));
        minions.add(new Minion("Persian Lancer", 500, 1, 5,
                3, new Hybrid(), 3, nullSpecialPower
               , SPATime.NULL, "just an ordinary hybrid minion"));
        minions.add(new Minion("Persian Horseman", 200, 4, 10,
                6, new Melee(), 0, nullSpecialPower
               , SPATime.NULL, "just an ordinary melee minion"));
        minions.add(new Minion("Persian Warrior", 600, 9, 24,
                6, new Melee(), 0,
                new SpecialPower("Persian Warrior SpecialPower", 0, 0, 0, -5, "",
                        TargetEnemyCard.getTargetInstance(), SPActionPersianChamp.getSpecialPower()), SPATime.ATTACK, "be tedad dafati ke dar nobat haye qabl be yek niru hamle karde, 5 vahed bishtar be an zarbe vared mikonad"));
        //TODO in Actionesh chie?
        minions.add(new Minion("Persian General",800, 7, 12,
                4, new Melee(), 0,
                new SpecialPower("Persian General SpecialPower", 0, 0, 0, 0, "",
                        null, ActionCombo.getAction()), SPATime.COMBO, "SPActionCombo"));
        minions.add(new Minion("Turanian Archer", 500, 1, 3,
                4, new Range(), 5, nullSpecialPower
               , SPATime.NULL, "just an ordinary range minion"));
        minions.add(new Minion("Turanian Slinger", 600, 1, 4,
                2, new Range(), 7, nullSpecialPower
             , SPATime.NULL, "just an ordinary range minion"));
        minions.add(new Minion("Turanian Lancer", 600, 1, 4,
                4, new Hybrid(), 3, nullSpecialPower
               , SPATime.NULL, "just an ordinary hybrid minion"));

        SpecialPower turanianSpy = new SpecialPower("Turanian Spy SpecialPower", 0, 0, 1, 0, "",
                TargetEnemyCard.getTargetInstance(), ActionDisarm.getAction());
        turanianSpy.addAction(ActionDeployPoison.getAction(), 0 ,4);
        minions.add( new Minion("Turanian SPY", 700, 4, 6,
                6, new Melee(), 0, turanianSpy,
                SPATime.ATTACK, "enemy's card will be disarmed for 1 turn and will be poisoned for 4 turns"));

        minions.add(new Minion("Turanian MaceBearer", 450, 2, 3,
                10, new Melee(), 0, nullSpecialPower
               , SPATime.NULL, "just a melee minion"));
        minions.add(new Minion("Turanian Prince",800, 6, 6,
                10, new Melee(), 0,
                new SpecialPower("Turanian Prince", 0, 0, 0, 0, "",
                        null, ActionCombo.getAction()), SPATime.COMBO, "combo"));
        minions.add(new Minion("Black Demon", 300, 9, 14,
                10, new Hybrid(), 7, nullSpecialPower
               , SPATime.NULL, "just a hybrid minion"));
        minions.add(new Minion("Stone Thrower Giant", 300, 9, 12,
                12, new Range(), 7, nullSpecialPower
                ,null, "just a range minion"));
        minions.add(new Minion("Eagle", 200, 2, 0,
                2, new Range(), 3,
                new SpecialPower("Eagle SpecialPower", 0, 0, 0, 10, "",
                        TargetSingleCell.getTargetInstance(), ActionChangeHPBuff.getAction()), SPATime.PASSIVE, "has power buff, increases health point 10 units"));

        minions.add(new Minion("Hog Rider Demon", 300, 6, 16,
                8, new Melee(), 0, nullSpecialPower
              , SPATime.NULL, "just a melee minion"));
        minions.add(new Minion("One Eye Giant", 500, 7, 12,
                11, new Hybrid(), 3,
                new SpecialPower("One Eye Giant SpecialPower", 0, 0, 0, -2, "",
                        RandomMinionInSurrounding.getTargetInstance(), ActionChangeHPBuff.getAction()), SPATime.DEATH, "attacks surrounding minions 2 points, on death"));
        minions.add(new Minion("Venomous Snake", 300, 4, 5,
                6, new Range(), 4,
                new SpecialPower("VenomousSnake", 0, 0, 0, 3, "",
                        TargetEnemyCard.getTargetInstance(), ActionDeployPoison.getAction()), SPATime.ATTACK, "enemy's card will be poisoned, duration : 3"));
        minions.add(new Minion("Fire Dragon", 250, 5, 9,
                5, new Range(), 4, nullSpecialPower
               , SPATime.NULL,"just an ordinary range minion"));
        minions.add(new Minion("Fierce Lion",600, 2, 1,
                8, new Melee(), 0,
                new SpecialPower("Fierce Lion SpecialPower", 0, 0, 0, 0, "",
                        TargetEnemyCard.getTargetInstance(), ActionDispel.getAction()), SPATime.ATTACK, "holy buff doesn't effect its attack"));
        //todo fateme
        minions.add(new Minion("Giant Snake", 500, 8, 14,
                7, new Range(), 5,
                new SpecialPower("Giant Snake SpecialPower", 0, 0, -1, -1, "",
                        TargetEnemyMinionswithin2ManhattanDistance.getTargetInstance(), ActionChangeHPBuff.getAction()), SPATime.ATTACK, "akse holy buff"));
        minions.add(new Minion("White Wolf", 400, 5, 8,
                2, new Melee(), 0,
                new SpecialPower("White Wolf SpecialPower", 0, 0, 2, -6, "",
                        TargetEnemyMinion.getTargetInstance(), ActionChangeHPBuff.getAction()), SPATime.ATTACK, "when it attacks a minion, next turn, minion's health point will be decreased 6 units, next turn, minion's health point will be decreased 4 units"));//unhanddeled
        minions.add(new Minion("Leopard",400, 4, 6,
                2, new Melee(), 0,
                new SpecialPower("Leopard SpecialPower", 0, 0, 1, -8, "",
                        TargetEnemyMinion.getTargetInstance(), ActionChangeHPBuff.getAction()), SPATime.ATTACK, "when it attacks a minion, next turn, minion's health point will be decreased 8 units"));
        minions.add(new Minion("Wolf", 400, 3, 6,
                1, new Melee(), 0,
                new SpecialPower("Wolf SpecialPower", 0, 0, 0, -6, "",
                        TargetEnemyMinion.getTargetInstance(), ActionChangeHPBuff.getAction()), SPATime.ATTACK, "when it attacks a minion, next turn, minion's health point will be decreased 6 units"));

        SpecialPower theWizard =  new SpecialPower("The Wizard SpecialPower", 0, 0, 1, 2, "",
                RandomMinionInSurrounding.getTargetInstance(), ActionChangeAPBuff.getAction());
        theWizard.addAction(ActionChangeHPBuff.getAction(), -1, 1);
        minions.add(new Minion("The Wizard", 550, 4, 5,
                4, new Range(), 3, theWizard, SPATime.PASSIVE
                ,"gives own and minions surrounded a power buff, increases attack point 2 units + a weakness buff, decreases health point 1 unit for one turn"));

        SpecialPower theGreatWizard = new SpecialPower("The Great Wizard SpecialPower", 0, 0, -1, 2, "",
                OwnMinionAndItsSurrounding.getTargetInstance(), ActionChangeAPBuff.getAction());
        theGreatWizard.addAction(ActionDeployHollyBuff.getAction(), 0, -1);
        minions.add(new Minion("The Great Wizard",550, 6, 6,
                6, new Range(), 5, theGreatWizard
                ,SPATime.PASSIVE, "gives own and minions surrounded a continuous power buff, increases attack point 2 units + a continuous holy buff "));

        minions.add(new Minion("Genie", 500, 5, 10,
                4, new Range(), 4,
                new SpecialPower("Genie SpecialPower", 0, 0, -1, 1, "",
                        TargetAllOwnMinions.getTargetInstance(), ActionChangeAPBuff.getAction()), SPATime.ON_TURN, "a continuous power buff, increases attack point 1 unit"));

        /*minions.add(new Minion("Wild Goraz",500, 6, 10,
                14, new Melee(), 0,
                , SPATime.DEFEND));
        minions.add(new Minion("Piran",400, 8, 20,
                12, new Melee(), 0,
                , SPATime.DEFEND));
        minions.add(new Minion("Giv",450, 4, 5,
                7, new Range(), 5,
                , SPATime.DEFEND));*///Emtiazi

        minions.add(new Minion("Bahman", 450, 8, 16,
                9, new Melee(), 0,
                new SpecialPower("Bahman SpecialPower", 0, 0, 1, -16, "",
                        TargetEnemyMinion.getTargetInstance(), ActionChangeHPBuff.getAction()), SPATime.SPAWN, "decreases health point of a random enemy minion 16 units "));

        /*inions.add(new Minion("Ashkbus",400, 7, 14,
                8, new Melee(), 0,
                , SPATime.DEFEND));*///emtiazi

        minions.add(new Minion("Iraj", 500, 4, 6,
                20, new Range(), 3, nullSpecialPower
                , SPATime.NULL, "just a range minion"));
        minions.add(new Minion("Great Giant", 600, 9, 30,
                8, new Hybrid(), 2, nullSpecialPower
              , SPATime.NULL, "just a hybrid minion"));
        minions.add(new Minion("Two Headed Giant",550, 4, 10,
                4, new Melee(), 0,
                new SpecialPower("Two Headed Giant SpecialPower", 0, 0, 1, 0, "",
                        TargetEnemyCard.getTargetInstance(), ActionDispelPositives.getAction()), SPATime.ATTACK, "every card that it attacks will be diarmed"));

        minions.add(new Minion("Mother Ice", 500, 3, 3,
                4, new Range(), 5,
                new SpecialPower("Mother Ice SpecialPower", 0, 0, 1, 0, "",
                        TargetAllMinionsInSurrounding.getTargetInstance(), ActionStun.getAction()), SPATime.SPAWN, "enemy minions surrounding stun for 1 turn"));
        minions.add(new Minion("Foolad Zereh", 650, 3, 1,
                1, new Melee(), 0,
                new SpecialPower("Foolad zereh SpecialPower", 0, 0, -1, 12, "",
                        TargetSingleCell.getTargetInstance(), ActionDeployHollyBuff.getAction()), SPATime.PASSIVE, "12 holy buffs continuously"));
        minions.add(new Minion("Siavash", 350, 4, 8,
                5, new Melee(), 0,
                new SpecialPower("Siavash SpecialPower", 0, 0, 0, -6, "",
                        TargetEnemyHero.getTargetInstance(), ActionChangeHPBuff.getAction()), SPATime.DEATH, "attacks enemy's hero 6 points, on death"));
        minions.add(new Minion("Eurymedon",600, 5, 10,
                4, new Melee(), 0,
                new SpecialPower("Eurymedon SpecialPower", 0, 0, 0, 0, "",
                        null, ActionCombo.getAction()), SPATime.COMBO, "SPActionCombo"));
        minions.add(new Minion("Arzhang Div",600, 3, 6,
                6, new Melee(), 0, new SpecialPower("Arzhangs SpecialPower", 0, 0, 0, 0, "",
                null, ActionCombo.getAction())
                , SPATime.COMBO, "SPActionCombo"));
        writeJson(minions, "Minion.json" );
        //Hero
        ArrayList<Hero> heroes = new ArrayList<>();
        heroes.add(new Hero("White Demon", 8000, 50, 4, new Melee(), 0,
                new SpecialPower("White Demon", 0, 1, -1, 4, "",
                        TargetSingleCell.getTargetInstance(), ActionChangeAPBuff.getAction())
                , 0, 2, "a melee hero with special power of power buff with increasing attack point 4 units continuously"));
        heroes.add(new Hero("Simorgh", 9000, 50, 4, new Melee(), 0,
                new SpecialPower("Simorgh", 0, 5, 1, 0, "",
                        TargetAllEnemyCards.getTargetInstance(), ActionStun.getAction())
                , 0, 8, "a melee hero with special power of stuning all enemy cards for 1 turn"));
        heroes.add(new Hero("Seven Headed Dragon", 50, 4, 0, new Melee(), 0,
                new SpecialPower("Seven Headed Dragon", 0, 0, 1, 0, "",
                        TargetEnemyCard.getTargetInstance(), ActionDisarm.getAction())
                , 0, 1, "a melee hero with special power of disarming 1 enemy"));
        heroes.add(new Hero("Rakhsh", 8000, 50, 4, new Melee(), 0,
                new SpecialPower("Rakhsh", 0, 1, 1, 0, "",
                        TargetEnemyCard.getTargetInstance(), ActionStun.getAction())
                , 0, 2, "a melee hero with special power of stunning enemy for 1 turn"));
        heroes.add(new Hero("Zahak", 10000, 50, 2, new Melee(), 0,
                new SpecialPower("Zahak", 0, 0, 3, 0, "",
                        TargetEnemyCard.getTargetInstance(), ActionDeployPoison.getAction())
                , 0, 0, "a melee hero with special power of poisoning enemy 3 turns after attack"));
        heroes.add(new Hero("Kaveh", 8000, 50, 4, new Melee(), 0,
                new SpecialPower("Kaveh", 0, 1, 3, 0, "",
                        TargetSingleCell.getTargetInstance(), ActionHollyCell.getAction()),
                0, 3, "a melee hero with special power of hollycell for 3 turns"));
        heroes.add(new Hero("Arash", 10000, 30, 2, new Range(), 6,
                new SpecialPower("Arash", 0, 2, 1, -4, "",
                        TargetOwnHeroRow.getTargetInstance(), ActionChangeHPBuff.getAction())
                , 0, 2, "a range hero with special power of attacking its own row cards 4 points"));
        heroes.add(new Hero("Afsane", 11000, 40, 3, new Range(), 3,
                new SpecialPower("Afsane", 0, 1, 1, 0, "",
                        TargetEnemyCard.getTargetInstance(), ActionDispel.getAction())
                , 0, 2, "a range hero with special power of dispelling 1 enemy card"));
        heroes.add(new Hero("EsfanDar", 12000, 35, 3, new Hybrid(), 3,
                new SpecialPower("EsfanDar", 0, 0, -1, 3, "",
                        TargetSingleCell.getTargetInstance(), ActionDeployHollyBuff.getAction()),
                0, 0, "a hybrid hero with a special power of  holy buffs continuously"));
        heroes.add(new Hero("Rostam", 8000, 55, 7, new Hybrid(), 4, nullSpecialPower
               , 0, 0, "just a hybrid hero"));

        writeJson(heroes, "Hero.json");

        //item
        usables.add(new Usable("Wisdom Crown", 300, 3, 1, "increases mana first 3 turn",
                TargetSingleCell.getTargetInstance(), ItemActionExtraMana.getItemAction()));
        usables.add(new Usable("Shield AF", 4000, 1, 12, "for own hero, 12 holy buff",
                TargetOwnHero.getTargetInstance(), ItemActionShieldAF.getItemAction()));
        usables.add(new Usable("Damool Arch", 30000, 1, 0, "only for ranged or hybrid own hero, while attacking disarms enemy card, duration : 1",
                TargetOwnHero.getTargetInstance(), ItemActionDamoolArch.getItemAction()));
        usables.add(new Usable("Simorgh feather", 3500, 1, -2, "only for ranged or hybrid enemy hero, decreases attack point 2 units",
                TargetEnemyHero.getTargetInstance(), ItemAction30chicken.getItemAction()));
        usables.add(new Usable("Terror Hood", 5000, 1, -2, "while attacking, weakness buff on random enemy card, decreases attack point 2 units",
                TargetRandomEnemy.getTargetInstance(), ItemActionChangeAPBuff.getItemAction()));
        usables.add(new Usable("King Wisdom", 9000, -1, 0, "gets extra mana every turn",
                TargetSingleCell.getTargetInstance(), ItemActionKingsWisdom.getItemAction()));
        usables.add(new Usable("Assassination Dagger", 15000, 1, 1, "while putting own cards, attacks enemy hero 1 point",
                TargetEnemyHero.getTargetInstance(), ItemActionAssasinationDagger.getItemAction()));
        usables.add(new Usable("Poisonous Dagger", 7000, 1, 0, "own card while attacking poisons random enemy card",
                TargetRandomEnemy.getTargetInstance(), ItemActionPoisonousDagger.getItemAction()));
        usables.add(new Usable("Shock Hammer", 15000, 1, 0, "own hero while attacking disarms an enemy card, duration : 1",
                TargetEnemyCard.getTargetInstance(), ItemActionDisArm.getItemAction()));
        usables.add(new Usable("Soul Eater", 25000, 1, 1, "on death of one of own cards, every own card gets power buff, increases attck point 1 unit",
                TargetOwnCard.getTargetInstance(), ItemActionChangeAPBuff.getItemAction()));
        usables.add(new Usable("‌Baptism", 20000, 2, 0, "every minion when spawns gets holy buff, duration : 2",
                TargetOwnMinion.getTargetInstance(), ItemActionBaptism.getItemAction()));

        writeJson(usables, "Usables.json");

        collectables.add(new Collectable("NooshDaru", 1, 6, "increases health point of a random card 6 units",
                TargetRandomOwn.getTargetInstance(), ItemActionChangeHP.getItemAction()));
        collectables.add(new Collectable("Two Headed Arrow", 1, 2, "increases attack point of random ranged or hybrid 2 units",
                TargetOwnRandomRangedAndHybridMinion.getTargetInstance(), ItemAction2HornedArrow.getItemAction()));
        collectables.add(new Collectable("Exir", 1, 3, "increases health point 3 units, a power buff with increasing ",
                TargetRandomOwnMinion.getTargetInstance(), ItemActionExir.getItemAction()));
        collectables.add(new Collectable("Mana's Majoon", 1, 3, "gives 3 extra mana the next turn",
                TargetSingleCell.getTargetInstance(), ItemActionManaGiver.getItemAction()));
        collectables.add(new Collectable("RooEnTan's Majoon",2,10, "10 holy buffs, duration : 2, for a random own card",
                TargetRandomOwn.getTargetInstance(), ItemActionHolyBuff.getItemAction()));
        collectables.add(new Collectable("Death's Curse", 0, 8, "8 attack points on nearest enemy card for a random minion ",
                TargetRandomOwnMinion.getTargetInstance(), ItemActionMinionRandomAttacker.getItemAction()));
        collectables.add(new Collectable("Random damage", 1, 2, "2 attack points for random card",
                TargetRandomOwn.getTargetInstance(), ItemActionRandomDamage.getItemAction()));
        collectables.add(new Collectable("Blades of agility", 1, 6, "6 attack points for a random card",
                TargetRandomOwn.getTargetInstance(), ItemActionChangeAP.getItemAction()));
        collectables.add(new Collectable("Chineese Sword", 1, 5, "5 attack points for melee",
                TargetMelee.getTargetInstance(), ItemActionChangeAP.getItemAction()));
        writeJson(collectables, "Collectables.json");
    }

    private static <E> void writeJson(ArrayList<E> arrays, String path) throws IOException {
        YaGson gson = new YaGson();
        FileWriter fileWriter = new FileWriter(path, false);
        for (E e:
                arrays) {
            gson.toJson(e, fileWriter);
            fileWriter.write("\n");
        }
        fileWriter.close();
    }

    public static void initGraphics() throws FileNotFoundException {
        setHermionesAvatars();
        setCardsAvatar();
        setGraphicsForHermiones();
        setBattleGraphicsForHermione();
    }

    private static void setCardsAvatar(){
        for (Card card : cards) {
            Image image = new Image("../../../../resources/ui/artifact_f6_winterblade.png");
            card.getCardGraphics().setAvatar(image);
        }
    }

    private static void setHermionesAvatars() throws FileNotFoundException {
        //heroes
        for (Hero hero : heroes) {
            setHermioneAvatar(hero, "resources/units/boss_borealjuggernaut.png");
        }
        //minions
        for (Minion minion : minions) {
            setHermioneAvatar(minion, "resources/units/boss_decepticle.png");
        }
    }
    private static void setHermioneAvatar(Hermione hermione, String path) throws FileNotFoundException {

        Image image = new Image(path);
        hermione.getGraphics().setAvatar(image);
    }

    private static void setGraphicsForHermiones(){
        for (Hero hero : heroes) {
            setGraphicForHermione(hero);
        }

        for (Minion minion : minions) {
            setGraphicForHermione(minion);
        }
    }
    private static void setGraphicForHermione(Hermione hermione) {
        hermione.getGraphics().addAttackListenr(new OnAttackListener() {
            @Override
            public void show(Hermione enemyCard) {
                System.out.println("animation");

            }
        });
        hermione.getGraphics().addCardSelectedListener(new OnCardSelectedListener() {
            @Override
            public void show(String state) {

            }
        });
        hermione.getGraphics().addDamageListeners(new OnDamageListener() {
            @Override
            public void show() {

            }
        });
        hermione.getGraphics().addDeathListener(new OnDeathListener() {
            @Override
            public void show() {

            }
        });
        hermione.getGraphics().addMoveListener(new OnMoveListener() {
            @Override
            public void show(Cell cell) {

            }
        });
        hermione.getGraphics().addSpawnListenr(new OnSpawnListener() {
            @Override
            public void show(Cell cell) {
                BattleFXMLC controller = (BattleFXMLC) hermione.getGraphics().getBattleMenu().getGraphic().getController();
                ImageView imageView = new ImageView(hermione.getGraphics().getAvatar());
                controller.addToScene(imageView);
                imageView.setX(controller.getMapX() + cell.getX() * controller.getCellWidth());
                imageView.setY(controller.getMapY() + cell.getY() * controller.getCellHeight());
                final Animation animation = new SpriteAnimation(
                        imageView,
                        Duration.millis(2000),
                        1, 1,
                        0, 0,
                        85, 85
                );
                animation.setCycleCount(Animation.INDEFINITE);
                animation.play();
            }
        });

        hermione.getGraphics().addSpecialPowerAppliedListener(new OnSpeacialPowerAppliedListeners() {
            @Override
            public void show(Cell cell) {

            }
        });
    }

    private static void setBattleGraphicsForHermione(){
        for (Hero hero : heroes) {
            hero.getGraphics().addSpawnListenr(new OnSpawnListener() {
                @Override
                public void show(Cell cell) {

                }
            });
        }
    }
}
