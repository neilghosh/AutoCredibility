package analyzer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * List of stopwords adapted from http://www.softexe.com/askw-stop-words-list.html
 * @author bjghosh
 *
 */
public class StopWordRemover {
	public static final String REGEX_ARG_WORD_FILTER = "[^a-zA-Z ]"; //anything that is not letters or space
	
	public static final String STOPWORDS_FILE_PATH = "data\\stopwords.txt";
	private ArrayList<String> _stopwordsList;
	public StopWordRemover(){
		loadStopwords();
	}
	
	/**
	 * Loads the stopwords from the file. Expects each word in seperate lines  in the file.
	 */
	public void loadStopwords()  {
		_stopwordsList = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(StopWordRemover.STOPWORDS_FILE_PATH)));
			String word = null;
			while((word = br.readLine())!=null){
				_stopwordsList.add(word);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isStopword(String word){
		if(_stopwordsList.contains(word))
			return true;
		else
			return false;
	}
	
	private String[] parse(String text){
		return text.replaceAll(StopWordRemover.REGEX_ARG_WORD_FILTER, " ").split(" ");
	}
	
	public String[] removeStopwords(String text){
		ArrayList<String> nonStopwordsList = new ArrayList<String>();
		String[] allWords = this.parse(text);
		
		for(String word: allWords){
			if(! this.isStopword(word))
				nonStopwordsList.add(word);
		}
		
		return nonStopwordsList.toArray(new String[]{});
	}
}
