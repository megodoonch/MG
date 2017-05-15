/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MG;
import java.util.List;
import java.util.ArrayList;

/**
 * Tuples of lexical items. the length of the tuple is the number of licensing features plus 1. Then we have one spot for each potential mover, plus the main structure.
 * @author meaghanfowlie
 */
public class Expression  {
    
    public Lex[] expression;
    public boolean valid;
    
    /**
     * Class constructor.
     * Generates an expression from a lexical item. the LI goes in the first spot, and the rest are <code>null</code>.
     * @param li
     * @param g 
     */
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
    
    /**
     * Stores a mover in position <code>i</code> in the expression, if it's empty.
     * If it's not empty it's an SMC violation.
     * @param li the mover to be stored
     * @param i the index of the place it goes
     * @return true if it worked, false if SMC violation
     */
    public boolean store(Lex li, int i) { // store in position i if it's empty
        if (this.expression[i] == null) {
            this.expression[i] = li;
            return true;
        } else {
            System.out.println("SMC violation: feature slot " + i + " is already filled");
            return false;
            
        }
    }
    
    /**
     * Stores a mover in its rightful place, if it's empty.
     * The mover belongs at the index corresponding to the <code>number</code> of its head feature
     * @param li
     * @return true if it worked; false if SMC violation
     */
    public boolean store(Lex li) {
        int i = li.getFeatures().getFeatures().get(0).getNumber();
        return this.store(li,i);
    }

    public Lex[] getExpression() {
        return expression;
    }
    
    /**
     * The head feature of an expression is the first feature on the first item.
     * This is the only non-mover.
     * @return the head feature of the first LI in the expression 
     */
    public Feature headFeature() {
        return this.expression[0].getFeatures().getFeatures().get(0);
    }
    
    /**
     * Returns the main LI in the expression: the non-mover.
     * @return the first LI in the expression.
     */
    public Lex head() {
        return this.expression[0];
    }
    
    /**
     * Combines the mover lists of two merging expressions. 
     * Fails if an SMC violation is incurred due to the use of <code>store</code>
     * @param expr2 the second expression being merged with this one
     * @param n the size of an expression in the grammar
     */
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
    
    /**
     * True if there's only the head feature left and it's a category from the list <code>cats</code>
     * @param g the grammar
     * @param cats the permitted categories. These are usually either all selectional features or just the final ones.
     * @return boolean
     */
    public boolean isComplete(MG g, ArrayList<String> cats) {
        Feature h = this.headFeature();
        if (this.head().getFeatures().getFeatures().size()==1  // only one feature left
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

    /**
     * If <code>fin</code>, accepted categories are the final features, otherwise they're all the selectional features.
     * @param g gramamr
     * @param fin if true, we only accept final features
     * @return 
     */
    public boolean isComplete(MG g, boolean fin) {
        if (fin) { // finals
            return this.isComplete(g,g.getFinals());
        } else { // any category
            return this.isComplete(g,g.getBareSelFeatures());
        }
    }
    
    /**
     * Default: only finals
     * @param g
     * @return 
     */
    public boolean isComplete(MG g) {
        // default only returns true if the category is final
        return this.isComplete(g, true);
    }
    
    
    /**
     * I don't think we're using this.
     * @param valid 
    */
    public void setValid(boolean valid) {
        this.valid = valid;
    }
    
    /**
     * Returns just the generated string.
     * Only applies if the head feature is a category, and there are no other features.
     * @param g the grammar
     * @return the generated string
     */
    public String spellout(MG g) {
        if (this.isComplete(g,false)) { // spells out any complete phrase regardless of category
            return this.head().getString().replaceAll("\\s+"," "); // remove extra spaces
        } else {
            System.out.println("Spellout error: you can't spell out an incomplete phrase");
            return null;
        }

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
