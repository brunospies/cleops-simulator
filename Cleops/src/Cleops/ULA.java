/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cleops;

/**
 *
 * @author bruno
 */
public class ULA{
	public FlipFlop	z;
        public FlipFlop v;
        public FlipFlop n;
        public FlipFlop c;

	public ULA(){
		z = new FlipFlop();
		v = new FlipFlop();
		n = new FlipFlop();
		c = new FlipFlop();
	}

	public void wrZVNC(byte data1, byte data2, byte result){
		byte b1, b2, b3;

		b1 = ((byte)(data1 & 128));
		b2 = ((byte)(data2 & 128));
		b3 = ((byte)(result & 128));
		
		if(result==0){ 
			z.writeData(true);
		}else{
			z.writeData(false);
		}
			
		if(b3==0){
			n.writeData(false);
		}else{
			n.writeData(true);
		}
	
		if(((b1&b2)|(b1&~b3)|(b2&~b3))==0){
			c.writeData(false);
		}else{
 			c.writeData(true);
		}

		if(((~b1&~b2&b3)|(b1&b2&~b3))==0){
			v.writeData(false);
		}else{
			v.writeData(true);
		}
	}

	public void wrZVNC(byte result){

		if(result==0){
			z.writeData(true);
		}else{
			z.writeData(false);
		}

		if((result&((byte)(128)))==0){
			n.writeData(false);
		}else{
			n.writeData(true);
		}

		c.writeData(false);
		v.writeData(false);
	}

	public byte add(byte data1, byte data2){
		byte result = ((byte)(data1 + data2));
		wrZVNC(data1, data2, result);

		return result;
	}

	public byte or(byte data1, byte data2){
		byte result = ((byte)(data1 | data2));
		wrZVNC(data1, data2, result);

		return result;
	}

	public byte and(byte data1, byte data2){
		byte result = ((byte)(data1 & data2));
		wrZVNC(data1, data2, result);

		return result;
	}

	public byte not(byte data){
		byte result = ((byte)(~data));
		wrZVNC(result);

		return result;
	}

	public byte pass(byte data){
		byte result = data;
		wrZVNC(result);

		return result;
	}
	
}
	
