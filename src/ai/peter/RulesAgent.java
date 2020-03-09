/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.peter;

// ****************
// PETER VARIABLES
// ****************

import ai.abstraction.AbstractAction;
import ai.abstraction.AbstractionLayerAI;
import ai.abstraction.Harvest;
import ai.abstraction.pathfinding.AStarPathFinding;
import ai.abstraction.pathfinding.PathFinding;

// ***************
// LIGHTRUSH CORE
// ***************

import ai.core.AI;
import ai.core.ParameterSpecification;

// **************
// PETER FILE IO
// **************

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

// *********************
// LIGHTRUSH STRUCTURES
// *********************

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

// *********************
// LIGHTRUSH STRUCTURES
// *********************

import rts.GameState;
import rts.PhysicalGameState;
import rts.Player;
import rts.PlayerAction;
import rts.units.*;

/**
 * Simple Quick Fix Textfile based simple rulesbased agent
 * Nothing fancy, just stupid ASAP work
 * See `src/peter/` for more interesting things
 * See `TryParse.md` for attempts at parsing
 * @author peter
 * https://stackoverflow.com/questions/7470861/return-multiple-values-from-a-java-method-why-no-n-tuple-objects
 */
public class RulesAgent extends AbstractionLayerAI {




    // ****************
    // PETER VARIABLES
    // ****************
    String rulesName = "SimpleAI.txt";
    File rulesFile;
    BufferedReader bufferedReader;
    Unit defaultUnit;




    // *********************
    // LIGHT RUSH VARIABLES
    // *********************
    Random r = new Random();
    protected UnitTypeTable utt;
    UnitType workerType;
    UnitType baseType;
    UnitType barracksType;
    UnitType lightType;




    // ********************************
    // LIGHT RUSH CONSTRUCTORS/HELPERS
    // ********************************

    public RulesAgent(UnitTypeTable a_utt) {
        this(a_utt, new AStarPathFinding());
    }
    
    public RulesAgent(UnitTypeTable a_utt, PathFinding a_pf) {
        super(a_pf);
        reset(a_utt);
    }

    public void reset() {
        super.reset();
    }
    
