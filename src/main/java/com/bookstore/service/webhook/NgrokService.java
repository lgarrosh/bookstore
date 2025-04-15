package com.bookstore.service.webhook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NgrokService {

	private static final Logger log = LoggerFactory.getLogger(NgrokService.class);
	private Process process;
	private String publicUrl;

	public NgrokService() {
		log.info("NgrokService been created");
	}

	public String startNgrok(int port) {
		ProcessBuilder processBuilder = new ProcessBuilder("/opt/homebrew/bin/ngrok", "http",
				"http://localhost:" + port, "--log=stdout");

		try {
			process = processBuilder.start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.contains("started tunnel")) {
					publicUrl = line.substring(line.indexOf("https://")).split(" ")[0];
					return (publicUrl);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (publicUrl);
	}

	public void stopNgrok() {
		if (process != null && process.isAlive()) {
			process.destroy();
			log.info("Ngrok завершен.");
		}
	}

	public String getNgrokUrl() {
		return publicUrl;
	}
}
