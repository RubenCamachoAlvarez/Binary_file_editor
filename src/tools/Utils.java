package tools;

public class Utils {
	
	public static boolean stringIsSingleCharacter(String value) {
		
		return value != null && (value.length() <= 1 || (value.length() == 2 && (value.equals("\\n") || value.equals("\\t") || value.equals("\\0"))));
		
	}
	
	public static boolean stringIsHexadecimal(String value) {
		
		if(value != null) {
			
			if(value.length() > 0 && value.length() < 3) {
			
				for(int index = 0; index < value.length(); index++) {
					
					int hexDigit = value.codePointAt(index);
					
					if(hexDigit < 'A' && hexDigit > 'F' && hexDigit < 'a' && hexDigit > 'f' && hexDigit < '0' && hexDigit > '9')
						
						return false;
					
				}
			
			}
				
			return true;
			
		}
		
		return false;
		
	}

}
