package Game.Ententies.Events;

import Game.Ententies.Entity;
import Game.Ententies.NPCs.Npc;
import Game.Ententies.PC.Player;
import Game.Ententies.PC.PlayerAi;

import java.util.List;

public class onPlayerPosition extends Event {
    boolean moveToPlayer;
    boolean done;
    List<Integer> x;
    List<Integer> y;

    public onPlayerPosition(String name, List<String> talks, boolean repeat, Npc npc, boolean moveToPlayer, boolean done, List<Integer> x, List<Integer> y) {
        super(name, talks, repeat, npc);
        this.moveToPlayer = moveToPlayer;
        this.done = done;
        this.x = x;
        this.y = y;

    }

    public boolean checkPlayer(Player player) {
        for (int i = 0; i < this.x.size(); i++) {
            if (this.x.get(i) == player.x && this.y.get(i) == player.y) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void handle(Entity player, PlayerAi playerAi) {

    }
}
