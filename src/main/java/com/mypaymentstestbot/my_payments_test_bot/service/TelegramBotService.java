package com.mypaymentstestbot.my_payments_test_bot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.LabeledPrice;
import com.pengrad.telegrambot.request.AnswerPreCheckoutQuery;
import com.pengrad.telegrambot.request.SendInvoice;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;

@Service
public class TelegramBotService {

	private static final Logger log = LoggerFactory.getLogger(TelegramBotService.class);
	private static final String START_COMMAND = "/start";
	
	@Autowired
	private TelegramBot bot;
	
	@Autowired
	private BookService bookService;
	
	public void processing(Update update) {
		
		if (update.message() != null && update.message().text() != null) {

		}
		
		// Отправка счет-фактуры
		if (update.message() != null && 
				update.message().text() != null && 
				update.message().text().equals(START_COMMAND)) {
			BaseResponse baseResponse = start(update.message().chat().id());
			log.info(baseResponse.isOk() ? "Успешно" : "Ошибка");
		}
		
		// Ожидайте обновления с полем successful_payment
		else if (update.message() != null && 
				update.message().successfulPayment() != null) {
			log.info("пользователь " 
					+ update.message().chat().firstName() 
					+ " пожертвовал "
					+ update.message().successfulPayment().totalAmount() 
					+ update.message().successfulPayment().currency());
			BaseResponse baseResponse = deliverGoods(update);
			log.info(baseResponse.isOk() ? "Успешно" : "Ошибка");
		}
		
		// Ожидайте обновления с полем pre_checkout_query
		else if (update.preCheckoutQuery() != null) { 
			log.info("пользователь " + update.preCheckoutQuery().from().firstName() + " хочет пожертвовать");
			// Подтверждение заказа с помощью answerPreCheckoutQuery
			BaseResponse baseResponse = bot.execute(new AnswerPreCheckoutQuery(update.preCheckoutQuery().id()));
			log.info(baseResponse.isOk() ? "Успешно" : "Ошибка");
		} 
		
		else {
			try {
				SendMessage request = new SendMessage(update.message().chat().id(), update.message().text());
				BaseResponse baseResponse = bot.execute(request);
				log.info(baseResponse.isOk() ? "Успешно" : "Ошибка");
			} catch (Exception e) {
				log.info("Ошибка в блоке else в методе processing");
			}
			
		}
		
		
	}
	
	private BaseResponse start(Long chatId) {
		log.info(chatId.toString());
		return sendDonationButton(chatId);
	}
	
	private  BaseResponse sendDonationButton(long chatId) {
        InlineKeyboardButton donateButton = new InlineKeyboardButton("Купить!").pay();  // Устанавливаем параметр pay для кнопки

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup(donateButton);
        SendInvoice message = new SendInvoice(chatId, "жжизнь без трусов 2.0", "Автор: Алекс Лесли", "1", "XTR", new LabeledPrice("Электронная книга", 5))
                .replyMarkup(keyboardMarkup);

        return bot.execute(message);
    }
	
	private BaseResponse deliverGoods(Update update) {
		SendMessage request = new SendMessage(update.message().chat().id(), bookService.getBookById(1).getFilePath());
		return bot.execute(request);
	}
}
