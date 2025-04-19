package com.bookstore;


import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bookstore.configuration.webhook.NgrokService;
import com.bookstore.db.BookService;


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
