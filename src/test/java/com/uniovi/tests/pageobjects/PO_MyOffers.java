package com.uniovi.tests.pageobjects;

import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.sun.corba.se.impl.ior.NewObjectKeyTemplateBase;
import com.uniovi.entities.Offer;

public class PO_MyOffers extends PO_View{

	static public List<Offer> listMyOffers(WebDriver driver)
	{
		List<Offer> nuestrasOfertas=new ArrayList<Offer>();
		
		List<WebElement> elementos_title=checkElement(driver, "@name", "title_value");
		List<WebElement> elementos_description=checkElement(driver, "@name", "description_value");
		List<WebElement> elementos_price=checkElement(driver, "@name", "price_value");
		
		for(int i=0;i<elementos_title.size();i++)
		{
			String title=elementos_title.get(i).getText();
			String description=elementos_description.get(i).getText();
			String price=elementos_price.get(i).getText();
			Offer oferta=new Offer(title, description, Double.parseDouble(price), null);
			nuestrasOfertas.add(oferta);
		}
		
		return nuestrasOfertas;
	}
	
	static public void removeOffer(WebDriver driver,int pos)
	{
		List<WebElement> elementos_remove=checkElement(driver, "@name", "remove_link");
		if(pos>=0 && pos<elementos_remove.size())
		{
			elementos_remove.get(pos).click();
		}
	}
	
}
