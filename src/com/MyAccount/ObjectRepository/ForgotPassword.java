package com.MyAccount.ObjectRepository;

import org.openqa.selenium.By;

public class ForgotPassword {
	
	public By txt_username = By.id("username");
	public By btn_return_to_login = By.xpath("//button[@value='Return To Login']");
	public By btn_submit_enable = By.xpath("//button[text()='Submit'][contains(@class,'false')]");
	public By btn_submit_disabled = By.xpath("//button[text()='Submit'][contains(@class,'disabled')]");
	public By txt_answer = By.id("answer");
	public By lbl_question = By.xpath("//label[@for='pwd']");

}
