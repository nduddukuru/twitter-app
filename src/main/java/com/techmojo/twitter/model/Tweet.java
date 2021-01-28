package com.techmojo.twitter.model;

import java.util.List;

public class Tweet {
	
	private	List<String> hashTags;
	private String	tweetMsg;
	
	public List<String> getHashTags() {
		return hashTags;
	}
	public void setHashTags(List<String> hashTags) {
		this.hashTags = hashTags;
	}
	public String getTweetMsg() {
		return tweetMsg;
	}
	public void setTweetMsg(String tweetMsg) {
		this.tweetMsg = tweetMsg;
	}
	
}
