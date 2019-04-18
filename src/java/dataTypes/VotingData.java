package dataTypes;
import java.util.Map;

public class VotingData {  
    private int year;
    private Map<Integer, Double> data;
    
    public Map<Integer, Double> getResults() {
    	return this.data;
    }
    
    public double getPartResults(Party party) {
    	if (party == Party.DEMOCRATIC) {
    		return data.get(0);
    	}
    	else {
    		return data.get(1);
    	}
    }
}
