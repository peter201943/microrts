
package peter.Agents;

import peter.Rules.*;
import rts.GameState;
import rts.PlayerAction;

/**
 * Builds a Player Action from the GameState, KnowledgeBase, and RulesBase
 */
public class InferenceEngine
{


    // *************************************
    // CLASS BASICS
    // *************************************

    private RulesBase rulesBase;
    private PlayerAction playerAction;

    public InferenceEngine()
    {
        this.rulesBase = new RulesBase();
        this.playerAction = new PlayerAction();
    }

    /**
     * Given a GameState, updates the KnowledgeBase, then the RulesBase, and finally the PlayerAction
     */
    public PlayerAction Update(GameState gameState)
    {
        // Update each of the Components
        this.rulesBase.Update(gameState);

        // Generate the PlayerAction
        playerAction = new PlayerAction();
        for (Rule rule : rulesBase.Rules())
        {
            if (rule.Status())
            {
                //playerAction.merge(rule.Action().Evaluate());
            }
        }

        // Return Result
        return playerAction;
    }

    /**
     * If we want to check without recomputing
     */
    public PlayerAction Plan()
    {
        return this.playerAction;
    }

    /**
     * For diagnostics
     */
    public String toString()
    {
        return "INFERENCE: \n" + "\n" + this.rulesBase.toString() + "\n" + this.playerAction.toString() + "\n";
    }



    // *************************************
    // BOILER PLATE API
    // *************************************

    public void Add(Rule rule)
    {
        this.rulesBase.Add(rule);
    }

    public void Remove(Rule rule)
    {
        this.rulesBase.Remove(rule);
    }

    public Rule FindRule(String ruleName)
    {
        return this.rulesBase.Find(ruleName);
    }

}