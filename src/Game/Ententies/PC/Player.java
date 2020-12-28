package Game.Ententies.PC;

import Game.Ententies.Entity;
import Game.World.Map;

import java.awt.*;

public class Player extends Entity {
    public Player(Map map, char glyph, Color color, int x, int y, String name) {
        super(map, glyph, color, x, y, name);
    }
}
