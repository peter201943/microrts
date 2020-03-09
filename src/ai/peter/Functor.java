


package ai.peter;

// https://stackoverflow.com/questions/2752192/array-of-function-pointers-in-java

import rts.GameState;
import rts.PhysicalGameState;
import rts.Player;
import rts.units.Unit;

public class Functor
{
    public void execute(Unit u, Player p, GameState gs, PhysicalGameState pgs){};
}