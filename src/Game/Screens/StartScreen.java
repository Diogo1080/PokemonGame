package Game.Screens;

import Game.Constants;
import Game.GameSaveDeleter;
import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class StartScreen implements Screen {
    private int selected = 0;
    private int selectedSave = 0;
    private String choosenName = "";
    private boolean chooseName = false;
    private boolean choosenNameExists = false;
    private boolean chooseSave = false;
    private boolean deleteSave = false;
    private final List<String> listOfSaves = getListOfSaves();

    @Override
    public void displayOutput(AsciiPanel terminal) {
        if (chooseName) {
            terminal.write("Enter to complete, BackSpace to delete, Escape to cancel action.", 8, 0);
            terminal.writeCenter("Enter your name:", 5);
            terminal.writeCenter(choosenName, 7);
            if (choosenNameExists){
                terminal.writeCenter(choosenName +" exists already",20);
                choosenNameExists=false;
            }
        } else if (chooseSave) {
            if (deleteSave) {
                String name = listOfSaves.get(selectedSave);
                terminal.writeCenter("Are you sure you want to delete " + name + "? (Y/N)",10);
            } else {
                terminal.write("Enter to select save, BackSpace to delete save, Escape to cancel action.", 2, 0);
                for (int i = 0; i < listOfSaves.size(); i++) {
                    if (selectedSave == i) {
                        terminal.writeCenter(">" + listOfSaves.get(i), i + 5);
                    }
                }
            }
        } else {
            switch (selected) {
                case 0 -> {
                    terminal.writeCenter("> New Game", 5);
                    terminal.writeCenter("  Load Game", 6);
                    terminal.writeCenter("  Exit", 7);
                }
                case 1 -> {
                    terminal.writeCenter("  New Game", 5);
                    terminal.writeCenter("> Load Game", 6);
                    terminal.writeCenter("  Exit", 7);
                }
                case 2 -> {
                    terminal.writeCenter("  New Game", 5);
                    terminal.writeCenter("  Load Game", 6);
                    terminal.writeCenter("> Exit", 7);
                }
            }
        }
    }

    private List<String> getListOfSaves() {
        File f = new File(Constants.BASESAVEPATH);
        File[] files = f.listFiles();
        List<String> nameOfDirectories = new LinkedList<>();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (file.isDirectory()) {
                    nameOfDirectories.add(file.getName());
                }
            }
        }
        return nameOfDirectories;
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        if (chooseName) {
            if (isAlfaNumeric(key)) {
                if (!(choosenName.length() >= 10)) {
                    choosenName = choosenName.concat(String.valueOf(key.getKeyChar()));
                    return this;
                }
            }
            switch (key.getKeyCode()) {
                case KeyEvent.VK_ENTER -> {
                    if (new File(Constants.BASESAVEPATH.concat("/").concat(choosenName)).exists()){
                        choosenNameExists=true;
                    }else{
                        return new PlayScreen(choosenName);
                    }
                }
                case KeyEvent.VK_ESCAPE -> chooseName = false;
                case KeyEvent.VK_BACK_SPACE -> {
                    if (!(choosenName.length() == 0)) {
                        choosenName = choosenName.substring(0, choosenName.length() - 1);
                    }
                }
            }
        } else if (chooseSave) {
            if (deleteSave) {
                switch (key.getKeyCode()) {
                    case KeyEvent.VK_N -> deleteSave=false;
                    case KeyEvent.VK_Y -> GameSaveDeleter.doIt(listOfSaves.get(selectedSave));
                }
            } else {
                switch (key.getKeyCode()) {
                    case KeyEvent.VK_W -> {
                        if (!(selectedSave - 1 < 0)) {
                            selectedSave--;
                        }
                    }
                    case KeyEvent.VK_S -> {
                        if (!(selectedSave + 1 > listOfSaves.size() - 1)) {
                            selectedSave++;
                        }
                    }
                    case KeyEvent.VK_ESCAPE -> chooseSave = false;
                    case KeyEvent.VK_BACK_SPACE -> deleteSave = true;
                    case KeyEvent.VK_ENTER -> {
                        return new PlayScreen(listOfSaves.get(selectedSave), Constants.BASESAVEPATH.concat("/").concat(listOfSaves.get(selectedSave)));
                    }
                }
            }
        } else {
            switch (key.getKeyCode()) {
                case KeyEvent.VK_W -> {
                    if (!(selected - 1 < 0)) {
                        selected--;
                    }
                }
                case KeyEvent.VK_S -> {
                    if (!(selected + 1 > 2)) {
                        selected++;
                    }
                }
                case KeyEvent.VK_ENTER -> {
                    if (selected == 0) {
                        chooseName = true;
                    } else if (selected == 1) {
                        if (!(listOfSaves.isEmpty())) {
                            chooseSave = true;
                        }
                    } else {
                        System.exit(1);
                    }
                }
            }
        }
        return this;
    }

    private boolean isAlfaNumeric(KeyEvent key) {
        if ((key.getKeyCode() >= 48 && key.getKeyCode() <= 57) ||
                (key.getKeyCode() >= 65 && key.getKeyCode() <= 90) ||
                (key.getKeyCode() >= 96 && key.getKeyCode() <= 105) ||
                (key.getKeyCode() == 32)) {
            return true;
        }
        return false;
    }
}
