/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cleops;

/**
 *
 * @author bruno
 */
public class Register{
	byte data;

	public Register(){
		this.data = ((byte)(0));
	}

	public void writeData(byte data){
		this.data = data;
	}

	public byte readData(){
		return data;
	}
}
