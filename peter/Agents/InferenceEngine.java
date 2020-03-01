
package peter;

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

    private KnowledgeBase knowledgeBase;
    private RulesBase rulesBase;
    private PlayerAction playerAction;

    public InferenceEngine()
    {
        this.knowledgeBase = new KnowledgeBase();
        this.rulesBase = new RulesBase();
        this.playerAction = new PlayerAction();
    }

    /**
     * Given a GameState, updates the KnowledgeBase, then the RulesBase, and finally the PlayerAction
     */
    public PlayerAction Update(GameState gameState)
    {
        // Update each of the Components
        this.knowledgeBase.Update(gameState);
        this.rulesBase.Update(knowledgeBase);

        // Generate the PlayerAction
        playerAction = new PlayerAction();
        for (Rule rule : this.rulesBase)
        {
            if (rule.State())
            {
                playerAction.merge(rule.Action());
            }
        }
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
        return "INFERENCE: \n" + "\n" + this.knowledgeBase.toString() + "\n" + this.rulesBase.toString() + "\n" + this.playerAction.toString() + "\n";
    }



    // *************************************
    // BOILER PLATE API
    // *************************************

    public void Add(Fact fact)
    {
        this.knowledgeBase.Add(fact);
    }

    public void Add(Rule rule)
    {
        this.rulesBase.Add(rule);
    }

    public void Remove(Rule rule)
    {
        this.rulesBase.Remove(rule);
    }

    public void Remove(Fact fact)
    {
        this.knowledgeBase.Remove(fact);
    }

    public Fact FindFact(String factName)
    {
        return this.knowledgeBase.Find(factName);
    }

    public Rule FindRule(String ruleName)
    {
        return this.rulesBase.Find(ruleName);
    }

}