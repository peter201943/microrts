
package peter;

import rts.GameState;

/**
 * Fact examines the GameState and updates its state.
 */
public abstract class Fact
{
    private String name;        // For ease of human management
    private boolean state;      // Is the fact valid?
    private int age;            // How long has this fact been true?
    private int time;           // When was the fact last update?
    
    /**
     * All facts need a name
     */
    public Fact(String name)
    {
        this.name = name;
        this.state = false;
        this.age = 0;
    }

    /**
     * Each fact needs to implement its own arbitrary update checks on a gameState
     */
    public boolean Update(GameState gameState)
    {
        this.state = false;
        this.time += 1;
        this.age = 0;
        return state;
    }

    public boolean State()
    {
        return this.state;
    }

    /**
     * For diagnostics, returns name and last updated state
     */
    public String toString()
    {
        return "FACT: " + this.name + ": " + String.valueOf(this.state) + " " + "[" + String.valueOf(this.age) + " / " + String.valueOf(this.time) + "]" + "\n";
    }

    public String Name()
    {
        return this.name;
    }

    public int Age()
    {
        return this.age;
    }

    public int Time()
    {
        return this.time;
    }
}