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
public class Sandbox {
    
    public static void main(String[] args) {
        // TODO code application logic here
        MG g = new MG();
        
        List<Polarity> pols = Arrays.asList(new Polarity("-","lic",-1),new Polarity("+","lic",+1),new Polarity("","sel",-1),new Polarity("=","sel",+1));
        
        for (Polarity pol : pols) {
            g.addPolarity(pol);
        }
        
        System.out.println(g);
        
        List<String> lic = Arrays.asList("wh", "foc", "nom");
        
        for (String f : lic) {
            g.addBareFeature(f,"lic");
        }
        
        System.out.println(g);
        
        List<String> sel = Arrays.asList("N", "D", "V", "T", "C");
        
        for (String f : sel) {
            g.addBareFeature(f,"sel");
        }
        

        g.generateFeatures();
        
        System.out.println(g);
        
        
        g.addWord("cat");
        g.addWord("the");
        g.addWord("who");
        
        System.out.println(g);
        
        Feature minwh = g.getFeatures().get(0);
        Feature poswh = g.getFeatures().get(1);
        Feature catN = g.getFeatures().get(6);
        Feature selN = g.getFeatures().get(7);
        Feature catD= g.getFeatures().get(8);
        
        
        g.printFeatures();
        
        
               
        g.addLexicalItem("the", new Integer[] {7,8,4}); // -nom
        g.addLexicalItem("the", new Integer[] {7,8}); 
        g.addLexicalItem("which", new Integer[] {7,8,0}); // -wh
        g.addLexicalItem("cat", new Integer[] {6});
        g.addLexicalItem("dog", new Integer[] {6});
        g.addLexicalItem("saw", new Integer[] {9,9,10});
        g.addLexicalItem("slept", new Integer[] {9,10});
        g.addLexicalItem("who", new Integer[] {8,0});
        g.addLexicalItem("", new Integer[] {11,5,12}); // T
        g.addLexicalItem("", new Integer[] {13,1,14}); // C
        
        // user interface
        
        // show lexicon
        g.printLexicon();
        
        // let's add everyting to a numeration as expressions
        //List<Expression> numeration = new ArrayList<>();
        
        Numeration numeration = new Numeration(g);
        
        for (Lex li : g.getLexicon()) {
            numeration.addExpression(li);
        }
        
        numeration.printNumeration();
        
//        Expression cat = numeration.get(0);
//        Expression the = numeration.get(1);
//        Expression dog = numeration.get(2);
//        Expression saw = numeration.get(3);
//        Expression slept = numeration.get(4);
//        Expression who = numeration.get(5);
//        Expression t = numeration.get(6);

        numeration.merge(0,3,true);
        
//        for (Feature f : g.getFeatures()) {
//            System.out.println("" + f + f.getNumber());
//        }
        
        numeration.merge(5,0,true);
        
        
        
        
        
        
        
    }
    
    
}
