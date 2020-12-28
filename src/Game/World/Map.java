package Game.World;

import Game.Ententies.Entity;
import Game.Ententies.NPCs.Npc;
import Game.Tile;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Map {
    private List<Entity> entityList;

    public List<Entity> getEntityList() {
        return entityList;
    }

    private Tile[][] tiles;
    private int width;

    public int width() {
        return width;
    }

    private int height;

    public int height() {
        return height;
    }

    public Map(Tile[][] tiles) {
        this.entityList = new LinkedList<>();
        this.tiles = tiles;
        this.width = tiles.length;
        this.height = tiles[0].length;
    }

    public Tile tile(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            return Tile.BOUNDS;
        else
            return tiles[x][y];
    }

    public char glyph(int x, int y) {
        return tile(x, y).glyph();
    }

    public Color color(int x, int y) {
        return tile(x, y).color();
    }

    public void addAtLocation(Entity entity, int x, int y) {
        entity.x = x;
        entity.y = y;
    }

    public Npc getEntityByCords(int x, int y) {
        for (Entity entity :entityList) {
            if (entity.x == x && entity.y == y){
                return (Npc) entity;
            }
        }
        return null;
    }
}
