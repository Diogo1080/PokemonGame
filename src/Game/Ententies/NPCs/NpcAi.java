package Game.Ententies.NPCs;

import Game.Ententies.EntitiesAi;
import Game.Ententies.Entity;
import Game.Tile;

public class NpcAi extends EntitiesAi {
    public NpcAi(Entity entity) {
        super(entity);
    }

    @Override
    public void onEnterNewTile(int x, int y, Tile tile, Entity entityByCords) { }

}
