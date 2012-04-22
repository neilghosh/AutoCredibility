package entity;

import java.util.Date;

/**
 * ENTITY: CLAIM
	Fields:
		Id
		Text
		Author_Id
		LastUpdate_DataTime
		Status					-- UNDECIDED / JUSTIFIED / FALSIFIED
 * @author bjghosh
 *
 */
public class Claim {

	private String id;
	private String  text;
	private String authorId;
	private Date lastUpdateDateTime;
	private String status;
	
	public static String STATUS_UNDECIDED = "UNDECIDED";
	public static String STATUS_JUSTIFIED = "JUSTIFIED";
	public static String STATUS_FALSIFIED = "FALSIFIED";
	
	public Claim() {
	}
	
	public Claim(String id, String  text, String authorId, Date lastUpdateDateTime, String status) {
		this.id = id;
		this.text = text;
		this.authorId = authorId;
		this.lastUpdateDateTime = lastUpdateDateTime;
		this.status = status;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
