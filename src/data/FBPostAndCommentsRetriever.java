package data;

import helper.DateAdapter;

import java.util.logging.Level;
import java.util.logging.Logger;
import db.Persister;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import analyzer.LinkAnalyzer;

import entity.Argument;
import entity.Author;
import entity.Claim;
import entity.Comment;
import entity.Link;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import com.thoughtworks.xstream.XStream;
//import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
public class FBPostAndCommentsRetriever {

    static String commentID = "100002085266630_106153009464207";
    static String accessToken = "112915312119465|b593093c0d322f224fbeaa12-100002085266630|KMjuKg4WxqOwf_zzDBZF2azzOr4";

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) {
    	
    	//java  -Dhttp.proxyHost=www-proxy.us.oracle.com  -Dhttp.proxyPort=80
//    	System.setProperty("http.proxyHost", "www-proxy.us.oracle.com");
//    	System.setProperty("http.proxyPort", "80");
    	System.setProperty("java.net.useSystemProxies", "true");
    	
    	Persister persister = new Persister();
		
		persister.emptyTables();

        //Prepare the URL for the post
        URL postURL = null;
        try {
            postURL = new URL("https://graph.facebook.com/" + commentID
                    + "?access_token=" + accessToken);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //System.out.println(postURL.toString());
        String jsonString = fetchURL(postURL);

        // System.out.println(s);

        Claim claim = new Claim();
        ArrayList<Argument> argumentList = new ArrayList<Argument>();
        //ArrayList<Author> author = new ArrayList<Author>();
        HashMap<String, String> authorMap = new HashMap<String, String>();

        JSONObject postObject = (JSONObject) JSONValue.parse(jsonString);
        claim.setId(postObject.get("id").toString());
        claim.setLastUpdateDateTime(DateAdapter.FbStrToDate(postObject.get(
                "updated_time").toString()));
        JSONObject fromObject = (JSONObject) postObject.get("from");
        claim.setAuthorId(fromObject.get("id").toString());
        claim.setText(postObject.get("message").toString());

        //System.out.println(postObject.get("message").toString());
        persister.persistClaim(claim);

        JSONObject commentsObject = (JSONObject) postObject.get("comments");
        JSONArray commentsArray = (JSONArray) commentsObject.get("data");
        String parentArgumentId = null;
        //for each comment on the post
        for (int i = 0; i < commentsArray.size(); i++) {


            JSONObject commentObject = (JSONObject) commentsArray.get(i);
            String commentText = (String) commentObject.get("message");
            JSONObject commentFromObj = (JSONObject) commentObject.get("from");
            boolean isComment = false;
            String commentTextWithBracket = null;
            Pattern p = Pattern.compile("\\[.*]");
            Matcher m = p.matcher(commentText);
            if (m.find()) {
                System.out.println("REGEX" + m.group(0));
                commentTextWithBracket = m.group(0);
                String commentor = m.group(0).replace("[", "").replace("]", "");
                String commentorId = authorMap.get(commentor);

                for (int j = argumentList.size() - 1; j >= 0; j--) {
                    System.out.println(commentorId + " " + argumentList.get(j).getAuthorId());
                    if (argumentList.get(j).getAuthorId().equalsIgnoreCase(commentorId)) {
                        isComment = true;
                        parentArgumentId = argumentList.get(j).getId();
                        System.out.println("Found Target" + argumentList.get(j).getText());
                        break;
                    }
                }

            }
            
            fetchLinks(commentObject.get("id").toString(), commentText);
            if (isComment) {
                System.out.println("Coment" + commentText);
                Comment comment = new Comment();
                comment.setId(commentObject.get("id").toString());
                comment.setAuthorId(commentFromObj.get("id").toString());
                comment.setLastUpdateDateTime(DateAdapter.FbStrToDate(commentObject.get("created_time").toString()));
                comment.setParentArgumentId(parentArgumentId);
                comment.setText(commentText.replace(commentTextWithBracket, ""));
                comment.setType("TEXTUAL_SENTIMENT");
                persister.persistComment(comment);
            } else {
                Argument argument = new Argument();
                argument.setText("Argument : " + commentText);
                argument.setText(commentText);
                argument.setId(commentObject.get("id").toString());
                argument.setLastUpdateDateTime(DateAdapter.FbStrToDate(commentObject.get("created_time").toString()));

                argument.setAuthorId(commentFromObj.get("id").toString());
                argument.setParentClaimId(claim.getId());
                argumentList.add(argument);
                persister.persistArgument(argument);

            }
            Author author = new Author();
            author = populateAuthor(commentFromObj.get("id").toString());
            //Maintaining a hashmap for author ands its id, if repetation happned it will
            //just override the same data
            authorMap.put(author.getName(), author.getId());
            System.out.println("putting " + author.getName() + " " + author.getId());
            persister.persistAuthor(author);

            //System.out.println(commentFromObj.get("id").toString() + commentText);

            ArrayList<Comment> likes = populateLikes(commentObject.get("id").toString());


        }

        /*
         * String json = "{\"product\":{\"name\":\"Banana\",\"id\":\"123\"" +
         * ",\"price\":\"23.0\"}}";
         *
         * XStream xstream = new XStream(new JettisonMappedXmlDriver());
         * xstream.alias("product", Post.class); Post product =
         * (Post)xstream.fromXML(json); System.out.println(product.getName());
         */

    }

    public static String fetchURL(URL postURL) {
        URLConnection yc;
        BufferedReader in;
        String inputLine;
        String jsonString = "";

        try {
            yc = postURL.openConnection();

            in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

            while ((inputLine = in.readLine()) != null) {
                // System.out.println(inputLine);
                jsonString = jsonString + inputLine + "\n";
            }

            in.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonString;
    }

    private static Author populateAuthor(String authorId) {
        Author author = new Author();
        author.setId(authorId);
        URL RESTURL = null;
        try {
            RESTURL = new URL(
                    "https://graph.facebook.com/"
                    + authorId
                    + "?access_token=" + accessToken);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String JSONString = fetchURL(RESTURL);
        //System.out.println(JSONString);
        JSONObject authorObj = (JSONObject) JSONValue.parse(JSONString);
        // author.setAge(DateAdapter.FbStrToDate(authorObj.get("birthday").toString()));
        author.setName(authorObj.get("name").toString());
        author.setImage(getImage(authorId));
        author.setJoinDateTime(DateAdapter.FbStrToDate(authorObj.get(
                "updated_time").toString()));

        return author;
    }

    private static String getImage(String authorId) {
        // TODO Auto-generated method stub
        URL url = null;
        URLConnection conn = null;
        try {
            url = new URL(
                    "https://graph.facebook.com/"
                    + authorId
                    + "/picture?access_token=" + accessToken);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {

            HttpURLConnection ucon = (HttpURLConnection) url.openConnection();
            ucon.setInstanceFollowRedirects(false);
            URL secondURL = new URL(ucon.getHeaderField("Location"));
            conn = secondURL.openConnection();

            //System.out.println(conn.getURL());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conn.getURL().toString();
    }

    private static ArrayList<Comment> populateLikes(String id) {
    	Persister persister = new Persister();
        URL likeURL = null;
        try {
            likeURL = new URL("https://graph.facebook.com/" + id + "/likes/?access_token=" + accessToken);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String jsonString = fetchURL(likeURL);
        JSONObject likesObject = (JSONObject) JSONValue.parse(jsonString);
        JSONArray likesArray = (JSONArray) likesObject.get("data");

        for (int i = 0; i < likesArray.size(); i++) {
            Comment comment = new Comment();
            JSONObject likeObject = (JSONObject) likesArray.get(i);
            String likeID = (String) likeObject.get("id");
            //comment.setId(likeID);
            comment.setAuthorId(likeID);
            comment.setType("FIXED_VOTE");
            comment.setParentArgumentId(id);
            //System.out.println((String)likeObject.get("name")+ "liked");
            persister.persistComment(comment);

        }

        return null;


    }

    private static void fetchLinks(String id, String commentText) {
    	Persister persister = new Persister();
        Pattern p = Pattern.compile("http://.*? ");
        Matcher m = p.matcher(commentText);
        if (m.find()) {
            String Strlink = m.group();
            System.out.println(Strlink);
            Link link = new Link();
            link.setLink(Strlink);
            link.setMsg_id(id);
            Double rank = LinkAnalyzer.fetchRank(Strlink);
            link.setRank(rank);
            
            persister.persistLink(link);
            
        }

    }

    
}
