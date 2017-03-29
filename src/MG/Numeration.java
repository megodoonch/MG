/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MG;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author meaghanfowlie
 */
public class Numeration {
    
    public List<Expression> numeration;
    private MG g; // the grammar
    
    
    public Numeration(MG g) {
        this.numeration = new ArrayList<>();
        this.g = g;
        
    }
    
    public void addExpression(Lex li) {
        // make li into an expression and add it to the numeration
        this.numeration.add(new Expression(li,this.g) );
    }
    
    public void printNumeration() {
        
        System.out.println("\n** Numeration **\n");
        int i=0;
        while (i<numeration.size()) {
            System.out.println(i + ". " + numeration.get(i));
            i++;
        }
    }
    
    public Expression merge(int i, int j, boolean print) {
        // merge expressions at index i and j 
        Expression expr1 = numeration.get(i);
        Expression expr2 = numeration.get(j);
        Expression merged = this.g.merge(expr1,expr2);
        if (merged != null) { 
            if (print) {
                System.out.println("\n--> Generated " + merged);
            }
            this.numeration.remove(expr1);
            this.numeration.remove(expr2);
            this.numeration.add(merged);
            
            
        }
        
        if (print) {
            printNumeration();
        }
        return merged;
        
    }

    public Expression merge(int i, int j) {
        return this.merge(i, j,false);
    }
    
    public Expression move(int i, boolean print) {
        Expression moved = this.g.move(numeration.get(i));
        if (moved !=null) { 
            if (print) {
                System.out.println("\n--> Generated " + moved);
            }
            this.numeration.remove(i);
            this.numeration.add(moved);
            
        }
        
        if (print) {
            printNumeration();
        }
        return moved;
        
    }
    
    public Expression move(int i) {
        return this.move(i,false);
    }
    
    
    public List<Expression> getNumeration() {
        return numeration;
    }

    public MG getG() {
        return g;
    }

    @Override
    public String toString() {
        return "Numeration{" + "numeration=" + numeration + ", g=" + g + '}';
    }
    
    
    
}
