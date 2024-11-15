/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cleops;

/**
 *
 * @author bruno
 */
public class Label {
    private final String label;
    private final int end;
    
    public Label(String label, int end){
        this.label = label;
        this.end = end;
    }
    
    public String getLabel(){
        return label;
    }
    
    public int getEnd(){
        return end;
    }   
}
