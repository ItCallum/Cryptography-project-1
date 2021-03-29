package uk.ac.ncl.undergraduate.modules.csc3621.cryptanalysis.easyfreq;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

/**
 * This class is for frequency cryptanalysis of ciphertext when the key is an
 * integer.
 *
 * @author Changyu Dong
 * @author Roberto Metere
 * @author Your Name
 */
public class FrequencyCryptanalysis {

	/**
	 * The ciphertext (encryption of the plaintext).
	 */
	private String ciphertext;

	/**
	 * The plaintext (readable content).
	 */
	private String plaintext;

	/**
	 * The key such that the encryption of the plaintext with such key gives the
	 * ciphertext.
	 */
	private int key;

	/**
	 * Frequency table (of the ciphertext).
	 */
	private FrequencyTable table;

	/**
	 * Set the ciphertext to analyse.
	 *
	 * @param text the text to set as ciphertext
	 */
	public void setCiphertext(String text) {
		this.ciphertext = text;
	}
	
	public String cleanText(String text) {
		return text.toLowerCase();
	}

	/**
	 * Create an new class to cryptanalyze texts.
	 */
	public FrequencyCryptanalysis() {
	}

	/**
	 * This method conducts cryptanalysis of the frequency of letters in the
	 * ciphertext to retrieve the encryption key.
	 *
	 * <p>
	 * TODO:
	 * <ul>
	 * <li>Conduct a frequency analysis of the internal buffer.
	 * <li>Find the key. You should try your best to find the key based on your
	 * analysis.
	 * <li>Store the key in the class variable <code>this.key</code>.
	 * </ul>
	 *
	 *
	 * @return the key as the result of the cryptanalysis
	 */
	public int cryptanalysis() {
		// Please, do not remove the editor-fold comments.
		// <editor-fold defaultstate="collapsed" desc="Write your code here
		// below!">

		//create a new FrequencyAnalyser to find the character frequency of the text
		FrequencyAnalyser cryptAnalyser = new FrequencyAnalyser();
		
		//as we only want letter characters remove everything else to get the correct size of the text. 
		String cipherTextCopy = this.ciphertext.toLowerCase().replaceAll("\\s+","").replaceAll("[^A-Za-z]+", "");
		
		//set the text of cryptAnalyser to be this cipher text 
		cryptAnalyser.setText(cipherTextCopy);

		FrequencyTable cryptTable = cryptAnalyser.analyse();
		//a variable to store the char which appears the most. 
		char totalHighestFreqChar = 0;

		//get the freq of 'a'. I will use this as a comparison
		double currentHighestFreqChar = cryptTable.getFrequency('a');
		//loop through all characters in the alphabet , to find the one with the highest freq
		for (char character = 'a'; character <= 'z'; character++) {
			//if the freq of character is greater than variable then set the it as variable.
			if (cryptTable.getFrequency(character) > currentHighestFreqChar) {
				currentHighestFreqChar = cryptTable.getFrequency(character);
				totalHighestFreqChar = character;
			}
		}
		
		// THIS IS ASSUMING THAT THE TEXT IS FOLLOWING THE STANDARD IN ENGLISH LANGUAGE OF E BEING THE MOST FREQ
		// OCCURING CHARACTER GIVEN A LARGE BIT OF TEXT
		// My freq analyise of the plain english text proves this as E is the most common character
		// https://en.wikipedia.org/wiki/Letter_frequency - This articale also back up the fact that e the letter with the highest freq
		// the key of the shift cipher is the value of the highestFreqChar - e + 26 then mod it
		// I add 26 so if highestFreqChar - 'e' is a minus number then it will make it postive
		// If highestFreqChar - 'e' is already postive then this will have no effect
		key = (totalHighestFreqChar - 'e' + 26) %26 ;
		
		// </editor-fold> // END OF YOUR CODE
		return this.key;

	}

