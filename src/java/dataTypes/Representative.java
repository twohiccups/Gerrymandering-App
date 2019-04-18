package dataTypes;
import mapObjects.Precinct;

public class Representative {
    private String name;
    private Party party;
    private Precinct homePrecinct;
    
    public String getName() {
        return name;
    }

    public Party getParty() {
        return party;
    }

    public Precinct getHomePrecinct() {
        return homePrecinct;
    }    
}
