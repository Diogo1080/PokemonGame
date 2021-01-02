package Game.Ententies.NPCs;

import Game.Constants;
import Game.Ententies.EntitiesAi;
import Game.Ententies.Entity;
import Game.Ententies.Events.Event;
import Game.Ententies.Events.onPlayerPosition;
import Game.Tile;

public class NpcAi extends EntitiesAi {
    int lastTime = 0;

    public NpcAi(Npc entity) {
        super(entity);
    }

    @Override
    public void onEnterNewTile(int x, int y, Tile tile, Entity entityByCords) {
        lastTime++;
        if (lastTime == Constants.NPCINTERNALCLOCK) {
            if (tile.isWalkable()) {
                entity.x = x;
                entity.y = y;
            }
            lastTime=0;
        }
    }

    @Override
    public Event onUpdate() {
        Npc entity = (Npc) super.entity;
        for (Event event : entity.getEvents()) {
            if (event instanceof onPlayerPosition) {
                onPlayerPosition onPlayerPositionEvent = (onPlayerPosition) event;
                if (onPlayerPositionEvent.checkForPlayer(entity.map) && !(onPlayerPositionEvent.getDone())) {
                    return onPlayerPositionEvent;
                }
            }
        }
        return null;
    }

}