	/**
	 * This method reconstructs the plaintext from the ciphertext with the key.
	 *
	 * <p>
	 * TODO:
	 * <ul>
	 * <li>After finding the key, use the key to decrypt the ciphertext
	 * <li>Store the plaintext in the class variable <code>this.plaintext</code>.
	 * </ul>
	 */
	public void decrypt() {
		// Please, do not remove the editor-fold comments.
		// <editor-fold defaultstate="collapsed" desc="Write your code here
		// below!">
		
		//clear the plain text
		this.plaintext = "";

		//A for loop to go through every alphabetical character in the cipher text and shift it by the key.
		//I have only set it to decode alphabetical character so that I dont effect speacial character.
		for (int i = 0; i < this.ciphertext.length(); i++) {

			//get the character at the value of i
			char ch = this.ciphertext.charAt(i);
			
			if (ch >= 'a' && ch <= 'z') {
				
				//decrypt for lowercase 
				//((ch + key + 26) % 26) is to find out what the decrtypted letter should be
				//+ ('a' -1)) is to set it to the correct ascii value
				ch = (char) (((ch + key + 26) % 26) + ('a' -1));

			}

			else if (ch >= 'A' && ch <= 'Z')
			{
				//decrypt for Uppercase 
				ch = (char) ((((ch+20) + key  + 26) % 26) + 'A' - 1);
			}
			
			//append ch to the plain text
			this.plaintext += ch;
		}
		//</editor-fold> // END OF YOUR CODE
	}
	
	/**
	 * Show the results of the complete analysis.
	 */
	public void showResult() {
		System.out.println("The key is " + this.key);
		this.decrypt();
		System.out.println("The plaintext is:");
		System.out.println(this.plaintext);
	}

	/**
	 * @param args the command line arguments
	 * @throws java.io.IOException         errors reading from files
	 * @throws java.net.URISyntaxException Errors in retrieving resources
	 */
	public static void main(String[] args) throws IOException, URISyntaxException {
		String mainPath, plaintextFilePath, ciphertextFilePath, plaintext, ciphertext;
		FrequencyAnalyser frequencyAnalyser;
		FrequencyTable frequencyTable;
		FrequencyCryptanalysis cryptanalysis;
		File solutionDirectory;
		String solutionFrequencyFilePath, solutionKeyFilePath, solutionPlaintextFilePath;

		cryptanalysis = new FrequencyCryptanalysis();

		// Get resources
		mainPath = Paths.get(FrequencyCryptanalysis.class.getResource("/").toURI()).toString();
		plaintextFilePath = mainPath + "/res/pg1661.txt";
		ciphertextFilePath = mainPath + "/res/Exercise1Ciphertext.txt";
		solutionDirectory = new File(mainPath + "/solution1");
		solutionFrequencyFilePath = solutionDirectory + "/frequency.txt";
		solutionKeyFilePath = solutionDirectory + "/key.txt";
		solutionPlaintextFilePath = solutionDirectory + "/plaintext.txt";

		// Analyse the plain text
		System.out.println("Frequency of plain text");
		plaintext = Util.readFileToBuffer(plaintextFilePath);
		frequencyAnalyser = new FrequencyAnalyser();
		frequencyAnalyser.setText(plaintext);
		frequencyTable = frequencyAnalyser.analyse();
		frequencyTable.print();

		// Crack the ciphertext
		System.out.println("Cipher text analysis");
		ciphertext = Util.readFileToBuffer(ciphertextFilePath);
		cryptanalysis.setCiphertext(ciphertext);
		cryptanalysis.cryptanalysis();
		cryptanalysis.showResult();
	
		// Write solution in res path if (!solutionDirectory.exists()) {
		solutionDirectory.mkdir();
		Util.printBufferToFile(frequencyTable.toString(), solutionFrequencyFilePath);
		Util.printBufferToFile(Integer.toString(cryptanalysis.key), solutionKeyFilePath);
		Util.printBufferToFile(cryptanalysis.plaintext, solutionPlaintextFilePath);

	}
}
