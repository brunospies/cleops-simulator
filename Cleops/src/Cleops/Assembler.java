/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
 
package Cleops;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bruno
 */
public class Assembler{
	public String status;
        public List<Label> labels;
		
	public Assembler(){
		status = "";
        }

	public int[] makeInstructions(String asm){
		int memory[] = new int[256];
                List<Label> labels_new = new ArrayList<>();
                boolean hlt_verification = true;
		int num_lines, pc=0, i, j, l, c, k, pcd=0, num_words, words_max= 40; 
		num_lines = contaOcorrencias('\n', asm);
		num_lines++;

		String number[] = new String[5];
		String label = new String();
 		String words[][] = new String[num_lines][words_max];
                asm = asm.replace("\t", " ");

		for(i=0;i<num_lines;i++){
			for(j=0;j<words_max;j++){
				words[i][j] = "";
			}
		}

		String[] lines = asm.split("\n"); // Separa em linhas 


		i=0;		
                
		//Gera a matriz String de palavras
		for(i=0;i<num_lines;i++){
			String[] word = lines[i].split(" ", words_max);
			num_words = contaOcorrencias(' ', lines[i]) + 1;
                        if (num_words>words_max) num_words=words_max;
			for(j=0;j<num_words;j++){
				words[i][j] = word[j];
				if(words[i][j].compareToIgnoreCase("db")==0){
					for(c=j+1;c<num_words;c++){
						if(word[c].startsWith("#")) word[c] = word[c].replace("#", "");
					}
				}
				if(words[i][j].startsWith("#")) break;
				if(words[i][j].startsWith("//")) break;
				if(words[i][j].startsWith(".")) break;
				if(words[i][j].startsWith(";")) break;
			}
		}
		
		for(i=0;i<num_lines;i++){
			for(j=0;j<words_max;j++){
				if(words[i][j].length()==0) continue;
				if(words[i][j].startsWith("//")) break;
				if(words[i][j].startsWith(".")) break;
				if(words[i][j].startsWith("#")) break;
				if(words[i][j].startsWith(";")) break;
				if(words[i][j].compareToIgnoreCase("org")==0){
					j++;
					pcd = getNumber(words[i][j], i);
					if (pcd>255) status += "Endereço muito grande ou não identificado! linha:"+(i+1)+"\n\n"; //adress too large
				}
				if(words[i][j].compareToIgnoreCase("lda")==0){
					pcd+=2;
				}else if(words[i][j].compareToIgnoreCase("sta")==0){
					pcd+=2;
				}else if(words[i][j].compareToIgnoreCase("add")==0){
					pcd+=2;
				}else if(words[i][j].compareToIgnoreCase("or")==0){
					pcd+=2;
				}else if(words[i][j].compareToIgnoreCase("and")==0){
					pcd+=2;
				}else if(words[i][j].compareToIgnoreCase("jmp")==0){
					pcd+=2;
				}else if(words[i][j].compareToIgnoreCase("jc")==0){
					pcd+=2;
				}else if(words[i][j].compareToIgnoreCase("jn")==0){
					pcd+=2;
				}else if(words[i][j].compareToIgnoreCase("jz")==0){
					pcd+=2;
				}else if(words[i][j].compareToIgnoreCase("jsr")==0){
					pcd+=2;
				}else if(words[i][j].compareToIgnoreCase("jv")==0){
					pcd+=2;
				}else if(words[i][j].compareToIgnoreCase("not")==0){
					pcd++;
				}else if(words[i][j].compareToIgnoreCase("rts")==0){
					pcd++;
				}else if(words[i][j].compareToIgnoreCase("hlt")==0){
					pcd++;
				}else if(words[i][j].compareToIgnoreCase("syscall")==0){
					pcd++;
				}else if(words[i][j].endsWith(":")){
					label = getLabel(words[i][j]);
                                        labels_new.add(new Label(label, pcd));
					for(l=0;l<num_lines;l++){
						for(c=0;c<words_max;c++){
							if(words[l][c].startsWith("#")){
								number[0] = words[l][c].replace("#", "");
							}else{
								number = words[l][c].split(",");
							}
							if(number[0].compareTo(label)==0){
								words[l][c] = words[l][c].replace(label, ""+pcd);
							}
						}
					}
					for(k=j+1;k<words_max;k++){
						if(words[i][k].compareToIgnoreCase("db")==0){
							for(c=k+1;c<words_max;c++){
								if(words[i][c].length()==0) continue;
								if(words[i][c].startsWith("//")) break;
								if(words[i][c].startsWith(".")) break;
								if(words[i][c].startsWith(";")) break;
								if(words[i][c].endsWith(",")) words[i][c] = words[i][c].replace(",", "");
								memory[pcd] = getNumber(words[i][c], i);
								pcd++;
								
							}
							words[i][j] = "//";
							break;
						}else if(words[i][k].length()==0){
						}else{
                                                    status += "Palavra não identificada! linha: "+(i+1)+"\n\n"; 
                                                    break;
                                                }
					}
					if(!"//".equals(words[i][j])) words[i][j] = "";
				}
				break;		
			}
		}
		
		if(pcd==0) status += "Código sem Instruções!\n\n"; //No instructions in code
		
		for(i=0;i<num_lines;i++){
			for(j=0;j<words_max;j++){
				if(words[i][j].length()==0) continue;
				if(words[i][j].startsWith("//")) break;
				if(words[i][j].startsWith(".")) break;
				if(words[i][j].startsWith("#")) break;
				if(words[i][j].startsWith(";")) break;
				if(words[i][j].compareToIgnoreCase("org")==0) break;
				if(words[i][j].compareToIgnoreCase("lda")==0){
					j++;
					if(words[i][j].startsWith("#")){
						memory[pc] = 64;
						pc++;
						memory[pc] = getNumber(words[i][j], i);
						pc++;
					}else if(words[i][j].endsWith("i")||words[i][j].endsWith("I")){
						memory[pc] = 72;
						pc++;
						number = words[i][j].split(",");
						memory[pc] = getNumber(number[0], i);
						pc++;
					}else if(words[i][j].endsWith("r")||words[i][j].endsWith("R")){
						memory[pc] = 76;
						pc++;
						number = words[i][j].split(",");
						memory[pc] = getNumber(number[0], i)-pc-1;
						pc++;
					}else{
						memory[pc] = 68;
						pc++;
						memory[pc] = getNumber(words[i][j], i);
						pc++;
					}

                                        verificaNumero(memory[pc-1], i);
				}else if(words[i][j].compareToIgnoreCase("sta")==0){
					j++;
					if(words[i][j].endsWith("i")||words[i][j].endsWith("I")){
						memory[pc] = 40;
						pc++;
						number = words[i][j].split(",");
						memory[pc] = getNumber(number[0], i);
						pc++;
					}else if(words[i][j].endsWith("r")||words[i][j].endsWith("R")){
						memory[pc] = 44;
						pc++;
						number = words[i][j].split(",");
						memory[pc] = getNumber(number[0], i)-pc-1;
						pc++;
					}else{
						memory[pc] = 36;
						pc++;
						memory[pc] = getNumber(words[i][j], i);
						pc++;
					}

                                        verificaNumero(memory[pc-1], i);
                                        
				}else if(words[i][j].compareToIgnoreCase("add")==0){
					j++;
					if(words[i][j].startsWith("#")){
						memory[pc] = 80;
						pc++;
						memory[pc] = getNumber(words[i][j], i);
						pc++;
					}else if(words[i][j].endsWith("i")||words[i][j].endsWith("I")){
						memory[pc] = 88;
						pc++;
						number = words[i][j].split(",");
						memory[pc] = getNumber(number[0], i);
						pc++;
					}else if(words[i][j].endsWith("r")||words[i][j].endsWith("R")){
						memory[pc] = 92;
						pc++;
						number = words[i][j].split(",");
						memory[pc] = getNumber(number[0], i)-pc-1;
						pc++;
					}else{
						memory[pc] = 84;
						pc++;
						memory[pc] = getNumber(words[i][j], i);
						pc++;
					}

                                        verificaNumero(memory[pc-1], i);
                                        
				}else if(words[i][j].compareToIgnoreCase("or")==0){
					j++;
					if(words[i][j].startsWith("#")){
						memory[pc] = 96;
						pc++;
						memory[pc] = getNumber(words[i][j], i);
						pc++;
					}else if(words[i][j].endsWith("i")||words[i][j].endsWith("I")){
						memory[pc] = 104;
						pc++;
						number = words[i][j].split(",");
						memory[pc] = getNumber(number[0], i);
						pc++;
					}else if(words[i][j].endsWith("r")||words[i][j].endsWith("R")){
						memory[pc] = 108;
						pc++;
						number = words[i][j].split(",");
						memory[pc] = getNumber(number[0], i)-pc-1;
						pc++;
					}else{
						memory[pc] = 100;
						pc++;
						memory[pc] = getNumber(words[i][j], i);
						pc++;
					}

					
                                        verificaNumero(memory[pc-1], i);
                                        
				}else if(words[i][j].compareToIgnoreCase("and")==0){
					j++;
					if(words[i][j].startsWith("#")){
						memory[pc] = 112;
						pc++;
						memory[pc] = getNumber(words[i][j], i);
						pc++;
					}else if(words[i][j].endsWith("i")||words[i][j].endsWith("I")){
						memory[pc] = 120;
						pc++;
						number = words[i][j].split(",");
						memory[pc] = getNumber(number[0], i);
						pc++;
					}else if(words[i][j].endsWith("r")||words[i][j].endsWith("R")){
						memory[pc] = 124;
						pc++;
						number = words[i][j].split(",");
						memory[pc] = getNumber(number[0], i)-pc-1;
						pc++;
					}else{
						memory[pc] = 116;
						pc++;
						memory[pc] = getNumber(words[i][j], i);
						pc++;
					}


                                        verificaNumero(memory[pc-1], i);
                                        
				}else if(words[i][j].compareToIgnoreCase("jmp")==0){
					j++;
					if(words[i][j].endsWith("r")||words[i][j].endsWith("R")){
						memory[pc] = 140;
						pc++;
                                                number = words[i][j].split(",");
						memory[pc] = getNumber(number[0], i)-pc-1;
						pc++;
					}else{
						memory[pc] = 132;
						pc++;
						memory[pc] = getNumber(words[i][j], i);
						pc++;
					}

                                        verificaNumero(memory[pc-1], i);
                                        
				}else if(words[i][j].compareToIgnoreCase("jc")==0){
					j++;
					if(words[i][j].endsWith("r")||words[i][j].endsWith("R")){
						memory[pc] = 156;
						pc++;
                                                number = words[i][j].split(",");
						memory[pc] = getNumber(number[0], i)-pc-1;
						pc++;
					}else{
						memory[pc] = 148;
						pc++;
						memory[pc] = getNumber(words[i][j], i);
						pc++;
					}

                                        verificaNumero(memory[pc-1], i);
                                        
				}else if(words[i][j].compareToIgnoreCase("jn")==0){
					j++;
					if(words[i][j].endsWith("r")||words[i][j].endsWith("R")){
						memory[pc] = 172;
						pc++;
                                                number = words[i][j].split(",");
						memory[pc] = getNumber(number[0], i)-pc-1;
						pc++;
					}else{
						memory[pc] = 164;
						pc++;
						memory[pc] = getNumber(words[i][j], i);
						pc++;
					}

                                        verificaNumero(memory[pc-1], i);
                                        
				}else if(words[i][j].compareToIgnoreCase("jz")==0){
					j++;
					if(words[i][j].endsWith("r")||words[i][j].endsWith("R")){
						memory[pc] = 188;
						pc++;
                                                number = words[i][j].split(",");
						memory[pc] = getNumber(number[0], i)-pc-1;
						pc++;
					}else{
						memory[pc] = 180;
						pc++;
						memory[pc] = getNumber(words[i][j], i);
						pc++;
					}

                                        verificaNumero(memory[pc-1], i);
                                        
				}else if(words[i][j].compareToIgnoreCase("jsr")==0){
					j++;
					if(words[i][j].endsWith("r")||words[i][j].endsWith("R")){
						memory[pc] = 204;
						pc++;
                                                number = words[i][j].split(",");
						memory[pc] = getNumber(number[0], i)-pc-1;
						pc++;
					}else{
						memory[pc] = 196;
						pc++;
						memory[pc] = getNumber(words[i][j], i);
						pc++;
					}

                                        verificaNumero(memory[pc-1], i);
                                        
				}else if(words[i][j].compareToIgnoreCase("jv")==0){
					j++;
					if(words[i][j].endsWith("r")||words[i][j].endsWith("R")){
						memory[pc] = 236;
						pc++;
                                                number = words[i][j].split(",");
						memory[pc] = getNumber(number[0], i)-pc-1;
						pc++;
					}else{
						memory[pc] = 228;
						pc++;
						memory[pc] = getNumber(words[i][j], i);
						pc++;
					}

                                        verificaNumero(memory[pc-1], i);
                                        
				}else if(words[i][j].compareToIgnoreCase("not")==0){
					memory[pc] = 0;
					pc++;
				}else if(words[i][j].compareToIgnoreCase("rts")==0){
					memory[pc] = 208;
					pc++;
				}else if(words[i][j].compareToIgnoreCase("hlt")==0){
					memory[pc] = 240;
					pc++;
                                        hlt_verification = false;
				}else if(words[i][j].compareToIgnoreCase("syscall")==0){
					memory[pc] = 16;
					pc++;
				}else{
					status+= "Instrução não identificada! linha: "+(i+1)+"\n\n";
				}
				break;
			}
		}
                if(hlt_verification) status+="Código sem HLT!\n\n";
                if(status.compareTo("")==0) status = "A montagem foi efetuada com sucesso!\n\n";
                labels = labels_new;
                return memory;
	}			
	
