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
public class DerivationTree {
    
    private String operation;
    private DerivationTree posDaughter;
    private DerivationTree negDaughter;
    private Lex lexicalDaughter;
    private State state;
    
    
    public DerivationTree(Lex li) { 
        // nodes with lexical daughters are Lex 
        this.operation = "Lex";
        this.lexicalDaughter = li;
        
        
    }
    
    public DerivationTree(int i, MG g) { // given index in lexicon
        this.operation = "Lex";
        Lex li = g.getLexicon().get(i);
        this.lexicalDaughter = li;
        this.state = new State(li,g);
    } 
    
    public DerivationTree(String operation, DerivationTree t1, DerivationTree t2) {
        // nodes with two daughters are always Merge
        this.operation = operation;
        this.posDaughter = t1;
        this.negDaughter = t2;
    }
    
    public DerivationTree(DerivationTree t) {
        // nodes with one daughter that's a tree are always Move
        this.operation = "Move";
        this.posDaughter = t;
    }

    
    
    public String getOperation() {
        return operation;
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

    public State getState() {
        return state;
    }
    
    

    public Expression evaluate(MG g) {
        // evaluate the tree to get an expression
        if (this == null) {
            return null;
        }
        Expression result = null;
        switch (this.operation) {
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
            case("Adjoin"): {
                result = g.adjoin(this.posDaughter.evaluate(g), this.negDaughter.evaluate(g));
            }
        
        }
         return result;
    }
    
    
    
    public State evalToState(MG g) {
        // state of  dt is the tuple of features, from finite state tree automton in Kobele et al 2007
        return new State(this.evaluate(g),g);
   
    }
    
    
    public State automaton(MG g) {
        // evaluate the tree to get an expression
        if (this == null) {
            return null;
        }
        State newState = this.state;
        switch (this.operation) {
            case("Lex"): {
                newState = new State(this.lexicalDaughter,g);
                break;
                
            }
            case("Merge"): {
                
                
                State state1 = this.posDaughter.automaton(g);
                State state2 = this.negDaughter.automaton(g);
                              
                if (state1.head().selectional(g) 
                        && state1.head().match(state2.head())) { // if head of left daugher is sel & features match
                   
                    newState = state1.copy(); // copy the left daughter's state so we can mess with it and return it as the new state
                    State mover = state2.copy(); // possible mover
                    
                    // check features
                    mover.check(0);
                    newState.check(0);
          
                    // Merge 2: merge a mover
                    if (!mover.getState()[0].getFeatures().isEmpty()) {
                        // add mover
                        newState.addMover(mover.head().getNumber(), mover.getState()[0]);

                    }
                    
                    // combine mover lists
                    int i = 1;
                    while (i < g.licSize() + 1) {
                        newState.addMover(i, mover.getState()[i]);
                        i++;

                    }
                
                    
                }
                break;                
                
                
            }
            case("Move"): {
                
                State state1 = this.posDaughter.automaton(g);
                 
                if (state1.head().licensing(g)) {
                    
                    int i = state1.head().getNumber(); // mover #
                    //int moverNumber = state1.moving(i, g);
                    //if (moverNumber == -1) {
                    if (state1.moving(i, g)) {
                        // move and stop
                        newState = state1.move2(g);
                    } else {
                        newState = state1.move1(g);
                    }
                                      
                }
 
                break;
            }
            case("Adjoin"): {
                
                
                State state1 = this.posDaughter.automaton(g);
                State state2 = this.negDaughter.automaton(g);
                Category adjunct = g.getCategories().get(state2.head().getValue());
                              
                if (state1.head().selectional(g) 
                        && state2.head().selectional(g) // adjunct head is also selectional
                        && adjunct.getAdjunctOf().contains(state1.head().getValue()) ){ // expr2 is an adjunct of expr1
                   
                    newState = state1.copy(); // copy the left daughter's state so we can mess with it and return it as the new state
                    State mover = state2.copy(); // possible mover
                    
                    // check adjuct feature
                    mover.check(0);
          
                    // Merge 2: merge a mover
                    if (!mover.getState()[0].getFeatures().isEmpty()) {
                        // add mover
                        newState.addMover(mover.head().getNumber(), mover.getState()[0]);

                    }
                    
                    // combine mover lists
                    int i = 1;
                    while (i < g.licSize() + 1) {
                        newState.addMover(i, mover.getState()[i]);
                        i++;

                    }
                
                    
                }
                break;
            }

        
        }
        //System.out.println(newState);
        this.state = newState;
        return newState;
        
        
        
    }
    
    
    
    
    @Override
    public String toString() {
        String t = "";
        switch (this.operation) {
            case("Lex"): {
                t = t + this.lexicalDaughter;
                break;
            }
            case("Merge"): {
                t = t + "Merge \n\t( " + this.posDaughter + ",\n\t\t" + this.negDaughter + " )";
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
