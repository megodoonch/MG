/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MG;

//import java.util.ArrayList;
//import java.util.Arrays;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author meaghanfowlie
 */
public class Main {
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        MG g = new MG();
        Numeration numeration = new Numeration(g);
        
        Scanner read = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n\n****** MENU *******");
            System.out.println("To use the default grammar, enter dg");
            System.out.println("To change the grammar, enter cg");
            System.out.println("To view the grammar, enter vg");
            System.out.println("To add a word to the numeration, enter a");
            System.out.println("To remove an item from the numeration, enter r");
            System.out.println("To see the numeration, enter n");
            System.out.println("To see the lexicon, enter l");
            System.out.println("To merge two expressions in the numeration, enter merge");
            System.out.println("To move, enter move");
            System.out.println("To check the completeness of the derivation, enter done");
            System.out.println("To quit, enter q");
            System.out.print("\nYour choice: ");

            String input = read.nextLine();
            
            if (input.equals("q")) {
                System.out.println("Thanks for MGing with me!");
                break;
            }

            switch (input) {               

                case ("dg"): {
                    g = defaultMG();
                    numeration = new Numeration(g);
                    break;
                }
                
                case("vg"): {
                    System.out.println(g);
                    break;
                }

                case ("a"): {
                    g.printLexicon();
                    System.out.print("Which word would you like to add? Enter the number: ");
                    while (true) {
                    
                        input = read.nextLine();
                        if (input.equals("") || input.equals("q")) {
                            
                            break;
                        }
                        try {
                            int n = Integer.parseInt(input);
                            if (g.getLexicon().size() >= n) {
                                Lex w = g.getLexicon().get(n); // get the nth LI                
                                numeration.addExpression(w); // add it to the numeration
                                System.out.print(w + " added. Enter another or hit enter if you're done: ");

                            } else {
                                System.out.println("I don't have a word at that index");
                            }
                        } catch (NumberFormatException e) {
                            //g.printLexicon();
                            System.out.println("\nMake sure you enter a number!");

                        }

                        
                    }
                    numeration.printNumeration();
                    break;
                }

                case("r"): {
                    numeration.printNumeration();
                    System.out.print("Which one would you like to remove? Enter the number: ");
                    input = read.nextLine();
                    try {
                        int n = Integer.parseInt(input);
                        numeration.getNumeration().remove(n);
                        numeration.printNumeration();
                    } catch (NumberFormatException e) {
                            //g.printLexicon();
                            System.out.println("\nMake sure you enter a number!");

                    }
                    break;
                }
                
                case ("n"): {
                    numeration.printNumeration();
                    break;
                }
                
                case ("l"): {
                    g.printLexicon();
                    break;
                }
                
                case ("merge"): {
                    while (true) {
                        numeration.printNumeration();
                        System.out.print("Choose index of selector (q to go back to menu): ");
                        input = read.nextLine();
                        if (input.equals("q")) {
                            break;
                        }
                        try {
                            int i = Integer.parseInt(input);
                            System.out.print("Choose index of selectee (q to go back to menu): ");
                            input = read.nextLine();
                            if (input.equals("q")) {
                                break;
                            }
                            try {
                                int j = Integer.parseInt(input);
                                int n = numeration.numeration.size();
                                if (i < n && j < n) { // if we have those items

                                    numeration.merge(i, j, true);
                                    break;
                                } else {
                                    System.out.println("Numeration doesn't have items at those indices");
                                }

                            } catch (NumberFormatException e) {
                                System.out.println("\nMake sure you enter a number!");
                            }

                        } catch (NumberFormatException e) {
                            System.out.println("\nMake sure you enter a number!");
                        }

                    }
                    break;
                }
                
                case ("adjoin"): {
                    while (true) {
                        numeration.printNumeration();
                        System.out.print("Choose index of adjoined-to phrase (q to go back to menu): ");
                        input = read.nextLine();
                        if (input.equals("q")) {
                            break;
                        }
                        try {
                            int i = Integer.parseInt(input);
                            System.out.print("Choose index of adjunct (q to go back to menu): ");
                            input = read.nextLine();
                            if (input.equals("q")) {
                                break;
                            }
                            try {
                                int j = Integer.parseInt(input);
                                int n = numeration.numeration.size();
                                if (i < n && j < n) { // if we have those items

                                    numeration.adjoin(i, j, true);
                                    break;
                                } else {
                                    System.out.println("Numeration doesn't have items at those indices");
                                }

                            } catch (NumberFormatException e) {
                                System.out.println("\nMake sure you enter a number!");
                            }

                        } catch (NumberFormatException e) {
                            System.out.println("\nMake sure you enter a number!");
                        }

                    }
                    break;
                }
                
                
                case ("move"): {
                    while (true) {
                        numeration.printNumeration();
                        System.out.print("Choose index of expression to move in (q to go back to the menu): ");
                        input = read.nextLine();
                        if (input.equals("q")) {
                            break;
                        }
                        try {
                            int i = Integer.parseInt(input);
                            int n = numeration.numeration.size();
                            if (i < n) { // if we have that item

                                numeration.move(i, true);
                                break;
                            } else {
                                System.out.println("No such item");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("\nMake sure you enter a number!");
                        }
                    }
                    break;
                }

                case("done"): {
                    if (numeration.getNumeration().size()!=1) {
                        System.out.println("There's zero or more than one item in the numeration. \nIf you think one of the items is a complete sentence,\n enter the number and I'll check just that one.\nOtherwise press enter to continue.");
                        input = read.nextLine();
                        if (input.equals("")) {
                            break;
                        }
                        try {
                            int n = Integer.parseInt(input);
                            Expression expr = numeration.getNumeration().get(n);
                            if (expr.isComplete(g)) {
                                System.out.println(expr.head() + " is a complete sentence of category "+ expr.headFeature());
                            } else {
                                System.out.println(expr.head() + " is not a complete sentence.");
                            }
                        } catch (NumberFormatException e) {
                            //g.printLexicon();
                            System.out.println("\nMake sure you enter a number!");

                        }

                        
                    } else {
                        Expression s = numeration.getNumeration().get(0);
                        if (s.isComplete(g)) {
                                System.out.println(s.head() + " is a complete sentence of category "+ s.headFeature());
                            } else {
                                System.out.println(s + " is not a complete sentence.");
                            }
                        
                        
                    }
                    break;
                }
                
                
                case("cg"): {
                    
                    while (true) {
                        System.out.println("*** Change the grammar ***");
                        System.out.println("Current grammar:");
                        System.out.println(g);
                        System.out.println("** Change grammar menu **");
                        System.out.println("to add a feature enter f");
                        System.out.println("to add a polarity enter p");
                        System.out.println("to add a word to the lexicon enter w");
                        System.out.println("to view the grammar, enter vg");
                        System.out.println("to go back to the main menu enter q");
                        System.out.print("\nYour choice:");

                        input = read.nextLine();
                        
                        if (input.equals("q")) {
                            break;
                        }
                        
                        switch (input) {
                            
                            case ("vg"): {
                                System.out.println(g);
                                break;
                            }
                            
                            case("f"): {
                                System.out.print("feature name (eg wh): ");
                                String value = read.nextLine();
                                System.out.println("choose sel/lic (merge feature/move feature)");
                                String set = read.nextLine();
                                g.addBareFeature(value, set);
                                g.generateFeatures();
                                break;
                                
                            }
                            
                            case("p"): {
                                System.out.println("Enter overt for overt move, \ncovert for covert move, \ncopy for copy move, or \ndel for delete-move");
                                input = read.nextLine();
                                switch (input) {
                                    
                                    case("overt"): {
                                        g.addPolarity(new Polarity("-","lic",-1,false,true,"overt"));
                                        System.out.println("overt move added");
                                        break;
                                    }
                                    case("covert"): {
                                        g.addPolarity(new Polarity("(-)","lic",-1,true,false,"covert"));
                                        System.out.println("covert move added");
                                        break;
                                    }
                                    case("copy"): {
                                        g.addPolarity(new Polarity("c","lic",-1,true,true,"copy"));
                                        System.out.println("copy move added");
                                        break;
                                    }
                                    case("del"): {
                                        g.addPolarity(new Polarity("0","lic",-1, false,false,"delete"));
                                        System.out.println("delete added");
                                        break;
                                    }
                                    default: {
                                        System.out.println("Invalid move type");
                                        break;
                                    }
                                }
                                g.generateFeatures(); // re-generate features
                                break;
                                
                                        
                            }
                            
                            case("w"): {
                                System.out.print("enter the string (the word proper): ");
                                String w = read.nextLine();
                                System.out.println("enter features by number from this list. Push enter again when you're done.");
                                g.printFeatures();
                                ArrayList<Integer> inds = new ArrayList<>();
                                
                                while (true) {
                                    input = read.nextLine();
                                    if (input.equals("")) {
                                        break;
                                    } else {
                                        // make sure it's a number
                                        try{
                                            int i = Integer.parseInt(input);
                                            inds.add(i);
                                        System.out.println(g.featureByNumber(i) + " added");
                                        }
                                        catch (NumberFormatException e) {
                                            g.printFeatures();
                                            System.out.println("\nMake sure you enter a number!");
                                            
                                        }
                                        
                                        
                                    }
                                }
                                
                                // find the features
                                ArrayList<Feature> fs = new ArrayList<>();
                                for (int n : inds) {
                                    fs.add(g.featureByNumber(n));    
                                }
                                g.addLexicalItem(w, new FeatureList(fs)); // add the word
                                
                                break;
                                
                            }
                        
                            
                        }
                        
                    }

                }
                
                
            }
        }
            
        
      
