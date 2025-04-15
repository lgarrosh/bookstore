package com.bookstore.steps;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bookstore.CucumberSpringConfiguration;
import com.bookstore.context.TestContext;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.GetWebhookInfo;
import com.pengrad.telegrambot.response.GetWebhookInfoResponse;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BookstoreApplicationTests extends CucumberSpringConfiguration {
	
	private static final Logger log = LoggerFactory.getLogger(BookstoreApplicationTests.class);

	@Autowired
	private TelegramBot bot;
	
	@Autowired
	private TestContext testContext;
	
	@When("отправляем запрос на получение информации по webhook")
	public void sendRequestGetWebhookInfo() {
		GetWebhookInfo getWebhookInfo = new GetWebhookInfo();
		GetWebhookInfoResponse webhookInfo = bot.execute(getWebhookInfo);
		assertTrue(webhookInfo.isOk());
		testContext.set("WebhookInfo", webhookInfo);
	}
	
	@Then("проверяем что webhook установлен")
	public void checkSetWebhook() {
		GetWebhookInfoResponse webhookInfo = testContext.get("WebhookInfo", GetWebhookInfoResponse.class);
		assertTrue(webhookInfo.webhookInfo().url() != null && !webhookInfo.webhookInfo().url().isEmpty(), "Вебхук не установлен");
	}
}
