package com.mypaymentstestbot.my_payments_test_bot;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mypaymentstestbot.my_payments_test_bot.utils.AppProperties;


@SpringBootTest
public class ProfileTest {
	
	@Autowired
	private AppProperties appProperties;
	
	@Test
	public void checkProfile() {
		System.out.println("-----------------------------------------------------");
		System.out.println(System.getProperty("spring.profiles.active"));
		System.out.println("\n" + appProperties.toString() + "\n");
		System.out.println("-----------------------------------------------------");
	}
}
