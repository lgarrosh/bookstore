package com.bookstore.service;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;

import org.springframework.stereotype.Service;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.request.SendMessage;

@Service
public class MenuService {
	
	private static final String TITLE_BOOK = "Alex lesly";
	private static final String CALLBACK = "1";
	
	public SendMessage sendMenuButton(Long chatId) {
		return new SendMessage(chatId, "Тестовый текст").replyMarkup(createKeyboardMarkup());
	}
	
	private InlineKeyboardMarkup createKeyboardMarkup() {
		return new InlineKeyboardMarkup(createKeyboardButton());
	}
	
	private InlineKeyboardButton[] createKeyboardButton() {
		return new InlineKeyboardButton[] {
			new InlineKeyboardButton(TITLE_BOOK).callbackData(CALLBACK)
		};
	}
}