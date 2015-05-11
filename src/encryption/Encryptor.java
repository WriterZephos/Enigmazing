package encryption;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.Scanner;

public class Encryptor {
	
	private final int key1;
	private final int key2;
	
	private final char[] chars = {'N','a','Z','K','&','?','J','4','-','(','\'','$',
									'0','S','5','x','m','H','u','*','}','@','i','#',
									'l','<','E','R','9','=','6','7','o','z','k',':',
									']','c','Q','.','\n','/','e','f','v','p','%','F','j',
									'"','I','t','L','\\','d','T','q','1','A','g','2',
									'b','B','+','U','P','8','C','X','O','Y','[','|',
									'y',';',',','s','r',')','V','M',' ','D','w','n',
									'!','{','h','G','>','_','W','^','3'};
	
	public Encryptor(int key1, int key2){
		
		for(int i = 2; i < key1+1 ; i++){
			if(key1 % i == 0 && 94 % i == 0){
				throw new java.lang.IllegalArgumentException("Invalid key parameter in constructor. Key1 must be relatively prime to 94 (no common factors).");
			}
		}
		
		this.key1 = key1;
		this.key2 = key2;
	}

	public String encrypt(String s, boolean encodeString){
		return encrypt(s,key1,key2, encodeString);
	}
	
	public String encrypt(String s){
		return encrypt(s,key1,key2, false);
	}
	
	public String encrypt(String s, int mult, int shift, boolean encodeKey){
		
		for(int i = 2; i < mult+1 ; i++){
			if(mult % i == 0 && 94 % i == 0){
				throw new java.lang.IllegalArgumentException("Invalid key. Multiplier must be relatively prime to 94.");
			}
		}
		
		char[] charArray = s.toCharArray();
		char[] newCharArray = new char[s.length()];
		
		for(int i = 0; i < charArray.length; i++){
			boolean matched = false;
			for(int j = 0; j < chars.length ; j++){
				
				if(charArray[i] == chars[j]){
					
					matched = true;
					int newIndex = ((j)*mult + shift) % 94;
					if(newIndex < 0) newIndex += 94;
					newCharArray[i] = chars[newIndex];
				}
			}
			if(!matched){
				newCharArray[i] = charArray[i];
			}
			
		}
		String newString = new String(newCharArray);		
		if(encodeKey){
			StringBuilder sb = new StringBuilder();
			String multString = encrypt(String.valueOf(mult),73,99,false);
			String shiftString = encrypt(String.valueOf(shift),17,49,false);
			sb.append("l#g((c'n'*F!#gEg9~" + multString + "~" + shiftString + "~");
			sb.append(newString);
			newString = sb.toString();
		}
		return newString;
	}
	
	public void encryptFile(String input, String output) throws IOException{
		encryptFile(input,output,false);
	}
	
	public void encryptFile(String input, String output, boolean encodeKey) throws IOException{
		
		String filenameInput = input;
		String filenameOutput = output;
		StringBuilder sb = new StringBuilder();
		String plainText = null;
		String cypherText = null;
		
		File plainTextFile = new File(filenameInput);
		File cypherTextFile = new File(filenameOutput);
		
		if(!plainTextFile.exists()){
			throw new FileNotFoundException("Input file does not exist: " + input);
		}
		
		try(Scanner reader = new Scanner(new BufferedReader(new FileReader(plainTextFile)))){
				
			while(reader.hasNextLine()){
				sb.append(reader.nextLine());
				if(reader.hasNextLine()) sb.append("\n");
			}
			
			plainText = sb.toString();
			
		}catch (FileNotFoundException e){
			System.out.println("File was not found: " + filenameInput);
		}
			
		if(plainText != null) cypherText = encrypt(plainText, key1, key2,encodeKey);

		if(!cypherTextFile.exists()){
			try (Formatter format = new Formatter(output);){
				format.flush();
				format.close();
			};
		}
		
		try(FileWriter writer = new FileWriter(cypherTextFile)){
				writer.write(cypherText);
				writer.flush();
		};	
		
	}
	
	private static void generateScrambledAlphabet(){
		char[] chars = {'a','b','c','d','e','f','g',
				'h','i','j','k','l','m','n','o','p',
				'q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G',
				'H','I','J','K','L','M','N','O','P',
				'Q','R','S','T','U','V','W','X','Y','Z',' ','.','?','!',',','"','/',
				'\\','@','#','$','%','1','2','3','4','5','6','7','8','9','0','(',')','*',
				'&','^','-','_','+','=','\'',':',';','>','<','[',']','{','}','|'};
		
		ArrayList<Character> myList = new ArrayList<>();
		for(char c : chars) myList.add(c);
		
		Collections.shuffle(myList);
		for(char c : myList) System.out.print("'" + c + "',");
		
		System.out.println(myList.size());
	}
	
	public static void main(String[] args) {
		
		generateScrambledAlphabet();

	}

}
