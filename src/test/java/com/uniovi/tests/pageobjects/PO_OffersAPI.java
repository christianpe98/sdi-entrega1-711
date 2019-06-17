package com.uniovi.tests.pageobjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_OffersAPI extends PO_View{

	static public List<String> listOffersTitle(WebDriver driver)
	{
		List<String> titulos=new ArrayList<String>();
		
		List<WebElement> elementos_title=checkElement(driver, "@name", "title_value");
		
		
		for(int i=0;i<elementos_title.size();i++)
		{
			String title=elementos_title.get(i).getText();
			titulos.add(title);
		}
		
		return titulos;
	}

	public static int numOffers(WebDriver driver) {
		List<WebElement> elementos_title=checkElement(driver, "@name", "title_value");
		return elementos_title.size();
	}
	
	static public void chatOffer(WebDriver driver,int pos)
	{
		List<WebElement> elementos=checkElement(driver, "@name", "link_chat");
		elementos.get(pos).click();
	}
}
