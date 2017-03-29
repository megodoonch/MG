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
public class Expression  {
    
    public Lex[] expression;
    public boolean valid;
    
    public Expression(Lex li, MG g) {
        int length = g.licSize() + 1;
        expression = new Lex[length];
        //make a copy so yoou don't mess with the lexicon
//        List<Feature> fs = new ArrayList<>();
//        li.getFeatures().stream().forEach((f) -> { // this is what netbeans suggested in place of a for loop
//            fs.add(f);
//        });
//        Lex copy = new Lex(li.getString(),fs);
        expression[0] = li.copy();        
        
    }
    
    public boolean store(Lex li, int i) { // store in position i if it's empty
        if (this.expression[i] == null) {
            this.expression[i] = li;
            return true;
        } else {
            System.out.println("SMC violation: feature slot " + i + " is already filled");
            return false;
            
        }
    }
    
    public boolean store(Lex li) {
        int i = li.getFeatures().get(0).getNumber();
        return this.store(li,i);
    }

    public Lex[] getExpression() {
        return expression;
    }
    
    public Feature headFeature() {
        return this.expression[0].getFeatures().get(0);
    }
    
    public Lex head() {
        return this.expression[0];
    }
    
    public void combineMovers(Expression expr2, int n) {
        int i = 1; // start at index 1, first mover
        while (i < n) {
            Lex mover = expr2.getExpression()[i] ;
            // store mover i
            if (mover != null) { 
                this.store(mover, i);
            }
            i++;
        } 
    }

    public Expression copy(MG g) {
        Expression newExpr = new Expression(this.head().copy(),g);
        int i=1;
        while (i<g.licSize()+1 ) {
            if (this.expression[i] != null) {
                newExpr.expression[i] = this.expression[i].copy(); 
            }
            i++;
        }
        return newExpr;
    }
    
    public boolean isComplete(MG g, ArrayList<String> cats) {
        Feature h = this.headFeature();
        if (this.head().getFeatures().size()==1  // only one feature left
                && h.getIntValue() ==-1 // it's negative
                && h.selectional(g) // it's selectional
                && cats.contains(this.headFeature().getValue())) {  // it's a feature in the given set
            // make sure the movers are null
            int i = 1;
            while (i<g.licSize()+1) {
                if (this.getExpression()[i] != null) {
                    return false;
                }
                i++;
            }
            return true;
        }
        return false;
              
    }

    public boolean isComplete(MG g, boolean fin) {
        if (fin) { // finals
            return this.isComplete(g,g.getFinals());
        } else { // any category
            return this.isComplete(g,g.getBareSelFeatures());
        }
    }
    
    
    public boolean isComplete(MG g) {
        // default only returns true if the category is final
        return this.isComplete(g, true);
    }
    
    

    public void setValid(boolean valid) {
        this.valid = valid;
    }
    
    public String spellout(MG g) {
        if (this.isComplete(g,false)) { // spells out any complete phrase regardless of category
            return this.head().getString().replaceAll("\\s+"," "); // remove extra spaces
        } else {
            System.out.println("Spellout error: you can't spell out an incomplete phrase");
            return null;
        }
//        try {
//            !this.isComplete(g,false); // spells out any complete phrase regardless of category
//            throw new MGException("Spellout error: you can't spell out an incomplete phrase");
//            
//
//        } catch (MGException error) {
//            System.out.println(error.getMessage());
//            return this.head().getString();
//
//        }
        
    }

    @Override
    public String toString() {
        String string = "";
        for (Lex li : this.expression) {
            string += li + "  ";
        }
        
        return string;
    }
    
    
    
}
