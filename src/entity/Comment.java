package entity;

import java.util.Date;

import analyzer.CommentAnalyzer;

/**
 * ENTITY: COMMENT
	Fields:
		Id
		Type                  -- VARIABLE_VOTE / TEXTUAL_SENTIMENT
		Author_Id
		Text
		LastUpdate_DateTime
		Parent_Argument_Id
		Emotion_Value
 * @author bjghosh
 *
 */
public class Comment {

	private String id;
	private String type;                  //FIXED_VOTE / VARIABLE_VOTE / TEXTUAL_SENTIMENT
	private String authorId;
	private String text;
	private Date lastUpdateDateTime;
	private String parentArgumentId;
	private double emotionValue;
	
	public static String TYPE_FIXED_VOTE = "FIXED_VOTE";
	public static String TYPE_VARIABLE_VOTE = "VARIABLE_VOTE";
	public static String TYPE_TEXTUAL_SENTIMENT = "TEXTUAL_SENTIMENT";
	
	public Comment() {
	}

	public Comment(String id, String type, String authorId, String text, Date lastUpdateDateTime, String parentArgumentId,
			double emotionValue) {
		this.id = id;
		this.type = type;
		this.authorId = authorId;
		this.text = text;
		this.lastUpdateDateTime = lastUpdateDateTime;
		this.parentArgumentId = parentArgumentId;
		this.emotionValue = emotionValue;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAuthorId() {
		return this.authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getLastUpdateDateTime() {
		return lastUpdateDateTime;
	}

	public void setLastUpdateDateTime(Date lastUpdateDateTime) {
		this.lastUpdateDateTime = lastUpdateDateTime;
	}

	public String getParentArgumentId() {
		return parentArgumentId;
	}

	public void setParentArgumentId(String parentArgumentId) {
		this.parentArgumentId = parentArgumentId;
	}

	public double getEmotionValue() {
		return emotionValue;
	}

	public void setEmotionValue(double emotionValue) {
		this.emotionValue = emotionValue;
	}

	public void analyze(){
		if(!Comment.TYPE_FIXED_VOTE.equals(this.type))
			this.type = CommentAnalyzer.detectCommentType(this.text);
		
		this.emotionValue = CommentAnalyzer.analyzeEmotion(this);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
