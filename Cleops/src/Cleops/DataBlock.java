/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package Cleops;

import javax.swing.JOptionPane;


/**
 *
 * @author bruno
 */
public class DataBlock{
       	public  ULA ula;
	public Register mdr, pc, ac, rs;
        public IR ir;
	public MAR mar;
	public Memory memory;
	public int ciclo;

	public DataBlock(){
		ula = new ULA();
		mdr = new Register();
		pc = new Register();
		ac = new Register();
		rs = new Register();
		ir = new IR();
		mar = new MAR();
		memory = new Memory();
		ciclo = 0;
	}

	public void resetProcessor(){
		mdr.writeData(((byte)(0)));
		pc.writeData(((byte)(0)));
		ac.writeData(((byte)(0)));
		rs.writeData(((byte)(0)));
		ir.writeData(((byte)(0)));
		mar.writeData(((byte)(0)));
		ciclo=0;
                ula.c.writeData(false);
                ula.n.writeData(false);
                ula.v.writeData(false);
                ula.z.writeData(false);
		memory.resetMemory();
	}

	public void resetPc(){
		pc.writeData((byte)0);
	}
	
	private void opcodeFetch(){
		mar.writeData(pc.readData());                        //MAR <= PC;
		ciclo+=1; 
		mdr.writeData(memory.readData(mar.adress));          //MDR <= MEM(MAR);
		pc.writeData((byte)(pc.readData()+(byte)1));         //PC++;
		ciclo+=1;
		ir.writeData(mdr.readData());                        //ir <= instruction;
	}

