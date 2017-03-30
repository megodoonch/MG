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
public class State {
    FeatureList[] state;
    
    public State(Lex li, MG g) {
        // makes a state from a lexical item
        
        // initialise the state to the right length
        this.state = new FeatureList[g.licSize()+1];
        // copy the faetures
        
        // the first element of the state is the features of the lixical item
        this.state[0] = li.getFeatures().copy();
       
    }
    
    // makes an empty state
    public State(MG g) {
        // initialise the state to the right length
        this.state = new FeatureList[g.licSize()+1];
    }    
    
    public State(int n) {
        this.state = new FeatureList[n];
    }
    
    public State(Expression e, MG g) {
        // make a state from an expression. Probably don't need this.
        FeatureList[] st;
        int n = g.licSize() +1; // length of array
        st = new FeatureList[n];
        int i = 0;
        while (i<n) {
            st[i] = e.getExpression()[i].getFeatures();
            i++;
        }
        this.state = st;
        
    }
    
    public boolean addMover(int i, FeatureList mover) {
        if (this.state[i] == null) { // only add a mover if there's room (SMC)
            this.state[i] = mover;
            return true;
        } else {
            return false;
        }
    }
    
    public Feature head() {
        // the head feature
        return this.state[0].getFeatures().get(0);
    }

    public FeatureList[] getState() {
        return state;
    }
    
   public boolean isComplete(MG g) {
       boolean valid = g.getFinals().contains(this.head().getValue()) && this.state[0].getFeatures().size()==1;
       int i = 1;
       while (i < g.licSize()+1) {
           if (this.state != null) {
               return false;
           }
           i++;
       }
       return valid;
        
      
   }
   
   public void check(int i) {   
       // remove the top feature of the ith element of the state
        this.state[i].getFeatures().remove(0);
    }
    
    
   
   public State copy() {
       
       int n = this.state.length;
       //System.out.println(n);
       State cp = new State(n);
       
       int i = 0;
       while (i < n) {
           //System.out.println(i);
           //System.out.println(state[i]);
           if (this.state[i] != null) { // only add it if it's not null
               cp.state[i] = this.state[i].copy();
           }
           i++;
       }

       //cp.state = this.state.clone();
       
       return cp;

   } 

    @Override
    public String toString() {
       String s = "";
       int i = 0;
       while (i<this.state.length) {
           s+=state[i];
           s+=" ";
           i++;
       }
       return s;
    }

    
   
   
   
}
