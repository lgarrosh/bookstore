package com.bookstore.service.bot.handlers;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.service.MenuService;

@Service
public class StartCommandHandler implements CommandHandler {
	
	
	@Autowired
	private MenuService menu;
	
//	public StartCommandHandler( MenuService menu) {
//		this.menu = menu;
//	}
	
	private static final String START = "/start";

	@Override
	public boolean canHandl(String command) {
		return command.equals(START);
	}

	@Override
	public SendMessage handle(Update update) {
		return menu.sendMenuButton(update.message().chat().id());
	}

}
