package com.MyAccount.ObjectRepository;

import org.openqa.selenium.By;

public class PayNow {
	
	String leftCol = "//div[@class='desktopView']//div[@class='flexWhiteBox leftCol']";
	String rightCol = "//div[@class='desktopView']//div[@class='flexWhiteBox rightCol']";

	
	public By leftBox = By.xpath(leftCol);
	public By rightBox = By.xpath(rightCol);

	public By productName = By.xpath(leftCol+"//h3/a");
	public By lbl_1_leftBox = By.xpath(leftCol+"//ul//li[1]/span[2]");
	public By lbl_2_leftBox = By.xpath(leftCol+"//ul//li[2]/span[2]");
	public By lbl_3_leftBox = By.xpath(leftCol+"//ul//li[3]/span[2]");
	public By lbl_4_leftBox = By.xpath(leftCol+"//ul//li[4]/span[2]");

	public By ddl_paymentMethod = By.xpath("//select[@name='selectedPaymentMethodRecId']");
	public By lbl_1_rightBox = By.xpath(rightCol+"//ul//li[1]/span[2]");
	public By lbl_2_rightBox = By.xpath(rightCol+"//ul//li[2]/span[2]");
	public By lbl_3_rightBox = By.xpath(rightCol+"//ul//li[3]/span[2]");
	public By lbl_4_rightBox = By.xpath(rightCol+"//ul//li[4]/span[2]");
	public By lnk_edit_method = By.xpath("//a[text()='Edit method']");
	public By btn_addPaymentMethod = By.xpath("//*[contains(text(),'Add Payment Method')]/parent::button[contains(@class,'agiaBlueButton')]"); 
	public By chk_rememberPayment = By.xpath("//input[@name='remember']");
	public By btn_submitPayment = By.xpath("//button[contains(@class,'agiaBlueButton')][text()='Submit Payment']");
	public By btn_iAgree = By.xpath("//button[contains(@class,'agiaBlueButton')][text()='I Agree']");

	
	
}
