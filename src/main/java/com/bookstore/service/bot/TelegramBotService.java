package com.bookstore.service.bot;

import java.util.Optional;

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
	
	@Autowired
	private StartCommandHandler start;
	
	
	public void processing(Update update) {
		Optional<BaseResponse> baseResponse = Optional.empty();
		if (update.message() != null && update.message().text() != null) {
			baseResponse = handlerCommand(update);
		} else if (update.callbackQuery() != null) {
			baseResponse = handlerCallback(update);
		} else if (update.preCheckoutQuery() != null) {
			baseResponse = paymentService.confirmTransaction(update.preCheckoutQuery().id());
		} else if (update.message().successfulPayment() != null) {
			baseResponse = paymentService.deliverProduct(update.message().chat().id(), update.message().successfulPayment().invoicePayload());
		} baseResponse.ifPresent(resp -> log.info(resp.isOk() ? "Успешно" : "Ошибка"));
	}
	
	private Optional<BaseResponse> handlerCommand(Update update) {
		if (start.canHandl(update.message().text())) {
			SendMessage sendMessage = start.handle(update);
			return Optional.of(bot.execute(sendMessage));
		}
		return Optional.empty();
	}
	
	private Optional<BaseResponse> handlerCallback(Update update) {
		Optional<BaseResponse> response = paymentService.sendInvoice(update.callbackQuery().from().id(), update.callbackQuery().data());
		if (response.isPresent() && response.get().isOk()) {
				response = Optional.ofNullable(bot.execute(new AnswerCallbackQuery(update.callbackQuery().id())));
		}
		return response;
	}
}






