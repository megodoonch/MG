/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MG;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author meaghanfowlie
 */
public class Lex {
    private String string;
    private FeatureList features;
    
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

   
    
    public Lex copy() {
        
        return new Lex(this.string , this.features.copy());
    }
    
    public FeatureList getFeatures() {
        return features;
    }
    
    public void check() {        
        this.features.getFeatures().remove(0);
    }
 
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
