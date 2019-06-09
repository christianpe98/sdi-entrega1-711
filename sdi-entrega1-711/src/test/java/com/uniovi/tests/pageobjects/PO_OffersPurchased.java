package com.uniovi.tests.pageobjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.uniovi.entities.Offer;

public class PO_OffersPurchased extends PO_View {

	static public List<Offer> ofertasCompradas(WebDriver driver) {
		List<Offer> ofertas = new ArrayList<Offer>();

		List<WebElement> elementos_title = checkElement(driver, "@name", "title_value");
		List<WebElement> elementos_description = checkElement(driver, "@name", "description_value");
		List<WebElement> elementos_price = checkElement(driver, "@name", "price_value");

		for (int i = 0; i < elementos_title.size(); i++) {
			String title = elementos_title.get(i).getText();
			String description = elementos_description.get(i).getText();
			String price = elementos_price.get(i).getText();
			Offer oferta = new Offer(title, description, Double.parseDouble(price), null);
			ofertas.add(oferta);
		}

		return ofertas;
	}
	
}
