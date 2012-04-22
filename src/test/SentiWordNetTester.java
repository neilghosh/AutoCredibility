package test;

import java.io.File;

import rita.wordnet.RiWordnet;

public class SentiWordNetTester {
	private static String _pathToSWN = "data" + File.separator + "SentiWordNet_3.0.0_20100908.txt";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Loading SentiWordNet...");
		SWN4 swn = new SWN4(_pathToSWN);
		System.out.println("Done loading.");
		
		String[] words = new String[]{
				"quickly",
				"marriage",
				"ugly",
				"rape",
				"kiss",
				"strong",
				"suggest",
				"support",
				"oppose",
				"love",
				"like",
				"dislike",
				"neutral",
				"hate"
		};
		
		for(String word: words){
			RiWordnet wordnet = new RiWordnet(null);
			String bestpos = wordnet.getBestPos(word);
			System.out.println("Sentiwordnet polarity for word '" + bestpos + ":" + word + "' : " + 
					swn.extractSentimentScore(word, bestpos));
			if(!"v".equals(bestpos)){
				//trying as a verb too
				bestpos = "v";
				System.out.println("Sentiwordnet polarity for word '" + bestpos + ":" + word + "' : " + swn.extractSentimentScore(word, bestpos));
			}
		}
	}

}
