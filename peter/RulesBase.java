
package peter;

import java.util.ArrayList;

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
    public RulesBase Update(KnowledgeBase knowledgeBase)
    {
        RulesBase results = new RulesBase();

        for (Rule rule : this.rules)
        {
            if (rule.Update(knowledgeBase))
            {
                results.Add(rule);
            }
        }

        return results;
    }

    /**
     * For Diagnostics, prints every fact
     */
    public String toString()
    {
        StringBuilder results = new StringBuilder();
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
        rules.Add(rule);
    }

    /**
     * Remove a Rule given its instance
     */
    public void Remove(Rule rule)
    {
        rules.Remove(rule);
    }

    /**
     *  Find a Rule given its instance
     */
    public void Get(Rule rule)
    {
        rules.Get(rule);
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
        return new Rule();
    }
}