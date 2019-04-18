package manager;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import dataTypes.StateName;
import mapObjects.District;
import mapObjects.State;

public class StateManager {

    private static StateManager instance = null;
    private Map<StateName, State> loadedStates;

    protected StateManager() {
        loadedStates = new HashMap<StateName, State>();
        //Oregon
        State s = Preprocessing.initializeStateFromFile(StateName.OREGON,
                "/Users/spitlord/PycharmProjects/electionPreprocessing/seRetarded/oregonSerialized.json",
                "/Users/spitlord/PycharmProjects/electionPreprocessing/goodAdjacencies/OregonAdjacency.json");
        loadedStates.put(StateName.OREGON, s);

        //Massachusetts
        s = Preprocessing.initializeStateFromFile(StateName.MASSACHUSETTS,
                "/Users/spitlord/PycharmProjects/electionPreprocessing/seRetarded/massachusettsSerialized.json",
                "/Users/spitlord/PycharmProjects/electionPreprocessing/goodAdjacencies/MassachusettsAdjacency.json");
        loadedStates.put(StateName.MASSACHUSETTS, s);
    }

    public static StateManager getInstance() {
        if (instance == null) {
            instance = new StateManager();
        }
        return instance;
    }

    public Map<StateName, State> getStates() {
        return loadedStates;
    }
    

    public static State getState(StateName name) {
//        if (loadedStates.containsKey(name)) {
//            resetState(loadedStates.get(name));
//            return loadedStates.get(name);
//        }
//        return this.initializeState(name);

        State s = Preprocessing.initializeStateFromFile(StateName.OREGON,
                "/Users/spitlord/PycharmProjects/electionPreprocessing/seRetarded/oregonSerialized.json",
                "/Users/spitlord/PycharmProjects/electionPreprocessing/goodAdjacencies/OregonAdjacency.json");
        return s;
    }

    private State initializeState(StateName name) {
        EntityManager em = MapEntityManagerFactory.getInstance().getEntityManagerFactory().createEntityManager();
        State s = em.find(State.class, name);
        em.close();
        return s;
    }

}
