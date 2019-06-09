package com.uniovi.tests.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_RemoveUsersView extends PO_NavView {

	static public int numUsers(WebDriver driver) {
		return PO_View.checkElement(driver, "@name", "check_value").size();
	}

	static public String getEmailUserByPos(WebDriver driver, int pos) {
		if (pos < 0 || pos > numUsers(driver)) {
			return "";
		}
		return PO_View.checkElement(driver, "@name", "email_value").get(pos).getText();
	}

	static public void removeUsers(WebDriver driver, List<Integer> posiciones) {
		List<WebElement> elementos = PO_View.checkElement(driver, "@name", "check_value");

		for (Integer pos : posiciones) {
			if (pos < 0 || pos > elementos.size()) {
				return;
			}
		}

		for (int i = 0; i < elementos.size(); i++) {
			if (posiciones.contains(i)) {
				elementos.get(i).click();
			}
		}

		PO_View.checkElement(driver, "@id", "btn_eliminar").get(0).click();

	}

}
