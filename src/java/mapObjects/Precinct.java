package mapObjects;

import java.util.ArrayList;

import dataTypes.VotingData;

public class Precinct implements Comparable{

    private int ID;
    private int districtID;
    private int population;
    private double area;
    private ArrayList<Precinct> neighbors;
    private VotingData votingData;
    private ArrayList<Point> points;
    private boolean isBorder;
    
    public void setPoints(ArrayList<Point> points) {
    	this.points = points;
    }
    
    public ArrayList<Point> getPoints() {
    	return this.points;
    }

    public ArrayList<Precinct> getNeighbors() {
        return neighbors;
    }

    public int getDistrictID() {
        return districtID;
    }

    public void setDistrictID(int districtID) {
        this.districtID = districtID;
    }

    public boolean isBorder() {
        return isBorder;
    }

    public VotingData getVotingData() {
        return votingData;
    }

    public double getArea() {
        return area;
    }

    public int getPopulation() {
        return population;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setNeighbors(ArrayList<Precinct> neighbors) {
        this.neighbors = neighbors;
    }

    @Override
    public int hashCode() {
        return ID * (int) area;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Precinct) {
            if (this.ID == ((Precinct) o).ID && this.area == ((Precinct) o).area) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return ID + " " + districtID + " "  + population + " " + area + " "  + neighbors + " " + votingData;
        
    }

    @Override
    public int compareTo(Object o) {
        if (o == this) return 0;
        if (o instanceof Precinct) {
            Precinct p = (Precinct) o;
            if (this.population > p.population) {
                return 1;
            }
        }
        return -1;
    }
    
    
    
}
