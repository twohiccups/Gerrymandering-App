/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;



/**
 *
 * @author spitlord
 */
public class MovesShort {
    public int precinctID;
    public int sourceDistrictID;
    public int destinationDistrictID;

    public MovesShort(int precinctID, int sourceDistrictID, int destinationDistrictID) {
        this.precinctID = precinctID;
        this.sourceDistrictID = sourceDistrictID;
        this.destinationDistrictID = destinationDistrictID;
    }
    
    public static MovesShort toShort(Move m) {
        return new MovesShort(
                m.getPrecinct().getID(),
                m.getSourceDistrict().getID(),
                m.getDestinationDistrict().getID()
        );
    }
    
    
    
    
}
