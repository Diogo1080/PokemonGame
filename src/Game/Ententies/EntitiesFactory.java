package Game.Ententies;

import Game.Ententies.NPCs.Events.Event;
import Game.Ententies.NPCs.NpcAi;
import Game.Ententies.NPCs.NpcKinds.People;
import Game.Ententies.PC.Player;
import Game.Ententies.PC.PlayerAi;
import Game.World.Map;
import asciiPanel.AsciiPanel;

import java.util.List;

public class EntitiesFactory {
    private Map map;

    public EntitiesFactory(Map map){
        this.map = map;
    }

    public Entity newPlayer(String name){
        Entity player = new Player(map, 'P', AsciiPanel.brightWhite,7,1,name);
        map.addAtLocation(player,7,1);
        player.setAi(new PlayerAi(player));
        return player;
    }
    public Entity newPeople(String name, int x, int y, List<Event> events){
        Entity NPC =  new People(map,'p',AsciiPanel.brightYellow,x,y,name,events);
        map.addAtLocation(NPC,x,y);
        NPC.setAi(new NpcAi(NPC));
        return NPC;
    }


}
