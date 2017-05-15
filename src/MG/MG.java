/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Implements a string-generating minimalist grammar.
 * Based on Stabler & Keenan 2003, Fowlie 2015 for the adjunction, and unpublished MSs for the implementation of move types. 
 * Grammar has:
 * - unordered adjunction
 * - overt, covert move, copy, delete
 * - merge to the right, move to the left
 * @author meaghanfowlie
 * 
 */
public class MG {
    
    private ArrayList<String> bareLicFeatures;
    private ArrayList<String> bareSelFeatures;
    private ArrayList<Polarity> licPolarities;
    private ArrayList<Polarity> selPolarities;
    private ArrayList<Feature> features;
    private ArrayList<String> alphabet;
    private ArrayList<Lex> lexicon;
    private ArrayList<String> finals; // final categories
    private Map<String,Category> categories;
    


    /**
     * Class constructor.
     */
    public MG() {
        this.bareSelFeatures = new ArrayList<>();
        this.bareLicFeatures = new ArrayList<>();
        this.licPolarities = new ArrayList<>();
        this.selPolarities = new ArrayList<>();
        this.features = new ArrayList<>();
        this.alphabet = new ArrayList<>();
        this.lexicon = new ArrayList<>();
        this.finals = new ArrayList<>();
        this.categories = new HashMap<>();
    
        
    }

    
 
// gets
    
    public ArrayList<Polarity> getLicPolarities() {
        return licPolarities;
    }

    public ArrayList<Polarity> getSelPolarities() {
        return selPolarities;
    }

    public ArrayList<Feature> getFeatures() {
        return features;
    }

    public ArrayList<String> getBareLicFeatures() {
        return bareLicFeatures;
    }

    public ArrayList<String> getBareSelFeatures() {
        return bareSelFeatures;
    }

    public ArrayList<String> getAlphabet() {
        return alphabet;
    }

    public ArrayList<Lex> getLexicon() {
        return lexicon;
    }

    public ArrayList<String> getFinals() {
        return finals;
    }

    public Map<String,Category> getCategories() {
        return categories;
    }

    
    // adding things
    
    /**
     * Adds a polarity to the grammar
     * @param pol 
     */
    public void addPolarity(Polarity pol) {
        // add the polarity if new
        if (pol.getSet().equals("lic") && !this.licPolarities.contains(pol)) {
            licPolarities.add(pol);
        } else if (pol.getSet().equals("sel") && !this.selPolarities.contains(pol)) {
            selPolarities.add(pol);
        }
    }

    
    /**
     * Adds a <code>feature</code> name to Sel or Lic feature <code>set</code>s.
     * @param feature The feature name
     * @param set sel or lic
     */
    public void addBareFeature(String feature, String set) {
        switch (set) {
            case "lic": 
                if (!this.bareLicFeatures.contains(feature)) {
                    this.bareLicFeatures.add(feature);
                }
                //System.out.println(feature);
                break;
            
            case "sel": 
                if (!this.bareSelFeatures.contains(feature)) {
                    this.bareSelFeatures.add(feature);
                    this.categories.put(feature, new Category(feature));
                }
                //System.out.println(feature);
                break;            
                       
        }
        
    }
    
    /**
     * Adds a feature to the set of final features, and to the bare features if necessary.
     * @param feature 
     */
    public void addFinal(String feature) {
        this.finals.add(feature);
        this.addBareFeature(feature, "sel");
        
    }

    /**
     * Adds a <code>feature</code> to the grammar.
     * @param feature Of class <code>Feature</code>
     */
    public void addFeature(Feature feature) {
        if (!this.features.contains(feature)) {
            this.features.add(feature);
        }
    }
    
    /**
     * Adds an adjunct-adjoinee mapping to the <code>categories</code> of the grammar.
     * Default adjoins to the left
     * @param adjunct
     * @param adjoinedTo 
     */
    public void addAdjunct(String adjunct,String adjoinedTo) {
        this.categories.get(adjunct).addAdjunct(adjoinedTo);
    }
    
    /**
     * Changes the side of the head the adjunct adjoins on.
     * @param adjunct
     * @param left <code>true</code> if adjoins to the left
     */
    public void changeAdjunctSide(String adjunct, boolean left) {
        this.categories.get(adjunct).setLeft(left);
    }
    
    /**
     * Adds a string to the alphabet.
     * @param word 
     */
    public void addWord(String word) {
        if (!this.alphabet.contains(word)) {
            this.alphabet.add(word);
        }
    }
    
