
package peter.Rules;

import java.util.ArrayList;

import rts.GameState;

/**
 * Knowledge Base Holds, Updates, and Access Facts.
 * Knowledge Base Update returns the True subset of the Knowledge Base.
 */
public class RulesBase
{
    private ArrayList<Rule> rules;      // Where we store rules

    /**
     * Clear the rule list
     */
    public RulesBase()
    {
        this.rules = new ArrayList<Rule>();
    }

    /**
     * Takes a World State and parses it into facts
     * By visiting each fact and updating it.
     * Returns a temporary rules base of the rules
     * That agents can parse.
     */
    public RulesBase Update(GameState gameState)
    {
        for (Rule rule : this.rules)
        {
            rule.Update(gameState);
        }
        return this;
    }

    /**
     * When we dont want to recompute
     */
    public RulesBase Status()
    {
        return this;
    }

    /**
     * For Diagnostics, prints every fact
     */
    public String toString()
    {
        StringBuilder results = new StringBuilder();
        results.append("RULES BASE: \n");
        for (Rule rule : this.rules)
        {
            results.append(rule.toString());
        }
        return results.toString();
    }

    

    // *************************************
    // Boiler Plate Getters and Setters
    // *************************************

    /**
     * Add a Rule given an instance
     */
    public void Add(Rule rule)
    {
        rules.add(rule);
    }

    /**
     * Remove a Rule given its instance
     */
    public void Remove(Rule rule)
    {
        for (Rule aRule : this.rules)
        {
            if (aRule.Name().equals(rule.Name()))
            {
                this.rules.remove(aRule);
            }
        }
    }

    /**
     *  Find a Rule given its instance
     */
    public Rule Get(Rule rule)
    {
        for (Rule aRule : this.rules)
        {
            if (aRule.Name().equals(rule.Name()))
            {
                return aRule;
            }
        }
        return new Rule("empty");
    }

    /**
     * Finds a rule given its name.
     * NOTE: Caller needs to check that the incoming
     * rule is not blank!
     */
    public Rule Find(String ruleName)
    {
        for (Rule rule : this.rules)
        {
            if (rule.Name().equals(ruleName))
            {
                return rule;
            }
        }
        return new Rule("empty");
    }

    public ArrayList<Rule> Rules()
    {
        return this.rules;
    }
}