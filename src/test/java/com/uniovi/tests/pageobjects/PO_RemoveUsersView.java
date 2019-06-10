package com.uniovi.tests.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_RemoveUsersView extends PO_NavView{

	static public int numUsers(WebDriver driver)
	{
		return PO_View.checkElement(driver, "@name", "checkEmail").size();
	}
	
	static public String getEmailUserByPos(WebDriver driver,int pos)
	{
		if(pos<0 || pos>numUsers(driver))
		{
			return "";
		}
		return PO_View.checkElement(driver, "@name", "checkEmail").get(pos).getText();
	}
	
	
	static public void clickCheckUser(WebDriver driver,int pos)
	{
		List<WebElement> elementos=PO_View.checkElement(driver, "@name", "checkEmail");
		elementos.get(pos).click();
	}
	
	static public void clickBtnRemove(WebDriver driver)
	{
		PO_View.checkElement(driver, "@id", "btn_eliminar").get(0).click();
	}
}
