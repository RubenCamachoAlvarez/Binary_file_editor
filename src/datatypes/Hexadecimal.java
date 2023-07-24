package datatypes;

import tools.Utils;

public class Hexadecimal {
	
	private String value = "00";
	
	private Hexadecimal(String value) {
		
		if(!value.equals(""))
		
			this.value = value;
		
	}
	
	public static Hexadecimal getInstance(String value) {
		
		if(Utils.stringIsHexadecimal(value)) {
			
			if(!value.equals(""))
			
				value = String.format("%2s", value.toUpperCase()).replace(' ', '0');
			
			return new Hexadecimal(value);
			
		}
		
		return null;
		
	}
	
	public SingleCharacter toSingleCharacter() {
		
		int value = 0;
		
		for(int index = this.value.length(); index > 0; index--) {
			
			int hexadecimalDigit = this.value.codePointAt(index - 1);
			
			value += ((hexadecimalDigit < 'A') ? hexadecimalDigit - '0' : hexadecimalDigit - 'A' + 10) * Math.pow(16, this.value.length() - index);
			
		}
		
		return SingleCharacter.getInstance(Character.toString(value));
		
	}
	
	@Override
	public String toString() {
		
		return this.value;
		
	}

}
