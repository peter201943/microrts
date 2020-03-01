
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
    private boolean state;              // Last updated state
    private ArrayList<Fact> conditions; // What needs to happen for the rule to activate
    
    /**
     * All rules need a name
     */
    public Rule(String name)
    {
        this.name = name;
        this.state = false;
    }

    public String Name()
    {
        return this.name;
    }

    /**
     * Each rule needs to implement its own arbitrary update checks on a knowledgeBase
     */
    public boolean Update(KnowledgeBase knowledgeBase)
    {
        for (Fact fact : conditions)
        {
            
        }
        return state;
    }

    /**
     * Each rules has a corresponding action to perform when true.
     * Each rules needs to specify what this is.
     */
    public PlayerAction Action()
    {
        return new PlayerAction();
    }

    /**
     * For diagnostics, returns name and last updated state
     */
    public String toString()
    {
        return "RULE: " + this.name + ": " + String.valueOf(this.state) + "\n";
    }
}