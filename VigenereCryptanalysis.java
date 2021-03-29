package uk.ac.ncl.undergraduate.modules.csc3621.cryptanalysis.easyfreq;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * This class is for frequency cryptanalysis of ciphertext.
 *
 * @author Changyu Dong
 * @author Roberto Metere
 * @author Your Name
 */
public class VigenereCryptanalysis {

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
	private final StringBuffer key = new StringBuffer();

	/**
	 * Create an new class to cryptanalyze texts.
	 */
	public VigenereCryptanalysis() {
	}

	/**
	 * Set the ciphertext to analyse.
	 *
	 * @param text the text to set as
	 */
	public void setCiphertext(String text) {
		this.ciphertext = text;
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
	 * @return the key as result of the cryptanalysis
	 */
	public String cryptanalysis() {

		// Please, do not remove the editor-fold comments.
		// <editor-fold defaultstate="collapsed" desc="Write your code here below!">

		// int keySize = 0;

		// key.delete(keySize, key.length());

		// Copy the cipher text and conver it all into one block to make analysis
		// easyier
		String copyciphertext = this.ciphertext;
		copyciphertext = copyciphertext.replaceAll("\\s+", "").replaceAll("[^A-Za-z]+", "");
		// ciphertext = ciphertext.toLowerCase().replaceAll("\\s+","");
		// System.out.println(ciphertext);

		// An array of all the spilt cipher
		ArrayList<String> CipherSpilt = new ArrayList<String>();

		String cipher = copyciphertext;
		// length of the cipher text
		int cryLength = cipher.length();

		// this is my method of going through the array to get the most frequent truplet
		int mostFreqTruplet = 0;
		int startoftruplet = 0;

		for (int i = 0; i < cryLength - 2; i++) {

			// counter to see if the truplet has appread
			int hasAppered = 0;

			for (int a = i; a < cryLength - 2; a++) {
				// check to see if the truplet appears in the text
				if (cipher.charAt(i) == cipher.charAt(a) && cipher.charAt(i + 1) == cipher.charAt(a + 1)
						&& cipher.charAt(i + 2) == cipher.charAt(a + 2)) {
					hasAppered++;

				}
				// If the truplet is the most common set it as the most common
				if (hasAppered > mostFreqTruplet) {
					mostFreqTruplet = hasAppered;
					startoftruplet = a;
				}
			}

		}

		// an array list of all the apperances of the key
		ArrayList<Integer> TrupApperances = new ArrayList<Integer>();

		for (int a = 0; a < cryLength - 2; a++) {

			if (cipher.charAt(startoftruplet) == cipher.charAt(a)
					&& cipher.charAt(startoftruplet + 1) == cipher.charAt(a + 1)
					&& cipher.charAt(startoftruplet + 2) == cipher.charAt(a + 2)) {

				TrupApperances.add(a + 1);
			}

		}

		// an array list for all the distance between truplets
		ArrayList<Integer> keyDistance = new ArrayList<Integer>();
		// int dMin = keyApperances.get(keyApperances.size() - 1);

		//if there is only one truple apperacne set it to the possiable key size possKeySize
		int possKeySize = TrupApperances.get(0);

		//if the amount of TrupApperances is greater than 2 
		if (TrupApperances.size() >= 2) {
			
			// find the distance between all truplets
			for (int i = 0; i < TrupApperances.size() - 1; i++) {

				keyDistance.add(TrupApperances.get(1 + i) - TrupApperances.get(i));
			}

			
			// if only 2 distances between those 
			if (keyDistance.size() == 2) {
				//gcd to find the possiable key size
				possKeySize = gcd(keyDistance.get(0), keyDistance.get(1));
			}
			// else if( ) {

			// }
			else {
				//else use a 3rd value to work out the as well to work out the key size
				int g1 = gcd(keyDistance.get(0), keyDistance.get(1));
				possKeySize = gcd(g1, keyDistance.get(2));
			}
		}

		// knowing the key size split the cipher into smaller shift ciphers
		for (int i = 0; i < possKeySize; i++) {
			String cipherSplit = new String();
			for (int j = i; j < cryLength - possKeySize; j = j + possKeySize) {
				cipherSplit += cipher.charAt(j);
			}

			// run a frequancy anlyise to find the most common letter
			FrequencyCryptanalysis cryptAnalyser = new FrequencyCryptanalysis();
			cryptAnalyser.setCiphertext(cipherSplit);
			int t = ((cryptAnalyser.cryptanalysis() + ('a')));
			// add the character to the overall key
			this.key.append((char) t);
		}

		// </editor-fold> // END OF YOUR CODE
		return this.key.toString();

	}

	// a class to find the greatest common demoninator
	public int gcd(int n1, int n2) {
		while (n1 != n2) {
			if (n1 > n2)
				n1 -= n2;
			else
				n2 -= n1;
		}
		return n1;
	}

	// public static boolean almostEqual(double a, double b) {
	// return Math.abs(a - b) < 10E-3;
	// }

	/*
	 * This method reconstructs the plaintext from the ciphertext with the key.
	 */
	public void decrypt() {
		this.plaintext = VigenereCipher.decrypt(ciphertext, this.key.toString());

	}

	/**
	 * Show the results of the complete analysis.
	 */
	public void showResult() {
		System.out.println("The key is " + this.key.toString());
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
		String mainPath, ciphertextFilePath, ciphertext, plaintextFilePath, plaintext;
		VigenereCryptanalysis cryptanalysis;
		VigenereCipher vigenereCipher, vigenereCipher2;
		File solutionDirectory;
		String solutionKeyFilePath, solutionPlaintextFilePath, solutionFrequancytextFilePath;
		FrequencyAnalyser frequencyAnalyser;
		FrequencyTable frequencyTable;

		cryptanalysis = new VigenereCryptanalysis();

		// Get resources
		mainPath = Paths.get(FrequencyCryptanalysis.class.getResource("/").toURI()).toString();
		ciphertextFilePath = mainPath + "/res/Exercise2Ciphertext.txt";
		solutionDirectory = new File(mainPath + "/solution2");
		solutionKeyFilePath = solutionDirectory + "/key.txt";
		solutionPlaintextFilePath = solutionDirectory + "/plaintext.txt";
		solutionFrequancytextFilePath = solutionDirectory + "/freqOfEx2.txt";
		plaintextFilePath = mainPath + "/res/pg1661.txt";

		String test1 = solutionDirectory + "/test1.txt";
		String test = solutionDirectory + "/test.txt";

		vigenereCipher = new VigenereCipher();
		vigenereCipher2 = new VigenereCipher();

		System.out.println("Ex2.1");
		System.out.println("A encryption and decryption of newcastleuniversity");
		String Ex21encrypt = vigenereCipher.encrypt("newcastleuniversity", "ncl");
		// System.out.println(Ex2.1encrypt);

		String Ex21dectypt = vigenereCipher.decrypt(Ex21encrypt, "ncl");
		// System.out.println(Ex2.1dectypt);

		if (Ex21dectypt.equals("newcastleuniversity")) {
			System.out.println("The code was encrypted and decrypted properly");
		} else {
			System.out.println("Somthing has gone wrong");

		}

		plaintext = Util.readFileToBuffer(plaintextFilePath);
		plaintext.toLowerCase();

		System.out.println("Ex2.2");
		System.out.println("A encryption and decryption of pg1661.txt");
		String Ex22encrypt = vigenereCipher2.encrypt(plaintext.toString(), "marco");
		//System.out.println(Ex22encrypt);

		String Ex22dectypt = vigenereCipher2.decrypt(Ex22encrypt, "marco");
		//System.out.println(Ex22dectypt);

		if (Ex22dectypt.equals(plaintext.toLowerCase())) {
			System.out.println("The code was encrypted and decrypted properly");
		} else {
			System.out.println("Somthing has gone wrong");

		}

		frequencyAnalyser = new FrequencyAnalyser();
		frequencyAnalyser.setText(Ex22encrypt);
		frequencyTable = frequencyAnalyser.analyse();

		System.out.println("Ex2.3");
		System.out.println("For answers please check in the solution 2 folder called Ex2_3");

		System.out.println("Ex2.4");
		System.out.println("A decryption of Exercise2Ciphertext.txt ");
		ciphertext = Util.readFileToBuffer(ciphertextFilePath);
		cryptanalysis.setCiphertext(ciphertext);
		cryptanalysis.cryptanalysis();
		cryptanalysis.showResult();

		// Write solution in res path if (!solutionDirectory.exists()) {
		solutionDirectory.mkdir();
		Util.printBufferToFile(frequencyTable.toString(),solutionFrequancytextFilePath);
		Util.printBufferToFile(cryptanalysis.key.toString(), solutionKeyFilePath);
		Util.printBufferToFile(cryptanalysis.plaintext, solutionPlaintextFilePath);
		// Util.printBufferToFile(Ex22dectypt.toString(), test);
		// Util.printBufferToFile(Ex22encrypt.toString(), test1);
	}
}
