package Game.Ententies.PC;

import Game.Ententies.EntitiesAi;
import Game.Ententies.Entity;
import Game.Ententies.Events.Event;
import Game.Tile;

import java.util.LinkedList;
import java.util.List;

public class PlayerAi extends EntitiesAi {
    private List<String> messages;

    public List<String> getMessages() {
        return messages;
    }

    public void notify(String message) {
        messages.add(message);
    }

    public PlayerAi(Player entity) {
        super(entity);
        messages = new LinkedList<>();
    }

    @Override
    public void onEnterNewTile(int x, int y, Tile tile, Entity entityByCords) {
        if (tile.isWalkable()) {
            super.entity.lastY = entity.y;
            super.entity.lastX = entity.x;

            entity.x = x;
            entity.y = y;
        }
    }

    @Override
    public Event onUpdate() {
        return null;
    }

}
