package Game.Ententies.Events;

import Game.Ententies.NPCs.Npc;
import Game.Ententies.NPCs.NpcKinds.People;
import Game.Ententies.NPCs.NpcKinds.Trainer;
import Game.Ententies.PC.Player;
import Game.Ententies.PC.PlayerAi;
import Game.Screens.BattleScreen;

import java.util.List;

public class onInteract extends Event {
    public onInteract(String name, List<String> talks, Event parent, boolean repeat, boolean done, Npc npc) {
        super(name, talks, parent, repeat, done, npc);
    }

    @Override
    public void handle(Player player, PlayerAi playerAi) {
        if (npc instanceof People) {
            playerAi.notify(npc.getName() + ": " + talks.get((int) (Math.random() * talks.size())));
        } else if (npc instanceof Trainer) {
            for (String talk : talks) {
                playerAi.notify(talk);
            }
            new BattleScreen(npc, player);
        }

    }
}
