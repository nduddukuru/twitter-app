package com.techmojo.twitter.listener;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

import com.techmojo.twitter.service.TwitterService;

@Component
public class ApplicationContextEventsListerner {
	
	@Autowired
	TwitterService twitterService;
	
	@Autowired
	private AbstractApplicationContext  context; 
	
	@EventListener
    public void onApplicationStartEvent(ApplicationReadyEvent  event) {
		
		System.out.println("@@@@@@@#    !!!Hello!!!..Welcome to TechMojo Twitter App    #@@@@@@@");
		
		int exitCode = twitterService.readTweetFromCmdLine();
		if(exitCode==-1) {
			context.registerShutdownHook();
			System.exit(0);
		}
	}
	
	@EventListener(classes = { ContextClosedEvent.class, ContextStoppedEvent.class })
    public void onApplicationCloseEvent() {
		System.out.println("@@@@@@@#    App is about to stop...Here is the top hashtags trending    @@@@@@@#");
		List<String> trendingHashTags = twitterService.getTrendingHashTags();
		for(String hashTag : trendingHashTags) {
			System.out.println(hashTag);
		}
	}
}
