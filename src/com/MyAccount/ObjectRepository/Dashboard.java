package com.MyAccount.ObjectRepository;

import org.openqa.selenium.By;

public class Dashboard {

	public By lbl_welcomeText = By.xpath("//h1[@class='welcomeText']");
	public By btn_payNow = By.xpath("(//div[@class='flexWhiteBox']//div[@class='bottomButton']/span[contains(@class,'agiaBlueButton payNow')])[1]");
	public By lnk_home = By.xpath("//ul[contains(@class,'navbar-nav')]//a[@href='#/dashboard']");
	

}
