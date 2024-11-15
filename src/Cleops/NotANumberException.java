/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cleops;

/**
 *
 * @author bruno
 */
public class NotANumberException extends Exception{
    
    public NotANumberException(){
        super("Caracter não numérico!");
    }
}
