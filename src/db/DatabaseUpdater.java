package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.sql.PreparedStatement;

import entity.Argument;
import entity.Author;
import entity.Claim;
import entity.Comment;

public class DatabaseUpdater {

	String url;
    String dbName;
    String driver;
    String userName;
    String password;
    Connection conn;
    
	public DatabaseUpdater() {
		url = "jdbc:mysql://localhost:3306/";
        dbName = "MTP_DB"; //"MTP"; //
        driver = "com.mysql.jdbc.Driver";
        userName = "root";
        password = "root"; //"root"
	}
	
	private void initDBConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url + dbName, userName, password);
	        System.out.println("Connected to the database");
	}
	
	private void closeDBConnection() throws SQLException{
		conn.close();
		conn = null;
	}
	
	public boolean updateArgumentsCredibility(ArrayList<Argument> argumentsList){
		
		String sql = "update argument set COMMUNITY_EMOTION_VALUE = ?, CREDIBILITY = ? where ID = ?";
		boolean allOk = true;
		
		try {
			initDBConnection();
		
			PreparedStatement ps = null;
			
			for(Argument arg: argumentsList){
				ps = conn.prepareStatement(sql);
				ps.setDouble(1, arg.getCommunityEmotionValue());
				ps.setDouble(2, arg.getCredibility());
				ps.setString(3, arg.getId());
				int updateCount = ps.executeUpdate();
				if(updateCount <= 0)
					allOk = false;
				ps.close();
				ps = null;
			}
			closeDBConnection();
			
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
		
		return allOk;
	}
	
	public boolean updateCommentsEmotionValue(ArrayList<Comment> commentsList){
		
		String sql = "update comment set EMOTION_VALUE = ? where ID = ?";
		boolean allOk = true;
		
		try {
			initDBConnection();
		
			PreparedStatement ps = null;
			
			for(Comment comment: commentsList){
				ps = conn.prepareStatement(sql);
				ps.setDouble(1, comment.getEmotionValue());
				ps.setString(2, comment.getId());
				int updateCount = ps.executeUpdate();
				if(updateCount <= 0)
					allOk = false;
				ps.close();
				ps = null;
			}
			closeDBConnection();
			
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
		
		return allOk;
	}
	
	public boolean updateAuthorsCredibility(ArrayList<Author> authorsList){
		String sql = "update author set CREDIBILITY = ? where ID = ?";
		boolean allOk = true;
		
		try {
			initDBConnection();
		
			PreparedStatement ps = null;
			
			for(Author author: authorsList){
				ps = conn.prepareStatement(sql);
				ps.setDouble(1, author.getCredibility());
				ps.setString(2, author.getId());
				int updateCount = ps.executeUpdate();
				if(updateCount <= 0)
					allOk = false;
				ps.close();
				ps = null;
			}
			closeDBConnection();
			
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
		
		return allOk;
		
	}	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DatabaseReader dbr = new DatabaseReader();
		Author author = dbr.fetchAuthor("100002085266630");
		
		System.out.println("Haha");
	}

}
