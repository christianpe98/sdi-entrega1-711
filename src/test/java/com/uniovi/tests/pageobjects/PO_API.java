package com.uniovi.tests.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_API extends PO_View{

	static public void desconectarse(WebDriver driver)
	{
		List<WebElement> elements=checkElement(driver, "id", "desconectar");
		elements.get(0).click();
		
	}

	public static void ventanaConversaciones(WebDriver driver) {
		List<WebElement> elements=checkElement(driver, "id", "conversacionesActivas");
		elements.get(0).click();
	}

	public static void ventanaOfertas(WebDriver driver) {
		List<WebElement> elements=checkElement(driver, "id", "ofertasDisponibles");
	elements.get(0).click();
	}
	
	
}
