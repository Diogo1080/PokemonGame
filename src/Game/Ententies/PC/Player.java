package Game.Ententies.PC;

import Game.Ententies.Entity;
import Game.Map;
import Game.Pokemon.Pokemon;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Player extends Entity {
    List<Pokemon> pokemonList = new LinkedList<>();

    public Player(Map map, char glyph, Color color, int x, int y, String name) {
        super(map, glyph, color, x, y, name);
    }

    public boolean hasPokemon() {
        return !pokemonList.isEmpty();
    }
}
