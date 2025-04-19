package com.bookstore.service.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {
	
	private static final Logger log = LoggerFactory.getLogger(AppProperties.class);
	
	private String telegramBotUrl;
	private String telegramBotToken;
	private String host;
	private Integer port;

	public static final String endpointWebhook = "webhook";

	public AppProperties() {
		log.info("AppProperties bean createed");
	}

	public String getFullUrl() {
		return telegramBotUrl + telegramBotToken;
	}

	public String getTelegramBotUrl() {
		return telegramBotUrl;
	}

	public void setTelegramBotUrl(String telegramBotUrl) {
		this.telegramBotUrl = telegramBotUrl;
	}

	public String getTelegramBotToken() {
		return telegramBotToken;
	}

	public String getHost() {
		return host;
	}

	public void setTelegramBotToken(String telegramBotToken) {
		this.telegramBotToken = telegramBotToken;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public static String getEndpointwebhook() {
		return endpointWebhook;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	@Override
	public String toString() {
		return "AppProperties [telegramBotUrl=" + telegramBotUrl + ", telegramBotToken=" + telegramBotToken + ", host="
				+ host + "]";
	}
	
}
