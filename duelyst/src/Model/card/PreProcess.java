package Model.card;

import Model.Map.Cell;
import Model.card.hermione.*;
import Model.card.spell.*;
import Model.card.spell.Buff.BuffActions.BuffActionHolly;
import Model.card.spell.SpellAction.*;
import Model.card.spell.Buff.BuffActions.BuffActionPoison;
import Model.card.spell.SpellAction.ActionChangeAP;
import Model.card.spell.SpellAction.ActionChangeHP;
import Model.card.spell.SpellAction.ActionDisarm;
import Model.card.spell.SpellAction.ActionStun;
import Model.card.spell.TargetAllCards;
import Model.card.spell.TargetAllEnemyCards;
import Model.card.spell.TargetAllOwnCards;
import Model.card.spell.TargetEnemyCard;
import Model.card.spell.TargetEnemyHero;
import Model.card.spell.TargetEnemyHeroColumn;
import Model.card.spell.TargetEnemyMinion;
import Model.card.spell.TargetOwnCard;
import Model.card.spell.TargetOwnHeroRow;
import Model.card.spell.TargetOwnMinion;
import Model.card.spell.TargetThreeByThree;
import Model.card.spell.TargetTwoByTwo;
import Model.item.Collectable;
import Model.item.Item;
import Model.item.Usable;
import com.google.gson.*;

import java.io.FileWriter;
import java.util.ArrayList;

public class PreProcess{