    public void reset(UnitTypeTable a_utt)  
    {
        // LIGHTRUSH
        utt = a_utt;
        workerType = utt.getUnitType("Worker");
        baseType = utt.getUnitType("Base");
        barracksType = utt.getUnitType("Barracks");
        lightType = utt.getUnitType("Light");

        // PETER
        this.rulesFile = new File(rulesName);
        try {
            this.bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(rulesFile)));
        } catch (FileNotFoundException e) {
            System.out.println("Peter, you done screwed up again!");
            e.printStackTrace();
        }
        this.ReadFile();
    }   
    
    public AI clone() {
        return new RulesAgent(utt, pf);
    }









    // ******************
    // LIGHT RUSH ACTION
    // ******************

    public PlayerAction getAction(int player, GameState gs) {

        // VARIABLES
        PhysicalGameState pgs = gs.getPhysicalGameState();
        Player p = gs.getPlayer(player);
        defaultUnit = new Unit(player, workerType, 0, 0);

        // behavior of bases:
        IdleBase(player,defaultUnit, p, gs, pgs);

        // behavior of barracks:
        IdleBarracks(player,defaultUnit,p,gs,pgs);

        // behavior of melee units:
        IdleMelee(player,defaultUnit,p,gs,pgs);

        // behavior of workers:
        IdleWorkers(player,defaultUnit,p,gs,pgs);

        // FINAL
        return translateActions(player, gs);
    }








    // *************************
    // PETER IDLES
    // *************************

    protected void IdleBase(int player, Unit u, Player p, GameState gs, PhysicalGameState pgs)
    {
        for (Unit U : pgs.getUnits()) {
            if (U.getType() == baseType
                    && u.getPlayer() == player
                    && gs.getActionAssignment(U) == null) {
                baseBehavior(U, p, gs, pgs);
            }
        }
    }

    protected void IdleBarracks(int player, Unit u, Player p, GameState gs, PhysicalGameState pgs)
    {
        for (Unit U : pgs.getUnits()) {
            if (U.getType() == barracksType
                    && U.getPlayer() == player
                    && gs.getActionAssignment(U) == null) {
                barracksBehavior(U, p, gs, pgs);
            }
        }
    }

    protected void IdleMelee(int player, Unit u, Player p, GameState gs, PhysicalGameState pgs)
    {
        for (Unit U : pgs.getUnits()) {
            if (U.getType().canAttack && !U.getType().canHarvest
                    && U.getPlayer() == player
                    && gs.getActionAssignment(U) == null) {
                meleeUnitBehavior(U, p, gs, pgs);
            }
        }
    }

    protected void IdleWorkers(int player, Unit u, Player p, GameState gs, PhysicalGameState pgs)
    {
        List<Unit> workers = new LinkedList<>();
        for (Unit U : pgs.getUnits()) {
            if (U.getType().canHarvest
                    && U.getPlayer() == player) {
                workers.add(U);
            }
        }
        workersBehavior(workers, p, gs, pgs);
    }







    // *************************
    // PETER HAVES
    // *************************

    protected boolean HaveWorker(int player, Unit u, Player p, GameState gs, PhysicalGameState pgs)
    {
        for (Unit u2 : pgs.getUnits()) {
            if (u2.getType() == workerType
                    && u2.getPlayer() == p.getID()) {
                return true;
            }
        }
        return false;
    }

    protected Unit ClosestEnemy(int player,Unit u, Player p, GameState gs, PhysicalGameState pgs)
    {
        Unit closestEnemy = null;
        int closestDistance = 0;
        for (Unit u2 : pgs.getUnits()) {
            if (u2.getPlayer() >= 0 && u2.getPlayer() != p.getID()) {
                int d = Math.abs(u2.getX() - u.getX()) + Math.abs(u2.getY() - u.getY());
                if (closestEnemy == null || d < closestDistance) {
                    closestEnemy = u2;
                    closestDistance = d;
                }
            }
        }
        return closestEnemy;
    }

    protected boolean HaveBase(int player,Unit u, Player p, GameState gs, PhysicalGameState pgs)
    {
        for (Unit u2 : pgs.getUnits()) {
            if (u2.getType() == baseType
                    && u2.getPlayer() == p.getID()) {
                return true;
            }
        }
        return false;
    }    
    
    
    protected boolean HaveBarracks(int player,Unit u, Player p, GameState gs, PhysicalGameState pgs)
    {
        for (Unit u2 : pgs.getUnits()) {
            if (u2.getType() == barracksType
                    && u2.getPlayer() == p.getID()) {
                return true;
            }
        }
        return false;
    }





    // *************************
    // PETER AFFORDS
    // *************************

    protected boolean AffordWorker(int player, Unit u, Player p, GameState gs, PhysicalGameState pgs)
    {
        if (p.getResources() >= workerType.cost) {
            return true;
        }
        else {
            return false;
        }
    }

    protected boolean AffordLight(int player,Unit u, Player p, GameState gs, PhysicalGameState pgs)
    {
        if (p.getResources() >= lightType.cost) {
            return true;
        }
        else {
            return false;
        }
    }

    protected boolean AffordBase(int player,Unit u, Player p, GameState gs, PhysicalGameState pgs, int resourcesUsed)
    {
        if (p.getResources() >= baseType.cost + resourcesUsed) {
            return true;
        }
        else {
            return false;
        }
    }

    protected boolean AffordBarracks(int player,Unit u, Player p, GameState gs, PhysicalGameState pgs, int resourcesUsed)
    {
        if (p.getResources() >= barracksType.cost + resourcesUsed) {
            return true;
        }
        else {
            return false;
        }
    }





    // *************************
    // PETER ACTIONS
    // *************************

    protected void TrainWorker(int player, Unit u, Player p, GameState gs, PhysicalGameState pgs)
    {
        train(u, workerType);
    }

    protected void TrainLight(int player,Unit u, Player p, GameState gs, PhysicalGameState pgs)
    {
        train(u, lightType);
    }

    protected void Attack(int player,Unit u, Player p, GameState gs, PhysicalGameState pgs, Unit closest)
    {
        if (closest != null) {
            attack(u, closest);
        }
    }

    protected Resources Harvest(int player,Unit U, Player p, GameState gs, PhysicalGameState pgs, Resources resources)
    {
        for (Unit u : resources.free) {
            Unit closestBase = null;
            Unit closestResource = null;
            int closestDistance = 0;
            for (Unit u2 : pgs.getUnits()) {
                if (u2.getType().isResource) {
                    int d = Math.abs(u2.getX() - u.getX()) + Math.abs(u2.getY() - u.getY());
                    if (closestResource == null || d < closestDistance) {
                        closestResource = u2;
                        closestDistance = d;
                    }
                }
            }
            closestDistance = 0;
            for (Unit u2 : pgs.getUnits()) {
                if (u2.getType().isStockpile && u2.getPlayer()==p.getID()) {
                    int d = Math.abs(u2.getX() - u.getX()) + Math.abs(u2.getY() - u.getY());
                    if (closestBase == null || d < closestDistance) {
                        closestBase = u2;
                        closestDistance = d;
                    }
                }
            }
            if (closestResource != null && closestBase != null) {
                AbstractAction aa = getAbstractAction(u);
                if (aa instanceof Harvest) {
                    Harvest h_aa = (Harvest)aa;
                    if (h_aa.getTarget() != closestResource || h_aa.getBase()!=closestBase) harvest(u, closestResource, closestBase);
                } else {
                    harvest(u, closestResource, closestBase);
                }
            }
        }
        return resources;
    }

    protected Resources BuildBase(int player,Unit u, Player p, GameState gs, PhysicalGameState pgs, Resources resources)
    {
        Unit U = resources.free.remove(0);
        buildIfNotAlreadyBuilding(U,baseType,u.getX(),u.getY(),resources.reserved,p,pgs);
        resources.used += baseType.cost;
        return resources;
    }

    protected Resources BuildBarracks(int player,Unit u, Player p, GameState gs, PhysicalGameState pgs, Resources resources)
    {
        Unit U = resources.free.remove(0);
        buildIfNotAlreadyBuilding(U,barracksType,U.getX(),U.getY(),resources.reserved,p,pgs);
        resources.used += barracksType.cost;
        return resources;
    }





    // *************************
    // PETER ADDENDUM
    // *************************

    private static class Resources
    {
        int used = 0;
        List<Unit> free = new LinkedList<>();
        List<Integer> reserved = new LinkedList<>();
    }

    protected boolean Negation(boolean truth)
    {
        return !truth;
    }







    // *************************
    // LIGHT RUSH BEHAVIORS
    // *************************

    public void baseBehavior(Unit u, Player p, GameState gs, PhysicalGameState pgs) {
        if (!HaveWorker(0,u,p,gs,pgs) && AffordWorker(0,u,p,gs,pgs)) {
            TrainWorker(0,u,p,gs,pgs);
        }
    }

    public void barracksBehavior(Unit u, Player p,  GameState gs, PhysicalGameState pgs) {
        if (AffordLight(0,u,p,gs,pgs)) {
            TrainLight(0,u,p,gs,pgs);
        }
    }

    public void meleeUnitBehavior(Unit u, Player p, GameState gs, PhysicalGameState pgs) {
        Attack(0,u,p,gs,pgs,ClosestEnemy(0,u,p,gs,pgs));
    }
    
    public void workersBehavior(List<Unit> workers, Player p,  GameState gs, PhysicalGameState pgs) {
        if (HaveWorker(0,defaultUnit,p,gs,pgs)) {
            Resources resources = new Resources();
            resources.used = 0;
            resources.free = new LinkedList<>(workers);
            resources.reserved = new LinkedList<>();
            if (!HaveBase(0,defaultUnit,p,gs,pgs)    &&     !resources.free.isEmpty()) {
                if (AffordBase(0,defaultUnit, p, gs, pgs, resources.used)) {
                    resources = BuildBase(0, defaultUnit, p, gs, pgs, resources);
                }
            }
            if (!HaveBarracks(0,defaultUnit,p,gs,pgs)) {
                if (AffordBarracks(0,defaultUnit, p, gs, pgs, resources.used)   &&   !resources.free.isEmpty()) {
                    resources = BuildBarracks(0, defaultUnit, p, gs, pgs, resources);
                }
            }
            resources = Harvest(0, defaultUnit, p, gs, pgs, resources);
        }
    }






    // **********************
    // LIGHT RUSH PARAMETERS
    // **********************
    
    @Override
    public List<ParameterSpecification> getParameters()
    {
        List<ParameterSpecification> parameters = new ArrayList<>();
        
        parameters.add(new ParameterSpecification("PathFinding", PathFinding.class, new AStarPathFinding()));

        return parameters;
    }    




    // *************************
    // PETER FILE READING
    // *************************

    // https://stackoverflow.com/questions/3413586/string-to-string-array-conversion-in-java
    // https://stackoverflow.com/questions/7590838/how-to-find-eof-in-a-string-in-java
    // https://stackoverflow.com/questions/2564298/java-how-to-initialize-string
    private void ReadFile()
    {
        // Input
        String inputString = "";
        String[] inputArray;
        int count = 0;

        // Parsing
        String[] symbols = new String[100];
        String currentSymbol = "";

        // Looping
        boolean done = false;

        // LOOP
        while (!done)
        {
            // READ
            try {
                inputString = UnsafeInput();
            }
            catch(IOException error) {
                System.out.println("PETER READ ERROR");
                error.printStackTrace();
            }

            // SPLIT
            inputArray = inputString.split(" ");

            // PARSE
            // End of File
            if (inputString.isEmpty())
            {
                done = true;
            }
            // Comment
            else if (inputArray[0].equals("#"))
            {
                continue;
            }
            // Blank Line
            else if(inputArray[0].equals("\n"))
            {
                continue;
            }
            // Assignment
            else if (inputArray[1].equals(":"))
            {
                // Check if new
                for (String symbol : symbols)
                {
                    // Seen
                    if (symbol.equals(inputArray[0]))
                    {
                        currentSymbol = inputArray[0];
                    }
                    // Unseen
                    else
                    {
                        symbols[count + 1] = inputArray[0];
                        count += 1;
                        currentSymbol = inputArray[0];
                    }
                }
                // Check for each condition (~, idle, have, afford)
                for (int i = 2; i < 100; i++)
                {
                    // Empty
                    if (inputArray[i].isEmpty())
                    {
                        break;
                    }
                    // ~
                    else if (inputArray[i].equals("~"))
                    {

                    }
                    // idle (ignored, as is implicit)
                    else if (inputArray[i].equals("idle"))
                    {
                        
                    }
                    // have
                    else if (inputArray[i].equals("have"))
                    {
                        
                    }
                    // afford
                    if (inputArray[i].equals("afford"))
                    {
                        
                    }
                }
            }
            // Unrecognized
            else
            {
                System.out.println("Warning! Unrecognized Input! Invalid Config File! Danger! Warning! Malfunction!\n_FAILS TO UNDERSTAND_");
            }
        }
    }
    
    public String UnsafeInput() throws IOException {
		return bufferedReader.readLine();
	}
}
