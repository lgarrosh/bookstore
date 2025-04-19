package com.bookstore.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pengrad.telegrambot.TelegramBot;

@Configuration
public class TelegramBotConfiguration {

	private static final Logger log = LoggerFactory.getLogger(TelegramBotConfiguration.class);

	@Autowired
	private AppProperties appProperties;

	@Bean
	public TelegramBot createTelegramBot() {
		TelegramBot bot = new TelegramBot(appProperties.getTelegramBotToken());
		log.info("TelegramBotConfiguration bean created");
		return (bot);
	}
}
