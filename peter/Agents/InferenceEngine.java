
package peter;

import rts.GameState;
import rts.PlayerAction;

/**
 * Builds a Player Action from the GameState, KnowledgeBase, and RulesBase
 */
public class InferenceEngine
{
    private KnowledgeBase knowledgeBase;
    private RulesBase rulesBase;
    private PlayerAction playerAction;

    public InferenceEngine()
    {
        this.knowledgeBase = new KnowledgeBase();
        this.rulesBase = new RulesBase();
        playerAction = new PlayerAction();
    }

    /**
     * Given a GameState, updates the KnowledgeBase, then the RulesBase, and finally the PlayerAction
     */
    public PlayerAction Update(GameState gameState)
    {
        playerAction = new PlayerAction();
        for (Rule rule : this.rulesBase.Update(this.knowledgeBase.Update(gameState)))
        {
            playerAction.merge(rule.Action());
        }
        return playerAction;
    }

    /**
     * For diagnostics
     */
    public String toString()
    {
        return "INFERENCE: \n" + "\n" + this.knowledgeBase.toString() + "\n" + this.rulesBase.toString() + "\n" + this.playerAction.toString() + "\n";
    }



    // *************************************
    // Boiler Plate Getters and Setters
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