    /**
     * Returns the number of licensing features.
     * We need this to know how many mover slots to make in an expression etc.
     * @return 
     */
    public int licSize() {
        // returns number of licensing features in the grammar
        return this.bareLicFeatures.size();
    }
    
    /**
     * Generates all <code>Features</code> based on the bare features and polarities in the grammar.
     * Adds them to the <code>features</code> of the grammar
     */
    public void generateFeatures() {
        // using the polarities and bare features of the grammar, generate the feature set
        ArrayList<Feature> newfs = new ArrayList<>();
        
        // licensing features
        int number = 1; // lic features get numbers
        for (String f : this.bareLicFeatures) {
            for (Polarity pol : this.getLicPolarities()) {
                newfs.add(new Feature(pol,f,number));
             
            }
            number++;
        }
        
        // selectional features
        number = 1; // what the heck, let's give sel feaures numbers too just in case that's useful
        for (String c : this.bareSelFeatures) {
            
            for (Polarity pol : this.getSelPolarities()) {
                newfs.add(new Feature(pol,c,number));
                
            }
            number++;
        }
        this.features = newfs;
    }
    
    /**
     * Returns a features based on its index
     * @param n index
     * @return the feature at that index
     */
    public Feature featureByNumber(int n) {
        return this.getFeatures().get(n);
    } 

    /**
     * Adds a lexical item to the lexicon.
     * Features are added by index
     * @param word the string  
     * @param numbers list of indices of features
     */
    public void addLexicalItem(String word, Integer[] numbers) { // by index in feature list
        ArrayList<Feature> fs = new ArrayList<>();
        for (int n : numbers) {
            fs.add(featureByNumber(n));    
        }
        this.lexicon.add(new Lex(word, new FeatureList(fs)));
        addWord(word);
    }
    
    /**
     * Adds a lexical item to the lexicon.
     * Features are listed
     * @param word
     * @param features 
     */
    public void addLexicalItem(String word, FeatureList features) {
        this.lexicon.add(new Lex(word,features));
        addWord(word);
    }
    
    /**
     * Removes a lexical item from the lexicon
     * @param i index of item to be removed
     */
    public void removeLexicalItem(int i) {
        this.lexicon.remove(i);
    }
    
    @Override
    public String toString() {
        return "MG{" + "\n licPolarities = " + licPolarities + 
                ",\n selPolarities = " + selPolarities + 
                ",\n bareSelFeatures = " + bareSelFeatures + 
                ",\n bareLicFeatures = " + bareLicFeatures + 
                ",\n features = " + features + 
                ",\n categories = " + categories + 
                ",\n final categories = " + finals + 
                ",\n alphabet = " + alphabet +
                ",\n lexicon = " + lexicon + "\n }";
    }
    
    public void printLexicon() {
        System.out.println("\n** Lexicon **\n");
        int i=0;
        while (i<this.getLexicon().size()) {
            System.out.println(i + ". " + this.getLexicon().get(i));
            i++;
        }
    }
    
