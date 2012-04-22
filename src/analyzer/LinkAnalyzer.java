package analyzer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import data.FBPostAndCommentsRetriever;

public class LinkAnalyzer {

	private static final String PAGE_RANK_URL = "http://webinfodb.net/a/pr.php?url=";

	public static Double fetchRank(String linkUrl) {

		// http://webinfodb.net/api.php
		// http://webinfodb.net/a/pr.php?url=webinfodb.net
		Double rank = -1.0;
		URL rankURL = null;
		try {
			rankURL = new URL(LinkAnalyzer.PAGE_RANK_URL + linkUrl);
		} catch (MalformedURLException ex) {
			Logger.getLogger(FBPostAndCommentsRetriever.class.getName()).log(
					Level.SEVERE, null, ex);
		}
		String strRank = FBPostAndCommentsRetriever.fetchURL(rankURL);
		System.out.println("Rank = " + strRank);
		try {
			rank = Double.parseDouble(strRank);

		} catch (Exception e) {
			rank = -1.0;
		}
		System.out.println("Returning = " + rank);
		return rank;
	}

}
