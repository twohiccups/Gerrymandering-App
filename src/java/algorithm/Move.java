package algorithm;

import mapObjects.District;
import mapObjects.Precinct;

public class Move {

	private Precinct precinct;
	private District sourceDistrict;
	private District destinationDistrict;
	private boolean isFinalized;

	public Move(Precinct precinct, District sourceDistrict, District destinationDistrict) {
		this.precinct = precinct;
		this.sourceDistrict = sourceDistrict;
		this.destinationDistrict = destinationDistrict;
		this.isFinalized = false;
	}

	public Precinct getPrecinct() {
		return precinct;
	}

	public District getSourceDistrict() {
		return sourceDistrict;
	}

	public District getDestinationDistrict() {
		return destinationDistrict;
	}

	public void setPrecinct(Precinct precinct) {
		this.precinct = precinct;
	}

	public void setSourceDistrict(District sourceDistrict) {
		this.sourceDistrict = sourceDistrict;
	}

	public void setDestinationDistrict(District destinationDistrict) {
		this.destinationDistrict = destinationDistrict;
	}

	public void setIsFinalized(boolean isFinalized) {
		this.isFinalized = isFinalized;
	}

	public boolean getIsFinalized() {
		return this.isFinalized;
	}

	@Override
	public String toString() {
		return "precinctID: " + precinct.getID() + " Source: " + sourceDistrict.getID() + " Destination: "
				+ destinationDistrict.getID();
	}

	public Object convertToJson() {
		Object a = new Object() {
			int precinct = this.precinct;
			int sourceDistrict = this.sourceDistrict;
			int destinationDistrict = this.precinct;
		};
		return a;
	}

}
