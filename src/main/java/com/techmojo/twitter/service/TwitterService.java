package com.techmojo.twitter.service;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techmojo.twitter.model.Tweet;
import com.techmojo.twitter.repository.TwitterRepository;
import com.techmojo.twitter.utils.MapEntryValueComparator;

@Service
public class TwitterService {
	
	@Autowired
	TwitterRepository twitterRepository;
	
	
	public List<String> getTrendingHashTags(){
		
		Map<String, Long> hashTagTrackerMap = twitterRepository.getHashTagTracker();
		
		List<Entry<String,Long>> hashTagList = new LinkedList<>(hashTagTrackerMap.entrySet());
		if(hashTagTrackerMap.size()>1) {
			Collections.sort(hashTagList, new MapEntryValueComparator());
		}
		
		return getTopTenHashTags(hashTagList);
		
	}
	
	private List<String> getTopTenHashTags(List<Entry<String,Long>> hashTagList) {
		
		List<String> topTenHashTags = new ArrayList<>();
		int count = 1;
		long prevHashTagCnt = 0;
		long currHashTagCnt = 0;
		String currTrendingHashTags = null;
		String prevTrendingHashTags = null;
		for(Entry<String,Long> hashTagEntry : hashTagList) {
			if(count>10)
				break;
			currHashTagCnt = hashTagEntry.getValue();
			currTrendingHashTags = hashTagEntry.getKey().concat(" trending @top-").concat(count+" ").concat("with tweets count-").concat(hashTagEntry.getValue()+" ");
			if(prevHashTagCnt==currHashTagCnt) {
				currTrendingHashTags =  hashTagEntry.getKey().concat(prevTrendingHashTags);
			}else if(prevHashTagCnt > 0) {
				topTenHashTags.add(prevTrendingHashTags);
				count++;
				currTrendingHashTags = hashTagEntry.getKey().concat(" trending @top-").concat(count+" ").concat("with tweets count-").concat(hashTagEntry.getValue()+" ");
			}
			prevHashTagCnt = currHashTagCnt;
			prevTrendingHashTags = currTrendingHashTags;
		}
		if(count<10){
			topTenHashTags.add(prevTrendingHashTags);
		}
		
		return topTenHashTags;
	}
	
	public void processTweet(String tweetMsg) {
		
		
		String[] tweetHashTags = tweetMsg.split("#");
		
		String hashTag = null;
		List<String> hashTags = null;
		
		if(tweetHashTags.length>1) {

			hashTags = new ArrayList<>();
			
			int count = 0;
			for(String tweetHashTag : tweetHashTags) {
				
				if(count==0) {
					count++;
					continue;
				}
				
				int whiteSpaceIndex = tweetHashTag.replaceAll("\\s", "#").indexOf("#");
				
				if(whiteSpaceIndex == -1) 
					hashTag = "#".concat(tweetHashTag);
				else 
					hashTag = "#".concat(tweetHashTag.substring(0, whiteSpaceIndex));
				
				hashTags.add(hashTag);
			}
		}
		
		Tweet tweet = buildTweetObj(tweetMsg, hashTags);
		
		twitterRepository.trackTweets(tweet);
	}
	
	private static Tweet buildTweetObj(String tweetMsg, List<String> hashTags) {
		
		Tweet tweet = new Tweet();
		tweet.setHashTags(hashTags);
		tweet.setTweetMsg(tweetMsg);
		return tweet;
	}
	
	public int readTweetFromCmdLine() {
		
		
		System.out.println("1)  Have anything to tweet!!...tweet here in command line and press enter @@@@@@@");
		System.out.println("2)  Don't like cmd line, then check below rest endpoints for tweets...Happy tweeting!!! @@@@@@@");
		System.out.println("    a) For tweet use this - http://localhost:7777/twitter/api/sendTweet");
		System.out.println("    b) For trending hashtags use this - http://localhost:7777/twitter/api/getTrendingHashTags");
		System.out.println("3)  To exit from App, please input 'exit' and press enter ");
		
		Scanner scanner = null;
		
		try {
			String input = null;
			scanner = new Scanner(new InputStreamReader(System.in));
			while(scanner.hasNext()) {
				input = scanner.nextLine();
				if("exit".equalsIgnoreCase(input.trim()))
					return -1;
				processTweet(input);
				System.out.println("Hey...received your tweet... '"+input+"'");
			}
			
		}finally {
			if(scanner!=null) {
				scanner.close();
			}
		}
		return 1;
	}
}
