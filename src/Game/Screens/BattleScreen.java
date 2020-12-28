package Game.Screens;

import Game.Ententies.NPCs.Npc;
import Game.Ententies.PC.Player;
import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;

public class BattleScreen implements Screen {
    public BattleScreen(Npc npc, Player playerAi) {

    }

    @Override
    public void displayOutput(AsciiPanel terminal) {

    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        return null;
    }
}
