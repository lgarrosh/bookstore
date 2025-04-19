package com.bookstore.service.bot.handlers;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface CommandHandler {
	boolean canHandl(String command);
	SendMessage handle(Update update);
}