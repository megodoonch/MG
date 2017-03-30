/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author meaghanfowlie
 */
public class FeatureList {
    // implements lists of features so we can do everything we want to them. Lists are "genereic" and this preculdes some operations, but embedding the list in an object makes them work.
    // Also, now we have a dedicated function for copying feature lists.

    
    ArrayList<Feature> features;
    
    public FeatureList() {
        features = new ArrayList<>();
        
    }
    
    public FeatureList(Feature[] featureArray) {
        
        this.features = new ArrayList<>(Arrays.asList(featureArray));
        
    }
    
    public FeatureList(List<Feature> featureList) {
        this.features = new ArrayList<>(featureList);
        
    }

    public ArrayList<Feature> getFeatures() {
        return features;
    }
    
    public void addFeature(Feature f) {
        this.features.add(f);
    }
    
    public FeatureList copy() {
    
        ArrayList<Feature> fs = new ArrayList<>();
        for (Feature f : this.features) {
            fs.add(f);
        }
        return new FeatureList(fs);

    }

    @Override
    public String toString() {
        return "" + this.features;
    }
    
    
}
