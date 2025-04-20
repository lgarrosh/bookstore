package com.bookstore.service;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.db.BookService;
import com.bookstore.db.entity.Book;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.request.SendMessage;

@Service
public class MenuService {
	
	private static final String MASSAGE_TEXT = "Выберите товар из списка";
	
	@Autowired
	private BookService bookService;
	
	public SendMessage sendMenuButton(Long chatId) {
		return new SendMessage(chatId, MASSAGE_TEXT).replyMarkup(createKeyboardMarkup());
	}
	
	private InlineKeyboardMarkup createKeyboardMarkup() {
		return new InlineKeyboardMarkup(createKeyboardButton());
	}
	
	private InlineKeyboardButton[] createKeyboardButton() {
		List<InlineKeyboardButton> inlineButton =new ArrayList<InlineKeyboardButton>();
		for (Book book : bookService.getAllByOrderByIdAsc()) {
			inlineButton.add(new InlineKeyboardButton(book.getTitle()).callbackData(String.valueOf(book.getId())));
		}
		return inlineButton.toArray(new InlineKeyboardButton[0]);
	}
}