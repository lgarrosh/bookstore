package com.mypaymentstestbot.my_payments_test_bot;


import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mypaymentstestbot.my_payments_test_bot.service.BookService;
import com.mypaymentstestbot.my_payments_test_bot.service.webhook.NgrokService;

@SpringBootTest
public class CheckSystemTest {
	private static final Logger log = LoggerFactory.getLogger(CheckSystemTest.class);
	
	@Autowired
	private NgrokService ngrokService;
	
	@Autowired
	private BookService bookService;
	
	@Test
	public void checkProfile() {
		String activeProfiles = System.getProperty("spring.profiles.active");
		
		Boolean check = activeProfiles != null || ngrokService.getNgrokUrl() != null;
		
		log.info( "active profile = " + activeProfiles);
		
		assertTrue(check, "active profile = " + activeProfiles + ", ngrok url = " + ngrokService.getNgrokUrl());
	}
	
	
	@Test
	public void checkConnectionDB() {
		try {
			bookService.getAllBooks();
		} catch (Exception e) {
			System.err.println("Не удалось совершить запрос к бд: " + e);
		}
	}
}
