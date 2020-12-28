package Game.Ententies.NPCs.NpcKinds;

import Game.Ententies.NPCs.Events.Event;
import Game.Ententies.NPCs.Npc;
import Game.World.Map;

import java.awt.*;
import java.util.List;

public class People extends Npc {
    public People(Map map, char glyph, Color color, int x, int y, String name, List<Event> events) {
        super(map, glyph, color, x, y, name, events);
    }
}
