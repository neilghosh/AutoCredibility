/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import entity.Argument;
import entity.Author;
import entity.Claim;
import entity.Comment;
import entity.Link;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

/**
 * 
 * @author sabghosh
 */
public class Persister {

	String url;
	String dbName;
	String driver;
	String userName;
	String password;

	public Persister() {
		url = "jdbc:mysql://localhost:3306/";
		dbName = "MTP_DB"; // "MTP_DB"
		driver = "com.mysql.jdbc.Driver";
		userName = "root";
		password =   "root" ;//"welcome";
	}

	public void emptyTables() {
		Connection conn = null;
		try {
			Class.forName(driver).newInstance();

			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			System.out.println("Connected to the database");
			Statement st = conn.createStatement();
			st.executeUpdate("delete from AUTHOR");
			st.executeUpdate("delete from CLAIM");
			st.executeUpdate("delete from ARGUMENT");
			st.executeUpdate("delete from COMMENT");
			st.executeUpdate("delete from LINKS");
			System.out
					.println("Database tables author, claim, argument, and comment cleaned");
			conn.close();
			System.out.println("Disconnected from database");

		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void persistArgument(Argument argument) {
		System.out.println("MySQL Connect Example.");
		Connection conn = null;

		String sql = null;
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			System.out.println("Connected to the database");
			Statement st = conn.createStatement();
			String defaultDate = "2011-12-31 23:59:59";
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sql = "INSERT ARGUMENT (ID, TEXT, AUTHOR_ID, LAST_UPDATE_DATE_TIME, PARENT_CLAIM_ID, COMMUNITY_EMOTION_VALUE, CREDIBILITY ) VALUES('"
					+ argument.getId()
					+ "','"
					+ argument.getText()
					+ "','"
					+ argument.getAuthorId()
					+ "','"
					// +argument.getLastUpdateDateTime()
					// +defaultDate
					+ df.format(argument.getLastUpdateDateTime())
					+ "','"
					+ argument.getParentClaimId()
					+ "',"
					+ argument.getCommunityEmotionValue()
					+ ","
					+ argument.getCredibility() + ")";
			int val = st.executeUpdate(sql);
			System.out.println("Inserted ............... " + val);
			conn.close();
			System.out.println("Disconnected from database");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(sql);
		}
	}

	public void persistClaim(Claim claim) {
		System.out.println("MySQL Connect Example.");
		Connection conn = null;
		String sql = null;
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			System.out.println("Connected to the database");
			Statement st = conn.createStatement();
			String defaultDate = "2011-12-31 23:59:59";
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sql = "INSERT CLAIM (ID, TEXT, AUTHOR_ID, LAST_UPDATE_DATE_TIME, STATUS) VALUES('"
					+ claim.getId()
					+ "','"
					+ claim.getText()
					+ "','"
					+ claim.getAuthorId()
					+ "','"
					// +argument.getLastUpdateDateTime()
					// +defaultDate
					+ df.format(claim.getLastUpdateDateTime())
					+ "','"
					+ claim.getStatus() + "')";
			int val = st.executeUpdate(sql);
			System.out.println("Inserted ............... " + val);
			conn.close();
			System.out.println("Disconnected from database");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(sql);
		}

	}

	public void persistAuthor(Author author) {
		System.out.println("MySQL persistAuthor():");
		if (new DatabaseReader().fetchAuthor(author.getId()) != null) {
			System.err.println("Author with ID=" + author.getId()
					+ " already exists! Skipping persistAuthor().");
			return;
		}

		Connection conn = null;
		String sql = null;
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			System.out.println("Connected to the database");
			Statement st = conn.createStatement();
			String defaultDate = "2011-12-31 23:59:59";
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sql = "INSERT AUTHOR (ID, NAME, IMAGE,AGE, JOIN_DATE_TIME, CREDIBILITY) VALUES('"
					+ author.getId()
					+ "','"
					+ author.getName()
					+ "','"
					+ author.getImage()
					+ "',"
					+ "0"
					+ ",'"
					+ defaultDate
					+ "'," + author.getCredibility() + ")";
			int val = st.executeUpdate(sql);
			System.out.println("Inserted ............... " + val);
			conn.close();
			System.out.println("Disconnected from database");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(sql);
		}

	}

	public void persistComment(Comment comment) {

		System.out.println("MySQL Connect Example.");
		Connection conn = null;
		String sql = null;
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			System.out.println("Connected to the database");
			Statement st = conn.createStatement();
			String defaultDate = "2011-12-31 23:59:59";
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sql = "INSERT COMMENT (ID, TYPE, AUTHOR_ID,TEXT, LAST_UPDATE_DATE_TIME,PARENT_ARGUMENT_ID, EMOTION_VALUE) VALUES('"
					+ comment.getId()
					+ "','"
					+ comment.getType()
					+ "','"
					+ comment.getAuthorId()
					+ "','"
					+ comment.getText()
					+ "','"
					+ defaultDate
					+ "','"
					+ comment.getParentArgumentId()
					+ "'," + comment.getEmotionValue() + ")";
			int val = st.executeUpdate(sql);
			System.out.println("Inserted ............... " + val);
			conn.close();
			System.out.println("Disconnected from database");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(sql);
		}

	}

	public void persistLink(Link link) {
		System.out.println("MySQL Connect Example.");
		Connection conn = null;
		String sql = null;
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			System.out.println("Connected to the database");
			Statement st = conn.createStatement();

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sql = "INSERT LINKS (MSG_ID, LINK, RANK) VALUES('"
					+ link.getMsg_id() + "','" + link.getLink() + "',"
					+ link.getRank() + ")";
			int val = st.executeUpdate(sql);
			// System.out.println("Inserted ............... "+val);
			conn.close();
			// System.out.println("Disconnected from database");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(sql);
		}

	}
}
