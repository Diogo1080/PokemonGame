package Game.Screens;

import Game.Ententies.EntitiesFactory;
import Game.Ententies.Entity;
import Game.Ententies.NPCs.Events.Event;
import Game.Ententies.NPCs.Events.onInteract;
import Game.Ententies.NPCs.Npc;
import Game.Ententies.PC.Player;
import Game.Ententies.PC.PlayerAi;
import Game.World.GameLoader;
import Game.World.Map;
import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;

public class PlayScreen implements Screen {
    private Map map;
    private Entity player;
    private PlayerAi playerAi;
    private int screenWidth;
    private int screenHeight;

    public PlayScreen() {
        screenWidth = 24;
        screenHeight = 24;
        LoadMap("./src/Game/World/StarterTown/Places/StartHouse");
        EntitiesFactory entitiesFactory = new EntitiesFactory(map);
        LoadEntities(entitiesFactory);
        player = entitiesFactory.newPlayer("Player");
        playerAi = (PlayerAi) player.getAi();
    }

    private void LoadMap(String Filename) {
        map = new GameLoader().LoadMap(Filename).build();
    }

    private void LoadEntities(EntitiesFactory entitiesFactory){
        GameLoader.LoadEntities("./src/Game/World/StarterTown/Places/StartHouse", entitiesFactory, map);
    }

    private void displayTiles(AsciiPanel terminal, int left, int top) {
        for (int x = 0; x < map.width(); x++) {
            for (int y = 0; y < map.height(); y++) {
                int wx = x + left;
                int wy = y + top;

                terminal.write(map.glyph(wx, wy), x, y, map.color(wx, wy));
            }
        }
        for (Entity entity : map.getEntityList()) {
            terminal.write(entity.getGlyph(), entity.x - left, entity.y - top, entity.getColor());
        }
    }

    public int getScrollX() {
        return Math.max(0, Math.min(player.x - screenWidth / 2, map.width() - screenWidth));
    }

    public int getScrollY() {
        return Math.max(0, Math.min(player.y - screenHeight / 2, map.height() - screenHeight));
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        int left = getScrollX();
        int top = getScrollY();

        displayMessages(terminal);
        displayTiles(terminal, left, top);
        terminal.write(player.getGlyph(), player.x - left, player.y - top, player.getColor());
    }

    private void displayMessages(AsciiPanel terminal) {
        int top = screenHeight - playerAi.getMessages().size();
        for (int i = 0; i < playerAi.getMessages().size(); i++) {
            terminal.writeCenter(playerAi.getMessages().get(i), top + i);
        }
        playerAi.getMessages().clear();
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_W -> player.moveBy(0, -1);
            case KeyEvent.VK_A -> player.moveBy(-1, 0);
            case KeyEvent.VK_S -> player.moveBy(0, 1);
            case KeyEvent.VK_D -> player.moveBy(1, 0);
            case KeyEvent.VK_E -> {
                return playerInteraction();
            }
        }
        return this;
    }


    public Screen playerInteraction() {
        List<Npc> entitiesAroundPlayer = new LinkedList<>();
        getEntitiesAroundPlayer(entitiesAroundPlayer);

        if (entitiesAroundPlayer.size() > 1) {
           return new SelectInteractionScreen(entitiesAroundPlayer,this);
        } else if (entitiesAroundPlayer.size() > 0) {
            Npc entity = entitiesAroundPlayer.get(0);
            for (Event eachEvent : entity.getEvents()) {
                if (eachEvent.getName().equals("onInteract")) {
                    onInteract event = (onInteract) eachEvent;
                    event.interact(entitiesAroundPlayer.get(0), (Player) player, playerAi);
                    break;
                }
            }
        } else {
            playerAi.Notify("There is no one around you");
        }
        return this;
    }

    private void getEntitiesAroundPlayer(List<Npc> entitiesAroundPlayer) {
        if (map.getEntityByCords(player.x + 1, player.y) != null) {
            entitiesAroundPlayer.add(map.getEntityByCords(player.x + 1, player.y));
        }
        if (map.getEntityByCords(player.x, player.y + 1) != null) {
            entitiesAroundPlayer.add(map.getEntityByCords(player.x, player.y + 1));
        }
        if (map.getEntityByCords(player.x - 1, player.y) != null) {
            entitiesAroundPlayer.add(map.getEntityByCords(player.x - 1, player.y));
        }
        if (map.getEntityByCords(player.x, player.y - 1) != null) {
            entitiesAroundPlayer.add(map.getEntityByCords(player.x, player.y - 1));
        }
    }

}
