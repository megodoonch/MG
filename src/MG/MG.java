/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
    


    
    public MG() {
        this.bareSelFeatures = new ArrayList();
        this.bareLicFeatures = new ArrayList();
        this.licPolarities = new ArrayList();
        this.selPolarities = new ArrayList();
        this.features = new ArrayList();
        this.alphabet = new ArrayList();
        this.lexicon = new ArrayList();
        this.finals = new ArrayList();
    
        
    }

    public void addPolarity(Polarity pol) {
        if (pol.getSet().equals("lic")) {
            licPolarities.add(pol);
        } else if (pol.getSet().equals("sel")) {
            selPolarities.add(pol);
        }
    }
    
 

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
    
    public void addWord(String word) {
        if (!this.alphabet.contains(word)) {
            this.alphabet.add(word);
        }
    }
    
    public int licSize() {
        return this.bareLicFeatures.size();
    }
    
    public void generateFeatures() {
        ArrayList<Feature> newfs = new ArrayList();
        int number = 1; // lic features get numbers
        for (String f : this.bareLicFeatures) {
            for (Polarity pol : this.getLicPolarities()) {
                newfs.add(new Feature(pol,f,number));
             
            }
            number++;
        }
        
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
        this.lexicon.add(new Lex(word, fs));
        addWord(word);
    }
    
    public void addLexicalItem(String word, List<Feature> features) {
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
    public boolean merge(Expression expr1,Expression expr2) {
        
        
        // if Merge even applies
        if (expr1.headFeature().getSet().equals("sel") // it's a selectional feature
                && expr1.headFeature().match(expr2.headFeature()))  { // features match and expr1 is +ve
            //check features
            expr1.expression[0].check(); 
            expr2.expression[0].check();
            // merge 1: merge and stay
            if (expr2.getExpression()[0].getFeatures().isEmpty()) {
                // combine strings to the right
                expr1.getExpression()[0].combine(expr2.getExpression()[0].getString(),false);
              
            // Merge 2: merge a mover    
            } else {
                // if +combine
                if (expr2.headFeature().getPolarity().isCombine()) {
                    //combine string on left (spec)
                    expr1.getExpression()[0].combine(expr2.getExpression()[0].getString(),true);                    
                }
                
                // if -store, remove string part
                if (!expr2.headFeature().getPolarity().isStore()) {
                    expr2.head().setString("");
                }
                // store whereever it belongs
                expr1.store(expr2.head());
    
            }
            
            // combine mover lists
            expr1.combineMovers(expr2, this.licSize()+1);
            
            
        } else {
            System.out.println("\n*** Merge error *** : features don't match or head feature isn't a selectional feature");
            return false;
        }
        
        return true;
    }
    
    
    // MOVE
    public boolean move(Expression expr) {
        // if top feature is a poitive licensing feature
        Feature head = expr.headFeature();
        if (head.getSet().equals("lic")) {
            Integer i = head.getNumber();
            Lex mover = expr.getExpression()[i];
            if (mover == null) {
                System.out.println("Move error: no matching mover");
                return false;
            } else {
                // check features
                if (head.match(mover.getFeatures().get(0))) {
                    expr.head().check();
                    mover.check();
                    
                    // remove mover
                    expr.getExpression()[i] = null;
                    
                    // move 1: move and stay
                    if (mover.getFeatures().isEmpty()) {
                        // combine on left
                        expr.getExpression()[0].combine(mover.getString(),true);
                        return true;
 
                    // move 2 : move and keep moving    
                    } else {
                        //combine
                        if (mover.getFeatures().get(0).getPolarity().isCombine()) {
                            expr.getExpression()[0].combine(mover.getString(),true);
                        }
                        //store
                        // if -store, remove string part
                        if (!mover.getFeatures().get(0).getPolarity().isStore()) {
                            mover.setString("");
                        }
                        expr.store(mover);
                        return true;
                        
                    }
                    
                } else {
                    System.out.println("Move error: features don't match. This shouldn't happen if Move is working correctly.");
                    return false;
                }
                
            }
            
        } else {
            System.out.println("Move error: not a licensing feature");
            return false;
        }
    }
    
    public Expression mergeStep(Expression expr1, Expression expr2) {
        //apply Merge. if it fails, throw an exception, otherwise return result.

        boolean valid = this.merge(expr1, expr2);
        expr1.setValid(valid);
        
        try {
            expr1.isValid();
            
        } catch (MGException e) {
            System.out.println("Merge error");
            e.printStackTrace();
        }

        return expr1;
    }
    
    public Expression moveStep(Expression expr)  {
        boolean valid = this.move(expr);
        expr.setValid(valid);
        
        try {
            expr.isValid();
            
        } catch (MGException e) {
            System.out.println("Move error");
            e.printStackTrace();
        }

        return expr;
        
        
    }
    
}
