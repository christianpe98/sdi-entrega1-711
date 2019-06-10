package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_AddOfferView extends PO_View{

	static public void addOffer(WebDriver driver, String titulo, String detalles, double precio) {
		WebElement title = driver.findElement(By.id("title_input"));
		title.click();
		title.clear();
		title.sendKeys(titulo);
		
		WebElement description = driver.findElement(By.id("description_input"));
		description.click();
		description.clear();
		description.sendKeys(detalles);
		
		
		WebElement price = driver.findElement(By.id("price_input"));
		price.click();
		price.clear();
		price.sendKeys(""+precio);

		// Pulsar el boton de Alta.
		By boton = By.id("btn_send_addOffer");
		driver.findElement(boton).click();
	}
}
