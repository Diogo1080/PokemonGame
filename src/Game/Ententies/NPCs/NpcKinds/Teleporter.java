package Game.Ententies.NPCs.NpcKinds;

import Game.Ententies.Events.onTeleport;
import Game.Ententies.NPCs.Npc;
import Game.Map;

import java.awt.*;

public class Teleporter extends Npc {

    public onTeleport getEvent() {
        return (onTeleport) getEvents().get(0);
    }

    public Teleporter(Map map, char glyph, Color color, int x, int y, String name) {
        super(map, glyph, color, x, y, name);
    }
}
