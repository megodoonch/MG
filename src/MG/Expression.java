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
        List<Feature> fs = new ArrayList();
        li.getFeatures().stream().forEach((f) -> { // this is what netbeans suggested in place of a for loop
            fs.add(f);
        });
        Lex copy = new Lex(li.getString(),fs);
        expression[0] = copy;        
        
    }
    
    public void store(Lex li, int i) { // store in position i if it's empty
        if (this.expression[i] == null) {
            this.expression[i] = li;
        } else {
            System.out.println("SMC violation: feature slot " + i + " is already filled");
            
        }
    }
    
    public void store(Lex li) {
        int i = li.getFeatures().get(0).getNumber();
        this.store(li,i);
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

    public boolean isComplete(MG g) {
        if (this.head().getFeatures().size()==1  // only one feature left
                && g.getFinals().contains(this.headFeature().getValue())) {  // it's a final feature
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

    public boolean isValid() throws MGException {
        if (this.valid) {
            return this.valid;
        } else {
            throw new MGException("Invalid derivation step");
        }
    }

    public void setValid(boolean valid) {
        this.valid = valid;
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
