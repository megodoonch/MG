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
 * Stores information about a category, primarily what it is an adjunct of and what side of the head it adjoins on
 */
public class Category {
    private ArrayList<String> adjunctOf;
    private String name;
    private boolean left; // adjoins to the left
    
    public Category(String name, ArrayList<String> adjunctOf, Boolean left) {
        this.name = name;
        this.adjunctOf = adjunctOf;
        this.left = left;
        
    }

    public Category(String name,ArrayList<String> adjunctOf) {
        this.adjunctOf = adjunctOf;
        this.name = name;
        this.left = true;
    }
    
    public Category(String name) {
        this.adjunctOf = new ArrayList<>();
        this.name = name;
        this.left = true;
    }
    
    
    public String getName() {
        return name;
    }

    public ArrayList<String> getAdjunctOf() {
        return adjunctOf;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }
    
    
    
    public void addAdjunct(String adjunct) {
        this.adjunctOf.add(adjunct);
    }
    
    public boolean isAdjunctOf(String cat) {
        return this.adjunctOf.contains(cat);
    }

    @Override
    public String toString() {
        String side;
        if (this.left) {
            side = "left";
        } else {
            side = "right";
        }       
        return name + ", " + side + " adjunct of " + adjunctOf;
    }
    
    
    
}
