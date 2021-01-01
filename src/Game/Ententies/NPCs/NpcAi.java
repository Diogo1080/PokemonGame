package Game.Ententies.NPCs;

import Game.Ententies.EntitiesAi;
import Game.Ententies.Entity;
import Game.Ententies.Events.Event;
import Game.Ententies.Events.onPlayerPosition;
import Game.Tile;

public class NpcAi extends EntitiesAi {
    public NpcAi(Npc entity) {
        super(entity);
    }

    @Override
    public void onEnterNewTile(int x, int y, Tile tile, Entity entityByCords) { }

    @Override
    public Event onUpdate() {
        Npc entity = (Npc) super.entity;
        for (Event event:entity.getEvents()) {
            if (event instanceof onPlayerPosition){

            }
        }
        return null;
    }

}
