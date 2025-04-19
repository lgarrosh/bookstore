package com.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.db.BookService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.LabeledPrice;
import com.pengrad.telegrambot.request.SendInvoice;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.request.AnswerPreCheckoutQuery;

@Service
public class PaymentService {
	
	@Autowired
	private TelegramBot bot;
	
	@Autowired
	private BookService bookService;
	
	
	public BaseResponse sendInvoice(Long chatId, String bookId) {
	    InlineKeyboardButton donateButton = new InlineKeyboardButton("Купить!").pay();
		
	    InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup(donateButton);
	    SendInvoice message = new SendInvoice(chatId, "жжизнь без трусов 2.0", "Автор: Алекс Лесли", "1", "XTR", new LabeledPrice("Электронная книга", 1))
	            .replyMarkup(keyboardMarkup);
	
	    return bot.execute(message);
	}
	
	public BaseResponse confirmTransaction(String preCheckoutId) { // update.preCheckoutQuery() != null
		return bot.execute(new AnswerPreCheckoutQuery(preCheckoutId));
	}
	
	public BaseResponse deliverProduct(Long chatId, String bookId) { // update.message().successfulPayment() != null
		SendMessage request = new SendMessage(chatId, bookService.getBookById(Integer.valueOf(bookId)).getFilePath());
		return bot.execute(request);
	}
}
