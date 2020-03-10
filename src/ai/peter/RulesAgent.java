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

    String rulesName = "SimpleAI.txt";
    File rulesFile;
    BufferedReader bufferedReader;
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

    public AI clone() {
        return new RulesAgent(utt, pf);
    }

    @Override
    public List<ParameterSpecification> getParameters()
    {
        List<ParameterSpecification> parameters = new ArrayList<>();
        
        parameters.add(new ParameterSpecification("PathFinding", PathFinding.class, new AStarPathFinding()));

        return parameters;
    }
    
    public void reset(UnitTypeTable a_utt)  
    {
        // LIGHTRUSH
        utt = a_utt;
        workerType      = utt.getUnitType("Worker");
        baseType        = utt.getUnitType("Base");
        barracksType    = utt.getUnitType("Barracks");
        lightType       = utt.getUnitType("Light");

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









    // ******************
    // LIGHT RUSH ACTION
    // ******************

    public PlayerAction getAction(int player, GameState gs) {

        PhysicalGameState pgs = gs.getPhysicalGameState();
        Player p = gs.getPlayer(player);
        Resources r = new Resources();

        for (Unit u : gs.getUnits())
        {
            if(u.getPlayer() == player)
            {
                DoTrainWorker(player,u,p,gs,pgs,r);
    
                DoBuildBarracks(player,u,p,gs,pgs,r);
            
                DoHarvest(player,u,p,gs,pgs,r);
            
                DoTrainLight(player,u,p,gs,pgs,r);
            
                DoAttack(player,u,p,gs,pgs,r);
            }
        }

        return translateActions(player, gs);
    }






    // *************************
    // PETER HAVES
    // *************************

    protected boolean HaveWorker(int player, Unit u, Player p, GameState gs, PhysicalGameState pgs, Resources r)
    {
        for (Unit u2 : pgs.getUnits()) {
            if (u2.getType() == workerType
                    && u2.getPlayer() == p.getID()) {
                return true;
            }
        }
        return false;
    }

    protected Unit ClosestEnemy(int player,Unit u, Player p, GameState gs, PhysicalGameState pgs, Resources r)
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

    protected boolean HaveBase(int player,Unit u, Player p, GameState gs, PhysicalGameState pgs, Resources r)
    {
        for (Unit u2 : pgs.getUnits()) {
            if (u2.getType() == baseType
                    && u2.getPlayer() == p.getID()) {
                return true;
            }
        }
        return false;
    }    
    
    
    protected boolean HaveBarracks(int player,Unit u, Player p, GameState gs, PhysicalGameState pgs, Resources r)
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

    protected boolean AffordWorker(int player, Unit u, Player p, GameState gs, PhysicalGameState pgs, Resources r)
    {
        if (p.getResources() >= workerType.cost) {
            return true;
        }
        else {
            return false;
        }
    }

    protected boolean AffordLight(int player,Unit u, Player p, GameState gs, PhysicalGameState pgs, Resources r)
    {
        if (p.getResources() >= lightType.cost) {
            return true;
        }
        else {
            return false;
        }
    }

    protected boolean AffordBase(int player,Unit u, Player p, GameState gs, PhysicalGameState pgs, Resources r)
    {
        if (p.getResources() >= baseType.cost + r.used) {
            return true;
        }
        else {
            return false;
        }
    }

    protected boolean AffordBarracks(int player,Unit u, Player p, GameState gs, PhysicalGameState pgs, Resources r)
    {
        if (p.getResources() >= barracksType.cost + r.used) {
            return true;
        }
        else {
            return false;
        }
    }





    // *************************
    // PETER ACTIONS
    // *************************

    protected void TrainWorker(int player, Unit u, Player p, GameState gs, PhysicalGameState pgs, Resources r)
    {
        train(u, workerType);
        r.busy.add(u);
    }

    protected void TrainLight(int player,Unit u, Player p, GameState gs, PhysicalGameState pgs, Resources r)
    {
        train(u, lightType);
        r.busy.add(u);
    }

    protected void Attack(int player,Unit u, Player p, GameState gs, PhysicalGameState pgs, Unit closest, Resources r)
    {
        if (closest != null) {
            attack(u, closest);
            r.busy.add(u);
        }
    }

    protected void Harvest(int player,Unit u, Player p, GameState gs, PhysicalGameState pgs, Resources r)
    {
        Unit closestBase = null;
        Unit closestResource = null;
        int closestDistance = 0;
        
        // Find nearest Resources
        for (Unit u2 : pgs.getUnits()) {
            if (u2.getType().isResource) {
                int d = Math.abs(u2.getX() - u.getX()) + Math.abs(u2.getY() - u.getY());
                if (closestResource == null || d < closestDistance) {
                    closestResource = u2;
                    closestDistance = d;
                }
            }
        }

        // Reset Distance
        closestDistance = 0;

        // Find nearest Base
        for (Unit u2 : pgs.getUnits()) {
            if (u2.getType().isStockpile && u2.getPlayer()==p.getID()) {
                int d = Math.abs(u2.getX() - u.getX()) + Math.abs(u2.getY() - u.getY());
                if (closestBase == null || d < closestDistance) {
                    closestBase = u2;
                    closestDistance = d;
                }
            }
        }

        // Harvest
        if (closestResource != null && closestBase != null) {
            AbstractAction aa = getAbstractAction(u);
            if (aa instanceof Harvest) {
                Harvest h_aa = (Harvest)aa;
                if (h_aa.getTarget() != closestResource || h_aa.getBase()!=closestBase) harvest(u, closestResource, closestBase);
            } else {
                harvest(u, closestResource, closestBase);
                r.busy.add(u);
            }
        }
    }

    protected void BuildBase(int player,Unit u, Player p, GameState gs, PhysicalGameState pgs, Resources r)
    {
        buildIfNotAlreadyBuilding(u,baseType,u.getX(),u.getY(),r.reserved,p,pgs);
        r.busy.add(u);
    }

    protected void BuildBarracks(int player,Unit u, Player p, GameState gs, PhysicalGameState pgs, Resources r)
    {
        buildIfNotAlreadyBuilding(u,barracksType,u.getX(),u.getY(),r.reserved,p,pgs);
        r.busy.add(u);
    }






    // *************************
    // PETER STATICS
    // *************************

    protected void DoTrainWorker(int player,Unit u, Player p, GameState gs, PhysicalGameState pgs, Resources r)
    {
        if (Base(player,u,p,gs,pgs,r) && Idle(player,u,p,gs,pgs,r) && Not(HaveWorker(player,u,p,gs,pgs,r)) && AffordWorker(player,u,p,gs,pgs,r))
        {
            TrainWorker(player, u, p, gs, pgs,r);
        }
    }

    protected void DoBuildBase(int player,Unit u, Player p, GameState gs, PhysicalGameState pgs, Resources r)
    {
        if (Worker(player,u,p,gs,pgs,r) && Idle(player,u,p,gs,pgs,r) && Not(HaveBase(player,u,p,gs,pgs,r)) && AffordBase(player,u,p,gs,pgs,r))
        {
            BuildBase(player, u, p, gs, pgs, r);
        }
    }

    protected void DoBuildBarracks(int player,Unit u, Player p, GameState gs, PhysicalGameState pgs, Resources r)
    {
        if (Worker(player,u,p,gs,pgs,r) && Not(HaveBarracks(player,u,p,gs,pgs,r)) && HaveBase(player,u,p,gs,pgs,r) && AffordBarracks(player,u,p,gs,pgs,r))
        {
            BuildBarracks(player, u, p, gs, pgs,r);
        }
    }

    protected void DoHarvest(int player,Unit u, Player p, GameState gs, PhysicalGameState pgs, Resources r)
    {
        if (Worker(player,u,p,gs,pgs,r) && Idle(player,u,p,gs,pgs,r))
        {
            Harvest(player,u,p,gs,pgs,r);
        }
    }

    protected void DoTrainLight(int player,Unit u, Player p, GameState gs, PhysicalGameState pgs, Resources r)
    {
        if (Barracks(player,u,p,gs,pgs,r) && Idle(player,u,p,gs,pgs,r) && AffordLight(player,u,p,gs,pgs,r))
        {
            TrainLight(player, u, p, gs, pgs,r);
        }
    }

    protected void DoAttack(int player,Unit u, Player p, GameState gs, PhysicalGameState pgs, Resources r)
    {
        if (Light(player,u,p,gs,pgs,r) && Idle(player,u,p,gs,pgs,r))
        {
            Attack(0,u,p,gs,pgs,ClosestEnemy(0,u,p,gs,pgs,r),r);
        }
    }








    // **********************
    // PETER NEW
    // **********************
    
    protected boolean Idle(int player,Unit u, Player p, GameState gs, PhysicalGameState pgs, Resources r)
    {
        if (!r.busy.contains(u)) {
            return true;
        }
        else {
            return false;
        }
    }

    protected boolean Base(int player,Unit u, Player p, GameState gs, PhysicalGameState pgs, Resources r)
    {
        if (u.getType() == baseType)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    protected boolean Worker(int player,Unit u, Player p, GameState gs, PhysicalGameState pgs, Resources r)
    {
        if (u.getType().canHarvest)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    protected boolean Barracks(int player,Unit u, Player p, GameState gs, PhysicalGameState pgs, Resources r)
    {
        if (u.getType() == barracksType)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    protected boolean Light(int player,Unit u, Player p, GameState gs, PhysicalGameState pgs, Resources r)
    {
        if (u.getType().canAttack && !u.getType().canHarvest)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    protected boolean Afford(int player,Unit u, Player p, GameState gs, PhysicalGameState pgs, Resources r)
    {
        if (p.getResources() >= u.getType().cost)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private static class Resources
    {
        int used = 0;
        List<Unit> free = new LinkedList<>();
        List<Integer> reserved = new LinkedList<>();
        List<Unit> busy = new LinkedList<>();
    }

    protected boolean Not(boolean truth)
    {
        return !truth;
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
                    // is
                    else if (inputArray[i].equals("is"))
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
