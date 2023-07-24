package datatypes;

import tools.Utils;

public class SingleCharacter {
	
	private int value;
	
	private SingleCharacter(String value) {
		
		switch(value) {
		
			case "\\t":
				
				this.value = '\t';
				
				break;
				
			case "\\n":
				
				this.value = '\n';
				
				break;
				
			case "":
				
			case "\\0":
				
				this.value = '\0';
				
				break;
				
			default:
				
				this.value = value.charAt(0);
				
				break;
		
		}	
		
	}
	
	public static SingleCharacter getInstance(String value) {
		
		if(Utils.stringIsSingleCharacter(value))
			
			return new SingleCharacter(value);
		
		return null;
		
	}
	
	public Hexadecimal toHexadecimal() {
		
		int value = this.value;
		
		String hexValue = "";
		
		do {
			
			int remainder = value % 16;
			
			value /= 16;
			
			hexValue = Character.toString((remainder < 10) ? remainder + '0' : remainder + 'A' - 10) + hexValue;
			
		} while(value >= 16);
		
		hexValue = Character.toString((value < 10) ? value + '0' : value + 'A' - 10) + hexValue;
		
		return Hexadecimal.getInstance(hexValue);
		
	}
	
	@Override
	public String toString() {
		
		return Character.toString(value);
		
	}

}
