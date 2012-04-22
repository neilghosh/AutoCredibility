package analyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import rita.wordnet.RiWordnet;

/**
 * Uses RiTA JAVA package, and WordNet to find relevance of one text w.r.t. another.
 * Uses the similar/synonyms words for a given word, and then finds percentage of matched words in text2 for a given text1
 * @author bjghosh
 * 
 * Can be modified by:
 * a) Giving more weight, if the noun word itself, as it is, appears in the arg text
 * b) Giving more weight to occurrence of nouns
 *
 */
public class RelevanceAnalyser {
	
	private String[] getSynonyms(String word){
		RiWordnet wordnet = new RiWordnet(null);
		String[] pos = wordnet.getPos(word);
		String bestpos = wordnet.getBestPos(word);
		System.out.println("\nBest POS determined for word '" + word +
				"' , (based on maximum senses) is '" + bestpos + "'");
		
		if("".equals(word.trim()) || "".equals(bestpos))
			return null;
		
		System.out.println("isStem(word='" + word + "', pos='" + bestpos + "'): " + wordnet.isStem(word, bestpos));
		
		System.out.println("\nFetching synonyms...\n");
		// Get synonyms
		String[] synonyms = wordnet.getAllSynonyms(word, bestpos);
		return synonyms;
	}
	
	private String[] getSimilars(String word){
		RiWordnet wordnet = new RiWordnet(null);
		String[] pos = wordnet.getPos(word);
		String bestpos = wordnet.getBestPos(word);
		System.out.println("\nBest POS determined (based on maximum senses) is '" + bestpos + "'");
		
		if("".equals(word.trim()) || "".equals(bestpos))
			return null;
		
		System.out.println("\nFetching similars...\n");
		// Get similars
		String[] similars = wordnet.getAllSimilar(word, bestpos);
		return similars;
	}
	
	/**
	 * Returns a unique set of all similar words and synonyms for the list of words passed.
	 * @param words
	 * @return
	 */
	private Set<String> getAllSimilars(String[] words){
		Set<String> uniqueSynonymsSet = new HashSet<String>();
		for(String word: words){
			uniqueSynonymsSet.add(word); //add the word itself too
			String[] synonyms = getSynonyms(word);
			if(synonyms != null)
				for(String synonym: synonyms){
					uniqueSynonymsSet.add(synonym); //add the synonyms
				}
			String[] similars = getSimilars(word);
			if(similars != null)
				for(String similar: similars){
					uniqueSynonymsSet.add(similar); //add the similars
				}
		}
		return uniqueSynonymsSet;
	}
	
	public double getRelevanceScore(String baseText, String newText){
		
		
		if(baseText == null || newText ==  null || "".equals(baseText.trim()) || "".equals(newText.trim()))
			return 0.0;
		
		baseText = baseText.toLowerCase();
		newText = newText.toLowerCase();
		
		StopWordRemover stopwordRemover = new StopWordRemover();
		String[] baseWords = stopwordRemover.removeStopwords(baseText); //would contains words from baseText sans stopwords
		
		//Now get all synonyms of these words
		Set<String> allSynonyms = this.getAllSimilars(baseWords);
		System.out.println(allSynonyms.toString());
		
		//Now get non-stopwords for the newText
		String[] newTextWords = stopwordRemover.removeStopwords(newText);
		
		//Now calculate percentage of the presence of allSynonyms in newTextWords
		double relevanceScore = 0;
		for(String newTextWord: newTextWords){
			if(allSynonyms.contains(newTextWord))
				relevanceScore++;
		}
		System.out.println("\n" + relevanceScore + " similar words found in argument, out of total " + 
				newTextWords.length + " words.");
		return relevanceScore / newTextWords.length;
	}
	
	
	public static void main(String[] args) {
		RelevanceAnalyser relevanceAnalyzer = new RelevanceAnalyser();
		String baseText = "Marriage is beautiful";
		String argumentText = "Marriage is bad, very bad. Because it needs tremendous compromise! " +
			"Bachelorhood is far better. Do not marry, to enjoy life to the fullest!" +
			"Marriage sucks! It is the worst thing to happen in life!";
		System.out.println("Relevance Score : " + relevanceAnalyzer.getRelevanceScore(baseText, argumentText));
		
	}
}

