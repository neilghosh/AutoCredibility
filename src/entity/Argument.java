package entity;

import java.util.Date;

/**
 * ENTITY: ARGUMENT
	Fields:
		Id
		Text
		Author_Id
		LastUpdate_DateTime
		Parent_Claim_Id
		Community_Emotion_Value
		Credibility
 * @author bjghosh
 *
 */
public class Argument {

	private String id;
	private String text;
	private String authorId;
	private Date lastUpdateDateTime;
	private String parentClaimId;
	private double communityEmotionValue;
	private double credibility;
	
	public Argument(String id, String text, String authorId, Date lastUpdateDateTime, String parentClaimId,
			double communityEmotionValue, double credibility) {
		this.id = id;
		this.text = text;
		this.authorId = authorId;
		this.lastUpdateDateTime = lastUpdateDateTime;
		this.parentClaimId = parentClaimId;
		this.communityEmotionValue = communityEmotionValue;
		this.credibility = credibility;
	}
	
	public Argument() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public Date getLastUpdateDateTime() {
		return lastUpdateDateTime;
	}

	public void setLastUpdateDateTime(Date lastUpdateDateTime) {
		this.lastUpdateDateTime = lastUpdateDateTime;
	}

	public String getParentClaimId() {
		return parentClaimId;
	}

	public void setParentClaimId(String parentClaimId) {
		this.parentClaimId = parentClaimId;
	}

	public double getCommunityEmotionValue() {
		return communityEmotionValue;
	}

	public void setCommunityEmotionValue(double communityEmotionValue) {
		this.communityEmotionValue = communityEmotionValue;
	}

	public double getCredibility() {
		return credibility;
	}

	public void setCredibility(double credibility) {
		this.credibility = credibility;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
