public class CryptoManager {
	
	private static final char LOWER_BOUND = ' ';
	private static final char UPPER_BOUND = '_';
	private static final int RANGE = UPPER_BOUND - LOWER_BOUND + 1;

	/**
	 * This method determines if a string is within the allowable bounds of ASCII codes 
	 * according to the LOWER_BOUND and UPPER_BOUND characters
	 * @param plainText a string to be encrypted, if it is within the allowable bounds
	 * @return true if all characters are within the allowable bounds, false if any character is outside
	 */
	public static boolean stringInBounds (String plainText) {
		boolean status = true;
		for (int i = 0; i < plainText.length(); i++) {
			if (plainText.charAt(i) < 32 || plainText.charAt(i) > 95)
				status = false;
		}
		return status;
	}

	/**
	 * Encrypts a string according to the Caesar Cipher.  The integer key specifies an offset
	 * and each character in plainText is replaced by the character \"offset\" away from it 
	 * @param plainText an uppercase string to be encrypted.
	 * @param key an integer that specifies the offset of each character
	 * @return the encrypted string
	 */
	public static String encryptCaesar(String plainText, int key) {
		String encryptedText = "";
		if (stringInBounds(plainText)) {
			// Step 1: Put key into allowable range
			while (key > RANGE) {
				key -= RANGE;
			}
			
			// Step 2: Encrypt each char into a new string that is modified with an offset
			for (int i = 0; i < plainText.length(); i++) {
				char encryptedCh = plainText.charAt(i);
				encryptedText += CryptoManager.getEncryptOffsetCaesarChar(encryptedCh, key);
				}
			return encryptedText;
		}
		else return "Invalid format: Alphanumerics and !\"#$%&\'()*+,-./<=>?@[\\]^_ only allowed";
	}
	
	/**
	 * Encrypts a string according the Bellaso Cipher.  Each character in plainText is offset 
	 * according to the ASCII value of the corresponding character in bellasoStr, which is repeated
	 * to correspond to the length of plainText
	 * @param plainText an uppercase string to be encrypted.
	 * @param bellasoStr an uppercase string that specifies the offsets, character by character.
	 * @return the encrypted string
	 */
	public static String encryptBellaso(String plainText, String bellasoStr) {
		String encrpytedText = "";
		
		if (stringInBounds(plainText)) {
			bellasoStr = CryptoManager.stretchBellaso(plainText, bellasoStr);
			
			for (int k = 0; k < plainText.length(); k++) {
				char encryptedCh = plainText.charAt(k);
				char bellasoKey = bellasoStr.charAt(k);
				encrpytedText += CryptoManager.getEncryptOffsetBellasoChar(encryptedCh, bellasoKey);
				}
			
			return encrpytedText;
		}
		else return "Invalid format: Alphanumerics and !\"#$%&\'()*+,-./<=>?@[\\]^_ only allowed";		
	}
	
	/**
	 * Decrypts a string according to the Caesar Cipher.  The integer key specifies an offset
	 * and each character in encryptedText is replaced by the character \"offset\" characters before it.
	 * This is the inverse of the encryptCaesar method.
	 * @param encryptedText an encrypted string to be decrypted.
	 * @param key an integer that specifies the offset of each character
	 * @return the plain text string
	 */
	public static String decryptCaesar(String encryptedText, int key) {
		String decryptedText = "";
		
		if (stringInBounds(encryptedText)) {
			for (int i = 0; i < encryptedText.length(); i++) {
				char encryptedCh = encryptedText.charAt(i);
				decryptedText += CryptoManager.getDecryptOffsetCeasarChar(encryptedCh, key);
				}
		return decryptedText;
		}
		else return "Invalid format: Alphanumerics and !\"#$%&\'()*+,-./<=>?@[\\]^_ only allowed";		
	}
	
	/**
	 * Decrypts a string according the Bellaso Cipher.  Each character in encryptedText is replaced by
	 * the character corresponding to the character in bellasoStr, which is repeated
	 * to correspond to the length of plainText.  This is the inverse of the encryptBellaso method.
	 * @param encryptedText an uppercase string to be encrypted.
	 * @param bellasoStr an uppercase string that specifies the offsets, character by character.
	 * @return the decrypted string
	 */
	public static String decryptBellaso(String encryptedText, String bellasoStr) {
		String decryptedText = "";
		
		if (stringInBounds(encryptedText)) {
			bellasoStr = stretchBellaso(encryptedText, bellasoStr);
			
			for (int i = 0; i < encryptedText.length(); i++) {
				char encryptedCh = encryptedText.charAt(i);
				char bellasoCh = bellasoStr.charAt(i);
				
				decryptedText += CryptoManager.getDecryptOffsetBellasoChar(encryptedCh, bellasoCh);
			}
			return decryptedText;
		}
		else return "Invalid format: Alphanumerics and !\"#$%&\'()*+,-./<=>?@[\\]^_ only allowed";		
	}
	
	
	/**
	 * "Stretches" the cipher key for Bellaso Ciphering by repeating the character
	 * in its first index and appending it next to the very last index. It then
	 * moves to its second index and appends it next to the NEW very last index.
	 * Process repeats with the third index, fourth index, so on and so forth until
	 * the length of cipher key is equal to the length of the text to be encrypted.
	 * 
	 * @param encryptedText a string to be encrypted which will be used to determine
	 * its length
	 * @param bellasoStr a string as the cipher key for Bellaso Cipher
	 * @return bellasoStr a string that is "stretched" composed of repeating subsequent characters
	 * equal in length to the encryptedText
	 */
	public static String stretchBellaso(String encryptedText, String bellasoStr) {
		int i = 0;
		while (bellasoStr.length() != encryptedText.length()) {
			bellasoStr += bellasoStr.charAt(i);
			i++;
		}
		return bellasoStr;
	}
	
