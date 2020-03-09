
package peter.Agents;

// File Imports
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import ai.abstraction.AbstractAction;
// RTS Imports
import ai.abstraction.AbstractionLayerAI;
import ai.abstraction.Harvest;
import ai.abstraction.LightRush;
import ai.abstraction.pathfinding.AStarPathFinding;
import ai.core.AI;
import ai.abstraction.pathfinding.PathFinding;
import ai.core.ParameterSpecification;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import rts.GameState;
import rts.PhysicalGameState;
import rts.Player;
import rts.PlayerAction;
import rts.units.*;
import java.io.IOException;					// Handle any input errors
import java.io.BufferedReader;				// For Getting User Commands
import java.io.FileInputStream;				// Read a File
import java.io.FileNotFoundException;

public class RulesBasedAgent extends AbstractionLayerAI {
    // Rule Based System Variables
    private InferenceEngine inferenceEngine;
    // private RuleParser ruleParser;

    // RTS System Variables
    Random r = new Random();
    protected UnitTypeTable utt;
    UnitType workerType;
    UnitType baseType;
    UnitType barracksType;
    UnitType lightType;

    // File Variables
    String fileName = "config.properties";
    BufferedReader bufferedReader;
    File configFile;

    // 1st Call
    public RulesBasedAgent(UnitTypeTable a_utt) {
        this(a_utt, new AStarPathFinding());
    }

    // 2nd Call
    public RulesBasedAgent(UnitTypeTable a_utt, PathFinding a_pf) {
        super(a_pf);
        reset(a_utt);
    }

    // 3rd Call
    public void reset() {
        super.reset();

        this.inferenceEngine = new InferenceEngine();
        // this.ruleParser = new RuleParser();
        this.BuildParser();
    }

