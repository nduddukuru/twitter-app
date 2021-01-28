package com.techmojo.twitter.configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwitterAppConfig {
	
	@Bean
	public Map<String, Long> hashTagTracker(){
		
		return new ConcurrentHashMap<String, Long>();
	}
	
}
