package com.techmojo.twitter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.techmojo.twitter.service.TwitterService;

@RestController
public class TwitterController {
	
	@Autowired
	TwitterService twitterService;
	
	@PostMapping(value="/api/sendTweet", consumes = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> tweetReceiver(@RequestBody String tweetMsg) {
		ResponseEntity<String> response = null;
		try {
			twitterService.processTweet(tweetMsg);
			response = ResponseEntity.ok("Hey...received your tweet... '"+tweetMsg+"'");
		}catch(Exception e) {
			response = ResponseEntity.ok(null);
			response.status(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@GetMapping(value="/api/getTrendingHashTags", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<String>> getTrendingHashTags() {
		ResponseEntity<List<String>> response = null;
		try {
			List<String> hashTags = twitterService.getTrendingHashTags();
			response = ResponseEntity.ok(hashTags);
		}catch(Exception e) {
			response = ResponseEntity.ok(null);
			response.status(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
		
	}
}
