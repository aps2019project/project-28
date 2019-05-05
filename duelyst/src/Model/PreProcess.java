package Model;

import Model.card.hermione.*;
import Model.card.spell.*;
import Model.card.spell.SpellAction.*;
import Model.card.spell.SpellAction.ActionChangeAP;
import Model.card.spell.SpellAction.ActionChangeHP;
import Model.card.spell.SpellAction.ActionDisarm;
import Model.card.spell.SpellAction.ActionStun;
import Model.card.spell.Targets.*;
import Model.item.Collectable;
import Model.item.Usable;
import com.google.gson.*;

import java.io.FileWriter;
import java.util.ArrayList;

public class PreProcess{

    public static void preProcess() throws java.io.IOException{
        Gson gson = new Gson();

        //Spell

        ArrayList<Spell> spells = new ArrayList<>();
        spells.add(new Spell("Total Disarm", 1000, 0, -1, 1,
                TargetEnemyCard.getTargetInstance(), ActionDisarm.getAction()));
        spells.add(new Spell("Area Dispel", 1500, 2, 1, 1,
                TargetTwoByTwo.getTargetInstance(), ActionDispel.getAction()));
        spells.add(new Spell("Empower", 250, 1, 1, 2,
                TargetOwnCard.getTargetInstance(), ActionChangeAP.getAction()));
        spells.add(new Spell("Fireball", 400, 1, 1,4,
                TargetEnemyCard.getTargetInstance(), ActionChangeAP.getAction()));
        spells.add(new Spell("God Strength", 450, 2, 1, 4,
                TargetOwnHero.getTargetInstance(), ActionChangeAP.getAction()));
        spells.add(new Spell("Hell Fire", 600, 3, 2, 0,
                TargetTwoByTwo.getTargetInstance(), ActionApplyFirecell.getAction()));
        spells.add(new Spell("Lightning Bolt", 1250, 2, 1, 8,
                TargetEnemyHero.getTargetInstance(), ActionChangeAP.getAction()));
        spells.add(new Spell("Poison Lake", 900, 5, 1, 0,
                TargetThreeByThree.getTargetInstance(), ActionPoisonCell.getAction()));
        spells.add(new Spell("Madness", 650, 0, 3, 4,
                TargetOwnCard.getTargetInstance(), ActionChangeAP.getAction(), ActionDisarm.getAction()));
        spells.add(new Spell("All Disarm", 2000, 9, 1, 0,
                TargetAllEnemyCards.getTargetInstance(), ActionDisarm.getAction()));
        spells.add(new Spell("All Poison", 1500, 8, 4, 0,
                TargetAllEnemyCards.getTargetInstance(), ActionAllPoison.getAction()));

        spells.add(new Spell("Dispel",2100, 0, 1, 0,
                TargetAllCards.getTargetInstance(), ActionDispel.getAction(),));

        spells.add(new Spell("Health With Profit", 2250, 0, 3, -6,
                TargetOwnCard.getTargetInstance(), ActionHealthWithProfit.getAction()));

        spells.add(new Spell("Power Up", 2500, 2, 1, 6,
                TargetOwnCard.getTargetInstance(), ));

        spells.add(new Spell("All Power", 2000, 4, -1, 2,
                TargetAllOwnCards.getTargetInstance(), ActionChangeAP.getAction()));
        spells.add(new Spell("All Attack", 1500, -4, 1, 6,
                TargetAllEnemyCards.getTargetInstance()));

        spells.add(new Spell("Weakening", 1000, 1, 1, -4,
                TargetEnemyMinion.getTargetInstance(), ));
        spells.add(new Spell("Sacrifice", 1600, 2, 1, -6,
                new TargetOwnMinion(), ));

        spells.add(new Spell("Kings Guard", 1750, 9, 1, 8,
                TargetHeroSurroundings.getTargetInstance(), ActionKillMinion.getAction()));
        spells.add(new Spell("Shock", 1200, 1, 2, 0,
                TargetEnemyCard.getTargetInstance(), ActionStun.getAction()));

        for (Spell spell:
             spells) {
            gson.toJson(spell, new FileWriter("Card.Json", true));
        }


        //Minion

        ArrayList<Minion> minions = new ArrayList<>();
        minions.add(new Minion("Persian Archer",300, 2, 6,
                4, new Range(), 7,
                null, null));
        minions.add(new Minion("Persian Swordsman",400, 2, 6,
                4, new Melee(), 0,
                new SpecialPower("Persian SwordsMan SpecialPower", 0, 0, 0, 0,
                        null, ActionStun.getAction()), "OnAttack"));
        minions.add(new Minion("Persian Lancer",500, 1, 5,
                3, new Hybrid(), 3,
                null, null));
        minions.add(new Minion("Persian Horseman",200, 4, 10,
                6, new Melee(), 0,
                null, null));
        minions.add(new Minion("Persian Warrior",600, 9, 24,
                6, new Melee(), 0,
                new SpecialPower("Persian Warrior SpecialPower", 0, 0, 0, 5,
                        , ActionChangeAP.getAction()), "On Attack"));
        
        minions.add(new Minion("Persian General",800, 7, 12,
                4, new Melee(), 0,
                ,"Combo"));
        minions.add(new Minion("Turanian Archer",500, 1, 3,
                4, new Range(), 5,
                null, null));
        minions.add(new Minion("Turanian Slinger",600, 1, 4,
                2, new Range(), 7,
                null, null));
        minions.add(new Minion("Turanian Lancer",600, 1, 4,
                4, new Hybrid(), 3,
                null, null));
        minions.add(new Minion("Turanian SPY",700, 4, 6,
                6, new Melee(), 0,
                new Spell("Turanian Spy SpecialPower", 0, 0, 0, 4,
                        TargetEnemyCard.getTargetInstance(), ActionDisarm.getAction(),)));

        minions.add(new Minion("Turanian MaceBearer",450, 2, 3,
                10, new Melee(), 0, null, null));
        minions.add(new Minion("Turanian Prince",800, 6, 6,
                10, new Melee(), 0,
                , "Combo"));
        minions.add(new Minion("Black Demon",300, 9, 14,
                10, new Hybrid(), 7, null, null));
        minions.add(new Minion("Stone Thrower Giant",300, 9, 12,
                12, new Range(), 7, null, null));
        minions.add(new Minion("Eagle",200, 2, 0,
                2, new Range(), 3,
                new SpecialPower("Eagle SpecialPower", 0, 0, 0, 10,
                        null, ActionChangeHP.getAction()),"Passive"));

        minions.add(new Minion("Hog Rider Demon",300, 6, 16,
                8, new Melee(), 0,
                null, null));
        minions.add(new Minion("One Eye Giant",500, 7, 12,
                11, new Hybrid(), 3,
                new SpecialPower("One Eye Giant SpecialPower", 0, 0, 0, 2,
                        , ActionChangeAP.getAction()), "OnDeath" ));
        minions.add(new Minion("Venomous Snake",300, 4, 5,
                6, new Range(), 4,
                new SpecialPower("VenomousSnake", 0, 0, 0, 3,
                        TargetEnemyCard.getTargetInstance(), ), "OnAttack"));
        minions.add(new Minion("Fire Dragon",250, 5, 9,
                5, new Range(), 4,
                null, null));
        minions.add(new Minion("Fierce Lion",600, 2, 1,
                8, new Melee(), 0,
                new SpecialPower("Fierce Lion SpecialPower", 0, 0, 0, 0,
                        null, ), "OnAttack"));

        minions.add(new Minion("Giant Snake",500, 8, 14,
                7, new Range(), 5,
                , "OnSpawn"));
        minions.add(new Minion("White Wolf",400, 5, 8,
                2, new Melee(), 0,
                , "OnAttack"));
        minions.add(new Minion("Leopard",400, 4, 6,
                2, new Melee(), 0,
                ,"OnAttack"));
        minions.add(new Minion("Wolf",400, 3, 6,
                1, new Melee(), 0,
                new SpecialPower("Wolf SpecialPower", 0, 0, 0, -6,
                        , ActionChangeHP.getAction()), "OnAttack"));
        minions.add(new Minion("The Wizard",550, 4, 5,
                4, new Range(), 3,
                ,"Passive"));

        minions.add(new Minion("The Great Wizard",550, 6, 6,
                6, new Range(), 5,
                ,"Passive"));
        minions.add(new Minion("Genie",500, 5, 10,
                4, new Range(), 4,
                new SpecialPower("Genie SpecialPower", 0, 0, 0, 1,
                        , ActionChangeAP.getAction()), "OnTurn"));
        minions.add(new Minion("Wild Goraz",500, 6, 10,
                14, new Melee(), 0,
                , "OnDefend"));
        minions.add(new Minion("Piran",400, 8, 20,
                12, new Melee(), 0,
                , "OnDefend"));
        minions.add(new Minion("Giv",450, 4, 5,
                7, new Range(), 5,
                , "OnDefend"));

        minions.add(new Minion("Bahman",450, 8, 16,
                9, new Melee(), 0,
                , "On Spawn"));
        minions.add(new Minion("Ashkbus",400, 7, 14,
                8, new Melee(), 0,
                , "OnDefend"));
        minions.add(new Minion("Iraj",500, 4, 6,
                20, new Range(), 3,
                null, null));
        minions.add(new Minion("Great Giant",600, 9, 30,
                8, new Hybrid(), 2,
                null, null));
        minions.add(new Minion("Two Headed Giant",550, 4, 10,
                4, new Melee(), 0,
                , "OnAttack"));

        minions.add(new Minion("Mother Ice",500, 3, 3,
                4, new Range(), 5,
                new SpecialPower("Mother Ice SpecialPower", 0, 0, 0, 1,
                        , ActionStun.getAction()), "OnSpawn"));
        minions.add(new Minion("Foolad Zereh",650, 3, 1,
                1, new Melee(), 0,
                , "Passive"));
        minions.add(new Minion("Siavash",350, 4, 8,
                5, new Melee(), 0,
                new SpecialPower("Siavash SpecialPower", 0, 0, 0, 6,
                        TargetEnemyHero.getTargetInstance(), ActionChangeAP.getAction()), "OnDeath");
        minions.add(new Minion("Eurymedon",600, 5, 10,
                4, new Melee(), 0,
                , "Combo"));
        minions.add(new Minion("Arzhang Div",600, 3, 6,
                6, new Melee(), 0,
                , "Combo"));

        for (Minion minion:
                    minions){
            gson.toJson(minion, new FileWriter("Card.Json", true));
        }

        //Hero

        ArrayList<Hero> heroes = new ArrayList<>();
        heroes.add(new Hero("White Demon", 8000, 50, 4, new Melee(), 0,
                new SpecialPower("White Demon", 0, 1, 1,)
                , 0, 2));
        heroes.add(new Hero("Simorgh", 9000, 50, 4, new Melee(), 0,
                new SpecialPower("Simorgh", 0, 5, 1, 0,
                        TargetAllEnemyCards.getTargetInstance(), ActionStun.getAction())
                , 0, 8));
        heroes.add(new Hero("Seven Headed Dragon", 50, 4, 0, new Melee(), 0,
                new SpecialPower("Seven Headed Dragon", 0, 0, 1, 0,
                        TargetEnemyCard.getTargetInstance(), ActionDisarm.getAction())
                ,0, 1));
        heroes.add(new Hero("Rakhsh", 8000, 50, 4, new Melee(), 0,
                new SpecialPower("Rakhsh", 0, 1, 1, 0,
                        TargetEnemyCard.getTargetInstance(), ActionStun.getAction())
                , 0, 2));
        heroes.add(new Hero("Zahak", 10000, 50, 2, new Melee(), 0,

                , 0, 0));
        heroes.add(new Hero("Kaveh", 8000, 50, 4, new Melee(), 0,
                new SpecialPower("Kaveh", 0, 1, 3, 0,
                        TargetSingleCell.getTargetInstance(), ),
                0, 3));
        heroes.add(new Hero("Arash", 10000, 30, 2, new Range(), 6,
                new SpecialPower("Arash", 0, 2, 1, 4,
                        TargetOwnHeroRow.getTargetInstance(),)
                , 0, 2));
        heroes.add(new Hero("Afsane", 11000, 40, 3, new Range(), 3,
                new SpecialPower("Afsane", 0, 1, 1, 0,
                        TargetEnemyCard.getTargetInstance(), ActionDispel.getAction())
                , 0, 2));
        heroes.add(new Hero("EsfanDar", 12000, 35, 3, new Hybrid(), 3,
                new SpecialPower("EsfanDar", 0, 0, -1, 0,
                        , ActionDisarm.getAction()), 0, 0));
        heroes.add(new Hero("Rostam", 8000, 55, 7,new Hybrid(), 4,
                null, 0, 0));

        for (Hero hero:
             heroes) {
            gson.toJson(hero, new FileWriter("Card.Json", true));
        }


        //item

        ArrayList<Usable> usables = new ArrayList<>();
        usables.add(new Usable("Wisdom Crown", 300,3, 1, null, ActionIncreaseMana));
        usables.add(new Usable("Shield AF", 4000, 1, 12, ItemTargetOwnHero));
        usables.add(new Usable("Damool's Arc", 30000, ));
        usables.add(new Usable("Simorgh's feather", 3500, 1, 6, ItemTargetOwnCard, ItemActionIncreaseAP));
        usables.add(new Usable("Terror Hood", 5000, 1, -2, ));
        usables.add(new Usable("King Wisdom", 9000, -1, 6, ));
        usables.add(new Usable("Assassination Dagger", 15000, 1, 1, ));
        usables.add(new Usable("Poisonous Dagger", 7000, 1, 0, ));
        usables.add(new Usable("Shock Hammer", 15000, 2, 0, ));
        usables.add(new Usable("Soul Eater", 25000, 1, 1, ));
        usables.add(new Usable("‌Baptism", 20000, 2, 0, ));

        for (Usable usable:
             usables) {
            gson.toJson(usable, new FileWriter("Card.Json", true));
        }

        ArrayList<Collectable> collectables = new ArrayList<>();
        collectables.add(new Collectable("NooshDaru",));
        collectables.add(new Collectable("Two Headed Arrow",));
        collectables.add(new Collectable("Eksir",));
        collectables.add(new Collectable("Mana's Majoon",));
        collectables.add(new Collectable("RooEnTan's Majoon",));
        collectables.add(new Collectable("Death's Curse",));
        collectables.add(new Collectable("Random damage",));
        collectables.add(new Collectable("Blades of agility",));
        collectables.add(new Collectable("Chineese Sword",));

        for (Collectable collectable:
                collectables) {
            gson.toJson(collectable, new FileWriter("Card.Json", true));
        }
    }
}