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
public class Feature {
    
    private Polarity polarity; // eg +,-,=
    private String value;    // eg wh
    private int number;

    public Feature(Polarity polarity, String value, int number) {
        this.polarity = polarity;
        this.value = value;
        this.number = number;
        
    }

    public Polarity getPolarity() {
        return polarity;
    }

    public String getValue() {
        return value;
    }

    public int getNumber() {
        return number;
    }

    
    
    @Override
    public String toString() {
        return polarity+value;
    }

    public boolean licensing(MG g) {
        return g.getLicPolarities().contains(this.polarity);
    }
    
    public boolean selectional(MG g) {
        return g.getSelPolarities().contains(this.polarity);
    }
  
    public int getIntValue() {
        return this.polarity.getIntValue();
    }
    
    public String getSet() {
        return this.polarity.getSet();
    }
    
    public boolean match(Feature otherFeature) { // two features match if they have the same barefeature and their integer values sum to 0, because one is -1 and the other is +1
        return (this.polarity.getIntValue()==1  // this is a positive feature
                && this.value.equals(otherFeature.value)  // the features are the same
                && this.getIntValue() + otherFeature.getIntValue() == 0) ; // the polarities are opposite
    }
}
