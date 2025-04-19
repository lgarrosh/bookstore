package com.bookstore.controller;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.bookstore.configuration.AppProperties;
import com.bookstore.service.bot.TelegramBotService;
import com.google.gson.Gson;
import com.pengrad.telegrambot.model.Update;


@RestController
public class RestControllerWhebHook {

	private static final Logger log = LoggerFactory.getLogger(RestControllerWhebHook.class);
	
	@Autowired
	private TelegramBotService service;

	public RestControllerWhebHook() {
		log.info("RestApiWhebHook been created");
	}

	@PostMapping(AppProperties.endpointWebhook)
	public void telegramDataUpdate(@RequestBody String updateJson) {
		
		try {
			Gson gson = new Gson();
			Update update = gson.fromJson(updateJson, Update.class);
			
			if (update != null) {
				service.processing(update);
			} else {
				log.info("Получено пустое обновление");
			}
		} catch (Exception e) {
			log.info("Получено неожидаемое сообщение от телеграм: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
