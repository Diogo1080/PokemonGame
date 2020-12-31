package Game.Ententies.NPCs;

import Game.Ententies.Entity;
import Game.Ententies.Events.Event;
import Game.Map;

import java.awt.*;
import java.util.List;

public abstract class Npc extends Entity {
    List<Event> events;

    public List<Event> getEvents() {
        return events;
    }

    public Npc(Map map, char glyph, Color color, int x, int y, String name, List<Event> events) {
        super(map, glyph, color, x, y, name);
        this.events=events;
    }
}
