package com.bookstore.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.db.BookService;
import com.bookstore.db.entity.Book;
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
	
	private static final String NOT_FAUNT_BOOK = "Книга не найдена";
	private static final String CURRENCY = "XTR";
	private static final String TITLE_BUTTON = "Купить";
	
	

	public Optional<BaseResponse> sendInvoice(Long chatId, String bookId) {
	    Book book = bookService.getBookById(Long.valueOf(bookId)).orElseThrow(() -> new RuntimeException(NOT_FAUNT_BOOK));
	    SendInvoice message = new SendInvoice(chatId, book.getTitle(), book.getAuthor(), 
	    		bookId, CURRENCY, new LabeledPrice(book.getTitle(), book.getPrice()))
	            .replyMarkup(createKeyboardMarkupPay(TITLE_BUTTON));
	    return Optional.ofNullable(bot.execute(message));
	}
	
	public Optional<BaseResponse> confirmTransaction(String preCheckoutId) { // update.preCheckoutQuery() != null
		return Optional.ofNullable(bot.execute(new AnswerPreCheckoutQuery(preCheckoutId)));
	}
	
	public Optional<BaseResponse> deliverProduct(Long chatId, String bookId) { // update.message().successfulPayment() != null
		Book book = bookService.getBookById(Long.valueOf(bookId)).orElseThrow(() -> new RuntimeException(NOT_FAUNT_BOOK));
		SendMessage request = new SendMessage(chatId, book.getFilePath());
		return Optional.ofNullable(bot.execute(request));
	}
	
	private InlineKeyboardMarkup createKeyboardMarkupPay(String title) {
		return new InlineKeyboardMarkup(new InlineKeyboardButton(title).pay());
	}
}
