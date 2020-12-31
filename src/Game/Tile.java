package Game;

import asciiPanel.AsciiPanel;

import java.awt.*;

public enum Tile {
    FLOOR('.', AsciiPanel.green,true ),
    PASSAGE((char) 61, AsciiPanel.brightWhite,true),
    PERSON((char) 112, AsciiPanel.cyan,false),
    TREE((char) 59, AsciiPanel.green,false),
    WATER((char) 126, AsciiPanel.brightCyan,false),
    COLLUM((char) 35, AsciiPanel.brightBlack,false),
    BUILDINGWALLTALL((char) 124, AsciiPanel.brightBlack,false),
    BUILDINGWALLWIDE((char) 45, AsciiPanel.brightBlack,false),
    REWARDPOKEBALL('0', AsciiPanel.red,false),
    BOUNDS('X', AsciiPanel.black,false);

    private char glyph;

    public char glyph() {
        return glyph;
    }

    private Color color;

    public Color color() {
        return color;
    }

    private boolean isWalkable;
    public boolean isWalkable() {
        return isWalkable;
    }

    Tile(char glyph, Color color, boolean isWalkable) {
        this.glyph = glyph;
        this.color = color;
        this.isWalkable = isWalkable;
    }

    public static Tile getTileByGlyph(char character) {
        for (Tile e : Tile.values()) {
            if (e.glyph == character) {
                return e;
            }
        }
        System.out.println(character);
        return null;
    }
}
