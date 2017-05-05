/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MG;

import java.util.ArrayList;

/**
 *
 * @author meaghanfowlie
 */
public class Category {
    private ArrayList<String> adjuncts;
    private String name;
    
    public Category(String name, ArrayList<String> adjuncts) {
        this.name = name;
        this.adjuncts = adjuncts;
        
    }

    public ArrayList<String> getAdjuncts() {
        return adjuncts;
    }

    public String getName() {
        return name;
    }
    
    public void addAdjunct(String adjunct) {
        this.adjuncts.add(adjunct);
    }
    
    public boolean hasAdjunct(String cat) {
        return this.adjuncts.contains(cat);
    }
    
}
