package Game.Ententies;

import Game.Ententies.NPCs.Npc;
import Game.Ententies.NPCs.NpcAi;
import Game.Ententies.NPCs.NpcKinds.People;
import Game.Ententies.NPCs.NpcKinds.RewardPokeball;
import Game.Ententies.NPCs.NpcKinds.Teleporter;
import Game.Ententies.PC.Player;
import Game.Ententies.PC.PlayerAi;
import Game.Map;
import asciiPanel.AsciiPanel;

public class EntitiesFactory {
    private Map map;

    public EntitiesFactory(Map map) {
        this.map = map;
    }

    public Entity newPlayer(String name) {
        Entity player = new Player(map, 'P', AsciiPanel.brightWhite, 7, 1, name);
        map.addAtLocation(player, 7, 1);
        player.setAi(new PlayerAi((Player) player));
        return player;
    }

    public Entity newPlayer(String name, int X, int Y) {
        Entity player = new Player(map, 'P', AsciiPanel.brightWhite, X, Y, name);
        map.addAtLocation(player, X, Y);
        player.setAi(new PlayerAi((Player) player));
        return player;
    }

    public Entity newPeople(String name, int x, int y) {
        Entity NPC = new People(map, 'p', AsciiPanel.brightYellow, x, y, name);
        map.addAtLocation(NPC, x, y);
        NPC.setAi(new NpcAi((Npc) NPC));
        return NPC;
    }

    public Entity newTeleporter(String name, int x, int y) {
        Entity Teleporter = new Teleporter(map, '=', AsciiPanel.brightWhite, x, y, name);
        map.addAtLocation(Teleporter, x, y);
        Teleporter.setAi(new NpcAi((Teleporter) Teleporter));
        return Teleporter;
    }


    public Entity newRewardPokeball(String name, int x, int y) {
        Entity rewardPokeball = new RewardPokeball(map, '0', AsciiPanel.red, x, y, name);
        map.addAtLocation(rewardPokeball, x, y);
        rewardPokeball.setAi(new NpcAi((RewardPokeball) rewardPokeball));
        return rewardPokeball;
    }
}
