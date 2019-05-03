package Model.card;

import Model.Map.Cell;
import Model.card.hermione.Minion;
import Model.card.hermione.Range;
import Model.card.spell.Action.*;
import Model.card.spell.SpecialPower;
import Model.card.spell.Spell;
import com.google.gson.*;

import java.io.FileWriter;
import java.util.ArrayList;

public class PreProcess{

    public static void preProcess() throws java.io.IOException{
        Gson gson = new Gson();

        //Spell

        ArrayList<Spell> spells = new ArrayList<>();
        spells.add(new Spell("Total Disarm", 1000, 0));
        spells.add(new Spell("Area Dispel", 1500, 2));
        spells.add(new Spell("Empower", 250, 1));
        spells.add(new Spell("Fireball", 400, 1));
        spells.add(new Spell("God Strength", 450, 2));
        spells.add(new Spell("Hell Fire", 600, 3));
        spells.add(new Spell("Lightning Bolt", 1250, 2));
        spells.add(new Spell("Poison Lake", 900, 5));
        spells.add(new Spell("Madness", 650, 0));
        spells.add(new Spell("All Disarm", 2000, 9));
        spells.add(new Spell("All Poison", 1500, 8));
        spells.add(new Spell("Dispel", 2100, 0));
        spells.add(new Spell("Health With Profit", 2250, 0));
        spells.add(new Spell("Power Up", 2500, 2));
        spells.add(new Spell("All Power", 2000, 4));
        spells.add(new Spell("All Attack", 1500, 4));
        spells.add(new Spell("Weakening", 1000, 1));
        spells.add(new Spell("Sacrifice", 1600, 2));
        spells.add(new Spell("Kings Guard", 1750, 9));
        spells.add(new Spell("Shock", 1200, 1));

        for (Spell spell:
             spells) {
            gson.toJson(spell, new FileWriter("Card.Json", true));
        }

        //Minion
        ArrayList<Minion> minions = new ArrayList<>();
        minions.add(new Minion("Persian Archer",300, 2, 6, 4, new Range(), 7, null, null));
        

    }



}