	public boolean runStep(){
		int instruction;
		int mode;
		
		opcodeFetch();
		instruction = ir.readData()&240;
		mode = ir.readData()&12;
                try{
                   switch (instruction) {
                       case 240 -> {
                           // HLT
                           ciclo+=1;
                           return false;
                       }
                       case 0 -> {
                           //NOT
                           ac.writeData(ula.not(ac.readData())); //AC <= ~AC;
                           ciclo+=1;
                       }
                       case 32 -> {
                           //STA
                           mar.writeData(pc.readData()); // MAR <= PC; 
                           ciclo+=1;
                           mdr.writeData(memory.readData(mar.adress)); //MDR <= MEM(MAR);
                           pc.writeData((byte)(pc.readData()+(byte)1));   //PC++;
                           ciclo+=1;
                           switch (mode) {
                               case 4 -> {
                                   //Direto
                                   mar.writeData(mdr.readData()); // MAR <= MDR;
                                   ciclo+=1;
                                   memory.writeData(mar.adress, ac.readData()); //MEM(MAR) <= AC;
                               }
                               case 8 -> {
                                   //Indireto
                                   mar.writeData(mdr.readData()); // MAR <= MDR;
                                   ciclo+=1;
                                   mdr.writeData(memory.readData(mar.adress));  //MDR <= MEM(MAR);
                                   ciclo+=1;
                                   mar.writeData(mdr.readData()); //MAR <= MDR;
                                   ciclo+=1;
                                   memory.writeData(mar.adress, ac.readData()); //MEM(MAR) <= AC;
                                   ciclo+=1;
                               }
                               case 12 -> {
                                   //Relativo
                                   mar.writeData((byte)(mdr.readData()+pc.readData())); //MAR <= MDR+PC;
                                   ciclo+=1;
                                   memory.writeData(mar.adress, ac.readData());  // MEM(MAR) <= AC;
                                   ciclo+=1;
                               }
                               default -> //EXEPTION
                                   System.out.println("STA r");
                                   //--------
                           }
                       }
                       case 64 -> {
                           //LDA
                           mar.writeData(pc.readData()); // MAR <= PC; 
                           ciclo+=1;
                           mdr.writeData(memory.readData(mar.adress)); //MDR <= MEM(MAR);
                           pc.writeData((byte)(pc.readData()+(byte)1));   //PC++;
                           ciclo+=1;
                           switch (mode) {
                               case 0 -> {
                                   //Imediato
                                   ac.writeData(ula.pass(mdr.readData()));  //AC <= MDR, wrnz;
                                   ciclo+=1;
                               }
                               case 4 -> {
                                   //Direto
                                   mar.writeData(mdr.readData());  //MAR <= MDR;
                                   ciclo+=1;
                                   mdr.writeData(memory.readData(mar.adress)); //MDR <= MEM(MAR);
                                   ciclo+=1;
                                   ac.writeData(ula.pass(mdr.readData())); //AC <= MDR, wrnz;
                                   ciclo+=1;
                               }
                               case 8 -> {
                                   //Indireto
                                   mar.writeData(mdr.readData());  //MAR <= MDR;
                                   ciclo+=1;
                                   mdr.writeData(memory.readData(mar.adress)); //MDR <= MEM(MAR);
                                   ciclo+=1;
                                   mar.writeData(mdr.readData());  //MAR <= MDR;
                                   ciclo+=1;
                                   mdr.writeData(memory.readData(mar.adress)); //MDR <= MEM(MAR);
                                   ciclo+=1;
                                   ac.writeData(ula.pass(mdr.readData()));   //AC <= MDR, wrnz;
                                   ciclo+=1;
                               }
                               case 12 -> {
                                   //Relativo
                                   mar.writeData((byte)(mdr.readData()+ pc.readData())); //MAR <= MDR+PC;
                                   ciclo+=1;
                                   mdr.writeData(memory.readData(mar.adress)); //MDR <= MEM(MAR);
                                   ciclo+=1;
                                   ac.writeData(ula.pass(mdr.readData())); //AC <= MDR, wrnz;
                                   ciclo+=1;
                               }
                               default -> {
                               }
                           }
                           //EXEPTION
                           //--------
                       }
                       case 80 -> {
                           //ADD
                           mar.writeData(pc.readData()); // MAR <= PC; 
                           ciclo+=1;
                           mdr.writeData(memory.readData(mar.adress)); //MDR <= MEM(MAR);
                           pc.writeData((byte)(pc.readData()+(byte)1));   //PC++;
                           ciclo+=1;
                           switch (mode) {
                               case 0 -> {
                                   //Imediato
                                   ac.writeData(ula.add(ac.readData(), mdr.readData())); //AC <= AC+MDR, wrnzcv;
                                   ciclo+=1;
                               }
                               case 4 -> {
                                   //Direto
                                   mar.writeData(mdr.readData());  //MAR <= MDR;
                                   ciclo+=1;
                                   mdr.writeData(memory.readData(mar.adress)); //MDR <= MEM(MAR);
                                   ciclo+=1;
                                   ac.writeData(ula.add(ac.readData(), mdr.readData())); //AC <= AC+MDR, wrnzcv;
                                   ciclo+=1;
                               }
                               case 8 -> {
                                   //Indireto
                                   mar.writeData(mdr.readData());  //MAR <= MDR;
                                   ciclo+=1;
                                   mdr.writeData(memory.readData(mar.adress)); //MDR <= MEM(MAR);
                                   ciclo+=1;
                                   mar.writeData(mdr.readData());  //MAR <= MDR;
                                   ciclo+=1;
                                   mdr.writeData(memory.readData(mar.adress)); //MDR <= MEM(MAR);
                                   ciclo+=1;
                                   ac.writeData(ula.add(ac.readData(), mdr.readData())); //AC <= AC+MDR, wrnzcv;
                                   ciclo+=1;
                               }
                               case 12 -> {
                                   //Relativo
                                   mar.writeData((byte)(mdr.readData()+ pc.readData())); //MAR <= MDR+PC;
                                   ciclo+=1;
                                   mdr.writeData(memory.readData(mar.adress)); //MDR <= MEM(MAR);
                                   ciclo+=1;
                                   ac.writeData(ula.add(ac.readData(), mdr.readData())); //AC <= AC+MDR, wrnzcv;
                                   ciclo+=1;
                               }
                               default -> {
                               }
                           }
                           //EXEPTION
                           //--------
                       }
                       case 96 -> {
                           //OR
                           mar.writeData(pc.readData()); // MAR <= PC; 
                           ciclo+=1;
                           mdr.writeData(memory.readData(mar.adress)); //MDR <= MEM(MAR);
                           pc.writeData((byte)(pc.readData()+(byte)1));   //PC++;
                           ciclo+=1;
                           switch (mode) {
                               case 0 -> {
                                   //Imediato
                                   ac.writeData(ula.or(ac.readData(), mdr.readData())); //AC <= AC|MDR, wrnzcv;
                                   ciclo+=1;
                               }
                               case 4 -> {
                                   //Direto
                                   mar.writeData(mdr.readData());  //MAR <= MDR;
                                   ciclo+=1;
                                   mdr.writeData(memory.readData(mar.adress)); //MDR <= MEM(MAR);
                                   ciclo+=1;
                                   ac.writeData(ula.or(ac.readData(), mdr.readData())); //AC <= AC|MDR, wrnzcv;
                                   ciclo+=1;
                               }
                               case 8 -> {
                                   //Indireto
                                   mar.writeData(mdr.readData());  //MAR <= MDR;
                                   ciclo+=1;
                                   mdr.writeData(memory.readData(mar.adress)); //MDR <= MEM(MAR);
                                   ciclo+=1;
                                   mar.writeData(mdr.readData());  //MAR <= MDR;
                                   ciclo+=1;
                                   mdr.writeData(memory.readData(mar.adress)); //MDR <= MEM(MAR);
                                   ciclo+=1;
                                   ac.writeData(ula.or(ac.readData(), mdr.readData())); //AC <= AC|MDR, wrnzcv;
                                   ciclo+=1;
                               }
                               case 12 -> {
                                   //Relativo
                                   mar.writeData((byte)(mdr.readData()+ pc.readData())); //MAR <= MDR+PC;
                                   ciclo+=1;
                                   mdr.writeData(memory.readData(mar.adress)); //MDR <= MEM(MAR);
                                   ciclo+=1;
                                   ac.writeData(ula.or(ac.readData(), mdr.readData())); //AC <= AC|MDR, wrnzcv;
                                   ciclo+=1;
                               }
                               default -> {
                               }
                           }
                           //EXEPTION
                           //--------
                       }
                       case 112 -> {
                           //AND
                           mar.writeData(pc.readData()); // MAR <= PC; 
                           ciclo+=1;
                           mdr.writeData(memory.readData(mar.adress)); //MDR <= MEM(MAR);
                           pc.writeData((byte)(pc.readData()+(byte)1));   //PC++;
                           ciclo+=1;
                           switch (mode) {
                               case 0 -> {
                                   //Imediato
                                   ac.writeData(ula.and(ac.readData(), mdr.readData())); //AC <= AC&MDR, wrnzcv;
                                   ciclo+=1;
                               }
                               case 4 -> {
                                   //Direto
                                   mar.writeData(mdr.readData());  //MAR <= MDR;
                                   ciclo+=1;
                                   mdr.writeData(memory.readData(mar.adress)); //MDR <= MEM(MAR);
                                   ciclo+=1;
                                   ac.writeData(ula.and(ac.readData(), mdr.readData())); //AC <= AC&MDR, wrnzcv;
                                   ciclo+=1;
                               }
                               case 8 -> {
                                   //Indireto
                                   mar.writeData(mdr.readData());  //MAR <= MDR;
                                   ciclo+=1;
                                   mdr.writeData(memory.readData(mar.adress)); //MDR <= MEM(MAR);
                                   ciclo+=1;
                                   mar.writeData(mdr.readData());  //MAR <= MDR;
                                   ciclo+=1;
                                   mdr.writeData(memory.readData(mar.adress)); //MDR <= MEM(MAR);
                                   ciclo+=1;
                                   ac.writeData(ula.and(ac.readData(), mdr.readData())); //AC <= AC&MDR, wrnzcv;
                                   ciclo+=1;
                               }
                               case 12 -> {
                                   //Relativo
                                   mar.writeData((byte)(mdr.readData()+ pc.readData())); //MAR <= MDR+PC;
                                   ciclo+=1;
                                   mdr.writeData(memory.readData(mar.adress)); //MDR <= MEM(MAR);
                                   ciclo+=1;
                                   ac.writeData(ula.and(ac.readData(), mdr.readData())); //AC <= AC&MDR, wrnzcv;
                                   ciclo+=1;
                               }
                               default -> {
                               }
                           }
                           //EXEPTION
                           //--------
                       }
                       case 128 -> {
                           //JMP
                           mar.writeData(pc.readData()); // MAR <= PC; 
                           ciclo+=1;
                           mdr.writeData(memory.readData(mar.adress)); //MDR <= MEM(MAR);
                           pc.writeData((byte)(pc.readData()+(byte)1));   //PC++;
                           ciclo+=1;
                           switch (mode) {
                               case 4 -> {
                                   //Direto
                                   
                                   pc.writeData(mdr.readData()); //PC <= MDR;
                                   ciclo+=1;
                               }
                               case 12 -> {
                                   //Relativo
                                   pc.writeData((byte)(mdr.readData()+ pc.readData()));  //PC <= MDR+PC;
                                   ciclo+=1;
                               }
                               default -> {
                               }
                           }
                           //EXEPTION
                           //--------
                       }
                       case 144 -> {
                           //JC
                           mar.writeData(pc.readData()); // MAR <= PC; 
                           ciclo+=1;
                           mdr.writeData(memory.readData(mar.adress)); //MDR <= MEM(MAR);
                           pc.writeData((byte)(pc.readData()+(byte)1));   //PC++;
                           ciclo+=1;
                           switch (mode) {
                               case 4 -> {
                                   //Direto
                                   if (giveC()) {pc.writeData(mdr.readData()); ciclo+=1;} //PC <= MDR;
                               }
                               case 12 -> {
                                   //Relativo
                                   if (giveC()) {pc.writeData((byte)(mdr.readData()+ pc.readData())); ciclo+=1;} //PC <= MDR+PC;
                               }
                               default -> {
                               }
                           }
                           //EXEPTION
                           //--------
                       }
                       case 224 -> {
                           //JV
                           mar.writeData(pc.readData()); // MAR <= PC; 
                           ciclo+=1;
                           mdr.writeData(memory.readData(mar.adress)); //MDR <= MEM(MAR);
                           pc.writeData((byte)(pc.readData()+(byte)1));   //PC++;
                           ciclo+=1;
                           switch (mode) {
                               case 4 -> {
                                   //Direto
                                   if (giveV()) {pc.writeData(mdr.readData()); ciclo+=1;} //PC <= MDR;
                               }
                               case 12 -> {
                                   //Relativo
                                   if (giveV()) {pc.writeData((byte)(mdr.readData()+ pc.readData())); ciclo+=1;} //PC <= MDR+PC;
                               }
                               default -> {
                               }
                           }
                           //EXEPTION
                           //--------
                       }
                       case 160 -> {
                           //JN
                           mar.writeData(pc.readData()); // MAR <= PC; 
                           ciclo+=1;
                           mdr.writeData(memory.readData(mar.adress)); //MDR <= MEM(MAR);
                           pc.writeData((byte)(pc.readData()+(byte)1));   //PC++;
                           ciclo+=1;
                           switch (mode) {
                               case 4 -> {
                                   //Direto
                                   if (giveN()) {pc.writeData(mdr.readData()); ciclo+=1;} //PC <= MDR;
                               }
                               case 12 -> {
                                   //Relativo
                                   if (giveN()) {pc.writeData((byte)(mdr.readData()+ pc.readData())); ciclo+=1;}  //PC <= MDR+PC;
                               }
                               default -> {
                               }
                           }
                           //EXEPTION
                           //--------
                       }
                       case 176 -> {
                           //JZ
                           mar.writeData(pc.readData()); // MAR <= PC; 
                           ciclo+=1;
                           mdr.writeData(memory.readData(mar.adress)); //MDR <= MEM(MAR);
                           pc.writeData((byte)(pc.readData()+(byte)1));   //PC++;
                           ciclo+=1;
                           switch (mode) {
                               case 4 -> {
                                   //Direto
                                   if (giveZ()) {pc.writeData(mdr.readData()); ciclo+=1;} //PC <= MDR;
                               }
                               case 12 -> {
                                   //Relativo
                                   if (giveZ()) {pc.writeData((byte)(mdr.readData()+ pc.readData())); ciclo+=1;} //PC <= MDR+PC;
                               }
                               default -> {
                               }
                           }
                           //EXEPTION
                           //--------
                       }
                       case 192 -> {
                           //JSR
                           mar.writeData(pc.readData()); // MAR <= PC; 
                           ciclo+=1;
                           mdr.writeData(memory.readData(mar.adress)); //MDR <= MEM(MAR);
                           pc.writeData((byte)(pc.readData()+(byte)1));   //PC++;
                           ciclo+=1;
                           rs.writeData(pc.readData()); //RS <= PC;
                           ciclo+=1;
                           switch (mode) {
                               case 4 -> {
                                   //Direto
                                   pc.writeData(mdr.readData()); //PC <= MDR;
                                   ciclo+=1;
                               }
                               case 12 -> {
                                   //Relativo
                                   pc.writeData((byte)(mdr.readData()+ pc.readData()));  //PC <= MDR+PC;
                                   ciclo+=1;
                               }
                               default -> {
                               }
                           }
                           //EXEPTION
                           //--------
                       }
                       case 208 -> {
                           //RTS
                           pc.writeData(rs.readData()); //PC <= RS;
                           ciclo+=1;
                       }
                       case 16 -> {
                           //SYSCALL
                           if(mode==0){
                               
                           }else if(mode==4){
                               
                           }
                       }
                       default -> {
                           throw new InstructionException();
                       }
                    }
                }catch(InstructionException e){
                    JOptionPane.showMessageDialog(null, e.getMessage());
                    return false;
                }
                   //EXEPTION INSTR
                   //-----------
		return true;	
	}

	public byte readMemory(int adress){
		return	memory.readData(adress);
	}

	public void writeMemory(int adress, byte data){
		memory.writeData(adress, data);
	}

	public boolean giveZ(){
		return ula.z.readData();
	}
	
	public boolean giveN(){
		return ula.n.readData();
	}

	public boolean giveC(){
		return ula.c.readData();
	}

	public boolean giveV(){
		return ula.v.readData();
	}
}	
	