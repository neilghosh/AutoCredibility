package test;

import java.io.*;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import org.w3c.dom.*;

/**
 * Simple Demo to show the power of YQL
 * @see http://idojava.blogspot.com/
 * @author Green Ido
 */
public class YQLDemo {

        /**
         * Find 'food' places for the JPR
         * @param args
         * @throws Exception
         */
        public static void main(String[] args) throws Exception {
                String request = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20local.search%20where%20query%3D%22food%22%20and%20location%3D%22crested%20butte%2C%20co%22&format=xml";

                HttpClient client = new HttpClient();
                GetMethod method = new GetMethod(request);

                int statusCode = client.executeMethod(method);
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
                        NodeList nodes = (NodeList) xPath.evaluate("query/results/Result",
                                        response, XPathConstants.NODESET);
                        int nodeCount = nodes.getLength();
                        // iterate over search Result nodes
                        for (int i = 0; i < nodeCount; i++) {
                                // Get each xpath expression as a string
                                String title = (String) xPath.evaluate("Title", nodes.item(i),
                                                XPathConstants.STRING);
                                String summary = (String) xPath.evaluate("Address", nodes
                                                .item(i), XPathConstants.STRING);
                                String url = (String) xPath.evaluate("Url", nodes.item(i),
                                                XPathConstants.STRING);
                                // print out the Title, Summary, and URL for each search result
                                System.out.println("Title: " + title);
                                System.out.println("Address: " + summary);
                                System.out.println("URL: " + url);
                                System.out.println("-----------");

                        }
                }
        }

}
