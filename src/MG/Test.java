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
    
    public static void main(String[] args) { // this needs to be here to use my exceptions
        // generate MG
        MG g = Main.defaultMG();
        Numeration numeration = new Numeration(g);
        for (int i : new Integer[] {0,3,6,9}) {
            numeration.addExpression(g.getLexicon().get(i)); 
        
        }
        
        System.out.println("Let's start numeration-style\n");
        
        // bad merge
        System.out.println("Test 0: bad Merge");
        System.out.println(String.format("\nMerge %d and %d", 0, 0));
        numeration.merge(0, 0,true);
        
        // TEST 1: the cat slept
        System.out.println("\nTest case 1: the cat slept");
        
        
        
        
        numeration.printNumeration();
        System.out.println(String.format("\nMerge %d and %d", 0, 1));
        numeration.merge(0, 1,true);
        System.out.println(String.format("\nMerge %d and %d",0,2));
        numeration.merge(0,2,true);
        System.out.println(String.format("\nMerge %d and %d",0,1));
        numeration.merge(0,1,true);
        System.out.println(String.format("\nMove in %d",0));
        numeration.move(0,true);
        
        System.out.println("\nChecking...");
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
        System.out.println("\n*Test case 2: which cat slept*");
        System.out.println("This time we do everything under the hood with merge and move, which output expressions");
        
        
        g.printLexicon();
        numeration = new Numeration(g);
        // add words to the numeration
        for (int i : new Integer[] {11,3,6,9,10}) {
            numeration.addExpression(g.getLexicon().get(i)); 
        
        }
        
        //numeration.printNumeration();
        
        // get from numeration, apply merge/move which outputs an expression
        Expression expr = 
                g.move(
                g.merge(numeration.getNumeration().get(4), 
                g.move(
                g.merge(numeration.getNumeration().get(3),
                g.merge(numeration.getNumeration().get(2),
                g.merge(numeration.getNumeration().get(0), numeration.getNumeration().get(1)))))));
        
        System.out.println("\noutput: " + expr);
        
        System.out.println(expr.head().getString() + " \nof category " + expr.headFeature());
        
        
        System.out.println("\n*Test 3: which cat slept*");
        System.out.println("This time we don't use the numeration at all, and just make expressions from the lexicon.");
        Expression expr2 = 
               g.move(
                       g.merge(new Expression(g.getLexicon().get(10),g), 
                               g.move(
                                       g.merge(new Expression(g.getLexicon().get(9),g), 
                                               g.merge(new Expression(g.getLexicon().get(6),g),
                                                    g.merge(new Expression(g.getLexicon().get(11),g), new Expression(g.getLexicon().get(3),g) )
                                               )
                                       )
                               )
                       )
               );
        
        System.out.println("\noutput: " + expr2);
        
        System.out.println(expr2.head().getString() + " \nof category " + expr2.headFeature());    
        
        
        // derivation trees proper
        
        System.out.println("\n* Test 4: which cat slept using derivation trees *\n");
        
        // we define trees that we then evaluate.
        DerivationTree t1 = new DerivationTree(new DerivationTree(g.getLexicon().get(11)), new DerivationTree(g.getLexicon().get(3)));
        
        Expression t1_output = t1.evaluate(g);
        System.out.println("*Stop partway*");
        System.out.println("Generated: " +t1_output);
        System.out.println("Spellout: " + t1_output.spellout(g));
        
        System.out.println("\n*continue...*");
        
        DerivationTree t2 = new DerivationTree (new DerivationTree (new DerivationTree(g.getLexicon().get(10)), new DerivationTree (
                        new DerivationTree (
                                new DerivationTree(g.getLexicon().get(9)),  new DerivationTree(
                                        new DerivationTree(g.getLexicon().get(6)),t1)))));
        
        System.out.println("\n"+t2);
        
        Expression t2_output = t2.evaluate(g);
        System.out.println("\nGenerated: "+t2_output);
        System.out.println("Spellout: " + t2_output.spellout(g));
        
        DerivationTree the = new DerivationTree(g.getLexicon().get(0));
        
        System.out.println(t1.getState());
        
        System.out.println(t2.automaton(g));
        
        System.out.println(t1.getState());
        
        //t2.automaton(g);
        
    }
    
}
