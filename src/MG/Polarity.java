/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MG;

/**
 *
 * @author meaghanfowlie
 */
public class Polarity {
    private String value; // +,-,= etc
    private String set;  // lic, sel
    private int intValue; // +1,-1 so that all negative licensing features have the same value
    private boolean combine; // if true, combine string here even even though you're moving
    private boolean store; // if true, store string, not just features, in mover list
    private String moveType;
    
    public Polarity(String value, String set, int intValue, boolean combine, boolean store, String moveType) {
        this.value = value;
        this.set = set;
        this.intValue = intValue;
        this.combine = combine;
        this.store = store;
        this.moveType = moveType;
    }
    
    // default move is -combine +store
    public Polarity(String value, String set, int intValue) {
        this(value,set,intValue,false,true,"");
    }
    
    public String getValue() {
        return value;
    }

    public String getSet() {
        return set;
    }

    
    public int getIntValue() {
        return intValue;
    }

    public boolean isCombine() {
        return combine;
    }

    public boolean isStore() {
        return store;
    }

    public String getMoveType() {
        return moveType;
    }

    
    
    @Override
    public String toString() {
        return value;
    }
    
    public String toString(Boolean verbose) {
        if (verbose) {
            return "value: " + value + ",set: " + set + ", integer value: " + intValue;
        } else {return value;}
    }
}
