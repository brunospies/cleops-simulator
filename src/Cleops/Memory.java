/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cleops;

/**
 *
 * @author bruno
 */
public class Memory{
	byte data[];

	public Memory(){
		data = new byte[256];
	}

	public void writeData(int adress, byte data){
		this.data[adress] = data;
	}

	public byte readData(int adress){
		return this.data[adress];
	}
	
	public void resetMemory(){
		int i;
		for (i=0;i<256;i++){
			this.data[i] = ((byte)(0));
		}
	}
}


