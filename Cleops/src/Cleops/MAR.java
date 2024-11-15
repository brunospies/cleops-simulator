/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cleops;

/**
 *
 * @author bruno
 */
public class MAR extends Register{
	int adress;

	public MAR(){
		super();
		adress = 0;
	}

	public void writeData(byte data){
		super.writeData(data);
		adress = data & 255; 	//unsigned conversion
	}
}
