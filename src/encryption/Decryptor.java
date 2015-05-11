package encryption;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.Scanner;

public class Decryptor {
	
	private final int key1;
	private final int key2;
	
	private final char[] chars = {
			'N','a','Z','K','&','?','J','4','-','(','\'','$',
			'0','S','5','x','m','H','u','*','}','@','i','#',
			'l','<','E','R','9','=','6','7','o','z','k',':',
			']','c','Q','.','\n','/','e','f','v','p','%','F','j',
			'"','I','t','L','\\','d','T','q','1','A','g','2',
			'b','B','+','U','P','8','C','X','O','Y','[','|',
			'y',';',',','s','r',')','V','M',' ','D','w','n',
			'!','{','h','G','>','_','W','^','3'};
	
	public Decryptor(int key1, int key2){
		
		for(int i = 2; i < key1+1 ; i++){
			if(key1 % i == 0 && 94 % i == 0){
				throw new java.lang.IllegalArgumentException("Invalid key. Key1 must be relatively prime to 94 (no common factors).");
			}
		}
		
		this.key1 = key1;
		this.key2 = key2;
	}
	
	public String decipher(String s) {
		return decipher(s,key1,key2, false);
	}
	
	public String decipher(String s, boolean encodedKey) {
		return decipher(s,key1,key2, encodedKey);
	}
	
	private String decipher(String s, int mult, int shift, boolean encodedKey){
		
		int temp1 = mult;
		int temp2 = shift;
		String tempString = s;
		
		if(encodedKey){
			if(s.startsWith("l#g((c'n'*F!#gEg9")){
				String[] tokens = s.split("~");
				if(tokens[0].equals("l#g((c'n'*F!#gEg9")){
				try{
					StringBuilder sb = new StringBuilder();
					temp1 = Integer.parseInt(decipher(tokens[1],73,99,false));
					temp2 = Integer.parseInt(decipher(tokens[2],17,49,false));
					for(int i = 3; i < tokens.length; i++){
						sb.append(tokens[i]);
					}
					tempString = sb.toString();
				} catch(Exception ex){
					throw new java.lang.IllegalArgumentException("Invalid key. The encoded key was not found or is not valid.");
				}
			}
		}
		}

		
		
		for(int i = 2; i < temp1+1 ; i++){
			if(temp1 % i == 0 && 94 % i == 0){
				throw new java.lang.IllegalArgumentException("Invalid key. Multiplier must be relatively prime to 94.");
			}
		}
		
		char[] charArray = tempString.toCharArray();
		char[] newCharArray = new char[tempString.length()];
		BigInteger multiplyer = BigInteger.valueOf(temp1);
		BigInteger mod = BigInteger.valueOf(94);
		System.out.println(temp1);
		int inverseMod = multiplyer.modInverse(mod).intValue();
		
		for(int i = 0; i < charArray.length; i++){
			boolean matched = false;
			for(int j = 0; j < chars.length ; j++){
				
				if(charArray[i] == chars[j]){
					
					matched = true;
					int newTempIndex = inverseMod * (j - temp2);
					int newIndex = newTempIndex % 94;    // ((j)*mult + shift) % 94;
					if(newIndex < 0) newIndex += 94;
					newCharArray[i] = chars[newIndex];
				}
			}
			if(!matched){
				newCharArray[i] = charArray[i];
			}
		}
		return new String(newCharArray);	
	}
	
	public void decipherFile(String input, String output) throws IOException{
		
		decipherFile(input,output,false);
	}
	
	public void decipherFile(String input, String output, boolean encodedKey) throws IOException{
		
		String filenameInput = input;
		String filenameOutput = output;
		StringBuilder sb = new StringBuilder();
		String cypherText = null;
		String plainText = null;
		
		File cypherTextFile = new File(filenameInput);
		File plainTextFile = new File(filenameOutput);
		
		if(!cypherTextFile.exists()){
			throw new FileNotFoundException("Input file does not exist: " + input);
		}
		
		try(Scanner reader = new Scanner(new BufferedReader(new FileReader(cypherTextFile)))){
				
			while(reader.hasNextLine()){
				sb.append(reader.nextLine());
				if(reader.hasNextLine()) sb.append("\n");
			}
			
			cypherText = sb.toString();
		}
			
		if(cypherText != null) plainText = decipher(cypherText, key1, key2,encodedKey);

		if(!plainTextFile.exists()){
			try (Formatter format = new Formatter(output)){
				format.flush();
				format.close();
			}
		}
		
		try(FileWriter writer = new FileWriter(plainTextFile)){
				writer.write(plainText);
				writer.flush();
		}
	}

	static private void generateScrambledAlphabet(){
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