        //System.out.println(g);
        
        //System.out.println(g.getLexicon().get(0).check(selN));
        
        //System.out.println(g);
        
        
        
    }
    
    public static MG defaultMG() {
        
        MG g = new MG();
        
        List<Polarity> pols = Arrays.asList(new Polarity("-","lic",-1),new Polarity("+","lic",+1),new Polarity("","sel",-1),new Polarity("=","sel",+1));
        
        for (Polarity pol : pols) {
            g.addPolarity(pol);
        }
        
        //System.out.println(g);
        
        List<String> lic = Arrays.asList("wh", "foc", "nom");
        
        for (String f : lic) {
            g.addBareFeature(f,"lic");
        }
        
        //System.out.println(g);
        
        
        
        List<String> sel = Arrays.asList("N", "D", "V","T","C","A","P");
        
        for (String f : sel) {
            g.addBareFeature(f,"sel");
        }
        
        List<String> fin = Arrays.asList("T","C");
        
        for (String f : fin) {
            g.addFinal(f);
        }
        
        g.addAdjunct("A","N");
        g.addAdjunct("P", "N");
        g.addAdjunct("P", "V");
        g.addAdjunct("C", "N");
        
        g.changeAdjunctSide("P",false);
        g.changeAdjunctSide("C",false);

        
        g.generateFeatures();
        
        //System.out.println(g);
        
        
        
        
        //System.out.println(g);
        
//        Feature minwh = g.getFeatures().get(0);
//        Feature poswh = g.getFeatures().get(1);
//        Feature catN = g.getFeatures().get(6);
//        Feature selN = g.getFeatures().get(7);
//        Feature catD= g.getFeatures().get(8);
//        
        
        //g.printFeatures();
        
        
               
        g.addLexicalItem("the", new Integer[] {7,8,4}); // -nom
        g.addLexicalItem("the", new Integer[] {7,8}); 
        g.addLexicalItem("which", new Integer[] {7,8,0}); // -wh
        

        g.addLexicalItem("cat", new Integer[] {6});
        g.addLexicalItem("dog", new Integer[] {6});
        g.addLexicalItem("saw", new Integer[] {9,9,10});
        g.addLexicalItem("slept", new Integer[] {9,10});
        g.addLexicalItem("who", new Integer[] {8,0});
        g.addLexicalItem("who", new Integer[] {8,4,0});

        g.addLexicalItem("", new Integer[] {11,5,12}); // T
        g.addLexicalItem("", new Integer[] {13,1,14}); // C
        g.addLexicalItem("which", new Integer[] {7,8,4,0}); // -wh
        
        g.addLexicalItem("happy", new Integer[] {16});
        g.addLexicalItem("blue", new Integer[] {16});
        g.addLexicalItem("with", new Integer[] {9,18});
        g.addLexicalItem("on", new Integer[] {9,18});
        g.addLexicalItem("happy", new Integer[] {16,2});

  
        
        
        // user interface
        
        // show lexicon
        //g.printLexicon();
        
        // let's add everyting to a numeration as expressions
        //List<Expression> numeration = new ArrayList<>();
        
        
        
        return g;
        
    }
    
    
    
}
