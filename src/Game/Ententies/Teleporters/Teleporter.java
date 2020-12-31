package Game.Ententies.Teleporters;

import Game.Ententies.Entity;
import Game.Ententies.Events.onTeleport;
import Game.Map;

import java.awt.*;

public class Teleporter extends Entity {
    onTeleport event;
    public onTeleport getEvent() {
        return event;
    }

    public Teleporter(Map map, char glyph, Color color, int x, int y, String name,onTeleport event) {
        super(map, glyph, color, x, y, name);
        this.event=event;
    }
}
