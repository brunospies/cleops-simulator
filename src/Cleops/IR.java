/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cleops;

/**
 *
 * @author bruno
 */
public class IR extends Register{
    String instruction;
    
    public IR(){
        super();
    }
    
    @Override
    public void writeData(byte data){
        instruction = decodeInstruction(data);
        super.writeData(data);
    }
    
    public String getInstruction(){
        return instruction;
    }
    
    private String decodeInstruction(byte data){
        int instruction = data&240;
        int mode = data&12;
        String inst = new String();
        switch (instruction) {
                       case 240 -> {
                           //HLT
                           inst = "HLT";
                       }
                       case 0 -> {
                           //NOT
                           inst = "NOT";
                       }
                       case 32 -> {
                           switch (mode) {
                               case 4 -> {
                                   //Direto
                                   inst = "STA";
                               }
                               case 8 -> {
                                   //Indireto
                                   inst = "STA I";
                               }
                               case 12 -> {
                                   //Relativo
                                   inst = "STA R";
                               }
                               default -> //EXEPTION
                               {}   
                           }
                       }
                       case 64 -> {
                           //LDA
                          
                           switch (mode) {
                               case 0 -> {
                                   inst = "LDA #";
                               }
                               case 4 -> {
                                   //Direto
                                   inst = "LDA";
                               }
                               case 8 -> {
                                   //Indireto
                                   inst = "LDA I";
                               }
                               case 12 -> {
                                   //Relativo
                                   inst = "LDA R";
                               }
                               default -> {
                               }
                           }
                           //EXEPTION
                           //--------
                       }
                       case 80 -> {
                           //ADD
                           switch (mode) {
                               case 0 -> {
                                   //Imediato
                                   inst = "ADD #";
                               }
                               case 4 -> {
                                   //Direto
                                   inst = "ADD";
                               }
                               case 8 -> {
                                   //Indireto
                                   inst = "ADD I";
                               }
                               case 12 -> {
                                   //Relativo
                                   inst = "ADD R";
                               }
                               default -> {
                               }
                           }
                           //EXEPTION
                           //--------
                       }
                       case 96 -> {
                           //OR
                           switch (mode) {
                               case 0 -> {
                                   //Imediato
                                   inst = "OR #";
                               }
                               case 4 -> {
                                   //Direto
                                   inst = "OR";
                               }
                               case 8 -> {
                                   //Indireto
                                   inst = "OR I";
                               }
                               case 12 -> {
                                   //Relativo
                                   inst = "OR R";
                               }
                               default -> {
                               }
                           }
                           //EXEPTION
                           //--------
                       }
                       case 112 -> {
                           //AND
                           switch (mode) {
                               case 0 -> {
                                   //Imediato
                                   inst = "AND #";
                               }
                               case 4 -> {
                                   //Direto
                                   inst = "AND";
                               }
                               case 8 -> {
                                   //Indireto
                                   inst = "AND I";
                               }
                               case 12 -> {
                                   //Relativo
                                   inst = "AND R";
                               }
                               default -> {
                               }
                           }
                           //EXEPTION
                           //--------
                       }
                       case 128 -> {
                           //JMP
                           switch (mode) {
                               case 4 -> {
                                   //Direto
                                   inst = "JMP";
                               }
                               case 12 -> {
                                   //Relativo
                                   inst = "JMP R";
                               }
                               default -> {
                               }
                           }
                           //EXEPTION
                           //--------
                       }
                       case 144 -> {
                           //JC
                           switch (mode) {
                               case 4 -> {
                                   //Direto
                                   inst = "JC";
                               }
                               case 12 -> {
                                   //Relativo
                                   inst = "JC R";
                               }
                               default -> {
                               }
                           }
                           //EXEPTION
                           //--------
                       }
                       case 224 -> {
                           //JV
                           switch (mode) {
                               case 4 -> {
                                   //Direto
                                   inst = "JV";
                               }
                               case 12 -> {
                                   //Relativo
                                   inst = "JV R";
                               }
                               default -> {
                               }
                           }
                           //EXEPTION
                           //--------
                       }
                       case 160 -> {
                           //JN
                           switch (mode) {
                               case 4 -> {
                                   //Direto
                                   inst = "JN";
                               }
                               case 12 -> {
                                   //Relativo
                                   inst = "JN R";
                               }
                               default -> {
                               }
                           }
                           //EXEPTION
                           //--------
                       }
                       case 176 -> {
                           //JZ
                           switch (mode) {
                               case 4 -> {
                                   //Direto
                                   inst = "JZ";
                               }
                               case 12 -> {
                                   //Relativo
                                   inst = "JZ R";
                               }
                               default -> {
                               }
                           }
                           //EXEPTION
                           //--------
                       }
                       case 192 -> {
                           //JSR
                           switch (mode) {
                               case 4 -> {
                                   //Direto
                                   inst = "JSR";
                               }
                               case 12 -> {
                                   //Relativo
                                   inst = "JSR R";
                               }
                               default -> {
                               }
                           }
                           //EXEPTION
                           //--------
                       }
                       case 208 -> {
                           //RTS
                           inst = "RTS";
                       }
                       case 16 -> {
                           //SYSCALL
                           if(mode==0){
                               inst = "SYSCALL";
                           }else if(mode==4){
                               inst = "SYSCALL";
                           }
                       }
                       default -> {
                       }
                    }
        return inst;
    }
    
}
