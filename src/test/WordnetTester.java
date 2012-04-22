package test;

import java.util.Arrays;

import rita.wordnet.RiWordnet;

public class WordnetTester {
	
	public static void main(String[] args) {
		RiWordnet wordnet = new RiWordnet(null);
		String word = "quick";
		String[] pos = wordnet.getPos(word);
		String bestpos = wordnet.getBestPos(word);
		System.out.println("Parts of Speech for the word '" + word + "' [Total:" + pos.length + "]");
		for(int i=0; i<pos.length; i++){
			String s = pos[i];
			if(i==0) System.out.print("[");
			System.out.print(s);
			if(i < pos.length -1) System.out.print(",");
		}
		System.out.println("]\nBest POS determined (based on maximum senses) is '" + bestpos + "'");
		System.out.println("isStem(word='" + word + "', pos='" + bestpos + "'): " + wordnet.isStem(word, bestpos));
		
		System.out.println("\n***************************************\n");
		// Get stems
		String[] stems = wordnet.getStems(word, bestpos);
		System.out.println("Stems for " + bestpos + ":" + word);
		if (stems != null) {
			// Sort alphabetically
			Arrays.sort(stems);
			for (int i = 0; i < stems.length; i++) {
				System.out.println("Stem " + (i+1) + ": " + stems[i]);
			}
		} else
			System.out.println("No Stems!");
		
		
		System.out.println("\n***************************************\n");
		// Hyponyms
		System.out.println("Hyponyms for " + bestpos + ":" + word);
		String[] hyponyms = wordnet.getAllHyponyms(word, bestpos);
		if (hyponyms != null) {
			for (int i = 0; i < hyponyms.length; i++) {
				System.out.println("Hynonym " + (i+1) + ": " + hyponyms[i]);
			}
		}else
			System.out.println("No hyponyms!");
		
		System.out.println("\n***************************************\n");
		// Get synonyms
		String[] synonyms = wordnet.getAllSynonyms(word, bestpos);
		System.out.println("Synonyms for " + bestpos + ":" + word);
		if (synonyms != null) {
			// Sort alphabetically
			Arrays.sort(synonyms);
			for (int i = 0; i < synonyms.length; i++) {
				System.out.println("Synonym " + (i+1) + ": " + synonyms[i]);
			}
		} else
			System.out.println("No synyonyms!");
		
		
		System.out.println("\n***************************************\n");
		// Get derived terms
		String[] derivedTerms = wordnet.getAllDerivedTerms(word, bestpos);
		System.out.println("DerivedTerms for " + bestpos + ":" + word);
		if (derivedTerms != null) {
			// Sort alphabetically
			Arrays.sort(derivedTerms);
			for (int i = 0; i < derivedTerms.length; i++) {
				System.out.println("DerivedTerms " + (i+1) + ": " + derivedTerms[i]);
			}
		} else
			System.out.println("No Derived Term!");
		
		
		System.out.println("\n***************************************\n");
		// Get similar terms
		String[] similars = wordnet.getSimilar(word, bestpos);
		System.out.println("Similar Terms for " + bestpos + ":" + word);
		if (similars != null) {
			// Sort alphabetically
			Arrays.sort(similars);
			for (int i = 0; i < similars.length; i++) {
				System.out.println("Similar Terms " + (i+1) + ": " + similars[i]);
			}
		} else
			System.out.println("No Similar Term!");
	}
}
