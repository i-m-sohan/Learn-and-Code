package com.intimetec.newsportal.console;

import com.intimetec.newsportal.console.app.menu.MainMenuHandler;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NewsportalConsoleApplication {

	public static void main(String[] args) {
		MainMenuHandler mainMenuHandler = new MainMenuHandler();
		mainMenuHandler.start();
	}

}
