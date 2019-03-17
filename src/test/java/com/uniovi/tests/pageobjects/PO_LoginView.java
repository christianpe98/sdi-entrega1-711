package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_LoginView extends PO_NavView {

	static public void fillForm(WebDriver driver, String emailp, String passwordp) {
		WebElement email = driver.findElement(By.id("email_input"));
		email.click();
		email.clear();
		email.sendKeys(emailp);
		WebElement password = driver.findElement(By.id("password_input"));
		password.click();
		password.clear();
		password.sendKeys(passwordp);
		
		// Pulsar el boton de Alta.
		By boton = By.id("btn_login_submit");
		driver.findElement(boton).click();
	}

}
