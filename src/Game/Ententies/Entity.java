package Game.Ententies;

import Game.World.Map;

import java.awt.*;

public abstract class Entity {
    private Map map;
    public int x;
    public int y;

    private final String name;
    public String getName() { return name; }

    protected char glyph;
    public char getGlyph() { return glyph; }

    private Color color;
    public Color getColor() { return color; }

    private EntitiesAi ai;
    public void setAi(EntitiesAi ai) { this.ai = ai; }
    public EntitiesAi getAi() { return ai; }

    public void moveBy(int mx, int my){
        ai.onEnterNewTile(x+mx, y+my, map.tile(x+mx, y+my),map.getEntityByCords(x+mx, y+my));
    }

    public Entity(Map map, char glyph, Color color, int x, int y, String name) {
        this.name=name;
        this.x=x;
        this.y=y;
        this.map = map;
        this.glyph = glyph;
        this.color = color;
    }
}

