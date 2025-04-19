package com.bookstore.service.webhook;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.bookstore.utils.AppProperties;
import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SetWebhook;
import com.pengrad.telegrambot.response.BaseResponse;

import jakarta.annotation.PostConstruct;

import java.io.File;

@Component
public class WebhookManager {

	private static final Logger log = LoggerFactory.getLogger(WebhookManager.class);

	@Autowired
	private NgrokService ngrokService;
	@Autowired
	private TelegramBot bot;
	@Autowired
	private AppProperties appProperties;

	public WebhookManager() {
		log.info("WebhookManager create been");
	}
	
	private SetWebhook getSetWebhookRequest() {
		String publicUrl;
		SetWebhook setWebhook =  new SetWebhook();
		if (appProperties.getHost() != null) { // if profile == prod
			publicUrl = appProperties.getHost();
			File certificate = new File("/root/webhook.crt");
			setWebhook.certificate(certificate).url(publicUrl + "/" + AppProperties.endpointWebhook);
		} else { // if profile == null
			publicUrl = ngrokService.startNgrok(appProperties.getPort());
			setWebhook.url(publicUrl + "/" + AppProperties.endpointWebhook);
		}
		log.info("publicUrl = " + publicUrl);
		return setWebhook;
	}

	@PostConstruct
	public void setWebhook() {
		SetWebhook webhookRequest = getSetWebhookRequest();
		bot.execute(webhookRequest, new Callback<SetWebhook, BaseResponse>() {
			@Override
			public void onResponse(SetWebhook request, BaseResponse response) {
				if (response.isOk()) {
					log.info("Webhook успешно установлен!");
				} else {
					log.info(response.description());
				}
			}

			@Override
			public void onFailure(SetWebhook request, IOException e) {
				log.info("Ошибка при установке вебхука: " + e.toString());
			}
		});
	}

	@EventListener(ContextClosedEvent.class)
	public void disroyWebhookAndNgrok() {
		if (appProperties.getHost() == null) {
			ngrokService.stopNgrok();
		}
	}
}
