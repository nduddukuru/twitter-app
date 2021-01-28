package com.techmojo.twitter.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.techmojo.twitter.model.Tweet;

@Repository
public class TwitterRepository {
	
	@Autowired
	Map<String, Long> hashTagTracker;
	
	public void trackTweets(Tweet tweet) {
		
		List<String> hashTags = tweet.getHashTags();
		if(hashTags!=null && !hashTags.isEmpty()) {
			Long tweetCount = 0L;
			for(String hashTag : hashTags) {
				tweetCount = 1L+(hashTagTracker.get(hashTag)!=null?hashTagTracker.get(hashTag):0L);
				hashTagTracker.put(hashTag, tweetCount);
			}
		}
	}
	
	public Map<String, Long> getHashTagTracker(){
		return hashTagTracker;
	}
}