	/**
	 * Makes a new string consisting of characters starting plainCh and adds subsequent
	 * characters (moving FORWARD ASCII dec value wise) 
	 * to that new string until the offset key specifies a stop point. 
	 * This method avoids the complicated that comes with unregulated and unwanted 
	 * ASCII characters such as those of ASCII 0 - 31 and ASCII 96 onwards. 
	 * 
	 * @param plainCh a char that determines the original point to do offset counting
	 * @param key an integer that determines the stopping point for the offset char
	 * @return newChar a last indexed char in a string specially made based on plainCh
	 * and key. This is the char that is offset through Caesar Ciphering.
	 */
	public static char getEncryptOffsetCaesarChar(char plainCh, int key) {
		char newChar = 'A';
		int count = 0;
		String alphaNumerics = "";

		for (int k = plainCh; count <= key; k++) {
			if (k == 96)
				k = 32;
			alphaNumerics += (char)k;
			count++;
		}
		newChar = alphaNumerics.charAt(alphaNumerics.length() - 1);
		return newChar;
	}
	
	/**
	 * Makes a new string consisting of characters starting from encryptedCh and adds subsequent
	 * characters (moving FORWARD ASCII dec value wise) to that new string until 
	 * the offset bellasoChar (converted to int as key) specifies a stop point. 
	 * This method avoids the complicated that comes with unregulated and unwanted 
	 * ASCII characters such as those of ASCII 0 - 31 and ASCII 96 onwards. 
	 * 
	 * @param encryptedCh a char that determines the original point to do offset counting
	 * @param bellasoCHar a char that will be converted to int that determines 
	 * the stopping point for the offset char
	 * @return newChar a last indexed char in a string specially made based on plainCh
	 * and key. This is the char that is offset through Bellaso Ciphering.
	 */
	public static char getEncryptOffsetBellasoChar(char encryptedCh, char bellasoChar) {
		char newChar = 'A';
		int count = 0;
		String alphaNumerics = "";

		for (int k = encryptedCh; count <= bellasoChar; k++) {
			if (k == 96)
				k = 32;
			alphaNumerics += (char)k;
			count++;
		}
		newChar = alphaNumerics.charAt(alphaNumerics.length() - 1);
		return newChar;
	}
	
	/**
	 * Makes a new string consisting of characters starting plainCh and adds subsequent
	 * characters (moving BACKWARD ASCII dec value wise) to that new string until 
	 * the offset bellasoChar (converted to int as key) specifies a stop point. 
	 * This method avoids the complicated that comes with unregulated and unwanted 
	 * ASCII characters such as those of ASCII 0 - 31 and ASCII 96 onwards. 
	 * 
	 * @param encryptedCh a char that determines the original point to do offset counting
	 * @param bellasoCHar a char that will be converted to int that determines 
	 * the stopping point for the offset char
	 * @return newChar a last indexed char in a string specially made based on plainCh
	 * and key. This is the char that is offset through Bellaso Ciphering.
	 */
	public static char getDecryptOffsetBellasoChar(char encryptedCh, char bellasoChar) {
		char newChar = 'A';
		int count = 0;
		String alphaNumerics = "";

		for (int k = encryptedCh; count <= bellasoChar; k--) {
			if (k == 31)
				k = 95;
			alphaNumerics += (char)k;
			count++;
		}
		newChar = alphaNumerics.charAt(alphaNumerics.length() - 1);
		return newChar;
	}
	
	/**
	 * Makes a new string consisting of characters starting encryptedCh and adds subsequent
	 * characters (moving BACKWARD ASCII dec value wise) 
	 * to that new string until the offset key specifies a stop point. 
	 * This method avoids the complicated that comes with unregulated and unwanted 
	 * ASCII characters such as those of ASCII 0 - 31 and ASCII 96 onwards. 
	 * 
	 * @param plainCh a char that determines the original point to do offset counting
	 * @param key an integer that determines the stopping point for the offset char
	 * @return newChar a last indexed char in a string specially made based on plainCh
	 * and key. This is the char that is offset through Caesar Ciphering.
	 */
	public static char getDecryptOffsetCeasarChar(char encryptedCh, int key) {
		char newChar = 'A';
		int count = 0;
		String alphaNumerics = "";

		for (int k = encryptedCh; count <= key; k--) {
			if (k == 31)
				k = 95;
			alphaNumerics += (char)k;
			count++;
		}
		newChar = alphaNumerics.charAt(alphaNumerics.length() - 1);
		return newChar;
	}
	
	
}