package Game.Ententies.PC;

import Game.Ententies.EntitiesAi;
import Game.Ententies.Entity;
import Game.Ententies.NPCs.Npc;
import Game.Tile;

import java.util.LinkedList;
import java.util.List;

public class PlayerAi extends EntitiesAi {
    private List<String> messages;
    public List<String> getMessages() {
        return messages;
    }

    public void Notify(String message){
        messages.add(message);
    }

    public PlayerAi(Entity ententie) {
        super(ententie);
        messages=new LinkedList<>();
    }

    @Override
    public void onEnterNewTile(int x, int y, Tile tile, Npc entityByCords) {
        if (entityByCords != null){
            return;
        }
        if (tile.isWalkable()) {
            entity.x = x;
            entity.y = y;
        }
    }

}