/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cleops;

/**
 *
 * @author bruno
 */
public class FlipFlop{
	boolean data;

	public FlipFlop(){
		this.data = false;
	}
	
	public void writeData(boolean data){
		this.data = data;
	}

	public boolean readData(){
		return this.data;
	}
}
