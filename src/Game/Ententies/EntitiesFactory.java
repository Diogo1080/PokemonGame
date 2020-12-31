package Game.Ententies;

import Game.Ententies.Events.Event;
import Game.Ententies.Events.onTeleport;
import Game.Ententies.NPCs.NpcAi;
import Game.Ententies.NPCs.NpcKinds.People;
import Game.Ententies.PC.Player;
import Game.Ententies.PC.PlayerAi;
import Game.Ententies.Teleporters.Teleporter;
import Game.Map;
import asciiPanel.AsciiPanel;

import java.util.List;

public class EntitiesFactory {
    private Map map;

    public EntitiesFactory(Map map) {
        this.map = map;
    }

    public Entity newPlayer(String name) {
        Entity player = new Player(map, 'P', AsciiPanel.brightWhite, 7, 1, name);
        map.addAtLocation(player, 7, 1);
        player.setAi(new PlayerAi(player));
        return player;
    }

    public Entity newPlayer(String name, int X, int Y) {
        Entity player = new Player(map, 'P', AsciiPanel.brightWhite, X, Y, name);
        map.addAtLocation(player, X, Y);
        player.setAi(new PlayerAi(player));
        return player;
    }

    public Entity newPeople(String name, int x, int y, List<Event> events) {
        Entity NPC = new People(map, 'p', AsciiPanel.brightYellow, x, y, name, events);
        map.addAtLocation(NPC, x, y);
        NPC.setAi(new NpcAi(NPC));
        return NPC;
    }

    public Entity newTeleporter(String name, int x, int y, List<Event> events) {
        Entity Teleporter = new Teleporter(map, '=', AsciiPanel.brightWhite, x, y, name, (onTeleport) events.get(0));
        map.addAtLocation(Teleporter, x, y);
        Teleporter.setAi(new NpcAi(Teleporter));
        return Teleporter;
    }


}
