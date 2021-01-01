package Game.Ententies.NPCs.NpcKinds;

import Game.Ententies.Events.Event;
import Game.Ententies.NPCs.Npc;
import Game.Map;

import java.awt.*;
import java.util.List;

public class Trainer extends Npc {

    public Trainer(Map map, char glyph, Color color, int x, int y, String name) {
        super(map, glyph, color, x, y, name);
    }
}
