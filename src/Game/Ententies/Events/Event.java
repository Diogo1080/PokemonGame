package Game.Ententies.Events;

import Game.Ententies.Entity;
import Game.Ententies.NPCs.Npc;
import Game.Ententies.PC.PlayerAi;

import java.util.List;

public abstract class Event {
    Npc npc;
    String name;
    List<String> talks;
    boolean repeat;

    public String getName() {
        return name;
    }

    public Event(String name, List<String> talks, boolean repeat, Npc npc) {
        this.npc = npc;
        this.name = name;
        this.talks = talks;
        this.repeat = repeat;
    }

    public abstract void handle(Entity player, PlayerAi playerAi);
}
