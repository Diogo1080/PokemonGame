package Game.World;

import Game.Ententies.EntitiesFactory;
import Game.Ententies.NPCs.Events.Event;
import Game.Ententies.NPCs.Events.onInteract;
import Game.Ententies.NPCs.NpcKinds.People;
import Game.Tile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class GameLoader {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int width;

    private void setWidth(int width) {
        this.width = width;
    }

    private int height;

    private void setHeight(int height) {
        this.height = height;
    }

    private Tile[][] tiles;

    public GameLoader() {
    }

    public Map build() {
        return new Map(tiles);
    }

    public GameLoader LoadMap(String filename) {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(filename)) {
            //Read JSON file
            JSONObject mapinfo = (JSONObject) jsonParser.parse(reader);
            setName((String) mapinfo.get("name"));
            setWidth(Math.toIntExact((Long) mapinfo.get("width")));
            setHeight(Math.toIntExact((Long) mapinfo.get("height")));
            JSONArray chars = (JSONArray) mapinfo.get("mapTiles");

            tiles = new Tile[width][height];

            for (int x = 0; x < height; x++) {
                for (int y = 0; y < width; y++) {
                    tiles[y][x] = Tile.getTileByGlyph(chars.get(x).toString().charAt(y));
                }
            }
            return this;

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<Event> loadEventsFromEntity(JSONArray entityEvents) {
        List<Event> eventsReturned = new LinkedList<>();

        for (Object event : entityEvents) {
            JSONObject eventInfo = (JSONObject) event;
            if ("onInteract".equals(eventInfo.get("name"))) {
                JSONArray talk = (JSONArray) eventInfo.get("talks");
                List<String> talks = new LinkedList<>();

                for (int i = 0; i < talk.size(); i++) {
                    talks.add((String) talk.get(i));
                }

                eventsReturned.add(new onInteract((String) eventInfo.get("name"), talks, (Boolean) eventInfo.get("repeat")));
            }
        }
        return eventsReturned;
    }

    public static void LoadEntities(String filename, EntitiesFactory entitiesFactory, Map map) {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(filename)) {
            JSONObject mapinfo = (JSONObject) jsonParser.parse(reader);
            JSONArray entities = (JSONArray) mapinfo.get("entities");

            for (Object entity : entities) {
                JSONObject entityInfo = (JSONObject) entity;
                if ("People".equals(entityInfo.get("type"))) {
                    People person = (People) entitiesFactory.newPeople(
                            (String) entityInfo.get("name"),
                            Math.toIntExact((Long) entityInfo.get("X")),
                            Math.toIntExact((Long) entityInfo.get("Y")),
                            loadEventsFromEntity((JSONArray) entityInfo.get("Events"))
                    );
                    map.getEntityList().add(person);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