	private int contaOcorrencias(char caracter, String str){
		int count=0;
		char[] caracteres = str.toCharArray();

		for (int i=0;i<str.length();i++){
			if(caracteres[i]==caracter){
		        	count++;
			}
		}
		return count;
	}

	private int getNumber(String str, int line){

		if(str.length()==0){
                    status+= "Valor não adicionado ou espaçado! linha:"+(line+1)+"\n\n"; 
                    return 0;
                }

		int i, j, num=0, n=0;
		boolean neg=false;
		char[] number = str.toCharArray();

		if(str.startsWith("#")){
			if(number[1]=='-'){
				neg = true;
				n=2;
			}else{
				n=1;
			}
		}
                
                if(number[0]=='-'){
                    neg = true;
                    n=1;
                }
		
		if(str.endsWith("h") || str.endsWith("H")){
                        try{
                            for(i=str.length()-2,j=0;i>=n;i--,j++){
                                switch (number[i]) {
                                    case '0' -> {
                                    }
                                    case '1' -> num+=(int)Math.pow(16, j);
                                    case '2' -> num+=2*(int)Math.pow(16, j);
                                    case '3' -> num+=3*(int)Math.pow(16, j);
                                    case '4' -> num+=4*(int)Math.pow(16, j);
                                    case '5' -> num+=5*(int)Math.pow(16, j);
                                    case '6' -> num+=6*(int)Math.pow(16, j);
                                    case '7' -> num+=7*(int)Math.pow(16, j);
                                    case '8' -> num+=8*(int)Math.pow(16, j);
                                    case '9' -> num+=9*(int)Math.pow(16, j);
                                    case 'A', 'a' -> num+=10*(int)Math.pow(16, j);
                                    case 'B', 'b' -> num+=11*(int)Math.pow(16, j);
                                    case 'C', 'c' -> num+=12*(int)Math.pow(16, j);
                                    case 'D', 'd' -> num+=13*(int)Math.pow(16, j);
                                    case 'E', 'e' -> num+=14*(int)Math.pow(16, j);
                                    case 'F', 'f' -> num+=15*(int)Math.pow(16, j);
                                    default -> {
                                        throw new NotANumberException();
                                    }
                                }
                            }
                        }catch(NotANumberException e){
                                status+= e.getMessage()+ " linha: "+ (line+1);
                        }
		
		}else if(str.endsWith("o") || str.endsWith("o")){
                        try{    
                            for(i=str.length()-2,j=0;i>=n;i--,j++){
                                switch (number[i]) {
                                    case '0' -> {
                                    }
                                    case '1' -> num+=(int)Math.pow(8, j);
                                    case '2' -> num+=2*(int)Math.pow(8, j);
                                    case '3' -> num+=3*(int)Math.pow(8, j);
                                    case '4' -> num+=4*(int)Math.pow(8, j);
                                    case '5' -> num+=5*(int)Math.pow(8, j);
                                    case '6' -> num+=6*(int)Math.pow(8, j);
                                    case '7' -> num+=7*(int)Math.pow(8, j);
                                    default -> {
                                        throw new NotANumberException();
                                    }
                                }
                            }
                        }catch(NotANumberException e){
                                status+= e.getMessage()+ " linha: "+ (line+1);
                        }
		}else if(str.endsWith("b") || str.endsWith("B")){
                        try{    
                            for(i=str.length()-2,j=0;i>=n;i--,j++){
                                switch (number[i]) {
                                    case '0' -> {
                                    }
                                    case '1' -> num+=(int)Math.pow(2, j);
                                    default -> {
                                        throw new NotANumberException();
                                    }
                                }
                            }
                        }catch(NotANumberException e){
                            status+= e.getMessage()+ " linha: "+ (line+1);
                        }

		}else if(str.endsWith("d") || str.endsWith("D")){
                        try{    
                            for(i=str.length()-2,j=0;i>=n;i--,j++){
                                switch (number[i]) {
                                    case '0' -> {
                                    }
                                    case '1' -> num+=(int)Math.pow(10, j);
                                    case '2' -> num+=2*(int)Math.pow(10, j);
                                    case '3' -> num+=3*(int)Math.pow(10, j);
                                    case '4' -> num+=4*(int)Math.pow(10, j);
                                    case '5' -> num+=5*(int)Math.pow(10, j);
                                    case '6' -> num+=6*(int)Math.pow(10, j);
                                    case '7' -> num+=7*(int)Math.pow(10, j);
                                    case '8' -> num+=8*(int)Math.pow(10, j);
                                    case '9' -> num+=9*(int)Math.pow(10, j);
                                    default -> {
                                        throw new NotANumberException();
                                    }
                                }
                            }
                        }catch(NotANumberException e){
                            status+= e.getMessage()+ " linha: "+ (line+1);
                        }
		}else if(str.endsWith("0") || str.endsWith("1") || str.endsWith("2") || str.endsWith("3") || str.endsWith("4") || str.endsWith("5") || str.endsWith("6") || str.endsWith("7") || str.endsWith("8") || str.endsWith("9")){
                        try{    
                            for(i=str.length()-1,j=0;i>=n;i--,j++){
                                switch (number[i]) {
                                    case '0' -> {
                                    }
                                    case '1' -> num+=(int)Math.pow(10, j);
                                    case '2' -> num+=2*(int)Math.pow(10, j);
                                    case '3' -> num+=3*(int)Math.pow(10, j);
                                    case '4' -> num+=4*(int)Math.pow(10, j);
                                    case '5' -> num+=5*(int)Math.pow(10, j);
                                    case '6' -> num+=6*(int)Math.pow(10, j);
                                    case '7' -> num+=7*(int)Math.pow(10, j);
                                    case '8' -> num+=8*(int)Math.pow(10, j);
                                    case '9' -> num+=9*(int)Math.pow(10, j);
                                    default -> {
                                        throw new NotANumberException();
                                    }
                                }
                            }
                        }catch(NotANumberException e){
                            status+= e.getMessage()+ " linha: "+ (line+1);
                        }
		}else{
			status+= "Valor não identificado! linha: "+(line+1)+"\n\n";
		}

		if(neg) num = -num;
			
		return num;
	}

	private String getLabel(String str){
		char[] temp = str.toCharArray();
		char label[] = new char[str.length()-1];
                System.arraycopy(temp, 0, label, 0, str.length()-1);
			
		return String.valueOf(label);
	}
        
        public void verificaNumero(int numero, int line){
            try{
                if (numero > 255) {
                    throw new NumberToLargeException();
                }
            }catch(NumberToLargeException e){
                status+= e.getMessage()+" " + numero + " em linha: "+ (line+1) + "\n\n";
            }
        }	
        
}
