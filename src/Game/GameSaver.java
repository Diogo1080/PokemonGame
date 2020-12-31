package Game;

import Game.Ententies.PC.Player;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class GameSaver {
    public static void saveGame(Player player) {
        File theDir = new File(Constants.BASESAVEPATH.concat("/").concat(player.getName()));
        if (!theDir.exists()) {
            theDir.mkdir();
            System.out.println(theDir.toString());
            savePlayer(player,theDir.toString());
            try {
                copyToMapsFolder(Path.of(Constants.BASEINITPATH), Path.of(theDir.toString().concat("/Maps")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveGame(String playerName) {
        File theDir = new File(Constants.BASESAVEPATH.concat("/").concat(playerName));
        if (!theDir.exists()) {
            theDir.mkdir();
            System.out.println(theDir.toString());
            try {
                copyToMapsFolder(Path.of(Constants.BASEINITPATH), Path.of(theDir.toString().concat("/Maps")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void savePlayer(Player player, String theDir) {
        JSONObject Player = new JSONObject();
        Player.put("name",player.getName());
        Player.put("currentMap",player.map.getPath());
        Player.put("X",player.x);
        Player.put("Y",player.y);

        //Add list: JSONArray list = new JSONArray();

        //Write JSON file
        try (FileWriter file = new FileWriter(theDir.concat("/Player.json"))) {

            file.write(Player.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void copyToMapsFolder(Path src, Path dest) throws IOException {
        try (Stream<Path> stream = Files.walk(src)) {
            stream.forEach(source -> copy(source, dest.resolve(src.relativize(source))));
        }
    }

    private static void copy(Path source, Path dest) {
        try {
            Files.copy(source, dest, REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
