package Game.Ententies.Events;

import Game.Constants;
import Game.Ententies.EntitiesFactory;
import Game.Ententies.NPCs.Npc;
import Game.Ententies.PC.Player;
import Game.Ententies.PC.PlayerAi;
import Game.GameLoader;
import Game.Map;

import java.util.List;

public class onTeleport extends Event {
    String mapToTeleport;
    int y, x;

    public onTeleport(String name, List<String> talks, Event parent, boolean repeat, boolean done, String mapToTeleport, int x, int y, Npc npc) {
        super(name, talks, parent, repeat, done, npc);
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

        map.getEntityList().add(player);
        player.map = map;
        player.x = x;
        player.y = y;
        return map;
    }


    @Override
    public void handle(Player player, PlayerAi playerAi) {

    }
}