    // 3rd Call
    public void reset(UnitTypeTable a_utt) {
        configFile = new File(fileName);
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(configFile)));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        this.utt = a_utt;
        this.workerType = utt.getUnitType("Worker");
        this.baseType = utt.getUnitType("Base");
        this.barracksType = utt.getUnitType("Barracks");
        this.lightType = utt.getUnitType("Light");

        this.inferenceEngine = new InferenceEngine();
        //this.ruleParser = new RuleParser();
        this.BuildParser();
    }   

    // 4th Call
    private void BuildParser()
    {
        /*
        // Instantiate Words
        Afford      afford      = new Afford(ruleParser);
        Barracks    barracks    = new Barracks(ruleParser);
        Base        base        = new Base(ruleParser);
        Have        have        = new Have(ruleParser);
        Idle        idle        = new Idle(ruleParser);
        Light       light       = new Light(ruleParser);
        SetEqual    setEqual    = new SetEqual(ruleParser);
        Symbol      symbol      = new Symbol(ruleParser);
        Worker      worker      = new Worker(ruleParser);

        // Instantiate Lines
        Assign      assignLine  = new Assign(ruleParser);
        Import      importLine  = new Import(ruleParser);

        // Instantiate Chars
        Comma       comma       = new Comma(ruleParser);
        Equals      equals      = new Equals(ruleParser);
        Hash        hash        = new Hash(ruleParser);
        LeftParen   leftParen   = new LeftParen(ruleParser);
        NewLine     newLine     = new NewLine(ruleParser);
        Period      period      = new Period(ruleParser);
        Quote       quote       = new Quote(ruleParser);
        RightParen  rightParen  = new RightParen(ruleParser);
        Space       space       = new Space(ruleParser);
        Squiggle    squiggle    = new Squiggle(ruleParser);


        // Add Words
        this.ruleParser.AddWordRule(afford);
        this.ruleParser.AddWordRule(barracks);
        this.ruleParser.AddWordRule(base);
        this.ruleParser.AddWordRule(have);
        this.ruleParser.AddWordRule(idle);
        this.ruleParser.AddWordRule(light);
        this.ruleParser.AddWordRule(setEqual);
        this.ruleParser.AddWordRule(symbol);
        this.ruleParser.AddWordRule(worker);

        // Add Lines
        this.ruleParser.AddLineRule(assignLine);
        this.ruleParser.AddLineRule(importLine);

        // Add Chars
        this.ruleParser.AddCharRule(comma);
        this.ruleParser.AddCharRule(equals);
        this.ruleParser.AddCharRule(hash);
        this.ruleParser.AddCharRule(leftParen);
        this.ruleParser.AddCharRule(newLine);
        this.ruleParser.AddCharRule(period);
        this.ruleParser.AddCharRule(quote);
        this.ruleParser.AddCharRule(rightParen);
        this.ruleParser.AddCharRule(space);
        this.ruleParser.AddCharRule(squiggle);
        */
    }

    // 5th Call
    private void BuildInference() throws IOException 
    {
        // Load Inference
        //this.ruleParser.Parse(fileContents, this.inferenceEngine);
    }




    // **************************
    // HELPERS
    // **************************

    public AI clone() {
        return new RulesBasedAgent(utt, pf);
    }














    /**
     * This is the main function of the AI. It is called at each game cycle with the most up to date game state and
     * returns which actions the AI wants to execute in this cycle.
     * The input parameters are:
     *  - player: the player that the AI controls (0 or 1)
     *  - gs: the current game state
     * This method returns the actions to be sent to each of the units in the gamestate controlled by the player,
     * packaged as a PlayerAction.
     */
    public PlayerAction getAction(int player, GameState gs) {
        PhysicalGameState pgs = gs.getPhysicalGameState();
        Player p = gs.getPlayer(player);

        // behavior of bases:
        for (Unit u : pgs.getUnits()) {
            if (u.getType() == baseType
                    && u.getPlayer() == player
                    && gs.getActionAssignment(u) == null) {
                baseBehavior(u, p, pgs);
            }
        }

        // behavior of barracks:
        for (Unit u : pgs.getUnits()) {
            if (u.getType() == barracksType
                    && u.getPlayer() == player
                    && gs.getActionAssignment(u) == null) {
                barracksBehavior(u, p, pgs);
            }
        }

        // behavior of melee units:
        for (Unit u : pgs.getUnits()) {
            if (u.getType().canAttack && !u.getType().canHarvest
                    && u.getPlayer() == player
                    && gs.getActionAssignment(u) == null) {
                meleeUnitBehavior(u, p, gs);
            }
        }

        // behavior of workers:
        List<Unit> workers = new LinkedList<>();
        for (Unit u : pgs.getUnits()) {
            if (u.getType().canHarvest
                    && u.getPlayer() == player) {
                workers.add(u);
            }
        }
        workersBehavior(workers, p, pgs);

        // This method simply takes all the unit actions executed so far, and packages them into a PlayerAction
        return translateActions(player, gs);
    }



    /**
     * REPLACE ME REPLACE ME REPLACE ME REPLACE ME REPLACE ME
     */
    public void baseBehavior(Unit u, Player p, PhysicalGameState pgs) {
        int nworkers = 0;
        for (Unit u2 : pgs.getUnits()) {
            if (u2.getType() == workerType
                    && u2.getPlayer() == p.getID()) {
                nworkers++;
            }
        }
        if (nworkers < 1 && p.getResources() >= workerType.cost) {
            train(u, workerType);
        }
    }


    /**
     * REPLACE ME REPLACE ME REPLACE ME REPLACE ME REPLACE ME
     */
    public void barracksBehavior(Unit u, Player p, PhysicalGameState pgs) {
        if (p.getResources() >= lightType.cost) {
            train(u, lightType);
        }
    }






    /**
     * REPLACE ME REPLACE ME REPLACE ME REPLACE ME REPLACE ME
     */
    public void meleeUnitBehavior(Unit u, Player p, GameState gs) {
        PhysicalGameState pgs = gs.getPhysicalGameState();
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
        if (closestEnemy != null) {
//            System.out.println("LightRushAI.meleeUnitBehavior: " + u + " attacks " + closestEnemy);
            attack(u, closestEnemy);
        }
    }




    /**
     * REPLACE ME REPLACE ME REPLACE ME REPLACE ME REPLACE ME
     */
    public void workersBehavior(List<Unit> workers, Player p, PhysicalGameState pgs) {
        int nbases = 0;
        int nbarracks = 0;

        int resourcesUsed = 0;
        List<Unit> freeWorkers = new LinkedList<>(workers);

        if (workers.isEmpty()) {
            return;
        }

        for (Unit u2 : pgs.getUnits()) {
            if (u2.getType() == baseType
                    && u2.getPlayer() == p.getID()) {
                nbases++;
            }
            if (u2.getType() == barracksType
                    && u2.getPlayer() == p.getID()) {
                nbarracks++;
            }
        }

        List<Integer> reservedPositions = new LinkedList<>();
        if (nbases == 0 && !freeWorkers.isEmpty()) {
            // build a base:
            if (p.getResources() >= baseType.cost + resourcesUsed) {
                Unit u = freeWorkers.remove(0);
                buildIfNotAlreadyBuilding(u,baseType,u.getX(),u.getY(),reservedPositions,p,pgs);
                resourcesUsed += baseType.cost;
            }
        }

        if (nbarracks == 0) {
            // build a barracks:
            if (p.getResources() >= barracksType.cost + resourcesUsed && !freeWorkers.isEmpty()) {
                Unit u = freeWorkers.remove(0);
                buildIfNotAlreadyBuilding(u,barracksType,u.getX(),u.getY(),reservedPositions,p,pgs);
                resourcesUsed += barracksType.cost;
            }
        }


        // harvest with all the free workers:
        for (Unit u : freeWorkers) {
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
    }




















    /**
     * Huh?
     */
    @Override
    public List<ParameterSpecification> getParameters() {
        List<ParameterSpecification> parameters = new ArrayList<>();
        
        parameters.add(new ParameterSpecification("PathFinding", PathFinding.class, new AStarPathFinding()));

        return parameters;
    }

    /**
     * Returns a LINE of input from the file
     */
	public String Input() {
		
		// Get The Input
        String inputString = "";
		
		// Errors?
		try {
            inputString = UnsafeInput();
        }
        catch(IOException error) {
            error.printStackTrace();
        }
		
		// Return
		return inputString;
	}
		
	public String UnsafeInput() throws IOException {
		return bufferedReader.readLine();
	}
}