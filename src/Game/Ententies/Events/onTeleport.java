package Game.Ententies.Events;

import Game.Constants;
import Game.Ententies.EntitiesFactory;
import Game.Ententies.Entity;
import Game.Ententies.NPCs.Npc;
import Game.Ententies.PC.Player;
import Game.Ententies.PC.PlayerAi;
import Game.GameLoader;
import Game.Map;

import java.util.List;

public class onTeleport extends Event {
    String mapToTeleport;
    int y, x;

    public onTeleport(String name, List<String> talks, boolean repeat, String mapToTeleport, int x, int y, Npc npc) {
        super(name, talks, repeat, npc);
        this.mapToTeleport = mapToTeleport;
        this.y = y;
        this.x = x;
    }

    public Map teleport(Player player, PlayerAi playerAi) {
        String mapLocation = Constants.MAPSAVEDPATH;
        mapLocation = String.format(mapLocation, player.getName());
        mapLocation = mapLocation + mapToTeleport;

        Map map = GameLoader.LoadMap(mapLocation);
        EntitiesFactory entitiesFactory = new EntitiesFactory(map);
        GameLoader.LoadEntities(mapLocation, entitiesFactory, map);

        playerAi.Notify(talks.get(0));
        player.map = map;
        player.x = x;
        player.y = y;
        return map;
    }


    @Override
    public void handle(Entity player, PlayerAi playerAi) {

    }
}
