package com.uniovi.tests.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_Purchased extends PO_NavView{

	static public int numCompradas(WebDriver driver)
	{
		List<WebElement> elementos_title=checkElement(driver, "@name", "title");
	return elementos_title.size();
	}
}
