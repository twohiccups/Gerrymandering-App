/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import algorithm.MovesShort;
import java.util.ArrayList;


 
public class UpdateObject {
    boolean isDone;
    boolean movesReady;
    ArrayList<MovesShort> moves;
    double currOF;

    public UpdateObject(boolean isDone, boolean movesReady, ArrayList<MovesShort> moves) {
        this.isDone = isDone;
        this.movesReady = movesReady;
        this.moves = moves;
    }
    
    
}
