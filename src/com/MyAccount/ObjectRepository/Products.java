package com.MyAccount.ObjectRepository;

import org.openqa.selenium.By;

public class Products {
	
	public By lnk_products = By.xpath("//div[contains(@class,'navigationHeader')]//label[text()='Products']/parent::a");
	public By lnk_show = By.xpath("//a/span[contains(@class,'leftArrow')]");

	
}