    public void printFeatures() {
        System.out.println("\n** Features **\n");
        int i=0;
        while (i<this.features.size()) {
            System.out.println(i + ". " + this.features.get(i));
            i++;
        }
    }
            
    
    
    
    //MERGE
    /**
     * Merges two expressions if their head features match.
     * @param expr1 the selector
     * @param expr2 the selectee
     * @return a new expression with both the expressions together
     */
    public Expression merge(Expression expr1,Expression expr2) {
        
        // make a copy of expr1 where we'll make our new guy
        Expression result = expr1.copy(this);
        // if Merge even applies
        if (result.headFeature().getSet().equals("sel") // it's a selectional feature
                && result.headFeature().match(expr2.headFeature()))  { // features match and result is +ve
            //check features
            result.expression[0].check(); 
            expr2.expression[0].check();
            // merge 1: merge and stay
            if (expr2.getExpression()[0].getFeatures().getFeatures().isEmpty()) {
                // combine strings to the right
                result.getExpression()[0].combine(expr2.getExpression()[0].getString(),false);
              
            // Merge 2: merge a mover    
            } else {
                // if +combine
                if (expr2.headFeature().getPolarity().isCombine()) {
                    //combine string on left (spec)
                    result.getExpression()[0].combine(expr2.getExpression()[0].getString(),false);                    
                }
                
                // if -store, remove string part
                if (!expr2.headFeature().getPolarity().isStore()) {
                    expr2.head().setString("");
                }
                // store whereever it belongs
                if (!result.store(expr2.head())) { // SMC violation
                    return null;
                }
    
            }
            
            // combine mover lists
            result.combineMovers(expr2, this.licSize()+1);
            
            
        } else {
            System.out.println("\n*** Merge error *** : features don't match or head feature isn't a selectional feature");
            return null;
        }
        
        return result;
    }
    
    
    // MOVE
    /**
     * Moves a waiting mover.
     * Based on the head feature, takes the corresponding mover out of storage and (internally) merges it 
     * @param expr an expression
     * @return the expression with Move applied
     */
    public Expression move(Expression expr) {
        Expression result = expr.copy(this);
        
        // if top feature is a poitive licensing feature
        Feature head = result.headFeature();
        if (head.getSet().equals("lic")) {
            Integer i = head.getNumber();
            Lex mover = result.getExpression()[i];
            if (mover == null) {
                System.out.println("Move error: no matching mover");
                return null;
            } else {
                // check features
                if (head.match(mover.getFeatures().getFeatures().get(0))) {
                    result.head().check();
                    mover.check();
                    
                    // remove mover
                    result.getExpression()[i] = null;
                    
                    // move 1: move and stay
                    if (mover.getFeatures().getFeatures().isEmpty()) {
                        // combine on left
                        result.getExpression()[0].combine(mover.getString(),true);
                        return result;
 
                    // move 2 : move and keep moving    
                    } else {
                        //combine
                        if (mover.getFeatures().getFeatures().get(0).getPolarity().isCombine()) {
                            result.getExpression()[0].combine(mover.getString(),true);
                        }
                        //store
                        // if -store, remove string part
                        if (!mover.getFeatures().getFeatures().get(0).getPolarity().isStore()) {
                            mover.setString("");
                        }
                        
                        if (!result.store(mover)) { //SMC violation
                            return null;
                        }
                        return result; // otherwise, we're good.
                    }
                    
                } else {
                    System.out.println("Move error: features don't match. This shouldn't happen if Move is working correctly.");
                    return null;
                }
                
            }
            
        } else {
            System.out.println("Move error: not a licensing feature");
            return null;
        }
    }
    
 
    //ADJOIN
    /**
     * Adjoins based only on whether the category of the adjoinee is in the set of categories the adjunct is defined to adjoin to.
     * this info is stored in the <code>categories</code>, along with whether we adjoin on the left or right
     * @param expr1 the adjoin-ee
     * @param expr2 the adjunct
     * @return a new expression with the adjunct added
     */
    public Expression adjoin(Expression expr1,Expression expr2) {
        
        // make a copy of expr1 where we'll make our new guy
        Expression result = expr1.copy(this);
        // get the category of the adjunct
        Category adjunct = this.categories.get(expr2.headFeature().getValue());
        //System.out.println(adjunct);
        // if Adjoin even applies
        if (result.headFeature().getSet().equals("sel") // it's a selectional feature
                && expr2.headFeature().getSet().equals("sel") // expr2 head is also selectional
                && adjunct.getAdjunctOf().contains(result.headFeature().getValue()) ){ // expr2 is an adjunct of expr1
            //check adjunct feature
            expr2.expression[0].check();
            // adjoin 1: adjoin and stay
            if (expr2.getExpression()[0].getFeatures().getFeatures().isEmpty()) {
                // combine strings
                result.getExpression()[0].combine(expr2.getExpression()[0].getString(), adjunct.isLeft());
              
            // Merge 2: merge a mover    
            } else {
                // if +combine
                if (expr2.headFeature().getPolarity().isCombine()) {
                    //combine string 
                    result.getExpression()[0].combine(expr2.getExpression()[0].getString(), adjunct.isLeft());                    
                }
                
                // if -store, remove string part
                if (!expr2.headFeature().getPolarity().isStore()) {
                    expr2.head().setString("");
                }
                // store whereever it belongs
                if (!result.store(expr2.head())) { // SMC violation
                    return null;
                }
    
            }
            
            // combine mover lists
            result.combineMovers(expr2, this.licSize()+1);
            
            
        } else {
            System.out.println("\n*** Adjoin error *** : not an adjunct");
            return null;
        }
        
        return result;
    }
    
    
    
    
}
