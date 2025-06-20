package com.intimetec.newsportal.console;

import com.intimetec.newsportal.console.app.MainMenuHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;
import java.util.List;

@SpringBootApplication
public class NewsportalConsoleApplication {

	public static void main(String[] args) {
		MainMenuHandler mainMenuHandler = new MainMenuHandler();
		mainMenuHandler.start();
	}

}
