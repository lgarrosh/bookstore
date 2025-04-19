package com.bookstore.service.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.service.PaymentService;
import com.bookstore.service.bot.handlers.StartCommandHandler;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;

@Service
public class TelegramBotService {

	private static final Logger log = LoggerFactory.getLogger(TelegramBotService.class);

	@Autowired
	private TelegramBot bot;
	
	@Autowired
	private PaymentService paymentService;
	
	
	public void processing(Update update) {
		BaseResponse baseresponse = null;
		if (update.message() != null && update.message().text() != null) {
			baseresponse = handlerCommand(update);
		} else if (update.callbackQuery() != null) {
			baseresponse = handlerCallback(update);
		} else if (update.preCheckoutQuery() != null) {
			baseresponse = paymentService.confirmTransaction(update.preCheckoutQuery().id());
		} else if (update.message().successfulPayment() != null) {
			baseresponse = paymentService.deliverProduct(update.message().chat().id(), update.message().successfulPayment().invoicePayload());
		} if (baseresponse != null) {
			log.info(baseresponse.isOk() ? "Успешно" : "Ошибка");
		}
	}
	
	private BaseResponse handlerCommand(Update update) {
		StartCommandHandler start = new StartCommandHandler();
		if (start.canHandl(update.message().text())) {
			SendMessage sendMessage = start.handle(update);
			BaseResponse response = bot.execute(sendMessage);
			return response;
		}
		return null;
	}
	
	private BaseResponse handlerCallback(Update update) {
		BaseResponse response = paymentService.sendInvoice(update.callbackQuery().from().id(), update.callbackQuery().data());
		if (response.isOk()) {
			response = bot.execute(new AnswerCallbackQuery(update.callbackQuery().id()));
		}
		return response;
	}
}






