package Game.Ententies.NPCs.NpcKinds;

import Game.Ententies.Events.Event;
import Game.Ententies.Events.onTeleport;
import Game.Ententies.NPCs.Npc;
import Game.Map;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Teleporter extends Npc {

    public onTeleport getEvent() {
        return (onTeleport) getEvents().get(0);
    }

    public Teleporter(Map map, char glyph, Color color, int x, int y, String name) {
        super(map, glyph, color, x, y, name);
    }
}
