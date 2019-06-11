package com.uniovi.tests.pageobjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_ViewOffers extends PO_NavView{

	
	
	static public void search(WebDriver driver,String search)
	{
		List<WebElement> elementos=checkElement(driver, "id", "search_input");
		elementos.get(0).clear();
		elementos.get(0).sendKeys(search);
		
		WebElement btnBuscador = driver.findElement(By.id("btn_search"));
		btnBuscador.click();
	}
	
	static public List<String> listNameOffers(WebDriver driver)
	{
		
		List<String> nameOfertas=new ArrayList<String>();
		int pagina=1;
		while(elementExists(driver,"link-"+pagina)) {
			List<WebElement> paginas=checkElement(driver, "id", "link-"+pagina);
			paginas.get(0).click();
			if(elementExistsName(driver, "title_value")) {
				List<WebElement> ofertas = PO_View.checkElement(driver, "@name", "title_value");
				for(int j=0;j<ofertas.size();j++)
				{
					nameOfertas.add(ofertas.get(j).getText());
				}
			}
			pagina++;
		}
		
		return nameOfertas;
	}
	
	static public void purchaseOffer(WebDriver driver,String title)
	{
		search(driver,title);
		List<WebElement> ofertas = PO_View.checkElement(driver, "@name", "btn_nopurchased");
		ofertas.get(0).click();
	}
}
