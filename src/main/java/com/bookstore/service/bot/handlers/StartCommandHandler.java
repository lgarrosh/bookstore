package com.bookstore.service.bot.handlers;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.bookstore.service.MenuService;

public class StartCommandHandler implements CommandHandler {
	
	private static final String START = "/start";

	@Override
	public boolean canHandl(String command) {
		return command.equals(START);
	}

	@Override
	public SendMessage handle(Update update) {
		MenuService menu = new MenuService();
		return menu.sendMenuButton(update.message().chat().id());
	}

}
