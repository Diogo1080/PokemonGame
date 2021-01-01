package Game.Ententies.Events;

import Game.Ententies.Entity;
import Game.Ententies.NPCs.Npc;
import Game.Ententies.NPCs.NpcKinds.People;
import Game.Ententies.NPCs.NpcKinds.Trainer;
import Game.Ententies.PC.Player;
import Game.Ententies.PC.PlayerAi;
import Game.Screens.BattleScreen;

import java.util.List;

public class onInteract extends Event {
    public onInteract(String name, List<String> talks, boolean repeat, Npc npc) {
        super(name, talks, repeat, npc);
    }

    @Override
    public void handle(Entity player, PlayerAi playerAi) {
        if (npc instanceof People) {
            playerAi.Notify(npc.getName() + ": " + talks.get((int) (Math.random() * talks.size())));
        } else if (npc instanceof Trainer) {
            for (String talk : talks) {
                playerAi.Notify(talk);
            }
            new BattleScreen(npc, (Player) player);
        }

    }
}
