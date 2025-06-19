package com.InventoryConsoleApp;

import com.InventoryConsoleApp.client.ProductClientApi;
import com.InventoryConsoleApp.console.MenuHandler;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringBootApplication
public class InventoryConsoleApp {

	public static void main(String[] args) throws Exception {
		MenuHandler menuHandler = new MenuHandler();
		menuHandler.showMenu();
	}

}
