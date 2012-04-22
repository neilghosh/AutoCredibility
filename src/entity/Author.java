package entity;

import java.util.Date;

/**
 * Entity: AUTHOR
	Fields:
		Id
		Name
		Image
		Age
		Join_DateTime
		Credibility
 * @author bjghosh
 *
 */
public class Author {

	private String id;
	private String name;
	private String image;
	private int age;
	private Date joinDateTime;
	private double credibility;
	
	public Author() {
	}
	
	public Author(String id, String name, String image, int age, Date joinDateTime, double credibility) {
		this.id = id;
		this.name = name;
		this.image = image;
		this.joinDateTime = joinDateTime;
		this.credibility = credibility;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Date getJoinDateTime() {
		return this.joinDateTime;
	}
	public void setJoinDateTime(Date joinDateTime) {
		this.joinDateTime = joinDateTime;
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
