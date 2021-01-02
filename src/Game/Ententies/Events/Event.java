package Game.Ententies.Events;

import Game.Ententies.NPCs.Npc;
import Game.Ententies.PC.Player;
import Game.Ententies.PC.PlayerAi;

import java.util.List;

public abstract class Event {
    Npc npc;
    String name;
    List<String> talks;
    Event parent;
    boolean repeat;
    boolean done;

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getName() {
        return name;
    }

    public Event(String name, List<String> talks, Event parent, boolean repeat, boolean done, Npc npc) {
        this.npc = npc;
        this.name = name;
        this.done = done;
        this.talks = talks;
        this.repeat = repeat;
        this.parent = parent;
    }

    public abstract void handle(Player player, PlayerAi playerAi);

    public boolean getDone() {
        return done;
    }

    public boolean getRepeat() {
        return repeat;
    }

    ;
}
