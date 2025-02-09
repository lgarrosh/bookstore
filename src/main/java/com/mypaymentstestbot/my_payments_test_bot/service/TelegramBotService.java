package com.mypaymentstestbot.my_payments_test_bot.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;

@Service
public class TelegramBotService {

private static final Logger log = LoggerFactory.getLogger(TelegramBotService.class);
	
	@Autowired
	private TelegramBot bot;
	
	public void processing(Update update) {
		Long chatId = update.message().chat().id();
		String message = update.message().text();

		SendMessage request = new SendMessage(chatId, message);
		BaseResponse baseResponse = bot.execute(request);
		log.info(baseResponse.isOk() ? "Успешно" : "Ошибка");
	}
}
