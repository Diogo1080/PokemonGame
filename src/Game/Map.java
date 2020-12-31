package Game;

import Game.Ententies.Entity;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Map {
    private List<Entity> entityList;
    private String name;
    private String path;

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }


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

    public Map(Tile[][] tiles, String name, String path) {
        this.entityList = new LinkedList<>();
        this.name = name;
        this.path = path;
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

    public Entity getEntityByCords(int x, int y) {
        for (Entity entity : entityList) {
            if (entity.x == x && entity.y == y) {
                return entity;
            }
        }
        return null;
    }
}
