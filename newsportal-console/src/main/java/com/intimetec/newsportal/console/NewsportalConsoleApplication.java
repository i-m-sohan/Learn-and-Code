package com.intimetec.newsportal.console;

import com.intimetec.newsportal.console.menu.MainMenuHandler;
import org.apache.catalina.Context;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NewsportalConsoleApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(NewsportalConsoleApplication.class, args);
		MainMenuHandler mainMenuHandler = context.getBean(MainMenuHandler.class);
		mainMenuHandler.start();
	}

}
