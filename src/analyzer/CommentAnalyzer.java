package analyzer;

import java.util.HashMap;

import entity.Comment;

public class CommentAnalyzer {

	public static HashMap<String, Double> EMOTION_MAP;

	public static final String EMOTION_TAG_ACCEPT = "ACCEPT";
	public static final String EMOTION_TAG_LIKE = "LIKE";
	public static final String EMOTION_TAG_LOVE = "LOVE";
	public static final String EMOTION_TAG_PROUD = "PROUD";
	public static final String EMOTION_TAG_RECOMMEND = "RECOMMEND";
	public static final String EMOTION_TAG_STRONGLYRECOMMEND = "STRONGLYRECOMMEND";

	public static final String EMOTION_TAG_NEUTRAL = "NEUTRAL";

	public static final String EMOTION_TAG_UNACCEPT = "UNACCEPT";
	public static final String EMOTION_TAG_DISLIKE = "DISLIKE";
	public static final String EMOTION_TAG_HATE = "HATE";
	public static final String EMOTION_TAG_DEROGATORY = "DEROGATORY";
	public static final String EMOTION_TAG_OPPOSE = "OPPOSE";
	public static final String EMOTION_TAG_STRONGLYOPPOSE = "STRONGLYOPPOSE";
	
	private static SentimentAnalyzer sentimentAnalyzer = null;

	static {
		EMOTION_MAP = new HashMap<String, Double>();

		EMOTION_MAP.put("ACCEPT", 1.0);
		EMOTION_MAP.put("LIKE", 1.5);
		EMOTION_MAP.put("LOVE", 2.0);
		EMOTION_MAP.put("PROUD", 3.0);
		EMOTION_MAP.put("RECOMMEND", 4.0);
		EMOTION_MAP.put("STRONGLYRECOMMEND", 5.0);
		EMOTION_MAP.put("NEUTRAL", 0.0);
		EMOTION_MAP.put("UNACCEPT", -1.0);
		EMOTION_MAP.put("DISLIKE", -1.5);
		EMOTION_MAP.put("HATE", -2.0);
		EMOTION_MAP.put("DEROGATORY", -3.0);
		EMOTION_MAP.put("OPPOSE", -4.0);
		EMOTION_MAP.put("STRONGLYOPPOSE", -5.0);
		
		sentimentAnalyzer = new SentimentAnalyzer();
	    sentimentAnalyzer.loadModel();

	}

	public CommentAnalyzer() {
	}

	/**
	 * Comment.TYPE_FIXED_VOTE when it is a FB like : This should be seeded
	 * while retrieving the FB likes (this method will not determine this type.
	 * This type has to be pre seeded)
	 * 
	 * Comment.TYPE_VARIABLE_VOTE when it is one of the EMOTION_TAG
	 * 
	 * Comment.TYPE_TEXTUAL_SENTIMENT when no emotion tag present in comment
	 * text. So we need to use SentimentAnalyzer
	 * 
	 * @param text
	 * @return
	 */
	public static String detectCommentType(String text) {
		String commentType = null;

		// Comment.TYPE_FIXED_VOTE
		// Comment.TYPE_VARIABLE_VOTE
		// Comment.TYPE_TEXTUAL_SENTIMENT
		if(text == null)
			return null;
		
		text = retrieveActualCommentText(text).trim();

		if (CommentAnalyzer.EMOTION_MAP.containsKey(text))
			commentType = Comment.TYPE_VARIABLE_VOTE;
		else
			commentType = Comment.TYPE_TEXTUAL_SENTIMENT;
		
		return commentType;
	}

	public static double analyzeEmotion(Comment comment) {
		double emotionValue = 0.0;
		
		if(!Comment.TYPE_FIXED_VOTE.equals(comment.getType()))
			comment.setType(CommentAnalyzer.detectCommentType(comment.getText()));
		
		if(Comment.TYPE_FIXED_VOTE.equalsIgnoreCase(comment.getType())){
			emotionValue = CommentAnalyzer.EMOTION_MAP.get(CommentAnalyzer.EMOTION_TAG_LIKE);
		}
		else if (Comment.TYPE_VARIABLE_VOTE.equalsIgnoreCase(comment.getType())){
			emotionValue = CommentAnalyzer.EMOTION_MAP.get(comment.getText());
		}
		else if (Comment.TYPE_TEXTUAL_SENTIMENT.equalsIgnoreCase(comment.getType()))
		{
			try {
	            emotionValue = sentimentAnalyzer.getPolarityValue(comment.getText());
	            
	        } catch (Throwable t) {
	            System.out.println("Thrown: " + t);
	            t.printStackTrace(System.out);
	        }
		}
		
		return emotionValue;
	}

	/**
	 * Normally a comment in our Argument Analysis framework is in the following
	 * format: &lt;@&gt;&lt;user_name&gt; &lt;comment_text&gt;
	 * 
	 * So, this method would strip away the initial &lt;@&gt;&lt;user_name&gt;,
	 * and return the actual comment text
	 * 
	 * @param text
	 * @return
	 */
	public static String retrieveActualCommentText(String text) {
		return text; // for the time being
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
