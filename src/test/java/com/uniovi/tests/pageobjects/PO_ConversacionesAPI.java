package com.uniovi.tests.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_ConversacionesAPI extends PO_View{
	
	static public void abrirConversacion(WebDriver driver,int pos)
	{
		List<WebElement> elements=checkElement(driver, "@name", "link_abrirChat");
		elements.get(pos).click();
	}
	
	static public int numConversaciones(WebDriver driver)
	{
		List<WebElement> elements=checkElement(driver, "@name", "link_abrirChat");
		return elements.size();
	}
	
	static public void eliminarConversacion(WebDriver driver,int pos)
	{
		List<WebElement> elements=checkElement(driver, "@name", "link_borrarChat");
		elements.get(pos).click();
	}
}
