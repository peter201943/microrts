
package peter;

import java.util.ArrayList;
import rts.GameState;

/**
 * Knowledge Base Holds, Updates, and Access Facts.
 * Knowledge Base Update returns the True subset of the Knowledge Base.
 */
public class KnowledgeBase
{
    private ArrayList<Fact> facts;      // Where we store facts

    /**
     * Clear the fact list
     */
    public KnowledgeBase()
    {
        this.facts = new ArrayList<Fact>();
    }

    /**
     * Takes a World State and parses it into facts
     * By visiting each fact and updating it.
     * Returns a temporary knowledge base of the facts
     * That rules can parse.
     */
    public KnowledgeBase Update(GameState gameState)
    {
        KnowledgeBase results = new KnowledgeBase();

        for (Fact fact : this.facts)
        {
            if (fact.Update(gameState))
            {
                results.Add(fact);
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
        for (Fact fact : this.facts)
        {
            results.append(fact.toString());
        }
        return results.toString();
    }



    // *************************************
    // Boiler Plate Getters and Setters
    // *************************************

    /**
     * Add a Fact given an instance
     */
    public void Add(Fact fact)
    {
        facts.Add(fact);
    }

    /**
     * Remove a Fact given its instance
     */
    public void Remove(Fact fact)
    {
        facts.Remove(fact);
    }

    /**
     *  Find a Fact given its instance
     */
    public void Get(Fact fact)
    {
        facts.Get(fact);
    }

    /**
     * Finds a fact given its name
     */
    public Fact Find(String factName)
    {
        for (Fact fact : this.facts)
        {
            if (fact.Name().equals(factName))
            {
                return fact;
            }
        }
        return new Fact();
    }
}