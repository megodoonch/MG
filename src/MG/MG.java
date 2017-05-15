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
 *
 * @author meaghanfowlie
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
    
    public void addPolarity(Polarity pol) {
        // add the polarity if new
        if (pol.getSet().equals("lic") && !this.licPolarities.contains(pol)) {
            licPolarities.add(pol);
        } else if (pol.getSet().equals("sel") && !this.selPolarities.contains(pol)) {
            selPolarities.add(pol);
        }
    }

    

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
    
    public void addFinal(String feature) {
        this.finals.add(feature);
        this.addBareFeature(feature, "sel");
        
    }

    public void addFeature(Feature feature) {
        if (!this.features.contains(feature)) {
            this.features.add(feature);
        }
    }
    
    public void addAdjunct(String adjunct,String adjoinedTo) {
        this.categories.get(adjunct).addAdjunct(adjoinedTo);
    }
    
    public void changeAdjunctSide(String adjunct, boolean left) {
        this.categories.get(adjunct).setLeft(left);
    }
    
    public void addWord(String word) {
        if (!this.alphabet.contains(word)) {
            this.alphabet.add(word);
        }
    }
    
    public int licSize() {
        // returns number of licensing features in the grammar
        return this.bareLicFeatures.size();
    }
    
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
    
    public Feature featureByNumber(int n) {
        return this.getFeatures().get(n);
    } 

    public void addLexicalItem(String word, Integer[] numbers) { // by index in feature list
        ArrayList<Feature> fs = new ArrayList<>();
        for (int n : numbers) {
            fs.add(featureByNumber(n));    
        }
        this.lexicon.add(new Lex(word, new FeatureList(fs)));
        addWord(word);
    }
    
    public void addLexicalItem(String word, FeatureList features) {
        this.lexicon.add(new Lex(word,features));
        addWord(word);
    }
    
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
