package com.uniovi.tests.pageobjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class PO_MyOffers extends PO_View{

	static public List<String> listMyOffersTitle(WebDriver driver)
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
	
	static public void removeOffer(WebDriver driver,int pos)
	{
		List<WebElement> elementos_remove=checkElement(driver, "@name", "remove_link");
		if(pos>=0 && pos<elementos_remove.size())
		{
			elementos_remove.get(pos).click();
		}
	}

	public static int numOffers(WebDriver driver) {
		List<WebElement> elementos_title=checkElement(driver, "@name", "title_value");
		return elementos_title.size();
	}
	
}
