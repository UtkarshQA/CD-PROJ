package com.MyAccount.ObjectRepository;

import org.openqa.selenium.By;

public class CommonObjectRepositories {
	public By lnk_logout = By.xpath("(//span[contains(@class,'logout-btn')])[1]");
	public By lbl_notification  = By.xpath("//div[contains(@class,'warningAlert')]");
	public By lbl_success_msg  = By.id("globalWC_emailConfirmContent");
	public By lbl_val_msg = By.xpath("//div[contains(@class,'warningAlert')]");
	public By lbl_succ_msg = By.xpath("//div[contains(@class,'successAlert')]");
	public By lbl_info_msg = By.xpath("//span[contains(@class,'message')]");
	public By lbl_val_msg_modalDialog = By.xpath("//div[@class='modal-dialog']//div[contains(@class,'warningAlert')]");
	public By lbl_succ_msg_modalDialog = By.xpath("//div[@class='modal-dialog']//div[contains(@class,'successAlert')]");
	public By lnk_paymentCenter = By.xpath("//div[contains(@class,'desktopView')]//label[text()='Payment Center']/parent::a");
	public By img_loading = By.xpath("(//div[contains(@class,'spinner')]//div[contains(@class,'bounce1')])[1]");
	//public By img_logo = By.xpath("//div[@class='logo']//img[contains(@src,'logo.png')]");
	public By btn_iAgree = By.xpath("//button[text()='I Agree']");
	public By lnk_products_productTable = By.xpath("//table[contains(@class,'productTable')]//a[contains(@href,'/product-details/')]/span[contains(@class,'anchorText')]");
	public By lnk_products_flexWhiteBox = By.xpath("//div[contains(@class,'flexWhiteBox')]//a[contains(@href,'/product-details/')]");

	
}
