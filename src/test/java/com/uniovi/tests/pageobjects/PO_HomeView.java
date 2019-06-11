package com.uniovi.tests.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.uniovi.tests.util.SeleniumUtils;

public class PO_HomeView extends PO_NavView{

	protected static PO_Properties p = new PO_Properties("messages");
	
	static public void checkWelcome(WebDriver driver, int language) {
		//Esperamos a que se cargue el saludo de bienvenida en Español
		SeleniumUtils.EsperaCargaPagina(driver, "text", p.getString("welcome.message",language), getTimeout());
	}
	
	static public double getSaldo(WebDriver driver)
	{
		PO_HomeView.clickElementId(driver, "profile-menu");
		 List<WebElement> elementos = PO_View.checkElement(driver, "id", "saldo");
		 String saldo=elementos.get(0).getText();
		 String saldoNumero=saldo.replace('€','0');
		 return Double.parseDouble(saldoNumero);
	}
	
	static public void checkChangeIdiom(WebDriver driver, String textIdiom1, String textIdiom2, int locale1, int locale2 ) {
			//Esperamos a que se cargue el saludo de bienvenida en Español
			PO_HomeView.checkWelcome(driver, locale1);
			
			//Cambiamos a segundo idioma
			PO_HomeView.changeIdiom(driver, textIdiom2);
			
			//COmprobamos que el texto de bienvenida haya cambiado a segundo idioma
			PO_HomeView.checkWelcome(driver, locale2);
			
			//Volvemos a Español.
			PO_HomeView.changeIdiom(driver, textIdiom1);
			
			//Esperamos a que se cargue el saludo de bienvenida en Español
			PO_HomeView.checkWelcome(driver, locale1);
	}
	
}
