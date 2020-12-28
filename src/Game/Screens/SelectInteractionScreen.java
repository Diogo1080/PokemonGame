package Game.Screens;

import Game.Ententies.NPCs.Npc;
import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;
import java.util.List;

public class SelectInteractionScreen implements Screen {
    List<Npc> entitiesAroundPlayer;
    Screen previousScreen;
    int selected = 0;

    public SelectInteractionScreen(List<Npc> entitiesAroundPlayer, Screen previousScreen) {
        this.entitiesAroundPlayer = entitiesAroundPlayer;
        this.previousScreen = previousScreen;
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        for (int i = 0; i < entitiesAroundPlayer.size() + 1; i++) {
            if (i == entitiesAroundPlayer.size()) {
                if (selected==i){
                    terminal.write("-> Exit",0,i);
                }else{
                    terminal.write("Exit",0,i);
                }
            } else if (selected == i) {
                terminal.write("-> " + entitiesAroundPlayer.get(i).getName(), 0, i);
            } else {
                terminal.write(entitiesAroundPlayer.get(i).getName(), 0, i);
            }
        }
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_W -> {
                if (!(selected - 1 < 0)) {
                    selected--;
                }
            }
            case KeyEvent.VK_S -> {
                if (!(selected == entitiesAroundPlayer.size() )) {
                    selected++;
                }
            }
            case KeyEvent.VK_E -> {
                
            }
        }
        return this;
    }
}
