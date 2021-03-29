package uk.ac.ncl.undergraduate.modules.csc3621.cryptanalysis.easyfreq;

/**
 * This class is capable of encrypt and decrypt according to the Vigen&egrave;re
 * cipher.
 *
 * @author Changyu Dong
 * @author Roberto Metere
 * @author Your Name
 */
public class VigenereCipher {

	/**
	 * Encryption function of the Vigen&egrave;re cipher.
	 *
	 * <p>
	 * TODO: Complete the Vigen&egrave;re encryption function.
	 *
	 * @param plaintext
	 *            the plaintext to encrypt
	 * @param key
	 *            the encryption key
	 * @return the ciphertext according with the Vigen&egrave;re cipher.
	 */
	public static String encrypt(String plaintext, String key) {
		// Please, do not remove the editor-fold comments.
        //<editor-fold defaultstate="collapsed" desc="Write your code here below!">

		//set the cipher text to be empty 
		String ciphertext = "";
		
		//Set the text to lowercase
		plaintext = plaintext.toLowerCase();
		key = key.toLowerCase();
		//for every character in the plaintext
		for (int i = 0, keyChar = 0; i < plaintext.length(); i++) {

			char c = plaintext.charAt(i);

			//if that character in an alphabet
			if (c >= 'a' && c <= 'z') {
				//encrypt the char
				//(dCharacter - (key.charAt(j)) + 26) % 26) is to find out what the decrtypted letter should be
    			//(a) is to set the ascii value
				c = (char) ((c + key.charAt(keyChar) - (2 * 'a')) % 26 + ('a'));
				//set j to be the next character in the key. If its at the last character then it should loop to the first
				keyChar = ++keyChar % key.length();
			}
			
			
			/* A method to encrypt upper case characters that didnt work as expected
			/*else if (c >= 'A' && c <= 'Z')
			{
				//decrypt for Uppercase 
				//System.out.println(key.charAt(j));
				c = (char) ((((c) + key.charAt(j) + 26) % 26) + ('A' - 1));
				System.out.println(c);
				j = ++j % key.length();
				
			*/
		
			//add the encrypted c to the ciphertext 
			ciphertext += c;
		}

		// return the ciphertext;
		return ciphertext;
		// Please, do not remove the editor-fold comments.
		// <editor-fold defaultstate="collapsed" desc="Write your code here
		// below!">

		// </editor-fold> // END OF YOUR CODE
	}

	/**
     * Decryption function of the Vigen&egrave;re cipher.
     *
     * <p>
     * TODO: Complete the Vigen&egrave;re decryption function.
     *
     * @param ciphertext the encrypted text
     * @param key the encryption key
     * @return the plaintext according with the Vigen&egrave;re cipher.
     */
    public static String decrypt(String ciphertext, String key) {
        // Please, do not remove the editor-fold comments.
        //<editor-fold defaultstate="collapsed" desc="Write your code here below!">
		//clean the plain text
    	String plaintext = "";
    	//convert the cipher text to lowerCase
		ciphertext = ciphertext.toLowerCase();

		key = key.toLowerCase();
		//System.out.println("key passed in " + key);
		
		
		//i is for the character in the text
		//j is for the character in the key
    	for(int i = 0, keyChar = 0; i < ciphertext.length(); i++){
    		
    		char dCharacter = ciphertext.charAt(i);
    	
    		if(dCharacter >= 'a' && dCharacter <= 'z'){
    			//to decrypt the character 
    			//(dCharacter - (key.charAt(j)) + 26) % 26) is to find out what the decrtypted letter should be
    			//(a) is to set the ascii value
    			dCharacter = (char)((dCharacter - (key.charAt(keyChar)) + 26) % 26 + ('a'));
    			//set j to be the next character in the key. If its at the last character then it should loop to the first
    			keyChar= ++keyChar % key.length(); 
    		}
    		
    		
    		/* 
    		 * A method to decrypt upper case characters that didnt work as expected
    		if (c >= 'A' && c <= 'Z')
			{
    			//key = key.toUpperCase();
    		
    			int q = ((c) - (((key.charAt(j)) + 26) % 26));

    			c = (char)(((c)  - ((key.charAt(j)) + 26) % 26)+ ('A'));
    			
				j= ++j % key.length(); 
			}
    		*/
    		//add the decrypted text to the plaintext
    		plaintext += dCharacter;
    	}
    	
    	return plaintext;

        //</editor-fold> // END OF YOUR CODE
    }
}
