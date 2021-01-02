package Game.Ententies.Events;

import Game.Ententies.NPCs.Npc;
import Game.Ententies.PC.Player;
import Game.Ententies.PC.PlayerAi;

import java.util.List;

public class rewardPlayer extends Event {
    List<String> choices;
    String type;

    public rewardPlayer(String name, List<String> talks, Event parent, boolean repeat, boolean done, Npc npc, List<String> choices, String type) {
        super(name, talks, parent, repeat, done, npc);
        this.choices = choices;
        this.type = type;
    }

    @Override
    public void handle(Player player, PlayerAi playerAi) {

    }
}
