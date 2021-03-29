package uk.ac.ncl.undergraduate.modules.csc3621.cryptanalysis.easyfreq;

/**
 * This class is to compute a frequency table of a texts.
 *
 * @author Changyu Dong
 * @author Roberto Metere
 * @author Your Name
 */
public class FrequencyAnalyser {

	/**
	 * The text to analyse
	 */
	private String text;

	/**
	 * Get the text to analyse.
	 *
	 * @return the text to analyse.
	 */
	public String getText() {
		return text;
	}

	/**
	 * Set the text to analyse.
	 *
	 * @param text the text to analyse.
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * This method returns a frequency table as a result of the analysis of the
	 * text.
	 *
	 * TODO: complete the function that conduct a frequency analysis of the internal
	 * buffer and produce a frequency table based on the analysis. Please, write
	 * your code between the comments as appropriate.
	 *
	 * @return frequency table as a result of the analysis of the text
	 */
	public FrequencyTable analyse() {
		// Please, do not remove the editor-fold comments.
		// <editor-fold defaultstate="collapsed" desc="Write your code here below!">

		FrequencyTable FreqAnalyseTable = new FrequencyTable();


		// set the text to lowercase, this is so we can easily discover the frequency of
		// each letter
		String lowerCaseText = getText().toLowerCase().replaceAll("\\s+","").replaceAll("[^A-Za-z]+", "");

		
		int textLength = lowerCaseText.length();
		/*
		 * A for loop to loop through every character in the alphabet, if the letter is
		 * in the text appears in the text increase the counter by one
		 */
		for (char c = 'a'; c <= 'z'; c++) {

			// a counter for the numbers of appearance of the character
			double charCounter = 0;

			for (int i = 0; i < textLength; i++) {

				if (lowerCaseText.charAt(i) == c) {

					charCounter++;
				}
			}

			// if the character dosn't appear in the string then set the frequency in the
			// table to 0
			if (charCounter == 0) {
				FreqAnalyseTable.setFrequency(c, charCounter);
			}

			// else set the frequency to be the amount of time the character appears in the
			// text / length of the text
			else {
				
				double frequency = charCounter / textLength;
				FreqAnalyseTable.setFrequency(c, frequency);
			}

		}
		// </editor-fold> // END OF YOUR CODE
		return FreqAnalyseTable;
	}

	/*
	 * Code for calculating the IC of a set of text, as I couldnt get it to work I have removed it 
	 * 
	 * 
	public double calculateIC(FrequencyTable Table, String text) {
		
		String clean = text.toLowerCase();
		int size = clean.length();
		double IC = 0;
		
		//System.out.println("totalsize" + length);
		for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
			int Ci = (int)(Table.getFrequency(alphabet) * size);
			//System.out.println(alphabet + " " + Ci);
			
			if (Ci <= 1) {

			} else {
				IC += (Ci * (Ci - 1));
			}
		}
	
		IC = IC / size ;
		IC = IC / (size - 1) ;
		return IC;
	}*/

}
