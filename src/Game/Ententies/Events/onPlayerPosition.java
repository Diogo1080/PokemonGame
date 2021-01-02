package Game.Ententies.Events;

import Game.Ententies.NPCs.Npc;
import Game.Ententies.PC.Player;
import Game.Ententies.PC.PlayerAi;
import Game.GameSaver;
import Game.Map;
import Game.PathFinding.Path;
import Game.PathFinding.Point;

import java.util.List;

public class onPlayerPosition extends Event {
    boolean firstEntry = true;
    boolean hasPokemon;
    boolean moveToPlayer;
    boolean movePlayerToNpc;

    boolean hasPokemonInstance;
    boolean moveToPlayerInstance;
    boolean movePlayerToNpcInstance;

    boolean range;
    boolean done;
    int message = -1;
    int npcInitialX;
    int npcInitialY;
    List<Integer> x;
    List<Integer> xFinal;
    List<Integer> y;
    List<Integer> yFinal;

    public onPlayerPosition(String name, List<String> talks, Event parent, Npc npc, boolean movePlayerToNpc, boolean hasPokemon, boolean range, boolean repeat, boolean moveToPlayer, boolean done, List<Integer> x, List<Integer> y, List<Integer> xFinal, List<Integer> yFinal) {
        super(name, talks, parent, repeat, done, npc);
        this.moveToPlayer = moveToPlayer;
        this.hasPokemon = hasPokemon;
        this.movePlayerToNpc = movePlayerToNpc;
        this.range = range;
        this.x = x;
        this.xFinal = xFinal;
        this.y = y;
        this.yFinal = yFinal;
    }

    public boolean checkForPlayer(Map map) {
        if (!done) {
            if (range) {
                for (int i = 0; i < this.x.size(); i++) {
                    if (x == xFinal) {
                        for (int y = this.y.get(i); y <= this.yFinal.get(i); y++) {
                            if (map.getEntityByCords(this.x.get(i), y) instanceof Player) {
                                return true;
                            }
                        }
                    } else {
                        for (int x = this.x.get(i); x <= this.xFinal.get(i); x++) {
                            if (map.getEntityByCords(x, this.y.get(i)) instanceof Player) {
                                return true;
                            }
                        }
                    }
                }
            } else {
                for (int i = 0; i < this.x.size(); i++) {
                    if (map.getEntityByCords(this.x.get(i), this.y.get(i)) instanceof Player) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isInInitialPosition(Npc npc) {
        return npc.x == npcInitialX && npc.y == npcInitialY;
    }

    @Override
    public void handle(Player player, PlayerAi playerAi) {


        if (firstEntry) {
            moveToPlayerInstance = this.moveToPlayer;
            movePlayerToNpcInstance = this.movePlayerToNpc;
            hasPokemonInstance = this.hasPokemon;

            npcInitialX = npc.x;
            npcInitialY = npc.y;
            firstEntry = false;
        }
        if (moveToPlayerInstance) {
            List<Point> points = new Path(npc, player.x, player.y).points();

            int mx = points.get(0).x - npc.x;
            int my = points.get(0).y - npc.y;

            npc.moveBy(mx, my);

            if (npc.map.getEntityByCords(npc.x, npc.y - 1) instanceof Player ||
                    npc.map.getEntityByCords(npc.x - 1, npc.y) instanceof Player ||
                    npc.map.getEntityByCords(npc.x, npc.y + 1) instanceof Player ||
                    npc.map.getEntityByCords(npc.x + 1, npc.y) instanceof Player
            ) {
                moveToPlayerInstance = false;
            }
        } else if (movePlayerToNpcInstance) {
            List<Point> points = new Path(player, npc.x, npc.y).points();

            int mx = points.get(0).x - player.x;
            int my = points.get(0).y - player.y;

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            player.moveBy(mx, my);

            if (npc.map.getEntityByCords(npc.x, npc.y - 1) instanceof Player ||
                    npc.map.getEntityByCords(npc.x - 1, npc.y) instanceof Player ||
                    npc.map.getEntityByCords(npc.x, npc.y + 1) instanceof Player ||
                    npc.map.getEntityByCords(npc.x + 1, npc.y) instanceof Player
            ) {
                movePlayerToNpcInstance = false;
            }
        } else {
            if (message < talks.size() - 1) {
                message++;
                playerAi.notify(npc.getName() + ": " + talks.get(message).formatted(player.getName()));
                return;
            }
            if (!isInInitialPosition(npc)) {
                List<Point> points = new Path(npc, npcInitialX, npcInitialY).points();

                int mx = points.get(0).x - npc.x;
                int my = points.get(0).y - npc.y;

                npc.moveBy(mx, my);

            } else {
                if (hasPokemonInstance) {
                    if (player.hasPokemon()) {
                        hasPokemonInstance = false;
                        repeat = false;
                    }
                }
                Event nextEvent = null;
                for (Event event : npc.getEvents()) {
                    if (event.parent != null) {
                        if (event.parent == this) {
                            event.done = false;
                            nextEvent = event;
                        }
                    }
                }

                super.done = true;
                if (repeat) {
                    firstEntry = true;
                    message = -1;
                }
                GameSaver.saveOnPlayerPositionEvent(this, player, npc, npc.map);
                if (nextEvent != null) {
                    GameSaver.saveOnPlayerPositionEvent((onPlayerPosition) nextEvent, player, npc, npc.map);
                }

            }
        }
    }
}
