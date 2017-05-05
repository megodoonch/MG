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
    // indices
    private int start;
    private int end;
    
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

    
    // with indices
    
    public CellEntry(State state, MoverCell[] moverIndices, int start, int end) {
        this.state = state;
        this.moverIndices = moverIndices;
        this.start = start;
        this.end = end;
    }

    public CellEntry(Expression expr, MG g, int start, int end) {
        this.state = new State(expr, g);
        // we don't know the mover indices but we know its length
        // to keep the indices the same let's add an empty mover at the start.
        this.moverIndices=new MoverCell[g.licSize()+1];
        this.start = start;
        this.end = end;
        
    }
    
    public CellEntry(Lex li, MG g, int start, int end) {
        // make the li into an expression and then into a state
        this.state = new State (new Expression(li,g), g);
        // no movers yet
        // to keep the indices the same let's add an empty mover at the start.
        this.moverIndices=new MoverCell[g.licSize()+1];
        this.start = start;
        this.end = end;
    }
    
    public State getState() {
        return state;
    }

    public MoverCell[] getMoverIndices() {
        return moverIndices;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
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
    
    public MoverCell[] mergeMoverLists(CellEntry selectee) {
        MoverCell[] movers1 = this.moverIndices;
        MoverCell[] movers2 = selectee.getMoverIndices();
        
        int n = this.moverIndices.length;
        MoverCell[] merged = new MoverCell[n];
        int i = 0;
        while (i<n) {
            
            merged[i] = this.moverIndices[i];
            if (merged[i] == null) {
                merged[i] = movers2[i];
            } else if (movers2[i] != null) {
                return null;
            }
 
            i++;
        }   
        return merged;
        
        
    }
    
    public CellEntry merge1(CellEntry selectee, MG g) {
        State state1 = this.state;
        State state2 = selectee.getState();
        State newState = state1.merge1(state2, g);
        if (newState==null) { // if merge failed
            return null;
        } else {
            // merge the mover lists
            MoverCell[] newMovers = this.mergeMoverLists(selectee);
            // make a new cell entry that will go from the start of the selector to the end of the selectee
            return new CellEntry(newState, newMovers, this.start, selectee.getEnd());
        }
            
    }
        
    public CellEntry merge2(CellEntry selectee, MG g) {
        State state1 = this.state;
        State state2 = selectee.getState();
        State newState = state1.merge2(state2, g);
        if (newState==null) { // if merge failed
            return null;
        } else {
            // merge the mover lists
            MoverCell[] newMovers = this.mergeMoverLists(selectee);
            // make a new cell entry that will go back in the same cell since we selected a mover
            return new CellEntry(newState, newMovers, this.start, this.end);
        }
                
    }
    
//    public CellEntry move1(MG g) {
//        State newState = this.state.move1(g);
//        MoverCell[] newMovers = new MoverCell[this.moverIndices.length];
//        
//        
//        return new CellEntry(newState,);
//        
//        
//               
//    }
    
    
    
}
