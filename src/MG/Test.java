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
public class Test {
    
    private Numeration numeration; 
    
    public static void main(String[] args) throws MGException { // this needs to be here to use my exceptions
        // generate MG
        MG g = Main.defaultMG();
        Numeration numeration = new Numeration(g);
        
        
        // TEST 1: the cat slept
        System.out.println("Test case 1: the cat slept");
        for (int i : new Integer[] {0,3,6,9}) {
            numeration.addExpression(g.getLexicon().get(i)); 
        
        }
        
        System.out.println("Merge");
        numeration.merge(0, 1,true);
        System.out.println("Merge");
        numeration.merge(1,0,true);
        System.out.println("Merge");
        numeration.merge(1,0,true);
        System.out.println("Move");
        numeration.move(0,true);
        
        System.out.println("Checking...");
        if (numeration.getNumeration().size() == 1) {
            Expression s = numeration.getNumeration().get(0);
            if (s.isComplete(g)) {
                System.out.println(s.head().getString() + " is a complete sentence of category " + s.headFeature());
            } else {
                System.out.println(s + " is not a complete sentence.");
            }

        } else {
            System.out.println("The derivation is complete only when there is exactly one item in the numeration.");
            System.out.println("** Numeration **");
            numeration.printNumeration();
        }

        // TEST 2: 
        System.out.println("Test case 2: which cat slept");
        
        
        g.printLexicon();
        numeration = new Numeration(g);
        // add words to the numeration
        for (int i : new Integer[] {11,3,6,9,10}) {
            numeration.addExpression(g.getLexicon().get(i)); 
        
        }
        
        //numeration.printNumeration();
        
        Expression expr = 
                g.moveStep(
                g.mergeStep(numeration.getNumeration().get(4), 
                g.moveStep(
                g.mergeStep(numeration.getNumeration().get(3),
                g.mergeStep(numeration.getNumeration().get(2),
                g.mergeStep(numeration.getNumeration().get(0), numeration.getNumeration().get(1)))))));
        
        System.out.println("\noutput: \n" + expr.head().getString() + " \nof category " + expr.headFeature());
        
        Expression expr2 = 
               g.moveStep(
                       g.mergeStep(new Expression(g.getLexicon().get(10),g), 
                               g.moveStep(
                                       g.mergeStep(new Expression(g.getLexicon().get(9),g), 
                                               g.mergeStep(new Expression(g.getLexicon().get(6),g),
                                                    g.mergeStep(new Expression(g.getLexicon().get(11),g), new Expression(g.getLexicon().get(3),g) )
                                               )
                                       )
                               )
                       )
               );
        
        System.out.println("\noutput: \n" + expr2.head().getString() + " \nof category " + expr2.headFeature());
        
        
        DerivationTree t1 = new DerivationTree(new DerivationTree(g.getLexicon().get(11)), new DerivationTree(g.getLexicon().get(3)));
        
        DerivationTree t2 = new DerivationTree(new DerivationTree(g.getLexicon().get(6)),t1);
        
        System.out.println(t2);
        
        System.out.println(t2.evaluate(g));
        
    }
    
}
