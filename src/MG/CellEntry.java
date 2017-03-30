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
public class CellEntry {
    // we make the cell entry a pair of the state (an array starting with the features of the main structure followed by mover features in their slots if any)
    // and a same-length tuple of mover indices if any
    private State state;
    private MoverCell[] moverIndices;
    
    public CellEntry(State state,MoverCell[] moverIndices) {
        this.state = state;
        this.moverIndices = moverIndices;
    }
    
    public CellEntry(Expression expr, MG g) {
        this.state = new State(expr, g);
        // we don't know the mover indices but we know its length
        // to keep the indices the same let's add an empty mover at the start.
        this.moverIndices=new MoverCell[g.licSize()+1];
        
    }
    
    public CellEntry(Lex li, MG g) {
        // make the li into an expression and then into a state
        this.state = new State (new Expression(li,g), g);
        // no movers yet
        // to keep the indices the same let's add an empty mover at the start.
        this.moverIndices=new MoverCell[g.licSize()+1];
    }

    public State getState() {
        return state;
    }

    public MoverCell[] getMoverIndices() {
        return moverIndices;
    }
    
    public CellEntry copy() {
        int n = this.moverIndices.length;
        MoverCell[] inds = new MoverCell[n];
        int i = 0;
        while (i<n) {
            inds[i] = this.moverIndices[i];
            i++;
        }        
        
        return new CellEntry(this.state.copy(),inds);
        
    }
    
    
}
