
package peter;

import rts.PlayerAction;

/**
 * Rule examines the KnowledgeBase and updates its state.
 */
public class Rule
{
    private String name;                // For ease of human management
                                        // NOTE: The lack of time, this is only meant to be a CHECK,
                                        // not a new layer of state (and potentially bugs).
    private boolean status;              // Last updated state
    private ArrayList<Fact> conditions; // What needs to happen for the rule to activate
    private Action action;
    
    /**
     * All rules need a name
     */
    public Rule(String name)
    {
        this.name = name;
        this.status = false;
        this.conditions = new ArrayList<Fact>();
        this.action = new Action();
    }

    public String Name()
    {
        return this.name;
    }

    public boolean Status()
    {
        return this.status;
    }

    public void Add(Fact fact)
    {
        this.conditions.Add(fact);
    }

    public void Set(Action action)
    {
        this.action = action;
    }

    /**
     * Each rule needs to implement its own arbitrary update checks on a knowledgeBase
     */
    public boolean Update(GameState gameState)
    {
        for (Fact fact : this.conditions)
        {
            fact.Update(gameState);
            if (!fact.Status())
            {
                self.status = false;
                return false;
            }
        }
        self.status = true;
        return true;
    }

    /**
     * Each rules has a corresponding action to perform when true.
     * Each rules needs to specify what this is.
     */
    public Action Action()
    {
        return this.action;
    }

    /**
     * For diagnostics, returns name and last updated state
     */
    public String toString()
    {
            StringBuilder results = new StringBuilder();
            results.append("RULE: " + this.name + ": " + String.valueOf(this.status) + "\n");
            for (Fact fact : this.conditions)
            {
                results.append(fact.toString());
            }
            results.append("\n");
            return results.toString();
    }
}