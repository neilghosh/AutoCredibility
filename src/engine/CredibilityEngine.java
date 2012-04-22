package engine;

import java.util.ArrayList;

import db.DatabaseReader;
import db.DatabaseUpdater;

import analyzer.CommentAnalyzer;
import analyzer.RelevanceAnalyser;
import analyzer.SpellingAnalyzer;

import entity.Argument;
import entity.Author;
import entity.Claim;
import entity.Comment;
import entity.Link;

public class CredibilityEngine {
	
	public static final double WEIGHT_COMMUNITY_EMOTION = 0.3;
	public static final double WEIGHT_AUTHOR_CREDIBILITY = 0.2;
	public static final double WEIGHT_SPELL_CORRECTNESS = 0.05;
	public static final double WEIGHT_RELEVANCE = 0.3;
	public static final double WEIGHT_LINK_RANK = 0.15;
	
	public static final double WEIGHT_CREDIBILITY_FROM_ARG = 0.4;
	public static final double WEIGHT_CREDIBILITY_FROM_CLAIM = 0.6;
	//WEIGHT_CREDIBILITY_FROM NAME, WEIGHT_CREDIBILITY_FROM IMAGE
	
	public CredibilityEngine() {
	}
	
	public void updateEmotion4Comments(ArrayList<Comment> commentsList){
		DatabaseUpdater dbu = new DatabaseUpdater();
		for(Comment comment: commentsList){
			comment.analyze();
		}
		dbu.updateCommentsEmotionValue(commentsList);
	}
	
	/**
	 * Updates the community emotion and credibility for the arguments, based on a number of factors
	 * @param argumentsList
	 */
	public void updateCredibility4Arguments(ArrayList<Argument> argumentsList){
		DatabaseReader dbreader = new DatabaseReader();
		DatabaseUpdater dbu = new DatabaseUpdater();
		
		for(Argument arg: argumentsList){
			String argId = arg.getId();
			String argText = arg.getText();
			
			//Community Emotion
			ArrayList<Comment> commentsList = dbreader.fetchCommentsOnArgument(argId);
			double communityEmotionValue = 0.0;
			for(Comment comment: commentsList){
				communityEmotionValue += comment.getEmotionValue();
			}
			
			arg.setCommunityEmotionValue(communityEmotionValue);
			
			//Rank of links mentioned in argument texts
			double linkRankCredibility = computeLinkRankCredibility4Argument(argId, dbreader);
			
			//Argument Credibility
			Claim parentClaim = dbreader.fetchClaim(arg.getParentClaimId());
			double authorCredibility = dbreader.fetchAuthor(arg.getAuthorId()).getCredibility();
			double spellCorrectness = new SpellingAnalyzer().getCorrectSpellPercent(new String[]{argText});
			double relevance = new RelevanceAnalyser().getRelevanceScore(parentClaim.getText(), argText);
			
			double credibility = computeCredibilityValue4Arg(spellCorrectness, authorCredibility, 
									relevance, communityEmotionValue, linkRankCredibility);
			
			arg.setCredibility(credibility);
		}
		
		dbu.updateArgumentsCredibility(argumentsList);
	}

	private double computeLinkRankCredibility4Argument(String argId, DatabaseReader dbreader) {
		double totalLinkRankValue = 0.0;
		ArrayList<Link> links = dbreader.fetchLinks(argId);
		for(Link link: links){
			double thisLinkRank = link.getRank();
			totalLinkRankValue += thisLinkRank;
		}
		totalLinkRankValue /= links.size(); //average link rank
		return totalLinkRankValue;
	}

	private double computeCredibilityValue4Arg(double spellCorrectness,	double authorCredibility, double relevance,
	double communityEmotionValue, double linkRankCredibility) 
	{
		return spellCorrectness * CredibilityEngine.WEIGHT_SPELL_CORRECTNESS +
				authorCredibility * CredibilityEngine.WEIGHT_AUTHOR_CREDIBILITY +
				relevance * CredibilityEngine.WEIGHT_RELEVANCE +
				communityEmotionValue * CredibilityEngine.WEIGHT_COMMUNITY_EMOTION +
				linkRankCredibility * CredibilityEngine.WEIGHT_LINK_RANK;
	}
	
	private double computeCredibilityValue4Author(double credibilityFromClaims, double credibilityFromArgs) 
			{
				return credibilityFromClaims * CredibilityEngine.WEIGHT_CREDIBILITY_FROM_CLAIM +
						credibilityFromArgs * CredibilityEngine.WEIGHT_CREDIBILITY_FROM_ARG;
			}
	
	public void updateCredibility4Authors(ArrayList<Author> authorsList){
		DatabaseReader dbreader = new DatabaseReader();
		DatabaseUpdater dbu = new DatabaseUpdater();
		
		for(Author author: authorsList){
			String authorId = author.getId();
			
			//credibility from his claims statuses
			double credibilityFromClaims = 0.0;
			ArrayList<Claim> claimsList = dbreader.fetchClaimsByAuthor(authorId);
			for(Claim claim: claimsList){
				if(Claim.STATUS_JUSTIFIED.equalsIgnoreCase(claim.getStatus()))
					credibilityFromClaims++;
				else if(Claim.STATUS_FALSIFIED.equalsIgnoreCase(claim.getStatus()))
					credibilityFromClaims--;
			}
			if(claimsList.size() > 0)
				credibilityFromClaims /= claimsList.size();
			
			//credibility from his posted arguments
			double credibilityFromArgs = 0.0;
			ArrayList<Argument> argsList = dbreader.fetchArgumentsByAuthor(authorId);
			for(Argument arg: argsList){
				credibilityFromArgs += arg.getCredibility();
			}
			
			double authorCredibility = computeCredibilityValue4Author(credibilityFromClaims, credibilityFromArgs);
			author.setCredibility(authorCredibility);
		}
		
		dbu.updateAuthorsCredibility(authorsList);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DatabaseReader dbr = new DatabaseReader();
		
		CredibilityEngine engine = new CredibilityEngine();
		
		ArrayList<Comment> commentsList = dbr.fetchCommentsAll();
		engine.updateEmotion4Comments(commentsList);
		
		ArrayList<Argument> argumentsList = dbr.fetchArgumentsAll();
		engine.updateCredibility4Arguments(argumentsList);

		ArrayList<Author> authorsList = dbr.fetchAuthorsAll();
		engine.updateCredibility4Authors(authorsList);
	}

}
