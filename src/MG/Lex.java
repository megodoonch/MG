/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MG;

import java.util.ArrayList;
import java.util.List;

/**
 * Class of lexical items
 * @author meaghanfowlie
 */
public class Lex {
    private String string;
    private FeatureList features;
    
    /**
     * Class constructor
     * @param string the word itself
     * @param features the features that come with it
     */
    public Lex(String string, FeatureList features) {
        this.string = string;
        this.features = features;
     
    }
    
    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

   
    /**
     * Make a copy
     * @return a deep copy of the LI
     */
    public Lex copy() {
        
        return new Lex(this.string , this.features.copy());
    }
    
    public FeatureList getFeatures() {
        return features;
    }
    
    /**
     * Delete the head feature
     */
    public void check() {        
        this.features.getFeatures().remove(0);
    }
 
    /**
     * Add <code>string</code> to the string of the LI
     * @param string the string we're adding
     * @param left if <code>true</code>, add <code>string</code> on the left of the LI's string, otherwise on the right
     */
    public void combine(String string, boolean left) {
        if (left) {
            this.string = string + " " + this.string; // combine left (spec)
        } else {
            this.string = this.string + " " + string; // combine right (comp)
        }
    }

    
    
    
    
    @Override
    public String toString() {
        return string+"::"+features;
    }
    
    
}
