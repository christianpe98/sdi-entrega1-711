package com.uniovi.tests.pageobjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.uniovi.tests.util.SeleniumUtils;

public class PO_ChatAPI  extends PO_View{

	static public boolean contieneMensaje(WebDriver driver,String mensaje)
	{
		List<WebElement> elements=checkElement(driver, "@name", "texto_mensaje");
		for(WebElement el:elements)
		{
			if(el.getText().equals(mensaje))
				return true;
		}
		return false;
	}
	
	static public void enviarMensaje(WebDriver driver,String mensaje)
	{
		List<WebElement> elements=checkElement(driver, "id", "mensaje");
		elements.get(0).sendKeys(mensaje);
		
		clickElementId(driver, "boton-enviar");
	}
}
