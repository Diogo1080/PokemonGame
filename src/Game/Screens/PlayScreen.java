package Game.Screens;

import Game.Constants;
import Game.Ententies.EntitiesFactory;
import Game.Ententies.Entity;
import Game.Ententies.Events.Event;
import Game.Ententies.Events.onInteract;
import Game.Ententies.NPCs.Npc;
import Game.Ententies.PC.Player;
import Game.Ententies.PC.PlayerAi;
import Game.Ententies.NPCs.NpcKinds.Teleporter;
import Game.GameLoader;
import Game.GameSaver;
import Game.Map;
import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;

public class PlayScreen implements Screen {
    private Map map;
    private Entity player;
    private PlayerAi playerAi;
    private int screenWidth;
    private int screenHeight;
    private boolean showMenu;
    private Event handleEvent = null;

    public PlayScreen(String playerName) {
        screenWidth = 40;
        screenHeight = 24;
        GameSaver.saveGame(playerName);
        String path = Constants.PATHTOINITGAME.formatted(playerName);
        LoadMap(path);
        EntitiesFactory entitiesFactory = new EntitiesFactory(map);
        LoadEntities(entitiesFactory, path);
        player = entitiesFactory.newPlayer(playerName);
        playerAi = (PlayerAi) player.getAi();
        GameSaver.savePlayer((Player) player, Constants.BASESAVEPATH.concat("/").concat(playerName));
    }

    public PlayScreen(String playerName, String filename) {
        screenWidth = 40;
        screenHeight = 24;
        LoadPlayer(filename + "/Player.Json");
        LoadMap(player.map.getPath());
        EntitiesFactory entitiesFactory = new EntitiesFactory(map);
        LoadEntities(entitiesFactory, player.map.getPath());
    }

    private void LoadPlayer(String filename) {
        player = GameLoader.LoadPlayer(filename);
        playerAi = (PlayerAi) player.getAi();
    }

    private void LoadMap(String Filename) {
        map = GameLoader.LoadMap(Filename);
    }

    private void LoadEntities(EntitiesFactory entitiesFactory, String filename) {
        GameLoader.LoadEntities(filename, entitiesFactory, map);
    }

    private void displayTiles(AsciiPanel terminal, int left, int top) {
        for (int x = 0; x < screenWidth - 10; x++) {
            for (int y = 0; y < screenHeight - 1; y++) {
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
        Entity entity = null;
        int mx = 0;
        int my = 0;
        switch (key.getKeyCode()) {
            case KeyEvent.VK_W -> {
                entity = map.getEntityByCords(player.x, player.y - 1);
                my = -1;
            }
            case KeyEvent.VK_A -> {
                entity = map.getEntityByCords(player.x - 1, player.y);
                mx = -1;
            }
            case KeyEvent.VK_S -> {
                entity = map.getEntityByCords(player.x, player.y + 1);
                my = 1;
            }
            case KeyEvent.VK_D -> {
                entity = map.getEntityByCords(player.x + 1, player.y);
                mx = 1;
            }
            case KeyEvent.VK_ENTER -> showMenu = true;
        }
        if (entity != null) {
            if (entity instanceof Npc) {
                playerInteraction((Npc) entity);
            }
            if (entity instanceof Teleporter) {
                triggerTeleportEvent((Teleporter) entity);
            }
        } else {
            player.moveBy(mx, my);
        }
        if (handleEvent != null) {
            handleEvent.handle(player,playerAi);
        } else {
            handleEvent = map.update();
        }

        return this;
    }


    private void triggerTeleportEvent(Teleporter teleporter) {
        map = teleporter.getEvent().teleport((Player) player, playerAi);
    }


    public void playerInteraction(Npc npc) {
        for (Event eachEvent : npc.getEvents()) {
            if (eachEvent.getName().equals("onInteract")) {
                onInteract event = (onInteract) eachEvent;
                event.handle(player, playerAi);
                break;
            }
        }
    }

}
