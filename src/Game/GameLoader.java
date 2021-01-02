package Game;

import Game.Ententies.EntitiesFactory;
import Game.Ententies.Entity;
import Game.Ententies.Events.*;
import Game.Ententies.NPCs.Npc;
import Game.Ententies.NPCs.NpcKinds.People;
import Game.Ententies.NPCs.NpcKinds.RewardPokeball;
import Game.Ententies.NPCs.NpcKinds.Teleporter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class GameLoader {
    private static String name;
    private static int width;
    private static int height;
    private static Tile[][] tiles;

    public GameLoader() {
    }

    public static Map build(String filename) {
        return new Map(tiles, name, filename);
    }

    public static Map LoadMap(String filename) {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(filename)) {
            //Read JSON file
            JSONObject mapinfo = (JSONObject) jsonParser.parse(reader);
            name = (String) mapinfo.get("name");
            width = Math.toIntExact((Long) mapinfo.get("width"));
            height = Math.toIntExact((Long) mapinfo.get("height"));
            JSONArray chars = (JSONArray) mapinfo.get("mapTiles");

            tiles = new Tile[width][height];

            for (int x = 0; x < height; x++) {
                for (int y = 0; y < width; y++) {
                    tiles[y][x] = Tile.getTileByGlyph(chars.get(x).toString().charAt(y));
                }
            }
            return build(filename);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<Event> loadEventsFromEntity(JSONArray entityEvents, Npc npc) {
        List<Event> eventsReturned = new LinkedList<>();
        Event parentEvent = null;
        for (Object event : entityEvents) {
            JSONObject eventInfo = (JSONObject) event;

            JSONArray talk = (JSONArray) eventInfo.get("talks");
            List<String> talks = new LinkedList<>();

            for (int i = 0; i < talk.size(); i++) {
                talks.add((String) talk.get(i));
            }

            if ("onInteract".equals(eventInfo.get("event"))) {
                eventsReturned.add(
                        new onInteract(
                                (String) eventInfo.get("name"),
                                talks,
                                parentEvent,
                                (Boolean) eventInfo.get("repeat"),
                                (Boolean) eventInfo.get("done"),
                                npc
                        )
                );
            }
            if ("onTeleport".equals(eventInfo.get("event"))) {
                JSONObject positionToTeleport = (JSONObject) eventInfo.get("positionToTeleport");

                int y = Math.toIntExact((Long) positionToTeleport.get("Y"));
                int x = Math.toIntExact((Long) positionToTeleport.get("X"));

                eventsReturned.add(
                        new onTeleport(
                                (String) eventInfo.get("name"),
                                talks,
                                parentEvent,
                                (Boolean) eventInfo.get("repeat"),
                                (Boolean) eventInfo.get("done"),
                                (String) eventInfo.get("mapToTeleport"),
                                x,
                                y,
                                npc
                        )
                );
            }
            if ("onPlayerPosition".equals(eventInfo.get("event"))) {
                List<Integer> y = new LinkedList<>(), x = new LinkedList<>(), yFinal = new LinkedList<>(), xFinal = new LinkedList<>();
                JSONArray positionsToLook = (JSONArray) eventInfo.get("positionsToLook");
                for (int i = 0; i < positionsToLook.size(); i++) {
                    JSONObject info = (JSONObject) positionsToLook.get(i);
                    y.add(Math.toIntExact((Long) info.get("Y")));
                    x.add(Math.toIntExact((Long) info.get("X")));
                    if ((boolean) eventInfo.get("range")) {
                        yFinal.add(Math.toIntExact((Long) info.get("YFinal")));
                        xFinal.add(Math.toIntExact((Long) info.get("XFinal")));
                    }
                }
                eventsReturned.add(
                        new onPlayerPosition(
                                (String) eventInfo.get("name"),
                                talks,
                                parentEvent,
                                npc,
                                (Boolean) eventInfo.get("movePlayerToNpc"),
                                (Boolean) eventInfo.get("playerHasPokemon"),
                                (Boolean) eventInfo.get("range"),
                                (Boolean) eventInfo.get("repeat"),
                                (Boolean) eventInfo.get("moveToPlayer"),
                                (Boolean) eventInfo.get("done"),
                                x,
                                y,
                                xFinal,
                                yFinal
                        )
                );
            }
            if ("rewardPlayer".equals(eventInfo.get("event"))) {
                List<String> choicesList = new LinkedList<>();
                JSONObject choice = (JSONObject) eventInfo.get("choices");
                JSONArray choices = (JSONArray) choice.get("names");
                for (int i = 0; i < choices.size(); i++) {
                    choicesList.add((String) choices.get(i));
                }
                eventsReturned.add(
                        new rewardPlayer(
                                (String) eventInfo.get("name"),
                                talks,
                                parentEvent,
                                (Boolean) eventInfo.get("repeat"),
                                (Boolean) eventInfo.get("done"),
                                npc,
                                choicesList,
                                (String) choice.get("type")
                        )
                );
            }
            if (eventInfo.get("parent") != null) {
                parentEvent = eventsReturned.get(eventsReturned.size() - 1);
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
                            Math.toIntExact((Long) entityInfo.get("Y"))
                    );
                    person.setEvents(loadEventsFromEntity((JSONArray) entityInfo.get("Events"), person));

                    map.getEntityList().add(person);
                }
                if ("Teleporter".equals(entityInfo.get("type"))) {
                    Teleporter teleporter = (Teleporter) entitiesFactory.newTeleporter(
                            (String) entityInfo.get("name"),
                            Math.toIntExact((Long) entityInfo.get("X")),
                            Math.toIntExact((Long) entityInfo.get("Y"))
                    );
                    teleporter.setEvents(loadEventsFromEntity((JSONArray) entityInfo.get("Events"), teleporter));
                    map.getEntityList().add(teleporter);
                }
                if ("rewardPokeball".equals(entityInfo.get("type"))) {
                    RewardPokeball rewardPokeball = (RewardPokeball) entitiesFactory.newRewardPokeball(
                            (String) entityInfo.get("name"),
                            Math.toIntExact((Long) entityInfo.get("X")),
                            Math.toIntExact((Long) entityInfo.get("Y"))
                    );
                    rewardPokeball.setEvents(loadEventsFromEntity((JSONArray) entityInfo.get("Events"), rewardPokeball));
                    map.getEntityList().add(rewardPokeball);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static Entity LoadPlayer(String filename) {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(filename)) {
            //Read JSON file
            JSONObject playerinfo = (JSONObject) jsonParser.parse(reader);
            EntitiesFactory entitiesFactory = new EntitiesFactory(LoadMap((String) playerinfo.get("currentMap")));
            return entitiesFactory.newPlayer(
                    (String) playerinfo.get("name"),
                    Math.toIntExact((long) playerinfo.get("X")),
                    Math.toIntExact((long) playerinfo.get("Y"))
            );

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
