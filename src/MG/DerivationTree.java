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
public class DerivationTree {
    
    private String mother;
    private DerivationTree posDaughter;
    private DerivationTree negDaughter;
    private Lex lexicalDaughter;
    
    public DerivationTree(Lex li) { 
        // nodes with lexical daughters are Lex 
        this.mother = "Lex";
        this.lexicalDaughter = li;
        
    }
    
    public DerivationTree(DerivationTree t1, DerivationTree t2) {
        // nodes with two daughters are always Merge
        this.mother = "Merge";
        this.posDaughter = t1;
        this.negDaughter = t2;
    }
    
    public DerivationTree(DerivationTree t) {
        // nodes with one daughter that's a tree are always Move
        this.mother = "Move";
        this.posDaughter = t;
    }

    public String getMother() {
        return mother;
    }

    public DerivationTree getPosDaughter() {
        return posDaughter;
    }

    public DerivationTree getNegDaughter() {
        return negDaughter;
    }

    public Lex getLexicalDaughter() {
        return lexicalDaughter;
    }

    public Expression evaluate(MG g) {
        // evaluate the tree to get an expression
        if (this == null) {
            return null;
        }
        Expression result = null;
        switch (this.mother) {
            case("Lex"): {
                result = new Expression(this.lexicalDaughter,g);
                break;
                
            }
            case("Merge"): {
                result = g.merge (this.posDaughter.evaluate(g), this.negDaughter.evaluate(g) );
                break;
            }
            case("Move"): {
                result = g.move ( this.posDaughter.evaluate(g) );
                break;
            }
        
        }
         return result;
    }
    
 
    
    @Override
    public String toString() {
        String t = "";
        switch (this.mother) {
            case("Lex"): {
                t = t + this.lexicalDaughter;
                break;
            }
            case("Merge"): {
                t = t + "Merge \n( " + this.posDaughter + "\t,\t" + this.negDaughter + " )";
                break;
            }
            case("Move"): {
                t = t + "Move \n( " + this.posDaughter + " )";
                break;
            }
        
        }
        return t;
    }
    
    
    
    
    
}
