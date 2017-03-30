/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MG;

import java.util.ArrayList;

/**
 *
 * @author meaghanfowlie
 */
public class Cell {
    
    private ArrayList<CellEntry> entries;
    // indices of the cell
    private int start;
    private int end;
    
    public Cell(int i,int j) {
        this.start=i;
        this.end=j;
        this.entries = new ArrayList<>();
    }
    
    public void addEntry(CellEntry entry) {
        this.entries.add(entry);
        
    }

    public ArrayList<CellEntry> getEntries() {
        return entries;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "Cell{" + "entries=" + entries + ", start=" + start + ", end=" + end + '}';
    }
    
     
    
    
    
}
