package Game.Ententies.NPCs.Events;

import java.util.List;

public abstract class Event {
    String name;
    List<String> talks;
    boolean repeat;

    public String getName() {
        return name;
    }

    public Event(String name, List<String> talks, boolean repeat){
        this.name=name;
        this.talks=talks;
        this.repeat=repeat;
    }
}
