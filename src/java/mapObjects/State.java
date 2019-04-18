package mapObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import algorithm.Move;
import dataTypes.Representative;
import dataTypes.StateName;

public class State {

	private StateName name;
	private ArrayList<District> districts;
	public District unassigned;
	private Set<Representative> representatives;
	private int numPrecincts;
	private int numDistricts;

    public State() {
    }
        
        

	public State(StateName name, Map<Integer, Precinct> precincts, int numDistricts) {
		this.name = name;
		this.numDistricts = numDistricts;
		this.numPrecincts = precincts.size();
		this.unassigned = new District();
		this.unassigned.setPrecincts(precincts);
	}
	
	public void reset() {
		Map<Integer, Precinct> temp = new HashMap<Integer, Precinct>();
		temp.putAll(this.unassigned.getPrecincts());
		for (District d : this.districts) {
			Iterator<Entry<Integer, Precinct>> it = d.getPrecincts().entrySet().iterator();
			while (it.hasNext()) it.next().getValue().setDistrictID(0);
			temp.putAll(d.getPrecincts());
		}
		this.districts = null;
		this.unassigned = new District();
		this.unassigned.setPrecincts(temp);
	}

	public boolean makeMove(Move move) {
		return true;
	}

	public double compareToState(State otherState) {
		return 0;
	}

	public int getNumPrecincts() {
		return this.numPrecincts;
	}

    public void setNumPrecincts(int numPrecincts) {
        this.numPrecincts = numPrecincts;
    }
        
	
	public StateName getName() {
		return this.name;
	}

	public boolean isAdjacenct(Precinct p1, Precinct p2) {
		if (p1.getNeighbors().contains(p2))
			return true;
		return false;
	}

	public District getUnassignedDistrict() {
		return this.unassigned;
	}

	public Set<Representative> getRepresentatives() {
		return representatives;
	}

	public int getNumDistricts() {
		return numDistricts;
	}

	public void addDistrict(District district) {
		if (this.districts == null) {
			this.districts = new ArrayList<District>();
		}
		this.districts.add(district);
		district.setID(this.districts.size());
	}

	public District getRandomDistrict() {
		return this.districts.get(ThreadLocalRandom.current().nextInt(this.getNumDistricts()));
	}

	public District getDistrict(int distID) {
		if (distID == 0) {
			return this.getUnassignedDistrict();
		}
		for (District d : districts) {
			if (distID == d.getID()) {
				return d;
			}
		}
		return null;
	}

    public void setName(StateName name) {
        this.name = name;
    }
        
        
	
	public ArrayList<District> getDistricts() {
		return this.districts;
	}

    public void setNumDistricts(int numDistricts) {
        this.numDistricts = numDistricts;
    }

    public void setUnassigned(District unassigned) {
        this.unassigned = unassigned;
    }
    
    
    
    @Override
    public String toString() {
    	return "Name: " + this.name + " Num precincts: " 
    			+ this.numPrecincts + " Num districts: " 
    			+ this.numDistricts + "\n    Unassigned: " 
    			+ this.unassigned.toString();
    }

    public void setDistricts(ArrayList<District> districts) {
        this.districts = districts;
    }
    
    
}
