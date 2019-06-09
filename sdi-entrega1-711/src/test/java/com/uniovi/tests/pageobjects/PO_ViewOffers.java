package com.uniovi.tests.pageobjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_ViewOffers extends PO_NavView {

	static public void search(WebDriver driver, String search) {
		List<WebElement> elementos = checkElement(driver, "id", "search_input");
		elementos.get(0).clear();
		elementos.get(0).sendKeys(search);

		WebElement btnBuscador = driver.findElement(By.id("btn_search"));
		btnBuscador.click();
	}

	static public List<String> listNameOffers(WebDriver driver) {

		List<String> nameOfertas = new ArrayList<String>();
		List<WebElement> paginas = PO_View.checkElement(driver, "@class", "page-link");
		for (int i = 1; i < paginas.size(); i++) {
			paginas.get(i).click();
			List<WebElement> ofertas = PO_View.checkElement(driver, "id", "title_value");
			for (int j = 0; j < ofertas.size(); j++) {
				nameOfertas.add(ofertas.get(j).getText());
				System.out.println(ofertas.get(j).getText());
			}
			paginas = PO_View.checkElement(driver, "@class", "page-link");
		}

		return nameOfertas;
	}

	static public void purchaseOffer(WebDriver driver, String title) {
		search(driver, title);
		List<WebElement> paginas = PO_View.checkElement(driver, "@class", "page-link");
		paginas.get(1).click();
		List<WebElement> ofertas = PO_View.checkElement(driver, "@name", "purchase_link");
		ofertas.get(0).click();
	}
}