    public static void preProcess() throws java.io.IOException{
        Gson gson = new Gson();

        //Spell

        ArrayList<Spell> spells = new ArrayList<>();
        spells.add(new Spell("Total Disarm", 1000, 0, TargetEnemyCard.getTargetInstance(), ));
        spells.add(new Spell("Area Dispel", 1500, 2, TargetTwoByTwo.getTargetInstance(), ));
        spells.add(new Spell("Empower", 250, 1, TargetOwnCard.getTargetInstance(), ));
        spells.add(new Spell("Fireball", 400, 1, TargetEnemyCard.getTargetInstance(), ));
        spells.add(new Spell("God Strength", 450, 2, new TargetOwnCard(), ));
        spells.add(new Spell("Hell Fire", 600, 3, new TargetTwoByTwo(),));
        spells.add(new Spell("Lightning Bolt", 1250, 2, new TargetEnemyHero(),));
        spells.add(new Spell("Poison Lake", 900, 5, new TargetThreeByThree(),));
        spells.add(new Spell("Madness", 650, 0, new TargetOwnCard(), ));
        spells.add(new Spell("All Disarm", 2000, 9, new TargetAllEnemyCards(),));
        spells.add(new Spell("All Poison", 1500, 8, new TargetAllEnemyCards(),));
        spells.add(new Spell("Dispel", 2100, 0, (Target) new TargetAllCards(),));//
        spells.add(new Spell("Health With Profit", 2250, 0, new TargetOwnCard(),));
        spells.add(new Spell("Power Up", 2500, 2, new TargetOwnCard(),));
        spells.add(new Spell("All Power", 2000, 4, new TargetAllOwnCards(),));
        spells.add(new Spell("All Attack", 1500, 4, ));//
        spells.add(new Spell("Weakening", 1000, 1, new TargetEnemyMinion(),));
        spells.add(new Spell("Sacrifice", 1600, 2, new TargetOwnMinion(),));
        spells.add(new Spell("Kings Guard", 1750, 9, ));//
        spells.add(new Spell("Shock", 1200, 1, new TargetEnemyCard(),));

        for (Spell spell:
             spells) {
            gson.toJson(spell, new FileWriter("Card.Json", true));
        }

        //Minion

        ArrayList<Minion> minions = new ArrayList<>();
        minions.add(new Minion("Persian Archer",300, 2, 6,
                4, new Range(), 7, null, null));
        minions.add(new Minion("Persian Swordsman",400, 2, 6,
                4, new Melee(), 0, new ActionStun(), /*on attack*/));
        minions.add(new Minion("Persian Lancer",500, 1, 5,
                3, new Hybrid(), 3, null, null));
        minions.add(new Minion("Persian Horseman",200, 4, 10,
                6, new Melee(), 0, null, null));
        minions.add(new Minion("Persian Warrior",600, 9, 24,
                6, new Melee(), 0, new ActionChangeAP(), /*On Attack*/));//more info un SP

        minions.add(new Minion("Persian General",800, 7, 12,
                4, new Melee(), 0, /*Combo*/, /*Combo*/));
        minions.add(new Minion("Turanian Archer",500, 1, 3,
                4, new Range(), 5, null, null));
        minions.add(new Minion("Turanian Slinger",600, 1, 4,
                2, new Range(), 7, null, null));
        minions.add(new Minion("Turanian Lancer",600, 1, 4,
                4, new Hybrid(), 3, null, null));
        minions.add(new Minion("Turanian SPY",700, 4, 6,
                6, new Melee(), 0, new ActionStun(), new ActionDisarm(), new BuffActionPoison(), /*On Attack*/));

        minions.add(new Minion("Turanian MaceBearer",450, 2, 3,
                10, new Melee(), 0, null, null));
        minions.add(new Minion("Turanian Prince",800, 6, 6,
                10, new Melee(), 0, /*Combo*/, /*combo*/));
        minions.add(new Minion("Black Demon",300, 9, 14,
                10, new Hybrid(), 7, null, null));
        minions.add(new Minion("Stone Thrower Giant",300, 9, 12,
                12, new Range(), 7, null, null));
        minions.add(new Minion("Eagle",200, 2, 0,
                2, new Range(), 3, /*Power Buff*/, /*Passive*/));

        minions.add(new Minion("Hog Rider Demon",300, 6, 16,
                8, new Melee(), 0, /*IDK*/, /*ON Death*/));
        minions.add(new Minion("One Eye Giant",500, 7, 12,
                11, new Hybrid(), 3, new BuffActionPoison(), /*On Attack*/));
        minions.add(new Minion("Venomous Snake",300, 4, 5,
                6, new Range(), 4, null, null));
        minions.add(new Minion("Fire Dragon",250, 5, 9,
                5, new Range(), 4, null, null));
        minions.add(new Minion("Fierce Lion",600, 2, 1,
                8, new Melee(), 0, /*NOT e Holy Buff*/, /*On Spawn*/));

        minions.add(new Minion("Giant Snake",500, 8, 14,
                7, new Range(), 5, /*Akse HolyBuff*/, /*On Spawn*/));
        minions.add(new Minion("White Wolf",400, 5, 8,
                2, new Melee(), 0, /*Ye BuffEe*/, /*On Attack*/));
        minions.add(new Minion("Leopard",400, 4, 6,
                2, new Melee(), 0, new ActionChangeHP(), /*On Attack*/));
        minions.add(new Minion("Wolf",400, 3, 6,
                1, new Melee(), 0, ActionChangeHP, /*On Attack*/));
        minions.add(new Minion("The Wizard",550, 4, 5,
                4, new Range(), 3, /*Power + Weakness*/, /*Passive*/));

        minions.add(new Minion("The Great Wizard",550, 6, 6,
                6, new Range(), 5, /**/, /*Passive*/));
        minions.add(new Minion("Genie",500, 5, 10,
                4, new Range(), 4, , /*On Turn*/));
        minions.add(new Minion("Wild Goraz",500, 6, 10,
                14, new Melee(), 0, /*NO Disarm*/, /*On Defend*/));
        minions.add(new Minion("Piran",400, 8, 20,
                12, new Melee(), 0, /*No Poisoned*/, /*On Defend*/));
        minions.add(new Minion("Giv",450, 4, 5,
                7, new Range(), 5, /*No Manfi*/, /*On Defend*/));

        minions.add(new Minion("Bahman",450, 8, 16,
                9, new Melee(), 0, , /*On Spawn*/));
        minions.add(new Minion("Ashkbus",400, 7, 14,
                8, new Melee(), 0, , /*On Defend*/));
        minions.add(new Minion("Iraj",500, 4, 6,
                20, new Range(), 3, null, null));
        minions.add(new Minion("Great Giant",600, 9, 30,
                8, new Hybrid(), 2, null, null));
        minions.add(new Minion("Two Headed Giant",550, 4, 10,
                4, new Melee(), 0, , /*On Attack*/));

        minions.add(new Minion("Snow Queen",500, 3, 3,
                4, new Range(), 5, , /*On Spawn*/));
        minions.add(new Minion("Foolad Zereh",650, 3, 1,
                1, new Melee(), 0, , /*Passive*/));
        minions.add(new Minion("Siavash",350, 4, 8,
                5, new Melee(), 0, , /*On Death*/));
        minions.add(new Minion("Eurymedon",600, 5, 10,
                4, new Melee(), 0, /*Combo*/, /*Combo*/));
        minions.add(new Minion("Arzhang Div",600, 3, 6,
                6, new Melee(), 0, /*Combo*/, /*Combo*/));

        for (Minion minion:
                    minions){
            gson.toJson(minion, new FileWriter("Card.Json", true));
        }

        //Hero

        ArrayList<Hero> heroes = new ArrayList<>();
        heroes.add(new Hero("White Demon", 8000, 50, 4, new Melee(), 0,
                /*PoweBuff*/, 1, 2));
        heroes.add(new Hero("Simorgh", 9000, 50, 4, new Melee(), 0,
                new ActionStun(), 5, 8));
        heroes.add(new Hero("Seven Headed Dragon", 50, 4, 0, new Melee(), 0,
                new ActionDisarm(), 0, 1));
        heroes.add(new Hero("Rakhsh", 8000, 50, 4, new Melee(), 0,
                new ActionStun(), 1, 2));
        heroes.add(new Hero("Zahak", 10000, 50, 2, new Melee(), 0,
                new BuffActionPoison(), 0, 0));
        heroes.add(new Hero("Kaveh", 8000, 50, 4, new Melee(), 0,
                new BuffActionHolly(), 1, 3));
        heroes.add(new Hero("Arash", 10000, 30, 2, new Range(), 6,
                /*Zarbe 4 vahed*/, 2, 2));
        heroes.add(new Hero("Afsane", 11000, 40, 3, new Range(), 3,
                new ActionDispel(), 1, 2));
        heroes.add(new Hero("EsfanDar", 12000, 35, 3, new Hybrid(), 3,
                new BuffActionHolly(), 0, 0));
        heroes.add(new Hero("Rostam", 8000, 55, 7,new Hybrid(), 4,
                null, 0, 0));

        for (Hero hero:
             heroes) {
            gson.toJson(hero, new FileWriter("Card.Json", true));
        }


        //item

        ArrayList<Usable> usables = new ArrayList<>();
        usables.add(new Usable("Wisdom Crown", 300, ));
        usables.add(new Usable("Shield AF", 4000,));
        usables.add(new Usable("Damool's Arc", 30000, ));
        usables.add(new Usable("Simorgh's feather", 3500,));
        usables.add(new Usable("Terror Hood", 5000,));
        usables.add(new Usable("King Wisdom", 9000,));
        usables.add(new Usable("Assassination Dagger", 15000,));
        usables.add(new Usable("Poisonous Dagger", 7000,));
        usables.add(new Usable("Shock Hammer", 15000, ));
        usables.add(new Usable("Soul Eater", 25000, ));
        usables.add(new Usable("‌Baptism", 20000, ));

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