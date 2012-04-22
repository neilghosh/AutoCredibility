package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

/**
 * Original SWN3.java used from http://sentiwordnet.isti.cnr.it/code/SWN3.java, and modified to SWN4
 * 
 * Modified Author: Bhaskar Jyoti Ghosh (bhaskarjyoti.ghosh@students.iiit.ac.in)
 * @author bjghosh
 * 
 * Modifications: Stores scores in _dict hashmap, instead of calculated sentiment tags.
 * 				  Adds one more method to get the corresponding sentiment tag for the passed sentiment score.
 *
 */
public class SWN4 {
	private String _pathToSWN = "data" + File.separator + "SentiWordNet_3.0.0_20100908.txt";
	private HashMap<String, Double> _dict;

	public SWN4(String pathToSWN){
		this._pathToSWN = pathToSWN;
				
		_dict = new HashMap<String, Double>();
		HashMap<String, Vector<Double>> _temp = new HashMap<String, Vector<Double>>();
		try{
			BufferedReader csv =  new BufferedReader(new FileReader(pathToSWN));
			String line = "";
			while((line = csv.readLine()) != null)
			{
				if(line.trim().startsWith("#"))
					continue;  //ignoring the comment lines in SentiWordNet file
				
				String[] data = line.split("\t");
				Double score = Double.parseDouble(data[2])-Double.parseDouble(data[3]);
				String[] words = data[4].split(" ");
				for(String w:words)
				{
					String[] w_n = w.split("#");
					w_n[0] += "#"+data[0];
					int index = Integer.parseInt(w_n[1])-1;
					if(_temp.containsKey(w_n[0]))
					{
						Vector<Double> v = _temp.get(w_n[0]);
						if(index>v.size())
							for(int i = v.size();i<index; i++)
								v.add(0.0);
						v.add(index, score);
						_temp.put(w_n[0], v);
					}
					else
					{
						Vector<Double> v = new Vector<Double>();
						for(int i = 0;i<index; i++)
							v.add(0.0);
						v.add(index, score);
						_temp.put(w_n[0], v);
					}
				}
			}
			
			fetchSentimentScores(_temp);
			
			
		}
		catch(Exception e){e.printStackTrace();}		
	}
	
	private String getSentimentTag(double score){
			String sentimentTag = null;
			
			if(score>=0.75)
				sentimentTag = "strong_positive";
			else
			if(score > 0.25 && score<=0.5)
				sentimentTag = "positive";
			else
			if(score > 0 && score>=0.25)
				sentimentTag = "weak_positive";
			else
			if(score < 0 && score>=-0.25)
				sentimentTag = "weak_negative";
			else
			if(score < -0.25 && score>=-0.5)
				sentimentTag = "negative";
			else
			if(score<=-0.75)
				sentimentTag = "strong_negative";
			
			return sentimentTag;
	}
	
	private void fetchSentimentScores(HashMap<String, Vector<Double>> _temp){
		Set<String> temp = _temp.keySet();
		for (Iterator<String> iterator = temp.iterator(); iterator.hasNext();) {
			String word = (String) iterator.next();
			Vector<Double> v = _temp.get(word);
			double score = 0.0;
			double sum = 0.0;
			for(int i = 0; i < v.size(); i++)
				score += ((double)1/(double)(i+1))*v.get(i);
			for(int i = 1; i<=v.size(); i++)
				sum += (double)1/(double)i;
			score /= sum;
			;
			_dict.put(word, score);
		}
	}

	public Double extractSentimentScore(String word, String pos)
	{
		return _dict.get(word+"#"+pos);
	}	
	
	public String extractSentimentTag(String word, String pos)
	{
		if(_dict.containsKey(word+"#"+pos))
			return getSentimentTag(_dict.get(word+"#"+pos));
		else
			return null;
	}
	
}
