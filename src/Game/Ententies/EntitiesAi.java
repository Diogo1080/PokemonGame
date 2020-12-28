package Game.Ententies;

import Game.Ententies.NPCs.Npc;
import Game.Tile;

public abstract class EntitiesAi {
    protected Entity entity;

    public EntitiesAi(Entity entity) {
        this.entity = entity;
        this.entity.setAi(this);
    }

    public abstract void onEnterNewTile(int x, int y, Tile tile, Npc entityByCords);
}

