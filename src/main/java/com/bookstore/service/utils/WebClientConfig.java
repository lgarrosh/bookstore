package com.bookstore.service.utils;


import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;


@Component
public class WebClientConfig {
	
	public static final int TIMEOUT = 1000;
	
	private WebClient webClient;
	
	private static final Logger log = LoggerFactory.getLogger(WebClientConfig.class);
	
	@Autowired
	private AppProperties appProperties;
	
	public WebClientConfig() {
		log.info("WebClientConfig bean created");
	}
	
	@PostConstruct
	private void setWebClient() {
		HttpClient httpClient = HttpClient.create()
	  			  .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT)
	  			  .responseTimeout(Duration.ofMillis(TIMEOUT))
	  			  .doOnConnected(conn -> 
	  			    conn.addHandlerLast(new ReadTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS))
	  			      .addHandlerLast(new WriteTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS)));
	  	
	  	WebClient wClient = WebClient.builder()
	  			.baseUrl(appProperties.getFullUrl())
	              .defaultHeader("Accept", "application/json")
	              .defaultHeader("Content-Type", "application/json")
	              .clientConnector(new ReactorClientHttpConnector(httpClient))
	              .build();
	  		webClient = wClient;
			log.info("WebClientConfig PostConstruct");
	}
	
	public Mono<String> getRequest(String uri) {
    	Mono<String> responseMono = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class);
    	return responseMono;
    }
    
    public Mono<String> postRequest(String uri, HashMap<String, String> body) {
    	Mono<String> responseMono = webClient.post()
    			.uri(uri)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class);
    	return responseMono;
    }

	public WebClient getWebClient() {
		return webClient;
	}
}
