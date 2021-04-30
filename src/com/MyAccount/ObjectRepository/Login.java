package com.MyAccount.ObjectRepository;

import org.openqa.selenium.By;

public class Login {

	public By txt_userName = By.xpath("//input[@name='username']");
	public By txt_password = By.xpath("//input[@name='password']");
	public By btn_login_enable = By.xpath("//button[@value='Log In'][contains(@class,'false')]");
	public By chk_rememberCheck = By.id("rememberCheck");
	public By lnk_forgot_username = By.xpath("//a[contains(text(),'Forgot username?')]");
	public By lnk_forgot_password = By.xpath("//a[contains(text(),'Forgot password?')]");
	public By btn_register = By.xpath("//button[@value='Submit']");
}
