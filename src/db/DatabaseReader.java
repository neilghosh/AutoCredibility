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
import entity.Link;

public class DatabaseReader {

	String url;
    String dbName;
    String driver;
    String userName;
    String password;
    Connection conn;
    
	public DatabaseReader() {
		url = "jdbc:mysql://localhost:3306/";
        dbName =  "MTP_DB" ;//"MTP";
        driver = "com.mysql.jdbc.Driver";
        userName = "root";
        password = "root" ;//"welcome"; //
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
	
	public ArrayList<Argument> fetchArgumentsOnClaim(String claimId){
		ArrayList<Argument> argumentsList = new ArrayList<Argument>();
		String sql = "select * from argument where parent_claim_id=?";
		
		try {
			initDBConnection();
		
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, claimId);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				Argument arg = new Argument();
				arg.setAuthorId(rs.getString("AUTHOR_ID"));
				arg.setCommunityEmotionValue(rs.getDouble("COMMUNITY_EMOTION_VALUE"));
				arg.setCredibility(rs.getDouble("CREDIBILITY"));
				arg.setId(rs.getString("ID"));
				arg.setLastUpdateDateTime(rs.getDate("LAST_UPDATE_DATE_TIME"));
				arg.setParentClaimId(rs.getString("PARENT_CLAIM_ID"));
				arg.setText(rs.getString("TEXT"));
				argumentsList.add(arg);
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
		return argumentsList;
	}
	
	public ArrayList<Argument> fetchArgumentsByAuthor(String authorId){
		ArrayList<Argument> argumentsList = new ArrayList<Argument>();
		
		String sql = "select * from argument where author_id=?";
		
		try {
			initDBConnection();
		
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, authorId);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				Argument arg = new Argument();
				arg.setAuthorId(rs.getString("AUTHOR_ID"));
				arg.setCommunityEmotionValue(rs.getDouble("COMMUNITY_EMOTION_VALUE"));
				arg.setCredibility(rs.getDouble("CREDIBILITY"));
				arg.setId(rs.getString("ID"));
				arg.setLastUpdateDateTime(rs.getDate("LAST_UPDATE_DATE_TIME"));
				arg.setParentClaimId(rs.getString("PARENT_CLAIM_ID"));
				arg.setText(rs.getString("TEXT"));
				argumentsList.add(arg);
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
		
		return argumentsList;
	}
	
	public ArrayList<Argument> fetchArgumentsAll(){
		ArrayList<Argument> argumentsList = new ArrayList<Argument>();
		String sql = "select * from argument";
		
		try {
			initDBConnection();
		
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				Argument arg = new Argument();
				arg.setAuthorId(rs.getString("AUTHOR_ID"));
				arg.setCommunityEmotionValue(rs.getDouble("COMMUNITY_EMOTION_VALUE"));
				arg.setCredibility(rs.getDouble("CREDIBILITY"));
				arg.setId(rs.getString("ID"));
				arg.setLastUpdateDateTime(rs.getDate("LAST_UPDATE_DATE_TIME"));
				arg.setParentClaimId(rs.getString("PARENT_CLAIM_ID"));
				arg.setText(rs.getString("TEXT"));
				argumentsList.add(arg);
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
		return argumentsList;
	}
	
	
	public ArrayList<Claim> fetchClaimsByAuthor(String authorId){
		ArrayList<Claim> claimsList = new ArrayList<Claim>();
		
		String sql = "select * from claim where author_id=?";
		
		try {
			initDBConnection();
		
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, authorId);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				Claim claim = new Claim();
				claim.setAuthorId(rs.getString("AUTHOR_ID"));
				claim.setId(rs.getString("ID"));
				claim.setLastUpdateDateTime(rs.getDate("LAST_UPDATE_DATE_TIME"));
				claim.setStatus(rs.getString("STATUS"));
				claim.setText(rs.getString("TEXT"));
				claimsList.add(claim);
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
		
		return claimsList;
	}
	
	public ArrayList<Comment> fetchCommentsOnArgument(String argId){
		ArrayList<Comment> commentsList = new ArrayList<Comment>();
		
		String sql = "select * from comment where PARENT_ARGUMENT_ID=?";
		
		try {
			initDBConnection();
		
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, argId);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				Comment comment = new Comment();
				comment.setAuthorId(rs.getString("AUTHOR_ID"));
				comment.setEmotionValue(rs.getDouble("EMOTION_VALUE"));
				comment.setType(rs.getString("TYPE"));
				comment.setId(rs.getString("ID"));
				comment.setLastUpdateDateTime(rs.getDate("LAST_UPDATE_DATE_TIME"));
				comment.setParentArgumentId(rs.getString("PARENT_ARGUMENT_ID"));
				comment.setText(rs.getString("TEXT"));
				commentsList.add(comment);
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
		
		return commentsList;
	}
	
	public ArrayList<Comment> fetchCommentsAll(){
		ArrayList<Comment> commentsList = new ArrayList<Comment>();
		
		String sql = "select * from comment";
		
		try {
			initDBConnection();
		
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				Comment comment = new Comment();
				comment.setAuthorId(rs.getString("AUTHOR_ID"));
				comment.setEmotionValue(rs.getDouble("EMOTION_VALUE"));
				comment.setType(rs.getString("TYPE"));
				comment.setId(rs.getString("ID"));
				comment.setLastUpdateDateTime(rs.getDate("LAST_UPDATE_DATE_TIME"));
				comment.setParentArgumentId(rs.getString("PARENT_ARGUMENT_ID"));
				comment.setText(rs.getString("TEXT"));
				commentsList.add(comment);
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
		
		return commentsList;
	}
	
	public Author fetchAuthor(String authorId){
		Author author = null;
		
		String sql = "select * from AUTHOR where id=?";
		
		try {
			initDBConnection();
		
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, authorId);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()){
				author = new Author();
				author.setName(rs.getString("NAME"));
				author.setId(rs.getString("ID"));
				author.setImage(rs.getString("IMAGE"));
				author.setAge(rs.getInt("AGE"));
				author.setJoinDateTime(rs.getDate("JOIN_DATE_TIME"));
				author.setCredibility(rs.getDouble("CREDIBILITY"));
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
		
		return author;
		
	}
	
	public ArrayList<Author> fetchAuthorsAll(){
		ArrayList<Author> authorsList = new ArrayList<Author>();
		
		String sql = "select * from author";
		
		try {
			initDBConnection();
		
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				Author author = new Author();
				author.setName(rs.getString("NAME"));
				author.setId(rs.getString("ID"));
				author.setImage(rs.getString("IMAGE"));
				author.setAge(rs.getInt("AGE"));
				author.setJoinDateTime(rs.getDate("JOIN_DATE_TIME"));
				author.setCredibility(rs.getDouble("CREDIBILITY"));
				authorsList.add(author);
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
		
		return authorsList;
		
	}
	
	public Claim fetchClaim(String claimId){
		Claim claim = null;
		
		String sql = "select * from claim where id=?";
		
		try {
			initDBConnection();
		
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, claimId);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()){
				claim = new Claim();
				claim.setAuthorId(rs.getString("AUTHOR_ID"));
				claim.setId(rs.getString("ID"));
				claim.setLastUpdateDateTime(rs.getDate("LAST_UPDATE_DATE_TIME"));
				claim.setStatus(rs.getString("STATUS"));
				claim.setText(rs.getString("TEXT"));
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
		
		return claim;
		
	}
	
	
	public ArrayList<Link> fetchLinks(String argumentId){
		ArrayList<Link> links = new ArrayList<Link>();
		
		String sql = "select * from LINKS where MSG_ID=?";
		
		try {
			initDBConnection();
		
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, argumentId);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()){
				Link link = new Link();
				link.setLink(rs.getString("LINK"));
				link.setMsg_id(rs.getString("MSG_ID"));
				link.setRank(rs.getDouble("RANK"));
				links.add(link);
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
		
		return links;
		
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
