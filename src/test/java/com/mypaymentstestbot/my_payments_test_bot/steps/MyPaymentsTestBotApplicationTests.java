package com.mypaymentstestbot.my_payments_test_bot.steps;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mypaymentstestbot.my_payments_test_bot.CucumberSpringConfiguration;
import com.mypaymentstestbot.my_payments_test_bot.context.TestContext;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.GetWebhookInfo;
import com.pengrad.telegrambot.response.GetWebhookInfoResponse;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MyPaymentsTestBotApplicationTests extends CucumberSpringConfiguration {
	
	private static final Logger log = LoggerFactory.getLogger(MyPaymentsTestBotApplicationTests.class);

	@Autowired
	private TelegramBot bot;
	
	@Autowired
	private TestContext testContext;
	
	@When("отправляем запрос на получение информации по webhook")
	public void sendRequestGetWebhookInfo() {
		GetWebhookInfo getWebhookInfo = new GetWebhookInfo();
		GetWebhookInfoResponse webhookInfo = bot.execute(getWebhookInfo);
		testContext.set("WebhookInfo", webhookInfo);
	}
	
	@Then("проверяем что webhook установлен")
	public void checkSetWebhook() {
		GetWebhookInfoResponse webhookInfo = testContext.get("WebhookInfo", GetWebhookInfoResponse.class);
		log.info(webhookInfo.toString());
		assertNotNull(webhookInfo.webhookInfo().url());
	}
}
