package com.MyAccount.ObjectRepository;

import org.openqa.selenium.By;

public class ForgotUsername {

	public By txt_planNumber = By.id("planNumber");
	public By txt_firstName = By.xpath("//input[@name='firstName']");
	public By txt_lastName = By.xpath("//input[@name='lastName']");
	public By txt_email = By.xpath("//input[@name='email']");
	public By btn_return_to_login = By.xpath("//button[@value='Return To Login']");
	public By btn_submit_enable = By.xpath("//button[@value='Submit'][contains(@class,'false')]");
	public By btn_submit_disabled = By.xpath("//button[@value='Submit'][contains(@class,'disabled')]");
	
}
