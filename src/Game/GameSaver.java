package Game;

import Game.Ententies.Events.onPlayerPosition;
import Game.Ententies.NPCs.Npc;
import Game.Ententies.PC.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
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
            savePlayer(player, theDir.toString());
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
        Player.put("name", player.getName());
        Player.put("currentMap", player.map.getPath());
        Player.put("X", player.x);
        Player.put("Y", player.y);

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

    public static void saveOnPlayerPositionEvent(onPlayerPosition onPlayerPosition, Player player, Npc npc, Map map) {
        try {
            // read the json file
            FileReader fileReader = new FileReader(map.getPath());
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);

            JSONArray entities = (JSONArray) jsonObject.get("entities");
            for (Object o : entities) {
                JSONObject entity = (JSONObject) o;
                if (npc.getName().equals(entity.get("name"))) {
                    JSONArray events = (JSONArray) entity.get("Events");
                    for (Object value : events) {
                        JSONObject event = (JSONObject) value;
                        if (onPlayerPosition.getName().equals(event.get("name"))) {
                            event.remove("done");
                            event.put("done", onPlayerPosition.getDone());
                        }
                    }
                }
            }
            fileReader.close();
            FileWriter fileWriter = new FileWriter(map.getPath());
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.flush();

            fileWriter.close();

            savePlayer(player,Constants.BASESAVEPATH.concat("/").concat(player.getName()).concat("/"));
        } catch (IOException | ParseException | NullPointerException ex) {
            ex.printStackTrace();
        }
    }
}
