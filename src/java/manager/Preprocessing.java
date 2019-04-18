
package manager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dataTypes.StateName;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import mapObjects.Precinct;
import mapObjects.State;

public class Preprocessing {

      public static State initializeStateFromFile(StateName name, String serializationFile, String adjacencyFile) {
        Gson j = new Gson();
        String string;
        try {
            string = new String(Files.readAllBytes(Paths.get(serializationFile)));
            State state = j.fromJson(string, State.class);
            initializePrecinctNeighbors(adjacencyFile, state);
            state.setName(name);
            state.setNumPrecincts(state.getUnassignedDistrict().getPrecincts().size());
            return state;
        } catch (IOException ex) {
        }

        return null;
    }

    public static void initializePrecinctNeighbors(String adjacencyFile, State s) throws IOException {
        Gson j = new Gson();
        String string = new String(Files.readAllBytes(Paths.get(adjacencyFile)));
        Type type = new TypeToken<Map<Integer, ArrayList<Integer>>>() {
        }.getType();
        Map<Integer, ArrayList<Integer>> neighborsMap = j.fromJson(string, type);

        neighborsMap.forEach((key, list) -> {
            Precinct p = s.getUnassignedDistrict().getPrecinctById(key);
            p.setNeighbors(new ArrayList<>());
            list.forEach(neighborID -> {
                p.getNeighbors().add(s.getUnassignedDistrict().getPrecinctById(neighborID));
            });
        });
    }

}
