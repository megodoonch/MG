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
public class LexCopy {
    private Lex li;
    
    public LexCopy(Lex li){
        this.li = li;
    }
    public void setStr(Lex li){
        this.li = li;
    }
    public void display(){
        System.out.println("The LI is " + li);
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
