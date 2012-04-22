package analyzer;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Simple Demo to show the power of YQL
 * @see http://idojava.blogspot.com/
 * @author Green Ido
 * 
 * Modified by BJGHOSH to use the YQL spelling query, to return the percentage of correct spelling
 */
public class SpellingAnalyzer {

	public double getCorrectSpellPercent(String[] texts){
		double correctPercentage = 0;
		for(String text: texts){
	        String request = 
	        	"http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20search.spelling%20where%20query%3D%22" 
	        	+ text.replaceAll(" ", "%20") +
	        	"%22&diagnostics=true";
	        
	        HttpClient client = new HttpClient();
	        try {
		        GetMethod method = new GetMethod(request);
		
		        int statusCode;
			
				statusCode = client.executeMethod(method);
			
		        if (statusCode != HttpStatus.SC_OK) {
		                System.err.println("Method failed: " + method.getStatusLine());
		        } else {
		                InputStream rstream = null;
		                rstream = method.getResponseBodyAsStream();
		                // Process response
		                Document response = DocumentBuilderFactory.newInstance()
		                                .newDocumentBuilder().parse(rstream);
		
		                XPathFactory factory = XPathFactory.newInstance();
		                XPath xPath = factory.newXPath();
		                // Get all search Result nodes
		                Node node = (Node) xPath.evaluate("query/results/suggestion",
		                                response, XPathConstants.NODE);
		                if(node == null){
		                	System.out.println("No suggestion: Spelling correct according to YQL");
		                }
		                else{
		                	System.out.println("Spelling Suggestion: " + node.getTextContent());
		                	correctPercentage++;
		                }
		        }
			} catch (HttpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e){
				e.printStackTrace();
			}
   		}
   		
   		correctPercentage = correctPercentage / texts.length;
   		return correctPercentage;
	}
	
	
        /**
         * Find 'food' places for the JPR
         * @param args
         * @throws Exception
         */
        public static void main(String[] args) throws Exception {
        	
        	String text = "I goes to the market. He go to sleep.";
        	new SpellingAnalyzer().getCorrectSpellPercent(new String[]{text});
        	
        }

}

