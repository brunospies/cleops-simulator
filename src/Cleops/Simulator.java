/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cleops;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.nio.file.*;
/**
 *
 * @author bruno
 */
public class Simulator{
	private final DataBlock data_block;
	private final Assembler assembler;
        private int[] memory;
        private int instructions;

	public Simulator(){
		data_block = new DataBlock();
		assembler = new Assembler();
                memory = new int[256];
	}
        
        public void makeInstructions(String asm, int num_lines){
            assembler.status = "";
            memory = assembler.makeInstructions(asm, num_lines);
            
            for(int i=0;i<256;i++){
                data_block.writeMemory(i, (byte)memory[i]);
            }  
        }
        
        public boolean runStep(){
            instructions++;
            return data_block.runStep();
        }
        
        public void reset(){
            data_block.resetProcessor();
            instructions = 0;
            for(int i=0;i<256;i++){
                data_block.writeMemory(i, (byte)memory[i]);
            }
        }
        
        public String openFile(String path) throws FileNotFoundException{
            File arquivo = new File(path);
            return readFile(arquivo);
        }
        
        public void saveNewFile(String path, String text) throws IOException{
            String diretory = path+".asm";
            File arquivo = new File(diretory);
            saveFile(arquivo, text);       
        }
        
        public int[] getMem(){
            int[] memory_exec = new int[256];
            
            for(int i=0;i<256;i++){
                memory_exec[i] = data_block.readMemory(i);
            }
            return memory_exec;
        }
        
        public int getMemValue(int adress){
            return data_block.readMemory(adress) & 255;
        }
        
        public void saveFile(File arquivo, String text) throws IOException{
            try{
                try (FileWriter fw = new FileWriter(arquivo)) {
                    fw.write(text);
                }
            }catch(IOException e){}
        }
        
        public String readFile(File arquivo) throws FileNotFoundException{
            String data = "";
            try {
                List<String> lines = Files.readAllLines(arquivo.toPath());
                
                for(String line : lines){
                    data+= line+"\n";
                }
            }catch(IOException e){
                try{
                    List<String> lines = Files.readAllLines(arquivo.toPath(), Charset.forName("ISO-8859-1"));
                    
                    for(String line : lines){
                        data+= line+"\n";
                    }
                }catch(IOException ex){
                    data = "Não foi possível ler o Arquivo! Erro: "+ex.getMessage()+", "+e.getMessage();
                }
            }
            return data;
        }
        
        public String getInstruction(){
            return data_block.ir.getInstruction();
        }
        
        public int giveAC(){
            return data_block.ac.readData();
        }
        
        public int giveMDR(){
            return data_block.mdr.readData();
        }
        
        public int giveMAR(){
            return data_block.mar.readData();
        }
        
        public int giveRS(){
            return data_block.rs.readData();
        }
        
        public int givePC(){
            return data_block.pc.readData();
        }
        
        public int giveIR(){
            return data_block.ir.readData();
        }
        
        public int giveCiclos(){
            return data_block.ciclo;
        }
        
        public boolean giveZ(){
            return data_block.giveZ();
        }
        
        public boolean giveN(){
            return data_block.giveN();
        }
        
        public boolean giveC(){
            return data_block.giveC();
        }
        
        public boolean giveV(){
            return data_block.giveV();
        }
        
        public String giveAssemblerMessage(){
            return assembler.status;
        }
        
        public List<Label> giveLabels(){
            return assembler.labels;
        }
        
        public int giveInstructions(){
            return instructions;
        }
       
}