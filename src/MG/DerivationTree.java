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
    
    public DerivationTree(DerivationTree t1, DerivationTree t2) {
        // nodes with two daughters are always Merge
        this.operation = "Merge";
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
                
                //System.out.println("Merge");
                //System.out.println("States: " + state1 + " " + state2);
                
                // set the states. this should annotate the current tree with states, i think
                //this.posDaughter.state = state1;
                //this.negDaughter.state = state2;
                
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
                //this.posDaughter.state = state1;
                //System.out.println("Move");
                //System.out.println("State: " + state1);
                
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
                    
//                    newState = state1.copy(); 
//                    int i = newState.head().getNumber(); // mover #
//                    
//                    if (newState.getState()[i]!=null) { // if thre's a mover
//                        FeatureList mover = newState.getState()[i]; // get the mover
//                        //check the features
//                        newState.check(i);
//                        newState.check(0);
//                        newState.getState()[i]=null; // take the mover out of the list
//                        
//                        if (!mover.getFeatures().isEmpty()) { // if it's still moving
//                            newState.addMover(mover.getFeatures().get(0).getNumber() , mover); // add back into the mover list
//                        }
//                        
//                    }
//                    
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
